package fr.adracode.morescenarios;

import fr.adracode.morescenarios.commands.MoreScenariosCmd;
import fr.adracode.morescenarios.commands.PregenCmd;
import fr.adracode.morescenarios.commands.StartCmd;
import fr.adracode.morescenarios.scenarios.ScenarioManager;
import fr.adracode.morescenarios.utils.PacketUtils;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Main class
 *
 * @author adracode
 */
public class MoreScenarios extends JavaPlugin {

    private static MoreScenarios plugin;
    private ScenarioManager scenarioManager;
    private World world;

    public static MoreScenarios getPlugin(){
        return plugin;
    }

    @Override
    public void onEnable(){
        if(plugin == null){
            plugin = this;
        }
        saveDefaultConfig();
        new MoreScenariosCmd();
        new StartCmd();
        new PregenCmd();
        scenarioManager = new ScenarioManager();
        world = getServer().getWorld(getConfig().getString("morescenarios.world-name", "world"));
        if(world == null){
            getLogger().log(Level.SEVERE, "World not found ! Please set your name's world in config");
        }
    }

    @Override
    public void onDisable(){
    }

    public ScenarioManager getScenarioManager(){
        return scenarioManager;
    }

    public void sendAllMessageBar(String message){
        for(Player pl : Bukkit.getOnlinePlayers()){
            ((CraftPlayer) pl).getHandle().playerConnection.sendPacket(PacketUtils.packetActionBar(message));
        }
    }

    public void sendAllTitle(String title, String subTitle){
        PlayerConnection playerConnection;
        for(Player pl : Bukkit.getOnlinePlayers()){
            playerConnection = ((CraftPlayer) pl).getHandle().playerConnection;
            playerConnection.sendPacket(PacketUtils.packetTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, title, 10, 40, 10));
            playerConnection.sendPacket(PacketUtils.packetTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitle, 10, 40, 10));
        }
    }

    public World getWorld(){
        return world;
    }
}
