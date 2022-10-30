package ga.euroblox.bananas_magic_zones.commands;

import ga.euroblox.bananas_magic_zones.BananasMagicZones;
import ga.euroblox.bananas_magic_zones.Portal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record SelectPortalCommand(BananasMagicZones plugin) implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player player && PortalCommand.selectedPortals.containsKey(player.getUniqueId())) {
                String name = PortalCommand.selectedPortals.get(player.getUniqueId());
                sender.sendMessage("Selected " + plugin.PortalsWithName(name).size() + " portal(s) with name " + name);
                return true;
            }
            return false;
        }
        List<Portal> portals = plugin.PortalsWithName(args[0]);
        if (portals.isEmpty()) {
            sender.sendMessage("Couldn't find a Portal with name " + args[0]);
            return true;
        }
        if (sender instanceof Player player) {
            PortalCommand.selectedPortals.put(player.getUniqueId(), args[0]);
            sender.sendMessage("Selected " + args[0]);
            return true;
        }
        return false;
    }
}
