package com.minhnbnt.shopmanager.configurations;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import javax.sql.DataSource;

@ApplicationScoped
public class DatasourceConfiguration {

    @Resource(name = "jdbc/database")
    private DataSource dataSource;

    @Produces
    public DataSource dataSource() {
        return dataSource;
    }
}
