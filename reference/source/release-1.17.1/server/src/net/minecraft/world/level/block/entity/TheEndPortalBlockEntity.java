package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class TheEndPortalBlockEntity extends BlockEntity {
   protected TheEndPortalBlockEntity(BlockEntityType<?> var1, BlockPos var2, BlockState var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public TheEndPortalBlockEntity(BlockPos var1, BlockState var2) {
      this(BlockEntityType.END_PORTAL, â˜ƒ, â˜ƒ);
   }

   public boolean shouldRenderFace(Direction var1) {
      return â˜ƒ.getAxis() == Direction.Axis.Y;
   }
}
