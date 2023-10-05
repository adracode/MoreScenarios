package fr.adracode.morescenarios.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Call when the UHC starts
 *
 * @author adracode
 */
public class UHCStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList(){
        return handlers;
    }

    public HandlerList getHandlers(){
        return handlers;
    }


}
