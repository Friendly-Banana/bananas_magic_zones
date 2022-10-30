package ga.euroblox.bananas_magic_zones.commands;

import ga.euroblox.bananas_magic_zones.BananasMagicZones;
import ga.euroblox.bananas_magic_zones.Portal;
import org.bukkit.command.CommandSender;

import java.util.List;

public class SetPortalCommand extends PortalCommand {
    public SetPortalCommand(BananasMagicZones plugin) {
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
