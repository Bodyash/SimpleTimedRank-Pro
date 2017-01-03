package me.bodyash.simpletimedrankpro.listeners;

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

public class TimeChecker implements Listener {

	private ConfigUtil config;
	private ConfigUser confU;

	public TimeChecker(ConfigUtil config, ConfigUser confU) {
		this.config = config;
		this.confU = confU;
	}

	public Long getPlayerDaysLeft(String playerName) {
		try {
			User u = confU.getUserData(playerName);
			return TimeUnit.MILLISECONDS.toDays(u.getUntilDate() - u.getFromDate());
		} catch (Exception e) {
			return null;
		}

	}

	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerJoin(PlayerJoinEvent e) {
		this.checkPlayer(e.getPlayer());
	}

	public void checkPlayer(Player player) {
		try {
			User u = null;
			if (confU.getUserData(player.getName()) != null) {
				u = confU.getUserData(player.getName());
				if (u.getStatus() != -1) {
					if ((new Date().getTime() - u.getUntilDate()) > 0) {
						player.sendMessage(config.getChatLogo() + " " + config.getTimeExpiredMsg());
						Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), this.parseSyntax(u, config.getDemoteCommand()));
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
