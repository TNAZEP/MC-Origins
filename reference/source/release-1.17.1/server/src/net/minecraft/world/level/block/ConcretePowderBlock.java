package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ConcretePowderBlock extends FallingBlock {
   private final BlockState concrete;

   public ConcretePowderBlock(Block var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.concrete = â˜ƒ.defaultBlockState();
   }

   @Override
   public void onLand(Level var1, BlockPos var2, BlockState var3, BlockState var4, FallingBlockEntity var5) {
      if (shouldSolidify(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.setBlock(â˜ƒ, this.concrete, 3);
      }
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockGetter â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      return shouldSolidify(â˜ƒ, â˜ƒx, â˜ƒxx) ? this.concrete : super.getStateForPlacement(â˜ƒ);
   }

   private static boolean shouldSolidify(BlockGetter var0, BlockPos var1, BlockState var2) {
      return canSolidify(â˜ƒ) || touchesLiquid(â˜ƒ, â˜ƒ);
   }

   private static boolean touchesLiquid(BlockGetter var0, BlockPos var1) {
      boolean â˜ƒ = false;
      BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.mutable();

      for(Direction â˜ƒxx : Direction.values()) {
         BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒx);
         if (â˜ƒxx != Direction.DOWN || canSolidify(â˜ƒxxx)) {
            â˜ƒx.setWithOffset(â˜ƒ, â˜ƒxx);
            â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒx);
            if (canSolidify(â˜ƒxxx) && !â˜ƒxxx.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒxx.getOpposite())) {
               â˜ƒ = true;
               break;
            }
         }
      }

      return â˜ƒ;
   }

   private static boolean canSolidify(BlockState var0) {
      return â˜ƒ.getFluidState().is(FluidTags.WATER);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return touchesLiquid(â˜ƒ, â˜ƒ) ? this.concrete : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getDustColor(BlockState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.getMapColor(â˜ƒ, â˜ƒ).col;
   }
}
