package ga.euroblox.bananas_spawn.commands;

import ga.euroblox.bananas_spawn.BananasSpawn;
import org.bukkit.command.CommandSender;

public class ListPortalCommand extends PortalCommand {
    public ListPortalCommand(BananasSpawn plugin) {
        super(plugin, false);
    }

    @Override
    public boolean NoPortalsGiven(CommandSender sender, String[] args) {
        String[] infos = new String[plugin.portals.size()];
        for (int i = 0; i < plugin.portals.size(); i++) {
            infos[i] = "%d. %s".formatted(i + 1, plugin.portals.get(i).toString());
        }
        sender.sendMessage("List of Portals:\n" + String.join("\n", infos));
        return true;
    }
}
