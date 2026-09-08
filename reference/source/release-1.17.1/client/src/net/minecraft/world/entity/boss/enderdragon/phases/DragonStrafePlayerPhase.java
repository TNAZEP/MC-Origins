package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonStrafePlayerPhase extends AbstractDragonPhaseInstance {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int FIREBALL_CHARGE_AMOUNT = 5;
   private int fireballCharge;
   private Path currentPath;
   private Vec3 targetLocation;
   private LivingEntity attackTarget;
   private boolean holdingPatternClockwise;

   public DragonStrafePlayerPhase(EnderDragon var1) {
      super(â˜ƒ);
   }

   @Override
   public void doServerTick() {
      if (this.attackTarget == null) {
         LOGGER.warn("Skipping player strafe phase because no player was found");
         this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
      } else {
         if (this.currentPath != null && this.currentPath.isDone()) {
            double â˜ƒ = this.attackTarget.getX();
            double â˜ƒx = this.attackTarget.getZ();
            double â˜ƒxx = â˜ƒ - this.dragon.getX();
            double â˜ƒxxx = â˜ƒx - this.dragon.getZ();
            double â˜ƒxxxx = Math.sqrt(â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx);
            double â˜ƒxxxxx = Math.min(0.4F + â˜ƒxxxx / 80.0 - 1.0, 10.0);
            this.targetLocation = new Vec3(â˜ƒ, this.attackTarget.getY() + â˜ƒxxxxx, â˜ƒx);
         }

         double â˜ƒ = this.targetLocation == null ? 0.0 : this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
         if (â˜ƒ < 100.0 || â˜ƒ > 22500.0) {
            this.findNewTarget();
         }

         double â˜ƒ = 64.0;
         if (this.attackTarget.distanceToSqr(this.dragon) < 4096.0) {
            if (this.dragon.hasLineOfSight(this.attackTarget)) {
               ++this.fireballCharge;
               Vec3 â˜ƒx = new Vec3(this.attackTarget.getX() - this.dragon.getX(), 0.0, this.attackTarget.getZ() - this.dragon.getZ()).normalize();
               Vec3 â˜ƒxx = new Vec3(
                     (double)Mth.sin(this.dragon.getYRot() * (float) (Math.PI / 180.0)),
                     0.0,
                     (double)(-Mth.cos(this.dragon.getYRot() * (float) (Math.PI / 180.0)))
                  )
                  .normalize();
               float â˜ƒxxx = (float)â˜ƒxx.dot(â˜ƒx);
               float â˜ƒxxxx = (float)(Math.acos((double)â˜ƒxxx) * 180.0F / (float)Math.PI);
               â˜ƒxxxx += 0.5F;
               if (this.fireballCharge >= 5 && â˜ƒxxxx >= 0.0F && â˜ƒxxxx < 10.0F) {
                  double â˜ƒxxxxx = 1.0;
                  Vec3 â˜ƒxxxxxx = this.dragon.getViewVector(1.0F);
                  double â˜ƒxxxxxxx = this.dragon.head.getX() - â˜ƒxxxxxx.x * 1.0;
                  double â˜ƒxxxxxxxx = this.dragon.head.getY(0.5) + 0.5;
                  double â˜ƒxxxxxxxxx = this.dragon.head.getZ() - â˜ƒxxxxxx.z * 1.0;
                  double â˜ƒxxxxxxxxxx = this.attackTarget.getX() - â˜ƒxxxxxxx;
                  double â˜ƒxxxxxxxxxxx = this.attackTarget.getY(0.5) - â˜ƒxxxxxxxx;
                  double â˜ƒxxxxxxxxxxxx = this.attackTarget.getZ() - â˜ƒxxxxxxxxx;
                  if (!this.dragon.isSilent()) {
                     this.dragon.level.levelEvent(null, 1017, this.dragon.blockPosition(), 0);
                  }

                  DragonFireball â˜ƒxxxxx = new DragonFireball(this.dragon.level, this.dragon, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx);
                  â˜ƒxxxxx.moveTo(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 0.0F, 0.0F);
                  this.dragon.level.addFreshEntity(â˜ƒxxxxx);
                  this.fireballCharge = 0;
                  if (this.currentPath != null) {
                     while(!this.currentPath.isDone()) {
                        this.currentPath.advance();
                     }
                  }

                  this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
               }
            } else if (this.fireballCharge > 0) {
               --this.fireballCharge;
            }
         } else if (this.fireballCharge > 0) {
            --this.fireballCharge;
         }
      }
   }

   private void findNewTarget() {
      if (this.currentPath == null || this.currentPath.isDone()) {
         int â˜ƒ = this.dragon.findClosestNode();
         int â˜ƒx = â˜ƒ;
         if (this.dragon.getRandom().nextInt(8) == 0) {
            this.holdingPatternClockwise = !this.holdingPatternClockwise;
            â˜ƒx = â˜ƒ + 6;
         }

         if (this.holdingPatternClockwise) {
            ++â˜ƒx;
         } else {
            --â˜ƒx;
         }

         if (this.dragon.getDragonFight() != null && this.dragon.getDragonFight().getCrystalsAlive() > 0) {
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
   public void begin() {
      this.fireballCharge = 0;
      this.targetLocation = null;
      this.currentPath = null;
      this.attackTarget = null;
   }

   public void setTarget(LivingEntity var1) {
      this.attackTarget = â˜ƒ;
      int â˜ƒ = this.dragon.findClosestNode();
      int â˜ƒx = this.dragon.findClosestNode(this.attackTarget.getX(), this.attackTarget.getY(), this.attackTarget.getZ());
      int â˜ƒxx = this.attackTarget.getBlockX();
      int â˜ƒxxx = this.attackTarget.getBlockZ();
      double â˜ƒxxxx = (double)â˜ƒxx - this.dragon.getX();
      double â˜ƒxxxxx = (double)â˜ƒxxx - this.dragon.getZ();
      double â˜ƒxxxxxx = Math.sqrt(â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxxxx * â˜ƒxxxxx);
      double â˜ƒxxxxxxx = Math.min(0.4F + â˜ƒxxxxxx / 80.0 - 1.0, 10.0);
      int â˜ƒxxxxxxxx = Mth.floor(this.attackTarget.getY() + â˜ƒxxxxxxx);
      Node â˜ƒxxxxxxxxx = new Node(â˜ƒxx, â˜ƒxxxxxxxx, â˜ƒxxx);
      this.currentPath = this.dragon.findPath(â˜ƒ, â˜ƒx, â˜ƒxxxxxxxxx);
      if (this.currentPath != null) {
         this.currentPath.advance();
         this.navigateToNextPathNode();
      }
   }

   @Nullable
   @Override
   public Vec3 getFlyTargetLocation() {
      return this.targetLocation;
   }

   @Override
   public EnderDragonPhase<DragonStrafePlayerPhase> getPhase() {
      return EnderDragonPhase.STRAFE_PLAYER;
   }
}
