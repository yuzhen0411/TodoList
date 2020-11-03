package todolist.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Properties;

public class TodoUtils {

	/*
	 * obtain DB connection
	 */
	public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException 
	{
//		access jdbc.prpoerties
		InputStream is = TodoUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");		
		Properties pros = new Properties();
		pros.load(is);
		
		String user = pros.getProperty("sqluser");
		String password = pros.getProperty("sqlpassword");
		String url = pros.getProperty("url");
		String driverClass = pros.getProperty("driverClass");
		
//		load JDBC driver
		Class.forName(driverClass);
		
//		a connection object represents a DB connection
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;
	}

	public static void  closeResource(Connection conn, Statement ps) 
	{
		if(conn != null) 
		{
			try 
			{
				conn.close();
			} 
			catch (SQLException e) 
			{
				
				e.printStackTrace();
			}
		}
		if(ps != null) 
		{
			try 
			{
				ps.close();
			} 
			catch (SQLException e) 
			{
				
				e.printStackTrace();
			}
		}
	}
	
	public static void closeResource(Connection conn, Statement ps, ResultSet rs) 
	{
		if(conn != null) 
		{
			try 
			{
				conn.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		if(ps != null)
		{
			try 
			{
				ps.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		if(rs != null) 
		{
			try 
			{
				rs.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public static Date getSQLDate(LocalDate date) 
	{
		return java.sql.Date.valueOf(date);
	}
}