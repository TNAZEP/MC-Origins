package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

public class PoiCompetitorScan extends Behavior<Villager> {
   final VillagerProfession profession;

   public PoiCompetitorScan(VillagerProfession var1) {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT));
      this.profession = â˜ƒ;
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      GlobalPos â˜ƒ = (GlobalPos)â˜ƒ.getBrain().getMemory(MemoryModuleType.JOB_SITE).get();
      â˜ƒ.getPoiManager()
         .getType(â˜ƒ.pos())
         .ifPresent(
            var3x -> BehaviorUtils.getNearbyVillagersWithCondition(â˜ƒ, var3xx -> this.competesForSameJobsite(â˜ƒ, var3x, var3xx))
                  .reduce(â˜ƒ, PoiCompetitorScan::selectWinner)
         );
   }

   private static Villager selectWinner(Villager var0, Villager var1) {
      Villager â˜ƒ;
      Villager â˜ƒx;
      if (â˜ƒ.getVillagerXp() > â˜ƒ.getVillagerXp()) {
         â˜ƒ = â˜ƒ;
         â˜ƒx = â˜ƒ;
      } else {
         â˜ƒ = â˜ƒ;
         â˜ƒx = â˜ƒ;
      }

      â˜ƒx.getBrain().eraseMemory(MemoryModuleType.JOB_SITE);
      return â˜ƒ;
   }

   private boolean competesForSameJobsite(GlobalPos var1, PoiType var2, Villager var3) {
      return this.hasJobSite(â˜ƒ)
         && â˜ƒ.equals(â˜ƒ.getBrain().getMemory(MemoryModuleType.JOB_SITE).get())
         && this.hasMatchingProfession(â˜ƒ, â˜ƒ.getVillagerData().getProfession());
   }

   private boolean hasMatchingProfession(PoiType var1, VillagerProfession var2) {
      return â˜ƒ.getJobPoiType().getPredicate().test(â˜ƒ);
   }

   private boolean hasJobSite(Villager var1) {
      return â˜ƒ.getBrain().getMemory(MemoryModuleType.JOB_SITE).isPresent();
   }
}
