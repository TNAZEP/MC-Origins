package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPhantom;
import net.minecraft.entity.monster.EntityPhantom;
import net.minecraft.util.ResourceLocation;

public class LayerPhantomEyes implements LayerRenderer<EntityPhantom> {
   private static final ResourceLocation field_204248_a = new ResourceLocation("textures/entity/phantom_eyes.png");
   private final RenderPhantom field_204249_b;

   public LayerPhantomEyes(RenderPhantom var1) {
      this.field_204249_b = ☃;
   }

   public void func_177141_a(EntityPhantom var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.field_204249_b.func_110776_a(field_204248_a);
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
      GlStateManager.func_179140_f();
      GlStateManager.func_179132_a(!☃.func_82150_aj());
      int ☃ = 61680;
      int ☃x = 61680;
      int ☃xx = 0;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 61680.0F, 0.0F);
      GlStateManager.func_179145_e();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.func_71410_x().field_71460_t.func_191514_d(true);
      this.field_204249_b.func_177087_b().func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      Minecraft.func_71410_x().field_71460_t.func_191514_d(false);
      this.field_204249_b.func_177105_a(☃);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
