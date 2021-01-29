package com.platform.resp;

import com.platform.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:用户登录信息
 * @author jianghy
 * @date 2020/8/4 10:37
 */
@Data
@ApiModel(description = "登录成功返回")
public class UserLoginRes {

    @ApiModelProperty(value="用户")
    private User user;

    @ApiModelProperty(value="是否为超级管理员（0：否，1： 是）")
    private Integer roleType;

    @ApiModelProperty(value="组织结构码")
    private String orgCode;
}
