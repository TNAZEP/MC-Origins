package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;

public class JumpOnBed extends Behavior<Mob> {
   private static final int MAX_TIME_TO_REACH_BED = 100;
   private static final int MIN_JUMPS = 3;
   private static final int MAX_JUMPS = 6;
   private static final int COOLDOWN_BETWEEN_JUMPS = 5;
   private final float speedModifier;
   @Nullable
   private BlockPos targetBed;
   private int remainingTimeToReachBed;
   private int remainingJumps;
   private int remainingCooldownUntilNextJump;

   public JumpOnBed(float var1) {
      super(ImmutableMap.of(MemoryModuleType.NEAREST_BED, MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
      this.speedModifier = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Mob var2) {
      return â˜ƒ.isBaby() && this.nearBed(â˜ƒ, â˜ƒ);
   }

   protected void start(ServerLevel var1, Mob var2, long var3) {
      super.start(â˜ƒ, â˜ƒ, â˜ƒ);
      this.getNearestBed(â˜ƒ).ifPresent(var3x -> {
         this.targetBed = var3x;
         this.remainingTimeToReachBed = 100;
         this.remainingJumps = 3 + â˜ƒ.random.nextInt(4);
         this.remainingCooldownUntilNextJump = 0;
         this.startWalkingTowardsBed(â˜ƒ, var3x);
      });
   }

   protected void stop(ServerLevel var1, Mob var2, long var3) {
      super.stop(â˜ƒ, â˜ƒ, â˜ƒ);
      this.targetBed = null;
      this.remainingTimeToReachBed = 0;
      this.remainingJumps = 0;
      this.remainingCooldownUntilNextJump = 0;
   }

   protected boolean canStillUse(ServerLevel var1, Mob var2, long var3) {
      return â˜ƒ.isBaby() && this.targetBed != null && this.isBed(â˜ƒ, this.targetBed) && !this.tiredOfWalking(â˜ƒ, â˜ƒ) && !this.tiredOfJumping(â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean timedOut(long var1) {
      return false;
   }

   protected void tick(ServerLevel var1, Mob var2, long var3) {
      if (!this.onOrOverBed(â˜ƒ, â˜ƒ)) {
         --this.remainingTimeToReachBed;
      } else if (this.remainingCooldownUntilNextJump > 0) {
         --this.remainingCooldownUntilNextJump;
      } else {
         if (this.onBedSurface(â˜ƒ, â˜ƒ)) {
            â˜ƒ.getJumpControl().jump();
            --this.remainingJumps;
            this.remainingCooldownUntilNextJump = 5;
         }
      }
   }

   private void startWalkingTowardsBed(Mob var1, BlockPos var2) {
      â˜ƒ.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(â˜ƒ, this.speedModifier, 0));
   }

   private boolean nearBed(ServerLevel var1, Mob var2) {
      return this.onOrOverBed(â˜ƒ, â˜ƒ) || this.getNearestBed(â˜ƒ).isPresent();
   }

   private boolean onOrOverBed(ServerLevel var1, Mob var2) {
      BlockPos â˜ƒ = â˜ƒ.blockPosition();
      BlockPos â˜ƒx = â˜ƒ.below();
      return this.isBed(â˜ƒ, â˜ƒ) || this.isBed(â˜ƒ, â˜ƒx);
   }

   private boolean onBedSurface(ServerLevel var1, Mob var2) {
      return this.isBed(â˜ƒ, â˜ƒ.blockPosition());
   }

   private boolean isBed(ServerLevel var1, BlockPos var2) {
      return â˜ƒ.getBlockState(â˜ƒ).is(BlockTags.BEDS);
   }

   private Optional<BlockPos> getNearestBed(Mob var1) {
      return â˜ƒ.getBrain().getMemory(MemoryModuleType.NEAREST_BED);
   }

   private boolean tiredOfWalking(ServerLevel var1, Mob var2) {
      return !this.onOrOverBed(â˜ƒ, â˜ƒ) && this.remainingTimeToReachBed <= 0;
   }

   private boolean tiredOfJumping(ServerLevel var1, Mob var2) {
      return this.onOrOverBed(â˜ƒ, â˜ƒ) && this.remainingJumps <= 0;
   }
}
