package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;

public class SecondaryPoiSensor extends Sensor<Villager> {
   private static final int SCAN_RATE = 40;

   public SecondaryPoiSensor() {
      super(40);
   }

   protected void doTick(ServerLevel var1, Villager var2) {
      ResourceKey<Level> â˜ƒ = â˜ƒ.dimension();
      BlockPos â˜ƒx = â˜ƒ.blockPosition();
      List<GlobalPos> â˜ƒxx = Lists.<GlobalPos>newArrayList();
      int â˜ƒxxx = 4;

      for(int â˜ƒxxxx = -4; â˜ƒxxxx <= 4; ++â˜ƒxxxx) {
         for(int â˜ƒxxxxx = -2; â˜ƒxxxxx <= 2; ++â˜ƒxxxxx) {
            for(int â˜ƒxxxxxx = -4; â˜ƒxxxxxx <= 4; ++â˜ƒxxxxxx) {
               BlockPos â˜ƒxxxxxxx = â˜ƒx.offset(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
               if (â˜ƒ.getVillagerData().getProfession().getSecondaryPoi().contains(â˜ƒ.getBlockState(â˜ƒxxxxxxx).getBlock())) {
                  â˜ƒxx.add(GlobalPos.of(â˜ƒ, â˜ƒxxxxxxx));
               }
            }
         }
      }

      Brain<?> â˜ƒxxxx = â˜ƒ.getBrain();
      if (!â˜ƒxx.isEmpty()) {
         â˜ƒxxxx.setMemory(MemoryModuleType.SECONDARY_JOB_SITE, â˜ƒxx);
      } else {
         â˜ƒxxxx.eraseMemory(MemoryModuleType.SECONDARY_JOB_SITE);
      }
   }

   @Override
   public Set<MemoryModuleType<?>> requires() {
      return ImmutableSet.of(MemoryModuleType.SECONDARY_JOB_SITE);
   }
}
