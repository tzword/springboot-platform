package com.platform.service;

import com.platform.entity.TestBean;

import java.util.List;

/**
 * @author jianghy
 * @Description: 测试service
 * @date 2020/11/13 10:48
 */
public interface TestService {
    Iterable<TestBean> findAll();

    void save(List<TestBean> list);

    void save(TestBean bean);

    List<TestBean> findByName(String text);

    List<TestBean> findByNameOrDesc(String text);

}
