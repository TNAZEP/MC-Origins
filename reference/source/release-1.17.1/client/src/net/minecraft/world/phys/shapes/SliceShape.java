package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Direction;

public class SliceShape extends VoxelShape {
   private final VoxelShape delegate;
   private final Direction.Axis axis;
   private static final DoubleList SLICE_COORDS = new CubePointRange(1);

   public SliceShape(VoxelShape var1, Direction.Axis var2, int var3) {
      super(makeSlice(â˜ƒ.shape, â˜ƒ, â˜ƒ));
      this.delegate = â˜ƒ;
      this.axis = â˜ƒ;
   }

   private static DiscreteVoxelShape makeSlice(DiscreteVoxelShape var0, Direction.Axis var1, int var2) {
      return new SubShape(
         â˜ƒ,
         â˜ƒ.choose(â˜ƒ, 0, 0),
         â˜ƒ.choose(0, â˜ƒ, 0),
         â˜ƒ.choose(0, 0, â˜ƒ),
         â˜ƒ.choose(â˜ƒ + 1, â˜ƒ.xSize, â˜ƒ.xSize),
         â˜ƒ.choose(â˜ƒ.ySize, â˜ƒ + 1, â˜ƒ.ySize),
         â˜ƒ.choose(â˜ƒ.zSize, â˜ƒ.zSize, â˜ƒ + 1)
      );
   }

   @Override
   protected DoubleList getCoords(Direction.Axis var1) {
      return â˜ƒ == this.axis ? SLICE_COORDS : this.delegate.getCoords(â˜ƒ);
   }
}
