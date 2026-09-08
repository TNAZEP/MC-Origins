package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityEndGatewayRenderer extends TileEntityEndPortalRenderer {
   private static final ResourceLocation field_188199_f = new ResourceLocation("textures/entity/end_gateway_beam.png");

   @Override
   public void func_199341_a(TileEntityEndPortal var1, double var2, double var4, double var6, float var8, int var9) {
      GlStateManager.func_179106_n();
      TileEntityEndGateway ☃ = (TileEntityEndGateway)☃;
      if (☃.func_195499_c() || ☃.func_195500_d()) {
         GlStateManager.func_179092_a(516, 0.1F);
         this.func_147499_a(field_188199_f);
         float ☃x = ☃.func_195499_c() ? ☃.func_195497_a(☃) : ☃.func_195491_b(☃);
         double ☃xx = ☃.func_195499_c() ? 256.0 - ☃ : 50.0;
         ☃x = MathHelper.func_76126_a(☃x * (float) Math.PI);
         int ☃xxx = MathHelper.func_76128_c((double)☃x * ☃xx);
         float[] ☃xxxx = ☃.func_195499_c() ? EnumDyeColor.MAGENTA.func_193349_f() : EnumDyeColor.PURPLE.func_193349_f();
         TileEntityBeaconRenderer.func_188205_a(☃, ☃, ☃, (double)☃, (double)☃x, ☃.func_145831_w().func_82737_E(), 0, ☃xxx, ☃xxxx, 0.15, 0.175);
         TileEntityBeaconRenderer.func_188205_a(☃, ☃, ☃, (double)☃, (double)☃x, ☃.func_145831_w().func_82737_E(), 0, -☃xxx, ☃xxxx, 0.15, 0.175);
      }

      super.func_199341_a(☃, ☃, ☃, ☃, ☃, ☃);
      GlStateManager.func_179127_m();
   }

   @Override
   protected int func_191286_a(double var1) {
      return super.func_191286_a(☃) + 1;
   }

   @Override
   protected float func_191287_c() {
      return 1.0F;
   }
}
