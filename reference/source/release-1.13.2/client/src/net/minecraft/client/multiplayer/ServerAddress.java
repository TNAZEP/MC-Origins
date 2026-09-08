package net.minecraft.client.multiplayer;

import java.net.IDN;
import java.util.Hashtable;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class ServerAddress {
   private final String field_78866_a;
   private final int field_78865_b;

   private ServerAddress(String var1, int var2) {
      this.field_78866_a = ☃;
      this.field_78865_b = ☃;
   }

   public String func_78861_a() {
      try {
         return IDN.toASCII(this.field_78866_a);
      } catch (IllegalArgumentException var2) {
         return "";
      }
   }

   public int func_78864_b() {
      return this.field_78865_b;
   }

   public static ServerAddress func_78860_a(String var0) {
      if (☃ == null) {
         return null;
      } else {
         String[] ☃ = ☃.split(":");
         if (☃.startsWith("[")) {
            int ☃x = ☃.indexOf("]");
            if (☃x > 0) {
               String ☃xx = ☃.substring(1, ☃x);
               String ☃xxx = ☃.substring(☃x + 1).trim();
               if (☃xxx.startsWith(":") && !☃xxx.isEmpty()) {
                  ☃xxx = ☃xxx.substring(1);
                  ☃ = new String[]{☃xx, ☃xxx};
               } else {
                  ☃ = new String[]{☃xx};
               }
            }
         }

         if (☃.length > 2) {
            ☃ = new String[]{☃};
         }

         String ☃ = ☃[0];
         int ☃x = ☃.length > 1 ? func_78862_a(☃[1], 25565) : 25565;
         if (☃x == 25565) {
            String[] ☃xx = func_78863_b(☃);
            ☃ = ☃xx[0];
            ☃x = func_78862_a(☃xx[1], 25565);
         }

         return new ServerAddress(☃, ☃x);
      }
   }

   private static String[] func_78863_b(String var0) {
      try {
         String ☃ = "com.sun.jndi.dns.DnsContextFactory";
         Class.forName("com.sun.jndi.dns.DnsContextFactory");
         Hashtable<String, String> ☃x = new Hashtable();
         ☃x.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
         ☃x.put("java.naming.provider.url", "dns:");
         ☃x.put("com.sun.jndi.dns.timeout.retries", "1");
         DirContext ☃xx = new InitialDirContext(☃x);
         Attributes ☃xxx = ☃xx.getAttributes("_minecraft._tcp." + ☃, new String[]{"SRV"});
         String[] ☃xxxx = ☃xxx.get("srv").get().toString().split(" ", 4);
         return new String[]{☃xxxx[3], ☃xxxx[2]};
      } catch (Throwable var6) {
         return new String[]{☃, Integer.toString(25565)};
      }
   }

   private static int func_78862_a(String var0, int var1) {
      try {
         return Integer.parseInt(☃.trim());
      } catch (Exception var3) {
         return ☃;
      }
   }
}
