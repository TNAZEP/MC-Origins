package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TargetBlock extends Block {
   private static final IntegerProperty OUTPUT_POWER = BlockStateProperties.POWER;
   private static final int ACTIVATION_TICKS_ARROWS = 20;
   private static final int ACTIVATION_TICKS_OTHER = 8;

   public TargetBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(OUTPUT_POWER, Integer.valueOf(0)));
   }

   @Override
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
      int â˜ƒx = updateRedstoneOutput(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      Entity â˜ƒxx = â˜ƒ.getOwner();
      if (â˜ƒxx instanceof ServerPlayer â˜ƒ) {
         â˜ƒ.awardStat(Stats.TARGET_HIT);
         CriteriaTriggers.TARGET_BLOCK_HIT.trigger(â˜ƒ, â˜ƒ, â˜ƒ.getLocation(), â˜ƒx);
      }
   }

   private static int updateRedstoneOutput(LevelAccessor var0, BlockState var1, BlockHitResult var2, Entity var3) {
      int â˜ƒ = getRedstoneStrength(â˜ƒ, â˜ƒ.getLocation());
      int â˜ƒx = â˜ƒ instanceof AbstractArrow ? 20 : 8;
      if (!â˜ƒ.getBlockTicks().hasScheduledTick(â˜ƒ.getBlockPos(), â˜ƒ.getBlock())) {
         setOutputPower(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getBlockPos(), â˜ƒx);
      }

      return â˜ƒ;
   }

   private static int getRedstoneStrength(BlockHitResult var0, Vec3 var1) {
      Direction â˜ƒx = â˜ƒ.getDirection();
      double â˜ƒxx = Math.abs(Mth.frac(â˜ƒ.x) - 0.5);
      double â˜ƒxxx = Math.abs(Mth.frac(â˜ƒ.y) - 0.5);
      double â˜ƒxxxx = Math.abs(Mth.frac(â˜ƒ.z) - 0.5);
      Direction.Axis â˜ƒxxxxx = â˜ƒx.getAxis();
      double â˜ƒ;
      if (â˜ƒxxxxx == Direction.Axis.Y) {
         â˜ƒ = Math.max(â˜ƒxx, â˜ƒxxxx);
      } else if (â˜ƒxxxxx == Direction.Axis.Z) {
         â˜ƒ = Math.max(â˜ƒxx, â˜ƒxxx);
      } else {
         â˜ƒ = Math.max(â˜ƒxxx, â˜ƒxxxx);
      }

      return Math.max(1, Mth.ceil(15.0 * Mth.clamp((0.5 - â˜ƒ) / 0.5, 0.0, 1.0)));
   }

   private static void setOutputPower(LevelAccessor var0, BlockState var1, int var2, BlockPos var3, int var4) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(OUTPUT_POWER, Integer.valueOf(â˜ƒ)), 3);
      â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, â˜ƒ.getBlock(), â˜ƒ);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(OUTPUT_POWER) != 0) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(OUTPUT_POWER, Integer.valueOf(0)), 3);
      }
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(OUTPUT_POWER);
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(OUTPUT_POWER);
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.isClientSide() && !â˜ƒ.is(â˜ƒ.getBlock())) {
         if (â˜ƒ.getValue(OUTPUT_POWER) > 0 && !â˜ƒ.getBlockTicks().hasScheduledTick(â˜ƒ, this)) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(OUTPUT_POWER, Integer.valueOf(0)), 18);
         }
      }
   }
}
