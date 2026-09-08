package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class SimpleBakedModel implements IBakedModel {
   protected final List<BakedQuad> field_177563_a;
   protected final Map<EnumFacing, List<BakedQuad>> field_177561_b;
   protected final boolean field_177562_c;
   protected final boolean field_177559_d;
   protected final TextureAtlasSprite field_177560_e;
   protected final ItemCameraTransforms field_177558_f;
   protected final ItemOverrideList field_188620_g;

   public SimpleBakedModel(
      List<BakedQuad> var1,
      Map<EnumFacing, List<BakedQuad>> var2,
      boolean var3,
      boolean var4,
      TextureAtlasSprite var5,
      ItemCameraTransforms var6,
      ItemOverrideList var7
   ) {
      this.field_177563_a = ☃;
      this.field_177561_b = ☃;
      this.field_177562_c = ☃;
      this.field_177559_d = ☃;
      this.field_177560_e = ☃;
      this.field_177558_f = ☃;
      this.field_188620_g = ☃;
   }

   @Override
   public List<BakedQuad> func_200117_a(@Nullable IBlockState var1, @Nullable EnumFacing var2, Random var3) {
      return ☃ == null ? this.field_177563_a : (List)this.field_177561_b.get(☃);
   }

   @Override
   public boolean func_177555_b() {
      return this.field_177562_c;
   }

   @Override
   public boolean func_177556_c() {
      return this.field_177559_d;
   }

   @Override
   public boolean func_188618_c() {
      return false;
   }

   @Override
   public TextureAtlasSprite func_177554_e() {
      return this.field_177560_e;
   }

   @Override
   public ItemCameraTransforms func_177552_f() {
      return this.field_177558_f;
   }

   @Override
   public ItemOverrideList func_188617_f() {
      return this.field_188620_g;
   }

   public static class Builder {
      private final List<BakedQuad> field_177656_a = Lists.<BakedQuad>newArrayList();
      private final Map<EnumFacing, List<BakedQuad>> field_177654_b = Maps.newEnumMap(EnumFacing.class);
      private final ItemOverrideList field_188646_c;
      private final boolean field_177655_c;
      private TextureAtlasSprite field_177652_d;
      private final boolean field_177653_e;
      private final ItemCameraTransforms field_177651_f;

      public Builder(ModelBlock var1, ItemOverrideList var2) {
         this(☃.func_178309_b(), ☃.func_178311_c(), ☃.func_181682_g(), ☃);
      }

      public Builder(IBlockState var1, IBakedModel var2, TextureAtlasSprite var3, Random var4, long var5) {
         this(☃.func_177555_b(), ☃.func_177556_c(), ☃.func_177552_f(), ☃.func_188617_f());
         this.field_177652_d = ☃.func_177554_e();

         for(EnumFacing ☃ : EnumFacing.values()) {
            ☃.setSeed(☃);

            for(BakedQuad ☃x : ☃.func_200117_a(☃, ☃, ☃)) {
               this.func_177650_a(☃, new BakedQuadRetextured(☃x, ☃));
            }
         }

         ☃.setSeed(☃);

         for(BakedQuad ☃ : ☃.func_200117_a(☃, null, ☃)) {
            this.func_177648_a(new BakedQuadRetextured(☃, ☃));
         }
      }

      private Builder(boolean var1, boolean var2, ItemCameraTransforms var3, ItemOverrideList var4) {
         for(EnumFacing ☃ : EnumFacing.values()) {
            this.field_177654_b.put(☃, Lists.newArrayList());
         }

         this.field_188646_c = ☃;
         this.field_177655_c = ☃;
         this.field_177653_e = ☃;
         this.field_177651_f = ☃;
      }

      public SimpleBakedModel.Builder func_177650_a(EnumFacing var1, BakedQuad var2) {
         ((List)this.field_177654_b.get(☃)).add(☃);
         return this;
      }

      public SimpleBakedModel.Builder func_177648_a(BakedQuad var1) {
         this.field_177656_a.add(☃);
         return this;
      }

      public SimpleBakedModel.Builder func_177646_a(TextureAtlasSprite var1) {
         this.field_177652_d = ☃;
         return this;
      }

      public IBakedModel func_177645_b() {
         if (this.field_177652_d == null) {
            throw new RuntimeException("Missing particle!");
         } else {
            return new SimpleBakedModel(
               this.field_177656_a,
               this.field_177654_b,
               this.field_177655_c,
               this.field_177653_e,
               this.field_177652_d,
               this.field_177651_f,
               this.field_188646_c
            );
         }
      }
   }
}
