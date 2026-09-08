package net.minecraft.world.entity.projectile;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EyeOfEnder extends Entity implements ItemSupplier {
   private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(EyeOfEnder.class, EntityDataSerializers.ITEM_STACK);
   private double tx;
   private double ty;
   private double tz;
   private int life;
   private boolean surviveAfterDeath;

   public EyeOfEnder(EntityType<? extends EyeOfEnder> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public EyeOfEnder(Level var1, double var2, double var4, double var6) {
      this(EntityType.EYE_OF_ENDER, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setItem(ItemStack var1) {
      if (!â˜ƒ.is(Items.ENDER_EYE) || â˜ƒ.hasTag()) {
         this.getEntityData().set(DATA_ITEM_STACK, Util.make(â˜ƒ.copy(), var0 -> var0.setCount(1)));
      }
   }

   private ItemStack getItemRaw() {
      return this.getEntityData().get(DATA_ITEM_STACK);
   }

   @Override
   public ItemStack getItem() {
      ItemStack â˜ƒ = this.getItemRaw();
      return â˜ƒ.isEmpty() ? new ItemStack(Items.ENDER_EYE) : â˜ƒ;
   }

   @Override
   protected void defineSynchedData() {
      this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
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

   public void signalTo(BlockPos var1) {
      double â˜ƒ = (double)â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      double â˜ƒxx = (double)â˜ƒ.getZ();
      double â˜ƒxxx = â˜ƒ - this.getX();
      double â˜ƒxxxx = â˜ƒxx - this.getZ();
      double â˜ƒxxxxx = Math.sqrt(â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx);
      if (â˜ƒxxxxx > 12.0) {
         this.tx = this.getX() + â˜ƒxxx / â˜ƒxxxxx * 12.0;
         this.tz = this.getZ() + â˜ƒxxxx / â˜ƒxxxxx * 12.0;
         this.ty = this.getY() + 8.0;
      } else {
         this.tx = â˜ƒ;
         this.ty = (double)â˜ƒx;
         this.tz = â˜ƒxx;
      }

      this.life = 0;
      this.surviveAfterDeath = this.random.nextInt(5) > 0;
   }

   @Override
   public void lerpMotion(double var1, double var3, double var5) {
      this.setDeltaMovement(â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
         double â˜ƒ = Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ);
         this.setYRot((float)(Mth.atan2(â˜ƒ, â˜ƒ) * 180.0F / (float)Math.PI));
         this.setXRot((float)(Mth.atan2(â˜ƒ, â˜ƒ) * 180.0F / (float)Math.PI));
         this.yRotO = this.getYRot();
         this.xRotO = this.getXRot();
      }
   }

   @Override
   public void tick() {
      super.tick();
      Vec3 â˜ƒ = this.getDeltaMovement();
      double â˜ƒx = this.getX() + â˜ƒ.x;
      double â˜ƒxx = this.getY() + â˜ƒ.y;
      double â˜ƒxxx = this.getZ() + â˜ƒ.z;
      double â˜ƒxxxx = â˜ƒ.horizontalDistance();
      this.setXRot(Projectile.lerpRotation(this.xRotO, (float)(Mth.atan2(â˜ƒ.y, â˜ƒxxxx) * 180.0F / (float)Math.PI)));
      this.setYRot(Projectile.lerpRotation(this.yRotO, (float)(Mth.atan2(â˜ƒ.x, â˜ƒ.z) * 180.0F / (float)Math.PI)));
      if (!this.level.isClientSide) {
         double â˜ƒxxxxx = this.tx - â˜ƒx;
         double â˜ƒxxxxxx = this.tz - â˜ƒxxx;
         float â˜ƒxxxxxxx = (float)Math.sqrt(â˜ƒxxxxx * â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx);
         float â˜ƒxxxxxxxx = (float)Mth.atan2(â˜ƒxxxxxx, â˜ƒxxxxx);
         double â˜ƒxxxxxxxxx = Mth.lerp(0.0025, â˜ƒxxxx, (double)â˜ƒxxxxxxx);
         double â˜ƒxxxxxxxxxx = â˜ƒ.y;
         if (â˜ƒxxxxxxx < 1.0F) {
            â˜ƒxxxxxxxxx *= 0.8;
            â˜ƒxxxxxxxxxx *= 0.8;
         }

         int â˜ƒxxxxx = this.getY() < this.ty ? 1 : -1;
         â˜ƒ = new Vec3(
            Math.cos((double)â˜ƒxxxxxxxx) * â˜ƒxxxxxxxxx,
            â˜ƒxxxxxxxxxx + ((double)â˜ƒxxxxx - â˜ƒxxxxxxxxxx) * 0.015F,
            Math.sin((double)â˜ƒxxxxxxxx) * â˜ƒxxxxxxxxx
         );
         this.setDeltaMovement(â˜ƒ);
      }

      float â˜ƒ = 0.25F;
      if (this.isInWater()) {
         for(int â˜ƒx = 0; â˜ƒx < 4; ++â˜ƒx) {
            this.level.addParticle(ParticleTypes.BUBBLE, â˜ƒx - â˜ƒ.x * 0.25, â˜ƒxx - â˜ƒ.y * 0.25, â˜ƒxxx - â˜ƒ.z * 0.25, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
         }
      } else {
         this.level
            .addParticle(
               ParticleTypes.PORTAL,
               â˜ƒx - â˜ƒ.x * 0.25 + this.random.nextDouble() * 0.6 - 0.3,
               â˜ƒxx - â˜ƒ.y * 0.25 - 0.5,
               â˜ƒxxx - â˜ƒ.z * 0.25 + this.random.nextDouble() * 0.6 - 0.3,
               â˜ƒ.x,
               â˜ƒ.y,
               â˜ƒ.z
            );
      }

      if (!this.level.isClientSide) {
         this.setPos(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         ++this.life;
         if (this.life > 80 && !this.level.isClientSide) {
            this.playSound(SoundEvents.ENDER_EYE_DEATH, 1.0F, 1.0F);
            this.discard();
            if (this.surviveAfterDeath) {
               this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), this.getItem()));
            } else {
               this.level.levelEvent(2003, this.blockPosition(), 0);
            }
         }
      } else {
         this.setPosRaw(â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      ItemStack â˜ƒ = this.getItemRaw();
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ.put("Item", â˜ƒ.save(new CompoundTag()));
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      ItemStack â˜ƒ = ItemStack.of(â˜ƒ.getCompound("Item"));
      this.setItem(â˜ƒ);
   }

   @Override
   public float getBrightness() {
      return 1.0F;
   }

   @Override
   public boolean isAttackable() {
      return false;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this);
   }
}
