package com.platform.controller;

import com.alibaba.fastjson.JSON;
import com.platform.common.domain.AjaxResult;
import com.platform.common.sysenum.ResultTypeEnum;
import com.platform.entity.CityInfo;
import com.platform.service.IGeoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jianghy
 * @Description: 地理位置
 * @date 2020/12/23 17:04
 */
@RestController
@RequestMapping("/geo")
@Slf4j
public class GeoController {

    @Autowired
    private IGeoService iGeoService;

    /**
     * @Description: 保存城市信息到redis
     * @param  1
     * @return java.lang.String
     * @throws
     * @author jianghy
     * @date 2020/12/23 17:06
     */
    @RequestMapping("/saveCityInfoToRedis")
    public AjaxResult saveCityInfoToRedis(){
        List<CityInfo> cityInfos = new ArrayList<>();
        cityInfos.add(new CityInfo("hefei", 117.17, 31.52));
        cityInfos.add(new CityInfo("anqing", 117.02, 30.31));
        cityInfos.add(new CityInfo("huaibei", 116.47, 33.57));
        cityInfos.add(new CityInfo("suzhou", 116.58, 33.38));
        cityInfos.add(new CityInfo("fuyang", 115.48, 32.54));
        cityInfos.add(new CityInfo("bengbu", 117.21, 32.56));
        cityInfos.add(new CityInfo("huangshan", 118.18, 29.43));
        iGeoService.saveCityInfoToRedis(cityInfos);
        return AjaxResult.success();
    }

    /**
     * @Description: 获取城市的经纬度
     * @param  1 
     * @return com.chinaunicom.common.domain.AjaxResult 
     * @throws
     * @author jianghy
     * @date 2020/12/23 17:30 
     */
    @RequestMapping("/getCityPos")
    public AjaxResult getCityPos(){
        List<Point> cityPos = iGeoService.getCityPos(Arrays.asList("anqing", "suzhou", "xxx").toArray(new String[3]));
        return AjaxResult.successData(ResultTypeEnum.BIZ_SUCCESS.getValue(),ResultTypeEnum.BIZ_SUCCESS.getDesc(),cityPos);
    }


    /**
     * @Description: 获取两个城市之间的距离
     * @param  1
     * @return com.chinaunicom.common.domain.AjaxResult
     * @throws
     * @author jianghy
     * @date 2020/12/23 17:35
     */
    @RequestMapping("/getTwoCityDistance")
    public AjaxResult getTwoCityDistance(){
        double value = iGeoService.getTwoCityDistance("anqing", "huaibei", null).getValue();
        return AjaxResult.successData(ResultTypeEnum.BIZ_SUCCESS.getValue(),ResultTypeEnum.BIZ_SUCCESS.getDesc(),value);
    }


    /**
     * @Description: 获取附近的城市
     * @param  1 
     * @return com.chinaunicom.common.domain.AjaxResult 
     * @throws
     * @author jianghy
     * @date 2020/12/23 17:52 
     */
    @RequestMapping("/getMemberRadius")
    public AjaxResult getMemberRadius(){
        Distance radius = new Distance(200, Metrics.KILOMETERS);
        GeoResults<RedisGeoCommands.GeoLocation<String>> suzhou = iGeoService.getMemberRadius("suzhou", radius, null);
        log.info("suzhou: {}",JSON.toJSONString(suzhou));
        //order by 距离 limit 2, 同时返回距离中心点的距离
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().limit(2).sortAscending();
        GeoResults<RedisGeoCommands.GeoLocation<String>> suzhou2 = iGeoService.getMemberRadius("suzhou", radius, null);
        log.info("suzhou2: {}",JSON.toJSONString(suzhou2));
        return AjaxResult.successData(ResultTypeEnum.BIZ_SUCCESS.getValue(),ResultTypeEnum.BIZ_SUCCESS.getDesc(),suzhou2);
    }


    /**
     * @Description: 获取城市的hash值
     * @param  1 
     * @return com.chinaunicom.common.domain.AjaxResult 
     * @throws
     * @author jianghy
     * @date 2020/12/23 17:54 
     */
    @RequestMapping("/getCityGeoHash")
    public AjaxResult getCityGeoHash(){
        List<String> cityGeoHash = iGeoService.getCityGeoHash(Arrays.asList("anqing", "suzhou", "xxx").toArray(new String[3]));
        return AjaxResult.successData(ResultTypeEnum.BIZ_SUCCESS.getValue(),ResultTypeEnum.BIZ_SUCCESS.getDesc(),cityGeoHash);
    }

}
