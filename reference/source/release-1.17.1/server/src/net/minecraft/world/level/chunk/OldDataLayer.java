package net.minecraft.world.level.chunk;

public class OldDataLayer {
   public final byte[] data;
   private final int depthBits;
   private final int depthBitsPlusFour;

   public OldDataLayer(byte[] var1, int var2) {
      this.data = â˜ƒ;
      this.depthBits = â˜ƒ;
      this.depthBitsPlusFour = â˜ƒ + 4;
   }

   public int get(int var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ << this.depthBitsPlusFour | â˜ƒ << this.depthBits | â˜ƒ;
      int â˜ƒx = â˜ƒ >> 1;
      int â˜ƒxx = â˜ƒ & 1;
      return â˜ƒxx == 0 ? this.data[â˜ƒx] & 15 : this.data[â˜ƒx] >> 4 & 15;
   }
}
