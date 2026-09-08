package net.minecraft.client.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadLanServerPing extends Thread {
   private static final AtomicInteger field_148658_a = new AtomicInteger(0);
   private static final Logger field_148657_b = LogManager.getLogger();
   private final String field_77528_b;
   private final DatagramSocket field_77529_c;
   private boolean field_77526_d = true;
   private final String field_77527_e;

   public ThreadLanServerPing(String var1, String var2) throws IOException {
      super("LanServerPinger #" + field_148658_a.incrementAndGet());
      this.field_77528_b = ☃;
      this.field_77527_e = ☃;
      this.setDaemon(true);
      this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_148657_b));
      this.field_77529_c = new DatagramSocket();
   }

   public void run() {
      String ☃ = func_77525_a(this.field_77528_b, this.field_77527_e);
      byte[] ☃x = ☃.getBytes(StandardCharsets.UTF_8);

      while(!this.isInterrupted() && this.field_77526_d) {
         try {
            InetAddress ☃xx = InetAddress.getByName("224.0.2.60");
            DatagramPacket ☃xxx = new DatagramPacket(☃x, ☃x.length, ☃xx, 4445);
            this.field_77529_c.send(☃xxx);
         } catch (IOException var6) {
            field_148657_b.warn("LanServerPinger: {}", var6.getMessage());
            break;
         }

         try {
            sleep(1500L);
         } catch (InterruptedException var5) {
         }
      }
   }

   public void interrupt() {
      super.interrupt();
      this.field_77526_d = false;
   }

   public static String func_77525_a(String var0, String var1) {
      return "[MOTD]" + ☃ + "[/MOTD][AD]" + ☃ + "[/AD]";
   }

   public static String func_77524_a(String var0) {
      int ☃ = ☃.indexOf("[MOTD]");
      if (☃ < 0) {
         return "missing no";
      } else {
         int ☃ = ☃.indexOf("[/MOTD]", ☃ + "[MOTD]".length());
         return ☃ < ☃ ? "missing no" : ☃.substring(☃ + "[MOTD]".length(), ☃);
      }
   }

   public static String func_77523_b(String var0) {
      int ☃ = ☃.indexOf("[/MOTD]");
      if (☃ < 0) {
         return null;
      } else {
         int ☃ = ☃.indexOf("[/MOTD]", ☃ + "[/MOTD]".length());
         if (☃ >= 0) {
            return null;
         } else {
            int ☃ = ☃.indexOf("[AD]", ☃ + "[/MOTD]".length());
            if (☃ < 0) {
               return null;
            } else {
               int ☃ = ☃.indexOf("[/AD]", ☃ + "[AD]".length());
               return ☃ < ☃ ? null : ☃.substring(☃ + "[AD]".length(), ☃);
            }
         }
      }
   }
}
