package bd;

import java.sql.ResultSet;

import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UserTools {
	
	public static void insertToBD(String nom,String prenom,String mail,String login,String mdps) throws BDException{
		try{
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="insert into Users values(null,\""+nom+"\",\""+prenom+"\",\""+login+"\",\""+mdps+"\",\""+mail+"\")";
			Statement st= (Statement) conn.createStatement();
			st.executeUpdate(query);
			st.close();
			conn.close();
		}catch(Exception e){
			throw new BDException("Exception de BD : insertToBD "+e.getMessage()); 
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



	public static boolean isConnected(String key) throws BDException {
		try{
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="select s.id from Session s where s.key=\""+key+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet rs= st.getResultSet();
			boolean retour= rs.next();
			rs.close();
			st.close();
			conn.close();
			return retour;
		}catch(Exception e){
			throw new BDException("isConnected : "+e.getMessage()); 
		}
	}



	public static void disconnectSession(String key) throws BDException {
		try{
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="select s.id from Session s where s.key=\""+key+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet rs= st.getResultSet();
			if (rs.next()){
				String q2="delete from Session where key=\""+key+"\"";
				st.executeUpdate(q2);
			}
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			throw new BDException("DisconnectSession : "+e.getMessage()); 
		}
		
	}

	public static String getKeyById(int id) throws BDException{
		try{
			Connection conn = (Connection) Database.getMySQLConnection();
			String query="select s.key from Session s where s.id=\""+id+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet res= st.getResultSet();
			String out="";
			if(res.next()){
				out= res.getString("key");
			}
			st.close();
			conn.close();
			return out;
		}catch(Exception e){
			throw new BDException("getKeyById: "+e.getMessage());
		}
		
	}
}
