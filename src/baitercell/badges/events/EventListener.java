package baitercell.badges.events;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;

import baitercell.badges.DBConManager;

public class EventListener implements Listener {
	
	public Plugin plugin;
	public DBConManager DBCM;
	
	public EventListener(Plugin instance, DBConManager DBCM){
		plugin = instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
		this.DBCM = DBCM;
	}
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void signChange(final SignChangeEvent event){
		if(event.getLine(0).equals("[Badge]")){
			if(event.getPlayer().isOp()){
				event.getPlayer().sendMessage(ChatColor.GOLD + "[Badges] " + ChatColor.GREEN + "Sign created");
			} else {
				event.setLine(0, "Not OP");
			}
		}
	}
	
	
	//Called when any player logs in
	@EventHandler(priority = EventPriority.NORMAL)
	public void PlayerLogin(final PlayerLoginEvent event){
		try {
			Statement st = DBCM.con.createStatement();
			
			//Check if the player already exists in the DB
			ResultSet rs = st.executeQuery("SELECT * FROM " +  DBCM.dbName + ".player WHERE playerName='" + event.getPlayer().getName() + "';");
			
			//Loop through the results, will skip if there are no results
			while(rs.next()) {
					System.out.println(Color.ORANGE + "[Badges] " + ChatColor.GREEN + "A player with the name " + ChatColor.GOLD + event.getPlayer().getName() + ChatColor.GREEN + " already exists");
					return;
			}
			//Insert the badge into the table
			st.executeUpdate("INSERT INTO `" + DBCM.dbName + "`.`player` (`playerName`) VALUES('" + event.getPlayer().getName() + "');");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void BlockRightClick(final PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(event.getClickedBlock().getState() instanceof Sign){
				Sign s = (Sign) event.getClickedBlock().getState();
				if(s.getLine(0).equals("[Badge]")){
					event.getPlayer().sendMessage(ChatColor.GOLD + "[Badges] " + ChatColor.GREEN + "You clicked a sign that gives out badges");
					event.getPlayer().sendMessage(ChatColor.GOLD + "[Badges] " + ChatColor.GREEN + "The badge you got was: " + ChatColor.GOLD + s.getLine(1));
				}
			}
		}
	}
	
	
	
	
	
	
	
}
