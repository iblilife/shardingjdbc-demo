package com.example.shardingjdbc.demo.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = DataSourcePopcornConfig.SCAN_PACKAGE, sqlSessionFactoryRef = DataSourcePopcornConfig.BEAN_SQL_SESSION_FACTORY_NAME)
public class DataSourcePopcornConfig {

    private final static Logger log = LoggerFactory.getLogger(DataSourcePopcornConfig.class);

    /* 多数据原配置 - 修改开始 */
    static final String SCAN_PACKAGE = "com.example.shardingjdbc.demo.mapper.popcorn"; // Mapper包路径
    private static final String MAPPER_LOCATION = "classpath*:mapper/popcorn/*.xml";
    private static final String DATA_SOURCE_PROPERTIES_PREFIX = "inooyee.datasource.popcorn";
    private static final String BEAN_NAME_PREFIX = "popcorn";
    /* 多数据原配置 - 修改结束 */

    static final String BEAN_SQL_SESSION_FACTORY_NAME = BEAN_NAME_PREFIX + "SqlSessionFactory";
    private static final String BEAN_DATA_SOURCE_NAME = BEAN_NAME_PREFIX + "DataSource";
    private static final String BEAN_TRANSACTION_MANAGER_NAME = BEAN_NAME_PREFIX + "TransactionManager";
    private static final String BEAN_SQL_SESSION_TEMPLATE_NAME = BEAN_NAME_PREFIX + "SqlSessionTemplate";
//    private static final String BEAN_MAPPER_HELPER_NAME = BEAN_NAME_PREFIX + "MapperHelper";

    @Bean
    @ConfigurationProperties(prefix = DATA_SOURCE_PROPERTIES_PREFIX)
    public Properties popcornDataSourceProperties() {
        return new Properties();
    }

    @Bean(name = BEAN_DATA_SOURCE_NAME)
    public DataSource dataSource(
            @Qualifier("popcornDataSourceProperties") Properties popcornDataSourceProperties
    ) throws SQLException {
        DruidDataSource popcorn = new DruidDataSource();
        popcorn.configFromPropety(popcornDataSourceProperties);

        ClassPathResource classPathResource = new ClassPathResource("/sharding-jdbc-popcorn-dev.yaml");
        try {
            InputStream inputStream = classPathResource.getInputStream();
            byte[] yamlFileBytes = inputStream.readAllBytes();
            return YamlShardingSphereDataSourceFactory.createDataSource(popcorn, yamlFileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("启动失败，获取数据配置文件失败", e);
        }
    }

    @Bean(name = BEAN_SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier(value = BEAN_DATA_SOURCE_NAME) DataSource dataSource
//            @Qualifier(value = "dataSource") DataSource dataSource
    ) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));

        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setObjectWrapperFactory(new MapWrapperFactory()); // 设置下划线转换驼峰命名处理

        // 配置通用 mybatis Mapper
        MapperHelper mapperHelper = new MapperHelper();
        //特殊配置
        Config config = new Config();
        config.setNotEmpty(false);
        config.setIDENTITY("MYSQL");
        //更多详细配置: http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/2.Integration.md
        mapperHelper.setConfig(config);
        mapperHelper.registerMapper(Mapper.class);
        configuration.setMapperHelper(mapperHelper);

        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }

    @Bean(name = BEAN_TRANSACTION_MANAGER_NAME)
    public DataSourceTransactionManager transactionManager(@Qualifier(BEAN_DATA_SOURCE_NAME) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = BEAN_SQL_SESSION_TEMPLATE_NAME)
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier(value = BEAN_SQL_SESSION_FACTORY_NAME) SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
