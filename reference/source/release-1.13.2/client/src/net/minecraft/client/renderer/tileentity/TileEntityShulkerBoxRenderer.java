package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderShulker;
import net.minecraft.client.renderer.entity.model.ModelShulker;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumFacing;

public class TileEntityShulkerBoxRenderer extends TileEntityRenderer<TileEntityShulkerBox> {
   private final ModelShulker field_191285_a;

   public TileEntityShulkerBoxRenderer(ModelShulker var1) {
      this.field_191285_a = ☃;
   }

   public void func_199341_a(TileEntityShulkerBox var1, double var2, double var4, double var6, float var8, int var9) {
      EnumFacing ☃ = EnumFacing.UP;
      if (☃.func_145830_o()) {
         IBlockState ☃x = this.func_178459_a().func_180495_p(☃.func_174877_v());
         if (☃x.func_177230_c() instanceof BlockShulkerBox) {
            ☃ = ☃x.func_177229_b(BlockShulkerBox.field_190957_a);
         }
      }

      GlStateManager.func_179126_j();
      GlStateManager.func_179143_c(515);
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179129_p();
      if (☃ >= 0) {
         this.func_147499_a(field_178460_a[☃]);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179094_E();
         GlStateManager.func_179152_a(4.0F, 4.0F, 1.0F);
         GlStateManager.func_179109_b(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.func_179128_n(5888);
      } else {
         EnumDyeColor ☃ = ☃.func_190592_s();
         if (☃ == null) {
            this.func_147499_a(RenderShulker.field_204402_a);
         } else {
            this.func_147499_a(RenderShulker.field_188342_a[☃.func_196059_a()]);
         }
      }

      GlStateManager.func_179094_E();
      GlStateManager.func_179091_B();
      if (☃ < 0) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      }

      GlStateManager.func_179109_b((float)☃ + 0.5F, (float)☃ + 1.5F, (float)☃ + 0.5F);
      GlStateManager.func_179152_a(1.0F, -1.0F, -1.0F);
      GlStateManager.func_179109_b(0.0F, 1.0F, 0.0F);
      float ☃ = 0.9995F;
      GlStateManager.func_179152_a(0.9995F, 0.9995F, 0.9995F);
      GlStateManager.func_179109_b(0.0F, -1.0F, 0.0F);
      switch(☃) {
         case DOWN:
            GlStateManager.func_179109_b(0.0F, 2.0F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
         case UP:
         default:
            break;
         case NORTH:
            GlStateManager.func_179109_b(0.0F, 1.0F, 1.0F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
            break;
         case SOUTH:
            GlStateManager.func_179109_b(0.0F, 1.0F, -1.0F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            break;
         case WEST:
            GlStateManager.func_179109_b(-1.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(-90.0F, 0.0F, 0.0F, 1.0F);
            break;
         case EAST:
            GlStateManager.func_179109_b(1.0F, 1.0F, 0.0F);
            GlStateManager.func_179114_b(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(90.0F, 0.0F, 0.0F, 1.0F);
      }

      this.field_191285_a.func_205069_a().func_78785_a(0.0625F);
      GlStateManager.func_179109_b(0.0F, -☃.func_190585_a(☃) * 0.5F, 0.0F);
      GlStateManager.func_179114_b(270.0F * ☃.func_190585_a(☃), 0.0F, 1.0F, 0.0F);
      this.field_191285_a.func_205068_b().func_78785_a(0.0625F);
      GlStateManager.func_179089_o();
      GlStateManager.func_179101_C();
      GlStateManager.func_179121_F();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      if (☃ >= 0) {
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179121_F();
         GlStateManager.func_179128_n(5888);
      }
   }
}
