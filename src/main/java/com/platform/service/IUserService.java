package com.platform.service;

import com.platform.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jianghy
 * @since 2020-04-28
 */
public interface IUserService extends IService<User> {
    //测试主数据源
    User testMasterGetById(long id);

    //测试备数据源
    User testSlaveGetById(long id);
}
