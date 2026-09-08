package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;

public class AssignProfessionFromJobSite extends Behavior<Villager> {
   public AssignProfessionFromJobSite() {
      super(ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryStatus.VALUE_PRESENT));
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      BlockPos â˜ƒ = ((GlobalPos)â˜ƒ.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get()).pos();
      return â˜ƒ.closerThan(â˜ƒ.position(), 2.0) || â˜ƒ.assignProfessionWhenSpawned();
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      GlobalPos â˜ƒ = (GlobalPos)â˜ƒ.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get();
      â˜ƒ.getBrain().eraseMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
      â˜ƒ.getBrain().setMemory(MemoryModuleType.JOB_SITE, â˜ƒ);
      â˜ƒ.broadcastEntityEvent(â˜ƒ, (byte)14);
      if (â˜ƒ.getVillagerData().getProfession() == VillagerProfession.NONE) {
         MinecraftServer â˜ƒx = â˜ƒ.getServer();
         Optional.ofNullable(â˜ƒx.getLevel(â˜ƒ.dimension()))
            .flatMap(var1x -> var1x.getPoiManager().getType(â˜ƒ.pos()))
            .flatMap(var0 -> Registry.VILLAGER_PROFESSION.stream().filter(var1x -> var1x.getJobPoiType() == var0).findFirst())
            .ifPresent(var2x -> {
               â˜ƒ.setVillagerData(â˜ƒ.getVillagerData().setProfession(var2x));
               â˜ƒ.refreshBrain(â˜ƒ);
            });
      }
   }
}
