package com.platform.controller;

import com.platform.common.base.CurdController;
import com.platform.entity.BizProduct;
import com.platform.service.impl.BizProductServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  控制器
 * </p>
 *
 * @author jianghy
 * @since 2021-01-11
*/
@RestController
@RequestMapping("/biz-product")
@Api(value = "管理", tags = {"管理"})
@Slf4j
public class BizProductController extends CurdController<BizProduct> {

    @Autowired
    private BizProductServiceImpl bizProductService;

    @GetMapping("/decrementProductStore")
    public String decrementProductStore(String productId){
        boolean b = bizProductService.decrementProductStore(productId);
        if (!b){
            log.info("库存不足");
        }
        return "";
    }

}