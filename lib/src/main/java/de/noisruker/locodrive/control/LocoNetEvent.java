/*
 * LocoNetEvent
 * LocoNetEvent.java
 * Copyright © 2022 Fabius Mettner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package de.noisruker.locodrive.control;

import de.noisruker.event.events.Event;
import de.noisruker.locodrive.args.*;
import org.jetbrains.annotations.NotNull;

/**
 * This event holds a loco net message received by the loco net
 * @param <T> The loco nets message received type
 * @param <R> The events result extending some {@link ILocoNetMessage}
 */
public class LocoNetEvent<T extends ILocoNetMessage, R extends ILocoNetMessage> extends Event<R> {

    /**
     * The by the loco net received loco net message
     */
    private final T locoNetMessage;

    /**
     * Creates a new loco net event based on the received loco net message
     * @param locoNetMessage The received loco net message
     */
    public LocoNetEvent(@NotNull T locoNetMessage) {
        super("LocoNetEvent<" + locoNetMessage.getClass().getName() + ">");
        this.locoNetMessage = locoNetMessage;
    }

    /**
     * @return The received loco net message
     */
    public @NotNull T getLocoNetMessage() {
        return locoNetMessage;
    }

    /**
     * The event for receiving an {@link Idle} message from the loco net
     */
    public static class IdleEvent extends LocoNetEvent<Idle, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public IdleEvent(@NotNull Idle locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link GpOn} message from the loco net
     */
    public static class GpOnEvent extends LocoNetEvent<GpOn, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public GpOnEvent(@NotNull GpOn locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link GpOff} message from the loco net
     */
    public static class GpOffEvent extends LocoNetEvent<GpOff, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public GpOffEvent(@NotNull GpOff locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link Busy} message from the loco net
     */
    public static class BusyEvent extends LocoNetEvent<Busy, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public BusyEvent(@NotNull Busy locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link LocoAdr} message from the loco net
     */
    public static class LocoAdrEvent extends LocoNetEvent<LocoAdr, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public LocoAdrEvent(@NotNull LocoAdr locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link SwAck} message from the loco net
     */
    public static class SwAckEvent extends LocoNetEvent<SwAck, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public SwAckEvent(@NotNull SwAck locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link SwState} message from the loco net
     */
    public static class SwStateEvent extends LocoNetEvent<SwState, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public SwStateEvent(@NotNull SwState locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link RqSlData} message from the loco net
     */
    public static class RqSlDataEvent extends LocoNetEvent<RqSlData, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public RqSlDataEvent(@NotNull RqSlData locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link MoveSlots} message from the loco net
     */
    public static class MoveSlotsEvent extends LocoNetEvent<MoveSlots, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public MoveSlotsEvent(@NotNull MoveSlots locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link LinkSlots} message from the loco net
     */
    public static class LinkSlotsEvent extends LocoNetEvent<LinkSlots, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public LinkSlotsEvent(@NotNull LinkSlots locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link UnlinkSlots} message from the loco net
     */
    public static class UnlinkSlotsEvent extends LocoNetEvent<UnlinkSlots, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public UnlinkSlotsEvent(@NotNull UnlinkSlots locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link ConsistFunc} message from the loco net
     */
    public static class ConsistFuncEvent extends LocoNetEvent<ConsistFunc, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public ConsistFuncEvent(@NotNull ConsistFunc locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link SlotStat1} message from the loco net
     */
    public static class SlotStat1Event extends LocoNetEvent<SlotStat1, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public SlotStat1Event(@NotNull SlotStat1 locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link LongAck} message from the loco net
     */
    public static class LongAckEvent extends LocoNetEvent<LongAck, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public LongAckEvent(@NotNull LongAck locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link InputRep} message from the loco net
     */
    public static class InputRepEvent extends LocoNetEvent<InputRep, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public InputRepEvent(@NotNull InputRep locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link SwRep} message from the loco net
     */
    public static class SwRepEvent extends LocoNetEvent<SwRep, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public SwRepEvent(@NotNull SwRep locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link SwReq} message from the loco net
     */
    public static class SwReqEvent extends LocoNetEvent<SwReq, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public SwReqEvent(@NotNull SwReq locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link LocoSnd} message from the loco net
     */
    public static class LocoSndEvent extends LocoNetEvent<LocoSnd, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public LocoSndEvent(@NotNull LocoSnd locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link LocoDirf} message from the loco net
     */
    public static class LocoDirfEvent extends LocoNetEvent<LocoDirf, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public LocoDirfEvent(@NotNull LocoDirf locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link LocoSpd} message from the loco net
     */
    public static class LocoSpdEvent extends LocoNetEvent<LocoSpd, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public LocoSpdEvent(@NotNull LocoSpd locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link MultiSense} message from the loco net
     */
    public static class MultiSenseEvent extends LocoNetEvent<MultiSense, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public MultiSenseEvent(@NotNull MultiSense locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link UhliFun} message from the loco net
     */
    public static class UhliFunEvent extends LocoNetEvent<UhliFun, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public UhliFunEvent(@NotNull UhliFun locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link WrSlData} message from the loco net
     */
    public static class WrSlDataEvent extends LocoNetEvent<WrSlData, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public WrSlDataEvent(@NotNull WrSlData locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link SlRdData} message from the loco net
     */
    public static class SlRdDataEvent extends LocoNetEvent<SlRdData, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public SlRdDataEvent(@NotNull SlRdData locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link ImmPacket} message from the loco net
     */
    public static class ImmPacketEvent extends LocoNetEvent<ImmPacket, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public ImmPacketEvent(@NotNull ImmPacket locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link Rep} message from the loco net
     */
    public static class RepEvent extends LocoNetEvent<Rep, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public RepEvent(@NotNull Rep locoNetMessage) {
            super(locoNetMessage);
        }
    }

    /**
     * The event for receiving an {@link PeerXfer} message from the loco net
     */
    public static class PeerXferEvent extends LocoNetEvent<PeerXfer, ILocoNetMessage> {
        /**
         * @param locoNetMessage The received message
         */
        public PeerXferEvent(@NotNull PeerXfer locoNetMessage) {
            super(locoNetMessage);
        }
    }
    
}
