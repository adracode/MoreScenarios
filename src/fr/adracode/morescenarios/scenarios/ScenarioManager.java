package fr.adracode.morescenarios.scenarios;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Manage scenarios
 *
 * @author adracode
 */
public class ScenarioManager {

    private final Hashtable<String, Scenario> scenarios = new Hashtable<>();

    public ScenarioManager(){
        scenarios.put("bloodenchant", new BloodEnchant());
        scenarios.put("flatland", new FlatLand());
        scenarios.put("flowerpower", new FlowerPower());
        scenarios.put("goldenretriever", new GoldenRetriever());
        scenarios.put("killeffect", new KillEffect());
        scenarios.put("kreinzinator", new Kreinzinator());
        scenarios.put("lavaless", new LavaLess());
        scenarios.put("noenchant", new NoEnchant());
        scenarios.put("permakill", new Permakill());
    }

    public Scenario getScenario(String scenario){
        return scenarios.get(scenario);
    }

    public List<String> getScenarioAvailable(){
        return new ArrayList<>(scenarios.keySet());
    }
}
