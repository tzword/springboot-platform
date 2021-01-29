package com.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @author jianghy
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "自我介绍")
    private String aboutme;

    @ApiModelProperty(value = "经过MD5加密的密码")
    private String passwd;

    @ApiModelProperty(value = "头像图片")
    private String avatar;

    @ApiModelProperty(value = "1:普通用户，2:房产经纪人")
    private Boolean type;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "是否启用,1启用，0停用")
    private Boolean enable;

    @ApiModelProperty(value = "所属经纪机构")
    private Integer agencyId;

    @ApiModelProperty(value = "用户token")
    private String token;

    @ApiModelProperty(value = "token过期时间")
    private Date tokenExpiredTime;



}
