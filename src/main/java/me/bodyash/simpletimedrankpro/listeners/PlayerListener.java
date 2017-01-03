package me.bodyash.simpletimedrankpro.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import me.bodyash.simpletimedrankpro.utils.updater.SpigotUpdater;

public class PlayerListener implements Listener {
	
	private SpigotUpdater spigotUpdater;
	private String logo;
	
	public PlayerListener(SpigotUpdater spigotUpdater, String logo) {
		this.logo = logo;
		this.spigotUpdater = spigotUpdater;
		spigotUpdater.printResultToConsole();
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void NotifyOp(PlayerLoginEvent e){
		if (e.getPlayer().isOp()){
			if (spigotUpdater.isUpdateFound()){
				e.getPlayer().sendMessage(logo + " Update found. Download new version at https://www.spigotmc.org/resources/simpletimedrank-pro.33678/");
			}
		}
	}
	

}
