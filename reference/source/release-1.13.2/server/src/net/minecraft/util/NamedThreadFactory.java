package net.minecraft.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NamedThreadFactory implements ThreadFactory {
   private static final Logger field_202908_a = LogManager.getLogger();
   private final ThreadGroup field_202909_b;
   private final AtomicInteger field_202910_c = new AtomicInteger(1);
   private final String field_202911_d;

   public NamedThreadFactory(String var1) {
      SecurityManager ☃ = System.getSecurityManager();
      this.field_202909_b = ☃ != null ? ☃.getThreadGroup() : Thread.currentThread().getThreadGroup();
      this.field_202911_d = ☃ + "-";
   }

   public Thread newThread(Runnable var1) {
      Thread ☃ = new Thread(this.field_202909_b, ☃, this.field_202911_d + this.field_202910_c.getAndIncrement(), 0L);
      ☃.setUncaughtExceptionHandler((var1x, var2x) -> {
         field_202908_a.error("Caught exception in thread {} from {}", var1x, ☃);
         field_202908_a.error("", var2x);
      });
      if (☃.getPriority() != 5) {
         ☃.setPriority(5);
      }

      return ☃;
   }
}
