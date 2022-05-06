package ga.euroblox.bananas_spawn;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public record WorldEventListener(BananasSpawn plugin) implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        for (Portal portal : plugin.portals)
            if (portal.visible)
                portal.SetIndicators(true, event.getWorld());
    }
}