package net.minecraft.client.color.item;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.core.IdMapper;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ItemColors {
   private static final int DEFAULT = -1;
   private final IdMapper<ItemColor> itemColors = new IdMapper<>(32);

   public static ItemColors createDefault(BlockColors var0) {
      ItemColors â˜ƒ = new ItemColors();
      â˜ƒ.register(
         (var0x, var1x) -> var1x > 0 ? -1 : ((DyeableLeatherItem)var0x.getItem()).getColor(var0x),
         Items.LEATHER_HELMET,
         Items.LEATHER_CHESTPLATE,
         Items.LEATHER_LEGGINGS,
         Items.LEATHER_BOOTS,
         Items.LEATHER_HORSE_ARMOR
      );
      â˜ƒ.register((var0x, var1x) -> GrassColor.get(0.5, 1.0), Blocks.TALL_GRASS, Blocks.LARGE_FERN);
      â˜ƒ.register((var0x, var1x) -> {
         if (var1x != 1) {
            return -1;
         } else {
            CompoundTag â˜ƒ = var0x.getTagElement("Explosion");
            int[] â˜ƒx = â˜ƒ != null && â˜ƒ.contains("Colors", 11) ? â˜ƒ.getIntArray("Colors") : null;
            if (â˜ƒx != null && â˜ƒx.length != 0) {
               if (â˜ƒx.length == 1) {
                  return â˜ƒx[0];
               } else {
                  int â˜ƒxx = 0;
                  int â˜ƒxxx = 0;
                  int â˜ƒxxxx = 0;

                  for(int â˜ƒxxxxx : â˜ƒx) {
                     â˜ƒxx += (â˜ƒxxxxx & 0xFF0000) >> 16;
                     â˜ƒxxx += (â˜ƒxxxxx & 0xFF00) >> 8;
                     â˜ƒxxxx += (â˜ƒxxxxx & 0xFF) >> 0;
                  }

                  â˜ƒxx /= â˜ƒx.length;
                  â˜ƒxxx /= â˜ƒx.length;
                  â˜ƒxxxx /= â˜ƒx.length;
                  return â˜ƒxx << 16 | â˜ƒxxx << 8 | â˜ƒxxxx;
               }
            } else {
               return 9079434;
            }
         }
      }, Items.FIREWORK_STAR);
      â˜ƒ.register((var0x, var1x) -> var1x > 0 ? -1 : PotionUtils.getColor(var0x), Items.POTION, Items.SPLASH_POTION, Items.LINGERING_POTION);

      for(SpawnEggItem â˜ƒx : SpawnEggItem.eggs()) {
         â˜ƒ.register((var1x, var2) -> â˜ƒ.getColor(var2), â˜ƒx);
      }

      â˜ƒ.register(
         (var1x, var2) -> {
            BlockState â˜ƒ = ((BlockItem)var1x.getItem()).getBlock().defaultBlockState();
            return â˜ƒ.getColor(â˜ƒ, null, null, var2);
         },
         Blocks.GRASS_BLOCK,
         Blocks.GRASS,
         Blocks.FERN,
         Blocks.VINE,
         Blocks.OAK_LEAVES,
         Blocks.SPRUCE_LEAVES,
         Blocks.BIRCH_LEAVES,
         Blocks.JUNGLE_LEAVES,
         Blocks.ACACIA_LEAVES,
         Blocks.DARK_OAK_LEAVES,
         Blocks.LILY_PAD
      );
      â˜ƒ.register((var0x, var1x) -> var1x == 0 ? PotionUtils.getColor(var0x) : -1, Items.TIPPED_ARROW);
      â˜ƒ.register((var0x, var1x) -> var1x == 0 ? -1 : MapItem.getColor(var0x), Items.FILLED_MAP);
      return â˜ƒ;
   }

   public int getColor(ItemStack var1, int var2) {
      ItemColor â˜ƒ = this.itemColors.byId(Registry.ITEM.getId(â˜ƒ.getItem()));
      return â˜ƒ == null ? -1 : â˜ƒ.getColor(â˜ƒ, â˜ƒ);
   }

   public void register(ItemColor var1, ItemLike... var2) {
      for(ItemLike â˜ƒ : â˜ƒ) {
         this.itemColors.addMapping(â˜ƒ, Item.getId(â˜ƒ.asItem()));
      }
   }
}
