package net.bosshub.bosscombatlimiter;

import net.bosshub.bosscombatlimiter.expansions.PlaceholderAPIExpansion;
import net.bosshub.bosscombatlimiter.listeners.ConsumeListener;
import net.bosshub.bosscombatlimiter.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class BossCombatLimiter extends JavaPlugin {
    public void onEnable() {
        // Register config
        saveDefaultConfig();

        // Register events
        getServer().getPluginManager().registerEvents((Listener)new PlayerInteractListener(this), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new ConsumeListener(this), (Plugin)this);

        // Register the PlaceholderAPI expansion
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIExpansion(this).register();
        }
    }

    public void onDisable() {}
}
