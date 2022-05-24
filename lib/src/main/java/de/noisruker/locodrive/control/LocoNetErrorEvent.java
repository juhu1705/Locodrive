/*
 * LocoNetErrorEvent
 * LocoNetErrorEvent.java
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

import de.noisruker.locodrive.args.MessageParseError;

/**
 * This event holds an error thrown by the loco net
 */
public class LocoNetErrorEvent extends Event<Void> {

    /**
     * The error thrown by the loco net
     */
    private final MessageParseError error;

    /**
     * Creates a new error event based on the error received from the loco net connection
     * @param error The error received from the loco net
     */
    public LocoNetErrorEvent(MessageParseError error) {
        super("loconeterror");
        this.error = error;
    }

    /**
     * @return The rust connection error to handle.
     */
    public MessageParseError getError() {
        return this.error;
    }

}
