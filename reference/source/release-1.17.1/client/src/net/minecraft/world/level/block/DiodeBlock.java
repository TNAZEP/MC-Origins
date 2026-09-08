package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.TickPriority;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class DiodeBlock extends HorizontalDirectionalBlock {
   protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

   protected DiodeBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return canSupportRigidBlock(â˜ƒ, â˜ƒ.below());
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!this.isLocked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         boolean â˜ƒ = â˜ƒ.getValue(POWERED);
         boolean â˜ƒx = this.shouldTurnOn(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ && !â˜ƒx) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(false)), 2);
         } else if (!â˜ƒ) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(true)), 2);
            if (!â˜ƒx) {
               â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, this.getDelay(â˜ƒ), TickPriority.VERY_HIGH);
            }
         }
      }
   }

   @Override
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getSignal(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      if (!â˜ƒ.getValue(POWERED)) {
         return 0;
      } else {
         return â˜ƒ.getValue(FACING) == â˜ƒ ? this.getOutputSignal(â˜ƒ, â˜ƒ, â˜ƒ) : 0;
      }
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         this.checkTickOnNeighbor(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         BlockEntity â˜ƒ = â˜ƒ.hasBlockEntity() ? â˜ƒ.getBlockEntity(â˜ƒ) : null;
         dropResources(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.removeBlock(â˜ƒ, false);

         for(Direction â˜ƒx : Direction.values()) {
            â˜ƒ.updateNeighborsAt(â˜ƒ.relative(â˜ƒx), this);
         }
      }
   }

   protected void checkTickOnNeighbor(Level var1, BlockPos var2, BlockState var3) {
      if (!this.isLocked(â˜ƒ, â˜ƒ, â˜ƒ)) {
         boolean â˜ƒ = â˜ƒ.getValue(POWERED);
         boolean â˜ƒx = this.shouldTurnOn(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ != â˜ƒx && !â˜ƒ.getBlockTicks().willTickThisTick(â˜ƒ, this)) {
            TickPriority â˜ƒxx = TickPriority.HIGH;
            if (this.shouldPrioritize(â˜ƒ, â˜ƒ, â˜ƒ)) {
               â˜ƒxx = TickPriority.EXTREMELY_HIGH;
            } else if (â˜ƒ) {
               â˜ƒxx = TickPriority.VERY_HIGH;
            }

            â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, this.getDelay(â˜ƒ), â˜ƒxx);
         }
      }
   }

   public boolean isLocked(LevelReader var1, BlockPos var2, BlockState var3) {
      return false;
   }

   protected boolean shouldTurnOn(Level var1, BlockPos var2, BlockState var3) {
      return this.getInputSignal(â˜ƒ, â˜ƒ, â˜ƒ) > 0;
   }

   protected int getInputSignal(Level var1, BlockPos var2, BlockState var3) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ);
      int â˜ƒxx = â˜ƒ.getSignal(â˜ƒx, â˜ƒ);
      if (â˜ƒxx >= 15) {
         return â˜ƒxx;
      } else {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒx);
         return Math.max(â˜ƒxx, â˜ƒ.is(Blocks.REDSTONE_WIRE) ? â˜ƒ.getValue(RedStoneWireBlock.POWER) : 0);
      }
   }

   protected int getAlternateSignal(LevelReader var1, BlockPos var2, BlockState var3) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      Direction â˜ƒx = â˜ƒ.getClockWise();
      Direction â˜ƒxx = â˜ƒ.getCounterClockWise();
      return Math.max(this.getAlternateSignalAt(â˜ƒ, â˜ƒ.relative(â˜ƒx), â˜ƒx), this.getAlternateSignalAt(â˜ƒ, â˜ƒ.relative(â˜ƒxx), â˜ƒxx));
   }

   protected int getAlternateSignalAt(LevelReader var1, BlockPos var2, Direction var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (this.isAlternateInput(â˜ƒ)) {
         if (â˜ƒ.is(Blocks.REDSTONE_BLOCK)) {
            return 15;
         } else {
            return â˜ƒ.is(Blocks.REDSTONE_WIRE) ? â˜ƒ.getValue(RedStoneWireBlock.POWER) : â˜ƒ.getDirectSignal(â˜ƒ, â˜ƒ);
         }
      } else {
         return 0;
      }
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(FACING, â˜ƒ.getHorizontalDirection().getOpposite());
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      if (this.shouldTurnOn(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      this.updateNeighborsInFront(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ && !â˜ƒ.is(â˜ƒ.getBlock())) {
         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.updateNeighborsInFront(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected void updateNeighborsInFront(Level var1, BlockPos var2, BlockState var3) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ.getOpposite());
      â˜ƒ.neighborChanged(â˜ƒx, this, â˜ƒ);
      â˜ƒ.updateNeighborsAtExceptFromFacing(â˜ƒx, this, â˜ƒ);
   }

   protected boolean isAlternateInput(BlockState var1) {
      return â˜ƒ.isSignalSource();
   }

   protected int getOutputSignal(BlockGetter var1, BlockPos var2, BlockState var3) {
      return 15;
   }

   public static boolean isDiode(BlockState var0) {
      return â˜ƒ.getBlock() instanceof DiodeBlock;
   }

   public boolean shouldPrioritize(BlockGetter var1, BlockPos var2, BlockState var3) {
      Direction â˜ƒ = ((Direction)â˜ƒ.getValue(FACING)).getOpposite();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒ));
      return isDiode(â˜ƒx) && â˜ƒx.getValue(FACING) != â˜ƒ;
   }

   protected abstract int getDelay(BlockState var1);
}
