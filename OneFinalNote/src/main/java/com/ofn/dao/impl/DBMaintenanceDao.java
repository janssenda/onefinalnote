package com.ofn.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.sql.CallableStatement;
import java.sql.Connection;

public class DBMaintenanceDao {

    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void refresh(){
        Connection c;
        try {
            c = jdbcTemplate.getDataSource().getConnection();
            CallableStatement refresh = c.prepareCall("{CALL refreshdata()}");
            refresh.execute();
        } catch (Exception e){
            // Success
        }

    }

}