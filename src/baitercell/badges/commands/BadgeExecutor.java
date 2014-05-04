package baitercell.badges.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import baitercell.badges.DBConManager;

public class BadgeExecutor implements CommandExecutor {
	
	DBConManager DBCM;

	public BadgeExecutor(DBConManager DBCM) {
		this.DBCM = DBCM;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if( args.length < 1) {
			System.out.println("Not enough args");
			sender.sendMessage(ChatColor.GOLD + "[Badges] " + ChatColor.GREEN +"Incorrent number of commands");
			sender.sendMessage(ChatColor.GOLD + "[Badges] " + ChatColor.GREEN + "Use " + ChatColor.GOLD +  "/badges help " + ChatColor.GREEN + "for more info");
			return true;
		}		
		System.out.println("Correct number of args");
		
		
		
		args[0] = args[0].toLowerCase();
	
		switch(args[0]) {
			case "help": Help(sender, cmd, label, args);
			break;
			case "create": Create(sender, cmd, label, args);
			break;
			case "delete": Delete(sender, cmd, label, args);
			break;
			case "user": User(sender, cmd, label, args);
			break;
			default: return true;
		}	
		return true;
	}
	
	
	//Creation of a badge
	public boolean Create(CommandSender sender, Command cmd, String label, String[] args){
		//If a badge name hasn't been put in stop running
		if(args.length < 2) {
			sender.sendMessage(ChatColor.GOLD + "[Badges] " + ChatColor.GREEN + "No badge name was entered");
			return true;
		}
		
		sender.sendMessage(ChatColor.GOLD + "[Badges] " + ChatColor.GREEN + "Creating badge: " + ChatColor.GOLD + args[1]);
		
		try {
			Statement st = DBCM.con.createStatement();
			
			ResultSet rs = st.executeQuery("SELECT * FROM " +  DBCM.dbName + ".badge;");
			
			//Check if the badge already exists in the DB
			while(rs.next()) {
				if(rs.getString("badgeName").equals(args[1])) { 
					sender.sendMessage(ChatColor.GOLD + "[Badges] " + ChatColor.GREEN + "A badge with the name " + ChatColor.GOLD + args[1] + ChatColor.GREEN + " already exists");
					return true;
				}
			}
			//Insert the badge into the table
			st.executeUpdate("INSERT INTO `" + DBCM.dbName + "`.`badge` (`badgeName`) VALUES('" + args[1] + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sender.sendMessage(ChatColor.GOLD + "[Badges] " + ChatColor.GOLD + args[1] + ChatColor.GREEN + " created successfully");
		return true;
		
	}
	
	//deletion of a badge
	public boolean Delete(CommandSender sender, Command cmd, String label, String[] args){
		System.out.println("Delete");
		return true;
		
	}
	
	//help on how to use the plugin
	public boolean Help(CommandSender sender, Command cmd, String label, String[] args){
		System.out.println("help");
		return true;
		
	}
	
	
	//returns a list of all the players badges
	//if another player is added as a arg, then it displays theirs
	public boolean User(CommandSender sender, Command cmd, String label, String[] args){
		System.out.println("User");
		return true;
		
	}

}
