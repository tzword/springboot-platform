//package com.platform.common.jg.kafka;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.amazonaws.services.s3.model.PutObjectResult;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.platform.common.jg.common.DASFileConstants;
//import com.platform.common.jg.util.AwsS3Util;
//import com.platform.common.jg.util.DateUtil;
//import com.platform.common.kafka.KafkaProducer;
//import com.platform.entity.AnnSource;
//import com.platform.entity.BaseStock;
//import com.platform.entity.FileMeta;
//import com.platform.entity.TAnnInfo;
//import com.platform.service.IAnnEsService;
//import com.platform.service.IAnnSourceService;
//import com.platform.service.IBaseStockService;
//import com.platform.service.ITAnnInfoService;
//import com.platform.sysenum.*;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Component;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author: michael.jiang
// * @Date: 2021/5/21 13:42
// * @Description: 巨潮消费端
// */
//
//@Component
//@Slf4j
//public class JcKafkaConsumer {
//    @Autowired
//    private IAnnSourceService iAnnSourceService;
//    @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private AwsS3Util awsS3Util;
//    @Value("#{'${ann.types:}'.split(',')}")
//    private List<String> annTypes;
//    @Value("${ann.typeUrl}")
//    private String annTypeUrl;
//    @Value("#{'${das.announce.type.q1:}'.split(',')}")
//    private List<String> announceTypeQ1;
//
//    @Value("#{'${das.announce.type.q2:}'.split(',')}")
//    private List<String> announceTypeQ2;
//
//    @Value("#{'${das.announce.type.q3:}'.split(',')}")
//    private List<String> announceTypeQ3;
//
//    @Value("#{'${das.announce.type.q4:}'.split(',')}")
//    private List<String> announceTypeQ4;
//
//
//    @Autowired
//    private ITAnnInfoService itAnnInfoService;
//
//    @Autowired
//    private IBaseStockService iBaseStockService;
//
//    @Autowired
//    private IAnnEsService iAnnEsService;
//
//    /**
//     * @return
//     * @description 巨潮上传s3接入流程
//     * <p>
//     * 1.获取kafka的json数据
//     * 2.监听file_download_info的表的更新，
//     * 3.判断如果isdownload是1，关联获取公告的信息，判断是不是A股
//     * 4.调用算法，获取公告的类型和编码，判断编码是不是在我们规定的算法里面
//     * 5.如果在里面，获取公告的下载地址
//     * 6.读取公告的下载地址，然后上传到s3
//     * 7.公告信息入库
//     * 8.上传完后编辑s3的公告信息，然后把json信息上传到s3
//     * @author michael.jiang
//     * @date 2021/5/21 13:46
//     */
//    @KafkaListener(topics = KafkaProducer.TOPIC_TEST, groupId = KafkaProducer.TOPIC_GROUP1)
//    public void topic_test(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
//        try {
//            Optional message = Optional.ofNullable(record.value());
//            if (!message.isPresent()) {
//                return;
//            }
//            Object msg = message.get();
//            log.info("topic_test 消费了： Topic:" + topic + ",Message:" + msg);
//            ack.acknowledge();
//
//            //解析binlog语句
//            log.info("msg:{}", msg);
//            String binlogStr = msg.toString();
//            JSONObject jsonObject = JSON.parseObject(binlogStr);
//            Map<String, String> annMap = setData(jsonObject);
//            if (null == annMap) {
//                return;
//            }
//            //判断是否下载完成
//            String isDownload = annMap.get("isDownload");
//            if (DownTypeEnum.UN_DOWN.getValue().equals(isDownload)) {
//                return;
//            }
//            //查询公告数据
//            AnnSource ann = iAnnSourceService.getAnnSourceInfo(annMap);
//            //判断是不是A股
//            String f008v = ann.getF008v();
//            if (!f008v.equals(BinLogSecTypeEnum.A.getDesc())) {
//                return;
//            }
//            Map<String, Object> map2 = new HashMap<>();
//            List<String> list = new ArrayList<>();
//            list.add(ann.getF002v());
//            map2.put("titles", list);
//
//            //调用算法
//            MultiValueMap map = new LinkedMultiValueMap<>();
//            map.add("parameter", map2);
//            HttpHeaders headers = new HttpHeaders();
//            HttpEntity requestBody = new HttpEntity(map, headers);
//            ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(annTypeUrl, requestBody, JSONObject.class);
//            JSONObject body = responseEntity.getBody();
//            if (null != body) {
//                JSONObject json = body.getJSONObject("message");
//                JSONArray result = json.getJSONArray("result");
//                JSONObject obj = result.getJSONObject(0);
//                String code = obj.getString("code");
//
//                if (annTypes.contains(code)) {
//                    String fileName = annMap.get("fileName");
//                    String filePath = annMap.get("filePath");
//                    //上传到s3上的地址
//                    String s3upload = awsS3Util.uploadFile(fileName, filePath);
//                    if (null == s3upload){
//                        return;
//                    }
//                    //把公告信息插入数据库
//                    TAnnInfo tAnn = generateAnnInfo(annMap, code, ann);
//                    //组装上传s3的json对象
//                    tAnn.setUrl(s3upload);
//                    FileMeta fileMeta = generateMetaFile(tAnn);
//                    //把json文件上传到s3上
//                    PutObjectResult putObjectResult = awsS3Util.uploadS3(fileMeta);
//                    if (null != putObjectResult.getETag()) {
//                        log.info("上传成功：{}", putObjectResult.getETag());
//                    }
//                    //推送到es
//                    iAnnEsService.save(tAnn);
//                }
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//
//    }
//
//
//    /**
//     * 组装公告参数
//     * @param jsonObject
//     * @return
//     */
//    public Map<String, String> setData(JSONObject jsonObject) {
//        if (null != jsonObject && jsonObject.containsKey("type") && jsonObject.containsKey("table")) {
//            //binlog解析类型
//            String type = jsonObject.getString("type");
//            //binlog表
//            String table = jsonObject.getString("table");
//            //如果是UPDATE代表是更新
//            if (BinLogTypeEnum.UPDATE.getDesc().equals(type) && BinLogTableEnum.FILE_DOWNLOAD_INFO.getDesc().equals(table)) {
//                JSONArray data = jsonObject.getJSONArray("data");
//                //取第一条
//                JSONObject jsonDetail = data.getJSONObject(0);
//                //公告发布时间
//                String updateTime = jsonDetail.getString("updateTime");
//                //获取表名的后缀
//                String tableSuffix = updateTime.substring(0, 10).replaceAll("-", "");
//                String annTableName = BinLogTableEnum.ANNOUNCEMENT_INFO.getDesc() + "_" + tableSuffix;
//                //放在map里
//                Map<String, String> map = new HashMap<>();
//                map.put("topicId", jsonDetail.getString("topicId"));
//                map.put("seccode", jsonDetail.getString("SECCODE"));
//                map.put("textid", jsonDetail.getString("TEXTID"));
//                map.put("isDownload", jsonDetail.getString("isDownload"));
//                //获取公告地址
//                map.put("filePath", jsonDetail.getString("filePath"));
//                map.put("fileName", jsonDetail.getString("fileName"));
//                map.put("annTableName", annTableName);
//                return map;
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * 封装JSON文件实体
//     *
//     * @param ann
//     * @return
//     */
//    public FileMeta generateMetaFile(TAnnInfo ann) {
//        FileMeta meta = new FileMeta();
//        meta.setMarcoPoloMsgId(ann.getId() + DASFileConstants.S3_FILE_NAME_SP + UUID.randomUUID().toString());
//        meta.setFileName(ann.getTitle() + ".PDF");
//        meta.setFilePath(ann.getUrl().startsWith("/") ? ann.getUrl().replaceFirst("/", "") : ann.getUrl());
//        meta.setFileMD5("");//
//        // 机构id
//        meta.setOrgId(ann.getOrgCode());
//        // 大类小类
//        meta.setCategoryCode(DASFileConstants.CATEGORY_CODE_ANNOUNCE_A);
//        meta.setTypeCode(ann.getAnnType());
//        // 财年财季
//        meta.setFiscalYear(ann.getFiscalYear());
//        meta.setReportPeriod(period(ann));
//        String pattern = "yyyy-MM-dd HH:mm:ss";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//        //发布日期
//        meta.setPublishDate(simpleDateFormat.format(ann.getCreateTime()));
//        //主键id
//        meta.setExtFileId(ann.getAnnRefId());
//        meta.setTitle(ann.getTitle());
//        return meta;
//    }
//
//
//    /**
//     * 封装公告实体
//     *
//     * @param ann
//     * @return
//     */
//    public TAnnInfo generateAnnInfo(Map<String, String> annMap, String code, AnnSource ann) {
//        String topicId = annMap.get("topicId");
//        String seccode = annMap.get("seccode");
//        String textid = annMap.get("textid");
//        TAnnInfo tAnnInfo = new TAnnInfo();
//        //公告唯一id
//        tAnnInfo.setAnnRefId(topicId + seccode + textid);//公告id= topicId+seccode+textid
//        //股票代码
//        tAnnInfo.setStockCode(seccode);
//        //公告类型编码
//        tAnnInfo.setAnnType(code);
//        //公告标题
//        tAnnInfo.setTitle(ann.getF002v());
//        //公告地址（巨潮pdf地址）
//        tAnnInfo.setUrl(ann.getF003v());
//        //年份
//        tAnnInfo.setFiscalYear(DateUtil.getYear(ann.getF001d()));
//        //公告日期
//        tAnnInfo.setCreateTime(ann.getF001d());
//        //发布日期
//        tAnnInfo.setPublishTime(new Date());
//        //创建人
//        tAnnInfo.setCreateUser("system");
//        //修改人
//        tAnnInfo.setUpdateUser("system");
//        //csfid
//        LambdaQueryWrapper<BaseStock> wrapper = Wrappers.lambdaQuery();
//        wrapper.eq(BaseStock::getTicker, seccode).last("limit 1");
//        BaseStock bs = iBaseStockService.getOne(wrapper);
//        tAnnInfo.setOrgCode(bs.getCsfid());
//        itAnnInfoService.save(tAnnInfo);
//        return tAnnInfo;
//    }
//
//
//    /**
//     * 公告类型	manual_typ
//     * 年度报告全文	100101
//     * 年度报告修订	100102
//     * 中期报告全文	100201
//     * 中期报告修订	100202
//     * 一季度报告全文	100301
//     * 一季度报告修订	100302
//     * 三季度报告全文	100306
//     * 三季度报告修订	100307
//     * 可转债募集说明书 141601
//     * <p>
//     * 后续可能有月度报告
//     */
//    String period(TAnnInfo ann) {
//
//        // 年第一季度报告: Q1
//        // 年半年度报告: Q2
//        // 年第三季度报告: Q3
//        // 年年度报告: Q4
//
//        if (announceTypeQ1.contains(ann.getAnnType())) {
//            return "Q1";
//        }
//        if (announceTypeQ2.contains(ann.getAnnType())) {
//            return "Q2";
//        }
//        if (announceTypeQ3.contains(ann.getAnnType())) {
//            return "Q3";
//        }
//        if (announceTypeQ4.contains(ann.getAnnType())) {
//            return "Q4";
//        }
//
//        String title = ann.getTitle();
//        if (title != null) {
//            if (title.contains(AnnPeriodEnum.ANNOUNCE_PERIOD_TITLE_Q1.getDesc())) {
//                return "Q1";
//            }
//            if (title.contains(AnnPeriodEnum.ANNOUNCE_PERIOD_TITLE_Q2.getDesc())) {
//                return "Q2";
//            }
//            if (title.contains(AnnPeriodEnum.ANNOUNCE_PERIOD_TITLE_Q3.getDesc())) {
//                return "Q3";
//            }
//            if (title.contains(AnnPeriodEnum.ANNOUNCE_PERIOD_TITLE_Q4.getDesc())) {
//                return "Q4";
//            }
//        }
//        return null;
//    }
//
//
//}
