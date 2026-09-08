package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DeadBushBlock extends BushBlock {
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

   protected DeadBushBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.is(Blocks.SAND)
         || â˜ƒ.is(Blocks.RED_SAND)
         || â˜ƒ.is(Blocks.TERRACOTTA)
         || â˜ƒ.is(Blocks.WHITE_TERRACOTTA)
         || â˜ƒ.is(Blocks.ORANGE_TERRACOTTA)
         || â˜ƒ.is(Blocks.MAGENTA_TERRACOTTA)
         || â˜ƒ.is(Blocks.LIGHT_BLUE_TERRACOTTA)
         || â˜ƒ.is(Blocks.YELLOW_TERRACOTTA)
         || â˜ƒ.is(Blocks.LIME_TERRACOTTA)
         || â˜ƒ.is(Blocks.PINK_TERRACOTTA)
         || â˜ƒ.is(Blocks.GRAY_TERRACOTTA)
         || â˜ƒ.is(Blocks.LIGHT_GRAY_TERRACOTTA)
         || â˜ƒ.is(Blocks.CYAN_TERRACOTTA)
         || â˜ƒ.is(Blocks.PURPLE_TERRACOTTA)
         || â˜ƒ.is(Blocks.BLUE_TERRACOTTA)
         || â˜ƒ.is(Blocks.BROWN_TERRACOTTA)
         || â˜ƒ.is(Blocks.GREEN_TERRACOTTA)
         || â˜ƒ.is(Blocks.RED_TERRACOTTA)
         || â˜ƒ.is(Blocks.BLACK_TERRACOTTA)
         || â˜ƒ.is(BlockTags.DIRT);
   }
}
