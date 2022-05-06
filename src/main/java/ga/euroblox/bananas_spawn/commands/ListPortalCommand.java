package ga.euroblox.bananas_spawn.commands;

import ga.euroblox.bananas_spawn.BananasSpawn;
import ga.euroblox.bananas_spawn.Portal;
import org.bukkit.command.CommandSender;

public class ListPortalCommand extends PortalCommand {
    public ListPortalCommand(BananasSpawn plugin) {
        super(plugin, false);
    }

    @Override
    public boolean NoPortalsGiven(CommandSender sender, String[] args) {
        sender.sendMessage("List of Portals:\n" + String.join("\n", plugin.portals.stream().map(Portal::toString).toList()));
        return true;
    }
}
