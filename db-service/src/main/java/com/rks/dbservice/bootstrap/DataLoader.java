package com.rks.dbservice.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String dbHealth = jdbcTemplate.queryForObject("select 'up' from dual", String.class);
        System.out.println("dbHealth: " + dbHealth);
    }
}
