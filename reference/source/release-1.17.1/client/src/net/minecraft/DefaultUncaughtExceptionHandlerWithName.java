package net.minecraft;

import java.lang.Thread.UncaughtExceptionHandler;
import org.apache.logging.log4j.Logger;

public class DefaultUncaughtExceptionHandlerWithName implements UncaughtExceptionHandler {
   private final Logger logger;

   public DefaultUncaughtExceptionHandlerWithName(Logger var1) {
      this.logger = â˜ƒ;
   }

   public void uncaughtException(Thread var1, Throwable var2) {
      this.logger.error("Caught previously unhandled exception :");
      this.logger.error(â˜ƒ.getName(), â˜ƒ);
   }
}
