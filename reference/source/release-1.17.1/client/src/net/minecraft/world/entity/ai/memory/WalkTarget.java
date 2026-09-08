package net.minecraft.world.entity.ai.memory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.phys.Vec3;

public class WalkTarget {
   private final PositionTracker target;
   private final float speedModifier;
   private final int closeEnoughDist;

   public WalkTarget(BlockPos var1, float var2, int var3) {
      this(new BlockPosTracker(â˜ƒ), â˜ƒ, â˜ƒ);
   }

   public WalkTarget(Vec3 var1, float var2, int var3) {
      this(new BlockPosTracker(new BlockPos(â˜ƒ)), â˜ƒ, â˜ƒ);
   }

   public WalkTarget(Entity var1, float var2, int var3) {
      this(new EntityTracker(â˜ƒ, false), â˜ƒ, â˜ƒ);
   }

   public WalkTarget(PositionTracker var1, float var2, int var3) {
      this.target = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.closeEnoughDist = â˜ƒ;
   }

   public PositionTracker getTarget() {
      return this.target;
   }

   public float getSpeedModifier() {
      return this.speedModifier;
   }

   public int getCloseEnoughDist() {
      return this.closeEnoughDist;
   }
}
