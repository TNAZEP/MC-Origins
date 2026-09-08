package net.minecraft.server.rcon;

import java.nio.charset.StandardCharsets;

public class PktUtils {
   public static final int MAX_PACKET_SIZE = 1460;
   public static final char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   public static String stringFromByteArray(byte[] var0, int var1, int var2) {
      int â˜ƒ = â˜ƒ - 1;
      int â˜ƒx = â˜ƒ > â˜ƒ ? â˜ƒ : â˜ƒ;

      while(0 != â˜ƒ[â˜ƒx] && â˜ƒx < â˜ƒ) {
         ++â˜ƒx;
      }

      return new String(â˜ƒ, â˜ƒ, â˜ƒx - â˜ƒ, StandardCharsets.UTF_8);
   }

   public static int intFromByteArray(byte[] var0, int var1) {
      return intFromByteArray(â˜ƒ, â˜ƒ, â˜ƒ.length);
   }

   public static int intFromByteArray(byte[] var0, int var1, int var2) {
      return 0 > â˜ƒ - â˜ƒ - 4 ? 0 : â˜ƒ[â˜ƒ + 3] << 24 | (â˜ƒ[â˜ƒ + 2] & 0xFF) << 16 | (â˜ƒ[â˜ƒ + 1] & 0xFF) << 8 | â˜ƒ[â˜ƒ] & 0xFF;
   }

   public static int intFromNetworkByteArray(byte[] var0, int var1, int var2) {
      return 0 > â˜ƒ - â˜ƒ - 4 ? 0 : â˜ƒ[â˜ƒ] << 24 | (â˜ƒ[â˜ƒ + 1] & 0xFF) << 16 | (â˜ƒ[â˜ƒ + 2] & 0xFF) << 8 | â˜ƒ[â˜ƒ + 3] & 0xFF;
   }

   public static String toHexString(byte var0) {
      return "" + HEX_CHAR[(â˜ƒ & 240) >>> 4] + HEX_CHAR[â˜ƒ & 15];
   }
}
