package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.pathfinder.Path;

public class YieldJobSite extends Behavior<Villager> {
   private final float speedModifier;

   public YieldJobSite(float var1) {
      super(
         ImmutableMap.of(
            MemoryModuleType.POTENTIAL_JOB_SITE,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.JOB_SITE,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryStatus.VALUE_PRESENT
         )
      );
      this.speedModifier = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      if (â˜ƒ.isBaby()) {
         return false;
      } else {
         return â˜ƒ.getVillagerData().getProfession() == VillagerProfession.NONE;
      }
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      BlockPos â˜ƒ = ((GlobalPos)â˜ƒ.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get()).pos();
      Optional<PoiType> â˜ƒx = â˜ƒ.getPoiManager().getType(â˜ƒ);
      if (â˜ƒx.isPresent()) {
         BehaviorUtils.getNearbyVillagersWithCondition(â˜ƒ, var3x -> this.nearbyWantsJobsite((PoiType)â˜ƒ.get(), var3x, â˜ƒ))
            .findFirst()
            .ifPresent(var4 -> this.yieldJobSite(â˜ƒ, â˜ƒ, var4, â˜ƒ, var4.getBrain().getMemory(MemoryModuleType.JOB_SITE).isPresent()));
      }
   }

   private boolean nearbyWantsJobsite(PoiType var1, Villager var2, BlockPos var3) {
      boolean â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).isPresent();
      if (â˜ƒ) {
         return false;
      } else {
         Optional<GlobalPos> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.JOB_SITE);
         VillagerProfession â˜ƒx = â˜ƒ.getVillagerData().getProfession();
         if (â˜ƒ.getVillagerData().getProfession() == VillagerProfession.NONE || !â˜ƒx.getJobPoiType().getPredicate().test(â˜ƒ)) {
            return false;
         } else {
            return !â˜ƒ.isPresent() ? this.canReachPos(â˜ƒ, â˜ƒ, â˜ƒ) : ((GlobalPos)â˜ƒ.get()).pos().equals(â˜ƒ);
         }
      }
   }

   private void yieldJobSite(ServerLevel var1, Villager var2, Villager var3, BlockPos var4, boolean var5) {
      this.eraseMemories(â˜ƒ);
      if (!â˜ƒ) {
         BehaviorUtils.setWalkAndLookTargetMemories(â˜ƒ, â˜ƒ, this.speedModifier, 1);
         â˜ƒ.getBrain().setMemory(MemoryModuleType.POTENTIAL_JOB_SITE, GlobalPos.of(â˜ƒ.dimension(), â˜ƒ));
         DebugPackets.sendPoiTicketCountPacket(â˜ƒ, â˜ƒ);
      }
   }

   private boolean canReachPos(Villager var1, BlockPos var2, PoiType var3) {
      Path â˜ƒ = â˜ƒ.getNavigation().createPath(â˜ƒ, â˜ƒ.getValidRange());
      return â˜ƒ != null && â˜ƒ.canReach();
   }

   private void eraseMemories(Villager var1) {
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
   }
}
