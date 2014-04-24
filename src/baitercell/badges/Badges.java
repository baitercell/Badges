package baitercell.badges;

import java.sql.Connection;
import java.sql.DriverManager;

import org.bukkit.plugin.java.JavaPlugin;





public class Badges extends JavaPlugin {

	public Connection con;
	
	
	public void onEnable(){
		System.out.println(this + " is now enabled");	
		init();
	}
	
	private void init() {
		
		// create a default config if one doesnt exist
		this.saveDefaultConfig(); 
		
		//get the details out of the file
		String ip = this.getConfig().getString("ip");
		String port = this.getConfig().getString("port");
		String dbName = this.getConfig().getString("dbName");
		String username = this.getConfig().getString("username");
		String password= this.getConfig().getString("password");
		
		
		
		//open a connection to the DB
		try{
			Class.forName("com.mysql.jdbc.Driver"); //use this driver
			con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + dbName,  username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//check db for tables
		
		//create tables
		
		//start db connection refresher thread
		
		//start listeners
		
	}

	public void onDisable(){
		System.out.println(this + " is now disabled");
		
		//close db connection
	}
	
}