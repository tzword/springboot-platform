package com.platform.controller;

import com.platform.entity.TAnnInfo;
import com.platform.service.IAnnEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.platform.common.base.CurdController;
import com.platform.entity.AnnSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * <p>
 *  控制器
 * </p>
 *
 * @author michael.jiang
 * @since 2021-05-22
*/
@RestController
@RequestMapping("/ann-source")
@Api(value = "管理", tags = {"管理"})
public class AnnSourceController extends CurdController<AnnSource> {

    @Autowired
    private IAnnEsService iAnnEsService;

    @GetMapping("/aa")
    public void addes(){
        TAnnInfo tAnnInfo = new TAnnInfo();
        tAnnInfo.setOrgCode("111");
        tAnnInfo.setUpdateUser("system");
        tAnnInfo.setCreateUser("system");
        tAnnInfo.setPublishTime(new Date());
        tAnnInfo.setUrl("www.baidu.com");
        tAnnInfo.setAnnRefId("sssssssssssssss");
        tAnnInfo.setAnnType("0005");
        tAnnInfo.setFiscalYear("1111");
        tAnnInfo.setTitle("3e3d");
        tAnnInfo.setStockCode("dfdsf");
        tAnnInfo.setId(12L);
        iAnnEsService.save(tAnnInfo);
    }
}
