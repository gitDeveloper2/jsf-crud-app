package org.mspace;

import java.sql.Connection;
import java.sql.DriverManager;

public class db_connect {

	public Connection get_connection() {
		Connection connection=null;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/api", "root","");
	}catch(Exception e) {
		System.out.print(e);
	}
		return connection;
	}
}









