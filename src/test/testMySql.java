package test;

import javax.naming.spi.DirStateFactory.Result;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import bd.Database;

public class testMySql {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("debut main");
		try{
			/*
			Class.forName("com.msql.jdbc.Driver").newInstance();
			Connection co = DriverManager.getConnection("jdbc:mysql://132.227.201.129:33306/gr1_berrabah_che gr1_berrabah_che ,bc1");
			*/
			Connection co = Database.getMySQLConnection();
			System.out.println("connection chargée");
			Statement st = co.createStatement();
			System.out.println("creation statement");
			/*String query = "select * from users";
			System.out.println("affectation de la query");
			ResultSet res = st.executeQuery(query);
			System.out.println("recup  des données");*/
			String login="Kooci";
			String query = "select mdps from Users where login=\""+login+"\"";
			st.executeQuery(query);
			ResultSet rs= st.getResultSet();
			rs.next();
			String retour= rs.getString("mdps");
			System.out.println(retour);
			
			
			/*while(res.next()){
				System.out.println(res.getString("nom"));
				System.out.println(res.getString("login"));
				System.out.println(res.getString("pwd"));
			}
			*/
			st.close();
			co.close();
			
		}catch(Exception e){
			System.out.println("chargement dbb impossible !"+e.getMessage());
			
			
		
		}
	}

}