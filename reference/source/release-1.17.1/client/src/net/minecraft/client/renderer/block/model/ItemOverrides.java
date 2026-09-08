package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemOverrides {
   public static final ItemOverrides EMPTY = new ItemOverrides();
   private final ItemOverrides.BakedOverride[] overrides;
   private final ResourceLocation[] properties;

   private ItemOverrides() {
      this.overrides = new ItemOverrides.BakedOverride[0];
      this.properties = new ResourceLocation[0];
   }

   public ItemOverrides(ModelBakery var1, BlockModel var2, Function<ResourceLocation, UnbakedModel> var3, List<ItemOverride> var4) {
      this.properties = (ResourceLocation[])â˜ƒ.stream()
         .flatMap(ItemOverride::getPredicates)
         .map(ItemOverride.Predicate::getProperty)
         .distinct()
         .toArray(var0 -> new ResourceLocation[var0]);
      Object2IntMap<ResourceLocation> â˜ƒ = new Object2IntOpenHashMap<>();

      for(int â˜ƒx = 0; â˜ƒx < this.properties.length; ++â˜ƒx) {
         â˜ƒ.put(this.properties[â˜ƒx], â˜ƒx);
      }

      List<ItemOverrides.BakedOverride> â˜ƒx = Lists.<ItemOverrides.BakedOverride>newArrayList();

      for(int â˜ƒxx = â˜ƒ.size() - 1; â˜ƒxx >= 0; --â˜ƒxx) {
         ItemOverride â˜ƒxxx = (ItemOverride)â˜ƒ.get(â˜ƒxx);
         BakedModel â˜ƒxxxx = this.bakeModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx);
         ItemOverrides.PropertyMatcher[] â˜ƒxxxxx = (ItemOverrides.PropertyMatcher[])â˜ƒxxx.getPredicates().map(var1x -> {
            int â˜ƒ = â˜ƒ.getInt(var1x.getProperty());
            return new ItemOverrides.PropertyMatcher(â˜ƒ, var1x.getValue());
         }).toArray(var0 -> new ItemOverrides.PropertyMatcher[var0]);
         â˜ƒx.add(new ItemOverrides.BakedOverride(â˜ƒxxxxx, â˜ƒxxxx));
      }

      this.overrides = (ItemOverrides.BakedOverride[])â˜ƒx.toArray(new ItemOverrides.BakedOverride[0]);
   }

   @Nullable
   private BakedModel bakeModel(ModelBakery var1, BlockModel var2, Function<ResourceLocation, UnbakedModel> var3, ItemOverride var4) {
      UnbakedModel â˜ƒ = (UnbakedModel)â˜ƒ.apply(â˜ƒ.getModel());
      return Objects.equals(â˜ƒ, â˜ƒ) ? null : â˜ƒ.bake(â˜ƒ.getModel(), BlockModelRotation.X0_Y0);
   }

   @Nullable
   public BakedModel resolve(BakedModel var1, ItemStack var2, @Nullable ClientLevel var3, @Nullable LivingEntity var4, int var5) {
      if (this.overrides.length != 0) {
         Item â˜ƒ = â˜ƒ.getItem();
         int â˜ƒx = this.properties.length;
         float[] â˜ƒxx = new float[â˜ƒx];

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒx; ++â˜ƒxxx) {
            ResourceLocation â˜ƒxxxx = this.properties[â˜ƒxxx];
            ItemPropertyFunction â˜ƒxxxxx = ItemProperties.getProperty(â˜ƒ, â˜ƒxxxx);
            if (â˜ƒxxxxx != null) {
               â˜ƒxx[â˜ƒxxx] = â˜ƒxxxxx.call(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            } else {
               â˜ƒxx[â˜ƒxxx] = Float.NEGATIVE_INFINITY;
            }
         }

         for(ItemOverrides.BakedOverride â˜ƒxxx : this.overrides) {
            if (â˜ƒxxx.test(â˜ƒxx)) {
               BakedModel â˜ƒxxxx = â˜ƒxxx.model;
               if (â˜ƒxxxx == null) {
                  return â˜ƒ;
               }

               return â˜ƒxxxx;
            }
         }
      }

      return â˜ƒ;
   }

   static class BakedOverride {
      private final ItemOverrides.PropertyMatcher[] matchers;
      @Nullable
      final BakedModel model;

      BakedOverride(ItemOverrides.PropertyMatcher[] var1, @Nullable BakedModel var2) {
         this.matchers = â˜ƒ;
         this.model = â˜ƒ;
      }

      boolean test(float[] var1) {
         for(ItemOverrides.PropertyMatcher â˜ƒ : this.matchers) {
            float â˜ƒx = â˜ƒ[â˜ƒ.index];
            if (â˜ƒx < â˜ƒ.value) {
               return false;
            }
         }

         return true;
      }
   }

   static class PropertyMatcher {
      public final int index;
      public final float value;

      PropertyMatcher(int var1, float var2) {
         this.index = â˜ƒ;
         this.value = â˜ƒ;
      }
   }
}
