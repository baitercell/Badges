package baitercell.badges;

import org.bukkit.plugin.java.JavaPlugin;

import baitercell.badges.commands.BadgeExecutor;



public class Badges extends JavaPlugin {
	
	public DBConManager DBCM;

	public void onEnable(){
		System.out.println(this + " is now enabled");	
		init();
		
		getCommand("badge").setExecutor(new BadgeExecutor());
	}
	
	private void init() {
		
		// create a default config if one doesnt exist
		this.saveDefaultConfig(); 
		
		//create a new connection manager passing it the plugin, so it can read the config.yml
		DBCM = new DBConManager(this);
		
		//open a new connection to the database
		DBCM.openDBConnection();

		
		//generate tables if they don't already exist
		try {
			DBCM.createTables();
		} catch (Exception e) {
			System.out.println("Unable to create tables, check the connection has connected");
			e.printStackTrace();
		}
		
		//database connection checker, to make sure the plugin still has a connection
		Thread DBConChecker = new Thread(DBCM);
		DBConChecker.start();
				
		//listener for changing the text on a sign (part of placing)
		new SignChangeListener(this);	
	}

	public void onDisable(){
		System.out.println(this + " is now disabled");
		
		//stop thread checking for a connection
		DBCM.setRunning(false);
		//close db connection
		DBCM.closeConnection();
	}
	
}