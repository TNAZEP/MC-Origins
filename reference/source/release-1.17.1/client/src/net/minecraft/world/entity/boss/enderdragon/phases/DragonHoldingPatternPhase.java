package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DragonHoldingPatternPhase extends AbstractDragonPhaseInstance {
   private static final TargetingConditions NEW_TARGET_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight();
   private Path currentPath;
   private Vec3 targetLocation;
   private boolean clockwise;

   public DragonHoldingPatternPhase(EnderDragon var1) {
      super(â˜ƒ);
   }

   @Override
   public EnderDragonPhase<DragonHoldingPatternPhase> getPhase() {
      return EnderDragonPhase.HOLDING_PATTERN;
   }

   @Override
   public void doServerTick() {
      double â˜ƒ = this.targetLocation == null ? 0.0 : this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
      if (â˜ƒ < 100.0 || â˜ƒ > 22500.0 || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
         this.findNewTarget();
      }
   }

   @Override
   public void begin() {
      this.currentPath = null;
      this.targetLocation = null;
   }

   @Nullable
   @Override
   public Vec3 getFlyTargetLocation() {
      return this.targetLocation;
   }

   private void findNewTarget() {
      if (this.currentPath != null && this.currentPath.isDone()) {
         BlockPos â˜ƒ = this.dragon.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPodiumFeature.END_PODIUM_LOCATION));
         int â˜ƒx = this.dragon.getDragonFight() == null ? 0 : this.dragon.getDragonFight().getCrystalsAlive();
         if (this.dragon.getRandom().nextInt(â˜ƒx + 3) == 0) {
            this.dragon.getPhaseManager().setPhase(EnderDragonPhase.LANDING_APPROACH);
            return;
         }

         double â˜ƒ = 64.0;
         Player â˜ƒx = this.dragon.level.getNearestPlayer(NEW_TARGET_TARGETING, this.dragon, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
         if (â˜ƒx != null) {
            â˜ƒ = â˜ƒ.distSqr(â˜ƒx.position(), true) / 512.0;
         }

         if (â˜ƒx != null && (this.dragon.getRandom().nextInt(Mth.abs((int)â˜ƒ) + 2) == 0 || this.dragon.getRandom().nextInt(â˜ƒx + 2) == 0)) {
            this.strafePlayer(â˜ƒx);
            return;
         }
      }

      if (this.currentPath == null || this.currentPath.isDone()) {
         int â˜ƒ = this.dragon.findClosestNode();
         int â˜ƒx = â˜ƒ;
         if (this.dragon.getRandom().nextInt(8) == 0) {
            this.clockwise = !this.clockwise;
            â˜ƒx = â˜ƒ + 6;
         }

         if (this.clockwise) {
            ++â˜ƒx;
         } else {
            --â˜ƒx;
         }

         if (this.dragon.getDragonFight() != null && this.dragon.getDragonFight().getCrystalsAlive() >= 0) {
            â˜ƒx %= 12;
            if (â˜ƒx < 0) {
               â˜ƒx += 12;
            }
         } else {
            â˜ƒx -= 12;
            â˜ƒx &= 7;
            â˜ƒx += 12;
         }

         this.currentPath = this.dragon.findPath(â˜ƒ, â˜ƒx, null);
         if (this.currentPath != null) {
            this.currentPath.advance();
         }
      }

      this.navigateToNextPathNode();
   }

   private void strafePlayer(Player var1) {
      this.dragon.getPhaseManager().setPhase(EnderDragonPhase.STRAFE_PLAYER);
      this.dragon.getPhaseManager().getPhase(EnderDragonPhase.STRAFE_PLAYER).setTarget(â˜ƒ);
   }

   private void navigateToNextPathNode() {
      if (this.currentPath != null && !this.currentPath.isDone()) {
         Vec3i â˜ƒ = this.currentPath.getNextNodePos();
         this.currentPath.advance();
         double â˜ƒx = (double)â˜ƒ.getX();
         double â˜ƒxx = (double)â˜ƒ.getZ();

         double â˜ƒ;
         do {
            â˜ƒ = (double)((float)â˜ƒ.getY() + this.dragon.getRandom().nextFloat() * 20.0F);
         } while(â˜ƒ < (double)â˜ƒ.getY());

         this.targetLocation = new Vec3(â˜ƒx, â˜ƒ, â˜ƒxx);
      }
   }

   @Override
   public void onCrystalDestroyed(EndCrystal var1, BlockPos var2, DamageSource var3, @Nullable Player var4) {
      if (â˜ƒ != null && this.dragon.canAttack(â˜ƒ)) {
         this.strafePlayer(â˜ƒ);
      }
   }
}
