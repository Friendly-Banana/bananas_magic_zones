package ga.euroblox.bananas_spawn.commands;

import ga.euroblox.bananas_spawn.BananasSpawn;
import ga.euroblox.bananas_spawn.Portal;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SetPortalCommand extends PortalCommand {
    public SetPortalCommand(BananasSpawn plugin) {
        super(plugin, true);
    }

    @Override
    public boolean RunForPortals(CommandSender sender, List<Portal> portals, String[] args) {
        if (args.length == 0) {
            return false;
        }
        for (Portal portal : portals)
            portal.command = String.join(" ", args);
        sender.sendMessage("Set the command of " + portals.size() + " Portal(s) to " + portals.get(0).command);
        return true;
    }
}
