package fr.adracode.morescenarios.scenarios;

import fr.adracode.morescenarios.MoreScenarios;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Scenario: KillEffect
 * Description: You get a random effect on a kill
 * <p>
 * You can choose when a day starts and end. 0 = start of a minecraft day, 12000 = start of minecraft night
 * You can choose the time you want to set, with setDay and setNight. 6000 = midday, 18000 = midnight
 *
 * @author adracode
 */
public class Permakill implements Scenario, Listener {

    private boolean active = false;

    private int startDay;
    private int endDay;
    private int setDay;
    private int setNight;

    public Permakill(){
        FileConfiguration config = MoreScenarios.getPlugin().getConfig();
        startDay = config.getInt("scenario.permakill.start-day", 0);
        endDay = config.getInt("scenario.permakill.end-day", 12000);
        setDay = config.getInt("scenario.permakill.set-day", 6000);
        setNight = config.getInt("scenario.permakill.set-night", 18000);
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e){
        World world = MoreScenarios.getPlugin().getWorld();
        if(world.getTime() >= startDay && world.getTime() < endDay){
            world.setTime((long) setNight);
        } else {
            world.setTime((long) setDay);
        }
    }

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

    @Override
    public void enable(){
        active = true;
        MoreScenarios.getPlugin().getServer().getPluginManager().registerEvents(this, MoreScenarios.getPlugin());
    }

    @Override
    public void disable(){
        active = false;
        HandlerList.unregisterAll(this);
    }
}
