package ga.euroblox.bananas_magic_zones;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Portal {
    private static final String idKey = ".id";
    private static final String nameKey = ".name";
    private static final String worldKey = ".worldName";
    private static final String commandKey = ".command";
    private static final String visibleKey = ".visible";
    private static final String boxKey = ".box";
    public static int nextId = 0;
    public final int id;
    private final String worldName;
    private final BoundingBox box;
    // the players we already performed the command for
    private final Set<UUID> enteredPlayers = new HashSet<>();
    public String name;
    public String command;
    boolean visible;
    private Entity pos1Indicator;
    private Entity pos2Indicator;

    public Portal(String name, String command, String worldName, BoundingBox box, boolean visible) {
        this(nextId, name, command, worldName, box, visible);
        nextId++;
    }

    public Portal(int id, String name, String command, String worldName, BoundingBox box, boolean visible) {
        this.id = id;
        this.name = name;
        this.command = command;
        this.worldName = worldName;
        this.box = box;
        this.visible = visible;
    }

    public static Portal Load(ConfigurationSection section, String key) {
        return new Portal(section.getInt(key + idKey), section.getString(key + nameKey), section.getString(key + commandKey), section.getString(key + worldKey, "world"), section.getObject(key + boxKey, BoundingBox.class), section.getBoolean(key + visibleKey, true));
    }

    public void Save(ConfigurationSection section, int key) {
        section.set(key + idKey, id);
        section.set(key + nameKey, name);
        section.set(key + commandKey, command);
        section.set(key + visibleKey, visible);
        section.set(key + worldKey, worldName);
        section.set(key + boxKey, box);
    }

    public boolean TryExecute(Player player, Location location) {
        if (Contains(location)) {
            // not already in portal
            if (enteredPlayers.add(player.getUniqueId())) {
                player.performCommand(command);
                return true;
            }
        } else enteredPlayers.remove(player.getUniqueId());
        return false;
    }

    private boolean Contains(Location location) {
        return location.getWorld().getName().equals(worldName) && box.contains(location.toVector());
    }

    public void SetPos1(Vector pos) {
        box.resize(pos.getX(), pos.getY(), pos.getZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ());
        if (visible && pos1Indicator != null) {
            pos1Indicator.teleport(pos.toLocation(pos1Indicator.getWorld()));
        }
    }

    public void SetPos2(Vector pos) {
        box.resize(box.getMinX(), box.getMinY(), box.getMinZ(), pos.getX(), pos.getY(), pos.getZ());
        if (visible && pos2Indicator != null) {
            pos2Indicator.teleport(pos.toLocation(pos2Indicator.getWorld()));
        }
    }

    public void SetIndicators(boolean active, Server server) {
        SetIndicators(active, server.getWorld(worldName));
    }

    public void SetIndicators(boolean active, World world) {
        visible = active;
        if (active && pos1Indicator == null) {
            assert world != null;
            pos1Indicator = SpawnIndicator(world, box.getMin(), "Pos1");
            pos2Indicator = SpawnIndicator(world, box.getMax(), "Pos2");
        }
        if (!active && pos1Indicator != null) {
            RemoveIndicators();
        }
    }

    private Entity SpawnIndicator(World world, Vector pos, String namePrefix) {
        Entity entity = world.spawnEntity(new Location(world, pos.getX(), pos.getY(), pos.getZ()), EntityType.ARMOR_STAND);
        entity.setGravity(false);
        entity.setInvulnerable(true);
        entity.customName(Component.text(namePrefix + ": " + name));
        entity.setCustomNameVisible(true);
        return entity;
    }

    public void RemoveIndicators() {
        pos1Indicator.remove();
        pos1Indicator = null;
        pos2Indicator.remove();
        pos2Indicator = null;
    }

    @Override
    public String toString() {
        return name + "  id: " + id + ", command: <" + command + ">, visible: " + visible + ", dimension: " + worldName + ", pos1: " + Utils.formatVector(box.getMin()) + ", pos2: " + Utils.formatVector(box.getMax());
    }

    public Component Info() {
        return Utils.SpaceJoin(Component.text(name).color(Utils.GOLD).decoration(TextDecoration.BOLD, true), Component.text("id: " + id).decoration(TextDecoration.BOLD, false), Component.text("command: ").append(Utils.Command(command, command)), "visible: " + visible, "dimension: " + worldName, "pos1: " + Utils.Command(Utils.formatVector(box.getMin()), "/tp @s " + Utils.formatVector(box.getMin())), "pos2: " + Utils.Command(Utils.formatVector(box.getMax()), "/tp @s " + Utils.formatVector(box.getMax())));
    }
}
