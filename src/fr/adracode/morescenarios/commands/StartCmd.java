package fr.adracode.morescenarios.commands;

import fr.adracode.morescenarios.MoreScenarios;
import fr.adracode.morescenarios.events.UHCStartEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Represent start of UHC
 *
 * @author adracode
 */
public class StartCmd implements CommandExecutor {

    public StartCmd(){
        MoreScenarios.getPlugin().getCommand("start").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        MoreScenarios.getPlugin().getServer().getPluginManager().callEvent(new UHCStartEvent());
        return true;
    }
}
