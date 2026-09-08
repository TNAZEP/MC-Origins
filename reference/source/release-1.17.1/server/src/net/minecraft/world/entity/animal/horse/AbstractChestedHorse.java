package net.minecraft.world.entity.animal.horse;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public abstract class AbstractChestedHorse extends AbstractHorse {
   private static final EntityDataAccessor<Boolean> DATA_ID_CHEST = SynchedEntityData.defineId(AbstractChestedHorse.class, EntityDataSerializers.BOOLEAN);
   public static final int INV_CHEST_COUNT = 15;

   protected AbstractChestedHorse(EntityType<? extends AbstractChestedHorse> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.canGallop = false;
   }

   @Override
   protected void randomizeAttributes() {
      this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.generateRandomMaxHealth());
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_ID_CHEST, false);
   }

   public static AttributeSupplier.Builder createBaseChestedHorseAttributes() {
      return createBaseHorseAttributes().add(Attributes.MOVEMENT_SPEED, 0.175F).add(Attributes.JUMP_STRENGTH, 0.5);
   }

   public boolean hasChest() {
      return this.entityData.get(DATA_ID_CHEST);
   }

   public void setChest(boolean var1) {
      this.entityData.set(DATA_ID_CHEST, â˜ƒ);
   }

   @Override
   protected int getInventorySize() {
      return this.hasChest() ? 17 : super.getInventorySize();
   }

   @Override
   public double getPassengersRidingOffset() {
      return super.getPassengersRidingOffset() - 0.25;
   }

   @Override
   protected void dropEquipment() {
      super.dropEquipment();
      if (this.hasChest()) {
         if (!this.level.isClientSide) {
            this.spawnAtLocation(Blocks.CHEST);
         }

         this.setChest(false);
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putBoolean("ChestedHorse", this.hasChest());
      if (this.hasChest()) {
         ListTag â˜ƒ = new ListTag();

         for(int â˜ƒx = 2; â˜ƒx < this.inventory.getContainerSize(); ++â˜ƒx) {
            ItemStack â˜ƒxx = this.inventory.getItem(â˜ƒx);
            if (!â˜ƒxx.isEmpty()) {
               CompoundTag â˜ƒxxx = new CompoundTag();
               â˜ƒxxx.putByte("Slot", (byte)â˜ƒx);
               â˜ƒxx.save(â˜ƒxxx);
               â˜ƒ.add(â˜ƒxxx);
            }
         }

         â˜ƒ.put("Items", â˜ƒ);
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.setChest(â˜ƒ.getBoolean("ChestedHorse"));
      this.createInventory();
      if (this.hasChest()) {
         ListTag â˜ƒ = â˜ƒ.getList("Items", 10);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
            int â˜ƒxxx = â˜ƒxx.getByte("Slot") & 255;
            if (â˜ƒxxx >= 2 && â˜ƒxxx < this.inventory.getContainerSize()) {
               this.inventory.setItem(â˜ƒxxx, ItemStack.of(â˜ƒxx));
            }
         }
      }

      this.updateContainerEquipment();
   }

   @Override
   public SlotAccess getSlot(int var1) {
      return â˜ƒ == 499 ? new SlotAccess() {
         @Override
         public ItemStack get() {
            return AbstractChestedHorse.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
         }

         @Override
         public boolean set(ItemStack var1) {
            if (â˜ƒ.isEmpty()) {
               if (AbstractChestedHorse.this.hasChest()) {
                  AbstractChestedHorse.this.setChest(false);
                  AbstractChestedHorse.this.createInventory();
               }

               return true;
            } else if (â˜ƒ.is(Items.CHEST)) {
               if (!AbstractChestedHorse.this.hasChest()) {
                  AbstractChestedHorse.this.setChest(true);
                  AbstractChestedHorse.this.createInventory();
               }

               return true;
            } else {
               return false;
            }
         }
      } : super.getSlot(â˜ƒ);
   }

   @Override
   public InteractionResult mobInteract(Player var1, InteractionHand var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (!this.isBaby()) {
         if (this.isTamed() && â˜ƒ.isSecondaryUseActive()) {
            this.openInventory(â˜ƒ);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }

         if (this.isVehicle()) {
            return super.mobInteract(â˜ƒ, â˜ƒ);
         }
      }

      if (!â˜ƒ.isEmpty()) {
         if (this.isFood(â˜ƒ)) {
            return this.fedFood(â˜ƒ, â˜ƒ);
         }

         if (!this.isTamed()) {
            this.makeMad();
            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }

         if (!this.hasChest() && â˜ƒ.is(Blocks.CHEST.asItem())) {
            this.setChest(true);
            this.playChestEquipsSound();
            if (!â˜ƒ.getAbilities().instabuild) {
               â˜ƒ.shrink(1);
            }

            this.createInventory();
            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }

         if (!this.isBaby() && !this.isSaddled() && â˜ƒ.is(Items.SADDLE)) {
            this.openInventory(â˜ƒ);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
         }
      }

      if (this.isBaby()) {
         return super.mobInteract(â˜ƒ, â˜ƒ);
      } else {
         this.doPlayerRide(â˜ƒ);
         return InteractionResult.sidedSuccess(this.level.isClientSide);
      }
   }

   protected void playChestEquipsSound() {
      this.playSound(SoundEvents.DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
   }

   public int getInventoryColumns() {
      return 5;
   }
}
