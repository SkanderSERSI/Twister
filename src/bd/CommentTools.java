package bd;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class CommentTools {
	private static int id_com=0;
	public static JSONObject add(String key,String ch,int id_msg) throws UnknownHostException, BDException, SQLException{
		Mongo m = new Mongo("li328.lip6.fr",27130);
		DB db = m.getDB("gr2_chikden_srsi");
		DBCollection coll = db.getCollection("messages");
		BasicDBObject doc = new BasicDBObject();
		Date now = new Date();
		JSONObject outr=new JSONObject();
		doc.put("auteur",new BasicDBObject().append("login",AuthentificationTools.getLoginById(FriendTools.getIdByKey(key))).append("id", FriendTools.getIdByKey(key)));
		id_com++;
		doc.put("id_com",id_com);
		doc.put("commentaire",ch);
		doc.put("date",now);
		
		// prendre les objets d'avant si l'avant != ""
		BasicDBObject q1= new BasicDBObject();
		q1.put("id_message",id_msg);
		DBCursor dbc =coll.find(q1); 
		
		BasicDBList liste=new BasicDBList();
		if(dbc.hasNext()){
			DBObject obj = dbc.next();
			//BasicDBObject o=(BasicDBObject) obj; 
			
			if (((BasicDBObject) obj).getString("comment")==""){
				liste.add(doc);
				BasicDBObject dbm = new BasicDBObject();
				dbm.append("$set", new BasicDBObject().append("comment",liste));
				
				BasicDBObject dbm2 = new BasicDBObject().append("id_message", id_msg);
				coll.update(dbm2,dbm);
				outr.put("id_com",id_com);
				outr.put("auteur",new JSONObject().append("login",AuthentificationTools.getLoginById(FriendTools.getIdByKey(key))).append("id", FriendTools.getIdByKey(key)));
				outr.put("commentaire",ch);
				outr.put("date",now);
				return outr;
			}
			BasicDBObject o=(BasicDBObject) obj;
			BasicDBList l=(BasicDBList) o.get("comment");
			for(int i=0;i<l.size();i++){
				liste.add(((BasicDBObject) l.get(i)));
			}
			liste.add(doc);
		}
		
		
		
		
		
		
		
		
		BasicDBObject dbm = new BasicDBObject();
		dbm.append("$set", new BasicDBObject().append("comment",liste));
		
		BasicDBObject dbm2 = new BasicDBObject().append("id_message", id_msg);
		coll.update(dbm2,dbm);
		
		

		outr.put("id_com",id_com);
		outr.put("auteur",new JSONObject().append("login",AuthentificationTools.getLoginById(FriendTools.getIdByKey(key))).append("id", FriendTools.getIdByKey(key)));
		outr.put("commentaire",ch);
		outr.put("date",now);
		return outr;
	}
	
	public void remove(int id_msg) throws UnknownHostException{
		Mongo m = new Mongo("li328.lip6.fr",27130);
		DB db = m.getDB("gr2_chikden_srsi");
		DBCollection coll = db.getCollection("comments");
		DBObject dbm = new BasicDBObject("id_com", id_msg);
		coll.remove(dbm);
	}
	
	public void edit(int id_msg,String ch) throws UnknownHostException{
		Mongo m=new Mongo("li328.lip6.fr",27130);
		DB db = m.getDB("gr2_chikden_srsi");
		DBCollection coll = db.getCollection("comments");
		DBObject dbm = new BasicDBObject("id_com", id_msg);
		DBObject dbm2 = new BasicDBObject("$set",new BasicDBObject("commentaire", ch));

		coll.update(dbm,dbm2);
		
	}
	
	public boolean commentExists(int id_msg) throws UnknownHostException{
		Mongo m = new Mongo("li328.lip6.fr",27130);
		DB db = m.getDB("gr2_chikden_srsi");
		DBCollection coll = db.getCollection("messages");
		
		DBCursor dbc= coll.find(new BasicDBObject("id_com",id_msg));
		if (dbc.hasNext()){
			return true;
		}else{
			return false;
		}
	}
	
	public static JSONObject serviceRefused(String ch){
		
		JSONObject out= new JSONObject();
		out.put("Commentaire",ch);
		return out;
	}
	
	public static JSONObject serviceAccepted(String ch){
		
		String succ="GREAT SUCCESS";
		JSONObject out= new JSONObject();
		out.put("Commentaire",succ+" "+ch);
		
		return out;
	}
	
	public static JSONObject list(int from) throws UnknownHostException, BDException{
		JSONObject out=new JSONObject();
		Mongo m = new Mongo("li328.lip6.fr",27130);
		DB db = m.getDB("gr2_chikden_srsi");
		DBCollection coll = db.getCollection("messages");
		DBCursor dbc = null;
		BasicDBObject q1= new BasicDBObject();
		
		q1.put("id_message",from);
		dbc = coll.find(q1);
		
		JSONArray l= new JSONArray();
		if(dbc.hasNext()){
			DBObject obj = dbc.next();
			BasicDBList o=(BasicDBList) obj.get("comment"); 
			for(int i=0;i<o.size();i++){
				
				l.put(((BasicDBObject) o.get(i)).get("commentaire"));
			}
		

		}
		return out.put("commentaire", l);
	}
}
