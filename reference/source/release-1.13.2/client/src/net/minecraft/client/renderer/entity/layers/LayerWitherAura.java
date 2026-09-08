package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.client.renderer.entity.model.ModelWither;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class LayerWitherAura implements LayerRenderer<EntityWither> {
   private static final ResourceLocation field_177217_a = new ResourceLocation("textures/entity/wither/wither_armor.png");
   private final RenderWither field_177215_b;
   private final ModelWither field_177216_c = new ModelWither(0.5F);

   public LayerWitherAura(RenderWither var1) {
      this.field_177215_b = ☃;
   }

   public void func_177141_a(EntityWither var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (☃.func_82205_o()) {
         GlStateManager.func_179132_a(!☃.func_82150_aj());
         this.field_177215_b.func_110776_a(field_177217_a);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179096_D();
         float ☃ = (float)☃.field_70173_aa + ☃;
         float ☃x = MathHelper.func_76134_b(☃ * 0.02F) * 3.0F;
         float ☃xx = ☃ * 0.01F;
         GlStateManager.func_179109_b(☃x, ☃xx, 0.0F);
         GlStateManager.func_179128_n(5888);
         GlStateManager.func_179147_l();
         float ☃xxx = 0.5F;
         GlStateManager.func_179131_c(0.5F, 0.5F, 0.5F, 1.0F);
         GlStateManager.func_179140_f();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
         this.field_177216_c.func_78086_a(☃, ☃, ☃, ☃);
         this.field_177216_c.func_178686_a(this.field_177215_b.func_177087_b());
         Minecraft.func_71410_x().field_71460_t.func_191514_d(true);
         this.field_177216_c.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         Minecraft.func_71410_x().field_71460_t.func_191514_d(false);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179096_D();
         GlStateManager.func_179128_n(5888);
         GlStateManager.func_179145_e();
         GlStateManager.func_179084_k();
         GlStateManager.func_179132_a(true);
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
