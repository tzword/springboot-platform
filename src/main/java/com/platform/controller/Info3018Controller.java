package com.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.platform.common.base.CurdController;
import com.platform.entity.Info3018;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 公告摘要表 控制器
 * </p>
 *
 * @author michael.jiang
 * @since 2021-06-29
*/
@RestController
@RequestMapping("/info3018")
@Api(value = "公告摘要表管理", tags = {"公告摘要表管理"})
public class Info3018Controller extends CurdController<Info3018> {

}
