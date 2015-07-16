package com.yan.wang.usbdrivedectector.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceConnection {

	public Connection getConnection() {
		Connection connection = null;

		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = getClass().getClassLoader().getResourceAsStream("db.properties");
	 
			// load a properties file
			prop.load(input);
	 
			// get the property value and print it out
//			System.out.println(prop.getProperty("postgres.database"));
//			System.out.println(prop.getProperty("postgres.username"));
//			System.out.println(prop.getProperty("postgres.password"));
	 
			connection = DriverManager.getConnection(prop.getProperty("postgres.database"), prop.getProperty("postgres.username"), prop.getProperty("postgres.password"));

			
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return connection;
	}
	
}
