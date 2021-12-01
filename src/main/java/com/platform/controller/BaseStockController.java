package com.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.platform.common.base.CurdController;
import com.platform.entity.BaseStock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  控制器
 * </p>
 *
 * @author michael.jiang
 * @since 2021-05-25
*/
@RestController
@RequestMapping("/base-stock")
@Api(value = "管理", tags = {"管理"})
public class BaseStockController extends CurdController<BaseStock> {

}
