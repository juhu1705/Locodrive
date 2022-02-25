package de.noisruker.locodrive.control;

import de.noisruker.event.EventManager;
import de.noisruker.locodrive.args.*;
import de.noisruker.threading.ThreadManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.logging.Level;
import static de.noisruker.logger.Logger.LOGGER;

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
    private final Thread workingThread;

    private LocoNetHandler() {
        this.workingThread = new Thread(() -> {
            try {
                LOGGER.info("Reader thread started");
                this.connector.startReader();
            } catch (Exception e) {
                LOGGER.info("Reader thread stopped");
            }
            LOGGER.info("Reader thread stopped");
        });

    }

    public void connectTo(@NotNull String serialPort) throws Exception {
        if(!Arrays.asList(this.getPortInfos()).contains(serialPort))
            throw new IllegalArgumentException("The port " + serialPort + " is not available!");
        this.connector = new LocoNetConnector(serialPort, this::read, this::handleError);
        LOGGER.info("Successfully connected to port " + this.connector.getPortName());
    }

    public String[] getPortInfos() {
        return PortInfos.getAllPorts();
    }

    public void stop() {
        LOGGER.info("Try interrupting reader");
        this.workingThread.interrupt();
    }

    public void startReader() throws IllegalStateException {
        if(this.connector == null)
            throw new IllegalStateException("No port is configured");
        else if(!workingThread.isAlive())
            workingThread.start();
    }

    private void read(Message message) {
        LOGGER.info("Trigger Event?");
        EventManager.getInstance().triggerEvent(new LocoNetEvent<>(switch (message.getMessageType()) {
            case 0 -> message.getIdle();
            case 1 -> message.getGpOn();
            case 2 -> message.getGpOff();
            case 3 -> message.getBusy();
            case 4 -> message.getLocoAdr();
            case 5 -> message.getSwAck();
            case 6 -> message.getSwState();
            case 7 -> message.getRqSlData();
            case 8 -> message.getMoveSlots();
            case 9 -> message.getLinkSlots();
            case 10 -> message.getUnlinkSlots();
            case 11 -> message.getConsistFunc();
            case 12 -> message.getSlotStat1();
            case 13 -> message.getLongAck();
            case 14 -> message.getInputRep();
            case 15 -> message.getSwRep();
            case 16 -> message.getSwReq();
            case 17 -> message.getLocoSnd();
            case 18 -> message.getLocoDirf();
            case 19 -> message.getLocoSpd();
            case 20 -> message.getMultiSense();
            case 21 -> message.getUhliFun();
            case 22 -> message.getWrSlData();
            case 23 -> message.getSlRdData();
            case 24 -> message.getImmPacket();
            case 25 -> message.getRep();
            case 26 -> message.getPeerXfer();
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
