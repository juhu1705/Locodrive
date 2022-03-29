/*
 * LocoRespondEvent
 * LocoRespondEvent.java
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
 * This event is triggered if a long acknowledgment response to a loco net message is received
 * @param <T> The loco net message the long acknowledgment response is received for
 * @param <R> The return value of this event
 */
public class LocoRespondEvent<T extends ILocoNetMessage, R extends ILocoNetMessage> extends Event<R> {

    /**
     * The loco net message the response is received for
     */
    private final T locoNetMessage;
    /**
     * The long acknowledgment response for this message
     */
    private final LongAck response;

    /**
     * A new loco net response event
     * @param response The long acknowledgement response received
     * @param locoNetMessage The loco net message the response is received for
     */
    public LocoRespondEvent(@NotNull LongAck response, @NotNull T locoNetMessage) {
        super("LocoNetEvent<" + locoNetMessage.getClass().getName() + ">");
        this.locoNetMessage = locoNetMessage;
        this.response = response;
    }

    /**
     * @return The loco net message the response is received for
     */
    public @NotNull T getLocoNetMessage() {
        return this.locoNetMessage;
    }

    /**
     * @return The long acknowledgment response received for the loco net message
     */
    public @NotNull LongAck getLocoNetResponse() {
        return this.response;
    }

    /**
     * The loco net response for the {@link ImmPacket} loco net message
     */
    public static class ImmResponseEvent extends LocoRespondEvent<ImmPacket, ILocoNetMessage> {
        /**
         * A new loco net response event
         * @param response The long acknowledgement response received
         * @param locoNetMessage The loco net message the response is received for
         */
        public ImmResponseEvent(@NotNull LongAck response, @NotNull ImmPacket locoNetMessage) {
            super(response, locoNetMessage);
        }
    }

    /**
     * The loco net response for the {@link WrSlData} loco net message
     */
    public static class WrSlResponseEvent extends LocoRespondEvent<WrSlData, ILocoNetMessage> {
        /**
         * A new loco net response event
         * @param response The long acknowledgement response received
         * @param locoNetMessage The loco net message the response is received for
         */
        public WrSlResponseEvent(@NotNull LongAck response, @NotNull WrSlData locoNetMessage) {
            super(response, locoNetMessage);
        }
    }

    /**
     * The loco net response for the {@link SwReq} loco net message
     */
    public static class SwReqResponseEvent extends LocoRespondEvent<SwReq, ILocoNetMessage> {
        /**
         * A new loco net response event
         * @param response The long acknowledgement response received
         * @param locoNetMessage The loco net message the response is received for
         */
        public SwReqResponseEvent(@NotNull LongAck response, @NotNull SwReq locoNetMessage) {
            super(response, locoNetMessage);
        }
    }

    /**
     * The loco net response for the {@link SwState} loco net message
     */
    public static class SwStateResponseEvent extends LocoRespondEvent<SwState, ILocoNetMessage> {
        /**
         * A new loco net response event
         * @param response The long acknowledgement response received
         * @param locoNetMessage The loco net message the response is received for
         */
        public SwStateResponseEvent(@NotNull LongAck response, @NotNull SwState locoNetMessage) {
            super(response, locoNetMessage);
        }
    }

    /**
     * The loco net response for the {@link SwAck} loco net message
     */
    public static class SwAckResponseEvent extends LocoRespondEvent<SwAck, ILocoNetMessage> {
        /**
         * A new loco net response event
         * @param response The long acknowledgement response received
         * @param locoNetMessage The loco net message the response is received for
         */
        public SwAckResponseEvent(@NotNull LongAck response, @NotNull SwAck locoNetMessage) {
            super(response, locoNetMessage);
        }
    }

    public static class LocoAdrErrorResponseEvent extends LocoRespondEvent<LocoAdr, ILocoNetMessage> {
        /**
         * A new loco net response event
         * @param response The long acknowledgement response received
         * @param locoNetMessage The loco net message the response is received for
         */
        public LocoAdrErrorResponseEvent(@NotNull LongAck response, @NotNull LocoAdr locoNetMessage) {
            super(response, locoNetMessage);
        }
    }
}
