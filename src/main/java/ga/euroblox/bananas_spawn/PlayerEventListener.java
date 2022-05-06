package ga.euroblox.bananas_spawn;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public record PlayerEventListener(BananasSpawn plugin) implements Listener {
    private static final HashMap<UUID, Integer> enteredPortals = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(player.getWorld().getSpawnLocation());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.hasChangedBlock())
            return;

        for (Portal portal : plugin.portals) {
            UUID uniqueId = event.getPlayer().getUniqueId();
            if (portal.Contains(event.getTo()) && portal.id != enteredPortals.get(uniqueId)) {
                enteredPortals.put(uniqueId, portal.id);
                event.getPlayer().performCommand(portal.command);
            } else if (portal.id == enteredPortals.get(uniqueId) && !portal.Contains(event.getTo()))
                enteredPortals.remove(uniqueId);
        }
    }
}