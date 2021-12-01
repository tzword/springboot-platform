package com.platform.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author michael.jiang
 * @since 2021-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ann_source")
@ApiModel(value="AnnSource对象", description="")
public class AnnSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("OBJECTID")
    private Long objectid;

    @TableField("RECID")
    private BigDecimal recid;

    @TableId("SECCODE")
    private String seccode;

    @TableField("SECNAME")
    private String secname;

    @TableField("TEXTID")
    private BigDecimal textid;

    @TableField("F001D")
    private Date f001d;

    @TableField("F002V")
    private String f002v;

    @TableField("F003V")
    private String f003v;

    @TableField("F004V")
    private String f004v;

    @TableField("F005N")
    private BigDecimal f005n;

    @TableField("F006V")
    private String f006v;

    @TableField("F007V")
    private String f007v;

    @TableField("F008V")
    private String f008v;

    @TableField("F009V")
    private String f009v;

    @TableField("F010V")
    private String f010v;

    @TableField("F015V")
    private String f015v;

    @TableField("MEMO")
    private String memo;

    @TableField("RECTIME")
    private Date rectime;

    @TableField("OPTYPE")
    private String optype;

    @TableField("createTime")
    private Date createTime;

    private String offset;

    @TableField("updateTime")
    private Date updateTime;

    @TableField("topicId")
    private Integer topicId;

    private Boolean isdownload;


}
