package fr.adracode.morescenarios.scenarios;

import fr.adracode.morescenarios.MoreScenarios;
import fr.adracode.morescenarios.events.FinishPregenerationEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Scenario: LavaLess
 * Description: Clear the map of all lava
 * <p>
 * First, replace all lava blocks by glass
 * (you can change the block but take one who are transparent to avoid light calculs)
 * Then, replace all glass blocks by air
 * Because lava are removed chunks by chunks,
 * it can flow out and create lags or not remove by itself if chunks are not loaded
 * <p>
 * Warning: Can take a long time to perform
 * <p>
 * !!! YOU MUST KEEP ALL CHUNKS LOADED BEFORE AND DURING THIS PROCESS !!!
 *
 * @author adracode
 */
public class LavaLess implements Scenario, Listener {

    private final List<Location> locations = new ArrayList<>();
    private final MoreScenarios plugin;
    private boolean active = false;
    private int totalLocations;
    private int id;
    private int startTime;
    private boolean isFinish = false;
    private int totalBlocks = 0;
    private int changedBlocks = 0;
    //These variable are used here to avoid lot of allocation while running the run method
    private Chunk chunkTemp;
    private Location tempLoc;
    private Block block;
    private int state = 0;

    public LavaLess(){
        plugin = MoreScenarios.getPlugin();
    }

    private void run(){
        long start = System.currentTimeMillis();
        //You can increase until 49L or decrease until 1L according to your TPS. For stable TPS (~19+), set to 20L-25L but can be slower
        while(System.currentTimeMillis() - start < 40L){
            if(locations.isEmpty()){
                Bukkit.getScheduler().cancelTask(id);
                if(state == 0){
                    state = 1;
                    Bukkit.broadcastMessage("§8[§cUHC§8] §7Analyse of §c" + totalBlocks + "§7 blocks in §c" + this.getTimeTaken() + " §7seconds for §c" + changedBlocks + "§7 replaced blocks of lava");
                    changedBlocks = 0;
                    totalBlocks = 0;
                    plugin.getServer().getPluginManager().callEvent(new FinishPregenerationEvent());
                    return;
                }
                Bukkit.broadcastMessage("§8[§cUHC§8] §7Analyse of §c" + totalBlocks + "§7 blocks in §c" + this.getTimeTaken() + " §7seconds for §c" + changedBlocks + "§7 removed blocks of glass");
                isFinish = true;
                plugin.getServer().getPluginManager().callEvent(new FinishPregenerationEvent());
                return;
            }
            tempLoc = locations.get(0);
            chunkTemp = tempLoc.getWorld().getChunkAt(tempLoc.getBlockX(), tempLoc.getBlockZ());
            locations.remove(0);
            if(this.state == 0){
                for(int x = 0; x < 16; x++){
                    for(int z = 0; z < 16; z++){
                        for(int y = 0; y <= 128; y++){
                            block = chunkTemp.getBlock(x, y, z);
                            this.incrementTotalBlock();
                            if(block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA){
                                //If you change, take a block that doesn't generate naturally
                                block.setType(Material.STAINED_GLASS_PANE);
                                this.incrementChangedBlock();
                            }
                        }
                    }
                }
                plugin.sendAllMessageBar("§aRemoving: §6" + (int) ((((float) this.totalLocations - (float) locations.size()) / (float) this.totalLocations) * 100) + "%");
            } else {
                for(int x = 0; x < 16; x++){
                    for(int z = 0; z < 16; z++){
                        for(int y = 0; y <= 128; y++){
                            block = chunkTemp.getBlock(x, y, z);
                            this.incrementTotalBlock();
                            if(block.getType() == Material.STAINED_GLASS_PANE){
                                block.setType(Material.AIR);
                                this.incrementChangedBlock();
                            }
                        }
                    }
                }
                plugin.sendAllMessageBar("§aFinishing: §6" + (int) ((((float) this.totalLocations - (float) locations.size()) / (float) this.totalLocations) * 100) + "%");
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onFinishPregeneration(FinishPregenerationEvent e){
        if(isFinish){
            return;
        }
        e.setCancelled(true);
        int sizeOfMap = 1000; //Represent a -1000/+1000 map
        World world = plugin.getServer().getWorld("world");
        int max = sizeOfMap / 16 + Bukkit.getViewDistance();
        for(int x = -max; x <= max; x++){
            for(int z = -max; z <= max; z++){
                locations.add(new Location(world, x, 0.0D, z));
            }
        }
        if(state == 0){
            plugin.sendAllTitle("§0", "§aRemoving lava...");
        } else {
            plugin.sendAllTitle("§0", "§aremoving glass...");
        }
        totalLocations = locations.size();
        startTime = (int) System.currentTimeMillis();
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::run, 1L, 1L);
    }

    public String getTimeTaken(){
        return String.valueOf(((int) System.currentTimeMillis() - this.startTime) / 1000);
    }

    public void incrementTotalBlock(){
        this.totalBlocks++;
    }

    public void incrementChangedBlock(){
        this.changedBlocks++;
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
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void disable(){
        active = false;
        HandlerList.unregisterAll(this);
    }

}
