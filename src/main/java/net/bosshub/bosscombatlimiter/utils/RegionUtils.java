package net.bosshub.bosscombatlimiter.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Player;

import java.util.List;

public class RegionUtils {

    public boolean isPlayerInRegion(Player player, List<String> disabledRegions) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(player.getLocation()));

        for (String disabledRegion : disabledRegions) {
            for (ProtectedRegion region : set) {
                if (region.getId().equalsIgnoreCase(disabledRegion)) {
                    return true;
                }
            }
        }

        return false;
    }

}
