package net.minecraft.world.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;

public class LoomMenu extends AbstractContainerMenu {
   private static final int INV_SLOT_START = 4;
   private static final int INV_SLOT_END = 31;
   private static final int USE_ROW_SLOT_START = 31;
   private static final int USE_ROW_SLOT_END = 40;
   private final ContainerLevelAccess access;
   final DataSlot selectedBannerPatternIndex = DataSlot.standalone();
   Runnable slotUpdateListener = () -> {
   };
   final Slot bannerSlot;
   final Slot dyeSlot;
   private final Slot patternSlot;
   private final Slot resultSlot;
   long lastSoundTime;
   private final Container inputContainer = new SimpleContainer(3) {
      @Override
      public void setChanged() {
         super.setChanged();
         LoomMenu.this.slotsChanged(this);
         LoomMenu.this.slotUpdateListener.run();
      }
   };
   private final Container outputContainer = new SimpleContainer(1) {
      @Override
      public void setChanged() {
         super.setChanged();
         LoomMenu.this.slotUpdateListener.run();
      }
   };

   public LoomMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, ContainerLevelAccess.NULL);
   }

   public LoomMenu(int var1, Inventory var2, final ContainerLevelAccess var3) {
      super(MenuType.LOOM, â˜ƒ);
      this.access = â˜ƒ;
      this.bannerSlot = this.addSlot(new Slot(this.inputContainer, 0, 13, 26) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return â˜ƒ.getItem() instanceof BannerItem;
         }
      });
      this.dyeSlot = this.addSlot(new Slot(this.inputContainer, 1, 33, 26) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return â˜ƒ.getItem() instanceof DyeItem;
         }
      });
      this.patternSlot = this.addSlot(new Slot(this.inputContainer, 2, 23, 45) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return â˜ƒ.getItem() instanceof BannerPatternItem;
         }
      });
      this.resultSlot = this.addSlot(new Slot(this.outputContainer, 0, 143, 58) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return false;
         }

         @Override
         public void onTake(Player var1, ItemStack var2) {
            LoomMenu.this.bannerSlot.remove(1);
            LoomMenu.this.dyeSlot.remove(1);
            if (!LoomMenu.this.bannerSlot.hasItem() || !LoomMenu.this.dyeSlot.hasItem()) {
               LoomMenu.this.selectedBannerPatternIndex.set(0);
            }

            â˜ƒ.execute((var1x, var2x) -> {
               long â˜ƒ = var1x.getGameTime();
               if (LoomMenu.this.lastSoundTime != â˜ƒ) {
                  var1x.playSound(null, var2x, SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
                  LoomMenu.this.lastSoundTime = â˜ƒ;
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

      this.addDataSlot(this.selectedBannerPatternIndex);
   }

   public int getSelectedBannerPatternIndex() {
      return this.selectedBannerPatternIndex.get();
   }

   @Override
   public boolean stillValid(Player var1) {
      return stillValid(this.access, â˜ƒ, Blocks.LOOM);
   }

   @Override
   public boolean clickMenuButton(Player var1, int var2) {
      if (â˜ƒ > 0 && â˜ƒ <= BannerPattern.AVAILABLE_PATTERNS) {
         this.selectedBannerPatternIndex.set(â˜ƒ);
         this.setupResultSlot();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void slotsChanged(Container var1) {
      ItemStack â˜ƒ = this.bannerSlot.getItem();
      ItemStack â˜ƒx = this.dyeSlot.getItem();
      ItemStack â˜ƒxx = this.patternSlot.getItem();
      ItemStack â˜ƒxxx = this.resultSlot.getItem();
      if (â˜ƒxxx.isEmpty()
         || !â˜ƒ.isEmpty()
            && !â˜ƒx.isEmpty()
            && this.selectedBannerPatternIndex.get() > 0
            && (this.selectedBannerPatternIndex.get() < BannerPattern.COUNT - BannerPattern.PATTERN_ITEM_COUNT || !â˜ƒxx.isEmpty())) {
         if (!â˜ƒxx.isEmpty() && â˜ƒxx.getItem() instanceof BannerPatternItem) {
            CompoundTag â˜ƒxxxx = â˜ƒ.getOrCreateTagElement("BlockEntityTag");
            boolean â˜ƒxxxxx = â˜ƒxxxx.contains("Patterns", 9) && !â˜ƒ.isEmpty() && â˜ƒxxxx.getList("Patterns", 10).size() >= 6;
            if (â˜ƒxxxxx) {
               this.selectedBannerPatternIndex.set(0);
            } else {
               this.selectedBannerPatternIndex.set(((BannerPatternItem)â˜ƒxx.getItem()).getBannerPattern().ordinal());
            }
         }
      } else {
         this.resultSlot.set(ItemStack.EMPTY);
         this.selectedBannerPatternIndex.set(0);
      }

      this.setupResultSlot();
      this.broadcastChanges();
   }

   public void registerUpdateListener(Runnable var1) {
      this.slotUpdateListener = â˜ƒ;
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         if (â˜ƒ == this.resultSlot.index) {
            if (!this.moveItemStackTo(â˜ƒxx, 4, 40, true)) {
               return ItemStack.EMPTY;
            }

            â˜ƒx.onQuickCraft(â˜ƒxx, â˜ƒ);
         } else if (â˜ƒ != this.dyeSlot.index && â˜ƒ != this.bannerSlot.index && â˜ƒ != this.patternSlot.index) {
            if (â˜ƒxx.getItem() instanceof BannerItem) {
               if (!this.moveItemStackTo(â˜ƒxx, this.bannerSlot.index, this.bannerSlot.index + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (â˜ƒxx.getItem() instanceof DyeItem) {
               if (!this.moveItemStackTo(â˜ƒxx, this.dyeSlot.index, this.dyeSlot.index + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (â˜ƒxx.getItem() instanceof BannerPatternItem) {
               if (!this.moveItemStackTo(â˜ƒxx, this.patternSlot.index, this.patternSlot.index + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (â˜ƒ >= 4 && â˜ƒ < 31) {
               if (!this.moveItemStackTo(â˜ƒxx, 31, 40, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (â˜ƒ >= 31 && â˜ƒ < 40 && !this.moveItemStackTo(â˜ƒxx, 4, 31, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 4, 40, false)) {
            return ItemStack.EMPTY;
         }

         if (â˜ƒxx.isEmpty()) {
            â˜ƒx.set(ItemStack.EMPTY);
         } else {
            â˜ƒx.setChanged();
         }

         if (â˜ƒxx.getCount() == â˜ƒ.getCount()) {
            return ItemStack.EMPTY;
         }

         â˜ƒx.onTake(â˜ƒ, â˜ƒxx);
      }

      return â˜ƒ;
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.access.execute((var2, var3) -> this.clearContainer(â˜ƒ, this.inputContainer));
   }

   private void setupResultSlot() {
      if (this.selectedBannerPatternIndex.get() > 0) {
         ItemStack â˜ƒ = this.bannerSlot.getItem();
         ItemStack â˜ƒx = this.dyeSlot.getItem();
         ItemStack â˜ƒxx = ItemStack.EMPTY;
         if (!â˜ƒ.isEmpty() && !â˜ƒx.isEmpty()) {
            â˜ƒxx = â˜ƒ.copy();
            â˜ƒxx.setCount(1);
            BannerPattern â˜ƒxxxx = BannerPattern.values()[this.selectedBannerPatternIndex.get()];
            DyeColor â˜ƒxxxxx = ((DyeItem)â˜ƒx.getItem()).getDyeColor();
            CompoundTag â˜ƒxxxxxx = â˜ƒxx.getOrCreateTagElement("BlockEntityTag");
            ListTag â˜ƒxxx;
            if (â˜ƒxxxxxx.contains("Patterns", 9)) {
               â˜ƒxxx = â˜ƒxxxxxx.getList("Patterns", 10);
            } else {
               â˜ƒxxx = new ListTag();
               â˜ƒxxxxxx.put("Patterns", â˜ƒxxx);
            }

            CompoundTag â˜ƒxxx = new CompoundTag();
            â˜ƒxxx.putString("Pattern", â˜ƒxxxx.getHashname());
            â˜ƒxxx.putInt("Color", â˜ƒxxxxx.getId());
            â˜ƒxxx.add(â˜ƒxxx);
         }

         if (!ItemStack.matches(â˜ƒxx, this.resultSlot.getItem())) {
            this.resultSlot.set(â˜ƒxx);
         }
      }
   }

   public Slot getBannerSlot() {
      return this.bannerSlot;
   }

   public Slot getDyeSlot() {
      return this.dyeSlot;
   }

   public Slot getPatternSlot() {
      return this.patternSlot;
   }

   public Slot getResultSlot() {
      return this.resultSlot;
   }
}
