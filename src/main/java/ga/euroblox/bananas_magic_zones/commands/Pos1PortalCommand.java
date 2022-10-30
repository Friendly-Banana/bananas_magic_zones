package ga.euroblox.bananas_magic_zones.commands;

import ga.euroblox.bananas_magic_zones.BananasMagicZones;
import ga.euroblox.bananas_magic_zones.Portal;
import ga.euroblox.bananas_magic_zones.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class Pos1PortalCommand extends PortalCommand {
    public Pos1PortalCommand(BananasMagicZones plugin) {
        super(plugin, true);
    }

    @Override
    public boolean RunForPortals(CommandSender sender, List<Portal> portals, String[] args) {
        Vector pos;
        if (args.length == 0 && sender instanceof Player player) {
            pos = player.getLocation().toVector();
        } else if (args.length == 3) {
            pos = new Vector(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
        } else {
            return false;
        }
        for (Portal portal : portals)
            portal.SetPos1(pos);
        sender.sendMessage("Set pos1 of " + portals.size() + " Portal(s) to " + Utils.formatVector(pos));
        return true;
    }
}
