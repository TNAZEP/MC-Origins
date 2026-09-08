package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class LookAtPlayerGoal extends Goal {
   public static final float DEFAULT_PROBABILITY = 0.02F;
   protected final Mob mob;
   protected Entity lookAt;
   protected final float lookDistance;
   private int lookTime;
   protected final float probability;
   private final boolean onlyHorizontal;
   protected final Class<? extends LivingEntity> lookAtType;
   protected final TargetingConditions lookAtContext;

   public LookAtPlayerGoal(Mob var1, Class<? extends LivingEntity> var2, float var3) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, 0.02F);
   }

   public LookAtPlayerGoal(Mob var1, Class<? extends LivingEntity> var2, float var3, float var4) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   public LookAtPlayerGoal(Mob var1, Class<? extends LivingEntity> var2, float var3, float var4, boolean var5) {
      this.mob = â˜ƒ;
      this.lookAtType = â˜ƒ;
      this.lookDistance = â˜ƒ;
      this.probability = â˜ƒ;
      this.onlyHorizontal = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.LOOK));
      if (â˜ƒ == Player.class) {
         this.lookAtContext = TargetingConditions.forNonCombat().range((double)â˜ƒ).selector(var1x -> EntitySelector.notRiding(â˜ƒ).test(var1x));
      } else {
         this.lookAtContext = TargetingConditions.forNonCombat().range((double)â˜ƒ);
      }
   }

   @Override
   public boolean canUse() {
      if (this.mob.getRandom().nextFloat() >= this.probability) {
         return false;
      } else {
         if (this.mob.getTarget() != null) {
            this.lookAt = this.mob.getTarget();
         }

         if (this.lookAtType == Player.class) {
            this.lookAt = this.mob.level.getNearestPlayer(this.lookAtContext, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
         } else {
            this.lookAt = this.mob
               .level
               .getNearestEntity(
                  this.mob
                     .level
                     .getEntitiesOfClass(
                        this.lookAtType, this.mob.getBoundingBox().inflate((double)this.lookDistance, 3.0, (double)this.lookDistance), var0 -> true
                     ),
                  this.lookAtContext,
                  this.mob,
                  this.mob.getX(),
                  this.mob.getEyeY(),
                  this.mob.getZ()
               );
         }

         return this.lookAt != null;
      }
   }

   @Override
   public boolean canContinueToUse() {
      if (!this.lookAt.isAlive()) {
         return false;
      } else if (this.mob.distanceToSqr(this.lookAt) > (double)(this.lookDistance * this.lookDistance)) {
         return false;
      } else {
         return this.lookTime > 0;
      }
   }

   @Override
   public void start() {
      this.lookTime = 40 + this.mob.getRandom().nextInt(40);
   }

   @Override
   public void stop() {
      this.lookAt = null;
   }

   @Override
   public void tick() {
      double â˜ƒ = this.onlyHorizontal ? this.mob.getEyeY() : this.lookAt.getEyeY();
      this.mob.getLookControl().setLookAt(this.lookAt.getX(), â˜ƒ, this.lookAt.getZ());
      --this.lookTime;
   }
}
