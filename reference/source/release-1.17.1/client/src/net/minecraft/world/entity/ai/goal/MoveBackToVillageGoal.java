package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class MoveBackToVillageGoal extends RandomStrollGoal {
   private static final int MAX_XZ_DIST = 10;
   private static final int MAX_Y_DIST = 7;

   public MoveBackToVillageGoal(PathfinderMob var1, double var2, boolean var4) {
      super(â˜ƒ, â˜ƒ, 10, â˜ƒ);
   }

   @Override
   public boolean canUse() {
      ServerLevel â˜ƒ = (ServerLevel)this.mob.level;
      BlockPos â˜ƒx = this.mob.blockPosition();
      return â˜ƒ.isVillage(â˜ƒx) ? false : super.canUse();
   }

   @Nullable
   @Override
   protected Vec3 getPosition() {
      ServerLevel â˜ƒ = (ServerLevel)this.mob.level;
      BlockPos â˜ƒx = this.mob.blockPosition();
      SectionPos â˜ƒxx = SectionPos.of(â˜ƒx);
      SectionPos â˜ƒxxx = BehaviorUtils.findSectionClosestToVillage(â˜ƒ, â˜ƒxx, 2);
      return â˜ƒxxx != â˜ƒxx ? DefaultRandomPos.getPosTowards(this.mob, 10, 7, Vec3.atBottomCenterOf(â˜ƒxxx.center()), (float) (Math.PI / 2)) : null;
   }
}
