package net.minecraft.nbt;

public class NBTSizeTracker {
   public static final NBTSizeTracker field_152451_a = new NBTSizeTracker(0L) {
      @Override
      public void func_152450_a(long var1) {
      }
   };
   private final long field_152452_b;
   private long field_152453_c;

   public NBTSizeTracker(long var1) {
      this.field_152452_b = ☃;
   }

   public void func_152450_a(long var1) {
      this.field_152453_c += ☃ / 8L;
      if (this.field_152453_c > this.field_152452_b) {
         throw new RuntimeException(
            "Tried to read NBT tag that was too big; tried to allocate: " + this.field_152453_c + "bytes where max allowed: " + this.field_152452_b
         );
      }
   }
}
