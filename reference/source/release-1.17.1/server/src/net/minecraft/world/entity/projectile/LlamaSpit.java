package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class LlamaSpit extends Projectile {
   public LlamaSpit(EntityType<? extends LlamaSpit> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public LlamaSpit(Level var1, Llama var2) {
      this(EntityType.LLAMA_SPIT, â˜ƒ);
      this.setOwner(â˜ƒ);
      this.setPos(
         â˜ƒ.getX() - (double)(â˜ƒ.getBbWidth() + 1.0F) * 0.5 * (double)Mth.sin(â˜ƒ.yBodyRot * (float) (Math.PI / 180.0)),
         â˜ƒ.getEyeY() - 0.1F,
         â˜ƒ.getZ() + (double)(â˜ƒ.getBbWidth() + 1.0F) * 0.5 * (double)Mth.cos(â˜ƒ.yBodyRot * (float) (Math.PI / 180.0))
      );
   }

   @Override
   public void tick() {
      super.tick();
      Vec3 â˜ƒ = this.getDeltaMovement();
      HitResult â˜ƒx = ProjectileUtil.getHitResult(this, this::canHitEntity);
      this.onHit(â˜ƒx);
      double â˜ƒxx = this.getX() + â˜ƒ.x;
      double â˜ƒxxx = this.getY() + â˜ƒ.y;
      double â˜ƒxxxx = this.getZ() + â˜ƒ.z;
      this.updateRotation();
      float â˜ƒxxxxx = 0.99F;
      float â˜ƒxxxxxx = 0.06F;
      if (this.level.getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)) {
         this.discard();
      } else if (this.isInWaterOrBubble()) {
         this.discard();
      } else {
         this.setDeltaMovement(â˜ƒ.scale(0.99F));
         if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.06F, 0.0));
         }

         this.setPos(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      }
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      Entity â˜ƒ = this.getOwner();
      if (â˜ƒ instanceof LivingEntity) {
         â˜ƒ.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity)â˜ƒ).setProjectile(), 1.0F);
      }
   }

   @Override
   protected void onHitBlock(BlockHitResult var1) {
      super.onHitBlock(â˜ƒ);
      if (!this.level.isClientSide) {
         this.discard();
      }
   }

   @Override
   protected void defineSynchedData() {
   }

   @Override
   public void recreateFromPacket(ClientboundAddEntityPacket var1) {
      super.recreateFromPacket(â˜ƒ);
      double â˜ƒ = â˜ƒ.getXa();
      double â˜ƒx = â˜ƒ.getYa();
      double â˜ƒxx = â˜ƒ.getZa();

      for(int â˜ƒxxx = 0; â˜ƒxxx < 7; ++â˜ƒxxx) {
         double â˜ƒxxxx = 0.4 + 0.1 * (double)â˜ƒxxx;
         this.level.addParticle(ParticleTypes.SPIT, this.getX(), this.getY(), this.getZ(), â˜ƒ * â˜ƒxxxx, â˜ƒx, â˜ƒxx * â˜ƒxxxx);
      }

      this.setDeltaMovement(â˜ƒ, â˜ƒx, â˜ƒxx);
   }
}
