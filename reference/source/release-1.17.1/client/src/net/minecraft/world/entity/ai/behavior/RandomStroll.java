package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class RandomStroll extends Behavior<PathfinderMob> {
   private static final int MAX_XZ_DIST = 10;
   private static final int MAX_Y_DIST = 7;
   private final float speedModifier;
   protected final int maxHorizontalDistance;
   protected final int maxVerticalDistance;
   private final boolean mayStrollFromWater;

   public RandomStroll(float var1) {
      this(â˜ƒ, true);
   }

   public RandomStroll(float var1, boolean var2) {
      this(â˜ƒ, 10, 7, â˜ƒ);
   }

   public RandomStroll(float var1, int var2, int var3) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, true);
   }

   public RandomStroll(float var1, int var2, int var3, boolean var4) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
      this.speedModifier = â˜ƒ;
      this.maxHorizontalDistance = â˜ƒ;
      this.maxVerticalDistance = â˜ƒ;
      this.mayStrollFromWater = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, PathfinderMob var2) {
      return this.mayStrollFromWater || !â˜ƒ.isInWaterOrBubble();
   }

   protected void start(ServerLevel var1, PathfinderMob var2, long var3) {
      Optional<Vec3> â˜ƒ = Optional.ofNullable(this.getTargetPos(â˜ƒ));
      â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, â˜ƒ.map(var1x -> new WalkTarget(var1x, this.speedModifier, 0)));
   }

   @Nullable
   protected Vec3 getTargetPos(PathfinderMob var1) {
      return LandRandomPos.getPos(â˜ƒ, this.maxHorizontalDistance, this.maxVerticalDistance);
   }
}
