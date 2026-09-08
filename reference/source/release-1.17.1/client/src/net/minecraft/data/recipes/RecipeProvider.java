package net.minecraft.data.recipes;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.EnterBlockTrigger;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeProvider implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final ImmutableList<ItemLike> COAL_SMELTABLES = ImmutableList.of(Items.COAL_ORE, Items.DEEPSLATE_COAL_ORE);
   private static final ImmutableList<ItemLike> IRON_SMELTABLES = ImmutableList.of(Items.IRON_ORE, Items.DEEPSLATE_IRON_ORE, Items.RAW_IRON);
   private static final ImmutableList<ItemLike> COPPER_SMELTABLES = ImmutableList.of(Items.COPPER_ORE, Items.DEEPSLATE_COPPER_ORE, Items.RAW_COPPER);
   private static final ImmutableList<ItemLike> GOLD_SMELTABLES = ImmutableList.of(
      Items.GOLD_ORE, Items.DEEPSLATE_GOLD_ORE, Items.NETHER_GOLD_ORE, Items.RAW_GOLD
   );
   private static final ImmutableList<ItemLike> DIAMOND_SMELTABLES = ImmutableList.of(Items.DIAMOND_ORE, Items.DEEPSLATE_DIAMOND_ORE);
   private static final ImmutableList<ItemLike> LAPIS_SMELTABLES = ImmutableList.of(Items.LAPIS_ORE, Items.DEEPSLATE_LAPIS_ORE);
   private static final ImmutableList<ItemLike> REDSTONE_SMELTABLES = ImmutableList.of(Items.REDSTONE_ORE, Items.DEEPSLATE_REDSTONE_ORE);
   private static final ImmutableList<ItemLike> EMERALD_SMELTABLES = ImmutableList.of(Items.EMERALD_ORE, Items.DEEPSLATE_EMERALD_ORE);
   private final DataGenerator generator;
   private static final Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> shapeBuilders = ImmutableMap.builder()
      .put(BlockFamily.Variant.BUTTON, (BiFunction)(var0, var1) -> buttonBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.CHISELED, (BiFunction)(var0, var1) -> chiseledBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.CUT, (BiFunction)(var0, var1) -> cutBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.DOOR, (BiFunction)(var0, var1) -> doorBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.FENCE, (BiFunction)(var0, var1) -> fenceBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.FENCE_GATE, (BiFunction)(var0, var1) -> fenceGateBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.SIGN, (BiFunction)(var0, var1) -> signBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.SLAB, (BiFunction)(var0, var1) -> slabBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.STAIRS, (BiFunction)(var0, var1) -> stairBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.PRESSURE_PLATE, (BiFunction)(var0, var1) -> pressurePlateBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.POLISHED, (BiFunction)(var0, var1) -> polishedBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.TRAPDOOR, (BiFunction)(var0, var1) -> trapdoorBuilder(var0, Ingredient.of(var1)))
      .put(BlockFamily.Variant.WALL, (BiFunction)(var0, var1) -> wallBuilder(var0, Ingredient.of(var1)))
      .build();

   public RecipeProvider(DataGenerator var1) {
      this.generator = â˜ƒ;
   }

   @Override
   public void run(HashCache var1) {
      Path â˜ƒ = this.generator.getOutputFolder();
      Set<ResourceLocation> â˜ƒx = Sets.<ResourceLocation>newHashSet();
      buildCraftingRecipes(var3x -> {
         if (!â˜ƒ.add(var3x.getId())) {
            throw new IllegalStateException("Duplicate recipe " + var3x.getId());
         } else {
            saveRecipe(â˜ƒ, var3x.serializeRecipe(), â˜ƒ.resolve("data/" + var3x.getId().getNamespace() + "/recipes/" + var3x.getId().getPath() + ".json"));
            JsonObject â˜ƒ = var3x.serializeAdvancement();
            if (â˜ƒ != null) {
               saveAdvancement(â˜ƒ, â˜ƒ, â˜ƒ.resolve("data/" + var3x.getId().getNamespace() + "/advancements/" + var3x.getAdvancementId().getPath() + ".json"));
            }
         }
      });
      saveAdvancement(
         â˜ƒ,
         Advancement.Builder.advancement().addCriterion("impossible", new ImpossibleTrigger.TriggerInstance()).serializeToJson(),
         â˜ƒ.resolve("data/minecraft/advancements/recipes/root.json")
      );
   }

   private static void saveRecipe(HashCache var0, JsonObject var1, Path var2) {
      try {
         String â˜ƒ = GSON.toJson((JsonElement)â˜ƒ);
         String â˜ƒx = SHA1.hashUnencodedChars(â˜ƒ).toString();
         if (!Objects.equals(â˜ƒ.getHash(â˜ƒ), â˜ƒx) || !Files.exists(â˜ƒ, new LinkOption[0])) {
            Files.createDirectories(â˜ƒ.getParent());
            BufferedWriter â˜ƒxx = Files.newBufferedWriter(â˜ƒ);

            try {
               â˜ƒxx.write(â˜ƒ);
            } catch (Throwable var9) {
               if (â˜ƒxx != null) {
                  try {
                     â˜ƒxx.close();
                  } catch (Throwable var8) {
                     var9.addSuppressed(var8);
                  }
               }

               throw var9;
            }

            if (â˜ƒxx != null) {
               â˜ƒxx.close();
            }
         }

         â˜ƒ.putNew(â˜ƒ, â˜ƒx);
      } catch (IOException var10) {
         LOGGER.error("Couldn't save recipe {}", â˜ƒ, var10);
      }
   }

   private static void saveAdvancement(HashCache var0, JsonObject var1, Path var2) {
      try {
         String â˜ƒ = GSON.toJson((JsonElement)â˜ƒ);
         String â˜ƒx = SHA1.hashUnencodedChars(â˜ƒ).toString();
         if (!Objects.equals(â˜ƒ.getHash(â˜ƒ), â˜ƒx) || !Files.exists(â˜ƒ, new LinkOption[0])) {
            Files.createDirectories(â˜ƒ.getParent());
            BufferedWriter â˜ƒxx = Files.newBufferedWriter(â˜ƒ);

            try {
               â˜ƒxx.write(â˜ƒ);
            } catch (Throwable var9) {
               if (â˜ƒxx != null) {
                  try {
                     â˜ƒxx.close();
                  } catch (Throwable var8) {
                     var9.addSuppressed(var8);
                  }
               }

               throw var9;
            }

            if (â˜ƒxx != null) {
               â˜ƒxx.close();
            }
         }

         â˜ƒ.putNew(â˜ƒ, â˜ƒx);
      } catch (IOException var10) {
         LOGGER.error("Couldn't save recipe advancement {}", â˜ƒ, var10);
      }
   }

   private static void buildCraftingRecipes(Consumer<FinishedRecipe> var0) {
      BlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(var1 -> generateRecipes(â˜ƒ, var1));
      planksFromLog(â˜ƒ, Blocks.ACACIA_PLANKS, ItemTags.ACACIA_LOGS);
      planksFromLogs(â˜ƒ, Blocks.BIRCH_PLANKS, ItemTags.BIRCH_LOGS);
      planksFromLogs(â˜ƒ, Blocks.CRIMSON_PLANKS, ItemTags.CRIMSON_STEMS);
      planksFromLog(â˜ƒ, Blocks.DARK_OAK_PLANKS, ItemTags.DARK_OAK_LOGS);
      planksFromLogs(â˜ƒ, Blocks.JUNGLE_PLANKS, ItemTags.JUNGLE_LOGS);
      planksFromLogs(â˜ƒ, Blocks.OAK_PLANKS, ItemTags.OAK_LOGS);
      planksFromLogs(â˜ƒ, Blocks.SPRUCE_PLANKS, ItemTags.SPRUCE_LOGS);
      planksFromLogs(â˜ƒ, Blocks.WARPED_PLANKS, ItemTags.WARPED_STEMS);
      woodFromLogs(â˜ƒ, Blocks.ACACIA_WOOD, Blocks.ACACIA_LOG);
      woodFromLogs(â˜ƒ, Blocks.BIRCH_WOOD, Blocks.BIRCH_LOG);
      woodFromLogs(â˜ƒ, Blocks.DARK_OAK_WOOD, Blocks.DARK_OAK_LOG);
      woodFromLogs(â˜ƒ, Blocks.JUNGLE_WOOD, Blocks.JUNGLE_LOG);
      woodFromLogs(â˜ƒ, Blocks.OAK_WOOD, Blocks.OAK_LOG);
      woodFromLogs(â˜ƒ, Blocks.SPRUCE_WOOD, Blocks.SPRUCE_LOG);
      woodFromLogs(â˜ƒ, Blocks.CRIMSON_HYPHAE, Blocks.CRIMSON_STEM);
      woodFromLogs(â˜ƒ, Blocks.WARPED_HYPHAE, Blocks.WARPED_STEM);
      woodFromLogs(â˜ƒ, Blocks.STRIPPED_ACACIA_WOOD, Blocks.STRIPPED_ACACIA_LOG);
      woodFromLogs(â˜ƒ, Blocks.STRIPPED_BIRCH_WOOD, Blocks.STRIPPED_BIRCH_LOG);
      woodFromLogs(â˜ƒ, Blocks.STRIPPED_DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_LOG);
      woodFromLogs(â˜ƒ, Blocks.STRIPPED_JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_LOG);
      woodFromLogs(â˜ƒ, Blocks.STRIPPED_OAK_WOOD, Blocks.STRIPPED_OAK_LOG);
      woodFromLogs(â˜ƒ, Blocks.STRIPPED_SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_LOG);
      woodFromLogs(â˜ƒ, Blocks.STRIPPED_CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_STEM);
      woodFromLogs(â˜ƒ, Blocks.STRIPPED_WARPED_HYPHAE, Blocks.STRIPPED_WARPED_STEM);
      woodenBoat(â˜ƒ, Items.ACACIA_BOAT, Blocks.ACACIA_PLANKS);
      woodenBoat(â˜ƒ, Items.BIRCH_BOAT, Blocks.BIRCH_PLANKS);
      woodenBoat(â˜ƒ, Items.DARK_OAK_BOAT, Blocks.DARK_OAK_PLANKS);
      woodenBoat(â˜ƒ, Items.JUNGLE_BOAT, Blocks.JUNGLE_PLANKS);
      woodenBoat(â˜ƒ, Items.OAK_BOAT, Blocks.OAK_PLANKS);
      woodenBoat(â˜ƒ, Items.SPRUCE_BOAT, Blocks.SPRUCE_PLANKS);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.BLACK_WOOL, Items.BLACK_DYE);
      carpet(â˜ƒ, Blocks.BLACK_CARPET, Blocks.BLACK_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.BLACK_CARPET, Items.BLACK_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.BLACK_BED, Blocks.BLACK_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.BLACK_BED, Items.BLACK_DYE);
      banner(â˜ƒ, Items.BLACK_BANNER, Blocks.BLACK_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.BLUE_WOOL, Items.BLUE_DYE);
      carpet(â˜ƒ, Blocks.BLUE_CARPET, Blocks.BLUE_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.BLUE_CARPET, Items.BLUE_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.BLUE_BED, Blocks.BLUE_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.BLUE_BED, Items.BLUE_DYE);
      banner(â˜ƒ, Items.BLUE_BANNER, Blocks.BLUE_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.BROWN_WOOL, Items.BROWN_DYE);
      carpet(â˜ƒ, Blocks.BROWN_CARPET, Blocks.BROWN_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.BROWN_CARPET, Items.BROWN_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.BROWN_BED, Blocks.BROWN_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.BROWN_BED, Items.BROWN_DYE);
      banner(â˜ƒ, Items.BROWN_BANNER, Blocks.BROWN_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.CYAN_WOOL, Items.CYAN_DYE);
      carpet(â˜ƒ, Blocks.CYAN_CARPET, Blocks.CYAN_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.CYAN_CARPET, Items.CYAN_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.CYAN_BED, Blocks.CYAN_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.CYAN_BED, Items.CYAN_DYE);
      banner(â˜ƒ, Items.CYAN_BANNER, Blocks.CYAN_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.GRAY_WOOL, Items.GRAY_DYE);
      carpet(â˜ƒ, Blocks.GRAY_CARPET, Blocks.GRAY_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.GRAY_CARPET, Items.GRAY_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.GRAY_BED, Blocks.GRAY_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.GRAY_BED, Items.GRAY_DYE);
      banner(â˜ƒ, Items.GRAY_BANNER, Blocks.GRAY_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.GREEN_WOOL, Items.GREEN_DYE);
      carpet(â˜ƒ, Blocks.GREEN_CARPET, Blocks.GREEN_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.GREEN_CARPET, Items.GREEN_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.GREEN_BED, Blocks.GREEN_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.GREEN_BED, Items.GREEN_DYE);
      banner(â˜ƒ, Items.GREEN_BANNER, Blocks.GREEN_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.LIGHT_BLUE_WOOL, Items.LIGHT_BLUE_DYE);
      carpet(â˜ƒ, Blocks.LIGHT_BLUE_CARPET, Blocks.LIGHT_BLUE_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.LIGHT_BLUE_CARPET, Items.LIGHT_BLUE_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.LIGHT_BLUE_BED, Blocks.LIGHT_BLUE_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.LIGHT_BLUE_BED, Items.LIGHT_BLUE_DYE);
      banner(â˜ƒ, Items.LIGHT_BLUE_BANNER, Blocks.LIGHT_BLUE_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.LIGHT_GRAY_WOOL, Items.LIGHT_GRAY_DYE);
      carpet(â˜ƒ, Blocks.LIGHT_GRAY_CARPET, Blocks.LIGHT_GRAY_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.LIGHT_GRAY_CARPET, Items.LIGHT_GRAY_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.LIGHT_GRAY_BED, Blocks.LIGHT_GRAY_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.LIGHT_GRAY_BED, Items.LIGHT_GRAY_DYE);
      banner(â˜ƒ, Items.LIGHT_GRAY_BANNER, Blocks.LIGHT_GRAY_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.LIME_WOOL, Items.LIME_DYE);
      carpet(â˜ƒ, Blocks.LIME_CARPET, Blocks.LIME_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.LIME_CARPET, Items.LIME_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.LIME_BED, Blocks.LIME_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.LIME_BED, Items.LIME_DYE);
      banner(â˜ƒ, Items.LIME_BANNER, Blocks.LIME_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.MAGENTA_WOOL, Items.MAGENTA_DYE);
      carpet(â˜ƒ, Blocks.MAGENTA_CARPET, Blocks.MAGENTA_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.MAGENTA_CARPET, Items.MAGENTA_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.MAGENTA_BED, Blocks.MAGENTA_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.MAGENTA_BED, Items.MAGENTA_DYE);
      banner(â˜ƒ, Items.MAGENTA_BANNER, Blocks.MAGENTA_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.ORANGE_WOOL, Items.ORANGE_DYE);
      carpet(â˜ƒ, Blocks.ORANGE_CARPET, Blocks.ORANGE_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.ORANGE_CARPET, Items.ORANGE_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.ORANGE_BED, Blocks.ORANGE_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.ORANGE_BED, Items.ORANGE_DYE);
      banner(â˜ƒ, Items.ORANGE_BANNER, Blocks.ORANGE_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.PINK_WOOL, Items.PINK_DYE);
      carpet(â˜ƒ, Blocks.PINK_CARPET, Blocks.PINK_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.PINK_CARPET, Items.PINK_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.PINK_BED, Blocks.PINK_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.PINK_BED, Items.PINK_DYE);
      banner(â˜ƒ, Items.PINK_BANNER, Blocks.PINK_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.PURPLE_WOOL, Items.PURPLE_DYE);
      carpet(â˜ƒ, Blocks.PURPLE_CARPET, Blocks.PURPLE_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.PURPLE_CARPET, Items.PURPLE_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.PURPLE_BED, Blocks.PURPLE_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.PURPLE_BED, Items.PURPLE_DYE);
      banner(â˜ƒ, Items.PURPLE_BANNER, Blocks.PURPLE_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.RED_WOOL, Items.RED_DYE);
      carpet(â˜ƒ, Blocks.RED_CARPET, Blocks.RED_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.RED_CARPET, Items.RED_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.RED_BED, Blocks.RED_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.RED_BED, Items.RED_DYE);
      banner(â˜ƒ, Items.RED_BANNER, Blocks.RED_WOOL);
      carpet(â˜ƒ, Blocks.WHITE_CARPET, Blocks.WHITE_WOOL);
      bedFromPlanksAndWool(â˜ƒ, Items.WHITE_BED, Blocks.WHITE_WOOL);
      banner(â˜ƒ, Items.WHITE_BANNER, Blocks.WHITE_WOOL);
      coloredWoolFromWhiteWoolAndDye(â˜ƒ, Blocks.YELLOW_WOOL, Items.YELLOW_DYE);
      carpet(â˜ƒ, Blocks.YELLOW_CARPET, Blocks.YELLOW_WOOL);
      coloredCarpetFromWhiteCarpetAndDye(â˜ƒ, Blocks.YELLOW_CARPET, Items.YELLOW_DYE);
      bedFromPlanksAndWool(â˜ƒ, Items.YELLOW_BED, Blocks.YELLOW_WOOL);
      bedFromWhiteBedAndDye(â˜ƒ, Items.YELLOW_BED, Items.YELLOW_DYE);
      banner(â˜ƒ, Items.YELLOW_BANNER, Blocks.YELLOW_WOOL);
      carpet(â˜ƒ, Blocks.MOSS_CARPET, Blocks.MOSS_BLOCK);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.BLACK_STAINED_GLASS, Items.BLACK_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.BLACK_STAINED_GLASS_PANE, Blocks.BLACK_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.BLACK_STAINED_GLASS_PANE, Items.BLACK_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.BLUE_STAINED_GLASS, Items.BLUE_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BLUE_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.BLUE_STAINED_GLASS_PANE, Items.BLUE_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.BROWN_STAINED_GLASS, Items.BROWN_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.BROWN_STAINED_GLASS_PANE, Items.BROWN_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.CYAN_STAINED_GLASS, Items.CYAN_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.CYAN_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.CYAN_STAINED_GLASS_PANE, Items.CYAN_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.GRAY_STAINED_GLASS, Items.GRAY_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.GRAY_STAINED_GLASS_PANE, Blocks.GRAY_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.GRAY_STAINED_GLASS_PANE, Items.GRAY_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.GREEN_STAINED_GLASS, Items.GREEN_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.GREEN_STAINED_GLASS_PANE, Items.GREEN_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.LIGHT_BLUE_STAINED_GLASS, Items.LIGHT_BLUE_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.LIGHT_BLUE_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Items.LIGHT_BLUE_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.LIGHT_GRAY_STAINED_GLASS, Items.LIGHT_GRAY_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Items.LIGHT_GRAY_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.LIME_STAINED_GLASS, Items.LIME_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.LIME_STAINED_GLASS_PANE, Blocks.LIME_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.LIME_STAINED_GLASS_PANE, Items.LIME_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.MAGENTA_STAINED_GLASS, Items.MAGENTA_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.MAGENTA_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.MAGENTA_STAINED_GLASS_PANE, Items.MAGENTA_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.ORANGE_STAINED_GLASS, Items.ORANGE_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.ORANGE_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.ORANGE_STAINED_GLASS_PANE, Items.ORANGE_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.PINK_STAINED_GLASS, Items.PINK_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.PINK_STAINED_GLASS_PANE, Blocks.PINK_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.PINK_STAINED_GLASS_PANE, Items.PINK_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.PURPLE_STAINED_GLASS, Items.PURPLE_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.PURPLE_STAINED_GLASS_PANE, Blocks.PURPLE_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.PURPLE_STAINED_GLASS_PANE, Items.PURPLE_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.RED_STAINED_GLASS, Items.RED_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.RED_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.RED_STAINED_GLASS_PANE, Items.RED_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.WHITE_STAINED_GLASS, Items.WHITE_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.WHITE_STAINED_GLASS_PANE, Blocks.WHITE_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.WHITE_STAINED_GLASS_PANE, Items.WHITE_DYE);
      stainedGlassFromGlassAndDye(â˜ƒ, Blocks.YELLOW_STAINED_GLASS, Items.YELLOW_DYE);
      stainedGlassPaneFromStainedGlass(â˜ƒ, Blocks.YELLOW_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS);
      stainedGlassPaneFromGlassPaneAndDye(â˜ƒ, Blocks.YELLOW_STAINED_GLASS_PANE, Items.YELLOW_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.BLACK_TERRACOTTA, Items.BLACK_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.BLUE_TERRACOTTA, Items.BLUE_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.BROWN_TERRACOTTA, Items.BROWN_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.CYAN_TERRACOTTA, Items.CYAN_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.GRAY_TERRACOTTA, Items.GRAY_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.GREEN_TERRACOTTA, Items.GREEN_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_BLUE_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.LIGHT_GRAY_TERRACOTTA, Items.LIGHT_GRAY_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.LIME_TERRACOTTA, Items.LIME_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.MAGENTA_TERRACOTTA, Items.MAGENTA_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.ORANGE_TERRACOTTA, Items.ORANGE_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.PINK_TERRACOTTA, Items.PINK_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.PURPLE_TERRACOTTA, Items.PURPLE_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.RED_TERRACOTTA, Items.RED_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.WHITE_TERRACOTTA, Items.WHITE_DYE);
      coloredTerracottaFromTerracottaAndDye(â˜ƒ, Blocks.YELLOW_TERRACOTTA, Items.YELLOW_DYE);
      concretePowder(â˜ƒ, Blocks.BLACK_CONCRETE_POWDER, Items.BLACK_DYE);
      concretePowder(â˜ƒ, Blocks.BLUE_CONCRETE_POWDER, Items.BLUE_DYE);
      concretePowder(â˜ƒ, Blocks.BROWN_CONCRETE_POWDER, Items.BROWN_DYE);
      concretePowder(â˜ƒ, Blocks.CYAN_CONCRETE_POWDER, Items.CYAN_DYE);
      concretePowder(â˜ƒ, Blocks.GRAY_CONCRETE_POWDER, Items.GRAY_DYE);
      concretePowder(â˜ƒ, Blocks.GREEN_CONCRETE_POWDER, Items.GREEN_DYE);
      concretePowder(â˜ƒ, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Items.LIGHT_BLUE_DYE);
      concretePowder(â˜ƒ, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Items.LIGHT_GRAY_DYE);
      concretePowder(â˜ƒ, Blocks.LIME_CONCRETE_POWDER, Items.LIME_DYE);
      concretePowder(â˜ƒ, Blocks.MAGENTA_CONCRETE_POWDER, Items.MAGENTA_DYE);
      concretePowder(â˜ƒ, Blocks.ORANGE_CONCRETE_POWDER, Items.ORANGE_DYE);
      concretePowder(â˜ƒ, Blocks.PINK_CONCRETE_POWDER, Items.PINK_DYE);
      concretePowder(â˜ƒ, Blocks.PURPLE_CONCRETE_POWDER, Items.PURPLE_DYE);
      concretePowder(â˜ƒ, Blocks.RED_CONCRETE_POWDER, Items.RED_DYE);
      concretePowder(â˜ƒ, Blocks.WHITE_CONCRETE_POWDER, Items.WHITE_DYE);
      concretePowder(â˜ƒ, Blocks.YELLOW_CONCRETE_POWDER, Items.YELLOW_DYE);
      ShapedRecipeBuilder.shaped(Items.CANDLE)
         .define('S', Items.STRING)
         .define('H', Items.HONEYCOMB)
         .pattern("S")
         .pattern("H")
         .unlockedBy("has_string", has(Items.STRING))
         .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
         .save(â˜ƒ);
      candle(â˜ƒ, Blocks.BLACK_CANDLE, Items.BLACK_DYE);
      candle(â˜ƒ, Blocks.BLUE_CANDLE, Items.BLUE_DYE);
      candle(â˜ƒ, Blocks.BROWN_CANDLE, Items.BROWN_DYE);
      candle(â˜ƒ, Blocks.CYAN_CANDLE, Items.CYAN_DYE);
      candle(â˜ƒ, Blocks.GRAY_CANDLE, Items.GRAY_DYE);
      candle(â˜ƒ, Blocks.GREEN_CANDLE, Items.GREEN_DYE);
      candle(â˜ƒ, Blocks.LIGHT_BLUE_CANDLE, Items.LIGHT_BLUE_DYE);
      candle(â˜ƒ, Blocks.LIGHT_GRAY_CANDLE, Items.LIGHT_GRAY_DYE);
      candle(â˜ƒ, Blocks.LIME_CANDLE, Items.LIME_DYE);
      candle(â˜ƒ, Blocks.MAGENTA_CANDLE, Items.MAGENTA_DYE);
      candle(â˜ƒ, Blocks.ORANGE_CANDLE, Items.ORANGE_DYE);
      candle(â˜ƒ, Blocks.PINK_CANDLE, Items.PINK_DYE);
      candle(â˜ƒ, Blocks.PURPLE_CANDLE, Items.PURPLE_DYE);
      candle(â˜ƒ, Blocks.RED_CANDLE, Items.RED_DYE);
      candle(â˜ƒ, Blocks.WHITE_CANDLE, Items.WHITE_DYE);
      candle(â˜ƒ, Blocks.YELLOW_CANDLE, Items.YELLOW_DYE);
      ShapedRecipeBuilder.shaped(Blocks.ACTIVATOR_RAIL, 6)
         .define('#', Blocks.REDSTONE_TORCH)
         .define('S', Items.STICK)
         .define('X', Items.IRON_INGOT)
         .pattern("XSX")
         .pattern("X#X")
         .pattern("XSX")
         .unlockedBy("has_rail", has(Blocks.RAIL))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Blocks.ANDESITE, 2)
         .requires(Blocks.DIORITE)
         .requires(Blocks.COBBLESTONE)
         .unlockedBy("has_stone", has(Blocks.DIORITE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.ANVIL)
         .define('I', Blocks.IRON_BLOCK)
         .define('i', Items.IRON_INGOT)
         .pattern("III")
         .pattern(" i ")
         .pattern("iii")
         .unlockedBy("has_iron_block", has(Blocks.IRON_BLOCK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.ARMOR_STAND)
         .define('/', Items.STICK)
         .define('_', Blocks.SMOOTH_STONE_SLAB)
         .pattern("///")
         .pattern(" / ")
         .pattern("/_/")
         .unlockedBy("has_stone_slab", has(Blocks.SMOOTH_STONE_SLAB))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.ARROW, 4)
         .define('#', Items.STICK)
         .define('X', Items.FLINT)
         .define('Y', Items.FEATHER)
         .pattern("X")
         .pattern("#")
         .pattern("Y")
         .unlockedBy("has_feather", has(Items.FEATHER))
         .unlockedBy("has_flint", has(Items.FLINT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.BARREL, 1)
         .define('P', ItemTags.PLANKS)
         .define('S', ItemTags.WOODEN_SLABS)
         .pattern("PSP")
         .pattern("P P")
         .pattern("PSP")
         .unlockedBy("has_planks", has(ItemTags.PLANKS))
         .unlockedBy("has_wood_slab", has(ItemTags.WOODEN_SLABS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.BEACON)
         .define('S', Items.NETHER_STAR)
         .define('G', Blocks.GLASS)
         .define('O', Blocks.OBSIDIAN)
         .pattern("GGG")
         .pattern("GSG")
         .pattern("OOO")
         .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.BEEHIVE)
         .define('P', ItemTags.PLANKS)
         .define('H', Items.HONEYCOMB)
         .pattern("PPP")
         .pattern("HHH")
         .pattern("PPP")
         .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.BEETROOT_SOUP)
         .requires(Items.BOWL)
         .requires(Items.BEETROOT, 6)
         .unlockedBy("has_beetroot", has(Items.BEETROOT))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.BLACK_DYE).requires(Items.INK_SAC).group("black_dye").unlockedBy("has_ink_sac", has(Items.INK_SAC)).save(â˜ƒ);
      oneToOneConversionRecipe(â˜ƒ, Items.BLACK_DYE, Blocks.WITHER_ROSE, "black_dye");
      ShapelessRecipeBuilder.shapeless(Items.BLAZE_POWDER, 2).requires(Items.BLAZE_ROD).unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD)).save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.BLUE_DYE)
         .requires(Items.LAPIS_LAZULI)
         .group("blue_dye")
         .unlockedBy("has_lapis_lazuli", has(Items.LAPIS_LAZULI))
         .save(â˜ƒ);
      oneToOneConversionRecipe(â˜ƒ, Items.BLUE_DYE, Blocks.CORNFLOWER, "blue_dye");
      ShapedRecipeBuilder.shaped(Blocks.BLUE_ICE)
         .define('#', Blocks.PACKED_ICE)
         .pattern("###")
         .pattern("###")
         .pattern("###")
         .unlockedBy("has_packed_ice", has(Blocks.PACKED_ICE))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.BONE_MEAL, 3).requires(Items.BONE).group("bonemeal").unlockedBy("has_bone", has(Items.BONE)).save(â˜ƒ);
      nineBlockStorageRecipesRecipesWithCustomUnpacking(â˜ƒ, Items.BONE_MEAL, Items.BONE_BLOCK, "bone_meal_from_bone_block", "bonemeal");
      ShapelessRecipeBuilder.shapeless(Items.BOOK).requires(Items.PAPER, 3).requires(Items.LEATHER).unlockedBy("has_paper", has(Items.PAPER)).save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.BOOKSHELF)
         .define('#', ItemTags.PLANKS)
         .define('X', Items.BOOK)
         .pattern("###")
         .pattern("XXX")
         .pattern("###")
         .unlockedBy("has_book", has(Items.BOOK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.BOW)
         .define('#', Items.STICK)
         .define('X', Items.STRING)
         .pattern(" #X")
         .pattern("# X")
         .pattern(" #X")
         .unlockedBy("has_string", has(Items.STRING))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.BOWL, 4)
         .define('#', ItemTags.PLANKS)
         .pattern("# #")
         .pattern(" # ")
         .unlockedBy("has_brown_mushroom", has(Blocks.BROWN_MUSHROOM))
         .unlockedBy("has_red_mushroom", has(Blocks.RED_MUSHROOM))
         .unlockedBy("has_mushroom_stew", has(Items.MUSHROOM_STEW))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.BREAD).define('#', Items.WHEAT).pattern("###").unlockedBy("has_wheat", has(Items.WHEAT)).save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.BREWING_STAND)
         .define('B', Items.BLAZE_ROD)
         .define('#', ItemTags.STONE_CRAFTING_MATERIALS)
         .pattern(" B ")
         .pattern("###")
         .unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.BRICKS).define('#', Items.BRICK).pattern("##").pattern("##").unlockedBy("has_brick", has(Items.BRICK)).save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.BROWN_DYE)
         .requires(Items.COCOA_BEANS)
         .group("brown_dye")
         .unlockedBy("has_cocoa_beans", has(Items.COCOA_BEANS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.BUCKET)
         .define('#', Items.IRON_INGOT)
         .pattern("# #")
         .pattern(" # ")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.CAKE)
         .define('A', Items.MILK_BUCKET)
         .define('B', Items.SUGAR)
         .define('C', Items.WHEAT)
         .define('E', Items.EGG)
         .pattern("AAA")
         .pattern("BEB")
         .pattern("CCC")
         .unlockedBy("has_egg", has(Items.EGG))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.CAMPFIRE)
         .define('L', ItemTags.LOGS)
         .define('S', Items.STICK)
         .define('C', ItemTags.COALS)
         .pattern(" S ")
         .pattern("SCS")
         .pattern("LLL")
         .unlockedBy("has_stick", has(Items.STICK))
         .unlockedBy("has_coal", has(ItemTags.COALS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.CARROT_ON_A_STICK)
         .define('#', Items.FISHING_ROD)
         .define('X', Items.CARROT)
         .pattern("# ")
         .pattern(" X")
         .unlockedBy("has_carrot", has(Items.CARROT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.WARPED_FUNGUS_ON_A_STICK)
         .define('#', Items.FISHING_ROD)
         .define('X', Items.WARPED_FUNGUS)
         .pattern("# ")
         .pattern(" X")
         .unlockedBy("has_warped_fungus", has(Items.WARPED_FUNGUS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.CAULDRON)
         .define('#', Items.IRON_INGOT)
         .pattern("# #")
         .pattern("# #")
         .pattern("###")
         .unlockedBy("has_water_bucket", has(Items.WATER_BUCKET))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.COMPOSTER)
         .define('#', ItemTags.WOODEN_SLABS)
         .pattern("# #")
         .pattern("# #")
         .pattern("###")
         .unlockedBy("has_wood_slab", has(ItemTags.WOODEN_SLABS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.CHEST)
         .define('#', ItemTags.PLANKS)
         .pattern("###")
         .pattern("# #")
         .pattern("###")
         .unlockedBy(
            "has_lots_of_items",
            new InventoryChangeTrigger.TriggerInstance(
               EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0]
            )
         )
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.CHEST_MINECART)
         .define('A', Blocks.CHEST)
         .define('B', Items.MINECART)
         .pattern("A")
         .pattern("B")
         .unlockedBy("has_minecart", has(Items.MINECART))
         .save(â˜ƒ);
      chiseledBuilder(Blocks.CHISELED_QUARTZ_BLOCK, Ingredient.of(Blocks.QUARTZ_SLAB))
         .unlockedBy("has_chiseled_quartz_block", has(Blocks.CHISELED_QUARTZ_BLOCK))
         .unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
         .unlockedBy("has_quartz_pillar", has(Blocks.QUARTZ_PILLAR))
         .save(â˜ƒ);
      chiseledBuilder(Blocks.CHISELED_STONE_BRICKS, Ingredient.of(Blocks.STONE_BRICK_SLAB)).unlockedBy("has_tag", has(ItemTags.STONE_BRICKS)).save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.CLAY)
         .define('#', Items.CLAY_BALL)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.CLOCK)
         .define('#', Items.GOLD_INGOT)
         .define('X', Items.REDSTONE)
         .pattern(" # ")
         .pattern("#X#")
         .pattern(" # ")
         .unlockedBy("has_redstone", has(Items.REDSTONE))
         .save(â˜ƒ);
      nineBlockStorageRecipes(â˜ƒ, Items.COAL, Items.COAL_BLOCK);
      ShapedRecipeBuilder.shaped(Blocks.COARSE_DIRT, 4)
         .define('D', Blocks.DIRT)
         .define('G', Blocks.GRAVEL)
         .pattern("DG")
         .pattern("GD")
         .unlockedBy("has_gravel", has(Blocks.GRAVEL))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.COMPARATOR)
         .define('#', Blocks.REDSTONE_TORCH)
         .define('X', Items.QUARTZ)
         .define('I', Blocks.STONE)
         .pattern(" # ")
         .pattern("#X#")
         .pattern("III")
         .unlockedBy("has_quartz", has(Items.QUARTZ))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.COMPASS)
         .define('#', Items.IRON_INGOT)
         .define('X', Items.REDSTONE)
         .pattern(" # ")
         .pattern("#X#")
         .pattern(" # ")
         .unlockedBy("has_redstone", has(Items.REDSTONE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.COOKIE, 8)
         .define('#', Items.WHEAT)
         .define('X', Items.COCOA_BEANS)
         .pattern("#X#")
         .unlockedBy("has_cocoa", has(Items.COCOA_BEANS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.CRAFTING_TABLE)
         .define('#', ItemTags.PLANKS)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_planks", has(ItemTags.PLANKS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.CROSSBOW)
         .define('~', Items.STRING)
         .define('#', Items.STICK)
         .define('&', Items.IRON_INGOT)
         .define('$', Blocks.TRIPWIRE_HOOK)
         .pattern("#&#")
         .pattern("~$~")
         .pattern(" # ")
         .unlockedBy("has_string", has(Items.STRING))
         .unlockedBy("has_stick", has(Items.STICK))
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.LOOM)
         .define('#', ItemTags.PLANKS)
         .define('@', Items.STRING)
         .pattern("@@")
         .pattern("##")
         .unlockedBy("has_string", has(Items.STRING))
         .save(â˜ƒ);
      chiseledBuilder(Blocks.CHISELED_RED_SANDSTONE, Ingredient.of(Blocks.RED_SANDSTONE_SLAB))
         .unlockedBy("has_red_sandstone", has(Blocks.RED_SANDSTONE))
         .unlockedBy("has_chiseled_red_sandstone", has(Blocks.CHISELED_RED_SANDSTONE))
         .unlockedBy("has_cut_red_sandstone", has(Blocks.CUT_RED_SANDSTONE))
         .save(â˜ƒ);
      chiseled(â˜ƒ, Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE_SLAB);
      nineBlockStorageRecipesRecipesWithCustomUnpacking(
         â˜ƒ, Items.COPPER_INGOT, Items.COPPER_BLOCK, getSimpleRecipeName(Items.COPPER_INGOT), getItemName(Items.COPPER_INGOT)
      );
      ShapelessRecipeBuilder.shapeless(Items.COPPER_INGOT, 9)
         .requires(Blocks.WAXED_COPPER_BLOCK)
         .group(getItemName(Items.COPPER_INGOT))
         .unlockedBy(getHasName(Blocks.WAXED_COPPER_BLOCK), has(Blocks.WAXED_COPPER_BLOCK))
         .save(â˜ƒ, getConversionRecipeName(Items.COPPER_INGOT, Blocks.WAXED_COPPER_BLOCK));
      waxRecipes(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.CYAN_DYE, 2)
         .requires(Items.BLUE_DYE)
         .requires(Items.GREEN_DYE)
         .unlockedBy("has_green_dye", has(Items.GREEN_DYE))
         .unlockedBy("has_blue_dye", has(Items.BLUE_DYE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.DARK_PRISMARINE)
         .define('S', Items.PRISMARINE_SHARD)
         .define('I', Items.BLACK_DYE)
         .pattern("SSS")
         .pattern("SIS")
         .pattern("SSS")
         .unlockedBy("has_prismarine_shard", has(Items.PRISMARINE_SHARD))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.DAYLIGHT_DETECTOR)
         .define('Q', Items.QUARTZ)
         .define('G', Blocks.GLASS)
         .define('W', Ingredient.of(ItemTags.WOODEN_SLABS))
         .pattern("GGG")
         .pattern("QQQ")
         .pattern("WWW")
         .unlockedBy("has_quartz", has(Items.QUARTZ))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.DEEPSLATE_BRICKS, 4)
         .define('S', Blocks.POLISHED_DEEPSLATE)
         .pattern("SS")
         .pattern("SS")
         .unlockedBy("has_polished_deepslate", has(Blocks.POLISHED_DEEPSLATE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.DEEPSLATE_TILES, 4)
         .define('S', Blocks.DEEPSLATE_BRICKS)
         .pattern("SS")
         .pattern("SS")
         .unlockedBy("has_deepslate_bricks", has(Blocks.DEEPSLATE_BRICKS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.DETECTOR_RAIL, 6)
         .define('R', Items.REDSTONE)
         .define('#', Blocks.STONE_PRESSURE_PLATE)
         .define('X', Items.IRON_INGOT)
         .pattern("X X")
         .pattern("X#X")
         .pattern("XRX")
         .unlockedBy("has_rail", has(Blocks.RAIL))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.DIAMOND_AXE)
         .define('#', Items.STICK)
         .define('X', Items.DIAMOND)
         .pattern("XX")
         .pattern("X#")
         .pattern(" #")
         .unlockedBy("has_diamond", has(Items.DIAMOND))
         .save(â˜ƒ);
      nineBlockStorageRecipes(â˜ƒ, Items.DIAMOND, Items.DIAMOND_BLOCK);
      ShapedRecipeBuilder.shaped(Items.DIAMOND_BOOTS)
         .define('X', Items.DIAMOND)
         .pattern("X X")
         .pattern("X X")
         .unlockedBy("has_diamond", has(Items.DIAMOND))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.DIAMOND_CHESTPLATE)
         .define('X', Items.DIAMOND)
         .pattern("X X")
         .pattern("XXX")
         .pattern("XXX")
         .unlockedBy("has_diamond", has(Items.DIAMOND))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.DIAMOND_HELMET)
         .define('X', Items.DIAMOND)
         .pattern("XXX")
         .pattern("X X")
         .unlockedBy("has_diamond", has(Items.DIAMOND))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.DIAMOND_HOE)
         .define('#', Items.STICK)
         .define('X', Items.DIAMOND)
         .pattern("XX")
         .pattern(" #")
         .pattern(" #")
         .unlockedBy("has_diamond", has(Items.DIAMOND))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.DIAMOND_LEGGINGS)
         .define('X', Items.DIAMOND)
         .pattern("XXX")
         .pattern("X X")
         .pattern("X X")
         .unlockedBy("has_diamond", has(Items.DIAMOND))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.DIAMOND_PICKAXE)
         .define('#', Items.STICK)
         .define('X', Items.DIAMOND)
         .pattern("XXX")
         .pattern(" # ")
         .pattern(" # ")
         .unlockedBy("has_diamond", has(Items.DIAMOND))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.DIAMOND_SHOVEL)
         .define('#', Items.STICK)
         .define('X', Items.DIAMOND)
         .pattern("X")
         .pattern("#")
         .pattern("#")
         .unlockedBy("has_diamond", has(Items.DIAMOND))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.DIAMOND_SWORD)
         .define('#', Items.STICK)
         .define('X', Items.DIAMOND)
         .pattern("X")
         .pattern("X")
         .pattern("#")
         .unlockedBy("has_diamond", has(Items.DIAMOND))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.DIORITE, 2)
         .define('Q', Items.QUARTZ)
         .define('C', Blocks.COBBLESTONE)
         .pattern("CQ")
         .pattern("QC")
         .unlockedBy("has_quartz", has(Items.QUARTZ))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.DISPENSER)
         .define('R', Items.REDSTONE)
         .define('#', Blocks.COBBLESTONE)
         .define('X', Items.BOW)
         .pattern("###")
         .pattern("#X#")
         .pattern("#R#")
         .unlockedBy("has_bow", has(Items.BOW))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.DRIPSTONE_BLOCK)
         .define('#', Items.POINTED_DRIPSTONE)
         .pattern("##")
         .pattern("##")
         .group("pointed_dripstone")
         .unlockedBy("has_pointed_dripstone", has(Items.POINTED_DRIPSTONE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.DROPPER)
         .define('R', Items.REDSTONE)
         .define('#', Blocks.COBBLESTONE)
         .pattern("###")
         .pattern("# #")
         .pattern("#R#")
         .unlockedBy("has_redstone", has(Items.REDSTONE))
         .save(â˜ƒ);
      nineBlockStorageRecipes(â˜ƒ, Items.EMERALD, Items.EMERALD_BLOCK);
      ShapedRecipeBuilder.shaped(Blocks.ENCHANTING_TABLE)
         .define('B', Items.BOOK)
         .define('#', Blocks.OBSIDIAN)
         .define('D', Items.DIAMOND)
         .pattern(" B ")
         .pattern("D#D")
         .pattern("###")
         .unlockedBy("has_obsidian", has(Blocks.OBSIDIAN))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.ENDER_CHEST)
         .define('#', Blocks.OBSIDIAN)
         .define('E', Items.ENDER_EYE)
         .pattern("###")
         .pattern("#E#")
         .pattern("###")
         .unlockedBy("has_ender_eye", has(Items.ENDER_EYE))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.ENDER_EYE)
         .requires(Items.ENDER_PEARL)
         .requires(Items.BLAZE_POWDER)
         .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.END_STONE_BRICKS, 4)
         .define('#', Blocks.END_STONE)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_end_stone", has(Blocks.END_STONE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.END_CRYSTAL)
         .define('T', Items.GHAST_TEAR)
         .define('E', Items.ENDER_EYE)
         .define('G', Blocks.GLASS)
         .pattern("GGG")
         .pattern("GEG")
         .pattern("GTG")
         .unlockedBy("has_ender_eye", has(Items.ENDER_EYE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.END_ROD, 4)
         .define('#', Items.POPPED_CHORUS_FRUIT)
         .define('/', Items.BLAZE_ROD)
         .pattern("/")
         .pattern("#")
         .unlockedBy("has_chorus_fruit_popped", has(Items.POPPED_CHORUS_FRUIT))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.FERMENTED_SPIDER_EYE)
         .requires(Items.SPIDER_EYE)
         .requires(Blocks.BROWN_MUSHROOM)
         .requires(Items.SUGAR)
         .unlockedBy("has_spider_eye", has(Items.SPIDER_EYE))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.FIRE_CHARGE, 3)
         .requires(Items.GUNPOWDER)
         .requires(Items.BLAZE_POWDER)
         .requires(Ingredient.of(Items.COAL, Items.CHARCOAL))
         .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.FIREWORK_ROCKET, 3)
         .requires(Items.GUNPOWDER)
         .requires(Items.PAPER)
         .unlockedBy("has_gunpowder", has(Items.GUNPOWDER))
         .save(â˜ƒ, "firework_rocket_simple");
      ShapedRecipeBuilder.shaped(Items.FISHING_ROD)
         .define('#', Items.STICK)
         .define('X', Items.STRING)
         .pattern("  #")
         .pattern(" #X")
         .pattern("# X")
         .unlockedBy("has_string", has(Items.STRING))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.FLINT_AND_STEEL)
         .requires(Items.IRON_INGOT)
         .requires(Items.FLINT)
         .unlockedBy("has_flint", has(Items.FLINT))
         .unlockedBy("has_obsidian", has(Blocks.OBSIDIAN))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.FLOWER_POT).define('#', Items.BRICK).pattern("# #").pattern(" # ").unlockedBy("has_brick", has(Items.BRICK)).save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.FURNACE)
         .define('#', ItemTags.STONE_CRAFTING_MATERIALS)
         .pattern("###")
         .pattern("# #")
         .pattern("###")
         .unlockedBy("has_cobblestone", has(ItemTags.STONE_CRAFTING_MATERIALS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.FURNACE_MINECART)
         .define('A', Blocks.FURNACE)
         .define('B', Items.MINECART)
         .pattern("A")
         .pattern("B")
         .unlockedBy("has_minecart", has(Items.MINECART))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GLASS_BOTTLE, 3)
         .define('#', Blocks.GLASS)
         .pattern("# #")
         .pattern(" # ")
         .unlockedBy("has_glass", has(Blocks.GLASS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.GLASS_PANE, 16)
         .define('#', Blocks.GLASS)
         .pattern("###")
         .pattern("###")
         .unlockedBy("has_glass", has(Blocks.GLASS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.GLOWSTONE)
         .define('#', Items.GLOWSTONE_DUST)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_glowstone_dust", has(Items.GLOWSTONE_DUST))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.GLOW_ITEM_FRAME)
         .requires(Items.ITEM_FRAME)
         .requires(Items.GLOW_INK_SAC)
         .unlockedBy("has_item_frame", has(Items.ITEM_FRAME))
         .unlockedBy("has_glow_ink_sac", has(Items.GLOW_INK_SAC))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_APPLE)
         .define('#', Items.GOLD_INGOT)
         .define('X', Items.APPLE)
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_AXE)
         .define('#', Items.STICK)
         .define('X', Items.GOLD_INGOT)
         .pattern("XX")
         .pattern("X#")
         .pattern(" #")
         .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_BOOTS)
         .define('X', Items.GOLD_INGOT)
         .pattern("X X")
         .pattern("X X")
         .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_CARROT)
         .define('#', Items.GOLD_NUGGET)
         .define('X', Items.CARROT)
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .unlockedBy("has_gold_nugget", has(Items.GOLD_NUGGET))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_CHESTPLATE)
         .define('X', Items.GOLD_INGOT)
         .pattern("X X")
         .pattern("XXX")
         .pattern("XXX")
         .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_HELMET)
         .define('X', Items.GOLD_INGOT)
         .pattern("XXX")
         .pattern("X X")
         .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_HOE)
         .define('#', Items.STICK)
         .define('X', Items.GOLD_INGOT)
         .pattern("XX")
         .pattern(" #")
         .pattern(" #")
         .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_LEGGINGS)
         .define('X', Items.GOLD_INGOT)
         .pattern("XXX")
         .pattern("X X")
         .pattern("X X")
         .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_PICKAXE)
         .define('#', Items.STICK)
         .define('X', Items.GOLD_INGOT)
         .pattern("XXX")
         .pattern(" # ")
         .pattern(" # ")
         .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.POWERED_RAIL, 6)
         .define('R', Items.REDSTONE)
         .define('#', Items.STICK)
         .define('X', Items.GOLD_INGOT)
         .pattern("X X")
         .pattern("X#X")
         .pattern("XRX")
         .unlockedBy("has_rail", has(Blocks.RAIL))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_SHOVEL)
         .define('#', Items.STICK)
         .define('X', Items.GOLD_INGOT)
         .pattern("X")
         .pattern("#")
         .pattern("#")
         .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GOLDEN_SWORD)
         .define('#', Items.STICK)
         .define('X', Items.GOLD_INGOT)
         .pattern("X")
         .pattern("X")
         .pattern("#")
         .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
         .save(â˜ƒ);
      nineBlockStorageRecipesRecipesWithCustomUnpacking(â˜ƒ, Items.GOLD_INGOT, Items.GOLD_BLOCK, "gold_ingot_from_gold_block", "gold_ingot");
      nineBlockStorageRecipesWithCustomPacking(â˜ƒ, Items.GOLD_NUGGET, Items.GOLD_INGOT, "gold_ingot_from_nuggets", "gold_ingot");
      ShapelessRecipeBuilder.shapeless(Blocks.GRANITE).requires(Blocks.DIORITE).requires(Items.QUARTZ).unlockedBy("has_quartz", has(Items.QUARTZ)).save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.GRAY_DYE, 2)
         .requires(Items.BLACK_DYE)
         .requires(Items.WHITE_DYE)
         .unlockedBy("has_white_dye", has(Items.WHITE_DYE))
         .unlockedBy("has_black_dye", has(Items.BLACK_DYE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.HAY_BLOCK)
         .define('#', Items.WHEAT)
         .pattern("###")
         .pattern("###")
         .pattern("###")
         .unlockedBy("has_wheat", has(Items.WHEAT))
         .save(â˜ƒ);
      pressurePlate(â˜ƒ, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Items.IRON_INGOT);
      ShapelessRecipeBuilder.shapeless(Items.HONEY_BOTTLE, 4)
         .requires(Items.HONEY_BLOCK)
         .requires(Items.GLASS_BOTTLE, 4)
         .unlockedBy("has_honey_block", has(Blocks.HONEY_BLOCK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.HONEY_BLOCK, 1)
         .define('S', Items.HONEY_BOTTLE)
         .pattern("SS")
         .pattern("SS")
         .unlockedBy("has_honey_bottle", has(Items.HONEY_BOTTLE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.HONEYCOMB_BLOCK)
         .define('H', Items.HONEYCOMB)
         .pattern("HH")
         .pattern("HH")
         .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.HOPPER)
         .define('C', Blocks.CHEST)
         .define('I', Items.IRON_INGOT)
         .pattern("I I")
         .pattern("ICI")
         .pattern(" I ")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.HOPPER_MINECART)
         .define('A', Blocks.HOPPER)
         .define('B', Items.MINECART)
         .pattern("A")
         .pattern("B")
         .unlockedBy("has_minecart", has(Items.MINECART))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.IRON_AXE)
         .define('#', Items.STICK)
         .define('X', Items.IRON_INGOT)
         .pattern("XX")
         .pattern("X#")
         .pattern(" #")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.IRON_BARS, 16)
         .define('#', Items.IRON_INGOT)
         .pattern("###")
         .pattern("###")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.IRON_BOOTS)
         .define('X', Items.IRON_INGOT)
         .pattern("X X")
         .pattern("X X")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.IRON_CHESTPLATE)
         .define('X', Items.IRON_INGOT)
         .pattern("X X")
         .pattern("XXX")
         .pattern("XXX")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      doorBuilder(Blocks.IRON_DOOR, Ingredient.of(Items.IRON_INGOT)).unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT)).save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.IRON_HELMET)
         .define('X', Items.IRON_INGOT)
         .pattern("XXX")
         .pattern("X X")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.IRON_HOE)
         .define('#', Items.STICK)
         .define('X', Items.IRON_INGOT)
         .pattern("XX")
         .pattern(" #")
         .pattern(" #")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      nineBlockStorageRecipesRecipesWithCustomUnpacking(â˜ƒ, Items.IRON_INGOT, Items.IRON_BLOCK, "iron_ingot_from_iron_block", "iron_ingot");
      nineBlockStorageRecipesWithCustomPacking(â˜ƒ, Items.IRON_NUGGET, Items.IRON_INGOT, "iron_ingot_from_nuggets", "iron_ingot");
      ShapedRecipeBuilder.shaped(Items.IRON_LEGGINGS)
         .define('X', Items.IRON_INGOT)
         .pattern("XXX")
         .pattern("X X")
         .pattern("X X")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.IRON_PICKAXE)
         .define('#', Items.STICK)
         .define('X', Items.IRON_INGOT)
         .pattern("XXX")
         .pattern(" # ")
         .pattern(" # ")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.IRON_SHOVEL)
         .define('#', Items.STICK)
         .define('X', Items.IRON_INGOT)
         .pattern("X")
         .pattern("#")
         .pattern("#")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.IRON_SWORD)
         .define('#', Items.STICK)
         .define('X', Items.IRON_INGOT)
         .pattern("X")
         .pattern("X")
         .pattern("#")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.IRON_TRAPDOOR)
         .define('#', Items.IRON_INGOT)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.ITEM_FRAME)
         .define('#', Items.STICK)
         .define('X', Items.LEATHER)
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .unlockedBy("has_leather", has(Items.LEATHER))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.JUKEBOX)
         .define('#', ItemTags.PLANKS)
         .define('X', Items.DIAMOND)
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .unlockedBy("has_diamond", has(Items.DIAMOND))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.LADDER, 3)
         .define('#', Items.STICK)
         .pattern("# #")
         .pattern("###")
         .pattern("# #")
         .unlockedBy("has_stick", has(Items.STICK))
         .save(â˜ƒ);
      nineBlockStorageRecipes(â˜ƒ, Items.LAPIS_LAZULI, Items.LAPIS_BLOCK);
      ShapedRecipeBuilder.shaped(Items.LEAD, 2)
         .define('~', Items.STRING)
         .define('O', Items.SLIME_BALL)
         .pattern("~~ ")
         .pattern("~O ")
         .pattern("  ~")
         .unlockedBy("has_slime_ball", has(Items.SLIME_BALL))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.LEATHER)
         .define('#', Items.RABBIT_HIDE)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_rabbit_hide", has(Items.RABBIT_HIDE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.LEATHER_BOOTS)
         .define('X', Items.LEATHER)
         .pattern("X X")
         .pattern("X X")
         .unlockedBy("has_leather", has(Items.LEATHER))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.LEATHER_CHESTPLATE)
         .define('X', Items.LEATHER)
         .pattern("X X")
         .pattern("XXX")
         .pattern("XXX")
         .unlockedBy("has_leather", has(Items.LEATHER))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.LEATHER_HELMET)
         .define('X', Items.LEATHER)
         .pattern("XXX")
         .pattern("X X")
         .unlockedBy("has_leather", has(Items.LEATHER))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.LEATHER_LEGGINGS)
         .define('X', Items.LEATHER)
         .pattern("XXX")
         .pattern("X X")
         .pattern("X X")
         .unlockedBy("has_leather", has(Items.LEATHER))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.LEATHER_HORSE_ARMOR)
         .define('X', Items.LEATHER)
         .pattern("X X")
         .pattern("XXX")
         .pattern("X X")
         .unlockedBy("has_leather", has(Items.LEATHER))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.LECTERN)
         .define('S', ItemTags.WOODEN_SLABS)
         .define('B', Blocks.BOOKSHELF)
         .pattern("SSS")
         .pattern(" B ")
         .pattern(" S ")
         .unlockedBy("has_book", has(Items.BOOK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.LEVER)
         .define('#', Blocks.COBBLESTONE)
         .define('X', Items.STICK)
         .pattern("X")
         .pattern("#")
         .unlockedBy("has_cobblestone", has(Blocks.COBBLESTONE))
         .save(â˜ƒ);
      oneToOneConversionRecipe(â˜ƒ, Items.LIGHT_BLUE_DYE, Blocks.BLUE_ORCHID, "light_blue_dye");
      ShapelessRecipeBuilder.shapeless(Items.LIGHT_BLUE_DYE, 2)
         .requires(Items.BLUE_DYE)
         .requires(Items.WHITE_DYE)
         .group("light_blue_dye")
         .unlockedBy("has_blue_dye", has(Items.BLUE_DYE))
         .unlockedBy("has_white_dye", has(Items.WHITE_DYE))
         .save(â˜ƒ, "light_blue_dye_from_blue_white_dye");
      oneToOneConversionRecipe(â˜ƒ, Items.LIGHT_GRAY_DYE, Blocks.AZURE_BLUET, "light_gray_dye");
      ShapelessRecipeBuilder.shapeless(Items.LIGHT_GRAY_DYE, 2)
         .requires(Items.GRAY_DYE)
         .requires(Items.WHITE_DYE)
         .group("light_gray_dye")
         .unlockedBy("has_gray_dye", has(Items.GRAY_DYE))
         .unlockedBy("has_white_dye", has(Items.WHITE_DYE))
         .save(â˜ƒ, "light_gray_dye_from_gray_white_dye");
      ShapelessRecipeBuilder.shapeless(Items.LIGHT_GRAY_DYE, 3)
         .requires(Items.BLACK_DYE)
         .requires(Items.WHITE_DYE, 2)
         .group("light_gray_dye")
         .unlockedBy("has_white_dye", has(Items.WHITE_DYE))
         .unlockedBy("has_black_dye", has(Items.BLACK_DYE))
         .save(â˜ƒ, "light_gray_dye_from_black_white_dye");
      oneToOneConversionRecipe(â˜ƒ, Items.LIGHT_GRAY_DYE, Blocks.OXEYE_DAISY, "light_gray_dye");
      oneToOneConversionRecipe(â˜ƒ, Items.LIGHT_GRAY_DYE, Blocks.WHITE_TULIP, "light_gray_dye");
      pressurePlate(â˜ƒ, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Items.GOLD_INGOT);
      ShapedRecipeBuilder.shaped(Blocks.LIGHTNING_ROD)
         .define('#', Items.COPPER_INGOT)
         .pattern("#")
         .pattern("#")
         .pattern("#")
         .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.LIME_DYE, 2)
         .requires(Items.GREEN_DYE)
         .requires(Items.WHITE_DYE)
         .unlockedBy("has_green_dye", has(Items.GREEN_DYE))
         .unlockedBy("has_white_dye", has(Items.WHITE_DYE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.JACK_O_LANTERN)
         .define('A', Blocks.CARVED_PUMPKIN)
         .define('B', Blocks.TORCH)
         .pattern("A")
         .pattern("B")
         .unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN))
         .save(â˜ƒ);
      oneToOneConversionRecipe(â˜ƒ, Items.MAGENTA_DYE, Blocks.ALLIUM, "magenta_dye");
      ShapelessRecipeBuilder.shapeless(Items.MAGENTA_DYE, 4)
         .requires(Items.BLUE_DYE)
         .requires(Items.RED_DYE, 2)
         .requires(Items.WHITE_DYE)
         .group("magenta_dye")
         .unlockedBy("has_blue_dye", has(Items.BLUE_DYE))
         .unlockedBy("has_rose_red", has(Items.RED_DYE))
         .unlockedBy("has_white_dye", has(Items.WHITE_DYE))
         .save(â˜ƒ, "magenta_dye_from_blue_red_white_dye");
      ShapelessRecipeBuilder.shapeless(Items.MAGENTA_DYE, 3)
         .requires(Items.BLUE_DYE)
         .requires(Items.RED_DYE)
         .requires(Items.PINK_DYE)
         .group("magenta_dye")
         .unlockedBy("has_pink_dye", has(Items.PINK_DYE))
         .unlockedBy("has_blue_dye", has(Items.BLUE_DYE))
         .unlockedBy("has_red_dye", has(Items.RED_DYE))
         .save(â˜ƒ, "magenta_dye_from_blue_red_pink");
      oneToOneConversionRecipe(â˜ƒ, Items.MAGENTA_DYE, Blocks.LILAC, "magenta_dye", 2);
      ShapelessRecipeBuilder.shapeless(Items.MAGENTA_DYE, 2)
         .requires(Items.PURPLE_DYE)
         .requires(Items.PINK_DYE)
         .group("magenta_dye")
         .unlockedBy("has_pink_dye", has(Items.PINK_DYE))
         .unlockedBy("has_purple_dye", has(Items.PURPLE_DYE))
         .save(â˜ƒ, "magenta_dye_from_purple_and_pink");
      ShapedRecipeBuilder.shaped(Blocks.MAGMA_BLOCK)
         .define('#', Items.MAGMA_CREAM)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_magma_cream", has(Items.MAGMA_CREAM))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.MAGMA_CREAM)
         .requires(Items.BLAZE_POWDER)
         .requires(Items.SLIME_BALL)
         .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.MAP)
         .define('#', Items.PAPER)
         .define('X', Items.COMPASS)
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .unlockedBy("has_compass", has(Items.COMPASS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.MELON)
         .define('M', Items.MELON_SLICE)
         .pattern("MMM")
         .pattern("MMM")
         .pattern("MMM")
         .unlockedBy("has_melon", has(Items.MELON_SLICE))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.MELON_SEEDS).requires(Items.MELON_SLICE).unlockedBy("has_melon", has(Items.MELON_SLICE)).save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.MINECART)
         .define('#', Items.IRON_INGOT)
         .pattern("# #")
         .pattern("###")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Blocks.MOSSY_COBBLESTONE)
         .requires(Blocks.COBBLESTONE)
         .requires(Blocks.VINE)
         .group("mossy_cobblestone")
         .unlockedBy("has_vine", has(Blocks.VINE))
         .save(â˜ƒ, getConversionRecipeName(Blocks.MOSSY_COBBLESTONE, Blocks.VINE));
      ShapelessRecipeBuilder.shapeless(Blocks.MOSSY_STONE_BRICKS)
         .requires(Blocks.STONE_BRICKS)
         .requires(Blocks.VINE)
         .group("mossy_stone_bricks")
         .unlockedBy("has_vine", has(Blocks.VINE))
         .save(â˜ƒ, getConversionRecipeName(Blocks.MOSSY_STONE_BRICKS, Blocks.VINE));
      ShapelessRecipeBuilder.shapeless(Blocks.MOSSY_COBBLESTONE)
         .requires(Blocks.COBBLESTONE)
         .requires(Blocks.MOSS_BLOCK)
         .group("mossy_cobblestone")
         .unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
         .save(â˜ƒ, getConversionRecipeName(Blocks.MOSSY_COBBLESTONE, Blocks.MOSS_BLOCK));
      ShapelessRecipeBuilder.shapeless(Blocks.MOSSY_STONE_BRICKS)
         .requires(Blocks.STONE_BRICKS)
         .requires(Blocks.MOSS_BLOCK)
         .group("mossy_stone_bricks")
         .unlockedBy("has_moss_block", has(Blocks.MOSS_BLOCK))
         .save(â˜ƒ, getConversionRecipeName(Blocks.MOSSY_STONE_BRICKS, Blocks.MOSS_BLOCK));
      ShapelessRecipeBuilder.shapeless(Items.MUSHROOM_STEW)
         .requires(Blocks.BROWN_MUSHROOM)
         .requires(Blocks.RED_MUSHROOM)
         .requires(Items.BOWL)
         .unlockedBy("has_mushroom_stew", has(Items.MUSHROOM_STEW))
         .unlockedBy("has_bowl", has(Items.BOWL))
         .unlockedBy("has_brown_mushroom", has(Blocks.BROWN_MUSHROOM))
         .unlockedBy("has_red_mushroom", has(Blocks.RED_MUSHROOM))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.NETHER_BRICKS)
         .define('N', Items.NETHER_BRICK)
         .pattern("NN")
         .pattern("NN")
         .unlockedBy("has_netherbrick", has(Items.NETHER_BRICK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.NETHER_WART_BLOCK)
         .define('#', Items.NETHER_WART)
         .pattern("###")
         .pattern("###")
         .pattern("###")
         .unlockedBy("has_nether_wart", has(Items.NETHER_WART))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.NOTE_BLOCK)
         .define('#', ItemTags.PLANKS)
         .define('X', Items.REDSTONE)
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .unlockedBy("has_redstone", has(Items.REDSTONE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.OBSERVER)
         .define('Q', Items.QUARTZ)
         .define('R', Items.REDSTONE)
         .define('#', Blocks.COBBLESTONE)
         .pattern("###")
         .pattern("RRQ")
         .pattern("###")
         .unlockedBy("has_quartz", has(Items.QUARTZ))
         .save(â˜ƒ);
      oneToOneConversionRecipe(â˜ƒ, Items.ORANGE_DYE, Blocks.ORANGE_TULIP, "orange_dye");
      ShapelessRecipeBuilder.shapeless(Items.ORANGE_DYE, 2)
         .requires(Items.RED_DYE)
         .requires(Items.YELLOW_DYE)
         .group("orange_dye")
         .unlockedBy("has_red_dye", has(Items.RED_DYE))
         .unlockedBy("has_yellow_dye", has(Items.YELLOW_DYE))
         .save(â˜ƒ, "orange_dye_from_red_yellow");
      ShapedRecipeBuilder.shaped(Items.PAINTING)
         .define('#', Items.STICK)
         .define('X', Ingredient.of(ItemTags.WOOL))
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .unlockedBy("has_wool", has(ItemTags.WOOL))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.PAPER, 3).define('#', Blocks.SUGAR_CANE).pattern("###").unlockedBy("has_reeds", has(Blocks.SUGAR_CANE)).save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.QUARTZ_PILLAR, 2)
         .define('#', Blocks.QUARTZ_BLOCK)
         .pattern("#")
         .pattern("#")
         .unlockedBy("has_chiseled_quartz_block", has(Blocks.CHISELED_QUARTZ_BLOCK))
         .unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
         .unlockedBy("has_quartz_pillar", has(Blocks.QUARTZ_PILLAR))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Blocks.PACKED_ICE).requires(Blocks.ICE, 9).unlockedBy("has_ice", has(Blocks.ICE)).save(â˜ƒ);
      oneToOneConversionRecipe(â˜ƒ, Items.PINK_DYE, Blocks.PEONY, "pink_dye", 2);
      oneToOneConversionRecipe(â˜ƒ, Items.PINK_DYE, Blocks.PINK_TULIP, "pink_dye");
      ShapelessRecipeBuilder.shapeless(Items.PINK_DYE, 2)
         .requires(Items.RED_DYE)
         .requires(Items.WHITE_DYE)
         .group("pink_dye")
         .unlockedBy("has_white_dye", has(Items.WHITE_DYE))
         .unlockedBy("has_red_dye", has(Items.RED_DYE))
         .save(â˜ƒ, "pink_dye_from_red_white_dye");
      ShapedRecipeBuilder.shaped(Blocks.PISTON)
         .define('R', Items.REDSTONE)
         .define('#', Blocks.COBBLESTONE)
         .define('T', ItemTags.PLANKS)
         .define('X', Items.IRON_INGOT)
         .pattern("TTT")
         .pattern("#X#")
         .pattern("#R#")
         .unlockedBy("has_redstone", has(Items.REDSTONE))
         .save(â˜ƒ);
      polished(â˜ƒ, Blocks.POLISHED_BASALT, Blocks.BASALT);
      ShapedRecipeBuilder.shaped(Blocks.PRISMARINE)
         .define('S', Items.PRISMARINE_SHARD)
         .pattern("SS")
         .pattern("SS")
         .unlockedBy("has_prismarine_shard", has(Items.PRISMARINE_SHARD))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.PRISMARINE_BRICKS)
         .define('S', Items.PRISMARINE_SHARD)
         .pattern("SSS")
         .pattern("SSS")
         .pattern("SSS")
         .unlockedBy("has_prismarine_shard", has(Items.PRISMARINE_SHARD))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.PUMPKIN_PIE)
         .requires(Blocks.PUMPKIN)
         .requires(Items.SUGAR)
         .requires(Items.EGG)
         .unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN))
         .unlockedBy("has_pumpkin", has(Blocks.PUMPKIN))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.PUMPKIN_SEEDS, 4).requires(Blocks.PUMPKIN).unlockedBy("has_pumpkin", has(Blocks.PUMPKIN)).save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE, 2)
         .requires(Items.BLUE_DYE)
         .requires(Items.RED_DYE)
         .unlockedBy("has_blue_dye", has(Items.BLUE_DYE))
         .unlockedBy("has_red_dye", has(Items.RED_DYE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.SHULKER_BOX)
         .define('#', Blocks.CHEST)
         .define('-', Items.SHULKER_SHELL)
         .pattern("-")
         .pattern("#")
         .pattern("-")
         .unlockedBy("has_shulker_shell", has(Items.SHULKER_SHELL))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.PURPUR_BLOCK, 4)
         .define('F', Items.POPPED_CHORUS_FRUIT)
         .pattern("FF")
         .pattern("FF")
         .unlockedBy("has_chorus_fruit_popped", has(Items.POPPED_CHORUS_FRUIT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.PURPUR_PILLAR)
         .define('#', Blocks.PURPUR_SLAB)
         .pattern("#")
         .pattern("#")
         .unlockedBy("has_purpur_block", has(Blocks.PURPUR_BLOCK))
         .save(â˜ƒ);
      slabBuilder(Blocks.PURPUR_SLAB, Ingredient.of(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR))
         .unlockedBy("has_purpur_block", has(Blocks.PURPUR_BLOCK))
         .save(â˜ƒ);
      stairBuilder(Blocks.PURPUR_STAIRS, Ingredient.of(Blocks.PURPUR_BLOCK, Blocks.PURPUR_PILLAR))
         .unlockedBy("has_purpur_block", has(Blocks.PURPUR_BLOCK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.QUARTZ_BLOCK)
         .define('#', Items.QUARTZ)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_quartz", has(Items.QUARTZ))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.QUARTZ_BRICKS, 4)
         .define('#', Blocks.QUARTZ_BLOCK)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
         .save(â˜ƒ);
      slabBuilder(Blocks.QUARTZ_SLAB, Ingredient.of(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR))
         .unlockedBy("has_chiseled_quartz_block", has(Blocks.CHISELED_QUARTZ_BLOCK))
         .unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
         .unlockedBy("has_quartz_pillar", has(Blocks.QUARTZ_PILLAR))
         .save(â˜ƒ);
      stairBuilder(Blocks.QUARTZ_STAIRS, Ingredient.of(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR))
         .unlockedBy("has_chiseled_quartz_block", has(Blocks.CHISELED_QUARTZ_BLOCK))
         .unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
         .unlockedBy("has_quartz_pillar", has(Blocks.QUARTZ_PILLAR))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.RABBIT_STEW)
         .requires(Items.BAKED_POTATO)
         .requires(Items.COOKED_RABBIT)
         .requires(Items.BOWL)
         .requires(Items.CARROT)
         .requires(Blocks.BROWN_MUSHROOM)
         .group("rabbit_stew")
         .unlockedBy("has_cooked_rabbit", has(Items.COOKED_RABBIT))
         .save(â˜ƒ, getConversionRecipeName(Items.RABBIT_STEW, Items.BROWN_MUSHROOM));
      ShapelessRecipeBuilder.shapeless(Items.RABBIT_STEW)
         .requires(Items.BAKED_POTATO)
         .requires(Items.COOKED_RABBIT)
         .requires(Items.BOWL)
         .requires(Items.CARROT)
         .requires(Blocks.RED_MUSHROOM)
         .group("rabbit_stew")
         .unlockedBy("has_cooked_rabbit", has(Items.COOKED_RABBIT))
         .save(â˜ƒ, getConversionRecipeName(Items.RABBIT_STEW, Items.RED_MUSHROOM));
      ShapedRecipeBuilder.shaped(Blocks.RAIL, 16)
         .define('#', Items.STICK)
         .define('X', Items.IRON_INGOT)
         .pattern("X X")
         .pattern("X#X")
         .pattern("X X")
         .unlockedBy("has_minecart", has(Items.MINECART))
         .save(â˜ƒ);
      nineBlockStorageRecipes(â˜ƒ, Items.REDSTONE, Items.REDSTONE_BLOCK);
      ShapedRecipeBuilder.shaped(Blocks.REDSTONE_LAMP)
         .define('R', Items.REDSTONE)
         .define('G', Blocks.GLOWSTONE)
         .pattern(" R ")
         .pattern("RGR")
         .pattern(" R ")
         .unlockedBy("has_glowstone", has(Blocks.GLOWSTONE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.REDSTONE_TORCH)
         .define('#', Items.STICK)
         .define('X', Items.REDSTONE)
         .pattern("X")
         .pattern("#")
         .unlockedBy("has_redstone", has(Items.REDSTONE))
         .save(â˜ƒ);
      oneToOneConversionRecipe(â˜ƒ, Items.RED_DYE, Items.BEETROOT, "red_dye");
      oneToOneConversionRecipe(â˜ƒ, Items.RED_DYE, Blocks.POPPY, "red_dye");
      oneToOneConversionRecipe(â˜ƒ, Items.RED_DYE, Blocks.ROSE_BUSH, "red_dye", 2);
      ShapelessRecipeBuilder.shapeless(Items.RED_DYE)
         .requires(Blocks.RED_TULIP)
         .group("red_dye")
         .unlockedBy("has_red_flower", has(Blocks.RED_TULIP))
         .save(â˜ƒ, "red_dye_from_tulip");
      ShapedRecipeBuilder.shaped(Blocks.RED_NETHER_BRICKS)
         .define('W', Items.NETHER_WART)
         .define('N', Items.NETHER_BRICK)
         .pattern("NW")
         .pattern("WN")
         .unlockedBy("has_nether_wart", has(Items.NETHER_WART))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.RED_SANDSTONE)
         .define('#', Blocks.RED_SAND)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_sand", has(Blocks.RED_SAND))
         .save(â˜ƒ);
      slabBuilder(Blocks.RED_SANDSTONE_SLAB, Ingredient.of(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE))
         .unlockedBy("has_red_sandstone", has(Blocks.RED_SANDSTONE))
         .unlockedBy("has_chiseled_red_sandstone", has(Blocks.CHISELED_RED_SANDSTONE))
         .save(â˜ƒ);
      stairBuilder(Blocks.RED_SANDSTONE_STAIRS, Ingredient.of(Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE))
         .unlockedBy("has_red_sandstone", has(Blocks.RED_SANDSTONE))
         .unlockedBy("has_chiseled_red_sandstone", has(Blocks.CHISELED_RED_SANDSTONE))
         .unlockedBy("has_cut_red_sandstone", has(Blocks.CUT_RED_SANDSTONE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.REPEATER)
         .define('#', Blocks.REDSTONE_TORCH)
         .define('X', Items.REDSTONE)
         .define('I', Blocks.STONE)
         .pattern("#X#")
         .pattern("III")
         .unlockedBy("has_redstone_torch", has(Blocks.REDSTONE_TORCH))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.SANDSTONE).define('#', Blocks.SAND).pattern("##").pattern("##").unlockedBy("has_sand", has(Blocks.SAND)).save(â˜ƒ);
      slabBuilder(Blocks.SANDSTONE_SLAB, Ingredient.of(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE))
         .unlockedBy("has_sandstone", has(Blocks.SANDSTONE))
         .unlockedBy("has_chiseled_sandstone", has(Blocks.CHISELED_SANDSTONE))
         .save(â˜ƒ);
      stairBuilder(Blocks.SANDSTONE_STAIRS, Ingredient.of(Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE))
         .unlockedBy("has_sandstone", has(Blocks.SANDSTONE))
         .unlockedBy("has_chiseled_sandstone", has(Blocks.CHISELED_SANDSTONE))
         .unlockedBy("has_cut_sandstone", has(Blocks.CUT_SANDSTONE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.SEA_LANTERN)
         .define('S', Items.PRISMARINE_SHARD)
         .define('C', Items.PRISMARINE_CRYSTALS)
         .pattern("SCS")
         .pattern("CCC")
         .pattern("SCS")
         .unlockedBy("has_prismarine_crystals", has(Items.PRISMARINE_CRYSTALS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.SHEARS)
         .define('#', Items.IRON_INGOT)
         .pattern(" #")
         .pattern("# ")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.SHIELD)
         .define('W', ItemTags.PLANKS)
         .define('o', Items.IRON_INGOT)
         .pattern("WoW")
         .pattern("WWW")
         .pattern(" W ")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      nineBlockStorageRecipes(â˜ƒ, Items.SLIME_BALL, Items.SLIME_BLOCK);
      cut(â˜ƒ, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE);
      cut(â˜ƒ, Blocks.CUT_SANDSTONE, Blocks.SANDSTONE);
      ShapedRecipeBuilder.shaped(Blocks.SNOW_BLOCK)
         .define('#', Items.SNOWBALL)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_snowball", has(Items.SNOWBALL))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.SNOW, 6).define('#', Blocks.SNOW_BLOCK).pattern("###").unlockedBy("has_snowball", has(Items.SNOWBALL)).save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.SOUL_CAMPFIRE)
         .define('L', ItemTags.LOGS)
         .define('S', Items.STICK)
         .define('#', ItemTags.SOUL_FIRE_BASE_BLOCKS)
         .pattern(" S ")
         .pattern("S#S")
         .pattern("LLL")
         .unlockedBy("has_stick", has(Items.STICK))
         .unlockedBy("has_soul_sand", has(ItemTags.SOUL_FIRE_BASE_BLOCKS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.GLISTERING_MELON_SLICE)
         .define('#', Items.GOLD_NUGGET)
         .define('X', Items.MELON_SLICE)
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .unlockedBy("has_melon", has(Items.MELON_SLICE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.SPECTRAL_ARROW, 2)
         .define('#', Items.GLOWSTONE_DUST)
         .define('X', Items.ARROW)
         .pattern(" # ")
         .pattern("#X#")
         .pattern(" # ")
         .unlockedBy("has_glowstone_dust", has(Items.GLOWSTONE_DUST))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.SPYGLASS)
         .define('#', Items.AMETHYST_SHARD)
         .define('X', Items.COPPER_INGOT)
         .pattern(" # ")
         .pattern(" X ")
         .pattern(" X ")
         .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.STICK, 4)
         .define('#', ItemTags.PLANKS)
         .pattern("#")
         .pattern("#")
         .group("sticks")
         .unlockedBy("has_planks", has(ItemTags.PLANKS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.STICK, 1)
         .define('#', Blocks.BAMBOO)
         .pattern("#")
         .pattern("#")
         .group("sticks")
         .unlockedBy("has_bamboo", has(Blocks.BAMBOO))
         .save(â˜ƒ, "stick_from_bamboo_item");
      ShapedRecipeBuilder.shaped(Blocks.STICKY_PISTON)
         .define('P', Blocks.PISTON)
         .define('S', Items.SLIME_BALL)
         .pattern("S")
         .pattern("P")
         .unlockedBy("has_slime_ball", has(Items.SLIME_BALL))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.STONE_BRICKS, 4)
         .define('#', Blocks.STONE)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_stone", has(Blocks.STONE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.STONE_AXE)
         .define('#', Items.STICK)
         .define('X', ItemTags.STONE_TOOL_MATERIALS)
         .pattern("XX")
         .pattern("X#")
         .pattern(" #")
         .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
         .save(â˜ƒ);
      slabBuilder(Blocks.STONE_BRICK_SLAB, Ingredient.of(Blocks.STONE_BRICKS)).unlockedBy("has_stone_bricks", has(ItemTags.STONE_BRICKS)).save(â˜ƒ);
      stairBuilder(Blocks.STONE_BRICK_STAIRS, Ingredient.of(Blocks.STONE_BRICKS)).unlockedBy("has_stone_bricks", has(ItemTags.STONE_BRICKS)).save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.STONE_HOE)
         .define('#', Items.STICK)
         .define('X', ItemTags.STONE_TOOL_MATERIALS)
         .pattern("XX")
         .pattern(" #")
         .pattern(" #")
         .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.STONE_PICKAXE)
         .define('#', Items.STICK)
         .define('X', ItemTags.STONE_TOOL_MATERIALS)
         .pattern("XXX")
         .pattern(" # ")
         .pattern(" # ")
         .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.STONE_SHOVEL)
         .define('#', Items.STICK)
         .define('X', ItemTags.STONE_TOOL_MATERIALS)
         .pattern("X")
         .pattern("#")
         .pattern("#")
         .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
         .save(â˜ƒ);
      slab(â˜ƒ, Blocks.SMOOTH_STONE_SLAB, Blocks.SMOOTH_STONE);
      ShapedRecipeBuilder.shaped(Items.STONE_SWORD)
         .define('#', Items.STICK)
         .define('X', ItemTags.STONE_TOOL_MATERIALS)
         .pattern("X")
         .pattern("X")
         .pattern("#")
         .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.WHITE_WOOL)
         .define('#', Items.STRING)
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_string", has(Items.STRING))
         .save(â˜ƒ, getConversionRecipeName(Blocks.WHITE_WOOL, Items.STRING));
      oneToOneConversionRecipe(â˜ƒ, Items.SUGAR, Blocks.SUGAR_CANE, "sugar");
      ShapelessRecipeBuilder.shapeless(Items.SUGAR, 3)
         .requires(Items.HONEY_BOTTLE)
         .group("sugar")
         .unlockedBy("has_honey_bottle", has(Items.HONEY_BOTTLE))
         .save(â˜ƒ, getConversionRecipeName(Items.SUGAR, Items.HONEY_BOTTLE));
      ShapedRecipeBuilder.shaped(Blocks.TARGET)
         .define('H', Items.HAY_BLOCK)
         .define('R', Items.REDSTONE)
         .pattern(" R ")
         .pattern("RHR")
         .pattern(" R ")
         .unlockedBy("has_redstone", has(Items.REDSTONE))
         .unlockedBy("has_hay_block", has(Blocks.HAY_BLOCK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.TNT)
         .define('#', Ingredient.of(Blocks.SAND, Blocks.RED_SAND))
         .define('X', Items.GUNPOWDER)
         .pattern("X#X")
         .pattern("#X#")
         .pattern("X#X")
         .unlockedBy("has_gunpowder", has(Items.GUNPOWDER))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.TNT_MINECART)
         .define('A', Blocks.TNT)
         .define('B', Items.MINECART)
         .pattern("A")
         .pattern("B")
         .unlockedBy("has_minecart", has(Items.MINECART))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.TORCH, 4)
         .define('#', Items.STICK)
         .define('X', Ingredient.of(Items.COAL, Items.CHARCOAL))
         .pattern("X")
         .pattern("#")
         .unlockedBy("has_stone_pickaxe", has(Items.STONE_PICKAXE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.SOUL_TORCH, 4)
         .define('X', Ingredient.of(Items.COAL, Items.CHARCOAL))
         .define('#', Items.STICK)
         .define('S', ItemTags.SOUL_FIRE_BASE_BLOCKS)
         .pattern("X")
         .pattern("#")
         .pattern("S")
         .unlockedBy("has_soul_sand", has(ItemTags.SOUL_FIRE_BASE_BLOCKS))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.LANTERN)
         .define('#', Items.TORCH)
         .define('X', Items.IRON_NUGGET)
         .pattern("XXX")
         .pattern("X#X")
         .pattern("XXX")
         .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET))
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.SOUL_LANTERN)
         .define('#', Items.SOUL_TORCH)
         .define('X', Items.IRON_NUGGET)
         .pattern("XXX")
         .pattern("X#X")
         .pattern("XXX")
         .unlockedBy("has_soul_torch", has(Items.SOUL_TORCH))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Blocks.TRAPPED_CHEST)
         .requires(Blocks.CHEST)
         .requires(Blocks.TRIPWIRE_HOOK)
         .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.TRIPWIRE_HOOK, 2)
         .define('#', ItemTags.PLANKS)
         .define('S', Items.STICK)
         .define('I', Items.IRON_INGOT)
         .pattern("I")
         .pattern("S")
         .pattern("#")
         .unlockedBy("has_string", has(Items.STRING))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.TURTLE_HELMET)
         .define('X', Items.SCUTE)
         .pattern("XXX")
         .pattern("X X")
         .unlockedBy("has_scute", has(Items.SCUTE))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.WHEAT, 9).requires(Blocks.HAY_BLOCK).unlockedBy("has_hay_block", has(Blocks.HAY_BLOCK)).save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.WHITE_DYE)
         .requires(Items.BONE_MEAL)
         .group("white_dye")
         .unlockedBy("has_bone_meal", has(Items.BONE_MEAL))
         .save(â˜ƒ);
      oneToOneConversionRecipe(â˜ƒ, Items.WHITE_DYE, Blocks.LILY_OF_THE_VALLEY, "white_dye");
      ShapedRecipeBuilder.shaped(Items.WOODEN_AXE)
         .define('#', Items.STICK)
         .define('X', ItemTags.PLANKS)
         .pattern("XX")
         .pattern("X#")
         .pattern(" #")
         .unlockedBy("has_stick", has(Items.STICK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.WOODEN_HOE)
         .define('#', Items.STICK)
         .define('X', ItemTags.PLANKS)
         .pattern("XX")
         .pattern(" #")
         .pattern(" #")
         .unlockedBy("has_stick", has(Items.STICK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.WOODEN_PICKAXE)
         .define('#', Items.STICK)
         .define('X', ItemTags.PLANKS)
         .pattern("XXX")
         .pattern(" # ")
         .pattern(" # ")
         .unlockedBy("has_stick", has(Items.STICK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.WOODEN_SHOVEL)
         .define('#', Items.STICK)
         .define('X', ItemTags.PLANKS)
         .pattern("X")
         .pattern("#")
         .pattern("#")
         .unlockedBy("has_stick", has(Items.STICK))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Items.WOODEN_SWORD)
         .define('#', Items.STICK)
         .define('X', ItemTags.PLANKS)
         .pattern("X")
         .pattern("X")
         .pattern("#")
         .unlockedBy("has_stick", has(Items.STICK))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.WRITABLE_BOOK)
         .requires(Items.BOOK)
         .requires(Items.INK_SAC)
         .requires(Items.FEATHER)
         .unlockedBy("has_book", has(Items.BOOK))
         .save(â˜ƒ);
      oneToOneConversionRecipe(â˜ƒ, Items.YELLOW_DYE, Blocks.DANDELION, "yellow_dye");
      oneToOneConversionRecipe(â˜ƒ, Items.YELLOW_DYE, Blocks.SUNFLOWER, "yellow_dye", 2);
      nineBlockStorageRecipes(â˜ƒ, Items.DRIED_KELP, Items.DRIED_KELP_BLOCK);
      ShapedRecipeBuilder.shaped(Blocks.CONDUIT)
         .define('#', Items.NAUTILUS_SHELL)
         .define('X', Items.HEART_OF_THE_SEA)
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .unlockedBy("has_nautilus_core", has(Items.HEART_OF_THE_SEA))
         .unlockedBy("has_nautilus_shell", has(Items.NAUTILUS_SHELL))
         .save(â˜ƒ);
      wall(â˜ƒ, Blocks.RED_SANDSTONE_WALL, Blocks.RED_SANDSTONE);
      wall(â˜ƒ, Blocks.STONE_BRICK_WALL, Blocks.STONE_BRICKS);
      wall(â˜ƒ, Blocks.SANDSTONE_WALL, Blocks.SANDSTONE);
      ShapelessRecipeBuilder.shapeless(Items.CREEPER_BANNER_PATTERN)
         .requires(Items.PAPER)
         .requires(Items.CREEPER_HEAD)
         .unlockedBy("has_creeper_head", has(Items.CREEPER_HEAD))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.SKULL_BANNER_PATTERN)
         .requires(Items.PAPER)
         .requires(Items.WITHER_SKELETON_SKULL)
         .unlockedBy("has_wither_skeleton_skull", has(Items.WITHER_SKELETON_SKULL))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.FLOWER_BANNER_PATTERN)
         .requires(Items.PAPER)
         .requires(Blocks.OXEYE_DAISY)
         .unlockedBy("has_oxeye_daisy", has(Blocks.OXEYE_DAISY))
         .save(â˜ƒ);
      ShapelessRecipeBuilder.shapeless(Items.MOJANG_BANNER_PATTERN)
         .requires(Items.PAPER)
         .requires(Items.ENCHANTED_GOLDEN_APPLE)
         .unlockedBy("has_enchanted_golden_apple", has(Items.ENCHANTED_GOLDEN_APPLE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.SCAFFOLDING, 6)
         .define('~', Items.STRING)
         .define('I', Blocks.BAMBOO)
         .pattern("I~I")
         .pattern("I I")
         .pattern("I I")
         .unlockedBy("has_bamboo", has(Blocks.BAMBOO))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.GRINDSTONE)
         .define('I', Items.STICK)
         .define('-', Blocks.STONE_SLAB)
         .define('#', ItemTags.PLANKS)
         .pattern("I-I")
         .pattern("# #")
         .unlockedBy("has_stone_slab", has(Blocks.STONE_SLAB))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.BLAST_FURNACE)
         .define('#', Blocks.SMOOTH_STONE)
         .define('X', Blocks.FURNACE)
         .define('I', Items.IRON_INGOT)
         .pattern("III")
         .pattern("IXI")
         .pattern("###")
         .unlockedBy("has_smooth_stone", has(Blocks.SMOOTH_STONE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.SMOKER)
         .define('#', ItemTags.LOGS)
         .define('X', Blocks.FURNACE)
         .pattern(" # ")
         .pattern("#X#")
         .pattern(" # ")
         .unlockedBy("has_furnace", has(Blocks.FURNACE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.CARTOGRAPHY_TABLE)
         .define('#', ItemTags.PLANKS)
         .define('@', Items.PAPER)
         .pattern("@@")
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_paper", has(Items.PAPER))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.SMITHING_TABLE)
         .define('#', ItemTags.PLANKS)
         .define('@', Items.IRON_INGOT)
         .pattern("@@")
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.FLETCHING_TABLE)
         .define('#', ItemTags.PLANKS)
         .define('@', Items.FLINT)
         .pattern("@@")
         .pattern("##")
         .pattern("##")
         .unlockedBy("has_flint", has(Items.FLINT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.STONECUTTER)
         .define('I', Items.IRON_INGOT)
         .define('#', Blocks.STONE)
         .pattern(" I ")
         .pattern("###")
         .unlockedBy("has_stone", has(Blocks.STONE))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.LODESTONE)
         .define('S', Items.CHISELED_STONE_BRICKS)
         .define('#', Items.NETHERITE_INGOT)
         .pattern("SSS")
         .pattern("S#S")
         .pattern("SSS")
         .unlockedBy("has_netherite_ingot", has(Items.NETHERITE_INGOT))
         .save(â˜ƒ);
      nineBlockStorageRecipesRecipesWithCustomUnpacking(
         â˜ƒ, Items.NETHERITE_INGOT, Items.NETHERITE_BLOCK, "netherite_ingot_from_netherite_block", "netherite_ingot"
      );
      ShapelessRecipeBuilder.shapeless(Items.NETHERITE_INGOT)
         .requires(Items.NETHERITE_SCRAP, 4)
         .requires(Items.GOLD_INGOT, 4)
         .group("netherite_ingot")
         .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.RESPAWN_ANCHOR)
         .define('O', Blocks.CRYING_OBSIDIAN)
         .define('G', Blocks.GLOWSTONE)
         .pattern("OOO")
         .pattern("GGG")
         .pattern("OOO")
         .unlockedBy("has_obsidian", has(Blocks.CRYING_OBSIDIAN))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.CHAIN)
         .define('I', Items.IRON_INGOT)
         .define('N', Items.IRON_NUGGET)
         .pattern("N")
         .pattern("I")
         .pattern("N")
         .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET))
         .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.TINTED_GLASS, 2)
         .define('G', Blocks.GLASS)
         .define('S', Items.AMETHYST_SHARD)
         .pattern(" S ")
         .pattern("SGS")
         .pattern(" S ")
         .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
         .save(â˜ƒ);
      ShapedRecipeBuilder.shaped(Blocks.AMETHYST_BLOCK)
         .define('S', Items.AMETHYST_SHARD)
         .pattern("SS")
         .pattern("SS")
         .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
         .save(â˜ƒ);
      SpecialRecipeBuilder.special(RecipeSerializer.ARMOR_DYE).save(â˜ƒ, "armor_dye");
      SpecialRecipeBuilder.special(RecipeSerializer.BANNER_DUPLICATE).save(â˜ƒ, "banner_duplicate");
      SpecialRecipeBuilder.special(RecipeSerializer.BOOK_CLONING).save(â˜ƒ, "book_cloning");
      SpecialRecipeBuilder.special(RecipeSerializer.FIREWORK_ROCKET).save(â˜ƒ, "firework_rocket");
      SpecialRecipeBuilder.special(RecipeSerializer.FIREWORK_STAR).save(â˜ƒ, "firework_star");
      SpecialRecipeBuilder.special(RecipeSerializer.FIREWORK_STAR_FADE).save(â˜ƒ, "firework_star_fade");
      SpecialRecipeBuilder.special(RecipeSerializer.MAP_CLONING).save(â˜ƒ, "map_cloning");
      SpecialRecipeBuilder.special(RecipeSerializer.MAP_EXTENDING).save(â˜ƒ, "map_extending");
      SpecialRecipeBuilder.special(RecipeSerializer.REPAIR_ITEM).save(â˜ƒ, "repair_item");
      SpecialRecipeBuilder.special(RecipeSerializer.SHIELD_DECORATION).save(â˜ƒ, "shield_decoration");
      SpecialRecipeBuilder.special(RecipeSerializer.SHULKER_BOX_COLORING).save(â˜ƒ, "shulker_box_coloring");
      SpecialRecipeBuilder.special(RecipeSerializer.TIPPED_ARROW).save(â˜ƒ, "tipped_arrow");
      SpecialRecipeBuilder.special(RecipeSerializer.SUSPICIOUS_STEW).save(â˜ƒ, "suspicious_stew");
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.POTATO), Items.BAKED_POTATO, 0.35F, 200).unlockedBy("has_potato", has(Items.POTATO)).save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.CLAY_BALL), Items.BRICK, 0.3F, 200).unlockedBy("has_clay_ball", has(Items.CLAY_BALL)).save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.LOGS_THAT_BURN), Items.CHARCOAL, 0.15F, 200)
         .unlockedBy("has_log", has(ItemTags.LOGS_THAT_BURN))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.CHORUS_FRUIT), Items.POPPED_CHORUS_FRUIT, 0.1F, 200)
         .unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BEEF), Items.COOKED_BEEF, 0.35F, 200).unlockedBy("has_beef", has(Items.BEEF)).save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.CHICKEN), Items.COOKED_CHICKEN, 0.35F, 200)
         .unlockedBy("has_chicken", has(Items.CHICKEN))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.COD), Items.COOKED_COD, 0.35F, 200).unlockedBy("has_cod", has(Items.COD)).save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.KELP), Items.DRIED_KELP, 0.1F, 200)
         .unlockedBy("has_kelp", has(Blocks.KELP))
         .save(â˜ƒ, getSmeltingRecipeName(Items.DRIED_KELP));
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.SALMON), Items.COOKED_SALMON, 0.35F, 200).unlockedBy("has_salmon", has(Items.SALMON)).save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.MUTTON), Items.COOKED_MUTTON, 0.35F, 200).unlockedBy("has_mutton", has(Items.MUTTON)).save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.PORKCHOP), Items.COOKED_PORKCHOP, 0.35F, 200)
         .unlockedBy("has_porkchop", has(Items.PORKCHOP))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.RABBIT), Items.COOKED_RABBIT, 0.35F, 200).unlockedBy("has_rabbit", has(Items.RABBIT)).save(â˜ƒ);
      oreSmelting(â˜ƒ, COAL_SMELTABLES, Items.COAL, 0.1F, 200, "coal");
      oreSmelting(â˜ƒ, IRON_SMELTABLES, Items.IRON_INGOT, 0.7F, 200, "iron_ingot");
      oreSmelting(â˜ƒ, COPPER_SMELTABLES, Items.COPPER_INGOT, 0.7F, 200, "copper_ingot");
      oreSmelting(â˜ƒ, GOLD_SMELTABLES, Items.GOLD_INGOT, 1.0F, 200, "gold_ingot");
      oreSmelting(â˜ƒ, DIAMOND_SMELTABLES, Items.DIAMOND, 1.0F, 200, "diamond");
      oreSmelting(â˜ƒ, LAPIS_SMELTABLES, Items.LAPIS_LAZULI, 0.2F, 200, "lapis_lazuli");
      oreSmelting(â˜ƒ, REDSTONE_SMELTABLES, Items.REDSTONE, 0.7F, 200, "redstone");
      oreSmelting(â˜ƒ, EMERALD_SMELTABLES, Items.EMERALD, 1.0F, 200, "emerald");
      nineBlockStorageRecipes(â˜ƒ, Items.RAW_IRON, Items.RAW_IRON_BLOCK);
      nineBlockStorageRecipes(â˜ƒ, Items.RAW_COPPER, Items.RAW_COPPER_BLOCK);
      nineBlockStorageRecipes(â˜ƒ, Items.RAW_GOLD, Items.RAW_GOLD_BLOCK);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.SAND), Blocks.GLASS.asItem(), 0.1F, 200).unlockedBy("has_sand", has(ItemTags.SAND)).save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.SEA_PICKLE), Items.LIME_DYE, 0.1F, 200)
         .unlockedBy("has_sea_pickle", has(Blocks.SEA_PICKLE))
         .save(â˜ƒ, getSmeltingRecipeName(Items.LIME_DYE));
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.CACTUS.asItem()), Items.GREEN_DYE, 1.0F, 200)
         .unlockedBy("has_cactus", has(Blocks.CACTUS))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(
               Items.GOLDEN_PICKAXE,
               Items.GOLDEN_SHOVEL,
               Items.GOLDEN_AXE,
               Items.GOLDEN_HOE,
               Items.GOLDEN_SWORD,
               Items.GOLDEN_HELMET,
               Items.GOLDEN_CHESTPLATE,
               Items.GOLDEN_LEGGINGS,
               Items.GOLDEN_BOOTS,
               Items.GOLDEN_HORSE_ARMOR
            ),
            Items.GOLD_NUGGET,
            0.1F,
            200
         )
         .unlockedBy("has_golden_pickaxe", has(Items.GOLDEN_PICKAXE))
         .unlockedBy("has_golden_shovel", has(Items.GOLDEN_SHOVEL))
         .unlockedBy("has_golden_axe", has(Items.GOLDEN_AXE))
         .unlockedBy("has_golden_hoe", has(Items.GOLDEN_HOE))
         .unlockedBy("has_golden_sword", has(Items.GOLDEN_SWORD))
         .unlockedBy("has_golden_helmet", has(Items.GOLDEN_HELMET))
         .unlockedBy("has_golden_chestplate", has(Items.GOLDEN_CHESTPLATE))
         .unlockedBy("has_golden_leggings", has(Items.GOLDEN_LEGGINGS))
         .unlockedBy("has_golden_boots", has(Items.GOLDEN_BOOTS))
         .unlockedBy("has_golden_horse_armor", has(Items.GOLDEN_HORSE_ARMOR))
         .save(â˜ƒ, getSmeltingRecipeName(Items.GOLD_NUGGET));
      SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(
               Items.IRON_PICKAXE,
               Items.IRON_SHOVEL,
               Items.IRON_AXE,
               Items.IRON_HOE,
               Items.IRON_SWORD,
               Items.IRON_HELMET,
               Items.IRON_CHESTPLATE,
               Items.IRON_LEGGINGS,
               Items.IRON_BOOTS,
               Items.IRON_HORSE_ARMOR,
               Items.CHAINMAIL_HELMET,
               Items.CHAINMAIL_CHESTPLATE,
               Items.CHAINMAIL_LEGGINGS,
               Items.CHAINMAIL_BOOTS
            ),
            Items.IRON_NUGGET,
            0.1F,
            200
         )
         .unlockedBy("has_iron_pickaxe", has(Items.IRON_PICKAXE))
         .unlockedBy("has_iron_shovel", has(Items.IRON_SHOVEL))
         .unlockedBy("has_iron_axe", has(Items.IRON_AXE))
         .unlockedBy("has_iron_hoe", has(Items.IRON_HOE))
         .unlockedBy("has_iron_sword", has(Items.IRON_SWORD))
         .unlockedBy("has_iron_helmet", has(Items.IRON_HELMET))
         .unlockedBy("has_iron_chestplate", has(Items.IRON_CHESTPLATE))
         .unlockedBy("has_iron_leggings", has(Items.IRON_LEGGINGS))
         .unlockedBy("has_iron_boots", has(Items.IRON_BOOTS))
         .unlockedBy("has_iron_horse_armor", has(Items.IRON_HORSE_ARMOR))
         .unlockedBy("has_chainmail_helmet", has(Items.CHAINMAIL_HELMET))
         .unlockedBy("has_chainmail_chestplate", has(Items.CHAINMAIL_CHESTPLATE))
         .unlockedBy("has_chainmail_leggings", has(Items.CHAINMAIL_LEGGINGS))
         .unlockedBy("has_chainmail_boots", has(Items.CHAINMAIL_BOOTS))
         .save(â˜ƒ, getSmeltingRecipeName(Items.IRON_NUGGET));
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.CLAY), Blocks.TERRACOTTA.asItem(), 0.35F, 200)
         .unlockedBy("has_clay_block", has(Blocks.CLAY))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.NETHERRACK), Items.NETHER_BRICK, 0.1F, 200)
         .unlockedBy("has_netherrack", has(Blocks.NETHERRACK))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.NETHER_QUARTZ_ORE), Items.QUARTZ, 0.2F, 200)
         .unlockedBy("has_nether_quartz_ore", has(Blocks.NETHER_QUARTZ_ORE))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.WET_SPONGE), Blocks.SPONGE.asItem(), 0.15F, 200)
         .unlockedBy("has_wet_sponge", has(Blocks.WET_SPONGE))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.COBBLESTONE), Blocks.STONE.asItem(), 0.1F, 200)
         .unlockedBy("has_cobblestone", has(Blocks.COBBLESTONE))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.STONE), Blocks.SMOOTH_STONE.asItem(), 0.1F, 200)
         .unlockedBy("has_stone", has(Blocks.STONE))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.SANDSTONE), Blocks.SMOOTH_SANDSTONE.asItem(), 0.1F, 200)
         .unlockedBy("has_sandstone", has(Blocks.SANDSTONE))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.RED_SANDSTONE), Blocks.SMOOTH_RED_SANDSTONE.asItem(), 0.1F, 200)
         .unlockedBy("has_red_sandstone", has(Blocks.RED_SANDSTONE))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.QUARTZ_BLOCK), Blocks.SMOOTH_QUARTZ.asItem(), 0.1F, 200)
         .unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.STONE_BRICKS), Blocks.CRACKED_STONE_BRICKS.asItem(), 0.1F, 200)
         .unlockedBy("has_stone_bricks", has(Blocks.STONE_BRICKS))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.BLACK_TERRACOTTA), Blocks.BLACK_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_black_terracotta", has(Blocks.BLACK_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.BLUE_TERRACOTTA), Blocks.BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_blue_terracotta", has(Blocks.BLUE_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.BROWN_TERRACOTTA), Blocks.BROWN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_brown_terracotta", has(Blocks.BROWN_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.CYAN_TERRACOTTA), Blocks.CYAN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_cyan_terracotta", has(Blocks.CYAN_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.GRAY_TERRACOTTA), Blocks.GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_gray_terracotta", has(Blocks.GRAY_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.GREEN_TERRACOTTA), Blocks.GREEN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_green_terracotta", has(Blocks.GREEN_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.LIGHT_BLUE_TERRACOTTA), Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_light_blue_terracotta", has(Blocks.LIGHT_BLUE_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.LIGHT_GRAY_TERRACOTTA), Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_light_gray_terracotta", has(Blocks.LIGHT_GRAY_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.LIME_TERRACOTTA), Blocks.LIME_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_lime_terracotta", has(Blocks.LIME_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.MAGENTA_TERRACOTTA), Blocks.MAGENTA_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_magenta_terracotta", has(Blocks.MAGENTA_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.ORANGE_TERRACOTTA), Blocks.ORANGE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_orange_terracotta", has(Blocks.ORANGE_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.PINK_TERRACOTTA), Blocks.PINK_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_pink_terracotta", has(Blocks.PINK_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.PURPLE_TERRACOTTA), Blocks.PURPLE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_purple_terracotta", has(Blocks.PURPLE_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.RED_TERRACOTTA), Blocks.RED_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_red_terracotta", has(Blocks.RED_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.WHITE_TERRACOTTA), Blocks.WHITE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_white_terracotta", has(Blocks.WHITE_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.YELLOW_TERRACOTTA), Blocks.YELLOW_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
         .unlockedBy("has_yellow_terracotta", has(Blocks.YELLOW_TERRACOTTA))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.ANCIENT_DEBRIS), Items.NETHERITE_SCRAP, 2.0F, 200)
         .unlockedBy("has_ancient_debris", has(Blocks.ANCIENT_DEBRIS))
         .save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.BASALT), Blocks.SMOOTH_BASALT, 0.1F, 200).unlockedBy("has_basalt", has(Blocks.BASALT)).save(â˜ƒ);
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(Blocks.COBBLED_DEEPSLATE), Blocks.DEEPSLATE, 0.1F, 200)
         .unlockedBy("has_cobbled_deepslate", has(Blocks.COBBLED_DEEPSLATE))
         .save(â˜ƒ);
      oreBlasting(â˜ƒ, COAL_SMELTABLES, Items.COAL, 0.1F, 100, "coal");
      oreBlasting(â˜ƒ, IRON_SMELTABLES, Items.IRON_INGOT, 0.7F, 100, "iron_ingot");
      oreBlasting(â˜ƒ, COPPER_SMELTABLES, Items.COPPER_INGOT, 0.7F, 100, "copper_ingot");
      oreBlasting(â˜ƒ, GOLD_SMELTABLES, Items.GOLD_INGOT, 1.0F, 100, "gold_ingot");
      oreBlasting(â˜ƒ, DIAMOND_SMELTABLES, Items.DIAMOND, 1.0F, 100, "diamond");
      oreBlasting(â˜ƒ, LAPIS_SMELTABLES, Items.LAPIS_LAZULI, 0.2F, 100, "lapis_lazuli");
      oreBlasting(â˜ƒ, REDSTONE_SMELTABLES, Items.REDSTONE, 0.7F, 100, "redstone");
      oreBlasting(â˜ƒ, EMERALD_SMELTABLES, Items.EMERALD, 1.0F, 100, "emerald");
      SimpleCookingRecipeBuilder.blasting(Ingredient.of(Blocks.NETHER_QUARTZ_ORE), Items.QUARTZ, 0.2F, 100)
         .unlockedBy("has_nether_quartz_ore", has(Blocks.NETHER_QUARTZ_ORE))
         .save(â˜ƒ, getBlastingRecipeName(Items.QUARTZ));
      SimpleCookingRecipeBuilder.blasting(
            Ingredient.of(
               Items.GOLDEN_PICKAXE,
               Items.GOLDEN_SHOVEL,
               Items.GOLDEN_AXE,
               Items.GOLDEN_HOE,
               Items.GOLDEN_SWORD,
               Items.GOLDEN_HELMET,
               Items.GOLDEN_CHESTPLATE,
               Items.GOLDEN_LEGGINGS,
               Items.GOLDEN_BOOTS,
               Items.GOLDEN_HORSE_ARMOR
            ),
            Items.GOLD_NUGGET,
            0.1F,
            100
         )
         .unlockedBy("has_golden_pickaxe", has(Items.GOLDEN_PICKAXE))
         .unlockedBy("has_golden_shovel", has(Items.GOLDEN_SHOVEL))
         .unlockedBy("has_golden_axe", has(Items.GOLDEN_AXE))
         .unlockedBy("has_golden_hoe", has(Items.GOLDEN_HOE))
         .unlockedBy("has_golden_sword", has(Items.GOLDEN_SWORD))
         .unlockedBy("has_golden_helmet", has(Items.GOLDEN_HELMET))
         .unlockedBy("has_golden_chestplate", has(Items.GOLDEN_CHESTPLATE))
         .unlockedBy("has_golden_leggings", has(Items.GOLDEN_LEGGINGS))
         .unlockedBy("has_golden_boots", has(Items.GOLDEN_BOOTS))
         .unlockedBy("has_golden_horse_armor", has(Items.GOLDEN_HORSE_ARMOR))
         .save(â˜ƒ, getBlastingRecipeName(Items.GOLD_NUGGET));
      SimpleCookingRecipeBuilder.blasting(
            Ingredient.of(
               Items.IRON_PICKAXE,
               Items.IRON_SHOVEL,
               Items.IRON_AXE,
               Items.IRON_HOE,
               Items.IRON_SWORD,
               Items.IRON_HELMET,
               Items.IRON_CHESTPLATE,
               Items.IRON_LEGGINGS,
               Items.IRON_BOOTS,
               Items.IRON_HORSE_ARMOR,
               Items.CHAINMAIL_HELMET,
               Items.CHAINMAIL_CHESTPLATE,
               Items.CHAINMAIL_LEGGINGS,
               Items.CHAINMAIL_BOOTS
            ),
            Items.IRON_NUGGET,
            0.1F,
            100
         )
         .unlockedBy("has_iron_pickaxe", has(Items.IRON_PICKAXE))
         .unlockedBy("has_iron_shovel", has(Items.IRON_SHOVEL))
         .unlockedBy("has_iron_axe", has(Items.IRON_AXE))
         .unlockedBy("has_iron_hoe", has(Items.IRON_HOE))
         .unlockedBy("has_iron_sword", has(Items.IRON_SWORD))
         .unlockedBy("has_iron_helmet", has(Items.IRON_HELMET))
         .unlockedBy("has_iron_chestplate", has(Items.IRON_CHESTPLATE))
         .unlockedBy("has_iron_leggings", has(Items.IRON_LEGGINGS))
         .unlockedBy("has_iron_boots", has(Items.IRON_BOOTS))
         .unlockedBy("has_iron_horse_armor", has(Items.IRON_HORSE_ARMOR))
         .unlockedBy("has_chainmail_helmet", has(Items.CHAINMAIL_HELMET))
         .unlockedBy("has_chainmail_chestplate", has(Items.CHAINMAIL_CHESTPLATE))
         .unlockedBy("has_chainmail_leggings", has(Items.CHAINMAIL_LEGGINGS))
         .unlockedBy("has_chainmail_boots", has(Items.CHAINMAIL_BOOTS))
         .save(â˜ƒ, getBlastingRecipeName(Items.IRON_NUGGET));
      SimpleCookingRecipeBuilder.blasting(Ingredient.of(Blocks.ANCIENT_DEBRIS), Items.NETHERITE_SCRAP, 2.0F, 100)
         .unlockedBy("has_ancient_debris", has(Blocks.ANCIENT_DEBRIS))
         .save(â˜ƒ, getBlastingRecipeName(Items.NETHERITE_SCRAP));
      cookRecipes(â˜ƒ, "smoking", RecipeSerializer.SMOKING_RECIPE, 100);
      cookRecipes(â˜ƒ, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, 600);
      stonecutterResultFromBase(â˜ƒ, Blocks.STONE_SLAB, Blocks.STONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.STONE_STAIRS, Blocks.STONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.STONE_BRICKS, Blocks.STONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.STONE_BRICK_SLAB, Blocks.STONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.STONE_BRICK_STAIRS, Blocks.STONE);
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.STONE), Blocks.CHISELED_STONE_BRICKS)
         .unlockedBy("has_stone", has(Blocks.STONE))
         .save(â˜ƒ, "chiseled_stone_bricks_stone_from_stonecutting");
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.STONE), Blocks.STONE_BRICK_WALL)
         .unlockedBy("has_stone", has(Blocks.STONE))
         .save(â˜ƒ, "stone_brick_walls_from_stone_stonecutting");
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_SANDSTONE, Blocks.SANDSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.SANDSTONE_SLAB, Blocks.SANDSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_SANDSTONE_SLAB, Blocks.SANDSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_SANDSTONE_SLAB, Blocks.CUT_SANDSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.SANDSTONE_WALL, Blocks.SANDSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.RED_SANDSTONE_SLAB, Blocks.RED_SANDSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.RED_SANDSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.CUT_RED_SANDSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.RED_SANDSTONE_WALL, Blocks.RED_SANDSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.CHISELED_RED_SANDSTONE, Blocks.RED_SANDSTONE);
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.QUARTZ_BLOCK), Blocks.QUARTZ_SLAB, 2)
         .unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK))
         .save(â˜ƒ, "quartz_slab_from_stonecutting");
      stonecutterResultFromBase(â˜ƒ, Blocks.QUARTZ_STAIRS, Blocks.QUARTZ_BLOCK);
      stonecutterResultFromBase(â˜ƒ, Blocks.QUARTZ_PILLAR, Blocks.QUARTZ_BLOCK);
      stonecutterResultFromBase(â˜ƒ, Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK);
      stonecutterResultFromBase(â˜ƒ, Blocks.QUARTZ_BRICKS, Blocks.QUARTZ_BLOCK);
      stonecutterResultFromBase(â˜ƒ, Blocks.COBBLESTONE_STAIRS, Blocks.COBBLESTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.COBBLESTONE_WALL, Blocks.COBBLESTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.STONE_BRICK_SLAB, Blocks.STONE_BRICKS, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.STONE_BRICK_STAIRS, Blocks.STONE_BRICKS);
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.STONE_BRICKS), Blocks.STONE_BRICK_WALL)
         .unlockedBy("has_stone_bricks", has(Blocks.STONE_BRICKS))
         .save(â˜ƒ, "stone_brick_wall_from_stone_bricks_stonecutting");
      stonecutterResultFromBase(â˜ƒ, Blocks.CHISELED_STONE_BRICKS, Blocks.STONE_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.BRICK_SLAB, Blocks.BRICKS, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.BRICK_STAIRS, Blocks.BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.BRICK_WALL, Blocks.BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.NETHER_BRICK_SLAB, Blocks.NETHER_BRICKS, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.NETHER_BRICK_WALL, Blocks.NETHER_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.CHISELED_NETHER_BRICKS, Blocks.NETHER_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.RED_NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICKS, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.RED_NETHER_BRICK_STAIRS, Blocks.RED_NETHER_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.RED_NETHER_BRICK_WALL, Blocks.RED_NETHER_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.PURPUR_SLAB, Blocks.PURPUR_BLOCK, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.PURPUR_STAIRS, Blocks.PURPUR_BLOCK);
      stonecutterResultFromBase(â˜ƒ, Blocks.PURPUR_PILLAR, Blocks.PURPUR_BLOCK);
      stonecutterResultFromBase(â˜ƒ, Blocks.PRISMARINE_SLAB, Blocks.PRISMARINE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.PRISMARINE_STAIRS, Blocks.PRISMARINE);
      stonecutterResultFromBase(â˜ƒ, Blocks.PRISMARINE_WALL, Blocks.PRISMARINE);
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.PRISMARINE_BRICKS), Blocks.PRISMARINE_BRICK_SLAB, 2)
         .unlockedBy("has_prismarine_brick", has(Blocks.PRISMARINE_BRICKS))
         .save(â˜ƒ, "prismarine_brick_slab_from_prismarine_stonecutting");
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.PRISMARINE_BRICKS), Blocks.PRISMARINE_BRICK_STAIRS)
         .unlockedBy("has_prismarine_brick", has(Blocks.PRISMARINE_BRICKS))
         .save(â˜ƒ, "prismarine_brick_stairs_from_prismarine_stonecutting");
      stonecutterResultFromBase(â˜ƒ, Blocks.DARK_PRISMARINE_SLAB, Blocks.DARK_PRISMARINE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.DARK_PRISMARINE_STAIRS, Blocks.DARK_PRISMARINE);
      stonecutterResultFromBase(â˜ƒ, Blocks.ANDESITE_SLAB, Blocks.ANDESITE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.ANDESITE_STAIRS, Blocks.ANDESITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.ANDESITE_WALL, Blocks.ANDESITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_ANDESITE_SLAB, Blocks.ANDESITE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_ANDESITE_STAIRS, Blocks.ANDESITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_ANDESITE_SLAB, Blocks.POLISHED_ANDESITE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_ANDESITE_STAIRS, Blocks.POLISHED_ANDESITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BASALT, Blocks.BASALT);
      stonecutterResultFromBase(â˜ƒ, Blocks.GRANITE_SLAB, Blocks.GRANITE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.GRANITE_STAIRS, Blocks.GRANITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.GRANITE_WALL, Blocks.GRANITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_GRANITE, Blocks.GRANITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_GRANITE_SLAB, Blocks.GRANITE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_GRANITE_STAIRS, Blocks.GRANITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_GRANITE_SLAB, Blocks.POLISHED_GRANITE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_GRANITE_STAIRS, Blocks.POLISHED_GRANITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DIORITE_SLAB, Blocks.DIORITE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.DIORITE_STAIRS, Blocks.DIORITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DIORITE_WALL, Blocks.DIORITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DIORITE, Blocks.DIORITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DIORITE_SLAB, Blocks.DIORITE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DIORITE_STAIRS, Blocks.DIORITE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DIORITE_SLAB, Blocks.POLISHED_DIORITE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DIORITE_STAIRS, Blocks.POLISHED_DIORITE);
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.MOSSY_STONE_BRICKS), Blocks.MOSSY_STONE_BRICK_SLAB, 2)
         .unlockedBy("has_mossy_stone_bricks", has(Blocks.MOSSY_STONE_BRICKS))
         .save(â˜ƒ, "mossy_stone_brick_slab_from_mossy_stone_brick_stonecutting");
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.MOSSY_STONE_BRICKS), Blocks.MOSSY_STONE_BRICK_STAIRS)
         .unlockedBy("has_mossy_stone_bricks", has(Blocks.MOSSY_STONE_BRICKS))
         .save(â˜ƒ, "mossy_stone_brick_stairs_from_mossy_stone_brick_stonecutting");
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.MOSSY_STONE_BRICKS), Blocks.MOSSY_STONE_BRICK_WALL)
         .unlockedBy("has_mossy_stone_bricks", has(Blocks.MOSSY_STONE_BRICKS))
         .save(â˜ƒ, "mossy_stone_brick_wall_from_mossy_stone_brick_stonecutting");
      stonecutterResultFromBase(â˜ƒ, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.MOSSY_COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_SANDSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.SMOOTH_SANDSTONE_STAIRS, Blocks.SMOOTH_SANDSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.SMOOTH_RED_SANDSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.SMOOTH_RED_SANDSTONE_STAIRS, Blocks.SMOOTH_RED_SANDSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.SMOOTH_QUARTZ, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.SMOOTH_QUARTZ_STAIRS, Blocks.SMOOTH_QUARTZ);
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.END_STONE_BRICKS), Blocks.END_STONE_BRICK_SLAB, 2)
         .unlockedBy("has_end_stone_brick", has(Blocks.END_STONE_BRICKS))
         .save(â˜ƒ, "end_stone_brick_slab_from_end_stone_brick_stonecutting");
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.END_STONE_BRICKS), Blocks.END_STONE_BRICK_STAIRS)
         .unlockedBy("has_end_stone_brick", has(Blocks.END_STONE_BRICKS))
         .save(â˜ƒ, "end_stone_brick_stairs_from_end_stone_brick_stonecutting");
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.END_STONE_BRICKS), Blocks.END_STONE_BRICK_WALL)
         .unlockedBy("has_end_stone_brick", has(Blocks.END_STONE_BRICKS))
         .save(â˜ƒ, "end_stone_brick_wall_from_end_stone_brick_stonecutting");
      stonecutterResultFromBase(â˜ƒ, Blocks.END_STONE_BRICKS, Blocks.END_STONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.END_STONE_BRICK_SLAB, Blocks.END_STONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.END_STONE_BRICK_STAIRS, Blocks.END_STONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.END_STONE_BRICK_WALL, Blocks.END_STONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.SMOOTH_STONE_SLAB, Blocks.SMOOTH_STONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.BLACKSTONE_SLAB, Blocks.BLACKSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.BLACKSTONE_STAIRS, Blocks.BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.BLACKSTONE_WALL, Blocks.BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE, Blocks.BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_WALL, Blocks.BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.BLACKSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_STAIRS, Blocks.BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.CHISELED_POLISHED_BLACKSTONE, Blocks.BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.BLACKSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, Blocks.BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICK_WALL, Blocks.BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_STAIRS, Blocks.POLISHED_BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_WALL, Blocks.POLISHED_BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICK_WALL, Blocks.POLISHED_BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.CHISELED_POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICKS, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_BLACKSTONE_BRICK_WALL, Blocks.POLISHED_BLACKSTONE_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_COPPER_SLAB, Blocks.CUT_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_COPPER_STAIRS, Blocks.CUT_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_CUT_COPPER_SLAB, Blocks.WAXED_CUT_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_CUT_COPPER_STAIRS, Blocks.WAXED_CUT_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_CUT_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_CUT_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_CUT_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_COPPER, Blocks.COPPER_BLOCK);
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_COPPER_STAIRS, Blocks.COPPER_BLOCK);
      stonecutterResultFromBase(â˜ƒ, Blocks.CUT_COPPER_SLAB, Blocks.COPPER_BLOCK, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.EXPOSED_CUT_COPPER, Blocks.EXPOSED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.EXPOSED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.EXPOSED_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.WEATHERED_CUT_COPPER, Blocks.WEATHERED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.WEATHERED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.WEATHERED_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.OXIDIZED_CUT_COPPER, Blocks.OXIDIZED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.OXIDIZED_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_CUT_COPPER, Blocks.WAXED_COPPER_BLOCK);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_CUT_COPPER_STAIRS, Blocks.WAXED_COPPER_BLOCK);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_CUT_COPPER_SLAB, Blocks.WAXED_COPPER_BLOCK, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_COPPER);
      stonecutterResultFromBase(â˜ƒ, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_COPPER, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.COBBLED_DEEPSLATE_SLAB, Blocks.COBBLED_DEEPSLATE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.COBBLED_DEEPSLATE_STAIRS, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.COBBLED_DEEPSLATE_WALL, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.CHISELED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DEEPSLATE_SLAB, Blocks.COBBLED_DEEPSLATE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DEEPSLATE_STAIRS, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DEEPSLATE_WALL, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICKS, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICK_SLAB, Blocks.COBBLED_DEEPSLATE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICK_WALL, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILES, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_SLAB, Blocks.COBBLED_DEEPSLATE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_STAIRS, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_WALL, Blocks.COBBLED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DEEPSLATE_SLAB, Blocks.POLISHED_DEEPSLATE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DEEPSLATE_STAIRS, Blocks.POLISHED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.POLISHED_DEEPSLATE_WALL, Blocks.POLISHED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICKS, Blocks.POLISHED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICK_SLAB, Blocks.POLISHED_DEEPSLATE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.POLISHED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICK_WALL, Blocks.POLISHED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILES, Blocks.POLISHED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_SLAB, Blocks.POLISHED_DEEPSLATE, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_STAIRS, Blocks.POLISHED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_WALL, Blocks.POLISHED_DEEPSLATE);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICK_SLAB, Blocks.DEEPSLATE_BRICKS, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.DEEPSLATE_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_BRICK_WALL, Blocks.DEEPSLATE_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_SLAB, Blocks.DEEPSLATE_BRICKS, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_STAIRS, Blocks.DEEPSLATE_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_WALL, Blocks.DEEPSLATE_BRICKS);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_SLAB, Blocks.DEEPSLATE_TILES, 2);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_STAIRS, Blocks.DEEPSLATE_TILES);
      stonecutterResultFromBase(â˜ƒ, Blocks.DEEPSLATE_TILE_WALL, Blocks.DEEPSLATE_TILES);
      netheriteSmithing(â˜ƒ, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE);
      netheriteSmithing(â˜ƒ, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS);
      netheriteSmithing(â˜ƒ, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET);
      netheriteSmithing(â˜ƒ, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS);
      netheriteSmithing(â˜ƒ, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
      netheriteSmithing(â˜ƒ, Items.DIAMOND_AXE, Items.NETHERITE_AXE);
      netheriteSmithing(â˜ƒ, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE);
      netheriteSmithing(â˜ƒ, Items.DIAMOND_HOE, Items.NETHERITE_HOE);
      netheriteSmithing(â˜ƒ, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL);
   }

   private static void oneToOneConversionRecipe(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2, @Nullable String var3) {
      oneToOneConversionRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1);
   }

   private static void oneToOneConversionRecipe(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2, @Nullable String var3, int var4) {
      ShapelessRecipeBuilder.shapeless(â˜ƒ, â˜ƒ).requires(â˜ƒ).group(â˜ƒ).unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ, getConversionRecipeName(â˜ƒ, â˜ƒ));
   }

   private static void oreSmelting(Consumer<FinishedRecipe> var0, List<ItemLike> var1, ItemLike var2, float var3, int var4, String var5) {
      oreCooking(â˜ƒ, RecipeSerializer.SMELTING_RECIPE, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, "_from_smelting");
   }

   private static void oreBlasting(Consumer<FinishedRecipe> var0, List<ItemLike> var1, ItemLike var2, float var3, int var4, String var5) {
      oreCooking(â˜ƒ, RecipeSerializer.BLASTING_RECIPE, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, "_from_blasting");
   }

   private static void oreCooking(
      Consumer<FinishedRecipe> var0, SimpleCookingSerializer<?> var1, List<ItemLike> var2, ItemLike var3, float var4, int var5, String var6, String var7
   ) {
      for(ItemLike â˜ƒ : â˜ƒ) {
         SimpleCookingRecipeBuilder.cooking(Ingredient.of(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
            .group(â˜ƒ)
            .unlockedBy(getHasName(â˜ƒ), has(â˜ƒ))
            .save(â˜ƒ, getItemName(â˜ƒ) + â˜ƒ + "_" + getItemName(â˜ƒ));
      }
   }

   private static void netheriteSmithing(Consumer<FinishedRecipe> var0, Item var1, Item var2) {
      UpgradeRecipeBuilder.smithing(Ingredient.of(â˜ƒ), Ingredient.of(Items.NETHERITE_INGOT), â˜ƒ)
         .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
         .save(â˜ƒ, getItemName(â˜ƒ) + "_smithing");
   }

   private static void planksFromLog(Consumer<FinishedRecipe> var0, ItemLike var1, Tag<Item> var2) {
      ShapelessRecipeBuilder.shapeless(â˜ƒ, 4).requires(â˜ƒ).group("planks").unlockedBy("has_log", has(â˜ƒ)).save(â˜ƒ);
   }

   private static void planksFromLogs(Consumer<FinishedRecipe> var0, ItemLike var1, Tag<Item> var2) {
      ShapelessRecipeBuilder.shapeless(â˜ƒ, 4).requires(â˜ƒ).group("planks").unlockedBy("has_logs", has(â˜ƒ)).save(â˜ƒ);
   }

   private static void woodFromLogs(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapedRecipeBuilder.shaped(â˜ƒ, 3).define('#', â˜ƒ).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(â˜ƒ)).save(â˜ƒ);
   }

   private static void woodenBoat(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapedRecipeBuilder.shaped(â˜ƒ).define('#', â˜ƒ).pattern("# #").pattern("###").group("boat").unlockedBy("in_water", insideOf(Blocks.WATER)).save(â˜ƒ);
   }

   private static RecipeBuilder buttonBuilder(ItemLike var0, Ingredient var1) {
      return ShapelessRecipeBuilder.shapeless(â˜ƒ).requires(â˜ƒ);
   }

   private static RecipeBuilder doorBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ, 3).define('#', â˜ƒ).pattern("##").pattern("##").pattern("##");
   }

   private static RecipeBuilder fenceBuilder(ItemLike var0, Ingredient var1) {
      int â˜ƒ = â˜ƒ == Blocks.NETHER_BRICK_FENCE ? 6 : 3;
      Item â˜ƒx = â˜ƒ == Blocks.NETHER_BRICK_FENCE ? Items.NETHER_BRICK : Items.STICK;
      return ShapedRecipeBuilder.shaped(â˜ƒ, â˜ƒ).define('W', â˜ƒ).define('#', â˜ƒx).pattern("W#W").pattern("W#W");
   }

   private static RecipeBuilder fenceGateBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ).define('#', Items.STICK).define('W', â˜ƒ).pattern("#W#").pattern("#W#");
   }

   private static void pressurePlate(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      pressurePlateBuilder(â˜ƒ, Ingredient.of(â˜ƒ)).unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ);
   }

   private static RecipeBuilder pressurePlateBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ).define('#', â˜ƒ).pattern("##");
   }

   private static void slab(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      slabBuilder(â˜ƒ, Ingredient.of(â˜ƒ)).unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ);
   }

   private static RecipeBuilder slabBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ, 6).define('#', â˜ƒ).pattern("###");
   }

   private static RecipeBuilder stairBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ, 4).define('#', â˜ƒ).pattern("#  ").pattern("## ").pattern("###");
   }

   private static RecipeBuilder trapdoorBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ, 2).define('#', â˜ƒ).pattern("###").pattern("###");
   }

   private static RecipeBuilder signBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ, 3).group("sign").define('#', â˜ƒ).define('X', Items.STICK).pattern("###").pattern("###").pattern(" X ");
   }

   private static void coloredWoolFromWhiteWoolAndDye(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapelessRecipeBuilder.shapeless(â˜ƒ)
         .requires(â˜ƒ)
         .requires(Blocks.WHITE_WOOL)
         .group("wool")
         .unlockedBy("has_white_wool", has(Blocks.WHITE_WOOL))
         .save(â˜ƒ);
   }

   private static void carpet(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapedRecipeBuilder.shaped(â˜ƒ, 3).define('#', â˜ƒ).pattern("##").group("carpet").unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ);
   }

   private static void coloredCarpetFromWhiteCarpetAndDye(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapedRecipeBuilder.shaped(â˜ƒ, 8)
         .define('#', Blocks.WHITE_CARPET)
         .define('$', â˜ƒ)
         .pattern("###")
         .pattern("#$#")
         .pattern("###")
         .group("carpet")
         .unlockedBy("has_white_carpet", has(Blocks.WHITE_CARPET))
         .unlockedBy(getHasName(â˜ƒ), has(â˜ƒ))
         .save(â˜ƒ, getConversionRecipeName(â˜ƒ, Blocks.WHITE_CARPET));
   }

   private static void bedFromPlanksAndWool(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapedRecipeBuilder.shaped(â˜ƒ)
         .define('#', â˜ƒ)
         .define('X', ItemTags.PLANKS)
         .pattern("###")
         .pattern("XXX")
         .group("bed")
         .unlockedBy(getHasName(â˜ƒ), has(â˜ƒ))
         .save(â˜ƒ);
   }

   private static void bedFromWhiteBedAndDye(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapelessRecipeBuilder.shapeless(â˜ƒ)
         .requires(Items.WHITE_BED)
         .requires(â˜ƒ)
         .group("dyed_bed")
         .unlockedBy("has_bed", has(Items.WHITE_BED))
         .save(â˜ƒ, getConversionRecipeName(â˜ƒ, Items.WHITE_BED));
   }

   private static void banner(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapedRecipeBuilder.shaped(â˜ƒ)
         .define('#', â˜ƒ)
         .define('|', Items.STICK)
         .pattern("###")
         .pattern("###")
         .pattern(" | ")
         .group("banner")
         .unlockedBy(getHasName(â˜ƒ), has(â˜ƒ))
         .save(â˜ƒ);
   }

   private static void stainedGlassFromGlassAndDye(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapedRecipeBuilder.shaped(â˜ƒ, 8)
         .define('#', Blocks.GLASS)
         .define('X', â˜ƒ)
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .group("stained_glass")
         .unlockedBy("has_glass", has(Blocks.GLASS))
         .save(â˜ƒ);
   }

   private static void stainedGlassPaneFromStainedGlass(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapedRecipeBuilder.shaped(â˜ƒ, 16)
         .define('#', â˜ƒ)
         .pattern("###")
         .pattern("###")
         .group("stained_glass_pane")
         .unlockedBy("has_glass", has(â˜ƒ))
         .save(â˜ƒ);
   }

   private static void stainedGlassPaneFromGlassPaneAndDye(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapedRecipeBuilder.shaped(â˜ƒ, 8)
         .define('#', Blocks.GLASS_PANE)
         .define('$', â˜ƒ)
         .pattern("###")
         .pattern("#$#")
         .pattern("###")
         .group("stained_glass_pane")
         .unlockedBy("has_glass_pane", has(Blocks.GLASS_PANE))
         .unlockedBy(getHasName(â˜ƒ), has(â˜ƒ))
         .save(â˜ƒ, getConversionRecipeName(â˜ƒ, Blocks.GLASS_PANE));
   }

   private static void coloredTerracottaFromTerracottaAndDye(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapedRecipeBuilder.shaped(â˜ƒ, 8)
         .define('#', Blocks.TERRACOTTA)
         .define('X', â˜ƒ)
         .pattern("###")
         .pattern("#X#")
         .pattern("###")
         .group("stained_terracotta")
         .unlockedBy("has_terracotta", has(Blocks.TERRACOTTA))
         .save(â˜ƒ);
   }

   private static void concretePowder(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapelessRecipeBuilder.shapeless(â˜ƒ, 8)
         .requires(â˜ƒ)
         .requires(Blocks.SAND, 4)
         .requires(Blocks.GRAVEL, 4)
         .group("concrete_powder")
         .unlockedBy("has_sand", has(Blocks.SAND))
         .unlockedBy("has_gravel", has(Blocks.GRAVEL))
         .save(â˜ƒ);
   }

   public static void candle(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      ShapelessRecipeBuilder.shapeless(â˜ƒ).requires(Blocks.CANDLE).requires(â˜ƒ).group("dyed_candle").unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ);
   }

   public static void wall(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      wallBuilder(â˜ƒ, Ingredient.of(â˜ƒ)).unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ);
   }

   public static RecipeBuilder wallBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ, 6).define('#', â˜ƒ).pattern("###").pattern("###");
   }

   public static void polished(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      polishedBuilder(â˜ƒ, Ingredient.of(â˜ƒ)).unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ);
   }

   public static RecipeBuilder polishedBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ, 4).define('S', â˜ƒ).pattern("SS").pattern("SS");
   }

   public static void cut(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      cutBuilder(â˜ƒ, Ingredient.of(â˜ƒ)).unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ);
   }

   public static ShapedRecipeBuilder cutBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ, 4).define('#', â˜ƒ).pattern("##").pattern("##");
   }

   public static void chiseled(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      chiseledBuilder(â˜ƒ, Ingredient.of(â˜ƒ)).unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ);
   }

   public static ShapedRecipeBuilder chiseledBuilder(ItemLike var0, Ingredient var1) {
      return ShapedRecipeBuilder.shaped(â˜ƒ).define('#', â˜ƒ).pattern("#").pattern("#");
   }

   private static void stonecutterResultFromBase(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      stonecutterResultFromBase(â˜ƒ, â˜ƒ, â˜ƒ, 1);
   }

   private static void stonecutterResultFromBase(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2, int var3) {
      SingleItemRecipeBuilder.stonecutting(Ingredient.of(â˜ƒ), â˜ƒ, â˜ƒ)
         .unlockedBy(getHasName(â˜ƒ), has(â˜ƒ))
         .save(â˜ƒ, getConversionRecipeName(â˜ƒ, â˜ƒ) + "_stonecutting");
   }

   private static void smeltingResultFromBase(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      SimpleCookingRecipeBuilder.smelting(Ingredient.of(â˜ƒ), â˜ƒ, 0.1F, 200).unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ);
   }

   private static void nineBlockStorageRecipes(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2) {
      nineBlockStorageRecipes(â˜ƒ, â˜ƒ, â˜ƒ, getSimpleRecipeName(â˜ƒ), null, getSimpleRecipeName(â˜ƒ), null);
   }

   private static void nineBlockStorageRecipesWithCustomPacking(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2, String var3, String var4) {
      nineBlockStorageRecipes(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, getSimpleRecipeName(â˜ƒ), null);
   }

   private static void nineBlockStorageRecipesRecipesWithCustomUnpacking(Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2, String var3, String var4) {
      nineBlockStorageRecipes(â˜ƒ, â˜ƒ, â˜ƒ, getSimpleRecipeName(â˜ƒ), null, â˜ƒ, â˜ƒ);
   }

   private static void nineBlockStorageRecipes(
      Consumer<FinishedRecipe> var0, ItemLike var1, ItemLike var2, String var3, @Nullable String var4, String var5, @Nullable String var6
   ) {
      ShapelessRecipeBuilder.shapeless(â˜ƒ, 9).requires(â˜ƒ).group(â˜ƒ).unlockedBy(getHasName(â˜ƒ), has(â˜ƒ)).save(â˜ƒ, new ResourceLocation(â˜ƒ));
      ShapedRecipeBuilder.shaped(â˜ƒ)
         .define('#', â˜ƒ)
         .pattern("###")
         .pattern("###")
         .pattern("###")
         .group(â˜ƒ)
         .unlockedBy(getHasName(â˜ƒ), has(â˜ƒ))
         .save(â˜ƒ, new ResourceLocation(â˜ƒ));
   }

   private static void cookRecipes(Consumer<FinishedRecipe> var0, String var1, SimpleCookingSerializer<?> var2, int var3) {
      simpleCookingRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Items.BEEF, Items.COOKED_BEEF, 0.35F);
      simpleCookingRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Items.CHICKEN, Items.COOKED_CHICKEN, 0.35F);
      simpleCookingRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Items.COD, Items.COOKED_COD, 0.35F);
      simpleCookingRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Items.KELP, Items.DRIED_KELP, 0.1F);
      simpleCookingRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Items.SALMON, Items.COOKED_SALMON, 0.35F);
      simpleCookingRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Items.MUTTON, Items.COOKED_MUTTON, 0.35F);
      simpleCookingRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Items.PORKCHOP, Items.COOKED_PORKCHOP, 0.35F);
      simpleCookingRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Items.POTATO, Items.BAKED_POTATO, 0.35F);
      simpleCookingRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Items.RABBIT, Items.COOKED_RABBIT, 0.35F);
   }

   private static void simpleCookingRecipe(
      Consumer<FinishedRecipe> var0, String var1, SimpleCookingSerializer<?> var2, int var3, ItemLike var4, ItemLike var5, float var6
   ) {
      SimpleCookingRecipeBuilder.cooking(Ingredient.of(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
         .unlockedBy(getHasName(â˜ƒ), has(â˜ƒ))
         .save(â˜ƒ, getItemName(â˜ƒ) + "_from_" + â˜ƒ);
   }

   private static void waxRecipes(Consumer<FinishedRecipe> var0) {
      ((BiMap)HoneycombItem.WAXABLES.get())
         .forEach(
            (var1, var2) -> ShapelessRecipeBuilder.shapeless(var2)
                  .requires(var1)
                  .requires(Items.HONEYCOMB)
                  .group(getItemName(var2))
                  .unlockedBy(getHasName(var1), has(var1))
                  .save(â˜ƒ, getConversionRecipeName(var2, Items.HONEYCOMB))
         );
   }

   private static void generateRecipes(Consumer<FinishedRecipe> var0, BlockFamily var1) {
      â˜ƒ.getVariants().forEach((var2, var3) -> {
         BiFunction<ItemLike, ItemLike, RecipeBuilder> â˜ƒ = (BiFunction)shapeBuilders.get(var2);
         ItemLike â˜ƒx = getBaseBlock(â˜ƒ, var2);
         if (â˜ƒ != null) {
            RecipeBuilder â˜ƒxx = (RecipeBuilder)â˜ƒ.apply(var3, â˜ƒx);
            â˜ƒ.getRecipeGroupPrefix().ifPresent(var2x -> â˜ƒ.group(var2x + (var2 == BlockFamily.Variant.CUT ? "" : "_" + var2.getName())));
            â˜ƒxx.unlockedBy((String)â˜ƒ.getRecipeUnlockedBy().orElseGet(() -> getHasName(â˜ƒ)), has(â˜ƒx));
            â˜ƒxx.save(â˜ƒ);
         }

         if (var2 == BlockFamily.Variant.CRACKED) {
            smeltingResultFromBase(â˜ƒ, var3, â˜ƒx);
         }
      });
   }

   private static Block getBaseBlock(BlockFamily var0, BlockFamily.Variant var1) {
      if (â˜ƒ == BlockFamily.Variant.CHISELED) {
         if (!â˜ƒ.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
            throw new IllegalStateException("Slab is not defined for the family.");
         } else {
            return â˜ƒ.get(BlockFamily.Variant.SLAB);
         }
      } else {
         return â˜ƒ.getBaseBlock();
      }
   }

   private static EnterBlockTrigger.TriggerInstance insideOf(Block var0) {
      return new EnterBlockTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, StatePropertiesPredicate.ANY);
   }

   private static InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints var0, ItemLike var1) {
      return inventoryTrigger(ItemPredicate.Builder.item().of(â˜ƒ).withCount(â˜ƒ).build());
   }

   private static InventoryChangeTrigger.TriggerInstance has(ItemLike var0) {
      return inventoryTrigger(ItemPredicate.Builder.item().of(â˜ƒ).build());
   }

   private static InventoryChangeTrigger.TriggerInstance has(Tag<Item> var0) {
      return inventoryTrigger(ItemPredicate.Builder.item().of(â˜ƒ).build());
   }

   private static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... var0) {
      return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, â˜ƒ);
   }

   private static String getHasName(ItemLike var0) {
      return "has_" + getItemName(â˜ƒ);
   }

   private static String getItemName(ItemLike var0) {
      return Registry.ITEM.getKey(â˜ƒ.asItem()).getPath();
   }

   private static String getSimpleRecipeName(ItemLike var0) {
      return getItemName(â˜ƒ);
   }

   private static String getConversionRecipeName(ItemLike var0, ItemLike var1) {
      return getItemName(â˜ƒ) + "_from_" + getItemName(â˜ƒ);
   }

   private static String getSmeltingRecipeName(ItemLike var0) {
      return getItemName(â˜ƒ) + "_from_smelting";
   }

   private static String getBlastingRecipeName(ItemLike var0) {
      return getItemName(â˜ƒ) + "_from_blasting";
   }

   @Override
   public String getName() {
      return "Recipes";
   }
}
