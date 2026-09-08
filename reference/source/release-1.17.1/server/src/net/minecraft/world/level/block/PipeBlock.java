package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PipeBlock extends Block {
   private static final Direction[] DIRECTIONS = Direction.values();
   public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
   public static final BooleanProperty EAST = BlockStateProperties.EAST;
   public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
   public static final BooleanProperty WEST = BlockStateProperties.WEST;
   public static final BooleanProperty UP = BlockStateProperties.UP;
   public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
   public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(Util.make(Maps.newEnumMap(Direction.class), var0 -> {
      var0.put(Direction.NORTH, NORTH);
      var0.put(Direction.EAST, EAST);
      var0.put(Direction.SOUTH, SOUTH);
      var0.put(Direction.WEST, WEST);
      var0.put(Direction.UP, UP);
      var0.put(Direction.DOWN, DOWN);
   }));
   protected final VoxelShape[] shapeByIndex;

   protected PipeBlock(float var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.shapeByIndex = this.makeShapes(â˜ƒ);
   }

   private VoxelShape[] makeShapes(float var1) {
      float â˜ƒ = 0.5F - â˜ƒ;
      float â˜ƒx = 0.5F + â˜ƒ;
      VoxelShape â˜ƒxx = Block.box(
         (double)(â˜ƒ * 16.0F), (double)(â˜ƒ * 16.0F), (double)(â˜ƒ * 16.0F), (double)(â˜ƒx * 16.0F), (double)(â˜ƒx * 16.0F), (double)(â˜ƒx * 16.0F)
      );
      VoxelShape[] â˜ƒxxx = new VoxelShape[DIRECTIONS.length];

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < DIRECTIONS.length; ++â˜ƒxxxx) {
         Direction â˜ƒxxxxx = DIRECTIONS[â˜ƒxxxx];
         â˜ƒxxx[â˜ƒxxxx] = Shapes.box(
            0.5 + Math.min((double)(-â˜ƒ), (double)â˜ƒxxxxx.getStepX() * 0.5),
            0.5 + Math.min((double)(-â˜ƒ), (double)â˜ƒxxxxx.getStepY() * 0.5),
            0.5 + Math.min((double)(-â˜ƒ), (double)â˜ƒxxxxx.getStepZ() * 0.5),
            0.5 + Math.max((double)â˜ƒ, (double)â˜ƒxxxxx.getStepX() * 0.5),
            0.5 + Math.max((double)â˜ƒ, (double)â˜ƒxxxxx.getStepY() * 0.5),
            0.5 + Math.max((double)â˜ƒ, (double)â˜ƒxxxxx.getStepZ() * 0.5)
         );
      }

      VoxelShape[] â˜ƒxxxx = new VoxelShape[64];

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 64; ++â˜ƒxxxxx) {
         VoxelShape â˜ƒxxxxxx = â˜ƒxx;

         for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < DIRECTIONS.length; ++â˜ƒxxxxxxx) {
            if ((â˜ƒxxxxx & 1 << â˜ƒxxxxxxx) != 0) {
               â˜ƒxxxxxx = Shapes.or(â˜ƒxxxxxx, â˜ƒxxx[â˜ƒxxxxxxx]);
            }
         }

         â˜ƒxxxx[â˜ƒxxxxx] = â˜ƒxxxxxx;
      }

      return â˜ƒxxxx;
   }

   @Override
   public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
      return false;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.shapeByIndex[this.getAABBIndex(â˜ƒ)];
   }

   protected int getAABBIndex(BlockState var1) {
      int â˜ƒ = 0;

      for(int â˜ƒx = 0; â˜ƒx < DIRECTIONS.length; ++â˜ƒx) {
         if (â˜ƒ.getValue((Property)PROPERTY_BY_DIRECTION.get(DIRECTIONS[â˜ƒx]))) {
            â˜ƒ |= 1 << â˜ƒx;
         }
      }

      return â˜ƒ;
   }
}
