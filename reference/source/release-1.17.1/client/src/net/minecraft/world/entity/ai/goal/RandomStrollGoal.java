package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class RandomStrollGoal extends Goal {
   public static final int DEFAULT_INTERVAL = 120;
   protected final PathfinderMob mob;
   protected double wantedX;
   protected double wantedY;
   protected double wantedZ;
   protected final double speedModifier;
   protected int interval;
   protected boolean forceTrigger;
   private final boolean checkNoActionTime;

   public RandomStrollGoal(PathfinderMob var1, double var2) {
      this(â˜ƒ, â˜ƒ, 120);
   }

   public RandomStrollGoal(PathfinderMob var1, double var2, int var4) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, true);
   }

   public RandomStrollGoal(PathfinderMob var1, double var2, int var4, boolean var5) {
      this.mob = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.interval = â˜ƒ;
      this.checkNoActionTime = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      if (this.mob.isVehicle()) {
         return false;
      } else {
         if (!this.forceTrigger) {
            if (this.checkNoActionTime && this.mob.getNoActionTime() >= 100) {
               return false;
            }

            if (this.mob.getRandom().nextInt(this.interval) != 0) {
               return false;
            }
         }

         Vec3 â˜ƒ = this.getPosition();
         if (â˜ƒ == null) {
            return false;
         } else {
            this.wantedX = â˜ƒ.x;
            this.wantedY = â˜ƒ.y;
            this.wantedZ = â˜ƒ.z;
            this.forceTrigger = false;
            return true;
         }
      }
   }

   @Nullable
   protected Vec3 getPosition() {
      return DefaultRandomPos.getPos(this.mob, 10, 7);
   }

   @Override
   public boolean canContinueToUse() {
      return !this.mob.getNavigation().isDone() && !this.mob.isVehicle();
   }

   @Override
   public void start() {
      this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
   }

   @Override
   public void stop() {
      this.mob.getNavigation().stop();
      super.stop();
   }

   public void trigger() {
      this.forceTrigger = true;
   }

   public void setInterval(int var1) {
      this.interval = â˜ƒ;
   }
}
