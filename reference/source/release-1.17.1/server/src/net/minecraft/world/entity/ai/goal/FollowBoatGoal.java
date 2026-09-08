package net.minecraft.world.entity.ai.goal;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;

public class FollowBoatGoal extends Goal {
   private int timeToRecalcPath;
   private final PathfinderMob mob;
   private Player following;
   private BoatGoals currentGoal;

   public FollowBoatGoal(PathfinderMob var1) {
      this.mob = â˜ƒ;
   }

   @Override
   public boolean canUse() {
      List<Boat> â˜ƒ = this.mob.level.getEntitiesOfClass(Boat.class, this.mob.getBoundingBox().inflate(5.0));
      boolean â˜ƒx = false;

      for(Boat â˜ƒxx : â˜ƒ) {
         Entity â˜ƒxxx = â˜ƒxx.getControllingPassenger();
         if (â˜ƒxxx instanceof Player && (Mth.abs(((Player)â˜ƒxxx).xxa) > 0.0F || Mth.abs(((Player)â˜ƒxxx).zza) > 0.0F)) {
            â˜ƒx = true;
            break;
         }
      }

      return this.following != null && (Mth.abs(this.following.xxa) > 0.0F || Mth.abs(this.following.zza) > 0.0F) || â˜ƒx;
   }

   @Override
   public boolean isInterruptable() {
      return true;
   }

   @Override
   public boolean canContinueToUse() {
      return this.following != null && this.following.isPassenger() && (Mth.abs(this.following.xxa) > 0.0F || Mth.abs(this.following.zza) > 0.0F);
   }

   @Override
   public void start() {
      for(Boat â˜ƒ : this.mob.level.getEntitiesOfClass(Boat.class, this.mob.getBoundingBox().inflate(5.0))) {
         if (â˜ƒ.getControllingPassenger() != null && â˜ƒ.getControllingPassenger() instanceof Player) {
            this.following = (Player)â˜ƒ.getControllingPassenger();
            break;
         }
      }

      this.timeToRecalcPath = 0;
      this.currentGoal = BoatGoals.GO_TO_BOAT;
   }

   @Override
   public void stop() {
      this.following = null;
   }

   @Override
   public void tick() {
      boolean â˜ƒ = Mth.abs(this.following.xxa) > 0.0F || Mth.abs(this.following.zza) > 0.0F;
      float â˜ƒx = this.currentGoal == BoatGoals.GO_IN_BOAT_DIRECTION ? (â˜ƒ ? 0.01F : 0.0F) : 0.015F;
      this.mob.moveRelative(â˜ƒx, new Vec3((double)this.mob.xxa, (double)this.mob.yya, (double)this.mob.zza));
      this.mob.move(MoverType.SELF, this.mob.getDeltaMovement());
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = 10;
         if (this.currentGoal == BoatGoals.GO_TO_BOAT) {
            BlockPos â˜ƒxx = this.following.blockPosition().relative(this.following.getDirection().getOpposite());
            â˜ƒxx = â˜ƒxx.offset(0, -1, 0);
            this.mob.getNavigation().moveTo((double)â˜ƒxx.getX(), (double)â˜ƒxx.getY(), (double)â˜ƒxx.getZ(), 1.0);
            if (this.mob.distanceTo(this.following) < 4.0F) {
               this.timeToRecalcPath = 0;
               this.currentGoal = BoatGoals.GO_IN_BOAT_DIRECTION;
            }
         } else if (this.currentGoal == BoatGoals.GO_IN_BOAT_DIRECTION) {
            Direction â˜ƒxx = this.following.getMotionDirection();
            BlockPos â˜ƒxxx = this.following.blockPosition().relative(â˜ƒxx, 10);
            this.mob.getNavigation().moveTo((double)â˜ƒxxx.getX(), (double)(â˜ƒxxx.getY() - 1), (double)â˜ƒxxx.getZ(), 1.0);
            if (this.mob.distanceTo(this.following) > 12.0F) {
               this.timeToRecalcPath = 0;
               this.currentGoal = BoatGoals.GO_TO_BOAT;
            }
         }
      }
   }
}
