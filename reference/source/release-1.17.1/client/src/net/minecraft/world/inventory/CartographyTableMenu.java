package net.minecraft.world.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class CartographyTableMenu extends AbstractContainerMenu {
   public static final int MAP_SLOT = 0;
   public static final int ADDITIONAL_SLOT = 1;
   public static final int RESULT_SLOT = 2;
   private static final int INV_SLOT_START = 3;
   private static final int INV_SLOT_END = 30;
   private static final int USE_ROW_SLOT_START = 30;
   private static final int USE_ROW_SLOT_END = 39;
   private final ContainerLevelAccess access;
   long lastSoundTime;
   public final Container container = new SimpleContainer(2) {
      @Override
      public void setChanged() {
         CartographyTableMenu.this.slotsChanged(this);
         super.setChanged();
      }
   };
   private final ResultContainer resultContainer = new ResultContainer() {
      @Override
      public void setChanged() {
         CartographyTableMenu.this.slotsChanged(this);
         super.setChanged();
      }
   };

   public CartographyTableMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, ContainerLevelAccess.NULL);
   }

   public CartographyTableMenu(int var1, Inventory var2, final ContainerLevelAccess var3) {
      super(MenuType.CARTOGRAPHY_TABLE, â˜ƒ);
      this.access = â˜ƒ;
      this.addSlot(new Slot(this.container, 0, 15, 15) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return â˜ƒ.is(Items.FILLED_MAP);
         }
      });
      this.addSlot(new Slot(this.container, 1, 15, 52) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return â˜ƒ.is(Items.PAPER) || â˜ƒ.is(Items.MAP) || â˜ƒ.is(Items.GLASS_PANE);
         }
      });
      this.addSlot(new Slot(this.resultContainer, 2, 145, 39) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return false;
         }

         @Override
         public void onTake(Player var1, ItemStack var2) {
            CartographyTableMenu.this.slots.get(0).remove(1);
            CartographyTableMenu.this.slots.get(1).remove(1);
            â˜ƒ.getItem().onCraftedBy(â˜ƒ, â˜ƒ.level, â˜ƒ);
            â˜ƒ.execute((var1x, var2x) -> {
               long â˜ƒ = var1x.getGameTime();
               if (CartographyTableMenu.this.lastSoundTime != â˜ƒ) {
                  var1x.playSound(null, var2x, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                  CartographyTableMenu.this.lastSoundTime = â˜ƒ;
               }
            });
            super.onTake(â˜ƒ, â˜ƒ);
         }
      });

      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒx + â˜ƒ * 9 + 9, 8 + â˜ƒx * 18, 84 + â˜ƒ * 18));
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒ, 8 + â˜ƒ * 18, 142));
      }
   }

   @Override
   public boolean stillValid(Player var1) {
      return stillValid(this.access, â˜ƒ, Blocks.CARTOGRAPHY_TABLE);
   }

   @Override
   public void slotsChanged(Container var1) {
      ItemStack â˜ƒ = this.container.getItem(0);
      ItemStack â˜ƒx = this.container.getItem(1);
      ItemStack â˜ƒxx = this.resultContainer.getItem(2);
      if (â˜ƒxx.isEmpty() || !â˜ƒ.isEmpty() && !â˜ƒx.isEmpty()) {
         if (!â˜ƒ.isEmpty() && !â˜ƒx.isEmpty()) {
            this.setupResultSlot(â˜ƒ, â˜ƒx, â˜ƒxx);
         }
      } else {
         this.resultContainer.removeItemNoUpdate(2);
      }
   }

   private void setupResultSlot(ItemStack var1, ItemStack var2, ItemStack var3) {
      this.access.execute((var4, var5) -> {
         MapItemSavedData â˜ƒ = MapItem.getSavedData(â˜ƒ, var4);
         if (â˜ƒ != null) {
            ItemStack â˜ƒx;
            if (â˜ƒ.is(Items.PAPER) && !â˜ƒ.locked && â˜ƒ.scale < 4) {
               â˜ƒx = â˜ƒ.copy();
               â˜ƒx.setCount(1);
               â˜ƒx.getOrCreateTag().putInt("map_scale_direction", 1);
               this.broadcastChanges();
            } else if (â˜ƒ.is(Items.GLASS_PANE) && !â˜ƒ.locked) {
               â˜ƒx = â˜ƒ.copy();
               â˜ƒx.setCount(1);
               â˜ƒx.getOrCreateTag().putBoolean("map_to_lock", true);
               this.broadcastChanges();
            } else {
               if (!â˜ƒ.is(Items.MAP)) {
                  this.resultContainer.removeItemNoUpdate(2);
                  this.broadcastChanges();
                  return;
               }

               â˜ƒx = â˜ƒ.copy();
               â˜ƒx.setCount(2);
               this.broadcastChanges();
            }

            if (!ItemStack.matches(â˜ƒx, â˜ƒ)) {
               this.resultContainer.setItem(2, â˜ƒx);
               this.broadcastChanges();
            }
         }
      });
   }

   @Override
   public boolean canTakeItemForPickAll(ItemStack var1, Slot var2) {
      return â˜ƒ.container != this.resultContainer && super.canTakeItemForPickAll(â˜ƒ, â˜ƒ);
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         if (â˜ƒ == 2) {
            â˜ƒxx.getItem().onCraftedBy(â˜ƒxx, â˜ƒ.level, â˜ƒ);
            if (!this.moveItemStackTo(â˜ƒxx, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            â˜ƒx.onQuickCraft(â˜ƒxx, â˜ƒ);
         } else if (â˜ƒ != 1 && â˜ƒ != 0) {
            if (â˜ƒxx.is(Items.FILLED_MAP)) {
               if (!this.moveItemStackTo(â˜ƒxx, 0, 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!â˜ƒxx.is(Items.PAPER) && !â˜ƒxx.is(Items.MAP) && !â˜ƒxx.is(Items.GLASS_PANE)) {
               if (â˜ƒ >= 3 && â˜ƒ < 30) {
                  if (!this.moveItemStackTo(â˜ƒxx, 30, 39, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (â˜ƒ >= 30 && â˜ƒ < 39 && !this.moveItemStackTo(â˜ƒxx, 3, 30, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.moveItemStackTo(â˜ƒxx, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (â˜ƒxx.isEmpty()) {
            â˜ƒx.set(ItemStack.EMPTY);
         }

         â˜ƒx.setChanged();
         if (â˜ƒxx.getCount() == â˜ƒ.getCount()) {
            return ItemStack.EMPTY;
         }

         â˜ƒx.onTake(â˜ƒ, â˜ƒxx);
         this.broadcastChanges();
      }

      return â˜ƒ;
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.resultContainer.removeItemNoUpdate(2);
      this.access.execute((var2, var3) -> this.clearContainer(â˜ƒ, this.container));
   }
}
