package me.majsky.Home;

import java.util.HashMap;
import java.util.Map;

import me.majsky.lib.DataSaver;
import me.majsky.lib.LocationConverter;
import me.majsky.lib.util.ChatUtils;
import me.majsky.lib.util.ParticleUtils;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HomePlugin extends JavaPlugin{

    public Map<String, String> homes;
    
    @Override
    public void onDisable() {
        DataSaver.saveReadable(homes, "homes", this);
    }
    
    @Override
    public void onEnable() {
        if(DataSaver.isPresent("homes", this))
            homes = DataSaver.loadReadable("homes", this);
        else
            homes = new HashMap<String, String>();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("You must be in-game!");
            return false;
        }
        
        Player p = (Player)sender;
        
        switch (command.getName().toLowerCase()) {
            case "sethome":
                ChatUtils.sendMessaegeAndSound(p, ChatColor.GOLD + "Home nadstaveny!", Sound.CLICK);
                homes.put(p.getName(), LocationConverter.encode(p.getLocation()));
                return true;
            case "home":
                if(!homes.containsKey(p.getName())){
                    ChatUtils.sendMessaegeAndSound(p, ChatColor.GOLD + "Si bezdomovec!", Sound.CLICK);
                    return false;
                }
                ParticleUtils.createParticlesWithSound(p.getWorld(), p.getLocation(), Effect.SMOKE, 0, Sound.ENDERMAN_TELEPORT, 1, 3);
                p.teleport(LocationConverter.decode(homes.get(p.getName())));
                ParticleUtils.createParticlesWithSound(p.getWorld(), p.getLocation(), Effect.SMOKE, 0, Sound.ENDERMAN_TELEPORT, 1, 3);
                return true;
            case "spawn":
                ParticleUtils.createParticlesWithSound(p.getWorld(), p.getLocation(), Effect.SMOKE, 0, Sound.ENDERMAN_TELEPORT, 1, 3);
                p.teleport(p.getWorld().getSpawnLocation());
                ParticleUtils.createParticlesWithSound(p.getWorld(), p.getLocation(), Effect.SMOKE, 0, Sound.ENDERMAN_TELEPORT, 1, 3);
                return true;
        }
        return false;
    }

}
