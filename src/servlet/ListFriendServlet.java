package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.ListFriendService;
import bd.BDException;

public class ListFriendServlet extends HttpServlet { 
	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		reponse.setContentType("Text/plain");
		
		String login = requete.getParameter("login");
		
		JSONObject retour = null;
		try {
			retour = ListFriendService.listFriend(login);
		} catch (BDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reponse.getWriter().print(retour);
	}
	
}
