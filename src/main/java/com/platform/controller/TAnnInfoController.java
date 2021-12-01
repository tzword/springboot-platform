package com.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.platform.common.base.CurdController;
import com.platform.entity.TAnnInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 公告表 控制器
 * </p>
 *
 * @author michael.jiang
 * @since 2021-05-24
*/
@RestController
@RequestMapping("/t-ann-info")
@Api(value = "公告表管理", tags = {"公告表管理"})
public class TAnnInfoController extends CurdController<TAnnInfo> {

}
