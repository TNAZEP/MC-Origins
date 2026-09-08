package net.minecraft.network.rcon;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RConThreadClient extends RConThreadBase {
   private static final Logger field_164005_h = LogManager.getLogger();
   private boolean field_72657_g;
   private Socket field_72659_h;
   private final byte[] field_72660_i = new byte[1460];
   private final String field_72658_j;

   RConThreadClient(IServer var1, Socket var2) {
      super(☃, "RCON Client");
      this.field_72659_h = ☃;

      try {
         this.field_72659_h.setSoTimeout(0);
      } catch (Exception var4) {
         this.field_72619_a = false;
      }

      this.field_72658_j = ☃.func_71330_a("rcon.password", "");
      this.func_72609_b("Rcon connection from: " + ☃.getInetAddress());
   }

   public void run() {
      try {
         try {
            while(this.field_72619_a) {
               BufferedInputStream ☃ = new BufferedInputStream(this.field_72659_h.getInputStream());
               int ☃x = ☃.read(this.field_72660_i, 0, 1460);
               if (10 > ☃x) {
                  return;
               }

               int ☃ = 0;
               int ☃x = RConUtils.func_72665_b(this.field_72660_i, 0, ☃x);
               if (☃x != ☃x - 4) {
                  return;
               }

               ☃ += 4;
               int ☃ = RConUtils.func_72665_b(this.field_72660_i, ☃, ☃x);
               ☃ += 4;
               int ☃x = RConUtils.func_72662_b(this.field_72660_i, ☃);
               ☃ += 4;
               switch(☃x) {
                  case 2:
                     if (this.field_72657_g) {
                        String ☃xx = RConUtils.func_72661_a(this.field_72660_i, ☃, ☃x);

                        try {
                           this.func_72655_a(☃, this.field_72617_b.func_71252_i(☃xx));
                        } catch (Exception var16) {
                           this.func_72655_a(☃, "Error executing: " + ☃xx + " (" + var16.getMessage() + ")");
                        }
                        break;
                     }

                     this.func_72656_f();
                     break;
                  case 3:
                     String ☃xx = RConUtils.func_72661_a(this.field_72660_i, ☃, ☃x);
                     ☃ += ☃xx.length();
                     if (!☃xx.isEmpty() && ☃xx.equals(this.field_72658_j)) {
                        this.field_72657_g = true;
                        this.func_72654_a(☃, 2, "");
                        break;
                     }

                     this.field_72657_g = false;
                     this.func_72656_f();
                     break;
                  default:
                     this.func_72655_a(☃, String.format("Unknown request %s", Integer.toHexString(☃x)));
               }
            }

            return;
         } catch (SocketTimeoutException var17) {
         } catch (IOException var18) {
         } catch (Exception var19) {
            field_164005_h.error("Exception whilst parsing RCON input", var19);
         }
      } finally {
         this.func_72653_g();
      }
   }

   private void func_72654_a(int var1, int var2, String var3) throws IOException {
      ByteArrayOutputStream ☃ = new ByteArrayOutputStream(1248);
      DataOutputStream ☃x = new DataOutputStream(☃);
      byte[] ☃xx = ☃.getBytes("UTF-8");
      ☃x.writeInt(Integer.reverseBytes(☃xx.length + 10));
      ☃x.writeInt(Integer.reverseBytes(☃));
      ☃x.writeInt(Integer.reverseBytes(☃));
      ☃x.write(☃xx);
      ☃x.write(0);
      ☃x.write(0);
      this.field_72659_h.getOutputStream().write(☃.toByteArray());
   }

   private void func_72656_f() throws IOException {
      this.func_72654_a(-1, 2, "");
   }

   private void func_72655_a(int var1, String var2) throws IOException {
      int ☃ = ☃.length();

      do {
         int ☃x = 4096 <= ☃ ? 4096 : ☃;
         this.func_72654_a(☃, 0, ☃.substring(0, ☃x));
         ☃ = ☃.substring(☃x);
         ☃ = ☃.length();
      } while(0 != ☃);
   }

   private void func_72653_g() {
      if (null != this.field_72659_h) {
         try {
            this.field_72659_h.close();
         } catch (IOException var2) {
            this.func_72606_c("IO: " + var2.getMessage());
         }

         this.field_72659_h = null;
      }
   }
}
