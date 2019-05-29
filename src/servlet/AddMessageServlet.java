package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bd.BDException;
import services.AddFriendService;
import services.MessagesServices;

public class AddMessageServlet extends HttpServlet{

	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		reponse.setContentType("Text/plain");
			
		String ch = requete.getParameter("ch");
		String key = (requete.getParameter("key"));
		
		JSONObject retour = null;
		
		try {
			retour = MessagesServices.addMessage(key, ch);
		} catch (BDException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		reponse.getWriter().print(retour);
	}
}
