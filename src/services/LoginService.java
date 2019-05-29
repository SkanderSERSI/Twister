package services;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import bd.AuthentificationTools;
import bd.BDException;
import bd.FriendTools;
import bd.UserTools;

public class LoginService {
	
	public static JSONObject login(String login, String mdps){
		if(login==null || login==" " || mdps==null || mdps==" ") return UserTools.serviceRefused("Erreur de paramètres",-1);
		
		try{
			
			
			//Verifie que l’utilisateur existe sinon ERROR 2
			boolean is_user = AuthentificationTools.userExists(login);
			if(!is_user) return UserTools.serviceRefused("Utilisateur inconnu "+login, 2);
			
			//Verifie que le password et l’utilisateur sont OK sinon ERROR 1
			boolean password_ok = AuthentificationTools.checkPassword(login, mdps);
			if(!password_ok) return UserTools.serviceRefused("Mot de passe incorrect", 1);
			boolean is_connected=UserTools.isConnected(UserTools.getKeyById(AuthentificationTools.getIdUser(login)));
			if (is_connected){
				return UserTools.serviceRefused("Vous �tes deja connectes "+login, 200);
			}
			//Recupère l’id de l’utilisateur
			int id= AuthentificationTools.getIdUser(login);
		
			JSONObject retour = new JSONObject();
			
			//Insère une nouvelle session dans la base de donnee
			String key= AuthentificationTools.insertSession(id, 0);
			
			JSONArray follows = new JSONArray();
			follows= FriendTools.list(login);
					
			retour.put("key", key);
			retour.put("id", id);
			retour.put("login", login);
			retour.put("follows", follows);

			return retour;
			//return UserTools.serviceAccepted();
			
		}catch(SQLException e){
			return UserTools.serviceRefused("Erreur SQL : "+e.getMessage(),1000);
		}
		catch(Exception e){
			return UserTools.serviceRefused("Erreur inconnue : "+e.getMessage(), 100000);
		}
		
	}
	
	public static JSONObject getLogin(int id) throws BDException{
		try{
			JSONObject out= new JSONObject();
			if (AuthentificationTools.userExistsById(id)){
				out.put("login", AuthentificationTools.getLoginById(id));	
			}
			else{
				return UserTools.serviceRefused("L'utilisateur "+id+" n'existe pas", -5);
			}
			return out;
				
		}catch(Exception e){
			throw new BDException("listFriend : "+e.getMessage()); 
		}
	}

	public static JSONObject getId(String login) throws BDException {
		try{
			JSONObject out= new JSONObject();
			if (AuthentificationTools.userExists(login)){
				out.put("id", AuthentificationTools.getIdUser(login));	
			}
			else{
				return UserTools.serviceRefused("L'utilisateur "+login+" n'existe pas", -5);
			}
			return out;
				
		}catch(Exception e){
			throw new BDException("listFriend : "+e.getMessage()); 
		}
	}
	
}
