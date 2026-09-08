package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedstoneWallTorchBlock extends RedstoneTorchBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

   protected RedstoneWallTorchBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.valueOf(true)));
   }

   @Override
   public String getDescriptionId() {
      return this.asItem().getDescriptionId();
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return WallTorchBlock.getShape(â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return Blocks.WALL_TORCH.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return Blocks.WALL_TORCH.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = Blocks.WALL_TORCH.getStateForPlacement(â˜ƒ);
      return â˜ƒ == null ? null : this.defaultBlockState().setValue(FACING, (Direction)â˜ƒ.getValue(FACING));
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(LIT)) {
         Direction â˜ƒ = ((Direction)â˜ƒ.getValue(FACING)).getOpposite();
         double â˜ƒx = 0.27;
         double â˜ƒxx = (double)â˜ƒ.getX() + 0.5 + (â˜ƒ.nextDouble() - 0.5) * 0.2 + 0.27 * (double)â˜ƒ.getStepX();
         double â˜ƒxxx = (double)â˜ƒ.getY() + 0.7 + (â˜ƒ.nextDouble() - 0.5) * 0.2 + 0.22;
         double â˜ƒxxxx = (double)â˜ƒ.getZ() + 0.5 + (â˜ƒ.nextDouble() - 0.5) * 0.2 + 0.27 * (double)â˜ƒ.getStepZ();
         â˜ƒ.addParticle(this.flameParticle, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected boolean hasNeighborSignal(Level var1, BlockPos var2, BlockState var3) {
      Direction â˜ƒ = ((Direction)â˜ƒ.getValue(FACING)).getOpposite();
      return â˜ƒ.hasSignal(â˜ƒ.relative(â˜ƒ), â˜ƒ);
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(LIT) && â˜ƒ.getValue(FACING) != â˜ƒ ? 15 : 0;
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return Blocks.WALL_TORCH.rotate(â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return Blocks.WALL_TORCH.mirror(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, LIT);
   }
}
