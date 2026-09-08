package net.minecraft.data.models.model;

import com.google.gson.JsonElement;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class TexturedModel {
   public static final TexturedModel.Provider CUBE = createDefault(TextureMapping::cube, ModelTemplates.CUBE_ALL);
   public static final TexturedModel.Provider CUBE_MIRRORED = createDefault(TextureMapping::cube, ModelTemplates.CUBE_MIRRORED_ALL);
   public static final TexturedModel.Provider COLUMN = createDefault(TextureMapping::column, ModelTemplates.CUBE_COLUMN);
   public static final TexturedModel.Provider COLUMN_HORIZONTAL = createDefault(TextureMapping::column, ModelTemplates.CUBE_COLUMN_HORIZONTAL);
   public static final TexturedModel.Provider CUBE_TOP_BOTTOM = createDefault(TextureMapping::cubeBottomTop, ModelTemplates.CUBE_BOTTOM_TOP);
   public static final TexturedModel.Provider CUBE_TOP = createDefault(TextureMapping::cubeTop, ModelTemplates.CUBE_TOP);
   public static final TexturedModel.Provider ORIENTABLE_ONLY_TOP = createDefault(TextureMapping::orientableCubeOnlyTop, ModelTemplates.CUBE_ORIENTABLE);
   public static final TexturedModel.Provider ORIENTABLE = createDefault(TextureMapping::orientableCube, ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM);
   public static final TexturedModel.Provider CARPET = createDefault(TextureMapping::wool, ModelTemplates.CARPET);
   public static final TexturedModel.Provider GLAZED_TERRACOTTA = createDefault(TextureMapping::pattern, ModelTemplates.GLAZED_TERRACOTTA);
   public static final TexturedModel.Provider CORAL_FAN = createDefault(TextureMapping::fan, ModelTemplates.CORAL_FAN);
   public static final TexturedModel.Provider PARTICLE_ONLY = createDefault(TextureMapping::particle, ModelTemplates.PARTICLE_ONLY);
   public static final TexturedModel.Provider ANVIL = createDefault(TextureMapping::top, ModelTemplates.ANVIL);
   public static final TexturedModel.Provider LEAVES = createDefault(TextureMapping::cube, ModelTemplates.LEAVES);
   public static final TexturedModel.Provider LANTERN = createDefault(TextureMapping::lantern, ModelTemplates.LANTERN);
   public static final TexturedModel.Provider HANGING_LANTERN = createDefault(TextureMapping::lantern, ModelTemplates.HANGING_LANTERN);
   public static final TexturedModel.Provider SEAGRASS = createDefault(TextureMapping::defaultTexture, ModelTemplates.SEAGRASS);
   public static final TexturedModel.Provider COLUMN_ALT = createDefault(TextureMapping::logColumn, ModelTemplates.CUBE_COLUMN);
   public static final TexturedModel.Provider COLUMN_HORIZONTAL_ALT = createDefault(TextureMapping::logColumn, ModelTemplates.CUBE_COLUMN_HORIZONTAL);
   public static final TexturedModel.Provider TOP_BOTTOM_WITH_WALL = createDefault(TextureMapping::cubeBottomTopWithWall, ModelTemplates.CUBE_BOTTOM_TOP);
   public static final TexturedModel.Provider COLUMN_WITH_WALL = createDefault(TextureMapping::columnWithWall, ModelTemplates.CUBE_COLUMN);
   private final TextureMapping mapping;
   private final ModelTemplate template;

   private TexturedModel(TextureMapping var1, ModelTemplate var2) {
      this.mapping = â˜ƒ;
      this.template = â˜ƒ;
   }

   public ModelTemplate getTemplate() {
      return this.template;
   }

   public TextureMapping getMapping() {
      return this.mapping;
   }

   public TexturedModel updateTextures(Consumer<TextureMapping> var1) {
      â˜ƒ.accept(this.mapping);
      return this;
   }

   public ResourceLocation create(Block var1, BiConsumer<ResourceLocation, Supplier<JsonElement>> var2) {
      return this.template.create(â˜ƒ, this.mapping, â˜ƒ);
   }

   public ResourceLocation createWithSuffix(Block var1, String var2, BiConsumer<ResourceLocation, Supplier<JsonElement>> var3) {
      return this.template.createWithSuffix(â˜ƒ, â˜ƒ, this.mapping, â˜ƒ);
   }

   private static TexturedModel.Provider createDefault(Function<Block, TextureMapping> var0, ModelTemplate var1) {
      return var2 -> new TexturedModel((TextureMapping)â˜ƒ.apply(var2), â˜ƒ);
   }

   public static TexturedModel createAllSame(ResourceLocation var0) {
      return new TexturedModel(TextureMapping.cube(â˜ƒ), ModelTemplates.CUBE_ALL);
   }

   @FunctionalInterface
   public interface Provider {
      TexturedModel get(Block var1);

      default ResourceLocation create(Block var1, BiConsumer<ResourceLocation, Supplier<JsonElement>> var2) {
         return this.get(â˜ƒ).create(â˜ƒ, â˜ƒ);
      }

      default ResourceLocation createWithSuffix(Block var1, String var2, BiConsumer<ResourceLocation, Supplier<JsonElement>> var3) {
         return this.get(â˜ƒ).createWithSuffix(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      default TexturedModel.Provider updateTexture(Consumer<TextureMapping> var1) {
         return var2 -> this.get(var2).updateTextures(â˜ƒ);
      }
   }
}
