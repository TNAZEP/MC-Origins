package net.minecraft.world.entity.projectile;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public abstract class Fireball extends AbstractHurtingProjectile implements ItemSupplier {
   private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(Fireball.class, EntityDataSerializers.ITEM_STACK);

   public Fireball(EntityType<? extends Fireball> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public Fireball(EntityType<? extends Fireball> var1, double var2, double var4, double var6, double var8, double var10, double var12, Level var14) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Fireball(EntityType<? extends Fireball> var1, LivingEntity var2, double var3, double var5, double var7, Level var9) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setItem(ItemStack var1) {
      if (!â˜ƒ.is(Items.FIRE_CHARGE) || â˜ƒ.hasTag()) {
         this.getEntityData().set(DATA_ITEM_STACK, Util.make(â˜ƒ.copy(), var0 -> var0.setCount(1)));
      }
   }

   protected ItemStack getItemRaw() {
      return this.getEntityData().get(DATA_ITEM_STACK);
   }

   @Override
   public ItemStack getItem() {
      ItemStack â˜ƒ = this.getItemRaw();
      return â˜ƒ.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : â˜ƒ;
   }

   @Override
   protected void defineSynchedData() {
      this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      ItemStack â˜ƒ = this.getItemRaw();
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ.put("Item", â˜ƒ.save(new CompoundTag()));
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      ItemStack â˜ƒ = ItemStack.of(â˜ƒ.getCompound("Item"));
      this.setItem(â˜ƒ);
   }
}
