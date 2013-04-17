package me.majsky.Home;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.World;

public class SavableLocation implements Serializable {
    private static final long serialVersionUID = 6790423460886630025L;
    
    private final double x, y, z;
    private final float pitch, yaw;
    private final World world;
    
    
    public SavableLocation(double x, double y, double z, float pitch, float yaw, World w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.world = w;
    }
    
    public SavableLocation(Location loc){
        this(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw(), loc.getWorld());
    }
    
    public Location getLocation(){
        return new Location(world, x, y, z, yaw, pitch);
    }
    
}
