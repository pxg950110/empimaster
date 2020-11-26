package empi.core.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
    
    @Value("${autoconfig.datasource.driverClassName}")
    private  String jdbcClass;
    @Value("${autoconfig.datasource.jdbcUrl}")
    private  String url;
    @Value("${autoconfig.datasource.username}")
    private  String userName;
    @Value("${autoconfig.datasource.password}")
    private  String password;

    @Bean("autoconfigDataSource")
    @Qualifier("autoconfigDataSource")
    @ConfigurationProperties(prefix = "autoconfig.datasource")
    @Primary
    public DataSource mysqlDataSource(){
        return DataSourceBuilder.create().build();
    }

}
