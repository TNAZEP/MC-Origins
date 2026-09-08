package net.minecraft.network.rcon;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class RConThreadMain extends RConThreadBase {
   private int field_72647_g;
   private final int field_72651_h;
   private String field_72652_i;
   private ServerSocket field_72649_j;
   private final String field_72650_k;
   private Map<SocketAddress, RConThreadClient> field_72648_l;

   public RConThreadMain(IServer var1) {
      super(☃, "RCON Listener");
      this.field_72647_g = ☃.func_71327_a("rcon.port", 0);
      this.field_72650_k = ☃.func_71330_a("rcon.password", "");
      this.field_72652_i = ☃.func_71277_t();
      this.field_72651_h = ☃.func_71234_u();
      if (0 == this.field_72647_g) {
         this.field_72647_g = this.field_72651_h + 10;
         this.func_72609_b("Setting default rcon port to " + this.field_72647_g);
         ☃.func_71328_a("rcon.port", this.field_72647_g);
         if (this.field_72650_k.isEmpty()) {
            ☃.func_71328_a("rcon.password", "");
         }

         ☃.func_71326_a();
      }

      if (this.field_72652_i.isEmpty()) {
         this.field_72652_i = "0.0.0.0";
      }

      this.func_72646_f();
      this.field_72649_j = null;
   }

   private void func_72646_f() {
      this.field_72648_l = Maps.newHashMap();
   }

   private void func_72645_g() {
      Iterator<Entry<SocketAddress, RConThreadClient>> ☃ = this.field_72648_l.entrySet().iterator();

      while(☃.hasNext()) {
         Entry<SocketAddress, RConThreadClient> ☃x = (Entry)☃.next();
         if (!((RConThreadClient)☃x.getValue()).func_72613_c()) {
            ☃.remove();
         }
      }
   }

   public void run() {
      this.func_72609_b("RCON running on " + this.field_72652_i + ":" + this.field_72647_g);

      try {
         while(this.field_72619_a) {
            try {
               Socket ☃ = this.field_72649_j.accept();
               ☃.setSoTimeout(500);
               RConThreadClient ☃x = new RConThreadClient(this.field_72617_b, ☃);
               ☃x.func_72602_a();
               this.field_72648_l.put(☃.getRemoteSocketAddress(), ☃x);
               this.func_72645_g();
            } catch (SocketTimeoutException var7) {
               this.func_72645_g();
            } catch (IOException var8) {
               if (this.field_72619_a) {
                  this.func_72609_b("IO: " + var8.getMessage());
               }
            }
         }
      } finally {
         this.func_72608_b(this.field_72649_j);
      }
   }

   @Override
   public void func_72602_a() {
      if (this.field_72650_k.isEmpty()) {
         this.func_72606_c("No rcon password set in '" + this.field_72617_b.func_71329_c() + "', rcon disabled!");
      } else if (0 >= this.field_72647_g || 65535 < this.field_72647_g) {
         this.func_72606_c("Invalid rcon port " + this.field_72647_g + " found in '" + this.field_72617_b.func_71329_c() + "', rcon disabled!");
      } else if (!this.field_72619_a) {
         try {
            this.field_72649_j = new ServerSocket(this.field_72647_g, 0, InetAddress.getByName(this.field_72652_i));
            this.field_72649_j.setSoTimeout(500);
            super.func_72602_a();
         } catch (IOException var2) {
            this.func_72606_c("Unable to initialise rcon on " + this.field_72652_i + ":" + this.field_72647_g + " : " + var2.getMessage());
         }
      }
   }
}
