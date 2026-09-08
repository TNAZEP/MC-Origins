package net.minecraft.network.rcon;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.util.Util;

public class RConThreadQuery extends RConThreadBase {
   private long field_72629_g;
   private int field_72636_h;
   private final int field_72637_i;
   private final int field_72634_j;
   private final String field_72635_k;
   private final String field_72632_l;
   private DatagramSocket field_72633_m;
   private final byte[] field_72630_n = new byte[1460];
   private DatagramPacket field_72631_o;
   private final Map<SocketAddress, String> field_72644_p;
   private String field_72643_q;
   private String field_72642_r;
   private final Map<SocketAddress, RConThreadQuery.Auth> field_72641_s;
   private final long field_72640_t;
   private final RConOutputStream field_72639_u;
   private long field_72638_v;

   public RConThreadQuery(IServer var1) {
      super(☃, "Query Listener");
      this.field_72636_h = ☃.func_71327_a("query.port", 0);
      this.field_72642_r = ☃.func_71277_t();
      this.field_72637_i = ☃.func_71234_u();
      this.field_72635_k = ☃.func_71274_v();
      this.field_72634_j = ☃.func_71275_y();
      this.field_72632_l = ☃.func_71270_I();
      this.field_72638_v = 0L;
      this.field_72643_q = "0.0.0.0";
      if (!this.field_72642_r.isEmpty() && !this.field_72643_q.equals(this.field_72642_r)) {
         this.field_72643_q = this.field_72642_r;
      } else {
         this.field_72642_r = "0.0.0.0";

         try {
            InetAddress ☃ = InetAddress.getLocalHost();
            this.field_72643_q = ☃.getHostAddress();
         } catch (UnknownHostException var3) {
            this.func_72606_c("Unable to determine local host IP, please set server-ip in '" + ☃.func_71329_c() + "' : " + var3.getMessage());
         }
      }

      if (0 == this.field_72636_h) {
         this.field_72636_h = this.field_72637_i;
         this.func_72609_b("Setting default query port to " + this.field_72636_h);
         ☃.func_71328_a("query.port", this.field_72636_h);
         ☃.func_71328_a("debug", false);
         ☃.func_71326_a();
      }

      this.field_72644_p = Maps.newHashMap();
      this.field_72639_u = new RConOutputStream(1460);
      this.field_72641_s = Maps.newHashMap();
      this.field_72640_t = new Date().getTime();
   }

   private void func_72620_a(byte[] var1, DatagramPacket var2) throws IOException {
      this.field_72633_m.send(new DatagramPacket(☃, ☃.length, ☃.getSocketAddress()));
   }

   private boolean func_72621_a(DatagramPacket var1) throws IOException {
      byte[] ☃ = ☃.getData();
      int ☃x = ☃.getLength();
      SocketAddress ☃xx = ☃.getSocketAddress();
      this.func_72607_a("Packet len " + ☃x + " [" + ☃xx + "]");
      if (3 <= ☃x && -2 == ☃[0] && -3 == ☃[1]) {
         this.func_72607_a("Packet '" + RConUtils.func_72663_a(☃[2]) + "' [" + ☃xx + "]");
         switch(☃[2]) {
            case 0:
               if (!this.func_72627_c(☃)) {
                  this.func_72607_a("Invalid challenge [" + ☃xx + "]");
                  return false;
               } else if (15 == ☃x) {
                  this.func_72620_a(this.func_72624_b(☃), ☃);
                  this.func_72607_a("Rules [" + ☃xx + "]");
               } else {
                  RConOutputStream ☃xxx = new RConOutputStream(1460);
                  ☃xxx.func_72667_a(0);
                  ☃xxx.func_72670_a(this.func_72625_a(☃.getSocketAddress()));
                  ☃xxx.func_72671_a(this.field_72635_k);
                  ☃xxx.func_72671_a("SMP");
                  ☃xxx.func_72671_a(this.field_72632_l);
                  ☃xxx.func_72671_a(Integer.toString(this.func_72603_d()));
                  ☃xxx.func_72671_a(Integer.toString(this.field_72634_j));
                  ☃xxx.func_72668_a((short)this.field_72637_i);
                  ☃xxx.func_72671_a(this.field_72643_q);
                  this.func_72620_a(☃xxx.func_72672_a(), ☃);
                  this.func_72607_a("Status [" + ☃xx + "]");
               }
            default:
               return true;
            case 9:
               this.func_72622_d(☃);
               this.func_72607_a("Challenge [" + ☃xx + "]");
               return true;
         }
      } else {
         this.func_72607_a("Invalid packet [" + ☃xx + "]");
         return false;
      }
   }

