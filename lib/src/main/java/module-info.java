/**
 * A module for connecting to a loco net via a serial port
 */
module de.noisruker.locodrive {
    requires java.base;
    requires org.jetbrains.annotations;
    requires java.logging;
    requires spring.context;

    exports de.noisruker.locodrive.control;
    exports de.noisruker.locodrive.args;
}