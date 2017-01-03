package me.bodyash.simpletimedrankpro.bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class simpletimedrankpro extends Plugin {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new simpletimedrankproListener());
    }

}