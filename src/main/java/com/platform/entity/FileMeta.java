package com.platform.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author jun.wan  2020/4/26.
 */
@Data
@ToString
public class FileMeta {


    /**
     * 版本
     */
    private String msgVersion = "1.0";

    /**
     * 任务ID (源于穆迪系统)
     */
    private String marcoPoloMsgId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件MD5
     */
    private String fileMD5;

    /**
     * 是否重传
     */
    private Boolean reusePath = true;

    /**
     * 机构ID (公司 csf_id/tick)
     */
    private String orgId;

    /**
     * 大类
     */
    private String categoryCode;

    /**
     * 小类
     */
    private String typeCode;

    /**
     * 财年
     */
    private String fiscalYear;

    /**
     * 财季
     */
    private String reportPeriod;

    /**
     * 外部系统id
     */
    private String extSysId = "csf_announce_collector";

    /**
     * 外部系统文件id
     */
    private String extFileId;

    /**
     * 发布时间
     */
    private String publishDate;

    /**
     * 标题 (穆迪无)
     */
    private String title;
}
