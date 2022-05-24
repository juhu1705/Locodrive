/*
 Event Manager
 Event.java
 Copyright © 2021  Fabius Mettner (Team Noisruker)

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.noisruker.locodrive.control;

import de.noisruker.locodrive.args.GpOff;
import org.springframework.context.ApplicationEvent;

/**
 * This class represent an event.
 */
public abstract class Event<T> extends ApplicationEvent {

    /**
     * The events result
     */
    private T result;

    /**
     * Creates a new Event with the specified name.
     *
     * @param name The events name
     */
    protected Event(String name) {
        super(name);
        this.result = null;
    }

    /**
     * Sets the result of this event, which will later on be returned to the events source. By default, this is {@code null}. Please ensure before editing to include this in the calculation for the output value if this is not {@code null}.
     *
     * @param result The result to set for this event
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * @return The events result
     */
    public T getResult() {
        return this.result;
    }
}
