package fr.adracode.morescenarios.scenarios;

import fr.adracode.morescenarios.MoreScenarios;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;

/**
 * Scenario: FlowerPower
 * Description: When you break a flower, you get a random item
 * <p>
 * If you want to remove an item, remove it from the 'items' list
 *
 * @author adracode
 */
public class FlowerPower implements Scenario, Listener {

    private final ArrayList<Material> items = new ArrayList<>();
    private final Random random = new Random();
    private boolean active = false;

    public FlowerPower(){
        FileConfiguration config = MoreScenarios.getPlugin().getConfig();
        items.addAll(Arrays.asList(Material.values()));
        items.remove(Material.AIR);
        for(String material : config.getStringList("scenario.flowerpower.remove-items")){
            try {
                items.remove(Material.valueOf(material));
            } catch(IllegalArgumentException e){
                MoreScenarios.getPlugin().getLogger().log(Level.SEVERE, "Item '" + material + "' doesn't exists in scenario.flowerpower.remove-items config");
            }
        }
        items.remove(Material.BREWING_STAND);
        items.remove(Material.SKULL);
        items.remove(Material.CARROT);
        items.remove(Material.POTATO);
        items.remove(Material.ACACIA_DOOR);
        items.remove(Material.BIRCH_DOOR);
        items.remove(Material.DARK_OAK_DOOR);
        items.remove(Material.JUNGLE_DOOR);
        items.remove(Material.SPRUCE_DOOR);
        items.remove(Material.CAULDRON);
        items.remove(Material.FLOWER_POT);
        items.remove(Material.WOODEN_DOOR);
        items.remove(Material.IRON_DOOR_BLOCK);
        items.remove(Material.SNOW);
        items.remove(Material.SUGAR_CANE_BLOCK);
        items.remove(Material.BED_BLOCK);
        items.remove(Material.CAKE_BLOCK);
        items.remove(Material.POTION);
        items.remove(Material.ENCHANTED_BOOK);
        items.remove(Material.MONSTER_EGG);
        items.remove(Material.TRIPWIRE);
        items.remove(Material.REDSTONE_WIRE);
        items.remove(Material.STANDING_BANNER);
        items.remove(Material.WALL_BANNER);
        items.remove(Material.WALL_SIGN);
        items.remove(Material.SIGN_POST);
        items.remove(Material.MELON_STEM);
        items.remove(Material.PUMPKIN_STEM);
        items.remove(Material.WOOD_DOUBLE_STEP);
        items.remove(Material.CROPS);
        items.remove(Material.WATER);
        items.remove(Material.STATIONARY_WATER);
        items.remove(Material.LAVA);
        items.remove(Material.STATIONARY_LAVA);
        items.remove(Material.REDSTONE_TORCH_OFF);
        items.remove(Material.DIODE_BLOCK_ON);
        items.remove(Material.DIODE_BLOCK_OFF);
        items.remove(Material.REDSTONE_LAMP_ON);
        items.remove(Material.REDSTONE_COMPARATOR_ON);
        items.remove(Material.REDSTONE_COMPARATOR_OFF);
        items.remove(Material.DOUBLE_STEP);
        items.remove(Material.DOUBLE_STONE_SLAB2);
        items.remove(Material.GLOWING_REDSTONE_ORE);
        items.remove(Material.SOIL);
        items.remove(Material.BURNING_FURNACE);
        items.remove(Material.DAYLIGHT_DETECTOR_INVERTED);
        items.remove(Material.PORTAL);
        items.remove(Material.ENDER_PORTAL);
        items.remove(Material.COCOA);
        items.remove(Material.PISTON_EXTENSION);
        items.remove(Material.PISTON_MOVING_PIECE);
        items.remove(Material.NETHER_WARTS);
        items.remove(Material.FIRE);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e){
        if(e.getBlock().getType() == Material.YELLOW_FLOWER || e.getBlock().getType() == Material.RED_ROSE){
            Location loc = e.getBlock().getLocation().add(0.5D, 0.5D, 0.5D);
            e.getBlock().setType(Material.AIR);
            loc.getWorld().dropItem(loc, new ItemStack(items.get(this.random.nextInt(items.size()))));
            e.setCancelled(true);
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
