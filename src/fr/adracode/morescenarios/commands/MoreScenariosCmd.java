package fr.adracode.morescenarios.commands;

import fr.adracode.morescenarios.MoreScenarios;
import fr.adracode.morescenarios.scenarios.Scenario;
import fr.adracode.morescenarios.scenarios.ScenarioManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Command for manage scenario
 *
 * @author adracode
 */
public class MoreScenariosCmd implements CommandExecutor, TabCompleter {

    public MoreScenariosCmd(){
        MoreScenarios.getPlugin().getCommand("morescenarios").setExecutor(this);
        MoreScenarios.getPlugin().getCommand("morescenarios").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        FileConfiguration config = MoreScenarios.getPlugin().getConfig();
        if(args.length == 0){
            sender.sendMessage(config.getString("command.scenarios.error", "command.scenarios.error").replaceAll("&", "§"));
            return true;
        }
        ScenarioManager manager = MoreScenarios.getPlugin().getScenarioManager();
        if(!manager.getScenarioAvailable().contains(args[0])){
            sender.sendMessage(config.getString("command.scenarios.noexist", "command.scenarios.noexist").replaceAll("&", "§"));
            return true;
        }
        Scenario scenario = manager.getScenario(args[0]);
        scenario.setScenario(!scenario.isActive());
        sender.sendMessage((scenario.isActive() ? config.getString("command.scenarios.activated", "command.scenarios.activated").replaceAll("&", "§") : config.getString("command.scenarios.deactivated", "command.scenarios.deactivated").replaceAll("&", "§")));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
        List<String> scenarios = MoreScenarios.getPlugin().getScenarioManager().getScenarioAvailable();
        if(args.length == 0){
            return scenarios;
        }
        if(args.length == 1){
            List<String> someSc = new ArrayList<>();
            for(String str : scenarios){
                if(str.startsWith(args[0])){
                    someSc.add(str);
                }
            }
            return someSc;
        }
        return null;
    }
}
