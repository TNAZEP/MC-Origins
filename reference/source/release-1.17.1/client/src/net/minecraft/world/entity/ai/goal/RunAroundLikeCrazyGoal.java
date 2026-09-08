package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class RunAroundLikeCrazyGoal extends Goal {
   private final AbstractHorse horse;
   private final double speedModifier;
   private double posX;
   private double posY;
   private double posZ;

   public RunAroundLikeCrazyGoal(AbstractHorse var1, double var2) {
      this.horse = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      if (!this.horse.isTamed() && this.horse.isVehicle()) {
         Vec3 â˜ƒ = DefaultRandomPos.getPos(this.horse, 5, 4);
         if (â˜ƒ == null) {
            return false;
         } else {
            this.posX = â˜ƒ.x;
            this.posY = â˜ƒ.y;
            this.posZ = â˜ƒ.z;
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public void start() {
      this.horse.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
   }

   @Override
   public boolean canContinueToUse() {
      return !this.horse.isTamed() && !this.horse.getNavigation().isDone() && this.horse.isVehicle();
   }

   @Override
   public void tick() {
      if (!this.horse.isTamed() && this.horse.getRandom().nextInt(50) == 0) {
         Entity â˜ƒ = (Entity)this.horse.getPassengers().get(0);
         if (â˜ƒ == null) {
            return;
         }

         if (â˜ƒ instanceof Player) {
            int â˜ƒ = this.horse.getTemper();
            int â˜ƒx = this.horse.getMaxTemper();
            if (â˜ƒx > 0 && this.horse.getRandom().nextInt(â˜ƒx) < â˜ƒ) {
               this.horse.tameWithName((Player)â˜ƒ);
               return;
            }

            this.horse.modifyTemper(5);
         }

         this.horse.ejectPassengers();
         this.horse.makeMad();
         this.horse.level.broadcastEntityEvent(this.horse, (byte)6);
      }
   }
}
