package fr.adracode.morescenarios.scenarios;

import fr.adracode.morescenarios.MoreScenarios;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;

/**
 * Scenario: GoldenRetriever
 * Description: When a player dies, a golden head is dropped
 * <p>
 * You can change the lore or name as you want
 *
 * @author adracode
 */
public class GoldenRetriever implements Scenario, Listener {

    private final String loreGhead;
    private final ItemStack goldenHead = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 0);
    private boolean active = false;

    public GoldenRetriever(){
        ItemMeta goldenHead = this.goldenHead.getItemMeta();
        FileConfiguration config = MoreScenarios.getPlugin().getConfig();
        goldenHead.setDisplayName(config.getString("scenario.goldenretriever.displayname", "§bGolden Head").replaceAll("&", "§"));
        loreGhead = config.getString("scenario.goldenretriever.lore", "§c❤❤❤❤").replaceAll("&", "§");
        goldenHead.setLore(Collections.singletonList(loreGhead));
        this.goldenHead.setItemMeta(goldenHead);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Location loc = e.getEntity().getLocation();
        loc.getWorld().dropItem(loc, goldenHead);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onConsumeGhead(PlayerItemConsumeEvent e){
        if(isItemGoldenHead(e.getItem())){
            Player player = e.getPlayer();
            player.removePotionEffect(PotionEffectType.REGENERATION);
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 1));
        }
    }

    private boolean isItemGoldenHead(ItemStack item){
        if(item.getType() == Material.GOLDEN_APPLE){
            return item.hasItemMeta() && item.getItemMeta().getLore().get(0).equals(this.loreGhead);
        }
        return false;
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
