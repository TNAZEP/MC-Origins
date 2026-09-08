package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.Vec3;

public class RamTarget<E extends PathfinderMob> extends Behavior<E> {
   public static final int TIME_OUT_DURATION = 200;
   public static final float RAM_SPEED_FORCE_FACTOR = 1.65F;
   private final Function<E, UniformInt> getTimeBetweenRams;
   private final TargetingConditions ramTargeting;
   private final float speed;
   private final ToDoubleFunction<E> getKnockbackForce;
   private Vec3 ramDirection;
   private final Function<E, SoundEvent> getImpactSound;

   public RamTarget(Function<E, UniformInt> var1, TargetingConditions var2, float var3, ToDoubleFunction<E> var4, Function<E, SoundEvent> var5) {
      super(ImmutableMap.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_PRESENT), 200);
      this.getTimeBetweenRams = â˜ƒ;
      this.ramTargeting = â˜ƒ;
      this.speed = â˜ƒ;
      this.getKnockbackForce = â˜ƒ;
      this.getImpactSound = â˜ƒ;
      this.ramDirection = Vec3.ZERO;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, PathfinderMob var2) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.RAM_TARGET);
   }

   protected boolean canStillUse(ServerLevel var1, PathfinderMob var2, long var3) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.RAM_TARGET);
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      BlockPos â˜ƒ = â˜ƒ.blockPosition();
      Brain<?> â˜ƒx = â˜ƒ.getBrain();
      Vec3 â˜ƒxx = (Vec3)â˜ƒx.getMemory(MemoryModuleType.RAM_TARGET).get();
      this.ramDirection = new Vec3((double)â˜ƒ.getX() - â˜ƒxx.x(), 0.0, (double)â˜ƒ.getZ() - â˜ƒxx.z()).normalize();
      â˜ƒx.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(â˜ƒxx, this.speed, 0));
   }

   protected void tick(ServerLevel var1, E var2, long var3) {
      List<LivingEntity> â˜ƒ = â˜ƒ.getNearbyEntities(LivingEntity.class, this.ramTargeting, â˜ƒ, â˜ƒ.getBoundingBox());
      Brain<?> â˜ƒx = â˜ƒ.getBrain();
      if (!â˜ƒ.isEmpty()) {
         LivingEntity â˜ƒxx = (LivingEntity)â˜ƒ.get(0);
         â˜ƒxx.hurt(DamageSource.mobAttack(â˜ƒ).setNoAggro(), (float)â˜ƒ.getAttributeValue(Attributes.ATTACK_DAMAGE));
         int â˜ƒxxx = â˜ƒ.hasEffect(MobEffects.MOVEMENT_SPEED) ? â˜ƒ.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
         int â˜ƒxxxx = â˜ƒ.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? â˜ƒ.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
         float â˜ƒxxxxx = 0.25F * (float)(â˜ƒxxx - â˜ƒxxxx);
         float â˜ƒxxxxxx = Mth.clamp(â˜ƒ.getSpeed() * 1.65F, 0.2F, 3.0F) + â˜ƒxxxxx;
         float â˜ƒxxxxxxx = â˜ƒxx.isDamageSourceBlocked(DamageSource.mobAttack(â˜ƒ)) ? 0.5F : 1.0F;
         â˜ƒxx.knockback((double)(â˜ƒxxxxxxx * â˜ƒxxxxxx) * this.getKnockbackForce.applyAsDouble(â˜ƒ), this.ramDirection.x(), this.ramDirection.z());
         this.finishRam(â˜ƒ, â˜ƒ);
         â˜ƒ.playSound(null, â˜ƒ, (SoundEvent)this.getImpactSound.apply(â˜ƒ), SoundSource.HOSTILE, 1.0F, 1.0F);
      } else {
         Optional<WalkTarget> â˜ƒ = â˜ƒx.getMemory(MemoryModuleType.WALK_TARGET);
         Optional<Vec3> â˜ƒx = â˜ƒx.getMemory(MemoryModuleType.RAM_TARGET);
         boolean â˜ƒxx = !â˜ƒ.isPresent() || !â˜ƒx.isPresent() || ((WalkTarget)â˜ƒ.get()).getTarget().currentPosition().distanceTo((Vec3)â˜ƒx.get()) < 0.25;
         if (â˜ƒxx) {
            this.finishRam(â˜ƒ, â˜ƒ);
         }
      }
   }

   protected void finishRam(ServerLevel var1, E var2) {
      â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)59);
      â˜ƒ.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, ((UniformInt)this.getTimeBetweenRams.apply(â˜ƒ)).sample(â˜ƒ.random));
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.RAM_TARGET);
   }
}
