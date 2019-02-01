package org.itstep.mvc.core.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DatabaseHelper {
    private static DatabaseHelper inst;

    public static DatabaseHelper getInstance() {
        return (inst != null) ? inst : (inst = new DatabaseHelper());
    }


    private DataSource dataSource;


    private DatabaseHelper() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/todo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        config.setUsername("root");
        config.setPassword("");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource = new HikariDataSource(config);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
