package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractHurtingProjectile extends Projectile {
   public double xPower;
   public double yPower;
   public double zPower;

   protected AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public AbstractHurtingProjectile(
      EntityType<? extends AbstractHurtingProjectile> var1, double var2, double var4, double var6, double var8, double var10, double var12, Level var14
   ) {
      this(â˜ƒ, â˜ƒ);
      this.moveTo(â˜ƒ, â˜ƒ, â˜ƒ, this.getYRot(), this.getXRot());
      this.reapplyPosition();
      double â˜ƒ = Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ);
      if (â˜ƒ != 0.0) {
         this.xPower = â˜ƒ / â˜ƒ * 0.1;
         this.yPower = â˜ƒ / â˜ƒ * 0.1;
         this.zPower = â˜ƒ / â˜ƒ * 0.1;
      }
   }

   public AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> var1, LivingEntity var2, double var3, double var5, double var7, Level var9) {
      this(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.setOwner(â˜ƒ);
      this.setRot(â˜ƒ.getYRot(), â˜ƒ.getXRot());
   }

   @Override
   protected void defineSynchedData() {
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
      Entity â˜ƒ = this.getOwner();
      if (this.level.isClientSide || (â˜ƒ == null || !â˜ƒ.isRemoved()) && this.level.hasChunkAt(this.blockPosition())) {
         super.tick();
         if (this.shouldBurn()) {
            this.setSecondsOnFire(1);
         }

         HitResult â˜ƒx = ProjectileUtil.getHitResult(this, this::canHitEntity);
         if (â˜ƒx.getType() != HitResult.Type.MISS) {
            this.onHit(â˜ƒx);
         }

         this.checkInsideBlocks();
         Vec3 â˜ƒx = this.getDeltaMovement();
         double â˜ƒxx = this.getX() + â˜ƒx.x;
         double â˜ƒxxx = this.getY() + â˜ƒx.y;
         double â˜ƒxxxx = this.getZ() + â˜ƒx.z;
         ProjectileUtil.rotateTowardsMovement(this, 0.2F);
         float â˜ƒxxxxx = this.getInertia();
         if (this.isInWater()) {
            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 4; ++â˜ƒxxxxxx) {
               float â˜ƒxxxxxxx = 0.25F;
               this.level.addParticle(ParticleTypes.BUBBLE, â˜ƒxx - â˜ƒx.x * 0.25, â˜ƒxxx - â˜ƒx.y * 0.25, â˜ƒxxxx - â˜ƒx.z * 0.25, â˜ƒx.x, â˜ƒx.y, â˜ƒx.z);
            }

            â˜ƒxxxxx = 0.8F;
         }

         this.setDeltaMovement(â˜ƒx.add(this.xPower, this.yPower, this.zPower).scale((double)â˜ƒxxxxx));
         this.level.addParticle(this.getTrailParticle(), â˜ƒxx, â˜ƒxxx + 0.5, â˜ƒxxxx, 0.0, 0.0, 0.0);
         this.setPos(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      } else {
         this.discard();
      }
   }

   @Override
   protected boolean canHitEntity(Entity var1) {
      return super.canHitEntity(â˜ƒ) && !â˜ƒ.noPhysics;
   }

   protected boolean shouldBurn() {
      return true;
   }

   protected ParticleOptions getTrailParticle() {
      return ParticleTypes.SMOKE;
   }

   protected float getInertia() {
      return 0.95F;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.put("power", this.newDoubleList(new double[]{this.xPower, this.yPower, this.zPower}));
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("power", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("power", 6);
         if (â˜ƒ.size() == 3) {
            this.xPower = â˜ƒ.getDouble(0);
            this.yPower = â˜ƒ.getDouble(1);
            this.zPower = â˜ƒ.getDouble(2);
         }
      }
   }

   @Override
   public boolean isPickable() {
      return true;
   }

   @Override
   public float getPickRadius() {
      return 1.0F;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else {
         this.markHurt();
         Entity â˜ƒ = â˜ƒ.getEntity();
         if (â˜ƒ != null) {
            Vec3 â˜ƒx = â˜ƒ.getLookAngle();
            this.setDeltaMovement(â˜ƒx);
            this.xPower = â˜ƒx.x * 0.1;
            this.yPower = â˜ƒx.y * 0.1;
            this.zPower = â˜ƒx.z * 0.1;
            this.setOwner(â˜ƒ);
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public float getBrightness() {
      return 1.0F;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      Entity â˜ƒ = this.getOwner();
      int â˜ƒx = â˜ƒ == null ? 0 : â˜ƒ.getId();
      return new ClientboundAddEntityPacket(
         this.getId(),
         this.getUUID(),
         this.getX(),
         this.getY(),
         this.getZ(),
         this.getXRot(),
         this.getYRot(),
         this.getType(),
         â˜ƒx,
         new Vec3(this.xPower, this.yPower, this.zPower)
      );
   }

   @Override
   public void recreateFromPacket(ClientboundAddEntityPacket var1) {
      super.recreateFromPacket(â˜ƒ);
      double â˜ƒ = â˜ƒ.getXa();
      double â˜ƒx = â˜ƒ.getYa();
      double â˜ƒxx = â˜ƒ.getZa();
      double â˜ƒxxx = Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx);
      if (â˜ƒxxx != 0.0) {
         this.xPower = â˜ƒ / â˜ƒxxx * 0.1;
         this.yPower = â˜ƒx / â˜ƒxxx * 0.1;
         this.zPower = â˜ƒxx / â˜ƒxxx * 0.1;
      }
   }
}
