package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class ItemBlockRenderTypes {
   private static final Map<Block, RenderType> TYPE_BY_BLOCK = Util.make(Maps.<Block, RenderType>newHashMap(), var0 -> {
      RenderType â˜ƒ = RenderType.tripwire();
      var0.put(Blocks.TRIPWIRE, â˜ƒ);
      RenderType â˜ƒx = RenderType.cutoutMipped();
      var0.put(Blocks.GRASS_BLOCK, â˜ƒx);
      var0.put(Blocks.IRON_BARS, â˜ƒx);
      var0.put(Blocks.GLASS_PANE, â˜ƒx);
      var0.put(Blocks.TRIPWIRE_HOOK, â˜ƒx);
      var0.put(Blocks.HOPPER, â˜ƒx);
      var0.put(Blocks.CHAIN, â˜ƒx);
      var0.put(Blocks.JUNGLE_LEAVES, â˜ƒx);
      var0.put(Blocks.OAK_LEAVES, â˜ƒx);
      var0.put(Blocks.SPRUCE_LEAVES, â˜ƒx);
      var0.put(Blocks.ACACIA_LEAVES, â˜ƒx);
      var0.put(Blocks.BIRCH_LEAVES, â˜ƒx);
      var0.put(Blocks.DARK_OAK_LEAVES, â˜ƒx);
      var0.put(Blocks.AZALEA_LEAVES, â˜ƒx);
      var0.put(Blocks.FLOWERING_AZALEA_LEAVES, â˜ƒx);
      RenderType â˜ƒxx = RenderType.cutout();
      var0.put(Blocks.OAK_SAPLING, â˜ƒxx);
      var0.put(Blocks.SPRUCE_SAPLING, â˜ƒxx);
      var0.put(Blocks.BIRCH_SAPLING, â˜ƒxx);
      var0.put(Blocks.JUNGLE_SAPLING, â˜ƒxx);
      var0.put(Blocks.ACACIA_SAPLING, â˜ƒxx);
      var0.put(Blocks.DARK_OAK_SAPLING, â˜ƒxx);
      var0.put(Blocks.GLASS, â˜ƒxx);
      var0.put(Blocks.WHITE_BED, â˜ƒxx);
      var0.put(Blocks.ORANGE_BED, â˜ƒxx);
      var0.put(Blocks.MAGENTA_BED, â˜ƒxx);
      var0.put(Blocks.LIGHT_BLUE_BED, â˜ƒxx);
      var0.put(Blocks.YELLOW_BED, â˜ƒxx);
      var0.put(Blocks.LIME_BED, â˜ƒxx);
      var0.put(Blocks.PINK_BED, â˜ƒxx);
      var0.put(Blocks.GRAY_BED, â˜ƒxx);
      var0.put(Blocks.LIGHT_GRAY_BED, â˜ƒxx);
      var0.put(Blocks.CYAN_BED, â˜ƒxx);
      var0.put(Blocks.PURPLE_BED, â˜ƒxx);
      var0.put(Blocks.BLUE_BED, â˜ƒxx);
      var0.put(Blocks.BROWN_BED, â˜ƒxx);
      var0.put(Blocks.GREEN_BED, â˜ƒxx);
      var0.put(Blocks.RED_BED, â˜ƒxx);
      var0.put(Blocks.BLACK_BED, â˜ƒxx);
      var0.put(Blocks.POWERED_RAIL, â˜ƒxx);
      var0.put(Blocks.DETECTOR_RAIL, â˜ƒxx);
      var0.put(Blocks.COBWEB, â˜ƒxx);
      var0.put(Blocks.GRASS, â˜ƒxx);
      var0.put(Blocks.FERN, â˜ƒxx);
      var0.put(Blocks.DEAD_BUSH, â˜ƒxx);
      var0.put(Blocks.SEAGRASS, â˜ƒxx);
      var0.put(Blocks.TALL_SEAGRASS, â˜ƒxx);
      var0.put(Blocks.DANDELION, â˜ƒxx);
      var0.put(Blocks.POPPY, â˜ƒxx);
      var0.put(Blocks.BLUE_ORCHID, â˜ƒxx);
      var0.put(Blocks.ALLIUM, â˜ƒxx);
      var0.put(Blocks.AZURE_BLUET, â˜ƒxx);
      var0.put(Blocks.RED_TULIP, â˜ƒxx);
      var0.put(Blocks.ORANGE_TULIP, â˜ƒxx);
      var0.put(Blocks.WHITE_TULIP, â˜ƒxx);
      var0.put(Blocks.PINK_TULIP, â˜ƒxx);
      var0.put(Blocks.OXEYE_DAISY, â˜ƒxx);
      var0.put(Blocks.CORNFLOWER, â˜ƒxx);
      var0.put(Blocks.WITHER_ROSE, â˜ƒxx);
      var0.put(Blocks.LILY_OF_THE_VALLEY, â˜ƒxx);
      var0.put(Blocks.BROWN_MUSHROOM, â˜ƒxx);
      var0.put(Blocks.RED_MUSHROOM, â˜ƒxx);
      var0.put(Blocks.TORCH, â˜ƒxx);
      var0.put(Blocks.WALL_TORCH, â˜ƒxx);
      var0.put(Blocks.SOUL_TORCH, â˜ƒxx);
      var0.put(Blocks.SOUL_WALL_TORCH, â˜ƒxx);
      var0.put(Blocks.FIRE, â˜ƒxx);
      var0.put(Blocks.SOUL_FIRE, â˜ƒxx);
      var0.put(Blocks.SPAWNER, â˜ƒxx);
      var0.put(Blocks.REDSTONE_WIRE, â˜ƒxx);
      var0.put(Blocks.WHEAT, â˜ƒxx);
      var0.put(Blocks.OAK_DOOR, â˜ƒxx);
      var0.put(Blocks.LADDER, â˜ƒxx);
      var0.put(Blocks.RAIL, â˜ƒxx);
      var0.put(Blocks.IRON_DOOR, â˜ƒxx);
      var0.put(Blocks.REDSTONE_TORCH, â˜ƒxx);
      var0.put(Blocks.REDSTONE_WALL_TORCH, â˜ƒxx);
      var0.put(Blocks.CACTUS, â˜ƒxx);
      var0.put(Blocks.SUGAR_CANE, â˜ƒxx);
      var0.put(Blocks.REPEATER, â˜ƒxx);
      var0.put(Blocks.OAK_TRAPDOOR, â˜ƒxx);
      var0.put(Blocks.SPRUCE_TRAPDOOR, â˜ƒxx);
      var0.put(Blocks.BIRCH_TRAPDOOR, â˜ƒxx);
      var0.put(Blocks.JUNGLE_TRAPDOOR, â˜ƒxx);
      var0.put(Blocks.ACACIA_TRAPDOOR, â˜ƒxx);
      var0.put(Blocks.DARK_OAK_TRAPDOOR, â˜ƒxx);
      var0.put(Blocks.CRIMSON_TRAPDOOR, â˜ƒxx);
      var0.put(Blocks.WARPED_TRAPDOOR, â˜ƒxx);
      var0.put(Blocks.ATTACHED_PUMPKIN_STEM, â˜ƒxx);
      var0.put(Blocks.ATTACHED_MELON_STEM, â˜ƒxx);
      var0.put(Blocks.PUMPKIN_STEM, â˜ƒxx);
      var0.put(Blocks.MELON_STEM, â˜ƒxx);
      var0.put(Blocks.VINE, â˜ƒxx);
      var0.put(Blocks.GLOW_LICHEN, â˜ƒxx);
      var0.put(Blocks.LILY_PAD, â˜ƒxx);
      var0.put(Blocks.NETHER_WART, â˜ƒxx);
      var0.put(Blocks.BREWING_STAND, â˜ƒxx);
      var0.put(Blocks.COCOA, â˜ƒxx);
      var0.put(Blocks.BEACON, â˜ƒxx);
      var0.put(Blocks.FLOWER_POT, â˜ƒxx);
      var0.put(Blocks.POTTED_OAK_SAPLING, â˜ƒxx);
      var0.put(Blocks.POTTED_SPRUCE_SAPLING, â˜ƒxx);
      var0.put(Blocks.POTTED_BIRCH_SAPLING, â˜ƒxx);
      var0.put(Blocks.POTTED_JUNGLE_SAPLING, â˜ƒxx);
      var0.put(Blocks.POTTED_ACACIA_SAPLING, â˜ƒxx);
      var0.put(Blocks.POTTED_DARK_OAK_SAPLING, â˜ƒxx);
      var0.put(Blocks.POTTED_FERN, â˜ƒxx);
      var0.put(Blocks.POTTED_DANDELION, â˜ƒxx);
      var0.put(Blocks.POTTED_POPPY, â˜ƒxx);
      var0.put(Blocks.POTTED_BLUE_ORCHID, â˜ƒxx);
      var0.put(Blocks.POTTED_ALLIUM, â˜ƒxx);
      var0.put(Blocks.POTTED_AZURE_BLUET, â˜ƒxx);
      var0.put(Blocks.POTTED_RED_TULIP, â˜ƒxx);
      var0.put(Blocks.POTTED_ORANGE_TULIP, â˜ƒxx);
      var0.put(Blocks.POTTED_WHITE_TULIP, â˜ƒxx);
      var0.put(Blocks.POTTED_PINK_TULIP, â˜ƒxx);
      var0.put(Blocks.POTTED_OXEYE_DAISY, â˜ƒxx);
      var0.put(Blocks.POTTED_CORNFLOWER, â˜ƒxx);
      var0.put(Blocks.POTTED_LILY_OF_THE_VALLEY, â˜ƒxx);
      var0.put(Blocks.POTTED_WITHER_ROSE, â˜ƒxx);
      var0.put(Blocks.POTTED_RED_MUSHROOM, â˜ƒxx);
      var0.put(Blocks.POTTED_BROWN_MUSHROOM, â˜ƒxx);
      var0.put(Blocks.POTTED_DEAD_BUSH, â˜ƒxx);
      var0.put(Blocks.POTTED_CACTUS, â˜ƒxx);
      var0.put(Blocks.POTTED_AZALEA, â˜ƒxx);
      var0.put(Blocks.POTTED_FLOWERING_AZALEA, â˜ƒxx);
      var0.put(Blocks.CARROTS, â˜ƒxx);
      var0.put(Blocks.POTATOES, â˜ƒxx);
      var0.put(Blocks.COMPARATOR, â˜ƒxx);
      var0.put(Blocks.ACTIVATOR_RAIL, â˜ƒxx);
      var0.put(Blocks.IRON_TRAPDOOR, â˜ƒxx);
      var0.put(Blocks.SUNFLOWER, â˜ƒxx);
      var0.put(Blocks.LILAC, â˜ƒxx);
      var0.put(Blocks.ROSE_BUSH, â˜ƒxx);
      var0.put(Blocks.PEONY, â˜ƒxx);
      var0.put(Blocks.TALL_GRASS, â˜ƒxx);
      var0.put(Blocks.LARGE_FERN, â˜ƒxx);
      var0.put(Blocks.SPRUCE_DOOR, â˜ƒxx);
      var0.put(Blocks.BIRCH_DOOR, â˜ƒxx);
      var0.put(Blocks.JUNGLE_DOOR, â˜ƒxx);
      var0.put(Blocks.ACACIA_DOOR, â˜ƒxx);
      var0.put(Blocks.DARK_OAK_DOOR, â˜ƒxx);
      var0.put(Blocks.END_ROD, â˜ƒxx);
      var0.put(Blocks.CHORUS_PLANT, â˜ƒxx);
      var0.put(Blocks.CHORUS_FLOWER, â˜ƒxx);
      var0.put(Blocks.BEETROOTS, â˜ƒxx);
      var0.put(Blocks.KELP, â˜ƒxx);
      var0.put(Blocks.KELP_PLANT, â˜ƒxx);
      var0.put(Blocks.TURTLE_EGG, â˜ƒxx);
      var0.put(Blocks.DEAD_TUBE_CORAL, â˜ƒxx);
      var0.put(Blocks.DEAD_BRAIN_CORAL, â˜ƒxx);
      var0.put(Blocks.DEAD_BUBBLE_CORAL, â˜ƒxx);
      var0.put(Blocks.DEAD_FIRE_CORAL, â˜ƒxx);
      var0.put(Blocks.DEAD_HORN_CORAL, â˜ƒxx);
      var0.put(Blocks.TUBE_CORAL, â˜ƒxx);
      var0.put(Blocks.BRAIN_CORAL, â˜ƒxx);
      var0.put(Blocks.BUBBLE_CORAL, â˜ƒxx);
      var0.put(Blocks.FIRE_CORAL, â˜ƒxx);
      var0.put(Blocks.HORN_CORAL, â˜ƒxx);
      var0.put(Blocks.DEAD_TUBE_CORAL_FAN, â˜ƒxx);
      var0.put(Blocks.DEAD_BRAIN_CORAL_FAN, â˜ƒxx);
      var0.put(Blocks.DEAD_BUBBLE_CORAL_FAN, â˜ƒxx);
      var0.put(Blocks.DEAD_FIRE_CORAL_FAN, â˜ƒxx);
      var0.put(Blocks.DEAD_HORN_CORAL_FAN, â˜ƒxx);
      var0.put(Blocks.TUBE_CORAL_FAN, â˜ƒxx);
      var0.put(Blocks.BRAIN_CORAL_FAN, â˜ƒxx);
      var0.put(Blocks.BUBBLE_CORAL_FAN, â˜ƒxx);
      var0.put(Blocks.FIRE_CORAL_FAN, â˜ƒxx);
      var0.put(Blocks.HORN_CORAL_FAN, â˜ƒxx);
      var0.put(Blocks.DEAD_TUBE_CORAL_WALL_FAN, â˜ƒxx);
      var0.put(Blocks.DEAD_BRAIN_CORAL_WALL_FAN, â˜ƒxx);
      var0.put(Blocks.DEAD_BUBBLE_CORAL_WALL_FAN, â˜ƒxx);
      var0.put(Blocks.DEAD_FIRE_CORAL_WALL_FAN, â˜ƒxx);
      var0.put(Blocks.DEAD_HORN_CORAL_WALL_FAN, â˜ƒxx);
      var0.put(Blocks.TUBE_CORAL_WALL_FAN, â˜ƒxx);
      var0.put(Blocks.BRAIN_CORAL_WALL_FAN, â˜ƒxx);
      var0.put(Blocks.BUBBLE_CORAL_WALL_FAN, â˜ƒxx);
      var0.put(Blocks.FIRE_CORAL_WALL_FAN, â˜ƒxx);
      var0.put(Blocks.HORN_CORAL_WALL_FAN, â˜ƒxx);
      var0.put(Blocks.SEA_PICKLE, â˜ƒxx);
      var0.put(Blocks.CONDUIT, â˜ƒxx);
      var0.put(Blocks.BAMBOO_SAPLING, â˜ƒxx);
      var0.put(Blocks.BAMBOO, â˜ƒxx);
      var0.put(Blocks.POTTED_BAMBOO, â˜ƒxx);
      var0.put(Blocks.SCAFFOLDING, â˜ƒxx);
      var0.put(Blocks.STONECUTTER, â˜ƒxx);
      var0.put(Blocks.LANTERN, â˜ƒxx);
      var0.put(Blocks.SOUL_LANTERN, â˜ƒxx);
      var0.put(Blocks.CAMPFIRE, â˜ƒxx);
      var0.put(Blocks.SOUL_CAMPFIRE, â˜ƒxx);
      var0.put(Blocks.SWEET_BERRY_BUSH, â˜ƒxx);
      var0.put(Blocks.WEEPING_VINES, â˜ƒxx);
      var0.put(Blocks.WEEPING_VINES_PLANT, â˜ƒxx);
      var0.put(Blocks.TWISTING_VINES, â˜ƒxx);
      var0.put(Blocks.TWISTING_VINES_PLANT, â˜ƒxx);
      var0.put(Blocks.NETHER_SPROUTS, â˜ƒxx);
      var0.put(Blocks.CRIMSON_FUNGUS, â˜ƒxx);
      var0.put(Blocks.WARPED_FUNGUS, â˜ƒxx);
      var0.put(Blocks.CRIMSON_ROOTS, â˜ƒxx);
      var0.put(Blocks.WARPED_ROOTS, â˜ƒxx);
      var0.put(Blocks.POTTED_CRIMSON_FUNGUS, â˜ƒxx);
      var0.put(Blocks.POTTED_WARPED_FUNGUS, â˜ƒxx);
      var0.put(Blocks.POTTED_CRIMSON_ROOTS, â˜ƒxx);
      var0.put(Blocks.POTTED_WARPED_ROOTS, â˜ƒxx);
      var0.put(Blocks.CRIMSON_DOOR, â˜ƒxx);
      var0.put(Blocks.WARPED_DOOR, â˜ƒxx);
      var0.put(Blocks.POINTED_DRIPSTONE, â˜ƒxx);
      var0.put(Blocks.SMALL_AMETHYST_BUD, â˜ƒxx);
      var0.put(Blocks.MEDIUM_AMETHYST_BUD, â˜ƒxx);
      var0.put(Blocks.LARGE_AMETHYST_BUD, â˜ƒxx);
      var0.put(Blocks.AMETHYST_CLUSTER, â˜ƒxx);
      var0.put(Blocks.LIGHTNING_ROD, â˜ƒxx);
      var0.put(Blocks.CAVE_VINES, â˜ƒxx);
      var0.put(Blocks.CAVE_VINES_PLANT, â˜ƒxx);
      var0.put(Blocks.SPORE_BLOSSOM, â˜ƒxx);
      var0.put(Blocks.FLOWERING_AZALEA, â˜ƒxx);
      var0.put(Blocks.AZALEA, â˜ƒxx);
      var0.put(Blocks.MOSS_CARPET, â˜ƒxx);
      var0.put(Blocks.BIG_DRIPLEAF, â˜ƒxx);
      var0.put(Blocks.BIG_DRIPLEAF_STEM, â˜ƒxx);
      var0.put(Blocks.SMALL_DRIPLEAF, â˜ƒxx);
      var0.put(Blocks.HANGING_ROOTS, â˜ƒxx);
      var0.put(Blocks.SCULK_SENSOR, â˜ƒxx);
      RenderType â˜ƒxxx = RenderType.translucent();
      var0.put(Blocks.ICE, â˜ƒxxx);
      var0.put(Blocks.NETHER_PORTAL, â˜ƒxxx);
      var0.put(Blocks.WHITE_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.ORANGE_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.MAGENTA_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.LIGHT_BLUE_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.YELLOW_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.LIME_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.PINK_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.GRAY_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.LIGHT_GRAY_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.CYAN_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.PURPLE_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.BLUE_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.BROWN_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.GREEN_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.RED_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.BLACK_STAINED_GLASS, â˜ƒxxx);
      var0.put(Blocks.WHITE_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.ORANGE_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.MAGENTA_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.YELLOW_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.LIME_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.PINK_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.GRAY_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.CYAN_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.PURPLE_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.BLUE_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.BROWN_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.GREEN_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.RED_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.BLACK_STAINED_GLASS_PANE, â˜ƒxxx);
      var0.put(Blocks.SLIME_BLOCK, â˜ƒxxx);
      var0.put(Blocks.HONEY_BLOCK, â˜ƒxxx);
      var0.put(Blocks.FROSTED_ICE, â˜ƒxxx);
      var0.put(Blocks.BUBBLE_COLUMN, â˜ƒxxx);
      var0.put(Blocks.TINTED_GLASS, â˜ƒxxx);
   });
   private static final Map<Fluid, RenderType> TYPE_BY_FLUID = Util.make(Maps.<Fluid, RenderType>newHashMap(), var0 -> {
      RenderType â˜ƒ = RenderType.translucent();
      var0.put(Fluids.FLOWING_WATER, â˜ƒ);
      var0.put(Fluids.WATER, â˜ƒ);
   });
   private static boolean renderCutout;

   public static RenderType getChunkRenderType(BlockState var0) {
      Block â˜ƒ = â˜ƒ.getBlock();
      if (â˜ƒ instanceof LeavesBlock) {
         return renderCutout ? RenderType.cutoutMipped() : RenderType.solid();
      } else {
         RenderType â˜ƒ = (RenderType)TYPE_BY_BLOCK.get(â˜ƒ);
         return â˜ƒ != null ? â˜ƒ : RenderType.solid();
      }
   }

   public static RenderType getMovingBlockRenderType(BlockState var0) {
      Block â˜ƒ = â˜ƒ.getBlock();
      if (â˜ƒ instanceof LeavesBlock) {
         return renderCutout ? RenderType.cutoutMipped() : RenderType.solid();
      } else {
         RenderType â˜ƒ = (RenderType)TYPE_BY_BLOCK.get(â˜ƒ);
         if (â˜ƒ != null) {
            return â˜ƒ == RenderType.translucent() ? RenderType.translucentMovingBlock() : â˜ƒ;
         } else {
            return RenderType.solid();
         }
      }
   }

   public static RenderType getRenderType(BlockState var0, boolean var1) {
      RenderType â˜ƒ = getChunkRenderType(â˜ƒ);
      if (â˜ƒ == RenderType.translucent()) {
         if (!Minecraft.useShaderTransparency()) {
            return Sheets.translucentCullBlockSheet();
         } else {
            return â˜ƒ ? Sheets.translucentCullBlockSheet() : Sheets.translucentItemSheet();
         }
      } else {
         return Sheets.cutoutBlockSheet();
      }
   }

   public static RenderType getRenderType(ItemStack var0, boolean var1) {
      Item â˜ƒ = â˜ƒ.getItem();
      if (â˜ƒ instanceof BlockItem) {
         Block â˜ƒx = ((BlockItem)â˜ƒ).getBlock();
         return getRenderType(â˜ƒx.defaultBlockState(), â˜ƒ);
      } else {
         return â˜ƒ ? Sheets.translucentCullBlockSheet() : Sheets.translucentItemSheet();
      }
   }

   public static RenderType getRenderLayer(FluidState var0) {
      RenderType â˜ƒ = (RenderType)TYPE_BY_FLUID.get(â˜ƒ.getType());
      return â˜ƒ != null ? â˜ƒ : RenderType.solid();
   }

   public static void setFancy(boolean var0) {
      renderCutout = â˜ƒ;
   }
}
