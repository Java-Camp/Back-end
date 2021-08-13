package com.jcf.orm.connections;

import com.jcf.orm.storages.OracleConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection implements AutoCloseable {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public MyConnection() {
        setConnectionPool();
    }

    public MyConnection(boolean isConnectionPool) {
        final Settings settings = Settings.getInstance();
        if (isConnectionPool) {
            setConnectionPool();
        }
        else {
            try {
                Class.forName(settings.getValue("spring.datasource.driver.class"));
                this.connection = DriverManager.getConnection(settings.getValue("spring.datasource.url"),
                        settings.getValue("spring.datasource.username"), settings.getValue("spring.datasource.password"));
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    @Deprecated
    private void setConnectionPool() {
        try {
            this.connection = OracleConnectionPool.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
