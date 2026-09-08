package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;

public class DragonDeathPhase extends AbstractDragonPhaseInstance {
   private Vec3 targetLocation;
   private int time;

   public DragonDeathPhase(EnderDragon var1) {
      super(â˜ƒ);
   }

   @Override
   public void doClientTick() {
      if (this.time++ % 10 == 0) {
         float â˜ƒ = (this.dragon.getRandom().nextFloat() - 0.5F) * 8.0F;
         float â˜ƒx = (this.dragon.getRandom().nextFloat() - 0.5F) * 4.0F;
         float â˜ƒxx = (this.dragon.getRandom().nextFloat() - 0.5F) * 8.0F;
         this.dragon
            .level
            .addParticle(
               ParticleTypes.EXPLOSION_EMITTER,
               this.dragon.getX() + (double)â˜ƒ,
               this.dragon.getY() + 2.0 + (double)â˜ƒx,
               this.dragon.getZ() + (double)â˜ƒxx,
               0.0,
               0.0,
               0.0
            );
      }
   }

   @Override
   public void doServerTick() {
      ++this.time;
      if (this.targetLocation == null) {
         BlockPos â˜ƒ = this.dragon.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION);
         this.targetLocation = Vec3.atBottomCenterOf(â˜ƒ);
      }

      double â˜ƒ = this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
      if (!(â˜ƒ < 100.0) && !(â˜ƒ > 22500.0) && !this.dragon.horizontalCollision && !this.dragon.verticalCollision) {
         this.dragon.setHealth(1.0F);
      } else {
         this.dragon.setHealth(0.0F);
      }
   }

   @Override
   public void begin() {
      this.targetLocation = null;
      this.time = 0;
   }

   @Override
   public float getFlySpeed() {
      return 3.0F;
   }

   @Nullable
   @Override
   public Vec3 getFlyTargetLocation() {
      return this.targetLocation;
   }

   @Override
   public EnderDragonPhase<DragonDeathPhase> getPhase() {
      return EnderDragonPhase.DYING;
   }
}
