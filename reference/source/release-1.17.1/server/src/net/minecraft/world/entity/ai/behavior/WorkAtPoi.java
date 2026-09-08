package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.npc.Villager;

public class WorkAtPoi extends Behavior<Villager> {
   private static final int CHECK_COOLDOWN = 300;
   private static final double DISTANCE = 1.73;
   private long lastCheck;

   public WorkAtPoi() {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED));
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      if (â˜ƒ.getGameTime() - this.lastCheck < 300L) {
         return false;
      } else if (â˜ƒ.random.nextInt(2) != 0) {
         return false;
      } else {
         this.lastCheck = â˜ƒ.getGameTime();
         GlobalPos â˜ƒ = (GlobalPos)â˜ƒ.getBrain().getMemory(MemoryModuleType.JOB_SITE).get();
         return â˜ƒ.dimension() == â˜ƒ.dimension() && â˜ƒ.pos().closerThan(â˜ƒ.position(), 1.73);
      }
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      Brain<Villager> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.setMemory(MemoryModuleType.LAST_WORKED_AT_POI, â˜ƒ);
      â˜ƒ.getMemory(MemoryModuleType.JOB_SITE).ifPresent(var1x -> â˜ƒ.setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(var1x.pos())));
      â˜ƒ.playWorkSound();
      this.useWorkstation(â˜ƒ, â˜ƒ);
      if (â˜ƒ.shouldRestock()) {
         â˜ƒ.restock();
      }
   }

   protected void useWorkstation(ServerLevel var1, Villager var2) {
   }

   protected boolean canStillUse(ServerLevel var1, Villager var2, long var3) {
      Optional<GlobalPos> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.JOB_SITE);
      if (!â˜ƒ.isPresent()) {
         return false;
      } else {
         GlobalPos â˜ƒ = (GlobalPos)â˜ƒ.get();
         return â˜ƒ.dimension() == â˜ƒ.dimension() && â˜ƒ.pos().closerThan(â˜ƒ.position(), 1.73);
      }
   }
}
