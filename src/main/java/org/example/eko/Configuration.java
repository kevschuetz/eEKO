package org.example.eko;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration {


    @Bean
    @Primary
    public DataSource getDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        if(System.getenv("DB_URL") == null){
            dataSourceBuilder.driverClassName("org.h2.Driver");
            dataSourceBuilder.url("jdbc:h2:file:C:/temp/test");
            dataSourceBuilder.username("sa");
            dataSourceBuilder.password("password");
        }else{
            dataSourceBuilder.driverClassName(System.getenv("DB_DRIVER_CLASS"));
            dataSourceBuilder.url(System.getenv("DB_URL"));
            dataSourceBuilder.username(System.getenv("DB_USER"));
            dataSourceBuilder.password(System.getenv("DB_PWD"));
        }
        return dataSourceBuilder.build();
    }
}
