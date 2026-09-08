package net.minecraft.world.inventory;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;

public class BrewingStandMenu extends AbstractContainerMenu {
   private static final int BOTTLE_SLOT_START = 0;
   private static final int BOTTLE_SLOT_END = 2;
   private static final int INGREDIENT_SLOT = 3;
   private static final int FUEL_SLOT = 4;
   private static final int SLOT_COUNT = 5;
   private static final int DATA_COUNT = 2;
   private static final int INV_SLOT_START = 5;
   private static final int INV_SLOT_END = 32;
   private static final int USE_ROW_SLOT_START = 32;
   private static final int USE_ROW_SLOT_END = 41;
   private final Container brewingStand;
   private final ContainerData brewingStandData;
   private final Slot ingredientSlot;

   public BrewingStandMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, new SimpleContainer(5), new SimpleContainerData(2));
   }

   public BrewingStandMenu(int var1, Inventory var2, Container var3, ContainerData var4) {
      super(MenuType.BREWING_STAND, â˜ƒ);
      checkContainerSize(â˜ƒ, 5);
      checkContainerDataCount(â˜ƒ, 2);
      this.brewingStand = â˜ƒ;
      this.brewingStandData = â˜ƒ;
      this.addSlot(new BrewingStandMenu.PotionSlot(â˜ƒ, 0, 56, 51));
      this.addSlot(new BrewingStandMenu.PotionSlot(â˜ƒ, 1, 79, 58));
      this.addSlot(new BrewingStandMenu.PotionSlot(â˜ƒ, 2, 102, 51));
      this.ingredientSlot = this.addSlot(new BrewingStandMenu.IngredientsSlot(â˜ƒ, 3, 79, 17));
      this.addSlot(new BrewingStandMenu.FuelSlot(â˜ƒ, 4, 17, 17));
      this.addDataSlots(â˜ƒ);

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
      return this.brewingStand.stillValid(â˜ƒ);
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         if ((â˜ƒ < 0 || â˜ƒ > 2) && â˜ƒ != 3 && â˜ƒ != 4) {
            if (BrewingStandMenu.FuelSlot.mayPlaceItem(â˜ƒ)) {
               if (this.moveItemStackTo(â˜ƒxx, 4, 5, false) || this.ingredientSlot.mayPlace(â˜ƒxx) && !this.moveItemStackTo(â˜ƒxx, 3, 4, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (this.ingredientSlot.mayPlace(â˜ƒxx)) {
               if (!this.moveItemStackTo(â˜ƒxx, 3, 4, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (BrewingStandMenu.PotionSlot.mayPlaceItem(â˜ƒ) && â˜ƒ.getCount() == 1) {
               if (!this.moveItemStackTo(â˜ƒxx, 0, 3, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (â˜ƒ >= 5 && â˜ƒ < 32) {
               if (!this.moveItemStackTo(â˜ƒxx, 32, 41, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (â˜ƒ >= 32 && â˜ƒ < 41) {
               if (!this.moveItemStackTo(â˜ƒxx, 5, 32, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.moveItemStackTo(â˜ƒxx, 5, 41, false)) {
               return ItemStack.EMPTY;
            }
         } else {
            if (!this.moveItemStackTo(â˜ƒxx, 5, 41, true)) {
               return ItemStack.EMPTY;
            }

            â˜ƒx.onQuickCraft(â˜ƒxx, â˜ƒ);
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

   public int getFuel() {
      return this.brewingStandData.get(1);
   }

   public int getBrewingTicks() {
      return this.brewingStandData.get(0);
   }

   static class FuelSlot extends Slot {
      public FuelSlot(Container var1, int var2, int var3, int var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean mayPlace(ItemStack var1) {
         return mayPlaceItem(â˜ƒ);
      }

      public static boolean mayPlaceItem(ItemStack var0) {
         return â˜ƒ.is(Items.BLAZE_POWDER);
      }

      @Override
      public int getMaxStackSize() {
         return 64;
      }
   }

   static class IngredientsSlot extends Slot {
      public IngredientsSlot(Container var1, int var2, int var3, int var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean mayPlace(ItemStack var1) {
         return PotionBrewing.isIngredient(â˜ƒ);
      }

      @Override
      public int getMaxStackSize() {
         return 64;
      }
   }

   static class PotionSlot extends Slot {
      public PotionSlot(Container var1, int var2, int var3, int var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean mayPlace(ItemStack var1) {
         return mayPlaceItem(â˜ƒ);
      }

      @Override
      public int getMaxStackSize() {
         return 1;
      }

      @Override
      public void onTake(Player var1, ItemStack var2) {
         Potion â˜ƒ = PotionUtils.getPotion(â˜ƒ);
         if (â˜ƒ instanceof ServerPlayer) {
            CriteriaTriggers.BREWED_POTION.trigger((ServerPlayer)â˜ƒ, â˜ƒ);
         }

         super.onTake(â˜ƒ, â˜ƒ);
      }

      public static boolean mayPlaceItem(ItemStack var0) {
         return â˜ƒ.is(Items.POTION) || â˜ƒ.is(Items.SPLASH_POTION) || â˜ƒ.is(Items.LINGERING_POTION) || â˜ƒ.is(Items.GLASS_BOTTLE);
      }
   }
}
