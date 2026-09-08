package net.minecraft.world.entity.boss.enderdragon;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.dimension.end.EndDragonFight;

public class EndCrystal extends Entity {
   private static final EntityDataAccessor<Optional<BlockPos>> DATA_BEAM_TARGET = SynchedEntityData.defineId(
      EndCrystal.class, EntityDataSerializers.OPTIONAL_BLOCK_POS
   );
   private static final EntityDataAccessor<Boolean> DATA_SHOW_BOTTOM = SynchedEntityData.defineId(EndCrystal.class, EntityDataSerializers.BOOLEAN);
   public int time;

   public EndCrystal(EntityType<? extends EndCrystal> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.blocksBuilding = true;
      this.time = this.random.nextInt(100000);
   }

   public EndCrystal(Level var1, double var2, double var4, double var6) {
      this(EntityType.END_CRYSTAL, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected Entity.MovementEmission getMovementEmission() {
      return Entity.MovementEmission.NONE;
   }

   @Override
   protected void defineSynchedData() {
      this.getEntityData().define(DATA_BEAM_TARGET, Optional.empty());
      this.getEntityData().define(DATA_SHOW_BOTTOM, true);
   }

   @Override
   public void tick() {
      ++this.time;
      if (this.level instanceof ServerLevel) {
         BlockPos â˜ƒ = this.blockPosition();
         if (((ServerLevel)this.level).dragonFight() != null && this.level.getBlockState(â˜ƒ).isAir()) {
            this.level.setBlockAndUpdate(â˜ƒ, BaseFireBlock.getState(this.level, â˜ƒ));
         }
      }
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      if (this.getBeamTarget() != null) {
         â˜ƒ.put("BeamTarget", NbtUtils.writeBlockPos(this.getBeamTarget()));
      }

      â˜ƒ.putBoolean("ShowBottom", this.showsBottom());
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      if (â˜ƒ.contains("BeamTarget", 10)) {
         this.setBeamTarget(NbtUtils.readBlockPos(â˜ƒ.getCompound("BeamTarget")));
      }

      if (â˜ƒ.contains("ShowBottom", 1)) {
         this.setShowBottom(â˜ƒ.getBoolean("ShowBottom"));
      }
   }

   @Override
   public boolean isPickable() {
      return true;
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else if (â˜ƒ.getEntity() instanceof EnderDragon) {
         return false;
      } else {
         if (!this.isRemoved() && !this.level.isClientSide) {
            this.remove(Entity.RemovalReason.KILLED);
            if (!â˜ƒ.isExplosion()) {
               this.level.explode(null, this.getX(), this.getY(), this.getZ(), 6.0F, Explosion.BlockInteraction.DESTROY);
            }

            this.onDestroyedBy(â˜ƒ);
         }

         return true;
      }
   }

   @Override
   public void kill() {
      this.onDestroyedBy(DamageSource.GENERIC);
      super.kill();
   }

   private void onDestroyedBy(DamageSource var1) {
      if (this.level instanceof ServerLevel) {
         EndDragonFight â˜ƒ = ((ServerLevel)this.level).dragonFight();
         if (â˜ƒ != null) {
            â˜ƒ.onCrystalDestroyed(this, â˜ƒ);
         }
      }
   }

   public void setBeamTarget(@Nullable BlockPos var1) {
      this.getEntityData().set(DATA_BEAM_TARGET, Optional.ofNullable(â˜ƒ));
   }

   @Nullable
   public BlockPos getBeamTarget() {
      return (BlockPos)((Optional)this.getEntityData().get(DATA_BEAM_TARGET)).orElse(null);
   }

   public void setShowBottom(boolean var1) {
      this.getEntityData().set(DATA_SHOW_BOTTOM, â˜ƒ);
   }

   public boolean showsBottom() {
      return this.getEntityData().get(DATA_SHOW_BOTTOM);
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      return super.shouldRenderAtSqrDistance(â˜ƒ) || this.getBeamTarget() != null;
   }

   @Override
   public ItemStack getPickResult() {
      return new ItemStack(Items.END_CRYSTAL);
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this);
   }
}
