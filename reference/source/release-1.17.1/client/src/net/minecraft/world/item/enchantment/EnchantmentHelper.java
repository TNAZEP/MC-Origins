package net.minecraft.world.item.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class EnchantmentHelper {
   private static final String TAG_ENCH_ID = "id";
   private static final String TAG_ENCH_LEVEL = "lvl";

   public static CompoundTag storeEnchantment(@Nullable ResourceLocation var0, int var1) {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("id", String.valueOf(â˜ƒ));
      â˜ƒ.putShort("lvl", (short)â˜ƒ);
      return â˜ƒ;
   }

   public static void setEnchantmentLevel(CompoundTag var0, int var1) {
      â˜ƒ.putShort("lvl", (short)â˜ƒ);
   }

   public static int getEnchantmentLevel(CompoundTag var0) {
      return Mth.clamp(â˜ƒ.getInt("lvl"), 0, 255);
   }

   @Nullable
   public static ResourceLocation getEnchantmentId(CompoundTag var0) {
      return ResourceLocation.tryParse(â˜ƒ.getString("id"));
   }

   @Nullable
   public static ResourceLocation getEnchantmentId(Enchantment var0) {
      return Registry.ENCHANTMENT.getKey(â˜ƒ);
   }

   public static int getItemEnchantmentLevel(Enchantment var0, ItemStack var1) {
      if (â˜ƒ.isEmpty()) {
         return 0;
      } else {
         ResourceLocation â˜ƒ = getEnchantmentId(â˜ƒ);
         ListTag â˜ƒx = â˜ƒ.getEnchantmentTags();

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
            CompoundTag â˜ƒxxx = â˜ƒx.getCompound(â˜ƒxx);
            ResourceLocation â˜ƒxxxx = getEnchantmentId(â˜ƒxxx);
            if (â˜ƒxxxx != null && â˜ƒxxxx.equals(â˜ƒ)) {
               return getEnchantmentLevel(â˜ƒxxx);
            }
         }

         return 0;
      }
   }

   public static Map<Enchantment, Integer> getEnchantments(ItemStack var0) {
      ListTag â˜ƒ = â˜ƒ.is(Items.ENCHANTED_BOOK) ? EnchantedBookItem.getEnchantments(â˜ƒ) : â˜ƒ.getEnchantmentTags();
      return deserializeEnchantments(â˜ƒ);
   }

   public static Map<Enchantment, Integer> deserializeEnchantments(ListTag var0) {
      Map<Enchantment, Integer> â˜ƒ = Maps.newLinkedHashMap();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
         Registry.ENCHANTMENT.getOptional(getEnchantmentId(â˜ƒxx)).ifPresent(var2x -> â˜ƒ.put(var2x, getEnchantmentLevel(â˜ƒ)));
      }

      return â˜ƒ;
   }

   public static void setEnchantments(Map<Enchantment, Integer> var0, ItemStack var1) {
      ListTag â˜ƒ = new ListTag();

      for(Entry<Enchantment, Integer> â˜ƒx : â˜ƒ.entrySet()) {
         Enchantment â˜ƒxx = (Enchantment)â˜ƒx.getKey();
         if (â˜ƒxx != null) {
            int â˜ƒxxx = â˜ƒx.getValue();
            â˜ƒ.add(storeEnchantment(getEnchantmentId(â˜ƒxx), â˜ƒxxx));
            if (â˜ƒ.is(Items.ENCHANTED_BOOK)) {
               EnchantedBookItem.addEnchantment(â˜ƒ, new EnchantmentInstance(â˜ƒxx, â˜ƒxxx));
            }
         }
      }

      if (â˜ƒ.isEmpty()) {
         â˜ƒ.removeTagKey("Enchantments");
      } else if (!â˜ƒ.is(Items.ENCHANTED_BOOK)) {
         â˜ƒ.addTagElement("Enchantments", â˜ƒ);
      }
   }

   private static void runIterationOnItem(EnchantmentHelper.EnchantmentVisitor var0, ItemStack var1) {
      if (!â˜ƒ.isEmpty()) {
         ListTag â˜ƒ = â˜ƒ.getEnchantmentTags();

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
            Registry.ENCHANTMENT.getOptional(getEnchantmentId(â˜ƒxx)).ifPresent(var2x -> â˜ƒ.accept(var2x, getEnchantmentLevel(â˜ƒ)));
         }
      }
   }

   private static void runIterationOnInventory(EnchantmentHelper.EnchantmentVisitor var0, Iterable<ItemStack> var1) {
      for(ItemStack â˜ƒ : â˜ƒ) {
         runIterationOnItem(â˜ƒ, â˜ƒ);
      }
   }

   public static int getDamageProtection(Iterable<ItemStack> var0, DamageSource var1) {
      MutableInt â˜ƒ = new MutableInt();
      runIterationOnInventory((var2x, var3) -> â˜ƒ.add(var2x.getDamageProtection(var3, â˜ƒ)), â˜ƒ);
      return â˜ƒ.intValue();
   }

   public static float getDamageBonus(ItemStack var0, MobType var1) {
      MutableFloat â˜ƒ = new MutableFloat();
      runIterationOnItem((var2x, var3) -> â˜ƒ.add(var2x.getDamageBonus(var3, â˜ƒ)), â˜ƒ);
      return â˜ƒ.floatValue();
   }

   public static float getSweepingDamageRatio(LivingEntity var0) {
      int â˜ƒ = getEnchantmentLevel(Enchantments.SWEEPING_EDGE, â˜ƒ);
      return â˜ƒ > 0 ? SweepingEdgeEnchantment.getSweepingDamageRatio(â˜ƒ) : 0.0F;
   }

   public static void doPostHurtEffects(LivingEntity var0, Entity var1) {
      EnchantmentHelper.EnchantmentVisitor â˜ƒ = (var2x, var3) -> var2x.doPostHurt(â˜ƒ, â˜ƒ, var3);
      if (â˜ƒ != null) {
         runIterationOnInventory(â˜ƒ, â˜ƒ.getAllSlots());
      }

      if (â˜ƒ instanceof Player) {
         runIterationOnItem(â˜ƒ, â˜ƒ.getMainHandItem());
      }
   }

   public static void doPostDamageEffects(LivingEntity var0, Entity var1) {
      EnchantmentHelper.EnchantmentVisitor â˜ƒ = (var2x, var3) -> var2x.doPostAttack(â˜ƒ, â˜ƒ, var3);
      if (â˜ƒ != null) {
         runIterationOnInventory(â˜ƒ, â˜ƒ.getAllSlots());
      }

      if (â˜ƒ instanceof Player) {
         runIterationOnItem(â˜ƒ, â˜ƒ.getMainHandItem());
      }
   }

   public static int getEnchantmentLevel(Enchantment var0, LivingEntity var1) {
      Iterable<ItemStack> â˜ƒ = â˜ƒ.getSlotItems(â˜ƒ).values();
      if (â˜ƒ == null) {
         return 0;
      } else {
         int â˜ƒ = 0;

         for(ItemStack â˜ƒx : â˜ƒ) {
            int â˜ƒxx = getItemEnchantmentLevel(â˜ƒ, â˜ƒx);
            if (â˜ƒxx > â˜ƒ) {
               â˜ƒ = â˜ƒxx;
            }
         }

         return â˜ƒ;
      }
   }

   public static int getKnockbackBonus(LivingEntity var0) {
      return getEnchantmentLevel(Enchantments.KNOCKBACK, â˜ƒ);
   }

   public static int getFireAspect(LivingEntity var0) {
      return getEnchantmentLevel(Enchantments.FIRE_ASPECT, â˜ƒ);
   }

   public static int getRespiration(LivingEntity var0) {
      return getEnchantmentLevel(Enchantments.RESPIRATION, â˜ƒ);
   }

   public static int getDepthStrider(LivingEntity var0) {
      return getEnchantmentLevel(Enchantments.DEPTH_STRIDER, â˜ƒ);
   }

   public static int getBlockEfficiency(LivingEntity var0) {
      return getEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, â˜ƒ);
   }

   public static int getFishingLuckBonus(ItemStack var0) {
      return getItemEnchantmentLevel(Enchantments.FISHING_LUCK, â˜ƒ);
   }

   public static int getFishingSpeedBonus(ItemStack var0) {
      return getItemEnchantmentLevel(Enchantments.FISHING_SPEED, â˜ƒ);
   }

   public static int getMobLooting(LivingEntity var0) {
      return getEnchantmentLevel(Enchantments.MOB_LOOTING, â˜ƒ);
   }

   public static boolean hasAquaAffinity(LivingEntity var0) {
      return getEnchantmentLevel(Enchantments.AQUA_AFFINITY, â˜ƒ) > 0;
   }

   public static boolean hasFrostWalker(LivingEntity var0) {
      return getEnchantmentLevel(Enchantments.FROST_WALKER, â˜ƒ) > 0;
   }

   public static boolean hasSoulSpeed(LivingEntity var0) {
      return getEnchantmentLevel(Enchantments.SOUL_SPEED, â˜ƒ) > 0;
   }

   public static boolean hasBindingCurse(ItemStack var0) {
      return getItemEnchantmentLevel(Enchantments.BINDING_CURSE, â˜ƒ) > 0;
   }

   public static boolean hasVanishingCurse(ItemStack var0) {
      return getItemEnchantmentLevel(Enchantments.VANISHING_CURSE, â˜ƒ) > 0;
   }

   public static int getLoyalty(ItemStack var0) {
      return getItemEnchantmentLevel(Enchantments.LOYALTY, â˜ƒ);
   }

   public static int getRiptide(ItemStack var0) {
      return getItemEnchantmentLevel(Enchantments.RIPTIDE, â˜ƒ);
   }

   public static boolean hasChanneling(ItemStack var0) {
      return getItemEnchantmentLevel(Enchantments.CHANNELING, â˜ƒ) > 0;
   }

   @Nullable
   public static Entry<EquipmentSlot, ItemStack> getRandomItemWith(Enchantment var0, LivingEntity var1) {
      return getRandomItemWith(â˜ƒ, â˜ƒ, var0x -> true);
   }

   @Nullable
   public static Entry<EquipmentSlot, ItemStack> getRandomItemWith(Enchantment var0, LivingEntity var1, Predicate<ItemStack> var2) {
      Map<EquipmentSlot, ItemStack> â˜ƒ = â˜ƒ.getSlotItems(â˜ƒ);
      if (â˜ƒ.isEmpty()) {
         return null;
      } else {
         List<Entry<EquipmentSlot, ItemStack>> â˜ƒ = Lists.newArrayList();

         for(Entry<EquipmentSlot, ItemStack> â˜ƒx : â˜ƒ.entrySet()) {
            ItemStack â˜ƒxx = (ItemStack)â˜ƒx.getValue();
            if (!â˜ƒxx.isEmpty() && getItemEnchantmentLevel(â˜ƒ, â˜ƒxx) > 0 && â˜ƒ.test(â˜ƒxx)) {
               â˜ƒ.add(â˜ƒx);
            }
         }

         return â˜ƒ.isEmpty() ? null : (Entry)â˜ƒ.get(â˜ƒ.getRandom().nextInt(â˜ƒ.size()));
      }
   }

   public static int getEnchantmentCost(Random var0, int var1, int var2, ItemStack var3) {
      Item â˜ƒ = â˜ƒ.getItem();
      int â˜ƒx = â˜ƒ.getEnchantmentValue();
      if (â˜ƒx <= 0) {
         return 0;
      } else {
         if (â˜ƒ > 15) {
            â˜ƒ = 15;
         }

         int â˜ƒ = â˜ƒ.nextInt(8) + 1 + (â˜ƒ >> 1) + â˜ƒ.nextInt(â˜ƒ + 1);
         if (â˜ƒ == 0) {
            return Math.max(â˜ƒ / 3, 1);
         } else {
            return â˜ƒ == 1 ? â˜ƒ * 2 / 3 + 1 : Math.max(â˜ƒ, â˜ƒ * 2);
         }
      }
   }

   public static ItemStack enchantItem(Random var0, ItemStack var1, int var2, boolean var3) {
      List<EnchantmentInstance> â˜ƒ = selectEnchantment(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      boolean â˜ƒx = â˜ƒ.is(Items.BOOK);
      if (â˜ƒx) {
         â˜ƒ = new ItemStack(Items.ENCHANTED_BOOK);
      }

      for(EnchantmentInstance â˜ƒ : â˜ƒ) {
         if (â˜ƒx) {
            EnchantedBookItem.addEnchantment(â˜ƒ, â˜ƒ);
         } else {
            â˜ƒ.enchant(â˜ƒ.enchantment, â˜ƒ.level);
         }
      }

      return â˜ƒ;
   }

   public static List<EnchantmentInstance> selectEnchantment(Random var0, ItemStack var1, int var2, boolean var3) {
      List<EnchantmentInstance> â˜ƒ = Lists.<EnchantmentInstance>newArrayList();
      Item â˜ƒx = â˜ƒ.getItem();
      int â˜ƒxx = â˜ƒx.getEnchantmentValue();
      if (â˜ƒxx <= 0) {
         return â˜ƒ;
      } else {
         â˜ƒ += 1 + â˜ƒ.nextInt(â˜ƒxx / 4 + 1) + â˜ƒ.nextInt(â˜ƒxx / 4 + 1);
         float â˜ƒ = (â˜ƒ.nextFloat() + â˜ƒ.nextFloat() - 1.0F) * 0.15F;
         â˜ƒ = Mth.clamp(Math.round((float)â˜ƒ + (float)â˜ƒ * â˜ƒ), 1, Integer.MAX_VALUE);
         List<EnchantmentInstance> â˜ƒx = getAvailableEnchantmentResults(â˜ƒ, â˜ƒ, â˜ƒ);
         if (!â˜ƒx.isEmpty()) {
            WeightedRandom.getRandomItem(â˜ƒ, â˜ƒx).ifPresent(â˜ƒ::add);

            while(â˜ƒ.nextInt(50) <= â˜ƒ) {
               if (!â˜ƒ.isEmpty()) {
                  filterCompatibleEnchantments(â˜ƒx, Util.lastOf(â˜ƒ));
               }

               if (â˜ƒx.isEmpty()) {
                  break;
               }

               WeightedRandom.getRandomItem(â˜ƒ, â˜ƒx).ifPresent(â˜ƒ::add);
               â˜ƒ /= 2;
            }
         }

         return â˜ƒ;
      }
   }

   public static void filterCompatibleEnchantments(List<EnchantmentInstance> var0, EnchantmentInstance var1) {
      Iterator<EnchantmentInstance> â˜ƒ = â˜ƒ.iterator();

      while(â˜ƒ.hasNext()) {
         if (!â˜ƒ.enchantment.isCompatibleWith(((EnchantmentInstance)â˜ƒ.next()).enchantment)) {
            â˜ƒ.remove();
         }
      }
   }

   public static boolean isEnchantmentCompatible(Collection<Enchantment> var0, Enchantment var1) {
      for(Enchantment â˜ƒ : â˜ƒ) {
         if (!â˜ƒ.isCompatibleWith(â˜ƒ)) {
            return false;
         }
      }

      return true;
   }

   public static List<EnchantmentInstance> getAvailableEnchantmentResults(int var0, ItemStack var1, boolean var2) {
      List<EnchantmentInstance> â˜ƒ = Lists.<EnchantmentInstance>newArrayList();
      Item â˜ƒx = â˜ƒ.getItem();
      boolean â˜ƒxx = â˜ƒ.is(Items.BOOK);

      for(Enchantment â˜ƒxxx : Registry.ENCHANTMENT) {
         if ((!â˜ƒxxx.isTreasureOnly() || â˜ƒ) && â˜ƒxxx.isDiscoverable() && (â˜ƒxxx.category.canEnchant(â˜ƒx) || â˜ƒxx)) {
            for(int â˜ƒxxxx = â˜ƒxxx.getMaxLevel(); â˜ƒxxxx > â˜ƒxxx.getMinLevel() - 1; --â˜ƒxxxx) {
               if (â˜ƒ >= â˜ƒxxx.getMinCost(â˜ƒxxxx) && â˜ƒ <= â˜ƒxxx.getMaxCost(â˜ƒxxxx)) {
                  â˜ƒ.add(new EnchantmentInstance(â˜ƒxxx, â˜ƒxxxx));
                  break;
               }
            }
         }
      }

      return â˜ƒ;
   }

   @FunctionalInterface
   interface EnchantmentVisitor {
      void accept(Enchantment var1, int var2);
   }
}
