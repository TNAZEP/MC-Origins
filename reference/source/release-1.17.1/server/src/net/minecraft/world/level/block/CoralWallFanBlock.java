package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class CoralWallFanBlock extends BaseCoralWallFanBlock {
   private final Block deadBlock;

   protected CoralWallFanBlock(Block var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.deadBlock = â˜ƒ;
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      this.tryScheduleDieTick(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!scanForWater(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.setBlock(
            â˜ƒ, this.deadBlock.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, (Direction)â˜ƒ.getValue(FACING)), 2
         );
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getOpposite() == â˜ƒ.getValue(FACING) && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         return Blocks.AIR.defaultBlockState();
      } else {
         if (â˜ƒ.getValue(WATERLOGGED)) {
            â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
         }

         this.tryScheduleDieTick(â˜ƒ, â˜ƒ, â˜ƒ);
         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
