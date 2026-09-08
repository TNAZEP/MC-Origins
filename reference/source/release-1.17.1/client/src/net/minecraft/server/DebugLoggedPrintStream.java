package net.minecraft.server;

import java.io.OutputStream;

public class DebugLoggedPrintStream extends LoggedPrintStream {
   public DebugLoggedPrintStream(String var1, OutputStream var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void logLine(String var1) {
      StackTraceElement[] â˜ƒ = Thread.currentThread().getStackTrace();
      StackTraceElement â˜ƒx = â˜ƒ[Math.min(3, â˜ƒ.length)];
      LOGGER.info("[{}]@.({}:{}): {}", this.name, â˜ƒx.getFileName(), â˜ƒx.getLineNumber(), â˜ƒ);
   }
}
