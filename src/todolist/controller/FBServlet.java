package todolist.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import todolist.bean.UserBean;
import todolist.service.LoginDao;
import todolist.service.RegistrationDao;

@WebServlet("/FBServlet")
public class FBServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private LoginDao loginDao;
	private RegistrationDao registrationDao;

	public void init() {
		loginDao = new LoginDao();
		registrationDao = new RegistrationDao();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("signusername");
		String password = request.getParameter("signpassword");
		UserBean userBean = new UserBean(username, password);
		
		HttpSession session = request.getSession();
		session.setAttribute("username",username);
		session.setAttribute("password",password);
		
//		FB account doesn't exist, must register
		if (loginDao.validate(userBean) != true) 
		{
			registrationDao.registerInfo(userBean);
		}
		
//		FB login successfully
		request.getRequestDispatcher("TodoList.jsp").forward(request, response);
	}
}
