package services;

import org.json.*;

import bd.AuthentificationTools;
import bd.BDException;
import bd.UserTools;

import java.sql.SQLException;

public class UserServices {
	
	public static JSONObject createUser(String nom, String prenom, Integer age, String login, String mdps){
		try{
			if(login !=null && nom !=null && prenom !=null && mdps !=null && age != null){
				boolean is_user =AuthentificationTools.userExists(login);
				if(is_user){
					return UserTools.serviceRefused("Utilisateur existant "+login,1);
				}
				UserTools.insertToBD(nom,prenom,age,login,mdps);
				return UserTools.serviceAccepted();
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
	
	public static JSONObject login(String login, String mdps){
		if(login==null || mdps==null ) return UserTools.serviceRefused("Erreur de paramètres",-1);
		
		try{
			//Verifie que l’utilisateur existe sinon ERROR 1
			boolean is_user = AuthentificationTools.userExists(login);
			if(!is_user) return UserTools.serviceRefused("Utilisateur inconnu "+login, 2);
			
			//Verifie que le password et l’utilisateur sont OK sinon ERROR 2
			boolean password_ok = AuthentificationTools.checkPassword(login, mdps);
			if(!password_ok) return UserTools.serviceRefused("Mot de passe incorrect", 1);
			
			//Recupère l’id de l’utilisateur
			int id= AuthentificationTools.getIdUser(login);
		
			JSONObject retour = new JSONObject();
			
			//Insère une nouvelle session dans la base de donnee
			//String key= AuthentificationTools.insertSession(id, false);
			//retour.put("key", key);
			
			//return retour;
			return UserTools.serviceAccepted();
			
		}catch(SQLException e){
			return UserTools.serviceRefused("Erreur SQL : "+e.getMessage(),1000);
		}
		catch(Exception e){
			return UserTools.serviceRefused("Erreur inconnue : "+e.getMessage(), 100000);
		}
		
	}
	
	public static JSONObject logout(String key) {
		try{
			boolean is_connected = UserTools.isConnected(key);
			if (is_connected){
				UserTools.disconnectSession(key);
				return UserTools.serviceAccepted();
			}
			return UserTools.serviceRefused("Vous etes deja déconnecté", 100000);
		}catch(BDException e){
			return (UserTools.serviceRefused("Erreur SQL : "+e.getMessage(),1000));
		}
		
	}
}
		
		

