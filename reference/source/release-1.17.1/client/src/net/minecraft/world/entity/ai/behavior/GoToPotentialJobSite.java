package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.schedule.Activity;

public class GoToPotentialJobSite extends Behavior<Villager> {
   private static final int TICKS_UNTIL_TIMEOUT = 1200;
   final float speedModifier;

   public GoToPotentialJobSite(float var1) {
      super(ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryStatus.VALUE_PRESENT), 1200);
      this.speedModifier = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      return â˜ƒ.getBrain().getActiveNonCoreActivity().map(var0 -> var0 == Activity.IDLE || var0 == Activity.WORK || var0 == Activity.PLAY).orElse(true);
   }

   protected boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.POTENTIAL_JOB_SITE);
   }

   protected void tick(ServerLevel var1, Villager var2, long var3) {
      BehaviorUtils.setWalkAndLookTargetMemories(
         â˜ƒ, ((GlobalPos)â˜ƒ.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get()).pos(), this.speedModifier, 1
      );
   }

   protected void stop(ServerLevel var1, Villager var2, long var3) {
      Optional<GlobalPos> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
      â˜ƒ.ifPresent(var1x -> {
         BlockPos â˜ƒ = var1x.pos();
         ServerLevel â˜ƒx = â˜ƒ.getServer().getLevel(var1x.dimension());
         if (â˜ƒx != null) {
            PoiManager â˜ƒxx = â˜ƒx.getPoiManager();
            if (â˜ƒxx.exists(â˜ƒ, var0x -> true)) {
               â˜ƒxx.release(â˜ƒ);
            }

            DebugPackets.sendPoiTicketCountPacket(â˜ƒ, â˜ƒ);
         }
      });
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
   }
}
