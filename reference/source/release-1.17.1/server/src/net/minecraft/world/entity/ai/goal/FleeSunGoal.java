package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FleeSunGoal extends Goal {
   protected final PathfinderMob mob;
   private double wantedX;
   private double wantedY;
   private double wantedZ;
   private final double speedModifier;
   private final Level level;

   public FleeSunGoal(PathfinderMob var1, double var2) {
      this.mob = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.level = â˜ƒ.level;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      if (this.mob.getTarget() != null) {
         return false;
      } else if (!this.level.isDay()) {
         return false;
      } else if (!this.mob.isOnFire()) {
         return false;
      } else if (!this.level.canSeeSky(this.mob.blockPosition())) {
         return false;
      } else {
         return !this.mob.getItemBySlot(EquipmentSlot.HEAD).isEmpty() ? false : this.setWantedPos();
      }
   }

   protected boolean setWantedPos() {
      Vec3 â˜ƒ = this.getHidePos();
      if (â˜ƒ == null) {
         return false;
      } else {
         this.wantedX = â˜ƒ.x;
         this.wantedY = â˜ƒ.y;
         this.wantedZ = â˜ƒ.z;
         return true;
      }
   }

   @Override
   public boolean canContinueToUse() {
      return !this.mob.getNavigation().isDone();
   }

   @Override
   public void start() {
      this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
   }

   @Nullable
   protected Vec3 getHidePos() {
      Random â˜ƒ = this.mob.getRandom();
      BlockPos â˜ƒx = this.mob.blockPosition();

      for(int â˜ƒxx = 0; â˜ƒxx < 10; ++â˜ƒxx) {
         BlockPos â˜ƒxxx = â˜ƒx.offset(â˜ƒ.nextInt(20) - 10, â˜ƒ.nextInt(6) - 3, â˜ƒ.nextInt(20) - 10);
         if (!this.level.canSeeSky(â˜ƒxxx) && this.mob.getWalkTargetValue(â˜ƒxxx) < 0.0F) {
            return Vec3.atBottomCenterOf(â˜ƒxxx);
         }
      }

      return null;
   }
}
