package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class VillageBoundRandomStroll extends Behavior<PathfinderMob> {
   private static final int MAX_XZ_DIST = 10;
   private static final int MAX_Y_DIST = 7;
   private final float speedModifier;
   private final int maxXyDist;
   private final int maxYDist;

   public VillageBoundRandomStroll(float var1) {
      this(â˜ƒ, 10, 7);
   }

   public VillageBoundRandomStroll(float var1, int var2, int var3) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
      this.speedModifier = â˜ƒ;
      this.maxXyDist = â˜ƒ;
      this.maxYDist = â˜ƒ;
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      BlockPos â˜ƒ = â˜ƒ.blockPosition();
      if (â˜ƒ.isVillage(â˜ƒ)) {
         this.setRandomPos(â˜ƒ);
      } else {
         SectionPos â˜ƒ = SectionPos.of(â˜ƒ);
         SectionPos â˜ƒx = BehaviorUtils.findSectionClosestToVillage(â˜ƒ, â˜ƒ, 2);
         if (â˜ƒx != â˜ƒ) {
            this.setTargetedPos(â˜ƒ, â˜ƒx);
         } else {
            this.setRandomPos(â˜ƒ);
         }
      }
   }

   private void setTargetedPos(PathfinderMob var1, SectionPos var2) {
      Optional<Vec3> â˜ƒ = Optional.ofNullable(
         DefaultRandomPos.getPosTowards(â˜ƒ, this.maxXyDist, this.maxYDist, Vec3.atBottomCenterOf(â˜ƒ.center()), (float) (Math.PI / 2))
      );
      â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, â˜ƒ.map(var1x -> new WalkTarget(var1x, this.speedModifier, 0)));
   }

   private void setRandomPos(PathfinderMob var1) {
      Optional<Vec3> â˜ƒ = Optional.ofNullable(LandRandomPos.getPos(â˜ƒ, this.maxXyDist, this.maxYDist));
      â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, â˜ƒ.map(var1x -> new WalkTarget(var1x, this.speedModifier, 0)));
   }
}
