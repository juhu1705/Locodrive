/*
 * Ungeachtet der Bestimmungen der GPL ist die Verwendung dieser Tests mit der JUnit-Bibliothek ausdrücklich erlaubt.
 */
package de.noisruker.locodrive;

import de.noisruker.event.EventManager;
import de.noisruker.locodrive.args.*;
import de.noisruker.locodrive.control.LocoNetEvent;
import de.noisruker.locodrive.control.LocoNetHandler;
import de.noisruker.logger.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

public class LibraryTest {

    @Test public void someLibraryMethodReturnsTrue() throws Exception {
        System.out.println("Test start");

        LocoNetHandler.getInstance();

        EventManager.getInstance().registerEventListener(LocoNetEvent.LocoSpdEvent.class, event -> Logger.LOGGER.info("Test"));
        EventManager.getInstance().registerEventListener(LocoNetEvent.GpOnEvent.class, event -> Logger.LOGGER.info("TUST"));
        EventManager.getInstance().registerEventListener(LocoNetEvent.class, event -> Logger.LOGGER.info("2"));

        EventManager.getInstance().triggerEvent(new LocoNetEvent<>(new LocoSpd(new SlotArg((short)7),0)));
        EventManager.getInstance().triggerEvent(new LocoNetEvent<>(new GpOn()));
        EventManager.getInstance().triggerEvent(new LocoNetEvent.GpOnEvent(new GpOn()));

        for(String port: LocoNetHandler.getInstance().getPortInfos()) {
            System.out.println(port);
        }

        LocoNetHandler.getInstance().connectTo(LocoNetHandler.getInstance().getPortInfos()[0]);



        AddressArg address = new AddressArg(16);
        assertEquals("AddressArg", 16, address.address());

        LocoNetHandler.getInstance().startReader();

        LocoNetHandler.getInstance().send(new GpOff());

        Thread.sleep(100);

        System.out.println("Start sending");

        LocoNetHandler.getInstance().send(new LocoSpd(new SlotArg((short) 7), 100));

        System.out.println("Send");

        Thread.sleep(100);

        LocoNetHandler.getInstance().stop();

        Thread.sleep(2000);

        LocoNetHandler.getInstance().send(new LocoSpd(new SlotArg((short) 7), 0));

        LocoNetHandler.getInstance().send(new GpOff());

        System.out.println("Handle stopped");
    }

}
