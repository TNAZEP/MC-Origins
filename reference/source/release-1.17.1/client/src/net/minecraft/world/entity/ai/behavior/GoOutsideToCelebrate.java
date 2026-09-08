package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;

public class GoOutsideToCelebrate extends MoveToSkySeeingSpot {
   public GoOutsideToCelebrate(float var1) {
      super(â˜ƒ);
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      Raid â˜ƒ = â˜ƒ.getRaidAt(â˜ƒ.blockPosition());
      return â˜ƒ != null && â˜ƒ.isVictory() && super.checkExtraStartConditions(â˜ƒ, â˜ƒ);
   }
}
