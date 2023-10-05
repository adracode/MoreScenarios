package fr.adracode.morescenarios.scenarios;

import fr.adracode.morescenarios.MoreScenarios;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Scenario: BloodEnchant
 * Description: You take one half heart of damage when you enchant an item
 * <p>
 * If player dies because of enchantment, you can choose a death message
 *
 * @author adracode
 */
public class BloodEnchant implements Scenario, Listener {

    private final ArrayList<UUID> willDie = new ArrayList<>();
    private boolean active = false;

    @EventHandler
    public void onEnchantItem(EnchantItemEvent e){
        Player player = e.getEnchanter();
        if(player.getHealth() <= 1){
            willDie.add(player.getUniqueId());
        }
        player.damage(1);
        Bukkit.getScheduler().scheduleSyncDelayedTask(MoreScenarios.getPlugin(), () -> willDie.remove(player.getUniqueId()), 2L);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(PlayerDeathEvent e){
        if(willDie.contains(e.getEntity().getUniqueId())){
            e.setDeathMessage(e.getEntity().getName() + " " + MoreScenarios.getPlugin().getConfig().getString("scenario.bloodenchant.death", "scenario.bloodenchant.death").replaceAll("&", "§"));
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
