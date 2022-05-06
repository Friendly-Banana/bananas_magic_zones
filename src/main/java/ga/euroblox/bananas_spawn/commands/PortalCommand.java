package ga.euroblox.bananas_spawn.commands;

import ga.euroblox.bananas_spawn.BananasSpawn;
import ga.euroblox.bananas_spawn.Portal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public abstract class PortalCommand implements CommandExecutor {
    static final HashMap<UUID, String> selectedPortals = new HashMap<>();
    protected final boolean requiresName;
    protected final BananasSpawn plugin;

    public PortalCommand(BananasSpawn plugin, boolean requiresName) {
        this.plugin = plugin;
        this.requiresName = requiresName;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (requiresName && selectedPortals.containsKey(player.getUniqueId())) {
                List<Portal> portals = plugin.PortalsWithName(selectedPortals.get(player.getUniqueId()));
                if (portals.isEmpty()) {
                    sender.sendMessage("Couldn't find a Portal with name " + selectedPortals.get(player.getUniqueId()));
                    return true;
                }
                return RunForPortals(sender, portals, args);
            }
            return NoPortalsGiven(sender, args);
        }
        return false;
    }

    public boolean NoPortalsGiven(CommandSender sender, String[] args) {
        sender.sendMessage("This command works on a portal. Please /select one.");
        return true;
    }

    public boolean RunForPortals(CommandSender sender, List<Portal> portals, String[] args) {
        return true;
    }
}
