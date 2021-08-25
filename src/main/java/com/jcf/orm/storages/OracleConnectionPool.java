package com.jcf.orm.storages;
import com.jcf.orm.connections.Settings;
import lombok.SneakyThrows;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class OracleConnectionPool {

    private PoolDataSource pds;

    @SneakyThrows
    public OracleConnectionPool() {
        final Settings settings = Settings.getInstance();
        pds = PoolDataSourceFactory.getPoolDataSource();
        pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        pds.setURL(settings.getValue("spring.datasource.url"));
        pds.setUser(settings.getValue("spring.datasource.username"));
        pds.setPassword(settings.getValue("spring.datasource.password"));
        pds.setInitialPoolSize(50);
        pds.setMinPoolSize(50);
        pds.setMaxPoolSize(50);
    }
    private static OracleConnectionPool instance = null;

    public static OracleConnectionPool getInstance() {
        if (instance == null)
            instance = new OracleConnectionPool();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return pds.getConnection();
    }
}
