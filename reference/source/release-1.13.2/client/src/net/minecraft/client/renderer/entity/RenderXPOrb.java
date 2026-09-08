package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderXPOrb extends Render<EntityXPOrb> {
   private static final ResourceLocation field_110785_a = new ResourceLocation("textures/entity/experience_orb.png");

   public RenderXPOrb(RenderManager var1) {
      super(☃);
      this.field_76989_e = 0.15F;
      this.field_76987_f = 0.75F;
   }

   public void func_76986_a(EntityXPOrb var1, double var2, double var4, double var6, float var8, float var9) {
      if (!this.field_188301_f) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)☃, (float)☃, (float)☃);
         this.func_180548_c(☃);
         RenderHelper.func_74519_b();
         int ☃ = ☃.func_70528_g();
         float ☃x = (float)(☃ % 4 * 16 + 0) / 64.0F;
         float ☃xx = (float)(☃ % 4 * 16 + 16) / 64.0F;
         float ☃xxx = (float)(☃ / 4 * 16 + 0) / 64.0F;
         float ☃xxxx = (float)(☃ / 4 * 16 + 16) / 64.0F;
         float ☃xxxxx = 1.0F;
         float ☃xxxxxx = 0.5F;
         float ☃xxxxxxx = 0.25F;
         int ☃xxxxxxxx = ☃.func_70070_b();
         int ☃xxxxxxxxx = ☃xxxxxxxx % 65536;
         int ☃xxxxxxxxxx = ☃xxxxxxxx / 65536;
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)☃xxxxxxxxx, (float)☃xxxxxxxxxx);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         float ☃xxxxxxxxxxx = 255.0F;
         float ☃xxxxxxxxxxxx = ((float)☃.field_70533_a + ☃) / 2.0F;
         int ☃xxxxxxxxxxxxx = (int)((MathHelper.func_76126_a(☃xxxxxxxxxxxx + 0.0F) + 1.0F) * 0.5F * 255.0F);
         int ☃xxxxxxxxxxxxxx = 255;
         int ☃xxxxxxxxxxxxxxx = (int)((MathHelper.func_76126_a(☃xxxxxxxxxxxx + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * 255.0F);
         GlStateManager.func_179109_b(0.0F, 0.1F, 0.0F);
         GlStateManager.func_179114_b(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179114_b(
            (float)(this.field_76990_c.field_78733_k.field_74320_O == 2 ? -1 : 1) * -this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F
         );
         float ☃xxxxxxxxxxxxxxxx = 0.3F;
         GlStateManager.func_179152_a(0.3F, 0.3F, 0.3F);
         Tessellator ☃xxxxxxxxxxxxxxxxx = Tessellator.func_178181_a();
         BufferBuilder ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxx.func_178180_c();
         ☃xxxxxxxxxxxxxxxxxx.func_181668_a(7, DefaultVertexFormats.field_181712_l);
         ☃xxxxxxxxxxxxxxxxxx.func_181662_b(-0.5, -0.25, 0.0)
            .func_187315_a((double)☃x, (double)☃xxxx)
            .func_181669_b(☃xxxxxxxxxxxxx, 255, ☃xxxxxxxxxxxxxxx, 128)
            .func_181663_c(0.0F, 1.0F, 0.0F)
            .func_181675_d();
         ☃xxxxxxxxxxxxxxxxxx.func_181662_b(0.5, -0.25, 0.0)
            .func_187315_a((double)☃xx, (double)☃xxxx)
            .func_181669_b(☃xxxxxxxxxxxxx, 255, ☃xxxxxxxxxxxxxxx, 128)
            .func_181663_c(0.0F, 1.0F, 0.0F)
            .func_181675_d();
         ☃xxxxxxxxxxxxxxxxxx.func_181662_b(0.5, 0.75, 0.0)
            .func_187315_a((double)☃xx, (double)☃xxx)
            .func_181669_b(☃xxxxxxxxxxxxx, 255, ☃xxxxxxxxxxxxxxx, 128)
            .func_181663_c(0.0F, 1.0F, 0.0F)
            .func_181675_d();
         ☃xxxxxxxxxxxxxxxxxx.func_181662_b(-0.5, 0.75, 0.0)
            .func_187315_a((double)☃x, (double)☃xxx)
            .func_181669_b(☃xxxxxxxxxxxxx, 255, ☃xxxxxxxxxxxxxxx, 128)
            .func_181663_c(0.0F, 1.0F, 0.0F)
            .func_181675_d();
         ☃xxxxxxxxxxxxxxxxx.func_78381_a();
         GlStateManager.func_179084_k();
         GlStateManager.func_179101_C();
         GlStateManager.func_179121_F();
         super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   protected ResourceLocation func_110775_a(EntityXPOrb var1) {
      return field_110785_a;
   }
}
