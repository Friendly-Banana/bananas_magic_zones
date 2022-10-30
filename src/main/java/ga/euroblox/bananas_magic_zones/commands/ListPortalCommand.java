package ga.euroblox.bananas_magic_zones.commands;

import ga.euroblox.bananas_magic_zones.BananasMagicZones;
import ga.euroblox.bananas_magic_zones.Portal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;

public class ListPortalCommand extends PortalCommand {
    public ListPortalCommand(BananasMagicZones plugin) {
        super(plugin, false);
    }

    @Override
    public boolean NoPortalsGiven(CommandSender sender, String[] args) {
        sender.sendMessage(Component.text("List of Portals:").decoration(TextDecoration.BOLD, true).append(Component.newline()).append(Component.join(JoinConfiguration.separator(Component.newline()), plugin.portals.stream().map(Portal::Info).toList())));
        return true;
    }
}
