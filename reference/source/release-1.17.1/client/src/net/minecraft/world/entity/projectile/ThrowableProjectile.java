package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class ThrowableProjectile extends Projectile {
   protected ThrowableProjectile(EntityType<? extends ThrowableProjectile> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   protected ThrowableProjectile(EntityType<? extends ThrowableProjectile> var1, double var2, double var4, double var6, Level var8) {
      this(â˜ƒ, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected ThrowableProjectile(EntityType<? extends ThrowableProjectile> var1, LivingEntity var2, Level var3) {
      this(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getEyeY() - 0.1F, â˜ƒ.getZ(), â˜ƒ);
      this.setOwner(â˜ƒ);
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      double â˜ƒ = this.getBoundingBox().getSize() * 4.0;
      if (Double.isNaN(â˜ƒ)) {
         â˜ƒ = 4.0;
      }

      â˜ƒ *= 64.0;
      return â˜ƒ < â˜ƒ * â˜ƒ;
   }

   @Override
   public void tick() {
      super.tick();
      HitResult â˜ƒ = ProjectileUtil.getHitResult(this, this::canHitEntity);
      boolean â˜ƒx = false;
      if (â˜ƒ.getType() == HitResult.Type.BLOCK) {
         BlockPos â˜ƒxx = ((BlockHitResult)â˜ƒ).getBlockPos();
         BlockState â˜ƒxxx = this.level.getBlockState(â˜ƒxx);
         if (â˜ƒxxx.is(Blocks.NETHER_PORTAL)) {
            this.handleInsidePortal(â˜ƒxx);
            â˜ƒx = true;
         } else if (â˜ƒxxx.is(Blocks.END_GATEWAY)) {
            BlockEntity â˜ƒxx = this.level.getBlockEntity(â˜ƒxx);
            if (â˜ƒxx instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
               TheEndGatewayBlockEntity.teleportEntity(this.level, â˜ƒxx, â˜ƒxxx, this, (TheEndGatewayBlockEntity)â˜ƒxx);
            }

            â˜ƒx = true;
         }
      }

      if (â˜ƒ.getType() != HitResult.Type.MISS && !â˜ƒx) {
         this.onHit(â˜ƒ);
      }

      this.checkInsideBlocks();
      Vec3 â˜ƒx = this.getDeltaMovement();
      double â˜ƒxx = this.getX() + â˜ƒx.x;
      double â˜ƒxxx = this.getY() + â˜ƒx.y;
      double â˜ƒxxxx = this.getZ() + â˜ƒx.z;
      this.updateRotation();
      float â˜ƒ;
      if (this.isInWater()) {
         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 4; ++â˜ƒxxxxx) {
            float â˜ƒxxxxxx = 0.25F;
            this.level.addParticle(ParticleTypes.BUBBLE, â˜ƒxx - â˜ƒx.x * 0.25, â˜ƒxxx - â˜ƒx.y * 0.25, â˜ƒxxxx - â˜ƒx.z * 0.25, â˜ƒx.x, â˜ƒx.y, â˜ƒx.z);
         }

         â˜ƒ = 0.8F;
      } else {
         â˜ƒ = 0.99F;
      }

      this.setDeltaMovement(â˜ƒx.scale((double)â˜ƒ));
      if (!this.isNoGravity()) {
         Vec3 â˜ƒ = this.getDeltaMovement();
         this.setDeltaMovement(â˜ƒ.x, â˜ƒ.y - (double)this.getGravity(), â˜ƒ.z);
      }

      this.setPos(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
   }

   protected float getGravity() {
      return 0.03F;
   }
}
