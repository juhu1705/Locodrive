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
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


public class LibraryTest {

    static {
        try {
            Utils.loadNativeLibrary();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRustConnection() throws Exception {
        System.err.println("Try rust code connection");

        ConfigurableApplicationContext context = SpringApplication.run(LibraryTest.class);

        context.start();

        AddressArg address = new AddressArg(16);
        assertEquals(16, address.address());

        SlotArg slot = new SlotArg(1);
        SwitchArg swArg = new SwitchArg(3, SwitchDirection.STRAIGHT, true);
        SpeedArg speedArg = new SpeedArg(121);

        LocoNetEvent<ILocoNetMessage, ILocoNetMessage>[] runEvents = new LocoNetEvent[] {
                new LocoNetEvent.IdleEvent(new Idle()),
                new LocoNetEvent.GpOffEvent(new GpOff()),
                new LocoNetEvent.GpOnEvent(new GpOn()),
                new LocoNetEvent.BusyEvent(new Busy()),

                new LocoNetEvent.LocoAdrEvent(new LocoAdr(3)),
                new LocoNetEvent.SwAckEvent(new SwAck(swArg)),
                new LocoNetEvent.SwStateEvent(new SwState(swArg)),
                new LocoNetEvent.RqSlDataEvent(new RqSlData(slot)),
                new LocoNetEvent.MoveSlotsEvent(new MoveSlots(slot, slot)),
                new LocoNetEvent.LinkSlotsEvent(new LinkSlots(slot, slot)),
                new LocoNetEvent.UnlinkSlotsEvent(new UnlinkSlots(slot, slot)),
                new LocoNetEvent.ConsistFuncEvent(new ConsistFunc(slot, new DirfArg((short) 4))),

                new LocoNetEvent.SlotStat1Event(new SlotStat1(slot, new Stat1Arg(false, Consist.FREE, State.COMMON, DecoderType.DCC_128))),
                new LocoNetEvent.LongAckEvent(new LongAck(new LopcArg((short) 3), new Ack1Arg((short) 2))),
                new LocoNetEvent.InputRepEvent(new InputRep(new InArg(200, SourceType.DS54_AUX, SensorLevel.LOW, false))),
                new LocoNetEvent.SwRepEvent(new SwRep(new SnArg(32, false, true))),
                new LocoNetEvent.SwReqEvent(new SwReq(swArg)),
                new LocoNetEvent.LocoSndEvent(new LocoSnd(slot, new SndArg(true, false, true, true))),
                new LocoNetEvent.LocoDirfEvent(new LocoDirf(slot, new DirfArg(true, false, false, false, false, false))),
                new LocoNetEvent.LocoSpdEvent(new LocoSpd(slot, 20)),
                new LocoNetEvent.MultiSenseEvent(new MultiSense(new MultiSenseArg((short) 1, false, (short) 3, (short) 54), new AddressArg(39))),
                new LocoNetEvent.LocoSpdEvent(new LocoSpd(slot, new SpeedArg())),
                new LocoNetEvent.UhliFunEvent(new UhliFun(slot, new FunctionArg(3, 0x2F))),
                new LocoNetEvent.WrSlDataEvent(new WrSlData(new WrSlDataStructure((short) 0xFF, new WrSlDataTime(new FastClock((short) 0xFF, (short) 0xFF, (short) 0x3F, (short) 0x4F, (short) 0x00, (short) 0x00, (short) 0x01), new TrkArg(false, true, false, true), new IdArg(0)), new WrSlDataPt(new Pcmd(false, true, false, true, false), new AddressArg(202), new TrkArg(false, false, false, false), new CvDataArg()), new WrSlDataGeneral(new SlotArg(12), new Stat1Arg(false, Consist.FREE, State.COMMON, DecoderType.REGULAR_28), new Stat2Arg(false, true, true), new AddressArg(2), speedArg, new DirfArg(false, false, false, false, false, false), new TrkArg(false, false, true, true), new IdArg(0))))),
                new LocoNetEvent.SlRdDataEvent(new SlRdData(slot, new Stat1Arg(false, Consist.LOGICAL_TOP, State.COMMON, DecoderType.SPEED_128), new AddressArg(22), speedArg, new DirfArg((short) 0x04), new TrkArg(false, true, false, true), new Stat2Arg(false, false, false), new IdArg(0))),
                new LocoNetEvent.ImmPacketEvent(new ImmPacket(new ImArg((short) 0, (short) 1, 12, (short) 0, (short) 0, (short) 1))),
                new LocoNetEvent.RepEvent(new Rep(new RepStructure(new LissyIrReport(false, 0, 2, (short) 4)))),
                new LocoNetEvent.PeerXferEvent(new PeerXfer(slot, new DstArg(20), new PxctData((short) 20, (short) 239, (short) 230, (short) 0, (short) 0, (short) 0, (short) 2, (short) 2, (short) 3)))
        };

        List<LocoNetEvent<ILocoNetMessage, ILocoNetMessage>> items = new CopyOnWriteArrayList<>();

        context.addApplicationListener((LocoNetEvent.LocoSpdEvent event) -> {
                System.out.println("Set speed of slot " + String.valueOf(event.getLocoNetMessage().getSlot().slot()) + " to " + String.valueOf(event.getLocoNetMessage().getSpd().spd()));
                System.out.println("Request to deactivate loco net");
                event.setResult(new GpOff());
                items.add((LocoNetEvent) event);
        });

        context.addApplicationListener((LocoNetEvent.GpOnEvent event) -> {
                System.out.println("loco net activated");
        });
        context.addApplicationListener((LocoNetEvent<ILocoNetMessage, ILocoNetMessage> event) -> {
                System.out.println("Some loco net event occurred: " + event.getLocoNetMessage());

                if(event.getLocoNetMessage() instanceof LocoSpd)
                    return;

                items.add(event);
        });

        Arrays.stream(runEvents).forEach(context::publishEvent);

        Thread.sleep(1000);

        assertEquals(runEvents.length, items.size());

        for(String port: PortInfos.getAllPorts()) {
            System.out.println("Connectable port: " + port);
        }

        System.out.println("Rust code connection was successfully.");
    }

    @Test
    public void testLocoNetConnection() throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(LibraryTest.class);

        LocoNetHandler handler = new LocoNetHandler(context, Logger.getGlobal());

        if(handler.getPorts().length == 0) {
            System.out.println("No port to connect to, scip loco net connection tests!");
        } else {
            context.addApplicationListener((LocoNetEvent<ILocoNetMessage, ILocoNetMessage> event) -> System.out.println(event.getLocoNetMessage()));


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
