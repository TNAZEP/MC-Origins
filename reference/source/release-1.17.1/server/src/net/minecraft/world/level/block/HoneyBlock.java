package net.minecraft.world.level.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HoneyBlock extends HalfTransparentBlock {
   private static final double SLIDE_STARTS_WHEN_VERTICAL_SPEED_IS_AT_LEAST = 0.13;
   private static final double MIN_FALL_SPEED_TO_BE_CONSIDERED_SLIDING = 0.08;
   private static final double THROTTLE_SLIDE_SPEED_TO = 0.05;
   private static final int SLIDE_ADVANCEMENT_CHECK_INTERVAL = 20;
   protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);

   public HoneyBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   private static boolean doesEntityDoHoneyBlockSlideEffects(Entity var0) {
      return â˜ƒ instanceof LivingEntity || â˜ƒ instanceof AbstractMinecart || â˜ƒ instanceof PrimedTnt || â˜ƒ instanceof Boat;
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public void fallOn(Level var1, BlockState var2, BlockPos var3, Entity var4, float var5) {
      â˜ƒ.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
      if (!â˜ƒ.isClientSide) {
         â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)54);
      }

      if (â˜ƒ.causeFallDamage(â˜ƒ, 0.2F, DamageSource.FALL)) {
         â˜ƒ.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);
      }
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (this.isSlidingDown(â˜ƒ, â˜ƒ)) {
         this.maybeDoSlideAchievement(â˜ƒ, â˜ƒ);
         this.doSlideMovement(â˜ƒ);
         this.maybeDoSlideEffects(â˜ƒ, â˜ƒ);
      }

      super.entityInside(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private boolean isSlidingDown(BlockPos var1, Entity var2) {
      if (â˜ƒ.isOnGround()) {
         return false;
      } else if (â˜ƒ.getY() > (double)â˜ƒ.getY() + 0.9375 - 1.0E-7) {
         return false;
      } else if (â˜ƒ.getDeltaMovement().y >= -0.08) {
         return false;
      } else {
         double â˜ƒ = Math.abs((double)â˜ƒ.getX() + 0.5 - â˜ƒ.getX());
         double â˜ƒx = Math.abs((double)â˜ƒ.getZ() + 0.5 - â˜ƒ.getZ());
         double â˜ƒxx = 0.4375 + (double)(â˜ƒ.getBbWidth() / 2.0F);
         return â˜ƒ + 1.0E-7 > â˜ƒxx || â˜ƒx + 1.0E-7 > â˜ƒxx;
      }
   }

   private void maybeDoSlideAchievement(Entity var1, BlockPos var2) {
      if (â˜ƒ instanceof ServerPlayer && â˜ƒ.level.getGameTime() % 20L == 0L) {
         CriteriaTriggers.HONEY_BLOCK_SLIDE.trigger((ServerPlayer)â˜ƒ, â˜ƒ.level.getBlockState(â˜ƒ));
      }
   }

   private void doSlideMovement(Entity var1) {
      Vec3 â˜ƒ = â˜ƒ.getDeltaMovement();
      if (â˜ƒ.y < -0.13) {
         double â˜ƒx = -0.05 / â˜ƒ.y;
         â˜ƒ.setDeltaMovement(new Vec3(â˜ƒ.x * â˜ƒx, -0.05, â˜ƒ.z * â˜ƒx));
      } else {
         â˜ƒ.setDeltaMovement(new Vec3(â˜ƒ.x, -0.05, â˜ƒ.z));
      }

      â˜ƒ.fallDistance = 0.0F;
   }

   private void maybeDoSlideEffects(Level var1, Entity var2) {
      if (doesEntityDoHoneyBlockSlideEffects(â˜ƒ)) {
         if (â˜ƒ.random.nextInt(5) == 0) {
            â˜ƒ.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
         }

         if (!â˜ƒ.isClientSide && â˜ƒ.random.nextInt(5) == 0) {
            â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)53);
         }
      }
   }

   public static void showSlideParticles(Entity var0) {
      showParticles(â˜ƒ, 5);
   }

   public static void showJumpParticles(Entity var0) {
      showParticles(â˜ƒ, 10);
   }

   private static void showParticles(Entity var0, int var1) {
      if (â˜ƒ.level.isClientSide) {
         BlockState â˜ƒ = Blocks.HONEY_BLOCK.defaultBlockState();

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
            â˜ƒ.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, â˜ƒ), â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), 0.0, 0.0, 0.0);
         }
      }
   }
}
