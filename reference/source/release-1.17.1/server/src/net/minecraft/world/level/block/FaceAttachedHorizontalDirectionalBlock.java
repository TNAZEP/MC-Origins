package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class FaceAttachedHorizontalDirectionalBlock extends HorizontalDirectionalBlock {
   public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;

   protected FaceAttachedHorizontalDirectionalBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return canAttach(â˜ƒ, â˜ƒ, getConnectedDirection(â˜ƒ).getOpposite());
   }

   public static boolean canAttach(LevelReader var0, BlockPos var1, Direction var2) {
      BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ);
      return â˜ƒ.getBlockState(â˜ƒ).isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ.getOpposite());
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      for(Direction â˜ƒ : â˜ƒ.getNearestLookingDirections()) {
         BlockState â˜ƒx;
         if (â˜ƒ.getAxis() == Direction.Axis.Y) {
            â˜ƒx = this.defaultBlockState()
               .setValue(FACE, â˜ƒ == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR)
               .setValue(FACING, â˜ƒ.getHorizontalDirection());
         } else {
            â˜ƒx = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, â˜ƒ.getOpposite());
         }

         if (â˜ƒx.canSurvive(â˜ƒ.getLevel(), â˜ƒ.getClickedPos())) {
            return â˜ƒx;
         }
      }

      return null;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return getConnectedDirection(â˜ƒ).getOpposite() == â˜ƒ && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected static Direction getConnectedDirection(BlockState var0) {
      switch((AttachFace)â˜ƒ.getValue(FACE)) {
         case CEILING:
            return Direction.DOWN;
         case FLOOR:
            return Direction.UP;
         default:
            return â˜ƒ.getValue(FACING);
      }
   }
}
