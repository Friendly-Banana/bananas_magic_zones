package ga.euroblox.bananas_spawn.commands;

import ga.euroblox.bananas_spawn.BananasSpawn;
import ga.euroblox.bananas_spawn.Portal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record SelectPortalCommand(BananasSpawn plugin) implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
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