   private byte[] func_72624_b(DatagramPacket var1) throws IOException {
      long ☃ = Util.func_211177_b();
      if (☃ < this.field_72638_v + 5000L) {
         byte[] ☃x = this.field_72639_u.func_72672_a();
         byte[] ☃xx = this.func_72625_a(☃.getSocketAddress());
         ☃x[1] = ☃xx[0];
         ☃x[2] = ☃xx[1];
         ☃x[3] = ☃xx[2];
         ☃x[4] = ☃xx[3];
         return ☃x;
      } else {
         this.field_72638_v = ☃;
         this.field_72639_u.func_72669_b();
         this.field_72639_u.func_72667_a(0);
         this.field_72639_u.func_72670_a(this.func_72625_a(☃.getSocketAddress()));
         this.field_72639_u.func_72671_a("splitnum");
         this.field_72639_u.func_72667_a(128);
         this.field_72639_u.func_72667_a(0);
         this.field_72639_u.func_72671_a("hostname");
         this.field_72639_u.func_72671_a(this.field_72635_k);
         this.field_72639_u.func_72671_a("gametype");
         this.field_72639_u.func_72671_a("SMP");
         this.field_72639_u.func_72671_a("game_id");
         this.field_72639_u.func_72671_a("MINECRAFT");
         this.field_72639_u.func_72671_a("version");
         this.field_72639_u.func_72671_a(this.field_72617_b.func_71249_w());
         this.field_72639_u.func_72671_a("plugins");
         this.field_72639_u.func_72671_a(this.field_72617_b.func_71258_A());
         this.field_72639_u.func_72671_a("map");
         this.field_72639_u.func_72671_a(this.field_72632_l);
         this.field_72639_u.func_72671_a("numplayers");
         this.field_72639_u.func_72671_a("" + this.func_72603_d());
         this.field_72639_u.func_72671_a("maxplayers");
         this.field_72639_u.func_72671_a("" + this.field_72634_j);
         this.field_72639_u.func_72671_a("hostport");
         this.field_72639_u.func_72671_a("" + this.field_72637_i);
         this.field_72639_u.func_72671_a("hostip");
         this.field_72639_u.func_72671_a(this.field_72643_q);
         this.field_72639_u.func_72667_a(0);
         this.field_72639_u.func_72667_a(1);
         this.field_72639_u.func_72671_a("player_");
         this.field_72639_u.func_72667_a(0);
         String[] ☃ = this.field_72617_b.func_71213_z();

         for(String ☃x : ☃) {
            this.field_72639_u.func_72671_a(☃x);
         }

         this.field_72639_u.func_72667_a(0);
         return this.field_72639_u.func_72672_a();
      }
   }

   private byte[] func_72625_a(SocketAddress var1) {
      return ((RConThreadQuery.Auth)this.field_72641_s.get(☃)).func_72591_c();
   }

   private Boolean func_72627_c(DatagramPacket var1) {
      SocketAddress ☃ = ☃.getSocketAddress();
      if (!this.field_72641_s.containsKey(☃)) {
         return false;
      } else {
         byte[] ☃ = ☃.getData();
         return ((RConThreadQuery.Auth)this.field_72641_s.get(☃)).func_72592_a() != RConUtils.func_72664_c(☃, 7, ☃.getLength()) ? false : true;
      }
   }

   private void func_72622_d(DatagramPacket var1) throws IOException {
      RConThreadQuery.Auth ☃ = new RConThreadQuery.Auth(☃);
      this.field_72641_s.put(☃.getSocketAddress(), ☃);
      this.func_72620_a(☃.func_72594_b(), ☃);
   }

