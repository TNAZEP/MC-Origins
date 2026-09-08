package net.minecraft.world.level.block;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TripWireBlock extends Block {
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final BooleanProperty ATTACHED = BlockStateProperties.ATTACHED;
   public static final BooleanProperty DISARMED = BlockStateProperties.DISARMED;
   public static final BooleanProperty NORTH = PipeBlock.NORTH;
   public static final BooleanProperty EAST = PipeBlock.EAST;
   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
   public static final BooleanProperty WEST = PipeBlock.WEST;
   private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = CrossCollisionBlock.PROPERTY_BY_DIRECTION;
   protected static final VoxelShape AABB = Block.box(0.0, 1.0, 0.0, 16.0, 2.5, 16.0);
   protected static final VoxelShape NOT_ATTACHED_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   private static final int RECHECK_PERIOD = 10;
   private final TripWireHookBlock hook;

   public TripWireBlock(TripWireHookBlock var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(POWERED, Boolean.valueOf(false))
            .setValue(ATTACHED, Boolean.valueOf(false))
            .setValue(DISARMED, Boolean.valueOf(false))
            .setValue(NORTH, Boolean.valueOf(false))
            .setValue(EAST, Boolean.valueOf(false))
            .setValue(SOUTH, Boolean.valueOf(false))
            .setValue(WEST, Boolean.valueOf(false))
      );
      this.hook = â˜ƒ;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return â˜ƒ.getValue(ATTACHED) ? AABB : NOT_ATTACHED_AABB;
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockGetter â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      return this.defaultBlockState()
         .setValue(NORTH, Boolean.valueOf(this.shouldConnectTo(â˜ƒ.getBlockState(â˜ƒx.north()), Direction.NORTH)))
         .setValue(EAST, Boolean.valueOf(this.shouldConnectTo(â˜ƒ.getBlockState(â˜ƒx.east()), Direction.EAST)))
         .setValue(SOUTH, Boolean.valueOf(this.shouldConnectTo(â˜ƒ.getBlockState(â˜ƒx.south()), Direction.SOUTH)))
         .setValue(WEST, Boolean.valueOf(this.shouldConnectTo(â˜ƒ.getBlockState(â˜ƒx.west()), Direction.WEST)));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ.getAxis().isHorizontal()
         ? â˜ƒ.setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ), Boolean.valueOf(this.shouldConnectTo(â˜ƒ, â˜ƒ)))
         : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         this.updateSource(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ && !â˜ƒ.is(â˜ƒ.getBlock())) {
         this.updateSource(â˜ƒ, â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(true)));
      }
   }

   @Override
   public void playerWillDestroy(Level var1, BlockPos var2, BlockState var3, Player var4) {
      if (!â˜ƒ.isClientSide && !â˜ƒ.getMainHandItem().isEmpty() && â˜ƒ.getMainHandItem().is(Items.SHEARS)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(DISARMED, Boolean.valueOf(true)), 4);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.SHEAR, â˜ƒ);
      }

      super.playerWillDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void updateSource(Level var1, BlockPos var2, BlockState var3) {
      for(Direction â˜ƒ : new Direction[]{Direction.SOUTH, Direction.WEST}) {
         for(int â˜ƒx = 1; â˜ƒx < 42; ++â˜ƒx) {
            BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒ, â˜ƒx);
            BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
            if (â˜ƒxxx.is(this.hook)) {
               if (â˜ƒxxx.getValue(TripWireHookBlock.FACING) == â˜ƒ.getOpposite()) {
                  this.hook.calculateState(â˜ƒ, â˜ƒxx, â˜ƒxxx, false, true, â˜ƒx, â˜ƒ);
               }
               break;
            }

            if (!â˜ƒxxx.is(this)) {
               break;
            }
         }
      }
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!â˜ƒ.isClientSide) {
         if (!â˜ƒ.getValue(POWERED)) {
            this.checkPressed(â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getBlockState(â˜ƒ).getValue(POWERED)) {
         this.checkPressed(â˜ƒ, â˜ƒ);
      }
   }

   private void checkPressed(Level var1, BlockPos var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      boolean â˜ƒx = â˜ƒ.getValue(POWERED);
      boolean â˜ƒxx = false;
      List<? extends Entity> â˜ƒxxx = â˜ƒ.getEntities(null, â˜ƒ.getShape(â˜ƒ, â˜ƒ).bounds().move(â˜ƒ));
      if (!â˜ƒxxx.isEmpty()) {
         for(Entity â˜ƒxxxx : â˜ƒxxx) {
            if (!â˜ƒxxxx.isIgnoringBlockTriggers()) {
               â˜ƒxx = true;
               break;
            }
         }
      }

      if (â˜ƒxx != â˜ƒx) {
         â˜ƒ = â˜ƒ.setValue(POWERED, Boolean.valueOf(â˜ƒxx));
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
         this.updateSource(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      if (â˜ƒxx) {
         â˜ƒ.getBlockTicks().scheduleTick(new BlockPos(â˜ƒ), this, 10);
      }
   }

   public boolean shouldConnectTo(BlockState var1, Direction var2) {
      if (â˜ƒ.is(this.hook)) {
         return â˜ƒ.getValue(TripWireHookBlock.FACING) == â˜ƒ.getOpposite();
      } else {
         return â˜ƒ.is(this);
      }
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      switch(â˜ƒ) {
         case CLOCKWISE_180:
            return â˜ƒ.setValue(NORTH, (Boolean)â˜ƒ.getValue(SOUTH))
               .setValue(EAST, (Boolean)â˜ƒ.getValue(WEST))
               .setValue(SOUTH, (Boolean)â˜ƒ.getValue(NORTH))
               .setValue(WEST, (Boolean)â˜ƒ.getValue(EAST));
         case COUNTERCLOCKWISE_90:
            return â˜ƒ.setValue(NORTH, (Boolean)â˜ƒ.getValue(EAST))
               .setValue(EAST, (Boolean)â˜ƒ.getValue(SOUTH))
               .setValue(SOUTH, (Boolean)â˜ƒ.getValue(WEST))
               .setValue(WEST, (Boolean)â˜ƒ.getValue(NORTH));
         case CLOCKWISE_90:
            return â˜ƒ.setValue(NORTH, (Boolean)â˜ƒ.getValue(WEST))
               .setValue(EAST, (Boolean)â˜ƒ.getValue(NORTH))
               .setValue(SOUTH, (Boolean)â˜ƒ.getValue(EAST))
               .setValue(WEST, (Boolean)â˜ƒ.getValue(SOUTH));
         default:
            return â˜ƒ;
      }
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      switch(â˜ƒ) {
         case LEFT_RIGHT:
            return â˜ƒ.setValue(NORTH, (Boolean)â˜ƒ.getValue(SOUTH)).setValue(SOUTH, (Boolean)â˜ƒ.getValue(NORTH));
         case FRONT_BACK:
            return â˜ƒ.setValue(EAST, (Boolean)â˜ƒ.getValue(WEST)).setValue(WEST, (Boolean)â˜ƒ.getValue(EAST));
         default:
            return super.mirror(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(POWERED, ATTACHED, DISARMED, NORTH, EAST, WEST, SOUTH);
   }
}
