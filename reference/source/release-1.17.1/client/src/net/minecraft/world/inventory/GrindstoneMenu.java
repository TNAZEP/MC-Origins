package net.minecraft.world.inventory;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class GrindstoneMenu extends AbstractContainerMenu {
   public static final int MAX_NAME_LENGTH = 35;
   public static final int INPUT_SLOT = 0;
   public static final int ADDITIONAL_SLOT = 1;
   public static final int RESULT_SLOT = 2;
   private static final int INV_SLOT_START = 3;
   private static final int INV_SLOT_END = 30;
   private static final int USE_ROW_SLOT_START = 30;
   private static final int USE_ROW_SLOT_END = 39;
   private final Container resultSlots = new ResultContainer();
   final Container repairSlots = new SimpleContainer(2) {
      @Override
      public void setChanged() {
         super.setChanged();
         GrindstoneMenu.this.slotsChanged(this);
      }
   };
   private final ContainerLevelAccess access;

   public GrindstoneMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, ContainerLevelAccess.NULL);
   }

   public GrindstoneMenu(int var1, Inventory var2, final ContainerLevelAccess var3) {
      super(MenuType.GRINDSTONE, â˜ƒ);
      this.access = â˜ƒ;
      this.addSlot(new Slot(this.repairSlots, 0, 49, 19) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return â˜ƒ.isDamageableItem() || â˜ƒ.is(Items.ENCHANTED_BOOK) || â˜ƒ.isEnchanted();
         }
      });
      this.addSlot(new Slot(this.repairSlots, 1, 49, 40) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return â˜ƒ.isDamageableItem() || â˜ƒ.is(Items.ENCHANTED_BOOK) || â˜ƒ.isEnchanted();
         }
      });
      this.addSlot(new Slot(this.resultSlots, 2, 129, 34) {
         @Override
         public boolean mayPlace(ItemStack var1) {
            return false;
         }

         @Override
         public void onTake(Player var1, ItemStack var2) {
            â˜ƒ.execute((var1x, var2x) -> {
               if (var1x instanceof ServerLevel) {
                  ExperienceOrb.award((ServerLevel)var1x, Vec3.atCenterOf(var2x), this.getExperienceAmount(var1x));
               }

               var1x.levelEvent(1042, var2x, 0);
            });
            GrindstoneMenu.this.repairSlots.setItem(0, ItemStack.EMPTY);
            GrindstoneMenu.this.repairSlots.setItem(1, ItemStack.EMPTY);
         }

         private int getExperienceAmount(Level var1) {
            int â˜ƒ = 0;
            â˜ƒ += this.getExperienceFromItem(GrindstoneMenu.this.repairSlots.getItem(0));
            â˜ƒ += this.getExperienceFromItem(GrindstoneMenu.this.repairSlots.getItem(1));
            if (â˜ƒ > 0) {
               int â˜ƒx = (int)Math.ceil((double)â˜ƒ / 2.0);
               return â˜ƒx + â˜ƒ.random.nextInt(â˜ƒx);
            } else {
               return 0;
            }
         }

         private int getExperienceFromItem(ItemStack var1) {
            int â˜ƒ = 0;
            Map<Enchantment, Integer> â˜ƒx = EnchantmentHelper.getEnchantments(â˜ƒ);

            for(Entry<Enchantment, Integer> â˜ƒxx : â˜ƒx.entrySet()) {
               Enchantment â˜ƒxxx = (Enchantment)â˜ƒxx.getKey();
               Integer â˜ƒxxxx = (Integer)â˜ƒxx.getValue();
               if (!â˜ƒxxx.isCurse()) {
                  â˜ƒ += â˜ƒxxx.getMinCost(â˜ƒxxxx);
               }
            }

            return â˜ƒ;
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
   public void slotsChanged(Container var1) {
      super.slotsChanged(â˜ƒ);
      if (â˜ƒ == this.repairSlots) {
         this.createResult();
      }
   }

   private void createResult() {
      ItemStack â˜ƒ = this.repairSlots.getItem(0);
      ItemStack â˜ƒx = this.repairSlots.getItem(1);
      boolean â˜ƒxx = !â˜ƒ.isEmpty() || !â˜ƒx.isEmpty();
      boolean â˜ƒxxx = !â˜ƒ.isEmpty() && !â˜ƒx.isEmpty();
      if (!â˜ƒxx) {
         this.resultSlots.setItem(0, ItemStack.EMPTY);
      } else {
         boolean â˜ƒ = !â˜ƒ.isEmpty() && !â˜ƒ.is(Items.ENCHANTED_BOOK) && !â˜ƒ.isEnchanted()
            || !â˜ƒx.isEmpty() && !â˜ƒx.is(Items.ENCHANTED_BOOK) && !â˜ƒx.isEnchanted();
         if (â˜ƒ.getCount() > 1 || â˜ƒx.getCount() > 1 || !â˜ƒxxx && â˜ƒ) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
            this.broadcastChanges();
            return;
         }

         int â˜ƒxx = 1;
         int â˜ƒ;
         ItemStack â˜ƒx;
         if (â˜ƒxxx) {
            if (!â˜ƒ.is(â˜ƒx.getItem())) {
               this.resultSlots.setItem(0, ItemStack.EMPTY);
               this.broadcastChanges();
               return;
            }

            Item â˜ƒxxx = â˜ƒ.getItem();
            int â˜ƒxxxx = â˜ƒxxx.getMaxDamage() - â˜ƒ.getDamageValue();
            int â˜ƒxxxxx = â˜ƒxxx.getMaxDamage() - â˜ƒx.getDamageValue();
            int â˜ƒxxxxxx = â˜ƒxxxx + â˜ƒxxxxx + â˜ƒxxx.getMaxDamage() * 5 / 100;
            â˜ƒ = Math.max(â˜ƒxxx.getMaxDamage() - â˜ƒxxxxxx, 0);
            â˜ƒx = this.mergeEnchants(â˜ƒ, â˜ƒx);
            if (!â˜ƒx.isDamageableItem()) {
               if (!ItemStack.matches(â˜ƒ, â˜ƒx)) {
                  this.resultSlots.setItem(0, ItemStack.EMPTY);
                  this.broadcastChanges();
                  return;
               }

               â˜ƒxx = 2;
            }
         } else {
            boolean â˜ƒ = !â˜ƒ.isEmpty();
            â˜ƒ = â˜ƒ ? â˜ƒ.getDamageValue() : â˜ƒx.getDamageValue();
            â˜ƒx = â˜ƒ ? â˜ƒ : â˜ƒx;
         }

         this.resultSlots.setItem(0, this.removeNonCurses(â˜ƒx, â˜ƒ, â˜ƒxx));
      }

      this.broadcastChanges();
   }

   private ItemStack mergeEnchants(ItemStack var1, ItemStack var2) {
      ItemStack â˜ƒ = â˜ƒ.copy();
      Map<Enchantment, Integer> â˜ƒx = EnchantmentHelper.getEnchantments(â˜ƒ);

      for(Entry<Enchantment, Integer> â˜ƒxx : â˜ƒx.entrySet()) {
         Enchantment â˜ƒxxx = (Enchantment)â˜ƒxx.getKey();
         if (!â˜ƒxxx.isCurse() || EnchantmentHelper.getItemEnchantmentLevel(â˜ƒxxx, â˜ƒ) == 0) {
            â˜ƒ.enchant(â˜ƒxxx, â˜ƒxx.getValue());
         }
      }

      return â˜ƒ;
   }

   private ItemStack removeNonCurses(ItemStack var1, int var2, int var3) {
      ItemStack â˜ƒ = â˜ƒ.copy();
      â˜ƒ.removeTagKey("Enchantments");
      â˜ƒ.removeTagKey("StoredEnchantments");
      if (â˜ƒ > 0) {
         â˜ƒ.setDamageValue(â˜ƒ);
      } else {
         â˜ƒ.removeTagKey("Damage");
      }

      â˜ƒ.setCount(â˜ƒ);
      Map<Enchantment, Integer> â˜ƒ = (Map)EnchantmentHelper.getEnchantments(â˜ƒ)
         .entrySet()
         .stream()
         .filter(var0 -> ((Enchantment)var0.getKey()).isCurse())
         .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
      EnchantmentHelper.setEnchantments(â˜ƒ, â˜ƒ);
      â˜ƒ.setRepairCost(0);
      if (â˜ƒ.is(Items.ENCHANTED_BOOK) && â˜ƒ.size() == 0) {
         â˜ƒ = new ItemStack(Items.BOOK);
         if (â˜ƒ.hasCustomHoverName()) {
            â˜ƒ.setHoverName(â˜ƒ.getHoverName());
         }
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         â˜ƒ.setRepairCost(AnvilMenu.calculateIncreasedRepairCost(â˜ƒ.getBaseRepairCost()));
      }

      return â˜ƒ;
   }

   @Override
   public void removed(Player var1) {
      super.removed(â˜ƒ);
      this.access.execute((var2, var3) -> this.clearContainer(â˜ƒ, this.repairSlots));
   }

   @Override
   public boolean stillValid(Player var1) {
      return stillValid(this.access, â˜ƒ, Blocks.GRINDSTONE);
   }

   @Override
   public ItemStack quickMoveStack(Player var1, int var2) {
      ItemStack â˜ƒ = ItemStack.EMPTY;
      Slot â˜ƒx = this.slots.get(â˜ƒ);
      if (â˜ƒx != null && â˜ƒx.hasItem()) {
         ItemStack â˜ƒxx = â˜ƒx.getItem();
         â˜ƒ = â˜ƒxx.copy();
         ItemStack â˜ƒxxx = this.repairSlots.getItem(0);
         ItemStack â˜ƒxxxx = this.repairSlots.getItem(1);
         if (â˜ƒ == 2) {
            if (!this.moveItemStackTo(â˜ƒxx, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            â˜ƒx.onQuickCraft(â˜ƒxx, â˜ƒ);
         } else if (â˜ƒ != 0 && â˜ƒ != 1) {
            if (!â˜ƒxxx.isEmpty() && !â˜ƒxxxx.isEmpty()) {
               if (â˜ƒ >= 3 && â˜ƒ < 30) {
                  if (!this.moveItemStackTo(â˜ƒxx, 30, 39, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (â˜ƒ >= 30 && â˜ƒ < 39 && !this.moveItemStackTo(â˜ƒxx, 3, 30, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.moveItemStackTo(â˜ƒxx, 0, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(â˜ƒxx, 3, 39, false)) {
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
}
