package services;


import java.sql.SQLException;

import org.json.JSONObject;

import bd.AuthentificationTools;
import bd.BDException;
import bd.CommentTools;
import bd.FriendTools;
import bd.MessageTools;
import bd.UserTools;

public class CommentServices {
	
	public static JSONObject addComment(String key,int id_message,String text) throws BDException{
		try{
			if((text.length()<140) && (UserTools.isConnected(key))){
				JSONObject o= new JSONObject();
				
				return CommentTools.add(key,text,id_message);
				
				
			}
			else{
				return CommentTools.serviceRefused(text+" non ajoute");
			}
				
		}catch(Exception e){
			throw new BDException("addCommentService : "+e.getMessage()); 
		}
	}
	
	public static JSONObject listCom(int id_msg) throws BDException{
		try{
			return CommentTools.list(id_msg);
				
				
		}catch(Exception e){
			throw new BDException("addCommentService : "+e.getMessage()); 
		}
	}
}
