package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallTorchBlock extends TorchBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   protected static final float AABB_OFFSET = 2.5F;
   private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.box(5.5, 3.0, 11.0, 10.5, 13.0, 16.0),
         Direction.SOUTH,
         Block.box(5.5, 3.0, 0.0, 10.5, 13.0, 5.0),
         Direction.WEST,
         Block.box(11.0, 3.0, 5.5, 16.0, 13.0, 10.5),
         Direction.EAST,
         Block.box(0.0, 3.0, 5.5, 5.0, 13.0, 10.5)
      )
   );

   protected WallTorchBlock(BlockBehaviour.Properties var1, ParticleOptions var2) {
      super(â˜ƒ, â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
   }

   @Override
   public String getDescriptionId() {
      return this.asItem().getDescriptionId();
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return getShape(â˜ƒ);
   }

   public static VoxelShape getShape(BlockState var0) {
      return (VoxelShape)AABBS.get(â˜ƒ.getValue(FACING));
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ.getOpposite());
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      return â˜ƒxx.isFaceSturdy(â˜ƒ, â˜ƒx, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = this.defaultBlockState();
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
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ.getOpposite() == â˜ƒ.getValue(FACING) && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ) ? Blocks.AIR.defaultBlockState() : â˜ƒ;
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      double â˜ƒx = (double)â˜ƒ.getX() + 0.5;
      double â˜ƒxx = (double)â˜ƒ.getY() + 0.7;
      double â˜ƒxxx = (double)â˜ƒ.getZ() + 0.5;
      double â˜ƒxxxx = 0.22;
      double â˜ƒxxxxx = 0.27;
      Direction â˜ƒxxxxxx = â˜ƒ.getOpposite();
      â˜ƒ.addParticle(
         ParticleTypes.SMOKE, â˜ƒx + 0.27 * (double)â˜ƒxxxxxx.getStepX(), â˜ƒxx + 0.22, â˜ƒxxx + 0.27 * (double)â˜ƒxxxxxx.getStepZ(), 0.0, 0.0, 0.0
      );
      â˜ƒ.addParticle(this.flameParticle, â˜ƒx + 0.27 * (double)â˜ƒxxxxxx.getStepX(), â˜ƒxx + 0.22, â˜ƒxxx + 0.27 * (double)â˜ƒxxxxxx.getStepZ(), 0.0, 0.0, 0.0);
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
      â˜ƒ.add(FACING);
   }
}
