package net.minecraft.world.item.enchantment;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;

public abstract class Enchantment {
   private final EquipmentSlot[] slots;
   private final Enchantment.Rarity rarity;
   public final EnchantmentCategory category;
   @Nullable
   protected String descriptionId;

   @Nullable
   public static Enchantment byId(int var0) {
      return Registry.ENCHANTMENT.byId(â˜ƒ);
   }

   protected Enchantment(Enchantment.Rarity var1, EnchantmentCategory var2, EquipmentSlot[] var3) {
      this.rarity = â˜ƒ;
      this.category = â˜ƒ;
      this.slots = â˜ƒ;
   }

   public Map<EquipmentSlot, ItemStack> getSlotItems(LivingEntity var1) {
      Map<EquipmentSlot, ItemStack> â˜ƒ = Maps.newEnumMap(EquipmentSlot.class);

      for(EquipmentSlot â˜ƒx : this.slots) {
         ItemStack â˜ƒxx = â˜ƒ.getItemBySlot(â˜ƒx);
         if (!â˜ƒxx.isEmpty()) {
            â˜ƒ.put(â˜ƒx, â˜ƒxx);
         }
      }

      return â˜ƒ;
   }

   public Enchantment.Rarity getRarity() {
      return this.rarity;
   }

   public int getMinLevel() {
      return 1;
   }

   public int getMaxLevel() {
      return 1;
   }

   public int getMinCost(int var1) {
      return 1 + â˜ƒ * 10;
   }

   public int getMaxCost(int var1) {
      return this.getMinCost(â˜ƒ) + 5;
   }

   public int getDamageProtection(int var1, DamageSource var2) {
      return 0;
   }

   public float getDamageBonus(int var1, MobType var2) {
      return 0.0F;
   }

   public final boolean isCompatibleWith(Enchantment var1) {
      return this.checkCompatibility(â˜ƒ) && â˜ƒ.checkCompatibility(this);
   }

   protected boolean checkCompatibility(Enchantment var1) {
      return this != â˜ƒ;
   }

   protected String getOrCreateDescriptionId() {
      if (this.descriptionId == null) {
         this.descriptionId = Util.makeDescriptionId("enchantment", Registry.ENCHANTMENT.getKey(this));
      }

      return this.descriptionId;
   }

   public String getDescriptionId() {
      return this.getOrCreateDescriptionId();
   }

   public Component getFullname(int var1) {
      MutableComponent â˜ƒ = new TranslatableComponent(this.getDescriptionId());
      if (this.isCurse()) {
         â˜ƒ.withStyle(ChatFormatting.RED);
      } else {
         â˜ƒ.withStyle(ChatFormatting.GRAY);
      }

      if (â˜ƒ != 1 || this.getMaxLevel() != 1) {
         â˜ƒ.append(" ").append(new TranslatableComponent("enchantment.level." + â˜ƒ));
      }

      return â˜ƒ;
   }

   public boolean canEnchant(ItemStack var1) {
      return this.category.canEnchant(â˜ƒ.getItem());
   }

   public void doPostAttack(LivingEntity var1, Entity var2, int var3) {
   }

   public void doPostHurt(LivingEntity var1, Entity var2, int var3) {
   }

   public boolean isTreasureOnly() {
      return false;
   }

   public boolean isCurse() {
      return false;
   }

   public boolean isTradeable() {
      return true;
   }

   public boolean isDiscoverable() {
      return true;
   }

   public static enum Rarity {
      COMMON(10),
      UNCOMMON(5),
      RARE(2),
      VERY_RARE(1);

      private final int weight;

      private Rarity(int var3) {
         this.weight = â˜ƒ;
      }

      public int getWeight() {
         return this.weight;
      }
   }
}
