package com.platform.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.entity.AnnSource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author michael.jiang
 * @since 2021-05-22
 */
public interface IAnnSourceService extends IService<AnnSource> {

    AnnSource getAnnSourceInfo(Map<String, String> annMap);



}
