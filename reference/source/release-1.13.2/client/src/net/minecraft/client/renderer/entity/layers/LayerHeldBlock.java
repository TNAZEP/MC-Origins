package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.monster.EntityEnderman;

public class LayerHeldBlock implements LayerRenderer<EntityEnderman> {
   private final RenderEnderman field_177174_a;

   public LayerHeldBlock(RenderEnderman var1) {
      this.field_177174_a = ☃;
   }

   public void func_177141_a(EntityEnderman var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      IBlockState ☃ = ☃.func_195405_dq();
      if (☃ != null) {
         BlockRendererDispatcher ☃x = Minecraft.func_71410_x().func_175602_ab();
         GlStateManager.func_179091_B();
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, 0.6875F, -0.75F);
         GlStateManager.func_179114_b(20.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.func_179114_b(45.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.func_179109_b(0.25F, 0.1875F, 0.25F);
         float ☃xx = 0.5F;
         GlStateManager.func_179152_a(-0.5F, -0.5F, 0.5F);
         int ☃xxx = ☃.func_70070_b();
         int ☃xxxx = ☃xxx % 65536;
         int ☃xxxxx = ☃xxx / 65536;
         OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, (float)☃xxxx, (float)☃xxxxx);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_177174_a.func_110776_a(TextureMap.field_110575_b);
         ☃x.func_175016_a(☃, 1.0F);
         GlStateManager.func_179121_F();
         GlStateManager.func_179101_C();
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
