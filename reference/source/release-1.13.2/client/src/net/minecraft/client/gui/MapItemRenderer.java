package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

public class MapItemRenderer implements AutoCloseable {
   private static final ResourceLocation field_148253_a = new ResourceLocation("textures/map/map_icons.png");
   private final TextureManager field_148251_b;
   private final Map<String, MapItemRenderer.Instance> field_148252_c = Maps.newHashMap();

   public MapItemRenderer(TextureManager var1) {
      this.field_148251_b = ☃;
   }

   public void func_148246_a(MapData var1) {
      this.func_148248_b(☃).func_148236_a();
   }

   public void func_148250_a(MapData var1, boolean var2) {
      this.func_148248_b(☃).func_148237_a(☃);
   }

   private MapItemRenderer.Instance func_148248_b(MapData var1) {
      MapItemRenderer.Instance ☃ = (MapItemRenderer.Instance)this.field_148252_c.get(☃.func_195925_e());
      if (☃ == null) {
         ☃ = new MapItemRenderer.Instance(☃);
         this.field_148252_c.put(☃.func_195925_e(), ☃);
      }

      return ☃;
   }

   @Nullable
   public MapItemRenderer.Instance func_191205_a(String var1) {
      return (MapItemRenderer.Instance)this.field_148252_c.get(☃);
   }

   public void func_148249_a() {
      for(MapItemRenderer.Instance ☃ : this.field_148252_c.values()) {
         ☃.close();
      }

      this.field_148252_c.clear();
   }

   @Nullable
   public MapData func_191207_a(@Nullable MapItemRenderer.Instance var1) {
      return ☃ != null ? ☃.field_148242_b : null;
   }

   public void close() {
      this.func_148249_a();
   }

   class Instance implements AutoCloseable {
      private final MapData field_148242_b;
      private final DynamicTexture field_148243_c;
      private final ResourceLocation field_148240_d;

      private Instance(MapData var2) {
         this.field_148242_b = ☃;
         this.field_148243_c = new DynamicTexture(128, 128, true);
         this.field_148240_d = MapItemRenderer.this.field_148251_b.func_110578_a("map/" + ☃.func_195925_e(), this.field_148243_c);
      }

      private void func_148236_a() {
         for(int ☃ = 0; ☃ < 128; ++☃) {
            for(int ☃x = 0; ☃x < 128; ++☃x) {
               int ☃xx = ☃x + ☃ * 128;
               int ☃xxx = this.field_148242_b.field_76198_e[☃xx] & 255;
               if (☃xxx / 4 == 0) {
                  this.field_148243_c.func_195414_e().func_195700_a(☃x, ☃, (☃xx + ☃xx / 128 & 1) * 8 + 16 << 24);
               } else {
                  this.field_148243_c.func_195414_e().func_195700_a(☃x, ☃, MaterialColor.field_76281_a[☃xxx / 4].func_151643_b(☃xxx & 3));
               }
            }
         }

         this.field_148243_c.func_110564_a();
      }

      private void func_148237_a(boolean var1) {
         int ☃ = 0;
         int ☃x = 0;
         Tessellator ☃xx = Tessellator.func_178181_a();
         BufferBuilder ☃xxx = ☃xx.func_178180_c();
         float ☃xxxx = 0.0F;
         MapItemRenderer.this.field_148251_b.func_110577_a(this.field_148240_d);
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE
         );
         GlStateManager.func_179118_c();
         ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);
         ☃xxx.func_181662_b(0.0, 128.0, -0.01F).func_187315_a(0.0, 1.0).func_181675_d();
         ☃xxx.func_181662_b(128.0, 128.0, -0.01F).func_187315_a(1.0, 1.0).func_181675_d();
         ☃xxx.func_181662_b(128.0, 0.0, -0.01F).func_187315_a(1.0, 0.0).func_181675_d();
         ☃xxx.func_181662_b(0.0, 0.0, -0.01F).func_187315_a(0.0, 0.0).func_181675_d();
         ☃xx.func_78381_a();
         GlStateManager.func_179141_d();
         GlStateManager.func_179084_k();
         int ☃xxxxx = 0;

