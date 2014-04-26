package baitercell.badges.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BadgeExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if( args.length < 1) {
			System.out.println("Not enough args");
			sender.sendMessage(ChatColor.GOLD + "[Badges]" + ChatColor.GREEN +"Incorrent number of commands");
			sender.sendMessage(ChatColor.GOLD + "[Badges]" + ChatColor.GREEN + "Use " + ChatColor.GOLD +  "/badges help " + ChatColor.GREEN + "for more info");
			return true;
		}
		
		System.out.println("Correct number of args");
		return true;
	}

}
