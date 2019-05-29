package services;

import org.json.JSONArray;
import org.json.JSONObject;

import bd.AuthentificationTools;
import bd.BDException;
import bd.FriendTools;

public class ListFriendService {
	public static JSONObject listFriend(String login) throws BDException{
		try{
			JSONObject out=new JSONObject();
			if (AuthentificationTools.userExists(login)){
				out.put("follows", FriendTools.list(login));	
			}
			else{
				return FriendTools.serviceRefused("L'utilisateur "+login+" n'existe pas", -5);
			}
			return out;
				
		}catch(Exception e){
			throw new BDException("listFriend : "+e.getMessage()); 
		}
	}
	
	public static JSONObject seekFriend(String nom) throws BDException{
		try{
			JSONObject out=new JSONObject();
			
			out.put("friends",FriendTools.seek(nom));
			return out;
		}catch(Exception e){
			throw new BDException("listFriend : "+e.getMessage()); 
		}
		
	}	
	
	public static JSONObject listFM(int id)throws BDException{
		try{
			JSONObject out=new JSONObject();
			if (AuthentificationTools.userExists(AuthentificationTools.getLoginById(id))){
				out.put("follows", FriendTools.listFM(id));	
			}
			else{
				return FriendTools.serviceRefused("L'utilisateur "+id+" n'existe pas", -5);
			}
			return out;
				
		}catch(Exception e){
			throw new BDException("listFriend : "+e.getMessage()); 
		}
	}
		
		
}
