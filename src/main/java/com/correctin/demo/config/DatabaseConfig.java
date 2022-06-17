//package com.correctin.demo.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//    @Configuration
//    public class DatabaseConfig {
//
//        @Value("${spring.datasource.url}")
//        private String dbUrl;
//
//        @Value("${spring.datasource.username}")
//        private String username;
//
//        @Value("${spring.datasource.password}")
//        private String password;
//
//        @Bean
//        public DataSource dataSource() {
//            HikariConfig config = new HikariConfig();
//            config.setJdbcUrl(dbUrl);
//            config.setUsername(username);
//            config.setPassword(password);
//            return new HikariDataSource(config);
//        }
//    }

