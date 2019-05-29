package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bd.BDException;
import bd.UserTools;
import services.AddFriendService;
import services.MessagesServices;

public class ListMessageServlet extends HttpServlet{

	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		reponse.setContentType("Text/plain");
		String key=requete.getParameter("key");
		String query=requete.getParameter("query");
		int from=Integer.parseInt(requete.getParameter("from"));
		int id_max=Integer.parseInt(requete.getParameter("id_max"));
		int id_min=Integer.parseInt(requete.getParameter("id_min"));
		int nb=Integer.parseInt(requete.getParameter("nb"));
		JSONObject retour = null;
		
		try {
			retour = MessagesServices.listMessages(key,query,from,id_max,id_min,nb);
		} catch (BDException e) {
			UserTools.serviceRefused("ListMessagesServlet Error", 1);
			
		}
		reponse.getWriter().print(retour);
	}
}
