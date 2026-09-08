package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.WeighedRandom;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LongJumpToRandomPos<E extends Mob> extends Behavior<E> {
   private static final int FIND_JUMP_TRIES = 20;
   private static final int PREPARE_JUMP_DURATION = 40;
   private static final int MIN_PATHFIND_DISTANCE_TO_VALID_JUMP = 8;
   public static final int TIME_OUT_DURATION = 200;
   private final UniformInt timeBetweenLongJumps;
   private final int maxLongJumpHeight;
   private final int maxLongJumpWidth;
   private final float maxJumpVelocity;
   private final List<LongJumpToRandomPos.PossibleJump> jumpCandidates = new ArrayList();
   private Optional<Vec3> initialPosition = Optional.empty();
   private Optional<LongJumpToRandomPos.PossibleJump> chosenJump = Optional.empty();
   private int findJumpTries;
   private long prepareJumpStart;
   private Function<E, SoundEvent> getJumpSound;

   public LongJumpToRandomPos(UniformInt var1, int var2, int var3, float var4, Function<E, SoundEvent> var5) {
      super(
         ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.LONG_JUMP_MID_JUMP,
            MemoryStatus.VALUE_ABSENT
         ),
         200
      );
      this.timeBetweenLongJumps = â˜ƒ;
      this.maxLongJumpHeight = â˜ƒ;
      this.maxLongJumpWidth = â˜ƒ;
      this.maxJumpVelocity = â˜ƒ;
      this.getJumpSound = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Mob var2) {
      return â˜ƒ.isOnGround() && !â˜ƒ.getBlockState(â˜ƒ.blockPosition()).is(Blocks.HONEY_BLOCK);
   }

   protected boolean canStillUse(ServerLevel var1, Mob var2, long var3) {
      boolean â˜ƒ = this.initialPosition.isPresent()
         && ((Vec3)this.initialPosition.get()).equals(â˜ƒ.position())
         && this.findJumpTries > 0
         && (this.chosenJump.isPresent() || !this.jumpCandidates.isEmpty());
      if (!â˜ƒ && !â˜ƒ.getBrain().getMemory(MemoryModuleType.LONG_JUMP_MID_JUMP).isPresent()) {
         â˜ƒ.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, this.timeBetweenLongJumps.sample(â˜ƒ.random) / 2);
      }

      return â˜ƒ;
   }

   protected void start(ServerLevel var1, Mob var2, long var3) {
      this.chosenJump = Optional.empty();
      this.findJumpTries = 20;
      this.jumpCandidates.clear();
      this.initialPosition = Optional.of(â˜ƒ.position());
      BlockPos â˜ƒ = â˜ƒ.blockPosition();
      int â˜ƒx = â˜ƒ.getX();
      int â˜ƒxx = â˜ƒ.getY();
      int â˜ƒxxx = â˜ƒ.getZ();
      Iterable<BlockPos> â˜ƒxxxx = BlockPos.betweenClosed(
         â˜ƒx - this.maxLongJumpWidth,
         â˜ƒxx - this.maxLongJumpHeight,
         â˜ƒxxx - this.maxLongJumpWidth,
         â˜ƒx + this.maxLongJumpWidth,
         â˜ƒxx + this.maxLongJumpHeight,
         â˜ƒxxx + this.maxLongJumpWidth
      );
      PathNavigation â˜ƒxxxxx = â˜ƒ.getNavigation();

      for(BlockPos â˜ƒxxxxxx : â˜ƒxxxx) {
         double â˜ƒxxxxxxx = â˜ƒxxxxxx.distSqr(â˜ƒ);
         if ((â˜ƒx != â˜ƒxxxxxx.getX() || â˜ƒxxx != â˜ƒxxxxxx.getZ())
            && â˜ƒxxxxx.isStableDestination(â˜ƒxxxxxx)
            && â˜ƒ.getPathfindingMalus(WalkNodeEvaluator.getBlockPathTypeStatic(â˜ƒ.level, â˜ƒxxxxxx.mutable())) == 0.0F) {
            Optional<Vec3> â˜ƒxxxxxxxx = this.calculateOptimalJumpVector(â˜ƒ, Vec3.atCenterOf(â˜ƒxxxxxx));
            â˜ƒxxxxxxxx.ifPresent(var4 -> this.jumpCandidates.add(new LongJumpToRandomPos.PossibleJump(new BlockPos(â˜ƒ), var4, Mth.ceil(â˜ƒ))));
         }
      }
   }

   protected void tick(ServerLevel var1, E var2, long var3) {
      if (this.chosenJump.isPresent()) {
         if (â˜ƒ - this.prepareJumpStart >= 40L) {
            â˜ƒ.setYRot(â˜ƒ.yBodyRot);
            â˜ƒ.setDiscardFriction(true);
            Vec3 â˜ƒ = ((LongJumpToRandomPos.PossibleJump)this.chosenJump.get()).getJumpVector();
            double â˜ƒx = â˜ƒ.length();
            double â˜ƒxx = â˜ƒx + â˜ƒ.getJumpBoostPower();
            â˜ƒ.setDeltaMovement(â˜ƒ.scale(â˜ƒxx / â˜ƒx));
            â˜ƒ.getBrain().setMemory(MemoryModuleType.LONG_JUMP_MID_JUMP, true);
            â˜ƒ.playSound(null, â˜ƒ, (SoundEvent)this.getJumpSound.apply(â˜ƒ), SoundSource.NEUTRAL, 1.0F, 1.0F);
         }
      } else {
         --this.findJumpTries;
         Optional<LongJumpToRandomPos.PossibleJump> â˜ƒ = WeighedRandom.getRandomItem(â˜ƒ.random, this.jumpCandidates);
         if (â˜ƒ.isPresent()) {
            this.jumpCandidates.remove(â˜ƒ.get());
            â˜ƒ.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(((LongJumpToRandomPos.PossibleJump)â˜ƒ.get()).getJumpTarget()));
            PathNavigation â˜ƒx = â˜ƒ.getNavigation();
            Path â˜ƒxx = â˜ƒx.createPath(((LongJumpToRandomPos.PossibleJump)â˜ƒ.get()).getJumpTarget(), 0, 8);
            if (â˜ƒxx == null || !â˜ƒxx.canReach()) {
               this.chosenJump = â˜ƒ;
               this.prepareJumpStart = â˜ƒ;
            }
         }
      }
   }

   private Optional<Vec3> calculateOptimalJumpVector(Mob var1, Vec3 var2) {
      Optional<Vec3> â˜ƒ = Optional.empty();

      for(int â˜ƒx = 65; â˜ƒx < 85; â˜ƒx += 5) {
         Optional<Vec3> â˜ƒxx = this.calculateJumpVectorForAngle(â˜ƒ, â˜ƒ, â˜ƒx);
         if (!â˜ƒ.isPresent() || â˜ƒxx.isPresent() && ((Vec3)â˜ƒxx.get()).lengthSqr() < ((Vec3)â˜ƒ.get()).lengthSqr()) {
            â˜ƒ = â˜ƒxx;
         }
      }

      return â˜ƒ;
   }

   private Optional<Vec3> calculateJumpVectorForAngle(Mob var1, Vec3 var2, int var3) {
      Vec3 â˜ƒ = â˜ƒ.position();
      Vec3 â˜ƒx = new Vec3(â˜ƒ.x - â˜ƒ.x, 0.0, â˜ƒ.z - â˜ƒ.z).normalize().scale(0.5);
      â˜ƒ = â˜ƒ.subtract(â˜ƒx);
      Vec3 â˜ƒxx = â˜ƒ.subtract(â˜ƒ);
      float â˜ƒxxx = (float)â˜ƒ * (float) Math.PI / 180.0F;
      double â˜ƒxxxx = Math.atan2(â˜ƒxx.z, â˜ƒxx.x);
      double â˜ƒxxxxx = â˜ƒxx.subtract(0.0, â˜ƒxx.y, 0.0).lengthSqr();
      double â˜ƒxxxxxx = Math.sqrt(â˜ƒxxxxx);
      double â˜ƒxxxxxxx = â˜ƒxx.y;
      double â˜ƒxxxxxxxx = Math.sin((double)(2.0F * â˜ƒxxx));
      double â˜ƒxxxxxxxxx = 0.08;
      double â˜ƒxxxxxxxxxx = Math.pow(Math.cos((double)â˜ƒxxx), 2.0);
      double â˜ƒxxxxxxxxxxx = Math.sin((double)â˜ƒxxx);
      double â˜ƒxxxxxxxxxxxx = Math.cos((double)â˜ƒxxx);
      double â˜ƒxxxxxxxxxxxxx = Math.sin(â˜ƒxxxx);
      double â˜ƒxxxxxxxxxxxxxx = Math.cos(â˜ƒxxxx);
      double â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxx * 0.08 / (â˜ƒxxxxxx * â˜ƒxxxxxxxx - 2.0 * â˜ƒxxxxxxx * â˜ƒxxxxxxxxxx);
      if (â˜ƒxxxxxxxxxxxxxxx < 0.0) {
         return Optional.empty();
      } else {
         double â˜ƒ = Math.sqrt(â˜ƒxxxxxxxxxxxxxxx);
         if (â˜ƒ > (double)this.maxJumpVelocity) {
            return Optional.empty();
         } else {
            double â˜ƒ = â˜ƒ * â˜ƒxxxxxxxxxxxx;
            double â˜ƒx = â˜ƒ * â˜ƒxxxxxxxxxxx;
            int â˜ƒxx = Mth.ceil(â˜ƒxxxxxx / â˜ƒ) * 2;
            double â˜ƒxxx = 0.0;
            Vec3 â˜ƒxxxx = null;

            for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxx - 1; ++â˜ƒxxxxx) {
               â˜ƒxxx += â˜ƒxxxxxx / (double)â˜ƒxx;
               double â˜ƒxxxxxx = â˜ƒxxxxxxxxxxx / â˜ƒxxxxxxxxxxxx * â˜ƒxxx
                  - Math.pow(â˜ƒxxx, 2.0) * 0.08 / (2.0 * â˜ƒxxxxxxxxxxxxxxx * Math.pow(â˜ƒxxxxxxxxxxxx, 2.0));
               double â˜ƒxxxxxxx = â˜ƒxxx * â˜ƒxxxxxxxxxxxxxx;
               double â˜ƒxxxxxxxx = â˜ƒxxx * â˜ƒxxxxxxxxxxxxx;
               Vec3 â˜ƒxxxxxxxxx = new Vec3(â˜ƒ.x + â˜ƒxxxxxxx, â˜ƒ.y + â˜ƒxxxxxx, â˜ƒ.z + â˜ƒxxxxxxxx);
               if (â˜ƒxxxx != null && !this.isClearTransition(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxxxxx)) {
                  return Optional.empty();
               }

               â˜ƒxxxx = â˜ƒxxxxxxxxx;
            }

            return Optional.of(new Vec3(â˜ƒ * â˜ƒxxxxxxxxxxxxxx, â˜ƒx, â˜ƒ * â˜ƒxxxxxxxxxxxxx).scale(0.95F));
         }
      }
   }

   private boolean isClearTransition(Mob var1, Vec3 var2, Vec3 var3) {
      EntityDimensions â˜ƒ = â˜ƒ.getDimensions(Pose.LONG_JUMPING);
      Vec3 â˜ƒx = â˜ƒ.subtract(â˜ƒ);
      double â˜ƒxx = (double)Math.min(â˜ƒ.width, â˜ƒ.height);
      int â˜ƒxxx = Mth.ceil(â˜ƒx.length() / â˜ƒxx);
      Vec3 â˜ƒxxxx = â˜ƒx.normalize();
      Vec3 â˜ƒxxxxx = â˜ƒ;

      for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxx) {
         â˜ƒxxxxx = â˜ƒxxxxxx == â˜ƒxxx - 1 ? â˜ƒ : â˜ƒxxxxx.add(â˜ƒxxxx.scale(â˜ƒxx * 0.9F));
         AABB â˜ƒxxxxxxx = â˜ƒ.makeBoundingBox(â˜ƒxxxxx);
         if (!â˜ƒ.level.noCollision(â˜ƒ, â˜ƒxxxxxxx)) {
            return false;
         }
      }

      return true;
   }

   public static class PossibleJump extends WeighedRandom.WeighedRandomItem {
      private final BlockPos jumpTarget;
      private final Vec3 jumpVector;

      public PossibleJump(BlockPos var1, Vec3 var2, int var3) {
         super(â˜ƒ);
         this.jumpTarget = â˜ƒ;
         this.jumpVector = â˜ƒ;
      }

      public BlockPos getJumpTarget() {
         return this.jumpTarget;
      }

      public Vec3 getJumpVector() {
         return this.jumpVector;
      }
   }
}
