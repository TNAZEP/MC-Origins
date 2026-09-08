package net.minecraft.util;

import java.lang.Thread.UncaughtExceptionHandler;
import org.apache.logging.log4j.Logger;

public class DefaultUncaughtExceptionHandlerWithName implements UncaughtExceptionHandler {
   private final Logger field_201710_a;

   public DefaultUncaughtExceptionHandlerWithName(Logger var1) {
      this.field_201710_a = ☃;
   }

   public void uncaughtException(Thread var1, Throwable var2) {
      this.field_201710_a.error("Caught previously unhandled exception :");
      this.field_201710_a.error(☃.getName(), ☃);
   }
}
