package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.ListFriendService;
import services.MessagesServices;
import bd.BDException;

public class SeekFriendsServlet extends HttpServlet {
		protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
			reponse.setContentType("Text/plain");
			String nom = (requete.getParameter("nom"));
			
			JSONObject retour = null;
			
			
			try {
				retour = ListFriendService.seekFriend(nom);
				
			} catch (BDException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			reponse.getWriter().print(retour);
		}
	

}