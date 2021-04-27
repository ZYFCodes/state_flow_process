package org.zyf.stateflowprocess.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author yanfengzhang
 * @description
 * @date 2021/4/27  23:16
 */
public class StateFlowProcessDataSource {
    private String localMapper = "classpath:mapper/*.xml";

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource stateFlowProcessDataSource(){
        return DruidDataSourceBuilder.create().build();
    }


    @Bean
    @Primary
    public SqlSessionFactory buildSqlSessionFactory(@Qualifier("stateFlowProcessDataSource") DataSource dataSource) throws  Exception {
        SqlSessionFactoryBean bean;
        bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(localMapper));
        return bean.getObject();
    }
}
