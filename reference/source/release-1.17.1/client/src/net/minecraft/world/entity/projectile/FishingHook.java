package net.minecraft.world.entity.projectile;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class FishingHook extends Projectile {
   private final Random syncronizedRandom = new Random();
   private boolean biting;
   private int outOfWaterTime;
   private static final int MAX_OUT_OF_WATER_TIME = 10;
   private static final EntityDataAccessor<Integer> DATA_HOOKED_ENTITY = SynchedEntityData.defineId(FishingHook.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_BITING = SynchedEntityData.defineId(FishingHook.class, EntityDataSerializers.BOOLEAN);
   private int life;
   private int nibble;
   private int timeUntilLured;
   private int timeUntilHooked;
   private float fishAngle;
   private boolean openWater = true;
   @Nullable
   private Entity hookedIn;
   private FishingHook.FishHookState currentState = FishingHook.FishHookState.FLYING;
   private final int luck;
   private final int lureSpeed;

   private FishingHook(EntityType<? extends FishingHook> var1, Level var2, int var3, int var4) {
      super(â˜ƒ, â˜ƒ);
      this.noCulling = true;
      this.luck = Math.max(0, â˜ƒ);
      this.lureSpeed = Math.max(0, â˜ƒ);
   }

   public FishingHook(EntityType<? extends FishingHook> var1, Level var2) {
      this(â˜ƒ, â˜ƒ, 0, 0);
   }

   public FishingHook(Player var1, Level var2, int var3, int var4) {
      this(EntityType.FISHING_BOBBER, â˜ƒ, â˜ƒ, â˜ƒ);
      this.setOwner(â˜ƒ);
      float â˜ƒ = â˜ƒ.getXRot();
      float â˜ƒx = â˜ƒ.getYRot();
      float â˜ƒxx = Mth.cos(-â˜ƒx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float â˜ƒxxx = Mth.sin(-â˜ƒx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float â˜ƒxxxx = -Mth.cos(-â˜ƒ * (float) (Math.PI / 180.0));
      float â˜ƒxxxxx = Mth.sin(-â˜ƒ * (float) (Math.PI / 180.0));
      double â˜ƒxxxxxx = â˜ƒ.getX() - (double)â˜ƒxxx * 0.3;
      double â˜ƒxxxxxxx = â˜ƒ.getEyeY();
      double â˜ƒxxxxxxxx = â˜ƒ.getZ() - (double)â˜ƒxx * 0.3;
      this.moveTo(â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒx, â˜ƒ);
      Vec3 â˜ƒxxxxxxxxx = new Vec3((double)(-â˜ƒxxx), (double)Mth.clamp(-(â˜ƒxxxxx / â˜ƒxxxx), -5.0F, 5.0F), (double)(-â˜ƒxx));
      double â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.length();
      â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxx.multiply(
         0.6 / â˜ƒxxxxxxxxxx + 0.5 + this.random.nextGaussian() * 0.0045,
         0.6 / â˜ƒxxxxxxxxxx + 0.5 + this.random.nextGaussian() * 0.0045,
         0.6 / â˜ƒxxxxxxxxxx + 0.5 + this.random.nextGaussian() * 0.0045
      );
      this.setDeltaMovement(â˜ƒxxxxxxxxx);
      this.setYRot((float)(Mth.atan2(â˜ƒxxxxxxxxx.x, â˜ƒxxxxxxxxx.z) * 180.0F / (float)Math.PI));
      this.setXRot((float)(Mth.atan2(â˜ƒxxxxxxxxx.y, â˜ƒxxxxxxxxx.horizontalDistance()) * 180.0F / (float)Math.PI));
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
   }

   @Override
   protected void defineSynchedData() {
      this.getEntityData().define(DATA_HOOKED_ENTITY, 0);
      this.getEntityData().define(DATA_BITING, false);
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (DATA_HOOKED_ENTITY.equals(â˜ƒ)) {
         int â˜ƒ = this.getEntityData().get(DATA_HOOKED_ENTITY);
         this.hookedIn = â˜ƒ > 0 ? this.level.getEntity(â˜ƒ - 1) : null;
      }

      if (DATA_BITING.equals(â˜ƒ)) {
         this.biting = this.getEntityData().get(DATA_BITING);
         if (this.biting) {
            this.setDeltaMovement(this.getDeltaMovement().x, (double)(-0.4F * Mth.nextFloat(this.syncronizedRandom, 0.6F, 1.0F)), this.getDeltaMovement().z);
         }
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      double â˜ƒ = 64.0;
      return â˜ƒ < 4096.0;
   }

   @Override
   public void lerpTo(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
   }

   @Override
   public void tick() {
      this.syncronizedRandom.setSeed(this.getUUID().getLeastSignificantBits() ^ this.level.getGameTime());
      super.tick();
      Player â˜ƒ = this.getPlayerOwner();
      if (â˜ƒ == null) {
         this.discard();
      } else if (this.level.isClientSide || !this.shouldStopFishing(â˜ƒ)) {
         if (this.onGround) {
            ++this.life;
            if (this.life >= 1200) {
               this.discard();
               return;
            }
         } else {
            this.life = 0;
         }

         float â˜ƒ = 0.0F;
         BlockPos â˜ƒx = this.blockPosition();
         FluidState â˜ƒxx = this.level.getFluidState(â˜ƒx);
         if (â˜ƒxx.is(FluidTags.WATER)) {
            â˜ƒ = â˜ƒxx.getHeight(this.level, â˜ƒx);
         }

         boolean â˜ƒ = â˜ƒ > 0.0F;
         if (this.currentState == FishingHook.FishHookState.FLYING) {
            if (this.hookedIn != null) {
               this.setDeltaMovement(Vec3.ZERO);
               this.currentState = FishingHook.FishHookState.HOOKED_IN_ENTITY;
               return;
            }

            if (â˜ƒ) {
               this.setDeltaMovement(this.getDeltaMovement().multiply(0.3, 0.2, 0.3));
               this.currentState = FishingHook.FishHookState.BOBBING;
               return;
            }

            this.checkCollision();
         } else {
            if (this.currentState == FishingHook.FishHookState.HOOKED_IN_ENTITY) {
               if (this.hookedIn != null) {
                  if (!this.hookedIn.isRemoved() && this.hookedIn.level.dimension() == this.level.dimension()) {
                     this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.8), this.hookedIn.getZ());
                  } else {
                     this.setHookedEntity(null);
                     this.currentState = FishingHook.FishHookState.FLYING;
                  }
               }

               return;
            }

            if (this.currentState == FishingHook.FishHookState.BOBBING) {
               Vec3 â˜ƒ = this.getDeltaMovement();
               double â˜ƒx = this.getY() + â˜ƒ.y - (double)â˜ƒx.getY() - (double)â˜ƒ;
               if (Math.abs(â˜ƒx) < 0.01) {
                  â˜ƒx += Math.signum(â˜ƒx) * 0.1;
               }

               this.setDeltaMovement(â˜ƒ.x * 0.9, â˜ƒ.y - â˜ƒx * (double)this.random.nextFloat() * 0.2, â˜ƒ.z * 0.9);
               if (this.nibble <= 0 && this.timeUntilHooked <= 0) {
                  this.openWater = true;
               } else {
                  this.openWater = this.openWater && this.outOfWaterTime < 10 && this.calculateOpenWater(â˜ƒx);
               }

               if (â˜ƒ) {
                  this.outOfWaterTime = Math.max(0, this.outOfWaterTime - 1);
                  if (this.biting) {
                     this.setDeltaMovement(
                        this.getDeltaMovement().add(0.0, -0.1 * (double)this.syncronizedRandom.nextFloat() * (double)this.syncronizedRandom.nextFloat(), 0.0)
                     );
                  }

                  if (!this.level.isClientSide) {
                     this.catchingFish(â˜ƒx);
                  }
               } else {
                  this.outOfWaterTime = Math.min(10, this.outOfWaterTime + 1);
               }
            }
         }

         if (!â˜ƒxx.is(FluidTags.WATER)) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.03, 0.0));
         }

         this.move(MoverType.SELF, this.getDeltaMovement());
         this.updateRotation();
         if (this.currentState == FishingHook.FishHookState.FLYING && (this.onGround || this.horizontalCollision)) {
            this.setDeltaMovement(Vec3.ZERO);
         }

         double â˜ƒ = 0.92;
         this.setDeltaMovement(this.getDeltaMovement().scale(0.92));
         this.reapplyPosition();
      }
   }

   private boolean shouldStopFishing(Player var1) {
      ItemStack â˜ƒ = â˜ƒ.getMainHandItem();
      ItemStack â˜ƒx = â˜ƒ.getOffhandItem();
      boolean â˜ƒxx = â˜ƒ.is(Items.FISHING_ROD);
      boolean â˜ƒxxx = â˜ƒx.is(Items.FISHING_ROD);
      if (!â˜ƒ.isRemoved() && â˜ƒ.isAlive() && (â˜ƒxx || â˜ƒxxx) && !(this.distanceToSqr(â˜ƒ) > 1024.0)) {
         return false;
      } else {
         this.discard();
         return true;
      }
   }

   private void checkCollision() {
      HitResult â˜ƒ = ProjectileUtil.getHitResult(this, this::canHitEntity);
      this.onHit(â˜ƒ);
   }

   @Override
   protected boolean canHitEntity(Entity var1) {
      return super.canHitEntity(â˜ƒ) || â˜ƒ.isAlive() && â˜ƒ instanceof ItemEntity;
   }

   @Override
   protected void onHitEntity(EntityHitResult var1) {
      super.onHitEntity(â˜ƒ);
      if (!this.level.isClientSide) {
         this.setHookedEntity(â˜ƒ.getEntity());
      }
   }

   @Override
   protected void onHitBlock(BlockHitResult var1) {
      super.onHitBlock(â˜ƒ);
      this.setDeltaMovement(this.getDeltaMovement().normalize().scale(â˜ƒ.distanceTo(this)));
   }

   private void setHookedEntity(@Nullable Entity var1) {
      this.hookedIn = â˜ƒ;
      this.getEntityData().set(DATA_HOOKED_ENTITY, â˜ƒ == null ? 0 : â˜ƒ.getId() + 1);
   }

   private void catchingFish(BlockPos var1) {
      ServerLevel â˜ƒ = (ServerLevel)this.level;
      int â˜ƒx = 1;
      BlockPos â˜ƒxx = â˜ƒ.above();
      if (this.random.nextFloat() < 0.25F && this.level.isRainingAt(â˜ƒxx)) {
         ++â˜ƒx;
      }

      if (this.random.nextFloat() < 0.5F && !this.level.canSeeSky(â˜ƒxx)) {
         --â˜ƒx;
      }

      if (this.nibble > 0) {
         --this.nibble;
         if (this.nibble <= 0) {
            this.timeUntilLured = 0;
            this.timeUntilHooked = 0;
            this.getEntityData().set(DATA_BITING, false);
         }
      } else if (this.timeUntilHooked > 0) {
         this.timeUntilHooked -= â˜ƒx;
         if (this.timeUntilHooked > 0) {
            this.fishAngle = (float)((double)this.fishAngle + this.random.nextGaussian() * 4.0);
            float â˜ƒ = this.fishAngle * (float) (Math.PI / 180.0);
            float â˜ƒx = Mth.sin(â˜ƒ);
            float â˜ƒxx = Mth.cos(â˜ƒ);
            double â˜ƒxxx = this.getX() + (double)(â˜ƒx * (float)this.timeUntilHooked * 0.1F);
            double â˜ƒxxxx = (double)((float)Mth.floor(this.getY()) + 1.0F);
            double â˜ƒxxxxx = this.getZ() + (double)(â˜ƒxx * (float)this.timeUntilHooked * 0.1F);
            BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(new BlockPos(â˜ƒxxx, â˜ƒxxxx - 1.0, â˜ƒxxxxx));
            if (â˜ƒxxxxxx.is(Blocks.WATER)) {
               if (this.random.nextFloat() < 0.15F) {
                  â˜ƒ.sendParticles(ParticleTypes.BUBBLE, â˜ƒxxx, â˜ƒxxxx - 0.1F, â˜ƒxxxxx, 1, (double)â˜ƒx, 0.1, (double)â˜ƒxx, 0.0);
               }

               float â˜ƒxxxxxxx = â˜ƒx * 0.04F;
               float â˜ƒxxxxxxxx = â˜ƒxx * 0.04F;
               â˜ƒ.sendParticles(ParticleTypes.FISHING, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 0, (double)â˜ƒxxxxxxxx, 0.01, (double)(-â˜ƒxxxxxxx), 1.0);
               â˜ƒ.sendParticles(ParticleTypes.FISHING, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 0, (double)(-â˜ƒxxxxxxxx), 0.01, (double)â˜ƒxxxxxxx, 1.0);
            }
         } else {
            this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
            double â˜ƒ = this.getY() + 0.5;
            â˜ƒ.sendParticles(
               ParticleTypes.BUBBLE,
               this.getX(),
               â˜ƒ,
               this.getZ(),
               (int)(1.0F + this.getBbWidth() * 20.0F),
               (double)this.getBbWidth(),
               0.0,
               (double)this.getBbWidth(),
               0.2F
            );
            â˜ƒ.sendParticles(
               ParticleTypes.FISHING,
               this.getX(),
               â˜ƒ,
               this.getZ(),
               (int)(1.0F + this.getBbWidth() * 20.0F),
               (double)this.getBbWidth(),
               0.0,
               (double)this.getBbWidth(),
               0.2F
            );
            this.nibble = Mth.nextInt(this.random, 20, 40);
            this.getEntityData().set(DATA_BITING, true);
         }
      } else if (this.timeUntilLured > 0) {
         this.timeUntilLured -= â˜ƒx;
         float â˜ƒ = 0.15F;
         if (this.timeUntilLured < 20) {
            â˜ƒ = (float)((double)â˜ƒ + (double)(20 - this.timeUntilLured) * 0.05);
         } else if (this.timeUntilLured < 40) {
            â˜ƒ = (float)((double)â˜ƒ + (double)(40 - this.timeUntilLured) * 0.02);
         } else if (this.timeUntilLured < 60) {
            â˜ƒ = (float)((double)â˜ƒ + (double)(60 - this.timeUntilLured) * 0.01);
         }

         if (this.random.nextFloat() < â˜ƒ) {
            float â˜ƒ = Mth.nextFloat(this.random, 0.0F, 360.0F) * (float) (Math.PI / 180.0);
            float â˜ƒx = Mth.nextFloat(this.random, 25.0F, 60.0F);
            double â˜ƒxx = this.getX() + (double)(Mth.sin(â˜ƒ) * â˜ƒx * 0.1F);
            double â˜ƒxxx = (double)((float)Mth.floor(this.getY()) + 1.0F);
            double â˜ƒxxxx = this.getZ() + (double)(Mth.cos(â˜ƒ) * â˜ƒx * 0.1F);
            BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(new BlockPos(â˜ƒxx, â˜ƒxxx - 1.0, â˜ƒxxxx));
            if (â˜ƒxxxxx.is(Blocks.WATER)) {
               â˜ƒ.sendParticles(ParticleTypes.SPLASH, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, 2 + this.random.nextInt(2), 0.1F, 0.0, 0.1F, 0.0);
            }
         }

         if (this.timeUntilLured <= 0) {
            this.fishAngle = Mth.nextFloat(this.random, 0.0F, 360.0F);
            this.timeUntilHooked = Mth.nextInt(this.random, 20, 80);
         }
      } else {
         this.timeUntilLured = Mth.nextInt(this.random, 100, 600);
         this.timeUntilLured -= this.lureSpeed * 20 * 5;
      }
   }

   private boolean calculateOpenWater(BlockPos var1) {
      FishingHook.OpenWaterType â˜ƒ = FishingHook.OpenWaterType.INVALID;

      for(int â˜ƒx = -1; â˜ƒx <= 2; ++â˜ƒx) {
         FishingHook.OpenWaterType â˜ƒxx = this.getOpenWaterTypeForArea(â˜ƒ.offset(-2, â˜ƒx, -2), â˜ƒ.offset(2, â˜ƒx, 2));
         switch(â˜ƒxx) {
            case INVALID:
               return false;
            case ABOVE_WATER:
               if (â˜ƒ == FishingHook.OpenWaterType.INVALID) {
                  return false;
               }
               break;
            case INSIDE_WATER:
               if (â˜ƒ == FishingHook.OpenWaterType.ABOVE_WATER) {
                  return false;
               }
         }

         â˜ƒ = â˜ƒxx;
      }

      return true;
   }

   private FishingHook.OpenWaterType getOpenWaterTypeForArea(BlockPos var1, BlockPos var2) {
      return (FishingHook.OpenWaterType)BlockPos.betweenClosedStream(â˜ƒ, â˜ƒ)
         .map(this::getOpenWaterTypeForBlock)
         .reduce((var0, var1x) -> var0 == var1x ? var0 : FishingHook.OpenWaterType.INVALID)
         .orElse(FishingHook.OpenWaterType.INVALID);
   }

   private FishingHook.OpenWaterType getOpenWaterTypeForBlock(BlockPos var1) {
      BlockState â˜ƒ = this.level.getBlockState(â˜ƒ);
      if (!â˜ƒ.isAir() && !â˜ƒ.is(Blocks.LILY_PAD)) {
         FluidState â˜ƒx = â˜ƒ.getFluidState();
         return â˜ƒx.is(FluidTags.WATER) && â˜ƒx.isSource() && â˜ƒ.getCollisionShape(this.level, â˜ƒ).isEmpty()
            ? FishingHook.OpenWaterType.INSIDE_WATER
            : FishingHook.OpenWaterType.INVALID;
      } else {
         return FishingHook.OpenWaterType.ABOVE_WATER;
      }
   }

   public boolean isOpenWaterFishing() {
      return this.openWater;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
   }

   public int retrieve(ItemStack var1) {
      Player â˜ƒ = this.getPlayerOwner();
      if (!this.level.isClientSide && â˜ƒ != null && !this.shouldStopFishing(â˜ƒ)) {
         int â˜ƒx = 0;
         if (this.hookedIn != null) {
            this.pullEntity(this.hookedIn);
            CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)â˜ƒ, â˜ƒ, this, Collections.emptyList());
            this.level.broadcastEntityEvent(this, (byte)31);
            â˜ƒx = this.hookedIn instanceof ItemEntity ? 3 : 5;
         } else if (this.nibble > 0) {
            LootContext.Builder â˜ƒx = new LootContext.Builder((ServerLevel)this.level)
               .withParameter(LootContextParams.ORIGIN, this.position())
               .withParameter(LootContextParams.TOOL, â˜ƒ)
               .withParameter(LootContextParams.THIS_ENTITY, this)
               .withRandom(this.random)
               .withLuck((float)this.luck + â˜ƒ.getLuck());
            LootTable â˜ƒxx = this.level.getServer().getLootTables().get(BuiltInLootTables.FISHING);
            List<ItemStack> â˜ƒxxx = â˜ƒxx.getRandomItems(â˜ƒx.create(LootContextParamSets.FISHING));
            CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)â˜ƒ, â˜ƒ, this, â˜ƒxxx);

            for(ItemStack â˜ƒxxxx : â˜ƒxxx) {
               ItemEntity â˜ƒxxxxx = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), â˜ƒxxxx);
               double â˜ƒxxxxxx = â˜ƒ.getX() - this.getX();
               double â˜ƒxxxxxxx = â˜ƒ.getY() - this.getY();
               double â˜ƒxxxxxxxx = â˜ƒ.getZ() - this.getZ();
               double â˜ƒxxxxxxxxx = 0.1;
               â˜ƒxxxxx.setDeltaMovement(
                  â˜ƒxxxxxx * 0.1,
                  â˜ƒxxxxxxx * 0.1 + Math.sqrt(Math.sqrt(â˜ƒxxxxxx * â˜ƒxxxxxx + â˜ƒxxxxxxx * â˜ƒxxxxxxx + â˜ƒxxxxxxxx * â˜ƒxxxxxxxx)) * 0.08,
                  â˜ƒxxxxxxxx * 0.1
               );
               this.level.addFreshEntity(â˜ƒxxxxx);
               â˜ƒ.level.addFreshEntity(new ExperienceOrb(â˜ƒ.level, â˜ƒ.getX(), â˜ƒ.getY() + 0.5, â˜ƒ.getZ() + 0.5, this.random.nextInt(6) + 1));
               if (â˜ƒxxxx.is(ItemTags.FISHES)) {
                  â˜ƒ.awardStat(Stats.FISH_CAUGHT, 1);
               }
            }

            â˜ƒx = 1;
         }

         if (this.onGround) {
            â˜ƒx = 2;
         }

         this.discard();
         return â˜ƒx;
      } else {
         return 0;
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 31 && this.level.isClientSide && this.hookedIn instanceof Player && ((Player)this.hookedIn).isLocalPlayer()) {
         this.pullEntity(this.hookedIn);
      }

      super.handleEntityEvent(â˜ƒ);
   }

   protected void pullEntity(Entity var1) {
      Entity â˜ƒ = this.getOwner();
      if (â˜ƒ != null) {
         Vec3 â˜ƒx = new Vec3(â˜ƒ.getX() - this.getX(), â˜ƒ.getY() - this.getY(), â˜ƒ.getZ() - this.getZ()).scale(0.1);
         â˜ƒ.setDeltaMovement(â˜ƒ.getDeltaMovement().add(â˜ƒx));
      }
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.NONE;
   }

   @Override
   public void remove(Entity.RemovalReason var1) {
      this.updateOwnerInfo(null);
      super.remove(â˜ƒ);
   }

   @Override
   public void onClientRemoval() {
      this.updateOwnerInfo(null);
   }

   @Override
   public void setOwner(@Nullable Entity var1) {
      super.setOwner(â˜ƒ);
      this.updateOwnerInfo(this);
   }

   private void updateOwnerInfo(@Nullable FishingHook var1) {
      Player â˜ƒ = this.getPlayerOwner();
      if (â˜ƒ != null) {
         â˜ƒ.fishing = â˜ƒ;
      }
   }

   @Nullable
   public Player getPlayerOwner() {
      Entity â˜ƒ = this.getOwner();
      return â˜ƒ instanceof Player ? (Player)â˜ƒ : null;
   }

   @Nullable
   public Entity getHookedIn() {
      return this.hookedIn;
   }

   @Override
   public boolean canChangeDimensions() {
      return false;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      Entity â˜ƒ = this.getOwner();
      return new ClientboundAddEntityPacket(this, â˜ƒ == null ? this.getId() : â˜ƒ.getId());
   }

   @Override
   public void recreateFromPacket(ClientboundAddEntityPacket var1) {
      super.recreateFromPacket(â˜ƒ);
      if (this.getPlayerOwner() == null) {
         int â˜ƒ = â˜ƒ.getData();
         LOGGER.error("Failed to recreate fishing hook on client. {} (id: {}) is not a valid owner.", this.level.getEntity(â˜ƒ), â˜ƒ);
         this.kill();
      }
   }

   static enum FishHookState {
      FLYING,
      HOOKED_IN_ENTITY,
      BOBBING;
   }

   static enum OpenWaterType {
      ABOVE_WATER,
      INSIDE_WATER,
      INVALID;
   }
}
