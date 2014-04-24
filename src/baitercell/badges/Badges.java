package baitercell.badges;

import org.bukkit.plugin.java.JavaPlugin;

public class Badges extends JavaPlugin {

	public void onEnable(){
		System.out.println(this + " is now enabled");	
		init();
	}
	
	private void init() {
		
		// create a default config if one doesnt exist
		this.saveDefaultConfig(); 
		
		DBConManager DBCM = new DBConManager(this);
		
		DBCM.openDBConnection();
		try {
			DBCM.checkTables();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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