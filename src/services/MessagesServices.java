package services;

import java.net.UnknownHostException;

import org.json.JSONObject;

import bd.BDException;
import bd.MessageTools;
import bd.UserTools;

public class MessagesServices {
	public static JSONObject addMessage(String key,String ch) throws BDException{
		try{
			if((ch.length()<140) && (UserTools.isConnected(key))){
				MessageTools.add(key,ch);
				
				return MessageTools.serviceAccepted(ch+" ajoute avec succes");
				
			}
			else{
				return MessageTools.serviceRefused(ch+" non ajoute");
			}
				
		}catch(Exception e){
			throw new BDException("addMessageService : "+e.getMessage()); 
		}
	}
	
	public static JSONObject removeMessage(int message_id) throws BDException{
		try{
			if(MessageTools.c().messageExists(message_id)){
				MessageTools.c().remove(message_id);
				return MessageTools.serviceAccepted("message supprimé");
			}
			else{
				return MessageTools.serviceRefused("message introuvable");
			}
				
		}catch(Exception e){
			throw new BDException("RemoveMessageService : "+e.getMessage()); 
		}
	}
	
	public static JSONObject editMessage(int message_id,String ch) throws BDException{
		try{
			if(MessageTools.messageExists(message_id)){
				MessageTools.edit(message_id,ch);
				return MessageTools.serviceAccepted("message edité");
			}
			else{
				return MessageTools.serviceRefused("message introuvable");
			}
				
		}catch(Exception e){
			throw new BDException("editMessageService : "+e.getMessage()); 
		}
	}
	
	public static JSONObject listMessages(String key,String query,int from,int id_max,int id_min,int nb) throws UnknownHostException, BDException{
		try {
			if(UserTools.isConnected(key)){
				return MessageTools.list(query,from,id_max,id_min,nb);
			}
			else
				return MessageTools.serviceRefused("Vous etes deconnecte impossible");
		} catch (BDException e) {
			throw new BDException("listMessages : "+e.getMessage());
		}
		
	}
	
	
}
