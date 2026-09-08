package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;

public class MultiPartBakedModel implements BakedModel {
   private final List<Pair<Predicate<BlockState>, BakedModel>> selectors;
   protected final boolean hasAmbientOcclusion;
   protected final boolean isGui3d;
   protected final boolean usesBlockLight;
   protected final TextureAtlasSprite particleIcon;
   protected final ItemTransforms transforms;
   protected final ItemOverrides overrides;
   private final Map<BlockState, BitSet> selectorCache = new Object2ObjectOpenCustomHashMap<>(Util.identityStrategy());

   public MultiPartBakedModel(List<Pair<Predicate<BlockState>, BakedModel>> var1) {
      this.selectors = â˜ƒ;
      BakedModel â˜ƒ = (BakedModel)((Pair)â˜ƒ.iterator().next()).getRight();
      this.hasAmbientOcclusion = â˜ƒ.useAmbientOcclusion();
      this.isGui3d = â˜ƒ.isGui3d();
      this.usesBlockLight = â˜ƒ.usesBlockLight();
      this.particleIcon = â˜ƒ.getParticleIcon();
      this.transforms = â˜ƒ.getTransforms();
      this.overrides = â˜ƒ.getOverrides();
   }

   @Override
   public List<BakedQuad> getQuads(@Nullable BlockState var1, @Nullable Direction var2, Random var3) {
      if (â˜ƒ == null) {
         return Collections.emptyList();
      } else {
         BitSet â˜ƒ = (BitSet)this.selectorCache.get(â˜ƒ);
         if (â˜ƒ == null) {
            â˜ƒ = new BitSet();

            for(int â˜ƒx = 0; â˜ƒx < this.selectors.size(); ++â˜ƒx) {
               Pair<Predicate<BlockState>, BakedModel> â˜ƒxx = (Pair)this.selectors.get(â˜ƒx);
               if (((Predicate)â˜ƒxx.getLeft()).test(â˜ƒ)) {
                  â˜ƒ.set(â˜ƒx);
               }
            }

            this.selectorCache.put(â˜ƒ, â˜ƒ);
         }

         List<BakedQuad> â˜ƒ = Lists.<BakedQuad>newArrayList();
         long â˜ƒx = â˜ƒ.nextLong();

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.length(); ++â˜ƒxx) {
            if (â˜ƒ.get(â˜ƒxx)) {
               â˜ƒ.addAll(((BakedModel)((Pair)this.selectors.get(â˜ƒxx)).getRight()).getQuads(â˜ƒ, â˜ƒ, new Random(â˜ƒx)));
            }
         }

         return â˜ƒ;
      }
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
      private final List<Pair<Predicate<BlockState>, BakedModel>> selectors = Lists.<Pair<Predicate<BlockState>, BakedModel>>newArrayList();

      public void add(Predicate<BlockState> var1, BakedModel var2) {
         this.selectors.add(Pair.of(â˜ƒ, â˜ƒ));
      }

      public BakedModel build() {
         return new MultiPartBakedModel(this.selectors);
      }
   }
}
