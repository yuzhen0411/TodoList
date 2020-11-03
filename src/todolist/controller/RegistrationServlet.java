package todolist.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import todolist.bean.UserBean;
import todolist.service.RegistrationDao;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private RegistrationDao registrationDao;

	public void init() 
	{
		registrationDao = new RegistrationDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String username = request.getParameter("signusername");
		String password = request.getParameter("signpassword");
		String confirmpsw = request.getParameter("confirmpw");
		
		HttpSession session = request.getSession();
		UserBean userBean = new UserBean(username, password);
		
//		correct password with valid account
		if(password.equals(confirmpsw) && registrationDao.usernameisExist(username) != true)
		{
			registrationDao.registerInfo(userBean);
			session.setAttribute("username",username);
			session.setAttribute("password",password);
			request.getRequestDispatcher("TodoList.jsp").forward(request, response);
		}
		
//		invalid account
		else if(registrationDao.usernameisExist(username))
		{
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('The username already exists.'); " 
			+ "window.location='RegistrationPage.jsp' </script>");
			out.flush();
			out.close();
		}
		
//		incorrect password
		else
		{
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('Two passwords are inconsistent. Please try again!'); " 
			+ "window.location='RegistrationPage.jsp' </script>");
			out.flush();
			out.close();
		}	
	}
}
