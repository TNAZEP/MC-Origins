package net.minecraft.world.level.block.piston;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PistonBaseBlock extends DirectionalBlock {
   public static final BooleanProperty EXTENDED = BlockStateProperties.EXTENDED;
   public static final int TRIGGER_EXTEND = 0;
   public static final int TRIGGER_CONTRACT = 1;
   public static final int TRIGGER_DROP = 2;
   public static final float PLATFORM_THICKNESS = 4.0F;
   protected static final VoxelShape EAST_AABB = Block.box(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
   protected static final VoxelShape WEST_AABB = Block.box(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
   protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape UP_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
   protected static final VoxelShape DOWN_AABB = Block.box(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
   private final boolean isSticky;

   public PistonBaseBlock(boolean var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(EXTENDED, Boolean.valueOf(false)));
      this.isSticky = â˜ƒ;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      if (â˜ƒ.getValue(EXTENDED)) {
         switch((Direction)â˜ƒ.getValue(FACING)) {
            case DOWN:
               return DOWN_AABB;
            case UP:
            default:
               return UP_AABB;
            case NORTH:
               return NORTH_AABB;
            case SOUTH:
               return SOUTH_AABB;
            case WEST:
               return WEST_AABB;
            case EAST:
               return EAST_AABB;
         }
      } else {
         return Shapes.block();
      }
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      if (!â˜ƒ.isClientSide) {
         this.checkIfExtend(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (!â˜ƒ.isClientSide) {
         this.checkIfExtend(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         if (!â˜ƒ.isClientSide && â˜ƒ.getBlockEntity(â˜ƒ) == null) {
            this.checkIfExtend(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(FACING, â˜ƒ.getNearestLookingDirection().getOpposite()).setValue(EXTENDED, Boolean.valueOf(false));
   }

   private void checkIfExtend(Level var1, BlockPos var2, BlockState var3) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      boolean â˜ƒx = this.getNeighborSignal(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒx && !â˜ƒ.getValue(EXTENDED)) {
         if (new PistonStructureResolver(â˜ƒ, â˜ƒ, â˜ƒ, true).resolve()) {
            â˜ƒ.blockEvent(â˜ƒ, this, 0, â˜ƒ.get3DDataValue());
         }
      } else if (!â˜ƒx && â˜ƒ.getValue(EXTENDED)) {
         BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ, 2);
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
         int â˜ƒxx = 1;
         if (â˜ƒx.is(Blocks.MOVING_PISTON) && â˜ƒx.getValue(FACING) == â˜ƒ) {
            BlockEntity â˜ƒxxxx = â˜ƒ.getBlockEntity(â˜ƒ);
            if (â˜ƒxxxx instanceof PistonMovingBlockEntity â˜ƒxxx
               && â˜ƒxxx.isExtending()
               && (â˜ƒxxx.getProgress(0.0F) < 0.5F || â˜ƒ.getGameTime() == â˜ƒxxx.getLastTicked() || ((ServerLevel)â˜ƒ).isHandlingTick())) {
               â˜ƒxx = 2;
            }
         }

         â˜ƒ.blockEvent(â˜ƒ, this, â˜ƒxx, â˜ƒ.get3DDataValue());
      }
   }

   private boolean getNeighborSignal(Level var1, BlockPos var2, Direction var3) {
      for(Direction â˜ƒ : Direction.values()) {
         if (â˜ƒ != â˜ƒ && â˜ƒ.hasSignal(â˜ƒ.relative(â˜ƒ), â˜ƒ)) {
            return true;
         }
      }

      if (â˜ƒ.hasSignal(â˜ƒ, Direction.DOWN)) {
         return true;
      } else {
         BlockPos â˜ƒ = â˜ƒ.above();

         for(Direction â˜ƒx : Direction.values()) {
            if (â˜ƒx != Direction.DOWN && â˜ƒ.hasSignal(â˜ƒ.relative(â˜ƒx), â˜ƒx)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean triggerEvent(BlockState var1, Level var2, BlockPos var3, int var4, int var5) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      if (!â˜ƒ.isClientSide) {
         boolean â˜ƒx = this.getNeighborSignal(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒx && (â˜ƒ == 1 || â˜ƒ == 2)) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(EXTENDED, Boolean.valueOf(true)), 2);
            return false;
         }

         if (!â˜ƒx && â˜ƒ == 0) {
            return false;
         }
      }

      if (â˜ƒ == 0) {
         if (!this.moveBlocks(â˜ƒ, â˜ƒ, â˜ƒ, true)) {
            return false;
         }

         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(EXTENDED, Boolean.valueOf(true)), 67);
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5F, â˜ƒ.random.nextFloat() * 0.25F + 0.6F);
         â˜ƒ.gameEvent(GameEvent.PISTON_EXTEND, â˜ƒ);
      } else if (â˜ƒ == 1 || â˜ƒ == 2) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ.relative(â˜ƒ));
         if (â˜ƒ instanceof PistonMovingBlockEntity) {
            ((PistonMovingBlockEntity)â˜ƒ).finalTick();
         }

         BlockState â˜ƒ = Blocks.MOVING_PISTON
            .defaultBlockState()
            .setValue(MovingPistonBlock.FACING, â˜ƒ)
            .setValue(MovingPistonBlock.TYPE, this.isSticky ? PistonType.STICKY : PistonType.DEFAULT);
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 20);
         â˜ƒ.setBlockEntity(
            MovingPistonBlock.newMovingBlockEntity(â˜ƒ, â˜ƒ, this.defaultBlockState().setValue(FACING, Direction.from3DDataValue(â˜ƒ & 7)), â˜ƒ, false, true)
         );
         â˜ƒ.blockUpdated(â˜ƒ, â˜ƒ.getBlock());
         â˜ƒ.updateNeighbourShapes(â˜ƒ, â˜ƒ, 2);
         if (this.isSticky) {
            BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ.getStepX() * 2, â˜ƒ.getStepY() * 2, â˜ƒ.getStepZ() * 2);
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
            boolean â˜ƒxxx = false;
            if (â˜ƒxx.is(Blocks.MOVING_PISTON)) {
               BlockEntity â˜ƒxxxxx = â˜ƒ.getBlockEntity(â˜ƒx);
               if (â˜ƒxxxxx instanceof PistonMovingBlockEntity â˜ƒxxxx && â˜ƒxxxx.getDirection() == â˜ƒ && â˜ƒxxxx.isExtending()) {
                  â˜ƒxxxx.finalTick();
                  â˜ƒxxx = true;
               }
            }

            if (!â˜ƒxxx) {
               if (â˜ƒ != 1
                  || â˜ƒxx.isAir()
                  || !isPushable(â˜ƒxx, â˜ƒ, â˜ƒx, â˜ƒ.getOpposite(), false, â˜ƒ)
                  || â˜ƒxx.getPistonPushReaction() != PushReaction.NORMAL && !â˜ƒxx.is(Blocks.PISTON) && !â˜ƒxx.is(Blocks.STICKY_PISTON)) {
                  â˜ƒ.removeBlock(â˜ƒ.relative(â˜ƒ), false);
               } else {
                  this.moveBlocks(â˜ƒ, â˜ƒ, â˜ƒ, false);
               }
            }
         } else {
            â˜ƒ.removeBlock(â˜ƒ.relative(â˜ƒ), false);
         }

         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.5F, â˜ƒ.random.nextFloat() * 0.15F + 0.6F);
         â˜ƒ.gameEvent(GameEvent.PISTON_CONTRACT, â˜ƒ);
      }

      return true;
   }

   public static boolean isPushable(BlockState var0, Level var1, BlockPos var2, Direction var3, boolean var4, Direction var5) {
      if (â˜ƒ.getY() < â˜ƒ.getMinBuildHeight() || â˜ƒ.getY() > â˜ƒ.getMaxBuildHeight() - 1 || !â˜ƒ.getWorldBorder().isWithinBounds(â˜ƒ)) {
         return false;
      } else if (â˜ƒ.isAir()) {
         return true;
      } else if (â˜ƒ.is(Blocks.OBSIDIAN) || â˜ƒ.is(Blocks.CRYING_OBSIDIAN) || â˜ƒ.is(Blocks.RESPAWN_ANCHOR)) {
         return false;
      } else if (â˜ƒ == Direction.DOWN && â˜ƒ.getY() == â˜ƒ.getMinBuildHeight()) {
         return false;
      } else if (â˜ƒ == Direction.UP && â˜ƒ.getY() == â˜ƒ.getMaxBuildHeight() - 1) {
         return false;
      } else {
         if (!â˜ƒ.is(Blocks.PISTON) && !â˜ƒ.is(Blocks.STICKY_PISTON)) {
            if (â˜ƒ.getDestroySpeed(â˜ƒ, â˜ƒ) == -1.0F) {
               return false;
            }

            switch(â˜ƒ.getPistonPushReaction()) {
               case BLOCK:
                  return false;
               case DESTROY:
                  return â˜ƒ;
               case PUSH_ONLY:
                  return â˜ƒ == â˜ƒ;
            }
         } else if (â˜ƒ.getValue(EXTENDED)) {
            return false;
         }

         return !â˜ƒ.hasBlockEntity();
      }
   }

   private boolean moveBlocks(Level var1, BlockPos var2, Direction var3, boolean var4) {
      BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ);
      if (!â˜ƒ && â˜ƒ.getBlockState(â˜ƒ).is(Blocks.PISTON_HEAD)) {
         â˜ƒ.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 20);
      }

      PistonStructureResolver â˜ƒ = new PistonStructureResolver(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (!â˜ƒ.resolve()) {
         return false;
      } else {
         Map<BlockPos, BlockState> â˜ƒ = Maps.<BlockPos, BlockState>newHashMap();
         List<BlockPos> â˜ƒx = â˜ƒ.getToPush();
         List<BlockState> â˜ƒxx = Lists.<BlockState>newArrayList();

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx.size(); ++â˜ƒxxx) {
            BlockPos â˜ƒxxxx = (BlockPos)â˜ƒx.get(â˜ƒxxx);
            BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
            â˜ƒxx.add(â˜ƒxxxxx);
            â˜ƒ.put(â˜ƒxxxx, â˜ƒxxxxx);
         }

         List<BlockPos> â˜ƒxxx = â˜ƒ.getToDestroy();
         BlockState[] â˜ƒxxxx = new BlockState[â˜ƒx.size() + â˜ƒxxx.size()];
         Direction â˜ƒxxxxx = â˜ƒ ? â˜ƒ : â˜ƒ.getOpposite();
         int â˜ƒxxxxxx = 0;

         for(int â˜ƒxxxxxxx = â˜ƒxxx.size() - 1; â˜ƒxxxxxxx >= 0; --â˜ƒxxxxxxx) {
            BlockPos â˜ƒxxxxxxxx = (BlockPos)â˜ƒxxx.get(â˜ƒxxxxxxx);
            BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxx);
            BlockEntity â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.hasBlockEntity() ? â˜ƒ.getBlockEntity(â˜ƒxxxxxxxx) : null;
            dropResources(â˜ƒxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx);
            â˜ƒ.setBlock(â˜ƒxxxxxxxx, Blocks.AIR.defaultBlockState(), 18);
            if (!â˜ƒxxxxxxxxx.is(BlockTags.FIRE)) {
               â˜ƒ.addDestroyBlockEffect(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
            }

            â˜ƒxxxx[â˜ƒxxxxxx++] = â˜ƒxxxxxxxxx;
         }

         for(int â˜ƒxxxxxxx = â˜ƒx.size() - 1; â˜ƒxxxxxxx >= 0; --â˜ƒxxxxxxx) {
            BlockPos â˜ƒxxxxxxxx = (BlockPos)â˜ƒx.get(â˜ƒxxxxxxx);
            BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxx);
            â˜ƒxxxxxxxx = â˜ƒxxxxxxxx.relative(â˜ƒxxxxx);
            â˜ƒ.remove(â˜ƒxxxxxxxx);
            BlockState â˜ƒxxxxxxxxxx = Blocks.MOVING_PISTON.defaultBlockState().setValue(FACING, â˜ƒ);
            â˜ƒ.setBlock(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, 68);
            â˜ƒ.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, (BlockState)â˜ƒxx.get(â˜ƒxxxxxxx), â˜ƒ, â˜ƒ, false));
            â˜ƒxxxx[â˜ƒxxxxxx++] = â˜ƒxxxxxxxxx;
         }

         if (â˜ƒ) {
            PistonType â˜ƒxxxxxxx = this.isSticky ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState â˜ƒxxxxxxxx = Blocks.PISTON_HEAD.defaultBlockState().setValue(PistonHeadBlock.FACING, â˜ƒ).setValue(PistonHeadBlock.TYPE, â˜ƒxxxxxxx);
            BlockState â˜ƒxxxxxxxxx = Blocks.MOVING_PISTON
               .defaultBlockState()
               .setValue(MovingPistonBlock.FACING, â˜ƒ)
               .setValue(MovingPistonBlock.TYPE, this.isSticky ? PistonType.STICKY : PistonType.DEFAULT);
            â˜ƒ.remove(â˜ƒ);
            â˜ƒ.setBlock(â˜ƒ, â˜ƒxxxxxxxxx, 68);
            â˜ƒ.setBlockEntity(MovingPistonBlock.newMovingBlockEntity(â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒ, true, true));
         }

         BlockState â˜ƒxxxxxxx = Blocks.AIR.defaultBlockState();

         for(BlockPos â˜ƒxxxxxxxx : â˜ƒ.keySet()) {
            â˜ƒ.setBlock(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, 82);
         }

         for(Entry<BlockPos, BlockState> â˜ƒxxxxxxxx : â˜ƒ.entrySet()) {
            BlockPos â˜ƒxxxxxxxxx = (BlockPos)â˜ƒxxxxxxxx.getKey();
            BlockState â˜ƒxxxxxxxxxx = (BlockState)â˜ƒxxxxxxxx.getValue();
            â˜ƒxxxxxxxxxx.updateIndirectNeighbourShapes(â˜ƒ, â˜ƒxxxxxxxxx, 2);
            â˜ƒxxxxxxx.updateNeighbourShapes(â˜ƒ, â˜ƒxxxxxxxxx, 2);
            â˜ƒxxxxxxx.updateIndirectNeighbourShapes(â˜ƒ, â˜ƒxxxxxxxxx, 2);
         }

         â˜ƒxxxxxx = 0;

         for(int â˜ƒxxxxxxxx = â˜ƒxxx.size() - 1; â˜ƒxxxxxxxx >= 0; --â˜ƒxxxxxxxx) {
            BlockState â˜ƒxxxxxxxxx = â˜ƒxxxx[â˜ƒxxxxxx++];
            BlockPos â˜ƒxxxxxxxxxx = (BlockPos)â˜ƒxxx.get(â˜ƒxxxxxxxx);
            â˜ƒxxxxxxxxx.updateIndirectNeighbourShapes(â˜ƒ, â˜ƒxxxxxxxxxx, 2);
            â˜ƒ.updateNeighborsAt(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx.getBlock());
         }

         for(int â˜ƒxxxxxxxx = â˜ƒx.size() - 1; â˜ƒxxxxxxxx >= 0; --â˜ƒxxxxxxxx) {
            â˜ƒ.updateNeighborsAt((BlockPos)â˜ƒx.get(â˜ƒxxxxxxxx), â˜ƒxxxx[â˜ƒxxxxxx++].getBlock());
         }

         if (â˜ƒ) {
            â˜ƒ.updateNeighborsAt(â˜ƒ, Blocks.PISTON_HEAD);
         }

         return true;
      }
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue(FACING, â˜ƒ.rotate(â˜ƒ.getValue(FACING)));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ.rotate(â˜ƒ.getRotation(â˜ƒ.getValue(FACING)));
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, EXTENDED);
   }

   @Override
   public boolean useShapeForLightOcclusion(BlockState var1) {
      return â˜ƒ.getValue(EXTENDED);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
