package empi.core.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages ={"empi.core.dao"},
        sqlSessionTemplateRef = "autoconfigSqlSessionTemplate")
public class DataBaseConfig {
     /**
     * 配置session工厂
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean("autoconfigSqlSessionFactory")
    @Primary
    public SqlSessionFactory autoconfigSqlSessionFactory(
            @Qualifier("autoconfigDataSource") DataSource dataSource
    ) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/mybatis/**Mapper.xml"));
        return sqlSessionFactoryBean.getObject();

    }


    /**
     * 配置事务管理
     * @param dataSource
     * @return
     */
    @Bean("autoconfigDataSourceTransactionManager")
    @Primary
    public DataSourceTransactionManager appDataSourceTransactionManager(
            @Qualifier("autoconfigDataSource") DataSource dataSource
    ) {
        return new DataSourceTransactionManager(dataSource);
    }

    //SqlSessionTemplate
    @Bean("autoconfigSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate autoconfigSqlSessionTemplate(@Qualifier("autoconfigSqlSessionFactory")
                                                            SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
