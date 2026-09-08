package net.minecraft.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;
import net.minecraft.Util;

public final class SerializableUUID {
   public static final Codec<UUID> CODEC = Codec.INT_STREAM
      .comapFlatMap(var0 -> Util.fixedSize(var0, 4).map(SerializableUUID::uuidFromIntArray), var0 -> Arrays.stream(uuidToIntArray(var0)));

   private SerializableUUID() {
   }

   public static UUID uuidFromIntArray(int[] var0) {
      return new UUID((long)â˜ƒ[0] << 32 | (long)â˜ƒ[1] & 4294967295L, (long)â˜ƒ[2] << 32 | (long)â˜ƒ[3] & 4294967295L);
   }

   public static int[] uuidToIntArray(UUID var0) {
      long â˜ƒ = â˜ƒ.getMostSignificantBits();
      long â˜ƒx = â˜ƒ.getLeastSignificantBits();
      return leastMostToIntArray(â˜ƒ, â˜ƒx);
   }

   private static int[] leastMostToIntArray(long var0, long var2) {
      return new int[]{(int)(â˜ƒ >> 32), (int)â˜ƒ, (int)(â˜ƒ >> 32), (int)â˜ƒ};
   }

   public static UUID readUUID(Dynamic<?> var0) {
      int[] â˜ƒ = â˜ƒ.asIntStream().toArray();
      if (â˜ƒ.length != 4) {
         throw new IllegalArgumentException("Could not read UUID. Expected int-array of length 4, got " + â˜ƒ.length + ".");
      } else {
         return uuidFromIntArray(â˜ƒ);
      }
   }
}
