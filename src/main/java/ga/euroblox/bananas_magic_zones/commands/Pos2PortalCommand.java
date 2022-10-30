package ga.euroblox.bananas_magic_zones.commands;

import ga.euroblox.bananas_magic_zones.BananasMagicZones;
import ga.euroblox.bananas_magic_zones.Portal;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Pos2PortalCommand extends PortalCommand {
    public Pos2PortalCommand(BananasMagicZones plugin) {
        super(plugin, true);
    }

    @Override
    public boolean RunForPortals(CommandSender sender, List<Portal> portals, String[] args) {
        if (args.length != 3) {
            return false;
        }
        double[] pos = new double[3];
        for (int i = 0; i < 3; i++) {
            pos[i] = Double.parseDouble(args[i]);
        }
        for (Portal portal : portals)
            portal.SetPos2(pos);
        sender.sendMessage("Set pos2 of " + portals.size() + " Portal(s) to " + pos[0] + ", " + pos[1] + ", " + pos[2]);
        return true;
    }
}
