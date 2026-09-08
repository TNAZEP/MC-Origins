package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;

public class LocateHidingPlaceDuringRaid extends LocateHidingPlace {
   public LocateHidingPlaceDuringRaid(int var1, float var2) {
      super(â˜ƒ, â˜ƒ, 1);
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      Raid â˜ƒ = â˜ƒ.getRaidAt(â˜ƒ.blockPosition());
      return super.checkExtraStartConditions(â˜ƒ, â˜ƒ) && â˜ƒ != null && â˜ƒ.isActive() && !â˜ƒ.isVictory() && !â˜ƒ.isLoss();
   }
}
