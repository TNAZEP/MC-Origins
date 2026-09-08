package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Util;
import org.apache.commons.lang3.tuple.Pair;

public class MultipartBakedModel implements IBakedModel {
   private final List<Pair<Predicate<IBlockState>, IBakedModel>> field_188626_f;
   protected final boolean field_188621_a;
   protected final boolean field_188622_b;
   protected final TextureAtlasSprite field_188623_c;
   protected final ItemCameraTransforms field_188624_d;
   protected final ItemOverrideList field_188625_e;
   private final Map<IBlockState, BitSet> field_210277_g = new Object2ObjectOpenCustomHashMap<>(Util.func_212443_g());

   public MultipartBakedModel(List<Pair<Predicate<IBlockState>, IBakedModel>> var1) {
      this.field_188626_f = ☃;
      IBakedModel ☃ = (IBakedModel)((Pair)☃.iterator().next()).getRight();
      this.field_188621_a = ☃.func_177555_b();
      this.field_188622_b = ☃.func_177556_c();
      this.field_188623_c = ☃.func_177554_e();
      this.field_188624_d = ☃.func_177552_f();
      this.field_188625_e = ☃.func_188617_f();
   }

   @Override
   public List<BakedQuad> func_200117_a(@Nullable IBlockState var1, @Nullable EnumFacing var2, Random var3) {
      if (☃ == null) {
         return Collections.emptyList();
      } else {
         BitSet ☃ = (BitSet)this.field_210277_g.get(☃);
         if (☃ == null) {
            ☃ = new BitSet();

            for(int ☃x = 0; ☃x < this.field_188626_f.size(); ++☃x) {
               Pair<Predicate<IBlockState>, IBakedModel> ☃xx = (Pair)this.field_188626_f.get(☃x);
               if (((Predicate)☃xx.getLeft()).test(☃)) {
                  ☃.set(☃x);
               }
            }

            this.field_210277_g.put(☃, ☃);
         }

         List<BakedQuad> ☃ = Lists.<BakedQuad>newArrayList();
         long ☃x = ☃.nextLong();

         for(int ☃xx = 0; ☃xx < ☃.length(); ++☃xx) {
            if (☃.get(☃xx)) {
               ☃.addAll(((IBakedModel)((Pair)this.field_188626_f.get(☃xx)).getRight()).func_200117_a(☃, ☃, new Random(☃x)));
            }
         }

         return ☃;
      }
   }

   @Override
   public boolean func_177555_b() {
      return this.field_188621_a;
   }

   @Override
   public boolean func_177556_c() {
      return this.field_188622_b;
   }

   @Override
   public boolean func_188618_c() {
      return false;
   }

   @Override
   public TextureAtlasSprite func_177554_e() {
      return this.field_188623_c;
   }

   @Override
   public ItemCameraTransforms func_177552_f() {
      return this.field_188624_d;
   }

   @Override
   public ItemOverrideList func_188617_f() {
      return this.field_188625_e;
   }

   public static class Builder {
      private final List<Pair<Predicate<IBlockState>, IBakedModel>> field_188649_a = Lists.<Pair<Predicate<IBlockState>, IBakedModel>>newArrayList();

      public void func_188648_a(Predicate<IBlockState> var1, IBakedModel var2) {
         this.field_188649_a.add(Pair.of(☃, ☃));
      }

      public IBakedModel func_188647_a() {
         return new MultipartBakedModel(this.field_188649_a);
      }
   }
}
