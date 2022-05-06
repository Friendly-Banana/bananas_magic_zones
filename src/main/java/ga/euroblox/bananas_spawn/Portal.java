package ga.euroblox.bananas_spawn;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;

public final class Portal {
    private static final DecimalFormat doubleFormat = new DecimalFormat("##.##");
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

    public boolean Contains(Location location) {
        if (location.getWorld().getName().equals(worldName))
            return box.contains(location.toVector());
        return false;
    }

    public void SetPos1(double[] pos) {
        box.resize(pos[0], pos[1], pos[2], box.getMaxX(), box.getMaxY(), box.getMaxZ());
        if (visible) {
            Location location = pos1Indicator.getLocation();
            location.set(pos[0], pos[1], pos[2]);
            pos1Indicator.teleport(location);
        }
    }

    public void SetPos2(double[] pos) {
        box.resize(box.getMinX(), box.getMinY(), box.getMinZ(), pos[0], pos[1], pos[2]);
        if (visible) {
            Location location = pos1Indicator.getLocation();
            location.set(pos[0], pos[1], pos[2]);
            pos2Indicator.teleport(location);
        }
    }

    public void SetIndicators(boolean active, Server server) {
        SetIndicators(active, server.getWorld(worldName));
    }

    public void SetIndicators(boolean active, World world) {
        visible = active;
        System.out.println(pos1Indicator);
        System.out.println(pos1Indicator == null ? null : pos1Indicator.isValid());
        if (active && pos1Indicator == null) {
            assert world != null;
            pos1Indicator = SpawnIndicator(world, box.getMin(), "Pos1");
            pos2Indicator = SpawnIndicator(world, box.getMax(), "Pos2");
        }
        if (!active && pos1Indicator != null) {
            RemoveIndicators();
        }
    }

    private Entity SpawnIndicator(World world, Vector pos, String nameSuffix) {
        Entity entity = world.spawnEntity(new Location(world, pos.getX(), pos.getY(), pos.getZ()), EntityType.ARMOR_STAND);
        entity.setGravity(false);
        entity.setInvulnerable(true);
        entity.customName(Component.text(name + " " + nameSuffix));
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
        return name + "  id: " + id +
                ", command: <" + command +
                ">, visible: " + visible +
                ", worldName: " + worldName +
                ", pos1: " + formatVector(box.getMin()) +
                ", pos2: " + formatVector(box.getMax());
    }

    private String formatVector(Vector vector) {
        return doubleFormat.format(vector.getX()) + ", " + doubleFormat.format(vector.getY()) + ", " + doubleFormat.format(vector.getZ());
    }
}
