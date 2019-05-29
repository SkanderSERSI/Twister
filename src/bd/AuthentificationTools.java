package bd;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class AuthentificationTools {

	public static boolean userExists(String login) throws BDException, SQLException{
		try{
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="select id_user from Users where login=\""+login+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet rs= st.getResultSet();
			boolean retour= rs.next();
			rs.close();
			st.close();
			conn.close();
			return retour;
		}catch(Exception e){
			throw new BDException("UserExists : "+e.getMessage()); 
		}
	}
	
	public static boolean userExistsById(int id) throws BDException, SQLException{
		try{
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="select login from Users where id_user=\""+id+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet rs= st.getResultSet();
			boolean retour= rs.next();
			rs.close();
			st.close();
			conn.close();
			return retour;
		}catch(Exception e){
			throw new BDException("UserExists : "+e.getMessage()); 
		}
	}
	
	public static boolean checkPassword(String login, String mdps) throws BDException, SQLException{
		boolean is_true=false;
		try{
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="select mdps from Users where login=\""+login+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet rs= st.getResultSet();
			rs.next();
			String retour= rs.getString("mdps");
			if(retour.equals(mdps)){
				is_true=true;
			}
			rs.close();
			st.close();
			conn.close();
			return is_true;
		}catch(Exception e){
			throw new BDException("checkPassword : "+e.getMessage()); 
		}
		
	}

	public static int getIdUser(String login) throws BDException {
		try{
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="select id_user from Users where login=\""+login+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet rs= st.getResultSet();
			rs.next();
			int retour= rs.getInt("id_user");
			rs.close();
			st.close();
			conn.close();
			return retour;
		}catch(Exception e){
			throw new BDException("getIdUser : "+e.getMessage()); 
		}
	}

	public static String insertSession(int id, int i) throws BDException {
		try{
			
			String key=Md5.encode(""+id);
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="insert into Session values(\""+key+"\",\""+id+"\","+"\""+i+"\","+"null)";
			Statement st= (Statement) conn.createStatement();
			st.executeUpdate(query);
			st.close();
			conn.close();
			return key;
		}catch(Exception e){
			throw new BDException("insertSession "+e.getMessage()); 
		}
	}

	public static int getId(String key) throws BDException{
		try{
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="select id from Session where key=\""+key+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet rs= st.getResultSet();
			rs.next();
			int retour= rs.getInt("id");
			rs.close();
			st.close();
			conn.close();
			return retour;
		}catch(Exception e){
			throw new BDException("getIdUser : "+e.getMessage()); 
		}
		
	}
	public static String getLoginById(int id) throws BDException, SQLException{
		try{
			Connection conn= (Connection) Database.getMySQLConnection();
			String query="select login from Users where id_user=\""+id+"\"";
			Statement st= (Statement) conn.createStatement();
			st.executeQuery(query);
			ResultSet rs= st.getResultSet();
			rs.next();
			String retour = rs.getString("login");
			rs.close();
			st.close();
			conn.close();
			return retour;
		}catch(Exception e){
			throw new BDException("getLoginById : "+e.getMessage()); 
		}
	}
}
