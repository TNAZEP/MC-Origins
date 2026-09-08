package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.PushReaction;

public class EndRodBlock extends RodBlock {
   protected EndRodBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      Direction â˜ƒ = â˜ƒ.getClickedFace();
      BlockState â˜ƒx = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos().relative(â˜ƒ.getOpposite()));
      return â˜ƒx.is(this) && â˜ƒx.getValue(FACING) == â˜ƒ
         ? this.defaultBlockState().setValue(FACING, â˜ƒ.getOpposite())
         : this.defaultBlockState().setValue(FACING, â˜ƒ);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      double â˜ƒx = (double)â˜ƒ.getX() + 0.55 - (double)(â˜ƒ.nextFloat() * 0.1F);
      double â˜ƒxx = (double)â˜ƒ.getY() + 0.55 - (double)(â˜ƒ.nextFloat() * 0.1F);
      double â˜ƒxxx = (double)â˜ƒ.getZ() + 0.55 - (double)(â˜ƒ.nextFloat() * 0.1F);
      double â˜ƒxxxx = (double)(0.4F - (â˜ƒ.nextFloat() + â˜ƒ.nextFloat()) * 0.4F);
      if (â˜ƒ.nextInt(5) == 0) {
         â˜ƒ.addParticle(
            ParticleTypes.END_ROD,
            â˜ƒx + (double)â˜ƒ.getStepX() * â˜ƒxxxx,
            â˜ƒxx + (double)â˜ƒ.getStepY() * â˜ƒxxxx,
            â˜ƒxxx + (double)â˜ƒ.getStepZ() * â˜ƒxxxx,
            â˜ƒ.nextGaussian() * 0.005,
            â˜ƒ.nextGaussian() * 0.005,
            â˜ƒ.nextGaussian() * 0.005
         );
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING);
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.NORMAL;
   }
}
