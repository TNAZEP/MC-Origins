package net.minecraft.world.phys.shapes;

import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public final class DiscreteCubeMerger implements IndexMerger {
   private final CubePointRange result;
   private final int firstDiv;
   private final int secondDiv;

   DiscreteCubeMerger(int var1, int var2) {
      this.result = new CubePointRange((int)Shapes.lcm(â˜ƒ, â˜ƒ));
      int â˜ƒ = IntMath.gcd(â˜ƒ, â˜ƒ);
      this.firstDiv = â˜ƒ / â˜ƒ;
      this.secondDiv = â˜ƒ / â˜ƒ;
   }

   @Override
   public boolean forMergedIndexes(IndexMerger.IndexConsumer var1) {
      int â˜ƒ = this.result.size() - 1;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         if (!â˜ƒ.merge(â˜ƒx / this.secondDiv, â˜ƒx / this.firstDiv, â˜ƒx)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int size() {
      return this.result.size();
   }

   @Override
   public DoubleList getList() {
      return this.result;
   }
}
