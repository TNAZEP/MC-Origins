package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BubbleColumnBlock extends Block implements BucketPickup {
   public static final BooleanProperty DRAG_DOWN = BlockStateProperties.DRAG;
   private static final int CHECK_PERIOD = 5;

   public BubbleColumnBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(DRAG_DOWN, Boolean.valueOf(true)));
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.above());
      if (â˜ƒ.isAir()) {
         â˜ƒ.onAboveBubbleCol(â˜ƒ.getValue(DRAG_DOWN));
         if (!â˜ƒ.isClientSide) {
            ServerLevel â˜ƒx = (ServerLevel)â˜ƒ;

            for(int â˜ƒxx = 0; â˜ƒxx < 2; ++â˜ƒxx) {
               â˜ƒx.sendParticles(
                  ParticleTypes.SPLASH,
                  (double)â˜ƒ.getX() + â˜ƒ.random.nextDouble(),
                  (double)(â˜ƒ.getY() + 1),
                  (double)â˜ƒ.getZ() + â˜ƒ.random.nextDouble(),
                  1,
                  0.0,
                  0.0,
                  0.0,
                  1.0
               );
               â˜ƒx.sendParticles(
                  ParticleTypes.BUBBLE,
                  (double)â˜ƒ.getX() + â˜ƒ.random.nextDouble(),
                  (double)(â˜ƒ.getY() + 1),
                  (double)â˜ƒ.getZ() + â˜ƒ.random.nextDouble(),
                  1,
                  0.0,
                  0.01,
                  0.0,
                  0.2
               );
            }
         }
      } else {
         â˜ƒ.onInsideBubbleColumn(â˜ƒ.getValue(DRAG_DOWN));
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      updateColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getBlockState(â˜ƒ.below()));
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return Fluids.WATER.getSource(false);
   }

   public static void updateColumn(LevelAccessor var0, BlockPos var1, BlockState var2) {
      updateColumn(â˜ƒ, â˜ƒ, â˜ƒ.getBlockState(â˜ƒ), â˜ƒ);
   }

   public static void updateColumn(LevelAccessor var0, BlockPos var1, BlockState var2, BlockState var3) {
      if (canExistIn(â˜ƒ)) {
         BlockState â˜ƒ = getColumnState(â˜ƒ);
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
         BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.mutable().move(Direction.UP);

         while(canExistIn(â˜ƒ.getBlockState(â˜ƒx))) {
            if (!â˜ƒ.setBlock(â˜ƒx, â˜ƒ, 2)) {
               return;
            }

            â˜ƒx.move(Direction.UP);
         }
      }
   }

   private static boolean canExistIn(BlockState var0) {
      return â˜ƒ.is(Blocks.BUBBLE_COLUMN) || â˜ƒ.is(Blocks.WATER) && â˜ƒ.getFluidState().getAmount() >= 8 && â˜ƒ.getFluidState().isSource();
   }

   private static BlockState getColumnState(BlockState var0) {
      if (â˜ƒ.is(Blocks.BUBBLE_COLUMN)) {
         return â˜ƒ;
      } else if (â˜ƒ.is(Blocks.SOUL_SAND)) {
         return Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, Boolean.valueOf(false));
      } else {
         return â˜ƒ.is(Blocks.MAGMA_BLOCK)
            ? Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, Boolean.valueOf(true))
            : Blocks.WATER.defaultBlockState();
      }
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      double â˜ƒ = (double)â˜ƒ.getX();
      double â˜ƒx = (double)â˜ƒ.getY();
      double â˜ƒxx = (double)â˜ƒ.getZ();
      if (â˜ƒ.getValue(DRAG_DOWN)) {
         â˜ƒ.addAlwaysVisibleParticle(ParticleTypes.CURRENT_DOWN, â˜ƒ + 0.5, â˜ƒx + 0.8, â˜ƒxx, 0.0, 0.0, 0.0);
         if (â˜ƒ.nextInt(200) == 0) {
            â˜ƒ.playLocalSound(
               â˜ƒ,
               â˜ƒx,
               â˜ƒxx,
               SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,
               SoundSource.BLOCKS,
               0.2F + â˜ƒ.nextFloat() * 0.2F,
               0.9F + â˜ƒ.nextFloat() * 0.15F,
               false
            );
         }
      } else {
         â˜ƒ.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP, â˜ƒ + 0.5, â˜ƒx, â˜ƒxx + 0.5, 0.0, 0.04, 0.0);
         â˜ƒ.addAlwaysVisibleParticle(
            ParticleTypes.BUBBLE_COLUMN_UP, â˜ƒ + (double)â˜ƒ.nextFloat(), â˜ƒx + (double)â˜ƒ.nextFloat(), â˜ƒxx + (double)â˜ƒ.nextFloat(), 0.0, 0.04, 0.0
         );
         if (â˜ƒ.nextInt(200) == 0) {
            â˜ƒ.playLocalSound(
               â˜ƒ,
               â˜ƒx,
               â˜ƒxx,
               SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT,
               SoundSource.BLOCKS,
               0.2F + â˜ƒ.nextFloat() * 0.2F,
               0.9F + â˜ƒ.nextFloat() * 0.15F,
               false
            );
         }
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ) || â˜ƒ == Direction.DOWN || â˜ƒ == Direction.UP && !â˜ƒ.is(Blocks.BUBBLE_COLUMN) && canExistIn(â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 5);
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
      return â˜ƒ.is(Blocks.BUBBLE_COLUMN) || â˜ƒ.is(Blocks.MAGMA_BLOCK) || â˜ƒ.is(Blocks.SOUL_SAND);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return Shapes.empty();
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.INVISIBLE;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(DRAG_DOWN);
   }

   @Override
   public ItemStack pickupBlock(LevelAccessor var1, BlockPos var2, BlockState var3) {
      â˜ƒ.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 11);
      return new ItemStack(Items.WATER_BUCKET);
   }

   @Override
   public Optional<SoundEvent> getPickupSound() {
      return Fluids.WATER.getPickupSound();
   }
}
