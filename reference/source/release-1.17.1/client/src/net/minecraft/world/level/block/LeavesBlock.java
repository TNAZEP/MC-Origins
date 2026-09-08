package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LeavesBlock extends Block {
   public static final int DECAY_DISTANCE = 7;
   public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE;
   public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
   private static final int TICK_DELAY = 1;

   public LeavesBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, Integer.valueOf(7)).setValue(PERSISTENT, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape getBlockSupportShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return Shapes.empty();
   }

   @Override
   public boolean isRandomlyTicking(BlockState var1) {
      return â˜ƒ.getValue(DISTANCE) == 7 && !â˜ƒ.getValue(PERSISTENT);
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!â˜ƒ.getValue(PERSISTENT) && â˜ƒ.getValue(DISTANCE) == 7) {
         dropResources(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.removeBlock(â˜ƒ, false);
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      â˜ƒ.setBlock(â˜ƒ, updateDistance(â˜ƒ, â˜ƒ, â˜ƒ), 3);
   }

   @Override
   public int getLightBlock(BlockState var1, BlockGetter var2, BlockPos var3) {
      return 1;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      int â˜ƒ = getDistanceAt(â˜ƒ) + 1;
      if (â˜ƒ != 1 || â˜ƒ.getValue(DISTANCE) != â˜ƒ) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }

      return â˜ƒ;
   }

   private static BlockState updateDistance(BlockState var0, LevelAccessor var1, BlockPos var2) {
      int â˜ƒ = 7;
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

      for(Direction â˜ƒxx : Direction.values()) {
         â˜ƒx.setWithOffset(â˜ƒ, â˜ƒxx);
         â˜ƒ = Math.min(â˜ƒ, getDistanceAt(â˜ƒ.getBlockState(â˜ƒx)) + 1);
         if (â˜ƒ == 1) {
            break;
         }
      }

      return â˜ƒ.setValue(DISTANCE, Integer.valueOf(â˜ƒ));
   }

   private static int getDistanceAt(BlockState var0) {
      if (â˜ƒ.is(BlockTags.LOGS)) {
         return 0;
      } else {
         return â˜ƒ.getBlock() instanceof LeavesBlock ? â˜ƒ.getValue(DISTANCE) : 7;
      }
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.isRainingAt(â˜ƒ.above())) {
         if (â˜ƒ.nextInt(15) == 1) {
            BlockPos â˜ƒ = â˜ƒ.below();
            BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
            if (!â˜ƒx.canOcclude() || !â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP)) {
               double â˜ƒxx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble();
               double â˜ƒxxx = (double)â˜ƒ.getY() - 0.05;
               double â˜ƒxxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble();
               â˜ƒ.addParticle(ParticleTypes.DRIPPING_WATER, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, 0.0, 0.0, 0.0);
            }
         }
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(DISTANCE, PERSISTENT);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.valueOf(true)), â˜ƒ.getLevel(), â˜ƒ.getClickedPos());
   }
}
