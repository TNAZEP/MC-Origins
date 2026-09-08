package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.state.IProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class BlockModelShapes {
   private final Map<IBlockState, IBakedModel> field_178129_a = Maps.<IBlockState, IBakedModel>newIdentityHashMap();
   private final ModelManager field_178128_c;

   public BlockModelShapes(ModelManager var1) {
      this.field_178128_c = ☃;
   }

   public TextureAtlasSprite func_178122_a(IBlockState var1) {
      return this.func_178125_b(☃).func_177554_e();
   }

   public IBakedModel func_178125_b(IBlockState var1) {
      IBakedModel ☃ = (IBakedModel)this.field_178129_a.get(☃);
      if (☃ == null) {
         ☃ = this.field_178128_c.func_174951_a();
      }

      return ☃;
   }

   public ModelManager func_178126_b() {
      return this.field_178128_c;
   }

   public void func_178124_c() {
      this.field_178129_a.clear();

      for(Block ☃ : IRegistry.field_212618_g) {
         ☃.func_176194_O().func_177619_a().forEach(var1 -> {
         });
      }
   }

   public static ModelResourceLocation func_209554_c(IBlockState var0) {
      return func_209553_a(IRegistry.field_212618_g.func_177774_c(☃.func_177230_c()), ☃);
   }

   public static ModelResourceLocation func_209553_a(ResourceLocation var0, IBlockState var1) {
      return new ModelResourceLocation(☃, func_209552_a(☃.func_206871_b()));
   }

   public static String func_209552_a(Map<IProperty<?>, Comparable<?>> var0) {
      StringBuilder ☃ = new StringBuilder();

      for(Entry<IProperty<?>, Comparable<?>> ☃x : ☃.entrySet()) {
         if (☃.length() != 0) {
            ☃.append(',');
         }

         IProperty<?> ☃xx = (IProperty)☃x.getKey();
         ☃.append(☃xx.func_177701_a());
         ☃.append('=');
         ☃.append(func_209555_a(☃xx, (Comparable<?>)☃x.getValue()));
      }

      return ☃.toString();
   }

   private static <T extends Comparable<T>> String func_209555_a(IProperty<T> var0, Comparable<?> var1) {
      return ☃.func_177702_a((T)☃);
   }
}
