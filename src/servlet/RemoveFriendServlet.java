package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bd.BDException;
import services.AddFriendService;
import services.ListFriendService;


public class RemoveFriendServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		reponse.setContentType("Text/plain");
		
		String key = requete.getParameter("key");
		int to = Integer.parseInt(requete.getParameter("to"));
		
		JSONObject retour = null;
		retour = AddFriendService.addFriend(key, to);
		reponse.getWriter().print(retour);
	}

}
