package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;

public class ResetProfession extends Behavior<Villager> {
   public ResetProfession() {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_ABSENT));
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      VillagerData â˜ƒ = â˜ƒ.getVillagerData();
      return â˜ƒ.getProfession() != VillagerProfession.NONE
         && â˜ƒ.getProfession() != VillagerProfession.NITWIT
         && â˜ƒ.getVillagerXp() == 0
         && â˜ƒ.getLevel() <= 1;
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      â˜ƒ.setVillagerData(â˜ƒ.getVillagerData().setProfession(VillagerProfession.NONE));
      â˜ƒ.refreshBrain(â˜ƒ);
   }
}
