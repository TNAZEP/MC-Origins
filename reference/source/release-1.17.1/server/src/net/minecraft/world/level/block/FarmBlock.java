package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FarmBlock extends Block {
   public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
   protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);
   public static final int MAX_MOISTURE = 7;

   protected FarmBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, Integer.valueOf(0)));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ == Direction.UP && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.above());
      return !â˜ƒ.getMaterial().isSolid() || â˜ƒ.getBlock() instanceof FenceGateBlock || â˜ƒ.getBlock() instanceof MovingPistonBlock;
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return !this.defaultBlockState().canSurvive(â˜ƒ.getLevel(), â˜ƒ.getClickedPos()) ? Blocks.DIRT.defaultBlockState() : super.getStateForPlacement(â˜ƒ);
   }

   @Override
   public boolean useShapeForLightOcclusion(BlockState var1) {
      return true;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         turnToDirt(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      int â˜ƒ = â˜ƒ.getValue(MOISTURE);
      if (!isNearWater(â˜ƒ, â˜ƒ) && !â˜ƒ.isRainingAt(â˜ƒ.above())) {
         if (â˜ƒ > 0) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(MOISTURE, Integer.valueOf(â˜ƒ - 1)), 2);
         } else if (!isUnderCrops(â˜ƒ, â˜ƒ)) {
            turnToDirt(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      } else if (â˜ƒ < 7) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(MOISTURE, Integer.valueOf(7)), 2);
      }
   }

   @Override
   public void fallOn(Level var1, BlockState var2, BlockPos var3, Entity var4, float var5) {
      if (!â˜ƒ.isClientSide
         && â˜ƒ.random.nextFloat() < â˜ƒ - 0.5F
         && â˜ƒ instanceof LivingEntity
         && (â˜ƒ instanceof Player || â˜ƒ.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING))
         && â˜ƒ.getBbWidth() * â˜ƒ.getBbWidth() * â˜ƒ.getBbHeight() > 0.512F) {
         turnToDirt(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      super.fallOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void turnToDirt(BlockState var0, Level var1, BlockPos var2) {
      â˜ƒ.setBlockAndUpdate(â˜ƒ, pushEntitiesUp(â˜ƒ, Blocks.DIRT.defaultBlockState(), â˜ƒ, â˜ƒ));
   }

   private static boolean isUnderCrops(BlockGetter var0, BlockPos var1) {
      Block â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.above()).getBlock();
      return â˜ƒ instanceof CropBlock || â˜ƒ instanceof StemBlock || â˜ƒ instanceof AttachedStemBlock;
   }

   private static boolean isNearWater(LevelReader var0, BlockPos var1) {
      for(BlockPos â˜ƒ : BlockPos.betweenClosed(â˜ƒ.offset(-4, 0, -4), â˜ƒ.offset(4, 1, 4))) {
         if (â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(MOISTURE);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
