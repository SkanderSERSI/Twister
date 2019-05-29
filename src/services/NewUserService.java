package services;

import java.sql.SQLException;

import org.json.JSONObject;

import bd.AuthentificationTools;
import bd.UserTools;

public class NewUserService {
	
	public static JSONObject newUser(String nom, String prenom, String mail, String login, String mdps){
		try{
			if(login !=null && nom !=null && prenom !=null && mdps !=null && mail != null){
				boolean is_user =AuthentificationTools.userExists(login);
				if(is_user){
					return UserTools.serviceRefused("Utilisateur existant "+login,1);
				}
				UserTools.insertToBD(nom,prenom,mail,login,mdps);
				return new JSONObject();
			}
			else{
				return UserTools.serviceRefused("Erreur de paramètres",-1);
			}
		}catch(SQLException e){
			return UserTools.serviceRefused("erreuur SQLexception : "+e.getMessage(),1000);
		}
		catch(Exception e){
			return UserTools.serviceRefused("erreuur inconnue klaus : "+e.getMessage(),100000);
		}
			
	}
}
