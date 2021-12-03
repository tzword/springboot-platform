package com.platform.entity;

import java.math.BigDecimal;
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
 * 公告摘要表
 * </p>
 *
 * @author michael.jiang
 * @since 2021-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Info3018对象", description="公告摘要表")
public class Info3018 implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "正文ID")
    @TableId("TEXTID")
    private BigDecimal textid;

    @ApiModelProperty(value = "证券代码")
    @TableField("SECCODE")
    private String seccode;

    @ApiModelProperty(value = "证券简称")
    @TableField("SECNAME")
    private String secname;

    @ApiModelProperty(value = "公告日期")
    @TableField("DECLAREDATE")
    private Date declaredate;

    @ApiModelProperty(value = "公告标题")
    @TableField("F001V")
    private String f001v;

    @ApiModelProperty(value = "巨潮分类")
    @TableField("F002V")
    private String f002v;

    @ApiModelProperty(value = "交易所分类")
    @TableField("F003V")
    private String f003v;

    @ApiModelProperty(value = "摘要详情")
    @TableField("F004V")
    private String f004v;

    @ApiModelProperty(value = "公告编码")
    @TableField("F005V")
    private String f005v;

    @ApiModelProperty(value = "来源")
    @TableField("F006V")
    private String f006v;

    @ApiModelProperty(value = "分类编码")
    @TableField("F007V")
    private String f007v;

    @ApiModelProperty(value = "备注")
    @TableField("MEMO")
    private String memo;

    @ApiModelProperty(value = "接收时间")
    @TableField("createTime")
    private Date createTime;


}
