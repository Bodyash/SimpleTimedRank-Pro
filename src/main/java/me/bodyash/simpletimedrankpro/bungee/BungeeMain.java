package me.bodyash.simpletimedrankpro.bungee;

import java.util.logging.Level;
import me.bodyash.simpletimedrankpro.bungee.listeners.BungeePlayerListener;
import me.bodyash.simpletimedrankpro.dao.ConfigUser;
import me.bodyash.simpletimedrankpro.dao.ConfigUserSql;
import me.bodyash.simpletimedrankpro.dao.ConfigUserYml;
import me.bodyash.simpletimedrankpro.utils.ConfigUtil;
import me.bodyash.simpletimedrankpro.utils.updater.SpigotUpdater;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeMain extends Plugin {
	private ConfigUtil config;
	private ConfigUser configUser;

	@Override
	public void onEnable() {

		config = new ConfigUtil(getDataFolder());
		this.getLogger().log(Level.INFO, config.getConsoleLogo() + "Bungee Server Detected... Enabling...");
		this.getLogger().log(Level.INFO, config.getConsoleLogo() + this.getDescription().getName() + " v."
				+ this.getDescription().getVersion() + " by " + this.getDescription().getAuthor());

		if (config.isCheckForUpdates()) {
			getProxy().getPluginManager().registerListener(this,
					new BungeePlayerListener(new SpigotUpdater(this), config));
		}

		// load DataBase (yml or sql)
		if (config.getDatabaseType().equals("mysql")) {
			configUser = new ConfigUserSql(this.config);
			if (((ConfigUserSql) configUser).test()) {
				getLogger().log(Level.INFO, config.getConsoleLogo() + "DB test OK, gonna use MYSQL");
			} else {
				getLogger().log(Level.SEVERE, config.getConsoleLogo() + "DB test FAIL, gonna use YML");
				configUser = new ConfigUserYml(config.getConsoleLogo(), this.getDataFolder());
			}
		} else {
			configUser = new ConfigUserYml(config.getConsoleLogo(), this.getDataFolder());
		}
		
		

	}

}