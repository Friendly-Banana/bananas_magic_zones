package ga.euroblox.bananas_spawn;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;

public final class Portal {
    private static final DecimalFormat doubleFormat = new DecimalFormat("##.##");
    private static final String worldKey = ".worldName";
    private static final String nameKey = ".name";
    private static final String commandKey = ".command";
    private static final String visibleKey = ".visible";
    private static final String boxKey = ".box";
    private final String worldName;
    private final BoundingBox box;
    public String name;
    public String command;
    boolean visible;
    private Entity pos1Indicator;
    private Entity pos2Indicator;

    public Portal(String name, String command, String worldName, BoundingBox box, boolean visible) {
        this.name = name;
        this.command = command;
        this.worldName = worldName;
        this.box = box;
        this.visible = visible;
    }

    public static Portal Load(ConfigurationSection section, String key) {
        return new Portal(section.getString(key + nameKey), section.getString(key + commandKey), section.getString(key + worldKey, "world"), section.getObject(key + boxKey, BoundingBox.class), section.getBoolean(key + visibleKey, true));
    }

    public void Save(FileConfiguration config, String key) {
        config.set(key + nameKey, name);
        config.set(key + commandKey, command);
        config.set(key + visibleKey, visible);
        config.set(key + worldKey, worldName);
        config.set(key + boxKey, box);
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
        if (active && pos1Indicator == null) {
            assert world != null;
            pos1Indicator = world.spawnEntity(new Location(world, box.getMinX(), box.getMinY(), box.getMinZ()), EntityType.ARMOR_STAND);
            pos1Indicator.setGravity(false);
            pos1Indicator.setInvulnerable(true);
            pos1Indicator.customName(Component.text(name + " Pos1: " + box.getMin()));
            pos2Indicator = world.spawnEntity(new Location(world, box.getMaxX(), box.getMaxY(), box.getMaxZ()), EntityType.ARMOR_STAND);
            pos2Indicator.setGravity(false);
            pos2Indicator.setInvulnerable(true);
            pos2Indicator.customName(Component.text(name + "Pos2: " + box.getMax()));
        }
        if (!active && pos1Indicator != null) {
            pos1Indicator.remove();
            pos2Indicator.remove();
        }
    }

    @Override
    public String toString() {
        return "Portal{" +
                "name: " + name +
                ", command: <" + command +
                ">, visible: " + visible +
                ", worldName: " + worldName +
                ", pos1: " + formatVector(box.getMin()) +
                ", pos2: " + formatVector(box.getMax()) +
                '}';
    }

    private String formatVector(Vector vector) {
        return doubleFormat.format(vector.getX()) + ", " + doubleFormat.format(vector.getY()) + ", " + doubleFormat.format(vector.getZ());
    }
}
