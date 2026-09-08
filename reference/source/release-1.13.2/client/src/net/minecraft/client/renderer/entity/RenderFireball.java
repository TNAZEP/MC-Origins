package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class RenderFireball extends Render<EntityFireball> {
   private final float field_77002_a;

   public RenderFireball(RenderManager var1, float var2) {
      super(☃);
      this.field_77002_a = ☃;
   }

   public void func_76986_a(EntityFireball var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.func_179094_E();
      this.func_180548_c(☃);
      GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
      GlStateManager.func_179091_B();
      GlStateManager.func_179152_a(this.field_77002_a, this.field_77002_a, this.field_77002_a);
      TextureAtlasSprite ☃ = Minecraft.func_71410_x().func_175599_af().func_175037_a().func_199934_a(Items.field_151059_bz);
      Tessellator ☃x = Tessellator.func_178181_a();
      BufferBuilder ☃xx = ☃x.func_178180_c();
      float ☃xxx = ☃.func_94209_e();
      float ☃xxxx = ☃.func_94212_f();
      float ☃xxxxx = ☃.func_94206_g();
      float ☃xxxxxx = ☃.func_94210_h();
      float ☃xxxxxxx = 1.0F;
      float ☃xxxxxxxx = 0.5F;
      float ☃xxxxxxxxx = 0.25F;
      GlStateManager.func_179114_b(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b((float)(this.field_76990_c.field_78733_k.field_74320_O == 2 ? -1 : 1) * -this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
      if (this.field_188301_f) {
         GlStateManager.func_179142_g();
         GlStateManager.func_187431_e(this.func_188298_c(☃));
      }

      ☃xx.func_181668_a(7, DefaultVertexFormats.field_181710_j);
      ☃xx.func_181662_b(-0.5, -0.25, 0.0).func_187315_a((double)☃xxx, (double)☃xxxxxx).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃xx.func_181662_b(0.5, -0.25, 0.0).func_187315_a((double)☃xxxx, (double)☃xxxxxx).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃xx.func_181662_b(0.5, 0.75, 0.0).func_187315_a((double)☃xxxx, (double)☃xxxxx).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃xx.func_181662_b(-0.5, 0.75, 0.0).func_187315_a((double)☃xxx, (double)☃xxxxx).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      ☃x.func_78381_a();
      if (this.field_188301_f) {
         GlStateManager.func_187417_n();
         GlStateManager.func_179119_h();
      }

      GlStateManager.func_179101_C();
      GlStateManager.func_179121_F();
      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected ResourceLocation func_110775_a(EntityFireball var1) {
      return TextureMap.field_110575_b;
   }
}
