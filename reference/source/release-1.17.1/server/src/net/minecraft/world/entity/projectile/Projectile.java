package net.minecraft.world.entity.projectile;

import com.google.common.base.MoreObjects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class Projectile extends Entity {
   @Nullable
   private UUID ownerUUID;
   @Nullable
   private Entity cachedOwner;
   private boolean leftOwner;
   private boolean hasBeenShot;

   Projectile(EntityType<? extends Projectile> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public void setOwner(@Nullable Entity var1) {
      if (â˜ƒ != null) {
         this.ownerUUID = â˜ƒ.getUUID();
         this.cachedOwner = â˜ƒ;
      }
   }

   @Nullable
   public Entity getOwner() {
      if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
         return this.cachedOwner;
      } else if (this.ownerUUID != null && this.level instanceof ServerLevel) {
         this.cachedOwner = ((ServerLevel)this.level).getEntity(this.ownerUUID);
         return this.cachedOwner;
      } else {
         return null;
      }
   }

   public Entity getEffectSource() {
      return MoreObjects.firstNonNull(this.getOwner(), this);
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      if (this.ownerUUID != null) {
         â˜ƒ.putUUID("Owner", this.ownerUUID);
      }

      if (this.leftOwner) {
         â˜ƒ.putBoolean("LeftOwner", true);
      }

      â˜ƒ.putBoolean("HasBeenShot", this.hasBeenShot);
   }

   protected boolean ownedBy(Entity var1) {
      return â˜ƒ.getUUID().equals(this.ownerUUID);
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      if (â˜ƒ.hasUUID("Owner")) {
         this.ownerUUID = â˜ƒ.getUUID("Owner");
      }

      this.leftOwner = â˜ƒ.getBoolean("LeftOwner");
      this.hasBeenShot = â˜ƒ.getBoolean("HasBeenShot");
   }

   @Override
   public void tick() {
      if (!this.hasBeenShot) {
         this.gameEvent(GameEvent.PROJECTILE_SHOOT, this.getOwner(), this.blockPosition());
         this.hasBeenShot = true;
      }

      if (!this.leftOwner) {
         this.leftOwner = this.checkLeftOwner();
      }

      super.tick();
   }

   private boolean checkLeftOwner() {
      Entity â˜ƒ = this.getOwner();
      if (â˜ƒ != null) {
         for(Entity â˜ƒx : this.level
            .getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), var0 -> !var0.isSpectator() && var0.isPickable())) {
            if (â˜ƒx.getRootVehicle() == â˜ƒ.getRootVehicle()) {
               return false;
            }
         }
      }

      return true;
   }

   public void shoot(double var1, double var3, double var5, float var7, float var8) {
      Vec3 â˜ƒ = new Vec3(â˜ƒ, â˜ƒ, â˜ƒ)
         .normalize()
         .add(
            this.random.nextGaussian() * 0.0075F * (double)â˜ƒ,
            this.random.nextGaussian() * 0.0075F * (double)â˜ƒ,
            this.random.nextGaussian() * 0.0075F * (double)â˜ƒ
         )
         .scale((double)â˜ƒ);
      this.setDeltaMovement(â˜ƒ);
      double â˜ƒx = â˜ƒ.horizontalDistance();
      this.setYRot((float)(Mth.atan2(â˜ƒ.x, â˜ƒ.z) * 180.0F / (float)Math.PI));
      this.setXRot((float)(Mth.atan2(â˜ƒ.y, â˜ƒx) * 180.0F / (float)Math.PI));
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
   }

   public void shootFromRotation(Entity var1, float var2, float var3, float var4, float var5, float var6) {
      float â˜ƒ = -Mth.sin(â˜ƒ * (float) (Math.PI / 180.0)) * Mth.cos(â˜ƒ * (float) (Math.PI / 180.0));
      float â˜ƒx = -Mth.sin((â˜ƒ + â˜ƒ) * (float) (Math.PI / 180.0));
      float â˜ƒxx = Mth.cos(â˜ƒ * (float) (Math.PI / 180.0)) * Mth.cos(â˜ƒ * (float) (Math.PI / 180.0));
      this.shoot((double)â˜ƒ, (double)â˜ƒx, (double)â˜ƒxx, â˜ƒ, â˜ƒ);
      Vec3 â˜ƒxxx = â˜ƒ.getDeltaMovement();
      this.setDeltaMovement(this.getDeltaMovement().add(â˜ƒxxx.x, â˜ƒ.isOnGround() ? 0.0 : â˜ƒxxx.y, â˜ƒxxx.z));
   }

   protected void onHit(HitResult var1) {
      HitResult.Type â˜ƒ = â˜ƒ.getType();
      if (â˜ƒ == HitResult.Type.ENTITY) {
         this.onHitEntity((EntityHitResult)â˜ƒ);
      } else if (â˜ƒ == HitResult.Type.BLOCK) {
         this.onHitBlock((BlockHitResult)â˜ƒ);
      }

      if (â˜ƒ != HitResult.Type.MISS) {
         this.gameEvent(GameEvent.PROJECTILE_LAND, this.getOwner());
      }
   }

   protected void onHitEntity(EntityHitResult var1) {
   }

   protected void onHitBlock(BlockHitResult var1) {
      BlockState â˜ƒ = this.level.getBlockState(â˜ƒ.getBlockPos());
      â˜ƒ.onProjectileHit(this.level, â˜ƒ, â˜ƒ, this);
   }

   @Override
   public void lerpMotion(double var1, double var3, double var5) {
      this.setDeltaMovement(â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
         double â˜ƒ = Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ);
         this.setXRot((float)(Mth.atan2(â˜ƒ, â˜ƒ) * 180.0F / (float)Math.PI));
         this.setYRot((float)(Mth.atan2(â˜ƒ, â˜ƒ) * 180.0F / (float)Math.PI));
         this.xRotO = this.getXRot();
         this.yRotO = this.getYRot();
         this.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
      }
   }

   protected boolean canHitEntity(Entity var1) {
      if (!â˜ƒ.isSpectator() && â˜ƒ.isAlive() && â˜ƒ.isPickable()) {
         Entity â˜ƒ = this.getOwner();
         return â˜ƒ == null || this.leftOwner || !â˜ƒ.isPassengerOfSameVehicle(â˜ƒ);
      } else {
         return false;
      }
   }

   protected void updateRotation() {
      Vec3 â˜ƒ = this.getDeltaMovement();
      double â˜ƒx = â˜ƒ.horizontalDistance();
      this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(â˜ƒ.y, â˜ƒx) * 180.0F / (float)Math.PI)));
      this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(â˜ƒ.x, â˜ƒ.z) * 180.0F / (float)Math.PI)));
   }

   protected static float lerpRotation(float var0, float var1) {
      while(â˜ƒ - â˜ƒ < -180.0F) {
         â˜ƒ -= 360.0F;
      }

      while(â˜ƒ - â˜ƒ >= 180.0F) {
         â˜ƒ += 360.0F;
      }

      return Mth.lerp(0.2F, â˜ƒ, â˜ƒ);
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      Entity â˜ƒ = this.getOwner();
      return new ClientboundAddEntityPacket(this, â˜ƒ == null ? 0 : â˜ƒ.getId());
   }

   @Override
   public void recreateFromPacket(ClientboundAddEntityPacket var1) {
      super.recreateFromPacket(â˜ƒ);
      Entity â˜ƒ = this.level.getEntity(â˜ƒ.getData());
      if (â˜ƒ != null) {
         this.setOwner(â˜ƒ);
      }
   }

   @Override
   public boolean mayInteract(Level var1, BlockPos var2) {
      Entity â˜ƒ = this.getOwner();
      if (â˜ƒ instanceof Player) {
         return â˜ƒ.mayInteract(â˜ƒ, â˜ƒ);
      } else {
         return â˜ƒ == null || â˜ƒ.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
      }
   }
}
