package me.bodyash.simpletimedrankpro.bukkit.listeners;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.bodyash.simpletimedrankpro.dao.ConfigUser;
import me.bodyash.simpletimedrankpro.dao.User;
import me.bodyash.simpletimedrankpro.utils.ConfigUtil;
import me.bodyash.simpletimedrankpro.utils.updater.SpigotUpdater;

public class TimeChecker implements Listener {

	private ConfigUtil config;
	private ConfigUser confU;
	private SpigotUpdater spigotUpdater;
	private boolean isCheckForUpdates;

	public TimeChecker(ConfigUtil config, ConfigUser confU, SpigotUpdater spigotUpdater) {
		this.config = config;
		this.confU = confU;
		this.isCheckForUpdates = config.isCheckForUpdates();
		this.spigotUpdater = spigotUpdater;
		if (this.isCheckForUpdates){
			spigotUpdater.printResultToConsole();
		}
	}

	public Long getPlayerDaysPromoted(String playerName) {
		try {
			User u = confU.getUserData(playerName);
			return TimeUnit.MILLISECONDS.toDays(u.getUntilDate() - u.getFromDate());
		} catch (Exception e) {
			return null;
		}

	}
	
	public Long getPlayerDaysLeft(String playerName) {
		try {
			User u = confU.getUserData(playerName);
			Long days =  TimeUnit.MILLISECONDS.toDays(u.getUntilDate() - new Date().getTime());
			return days;
		} catch (Exception e) {
			return null;
		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onPlayerJoin(PlayerJoinEvent e) {
		this.checkPlayer(e.getPlayer());
		if (this.isCheckForUpdates){
			this.notifyOp(e.getPlayer());
		}
	}
	
	public void notifyOp(Player player){
		if (player.isOp()){
			if (this.spigotUpdater.isUpdateFound()){
				player.sendMessage(config.getChatLogo() + "Update found. Download new version at https://www.spigotmc.org/resources/simpletimedrank-pro.33678/");
			}
		}
	}

	public void checkPlayer(Player player) {
		try {
			User u = confU.getUserData(player.getName());
			if (u != null) {
				if (u.getStatus() != -1) {
					if ((new Date().getTime() - u.getUntilDate()) > 0) {
						Bukkit.getLogger().log(Level.INFO, u.getUserName() + " Until: " + new Date(u.getUntilDate()).toString() + " as " + u.getPromotedRank());
						player.sendMessage(config.getChatLogo() + " " + config.getTimeExpiredMsg());
						/*Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), this.parseSyntax(u, config.getDemoteCommand()));*/
						for (String demoteCommand : this.config.getDemoteCommands()) {
							Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), this.parseSyntax(u, demoteCommand));
						}
						Bukkit.getLogger().log(Level.INFO, config.getConsoleLogo() + "Player " + u.getUserName()
								+ " was demoted to " + u.getOldRank());
						confU.setUserTimeExpired(u.getUserName());
						return;
					}
					GregorianCalendar now = new GregorianCalendar();
					GregorianCalendar until = new GregorianCalendar();
					until.setTime(new Date(u.getUntilDate()));
					if ((now.get(Calendar.DAY_OF_YEAR) - until.get(Calendar.DAY_OF_YEAR) == 0)
							&& (now.get(Calendar.YEAR) - until.get(Calendar.YEAR)) == 0) {
						player.sendMessage(config.getChatLogo() + " " + this.parseSyntax(u, config.getLastDayMsg()));
						return;
					}
				}
			}
		} catch (Exception e2) {
			System.err.println(String.valueOf(config.getConsoleLogo())
					+ "Error: An error occurred on the method \"onPlayerJoin\".");
			System.err.println(String.valueOf(config.getConsoleLogo()) + "Please see infos below:");
			e2.printStackTrace();
		}

	}


	public String parseSyntax(User u, String msg) {
		msg = msg.replace("%player%", u.getUserName());
		msg = msg.replace("%newRank%", u.getPromotedRank());
		msg = msg.replace("%oldRank%", u.getOldRank());
		return msg;
	}

}
