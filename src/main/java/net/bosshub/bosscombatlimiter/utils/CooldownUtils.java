package net.bosshub.bosscombatlimiter.utils;

import net.bosshub.bosscombatlimiter.BossCombatLimiter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class CooldownUtils {
    private final BossCombatLimiter main;

    public CooldownUtils(BossCombatLimiter main) {
        this.main = main;
    }

    public void addPlayerToCooldown(Player player, HashMap<UUID, Integer> map, int time) {
        // Get the players UUID
        UUID uuid = player.getUniqueId();
        // Add the player to the cooldown map
        map.put(uuid, time);
        // Start the cooldown timer
        (new BukkitRunnable() {
            public void run() {
                if ((Integer) map.get(uuid) <= 1) {
                    map.remove(uuid);
                    cancel();
                } else {
                    map.put(uuid, (Integer) map.get(uuid) - 1);
                }
            }
        }).runTaskTimer((Plugin) this.main, 0L, 20L);
    }

    public void removePlayerFromCooldown(Player player, HashMap<UUID, Integer> map) {
        UUID uuid = player.getUniqueId();
        map.remove(uuid);
    }

}
