package todolist.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import todolist.bean.UserBean;
import todolist.service.LoginDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private LoginDao loginDao;

	public void init() 
	{
		loginDao = new LoginDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserBean userBean = new UserBean(username, password);
		
		HttpSession session = request.getSession();
		
//		Success to login
		if (loginDao.validate(userBean)) 
		{
			session.setAttribute("username",username);
			session.setAttribute("password",password);
			request.getRequestDispatcher("TodoList.jsp").forward(request, response);
		}
		
//		fail to login
		else 
		{
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('Your username or password is incorrect.'); " 
			+ "window.location='LoginPage.jsp' </script>");
			out.flush();
			out.close();
			}
	}
}