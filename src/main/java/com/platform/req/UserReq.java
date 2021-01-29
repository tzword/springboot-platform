package com.platform.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author jianghy
 * @Description: 用户请求参数
 * @date 2020/11/13 16:14
 */
@Data
@ApiModel(value = "测试用户请求",description = "测试用户请求")
public class UserReq {

    @ApiModelProperty(value = "用户名",required = true)
    @NotNull
    private String username;

    @ApiModelProperty(value = "密码",required = true)
    @NotEmpty
    private String password;

    @ApiModelProperty(value = "简介",required = true)
    @NotBlank
    private String content;

}
