package net.minecraft.util.math.shapes;

import java.util.BitSet;
import net.minecraft.util.EnumFacing;

public final class VoxelShapePartBitSet extends VoxelShapePart {
   private final BitSet field_197853_e;
   private int field_199630_f;
   private int field_199631_g;
   private int field_199632_h;
   private int field_199633_i;
   private int field_199634_j;
   private int field_199635_k;

   public VoxelShapePartBitSet(int var1, int var2, int var3) {
      this(☃, ☃, ☃, ☃, ☃, ☃, 0, 0, 0);
   }

   public VoxelShapePartBitSet(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      super(☃, ☃, ☃);
      this.field_197853_e = new BitSet(☃ * ☃ * ☃);
      this.field_199630_f = ☃;
      this.field_199631_g = ☃;
      this.field_199632_h = ☃;
      this.field_199633_i = ☃;
      this.field_199634_j = ☃;
      this.field_199635_k = ☃;
   }

   public VoxelShapePartBitSet(VoxelShapePart var1) {
      super(☃.field_197838_b, ☃.field_197839_c, ☃.field_197840_d);
      if (☃ instanceof VoxelShapePartBitSet) {
         this.field_197853_e = (BitSet)((VoxelShapePartBitSet)☃).field_197853_e.clone();
      } else {
         this.field_197853_e = new BitSet(this.field_197838_b * this.field_197839_c * this.field_197840_d);

         for(int ☃ = 0; ☃ < this.field_197838_b; ++☃) {
            for(int ☃x = 0; ☃x < this.field_197839_c; ++☃x) {
               for(int ☃xx = 0; ☃xx < this.field_197840_d; ++☃xx) {
                  if (☃.func_197835_b(☃, ☃x, ☃xx)) {
                     this.field_197853_e.set(this.func_197848_a(☃, ☃x, ☃xx));
                  }
               }
            }
         }
      }

      this.field_199630_f = ☃.func_199623_a(EnumFacing.Axis.X);
      this.field_199631_g = ☃.func_199623_a(EnumFacing.Axis.Y);
      this.field_199632_h = ☃.func_199623_a(EnumFacing.Axis.Z);
      this.field_199633_i = ☃.func_199624_b(EnumFacing.Axis.X);
      this.field_199634_j = ☃.func_199624_b(EnumFacing.Axis.Y);
      this.field_199635_k = ☃.func_199624_b(EnumFacing.Axis.Z);
   }

   protected int func_197848_a(int var1, int var2, int var3) {
      return (☃ * this.field_197839_c + ☃) * this.field_197840_d + ☃;
   }

   @Override
   public boolean func_197835_b(int var1, int var2, int var3) {
      return this.field_197853_e.get(this.func_197848_a(☃, ☃, ☃));
   }

   @Override
   public void func_199625_a(int var1, int var2, int var3, boolean var4, boolean var5) {
      this.field_197853_e.set(this.func_197848_a(☃, ☃, ☃), ☃);
      if (☃ && ☃) {
         this.field_199630_f = Math.min(this.field_199630_f, ☃);
         this.field_199631_g = Math.min(this.field_199631_g, ☃);
         this.field_199632_h = Math.min(this.field_199632_h, ☃);
         this.field_199633_i = Math.max(this.field_199633_i, ☃ + 1);
         this.field_199634_j = Math.max(this.field_199634_j, ☃ + 1);
         this.field_199635_k = Math.max(this.field_199635_k, ☃ + 1);
      }
   }

   @Override
   public boolean func_197830_a() {
      return this.field_197853_e.isEmpty();
   }

   @Override
   public int func_199623_a(EnumFacing.Axis var1) {
      return ☃.func_196052_a(this.field_199630_f, this.field_199631_g, this.field_199632_h);
   }

   @Override
   public int func_199624_b(EnumFacing.Axis var1) {
      return ☃.func_196052_a(this.field_199633_i, this.field_199634_j, this.field_199635_k);
   }

   @Override
   protected boolean func_197833_a(int var1, int var2, int var3, int var4) {
      if (☃ < 0 || ☃ < 0 || ☃ < 0) {
         return false;
      } else if (☃ < this.field_197838_b && ☃ < this.field_197839_c && ☃ <= this.field_197840_d) {
         return this.field_197853_e.nextClearBit(this.func_197848_a(☃, ☃, ☃)) >= this.func_197848_a(☃, ☃, ☃);
      } else {
         return false;
      }
   }

   @Override
   protected void func_197834_a(int var1, int var2, int var3, int var4, boolean var5) {
      this.field_197853_e.set(this.func_197848_a(☃, ☃, ☃), this.func_197848_a(☃, ☃, ☃), ☃);
   }

   static VoxelShapePartBitSet func_197852_a(
      VoxelShapePart var0, VoxelShapePart var1, IDoubleListMerger var2, IDoubleListMerger var3, IDoubleListMerger var4, IBooleanFunction var5
   ) {
      VoxelShapePartBitSet ☃ = new VoxelShapePartBitSet(☃.func_212435_a().size() - 1, ☃.func_212435_a().size() - 1, ☃.func_212435_a().size() - 1);
      int[] ☃x = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
      ☃.func_197855_a((var7x, var8, var9) -> {
         boolean[] ☃ = new boolean[]{false};
         boolean ☃x = ☃.func_197855_a((var10x, var11x, var12) -> {
            boolean[] ☃ = new boolean[]{false};
            boolean ☃x = ☃.func_197855_a((var12x, var13x, var14x) -> {
               boolean ☃ = ☃.apply(☃.func_197818_c(var7x, var10x, var12x), ☃.func_197818_c(var8, var11x, var13x));
               if (☃) {
                  ☃.field_197853_e.set(☃.func_197848_a(var9, var12, var14x));
                  ☃[2] = Math.min(☃[2], var14x);
                  ☃[5] = Math.max(☃[5], var14x);
                  ☃[0] = true;
               }

               return true;
            });
            if (☃[0]) {
               ☃[1] = Math.min(☃[1], var12);
               ☃[4] = Math.max(☃[4], var12);
               ☃[0] = true;
            }

            return ☃x;
         });
         if (☃[0]) {
            ☃[0] = Math.min(☃[0], var9);
            ☃[3] = Math.max(☃[3], var9);
         }

         return ☃x;
      });
      ☃.field_199630_f = ☃x[0];
      ☃.field_199631_g = ☃x[1];
      ☃.field_199632_h = ☃x[2];
      ☃.field_199633_i = ☃x[3] + 1;
      ☃.field_199634_j = ☃x[4] + 1;
      ☃.field_199635_k = ☃x[5] + 1;
      return ☃;
   }
}
