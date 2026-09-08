package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.LevelReader;

public abstract class MoveToBlockGoal extends Goal {
   private static final int GIVE_UP_TICKS = 1200;
   private static final int STAY_TICKS = 1200;
   private static final int INTERVAL_TICKS = 200;
   protected final PathfinderMob mob;
   public final double speedModifier;
   protected int nextStartTick;
   protected int tryTicks;
   private int maxStayTicks;
   protected BlockPos blockPos = BlockPos.ZERO;
   private boolean reachedTarget;
   private final int searchRange;
   private final int verticalSearchRange;
   protected int verticalSearchStart;

   public MoveToBlockGoal(PathfinderMob var1, double var2, int var4) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, 1);
   }

   public MoveToBlockGoal(PathfinderMob var1, double var2, int var4, int var5) {
      this.mob = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.searchRange = â˜ƒ;
      this.verticalSearchStart = 0;
      this.verticalSearchRange = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
   }

   @Override
   public boolean canUse() {
      if (this.nextStartTick > 0) {
         --this.nextStartTick;
         return false;
      } else {
         this.nextStartTick = this.nextStartTick(this.mob);
         return this.findNearestBlock();
      }
   }

   protected int nextStartTick(PathfinderMob var1) {
      return 200 + â˜ƒ.getRandom().nextInt(200);
   }

   @Override
   public boolean canContinueToUse() {
      return this.tryTicks >= -this.maxStayTicks && this.tryTicks <= 1200 && this.isValidTarget(this.mob.level, this.blockPos);
   }

   @Override
   public void start() {
      this.moveMobToBlock();
      this.tryTicks = 0;
      this.maxStayTicks = this.mob.getRandom().nextInt(this.mob.getRandom().nextInt(1200) + 1200) + 1200;
   }

   protected void moveMobToBlock() {
      this.mob
         .getNavigation()
         .moveTo(
            (double)((float)this.blockPos.getX()) + 0.5, (double)(this.blockPos.getY() + 1), (double)((float)this.blockPos.getZ()) + 0.5, this.speedModifier
         );
   }

   public double acceptedDistance() {
      return 1.0;
   }

   protected BlockPos getMoveToTarget() {
      return this.blockPos.above();
   }

   @Override
   public void tick() {
      BlockPos â˜ƒ = this.getMoveToTarget();
      if (!â˜ƒ.closerThan(this.mob.position(), this.acceptedDistance())) {
         this.reachedTarget = false;
         ++this.tryTicks;
         if (this.shouldRecalculatePath()) {
            this.mob.getNavigation().moveTo((double)((float)â˜ƒ.getX()) + 0.5, (double)â˜ƒ.getY(), (double)((float)â˜ƒ.getZ()) + 0.5, this.speedModifier);
         }
      } else {
         this.reachedTarget = true;
         --this.tryTicks;
      }
   }

   public boolean shouldRecalculatePath() {
      return this.tryTicks % 40 == 0;
   }

   protected boolean isReachedTarget() {
      return this.reachedTarget;
   }

   protected boolean findNearestBlock() {
      int â˜ƒ = this.searchRange;
      int â˜ƒx = this.verticalSearchRange;
      BlockPos â˜ƒxx = this.mob.blockPosition();
      BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxx = this.verticalSearchStart; â˜ƒxxxx <= â˜ƒx; â˜ƒxxxx = â˜ƒxxxx > 0 ? -â˜ƒxxxx : 1 - â˜ƒxxxx) {
         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒ; ++â˜ƒxxxxx) {
            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx <= â˜ƒxxxxx; â˜ƒxxxxxx = â˜ƒxxxxxx > 0 ? -â˜ƒxxxxxx : 1 - â˜ƒxxxxxx) {
               for(int â˜ƒxxxxxxx = â˜ƒxxxxxx < â˜ƒxxxxx && â˜ƒxxxxxx > -â˜ƒxxxxx ? â˜ƒxxxxx : 0;
                  â˜ƒxxxxxxx <= â˜ƒxxxxx;
                  â˜ƒxxxxxxx = â˜ƒxxxxxxx > 0 ? -â˜ƒxxxxxxx : 1 - â˜ƒxxxxxxx
               ) {
                  â˜ƒxxx.setWithOffset(â˜ƒxx, â˜ƒxxxxxx, â˜ƒxxxx - 1, â˜ƒxxxxxxx);
                  if (this.mob.isWithinRestriction(â˜ƒxxx) && this.isValidTarget(this.mob.level, â˜ƒxxx)) {
                     this.blockPos = â˜ƒxxx;
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   protected abstract boolean isValidTarget(LevelReader var1, BlockPos var2);
}
