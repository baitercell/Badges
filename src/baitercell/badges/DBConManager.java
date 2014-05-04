package baitercell.badges;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConManager extends Thread {

	public String ip;
	public String port;
	public String dbName;
	public String username;
	public String password;
	public int timeout;
	
	public Boolean running;
	
	//The connection to the database
	public Connection con;
	
	Badges badges;
	
	
	public DBConManager(Badges badges) {
		this.badges = badges;
		running = true;
		
		//get the details out of the file
		ip = badges.getConfig().getString("ip");
		port = badges.getConfig().getString("port");
		dbName = badges.getConfig().getString("dbName");
		username = badges.getConfig().getString("username");
		password = badges.getConfig().getString("password");
		timeout = badges.getConfig().getInt("timeout");
	}
	
	
	public void openDBConnection(){
		//open a connection to the DB
		try{
			Class.forName("com.mysql.jdbc.Driver"); //use this driver
			con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + dbName,  username, password);
		} catch (Exception e) {
			System.out.println("Connection to the DB Failed");
			e.printStackTrace();
		}
	}
	
	public void closeConnection(){
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Connection unable to close, maybe it wasnt open");
			e.printStackTrace();
		}
	}
	
	public void createTables() throws Exception{
		Statement st = con.createStatement();

		st.executeUpdate("CREATE TABLE IF NOT EXISTS `badgedb`.`badge` ( `idbadge` INT NOT NULL AUTO_INCREMENT, `badgeName` VARCHAR(45) NULL,  PRIMARY KEY (`idbadge`))ENGINE = InnoDB;");		
		st.executeUpdate("CREATE TABLE IF NOT EXISTS `badgedb`.`player` (`idplayer` INT NOT NULL AUTO_INCREMENT, `playerName` VARCHAR(45) NULL, PRIMARY KEY (`idplayer`))ENGINE = InnoDB;");		
		st.executeUpdate("CREATE TABLE IF NOT EXISTS `shirt` (`player_idplayer` INT NOT NULL,`badge_idbadge` INT NOT NULL, PRIMARY KEY (`player_idplayer`,`badge_idbadge`), KEY `fk_shirt_player_idx` (`player_idplayer`), KEY `fk_shirt_badge1_idx` (`badge_idbadge`), CONSTRAINT `fk_shirt_badge1` FOREIGN KEY (`badge_idbadge`) REFERENCES `badge` (`idbadge`) ON DELETE NO ACTION ON UPDATE NO ACTION, CONSTRAINT `fk_shirt_player` FOREIGN KEY (`player_idplayer`) REFERENCES `player` (`idplayer`) ON DELETE NO ACTION ON UPDATE NO ACTION) ENGINE=InnoDB;");		
	}
	
	public void setRunning(Boolean run){
		running = run;
	}
	
	public void run(){
		while(running){
			try {
				if(!con.isValid(10)){ //connection is not valid
					//open a connection to the DB
					openDBConnection();
				} else {
					try {
						System.out.println("DB is connected, will check again in: " + timeout + " milliseconds.");
						sleep(timeout);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
