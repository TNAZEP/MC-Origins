package net.minecraft.client.renderer.model;

import com.google.common.annotations.VisibleForTesting;
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
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelBlock implements IUnbakedModel {
   private static final Logger field_178313_f = LogManager.getLogger();
   private static final ItemModelGenerator field_209571_g = new ItemModelGenerator();
   private static final FaceBakery field_209572_h = new FaceBakery();
   @VisibleForTesting
   static final Gson field_178319_a = new GsonBuilder()
      .registerTypeAdapter(ModelBlock.class, new ModelBlock.Deserializer())
      .registerTypeAdapter(BlockPart.class, new BlockPart.Deserializer())
      .registerTypeAdapter(BlockPartFace.class, new BlockPartFace.Deserializer())
      .registerTypeAdapter(BlockFaceUV.class, new BlockFaceUV.Deserializer())
      .registerTypeAdapter(ItemTransformVec3f.class, new ItemTransformVec3f.Deserializer())
      .registerTypeAdapter(ItemCameraTransforms.class, new ItemCameraTransforms.Deserializer())
      .registerTypeAdapter(ItemOverride.class, new ItemOverride.Deserializer())
      .create();
   private final List<BlockPart> field_178314_g;
   private final boolean field_178321_h;
   private final boolean field_178322_i;
   private final ItemCameraTransforms field_178320_j;
   private final List<ItemOverride> field_187968_k;
   public String field_178317_b = "";
   @VisibleForTesting
   protected final Map<String, String> field_178318_c;
   @VisibleForTesting
   ModelBlock field_178315_d;
   @VisibleForTesting
   ResourceLocation field_178316_e;

   public static ModelBlock func_178307_a(Reader var0) {
      return JsonUtils.func_193839_a(field_178319_a, ☃, ModelBlock.class);
   }

   public static ModelBlock func_178294_a(String var0) {
      return func_178307_a(new StringReader(☃));
   }

   public ModelBlock(
      @Nullable ResourceLocation var1,
      List<BlockPart> var2,
      Map<String, String> var3,
      boolean var4,
      boolean var5,
      ItemCameraTransforms var6,
      List<ItemOverride> var7
   ) {
      this.field_178314_g = ☃;
      this.field_178322_i = ☃;
      this.field_178321_h = ☃;
      this.field_178318_c = ☃;
      this.field_178316_e = ☃;
      this.field_178320_j = ☃;
      this.field_187968_k = ☃;
   }

   public List<BlockPart> func_178298_a() {
      return this.field_178314_g.isEmpty() && this.func_178295_k() ? this.field_178315_d.func_178298_a() : this.field_178314_g;
   }

   private boolean func_178295_k() {
      return this.field_178315_d != null;
   }

   public boolean func_178309_b() {
      return this.func_178295_k() ? this.field_178315_d.func_178309_b() : this.field_178322_i;
   }

   public boolean func_178311_c() {
      return this.field_178321_h;
   }

   public boolean func_178303_d() {
      return this.field_178316_e == null || this.field_178315_d != null && this.field_178315_d.func_178303_d();
   }

   private void func_209566_a(Function<ResourceLocation, IUnbakedModel> var1) {
      if (this.field_178316_e != null) {
         IUnbakedModel ☃ = (IUnbakedModel)☃.apply(this.field_178316_e);
         if (☃ != null) {
            if (!(☃ instanceof ModelBlock)) {
               throw new IllegalStateException("BlockModel parent has to be a block model.");
            }

            this.field_178315_d = (ModelBlock)☃;
         }
      }
   }

   public List<ItemOverride> func_187966_f() {
      return this.field_187968_k;
   }

   private ItemOverrideList func_209568_a(ModelBlock var1, Function<ResourceLocation, IUnbakedModel> var2, Function<ResourceLocation, TextureAtlasSprite> var3) {
      return this.field_187968_k.isEmpty() ? ItemOverrideList.field_188022_a : new ItemOverrideList(☃, ☃, ☃, this.field_187968_k);
   }

   @Override
   public Collection<ResourceLocation> func_187965_e() {
      Set<ResourceLocation> ☃ = Sets.<ResourceLocation>newHashSet();

      for(ItemOverride ☃x : this.field_187968_k) {
         ☃.add(☃x.func_188026_a());
      }

      if (this.field_178316_e != null) {
         ☃.add(this.field_178316_e);
      }

      return ☃;
   }

   @Override
   public Collection<ResourceLocation> func_209559_a(Function<ResourceLocation, IUnbakedModel> var1, Set<String> var2) {
      if (!this.func_178303_d()) {
         Set<ModelBlock> ☃ = Sets.<ModelBlock>newLinkedHashSet();
         ModelBlock ☃x = this;

         do {
            ☃.add(☃x);
            ☃x.func_209566_a(☃);
            if (☃.contains(☃x.field_178315_d)) {
               field_178313_f.warn(
                  "Found 'parent' loop while loading model '{}' in chain: {} -> {}",
                  ☃x.field_178317_b,
                  ☃.stream().map(var0 -> var0.field_178317_b).collect(Collectors.joining(" -> ")),
                  ☃x.field_178315_d.field_178317_b
               );
               ☃x.field_178316_e = ModelBakery.field_177604_a;
               ☃x.func_209566_a(☃);
            }

            ☃x = ☃x.field_178315_d;
         } while(!☃x.func_178303_d());
      }

      Set<ResourceLocation> ☃ = Sets.<ResourceLocation>newHashSet(new ResourceLocation(this.func_178308_c("particle")));

      for(BlockPart ☃x : this.func_178298_a()) {
         for(BlockPartFace ☃xx : ☃x.field_178240_c.values()) {
            String ☃xxx = this.func_178308_c(☃xx.field_178242_d);
            if (Objects.equals(☃xxx, MissingTextureSprite.func_195677_a().func_195668_m().toString())) {
               ☃.add(String.format("%s in %s", ☃xx.field_178242_d, this.field_178317_b));
            }

            ☃.add(new ResourceLocation(☃xxx));
         }
      }

      this.field_187968_k.forEach(var4x -> {
         IUnbakedModel ☃ = (IUnbakedModel)☃.apply(var4x.func_188026_a());
         if (!Objects.equals(☃, this)) {
            ☃.addAll(☃.func_209559_a(☃, ☃));
         }
      });
      if (this.func_178310_f() == ModelBakery.field_177606_o) {
         ItemModelGenerator.field_178398_a.forEach(var2x -> ☃.add(new ResourceLocation(this.func_178308_c(var2x))));
      }

      return ☃;
   }

   @Override
   public IBakedModel func_209558_a(
      Function<ResourceLocation, IUnbakedModel> var1, Function<ResourceLocation, TextureAtlasSprite> var2, ModelRotation var3, boolean var4
   ) {
      return this.func_209565_a(this, ☃, ☃, ☃, ☃);
   }

   private IBakedModel func_209565_a(
      ModelBlock var1, Function<ResourceLocation, IUnbakedModel> var2, Function<ResourceLocation, TextureAtlasSprite> var3, ModelRotation var4, boolean var5
   ) {
      ModelBlock ☃ = this.func_178310_f();
      if (☃ == ModelBakery.field_177606_o) {
         return field_209571_g.func_209579_a(☃, this).func_209565_a(☃, ☃, ☃, ☃, ☃);
      } else if (☃ == ModelBakery.field_177616_r) {
         return new BuiltInModel(this.func_181682_g(), this.func_209568_a(☃, ☃, ☃));
      } else {
         TextureAtlasSprite ☃ = (TextureAtlasSprite)☃.apply(new ResourceLocation(this.func_178308_c("particle")));
         SimpleBakedModel.Builder ☃x = new SimpleBakedModel.Builder(this, this.func_209568_a(☃, ☃, ☃)).func_177646_a(☃);

         for(BlockPart ☃xx : this.func_178298_a()) {
            for(EnumFacing ☃xxx : ☃xx.field_178240_c.keySet()) {
               BlockPartFace ☃xxxx = (BlockPartFace)☃xx.field_178240_c.get(☃xxx);
               TextureAtlasSprite ☃xxxxx = (TextureAtlasSprite)☃.apply(new ResourceLocation(this.func_178308_c(☃xxxx.field_178242_d)));
               if (☃xxxx.field_178244_b == null) {
                  ☃x.func_177648_a(func_209567_a(☃xx, ☃xxxx, ☃xxxxx, ☃xxx, ☃, ☃));
               } else {
                  ☃x.func_177650_a(☃.func_177523_a(☃xxxx.field_178244_b), func_209567_a(☃xx, ☃xxxx, ☃xxxxx, ☃xxx, ☃, ☃));
               }
            }
         }

         return ☃x.func_177645_b();
      }
   }

   private static BakedQuad func_209567_a(BlockPart var0, BlockPartFace var1, TextureAtlasSprite var2, EnumFacing var3, ModelRotation var4, boolean var5) {
      return field_209572_h.func_199332_a(☃.field_178241_a, ☃.field_178239_b, ☃, ☃, ☃, ☃, ☃.field_178237_d, ☃, ☃.field_178238_e);
   }

   public boolean func_178300_b(String var1) {
      return !MissingTextureSprite.func_195677_a().func_195668_m().toString().equals(this.func_178308_c(☃));
   }

   public String func_178308_c(String var1) {
      if (!this.func_178304_d(☃)) {
         ☃ = '#' + ☃;
      }

      return this.func_178302_a(☃, new ModelBlock.Bookkeep(this));
   }

   private String func_178302_a(String var1, ModelBlock.Bookkeep var2) {
      if (this.func_178304_d(☃)) {
         if (this == ☃.field_178323_b) {
            field_178313_f.warn("Unable to resolve texture due to upward reference: {} in {}", ☃, this.field_178317_b);
            return MissingTextureSprite.func_195677_a().func_195668_m().toString();
         } else {
            String ☃ = (String)this.field_178318_c.get(☃.substring(1));
            if (☃ == null && this.func_178295_k()) {
               ☃ = this.field_178315_d.func_178302_a(☃, ☃);
            }

            ☃.field_178323_b = this;
            if (☃ != null && this.func_178304_d(☃)) {
               ☃ = ☃.field_178324_a.func_178302_a(☃, ☃);
            }

            return ☃ != null && !this.func_178304_d(☃) ? ☃ : MissingTextureSprite.func_195677_a().func_195668_m().toString();
         }
      } else {
         return ☃;
      }
   }

   private boolean func_178304_d(String var1) {
      return ☃.charAt(0) == '#';
   }

   public ModelBlock func_178310_f() {
      return this.func_178295_k() ? this.field_178315_d.func_178310_f() : this;
   }

   public ItemCameraTransforms func_181682_g() {
      ItemTransformVec3f ☃ = this.func_181681_a(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
      ItemTransformVec3f ☃x = this.func_181681_a(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
      ItemTransformVec3f ☃xx = this.func_181681_a(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND);
      ItemTransformVec3f ☃xxx = this.func_181681_a(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND);
      ItemTransformVec3f ☃xxxx = this.func_181681_a(ItemCameraTransforms.TransformType.HEAD);
      ItemTransformVec3f ☃xxxxx = this.func_181681_a(ItemCameraTransforms.TransformType.GUI);
      ItemTransformVec3f ☃xxxxxx = this.func_181681_a(ItemCameraTransforms.TransformType.GROUND);
      ItemTransformVec3f ☃xxxxxxx = this.func_181681_a(ItemCameraTransforms.TransformType.FIXED);
      return new ItemCameraTransforms(☃, ☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx);
   }

   private ItemTransformVec3f func_181681_a(ItemCameraTransforms.TransformType var1) {
      return this.field_178315_d != null && !this.field_178320_j.func_181687_c(☃) ? this.field_178315_d.func_181681_a(☃) : this.field_178320_j.func_181688_b(☃);
   }

   static final class Bookkeep {
      public final ModelBlock field_178324_a;
      public ModelBlock field_178323_b;

      private Bookkeep(ModelBlock var1) {
         this.field_178324_a = ☃;
      }
   }

   public static class Deserializer implements JsonDeserializer<ModelBlock> {
      public ModelBlock deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = ☃.getAsJsonObject();
         List<BlockPart> ☃x = this.func_178325_a(☃, ☃);
         String ☃xx = this.func_178326_c(☃);
         Map<String, String> ☃xxx = this.func_178329_b(☃);
         boolean ☃xxxx = this.func_178328_a(☃);
         ItemCameraTransforms ☃xxxxx = ItemCameraTransforms.field_178357_a;
         if (☃.has("display")) {
            JsonObject ☃xxxxxx = JsonUtils.func_152754_s(☃, "display");
            ☃xxxxx = ☃.deserialize(☃xxxxxx, ItemCameraTransforms.class);
         }

         List<ItemOverride> ☃ = this.func_187964_a(☃, ☃);
         ResourceLocation ☃x = ☃xx.isEmpty() ? null : new ResourceLocation(☃xx);
         return new ModelBlock(☃x, ☃x, ☃xxx, ☃xxxx, true, ☃xxxxx, ☃);
      }

      protected List<ItemOverride> func_187964_a(JsonDeserializationContext var1, JsonObject var2) {
         List<ItemOverride> ☃ = Lists.<ItemOverride>newArrayList();
         if (☃.has("overrides")) {
            for(JsonElement ☃x : JsonUtils.func_151214_t(☃, "overrides")) {
               ☃.add(☃.deserialize(☃x, ItemOverride.class));
            }
         }

         return ☃;
      }

      private Map<String, String> func_178329_b(JsonObject var1) {
         Map<String, String> ☃ = Maps.newHashMap();
         if (☃.has("textures")) {
            JsonObject ☃x = ☃.getAsJsonObject("textures");

            for(Entry<String, JsonElement> ☃xx : ☃x.entrySet()) {
               ☃.put(☃xx.getKey(), ((JsonElement)☃xx.getValue()).getAsString());
            }
         }

         return ☃;
      }

      private String func_178326_c(JsonObject var1) {
         return JsonUtils.func_151219_a(☃, "parent", "");
      }

      protected boolean func_178328_a(JsonObject var1) {
         return JsonUtils.func_151209_a(☃, "ambientocclusion", true);
      }

      protected List<BlockPart> func_178325_a(JsonDeserializationContext var1, JsonObject var2) {
         List<BlockPart> ☃ = Lists.<BlockPart>newArrayList();
         if (☃.has("elements")) {
            for(JsonElement ☃x : JsonUtils.func_151214_t(☃, "elements")) {
               ☃.add(☃.deserialize(☃x, BlockPart.class));
            }
         }

         return ☃;
      }
   }
}
