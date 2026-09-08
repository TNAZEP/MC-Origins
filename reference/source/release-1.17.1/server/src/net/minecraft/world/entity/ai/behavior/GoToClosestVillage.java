package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;

public class GoToClosestVillage extends Behavior<Villager> {
   private final float speedModifier;
   private final int closeEnoughDistance;

   public GoToClosestVillage(float var1, int var2) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
      this.speedModifier = â˜ƒ;
      this.closeEnoughDistance = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      return !â˜ƒ.isVillage(â˜ƒ.blockPosition());
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      PoiManager â˜ƒ = â˜ƒ.getPoiManager();
      int â˜ƒx = â˜ƒ.sectionsToVillage(SectionPos.of(â˜ƒ.blockPosition()));
      Vec3 â˜ƒxx = null;

      for(int â˜ƒxxx = 0; â˜ƒxxx < 5; ++â˜ƒxxx) {
         Vec3 â˜ƒxxxx = LandRandomPos.getPos(â˜ƒ, 15, 7, var1x -> (double)(-â˜ƒ.sectionsToVillage(SectionPos.of(var1x))));
         if (â˜ƒxxxx != null) {
            int â˜ƒxxxxx = â˜ƒ.sectionsToVillage(SectionPos.of(new BlockPos(â˜ƒxxxx)));
            if (â˜ƒxxxxx < â˜ƒx) {
               â˜ƒxx = â˜ƒxxxx;
               break;
            }

            if (â˜ƒxxxxx == â˜ƒx) {
               â˜ƒxx = â˜ƒxxxx;
            }
         }
      }

      if (â˜ƒxx != null) {
         â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(â˜ƒxx, this.speedModifier, this.closeEnoughDistance));
      }
   }
}
