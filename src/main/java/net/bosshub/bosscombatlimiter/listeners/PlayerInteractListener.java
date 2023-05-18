package net.bosshub.bosscombatlimiter.listeners;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.bosshub.bosscombatlimiter.BossCombatLimiter;
import net.bosshub.bosscombatlimiter.utils.CooldownUtils;
import net.bosshub.bosscombatlimiter.utils.RegionUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInteractListener implements Listener {
    public BossCombatLimiter main;
    private final RegionUtils regionUtils;
    private final CooldownUtils cooldownUtils;
    public static HashMap<UUID, Integer> pearlCooldownMap;
    public static HashMap<UUID, Integer> riptideCooldownMap;

    private int pearlCooldown = 20;
    private int riptideCooldown = 20;
    private final String prefix;
    private final List<String> disabledRegions;

    public PlayerInteractListener(BossCombatLimiter main) {
        this.main = main;

        regionUtils = new RegionUtils();
        cooldownUtils = new CooldownUtils(main);

        pearlCooldownMap = new HashMap<>();
        riptideCooldownMap = new HashMap<>();

        prefix = this.main.getConfig().getString("message-prefix");
        disabledRegions = this.main.getConfig().getStringList("disabled-regions");
        pearlCooldown = this.main.getConfig().getInt("pearl-cooldown");
        riptideCooldown = this.main.getConfig().getInt("riptide-cooldown");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack itemStack = e.getItem();
        if(itemStack == null) return;

        Player player = e.getPlayer();
        final UUID uuid = player.getUniqueId();

        if(e.getAction().isRightClick()) {
            if(itemStack.getType().equals(Material.ENDER_PEARL)) {
                // If player is in region
                if(regionUtils.isPlayerInRegion(player, disabledRegions)) {
                    if(pearlCooldownMap.containsKey(player.getUniqueId())) {
                        e.setCancelled(true);
                        player.sendMessage(
                                Component.text(prefix, TextColor.color(0xff0000), TextDecoration.BOLD)
                                        .append(Component.text("You can't pearl for another " + pearlCooldownMap.get(uuid) + " seconds!")
                                                .color(NamedTextColor.WHITE)
                                                .decoration(TextDecoration.BOLD, false))
                        );
                    } else {
                        cooldownUtils.addPlayerToCooldown(player, pearlCooldownMap, pearlCooldown);
                    }
                }

            } else if(itemStack.getType().equals(Material.TRIDENT)) {
                if(regionUtils.isPlayerInRegion(player, disabledRegions)) {
                    if(riptideCooldownMap.containsKey(player.getUniqueId())) {
                        e.setCancelled(true);
                        player.sendMessage(
                                Component.text(prefix, TextColor.color(0xff0000), TextDecoration.BOLD)
                                        .append(Component.text("You can't use riptide for another " + riptideCooldownMap.get(uuid) + " seconds!")
                                                .color(NamedTextColor.WHITE)
                                                .decoration(TextDecoration.BOLD, false))
                        );
                    } else {
                        cooldownUtils.addPlayerToCooldown(player, riptideCooldownMap, riptideCooldown);
                    }
                }
            }
        }
    }
}
