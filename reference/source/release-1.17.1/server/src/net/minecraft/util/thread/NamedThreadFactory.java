package net.minecraft.util.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NamedThreadFactory implements ThreadFactory {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ThreadGroup group;
   private final AtomicInteger threadNumber = new AtomicInteger(1);
   private final String namePrefix;

   public NamedThreadFactory(String var1) {
      SecurityManager â˜ƒ = System.getSecurityManager();
      this.group = â˜ƒ != null ? â˜ƒ.getThreadGroup() : Thread.currentThread().getThreadGroup();
      this.namePrefix = â˜ƒ + "-";
   }

   public Thread newThread(Runnable var1) {
      Thread â˜ƒ = new Thread(this.group, â˜ƒ, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
      â˜ƒ.setUncaughtExceptionHandler((var1x, var2x) -> {
         LOGGER.error("Caught exception in thread {} from {}", var1x, â˜ƒ);
         LOGGER.error("", var2x);
      });
      if (â˜ƒ.getPriority() != 5) {
         â˜ƒ.setPriority(5);
      }

      return â˜ƒ;
   }
}
