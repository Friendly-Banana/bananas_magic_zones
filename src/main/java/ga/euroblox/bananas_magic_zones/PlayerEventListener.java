package ga.euroblox.bananas_magic_zones;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public record PlayerEventListener(BananasMagicZones plugin) implements Listener {
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
            portal.TryExecute(event.getPlayer(), event.getTo());
        }
    }
}