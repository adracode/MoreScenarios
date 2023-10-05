package fr.adracode.morescenarios.scenarios;

import fr.adracode.morescenarios.MoreScenarios;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Scenario: NoEnchant
 * Description: You can't craft or use enchanting table
 *
 * @author adracode
 */
public class NoEnchant implements Scenario, Listener {

    private boolean active = false;

    @EventHandler
    public void onEnchantItem(PrepareItemEnchantEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onCraftEnchantTable(PrepareItemCraftEvent e){
        if(e.getRecipe().getResult().getType() == Material.ENCHANTMENT_TABLE){
            e.getInventory().setResult(new ItemStack(Material.AIR));
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
