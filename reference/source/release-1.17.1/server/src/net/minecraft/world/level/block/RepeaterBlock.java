package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class RepeaterBlock extends DiodeBlock {
   public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;
   public static final IntegerProperty DELAY = BlockStateProperties.DELAY;

   protected RepeaterBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(FACING, Direction.NORTH)
            .setValue(DELAY, Integer.valueOf(1))
            .setValue(LOCKED, Boolean.valueOf(false))
            .setValue(POWERED, Boolean.valueOf(false))
      );
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (!â˜ƒ.getAbilities().mayBuild) {
         return InteractionResult.PASS;
      } else {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.cycle(DELAY), 3);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      }
   }

   @Override
   protected int getDelay(BlockState var1) {
      return â˜ƒ.getValue(DELAY) * 2;
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = super.getStateForPlacement(â˜ƒ);
      return â˜ƒ.setValue(LOCKED, Boolean.valueOf(this.isLocked(â˜ƒ.getLevel(), â˜ƒ.getClickedPos(), â˜ƒ)));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return !â˜ƒ.isClientSide() && â˜ƒ.getAxis() != ((Direction)â˜ƒ.getValue(FACING)).getAxis()
         ? â˜ƒ.setValue(LOCKED, Boolean.valueOf(this.isLocked(â˜ƒ, â˜ƒ, â˜ƒ)))
         : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isLocked(LevelReader var1, BlockPos var2, BlockState var3) {
      return this.getAlternateSignal(â˜ƒ, â˜ƒ, â˜ƒ) > 0;
   }

   @Override
   protected boolean isAlternateInput(BlockState var1) {
      return isDiode(â˜ƒ);
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(POWERED)) {
         Direction â˜ƒ = â˜ƒ.getValue(FACING);
         double â˜ƒx = (double)â˜ƒ.getX() + 0.5 + (â˜ƒ.nextDouble() - 0.5) * 0.2;
         double â˜ƒxx = (double)â˜ƒ.getY() + 0.4 + (â˜ƒ.nextDouble() - 0.5) * 0.2;
         double â˜ƒxxx = (double)â˜ƒ.getZ() + 0.5 + (â˜ƒ.nextDouble() - 0.5) * 0.2;
         float â˜ƒxxxx = -5.0F;
         if (â˜ƒ.nextBoolean()) {
            â˜ƒxxxx = (float)(â˜ƒ.getValue(DELAY) * 2 - 1);
         }

         â˜ƒxxxx /= 16.0F;
         double â˜ƒ = (double)(â˜ƒxxxx * (float)â˜ƒ.getStepX());
         double â˜ƒx = (double)(â˜ƒxxxx * (float)â˜ƒ.getStepZ());
         â˜ƒ.addParticle(DustParticleOptions.REDSTONE, â˜ƒx + â˜ƒ, â˜ƒxx, â˜ƒxxx + â˜ƒx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, DELAY, LOCKED, POWERED);
   }
}
