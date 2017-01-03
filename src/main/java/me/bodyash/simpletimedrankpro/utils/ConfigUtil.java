package me.bodyash.simpletimedrankpro.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import me.bodyash.simpletimedrankpro.Main;

public class ConfigUtil {
	
	// DATABASE DATABASE DATABASE DATABASE DATABASE DATABASE DATABASE DATABASE
	private String database = "Database.";
	private String databaseType = "yml";
	private String databaseTypePath = String.valueOf(this.database) + "DatabaseType";

	private String databaseIp = "127.0.0.1";
	private String databaseIpPath = String.valueOf(this.database) + "ip";

	private String databasePort = "3306";
	private String databasePortPath = String.valueOf(this.database) + "port";

	private String databaseUser = "root";
	private String databaseUserPath = String.valueOf(this.database) + "user";

	private String databasePass = "root";


	private String databasePassPath = String.valueOf(this.database) + "pass";
	
	private String databaseDB = "strp";
	private String databaseDBPath = String.valueOf(this.database) + "database";
	
	private String databaseTable = "users";
	private String databaseTablePath = String.valueOf(this.database) + "table";

	// LOGO LOGO LOGO LOGO LOGO LOGO LOGO LOGO LOGO LOGO LOGO LOGO LOGO
	private String consoleLogo =  "[SimpleTimedRank PRO]";
	private String chatLogo = "&a[&6SimpleTimedRank &5PRO&a]";
	private String chatLogoPath = "ChatLogo";

	// FILES FILES FILES FILES FILES FILES FILES FILES FILES FILES FILES
	private File configFile;
	private YamlConfiguration config;

	// MESSAGES MESSAGES MESSAGES MESSAGES MESSAGES MESSAGES MESSAGES MESSAGES
	private String messages = "Messages.";
	public String noPermMessage = "&3You don't have permissions to do that!";
	private String noPermMessagePath = String.valueOf(this.messages) + "NoPermMessage";

	public String lastDayMsg = "This is your last day as %newRank%!";
	private String lastDayMsgPath = String.valueOf(this.messages) + "LastDayMessage";

	public String timeExpiredMsg = "Your time has been expired!";
	private String timeExpiredMsgPath = String.valueOf(this.messages) + "TimeExpiredMessage";

	public String cantCheckTimeMsg = "Can't check the time!";
	private String cantCheckTimeMsgPath = String.valueOf(this.messages) + "CantCheckTimeMessage";
	//New Messages
/*	private String helpCommandMsg = "If you need help with the commands type '/strp help'.";
	private String helpCommandMsgPath = String.valueOf(this.messages) + "HelpCommandMessage";
	
	private String timeLeftAsMsg = "Time left as";
	private String timeLeftAsMsgPath = String.valueOf(this.messages) + "TimeLeftAsMessage";
	
	private String timeLeftDays = "Days";
	private String timeLeftDaysPath = String.valueOf(this.messages) + "TimeLeftDays";
	
	private String cantFindThePlayer = "Can't find the player";
	private String cantFindThePlayerPath = String.valueOf(this.messages) + "CantFindThePlayerMsg";

	private String cantDemoteThePlayerMsg = 
		*/
	
	// OPTIONS OPTIONS OPTIONS OPTIONS OPTIONS OPTIONS OPTIONS OPTIONS OPTIONS
	private String options = "Options.";
	public String checkMethod = "onPlayerJoin";
	private String checkMethodPath = String.valueOf(this.options) + "CheckMethod";

	public boolean checkForUpdates = true;
	private String checkForUpdatesPath = String.valueOf(this.options) + "CheckForUpdates";

	public long interval = 5;
	private String intervalPath = String.valueOf(this.options) + "Interval";

	public String dateFormat = "dd.MM.yyyy";
	private String dateFormatPath = String.valueOf(this.options) + "DateFormat";

	public String promoteCommand = "pex user %player% group set %newRank%";
	private String promoteCommandPath = String.valueOf(this.options) + "PromoteCommand";

	public String demoteCommand = "pex user %player% group set %oldRank%";
	private String demoteCommandPath = String.valueOf(this.options) + "DemoteCommand";

	// OTHER OTHER OTHER OTHER OTHER OTHER OTHER OTHER OTHER OTHER OTHER OTHER
	public double versionNumber = this.versionNumberStorage;
	private double versionNumberStorage = 1.0;
	private String versionNumberPath = "VersionNumber";

	public ConfigUtil(Main main) {
		this.configFile = new File(main.getDataFolder(), "config.yml");
		this.config = YamlConfiguration.loadConfiguration(configFile);
		this.startup();
	}

