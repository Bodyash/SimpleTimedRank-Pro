package me.bodyash.simpletimedrankpro;

import java.util.Collection;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.bodyash.simpletimedrankpro.dao.ConfigUser;
import me.bodyash.simpletimedrankpro.dao.ConfigUserSql;
import me.bodyash.simpletimedrankpro.dao.ConfigUserYml;
import me.bodyash.simpletimedrankpro.listeners.CommandListener;
import me.bodyash.simpletimedrankpro.listeners.PlayerListener;
import me.bodyash.simpletimedrankpro.listeners.TimeChecker;
import me.bodyash.simpletimedrankpro.utils.ConfigUtil;
import me.bodyash.simpletimedrankpro.utils.updater.SpigotUpdater;

public class Main extends JavaPlugin {

	private ConfigUtil config;
	private ConfigUser configUser;
	private TimeChecker timeChecker;
	private CommandListener commandListener;

	@Override
	public void onEnable() {
		// load config file
		config = new ConfigUtil(this);
		Bukkit.getLogger().log(Level.INFO, config.getConsoleLogo() + "plugin is loading...");
		Bukkit.getLogger().log(Level.INFO, config.getConsoleLogo() + this.getDescription().getName() + " v."
				+ this.getDescription().getVersion() + " by " + this.getDescription().getAuthors());
		// if we want to check 4 updates - register new listener (onPlayerJoin)
		// and notify about updates if player is op.
		if (config.checkForUpdates == true) {
			getServer().getPluginManager()
					.registerEvents(new PlayerListener(new SpigotUpdater(this), config.getChatLogo()), this);
		}
		// load DataBase (yml or sql)
		if (config.getDatabaseType().equals("mysql")) {
			configUser = new ConfigUserSql(this.config);
			if (((ConfigUserSql) configUser).test()) {
				Bukkit.getLogger().log(Level.INFO, config.getConsoleLogo() + "DB test OK, gonna use MYSQL");
			} else {
				Bukkit.getLogger().log(Level.SEVERE, config.getConsoleLogo() + "DB test FAIL, gonna use YML");
				configUser = new ConfigUserYml(config.getConsoleLogo(), this);
			}
		} else {
			configUser = new ConfigUserYml(config.getConsoleLogo(), this);
		}
		// register TimeChecker and IntervalChecker
		timeChecker = new TimeChecker(config, configUser);
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
