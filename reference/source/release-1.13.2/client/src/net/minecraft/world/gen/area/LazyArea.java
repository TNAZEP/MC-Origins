package net.minecraft.world.gen.area;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import net.minecraft.world.gen.layer.traits.IPixelTransformer;

public final class LazyArea implements IArea {
   private final IPixelTransformer field_202681_a;
   private final Long2IntLinkedOpenHashMap field_202682_b;
   private final int field_202683_c;
   private final AreaDimension field_202684_d;

   public LazyArea(Long2IntLinkedOpenHashMap var1, int var2, AreaDimension var3, IPixelTransformer var4) {
      this.field_202682_b = ☃;
      this.field_202683_c = ☃;
      this.field_202684_d = ☃;
      this.field_202681_a = ☃;
   }

   @Override
   public int func_202678_a(int var1, int var2) {
      long ☃ = this.func_202679_b(☃, ☃);
      synchronized(this.field_202682_b) {
         int ☃x = this.field_202682_b.get(☃);
         if (☃x != Integer.MIN_VALUE) {
            return ☃x;
         } else {
            int ☃x = this.field_202681_a.apply(☃, ☃);
            this.field_202682_b.put(☃, ☃x);
            if (this.field_202682_b.size() > this.field_202683_c) {
               for(int ☃xx = 0; ☃xx < this.field_202683_c / 16; ++☃xx) {
                  this.field_202682_b.removeFirstInt();
               }
            }

            return ☃x;
         }
      }
   }

   private long func_202679_b(int var1, int var2) {
      long ☃ = 1L;
      ☃ <<= 26;
      ☃ |= (long)(☃ + this.field_202684_d.func_202690_a()) & 67108863L;
      ☃ <<= 26;
      return ☃ | (long)(☃ + this.field_202684_d.func_202691_b()) & 67108863L;
   }

   public int func_202680_a() {
      return this.field_202683_c;
   }
}
