package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.LevelReader;

public class CatLieOnBedGoal extends MoveToBlockGoal {
   private final Cat cat;

   public CatLieOnBedGoal(Cat var1, double var2, int var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, 6);
      this.cat = â˜ƒ;
      this.verticalSearchStart = -2;
      this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      return this.cat.isTame() && !this.cat.isOrderedToSit() && !this.cat.isLying() && super.canUse();
   }

   @Override
   public void start() {
      super.start();
      this.cat.setInSittingPose(false);
   }

   @Override
   protected int nextStartTick(PathfinderMob var1) {
      return 40;
   }

   @Override
   public void stop() {
      super.stop();
      this.cat.setLying(false);
   }

   @Override
   public void tick() {
      super.tick();
      this.cat.setInSittingPose(false);
      if (!this.isReachedTarget()) {
         this.cat.setLying(false);
      } else if (!this.cat.isLying()) {
         this.cat.setLying(true);
      }
   }

   @Override
   protected boolean isValidTarget(LevelReader var1, BlockPos var2) {
      return â˜ƒ.isEmptyBlock(â˜ƒ.above()) && â˜ƒ.getBlockState(â˜ƒ).is(BlockTags.BEDS);
   }
}
