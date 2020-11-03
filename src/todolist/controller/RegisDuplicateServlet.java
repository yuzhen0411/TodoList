package todolist.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import todolist.service.RegistrationDao;

@WebServlet("/duplicated")
public class RegisDuplicateServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private RegistrationDao registrationDao;

	public void init() 
	{
		registrationDao = new RegistrationDao();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String username = request.getParameter("signusername");
		
//		username is exist or not
		boolean existUsername = registrationDao.usernameisExist(username);

//		put result into Map instance
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("existUsername", existUsername);
		
//		pass result to front-page
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(resultMap));		
	}
}
