package ga.euroblox.bananas_spawn.commands;

import ga.euroblox.bananas_spawn.BananasSpawn;
import ga.euroblox.bananas_spawn.Portal;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HidePortalCommand extends PortalCommand {

    public HidePortalCommand(BananasSpawn plugin) {
        super(plugin, false);
    }

    @Override
    public boolean RunForPortals(CommandSender sender, List<Portal> portals, String[] args) {
        for (Portal portal : portals)
            portal.SetIndicators(false, sender.getServer());
        sender.sendMessage("Now hiding " + portals.size() + " Portal(s).");
        return true;
    }

    @Override
    public boolean NoPortalsGiven(CommandSender sender, String[] args) {
        for (Portal portal : plugin.portals)
            portal.SetIndicators(false, sender.getServer());
        sender.sendMessage("Now hiding all Portals.");
        return true;
    }
}
