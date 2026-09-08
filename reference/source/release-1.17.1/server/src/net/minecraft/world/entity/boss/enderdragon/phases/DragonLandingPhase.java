package net.minecraft.world.entity.boss.enderdragon.phases;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;

public class DragonLandingPhase extends AbstractDragonPhaseInstance {
   private Vec3 targetLocation;

   public DragonLandingPhase(EnderDragon var1) {
      super(â˜ƒ);
   }

   @Override
   public void doClientTick() {
      Vec3 â˜ƒ = this.dragon.getHeadLookVector(1.0F).normalize();
      â˜ƒ.yRot((float) (-Math.PI / 4));
      double â˜ƒx = this.dragon.head.getX();
      double â˜ƒxx = this.dragon.head.getY(0.5);
      double â˜ƒxxx = this.dragon.head.getZ();

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 8; ++â˜ƒxxxx) {
         Random â˜ƒxxxxx = this.dragon.getRandom();
         double â˜ƒxxxxxx = â˜ƒx + â˜ƒxxxxx.nextGaussian() / 2.0;
         double â˜ƒxxxxxxx = â˜ƒxx + â˜ƒxxxxx.nextGaussian() / 2.0;
         double â˜ƒxxxxxxxx = â˜ƒxxx + â˜ƒxxxxx.nextGaussian() / 2.0;
         Vec3 â˜ƒxxxxxxxxx = this.dragon.getDeltaMovement();
         this.dragon
            .level
            .addParticle(
               ParticleTypes.DRAGON_BREATH,
               â˜ƒxxxxxx,
               â˜ƒxxxxxxx,
               â˜ƒxxxxxxxx,
               -â˜ƒ.x * 0.08F + â˜ƒxxxxxxxxx.x,
               -â˜ƒ.y * 0.3F + â˜ƒxxxxxxxxx.y,
               -â˜ƒ.z * 0.08F + â˜ƒxxxxxxxxx.z
            );
         â˜ƒ.yRot((float) (Math.PI / 16));
      }
   }

   @Override
   public void doServerTick() {
      if (this.targetLocation == null) {
         this.targetLocation = Vec3.atBottomCenterOf(
            this.dragon.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION)
         );
      }

      if (this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ()) < 1.0) {
         this.dragon.getPhaseManager().getPhase(EnderDragonPhase.SITTING_FLAMING).resetFlameCount();
         this.dragon.getPhaseManager().setPhase(EnderDragonPhase.SITTING_SCANNING);
      }
   }

   @Override
   public float getFlySpeed() {
      return 1.5F;
   }

   @Override
   public float getTurnSpeed() {
      float â˜ƒ = (float)this.dragon.getDeltaMovement().horizontalDistance() + 1.0F;
      float â˜ƒx = Math.min(â˜ƒ, 40.0F);
      return â˜ƒx / â˜ƒ;
   }

   @Override
   public void begin() {
      this.targetLocation = null;
   }

   @Nullable
   @Override
   public Vec3 getFlyTargetLocation() {
      return this.targetLocation;
   }

   @Override
   public EnderDragonPhase<DragonLandingPhase> getPhase() {
      return EnderDragonPhase.LANDING;
   }
}
