package baitercell.badges.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.Plugin;

public class SignChangeListener implements Listener {
	
	public Plugin plugin;
	
	public SignChangeListener(Plugin instance){
		plugin = instance;
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void signChange(final SignChangeEvent event){
		if(event.getLine(0).equals("[Badge]")){
			System.out.println("Badge made");
		}
	}
}
