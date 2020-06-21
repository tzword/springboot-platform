package com.platform.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.dataSources.DataSource;
import com.platform.common.dataSources.DataSourceType;
import com.platform.entity.User;
import com.platform.mapper.UserMapper;
import com.platform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jianghy
 * @since 2020-04-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private BaseMapper<User> mapper;
    
    
    /**
     * @Description: 主库查询
     * @param id 1 
     * @return com.platform.entity.User
     * @throws
     * @author jianghy
     * @date 2020/4/30 12:35 
     */
    @Override
    @DataSource(value = DataSourceType.MASTER)
    public User testMasterGetById(long id) {
        User user = mapper.selectById(id);
        return user;
    }

    /**
     * @Description: 从库查询
     * @param id 1 
     * @return com.platform.entity.User
     * @throws
     * @author jianghy
     * @date 2020/4/30 12:35 
     */
    @Override
    @DataSource(value = DataSourceType.SLAVE)
    public User testSlaveGetById(long id) {
        User user = mapper.selectById(id);
        return user;
    }
}
