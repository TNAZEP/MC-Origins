package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LayerSpinAttackEffect implements LayerRenderer<AbstractClientPlayer> {
   public static final ResourceLocation field_204836_a = new ResourceLocation("textures/entity/trident_riptide.png");
   private final RenderPlayer field_204837_b;
   private final LayerSpinAttackEffect.Model field_204838_c;

   public LayerSpinAttackEffect(RenderPlayer var1) {
      this.field_204837_b = ☃;
      this.field_204838_c = new LayerSpinAttackEffect.Model();
   }

   public void func_177141_a(AbstractClientPlayer var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (☃.func_204805_cN()) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_204837_b.func_110776_a(field_204836_a);

         for(int ☃ = 0; ☃ < 3; ++☃) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179114_b(☃ * (float)(-(45 + ☃ * 5)), 0.0F, 1.0F, 0.0F);
            float ☃x = 0.75F * (float)☃;
            GlStateManager.func_179152_a(☃x, ☃x, ☃x);
            GlStateManager.func_179109_b(0.0F, -0.2F + 0.6F * (float)☃, 0.0F);
            this.field_204838_c.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
            GlStateManager.func_179121_F();
         }
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }

   static class Model extends ModelBase {
      private final ModelRenderer field_204834_a;

      public Model() {
         this.field_78090_t = 64;
         this.field_78089_u = 64;
         this.field_204834_a = new ModelRenderer(this, 0, 0);
         this.field_204834_a.func_78789_a(-8.0F, -16.0F, -8.0F, 16, 32, 16);
      }

      @Override
      public void func_78088_a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.field_204834_a.func_78785_a(☃);
      }
   }
}
