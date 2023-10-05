package fr.adracode.morescenarios.scenarios;


import fr.adracode.morescenarios.MoreScenarios;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Scenario: FlatLand
 * Description: The map is Flat, you can find stuff in villages
 * <p>
 * You must adapt it to your UHC Plugin,
 * otherwise, it won't work correctly, this is a template of how to use it
 *
 * @author adracode
 */
public class FlatLand implements Scenario, Listener {

    private boolean active = false;
    private Location flatland;

    @Override
    public boolean isActive(){
        return active;
    }

    @Override
    public void setScenario(boolean status){
        if(status){
            enable();
        } else {
            disable();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().teleport(flatland);
    }

    @Override
    public void enable(){
        active = true;
        MoreScenarios.getPlugin().getServer().getPluginManager().registerEvents(this, MoreScenarios.getPlugin());
        MoreScenarios.getPlugin().getServer().createWorld(new WorldCreator("flat")
                .environment(World.Environment.NORMAL)
                .type(WorldType.FLAT)
                .generatorSettings("3;minecraft:bedrock,2*minecraft:dirt,minecraft:grass;1;village(size=3 distance=9)"));
        flatland = new Location(MoreScenarios.getPlugin().getServer().getWorld("flat"), 0.5D, 6D, 0.5D);
        for(Player pl : Bukkit.getOnlinePlayers()){
            pl.teleport(flatland);
        }
    }

    @Override
    public void disable(){
        active = false;
    }
}

