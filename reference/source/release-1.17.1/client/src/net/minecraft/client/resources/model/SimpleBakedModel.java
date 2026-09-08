package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleBakedModel implements BakedModel {
   protected final List<BakedQuad> unculledFaces;
   protected final Map<Direction, List<BakedQuad>> culledFaces;
   protected final boolean hasAmbientOcclusion;
   protected final boolean isGui3d;
   protected final boolean usesBlockLight;
   protected final TextureAtlasSprite particleIcon;
   protected final ItemTransforms transforms;
   protected final ItemOverrides overrides;

   public SimpleBakedModel(
      List<BakedQuad> var1,
      Map<Direction, List<BakedQuad>> var2,
      boolean var3,
      boolean var4,
      boolean var5,
      TextureAtlasSprite var6,
      ItemTransforms var7,
      ItemOverrides var8
   ) {
      this.unculledFaces = â˜ƒ;
      this.culledFaces = â˜ƒ;
      this.hasAmbientOcclusion = â˜ƒ;
      this.isGui3d = â˜ƒ;
      this.usesBlockLight = â˜ƒ;
      this.particleIcon = â˜ƒ;
      this.transforms = â˜ƒ;
      this.overrides = â˜ƒ;
   }

   @Override
   public List<BakedQuad> getQuads(@Nullable BlockState var1, @Nullable Direction var2, Random var3) {
      return â˜ƒ == null ? this.unculledFaces : (List)this.culledFaces.get(â˜ƒ);
   }

   @Override
   public boolean useAmbientOcclusion() {
      return this.hasAmbientOcclusion;
   }

   @Override
   public boolean isGui3d() {
      return this.isGui3d;
   }

   @Override
   public boolean usesBlockLight() {
      return this.usesBlockLight;
   }

   @Override
   public boolean isCustomRenderer() {
      return false;
   }

   @Override
   public TextureAtlasSprite getParticleIcon() {
      return this.particleIcon;
   }

   @Override
   public ItemTransforms getTransforms() {
      return this.transforms;
   }

   @Override
   public ItemOverrides getOverrides() {
      return this.overrides;
   }

   public static class Builder {
      private final List<BakedQuad> unculledFaces = Lists.<BakedQuad>newArrayList();
      private final Map<Direction, List<BakedQuad>> culledFaces = Maps.newEnumMap(Direction.class);
      private final ItemOverrides overrides;
      private final boolean hasAmbientOcclusion;
      private TextureAtlasSprite particleIcon;
      private final boolean usesBlockLight;
      private final boolean isGui3d;
      private final ItemTransforms transforms;

      public Builder(BlockModel var1, ItemOverrides var2, boolean var3) {
         this(â˜ƒ.hasAmbientOcclusion(), â˜ƒ.getGuiLight().lightLikeBlock(), â˜ƒ, â˜ƒ.getTransforms(), â˜ƒ);
      }

      private Builder(boolean var1, boolean var2, boolean var3, ItemTransforms var4, ItemOverrides var5) {
         for(Direction â˜ƒ : Direction.values()) {
            this.culledFaces.put(â˜ƒ, Lists.newArrayList());
         }

         this.overrides = â˜ƒ;
         this.hasAmbientOcclusion = â˜ƒ;
         this.usesBlockLight = â˜ƒ;
         this.isGui3d = â˜ƒ;
         this.transforms = â˜ƒ;
      }

      public SimpleBakedModel.Builder addCulledFace(Direction var1, BakedQuad var2) {
         ((List)this.culledFaces.get(â˜ƒ)).add(â˜ƒ);
         return this;
      }

      public SimpleBakedModel.Builder addUnculledFace(BakedQuad var1) {
         this.unculledFaces.add(â˜ƒ);
         return this;
      }

      public SimpleBakedModel.Builder particle(TextureAtlasSprite var1) {
         this.particleIcon = â˜ƒ;
         return this;
      }

      public SimpleBakedModel.Builder item() {
         return this;
      }

      public BakedModel build() {
         if (this.particleIcon == null) {
            throw new RuntimeException("Missing particle!");
         } else {
            return new SimpleBakedModel(
               this.unculledFaces,
               this.culledFaces,
               this.hasAmbientOcclusion,
               this.usesBlockLight,
               this.isGui3d,
               this.particleIcon,
               this.transforms,
               this.overrides
            );
         }
      }
   }
}
