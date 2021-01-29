package com.platform.dao;

import com.platform.entity.TestBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jianghy
 * @Description: 测试dao
 * @date 2020/11/13 10:45
 */
@Repository
public interface TestDao extends ElasticsearchRepository<TestBean, Long> {

    List<TestBean> queryTestBeanByNameLike(String name);

    List<TestBean> findByName(String name);
}