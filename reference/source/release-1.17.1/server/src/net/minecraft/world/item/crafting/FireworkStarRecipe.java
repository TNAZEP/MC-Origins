package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.FireworkRocketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FireworkStarRecipe extends CustomRecipe {
   private static final Ingredient SHAPE_INGREDIENT = Ingredient.of(
      Items.FIRE_CHARGE,
      Items.FEATHER,
      Items.GOLD_NUGGET,
      Items.SKELETON_SKULL,
      Items.WITHER_SKELETON_SKULL,
      Items.CREEPER_HEAD,
      Items.PLAYER_HEAD,
      Items.DRAGON_HEAD,
      Items.ZOMBIE_HEAD
   );
   private static final Ingredient TRAIL_INGREDIENT = Ingredient.of(Items.DIAMOND);
   private static final Ingredient FLICKER_INGREDIENT = Ingredient.of(Items.GLOWSTONE_DUST);
   private static final Map<Item, FireworkRocketItem.Shape> SHAPE_BY_ITEM = Util.make(Maps.newHashMap(), var0 -> {
      var0.put(Items.FIRE_CHARGE, FireworkRocketItem.Shape.LARGE_BALL);
      var0.put(Items.FEATHER, FireworkRocketItem.Shape.BURST);
      var0.put(Items.GOLD_NUGGET, FireworkRocketItem.Shape.STAR);
      var0.put(Items.SKELETON_SKULL, FireworkRocketItem.Shape.CREEPER);
      var0.put(Items.WITHER_SKELETON_SKULL, FireworkRocketItem.Shape.CREEPER);
      var0.put(Items.CREEPER_HEAD, FireworkRocketItem.Shape.CREEPER);
      var0.put(Items.PLAYER_HEAD, FireworkRocketItem.Shape.CREEPER);
      var0.put(Items.DRAGON_HEAD, FireworkRocketItem.Shape.CREEPER);
      var0.put(Items.ZOMBIE_HEAD, FireworkRocketItem.Shape.CREEPER);
   });
   private static final Ingredient GUNPOWDER_INGREDIENT = Ingredient.of(Items.GUNPOWDER);

   public FireworkStarRecipe(ResourceLocation var1) {
      super(â˜ƒ);
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      boolean â˜ƒ = false;
      boolean â˜ƒx = false;
      boolean â˜ƒxx = false;
      boolean â˜ƒxxx = false;
      boolean â˜ƒxxxx = false;

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒ.getContainerSize(); ++â˜ƒxxxxx) {
         ItemStack â˜ƒxxxxxx = â˜ƒ.getItem(â˜ƒxxxxx);
         if (!â˜ƒxxxxxx.isEmpty()) {
            if (SHAPE_INGREDIENT.test(â˜ƒxxxxxx)) {
               if (â˜ƒxx) {
                  return false;
               }

               â˜ƒxx = true;
            } else if (FLICKER_INGREDIENT.test(â˜ƒxxxxxx)) {
               if (â˜ƒxxxx) {
                  return false;
               }

               â˜ƒxxxx = true;
            } else if (TRAIL_INGREDIENT.test(â˜ƒxxxxxx)) {
               if (â˜ƒxxx) {
                  return false;
               }

               â˜ƒxxx = true;
            } else if (GUNPOWDER_INGREDIENT.test(â˜ƒxxxxxx)) {
               if (â˜ƒ) {
                  return false;
               }

               â˜ƒ = true;
            } else {
               if (!(â˜ƒxxxxxx.getItem() instanceof DyeItem)) {
                  return false;
               }

               â˜ƒx = true;
            }
         }
      }

      return â˜ƒ && â˜ƒx;
   }

   public ItemStack assemble(CraftingContainer var1) {
      ItemStack â˜ƒ = new ItemStack(Items.FIREWORK_STAR);
      CompoundTag â˜ƒx = â˜ƒ.getOrCreateTagElement("Explosion");
      FireworkRocketItem.Shape â˜ƒxx = FireworkRocketItem.Shape.SMALL_BALL;
      List<Integer> â˜ƒxxx = Lists.newArrayList();

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒ.getContainerSize(); ++â˜ƒxxxx) {
         ItemStack â˜ƒxxxxx = â˜ƒ.getItem(â˜ƒxxxx);
         if (!â˜ƒxxxxx.isEmpty()) {
            if (SHAPE_INGREDIENT.test(â˜ƒxxxxx)) {
               â˜ƒxx = (FireworkRocketItem.Shape)SHAPE_BY_ITEM.get(â˜ƒxxxxx.getItem());
            } else if (FLICKER_INGREDIENT.test(â˜ƒxxxxx)) {
               â˜ƒx.putBoolean("Flicker", true);
            } else if (TRAIL_INGREDIENT.test(â˜ƒxxxxx)) {
               â˜ƒx.putBoolean("Trail", true);
            } else if (â˜ƒxxxxx.getItem() instanceof DyeItem) {
               â˜ƒxxx.add(((DyeItem)â˜ƒxxxxx.getItem()).getDyeColor().getFireworkColor());
            }
         }
      }

      â˜ƒx.putIntArray("Colors", â˜ƒxxx);
      â˜ƒx.putByte("Type", (byte)â˜ƒxx.getId());
      return â˜ƒ;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ * â˜ƒ >= 2;
   }

   @Override
   public ItemStack getResultItem() {
      return new ItemStack(Items.FIREWORK_STAR);
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.FIREWORK_STAR;
   }
}
