/*
 * Ungeachtet der Bestimmungen der GPL ist die Verwendung dieser Tests mit der JUnit-Bibliothek ausdrücklich erlaubt.
 */
package de.noisruker.locodrive;

import de.noisruker.locodrive.args.*;
import de.noisruker.locodrive.control.ILocoNetMessage;
import de.noisruker.locodrive.control.LocoNetEvent;
import de.noisruker.locodrive.control.LocoNetHandler;
import de.noisruker.locodrive.control.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEventPublisher;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


public class LibraryTest {

    @Test
    public void testRustConnection() throws Exception {
        System.out.println("Try rust code connection");

        SpringApplication builder = new SpringApplicationBuilder(LibraryTest.class).build();

        Utils.loadNativeLibrary();

        AddressArg address = new AddressArg(16);
        assertEquals(16, address.address());

        builder.getListeners().add((LocoNetEvent.LocoSpdEvent event) -> {
                System.out.println("Set speed of slot " + String.valueOf(event.getLocoNetMessage().getSlot().slot()) + " to " + String.valueOf(event.getLocoNetMessage().getSpd().spd()));
                System.out.println("Request to deactivate loco net");
                event.setResult(new GpOff());
        });

        ApplicationEventPublisher publisher = builder.run();
        builder.getListeners().add((LocoNetEvent.GpOnEvent event) ->
                System.out.println("loco net activated"));
        builder.getListeners().add((LocoNetEvent<ILocoNetMessage, ILocoNetMessage> event) ->
                System.out.println("Some loco net event occurred: " + event.toString()));

        publisher.publishEvent(new LocoNetEvent<>(new LocoSpd(new SlotArg((short)7),0)));
        publisher.publishEvent(new LocoNetEvent<>(new GpOn()));
        publisher.publishEvent(new LocoNetEvent.GpOnEvent(new GpOn()));
        publisher.publishEvent(new LocoNetEvent.LocoSpdEvent(new LocoSpd(new SlotArg((short) 4), 0)));

        for(String port: PortInfos.getAllPorts()) {
            System.out.println("Connectable port: " + port);
        }

        System.out.println("Rust code connection was successfully.");
    }

    @Test
    public void testLocoNetConnection() throws Exception {

        SpringApplication builder = new SpringApplicationBuilder(LibraryTest.class).build();

        Utils.loadNativeLibrary();
        LocoNetHandler handler = new LocoNetHandler(builder.run(), (Logger) System.getLogger(""));

        if(handler.getPorts().length == 0) {
            System.out.println("No port to connect to, scip loco net connection tests!");
        } else {
            builder.getListeners().add((LocoNetEvent<ILocoNetMessage, ILocoNetMessage> event) -> System.out.println(event.getLocoNetMessage().toString()));


            handler.connectTo(PortInfos.getAllPorts()[0]);

            handler.startReader();

            assertTrue(handler.send(new LocoAdr(new AddressArg(3))));

            assertTrue(handler.send(new GpOff()));

            assertTrue(handler.send(new LocoSpd(new SlotArg((short) 7), 100)));
            assertTrue(handler.send(new LocoSpd(new SlotArg((short) 7), 0)));

            handler.stop();

            assertFalse(handler.send(new LocoSpd(new SlotArg((short) 7), 0)));

            assertFalse(handler.send(new GpOff()));

            System.out.println("loco net connection was successfully");
        }
    }

}
