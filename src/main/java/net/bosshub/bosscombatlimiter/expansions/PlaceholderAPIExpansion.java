package net.bosshub.bosscombatlimiter.expansions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.bosshub.bosscombatlimiter.BossCombatLimiter;
import net.bosshub.bosscombatlimiter.listeners.ConsumeListener;
import net.bosshub.bosscombatlimiter.listeners.PlayerInteractListener;
import org.bukkit.entity.Player;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    private final BossCombatLimiter plugin;

    public PlaceholderAPIExpansion(BossCombatLimiter plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "Goose";
    }

    @Override
    public String getIdentifier() {
        return "bosscombatlimiter";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if(player == null) {
            return "-1";
        }

        if(params.equalsIgnoreCase("pearl_cooldown")) {
            if(PlayerInteractListener.pearlCooldownMap.get(player.getUniqueId()) == null) return "0";
            int cooldown = PlayerInteractListener.pearlCooldownMap.get(player.getUniqueId());
            return String.valueOf(cooldown);
        } else if(params.equalsIgnoreCase("crapple_cooldown")) {
            if(ConsumeListener.crappleCooldownMap.get(player.getUniqueId()) == null) return "0";
            int cooldown = ConsumeListener.crappleCooldownMap.get(player.getUniqueId());
            return String.valueOf(cooldown);
        } else if(params.equalsIgnoreCase("riptide_cooldown")) {
            if(PlayerInteractListener.riptideCooldownMap.get(player.getUniqueId()) == null) return "0";
            int cooldown = PlayerInteractListener.riptideCooldownMap.get(player.getUniqueId());
            return String.valueOf(cooldown);
        }

        return "0";
    }

}
