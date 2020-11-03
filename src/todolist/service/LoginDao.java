package todolist.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import todolist.bean.UserBean;
import todolist.utils.TodoUtils;

/*
 * check login information available
 */
public class LoginDao 
{
	private static final String SELECT_USER= "select * from user where username = ? and password = ? ";
	
	public boolean validate(UserBean loginBean)
	{
		boolean status = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try 
		{
//			get connection from sql server
			conn = TodoUtils.getConnection();

//			precompile sql statement
			ps = conn.prepareStatement(SELECT_USER);
			ps.setString(1, loginBean.getUsername());
			ps.setString(2, loginBean.getPassword());

//			check if an user exists
//			status is true with a ResultSet Object
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