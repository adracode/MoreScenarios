package fr.adracode.morescenarios.commands;

import fr.adracode.morescenarios.MoreScenarios;
import fr.adracode.morescenarios.events.FinishPregenerationEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Represent the end of pregeneration
 *
 * @author adracode
 */
public class PregenCmd implements CommandExecutor {

    public PregenCmd(){
        MoreScenarios.getPlugin().getCommand("pregen").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings){
        MoreScenarios.getPlugin().getServer().getPluginManager().callEvent(new FinishPregenerationEvent());
        return true;
    }
}
