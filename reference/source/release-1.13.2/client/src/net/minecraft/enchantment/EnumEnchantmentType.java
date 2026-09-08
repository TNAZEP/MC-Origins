package net.minecraft.enchantment;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAbstractSkull;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemTrident;

public enum EnumEnchantmentType {
   ALL {
      @Override
      public boolean func_77557_a(Item var1) {
         for(EnumEnchantmentType ☃ : EnumEnchantmentType.values()) {
            if (☃ != EnumEnchantmentType.ALL && ☃.func_77557_a(☃)) {
               return true;
            }
         }

         return false;
      }
   },
   ARMOR {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃ instanceof ItemArmor;
      }
   },
   ARMOR_FEET {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃ instanceof ItemArmor && ((ItemArmor)☃).func_185083_B_() == EntityEquipmentSlot.FEET;
      }
   },
   ARMOR_LEGS {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃ instanceof ItemArmor && ((ItemArmor)☃).func_185083_B_() == EntityEquipmentSlot.LEGS;
      }
   },
   ARMOR_CHEST {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃ instanceof ItemArmor && ((ItemArmor)☃).func_185083_B_() == EntityEquipmentSlot.CHEST;
      }
   },
   ARMOR_HEAD {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃ instanceof ItemArmor && ((ItemArmor)☃).func_185083_B_() == EntityEquipmentSlot.HEAD;
      }
   },
   WEAPON {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃ instanceof ItemSword;
      }
   },
   DIGGER {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃ instanceof ItemTool;
      }
   },
   FISHING_ROD {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃ instanceof ItemFishingRod;
      }
   },
   TRIDENT {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃ instanceof ItemTrident;
      }
   },
   BREAKABLE {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃.func_77645_m();
      }
   },
   BOW {
      @Override
      public boolean func_77557_a(Item var1) {
         return ☃ instanceof ItemBow;
      }
   },
   WEARABLE {
      @Override
      public boolean func_77557_a(Item var1) {
         Block ☃ = Block.func_149634_a(☃);
         return ☃ instanceof ItemArmor || ☃ instanceof ItemElytra || ☃ instanceof BlockAbstractSkull || ☃ instanceof BlockPumpkin;
      }
   };

   private EnumEnchantmentType() {
   }

   public abstract boolean func_77557_a(Item var1);
}
