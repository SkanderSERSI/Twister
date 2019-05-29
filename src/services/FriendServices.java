package services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import bd.AuthentificationTools;
import bd.BDException;
import bd.Database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class FriendServices {
	public static void manageFriendship(int from,int to){
		try{
			if (!FriendTools.areFriend(from,to)){
				FriendTools.addFriend(from,to);
				
			}
			else{
				FriendTools.removeFriend(from,to);
			}
			FriendTools.serviceAccepted();
				
		}catch(Exception e){
			throw new BDException("manageFriendship : "+e.getMessage()); 
		}
	}
	
	public static JSONObject listFriend(int user_id){
		try{
			JSONObject out=new JSONObject();
			if (AuthentificationTools.userExists(user_id)){
				out=FriendTools.list(user_id);
				
			}
			else{
				return FriendTools.serviceRefused();
			}
			return out;
				
		}catch(Exception e){
			throw new BDException("listFriend : "+e.getMessage()); 
		}
	}
	

}
