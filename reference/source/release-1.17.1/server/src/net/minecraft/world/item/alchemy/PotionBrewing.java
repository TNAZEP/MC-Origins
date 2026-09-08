package net.minecraft.world.item.alchemy;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.crafting.Ingredient;

public class PotionBrewing {
   public static final int BREWING_TIME_SECONDS = 20;
   private static final List<PotionBrewing.Mix<Potion>> POTION_MIXES = Lists.<PotionBrewing.Mix<Potion>>newArrayList();
   private static final List<PotionBrewing.Mix<Item>> CONTAINER_MIXES = Lists.<PotionBrewing.Mix<Item>>newArrayList();
   private static final List<Ingredient> ALLOWED_CONTAINERS = Lists.<Ingredient>newArrayList();
   private static final Predicate<ItemStack> ALLOWED_CONTAINER = var0 -> {
      for(Ingredient â˜ƒ : ALLOWED_CONTAINERS) {
         if (â˜ƒ.test(var0)) {
            return true;
         }
      }

      return false;
   };

   public static boolean isIngredient(ItemStack var0) {
      return isContainerIngredient(â˜ƒ) || isPotionIngredient(â˜ƒ);
   }

   protected static boolean isContainerIngredient(ItemStack var0) {
      int â˜ƒ = 0;

      for(int â˜ƒx = CONTAINER_MIXES.size(); â˜ƒ < â˜ƒx; ++â˜ƒ) {
         if (((PotionBrewing.Mix)CONTAINER_MIXES.get(â˜ƒ)).ingredient.test(â˜ƒ)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean isPotionIngredient(ItemStack var0) {
      int â˜ƒ = 0;

      for(int â˜ƒx = POTION_MIXES.size(); â˜ƒ < â˜ƒx; ++â˜ƒ) {
         if (((PotionBrewing.Mix)POTION_MIXES.get(â˜ƒ)).ingredient.test(â˜ƒ)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isBrewablePotion(Potion var0) {
      int â˜ƒ = 0;

      for(int â˜ƒx = POTION_MIXES.size(); â˜ƒ < â˜ƒx; ++â˜ƒ) {
         if (((PotionBrewing.Mix)POTION_MIXES.get(â˜ƒ)).to == â˜ƒ) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasMix(ItemStack var0, ItemStack var1) {
      if (!ALLOWED_CONTAINER.test(â˜ƒ)) {
         return false;
      } else {
         return hasContainerMix(â˜ƒ, â˜ƒ) || hasPotionMix(â˜ƒ, â˜ƒ);
      }
   }

   protected static boolean hasContainerMix(ItemStack var0, ItemStack var1) {
      Item â˜ƒ = â˜ƒ.getItem();
      int â˜ƒx = 0;

      for(int â˜ƒxx = CONTAINER_MIXES.size(); â˜ƒx < â˜ƒxx; ++â˜ƒx) {
         PotionBrewing.Mix<Item> â˜ƒxxx = (PotionBrewing.Mix)CONTAINER_MIXES.get(â˜ƒx);
         if (â˜ƒxxx.from == â˜ƒ && â˜ƒxxx.ingredient.test(â˜ƒ)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean hasPotionMix(ItemStack var0, ItemStack var1) {
      Potion â˜ƒ = PotionUtils.getPotion(â˜ƒ);
      int â˜ƒx = 0;

      for(int â˜ƒxx = POTION_MIXES.size(); â˜ƒx < â˜ƒxx; ++â˜ƒx) {
         PotionBrewing.Mix<Potion> â˜ƒxxx = (PotionBrewing.Mix)POTION_MIXES.get(â˜ƒx);
         if (â˜ƒxxx.from == â˜ƒ && â˜ƒxxx.ingredient.test(â˜ƒ)) {
            return true;
         }
      }

      return false;
   }

   public static ItemStack mix(ItemStack var0, ItemStack var1) {
      if (!â˜ƒ.isEmpty()) {
         Potion â˜ƒ = PotionUtils.getPotion(â˜ƒ);
         Item â˜ƒx = â˜ƒ.getItem();
         int â˜ƒxx = 0;

         for(int â˜ƒxxx = CONTAINER_MIXES.size(); â˜ƒxx < â˜ƒxxx; ++â˜ƒxx) {
            PotionBrewing.Mix<Item> â˜ƒxxxx = (PotionBrewing.Mix)CONTAINER_MIXES.get(â˜ƒxx);
            if (â˜ƒxxxx.from == â˜ƒx && â˜ƒxxxx.ingredient.test(â˜ƒ)) {
               return PotionUtils.setPotion(new ItemStack(â˜ƒxxxx.to), â˜ƒ);
            }
         }

         â˜ƒxx = 0;

         for(int â˜ƒxxx = POTION_MIXES.size(); â˜ƒxx < â˜ƒxxx; ++â˜ƒxx) {
            PotionBrewing.Mix<Potion> â˜ƒxxxx = (PotionBrewing.Mix)POTION_MIXES.get(â˜ƒxx);
            if (â˜ƒxxxx.from == â˜ƒ && â˜ƒxxxx.ingredient.test(â˜ƒ)) {
               return PotionUtils.setPotion(new ItemStack(â˜ƒx), â˜ƒxxxx.to);
            }
         }
      }

      return â˜ƒ;
   }

   public static void bootStrap() {
      addContainer(Items.POTION);
      addContainer(Items.SPLASH_POTION);
      addContainer(Items.LINGERING_POTION);
      addContainerRecipe(Items.POTION, Items.GUNPOWDER, Items.SPLASH_POTION);
      addContainerRecipe(Items.SPLASH_POTION, Items.DRAGON_BREATH, Items.LINGERING_POTION);
      addMix(Potions.WATER, Items.GLISTERING_MELON_SLICE, Potions.MUNDANE);
      addMix(Potions.WATER, Items.GHAST_TEAR, Potions.MUNDANE);
      addMix(Potions.WATER, Items.RABBIT_FOOT, Potions.MUNDANE);
      addMix(Potions.WATER, Items.BLAZE_POWDER, Potions.MUNDANE);
      addMix(Potions.WATER, Items.SPIDER_EYE, Potions.MUNDANE);
      addMix(Potions.WATER, Items.SUGAR, Potions.MUNDANE);
      addMix(Potions.WATER, Items.MAGMA_CREAM, Potions.MUNDANE);
      addMix(Potions.WATER, Items.GLOWSTONE_DUST, Potions.THICK);
      addMix(Potions.WATER, Items.REDSTONE, Potions.MUNDANE);
      addMix(Potions.WATER, Items.NETHER_WART, Potions.AWKWARD);
      addMix(Potions.AWKWARD, Items.GOLDEN_CARROT, Potions.NIGHT_VISION);
      addMix(Potions.NIGHT_VISION, Items.REDSTONE, Potions.LONG_NIGHT_VISION);
      addMix(Potions.NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, Potions.INVISIBILITY);
      addMix(Potions.LONG_NIGHT_VISION, Items.FERMENTED_SPIDER_EYE, Potions.LONG_INVISIBILITY);
      addMix(Potions.INVISIBILITY, Items.REDSTONE, Potions.LONG_INVISIBILITY);
      addMix(Potions.AWKWARD, Items.MAGMA_CREAM, Potions.FIRE_RESISTANCE);
      addMix(Potions.FIRE_RESISTANCE, Items.REDSTONE, Potions.LONG_FIRE_RESISTANCE);
      addMix(Potions.AWKWARD, Items.RABBIT_FOOT, Potions.LEAPING);
      addMix(Potions.LEAPING, Items.REDSTONE, Potions.LONG_LEAPING);
      addMix(Potions.LEAPING, Items.GLOWSTONE_DUST, Potions.STRONG_LEAPING);
      addMix(Potions.LEAPING, Items.FERMENTED_SPIDER_EYE, Potions.SLOWNESS);
      addMix(Potions.LONG_LEAPING, Items.FERMENTED_SPIDER_EYE, Potions.LONG_SLOWNESS);
      addMix(Potions.SLOWNESS, Items.REDSTONE, Potions.LONG_SLOWNESS);
      addMix(Potions.SLOWNESS, Items.GLOWSTONE_DUST, Potions.STRONG_SLOWNESS);
      addMix(Potions.AWKWARD, Items.TURTLE_HELMET, Potions.TURTLE_MASTER);
      addMix(Potions.TURTLE_MASTER, Items.REDSTONE, Potions.LONG_TURTLE_MASTER);
      addMix(Potions.TURTLE_MASTER, Items.GLOWSTONE_DUST, Potions.STRONG_TURTLE_MASTER);
      addMix(Potions.SWIFTNESS, Items.FERMENTED_SPIDER_EYE, Potions.SLOWNESS);
      addMix(Potions.LONG_SWIFTNESS, Items.FERMENTED_SPIDER_EYE, Potions.LONG_SLOWNESS);
      addMix(Potions.AWKWARD, Items.SUGAR, Potions.SWIFTNESS);
      addMix(Potions.SWIFTNESS, Items.REDSTONE, Potions.LONG_SWIFTNESS);
      addMix(Potions.SWIFTNESS, Items.GLOWSTONE_DUST, Potions.STRONG_SWIFTNESS);
      addMix(Potions.AWKWARD, Items.PUFFERFISH, Potions.WATER_BREATHING);
      addMix(Potions.WATER_BREATHING, Items.REDSTONE, Potions.LONG_WATER_BREATHING);
      addMix(Potions.AWKWARD, Items.GLISTERING_MELON_SLICE, Potions.HEALING);
      addMix(Potions.HEALING, Items.GLOWSTONE_DUST, Potions.STRONG_HEALING);
      addMix(Potions.HEALING, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
      addMix(Potions.STRONG_HEALING, Items.FERMENTED_SPIDER_EYE, Potions.STRONG_HARMING);
      addMix(Potions.HARMING, Items.GLOWSTONE_DUST, Potions.STRONG_HARMING);
      addMix(Potions.POISON, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
      addMix(Potions.LONG_POISON, Items.FERMENTED_SPIDER_EYE, Potions.HARMING);
      addMix(Potions.STRONG_POISON, Items.FERMENTED_SPIDER_EYE, Potions.STRONG_HARMING);
      addMix(Potions.AWKWARD, Items.SPIDER_EYE, Potions.POISON);
      addMix(Potions.POISON, Items.REDSTONE, Potions.LONG_POISON);
      addMix(Potions.POISON, Items.GLOWSTONE_DUST, Potions.STRONG_POISON);
      addMix(Potions.AWKWARD, Items.GHAST_TEAR, Potions.REGENERATION);
      addMix(Potions.REGENERATION, Items.REDSTONE, Potions.LONG_REGENERATION);
      addMix(Potions.REGENERATION, Items.GLOWSTONE_DUST, Potions.STRONG_REGENERATION);
      addMix(Potions.AWKWARD, Items.BLAZE_POWDER, Potions.STRENGTH);
      addMix(Potions.STRENGTH, Items.REDSTONE, Potions.LONG_STRENGTH);
      addMix(Potions.STRENGTH, Items.GLOWSTONE_DUST, Potions.STRONG_STRENGTH);
      addMix(Potions.WATER, Items.FERMENTED_SPIDER_EYE, Potions.WEAKNESS);
      addMix(Potions.WEAKNESS, Items.REDSTONE, Potions.LONG_WEAKNESS);
      addMix(Potions.AWKWARD, Items.PHANTOM_MEMBRANE, Potions.SLOW_FALLING);
      addMix(Potions.SLOW_FALLING, Items.REDSTONE, Potions.LONG_SLOW_FALLING);
   }

   private static void addContainerRecipe(Item var0, Item var1, Item var2) {
      if (!(â˜ƒ instanceof PotionItem)) {
         throw new IllegalArgumentException("Expected a potion, got: " + Registry.ITEM.getKey(â˜ƒ));
      } else if (!(â˜ƒ instanceof PotionItem)) {
         throw new IllegalArgumentException("Expected a potion, got: " + Registry.ITEM.getKey(â˜ƒ));
      } else {
         CONTAINER_MIXES.add(new PotionBrewing.Mix<>(â˜ƒ, Ingredient.of(â˜ƒ), â˜ƒ));
      }
   }

   private static void addContainer(Item var0) {
      if (!(â˜ƒ instanceof PotionItem)) {
         throw new IllegalArgumentException("Expected a potion, got: " + Registry.ITEM.getKey(â˜ƒ));
      } else {
         ALLOWED_CONTAINERS.add(Ingredient.of(â˜ƒ));
      }
   }

   private static void addMix(Potion var0, Item var1, Potion var2) {
      POTION_MIXES.add(new PotionBrewing.Mix<>(â˜ƒ, Ingredient.of(â˜ƒ), â˜ƒ));
   }

   static class Mix<T> {
      final T from;
      final Ingredient ingredient;
      final T to;

      public Mix(T var1, Ingredient var2, T var3) {
         this.from = â˜ƒ;
         this.ingredient = â˜ƒ;
         this.to = â˜ƒ;
      }
   }
}
