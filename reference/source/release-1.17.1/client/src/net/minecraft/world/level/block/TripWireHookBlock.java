package net.minecraft.world.level.block;

import com.google.common.base.MoreObjects;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TripWireHookBlock extends Block {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
   protected static final int WIRE_DIST_MIN = 1;
   protected static final int WIRE_DIST_MAX = 42;
   private static final int RECHECK_PERIOD = 10;
   protected static final int AABB_OFFSET = 3;
   protected static final VoxelShape NORTH_AABB = Block.box(5.0, 0.0, 10.0, 11.0, 10.0, 16.0);
   protected static final VoxelShape SOUTH_AABB = Block.box(5.0, 0.0, 0.0, 11.0, 10.0, 6.0);
   protected static final VoxelShape WEST_AABB = Block.box(10.0, 0.0, 5.0, 16.0, 10.0, 11.0);
   protected static final VoxelShape EAST_AABB = Block.box(0.0, 0.0, 5.0, 6.0, 10.0, 11.0);

   public TripWireHookBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false)).setValue(ATTACHED, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      switch((Direction)â˜ƒ.getValue(FACING)) {
         case EAST:
         default:
            return EAST_AABB;
         case WEST:
            return WEST_AABB;
         case SOUTH:
            return SOUTH_AABB;
         case NORTH:
            return NORTH_AABB;
      }
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ.getOpposite());
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      return â˜ƒ.getAxis().isHorizontal() && â˜ƒxx.isFaceSturdy(â˜ƒ, â˜ƒx, â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ.getOpposite() == â˜ƒ.getValue(FACING) && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = this.defaultBlockState().setValue(POWERED, Boolean.valueOf(false)).setValue(ATTACHED, Boolean.valueOf(false));
      LevelReader â˜ƒx = â˜ƒ.getLevel();
      BlockPos â˜ƒxx = â˜ƒ.getClickedPos();
      Direction[] â˜ƒxxx = â˜ƒ.getNearestLookingDirections();

      for(Direction â˜ƒxxxx : â˜ƒxxx) {
         if (â˜ƒxxxx.getAxis().isHorizontal()) {
            Direction â˜ƒxxxxx = â˜ƒxxxx.getOpposite();
            â˜ƒ = â˜ƒ.setValue(FACING, â˜ƒxxxxx);
            if (â˜ƒ.canSurvive(â˜ƒx, â˜ƒxx)) {
               return â˜ƒ;
            }
         }
      }

      return null;
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      this.calculateState(â˜ƒ, â˜ƒ, â˜ƒ, false, false, -1, null);
   }

   public void calculateState(Level var1, BlockPos var2, BlockState var3, boolean var4, boolean var5, int var6, @Nullable BlockState var7) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      boolean â˜ƒx = â˜ƒ.getValue(ATTACHED);
      boolean â˜ƒxx = â˜ƒ.getValue(POWERED);
      boolean â˜ƒxxx = !â˜ƒ;
      boolean â˜ƒxxxx = false;
      int â˜ƒxxxxx = 0;
      BlockState[] â˜ƒxxxxxx = new BlockState[42];

      for(int â˜ƒxxxxxxx = 1; â˜ƒxxxxxxx < 42; ++â˜ƒxxxxxxx) {
         BlockPos â˜ƒxxxxxxxx = â˜ƒ.relative(â˜ƒ, â˜ƒxxxxxxx);
         BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxx);
         if (â˜ƒxxxxxxxxx.is(Blocks.TRIPWIRE_HOOK)) {
            if (â˜ƒxxxxxxxxx.getValue(FACING) == â˜ƒ.getOpposite()) {
               â˜ƒxxxxx = â˜ƒxxxxxxx;
            }
            break;
         }

         if (!â˜ƒxxxxxxxxx.is(Blocks.TRIPWIRE) && â˜ƒxxxxxxx != â˜ƒ) {
            â˜ƒxxxxxx[â˜ƒxxxxxxx] = null;
            â˜ƒxxx = false;
         } else {
            if (â˜ƒxxxxxxx == â˜ƒ) {
               â˜ƒxxxxxxxxx = MoreObjects.firstNonNull(â˜ƒ, â˜ƒxxxxxxxxx);
            }

            boolean â˜ƒxxxxxxxx = !â˜ƒxxxxxxxxx.getValue(TripWireBlock.DISARMED);
            boolean â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxx.getValue(TripWireBlock.POWERED);
            â˜ƒxxxx |= â˜ƒxxxxxxxx && â˜ƒxxxxxxxxx;
            â˜ƒxxxxxx[â˜ƒxxxxxxx] = â˜ƒxxxxxxxxx;
            if (â˜ƒxxxxxxx == â˜ƒ) {
               â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 10);
               â˜ƒxxx &= â˜ƒxxxxxxxx;
            }
         }
      }

      â˜ƒxxx &= â˜ƒxxxxx > 1;
      â˜ƒxxxx &= â˜ƒxxx;
      BlockState â˜ƒxxxxxxx = this.defaultBlockState().setValue(ATTACHED, Boolean.valueOf(â˜ƒxxx)).setValue(POWERED, Boolean.valueOf(â˜ƒxxxx));
      if (â˜ƒxxxxx > 0) {
         BlockPos â˜ƒxxxxxxxx = â˜ƒ.relative(â˜ƒ, â˜ƒxxxxx);
         Direction â˜ƒxxxxxxxxx = â˜ƒ.getOpposite();
         â˜ƒ.setBlock(â˜ƒxxxxxxxx, â˜ƒxxxxxxx.setValue(FACING, â˜ƒxxxxxxxxx), 3);
         this.notifyNeighbors(â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
         this.playSound(â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒx, â˜ƒxx);
      }

      this.playSound(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒx, â˜ƒxx);
      if (!â˜ƒ) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒxxxxxxx.setValue(FACING, â˜ƒ), 3);
         if (â˜ƒ) {
            this.notifyNeighbors(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      if (â˜ƒx != â˜ƒxxx) {
         for(int â˜ƒxxxxxxx = 1; â˜ƒxxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxxx) {
            BlockPos â˜ƒxxxxxxxx = â˜ƒ.relative(â˜ƒ, â˜ƒxxxxxxx);
            BlockState â˜ƒxxxxxxxxx = â˜ƒxxxxxx[â˜ƒxxxxxxx];
            if (â˜ƒxxxxxxxxx != null) {
               â˜ƒ.setBlock(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx.setValue(ATTACHED, Boolean.valueOf(â˜ƒxxx)), 3);
               if (!â˜ƒ.getBlockState(â˜ƒxxxxxxxx).isAir()) {
               }
            }
         }
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      this.calculateState(â˜ƒ, â˜ƒ, â˜ƒ, false, true, -1, null);
   }

   private void playSound(Level var1, BlockPos var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      if (â˜ƒ && !â˜ƒ) {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.TRIPWIRE_CLICK_ON, SoundSource.BLOCKS, 0.4F, 0.6F);
         â˜ƒ.gameEvent(GameEvent.BLOCK_PRESS, â˜ƒ);
      } else if (!â˜ƒ && â˜ƒ) {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.TRIPWIRE_CLICK_OFF, SoundSource.BLOCKS, 0.4F, 0.5F);
         â˜ƒ.gameEvent(GameEvent.BLOCK_UNPRESS, â˜ƒ);
      } else if (â˜ƒ && !â˜ƒ) {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.TRIPWIRE_ATTACH, SoundSource.BLOCKS, 0.4F, 0.7F);
         â˜ƒ.gameEvent(GameEvent.BLOCK_ATTACH, â˜ƒ);
      } else if (!â˜ƒ && â˜ƒ) {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.TRIPWIRE_DETACH, SoundSource.BLOCKS, 0.4F, 1.2F / (â˜ƒ.random.nextFloat() * 0.2F + 0.9F));
         â˜ƒ.gameEvent(GameEvent.BLOCK_DETACH, â˜ƒ);
      }
   }

   private void notifyNeighbors(Level var1, BlockPos var2, Direction var3) {
      â˜ƒ.updateNeighborsAt(â˜ƒ, this);
      â˜ƒ.updateNeighborsAt(â˜ƒ.relative(â˜ƒ.getOpposite()), this);
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ && !â˜ƒ.is(â˜ƒ.getBlock())) {
         boolean â˜ƒ = â˜ƒ.getValue(ATTACHED);
         boolean â˜ƒx = â˜ƒ.getValue(POWERED);
         if (â˜ƒ || â˜ƒx) {
            this.calculateState(â˜ƒ, â˜ƒ, â˜ƒ, true, false, -1, null);
         }

         if (â˜ƒx) {
            â˜ƒ.updateNeighborsAt(â˜ƒ, this);
            â˜ƒ.updateNeighborsAt(â˜ƒ.relative(((Direction)â˜ƒ.getValue(FACING)).getOpposite()), this);
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
      if (!â˜ƒ.getValue(POWERED)) {
         return 0;
      } else {
         return â˜ƒ.getValue(FACING) == â˜ƒ ? 15 : 0;
      }
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
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
      â˜ƒ.add(FACING, POWERED, ATTACHED);
   }
}
