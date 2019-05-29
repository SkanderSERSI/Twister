package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bd.BDException;
import services.AddFriendService;
import services.CommentServices;
import services.ListFriendService;


public class AddCommentServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		reponse.setContentType("Text/plain");
		
		String key = requete.getParameter("key");
		int id = Integer.parseInt(requete.getParameter("id"));
		String ch=requete.getParameter("ch");
		
		JSONObject retour = null;
		try {
			retour = CommentServices.addComment(key,id,ch);
		} catch (BDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reponse.getWriter().print(retour);
	}

}
