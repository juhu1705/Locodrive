/*
 * Ungeachtet der Bestimmungen der GPL ist die Verwendung dieser Tests mit der JUnit-Bibliothek ausdrücklich erlaubt.
 */
package de.noisruker.locodrive;

import de.noisruker.event.EventManager;
import de.noisruker.locodrive.args.*;
import de.noisruker.locodrive.control.LocoNetEvent;
import de.noisruker.locodrive.control.LocoNetHandler;
import de.noisruker.logger.Logger;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;


public class LibraryTest {

    @Test
    public void testRustConnection() throws Exception {
        Logger.LOGGER.info("Try rust code connection");

        LocoNetHandler.getInstance();

        AddressArg address = new AddressArg(16);
        assertEquals(16, address.address());

        EventManager.getInstance().registerEventListener(LocoNetEvent.LocoSpdEvent.class, event -> {
                Logger.LOGGER.info("Set speed of slot " + String.valueOf(event.getLocoNetMessage().getSlot().slot()) + " to " + String.valueOf(event.getLocoNetMessage().getSpd().spd()));
                Logger.LOGGER.info("Request to deactivate loco net");
                event.setResult(new GpOff());
        });
        EventManager.getInstance().registerEventListener(LocoNetEvent.GpOnEvent.class, event ->
                Logger.LOGGER.info("loco net activated"));
        EventManager.getInstance().registerEventListener(LocoNetEvent.class, event ->
                Logger.LOGGER.info("Some loco net event occurred: " + event.toString()));

        EventManager.getInstance().triggerEvent(new LocoNetEvent<>(new LocoSpd(new SlotArg((short)7),0)));
        EventManager.getInstance().triggerEvent(new LocoNetEvent<>(new GpOn()));
        EventManager.getInstance().triggerEvent(new LocoNetEvent.GpOnEvent(new GpOn()));
        EventManager.getInstance().triggerEventAsync(new LocoNetEvent.LocoSpdEvent(new LocoSpd(new SlotArg((short) 4), 0)), ret -> {
            if(ret instanceof GpOff) {
                Logger.LOGGER.info("Received response!");
            } else {
                Logger.LOGGER.log(Level.SEVERE, "Unexpected response!");
                throw new IllegalStateException();
            }
        });

        for(String port: PortInfos.getAllPorts()) {
            Logger.LOGGER.info("Connectable port: " + port);
        }

        Logger.LOGGER.info("Rust code connection was successfully.");
    }

    @Test public void testLocoNetConnection() throws Exception {
        if(LocoNetHandler.getInstance().getPorts().length == 0) {
            Logger.LOGGER.info("No port to connect to, scip loco net connection tests!");
        } else {
            EventManager.getInstance().registerEventListener(LocoNetEvent.class, event -> Logger.LOGGER.info(event.getLocoNetMessage().toString()));

            LocoNetHandler.getInstance().connectTo(PortInfos.getAllPorts()[0]);

            LocoNetHandler.getInstance().startReader();

            assertTrue(LocoNetHandler.getInstance().send(new LocoAdr(new AddressArg(3))));

            assertTrue(LocoNetHandler.getInstance().send(new GpOff()));

            assertTrue(LocoNetHandler.getInstance().send(new LocoSpd(new SlotArg((short) 7), 100)));
            assertTrue(LocoNetHandler.getInstance().send(new LocoSpd(new SlotArg((short) 7), 0)));

            LocoNetHandler.getInstance().stop();

            assertFalse(LocoNetHandler.getInstance().send(new LocoSpd(new SlotArg((short) 7), 0)));

            assertFalse(LocoNetHandler.getInstance().send(new GpOff()));

            Logger.LOGGER.info("loco net connection was successfully");
        }
    }

}
