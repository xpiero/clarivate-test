package com.clarivate.test.db;

import java.sql.Connection;

import javax.sql.DataSource;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class BaseDao {

	 @Qualifier("dataSource")
	    @Autowired
	    private DataSource dataSource;
	
    Handle getHandle(){
        Connection conn =  DataSourceUtils.getConnection(dataSource);
        return DBI.open(conn);
    }
}
