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

public class EditMessageServlet extends HttpServlet{

	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		reponse.setContentType("Text/plain");
			
		String ch = requete.getParameter("ch");
		int id = Integer.parseInt(requete.getParameter("id"));
		
		JSONObject retour = null;
		
		try {
			retour = MessagesServices.editMessage(id, ch);
		} catch (BDException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		reponse.getWriter().print(retour);
	}
}
