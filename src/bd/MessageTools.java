package bd;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.*;


public class MessageTools {
	private static int id_msg=0;
	public static MessageTools c() {
		return new MessageTools();
	}
	public static void add(String key,String ch) throws UnknownHostException, BDException{
		Mongo m = new Mongo("li328.lip6.fr",27130);
		DB db = m.getDB("gr2_chikden_srsi");
		DBCollection coll = db.getCollection("messages");
		BasicDBObject doc = new BasicDBObject();
		//doc.put("id_author", AuthentificationTools.getId(key));
		doc.put("key",key);
		id_msg++;
		doc.put("id_message",id_msg);
		doc.put("message",ch);
		doc.put("comment","");
		coll.insert(doc);	
	}
	
	public void remove(int id_msg) throws UnknownHostException{
		Mongo m = new Mongo("li328.lip6.fr",27130);
		DB db = m.getDB("gr2_chikden_srsi");
		DBCollection coll = db.getCollection("messages");
		DBObject dbm = new BasicDBObject("id_message", id_msg);
		coll.remove(dbm);
	}
	
	
	public static void edit(int id_msg,String ch) throws UnknownHostException{
		Mongo m=new Mongo("li328.lip6.fr",27130);
		DB db = m.getDB("gr2_chikden_srsi");
		DBCollection coll = db.getCollection("messages");
		BasicDBObject dbm = new BasicDBObject();
		dbm.append("$set", new BasicDBObject().append("message",ch));
		
		BasicDBObject dbm2 = new BasicDBObject().append("id_message", id_msg);

		coll.update(dbm2,dbm);
		
	}
	
	public static boolean messageExists(int id_msg) throws UnknownHostException{
		Mongo m = new Mongo("li328.lip6.fr",27130);
		DB db = m.getDB("gr2_chikden_srsi");
		DBCollection coll = db.getCollection("messages");
		
		DBCursor dbc= coll.find(new BasicDBObject("id_message",id_msg));
		if (dbc.hasNext()){
			return true;
		}else{
			return false;
		}
	}
	
	public static JSONObject serviceRefused(String ch){
		
		JSONObject out= new JSONObject();
		out.put("Message",ch);
		return out;
	}
	
	public static JSONObject serviceAccepted(String ch){
		
		String succ="GREAT SUCCESS";
		JSONObject out= new JSONObject();
		out.put("Message",succ+" "+ch);
		
		return out;
	}
	
	public static JSONObject list(String query,int from,int id_max,int id_min,int nb) throws UnknownHostException, BDException{
		JSONObject out=new JSONObject();
		Mongo m = new Mongo("li328.lip6.fr",27130);
		DB db = m.getDB("gr2_chikden_srsi");
		DBCollection coll = db.getCollection("messages");
		DBCursor dbc = null;
		if(id_max!=-1 && id_min!=-1){
			List<DBObject> criteria= new ArrayList<DBObject>();
			
			BasicDBObject q1= new BasicDBObject();
			
			//BasicDBObject q3= new BasicDBObject();
			//BasicDBObject qt=new BasicDBObject();
			//q1.put("id_message", new BasicDBObject("$gt",id_min).append("$lt",id_max));
			
			q1.put("key",UserTools.getKeyById(from));
			criteria.add(q1);
			criteria.add(new BasicDBObject("id_message",new BasicDBObject("$gt",id_min).append("$lt",id_max)));
			//DBCursor dbc = coll.find({key:{$gt:id_min}});
			
			
			dbc = coll.find(new BasicDBObject("$and",criteria));
		}
		JSONArray liste=new JSONArray();
		
		while(dbc.hasNext()){
			DBObject obj = dbc.next();
			out= new JSONObject();
			out.put("id_message",obj.get("id_message"));
			out.put("message",obj.get("message"));
			out.put("comment",obj.get("comment"));
			liste.put(out);
		}
		JSONObject res=new JSONObject();
		res.put(""+from, liste);
		
		return res;
				
		}
		
		public static int getNbMessages(int id) throws BDException, UnknownHostException{
			
			Mongo m = new Mongo("li328.lip6.fr",27130);
			DB db = m.getDB("gr2_chikden_srsi");
			DBCollection coll = db.getCollection("messages");
			DBCursor dbc = null;
			int i=0;
			BasicDBObject q1= new BasicDBObject();
			q1.put("key",UserTools.getKeyById(id));
			
			dbc=coll.find(q1);
			while(dbc.hasNext()){
				DBObject obj = dbc.next();
				i++;
			}
			return i;
		}
}