	public void startup() {
		if (!this.configFile.exists()) {
			Bukkit.getLogger().log(Level.WARNING, this.consoleLogo + "... Starting config creation ...");
			this.createConfig();
		} else {
			
			
			
			if (config.getString(databaseTypePath).isEmpty()){
				Bukkit.getLogger().log(Level.WARNING, "Some problems when loading config /DataBaseType/");
				this.databaseType = "yml";
			}else{
				this.databaseType = config.getString(databaseTypePath);
			}
			if (config.getString(databaseIpPath).isEmpty()){
				Bukkit.getLogger().log(Level.WARNING, "Some problems when loading config /DataIp/");
				this.databaseIp = "127.0.0.1";
			}else{
				this.databaseIp = config.getString(databaseIpPath);
			}
			if (config.getString(databasePortPath).isEmpty()){
				Bukkit.getLogger().log(Level.WARNING, "Some problems when loading config /DataPort/");
				this.databasePort = "3306";
			}else{
				this.databasePort = config.getString(databasePortPath);
			}
			if (config.getString(databaseUserPath).isEmpty()){
				Bukkit.getLogger().log(Level.WARNING, "Some problems when loading config /DataUser/");
				this.databaseUser = "root";
			}else{
				this.databaseUser = config.getString(databaseUserPath);
			}
			if (config.getString(databasePassPath).isEmpty()){
				Bukkit.getLogger().log(Level.WARNING, "Some problems when loading config /DataPassword/");
				this.databasePass = "root";
			}else{
				this.databasePass = config.getString(databasePassPath);
			}
			if (config.getString(databasePassPath).isEmpty()){
				Bukkit.getLogger().log(Level.WARNING, "Some problems when loading config /DataPassword/");
				this.databasePass = "root";
			}else{
				this.databasePass = config.getString(databasePassPath);
			}
			if (config.getString(databasePassPath).isEmpty()){
				Bukkit.getLogger().log(Level.WARNING, "Some problems when loading config /DataPassword/");
				this.databasePass = "root";
			}else{
				this.databasePass = config.getString(databasePassPath);
			}
			if (config.getString(databaseDBPath).isEmpty()){
				Bukkit.getLogger().log(Level.WARNING, "Some problems when loading config /DataDB/");
				this.databaseDB = "strp";
			}else{
				this.databaseDB = config.getString(databaseDBPath);
			}
			if (config.getString(databaseTablePath).isEmpty()){
				Bukkit.getLogger().log(Level.WARNING, "Some problems when loading config /DataTable/");
				this.databaseTable = "users";
			}else{
				this.databasePass = config.getString(databasePassPath);
			}
			if (this.config.getString(this.noPermMessagePath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo + "... Something went wrong while setting the \"NoPermMessage\", using default message (\u00a73You don't have permissions to do that!). ..."));
                this.noPermMessage = "&3You don't have permissions to do that!";
            } else {
                this.noPermMessage = this.config.getString(this.noPermMessagePath);
            }
            if (this.config.getString(this.dateFormatPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo + "... Something went wrong while setting the \"DateFormat\", using default date format (dd.MM.yyyy). ..."));
                this.dateFormat = "dd.MM.yyyy";
            } else {
                this.dateFormat = this.config.getString(this.dateFormatPath);
            }
            if (this.config.getString(this.promoteCommandPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo + "... Something went wrong while setting the \"PromoteCommand\", using default command (pex user %player% group set %newRank%). ..."));
                this.promoteCommand = "pex user %player% group set %newRank%";
            } else {
                this.promoteCommand = this.config.getString(this.promoteCommandPath);
            }
            if (this.config.getString(this.demoteCommandPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo + "... Something went wrong while setting the \"DemoteCommand\", using default command (pex user %player% group set %oldRank%). ..."));
                this.demoteCommand = "pex user %player% group set %oldRank%";
            } else {
                this.demoteCommand = this.config.getString(this.demoteCommandPath);
            }
            if (this.config.getString(this.lastDayMsgPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"LastDayMessage\", using default message (This is your last day as %oldRank%!). ..."));
                this.lastDayMsg = "This is your last day as %newRank%!";
            } else {
                this.lastDayMsg = this.config.getString(this.lastDayMsgPath);
            }
            if (this.config.getString(this.timeExpiredMsgPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"TimeExpiredMessage\", using default message (Your time has been expired!). ..."));
                this.timeExpiredMsg = "Your time has been expired!";
            } else {
                this.timeExpiredMsg = this.config.getString(this.timeExpiredMsgPath);
            }
            if (this.config.getString(this.cantCheckTimeMsgPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"CantCheckTimeMessage\", using default message (Can't check the time because you are not registered yet.). ..."));
                this.cantCheckTimeMsg = "Can't check the time because you are not registered yet.";
            } else {
                this.cantCheckTimeMsg = this.config.getString(this.cantCheckTimeMsgPath);
            }
            if (this.config.getString(this.checkMethodPath).isEmpty() || !this.config.getString(this.checkMethodPath).equalsIgnoreCase("all") && !this.config.getString(this.checkMethodPath).equalsIgnoreCase("interval") && !this.config.getString(this.checkMethodPath).equalsIgnoreCase("onPlayerJoin")) {
                System.err.println(String.valueOf(consoleLogo + "... Something went wrong while setting the \"CheckMethod\", using default setting (onPlayerJoin). ..."));
                this.checkMethod = "onPlayerJoin";
            } else {
                this.checkMethod = this.config.getString(this.checkMethodPath);
            }
            try {
                if (!this.config.getString(this.intervalPath).isEmpty()) {
                    this.interval = Long.parseLong(this.config.getString(this.intervalPath));
                }
            }
            catch (Exception e) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"Interval\", using default value (5 [Minutes]). ..."));
                this.interval = 5;
            }

		}
	}

	private void createConfig() {
		this.config.options().header(
				"Info:\nThis is a short config declaration if you need a detailed declaration of all methods visit: https://www.spigotmc.org/resources/simpletimedrank.26566/\n"
				+ "Its not recommended to change this!\nDateFormat: dd.MM.yyyy\nIf you want to change something be sure to use the right case!\nFor example: MM.dd.yyyy\n"
				+ "Available variables: %player% ; %newRank% ; %oldRank%\nBut note: The variables are not working for the following messages / commands:\n    - NoPermMessage\n    - TimeExpired\n    - CantCheckTimeMessage\nChange the CheckMethod if you want the plugin to use another time checking method\n"
				+ "Available methods are:\n    - all\t\t\t\t\t: use all existing ckeck methods -> resource intensive if you set a low number of intervals.\n    - onPlayerJoin\t: everytime a player is joining, his/her time will be checked.\n    - interval\t\t\t: every x minutes the server will check ALL online players to promot or demote them if they exist in the users.yml.\n"
				+ "The \"interval\" is defined in minutes. Please use whole numbers.\n"
				+ "!!ATTENTION: Please do not touch the version number!!"
				+ "\nDataBaseType: mysql or yml. If u wanna use mysql - configure please");
		
		this.config.set( checkForUpdatesPath, checkForUpdates );
		
		this.config.set(databaseTypePath, databaseType);
		this.config.set(databaseIpPath, databaseIp);
		this.config.set(databasePortPath, databasePort);
		this.config.set(databaseUserPath, databaseUser);
		this.config.set(databasePassPath, databasePass);
		this.config.set(databaseDBPath, databaseDB);
		this.config.set(databaseTablePath, databaseTable);
		
		this.config.set(chatLogoPath, chatLogo);
		
		this.config.set(noPermMessagePath, noPermMessage);
		this.config.set(cantCheckTimeMsgPath, cantCheckTimeMsg);
		this.config.set(timeExpiredMsgPath, timeExpiredMsg);
		this.config.set(lastDayMsgPath, lastDayMsg);
		
		this.config.set( checkMethodPath, checkMethod );
		this.config.set( intervalPath, interval );
		this.config.set( dateFormatPath, dateFormat );
		this.config.set( promoteCommandPath, promoteCommand);
		this.config.set( demoteCommandPath, demoteCommand );
		
		this.config.set(versionNumberPath, versionNumber);
		
		this.saveConfig();

	}
	
	private void saveConfig() {
		try {
            this.config.save(this.configFile);
            System.out.println("... Finished config creation!");
        }
        catch (IOException e) {
            System.err.println("Can't create config file, see info below:");
            e.printStackTrace();
        }
	}

	
	//UNUSED METHOD
	public String colorize(String s) {
		if (s == null)
			return null;
		return s.replaceAll("&([0-9a-f])", "\u00A7$1");
	}

	public String getConsoleLogo() {
		return this.colorize(consoleLogo) + " ";
	}
	public String getChatLogo() {
		return this.colorize(chatLogo) + " ";
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public String getDatabaseIp() {
		return databaseIp;
	}

	public String getDatabasePort() {
		return databasePort;
	}

	public String getDatabaseUser() {
		return databaseUser;
	}

	public String getDatabasePass() {
		return databasePass;
	}

	public String getDatabaseDB() {
		return databaseDB;
	}

	public String getDatabaseTable() {
		return databaseTable;
	}

	public String getNoPermMessage() {
		return this.colorize(noPermMessage) + " ";
	}

	public String getLastDayMsg() {
		return this.colorize(lastDayMsg) + " ";
	}

	public String getTimeExpiredMsg() {
		return this.colorize(timeExpiredMsg) + " ";
	}

	public String getCantCheckTimeMsg() {
		return this.colorize(cantCheckTimeMsg) + " ";
	}

	public String getOptions() {
		return options;
	}

	public String getCheckMethod() {
		return checkMethod;
	}

	public boolean isCheckForUpdates() {
		return checkForUpdates;
	}

	public long getInterval() {
		return interval;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public String getPromoteCommand() {
		return promoteCommand;
	}

	public String getDemoteCommand() {
		return demoteCommand;
	}
	
}
