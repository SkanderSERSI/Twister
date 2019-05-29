package services;

import org.json.JSONObject;

import bd.BDException;
import bd.UserTools;

public class LogoutService {
	public static JSONObject logout(String key) {
		try{
			boolean is_connected = UserTools.isConnected(key);
			if (is_connected){
				UserTools.disconnectSession(key);
				return UserTools.serviceAccepted();
			}
			return UserTools.serviceRefused("Vous etes deja deconnecte", 100000);
		}catch(BDException e){
			return (UserTools.serviceRefused("Erreur SQL : "+e.getMessage(),1000));
		}
	}
	
}
