package net.minecraft.server;

import java.io.OutputStream;
import net.minecraft.util.LoggingPrintStream;

public class DebugLoggingPrintStream extends LoggingPrintStream {
   public DebugLoggingPrintStream(String var1, OutputStream var2) {
      super(☃, ☃);
   }

   @Override
   protected void func_179882_a(String var1) {
      StackTraceElement[] ☃ = Thread.currentThread().getStackTrace();
      StackTraceElement ☃x = ☃[Math.min(3, ☃.length)];
      field_179884_a.info("[{}]@.({}:{}): {}", this.field_179883_b, ☃x.getFileName(), ☃x.getLineNumber(), ☃);
   }
}
