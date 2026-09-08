package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LeverBlock extends FaceAttachedHorizontalDirectionalBlock {
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   protected static final int DEPTH = 6;
   protected static final int WIDTH = 6;
   protected static final int HEIGHT = 8;
   protected static final VoxelShape NORTH_AABB = Block.box(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
   protected static final VoxelShape SOUTH_AABB = Block.box(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
   protected static final VoxelShape WEST_AABB = Block.box(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
   protected static final VoxelShape EAST_AABB = Block.box(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);
   protected static final VoxelShape UP_AABB_Z = Block.box(5.0, 0.0, 4.0, 11.0, 6.0, 12.0);
   protected static final VoxelShape UP_AABB_X = Block.box(4.0, 0.0, 5.0, 12.0, 6.0, 11.0);
   protected static final VoxelShape DOWN_AABB_Z = Block.box(5.0, 10.0, 4.0, 11.0, 16.0, 12.0);
   protected static final VoxelShape DOWN_AABB_X = Block.box(4.0, 10.0, 5.0, 12.0, 16.0, 11.0);

   protected LeverBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false)).setValue(FACE, AttachFace.WALL)
      );
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      switch((AttachFace)â˜ƒ.getValue(FACE)) {
         case FLOOR:
            switch(((Direction)â˜ƒ.getValue(FACING)).getAxis()) {
               case X:
                  return UP_AABB_X;
               case Z:
               default:
                  return UP_AABB_Z;
            }
         case WALL:
            switch((Direction)â˜ƒ.getValue(FACING)) {
               case EAST:
                  return EAST_AABB;
               case WEST:
                  return WEST_AABB;
               case SOUTH:
                  return SOUTH_AABB;
               case NORTH:
               default:
                  return NORTH_AABB;
            }
         case CEILING:
         default:
            switch(((Direction)â˜ƒ.getValue(FACING)).getAxis()) {
               case X:
                  return DOWN_AABB_X;
               case Z:
               default:
                  return DOWN_AABB_Z;
            }
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.isClientSide) {
         BlockState â˜ƒ = â˜ƒ.cycle(POWERED);
         if (â˜ƒ.getValue(POWERED)) {
            makeParticle(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F);
         }

         return InteractionResult.SUCCESS;
      } else {
         BlockState â˜ƒ = this.pull(â˜ƒ, â˜ƒ, â˜ƒ);
         float â˜ƒx = â˜ƒ.getValue(POWERED) ? 0.6F : 0.5F;
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, â˜ƒx);
         â˜ƒ.gameEvent(â˜ƒ, â˜ƒ.getValue(POWERED) ? GameEvent.BLOCK_SWITCH : GameEvent.BLOCK_UNSWITCH, â˜ƒ);
         return InteractionResult.CONSUME;
      }
   }

   public BlockState pull(BlockState var1, Level var2, BlockPos var3) {
      â˜ƒ = â˜ƒ.cycle(POWERED);
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
      this.updateNeighbours(â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   private static void makeParticle(BlockState var0, LevelAccessor var1, BlockPos var2, float var3) {
      Direction â˜ƒ = ((Direction)â˜ƒ.getValue(FACING)).getOpposite();
      Direction â˜ƒx = getConnectedDirection(â˜ƒ).getOpposite();
      double â˜ƒxx = (double)â˜ƒ.getX() + 0.5 + 0.1 * (double)â˜ƒ.getStepX() + 0.2 * (double)â˜ƒx.getStepX();
      double â˜ƒxxx = (double)â˜ƒ.getY() + 0.5 + 0.1 * (double)â˜ƒ.getStepY() + 0.2 * (double)â˜ƒx.getStepY();
      double â˜ƒxxxx = (double)â˜ƒ.getZ() + 0.5 + 0.1 * (double)â˜ƒ.getStepZ() + 0.2 * (double)â˜ƒx.getStepZ();
      â˜ƒ.addParticle(new DustParticleOptions(DustParticleOptions.REDSTONE_PARTICLE_COLOR, â˜ƒ), â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, 0.0, 0.0, 0.0);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(POWERED) && â˜ƒ.nextFloat() < 0.25F) {
         makeParticle(â˜ƒ, â˜ƒ, â˜ƒ, 0.5F);
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ && !â˜ƒ.is(â˜ƒ.getBlock())) {
         if (â˜ƒ.getValue(POWERED)) {
            this.updateNeighbours(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWERED) ? 15 : 0;
   }

   @Override
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWERED) && getConnectedDirection(â˜ƒ) == â˜ƒ ? 15 : 0;
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   private void updateNeighbours(BlockState var1, Level var2, BlockPos var3) {
      â˜ƒ.updateNeighborsAt(â˜ƒ, this);
      â˜ƒ.updateNeighborsAt(â˜ƒ.relative(getConnectedDirection(â˜ƒ).getOpposite()), this);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACE, FACING, POWERED);
   }
}
