package net.minecraft.network.rcon;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.DefaultUncaughtExceptionHandlerWithName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RConThreadBase implements Runnable {
   private static final Logger field_199680_h = LogManager.getLogger();
   private static final AtomicInteger field_164004_h = new AtomicInteger(0);
   protected boolean field_72619_a;
   protected IServer field_72617_b;
   protected final String field_164003_c;
   protected Thread field_72618_c;
   protected int field_72615_d = 5;
   protected List<DatagramSocket> field_72616_e = Lists.newArrayList();
   protected List<ServerSocket> field_72614_f = Lists.newArrayList();

   protected RConThreadBase(IServer var1, String var2) {
      this.field_72617_b = ☃;
      this.field_164003_c = ☃;
      if (this.field_72617_b.func_71239_B()) {
         this.func_72606_c("Debugging is enabled, performance maybe reduced!");
      }
   }

   public synchronized void func_72602_a() {
      this.field_72618_c = new Thread(this, this.field_164003_c + " #" + field_164004_h.incrementAndGet());
      this.field_72618_c.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandlerWithName(field_199680_h));
      this.field_72618_c.start();
      this.field_72619_a = true;
   }

   public boolean func_72613_c() {
      return this.field_72619_a;
   }

   protected void func_72607_a(String var1) {
      this.field_72617_b.func_71198_k(☃);
   }

   protected void func_72609_b(String var1) {
      this.field_72617_b.func_71244_g(☃);
   }

   protected void func_72606_c(String var1) {
      this.field_72617_b.func_71236_h(☃);
   }

   protected void func_72610_d(String var1) {
      this.field_72617_b.func_71201_j(☃);
   }

   protected int func_72603_d() {
      return this.field_72617_b.func_71233_x();
   }

   protected void func_72601_a(DatagramSocket var1) {
      this.func_72607_a("registerSocket: " + ☃);
      this.field_72616_e.add(☃);
   }

   protected boolean func_72604_a(DatagramSocket var1, boolean var2) {
      this.func_72607_a("closeSocket: " + ☃);
      if (null == ☃) {
         return false;
      } else {
         boolean ☃ = false;
         if (!☃.isClosed()) {
            ☃.close();
            ☃ = true;
         }

         if (☃) {
            this.field_72616_e.remove(☃);
         }

         return ☃;
      }
   }

   protected boolean func_72608_b(ServerSocket var1) {
      return this.func_72605_a(☃, true);
   }

   protected boolean func_72605_a(ServerSocket var1, boolean var2) {
      this.func_72607_a("closeSocket: " + ☃);
      if (null == ☃) {
         return false;
      } else {
         boolean ☃ = false;

         try {
            if (!☃.isClosed()) {
               ☃.close();
               ☃ = true;
            }
         } catch (IOException var5) {
            this.func_72606_c("IO: " + var5.getMessage());
         }

         if (☃) {
            this.field_72614_f.remove(☃);
         }

         return ☃;
      }
   }

   protected void func_72611_e() {
      this.func_72612_a(false);
   }

   protected void func_72612_a(boolean var1) {
      int ☃ = 0;

      for(DatagramSocket ☃x : this.field_72616_e) {
         if (this.func_72604_a(☃x, false)) {
            ++☃;
         }
      }

      this.field_72616_e.clear();

      for(ServerSocket ☃x : this.field_72614_f) {
         if (this.func_72605_a(☃x, false)) {
            ++☃;
         }
      }

      this.field_72614_f.clear();
      if (☃ && 0 < ☃) {
         this.func_72606_c("Force closed " + ☃ + " sockets");
      }
   }
}
