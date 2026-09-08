package net.minecraft.world.entity.ai.goal;

import java.util.List;
import net.minecraft.world.entity.animal.Animal;

public class FollowParentGoal extends Goal {
   public static final int HORIZONTAL_SCAN_RANGE = 8;
   public static final int VERTICAL_SCAN_RANGE = 4;
   public static final int DONT_FOLLOW_IF_CLOSER_THAN = 3;
   private final Animal animal;
   private Animal parent;
   private final double speedModifier;
   private int timeToRecalcPath;

   public FollowParentGoal(Animal var1, double var2) {
      this.animal = â˜ƒ;
      this.speedModifier = â˜ƒ;
   }

   @Override
   public boolean canUse() {
      if (this.animal.getAge() >= 0) {
         return false;
      } else {
         List<? extends Animal> â˜ƒ = this.animal.level.getEntitiesOfClass(this.animal.getClass(), this.animal.getBoundingBox().inflate(8.0, 4.0, 8.0));
         Animal â˜ƒx = null;
         double â˜ƒxx = Double.MAX_VALUE;

         for(Animal â˜ƒxxx : â˜ƒ) {
            if (â˜ƒxxx.getAge() >= 0) {
               double â˜ƒxxxx = this.animal.distanceToSqr(â˜ƒxxx);
               if (!(â˜ƒxxxx > â˜ƒxx)) {
                  â˜ƒxx = â˜ƒxxxx;
                  â˜ƒx = â˜ƒxxx;
               }
            }
         }

         if (â˜ƒx == null) {
            return false;
         } else if (â˜ƒxx < 9.0) {
            return false;
         } else {
            this.parent = â˜ƒx;
            return true;
         }
      }
   }

   @Override
   public boolean canContinueToUse() {
      if (this.animal.getAge() >= 0) {
         return false;
      } else if (!this.parent.isAlive()) {
         return false;
      } else {
         double â˜ƒ = this.animal.distanceToSqr(this.parent);
         return !(â˜ƒ < 9.0) && !(â˜ƒ > 256.0);
      }
   }

   @Override
   public void start() {
      this.timeToRecalcPath = 0;
   }

   @Override
   public void stop() {
      this.parent = null;
   }

   @Override
   public void tick() {
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = 10;
         this.animal.getNavigation().moveTo(this.parent, this.speedModifier);
      }
   }
}
