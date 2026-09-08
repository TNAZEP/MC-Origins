package net.minecraft.world.entity.decoration;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemFrame extends HangingEntity {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(ItemFrame.class, EntityDataSerializers.ITEM_STACK);
   private static final EntityDataAccessor<Integer> DATA_ROTATION = SynchedEntityData.defineId(ItemFrame.class, EntityDataSerializers.INT);
   public static final int NUM_ROTATIONS = 8;
   private float dropChance = 1.0F;
   private boolean fixed;

   public ItemFrame(EntityType<? extends ItemFrame> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public ItemFrame(Level var1, BlockPos var2, Direction var3) {
      this(EntityType.ITEM_FRAME, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ItemFrame(EntityType<? extends ItemFrame> var1, Level var2, BlockPos var3, Direction var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setDirection(â˜ƒ);
   }

   @Override
   protected float getEyeHeight(Pose var1, EntityDimensions var2) {
      return 0.0F;
   }

   @Override
   protected void defineSynchedData() {
      this.getEntityData().define(DATA_ITEM, ItemStack.EMPTY);
      this.getEntityData().define(DATA_ROTATION, 0);
   }

   @Override
   protected void setDirection(Direction var1) {
      Validate.notNull(â˜ƒ);
      this.direction = â˜ƒ;
      if (â˜ƒ.getAxis().isHorizontal()) {
         this.setXRot(0.0F);
         this.setYRot((float)(this.direction.get2DDataValue() * 90));
      } else {
         this.setXRot((float)(-90 * â˜ƒ.getAxisDirection().getStep()));
         this.setYRot(0.0F);
      }

      this.xRotO = this.getXRot();
      this.yRotO = this.getYRot();
      this.recalculateBoundingBox();
   }

   @Override
   protected void recalculateBoundingBox() {
      if (this.direction != null) {
         double â˜ƒ = 0.46875;
         double â˜ƒx = (double)this.pos.getX() + 0.5 - (double)this.direction.getStepX() * 0.46875;
         double â˜ƒxx = (double)this.pos.getY() + 0.5 - (double)this.direction.getStepY() * 0.46875;
         double â˜ƒxxx = (double)this.pos.getZ() + 0.5 - (double)this.direction.getStepZ() * 0.46875;
         this.setPosRaw(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         double â˜ƒxxxx = (double)this.getWidth();
         double â˜ƒxxxxx = (double)this.getHeight();
         double â˜ƒxxxxxx = (double)this.getWidth();
         Direction.Axis â˜ƒxxxxxxx = this.direction.getAxis();
         switch(â˜ƒxxxxxxx) {
            case X:
               â˜ƒxxxx = 1.0;
               break;
            case Y:
               â˜ƒxxxxx = 1.0;
               break;
            case Z:
               â˜ƒxxxxxx = 1.0;
         }

         â˜ƒxxxx /= 32.0;
         â˜ƒxxxxx /= 32.0;
         â˜ƒxxxxxx /= 32.0;
         this.setBoundingBox(new AABB(â˜ƒx - â˜ƒxxxx, â˜ƒxx - â˜ƒxxxxx, â˜ƒxxx - â˜ƒxxxxxx, â˜ƒx + â˜ƒxxxx, â˜ƒxx + â˜ƒxxxxx, â˜ƒxxx + â˜ƒxxxxxx));
      }
   }

   @Override
   public boolean survives() {
      if (this.fixed) {
         return true;
      } else if (!this.level.noCollision(this)) {
         return false;
      } else {
         BlockState â˜ƒ = this.level.getBlockState(this.pos.relative(this.direction.getOpposite()));
         return â˜ƒ.getMaterial().isSolid() || this.direction.getAxis().isHorizontal() && DiodeBlock.isDiode(â˜ƒ)
            ? this.level.getEntities(this, this.getBoundingBox(), HANGING_ENTITY).isEmpty()
            : false;
      }
   }

   @Override
   public void move(MoverType var1, Vec3 var2) {
      if (!this.fixed) {
         super.move(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void push(double var1, double var3, double var5) {
      if (!this.fixed) {
         super.push(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public float getPickRadius() {
      return 0.0F;
   }

   @Override
   public void kill() {
      this.removeFramedMap(this.getItem());
      super.kill();
   }

   @Override
   public boolean hurt(DamageSource var1, float var2) {
      if (this.fixed) {
         return â˜ƒ != DamageSource.OUT_OF_WORLD && !â˜ƒ.isCreativePlayer() ? false : super.hurt(â˜ƒ, â˜ƒ);
      } else if (this.isInvulnerableTo(â˜ƒ)) {
         return false;
      } else if (!â˜ƒ.isExplosion() && !this.getItem().isEmpty()) {
         if (!this.level.isClientSide) {
            this.dropItem(â˜ƒ.getEntity(), false);
            this.playSound(this.getRemoveItemSound(), 1.0F, 1.0F);
         }

         return true;
      } else {
         return super.hurt(â˜ƒ, â˜ƒ);
      }
   }

   public SoundEvent getRemoveItemSound() {
      return SoundEvents.ITEM_FRAME_REMOVE_ITEM;
   }

   @Override
   public int getWidth() {
      return 12;
   }

   @Override
   public int getHeight() {
      return 12;
   }

   @Override
   public boolean shouldRenderAtSqrDistance(double var1) {
      double â˜ƒ = 16.0;
      â˜ƒ *= 64.0 * getViewScale();
      return â˜ƒ < â˜ƒ * â˜ƒ;
   }

   @Override
   public void dropItem(@Nullable Entity var1) {
      this.playSound(this.getBreakSound(), 1.0F, 1.0F);
      this.dropItem(â˜ƒ, true);
   }

   public SoundEvent getBreakSound() {
      return SoundEvents.ITEM_FRAME_BREAK;
   }

   @Override
   public void playPlacementSound() {
      this.playSound(this.getPlaceSound(), 1.0F, 1.0F);
   }

   public SoundEvent getPlaceSound() {
      return SoundEvents.ITEM_FRAME_PLACE;
   }

   private void dropItem(@Nullable Entity var1, boolean var2) {
      if (!this.fixed) {
         ItemStack â˜ƒ = this.getItem();
         this.setItem(ItemStack.EMPTY);
         if (!this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            if (â˜ƒ == null) {
               this.removeFramedMap(â˜ƒ);
            }
         } else {
            if (â˜ƒ instanceof Player â˜ƒ && â˜ƒ.getAbilities().instabuild) {
               this.removeFramedMap(â˜ƒ);
               return;
            }

            if (â˜ƒ) {
               this.spawnAtLocation(this.getFrameItemStack());
            }

            if (!â˜ƒ.isEmpty()) {
               â˜ƒ = â˜ƒ.copy();
               this.removeFramedMap(â˜ƒ);
               if (this.random.nextFloat() < this.dropChance) {
                  this.spawnAtLocation(â˜ƒ);
               }
            }
         }
      }
   }

   private void removeFramedMap(ItemStack var1) {
      if (â˜ƒ.is(Items.FILLED_MAP)) {
         MapItemSavedData â˜ƒ = MapItem.getSavedData(â˜ƒ, this.level);
         if (â˜ƒ != null) {
            â˜ƒ.removedFromFrame(this.pos, this.getId());
            â˜ƒ.setDirty(true);
         }
      }

      â˜ƒ.setEntityRepresentation(null);
   }

   public ItemStack getItem() {
      return this.getEntityData().get(DATA_ITEM);
   }

   public void setItem(ItemStack var1) {
      this.setItem(â˜ƒ, true);
   }

   public void setItem(ItemStack var1, boolean var2) {
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ = â˜ƒ.copy();
         â˜ƒ.setCount(1);
         â˜ƒ.setEntityRepresentation(this);
      }

      this.getEntityData().set(DATA_ITEM, â˜ƒ);
      if (!â˜ƒ.isEmpty()) {
         this.playSound(this.getAddItemSound(), 1.0F, 1.0F);
      }

      if (â˜ƒ && this.pos != null) {
         this.level.updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
      }
   }

   public SoundEvent getAddItemSound() {
      return SoundEvents.ITEM_FRAME_ADD_ITEM;
   }

   @Override
   public SlotAccess getSlot(int var1) {
      return â˜ƒ == 0 ? new SlotAccess() {
         @Override
         public ItemStack get() {
            return ItemFrame.this.getItem();
         }

         @Override
         public boolean set(ItemStack var1) {
            ItemFrame.this.setItem(â˜ƒ);
            return true;
         }
      } : super.getSlot(â˜ƒ);
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (â˜ƒ.equals(DATA_ITEM)) {
         ItemStack â˜ƒ = this.getItem();
         if (!â˜ƒ.isEmpty() && â˜ƒ.getFrame() != this) {
            â˜ƒ.setEntityRepresentation(this);
         }
      }
   }

   public int getRotation() {
      return this.getEntityData().get(DATA_ROTATION);
   }

   public void setRotation(int var1) {
      this.setRotation(â˜ƒ, true);
   }

   private void setRotation(int var1, boolean var2) {
      this.getEntityData().set(DATA_ROTATION, â˜ƒ % 8);
      if (â˜ƒ && this.pos != null) {
         this.level.updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (!this.getItem().isEmpty()) {
         â˜ƒ.put("Item", this.getItem().save(new CompoundTag()));
         â˜ƒ.putByte("ItemRotation", (byte)this.getRotation());
         â˜ƒ.putFloat("ItemDropChance", this.dropChance);
      }

      â˜ƒ.putByte("Facing", (byte)this.direction.get3DDataValue());
      â˜ƒ.putBoolean("Invisible", this.isInvisible());
      â˜ƒ.putBoolean("Fixed", this.fixed);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      CompoundTag â˜ƒ = â˜ƒ.getCompound("Item");
      if (â˜ƒ != null && !â˜ƒ.isEmpty()) {
         ItemStack â˜ƒx = ItemStack.of(â˜ƒ);
         if (â˜ƒx.isEmpty()) {
            LOGGER.warn("Unable to load item from: {}", â˜ƒ);
         }

         ItemStack â˜ƒx = this.getItem();
         if (!â˜ƒx.isEmpty() && !ItemStack.matches(â˜ƒx, â˜ƒx)) {
            this.removeFramedMap(â˜ƒx);
         }

         this.setItem(â˜ƒx, false);
         this.setRotation(â˜ƒ.getByte("ItemRotation"), false);
         if (â˜ƒ.contains("ItemDropChance", 99)) {
            this.dropChance = â˜ƒ.getFloat("ItemDropChance");
         }
      }

      this.setDirection(Direction.from3DDataValue(â˜ƒ.getByte("Facing")));
      this.setInvisible(â˜ƒ.getBoolean("Invisible"));
      this.fixed = â˜ƒ.getBoolean("Fixed");
   }

   @Override
   public InteractionResult interact(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      boolean â˜ƒx = !this.getItem().isEmpty();
      boolean â˜ƒxx = !â˜ƒ.isEmpty();
      if (this.fixed) {
         return InteractionResult.PASS;
      } else if (!this.level.isClientSide) {
         if (!â˜ƒx) {
            if (â˜ƒxx && !this.isRemoved()) {
               if (â˜ƒ.is(Items.FILLED_MAP)) {
                  MapItemSavedData â˜ƒ = MapItem.getSavedData(â˜ƒ, this.level);
                  if (â˜ƒ != null && â˜ƒ.isTrackedCountOverLimit(256)) {
                     return InteractionResult.FAIL;
                  }
               }

               this.setItem(â˜ƒ);
               if (!â˜ƒ.getAbilities().instabuild) {
                  â˜ƒ.shrink(1);
               }
            }
         } else {
            this.playSound(this.getRotateItemSound(), 1.0F, 1.0F);
            this.setRotation(this.getRotation() + 1);
         }

         return InteractionResult.CONSUME;
      } else {
         return !â˜ƒx && !â˜ƒxx ? InteractionResult.PASS : InteractionResult.SUCCESS;
      }
   }

   public SoundEvent getRotateItemSound() {
      return SoundEvents.ITEM_FRAME_ROTATE_ITEM;
   }

   public int getAnalogOutput() {
      return this.getItem().isEmpty() ? 0 : this.getRotation() % 8 + 1;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this, this.getType(), this.direction.get3DDataValue(), this.getPos());
   }

   @Override
   public void recreateFromPacket(ClientboundAddEntityPacket var1) {
      super.recreateFromPacket(â˜ƒ);
      this.setDirection(Direction.from3DDataValue(â˜ƒ.getData()));
   }

   @Override
   public ItemStack getPickResult() {
      ItemStack â˜ƒ = this.getItem();
      return â˜ƒ.isEmpty() ? this.getFrameItemStack() : â˜ƒ.copy();
   }

   protected ItemStack getFrameItemStack() {
      return new ItemStack(Items.ITEM_FRAME);
   }
}
