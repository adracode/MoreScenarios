package fr.adracode.morescenarios.scenarios;

import fr.adracode.morescenarios.MoreScenarios;
import fr.adracode.morescenarios.events.UHCStartEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Scenario: KillEffect
 * Description: You get a random effect on a kill
 * <p>
 * You can remove any effect from the 'effects' list
 * When a kill is performed, give an effect among 'effects' list
 *
 * @author adracode
 */
public class Kreinzinator implements Scenario, Listener {

    private boolean active = false;

    @EventHandler
    public void onStartUHC(UHCStartEvent e){
        ShapedRecipe kreinzinator = new ShapedRecipe(new ItemStack(Material.DIAMOND));
        kreinzinator.shape("RRR", "RRR", "RRR");
        kreinzinator.setIngredient('R', Material.REDSTONE_BLOCK);
        MoreScenarios.getPlugin().getServer().addRecipe(kreinzinator);
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
