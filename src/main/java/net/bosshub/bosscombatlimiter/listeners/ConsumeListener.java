package net.bosshub.bosscombatlimiter.listeners;

import net.bosshub.bosscombatlimiter.BossCombatLimiter;
import net.bosshub.bosscombatlimiter.utils.CooldownUtils;
import net.bosshub.bosscombatlimiter.utils.RegionUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ConsumeListener implements Listener {
    public BossCombatLimiter main;
    private final RegionUtils regionUtils;
    private final CooldownUtils cooldownUtils;
    public static HashMap<UUID, Integer> crappleCooldownMap;

    private int crappleCooldown = 30;
    private final String prefix;
    private final List<String> disabledRegions;

    public ConsumeListener(BossCombatLimiter main) {
        this.main = main;

        regionUtils = new RegionUtils();

        crappleCooldownMap = new HashMap<>();
        cooldownUtils = new CooldownUtils(main);

        this.crappleCooldown = this.main.getConfig().getInt("crapple-cooldown");
        this.prefix = this.main.getConfig().getString("message-prefix");
        this.disabledRegions = this.main.getConfig().getStringList("disabled-regions");
    }
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        if(event.getItem().getType().equals(Material.GOLDEN_APPLE)) {
            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();

            if(regionUtils.isPlayerInRegion(player, disabledRegions)) {
                if (crappleCooldownMap.containsKey(uuid)) {
                    player.sendMessage(
                            Component.text(prefix, TextColor.color(0xff0000), TextDecoration.BOLD)
                            .append(Component.text("You can't eat that for another " + crappleCooldownMap.get(uuid) + " seconds!")
                                    .color(NamedTextColor.WHITE)
                                    .decoration(TextDecoration.BOLD, false))
                    );
                    event.setCancelled(true);
                } else {
                    cooldownUtils.addPlayerToCooldown(player, crappleCooldownMap, crappleCooldown);
                }
            }
        }
    }

}
