package com.platform.common.config;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.platform.common.dataSources.DataSourceType;
import com.platform.common.dataSources.DynamicDataSource;

/**
 * @author jianghy
 * @Description: 多数据源配置
 * @date 2020/4/26 10:47
 */
@Configuration
@MapperScan(basePackages = "com.platform.mapper")
public class MybatisConfig {

    @Autowired
    private PaginationInterceptor paginationInterceptor;

    @Bean
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
        targetDataSources.put(DataSourceType.SLAVE.name(), slaveDataSource);
        return new DynamicDataSource(masterDataSource(), targetDataSources);
    }

    
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource(dynamicDataSource);
//        // 设置mapper.xml的位置路径
//        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml");
//        factoryBean.setMapperLocations(resources);
//        return factoryBean.getObject();
//    }

    /**
     * @Description: 把mybatis的SqlSessionFactory转换为MybatisSqlSessionFactoryBean
     * @param dynamicDataSource 1 
     * @return org.apache.ibatis.session.SqlSessionFactory 
     * @throws
     * @author jianghy
     * @date 2020/4/27 17:25 
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource);

        //设置 MyBatis-Plus 分页插件
        Interceptor[] plugins = {paginationInterceptor};
        sqlSessionFactoryBean.setPlugins(plugins);

        // 设置mapper.xml的位置路径
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean.getObject();

    }

    /**
     * @Description: 配置@Transactional注解事务
     * @param dynamicDataSource 1
     * @return org.springframework.transaction.PlatformTransactionManager
     * @throws
     * @author jianghy
     * @date 2020/4/26 14:09
     */
    @Bean
    public PlatformTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
