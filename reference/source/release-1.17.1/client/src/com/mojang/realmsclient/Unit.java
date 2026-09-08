package com.mojang.realmsclient;

import java.util.Locale;

public enum Unit {
   B,
   KB,
   MB,
   GB;

   private static final int BASE_UNIT = 1024;

   public static Unit getLargest(long var0) {
      if (â˜ƒ < 1024L) {
         return B;
      } else {
         try {
            int â˜ƒ = (int)(Math.log((double)â˜ƒ) / Math.log(1024.0));
            String â˜ƒx = String.valueOf("KMGTPE".charAt(â˜ƒ - 1));
            return valueOf(â˜ƒx + "B");
         } catch (Exception var4) {
            return GB;
         }
      }
   }

   public static double convertTo(long var0, Unit var2) {
      return â˜ƒ == B ? (double)â˜ƒ : (double)â˜ƒ / Math.pow(1024.0, (double)â˜ƒ.ordinal());
   }

   public static String humanReadable(long var0) {
      int â˜ƒ = 1024;
      if (â˜ƒ < 1024L) {
         return â˜ƒ + " B";
      } else {
         int â˜ƒ = (int)(Math.log((double)â˜ƒ) / Math.log(1024.0));
         String â˜ƒx = "KMGTPE".charAt(â˜ƒ - 1) + "";
         return String.format(Locale.ROOT, "%.1f %sB", (double)â˜ƒ / Math.pow(1024.0, (double)â˜ƒ), â˜ƒx);
      }
   }

   public static String humanReadable(long var0, Unit var2) {
      return String.format("%." + (â˜ƒ == GB ? "1" : "0") + "f %s", convertTo(â˜ƒ, â˜ƒ), â˜ƒ.name());
   }
}
