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
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public abstract class LavaFluid extends FlowingFluid {
   public static final float MIN_LEVEL_CUTOFF = 0.44444445F;

   @Override
   public Fluid getFlowing() {
      return Fluids.FLOWING_LAVA;
   }

   @Override
   public Fluid getSource() {
      return Fluids.LAVA;
   }

   @Override
   public Item getBucket() {
      return Items.LAVA_BUCKET;
   }

   @Override
   public void animateTick(Level var1, BlockPos var2, FluidState var3, Random var4) {
      BlockPos â˜ƒ = â˜ƒ.above();
      if (â˜ƒ.getBlockState(â˜ƒ).isAir() && !â˜ƒ.getBlockState(â˜ƒ).isSolidRender(â˜ƒ, â˜ƒ)) {
         if (â˜ƒ.nextInt(100) == 0) {
            double â˜ƒx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble();
            double â˜ƒxx = (double)â˜ƒ.getY() + 1.0;
            double â˜ƒxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble();
            â˜ƒ.addParticle(ParticleTypes.LAVA, â˜ƒx, â˜ƒxx, â˜ƒxxx, 0.0, 0.0, 0.0);
            â˜ƒ.playLocalSound(
               â˜ƒx, â˜ƒxx, â˜ƒxxx, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.2F + â˜ƒ.nextFloat() * 0.2F, 0.9F + â˜ƒ.nextFloat() * 0.15F, false
            );
         }

         if (â˜ƒ.nextInt(200) == 0) {
            â˜ƒ.playLocalSound(
               (double)â˜ƒ.getX(),
               (double)â˜ƒ.getY(),
               (double)â˜ƒ.getZ(),
               SoundEvents.LAVA_AMBIENT,
               SoundSource.BLOCKS,
               0.2F + â˜ƒ.nextFloat() * 0.2F,
               0.9F + â˜ƒ.nextFloat() * 0.15F,
               false
            );
         }
      }
   }

   @Override
   public void randomTick(Level var1, BlockPos var2, FluidState var3, Random var4) {
      if (â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
         int â˜ƒ = â˜ƒ.nextInt(3);
         if (â˜ƒ > 0) {
            BlockPos â˜ƒx = â˜ƒ;

            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
               â˜ƒx = â˜ƒx.offset(â˜ƒ.nextInt(3) - 1, 1, â˜ƒ.nextInt(3) - 1);
               if (!â˜ƒ.isLoaded(â˜ƒx)) {
                  return;
               }

               BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒx);
               if (â˜ƒxxx.isAir()) {
                  if (this.hasFlammableNeighbours(â˜ƒ, â˜ƒx)) {
                     â˜ƒ.setBlockAndUpdate(â˜ƒx, BaseFireBlock.getState(â˜ƒ, â˜ƒx));
                     return;
                  }
               } else if (â˜ƒxxx.getMaterial().blocksMotion()) {
                  return;
               }
            }
         } else {
            for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
               BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ.nextInt(3) - 1, 0, â˜ƒ.nextInt(3) - 1);
               if (!â˜ƒ.isLoaded(â˜ƒx)) {
                  return;
               }

               if (â˜ƒ.isEmptyBlock(â˜ƒx.above()) && this.isFlammable(â˜ƒ, â˜ƒx)) {
                  â˜ƒ.setBlockAndUpdate(â˜ƒx.above(), BaseFireBlock.getState(â˜ƒ, â˜ƒx));
               }
            }
         }
      }
   }

   private boolean hasFlammableNeighbours(LevelReader var1, BlockPos var2) {
      for(Direction â˜ƒ : Direction.values()) {
         if (this.isFlammable(â˜ƒ, â˜ƒ.relative(â˜ƒ))) {
            return true;
         }
      }

      return false;
   }

   private boolean isFlammable(LevelReader var1, BlockPos var2) {
      return â˜ƒ.getY() >= â˜ƒ.getMinBuildHeight() && â˜ƒ.getY() < â˜ƒ.getMaxBuildHeight() && !â˜ƒ.hasChunkAt(â˜ƒ)
         ? false
         : â˜ƒ.getBlockState(â˜ƒ).getMaterial().isFlammable();
   }

   @Nullable
   @Override
   public ParticleOptions getDripParticle() {
      return ParticleTypes.DRIPPING_LAVA;
   }

   @Override
   protected void beforeDestroyingBlock(LevelAccessor var1, BlockPos var2, BlockState var3) {
      this.fizz(â˜ƒ, â˜ƒ);
   }

   @Override
   public int getSlopeFindDistance(LevelReader var1) {
      return â˜ƒ.dimensionType().ultraWarm() ? 4 : 2;
   }

   @Override
   public BlockState createLegacyBlock(FluidState var1) {
      return Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(â˜ƒ)));
   }

   @Override
   public boolean isSame(Fluid var1) {
      return â˜ƒ == Fluids.LAVA || â˜ƒ == Fluids.FLOWING_LAVA;
   }

   @Override
   public int getDropOff(LevelReader var1) {
      return â˜ƒ.dimensionType().ultraWarm() ? 1 : 2;
   }

   @Override
   public boolean canBeReplacedWith(FluidState var1, BlockGetter var2, BlockPos var3, Fluid var4, Direction var5) {
      return â˜ƒ.getHeight(â˜ƒ, â˜ƒ) >= 0.44444445F && â˜ƒ.is(FluidTags.WATER);
   }

   @Override
   public int getTickDelay(LevelReader var1) {
      return â˜ƒ.dimensionType().ultraWarm() ? 10 : 30;
   }

   @Override
   public int getSpreadDelay(Level var1, BlockPos var2, FluidState var3, FluidState var4) {
      int â˜ƒ = this.getTickDelay(â˜ƒ);
      if (!â˜ƒ.isEmpty()
         && !â˜ƒ.isEmpty()
         && !â˜ƒ.getValue(FALLING)
         && !â˜ƒ.getValue(FALLING)
         && â˜ƒ.getHeight(â˜ƒ, â˜ƒ) > â˜ƒ.getHeight(â˜ƒ, â˜ƒ)
         && â˜ƒ.getRandom().nextInt(4) != 0) {
         â˜ƒ *= 4;
      }

      return â˜ƒ;
   }

   private void fizz(LevelAccessor var1, BlockPos var2) {
      â˜ƒ.levelEvent(1501, â˜ƒ, 0);
   }

   @Override
   protected boolean canConvertToSource() {
      return false;
   }

   @Override
   protected void spreadTo(LevelAccessor var1, BlockPos var2, BlockState var3, Direction var4, FluidState var5) {
      if (â˜ƒ == Direction.DOWN) {
         FluidState â˜ƒ = â˜ƒ.getFluidState(â˜ƒ);
         if (this.is(FluidTags.LAVA) && â˜ƒ.is(FluidTags.WATER)) {
            if (â˜ƒ.getBlock() instanceof LiquidBlock) {
               â˜ƒ.setBlock(â˜ƒ, Blocks.STONE.defaultBlockState(), 3);
            }

            this.fizz(â˜ƒ, â˜ƒ);
            return;
         }
      }

      super.spreadTo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean isRandomlyTicking() {
      return true;
   }

   @Override
   protected float getExplosionResistance() {
      return 100.0F;
   }

   @Override
   public Optional<SoundEvent> getPickupSound() {
      return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
   }

   public static class Flowing extends LavaFluid {
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

   public static class Source extends LavaFluid {
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
