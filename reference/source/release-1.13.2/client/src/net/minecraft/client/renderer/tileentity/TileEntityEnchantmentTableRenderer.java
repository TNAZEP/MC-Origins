package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBook;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class TileEntityEnchantmentTableRenderer extends TileEntityRenderer<TileEntityEnchantmentTable> {
   private static final ResourceLocation field_147540_b = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private final ModelBook field_147541_c = new ModelBook();

   public void func_199341_a(TileEntityEnchantmentTable var1, double var2, double var4, double var6, float var8, int var9) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 0.75F, (float)☃ + 0.5F);
      float ☃ = (float)☃.field_195522_a + ☃;
      GlStateManager.func_179109_b(0.0F, 0.1F + MathHelper.func_76126_a(☃ * 0.1F) * 0.01F, 0.0F);
      float ☃x = ☃.field_195529_l - ☃.field_195530_m;

      while(☃x >= (float) Math.PI) {
         ☃x -= (float) (Math.PI * 2);
      }

      while(☃x < (float) -Math.PI) {
         ☃x += (float) (Math.PI * 2);
      }

      float ☃xx = ☃.field_195530_m + ☃x * ☃;
      GlStateManager.func_179114_b(-☃xx * (180.0F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(80.0F, 0.0F, 0.0F, 1.0F);
      this.func_147499_a(field_147540_b);
      float ☃xxx = ☃.field_195524_g + (☃.field_195523_f - ☃.field_195524_g) * ☃ + 0.25F;
      float ☃xxxx = ☃.field_195524_g + (☃.field_195523_f - ☃.field_195524_g) * ☃ + 0.75F;
      ☃xxx = (☃xxx - (float)MathHelper.func_76140_b((double)☃xxx)) * 1.6F - 0.3F;
      ☃xxxx = (☃xxxx - (float)MathHelper.func_76140_b((double)☃xxxx)) * 1.6F - 0.3F;
      if (☃xxx < 0.0F) {
         ☃xxx = 0.0F;
      }

      if (☃xxxx < 0.0F) {
         ☃xxxx = 0.0F;
      }

      if (☃xxx > 1.0F) {
         ☃xxx = 1.0F;
      }

      if (☃xxxx > 1.0F) {
         ☃xxxx = 1.0F;
      }

      float ☃xx = ☃.field_195528_k + (☃.field_195527_j - ☃.field_195528_k) * ☃;
      GlStateManager.func_179089_o();
      this.field_147541_c.func_78088_a(null, ☃, ☃xxx, ☃xxxx, ☃xx, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
   }
}
