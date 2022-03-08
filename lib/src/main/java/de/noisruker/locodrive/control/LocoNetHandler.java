package de.noisruker.locodrive.control;

import de.noisruker.event.EventManager;
import de.noisruker.locodrive.args.*;
import org.jetbrains.annotations.NotNull;

import java.nio.channels.AlreadyConnectedException;
import java.util.Arrays;
import java.util.logging.Level;
import static de.noisruker.logger.Logger.LOGGER;
import static de.noisruker.locodrive.control.LocoNetEvent.*;

public class LocoNetHandler {

    static {
        try {
            final String OS = System.getProperty("os.name").toLowerCase();

            if(OS.contains("win"))
                NativeUtils.loadLibraryFromJar("/locodrive.dll");
            else if(OS.contains("nix") || OS.contains("nux") || OS.contains("aix"))
                NativeUtils.loadLibraryFromJar("/locodrive.so");
            else if(OS.contains("mac"))
                NativeUtils.loadLibraryFromJar("/locodrive.dylib");
            else
                throw new RuntimeException("Not allowed System OS. For support please ask the creator!");
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private static final LocoNetHandler INSTANCE = new LocoNetHandler();

    @NotNull
    public static LocoNetHandler getInstance() {
        return LocoNetHandler.INSTANCE;
    }

    private LocoNetConnector connector = null;

    private LocoNetHandler() {

    }

    public void connectTo(@NotNull String serialPort) throws Exception {
        if(!Arrays.asList(this.getPortInfos()).contains(serialPort))
            throw new IllegalArgumentException("The port " + serialPort + " is not available!");
        this.connector = new LocoNetConnector(serialPort, this::read, this::handleLack, this::handleError);
        LOGGER.info("Successfully connected to port " + this.connector.getPortName());
    }

    public String[] getPortInfos() {
        return PortInfos.getAllPorts();
    }

    public void stop() {
        LOGGER.info("Try interrupting reader");
        this.connector.stopReader();
    }

    public void startReader() throws IllegalStateException {
        if(this.connector == null)
            throw new IllegalStateException("No port is configured");
        if(!this.connector.startReader())
            throw new AlreadyConnectedException();
    }

    private void handleLack(LongAck ack, Message message) {
        // TODO: Implement event trigger!
    }

    private void read(Message message) {
        LOGGER.info("Trigger Event?");
        EventManager.getInstance().triggerEvent((switch (message.getMessageType()) {
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
        }));
    }

    public void handleError(MessageParseError error) {
        LOGGER.log(Level.SEVERE, "A error occurred while reading from serial port (port: " + this.connector.getPortName() + "; Error: " + error.toString() + ")");

        EventManager.getInstance().triggerEvent(new LocoNetErrorEvent(error));
    }

    public synchronized boolean send(@NotNull ILocoNetMessage message) {
        LOGGER.info("Connect sending");
        return message.send(this.connector);
    }

}
