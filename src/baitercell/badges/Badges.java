package baitercell.badges;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import baitercell.badges.commands.BadgeExecutor;
import baitercell.badges.events.EventListener;


public class Badges extends JavaPlugin {
	
	public DBConManager DBCM;

	public void onEnable(){
		init();
	}
	
	private void init() {
		//Create a default config if one doesnt exist
		this.saveDefaultConfig(); 
		
		//Create a new connection manager passing it the plugin, so it can read the config.yml
		DBCM = new DBConManager(this);
		
		//Open a new connection to the database
		DBCM.openDBConnection();

		//Generate tables if they don't already exist
		try {
			DBCM.createTables();
		} catch (Exception e) {
			System.out.println("Unable to create tables, check the connection has connected");
			e.printStackTrace();
		}
		
		//Database connection checker, to make sure the plugin still has a connection
		Thread DBConChecker = new Thread(DBCM);
		DBConChecker.start();
				
		//Listener for all events
		new EventListener(this, DBCM);	
		
		//The executor for all commands
		getCommand("badge").setExecutor(new BadgeExecutor(DBCM));
		
		AddPlayersToDB();
	}

	public void onDisable(){
		//stop thread checking for a connection
		DBCM.setRunning(false);
		//close db connection
		DBCM.closeConnection();
	}
	
	//Adds players to the DB
	//Only runs on server startup
	//because players could be logged in before
	//the plugin is running
	public void AddPlayersToDB(){
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();

		for( int i = 0; i < onlinePlayers.length; i++) {
			try {
				Statement st = DBCM.con.createStatement();
				
				//Check if the player already exists in the DB
				ResultSet rs = st.executeQuery("SELECT * FROM " +  DBCM.dbName + ".player WHERE playerName='" + onlinePlayers[i].getName() + "';");
				
				//Loop through the results, will skip if there are no results
				while(rs.next()) {
					return;
				}
				//Insert the badge into the table
				st.executeUpdate("INSERT INTO `" + DBCM.dbName + "`.`player` (`playerName`) VALUES('" + onlinePlayers[i].getName() + "');");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
	}
}