         for(MapDecoration ☃xxxxxx : this.field_148242_b.field_76203_h.values()) {
            if (!☃ || ☃xxxxxx.func_191180_f()) {
               MapItemRenderer.this.field_148251_b.func_110577_a(MapItemRenderer.field_148253_a);
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b(0.0F + (float)☃xxxxxx.func_176112_b() / 2.0F + 64.0F, 0.0F + (float)☃xxxxxx.func_176113_c() / 2.0F + 64.0F, -0.02F);
               GlStateManager.func_179114_b((float)(☃xxxxxx.func_176111_d() * 360) / 16.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.func_179152_a(4.0F, 4.0F, 3.0F);
               GlStateManager.func_179109_b(-0.125F, 0.125F, 0.0F);
               byte ☃xxxxxxx = ☃xxxxxx.func_176110_a();
               float ☃xxxxxxxx = (float)(☃xxxxxxx % 16 + 0) / 16.0F;
               float ☃xxxxxxxxx = (float)(☃xxxxxxx / 16 + 0) / 16.0F;
               float ☃xxxxxxxxxx = (float)(☃xxxxxxx % 16 + 1) / 16.0F;
               float ☃xxxxxxxxxxx = (float)(☃xxxxxxx / 16 + 1) / 16.0F;
               ☃xxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);
               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
               float ☃xxxxxxxxxxxx = -0.001F;
               ☃xxx.func_181662_b(-1.0, 1.0, (double)((float)☃xxxxx * -0.001F)).func_187315_a((double)☃xxxxxxxx, (double)☃xxxxxxxxx).func_181675_d();
               ☃xxx.func_181662_b(1.0, 1.0, (double)((float)☃xxxxx * -0.001F)).func_187315_a((double)☃xxxxxxxxxx, (double)☃xxxxxxxxx).func_181675_d();
               ☃xxx.func_181662_b(1.0, -1.0, (double)((float)☃xxxxx * -0.001F)).func_187315_a((double)☃xxxxxxxxxx, (double)☃xxxxxxxxxxx).func_181675_d();
               ☃xxx.func_181662_b(-1.0, -1.0, (double)((float)☃xxxxx * -0.001F)).func_187315_a((double)☃xxxxxxxx, (double)☃xxxxxxxxxxx).func_181675_d();
               ☃xx.func_78381_a();
               GlStateManager.func_179121_F();
               if (☃xxxxxx.func_204309_g() != null) {
                  FontRenderer ☃xxxxxxxxxxxxx = Minecraft.func_71410_x().field_71466_p;
                  String ☃xxxxxxxxxxxxxx = ☃xxxxxx.func_204309_g().func_150254_d();
                  float ☃xxxxxxxxxxxxxxx = (float)☃xxxxxxxxxxxxx.func_78256_a(☃xxxxxxxxxxxxxx);
                  float ☃xxxxxxxxxxxxxxxx = MathHelper.func_76131_a(25.0F / ☃xxxxxxxxxxxxxxx, 0.0F, 6.0F / (float)☃xxxxxxxxxxxxx.field_78288_b);
                  GlStateManager.func_179094_E();
                  GlStateManager.func_179109_b(
                     0.0F + (float)☃xxxxxx.func_176112_b() / 2.0F + 64.0F - ☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxx / 2.0F,
                     0.0F + (float)☃xxxxxx.func_176113_c() / 2.0F + 64.0F + 4.0F,
                     -0.025F
                  );
                  GlStateManager.func_179152_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, 1.0F);
                  GuiIngame.func_73734_a(-1, -1, (int)☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx.field_78288_b - 1, Integer.MIN_VALUE);
                  GlStateManager.func_179109_b(0.0F, 0.0F, -0.1F);
                  ☃xxxxxxxxxxxxx.func_211126_b(☃xxxxxxxxxxxxxx, 0.0F, 0.0F, -1);
                  GlStateManager.func_179121_F();
               }

               ++☃xxxxx;
            }
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 0.0F, -0.04F);
         GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
         GlStateManager.func_179121_F();
      }

      public void close() {
         this.field_148243_c.close();
      }
   }
}
