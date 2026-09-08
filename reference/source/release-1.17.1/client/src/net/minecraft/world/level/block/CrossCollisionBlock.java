package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CrossCollisionBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty NORTH = PipeBlock.NORTH;
   public static final BooleanProperty EAST = PipeBlock.EAST;
   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
   public static final BooleanProperty WEST = PipeBlock.WEST;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = (Map<Direction, BooleanProperty>)PipeBlock.PROPERTY_BY_DIRECTION
      .entrySet()
      .stream()
      .filter(var0 -> ((Direction)var0.getKey()).getAxis().isHorizontal())
      .collect(Util.toMap());
   protected final VoxelShape[] collisionShapeByIndex;
   protected final VoxelShape[] shapeByIndex;
   private final Object2IntMap<BlockState> stateToIndex = new Object2IntOpenHashMap<>();

   protected CrossCollisionBlock(float var1, float var2, float var3, float var4, float var5, BlockBehaviour.Properties var6) {
      super(â˜ƒ);
      this.collisionShapeByIndex = this.makeShapes(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, â˜ƒ);
      this.shapeByIndex = this.makeShapes(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, â˜ƒ);

      for(BlockState â˜ƒ : this.stateDefinition.getPossibleStates()) {
         this.getAABBIndex(â˜ƒ);
      }
   }

   protected VoxelShape[] makeShapes(float var1, float var2, float var3, float var4, float var5) {
      float â˜ƒ = 8.0F - â˜ƒ;
      float â˜ƒx = 8.0F + â˜ƒ;
      float â˜ƒxx = 8.0F - â˜ƒ;
      float â˜ƒxxx = 8.0F + â˜ƒ;
      VoxelShape â˜ƒxxxx = Block.box((double)â˜ƒ, 0.0, (double)â˜ƒ, (double)â˜ƒx, (double)â˜ƒ, (double)â˜ƒx);
      VoxelShape â˜ƒxxxxx = Block.box((double)â˜ƒxx, (double)â˜ƒ, 0.0, (double)â˜ƒxxx, (double)â˜ƒ, (double)â˜ƒxxx);
      VoxelShape â˜ƒxxxxxx = Block.box((double)â˜ƒxx, (double)â˜ƒ, (double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒ, 16.0);
      VoxelShape â˜ƒxxxxxxx = Block.box(0.0, (double)â˜ƒ, (double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒ, (double)â˜ƒxxx);
      VoxelShape â˜ƒxxxxxxxx = Block.box((double)â˜ƒxx, (double)â˜ƒ, (double)â˜ƒxx, 16.0, (double)â˜ƒ, (double)â˜ƒxxx);
      VoxelShape â˜ƒxxxxxxxxx = Shapes.or(â˜ƒxxxxx, â˜ƒxxxxxxxx);
      VoxelShape â˜ƒxxxxxxxxxx = Shapes.or(â˜ƒxxxxxx, â˜ƒxxxxxxx);
      VoxelShape[] â˜ƒxxxxxxxxxxx = new VoxelShape[]{
         Shapes.empty(),
         â˜ƒxxxxxx,
         â˜ƒxxxxxxx,
         â˜ƒxxxxxxxxxx,
         â˜ƒxxxxx,
         Shapes.or(â˜ƒxxxxxx, â˜ƒxxxxx),
         Shapes.or(â˜ƒxxxxxxx, â˜ƒxxxxx),
         Shapes.or(â˜ƒxxxxxxxxxx, â˜ƒxxxxx),
         â˜ƒxxxxxxxx,
         Shapes.or(â˜ƒxxxxxx, â˜ƒxxxxxxxx),
         Shapes.or(â˜ƒxxxxxxx, â˜ƒxxxxxxxx),
         Shapes.or(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxx),
         â˜ƒxxxxxxxxx,
         Shapes.or(â˜ƒxxxxxx, â˜ƒxxxxxxxxx),
         Shapes.or(â˜ƒxxxxxxx, â˜ƒxxxxxxxxx),
         Shapes.or(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx)
      };

      for(int â˜ƒxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxx < 16; ++â˜ƒxxxxxxxxxxxx) {
         â˜ƒxxxxxxxxxxx[â˜ƒxxxxxxxxxxxx] = Shapes.or(â˜ƒxxxx, â˜ƒxxxxxxxxxxx[â˜ƒxxxxxxxxxxxx]);
      }

      return â˜ƒxxxxxxxxxxx;
   }

   @Override
   public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
      return !â˜ƒ.getValue(WATERLOGGED);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.shapeByIndex[this.getAABBIndex(â˜ƒ)];
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.collisionShapeByIndex[this.getAABBIndex(â˜ƒ)];
   }

   private static int indexFor(Direction var0) {
      return 1 << â˜ƒ.get2DDataValue();
   }

   protected int getAABBIndex(BlockState var1) {
      return this.stateToIndex.computeIntIfAbsent(â˜ƒ, var0 -> {
         int â˜ƒ = 0;
         if (var0.getValue(NORTH)) {
            â˜ƒ |= indexFor(Direction.NORTH);
         }

         if (var0.getValue(EAST)) {
            â˜ƒ |= indexFor(Direction.EAST);
         }

         if (var0.getValue(SOUTH)) {
            â˜ƒ |= indexFor(Direction.SOUTH);
         }

         if (var0.getValue(WEST)) {
            â˜ƒ |= indexFor(Direction.WEST);
         }

         return â˜ƒ;
      });
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
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
}
