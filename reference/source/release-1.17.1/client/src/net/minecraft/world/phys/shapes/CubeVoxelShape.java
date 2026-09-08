package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

public final class CubeVoxelShape extends VoxelShape {
   protected CubeVoxelShape(DiscreteVoxelShape var1) {
      super(â˜ƒ);
   }

   @Override
   protected DoubleList getCoords(Direction.Axis var1) {
      return new CubePointRange(this.shape.getSize(â˜ƒ));
   }

   @Override
   protected int findIndex(Direction.Axis var1, double var2) {
      int â˜ƒ = this.shape.getSize(â˜ƒ);
      return Mth.floor(Mth.clamp(â˜ƒ * (double)â˜ƒ, -1.0, (double)â˜ƒ));
   }
}
