package me.bodyash.simpletimedrankpro.utils.updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.bodyash.simpletimedrankpro.bungee.BungeeMain;

public class SpigotUpdater {
	private String WRITE_STRING;
	private String version;
	private String oldVersion;
	private SpigotUpdater.UpdateResult result = SpigotUpdater.UpdateResult.DISABLED;
	private HttpURLConnection connection;
	private boolean updateFound;

	public enum UpdateResult {
		NO_UPDATE,
		DISABLED,
		FAIL_SPIGOT,
		SPIGOT_UPDATE_AVAILABLE
	}

	public SpigotUpdater(JavaPlugin plugin) {
		String RESOURCE_ID = "33678"; // change resource id
		oldVersion = plugin.getDescription().getVersion().replaceAll("-SNAPSHOT-", ".");
		try {
			String QUERY = "/legacy/update.php";
			String HOST = "https://api.spigotmc.org";
			connection = (HttpURLConnection) new URL(HOST + QUERY).openConnection();
		} catch (IOException e) {
			result = UpdateResult.FAIL_SPIGOT;
			return;
		}

		WRITE_STRING = "?resource=" + RESOURCE_ID;
		runSpigot();
	}

	public SpigotUpdater(BungeeMain bungeeMain) {
		String RESOURCE_ID = "33678"; // change resource id
		oldVersion = bungeeMain.getDescription().getVersion().replaceAll("-SNAPSHOT-", ".");
		try {
			String QUERY = "/legacy/update.php";
			String HOST = "https://api.spigotmc.org";
			connection = (HttpURLConnection) new URL(HOST + QUERY).openConnection();
		} catch (IOException e) {
			result = UpdateResult.FAIL_SPIGOT;
			return;
		}

		WRITE_STRING = "?resource=" + RESOURCE_ID;
		runSpigot();
	}

	private void runSpigot() {
		connection.setDoOutput(true);
		try {
			String REQUEST_METHOD = "GET";
			connection.setRequestMethod(REQUEST_METHOD);
			connection.getOutputStream().write(WRITE_STRING.getBytes("UTF-8"));
		} catch (IOException e) {
			result = UpdateResult.FAIL_SPIGOT;
		}
		String currversion;
		try {
			currversion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
		} catch (Exception e) {
			result = UpdateResult.FAIL_SPIGOT;
			return;
		}
		if (currversion.length() <= 7) {
			this.version = currversion.replace("[^A-Za-z]", "").replace("|", "").replace("-", "");
			spigotCheckUpdate();
			return;
		}
		result = UpdateResult.FAIL_SPIGOT;
	}

	private void spigotCheckUpdate() {
		Integer oldVersion = Integer.parseInt(this.oldVersion.replace(".", ""));
		Integer currentVersion = Integer.parseInt(this.version.replace(".", ""));
		if (oldVersion < currentVersion) {
				result = UpdateResult.SPIGOT_UPDATE_AVAILABLE;
				this.updateFound = true;
		} else {
			result = UpdateResult.NO_UPDATE;
			this.updateFound = false;
		}
	}
	

	public UpdateResult getResult() {
		return result;
	}

	public String getVersion() {
		return version;
	}
	
    
    public boolean isUpdateFound(){
    	return updateFound;
    }

	public void printResultToConsole() {
		if(result == UpdateResult.FAIL_SPIGOT){
			Bukkit.getLogger().log(Level.WARNING, "[SimpleTimedRank Pro] Update check fail");
		}
		if(result == UpdateResult.NO_UPDATE){
			Bukkit.getLogger().log(Level.INFO, "[SimpleTimedRank Pro] Update not found");
		}
		if (result == UpdateResult.SPIGOT_UPDATE_AVAILABLE){
			Bukkit.getLogger().log(Level.INFO, "[SimpleTimedRank Pro] UPDATE FOUND!");
		}
	}
	
	public void printResultToBungeeConsole(BungeeMain bungee) {
		if(result == UpdateResult.FAIL_SPIGOT){
			bungee.getLogger().log(Level.WARNING, "[SimpleTimedRank Pro] Update check fail");
		}
		if(result == UpdateResult.NO_UPDATE){
			bungee.getLogger().log(Level.INFO, "[SimpleTimedRank Pro] Update not found");
		}
		if (result == UpdateResult.SPIGOT_UPDATE_AVAILABLE){
			bungee.getLogger().log(Level.INFO, "[SimpleTimedRank Pro] UPDATE FOUND!");
		}
	}
	

}