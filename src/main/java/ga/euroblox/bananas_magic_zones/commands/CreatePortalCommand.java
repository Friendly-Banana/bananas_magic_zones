package ga.euroblox.bananas_magic_zones.commands;

import ga.euroblox.bananas_magic_zones.BananasMagicZones;
import ga.euroblox.bananas_magic_zones.Portal;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreatePortalCommand extends PortalCommand {
    public CreatePortalCommand(BananasMagicZones plugin) {
        super(plugin, false);
    }

    @Override
    public boolean NoPortalsGiven(CommandSender sender, String[] args) {
        if (sender instanceof Player player) {
            String name = args.length > 0 ? args[0] : player.getName() + player.getWorld().getFullTime();
            String cmd = args.length > 1 ? args[1] : "msg @s Use /command to set the command of this portal.";
            Portal portal = new Portal(name, cmd, player.getWorld().getName(), player.getBoundingBox(), true);
            plugin.AddPortal(portal);
            sender.sendMessage("Created new portal " + portal.name);
            player.performCommand("select " + portal.name);
        }
        return true;
    }
}
