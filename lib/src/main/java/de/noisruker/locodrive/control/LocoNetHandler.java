/*
 * LocoNetHandler
 * LocoNetHandler.java
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

import de.noisruker.event.EventManager;
import de.noisruker.locodrive.args.*;
import org.jetbrains.annotations.NotNull;

import java.nio.channels.AlreadyConnectedException;
import java.util.Arrays;
import java.util.logging.Level;
import static de.noisruker.logger.Logger.LOGGER;
import static de.noisruker.locodrive.control.LocoNetEvent.*;
import static de.noisruker.locodrive.control.LocoRespondEvent.*;

/**
 * A controller class for one loco net connection.
 *
 * All received messages will be fired over the {@link de.noisruker.event.EventManager event manager}.
 *
 * You can easily send messages to the loco net calling {@link LocoNetHandler#send(ILocoNetMessage)} with the message you want to send.
 *
 * When this class is called first it automatically loads the locodrive native library, for manually loading call {@link Utils#loadNativeLibrary()}.
 */
public class LocoNetHandler {

    /**
     * The LocoNetHandler instance to use
     */
    private static final LocoNetHandler INSTANCE = new LocoNetHandler();

    /**
     * @return The LocoNetHandler instance
     */
    @NotNull
    public static LocoNetHandler getInstance() {
        return LocoNetHandler.INSTANCE;
    }

    /**
     * The rust connection manager for the loco net connection
     */
    private LocoNetConnector connector = null;

