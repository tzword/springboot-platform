package com.platform.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.common.jg.common.DateConstants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Document(indexName = "idx_hf_ann_search",type = "_doc")
public class AnnInfoEs {
    @Id
    private Long id;

    /**
     * 公告真实id(对应mongo里的公告id)
     */
    private String annRefId;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 报告地址
     */
    private String url;

    /**
     * 公告类型(01:半年度，02:全年度）
     */
    private String annType;

    /**
     * 发布日期
     */
    @JsonFormat(pattern = DateConstants.SIMPLE_DATE_FORMAT)
    private Date publishTime;

    /**
     * 年份
     */
    private String fiscalYear;

    /**
     * 企业csf_id
     */
    private String orgCode;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateConstants.SIMPLE_DATE_TIME_FORMAT)
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = DateConstants.SIMPLE_DATE_TIME_FORMAT)
    private Date updateTime;
}
