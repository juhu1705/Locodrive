package de.noisruker.locodrive.control;

import de.noisruker.locodrive.args.LocoNetConnector;

public interface LocoNetMessage {
    public boolean send(LocoNetConnector connector);
}
