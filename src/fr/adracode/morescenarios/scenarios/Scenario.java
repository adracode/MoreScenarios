package fr.adracode.morescenarios.scenarios;

public interface Scenario {

    boolean isActive();

    void setScenario(boolean status);

    void enable();

    void disable();

}
