package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;

public class Swim extends Behavior<Mob> {
   private final float chance;

   public Swim(float var1) {
      super(ImmutableMap.of());
      this.chance = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Mob var2) {
      return â˜ƒ.isInWater() && â˜ƒ.getFluidHeight(FluidTags.WATER) > â˜ƒ.getFluidJumpThreshold() || â˜ƒ.isInLava();
   }

   protected boolean canStillUse(ServerLevel var1, Mob var2, long var3) {
      return this.checkExtraStartConditions(â˜ƒ, â˜ƒ);
   }

   protected void tick(ServerLevel var1, Mob var2, long var3) {
      if (â˜ƒ.getRandom().nextFloat() < this.chance) {
         â˜ƒ.getJumpControl().jump();
      }
   }
}
