package net.minecraft.client.gui.fonts;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class TexturedGlyph {
   private final ResourceLocation field_211235_a;
   private final float field_211236_b;
   private final float field_211237_c;
   private final float field_211238_d;
   private final float field_211239_e;
   private final float field_211240_f;
   private final float field_211241_g;
   private final float field_211242_h;
   private final float field_211243_i;

   public TexturedGlyph(ResourceLocation var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      this.field_211235_a = ☃;
      this.field_211236_b = ☃;
      this.field_211237_c = ☃;
      this.field_211238_d = ☃;
      this.field_211239_e = ☃;
      this.field_211240_f = ☃;
      this.field_211241_g = ☃;
      this.field_211242_h = ☃;
      this.field_211243_i = ☃;
   }

   public void func_211234_a(TextureManager var1, boolean var2, float var3, float var4, BufferBuilder var5, float var6, float var7, float var8, float var9) {
      int ☃ = 3;
      float ☃x = ☃ + this.field_211240_f;
      float ☃xx = ☃ + this.field_211241_g;
      float ☃xxx = this.field_211242_h - 3.0F;
      float ☃xxxx = this.field_211243_i - 3.0F;
      float ☃xxxxx = ☃ + ☃xxx;
      float ☃xxxxxx = ☃ + ☃xxxx;
      float ☃xxxxxxx = ☃ ? 1.0F - 0.25F * ☃xxx : 0.0F;
      float ☃xxxxxxxx = ☃ ? 1.0F - 0.25F * ☃xxxx : 0.0F;
      ☃.func_181662_b((double)(☃x + ☃xxxxxxx), (double)☃xxxxx, 0.0)
         .func_187315_a((double)this.field_211236_b, (double)this.field_211238_d)
         .func_181666_a(☃, ☃, ☃, ☃)
         .func_181675_d();
      ☃.func_181662_b((double)(☃x + ☃xxxxxxxx), (double)☃xxxxxx, 0.0)
         .func_187315_a((double)this.field_211236_b, (double)this.field_211239_e)
         .func_181666_a(☃, ☃, ☃, ☃)
         .func_181675_d();
      ☃.func_181662_b((double)(☃xx + ☃xxxxxxxx), (double)☃xxxxxx, 0.0)
         .func_187315_a((double)this.field_211237_c, (double)this.field_211239_e)
         .func_181666_a(☃, ☃, ☃, ☃)
         .func_181675_d();
      ☃.func_181662_b((double)(☃xx + ☃xxxxxxx), (double)☃xxxxx, 0.0)
         .func_187315_a((double)this.field_211237_c, (double)this.field_211238_d)
         .func_181666_a(☃, ☃, ☃, ☃)
         .func_181675_d();
   }

   @Nullable
   public ResourceLocation func_211233_b() {
      return this.field_211235_a;
   }
}
