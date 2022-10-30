package ga.euroblox.bananas_magic_zones;

import ga.euroblox.bananas_magic_zones.commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class BananasMagicZones extends JavaPlugin {
    private static final String PORTALS_KEY = "portals";
    public List<Portal> portals = new ArrayList<>();
    public Set<UUID> portalSeer = new HashSet<>();

    public void AddPortal(Portal portal) {
        portals.add(portal);
        portal.SetIndicators(true, getServer());
    }

    public void RemovePortal(Portal portal) {
        portals.remove(portal);
        portal.SetIndicators(false, getServer());
    }

    public List<Portal> PortalsWithName(String name) {
        try {
            int id = Integer.parseInt(name);
            if (id >= 0) {
                List<Portal> p = portals.stream().filter(portal -> portal.id == id).toList();
                if (!p.isEmpty())
                    return p;
            }
        } catch (NumberFormatException ignored) {
        }
        return portals.stream().filter(portal -> portal.name.equals(name)).toList();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
        getCommand("lobby").setExecutor(new LobbyCommand());
        getCommand("create").setExecutor(new CreatePortalCommand(this));
        getCommand("remove").setExecutor(new RemovePortalCommand(this));
        getCommand("remove").setTabCompleter(this::TabCompletePortals);
        getCommand("select").setExecutor(new SelectPortalCommand(this));
        getCommand("select").setTabCompleter(this::TabCompletePortals);
        getCommand("rename").setExecutor(new RenamePortalCommand(this));
        getCommand("command").setExecutor(new SetPortalCommand(this));
        getCommand("pos1").setExecutor(new Pos1PortalCommand(this));
        getCommand("pos2").setExecutor(new Pos2PortalCommand(this));
        getCommand("show").setExecutor(new ShowPortalCommand(this));
        getCommand("show").setTabCompleter(this::TabCompletePortals);
        getCommand("hide").setExecutor(new HidePortalCommand(this));
        getCommand("hide").setTabCompleter(this::TabCompletePortals);
        getCommand("list").setExecutor(new ListPortalCommand(this));

        ConfigurationSection section = getConfig().getConfigurationSection(PORTALS_KEY);
        if (section == null)
            section = getConfig().createSection(PORTALS_KEY);
        for (String key : section.getKeys(false))
            portals.add(Portal.Load(section, key));
        Portal.nextId = portals.size();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ConfigurationSection section = getConfig().getConfigurationSection(PORTALS_KEY);
        if (section == null)
            section = getConfig().createSection(PORTALS_KEY);
        for (int i = 0; i < portals.size(); i++) {
            portals.get(i).RemoveIndicators();
            portals.get(i).Save(section, i);
        }
        saveConfig();
    }

    private List<String> TabCompletePortals(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return portals.stream().filter(portal -> portal.name.startsWith(args[0])).map(portal -> portal.name).toList();
    }
}
