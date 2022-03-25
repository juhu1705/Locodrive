package de.noisruker.locodrive.control;

import de.noisruker.event.events.Event;
import de.noisruker.locodrive.args.MessageParseError;

public class LocoNetErrorEvent extends Event<Void> {

    private final MessageParseError error;

    protected LocoNetErrorEvent(MessageParseError error) {
        super("loconeterror");
        this.error = error;
    }

    /**
     *
     * @return The rust connection error to handle.
     */
    public MessageParseError getError() {
        return this.error;
    }

}
