package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class CoralBlock extends Block {
   private final Block deadBlock;

   public CoralBlock(Block var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.deadBlock = â˜ƒ;
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!this.scanForWater(â˜ƒ, â˜ƒ)) {
         â˜ƒ.setBlock(â˜ƒ, this.deadBlock.defaultBlockState(), 2);
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (!this.scanForWater(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 60 + â˜ƒ.getRandom().nextInt(40));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected boolean scanForWater(BlockGetter var1, BlockPos var2) {
      for(Direction â˜ƒ : Direction.values()) {
         FluidState â˜ƒx = â˜ƒ.getFluidState(â˜ƒ.relative(â˜ƒ));
         if (â˜ƒx.is(FluidTags.WATER)) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      if (!this.scanForWater(â˜ƒ.getLevel(), â˜ƒ.getClickedPos())) {
         â˜ƒ.getLevel().getBlockTicks().scheduleTick(â˜ƒ.getClickedPos(), this, 60 + â˜ƒ.getLevel().getRandom().nextInt(40));
      }

      return this.defaultBlockState();
   }
}
