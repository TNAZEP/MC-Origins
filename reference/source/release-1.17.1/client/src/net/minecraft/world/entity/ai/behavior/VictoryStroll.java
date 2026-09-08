package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.raid.Raid;

public class VictoryStroll extends VillageBoundRandomStroll {
   public VictoryStroll(float var1) {
      super(â˜ƒ);
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, PathfinderMob var2) {
      Raid â˜ƒ = â˜ƒ.getRaidAt(â˜ƒ.blockPosition());
      return â˜ƒ != null && â˜ƒ.isVictory() && super.checkExtraStartConditions(â˜ƒ, â˜ƒ);
   }
}
