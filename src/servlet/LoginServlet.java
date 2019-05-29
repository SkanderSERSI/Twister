package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.LoginService;

public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		reponse.setContentType("Text/plain");
		String login = requete.getParameter("login");
		String mdps = requete.getParameter("mdps");
		
		JSONObject retour =  LoginService.login(login, mdps);
		reponse.getWriter().print(retour);
	}

}
