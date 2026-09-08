package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.npc.Villager;

public class StrollToPoiList extends Behavior<Villager> {
   private final MemoryModuleType<List<GlobalPos>> strollToMemoryType;
   private final MemoryModuleType<GlobalPos> mustBeCloseToMemoryType;
   private final float speedModifier;
   private final int closeEnoughDist;
   private final int maxDistanceFromPoi;
   private long nextOkStartTime;
   @Nullable
   private GlobalPos targetPos;

   public StrollToPoiList(MemoryModuleType<List<GlobalPos>> var1, float var2, int var3, int var4, MemoryModuleType<GlobalPos> var5) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, â˜ƒ, MemoryStatus.VALUE_PRESENT, â˜ƒ, MemoryStatus.VALUE_PRESENT));
      this.strollToMemoryType = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.closeEnoughDist = â˜ƒ;
      this.maxDistanceFromPoi = â˜ƒ;
      this.mustBeCloseToMemoryType = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Villager var2) {
      Optional<List<GlobalPos>> â˜ƒ = â˜ƒ.getBrain().getMemory(this.strollToMemoryType);
      Optional<GlobalPos> â˜ƒx = â˜ƒ.getBrain().getMemory(this.mustBeCloseToMemoryType);
      if (â˜ƒ.isPresent() && â˜ƒx.isPresent()) {
         List<GlobalPos> â˜ƒxx = (List)â˜ƒ.get();
         if (!â˜ƒxx.isEmpty()) {
            this.targetPos = (GlobalPos)â˜ƒxx.get(â˜ƒ.getRandom().nextInt(â˜ƒxx.size()));
            return this.targetPos != null
               && â˜ƒ.dimension() == this.targetPos.dimension()
               && ((GlobalPos)â˜ƒx.get()).pos().closerThan(â˜ƒ.position(), (double)this.maxDistanceFromPoi);
         }
      }

      return false;
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      if (â˜ƒ > this.nextOkStartTime && this.targetPos != null) {
         â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(this.targetPos.pos(), this.speedModifier, this.closeEnoughDist));
         this.nextOkStartTime = â˜ƒ + 100L;
      }
   }
}
