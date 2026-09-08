package net.minecraft.world.level.chunk;

import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.VisibleForDebug;

public final class DataLayer {
   public static final int LAYER_COUNT = 16;
   public static final int LAYER_SIZE = 128;
   public static final int SIZE = 2048;
   private static final int NIBBLE_SIZE = 4;
   @Nullable
   protected byte[] data;

   public DataLayer() {
   }

   public DataLayer(byte[] var1) {
      this.data = â˜ƒ;
      if (â˜ƒ.length != 2048) {
         throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException("DataLayer should be 2048 bytes not: " + â˜ƒ.length));
      }
   }

   protected DataLayer(int var1) {
      this.data = new byte[â˜ƒ];
   }

   public int get(int var1, int var2, int var3) {
      return this.get(getIndex(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public void set(int var1, int var2, int var3, int var4) {
      this.set(getIndex(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
   }

   private static int getIndex(int var0, int var1, int var2) {
      return â˜ƒ << 8 | â˜ƒ << 4 | â˜ƒ;
   }

   private int get(int var1) {
      if (this.data == null) {
         return 0;
      } else {
         int â˜ƒ = getByteIndex(â˜ƒ);
         int â˜ƒx = getNibbleIndex(â˜ƒ);
         return this.data[â˜ƒ] >> 4 * â˜ƒx & 15;
      }
   }

   private void set(int var1, int var2) {
      if (this.data == null) {
         this.data = new byte[2048];
      }

      int â˜ƒ = getByteIndex(â˜ƒ);
      int â˜ƒx = getNibbleIndex(â˜ƒ);
      int â˜ƒxx = ~(15 << 4 * â˜ƒx);
      int â˜ƒxxx = (â˜ƒ & 15) << 4 * â˜ƒx;
      this.data[â˜ƒ] = (byte)(this.data[â˜ƒ] & â˜ƒxx | â˜ƒxxx);
   }

   private static int getNibbleIndex(int var0) {
      return â˜ƒ & 1;
   }

   private static int getByteIndex(int var0) {
      return â˜ƒ >> 1;
   }

   public byte[] getData() {
      if (this.data == null) {
         this.data = new byte[2048];
      }

      return this.data;
   }

   public DataLayer copy() {
      return this.data == null ? new DataLayer() : new DataLayer((byte[])this.data.clone());
   }

   public String toString() {
      StringBuilder â˜ƒ = new StringBuilder();

      for(int â˜ƒx = 0; â˜ƒx < 4096; ++â˜ƒx) {
         â˜ƒ.append(Integer.toHexString(this.get(â˜ƒx)));
         if ((â˜ƒx & 15) == 15) {
            â˜ƒ.append("\n");
         }

         if ((â˜ƒx & 0xFF) == 255) {
            â˜ƒ.append("\n");
         }
      }

      return â˜ƒ.toString();
   }

   @VisibleForDebug
   public String layerToString(int var1) {
      StringBuilder â˜ƒ = new StringBuilder();

      for(int â˜ƒx = 0; â˜ƒx < 256; ++â˜ƒx) {
         â˜ƒ.append(Integer.toHexString(this.get(â˜ƒx)));
         if ((â˜ƒx & 15) == 15) {
            â˜ƒ.append("\n");
         }
      }

      return â˜ƒ.toString();
   }

   public boolean isEmpty() {
      return this.data == null;
   }
}
