package net.minecraft.world.entity.ai.targeting;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class TargetingConditions {
   public static final TargetingConditions DEFAULT = forCombat();
   private static final double MIN_VISIBILITY_DISTANCE_FOR_INVISIBLE_TARGET = 2.0;
   private final boolean isCombat;
   private double range = -1.0;
   private boolean checkLineOfSight = true;
   private boolean testInvisible = true;
   private Predicate<LivingEntity> selector;

   private TargetingConditions(boolean var1) {
      this.isCombat = â˜ƒ;
   }

   public static TargetingConditions forCombat() {
      return new TargetingConditions(true);
   }

   public static TargetingConditions forNonCombat() {
      return new TargetingConditions(false);
   }

   public TargetingConditions copy() {
      TargetingConditions â˜ƒ = this.isCombat ? forCombat() : forNonCombat();
      â˜ƒ.range = this.range;
      â˜ƒ.checkLineOfSight = this.checkLineOfSight;
      â˜ƒ.testInvisible = this.testInvisible;
      â˜ƒ.selector = this.selector;
      return â˜ƒ;
   }

   public TargetingConditions range(double var1) {
      this.range = â˜ƒ;
      return this;
   }

   public TargetingConditions ignoreLineOfSight() {
      this.checkLineOfSight = false;
      return this;
   }

   public TargetingConditions ignoreInvisibilityTesting() {
      this.testInvisible = false;
      return this;
   }

   public TargetingConditions selector(@Nullable Predicate<LivingEntity> var1) {
      this.selector = â˜ƒ;
      return this;
   }

   public boolean test(@Nullable LivingEntity var1, LivingEntity var2) {
      if (â˜ƒ == â˜ƒ) {
         return false;
      } else if (!â˜ƒ.canBeSeenByAnyone()) {
         return false;
      } else if (this.selector != null && !this.selector.test(â˜ƒ)) {
         return false;
      } else {
         if (â˜ƒ == null) {
            if (this.isCombat && (!â˜ƒ.canBeSeenAsEnemy() || â˜ƒ.level.getDifficulty() == Difficulty.PEACEFUL)) {
               return false;
            }
         } else {
            if (this.isCombat && (!â˜ƒ.canAttack(â˜ƒ) || !â˜ƒ.canAttackType(â˜ƒ.getType()) || â˜ƒ.isAlliedTo(â˜ƒ))) {
               return false;
            }

            if (this.range > 0.0) {
               double â˜ƒ = this.testInvisible ? â˜ƒ.getVisibilityPercent(â˜ƒ) : 1.0;
               double â˜ƒx = Math.max(this.range * â˜ƒ, 2.0);
               double â˜ƒxx = â˜ƒ.distanceToSqr(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
               if (â˜ƒxx > â˜ƒx * â˜ƒx) {
                  return false;
               }
            }

            if (this.checkLineOfSight && â˜ƒ instanceof Mob && !((Mob)â˜ƒ).getSensing().hasLineOfSight(â˜ƒ)) {
               return false;
            }
         }

         return true;
      }
   }
}
