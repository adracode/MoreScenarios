package fr.adracode.morescenarios.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Call when the pregeneration is finish
 *
 * @author adracode
 */
public class FinishPregenerationEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;

    public static HandlerList getHandlerList(){
        return handlers;
    }

    public HandlerList getHandlers(){
        return handlers;
    }

    @Override
    public boolean isCancelled(){
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel){
        this.cancel = cancel;
    }
}
