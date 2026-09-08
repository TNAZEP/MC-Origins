package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;

public class TradeWithPlayerGoal extends Goal {
   private final AbstractVillager mob;

   public TradeWithPlayerGoal(AbstractVillager var1) {
      this.mob = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      if (!this.mob.isAlive()) {
         return false;
      } else if (this.mob.isInWater()) {
         return false;
      } else if (!this.mob.isOnGround()) {
         return false;
      } else if (this.mob.hurtMarked) {
         return false;
      } else {
         Player â˜ƒ = this.mob.getTradingPlayer();
         if (â˜ƒ == null) {
            return false;
         } else if (this.mob.distanceToSqr(â˜ƒ) > 16.0) {
            return false;
         } else {
            return â˜ƒ.containerMenu != null;
         }
      }
   }

   @Override
   public void start() {
      this.mob.getNavigation().stop();
   }

   @Override
   public void stop() {
      this.mob.setTradingPlayer(null);
   }
}
