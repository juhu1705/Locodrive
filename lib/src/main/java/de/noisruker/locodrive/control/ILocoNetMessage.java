/*
 * ILocoNetMessage
 * ILocoNetMessage.java
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

import de.noisruker.locodrive.args.LocoNetConnector;

/**
 * A message that you can send to the loco net
 */
public interface ILocoNetMessage {
    /**
     * Sends this message to the given loco net connection. You can use {@link LocoNetHandler#send(ILocoNetMessage)} to send it to the loco net connection configured by the loco net handler.
     * @param connector The loco net to connection to send to.
     * @return If the sending was successfully
     */
    boolean send(LocoNetConnector connector);
}
