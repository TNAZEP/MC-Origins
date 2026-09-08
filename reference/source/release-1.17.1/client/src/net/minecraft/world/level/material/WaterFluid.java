package net.minecraft.world.level.material;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public abstract class WaterFluid extends FlowingFluid {
   @Override
   public Fluid getFlowing() {
      return Fluids.FLOWING_WATER;
   }

   @Override
   public Fluid getSource() {
      return Fluids.WATER;
   }

   @Override
   public Item getBucket() {
      return Items.WATER_BUCKET;
   }

   @Override
   public void animateTick(Level var1, BlockPos var2, FluidState var3, Random var4) {
      if (!â˜ƒ.isSource() && !â˜ƒ.getValue(FALLING)) {
         if (â˜ƒ.nextInt(64) == 0) {
            â˜ƒ.playLocalSound(
               (double)â˜ƒ.getX() + 0.5,
               (double)â˜ƒ.getY() + 0.5,
               (double)â˜ƒ.getZ() + 0.5,
               SoundEvents.WATER_AMBIENT,
               SoundSource.BLOCKS,
               â˜ƒ.nextFloat() * 0.25F + 0.75F,
               â˜ƒ.nextFloat() + 0.5F,
               false
            );
         }
      } else if (â˜ƒ.nextInt(10) == 0) {
         â˜ƒ.addParticle(
            ParticleTypes.UNDERWATER,
            (double)â˜ƒ.getX() + â˜ƒ.nextDouble(),
            (double)â˜ƒ.getY() + â˜ƒ.nextDouble(),
            (double)â˜ƒ.getZ() + â˜ƒ.nextDouble(),
            0.0,
            0.0,
            0.0
         );
      }
   }

   @Nullable
   @Override
   public ParticleOptions getDripParticle() {
      return ParticleTypes.DRIPPING_WATER;
   }

   @Override
   protected boolean canConvertToSource() {
      return true;
   }

   @Override
   protected void beforeDestroyingBlock(LevelAccessor var1, BlockPos var2, BlockState var3) {
      BlockEntity â˜ƒ = â˜ƒ.hasBlockEntity() ? â˜ƒ.getBlockEntity(â˜ƒ) : null;
      Block.dropResources(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getSlopeFindDistance(LevelReader var1) {
      return 4;
   }

   @Override
   public BlockState createLegacyBlock(FluidState var1) {
      return Blocks.WATER.defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(â˜ƒ)));
   }

   @Override
   public boolean isSame(Fluid var1) {
      return â˜ƒ == Fluids.WATER || â˜ƒ == Fluids.FLOWING_WATER;
   }

   @Override
   public int getDropOff(LevelReader var1) {
      return 1;
   }

   @Override
   public int getTickDelay(LevelReader var1) {
      return 5;
   }

   @Override
   public boolean canBeReplacedWith(FluidState var1, BlockGetter var2, BlockPos var3, Fluid var4, Direction var5) {
      return â˜ƒ == Direction.DOWN && !â˜ƒ.is(FluidTags.WATER);
   }

   @Override
   protected float getExplosionResistance() {
      return 100.0F;
   }

   @Override
   public Optional<SoundEvent> getPickupSound() {
      return Optional.of(SoundEvents.BUCKET_FILL);
   }

   public static class Flowing extends WaterFluid {
      @Override
      protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> var1) {
         super.createFluidStateDefinition(â˜ƒ);
         â˜ƒ.add(LEVEL);
      }

      @Override
      public int getAmount(FluidState var1) {
         return â˜ƒ.getValue(LEVEL);
      }

      @Override
      public boolean isSource(FluidState var1) {
         return false;
      }
   }

   public static class Source extends WaterFluid {
      @Override
      public int getAmount(FluidState var1) {
         return 8;
      }

      @Override
      public boolean isSource(FluidState var1) {
         return true;
      }
   }
}