    /**
     * Creates a new LocoNetHandler and loads the needed native libraries
     */
    private LocoNetHandler() {
        try {
            Utils.loadNativeLibrary();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects this loco net handler to a serial port
     *
     * The baudRate will be set to 115_200, the sendingTimeout will be 500 milliseconds, the flowControl will be software based and the updateCycles will be all 5 seconds.
     * @param serialPort The name of the serial port to connect to. (All port names can be listed by calling {@link PortInfos#getAllPorts()})
     * @throws Exception If the connecting to the port failed
     */
    public void connectTo(@NotNull String serialPort) throws Exception {
        if(!Arrays.asList(PortInfos.getAllPorts()).contains(serialPort))
            throw new IllegalArgumentException("The port " + serialPort + " is not available!");
        this.connector = new LocoNetConnector(serialPort, this::read, this::handleLack, this::handleError);
        LOGGER.info("Successfully connected to port " + this.connector.getPortName());
    }

    /**
     * Connects this loco net handler to a serial port with a specific baud_rate and sending timeout.
     *
     * The flowControl will be software based and the updateCycles will be all 5 seconds.
     * @param serialPort The name of the serial port to connect to. (All port names can be listed by calling {@link PortInfos#getAllPorts()})
     * @param baudRate The baud rate to use. Common baud rates may be: 115_200, 57_600, 38_400 or 19_200
     * @param sendingTimeout The time send messages wait for the message receive response before failing and returning false
     * @throws Exception If the connecting to the port failed
     */
    public void connectTo(@NotNull String serialPort, long baudRate, long sendingTimeout) throws Exception {
        if(!Arrays.asList(PortInfos.getAllPorts()).contains(serialPort))
            throw new IllegalArgumentException("The port " + serialPort + " is not available!");
        this.connector = new LocoNetConnector(serialPort, baudRate, sendingTimeout, this::read, this::handleLack, this::handleError);
        LOGGER.info("Successfully connected to port " + this.connector.getPortName());
    }

    /**
     * Connects this loco net handler to a serial port with a specific baud_rate, sending timeout, updateCycle rate and flowControl.
     * @param serialPort The name of the serial port to connect to. (All port names can be listed by calling {@link PortInfos#getAllPorts()})
     * @param baudRate The baud rate to use. Common baud rates may be: 115_200, 57_600, 38_400 or 19_200
     * @param sendingTimeout The time send messages wait for the message receive response before failing and returning false
     * @param updateCycles The time the reader threads sleeps while waiting for incoming loco net messages, before awaking and do the turnoff check
     * @param flowControl The flow control used for the loco net connection
     * @throws Exception If the connecting to the port failed
     */
    public void connectTo(@NotNull String serialPort, long baudRate, long sendingTimeout, long updateCycles, FlowControl flowControl) throws Exception {
        if(!Arrays.asList(PortInfos.getAllPorts()).contains(serialPort))
            throw new IllegalArgumentException("The port " + serialPort + " is not available!");
        this.connector = new LocoNetConnector(serialPort, baudRate, sendingTimeout, updateCycles, flowControl, this::read, this::handleLack, this::handleError);
        LOGGER.info("Successfully connected to port " + this.connector.getPortName());
    }

    /**
     * Starts the loco net message reader for this port.
     * Messages received to this port will be triggered over the {@link EventManager}.
     * {@link LocoNetEvent Here} can You find all available LocoNetEvents that may be triggered.
     * {@link LocoNetErrorEvent Here} can You find all available LocoNetErrorEvents that may be triggered.
     * {@link LocoRespondEvent Here} can You find all available LocoNet responses for the specified messages.
     * @throws IllegalStateException If no port is configured
     */
    public void startReader() throws IllegalStateException {
        if(this.connector == null)
            throw new IllegalStateException("No port is configured");
        if(!this.connector.startReader())
            throw new AlreadyConnectedException();
    }

    /**
     * Stops the loco net message reader and wait for the reading thread to end before returning.
     * @throws IllegalStateException If no reader is configured
     */
    public void stop() throws IllegalStateException {
        if(this.connector == null)
            throw new IllegalStateException("No port is configured");

        LOGGER.info("Try interrupting reader");
        this.connector.stopReader();
    }

    /**
     * Handles a message response
     * @param ack The message response to handle
     * @param message The message the response was received for
     */
    private void handleLack(LongAck ack, Message message) {
        EventManager.getInstance().triggerEventAsync((switch (message.getMessageType()) {
            case 4 -> new LocoAdrErrorResponseEvent(ack, message.getLocoAdr());
            case 5 -> new SwAckResponseEvent(ack, message.getSwAck());
            case 6 -> new SwStateResponseEvent(ack, message.getSwState());
            case 16 -> new SwReqResponseEvent(ack, message.getSwReq());
            case 22 -> new WrSlResponseEvent(ack, message.getWrSlData());
            case 24 -> new ImmResponseEvent(ack, message.getImmPacket());
            default -> throw new IllegalStateException("Unexpected value: " + message.getMessageType());
        }), this::sendResponse);
    }

    /**
     * Handles an incoming loco net message
     * @param message The message to handle
     */
    private void read(Message message) {
        EventManager.getInstance().triggerEventAsync((switch (message.getMessageType()) {
            case 0 -> new IdleEvent(message.getIdle());
            case 1 -> new GpOnEvent(message.getGpOn());
            case 2 -> new GpOffEvent(message.getGpOff());
            case 3 -> new BusyEvent(message.getBusy());
            case 4 -> new LocoAdrEvent(message.getLocoAdr());
            case 5 -> new SwAckEvent(message.getSwAck());
            case 6 -> new SwStateEvent(message.getSwState());
            case 7 -> new RqSlDataEvent(message.getRqSlData());
            case 8 -> new MoveSlotsEvent(message.getMoveSlots());
            case 9 -> new LinkSlotsEvent(message.getLinkSlots());
            case 10 -> new UnlinkSlotsEvent(message.getUnlinkSlots());
            case 11 -> new ConsistFuncEvent(message.getConsistFunc());
            case 12 -> new SlotStat1Event(message.getSlotStat1());
            case 13 -> new LongAckEvent(message.getLongAck());
            case 14 -> new InputRepEvent(message.getInputRep());
            case 15 -> new SwRepEvent(message.getSwRep());
            case 16 -> new SwReqEvent(message.getSwReq());
            case 17 -> new LocoSndEvent(message.getLocoSnd());
            case 18 -> new LocoDirfEvent(message.getLocoDirf());
            case 19 -> new LocoSpdEvent(message.getLocoSpd());
            case 20 -> new MultiSenseEvent(message.getMultiSense());
            case 21 -> new UhliFunEvent(message.getUhliFun());
            case 22 -> new WrSlDataEvent(message.getWrSlData());
            case 23 -> new SlRdDataEvent(message.getSlRdData());
            case 24 -> new ImmPacketEvent(message.getImmPacket());
            case 25 -> new RepEvent(message.getRep());
            case 26 -> new PeerXferEvent(message.getPeerXfer());
            default -> throw new IllegalStateException("Unexpected value: " + message.getMessageType());
        }), this::sendResponse);
    }

    /**
     * Handles a occurring loco net error
     * @param error The loco net error to handle
     */
    public void handleError(MessageParseError error) {
        LOGGER.log(Level.SEVERE, "A error occurred while reading from serial port (port: " + this.connector.getPortName() + "; Error: " + error.toString() + ")");

        EventManager.getInstance().triggerEvent(new LocoNetErrorEvent(error));
    }

    /**
     * Sends a responding message back to the loco net
     * @param message The message to send back to the connected loco net
     * @throws IllegalStateException If the port is not configured
     */
    private void sendResponse(ILocoNetMessage message) throws IllegalStateException {
        if(message == null)
            return;
        if(this.connector == null)
            throw new IllegalStateException("No port is configured");

        LOGGER.info("Sending new message to LocoNet: " + message);

        if(!message.send(this.connector)) {
            LOGGER.warning("The response message could not be send to the loco net");
        }
    }

    /**
     * Sends a given {@link ILocoNetMessage loco net message} to the connected loco net
     *
     * Note: The loco net reader must be running to validate the receiving of the message, else all message sending will fail.
     * @param message The message to send
     * @return If the sending was successfully
     * @throws IllegalStateException If no port for this loco net is configured (No connection configured)
     */
    public synchronized boolean send(@NotNull ILocoNetMessage message) throws IllegalStateException {
        if(this.connector == null)
            throw new IllegalStateException("No port is configured");

        LOGGER.info("Sending new message to LocoNet: " + message);
        return message.send(this.connector);
    }

}
