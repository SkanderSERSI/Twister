package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bd.BDException;
import services.AddFriendService;
import services.LoginService;
import services.MessagesServices;

public class GetIdServlet extends HttpServlet{

	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		reponse.setContentType("Text/plain");
			
		String login =(requete.getParameter("login"));
		
		JSONObject retour= new JSONObject();
		
		try {
			retour = LoginService.getId(login);
		} catch (BDException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		reponse.getWriter().print(retour);
	}
}
