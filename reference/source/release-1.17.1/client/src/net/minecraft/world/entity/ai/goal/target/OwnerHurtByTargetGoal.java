package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class OwnerHurtByTargetGoal extends TargetGoal {
   private final TamableAnimal tameAnimal;
   private LivingEntity ownerLastHurtBy;
   private int timestamp;

   public OwnerHurtByTargetGoal(TamableAnimal var1) {
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
            this.ownerLastHurtBy = â˜ƒ.getLastHurtByMob();
            int â˜ƒ = â˜ƒ.getLastHurtByMobTimestamp();
            return â˜ƒ != this.timestamp
               && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT)
               && this.tameAnimal.wantsToAttack(this.ownerLastHurtBy, â˜ƒ);
         }
      } else {
         return false;
      }
   }

   @Override
   public void start() {
      this.mob.setTarget(this.ownerLastHurtBy);
      LivingEntity â˜ƒ = this.tameAnimal.getOwner();
      if (â˜ƒ != null) {
         this.timestamp = â˜ƒ.getLastHurtByMobTimestamp();
      }

      super.start();
   }
}
