package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;

public class EnchantedBookItem extends Item {
   public static final String TAG_STORED_ENCHANTMENTS = "StoredEnchantments";

   public EnchantedBookItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean isFoil(ItemStack var1) {
      return true;
   }

   @Override
   public boolean isEnchantable(ItemStack var1) {
      return false;
   }

   public static ListTag getEnchantments(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      return â˜ƒ != null ? â˜ƒ.getList("StoredEnchantments", 10) : new ListTag();
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      super.appendHoverText(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      ItemStack.appendEnchantmentNames(â˜ƒ, getEnchantments(â˜ƒ));
   }

   public static void addEnchantment(ItemStack var0, EnchantmentInstance var1) {
      ListTag â˜ƒ = getEnchantments(â˜ƒ);
      boolean â˜ƒx = true;
      ResourceLocation â˜ƒxx = EnchantmentHelper.getEnchantmentId(â˜ƒ.enchantment);

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ.size(); ++â˜ƒxxx) {
         CompoundTag â˜ƒxxxx = â˜ƒ.getCompound(â˜ƒxxx);
         ResourceLocation â˜ƒxxxxx = EnchantmentHelper.getEnchantmentId(â˜ƒxxxx);
         if (â˜ƒxxxxx != null && â˜ƒxxxxx.equals(â˜ƒxx)) {
            if (EnchantmentHelper.getEnchantmentLevel(â˜ƒxxxx) < â˜ƒ.level) {
               EnchantmentHelper.setEnchantmentLevel(â˜ƒxxxx, â˜ƒ.level);
            }

            â˜ƒx = false;
            break;
         }
      }

      if (â˜ƒx) {
         â˜ƒ.add(EnchantmentHelper.storeEnchantment(â˜ƒxx, â˜ƒ.level));
      }

      â˜ƒ.getOrCreateTag().put("StoredEnchantments", â˜ƒ);
   }

   public static ItemStack createForEnchantment(EnchantmentInstance var0) {
      ItemStack â˜ƒ = new ItemStack(Items.ENCHANTED_BOOK);
      addEnchantment(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   @Override
   public void fillItemCategory(CreativeModeTab var1, NonNullList<ItemStack> var2) {
      if (â˜ƒ == CreativeModeTab.TAB_SEARCH) {
         for(Enchantment â˜ƒ : Registry.ENCHANTMENT) {
            if (â˜ƒ.category != null) {
               for(int â˜ƒx = â˜ƒ.getMinLevel(); â˜ƒx <= â˜ƒ.getMaxLevel(); ++â˜ƒx) {
                  â˜ƒ.add(createForEnchantment(new EnchantmentInstance(â˜ƒ, â˜ƒx)));
               }
            }
         }
      } else if (â˜ƒ.getEnchantmentCategories().length != 0) {
         for(Enchantment â˜ƒ : Registry.ENCHANTMENT) {
            if (â˜ƒ.hasEnchantmentCategory(â˜ƒ.category)) {
               â˜ƒ.add(createForEnchantment(new EnchantmentInstance(â˜ƒ, â˜ƒ.getMaxLevel())));
            }
         }
      }
   }
}
