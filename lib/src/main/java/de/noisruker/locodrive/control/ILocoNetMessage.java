package de.noisruker.locodrive.control;

import de.noisruker.locodrive.args.LocoNetConnector;

public interface ILocoNetMessage {
    boolean send(LocoNetConnector connector);
}
