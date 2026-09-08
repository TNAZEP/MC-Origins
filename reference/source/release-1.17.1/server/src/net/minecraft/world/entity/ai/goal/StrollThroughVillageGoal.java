package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class StrollThroughVillageGoal extends Goal {
   private static final int DISTANCE_THRESHOLD = 10;
   private final PathfinderMob mob;
   private final int interval;
   @Nullable
   private BlockPos wantedPos;

   public StrollThroughVillageGoal(PathfinderMob var1, int var2) {
      this.mob = â˜ƒ;
      this.interval = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      if (this.mob.isVehicle()) {
         return false;
      } else if (this.mob.level.isDay()) {
         return false;
      } else if (this.mob.getRandom().nextInt(this.interval) != 0) {
         return false;
      } else {
         ServerLevel â˜ƒ = (ServerLevel)this.mob.level;
         BlockPos â˜ƒx = this.mob.blockPosition();
         if (!â˜ƒ.isCloseToVillage(â˜ƒx, 6)) {
            return false;
         } else {
            Vec3 â˜ƒ = LandRandomPos.getPos(this.mob, 15, 7, var1x -> (double)(-â˜ƒ.sectionsToVillage(SectionPos.of(var1x))));
            this.wantedPos = â˜ƒ == null ? null : new BlockPos(â˜ƒ);
            return this.wantedPos != null;
         }
      }
   }

   @Override
   public boolean canContinueToUse() {
      return this.wantedPos != null && !this.mob.getNavigation().isDone() && this.mob.getNavigation().getTargetPos().equals(this.wantedPos);
   }

   @Override
   public void tick() {
      if (this.wantedPos != null) {
         PathNavigation â˜ƒ = this.mob.getNavigation();
         if (â˜ƒ.isDone() && !this.wantedPos.closerThan(this.mob.position(), 10.0)) {
            Vec3 â˜ƒx = Vec3.atBottomCenterOf(this.wantedPos);
            Vec3 â˜ƒxx = this.mob.position();
            Vec3 â˜ƒxxx = â˜ƒxx.subtract(â˜ƒx);
            â˜ƒx = â˜ƒxxx.scale(0.4).add(â˜ƒx);
            Vec3 â˜ƒxxxx = â˜ƒx.subtract(â˜ƒxx).normalize().scale(10.0).add(â˜ƒxx);
            BlockPos â˜ƒxxxxx = new BlockPos(â˜ƒxxxx);
            â˜ƒxxxxx = this.mob.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, â˜ƒxxxxx);
            if (!â˜ƒ.moveTo((double)â˜ƒxxxxx.getX(), (double)â˜ƒxxxxx.getY(), (double)â˜ƒxxxxx.getZ(), 1.0)) {
               this.moveRandomly();
            }
         }
      }
   }

   private void moveRandomly() {
      Random â˜ƒ = this.mob.getRandom();
      BlockPos â˜ƒx = this.mob
         .level
         .getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.mob.blockPosition().offset(-8 + â˜ƒ.nextInt(16), 0, -8 + â˜ƒ.nextInt(16)));
      this.mob.getNavigation().moveTo((double)â˜ƒx.getX(), (double)â˜ƒx.getY(), (double)â˜ƒx.getZ(), 1.0);
   }
}
