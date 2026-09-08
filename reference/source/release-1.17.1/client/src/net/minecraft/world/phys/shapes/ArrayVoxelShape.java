package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import net.minecraft.Util;
import net.minecraft.core.Direction;

public class ArrayVoxelShape extends VoxelShape {
   private final DoubleList xs;
   private final DoubleList ys;
   private final DoubleList zs;

   protected ArrayVoxelShape(DiscreteVoxelShape var1, double[] var2, double[] var3, double[] var4) {
      this(
         â˜ƒ,
         DoubleArrayList.wrap(Arrays.copyOf(â˜ƒ, â˜ƒ.getXSize() + 1)),
         DoubleArrayList.wrap(Arrays.copyOf(â˜ƒ, â˜ƒ.getYSize() + 1)),
         DoubleArrayList.wrap(Arrays.copyOf(â˜ƒ, â˜ƒ.getZSize() + 1))
      );
   }

   ArrayVoxelShape(DiscreteVoxelShape var1, DoubleList var2, DoubleList var3, DoubleList var4) {
      super(â˜ƒ);
      int â˜ƒ = â˜ƒ.getXSize() + 1;
      int â˜ƒx = â˜ƒ.getYSize() + 1;
      int â˜ƒxx = â˜ƒ.getZSize() + 1;
      if (â˜ƒ == â˜ƒ.size() && â˜ƒx == â˜ƒ.size() && â˜ƒxx == â˜ƒ.size()) {
         this.xs = â˜ƒ;
         this.ys = â˜ƒ;
         this.zs = â˜ƒ;
      } else {
         throw (IllegalArgumentException)Util.pauseInIde(
            new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape.")
         );
      }
   }

   @Override
   protected DoubleList getCoords(Direction.Axis var1) {
      switch(â˜ƒ) {
         case X:
            return this.xs;
         case Y:
            return this.ys;
         case Z:
            return this.zs;
         default:
            throw new IllegalArgumentException();
      }
   }
}
