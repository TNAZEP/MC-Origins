package net.minecraft.world.inventory;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class InventoryMenu extends RecipeBookMenu<CraftingContainer> {
   public static final int CONTAINER_ID = 0;
   public static final int RESULT_SLOT = 0;
   public static final int CRAFT_SLOT_START = 1;
   public static final int CRAFT_SLOT_END = 5;
   public static final int ARMOR_SLOT_START = 5;
   public static final int ARMOR_SLOT_END = 9;
   public static final int INV_SLOT_START = 9;
   public static final int INV_SLOT_END = 36;
   public static final int USE_ROW_SLOT_START = 36;
   public static final int USE_ROW_SLOT_END = 45;
   public static final int SHIELD_SLOT = 45;
   public static final ResourceLocation BLOCK_ATLAS = new ResourceLocation("textures/atlas/blocks.png");
   public static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
   public static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
   public static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
   public static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
   public static final ResourceLocation EMPTY_ARMOR_SLOT_SHIELD = new ResourceLocation("item/empty_armor_slot_shield");
   static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{
      EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET
   };
   private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
   private final CraftingContainer craftSlots = new CraftingContainer(this, 2, 2);
   private final ResultContainer resultSlots = new ResultContainer();
   public final boolean active;
   private final Player owner;

   public InventoryMenu(Inventory var1, boolean var2, Player var3) {
      super(null, 0);
      this.active = â˜ƒ;
      this.owner = â˜ƒ;
      this.addSlot(new ResultSlot(â˜ƒ.player, this.craftSlots, this.resultSlots, 0, 154, 28));

      for(int â˜ƒ = 0; â˜ƒ < 2; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 2; ++â˜ƒx) {
            this.addSlot(new Slot(this.craftSlots, â˜ƒx + â˜ƒ * 2, 98 + â˜ƒx * 18, 18 + â˜ƒ * 18));
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
         final EquipmentSlot â˜ƒx = SLOT_IDS[â˜ƒ];
         this.addSlot(new Slot(â˜ƒ, 39 - â˜ƒ, 8, 8 + â˜ƒ * 18) {
            @Override
            public int getMaxStackSize() {
               return 1;
            }

            @Override
            public boolean mayPlace(ItemStack var1) {
               return â˜ƒ == Mob.getEquipmentSlotForItem(â˜ƒ);
            }

            @Override
            public boolean mayPickup(Player var1) {
               ItemStack â˜ƒ = this.getItem();
               return !â˜ƒ.isEmpty() && !â˜ƒ.isCreative() && EnchantmentHelper.hasBindingCurse(â˜ƒ) ? false : super.mayPickup(â˜ƒ);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
               return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.TEXTURE_EMPTY_SLOTS[â˜ƒ.getIndex()]);
            }
         });
      }

      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 9; ++â˜ƒx) {
            this.addSlot(new Slot(â˜ƒ, â˜ƒx + (â˜ƒ + 1) * 9, 8 + â˜ƒx * 18, 84 + â˜ƒ * 18));
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < 9; ++â˜ƒ) {
         this.addSlot(new Slot(â˜ƒ, â˜ƒ, 8 + â˜ƒ * 18, 142));
      }

      this.addSlot(new Slot(â˜ƒ, 40, 77, 62) {
         @Override
         public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
            return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
         }
      });
   }

   public static boolean isHotbarSlot(int var0) {
      return â˜ƒ >= 36 && â˜ƒ < 45 || â˜ƒ == 45;
   }

   @Override
   public void fillCraftSlotsStackedContents(StackedContents var1) {
      this.craftSlots.fillStackedContents(â˜ƒ);
   }

   @Override
   public void clearCraftingContent() {
      this.resultSlots.clearContent();
      this.craftSlots.clearContent();
   }

   @Override
   public boolean recipeMatches(Recipe<? super CraftingContainer> var1) {
      return â˜ƒ.matches(this.craftSlots, this.owner.level);
   }

   @Override
   public void slotsChanged(Container var1) {
      CraftingMenu.slotChangedCraftingGrid(this, this.owner.level, this.owner, this.craftSlots, this.resultSlots);
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.resultSlots.clearContent();
      if (!â˜ƒ.level.isClientSide) {
         this.clearContainer(â˜ƒ, this.craftSlots);
      }
   }

   @Override
   public boolean stillValid(Player var1) {
      return true;
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         EquipmentSlot â˜ƒxxx = Mob.getEquipmentSlotForItem(â˜ƒ);
         if (â˜ƒ == 0) {
            if (!this.moveItemStackTo(â˜ƒxx, 9, 45, true)) {
               return ItemStack.EMPTY;
            }

            â˜ƒx.onQuickCraft(â˜ƒxx, â˜ƒ);
         } else if (â˜ƒ >= 1 && â˜ƒ < 5) {
            if (!this.moveItemStackTo(â˜ƒxx, 9, 45, false)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒ >= 5 && â˜ƒ < 9) {
            if (!this.moveItemStackTo(â˜ƒxx, 9, 45, false)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒxxx.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(8 - â˜ƒxxx.getIndex()).hasItem()) {
            int â˜ƒxx = 8 - â˜ƒxxx.getIndex();
            if (!this.moveItemStackTo(â˜ƒxx, â˜ƒxx, â˜ƒxx + 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒxxx == EquipmentSlot.OFFHAND && !this.slots.get(45).hasItem()) {
            if (!this.moveItemStackTo(â˜ƒxx, 45, 46, false)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒ >= 9 && â˜ƒ < 36) {
            if (!this.moveItemStackTo(â˜ƒxx, 36, 45, false)) {
               return ItemStack.EMPTY;
            }
         } else if (â˜ƒ >= 36 && â˜ƒ < 45) {
            if (!this.moveItemStackTo(â˜ƒxx, 9, 36, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 9, 45, false)) {
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
         if (â˜ƒ == 0) {
            â˜ƒ.drop(â˜ƒxx, false);
         }
      }

      return â˜ƒ;
   }

   @Override
   public boolean canTakeItemForPickAll(ItemStack var1, Slot var2) {
      return â˜ƒ.container != this.resultSlots && super.canTakeItemForPickAll(â˜ƒ, â˜ƒ);
   }

   @Override
   public int getResultSlotIndex() {
      return 0;
   }

   @Override
   public int getGridWidth() {
      return this.craftSlots.getWidth();
   }

   @Override
   public int getGridHeight() {
      return this.craftSlots.getHeight();
   }

   @Override
   public int getSize() {
      return 5;
   }

   public CraftingContainer getCraftSlots() {
      return this.craftSlots;
   }

   @Override
   public RecipeBookType getRecipeBookType() {
      return RecipeBookType.CRAFTING;
   }

   @Override
   public boolean shouldMoveToInventory(int var1) {
      return â˜ƒ != this.getResultSlotIndex();
   }
}
