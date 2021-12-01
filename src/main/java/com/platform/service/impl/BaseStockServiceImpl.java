package com.platform.service.impl;

import com.platform.entity.BaseStock;
import com.platform.mapper.BaseStockMapper;
import com.platform.service.IBaseStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author michael.jiang
 * @since 2021-05-25
 */
@Service
public class BaseStockServiceImpl extends ServiceImpl<BaseStockMapper, BaseStock> implements IBaseStockService {

}
