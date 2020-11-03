package todolist.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import todolist.service.TodoDao;
import todolist.service.TodolistDao;
import todolist.bean.TodoBean;

@WebServlet("/")
public class TodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TodoDao todoDAO;
	
	public void init() {
		todoDAO = new TodolistDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

			switch (action) {
			case "/newForm":
				showNewForm(request, response);
				break;
			case "/insert":
				insertTodo(request, response);
				break;
			case "/delete":
				deleteTodo(request, response);
				break;
			case "/editForm":
				showEditForm(request, response);
				break;
			case "/update":
				updateTodo(request, response);
				break;
			case "/list":
				listTodo(request, response);
				break;
			case "/logout":
				logoutTodo(request, response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("LoginPage.jsp");
				dispatcher.forward(request, response);
				break;
			}
	}

	private void listTodo(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException 
	{
//		get current user info
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("username");
		String uname = (String) obj;
		
//		collect and show all todos based on user info
		List<TodoBean> listTodo = todoDAO.selectAllTodos(uname);
		request.setAttribute("listTodo", listTodo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("TodoList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("TodoForm.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		int id = Integer.parseInt(request.getParameter("id"));
		TodoBean existingTodo = todoDAO.selectTodo(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("TodoForm.jsp");
		request.setAttribute("todo", existingTodo);
		dispatcher.forward(request, response);

	}

	private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		HttpSession session = request.getSession();
		
		String username = (String)session.getAttribute("username");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String isDone = request.getParameter("isDone");

//      LocalDate format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String td = request.getParameter("targetDate");
		LocalDate targetDate = LocalDate.parse(td, formatter);		
		
		TodoBean newTodo = new TodoBean(title, username, description, targetDate, isDone);
		todoDAO.insertTodo(newTodo);
		
//		jump to listTodo method
		response.sendRedirect("list");
	}

	private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		HttpSession session = request.getSession();
		
		String username = (String)session.getAttribute("username");
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String isDone = request.getParameter("isDone");
		
//		LocalDate format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String td = request.getParameter("targetDate");
		LocalDate targetDate = LocalDate.parse(td, formatter);
		
		TodoBean updateTodo = new TodoBean(id, title, username, description, targetDate, isDone);
		todoDAO.updateTodo(updateTodo);
		
//		jump to listTodo method
		response.sendRedirect("list");
	}

	private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int id = Integer.parseInt(request.getParameter("id"));
		todoDAO.deleteTodo(id);
		
//		jump to listTodo method
		response.sendRedirect("list");
	}
	private void logoutTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getSession().invalidate();
		response.sendRedirect("LoginPage.jsp");
	}
}