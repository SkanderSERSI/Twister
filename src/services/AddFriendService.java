package services;

import org.json.JSONObject;

import bd.AuthentificationTools;
import bd.FriendTools;
import bd.UserTools;

public class AddFriendService {
	public static JSONObject addFriend(String key,int to){
		try{
			if(AuthentificationTools.userExistsById(to) && UserTools.isConnected(key)){
				if (!FriendTools.areFriends(key,to)){
					FriendTools.addFriend(FriendTools.getIdByKey(key),to);
					return FriendTools.serviceAccepted();
				}
				else if(FriendTools.areFriends(key,to)){
					FriendTools.deleteFriend(key,to);
					return FriendTools.serviceRefused("Ami supprimé ce nest pas une erreur "+FriendTools.getIdByKey(key)+" "+to, -4);
				}
				else{
					return FriendTools.serviceRefused("ni ami ni pas amis addFriens de firendservices erreur "+FriendTools.getIdByKey(key)+" "+to, -4);
				}
			}else{
				return FriendTools.serviceRefused("Utilisateur inexistant ou vous n'etes pas connecté "+to, -4);
			}
				
		}catch(Exception e){
			return FriendTools.serviceRefused("manageFriendship : "+e.getMessage(), -12); 
		}
	}
	
}
