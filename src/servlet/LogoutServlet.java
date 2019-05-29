package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.LogoutService;

public class LogoutServlet extends HttpServlet {
	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		reponse.setContentType("Text/plain");
		String key = requete.getParameter("key");
		
		JSONObject retour =  LogoutService.logout(key);
		reponse.getWriter().print(retour);
	}
}
