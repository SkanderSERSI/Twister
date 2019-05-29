package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.NewUserService;


public class NewUserServlet extends HttpServlet {
	protected void doGet(HttpServletRequest requete,HttpServletResponse reponse) throws ServletException,IOException{
		
		reponse.setContentType("Text/plain");
		String login = requete.getParameter("login");
		String mdps = requete.getParameter("mdps");
		String nom = requete.getParameter("nom");
		String prenom = requete.getParameter("prenom");
		String mail = requete.getParameter("mail");
		
		JSONObject retour =  NewUserService.newUser(nom, prenom, mail, login, mdps);
		reponse.getWriter().print(retour);
	}

}
