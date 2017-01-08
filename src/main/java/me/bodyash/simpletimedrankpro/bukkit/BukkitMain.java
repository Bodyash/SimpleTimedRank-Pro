package me.bodyash.simpletimedrankpro.bukkit;

import java.util.Collection;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.bodyash.simpletimedrankpro.bukkit.listeners.CommandListener;
import me.bodyash.simpletimedrankpro.bukkit.listeners.TimeChecker;
import me.bodyash.simpletimedrankpro.dao.ConfigUser;
import me.bodyash.simpletimedrankpro.dao.ConfigUserSql;
import me.bodyash.simpletimedrankpro.dao.ConfigUserYml;
import me.bodyash.simpletimedrankpro.utils.ConfigUtil;
import me.bodyash.simpletimedrankpro.utils.updater.SpigotUpdater;

public class BukkitMain extends JavaPlugin {

	private ConfigUtil config;
	private ConfigUser configUser;
	private TimeChecker timeChecker;
	private CommandListener commandListener;

	@Override
	public void onEnable() {
		config = new ConfigUtil(getDataFolder());
		Bukkit.getLogger().log(Level.INFO, config.getConsoleLogo() + "plugin is loading...");
		Bukkit.getLogger().log(Level.INFO, config.getConsoleLogo() + this.getDescription().getName() + " v."
				+ this.getDescription().getVersion() + " by " + this.getDescription().getAuthors());
		// load DataBase (yml or sql)
		if (config.getDatabaseType().equals("mysql")) {
			configUser = new ConfigUserSql(this.config);
			if (((ConfigUserSql) configUser).test()) {
				Bukkit.getLogger().log(Level.INFO, config.getConsoleLogo() + "DB test OK, gonna use MYSQL");
			} else {
				Bukkit.getLogger().log(Level.SEVERE, config.getConsoleLogo() + "DB test FAIL, gonna use YML");
				configUser = new ConfigUserYml(config.getConsoleLogo(), this.getDataFolder());
			}
		} else {
			configUser = new ConfigUserYml(config.getConsoleLogo(), this.getDataFolder());
		}
		// register TimeChecker and IntervalChecker
		timeChecker = new TimeChecker(config, configUser, new SpigotUpdater(this));
		if (config.getCheckMethod().compareToIgnoreCase("all") == 0) {
			getServer().getPluginManager().registerEvents(timeChecker, this);
			this.intervalChecker();
		}
		if (config.getCheckMethod().compareToIgnoreCase("interval") == 0) {
			this.intervalChecker();
		} else {
			getServer().getPluginManager().registerEvents(timeChecker, this);
		}
		//initialize command listener
		commandListener = new CommandListener(config, this, configUser, timeChecker);
		Bukkit.getLogger().log(Level.INFO,
				config.getConsoleLogo() + "Plugin SUCCESSFULLY enabled! My congratulations!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return commandListener.onCommand(sender, command, label, args);
	}

	private void intervalChecker() {
		long interval = this.config.getInterval() * 1200;
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				Collection<? extends Player> arrplayer = Bukkit.getOnlinePlayers();
				for (Player player : arrplayer) {
					timeChecker.checkPlayer(player);
				}
			}
		}, 0, interval);
	}
}
