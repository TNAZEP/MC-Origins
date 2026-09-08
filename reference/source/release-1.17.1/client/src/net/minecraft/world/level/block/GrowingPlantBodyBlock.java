package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingPlantBodyBlock extends GrowingPlantBlock implements BonemealableBlock {
   protected GrowingPlantBodyBlock(BlockBehaviour.Properties var1, Direction var2, VoxelShape var3, boolean var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected BlockState updateHeadAfterConvertedFromBody(BlockState var1, BlockState var2) {
      return â˜ƒ;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ == this.growthDirection.getOpposite() && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }

      GrowingPlantHeadBlock â˜ƒ = this.getHeadBlock();
      if (â˜ƒ == this.growthDirection && !â˜ƒ.is(this) && !â˜ƒ.is(â˜ƒ)) {
         return this.updateHeadAfterConvertedFromBody(â˜ƒ, â˜ƒ.getStateForPlacement(â˜ƒ));
      } else {
         if (this.scheduleFluidTicks) {
            â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
         }

         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return new ItemStack(this.getHeadBlock());
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      Optional<BlockPos> â˜ƒ = this.getHeadPos(â˜ƒ, â˜ƒ, â˜ƒ.getBlock());
      return â˜ƒ.isPresent() && this.getHeadBlock().canGrowInto(â˜ƒ.getBlockState(((BlockPos)â˜ƒ.get()).relative(this.growthDirection)));
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      Optional<BlockPos> â˜ƒ = this.getHeadPos(â˜ƒ, â˜ƒ, â˜ƒ.getBlock());
      if (â˜ƒ.isPresent()) {
         BlockState â˜ƒx = â˜ƒ.getBlockState((BlockPos)â˜ƒ.get());
         ((GrowingPlantHeadBlock)â˜ƒx.getBlock()).performBonemeal(â˜ƒ, â˜ƒ, (BlockPos)â˜ƒ.get(), â˜ƒx);
      }
   }

   private Optional<BlockPos> getHeadPos(BlockGetter var1, BlockPos var2, Block var3) {
      return BlockUtil.getTopConnectedBlock(â˜ƒ, â˜ƒ, â˜ƒ, this.growthDirection, this.getHeadBlock());
   }

   @Override
   public boolean canBeReplaced(BlockState var1, BlockPlaceContext var2) {
      boolean â˜ƒ = super.canBeReplaced(â˜ƒ, â˜ƒ);
      return â˜ƒ && â˜ƒ.getItemInHand().is(this.getHeadBlock().asItem()) ? false : â˜ƒ;
   }

   @Override
   protected Block getBodyBlock() {
      return this;
   }
}
