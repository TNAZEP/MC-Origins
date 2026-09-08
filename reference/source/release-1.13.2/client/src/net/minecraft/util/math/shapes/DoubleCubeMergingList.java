package net.minecraft.util.math.shapes;

import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public final class DoubleCubeMergingList implements IDoubleListMerger {
   private final DoubleRangeList field_212436_a;
   private final int field_197859_a;
   private final int field_197860_b;
   private final int field_197861_c;

   DoubleCubeMergingList(int var1, int var2) {
      this.field_212436_a = new DoubleRangeList((int)VoxelShapes.func_197877_a(☃, ☃));
      this.field_197859_a = ☃;
      this.field_197860_b = ☃;
      this.field_197861_c = IntMath.gcd(☃, ☃);
   }

   @Override
   public boolean func_197855_a(IDoubleListMerger.Consumer var1) {
      int ☃ = this.field_197859_a / this.field_197861_c;
      int ☃x = this.field_197860_b / this.field_197861_c;

      for(int ☃xx = 0; ☃xx <= this.field_212436_a.size(); ++☃xx) {
         if (!☃.merge(☃xx / ☃x, ☃xx / ☃, ☃xx)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public DoubleList func_212435_a() {
      return this.field_212436_a;
   }
}
