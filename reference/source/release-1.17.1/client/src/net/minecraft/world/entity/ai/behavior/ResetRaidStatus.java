package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;

public class ResetRaidStatus extends Behavior<LivingEntity> {
   public ResetRaidStatus() {
      super(ImmutableMap.of());
   }

   @Override
   protected boolean checkExtraStartConditions(ServerLevel var1, LivingEntity var2) {
      return â˜ƒ.random.nextInt(20) == 0;
   }

   @Override
   protected void start(ServerLevel var1, LivingEntity var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      Raid â˜ƒx = â˜ƒ.getRaidAt(â˜ƒ.blockPosition());
      if (â˜ƒx == null || â˜ƒx.isStopped() || â˜ƒx.isLoss()) {
         â˜ƒ.setDefaultActivity(Activity.IDLE);
         â˜ƒ.updateActivityFromSchedule(â˜ƒ.getDayTime(), â˜ƒ.getGameTime());
      }
   }
}
