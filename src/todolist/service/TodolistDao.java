package todolist.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import todolist.bean.TodoBean;
import todolist.utils.TodoUtils;

/*
 * TodoList CRUD function
 */
public class TodolistDao implements TodoDao 
{
	private static final String INSERT_TODOS = "insert into todos (title, username, description, target_date,  is_done) values (?, ?, ?, ?, ?)";
	private static final String SELECT_TODO_BY_ID = "select id,title,username,description,target_date,is_done from todos where id =?";
	private static final String SELECT_ALL_TODOS = "select * from todos where username = ?";
	private static final String DELETE_TODO_BY_ID = "delete from todos where id = ?;";
	private static final String UPDATE_TODO = "update todos set title = ?, username= ?, description =?, target_date =?, is_done = ? where id = ?;";

	String isDone;
	
	public TodolistDao() 
	{
	}

	@Override
	public void insertTodo(TodoBean todo)
	{
		Connection conn = null;
		PreparedStatement ps = null;

		try 
		{
			conn = TodoUtils.getConnection();
			ps = conn.prepareStatement(INSERT_TODOS);	
			ps.setString(1, todo.getTitle());
			ps.setString(2, todo.getUsername());
			ps.setString(3, todo.getDescription());
			ps.setDate(4, TodoUtils.getSQLDate(todo.getTargetDate()));
			
//			String convert to Boolean
			ps.setBoolean(5, Boolean.parseBoolean(todo.getStatus()));

			ps.executeUpdate();
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
	}

	@Override
	public TodoBean selectTodo(long todoId) 
	{
		TodoBean todo = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try 
		{
			conn = TodoUtils.getConnection();
			ps = conn.prepareStatement(SELECT_TODO_BY_ID);
			ps.setLong(1, todoId);
			rs = ps.executeQuery();
				
//			pointer move down with next data
			while (rs.next()) 
			{
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String username = rs.getString("username");
				String description = rs.getString("description");
				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
				String isDone = boolToString(rs.getBoolean("is_done"));
				
				todo = new TodoBean(id, title, username, description, targetDate, isDone);
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
			TodoUtils.closeResource(conn, ps, rs);
		}
		return todo;
	}

	@Override
	public List<TodoBean> selectAllTodos(String uname) 
	{
		List<TodoBean> todos = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try 
		{
			conn = TodoUtils.getConnection();
			ps = conn.prepareStatement(SELECT_ALL_TODOS);
			ps.setString(1, uname);
			rs = ps.executeQuery();
				
			while (rs.next()) 
			{
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String username = rs.getString("username");
				String description = rs.getString("description");
				LocalDate targetDate = rs.getDate("target_date").toLocalDate();
				String isDone = boolToString(rs.getBoolean("is_done"));
				
				todos.add(new TodoBean(id, title, username, description, targetDate, isDone));
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
		finally {
			TodoUtils.closeResource(conn, ps, rs);
		}
		return todos;
	}

	@Override
	public boolean deleteTodo(int id)
	{
		boolean rowDeleted = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
		try 
		{
			conn = TodoUtils.getConnection();
			ps = conn.prepareStatement(DELETE_TODO_BY_ID);
			ps.setInt(1, id);
			
			rowDeleted = ps.executeUpdate() > 0;
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
			return rowDeleted;
	}

	@Override
	public boolean updateTodo(TodoBean todo){
		boolean rowUpdated = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = TodoUtils.getConnection();
			ps = conn.prepareStatement(UPDATE_TODO);
			ps.setString(1, todo.getTitle());
			ps.setString(2, todo.getUsername());
			ps.setString(3, todo.getDescription());
			ps.setDate(4, TodoUtils.getSQLDate(todo.getTargetDate()));
			ps.setLong(6, todo.getId());
			
//			String convert to Boolean
			ps.setBoolean(5, Boolean.parseBoolean(todo.getStatus()));			
			
			rowUpdated = ps.executeUpdate() > 0;
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
		return rowUpdated;
	}
	
	/*
	 * Boolean value presented by String status
	 */
	private String boolToString(Boolean isDonetf)
	{
		if(isDonetf == true)
		{
			isDone = "complete";
		}
		else
		{
			isDone = "in progress";
		}
		return isDone;
	}
}