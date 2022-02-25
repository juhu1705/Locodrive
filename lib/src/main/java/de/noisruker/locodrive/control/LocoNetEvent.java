package de.noisruker.locodrive.control;

import de.noisruker.event.events.Event;
import de.noisruker.locodrive.args.*;
import org.jetbrains.annotations.NotNull;

public class LocoNetEvent<T extends ILocoNetMessage, R extends ILocoNetMessage> extends Event<R> {

    private final T locoNetMessage;

    public LocoNetEvent(@NotNull T locoNetMessage) {
        super("LocoNetEvent<" + locoNetMessage.getClass().getName() + ">");
        this.locoNetMessage = locoNetMessage;
    }

    public @NotNull T getLocoNetMessage() {
        return locoNetMessage;
    }

    public static class IdleEvent extends LocoNetEvent<Idle, ILocoNetMessage> {
        public IdleEvent(@NotNull Idle locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class GpOnEvent extends LocoNetEvent<GpOn, ILocoNetMessage> {
        public GpOnEvent(@NotNull GpOn locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class GpOffEvent extends LocoNetEvent<GpOff, ILocoNetMessage> {
        public GpOffEvent(@NotNull GpOff locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class BusyEvent extends LocoNetEvent<Busy, ILocoNetMessage> {
        public BusyEvent(@NotNull Busy locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class LocoAdrEvent extends LocoNetEvent<LocoAdr, ILocoNetMessage> {
        public LocoAdrEvent(@NotNull LocoAdr locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class SwAckEvent extends LocoNetEvent<SwAck, ILocoNetMessage> {
        public SwAckEvent(@NotNull SwAck locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class SwStateEvent extends LocoNetEvent<SwState, ILocoNetMessage> {
        public SwStateEvent(@NotNull SwState locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class RqSlDataEvent extends LocoNetEvent<RqSlData, ILocoNetMessage> {
        public RqSlDataEvent(@NotNull RqSlData locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class MoveSlotsEvent extends LocoNetEvent<MoveSlots, ILocoNetMessage> {
        public MoveSlotsEvent(@NotNull MoveSlots locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class LinkSlotsEvent extends LocoNetEvent<LinkSlots, ILocoNetMessage> {
        public LinkSlotsEvent(@NotNull LinkSlots locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class UnlinkSlotsEvent extends LocoNetEvent<UnlinkSlots, ILocoNetMessage> {
        public UnlinkSlotsEvent(@NotNull UnlinkSlots locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class ConsistFuncEvent extends LocoNetEvent<ConsistFunc, ILocoNetMessage> {
        public ConsistFuncEvent(@NotNull ConsistFunc locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class SlotStat1Event extends LocoNetEvent<SlotStat1, ILocoNetMessage> {
        public SlotStat1Event(@NotNull SlotStat1 locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class LongAckEvent extends LocoNetEvent<LongAck, ILocoNetMessage> {
        public LongAckEvent(@NotNull LongAck locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class InputRepEvent extends LocoNetEvent<InputRep, ILocoNetMessage> {
        public InputRepEvent(@NotNull InputRep locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class SwRepEvent extends LocoNetEvent<SwRep, ILocoNetMessage> {
        public SwRepEvent(@NotNull SwRep locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class SwReqEvent extends LocoNetEvent<SwReq, ILocoNetMessage> {
        public SwReqEvent(@NotNull SwReq locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class LocoSndEvent extends LocoNetEvent<LocoSnd, ILocoNetMessage> {
        public LocoSndEvent(@NotNull LocoSnd locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class LocoDirfEvent extends LocoNetEvent<LocoDirf, ILocoNetMessage> {
        public LocoDirfEvent(@NotNull LocoDirf locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class LocoSpdEvent extends LocoNetEvent<LocoSpd, ILocoNetMessage> {
        public LocoSpdEvent(@NotNull LocoSpd locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class MultiSenseEvent extends LocoNetEvent<MultiSense, ILocoNetMessage> {
        public MultiSenseEvent(@NotNull MultiSense locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class UhliFunEvent extends LocoNetEvent<UhliFun, ILocoNetMessage> {
        public UhliFunEvent(@NotNull UhliFun locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class WrSlDataEvent extends LocoNetEvent<WrSlData, ILocoNetMessage> {
        public WrSlDataEvent(@NotNull WrSlData locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class SlRdDataEvent extends LocoNetEvent<SlRdData, ILocoNetMessage> {
        public SlRdDataEvent(@NotNull SlRdData locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class ImmPacketEvent extends LocoNetEvent<ImmPacket, ILocoNetMessage> {
        public ImmPacketEvent(@NotNull ImmPacket locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class RepEvent extends LocoNetEvent<Rep, ILocoNetMessage> {
        public RepEvent(@NotNull Rep locoNetMessage) {
            super(locoNetMessage);
        }
    }

    public static class PeerXferEvent extends LocoNetEvent<PeerXfer, ILocoNetMessage> {
        public PeerXferEvent(@NotNull PeerXfer locoNetMessage) {
            super(locoNetMessage);
        }
    }
    
}