   private void func_72628_f() {
      if (this.field_72619_a) {
         long ☃ = Util.func_211177_b();
         if (☃ >= this.field_72629_g + 30000L) {
            this.field_72629_g = ☃;
            Iterator<Entry<SocketAddress, RConThreadQuery.Auth>> ☃x = this.field_72641_s.entrySet().iterator();

            while(☃x.hasNext()) {
               Entry<SocketAddress, RConThreadQuery.Auth> ☃xx = (Entry)☃x.next();
               if (((RConThreadQuery.Auth)☃xx.getValue()).func_72593_a(☃)) {
                  ☃x.remove();
               }
            }
         }
      }
   }

   public void run() {
      this.func_72609_b("Query running on " + this.field_72642_r + ":" + this.field_72636_h);
      this.field_72629_g = Util.func_211177_b();
      this.field_72631_o = new DatagramPacket(this.field_72630_n, this.field_72630_n.length);

      try {
         while(this.field_72619_a) {
            try {
               this.field_72633_m.receive(this.field_72631_o);
               this.func_72628_f();
               this.func_72621_a(this.field_72631_o);
            } catch (SocketTimeoutException var7) {
               this.func_72628_f();
            } catch (PortUnreachableException var8) {
            } catch (IOException var9) {
               this.func_72623_a(var9);
            }
         }
      } finally {
         this.func_72611_e();
      }
   }

   @Override
   public void func_72602_a() {
      if (!this.field_72619_a) {
         if (0 < this.field_72636_h && 65535 >= this.field_72636_h) {
            if (this.func_72626_g()) {
               super.func_72602_a();
            }
         } else {
            this.func_72606_c("Invalid query port " + this.field_72636_h + " found in '" + this.field_72617_b.func_71329_c() + "' (queries disabled)");
         }
      }
   }

   private void func_72623_a(Exception var1) {
      if (this.field_72619_a) {
         this.func_72606_c("Unexpected exception, buggy JRE? (" + ☃ + ")");
         if (!this.func_72626_g()) {
            this.func_72610_d("Failed to recover from buggy JRE, shutting down!");
            this.field_72619_a = false;
         }
      }
   }

   private boolean func_72626_g() {
      try {
         this.field_72633_m = new DatagramSocket(this.field_72636_h, InetAddress.getByName(this.field_72642_r));
         this.func_72601_a(this.field_72633_m);
         this.field_72633_m.setSoTimeout(500);
         return true;
      } catch (SocketException var2) {
         this.func_72606_c("Unable to initialise query system on " + this.field_72642_r + ":" + this.field_72636_h + " (Socket): " + var2.getMessage());
      } catch (UnknownHostException var3) {
         this.func_72606_c("Unable to initialise query system on " + this.field_72642_r + ":" + this.field_72636_h + " (Unknown Host): " + var3.getMessage());
      } catch (Exception var4) {
         this.func_72606_c("Unable to initialise query system on " + this.field_72642_r + ":" + this.field_72636_h + " (E): " + var4.getMessage());
      }

      return false;
   }

   class Auth {
      private final long field_72598_b = new Date().getTime();
      private final int field_72599_c;
      private final byte[] field_72596_d;
      private final byte[] field_72597_e;
      private final String field_72595_f;

      public Auth(DatagramPacket var2) {
         byte[] ☃ = ☃.getData();
         this.field_72596_d = new byte[4];
         this.field_72596_d[0] = ☃[3];
         this.field_72596_d[1] = ☃[4];
         this.field_72596_d[2] = ☃[5];
         this.field_72596_d[3] = ☃[6];
         this.field_72595_f = new String(this.field_72596_d, StandardCharsets.UTF_8);
         this.field_72599_c = new Random().nextInt(16777216);
         this.field_72597_e = String.format("\t%s%d\u0000", this.field_72595_f, this.field_72599_c).getBytes(StandardCharsets.UTF_8);
      }

      public Boolean func_72593_a(long var1) {
         return this.field_72598_b < ☃;
      }

      public int func_72592_a() {
         return this.field_72599_c;
      }

      public byte[] func_72594_b() {
         return this.field_72597_e;
      }

      public byte[] func_72591_c() {
         return this.field_72596_d;
      }
   }
}
