package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemOverrideList {
   public static final ItemOverrideList field_188022_a = new ItemOverrideList();
   private final List<ItemOverride> field_188023_b = Lists.<ItemOverride>newArrayList();
   private final List<IBakedModel> field_209582_c;

   private ItemOverrideList() {
      this.field_209582_c = Collections.emptyList();
   }

   public ItemOverrideList(
      ModelBlock var1, Function<ResourceLocation, IUnbakedModel> var2, Function<ResourceLocation, TextureAtlasSprite> var3, List<ItemOverride> var4
   ) {
      this.field_209582_c = (List)☃.stream().map(var3x -> {
         IUnbakedModel ☃ = (IUnbakedModel)☃.apply(var3x.func_188026_a());
         return Objects.equals(☃, ☃) ? null : ☃.func_209558_a(☃, ☃, ModelRotation.X0_Y0, false);
      }).collect(Collectors.toList());
      Collections.reverse(this.field_209582_c);

      for(int ☃ = ☃.size() - 1; ☃ >= 0; --☃) {
         this.field_188023_b.add(☃.get(☃));
      }
   }

   @Nullable
   public IBakedModel func_209581_a(IBakedModel var1, ItemStack var2, @Nullable World var3, @Nullable EntityLivingBase var4) {
      if (!this.field_188023_b.isEmpty()) {
         for(int ☃ = 0; ☃ < this.field_188023_b.size(); ++☃) {
            ItemOverride ☃x = (ItemOverride)this.field_188023_b.get(☃);
            if (☃x.func_188027_a(☃, ☃, ☃)) {
               IBakedModel ☃xx = (IBakedModel)this.field_209582_c.get(☃);
               if (☃xx == null) {
                  return ☃;
               }

               return ☃xx;
            }
         }
      }

      return ☃;
   }
}
