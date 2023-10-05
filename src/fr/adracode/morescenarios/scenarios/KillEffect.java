package fr.adracode.morescenarios.scenarios;

import fr.adracode.morescenarios.MoreScenarios;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;

/**
 * Scenario: KillEffect
 * Description: You get a random effect on a kill
 * <p>
 * You can remove or add any effect from the 'effects' list
 * When a kill is performed, give an effect among 'effects' list
 *
 * @author adracode
 */
public class KillEffect implements Scenario, Listener {

    private final ArrayList<PotionEffectType> effects = new ArrayList<>();
    private final Random random = new Random();
    private boolean active = false;

    public KillEffect(){
        FileConfiguration config = MoreScenarios.getPlugin().getConfig();
        for(String potionEffect : config.getStringList("scenario.killeffect.effects")){
            if(PotionEffectType.getByName(potionEffect) == null){
                MoreScenarios.getPlugin().getLogger().log(Level.CONFIG, "Effect '" + potionEffect + "' doesn't exist in scenario.killeffect.effects config");
            } else {
                effects.add(PotionEffectType.getByName(potionEffect));
            }
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e){
        Player player = e.getEntity();
        if(player == null || player.getKiller() == null){
            return;
        }
        Player killer = player.getKiller();
        PotionEffectType random = effects.get(this.random.nextInt(effects.size()));
        FileConfiguration config = MoreScenarios.getPlugin().getConfig();
        if(killer.hasPotionEffect(random)){
            int levelMax = config.getInt("scenario.killeffect.max-level", 2);
            int level = getPotionLevel(killer, random);
            if(level == -1){
                MoreScenarios.getPlugin().getLogger().log(Level.SEVERE, "Level potion not found in scenario KillerEffect");
                return;
            }
            killer.addPotionEffect(new PotionEffect(random, config.getInt("scenario.killeffect.duration", 180) * 20, Integer.min(level + 1, levelMax), config.getBoolean("scenario.killeffect.show-particles", false)), true);
        } else {
            killer.addPotionEffect(new PotionEffect((random), config.getInt("scenario.killeffect.duration", 180) * 20, 0, false, config.getBoolean("scenario.killeffect.show-particles", false)));
        }
    }

    private int getPotionLevel(Player player, PotionEffectType type){
        int level = -1;
        Iterator<PotionEffect> iterator = player.getActivePotionEffects().iterator();
        while(iterator.hasNext() && level == -1){
            PotionEffect pe = iterator.next();
            if(pe.getType() == type){
                level = pe.getAmplifier();
            }
        }
        return level;
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
