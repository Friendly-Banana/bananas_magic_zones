package ga.euroblox.bananas_magic_zones.commands;

import ga.euroblox.bananas_magic_zones.BananasMagicZones;
import ga.euroblox.bananas_magic_zones.Portal;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RenamePortalCommand extends PortalCommand {
    public RenamePortalCommand(BananasMagicZones plugin) {
        super(plugin, true);
    }

    @Override
    public boolean RunForPortals(CommandSender sender, List<Portal> portals, String[] args) {
        if (args.length == 0) {
            return false;
        }
        for (Portal portal : portals)
            portal.name = args[0];
        sender.sendMessage("Renamed " + portals.size() + " Portal(s) to " + args[0]);
        if (sender instanceof Player player)
            player.performCommand("select " + args[0]);
        return true;
    }
}
