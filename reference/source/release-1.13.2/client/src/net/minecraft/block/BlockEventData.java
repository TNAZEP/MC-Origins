package net.minecraft.block;

import net.minecraft.util.math.BlockPos;

public class BlockEventData {
   private final BlockPos field_180329_a;
   private final Block field_151344_d;
   private final int field_151345_e;
   private final int field_151343_f;

   public BlockEventData(BlockPos var1, Block var2, int var3, int var4) {
      this.field_180329_a = ☃;
      this.field_151344_d = ☃;
      this.field_151345_e = ☃;
      this.field_151343_f = ☃;
   }

   public BlockPos func_180328_a() {
      return this.field_180329_a;
   }

   public Block func_151337_f() {
      return this.field_151344_d;
   }

   public int func_151339_d() {
      return this.field_151345_e;
   }

   public int func_151338_e() {
      return this.field_151343_f;
   }

   public boolean equals(Object var1) {
      if (!(☃ instanceof BlockEventData)) {
         return false;
      } else {
         BlockEventData ☃ = (BlockEventData)☃;
         return this.field_180329_a.equals(☃.field_180329_a)
            && this.field_151345_e == ☃.field_151345_e
            && this.field_151343_f == ☃.field_151343_f
            && this.field_151344_d == ☃.field_151344_d;
      }
   }

   public String toString() {
      return "TE(" + this.field_180329_a + ")," + this.field_151345_e + "," + this.field_151343_f + "," + this.field_151344_d;
   }
}
