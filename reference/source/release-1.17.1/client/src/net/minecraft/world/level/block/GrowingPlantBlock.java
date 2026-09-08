package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingPlantBlock extends Block {
   protected final Direction growthDirection;
   protected final boolean scheduleFluidTicks;
   protected final VoxelShape shape;

   protected GrowingPlantBlock(BlockBehaviour.Properties var1, Direction var2, VoxelShape var3, boolean var4) {
      super(â˜ƒ);
      this.growthDirection = â˜ƒ;
      this.shape = â˜ƒ;
      this.scheduleFluidTicks = â˜ƒ;
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos().relative(this.growthDirection));
      return !â˜ƒ.is(this.getHeadBlock()) && !â˜ƒ.is(this.getBodyBlock()) ? this.getStateForPlacement(â˜ƒ.getLevel()) : this.getBodyBlock().defaultBlockState();
   }

   public BlockState getStateForPlacement(LevelAccessor var1) {
      return this.defaultBlockState();
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockPos â˜ƒ = â˜ƒ.relative(this.growthDirection.getOpposite());
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (!this.canAttachTo(â˜ƒx)) {
         return false;
      } else {
         return â˜ƒx.is(this.getHeadBlock()) || â˜ƒx.is(this.getBodyBlock()) || â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, this.growthDirection);
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.destroyBlock(â˜ƒ, true);
      }
   }

   protected boolean canAttachTo(BlockState var1) {
      return true;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.shape;
   }

   protected abstract GrowingPlantHeadBlock getHeadBlock();

   protected abstract Block getBodyBlock();
}
