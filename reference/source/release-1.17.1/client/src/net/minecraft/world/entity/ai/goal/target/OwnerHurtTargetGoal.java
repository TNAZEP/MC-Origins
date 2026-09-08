package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class OwnerHurtTargetGoal extends TargetGoal {
   private final TamableAnimal tameAnimal;
   private LivingEntity ownerLastHurt;
   private int timestamp;

   public OwnerHurtTargetGoal(TamableAnimal var1) {
      super(â˜ƒ, false);
      this.tameAnimal = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.TARGET));
   }

   @Override
   public boolean canUse() {
      if (this.tameAnimal.isTame() && !this.tameAnimal.isOrderedToSit()) {
         LivingEntity â˜ƒ = this.tameAnimal.getOwner();
         if (â˜ƒ == null) {
            return false;
         } else {
            this.ownerLastHurt = â˜ƒ.getLastHurtMob();
            int â˜ƒ = â˜ƒ.getLastHurtMobTimestamp();
            return â˜ƒ != this.timestamp
               && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT)
               && this.tameAnimal.wantsToAttack(this.ownerLastHurt, â˜ƒ);
         }
      } else {
         return false;
      }
   }

   @Override
   public void start() {
      this.mob.setTarget(this.ownerLastHurt);
      LivingEntity â˜ƒ = this.tameAnimal.getOwner();
      if (â˜ƒ != null) {
         this.timestamp = â˜ƒ.getLastHurtMobTimestamp();
      }

      super.start();
   }
}
