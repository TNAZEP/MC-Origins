package net.minecraft.server;

import java.io.OutputStream;
import java.io.PrintStream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggedPrintStream extends PrintStream {
   protected static final Logger LOGGER = LogManager.getLogger();
   protected final String name;

   public LoggedPrintStream(String var1, OutputStream var2) {
      super(â˜ƒ);
      this.name = â˜ƒ;
   }

   public void println(@Nullable String var1) {
      this.logLine(â˜ƒ);
   }

   public void println(Object var1) {
      this.logLine(String.valueOf(â˜ƒ));
   }

   protected void logLine(@Nullable String var1) {
      LOGGER.info("[{}]: {}", this.name, â˜ƒ);
   }
}
