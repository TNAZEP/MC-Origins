package net.minecraft.world.inventory;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilMenu extends ItemCombinerMenu {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final boolean DEBUG_COST = false;
   public static final int MAX_NAME_LENGTH = 50;
   private int repairItemCountCost;
   private String itemName;
   private final DataSlot cost = DataSlot.standalone();
   private static final int COST_FAIL = 0;
   private static final int COST_BASE = 1;
   private static final int COST_ADDED_BASE = 1;
   private static final int COST_REPAIR_MATERIAL = 1;
   private static final int COST_REPAIR_SACRIFICE = 2;
   private static final int COST_INCOMPATIBLE_PENALTY = 1;
   private static final int COST_RENAME = 1;

   public AnvilMenu(int var1, Inventory var2) {
      this(â˜ƒ, â˜ƒ, ContainerLevelAccess.NULL);
   }

   public AnvilMenu(int var1, Inventory var2, ContainerLevelAccess var3) {
      super(MenuType.ANVIL, â˜ƒ, â˜ƒ, â˜ƒ);
      this.addDataSlot(this.cost);
   }

   @Override
   protected boolean isValidBlock(BlockState var1) {
      return â˜ƒ.is(BlockTags.ANVIL);
   }

   @Override
   protected boolean mayPickup(Player var1, boolean var2) {
      return (â˜ƒ.getAbilities().instabuild || â˜ƒ.experienceLevel >= this.cost.get()) && this.cost.get() > 0;
   }

   @Override
   protected void onTake(Player var1, ItemStack var2) {
      if (!â˜ƒ.getAbilities().instabuild) {
         â˜ƒ.giveExperienceLevels(-this.cost.get());
      }

      this.inputSlots.setItem(0, ItemStack.EMPTY);
      if (this.repairItemCountCost > 0) {
         ItemStack â˜ƒ = this.inputSlots.getItem(1);
         if (!â˜ƒ.isEmpty() && â˜ƒ.getCount() > this.repairItemCountCost) {
            â˜ƒ.shrink(this.repairItemCountCost);
            this.inputSlots.setItem(1, â˜ƒ);
         } else {
            this.inputSlots.setItem(1, ItemStack.EMPTY);
         }
      } else {
         this.inputSlots.setItem(1, ItemStack.EMPTY);
      }

      this.cost.set(0);
      this.access.execute((var1x, var2x) -> {
         BlockState â˜ƒ = var1x.getBlockState(var2x);
         if (!â˜ƒ.getAbilities().instabuild && â˜ƒ.is(BlockTags.ANVIL) && â˜ƒ.getRandom().nextFloat() < 0.12F) {
            BlockState â˜ƒx = AnvilBlock.damage(â˜ƒ);
            if (â˜ƒx == null) {
               var1x.removeBlock(var2x, false);
               var1x.levelEvent(1029, var2x, 0);
            } else {
               var1x.setBlock(var2x, â˜ƒx, 2);
               var1x.levelEvent(1030, var2x, 0);
            }
         } else {
            var1x.levelEvent(1030, var2x, 0);
         }
      });
   }

   @Override
   public void createResult() {
      ItemStack â˜ƒ = this.inputSlots.getItem(0);
      this.cost.set(1);
      int â˜ƒx = 0;
      int â˜ƒxx = 0;
      int â˜ƒxxx = 0;
      if (â˜ƒ.isEmpty()) {
         this.resultSlots.setItem(0, ItemStack.EMPTY);
         this.cost.set(0);
      } else {
         ItemStack â˜ƒ = â˜ƒ.copy();
         ItemStack â˜ƒx = this.inputSlots.getItem(1);
         Map<Enchantment, Integer> â˜ƒxx = EnchantmentHelper.getEnchantments(â˜ƒ);
         â˜ƒxx += â˜ƒ.getBaseRepairCost() + (â˜ƒx.isEmpty() ? 0 : â˜ƒx.getBaseRepairCost());
         this.repairItemCountCost = 0;
         if (!â˜ƒx.isEmpty()) {
            boolean â˜ƒxxx = â˜ƒx.is(Items.ENCHANTED_BOOK) && !EnchantedBookItem.getEnchantments(â˜ƒx).isEmpty();
            if (â˜ƒ.isDamageableItem() && â˜ƒ.getItem().isValidRepairItem(â˜ƒ, â˜ƒx)) {
               int â˜ƒxxxx = Math.min(â˜ƒ.getDamageValue(), â˜ƒ.getMaxDamage() / 4);
               if (â˜ƒxxxx <= 0) {
                  this.resultSlots.setItem(0, ItemStack.EMPTY);
                  this.cost.set(0);
                  return;
               }

               int â˜ƒ;
               for(â˜ƒ = 0; â˜ƒxxxx > 0 && â˜ƒ < â˜ƒx.getCount(); ++â˜ƒ) {
                  int â˜ƒxxxx = â˜ƒ.getDamageValue() - â˜ƒxxxx;
                  â˜ƒ.setDamageValue(â˜ƒxxxx);
                  ++â˜ƒx;
                  â˜ƒxxxx = Math.min(â˜ƒ.getDamageValue(), â˜ƒ.getMaxDamage() / 4);
               }

               this.repairItemCountCost = â˜ƒ;
            } else {
               if (!â˜ƒxxx && (!â˜ƒ.is(â˜ƒx.getItem()) || !â˜ƒ.isDamageableItem())) {
                  this.resultSlots.setItem(0, ItemStack.EMPTY);
                  this.cost.set(0);
                  return;
               }

               if (â˜ƒ.isDamageableItem() && !â˜ƒxxx) {
                  int â˜ƒxxx = â˜ƒ.getMaxDamage() - â˜ƒ.getDamageValue();
                  int â˜ƒxxxx = â˜ƒx.getMaxDamage() - â˜ƒx.getDamageValue();
                  int â˜ƒxxxxx = â˜ƒxxxx + â˜ƒ.getMaxDamage() * 12 / 100;
                  int â˜ƒxxxxxx = â˜ƒxxx + â˜ƒxxxxx;
                  int â˜ƒxxxxxxx = â˜ƒ.getMaxDamage() - â˜ƒxxxxxx;
                  if (â˜ƒxxxxxxx < 0) {
                     â˜ƒxxxxxxx = 0;
                  }

                  if (â˜ƒxxxxxxx < â˜ƒ.getDamageValue()) {
                     â˜ƒ.setDamageValue(â˜ƒxxxxxxx);
                     â˜ƒx += 2;
                  }
               }

               Map<Enchantment, Integer> â˜ƒxxx = EnchantmentHelper.getEnchantments(â˜ƒx);
               boolean â˜ƒxxxx = false;
               boolean â˜ƒxxxxx = false;

               for(Enchantment â˜ƒxxxxxx : â˜ƒxxx.keySet()) {
                  if (â˜ƒxxxxxx != null) {
                     int â˜ƒxxxxxxx = â˜ƒxx.getOrDefault(â˜ƒxxxxxx, 0);
                     int â˜ƒxxxxxxxx = â˜ƒxxx.get(â˜ƒxxxxxx);
                     â˜ƒxxxxxxxx = â˜ƒxxxxxxx == â˜ƒxxxxxxxx ? â˜ƒxxxxxxxx + 1 : Math.max(â˜ƒxxxxxxxx, â˜ƒxxxxxxx);
                     boolean â˜ƒxxxxxxxxx = â˜ƒxxxxxx.canEnchant(â˜ƒ);
                     if (this.player.getAbilities().instabuild || â˜ƒ.is(Items.ENCHANTED_BOOK)) {
                        â˜ƒxxxxxxxxx = true;
                     }

                     for(Enchantment â˜ƒxxxxxxx : â˜ƒxx.keySet()) {
                        if (â˜ƒxxxxxxx != â˜ƒxxxxxx && !â˜ƒxxxxxx.isCompatibleWith(â˜ƒxxxxxxx)) {
                           â˜ƒxxxxxxxxx = false;
                           ++â˜ƒx;
                        }
                     }

                     if (!â˜ƒxxxxxxxxx) {
                        â˜ƒxxxxx = true;
                     } else {
                        â˜ƒxxxx = true;
                        if (â˜ƒxxxxxxxx > â˜ƒxxxxxx.getMaxLevel()) {
                           â˜ƒxxxxxxxx = â˜ƒxxxxxx.getMaxLevel();
                        }

                        â˜ƒxx.put(â˜ƒxxxxxx, â˜ƒxxxxxxxx);
                        int â˜ƒxxxxxxx = 0;
                        switch(â˜ƒxxxxxx.getRarity()) {
                           case COMMON:
                              â˜ƒxxxxxxx = 1;
                              break;
                           case UNCOMMON:
                              â˜ƒxxxxxxx = 2;
                              break;
                           case RARE:
                              â˜ƒxxxxxxx = 4;
                              break;
                           case VERY_RARE:
                              â˜ƒxxxxxxx = 8;
                        }

                        if (â˜ƒxxx) {
                           â˜ƒxxxxxxx = Math.max(1, â˜ƒxxxxxxx / 2);
                        }

                        â˜ƒx += â˜ƒxxxxxxx * â˜ƒxxxxxxxx;
                        if (â˜ƒ.getCount() > 1) {
                           â˜ƒx = 40;
                        }
                     }
                  }
               }

               if (â˜ƒxxxxx && !â˜ƒxxxx) {
                  this.resultSlots.setItem(0, ItemStack.EMPTY);
                  this.cost.set(0);
                  return;
               }
            }
         }

         if (StringUtils.isBlank(this.itemName)) {
            if (â˜ƒ.hasCustomHoverName()) {
               â˜ƒxxx = 1;
               â˜ƒx += â˜ƒxxx;
               â˜ƒ.resetHoverName();
            }
         } else if (!this.itemName.equals(â˜ƒ.getHoverName().getString())) {
            â˜ƒxxx = 1;
            â˜ƒx += â˜ƒxxx;
            â˜ƒ.setHoverName(new TextComponent(this.itemName));
         }

         this.cost.set(â˜ƒxx + â˜ƒx);
         if (â˜ƒx <= 0) {
            â˜ƒ = ItemStack.EMPTY;
         }

         if (â˜ƒxxx == â˜ƒx && â˜ƒxxx > 0 && this.cost.get() >= 40) {
            this.cost.set(39);
         }

         if (this.cost.get() >= 40 && !this.player.getAbilities().instabuild) {
            â˜ƒ = ItemStack.EMPTY;
         }

         if (!â˜ƒ.isEmpty()) {
            int â˜ƒ = â˜ƒ.getBaseRepairCost();
            if (!â˜ƒx.isEmpty() && â˜ƒ < â˜ƒx.getBaseRepairCost()) {
               â˜ƒ = â˜ƒx.getBaseRepairCost();
            }

            if (â˜ƒxxx != â˜ƒx || â˜ƒxxx == 0) {
               â˜ƒ = calculateIncreasedRepairCost(â˜ƒ);
            }

            â˜ƒ.setRepairCost(â˜ƒ);
            EnchantmentHelper.setEnchantments(â˜ƒxx, â˜ƒ);
         }

         this.resultSlots.setItem(0, â˜ƒ);
         this.broadcastChanges();
      }
   }

   public static int calculateIncreasedRepairCost(int var0) {
      return â˜ƒ * 2 + 1;
   }

   public void setItemName(String var1) {
      this.itemName = â˜ƒ;
      if (this.getSlot(2).hasItem()) {
         ItemStack â˜ƒ = this.getSlot(2).getItem();
         if (StringUtils.isBlank(â˜ƒ)) {
            â˜ƒ.resetHoverName();
         } else {
            â˜ƒ.setHoverName(new TextComponent(this.itemName));
         }
      }

      this.createResult();
   }

   public int getCost() {
      return this.cost.get();
   }
}
