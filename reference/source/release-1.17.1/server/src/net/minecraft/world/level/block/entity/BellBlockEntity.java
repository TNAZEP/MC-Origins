package net.minecraft.world.level.block.entity;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.mutable.MutableInt;

public class BellBlockEntity extends BlockEntity {
   private static final int DURATION = 50;
   private static final int GLOW_DURATION = 60;
   private static final int MIN_TICKS_BETWEEN_SEARCHES = 60;
   private static final int MAX_RESONATION_TICKS = 40;
   private static final int TICKS_BEFORE_RESONATION = 5;
   private static final int SEARCH_RADIUS = 48;
   private static final int HEAR_BELL_RADIUS = 32;
   private static final int HIGHLIGHT_RAIDERS_RADIUS = 48;
   private long lastRingTimestamp;
   public int ticks;
   public boolean shaking;
   public Direction clickDirection;
   private List<LivingEntity> nearbyEntities;
   private boolean resonating;
   private int resonationTicks;

   public BellBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.BELL, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean triggerEvent(int var1, int var2) {
      if (â˜ƒ == 1) {
         this.updateEntities();
         this.resonationTicks = 0;
         this.clickDirection = Direction.from3DDataValue(â˜ƒ);
         this.ticks = 0;
         this.shaking = true;
         return true;
      } else {
         return super.triggerEvent(â˜ƒ, â˜ƒ);
      }
   }

   private static void tick(Level var0, BlockPos var1, BlockState var2, BellBlockEntity var3, BellBlockEntity.ResonationEndAction var4) {
      if (â˜ƒ.shaking) {
         ++â˜ƒ.ticks;
      }

      if (â˜ƒ.ticks >= 50) {
         â˜ƒ.shaking = false;
         â˜ƒ.ticks = 0;
      }

      if (â˜ƒ.ticks >= 5 && â˜ƒ.resonationTicks == 0 && areRaidersNearby(â˜ƒ, â˜ƒ.nearbyEntities)) {
         â˜ƒ.resonating = true;
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.BELL_RESONATE, SoundSource.BLOCKS, 1.0F, 1.0F);
      }

      if (â˜ƒ.resonating) {
         if (â˜ƒ.resonationTicks < 40) {
            ++â˜ƒ.resonationTicks;
         } else {
            â˜ƒ.run(â˜ƒ, â˜ƒ, â˜ƒ.nearbyEntities);
            â˜ƒ.resonating = false;
         }
      }
   }

   public static void clientTick(Level var0, BlockPos var1, BlockState var2, BellBlockEntity var3) {
      tick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, BellBlockEntity::showBellParticles);
   }

   public static void serverTick(Level var0, BlockPos var1, BlockState var2, BellBlockEntity var3) {
      tick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, BellBlockEntity::makeRaidersGlow);
   }

   public void onHit(Direction var1) {
      BlockPos â˜ƒ = this.getBlockPos();
      this.clickDirection = â˜ƒ;
      if (this.shaking) {
         this.ticks = 0;
      } else {
         this.shaking = true;
      }

      this.level.blockEvent(â˜ƒ, this.getBlockState().getBlock(), 1, â˜ƒ.get3DDataValue());
   }

   private void updateEntities() {
      BlockPos â˜ƒ = this.getBlockPos();
      if (this.level.getGameTime() > this.lastRingTimestamp + 60L || this.nearbyEntities == null) {
         this.lastRingTimestamp = this.level.getGameTime();
         AABB â˜ƒx = new AABB(â˜ƒ).inflate(48.0);
         this.nearbyEntities = this.level.getEntitiesOfClass(LivingEntity.class, â˜ƒx);
      }

      if (!this.level.isClientSide) {
         for(LivingEntity â˜ƒ : this.nearbyEntities) {
            if (â˜ƒ.isAlive() && !â˜ƒ.isRemoved() && â˜ƒ.closerThan(â˜ƒ.position(), 32.0)) {
               â˜ƒ.getBrain().setMemory(MemoryModuleType.HEARD_BELL_TIME, this.level.getGameTime());
            }
         }
      }
   }

   private static boolean areRaidersNearby(BlockPos var0, List<LivingEntity> var1) {
      for(LivingEntity â˜ƒ : â˜ƒ) {
         if (â˜ƒ.isAlive() && !â˜ƒ.isRemoved() && â˜ƒ.closerThan(â˜ƒ.position(), 32.0) && â˜ƒ.getType().is(EntityTypeTags.RAIDERS)) {
            return true;
         }
      }

      return false;
   }

   private static void makeRaidersGlow(Level var0, BlockPos var1, List<LivingEntity> var2) {
      â˜ƒ.stream().filter(var1x -> isRaiderWithinRange(â˜ƒ, var1x)).forEach(BellBlockEntity::glow);
   }

   private static void showBellParticles(Level var0, BlockPos var1, List<LivingEntity> var2) {
      MutableInt â˜ƒ = new MutableInt(16700985);
      int â˜ƒx = (int)â˜ƒ.stream().filter(var1x -> â˜ƒ.closerThan(var1x.position(), 48.0)).count();
      â˜ƒ.stream()
         .filter(var1x -> isRaiderWithinRange(â˜ƒ, var1x))
         .forEach(
            var4x -> {
               float â˜ƒ = 1.0F;
               double â˜ƒx = Math.sqrt(
                  (var4x.getX() - (double)â˜ƒ.getX()) * (var4x.getX() - (double)â˜ƒ.getX())
                     + (var4x.getZ() - (double)â˜ƒ.getZ()) * (var4x.getZ() - (double)â˜ƒ.getZ())
               );
               double â˜ƒxx = (double)((float)â˜ƒ.getX() + 0.5F) + 1.0 / â˜ƒx * (var4x.getX() - (double)â˜ƒ.getX());
               double â˜ƒxxx = (double)((float)â˜ƒ.getZ() + 0.5F) + 1.0 / â˜ƒx * (var4x.getZ() - (double)â˜ƒ.getZ());
               int â˜ƒxxxx = Mth.clamp((â˜ƒ - 21) / -2, 3, 15);
      
               for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxxx; ++â˜ƒxxxxx) {
                  int â˜ƒxxxxxx = â˜ƒ.addAndGet(5);
                  double â˜ƒxxxxxxx = (double)FastColor.ARGB32.red(â˜ƒxxxxxx) / 255.0;
                  double â˜ƒxxxxxxxx = (double)FastColor.ARGB32.green(â˜ƒxxxxxx) / 255.0;
                  double â˜ƒxxxxxxxxx = (double)FastColor.ARGB32.blue(â˜ƒxxxxxx) / 255.0;
                  â˜ƒ.addParticle(ParticleTypes.ENTITY_EFFECT, â˜ƒxx, (double)((float)â˜ƒ.getY() + 0.5F), â˜ƒxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
               }
            }
         );
   }

   private static boolean isRaiderWithinRange(BlockPos var0, LivingEntity var1) {
      return â˜ƒ.isAlive() && !â˜ƒ.isRemoved() && â˜ƒ.closerThan(â˜ƒ.position(), 48.0) && â˜ƒ.getType().is(EntityTypeTags.RAIDERS);
   }

   private static void glow(LivingEntity var0) {
      â˜ƒ.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60));
   }

   @FunctionalInterface
   interface ResonationEndAction {
      void run(Level var1, BlockPos var2, List<LivingEntity> var3);
   }
}
