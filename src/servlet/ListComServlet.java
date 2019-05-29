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
import services.CommentServices;
import services.MessagesServices;

public class ListComServlet extends HttpServlet{

	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		reponse.setContentType("Text/plain");
		
		int id=Integer.parseInt(requete.getParameter("id"));
		JSONObject retour = null;
		
		try {
			retour = CommentServices.listCom(id);
		} catch (BDException e) {
			UserTools.serviceRefused("ListMessagesServlet Error", 1);
			
		}
		reponse.getWriter().print(retour);
	}
}
