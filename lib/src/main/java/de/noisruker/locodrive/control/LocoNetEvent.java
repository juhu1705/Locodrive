package de.noisruker.locodrive;

import org.jetbrains.annotations.NotNull;

public class LocoNetEvent<T> extends de.noisruker.event.events.Event {

    private final T locoNetMessage;

    protected LocoNetEvent(@NotNull T locoNetMessage) {
        super("LocoNetEvent<" + locoNetMessage.getClass().getName() + ">");
        this.locoNetMessage = locoNetMessage;
    }

    public T getLocoNetMessage() {
        return locoNetMessage;
    }
    
}
