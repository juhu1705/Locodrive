/*
 * Util
 * Util.java
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

import de.noisruker.locodrive.args.NativeUtils;

import java.io.IOException;

public class Utils {

    /**
     * Loads the for the loco net needed native library to the runtime and links it with this java programm.
     * @throws IOException When the loading process failed
     */
    public static void loadNativeLibrary() throws IOException {
        final String OS = System.getProperty("os.name").toLowerCase();

        if(OS.contains("win"))
            NativeUtils.loadLibraryFromJar("/locodrive.dll");
        else if(OS.contains("nix") || OS.contains("nux") || OS.contains("aix"))
            NativeUtils.loadLibraryFromJar("/liblocodrive.so");
        else if(OS.contains("mac"))
            NativeUtils.loadLibraryFromJar("/liblocodrive.dylib");
        else
            throw new RuntimeException("Not allowed System OS. For support please ask the creator!");
    }

}
