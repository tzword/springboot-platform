package com.platform.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author jianghy
 * @Description: 钉钉消息请求
 * @date 2021/1/27 17:07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DingdingReq {
    @ApiModelProperty(value = "手机号",required = true)
    @NotNull(message = "手机号不能为空")
    private List<String> mobiles;

    @ApiModelProperty(value = "消息内容",required = true)
    @NotNull(message = "消息内容不能为空")
    private String content;
}
