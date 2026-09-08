package net.minecraft.world.entity.vehicle;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MinecartHopper extends AbstractMinecartContainer implements Hopper {
   public static final int MOVE_ITEM_SPEED = 4;
   private boolean enabled = true;
   private int cooldownTime = -1;
   private final BlockPos lastPosition = BlockPos.ZERO;

   public MinecartHopper(EntityType<? extends MinecartHopper> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public MinecartHopper(Level var1, double var2, double var4, double var6) {
      super(EntityType.HOPPER_MINECART, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public AbstractMinecart.Type getMinecartType() {
      return AbstractMinecart.Type.HOPPER;
   }

   @Override
   public BlockState getDefaultDisplayBlockState() {
      return Blocks.HOPPER.defaultBlockState();
   }

   @Override
   public int getDefaultDisplayOffset() {
      return 1;
   }

   @Override
   public int getContainerSize() {
      return 5;
   }

   @Override
   public void activateMinecart(int var1, int var2, int var3, boolean var4) {
      boolean â˜ƒ = !â˜ƒ;
      if (â˜ƒ != this.isEnabled()) {
         this.setEnabled(â˜ƒ);
      }
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean var1) {
      this.enabled = â˜ƒ;
   }

   @Override
   public double getLevelX() {
      return this.getX();
   }

   @Override
   public double getLevelY() {
      return this.getY() + 0.5;
   }

   @Override
   public double getLevelZ() {
      return this.getZ();
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level.isClientSide && this.isAlive() && this.isEnabled()) {
         BlockPos â˜ƒ = this.blockPosition();
         if (â˜ƒ.equals(this.lastPosition)) {
            --this.cooldownTime;
         } else {
            this.setCooldown(0);
         }

         if (!this.isOnCooldown()) {
            this.setCooldown(0);
            if (this.suckInItems()) {
               this.setCooldown(4);
               this.setChanged();
            }
         }
      }
   }

   public boolean suckInItems() {
      if (HopperBlockEntity.suckInItems(this.level, this)) {
         return true;
      } else {
         List<ItemEntity> â˜ƒ = this.level
            .getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(0.25, 0.0, 0.25), EntitySelector.ENTITY_STILL_ALIVE);
         if (!â˜ƒ.isEmpty()) {
            HopperBlockEntity.addItem(this, (ItemEntity)â˜ƒ.get(0));
         }

         return false;
      }
   }

   @Override
   public void destroy(DamageSource var1) {
      super.destroy(â˜ƒ);
      if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
         this.spawnAtLocation(Blocks.HOPPER);
      }
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("TransferCooldown", this.cooldownTime);
      â˜ƒ.putBoolean("Enabled", this.enabled);
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.cooldownTime = â˜ƒ.getInt("TransferCooldown");
      this.enabled = â˜ƒ.contains("Enabled") ? â˜ƒ.getBoolean("Enabled") : true;
   }

   public void setCooldown(int var1) {
      this.cooldownTime = â˜ƒ;
   }

   public boolean isOnCooldown() {
      return this.cooldownTime > 0;
   }

   @Override
   public AbstractContainerMenu createMenu(int var1, Inventory var2) {
      return new HopperMenu(â˜ƒ, â˜ƒ, this);
   }
}
