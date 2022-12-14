package ga.euroblox.bananas_magic_zones.commands;

import ga.euroblox.bananas_magic_zones.BananasMagicZones;
import ga.euroblox.bananas_magic_zones.Portal;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RemovePortalCommand extends PortalCommand {

    public RemovePortalCommand(BananasMagicZones plugin) {
        super(plugin, true);
    }

    @Override
    public boolean RunForPortals(CommandSender sender, List<Portal> portals, String[] args) {
        for (Portal portal : portals)
            plugin.RemovePortal(portal);
        sender.sendMessage("Removed " + portals.size() + " Portal(s).");
        return true;
    }
}
