package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BuiltInModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockModel implements UnbakedModel {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final FaceBakery FACE_BAKERY = new FaceBakery();
   @VisibleForTesting
   static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(BlockModel.class, new BlockModel.Deserializer())
      .registerTypeAdapter(BlockElement.class, new BlockElement.Deserializer())
      .registerTypeAdapter(BlockElementFace.class, new BlockElementFace.Deserializer())
      .registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer())
      .registerTypeAdapter(ItemTransform.class, new ItemTransform.Deserializer())
      .registerTypeAdapter(ItemTransforms.class, new ItemTransforms.Deserializer())
      .registerTypeAdapter(ItemOverride.class, new ItemOverride.Deserializer())
      .create();
   private static final char REFERENCE_CHAR = '#';
   public static final String PARTICLE_TEXTURE_REFERENCE = "particle";
   private final List<BlockElement> elements;
   @Nullable
   private final BlockModel.GuiLight guiLight;
   private final boolean hasAmbientOcclusion;
   private final ItemTransforms transforms;
   private final List<ItemOverride> overrides;
   public String name = "";
   @VisibleForTesting
   protected final Map<String, Either<Material, String>> textureMap;
   @Nullable
   protected BlockModel parent;
   @Nullable
   protected ResourceLocation parentLocation;

   public static BlockModel fromStream(Reader var0) {
      return GsonHelper.fromJson(GSON, â˜ƒ, BlockModel.class);
   }

   public static BlockModel fromString(String var0) {
      return fromStream(new StringReader(â˜ƒ));
   }

   public BlockModel(
      @Nullable ResourceLocation var1,
      List<BlockElement> var2,
      Map<String, Either<Material, String>> var3,
      boolean var4,
      @Nullable BlockModel.GuiLight var5,
      ItemTransforms var6,
      List<ItemOverride> var7
   ) {
      this.elements = â˜ƒ;
      this.hasAmbientOcclusion = â˜ƒ;
      this.guiLight = â˜ƒ;
      this.textureMap = â˜ƒ;
      this.parentLocation = â˜ƒ;
      this.transforms = â˜ƒ;
      this.overrides = â˜ƒ;
   }

   public List<BlockElement> getElements() {
      return this.elements.isEmpty() && this.parent != null ? this.parent.getElements() : this.elements;
   }

   public boolean hasAmbientOcclusion() {
      return this.parent != null ? this.parent.hasAmbientOcclusion() : this.hasAmbientOcclusion;
   }

   public BlockModel.GuiLight getGuiLight() {
      if (this.guiLight != null) {
         return this.guiLight;
      } else {
         return this.parent != null ? this.parent.getGuiLight() : BlockModel.GuiLight.SIDE;
      }
   }

   public boolean isResolved() {
      return this.parentLocation == null || this.parent != null && this.parent.isResolved();
   }

   public List<ItemOverride> getOverrides() {
      return this.overrides;
   }

   private ItemOverrides getItemOverrides(ModelBakery var1, BlockModel var2) {
      return this.overrides.isEmpty() ? ItemOverrides.EMPTY : new ItemOverrides(â˜ƒ, â˜ƒ, â˜ƒ::getModel, this.overrides);
   }

   @Override
   public Collection<ResourceLocation> getDependencies() {
      Set<ResourceLocation> â˜ƒ = Sets.<ResourceLocation>newHashSet();

      for(ItemOverride â˜ƒx : this.overrides) {
         â˜ƒ.add(â˜ƒx.getModel());
      }

      if (this.parentLocation != null) {
         â˜ƒ.add(this.parentLocation);
      }

      return â˜ƒ;
   }

   @Override
   public Collection<Material> getMaterials(Function<ResourceLocation, UnbakedModel> var1, Set<Pair<String, String>> var2) {
      Set<UnbakedModel> â˜ƒ = Sets.<UnbakedModel>newLinkedHashSet();

      for(BlockModel â˜ƒx = this; â˜ƒx.parentLocation != null && â˜ƒx.parent == null; â˜ƒx = â˜ƒx.parent) {
         â˜ƒ.add(â˜ƒx);
         UnbakedModel â˜ƒxx = (UnbakedModel)â˜ƒ.apply(â˜ƒx.parentLocation);
         if (â˜ƒxx == null) {
            LOGGER.warn("No parent '{}' while loading model '{}'", this.parentLocation, â˜ƒx);
         }

         if (â˜ƒ.contains(â˜ƒxx)) {
            LOGGER.warn(
               "Found 'parent' loop while loading model '{}' in chain: {} -> {}",
               â˜ƒx,
               â˜ƒ.stream().map(Object::toString).collect(Collectors.joining(" -> ")),
               this.parentLocation
            );
            â˜ƒxx = null;
         }

         if (â˜ƒxx == null) {
            â˜ƒx.parentLocation = ModelBakery.MISSING_MODEL_LOCATION;
            â˜ƒxx = (UnbakedModel)â˜ƒ.apply(â˜ƒx.parentLocation);
         }

         if (!(â˜ƒxx instanceof BlockModel)) {
            throw new IllegalStateException("BlockModel parent has to be a block model.");
         }

         â˜ƒx.parent = (BlockModel)â˜ƒxx;
      }

      Set<Material> â˜ƒx = Sets.<Material>newHashSet(this.getMaterial("particle"));

      for(BlockElement â˜ƒxx : this.getElements()) {
         for(BlockElementFace â˜ƒxxx : â˜ƒxx.faces.values()) {
            Material â˜ƒxxxx = this.getMaterial(â˜ƒxxx.texture);
            if (Objects.equals(â˜ƒxxxx.texture(), MissingTextureAtlasSprite.getLocation())) {
               â˜ƒ.add(Pair.of(â˜ƒxxx.texture, this.name));
            }

            â˜ƒx.add(â˜ƒxxxx);
         }
      }

      this.overrides.forEach(var4x -> {
         UnbakedModel â˜ƒ = (UnbakedModel)â˜ƒ.apply(var4x.getModel());
         if (!Objects.equals(â˜ƒ, this)) {
            â˜ƒ.addAll(â˜ƒ.getMaterials(â˜ƒ, â˜ƒ));
         }
      });
      if (this.getRootModel() == ModelBakery.GENERATION_MARKER) {
         ItemModelGenerator.LAYERS.forEach(var2x -> â˜ƒ.add(this.getMaterial(var2x)));
      }

      return â˜ƒx;
   }

   @Override
   public BakedModel bake(ModelBakery var1, Function<Material, TextureAtlasSprite> var2, ModelState var3, ResourceLocation var4) {
      return this.bake(â˜ƒ, this, â˜ƒ, â˜ƒ, â˜ƒ, true);
   }

   public BakedModel bake(ModelBakery var1, BlockModel var2, Function<Material, TextureAtlasSprite> var3, ModelState var4, ResourceLocation var5, boolean var6) {
      TextureAtlasSprite â˜ƒ = (TextureAtlasSprite)â˜ƒ.apply(this.getMaterial("particle"));
      if (this.getRootModel() == ModelBakery.BLOCK_ENTITY_MARKER) {
         return new BuiltInModel(this.getTransforms(), this.getItemOverrides(â˜ƒ, â˜ƒ), â˜ƒ, this.getGuiLight().lightLikeBlock());
      } else {
         SimpleBakedModel.Builder â˜ƒ = new SimpleBakedModel.Builder(this, this.getItemOverrides(â˜ƒ, â˜ƒ), â˜ƒ).particle(â˜ƒ);

         for(BlockElement â˜ƒx : this.getElements()) {
            for(Direction â˜ƒxx : â˜ƒx.faces.keySet()) {
               BlockElementFace â˜ƒxxx = (BlockElementFace)â˜ƒx.faces.get(â˜ƒxx);
               TextureAtlasSprite â˜ƒxxxx = (TextureAtlasSprite)â˜ƒ.apply(this.getMaterial(â˜ƒxxx.texture));
               if (â˜ƒxxx.cullForDirection == null) {
                  â˜ƒ.addUnculledFace(bakeFace(â˜ƒx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxx, â˜ƒ, â˜ƒ));
               } else {
                  â˜ƒ.addCulledFace(Direction.rotate(â˜ƒ.getRotation().getMatrix(), â˜ƒxxx.cullForDirection), bakeFace(â˜ƒx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxx, â˜ƒ, â˜ƒ));
               }
            }
         }

         return â˜ƒ.build();
      }
   }

   private static BakedQuad bakeFace(BlockElement var0, BlockElementFace var1, TextureAtlasSprite var2, Direction var3, ModelState var4, ResourceLocation var5) {
      return FACE_BAKERY.bakeQuad(â˜ƒ.from, â˜ƒ.to, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.rotation, â˜ƒ.shade, â˜ƒ);
   }

   public boolean hasTexture(String var1) {
      return !MissingTextureAtlasSprite.getLocation().equals(this.getMaterial(â˜ƒ).texture());
   }

   public Material getMaterial(String var1) {
      if (isTextureReference(â˜ƒ)) {
         â˜ƒ = â˜ƒ.substring(1);
      }

      List<String> â˜ƒ = Lists.newArrayList();

      while(true) {
         Either<Material, String> â˜ƒx = this.findTextureEntry(â˜ƒ);
         Optional<Material> â˜ƒxx = â˜ƒx.left();
         if (â˜ƒxx.isPresent()) {
            return (Material)â˜ƒxx.get();
         }

         â˜ƒ = (String)â˜ƒx.right().get();
         if (â˜ƒ.contains(â˜ƒ)) {
            LOGGER.warn("Unable to resolve texture due to reference chain {}->{} in {}", Joiner.on("->").join(â˜ƒ), â˜ƒ, this.name);
            return new Material(TextureAtlas.LOCATION_BLOCKS, MissingTextureAtlasSprite.getLocation());
         }

         â˜ƒ.add(â˜ƒ);
      }
   }

   private Either<Material, String> findTextureEntry(String var1) {
      for(BlockModel â˜ƒ = this; â˜ƒ != null; â˜ƒ = â˜ƒ.parent) {
         Either<Material, String> â˜ƒx = (Either)â˜ƒ.textureMap.get(â˜ƒ);
         if (â˜ƒx != null) {
            return â˜ƒx;
         }
      }

      return Either.left(new Material(TextureAtlas.LOCATION_BLOCKS, MissingTextureAtlasSprite.getLocation()));
   }

   static boolean isTextureReference(String var0) {
      return â˜ƒ.charAt(0) == '#';
   }

   public BlockModel getRootModel() {
      return this.parent == null ? this : this.parent.getRootModel();
   }

   public ItemTransforms getTransforms() {
      ItemTransform â˜ƒ = this.getTransform(ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
      ItemTransform â˜ƒx = this.getTransform(ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
      ItemTransform â˜ƒxx = this.getTransform(ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND);
      ItemTransform â˜ƒxxx = this.getTransform(ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
      ItemTransform â˜ƒxxxx = this.getTransform(ItemTransforms.TransformType.HEAD);
      ItemTransform â˜ƒxxxxx = this.getTransform(ItemTransforms.TransformType.GUI);
      ItemTransform â˜ƒxxxxxx = this.getTransform(ItemTransforms.TransformType.GROUND);
      ItemTransform â˜ƒxxxxxxx = this.getTransform(ItemTransforms.TransformType.FIXED);
      return new ItemTransforms(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
   }

   private ItemTransform getTransform(ItemTransforms.TransformType var1) {
      return this.parent != null && !this.transforms.hasTransform(â˜ƒ) ? this.parent.getTransform(â˜ƒ) : this.transforms.getTransform(â˜ƒ);
   }

   public String toString() {
      return this.name;
   }

   public static class Deserializer implements JsonDeserializer<BlockModel> {
      private static final boolean DEFAULT_AMBIENT_OCCLUSION = true;

      public BlockModel deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         List<BlockElement> â˜ƒx = this.getElements(â˜ƒ, â˜ƒ);
         String â˜ƒxx = this.getParentName(â˜ƒ);
         Map<String, Either<Material, String>> â˜ƒxxx = this.getTextureMap(â˜ƒ);
         boolean â˜ƒxxxx = this.getAmbientOcclusion(â˜ƒ);
         ItemTransforms â˜ƒxxxxx = ItemTransforms.NO_TRANSFORMS;
         if (â˜ƒ.has("display")) {
            JsonObject â˜ƒxxxxxx = GsonHelper.getAsJsonObject(â˜ƒ, "display");
            â˜ƒxxxxx = â˜ƒ.deserialize(â˜ƒxxxxxx, ItemTransforms.class);
         }

         List<ItemOverride> â˜ƒ = this.getOverrides(â˜ƒ, â˜ƒ);
         BlockModel.GuiLight â˜ƒx = null;
         if (â˜ƒ.has("gui_light")) {
            â˜ƒx = BlockModel.GuiLight.getByName(GsonHelper.getAsString(â˜ƒ, "gui_light"));
         }

         ResourceLocation â˜ƒ = â˜ƒxx.isEmpty() ? null : new ResourceLocation(â˜ƒxx);
         return new BlockModel(â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒxxxx, â˜ƒx, â˜ƒxxxxx, â˜ƒ);
      }

      protected List<ItemOverride> getOverrides(JsonDeserializationContext var1, JsonObject var2) {
         List<ItemOverride> â˜ƒ = Lists.<ItemOverride>newArrayList();
         if (â˜ƒ.has("overrides")) {
            for(JsonElement â˜ƒx : GsonHelper.getAsJsonArray(â˜ƒ, "overrides")) {
               â˜ƒ.add((ItemOverride)â˜ƒ.deserialize(â˜ƒx, ItemOverride.class));
            }
         }

         return â˜ƒ;
      }

      private Map<String, Either<Material, String>> getTextureMap(JsonObject var1) {
         ResourceLocation â˜ƒ = TextureAtlas.LOCATION_BLOCKS;
         Map<String, Either<Material, String>> â˜ƒx = Maps.newHashMap();
         if (â˜ƒ.has("textures")) {
            JsonObject â˜ƒxx = GsonHelper.getAsJsonObject(â˜ƒ, "textures");

            for(Entry<String, JsonElement> â˜ƒxxx : â˜ƒxx.entrySet()) {
               â˜ƒx.put((String)â˜ƒxxx.getKey(), parseTextureLocationOrReference(â˜ƒ, ((JsonElement)â˜ƒxxx.getValue()).getAsString()));
            }
         }

         return â˜ƒx;
      }

      private static Either<Material, String> parseTextureLocationOrReference(ResourceLocation var0, String var1) {
         if (BlockModel.isTextureReference(â˜ƒ)) {
            return Either.right(â˜ƒ.substring(1));
         } else {
            ResourceLocation â˜ƒ = ResourceLocation.tryParse(â˜ƒ);
            if (â˜ƒ == null) {
               throw new JsonParseException(â˜ƒ + " is not valid resource location");
            } else {
               return Either.left(new Material(â˜ƒ, â˜ƒ));
            }
         }
      }

      private String getParentName(JsonObject var1) {
         return GsonHelper.getAsString(â˜ƒ, "parent", "");
      }

      protected boolean getAmbientOcclusion(JsonObject var1) {
         return GsonHelper.getAsBoolean(â˜ƒ, "ambientocclusion", true);
      }

      protected List<BlockElement> getElements(JsonDeserializationContext var1, JsonObject var2) {
         List<BlockElement> â˜ƒ = Lists.<BlockElement>newArrayList();
         if (â˜ƒ.has("elements")) {
            for(JsonElement â˜ƒx : GsonHelper.getAsJsonArray(â˜ƒ, "elements")) {
               â˜ƒ.add((BlockElement)â˜ƒ.deserialize(â˜ƒx, BlockElement.class));
            }
         }

         return â˜ƒ;
      }
   }

   public static enum GuiLight {
      FRONT("front"),
      SIDE("side");

      private final String name;

      private GuiLight(String var3) {
         this.name = â˜ƒ;
      }

      public static BlockModel.GuiLight getByName(String var0) {
         for(BlockModel.GuiLight â˜ƒ : values()) {
            if (â˜ƒ.name.equals(â˜ƒ)) {
               return â˜ƒ;
            }
         }

         throw new IllegalArgumentException("Invalid gui light: " + â˜ƒ);
      }

      public boolean lightLikeBlock() {
         return this == SIDE;
      }
   }

   public static class LoopException extends RuntimeException {
      public LoopException(String var1) {
         super(â˜ƒ);
      }
   }
}
