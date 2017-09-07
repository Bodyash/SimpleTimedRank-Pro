package me.bodyash.simpletimedrankpro.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

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
	private String noPermMessage = "&3You don't have permissions to do that!";
	private String noPermMessagePath = String.valueOf(this.messages) + "NoPermMessage";

	private String lastDayMsg = "This is your last day as %newRank%!";
	private String lastDayMsgPath = String.valueOf(this.messages) + "LastDayMessage";

	private String timeExpiredMsg = "Your time has been expired!";
	private String timeExpiredMsgPath = String.valueOf(this.messages) + "TimeExpiredMessage";

	private String cantCheckTimeMsg = "Can't check the time!";
	private String cantCheckTimeMsgPath = String.valueOf(this.messages) + "CantCheckTimeMessage";

	private String timeLeftDaysMsg = "Time left as %newRank%: %days% Day(s)";
	private String timeLeftDaysMsgPath = String.valueOf(this.messages) + "TimeLeftDaysMsg";
	
	private String timeLeftHoursMsg = "Time left as %newRank%: %h% hours %m% minutes";
	private String timeLeftHoursMsgPath = String.valueOf(this.messages) + "TimeLeftHoursMsg";
	
	private String helpCommandMsg = "&eIf you need help with the commands type &b/strp help";
	private String helpCommandMsgPath = String.valueOf(this.messages) + "HelpCommandMessage";
	
	// OPTIONS OPTIONS OPTIONS OPTIONS OPTIONS OPTIONS OPTIONS OPTIONS OPTIONS
	private String options = "Options.";
	private String checkMethod = "onPlayerJoin";
	private String checkMethodPath = String.valueOf(this.options) + "CheckMethod";

	private boolean checkForUpdates = true;
	private String checkForUpdatesPath = String.valueOf(this.options) + "CheckForUpdates";

	private long interval = 5;
	private String intervalPath = String.valueOf(this.options) + "Interval";

	private String dateFormat = "dd.MM.yyyy";
	private String dateFormatPath = String.valueOf(this.options) + "DateFormat";

	private List<String>promoteCommands;
	private List<String>demoteCommands;
	private String promoteCommandsPath = String.valueOf(this.options) + "PromoteCommands";
	private String demoteCommandsPath = String.valueOf(this.options) + "DemoteCommands";

	// OTHER OTHER OTHER OTHER OTHER OTHER OTHER OTHER OTHER OTHER OTHER OTHER
	private double versionNumber = 1.1;
	private String versionNumberPath = "VersionNumber";

	public ConfigUtil(File pluginFolder) {
		this.configFile = new File(pluginFolder, "config.yml");
		this.config = YamlConfiguration.loadConfiguration(configFile);
		this.startup();
	}

	@SuppressWarnings("unchecked")
	public void startup() {
		if (!this.configFile.exists()) {
			Bukkit.getLogger().log(Level.WARNING, this.consoleLogo + "... Starting config creation ...");
			this.createConfig();
		} else {
			if (config.getString(versionNumberPath).isEmpty()){
				Bukkit.getLogger().log(Level.WARNING, "Some problems when loading config /VersionNumber/");
			}else{
				Double tempversion = Double.valueOf(config.getString(versionNumberPath));
				if (this.versionNumber > tempversion){
					config.set(versionNumberPath, this.versionNumber);
					if (tempversion == 0.0){
						this.config.set(timeLeftDaysMsgPath, timeLeftDaysMsg);
						this.config.set(timeLeftHoursMsgPath, timeLeftHoursMsg);
						this.config.set(helpCommandMsgPath, helpCommandMsg);
						this.saveConfig();
					}
					if(tempversion == 1.0){
						List<String> promoteCommandsList = new ArrayList<String>();
						promoteCommandsList.add("pex user %player% group set %newRank%");
						promoteCommandsList.add("broadcast %player% has been promoted to %newRank%");
						this.config.set(promoteCommandsPath, promoteCommandsList);
						List<String> demoteCommandsList = new ArrayList<String>();
						demoteCommandsList.add("pex user %player% group set %newRank%");
						demoteCommandsList.add("broadcast %player% has been promoted to %newRank%");
						this.config.set(demoteCommandsPath, demoteCommandsList);
					}
				}
			}
			
			
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
				this.databaseTable = config.getString(databaseTablePath);
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
            if (this.config.getList(this.promoteCommandsPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo + "... Something went wrong while setting the \"PromoteCommands\", using default command (pex user %player% group set %newRank%). ..."));
        		promoteCommands.add("pex user %player% group set %newRank%");
        		promoteCommands.add("broadcast %player% has been promoted to %newRank%");
            } else {
                this.promoteCommands = (List<String>) this.config.getList(this.promoteCommandsPath);
            }
            if (this.config.getString(this.demoteCommandsPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo + "... Something went wrong while setting the \"DemoteCommand\", using default command (pex user %player% group set %oldRank%). ..."));
        		demoteCommands.add("pex user %player% group set %newRank%");
        		demoteCommands.add("broadcast %player% has been promoted to %newRank%");
            } else {
                this.demoteCommands = (List<String>) this.config.getList(this.demoteCommandsPath);
            }
            if (this.config.getString(this.lastDayMsgPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"LastDayMessage\", using default message (This is your last day as %oldRank%!). ..."));
                this.lastDayMsg = "This is your last day as %newRank%!";
            } else {
                this.lastDayMsg = this.config.getString(this.lastDayMsgPath);
            }
            if (this.config.getString(this.chatLogoPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"ChatLogo\", using default message (&a[&6SimpleTimedRank &5PRO&a]). ..."));
                this.chatLogo = "&a[&6SimpleTimedRank &5PRO&a]";
            } else {
                this.chatLogo = this.config.getString(this.chatLogoPath);
            }
            if (this.config.getString(this.timeExpiredMsgPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"TimeExpiredMessage\", using default message (Your time has been expired!). ..."));
                this.timeExpiredMsg = "Your time has been expired!";
            } else {
                this.timeExpiredMsg = this.config.getString(this.timeExpiredMsgPath);
            }
            
            
            if (this.config.getString(this.timeLeftDaysMsgPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"TimeLeftDaysMsg\", using default message (\"Time left as %newRank%: %days% Day(s)\"). ..."));
                this.timeLeftDaysMsg = "Time left as %newRank%: %days% Day(s)";
            } else {
                this.timeLeftDaysMsg = this.config.getString(this.timeLeftDaysMsgPath);
            }
            if (this.config.getString(this.timeLeftHoursMsgPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"TimeLeftHoursMsg\", using default message (\"Time left as %newRank%: %h% hours %m% minutes\"). ..."));
                this.timeLeftHoursMsg = "Time left as %newRank%: %h% hours %m% minutes";
            } else {
                this.timeLeftHoursMsg = this.config.getString(this.timeLeftHoursMsgPath);
            }
            if (this.config.getString(this.helpCommandMsgPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"HelpCommandMsg\", using default message (If you need help with the commands type '/str help'.). ..."));
                this.helpCommandMsg = "&eIf you need help with the commands type &b/strp help";
            } else {
                this.helpCommandMsg = this.config.getString(this.helpCommandMsgPath);
            }
            if (this.config.getString(this.checkForUpdatesPath).isEmpty()) {
                System.err.println(String.valueOf(consoleLogo  + "... Something went wrong while setting the \"CheckForUpdates\", using default value (true). ..."));
                this.checkForUpdates = true;
            }else{
            	this.checkForUpdates = this.config.getBoolean(this.checkForUpdatesPath);
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
		this.config.set(timeLeftDaysMsgPath, timeLeftDaysMsg);
		this.config.set(timeLeftHoursMsgPath, timeLeftHoursMsg);
		this.config.set(helpCommandMsgPath, helpCommandMsg);
		
		this.config.set( checkMethodPath, checkMethod );
		this.config.set( intervalPath, interval );
		this.config.set( dateFormatPath, dateFormat );
		
		List<String> promoteCommandsList = new ArrayList<String>();
		promoteCommandsList.add("pex user %player% group set %newRank%");
		promoteCommandsList.add("broadcast %player% has been promoted to %newRank%");
		this.config.set(promoteCommandsPath, promoteCommandsList);
		
		List<String> demoteCommandsList = new ArrayList<String>();
		demoteCommandsList.add("pex user %player% group set %newRank%");
		demoteCommandsList.add("broadcast %player% has been promoted to %newRank%");
		this.config.set(demoteCommandsPath, demoteCommandsList);
		
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

	public List<String> getPromoteCommands() {
		return promoteCommands;
	}

	public List<String> getDemoteCommands() {
		return demoteCommands;
	}

	public String getTimeLeftDaysMsg() {
		return this.colorize(timeLeftDaysMsg) + " ";
	}

	public String getTimeLeftHoursMsg() {
		return colorize(timeLeftHoursMsg) + " ";
	}

	public String getHelpCommandMsg() {
		return colorize(helpCommandMsg) + " ";
	}
	
}
