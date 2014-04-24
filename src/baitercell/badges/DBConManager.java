package baitercell.badges;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class DBConManager extends Thread {

	public String ip;
	public String port;
	public String dbName;
	public String username;
	public String password;
	
	public Connection con;
	
	Badges badges;
	
	
	public DBConManager(Badges badges) {
		this.badges = badges;
	}
	
	
	public void openDBConnection(){
		
		//get the details out of the file
		ip = badges.getConfig().getString("ip");
		port = badges.getConfig().getString("port");
		dbName = badges.getConfig().getString("dbName");
		username = badges.getConfig().getString("username");
		password= badges.getConfig().getString("password");
		
		//open a connection to the DB
		try{
			Class.forName("com.mysql.jdbc.Driver"); //use this driver
			con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + dbName,  username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkTables() throws Exception{
		
		DatabaseMetaData md = con.getMetaData();

		ResultSet rs = md.getTables(null, null, "badges", null);
		
		while(rs.next()){
			System.out.println(rs.getString(1));
		}	
	}
	
	
	
	
	
	
	
	
}
