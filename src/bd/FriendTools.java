package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class FriendTools {
	public static void addFriend(int from,int to) throws BDException{
		try{
			Connection conn = (Connection) Database.getMySQLConnection();
			String query="insert into Friends values(\""+from+"\",\""+to+"\", null)";
			Statement st= (Statement) conn.createStatement();
			st.executeUpdate(query);
			st.close();
			conn.close();
		}catch(Exception e){
			throw new BDException("FriendTools : "+e.getMessage()); 
		}
		
		
		
	}
	
	
	public static boolean areFriends(String key, int to) throws BDException{
		try{
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="select f.* from Friends f, Session s where s.key=\""+key+"\" and s.id=f.seg and f.gher="+to+"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet rs= st.getResultSet();
			if (rs.next()){
				rs.close();
				st.close();
				conn.close();
				System.out.println("ils sont bien amis");
				return true;
			}
				
			rs.close();
			st.close();
			conn.close();
			System.out.println("ils ne sont pas amis");
			return false;
		}catch(Exception e){
			throw new BDException("areFriends : "+e.getMessage()); 
		}
	}
	
	public static void deleteFriend(String key,int to) throws BDException{
		try{
			Connection conn = (Connection) Database.getMySQLConnection();
			int from=getIdByKey(key);
			String query="delete from Friends where seg=\""+from+"\" and gher=\""+to+"\"";
			Statement st= (Statement) conn.createStatement();
			if (areFriends(key,to)){
				
				st.executeUpdate(query);
				
			}
			else{
				System.out.println("ils ne sont pas amis");
			}
			st.close();
			conn.close();
		}catch(Exception e){
			throw new BDException("deleteFriend: "+e.getMessage()); 
		}	
	}
	
	public static JSONArray list(String login) throws BDException{
		try{
			Connection conn = (Connection) Database.getMySQLConnection();
			JSONObject out = new JSONObject();
			String query="select f.gher from Friends f, Users u where u.login=\""+login+"\" and u.id_user=f.seg";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet res= st.getResultSet();
			int i=0, a1;
			JSONArray a= new JSONArray();
			while(res.next()){
				a1= res.getInt("gher");
				a.put(a1);
			}
			//out.put("follows",a);
			st.close();
			conn.close();
			return a;
		}catch(Exception e){
			throw new BDException("listFriend: "+e.getMessage());
		}

	}
	
	public static int getIdByKey(String key) throws BDException{
		try{
			Connection conn = (Connection) Database.getMySQLConnection();
			String query="select s.id from Session s where s.key=\""+key+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet res= st.getResultSet();
			int out=-1;
			if(res.next()){
				out= res.getInt("id");
			}
			st.close();
			conn.close();
			return out;
		}catch(Exception e){
			throw new BDException("getIdByKey: "+e.getMessage());
		}
		
	}
	
	public static JSONObject serviceRefused(String nom,int i){
		
		JSONObject out= new JSONObject();
		out.put("Message",nom);
		out.put("code",i);
		return out;
	}
	
	public static JSONObject serviceAccepted(){
		
		String succ="GREAT SUCCESS";
		JSONObject out= new JSONObject();
		out.put("Message",succ);
		out.put("code", 0);
		
		return out;
	}
	

	public static JSONArray seek(String nom) throws BDException{
		try{
			Connection conn = (Connection) Database.getMySQLConnection();
			
			String query="select nom, prenom,id_user from Users where nom=\""+nom+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet res= st.getResultSet();
			int i=0, a1;
			JSONArray a= new JSONArray();
			
			while(res.next()){
				JSONObject out = new JSONObject();
				out.put("nom", res.getString("nom"));
				out.put("prenom", res.getString("prenom"));
				out.put("id",res.getInt("id_user"));
				a.put(out);
			}
			
			st.close();
			conn.close();
			return a;
		}catch(Exception e){
			throw new BDException("seek: "+e.getMessage());
		}
	}
	
	public static JSONObject listFM(int id) throws BDException{
		try{
			Connection conn = (Connection) Database.getMySQLConnection();
			JSONArray l= list(AuthentificationTools.getLoginById(id));
			JSONArray lm= new JSONArray();
			JSONObject out=new JSONObject();
			JSONArray test= new JSONArray();
			int nb=0;
			for(int i=0;i<l.length();i++){
				nb+=MessageTools.getNbMessages((Integer)l.get(i));
			}
			
			for(int i=0;i<l.length();i++){
				lm.put(MessageTools.list("",(Integer)l.get(i),10, 0, 100));
				
			}
			return out.put(""+id, lm);
		}catch(Exception e){
			throw new BDException("listFM: "+e.getMessage());
		}
	}
	
}