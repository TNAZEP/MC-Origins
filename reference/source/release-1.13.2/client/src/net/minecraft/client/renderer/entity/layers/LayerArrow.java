package net.minecraft.client.renderer.entity.layers;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.model.ModelBox;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.util.math.MathHelper;

public class LayerArrow implements LayerRenderer<EntityLivingBase> {
   private final RenderLivingBase<?> field_177168_a;

   public LayerArrow(RenderLivingBase<?> var1) {
      this.field_177168_a = ☃;
   }

   @Override
   public void func_177141_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      int ☃ = ☃.func_85035_bI();
      if (☃ > 0) {
         Entity ☃x = new EntityTippedArrow(☃.field_70170_p, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);
         Random ☃xx = new Random((long)☃.func_145782_y());
         RenderHelper.func_74518_a();

         for(int ☃xxx = 0; ☃xxx < ☃; ++☃xxx) {
            GlStateManager.func_179094_E();
            ModelRenderer ☃xxxx = this.field_177168_a.func_177087_b().func_85181_a(☃xx);
            ModelBox ☃xxxxx = (ModelBox)☃xxxx.field_78804_l.get(☃xx.nextInt(☃xxxx.field_78804_l.size()));
            ☃xxxx.func_78794_c(0.0625F);
            float ☃xxxxxx = ☃xx.nextFloat();
            float ☃xxxxxxx = ☃xx.nextFloat();
            float ☃xxxxxxxx = ☃xx.nextFloat();
            float ☃xxxxxxxxx = (☃xxxxx.field_78252_a + (☃xxxxx.field_78248_d - ☃xxxxx.field_78252_a) * ☃xxxxxx) / 16.0F;
            float ☃xxxxxxxxxx = (☃xxxxx.field_78250_b + (☃xxxxx.field_78249_e - ☃xxxxx.field_78250_b) * ☃xxxxxxx) / 16.0F;
            float ☃xxxxxxxxxxx = (☃xxxxx.field_78251_c + (☃xxxxx.field_78246_f - ☃xxxxx.field_78251_c) * ☃xxxxxxxx) / 16.0F;
            GlStateManager.func_179109_b(☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxx);
            ☃xxxxxx = ☃xxxxxx * 2.0F - 1.0F;
            ☃xxxxxxx = ☃xxxxxxx * 2.0F - 1.0F;
            ☃xxxxxxxx = ☃xxxxxxxx * 2.0F - 1.0F;
            ☃xxxxxx *= -1.0F;
            ☃xxxxxxx *= -1.0F;
            ☃xxxxxxxx *= -1.0F;
            float ☃xxxxxxxxxxxx = MathHelper.func_76129_c(☃xxxxxx * ☃xxxxxx + ☃xxxxxxxx * ☃xxxxxxxx);
            ☃x.field_70177_z = (float)(Math.atan2((double)☃xxxxxx, (double)☃xxxxxxxx) * 180.0F / (float)Math.PI);
            ☃x.field_70125_A = (float)(Math.atan2((double)☃xxxxxxx, (double)☃xxxxxxxxxxxx) * 180.0F / (float)Math.PI);
            ☃x.field_70126_B = ☃x.field_70177_z;
            ☃x.field_70127_C = ☃x.field_70125_A;
            double ☃xxxxxxxxxxxxx = 0.0;
            double ☃xxxxxxxxxxxxxx = 0.0;
            double ☃xxxxxxxxxxxxxxx = 0.0;
            this.field_177168_a.func_177068_d().func_188391_a(☃x, 0.0, 0.0, 0.0, 0.0F, ☃, false);
            GlStateManager.func_179121_F();
         }

         RenderHelper.func_74519_b();
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
