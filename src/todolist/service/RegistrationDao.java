package todolist.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import todolist.bean.UserBean;
import todolist.utils.TodoUtils;

public class RegistrationDao 
{
	private static final String INSERT_USER = "insert into user (username,password) values (?,?)";
	
	/*
	 * store user registration information into database 
	 */
	public boolean registerInfo(UserBean registerBean)
	{
		boolean status = false;
		Connection conn = null;
		PreparedStatement ps = null;

		try 
		{
//			get connection from sql server
			conn = TodoUtils.getConnection();
			
//			precompile sql statement
			ps = conn.prepareStatement(INSERT_USER);
			ps.setString(1, registerBean.getUsername());
			ps.setString(2, registerBean.getPassword());
				
//			return updated rows
			int result = ps.executeUpdate();
			if(result != 0) 
			{
				status = true;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			TodoUtils.closeResource(conn, ps);
		}
		return status;
	}
	
	
	/*
	 * check duplicated username
	 */
	public boolean usernameisExist(String signUsername)
	{
		Boolean status = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try 
		{
//			get connection from sql server
			conn = TodoUtils.getConnection();
			
//			precompile sql statement
			ps = conn.prepareStatement("select * from user where username =?");
			ps.setString(1, signUsername);
			
//			check if an user exists
			rs = ps.executeQuery();
			status = rs.next();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			TodoUtils.closeResource(conn, ps, rs);
		}
		return status;
	}
}
