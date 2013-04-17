package me.majsky.Home;

import java.util.HashMap;
import java.util.Map;

import me.majsky.lib.ChatUtils;
import me.majsky.lib.DataSaver;
import me.majsky.lib.ParticleUtils;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HomePlugin extends JavaPlugin{

    public Map<String, SavableLocation> homes;
    
    @Override
    public void onDisable() {
        DataSaver.save(homes, "homes", this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onEnable() {
        if(DataSaver.isPresent("homes", this))
            homes = (Map<String, SavableLocation>) DataSaver.load("homes", this);
        else
            homes = new HashMap<String, SavableLocation>();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;
        
        Player p = (Player)sender;
        
        switch (command.getName().toLowerCase()) {
            case "sethome":
                ChatUtils.sendMessaegeAndSound(p, ChatColor.GOLD + "Home nadstaveny!", Sound.CLICK);
                homes.put(p.getName(), new SavableLocation(p.getLocation()));
                return true;
            case "home":
                if(!homes.containsKey(p.getName())){
                    ChatUtils.sendMessaegeAndSound(p, ChatColor.GOLD + "Si bezdomovec!", Sound.CLICK);
                    return false;
                }
                ParticleUtils.createParticlesWithSound(p.getWorld(), p.getLocation(), Effect.SMOKE, 0, Sound.ENDERMAN_TELEPORT, 1, 3);
                p.teleport(homes.get(p.getName()).getLocation());
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
