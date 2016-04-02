package com.github.monikaemm;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DbAccess {

    private static JdbcTemplate template;

    public static JdbcTemplate getTemplate() {
        if (template == null) {
            template = initTemplate();
        }
        return template;
    }

    private static JdbcTemplate initTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(getDbUrl());
        return new JdbcTemplate(dataSource);
    }

    private static String getDbUrl() {
        String host = System.getProperty("db.host");
        String port = System.getProperty("db.port");
        String schema = System.getProperty("db.schema");
        String user = System.getProperty("db.user");
        String password = System.getProperty("db.password");
        return String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s&useSSL=false", host, port, schema, user, password);
    }
}
