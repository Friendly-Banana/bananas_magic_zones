package ga.euroblox.bananas_magic_zones;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public record WorldEventListener(BananasMagicZones plugin) implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        for (Portal portal : plugin.portals)
            if (portal.visible)
                portal.SetIndicators(true, event.getWorld());
    }
}