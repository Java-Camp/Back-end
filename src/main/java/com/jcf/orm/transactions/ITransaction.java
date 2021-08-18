package com.jcf.orm.transactions;

import java.sql.Connection;

public interface ITransaction {

    Connection getConnection();

    Connection close();

    void setConnection(Connection connection);

    void commit();

    void rollback();


}