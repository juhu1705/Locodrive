package de.noisruker.locodrive.control;

import de.noisruker.event.events.Event;
import de.noisruker.locodrive.args.*;
import org.jetbrains.annotations.NotNull;

public class LocoRespondEvent<T extends ILocoNetMessage, R extends ILocoNetMessage> extends Event<R> {

    private final T locoNetMessage;
    private final LongAck response;

    public LocoRespondEvent(@NotNull LongAck response, @NotNull T locoNetMessage) {
        super("LocoNetEvent<" + locoNetMessage.getClass().getName() + ">");
        this.locoNetMessage = locoNetMessage;
        this.response = response;
    }

    public @NotNull T getLocoNetMessage() {
        return this.locoNetMessage;
    }

    public @NotNull LongAck getLocoNetResponse() {
        return this.response;
    }

    public static class ImmResponseEvent extends LocoRespondEvent<ImmPacket, ILocoNetMessage> {
        public ImmResponseEvent(@NotNull LongAck response, @NotNull ImmPacket locoNetMessage) {
            super(response, locoNetMessage);
        }
    }

    public static class WrSlResponseEvent extends LocoRespondEvent<WrSlData, ILocoNetMessage> {
        public WrSlResponseEvent(@NotNull LongAck response, @NotNull WrSlData locoNetMessage) {
            super(response, locoNetMessage);
        }
    }

    public static class SwReqResponseEvent extends LocoRespondEvent<SwReq, ILocoNetMessage> {
        public SwReqResponseEvent(@NotNull LongAck response, @NotNull SwReq locoNetMessage) {
            super(response, locoNetMessage);
        }
    }

    public static class SwStateResponseEvent extends LocoRespondEvent<SwState, ILocoNetMessage> {
        public SwStateResponseEvent(@NotNull LongAck response, @NotNull SwState locoNetMessage) {
            super(response, locoNetMessage);
        }
    }

    public static class SwAckResponseEvent extends LocoRespondEvent<SwAck, ILocoNetMessage> {
        public SwAckResponseEvent(@NotNull LongAck response, @NotNull SwAck locoNetMessage) {
            super(response, locoNetMessage);
        }
    }

    public static class LocoAdrErrorResponseEvent extends LocoRespondEvent<LocoAdr, ILocoNetMessage> {
        public LocoAdrErrorResponseEvent(@NotNull LongAck response, @NotNull LocoAdr locoNetMessage) {
            super(response, locoNetMessage);
        }
    }
}
