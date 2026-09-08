package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.Vec3;

public class SetWalkTargetFromBlockMemory extends Behavior<Villager> {
   private final MemoryModuleType<GlobalPos> memoryType;
   private final float speedModifier;
   private final int closeEnoughDist;
   private final int tooFarDistance;
   private final int tooLongUnreachableDuration;

   public SetWalkTargetFromBlockMemory(MemoryModuleType<GlobalPos> var1, float var2, int var3, int var4, int var5) {
      super(
         ImmutableMap.of(
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryStatus.REGISTERED,
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_ABSENT,
            â˜ƒ,
            MemoryStatus.VALUE_PRESENT
         )
      );
      this.memoryType = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.closeEnoughDist = â˜ƒ;
      this.tooFarDistance = â˜ƒ;
      this.tooLongUnreachableDuration = â˜ƒ;
   }

   private void dropPOI(Villager var1, long var2) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.releasePoi(this.memoryType);
      â˜ƒ.eraseMemory(this.memoryType);
      â˜ƒ.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, â˜ƒ);
   }

   protected void start(ServerLevel var1, Villager var2, long var3) {
      Brain<?> â˜ƒ = â˜ƒ.getBrain();
      â˜ƒ.getMemory(this.memoryType).ifPresent(var6 -> {
         if (this.wrongDimension(â˜ƒ, var6) || this.tiredOfTryingToFindTarget(â˜ƒ, â˜ƒ)) {
            this.dropPOI(â˜ƒ, â˜ƒ);
         } else if (this.tooFar(â˜ƒ, var6)) {
            Vec3 â˜ƒ = null;
            int â˜ƒx = 0;

            for(int â˜ƒxx = 1000; â˜ƒx < 1000 && (â˜ƒ == null || this.tooFar(â˜ƒ, GlobalPos.of(â˜ƒ.dimension(), new BlockPos(â˜ƒ)))); ++â˜ƒx) {
               â˜ƒ = DefaultRandomPos.getPosTowards(â˜ƒ, 15, 7, Vec3.atBottomCenterOf(var6.pos()), (float) (Math.PI / 2));
            }

            if (â˜ƒx == 1000) {
               this.dropPOI(â˜ƒ, â˜ƒ);
               return;
            }

            â˜ƒ.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(â˜ƒ, this.speedModifier, this.closeEnoughDist));
         } else if (!this.closeEnough(â˜ƒ, â˜ƒ, var6)) {
            â˜ƒ.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(var6.pos(), this.speedModifier, this.closeEnoughDist));
         }
      });
   }

   private boolean tiredOfTryingToFindTarget(ServerLevel var1, Villager var2) {
      Optional<Long> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      if (â˜ƒ.isPresent()) {
         return â˜ƒ.getGameTime() - â˜ƒ.get() > (long)this.tooLongUnreachableDuration;
      } else {
         return false;
      }
   }

   private boolean tooFar(Villager var1, GlobalPos var2) {
      return â˜ƒ.pos().distManhattan(â˜ƒ.blockPosition()) > this.tooFarDistance;
   }

   private boolean wrongDimension(ServerLevel var1, GlobalPos var2) {
      return â˜ƒ.dimension() != â˜ƒ.dimension();
   }

   private boolean closeEnough(ServerLevel var1, Villager var2, GlobalPos var3) {
      return â˜ƒ.dimension() == â˜ƒ.dimension() && â˜ƒ.pos().distManhattan(â˜ƒ.blockPosition()) <= this.closeEnoughDist;
   }
}
