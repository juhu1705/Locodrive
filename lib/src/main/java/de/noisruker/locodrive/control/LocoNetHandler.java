package de.noisruker.locodrive;

import de.noisruker.locodrive.args.NativeUtils;

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

    public static LocoNetHandler getInstance() {
        return LocoNetHandler.INSTANCE;
    }

    public void startReader() {

    }

    public static void read() {
        System.out.println("Test");
    }

    public static native void doStuffInNative();

}
