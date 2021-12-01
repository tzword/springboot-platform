package com.platform.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
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
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("base_stock")
@ApiModel(value="BaseStock对象", description="")
public class BaseStock implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String code;

    private String ticker;

    private String orgid;

    private String orgEn;

    private String org;

    private String abbr;

    private String abbrEn;

    private String abbrPy;

    private String mktCode;

    private String mktEn;

    private String mkt;

    private String listStatus;

    private String listDt;

    private String listEdt;

    private String csfid;

    private Date createTime;

    private Date updateTime;


}
