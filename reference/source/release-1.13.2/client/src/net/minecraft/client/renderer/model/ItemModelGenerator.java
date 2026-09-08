package net.minecraft.client.renderer.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class ItemModelGenerator {
   public static final List<String> field_178398_a = Lists.newArrayList("layer0", "layer1", "layer2", "layer3", "layer4");

   public ModelBlock func_209579_a(Function<ResourceLocation, TextureAtlasSprite> var1, ModelBlock var2) {
      Map<String, String> ☃ = Maps.newHashMap();
      List<BlockPart> ☃x = Lists.<BlockPart>newArrayList();

      for(int ☃xx = 0; ☃xx < field_178398_a.size(); ++☃xx) {
         String ☃xxx = (String)field_178398_a.get(☃xx);
         if (!☃.func_178300_b(☃xxx)) {
            break;
         }

         String ☃xxx = ☃.func_178308_c(☃xxx);
         ☃.put(☃xxx, ☃xxx);
         TextureAtlasSprite ☃xxxx = (TextureAtlasSprite)☃.apply(new ResourceLocation(☃xxx));
         ☃x.addAll(this.func_178394_a(☃xx, ☃xxx, ☃xxxx));
      }

      ☃.put("particle", ☃.func_178300_b("particle") ? ☃.func_178308_c("particle") : (String)☃.get("layer0"));
      ModelBlock ☃xx = new ModelBlock(null, ☃x, ☃, false, false, ☃.func_181682_g(), ☃.func_187966_f());
      ☃xx.field_178317_b = ☃.field_178317_b;
      return ☃xx;
   }

   private List<BlockPart> func_178394_a(int var1, String var2, TextureAtlasSprite var3) {
      Map<EnumFacing, BlockPartFace> ☃ = Maps.<EnumFacing, BlockPartFace>newHashMap();
      ☃.put(EnumFacing.SOUTH, new BlockPartFace(null, ☃, ☃, new BlockFaceUV(new float[]{0.0F, 0.0F, 16.0F, 16.0F}, 0)));
      ☃.put(EnumFacing.NORTH, new BlockPartFace(null, ☃, ☃, new BlockFaceUV(new float[]{16.0F, 0.0F, 0.0F, 16.0F}, 0)));
      List<BlockPart> ☃x = Lists.<BlockPart>newArrayList();
      ☃x.add(new BlockPart(new Vector3f(0.0F, 0.0F, 7.5F), new Vector3f(16.0F, 16.0F, 8.5F), ☃, null, true));
      ☃x.addAll(this.func_178397_a(☃, ☃, ☃));
      return ☃x;
   }

   private List<BlockPart> func_178397_a(TextureAtlasSprite var1, String var2, int var3) {
      float ☃ = (float)☃.func_94211_a();
      float ☃x = (float)☃.func_94216_b();
      List<BlockPart> ☃xx = Lists.<BlockPart>newArrayList();

      for(ItemModelGenerator.Span ☃xxx : this.func_178393_a(☃)) {
         float ☃xxxx = 0.0F;
         float ☃xxxxx = 0.0F;
         float ☃xxxxxx = 0.0F;
         float ☃xxxxxxx = 0.0F;
         float ☃xxxxxxxx = 0.0F;
         float ☃xxxxxxxxx = 0.0F;
         float ☃xxxxxxxxxx = 0.0F;
         float ☃xxxxxxxxxxx = 0.0F;
         float ☃xxxxxxxxxxxx = 0.0F;
         float ☃xxxxxxxxxxxxx = 0.0F;
         float ☃xxxxxxxxxxxxxx = (float)☃xxx.func_178385_b();
         float ☃xxxxxxxxxxxxxxx = (float)☃xxx.func_178384_c();
         float ☃xxxxxxxxxxxxxxxx = (float)☃xxx.func_178381_d();
         ItemModelGenerator.SpanFacing ☃xxxxxxxxxxxxxxxxx = ☃xxx.func_178383_a();
         switch(☃xxxxxxxxxxxxxxxxx) {
            case UP:
               ☃xxxxxxxx = ☃xxxxxxxxxxxxxx;
               ☃xxxx = ☃xxxxxxxxxxxxxx;
               ☃xxxxxx = ☃xxxxxxxxx = ☃xxxxxxxxxxxxxxx + 1.0F;
               ☃xxxxxxxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxxxxxxxxxx = 16.0F / ☃;
               ☃xxxxxxxxxxxxx = 16.0F / (☃x - 1.0F);
               break;
            case DOWN:
               ☃xxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxxxxxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxxxxxx = ☃xxxxxxxxxxxxxx;
               ☃xxxx = ☃xxxxxxxxxxxxxx;
               ☃xxxxxx = ☃xxxxxxxxx = ☃xxxxxxxxxxxxxxx + 1.0F;
               ☃xxxxx = ☃xxxxxxxxxxxxxxxx + 1.0F;
               ☃xxxxxxx = ☃xxxxxxxxxxxxxxxx + 1.0F;
               ☃xxxxxxxxxxxx = 16.0F / ☃;
               ☃xxxxxxxxxxxxx = 16.0F / (☃x - 1.0F);
               break;
            case LEFT:
               ☃xxxxxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxxxxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxxxxxxxxx = ☃xxxxxxxxxxxxxx;
               ☃xxxxx = ☃xxxxxxxxxxxxxx;
               ☃xxxxxxx = ☃xxxxxxxxxx = ☃xxxxxxxxxxxxxxx + 1.0F;
               ☃xxxxxxxxxxxx = 16.0F / (☃ - 1.0F);
               ☃xxxxxxxxxxxxx = 16.0F / ☃x;
               break;
            case RIGHT:
               ☃xxxxxxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxxxxxx = ☃xxxxxxxxxxxxxxxx;
               ☃xxxx = ☃xxxxxxxxxxxxxxxx + 1.0F;
               ☃xxxxxx = ☃xxxxxxxxxxxxxxxx + 1.0F;
               ☃xxxxxxxxxxx = ☃xxxxxxxxxxxxxx;
               ☃xxxxx = ☃xxxxxxxxxxxxxx;
               ☃xxxxxxx = ☃xxxxxxxxxx = ☃xxxxxxxxxxxxxxx + 1.0F;
               ☃xxxxxxxxxxxx = 16.0F / (☃ - 1.0F);
               ☃xxxxxxxxxxxxx = 16.0F / ☃x;
         }

         float ☃xxxx = 16.0F / ☃;
         float ☃xxxxx = 16.0F / ☃x;
         ☃xxxx *= ☃xxxx;
         ☃xxxxxx *= ☃xxxx;
         ☃xxxxx *= ☃xxxxx;
         ☃xxxxxxx *= ☃xxxxx;
         ☃xxxxx = 16.0F - ☃xxxxx;
         ☃xxxxxxx = 16.0F - ☃xxxxxxx;
         ☃xxxxxxxx *= ☃xxxxxxxxxxxx;
         ☃xxxxxxxxx *= ☃xxxxxxxxxxxx;
         ☃xxxxxxxxxx *= ☃xxxxxxxxxxxxx;
         ☃xxxxxxxxxxx *= ☃xxxxxxxxxxxxx;
         Map<EnumFacing, BlockPartFace> ☃xxxxxx = Maps.<EnumFacing, BlockPartFace>newHashMap();
         ☃xxxxxx.put(
            ☃xxxxxxxxxxxxxxxxx.func_178367_a(),
            new BlockPartFace(null, ☃, ☃, new BlockFaceUV(new float[]{☃xxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxxx}, 0))
         );
         switch(☃xxxxxxxxxxxxxxxxx) {
            case UP:
               ☃xx.add(new BlockPart(new Vector3f(☃xxxx, ☃xxxxx, 7.5F), new Vector3f(☃xxxxxx, ☃xxxxx, 8.5F), ☃xxxxxx, null, true));
               break;
            case DOWN:
               ☃xx.add(new BlockPart(new Vector3f(☃xxxx, ☃xxxxxxx, 7.5F), new Vector3f(☃xxxxxx, ☃xxxxxxx, 8.5F), ☃xxxxxx, null, true));
               break;
            case LEFT:
               ☃xx.add(new BlockPart(new Vector3f(☃xxxx, ☃xxxxx, 7.5F), new Vector3f(☃xxxx, ☃xxxxxxx, 8.5F), ☃xxxxxx, null, true));
               break;
            case RIGHT:
               ☃xx.add(new BlockPart(new Vector3f(☃xxxxxx, ☃xxxxx, 7.5F), new Vector3f(☃xxxxxx, ☃xxxxxxx, 8.5F), ☃xxxxxx, null, true));
         }
      }

      return ☃xx;
   }

   private List<ItemModelGenerator.Span> func_178393_a(TextureAtlasSprite var1) {
      int ☃ = ☃.func_94211_a();
      int ☃x = ☃.func_94216_b();
      List<ItemModelGenerator.Span> ☃xx = Lists.<ItemModelGenerator.Span>newArrayList();

      for(int ☃xxx = 0; ☃xxx < ☃.func_110970_k(); ++☃xxx) {
         for(int ☃xxxx = 0; ☃xxxx < ☃x; ++☃xxxx) {
            for(int ☃xxxxx = 0; ☃xxxxx < ☃; ++☃xxxxx) {
               boolean ☃xxxxxx = !this.func_199339_a(☃, ☃xxx, ☃xxxxx, ☃xxxx, ☃, ☃x);
               this.func_199338_a(ItemModelGenerator.SpanFacing.UP, ☃xx, ☃, ☃xxx, ☃xxxxx, ☃xxxx, ☃, ☃x, ☃xxxxxx);
               this.func_199338_a(ItemModelGenerator.SpanFacing.DOWN, ☃xx, ☃, ☃xxx, ☃xxxxx, ☃xxxx, ☃, ☃x, ☃xxxxxx);
               this.func_199338_a(ItemModelGenerator.SpanFacing.LEFT, ☃xx, ☃, ☃xxx, ☃xxxxx, ☃xxxx, ☃, ☃x, ☃xxxxxx);
               this.func_199338_a(ItemModelGenerator.SpanFacing.RIGHT, ☃xx, ☃, ☃xxx, ☃xxxxx, ☃xxxx, ☃, ☃x, ☃xxxxxx);
            }
         }
      }

      return ☃xx;
   }

   private void func_199338_a(
      ItemModelGenerator.SpanFacing var1,
      List<ItemModelGenerator.Span> var2,
      TextureAtlasSprite var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      boolean var9
   ) {
      boolean ☃ = this.func_199339_a(☃, ☃, ☃ + ☃.func_178372_b(), ☃ + ☃.func_178371_c(), ☃, ☃) && ☃;
      if (☃) {
         this.func_178395_a(☃, ☃, ☃, ☃);
      }
   }

   private void func_178395_a(List<ItemModelGenerator.Span> var1, ItemModelGenerator.SpanFacing var2, int var3, int var4) {
      ItemModelGenerator.Span ☃ = null;

      for(ItemModelGenerator.Span ☃x : ☃) {
         if (☃x.func_178383_a() == ☃) {
            int ☃xx = ☃.func_178369_d() ? ☃ : ☃;
            if (☃x.func_178381_d() == ☃xx) {
               ☃ = ☃x;
               break;
            }
         }
      }

      int ☃x = ☃.func_178369_d() ? ☃ : ☃;
      int ☃xx = ☃.func_178369_d() ? ☃ : ☃;
      if (☃ == null) {
         ☃.add(new ItemModelGenerator.Span(☃, ☃xx, ☃x));
      } else {
         ☃.func_178382_a(☃xx);
      }
   }

   private boolean func_199339_a(TextureAtlasSprite var1, int var2, int var3, int var4, int var5, int var6) {
      return ☃ >= 0 && ☃ >= 0 && ☃ < ☃ && ☃ < ☃ ? ☃.func_195662_a(☃, ☃, ☃) : true;
   }

   static class Span {
      private final ItemModelGenerator.SpanFacing field_178389_a;
      private int field_178387_b;
      private int field_178388_c;
      private final int field_178386_d;

      public Span(ItemModelGenerator.SpanFacing var1, int var2, int var3) {
         this.field_178389_a = ☃;
         this.field_178387_b = ☃;
         this.field_178388_c = ☃;
         this.field_178386_d = ☃;
      }

      public void func_178382_a(int var1) {
         if (☃ < this.field_178387_b) {
            this.field_178387_b = ☃;
         } else if (☃ > this.field_178388_c) {
            this.field_178388_c = ☃;
         }
      }

      public ItemModelGenerator.SpanFacing func_178383_a() {
         return this.field_178389_a;
      }

      public int func_178385_b() {
         return this.field_178387_b;
      }

      public int func_178384_c() {
         return this.field_178388_c;
      }

      public int func_178381_d() {
         return this.field_178386_d;
      }
   }

   static enum SpanFacing {
      UP(EnumFacing.UP, 0, -1),
      DOWN(EnumFacing.DOWN, 0, 1),
      LEFT(EnumFacing.EAST, -1, 0),
      RIGHT(EnumFacing.WEST, 1, 0);

      private final EnumFacing field_178376_e;
      private final int field_178373_f;
      private final int field_178374_g;

      private SpanFacing(EnumFacing var3, int var4, int var5) {
         this.field_178376_e = ☃;
         this.field_178373_f = ☃;
         this.field_178374_g = ☃;
      }

      public EnumFacing func_178367_a() {
         return this.field_178376_e;
      }

      public int func_178372_b() {
         return this.field_178373_f;
      }

      public int func_178371_c() {
         return this.field_178374_g;
      }

      private boolean func_178369_d() {
         return this == DOWN || this == UP;
      }
   }
}
