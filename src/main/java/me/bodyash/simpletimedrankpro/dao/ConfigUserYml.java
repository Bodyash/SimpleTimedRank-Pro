package me.bodyash.simpletimedrankpro.dao;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import me.bodyash.simpletimedrankpro.Main;

public class ConfigUserYml implements ConfigUser {
	private String consoleLogo;
	private File configFile;
	private YamlConfiguration config;
	public double versionNumber;
	private String versionNumberPath;
	private File dataFolder;

	public ConfigUserYml(String consoleLogo, Main main) {
		this.versionNumberPath = "VersionNumber";
		this.consoleLogo = consoleLogo;
		this.dataFolder = main.getDataFolder();
		this.configFile = new File(main.getDataFolder(), "users.yml");
		this.config = YamlConfiguration.loadConfiguration((File) this.configFile);
		this.loadUsersConfig();
	}

	@Override
	public void loadUsersConfig() {
		if (!this.configFile.exists()) {
			System.out.println(String.valueOf(this.consoleLogo) + "... Starting users config creation ...");
			this.createUsersConfig();
		} /*
			 * else if
			 * (Double.valueOf(this.config.getString(this.versionNumberPath)) <
			 * this.versionNumber) {
			 * System.err.println(String.valueOf(this.consoleLogo) +
			 * "... New users config version detected ...");
			 * System.err.println(String.valueOf(this.consoleLogo) +
			 * "... Backing up old users config ...");
			 * System.err.println(String.valueOf(this.consoleLogo) +
			 * "... Don't forget to restore your user data! ...");
			 * System.err.println(String.valueOf(this.consoleLogo) +
			 * "... Don't forget to restore your user data! ...");
			 * System.err.println(String.valueOf(this.consoleLogo) +
			 * "... Don't forget to restore your user data! ...");
			 * System.err.println(String.valueOf(this.consoleLogo) +
			 * "... Sorry for that but if I change something in the users.yml this should be very important! ..."
			 * ); try { this.configFile.renameTo(new
			 * File(this.main.getDataFolder(), "usersOld.yml")); File
			 * configFile2 = new File(this.main.getDataFolder(), "users.yml");
			 * if (configFile2.exists()) { configFile2.delete(); }
			 * this.createUsersConfig(); } catch (Exception e) {
			 * System.err.println(String.valueOf(this.consoleLogo) +
			 * "Can't backup the OLD users config file, see info below:");
			 * e.printStackTrace(); } if (!this.configFile.exists()) {
			 * System.out.println(String.valueOf(this.consoleLogo) +
			 * "... Starting NEW users config creation ...");
			 * this.createUsersConfig(); try {
			 * this.config.save(this.configFile);
			 * System.out.println(String.valueOf(this.consoleLogo) +
			 * "... Finished NEW users config creation!"); } catch (IOException
			 * e) { System.err.println(String.valueOf(this.consoleLogo) +
			 * "Can't create the NEW users config file, see info below:");
			 * e.printStackTrace(); } } }
			 */
		// totaly useless code

	}

	@Override
	public void createUsersConfig() {
		this.config.options().header(
				"Info:\n!!ATTENTION: Please do not touch the version number!!\nStatus declaration:\n1 --> this means the status is active\n0 --> currently unused\n-1 --> the status is expired \n!!ATTENTION: Do not touch the user data only if you know what you do!!");
		this.config.set(this.versionNumberPath, (Object) this.versionNumber);
		this.config.createSection("Users");
		try {
			this.config.save(this.configFile);
			System.out.println(String.valueOf(this.consoleLogo) + "... Finished users config creation!");
		} catch (IOException e) {
			System.err.println(String.valueOf(this.consoleLogo) + "Can't create users config file, see info below:");
			e.printStackTrace();
		}
	}

	@Override
	public void addUser(User u) {
		try {
			this.config.set("Users." + u.getUserName() + ".UntilDate", (Object) u.getUntilDate());
			this.config.set("Users." + u.getUserName() + ".FromDate", (Object) u.getFromDate());
			this.config.set("Users." + u.getUserName() + ".PromotedRank", (Object) u.getPromotedRank());
			this.config.set("Users." + u.getUserName() + ".OldRank", (Object) u.getPromotedRank());
			this.config.set("Users." + u.getUserName() + ".Status", (Object) u.getStatus());
			this.config.save(this.configFile);
		} catch (Exception e) {
			System.err.println(String.valueOf(this.consoleLogo)
					+ "An error occured while trying to add a new player to the users config!");
		}

	}

	@Override
	public User getUserData(String playerName) {
		try {
			File configFile = new File(this.dataFolder, "users.yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration((File) configFile);
			User user = new User(playerName, config.getLong("Users." + playerName + ".UntilDate"),
					config.getLong("Users." + playerName + ".FromDate"),
					config.getString("Users." + playerName + ".PromotedRank"),
					config.getString("Users." + playerName + ".OldRank"),
					config.getInt("Users." + playerName + ".Status"));
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void setUserTimeExpired(String name) {
        try {
            String expired = "expired";
            this.config.set("Users." + name + ".UntilDate", (Object)expired);
            this.config.set("Users." + name + ".FromDate", (Object)expired);
            this.config.set("Users." + name + ".PromotedRank", (Object)expired);
            this.config.set("Users." + name + ".OldRank", (Object)expired);
            this.config.set("Users." + name + ".Status", (Object)"-1");
            this.config.save(this.configFile);
        }
        catch (Exception e) {
            System.err.println(String.valueOf(this.consoleLogo) + "An error occured while trying to expire the time of the player " + name + "!");
            System.err.println(String.valueOf(this.consoleLogo) + "If you know that this player exist please do this manually; Sorry for that!");
        }

	}

}
