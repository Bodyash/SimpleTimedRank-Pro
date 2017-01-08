package me.bodyash.simpletimedrankpro.bungee.listeners;

import me.bodyash.simpletimedrankpro.utils.ConfigUtil;
import me.bodyash.simpletimedrankpro.utils.updater.SpigotUpdater;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeePlayerListener implements Listener {
	
	SpigotUpdater updater;
	ConfigUtil config;
	
	public BungeePlayerListener(SpigotUpdater updater, ConfigUtil config) {
		this.updater = updater;
		this.config = config;
	}
	
    @EventHandler
    public void onServerConnected(final ServerConnectedEvent event) {
        event.getPlayer().sendMessage(new ComponentBuilder("Welcome to " + event.getServer().getInfo().getName() + "!").color(ChatColor.GREEN).create());
        if (event.getPlayer().hasPermission("simpletimedrank.bungeeadmin")){
        	if (updater.isUpdateFound()){
        		event.getPlayer().sendMessage(new ComponentBuilder(config.getChatLogo() + "Update FOUND!").color(ChatColor.GREEN).create());
        	}
        }
    }
    
    public void onCommand (net.md_5.bungee.api.event.ChatEvent event){
    	
    }
}