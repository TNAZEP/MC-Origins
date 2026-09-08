package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BucketItem extends Item implements DispensibleContainerItem {
   private final Fluid content;

   public BucketItem(Fluid var1, Item.Properties var2) {
      super(â˜ƒ);
      this.content = â˜ƒ;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      BlockHitResult â˜ƒx = getPlayerPOVHitResult(â˜ƒ, â˜ƒ, this.content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
      if (â˜ƒx.getType() == HitResult.Type.MISS) {
         return InteractionResultHolder.pass(â˜ƒ);
      } else if (â˜ƒx.getType() != HitResult.Type.BLOCK) {
         return InteractionResultHolder.pass(â˜ƒ);
      } else {
         BlockPos â˜ƒ = â˜ƒx.getBlockPos();
         Direction â˜ƒx = â˜ƒx.getDirection();
         BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
         if (!â˜ƒ.mayInteract(â˜ƒ, â˜ƒ) || !â˜ƒ.mayUseItemAt(â˜ƒxx, â˜ƒx, â˜ƒ)) {
            return InteractionResultHolder.fail(â˜ƒ);
         } else if (this.content == Fluids.EMPTY) {
            BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
            if (â˜ƒx.getBlock() instanceof BucketPickup â˜ƒ) {
               ItemStack â˜ƒxx = â˜ƒ.pickupBlock(â˜ƒ, â˜ƒ, â˜ƒx);
               if (!â˜ƒxx.isEmpty()) {
                  â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
                  â˜ƒ.getPickupSound().ifPresent(var1x -> â˜ƒ.playSound(var1x, 1.0F, 1.0F));
                  â˜ƒ.gameEvent(â˜ƒ, GameEvent.FLUID_PICKUP, â˜ƒ);
                  ItemStack â˜ƒxxx = ItemUtils.createFilledResult(â˜ƒ, â˜ƒ, â˜ƒxx);
                  if (!â˜ƒ.isClientSide) {
                     CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)â˜ƒ, â˜ƒxx);
                  }

                  return InteractionResultHolder.sidedSuccess(â˜ƒxxx, â˜ƒ.isClientSide());
               }
            }

            return InteractionResultHolder.fail(â˜ƒ);
         } else {
            BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
            BlockPos â˜ƒx = â˜ƒ.getBlock() instanceof LiquidBlockContainer && this.content == Fluids.WATER ? â˜ƒ : â˜ƒxx;
            if (this.emptyContents(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒx)) {
               this.checkExtraContent(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
               if (â˜ƒ instanceof ServerPlayer) {
                  CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)â˜ƒ, â˜ƒx, â˜ƒ);
               }

               â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
               return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(â˜ƒ, â˜ƒ), â˜ƒ.isClientSide());
            } else {
               return InteractionResultHolder.fail(â˜ƒ);
            }
         }
      }
   }

   public static ItemStack getEmptySuccessItem(ItemStack var0, Player var1) {
      return !â˜ƒ.getAbilities().instabuild ? new ItemStack(Items.BUCKET) : â˜ƒ;
   }

   @Override
   public void checkExtraContent(@Nullable Player var1, Level var2, ItemStack var3, BlockPos var4) {
   }

   @Override
   public boolean emptyContents(@Nullable Player var1, Level var2, BlockPos var3, @Nullable BlockHitResult var4) {
      if (!(this.content instanceof FlowingFluid)) {
         return false;
      } else {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         Block â˜ƒx = â˜ƒ.getBlock();
         Material â˜ƒxx = â˜ƒ.getMaterial();
         boolean â˜ƒxxx = â˜ƒ.canBeReplaced(this.content);
         boolean â˜ƒxxxx = â˜ƒ.isAir()
            || â˜ƒxxx
            || â˜ƒx instanceof LiquidBlockContainer && ((LiquidBlockContainer)â˜ƒx).canPlaceLiquid(â˜ƒ, â˜ƒ, â˜ƒ, this.content);
         if (!â˜ƒxxxx) {
            return â˜ƒ != null && this.emptyContents(â˜ƒ, â˜ƒ, â˜ƒ.getBlockPos().relative(â˜ƒ.getDirection()), null);
         } else if (â˜ƒ.dimensionType().ultraWarm() && this.content.is(FluidTags.WATER)) {
            int â˜ƒ = â˜ƒ.getX();
            int â˜ƒx = â˜ƒ.getY();
            int â˜ƒxx = â˜ƒ.getZ();
            â˜ƒ.playSound(â˜ƒ, â˜ƒ, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (â˜ƒ.random.nextFloat() - â˜ƒ.random.nextFloat()) * 0.8F);

            for(int â˜ƒxxx = 0; â˜ƒxxx < 8; ++â˜ƒxxx) {
               â˜ƒ.addParticle(
                  ParticleTypes.LARGE_SMOKE, (double)â˜ƒ + Math.random(), (double)â˜ƒx + Math.random(), (double)â˜ƒxx + Math.random(), 0.0, 0.0, 0.0
               );
            }

            return true;
         } else if (â˜ƒx instanceof LiquidBlockContainer && this.content == Fluids.WATER) {
            ((LiquidBlockContainer)â˜ƒx).placeLiquid(â˜ƒ, â˜ƒ, â˜ƒ, ((FlowingFluid)this.content).getSource(false));
            this.playEmptySound(â˜ƒ, â˜ƒ, â˜ƒ);
            return true;
         } else {
            if (!â˜ƒ.isClientSide && â˜ƒxxx && !â˜ƒxx.isLiquid()) {
               â˜ƒ.destroyBlock(â˜ƒ, true);
            }

            if (!â˜ƒ.setBlock(â˜ƒ, this.content.defaultFluidState().createLegacyBlock(), 11) && !â˜ƒ.getFluidState().isSource()) {
               return false;
            } else {
               this.playEmptySound(â˜ƒ, â˜ƒ, â˜ƒ);
               return true;
            }
         }
      }
   }

   protected void playEmptySound(@Nullable Player var1, LevelAccessor var2, BlockPos var3) {
      SoundEvent â˜ƒ = this.content.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
      â˜ƒ.playSound(â˜ƒ, â˜ƒ, â˜ƒ, SoundSource.BLOCKS, 1.0F, 1.0F);
      â˜ƒ.gameEvent(â˜ƒ, GameEvent.FLUID_PLACE, â˜ƒ);
   }
}
