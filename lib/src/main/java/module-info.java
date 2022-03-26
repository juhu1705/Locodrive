module de.noisruker.locodrive {
    requires java.base;
    requires de.noisruker.event;
    requires org.jetbrains.annotations;
    requires java.logging;
    requires de.noisruker.logger;

    exports de.noisruker.locodrive.control;
    exports de.noisruker.locodrive.args;
}