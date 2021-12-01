package com.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 公告表
 * </p>
 *
 * @author michael.jiang
 * @since 2021-05-24
 */
@Data
public class TAnnInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //公告真实id(对应mongo里的公告id)
    private String annRefId;

    //股票代码
    private String stockCode;

    //公告标题
    private String title;

    //报告地址
    private String url;

    //公告类型(01:半年度，02:全年度）
    private String annType;

    //发布日期
    private Date publishTime;

    //年份
    private String fiscalYear;

    //企业csf_id
    private String orgCode;

    //创建人
    private String createUser;

    //创建时间
    private Date createTime;

    //修改人
    private String updateUser;

    //修改时间
    private Date updateTime;


}
