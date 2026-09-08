package net.minecraft.client.renderer.tileentity;

import java.util.Calendar;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelChest;
import net.minecraft.client.renderer.entity.model.ModelLargeChest;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityTrappedChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class TileEntityChestRenderer<T extends TileEntity & IChestLid> extends TileEntityRenderer<T> {
   private static final ResourceLocation field_147507_b = new ResourceLocation("textures/entity/chest/trapped_double.png");
   private static final ResourceLocation field_147508_c = new ResourceLocation("textures/entity/chest/christmas_double.png");
   private static final ResourceLocation field_147505_d = new ResourceLocation("textures/entity/chest/normal_double.png");
   private static final ResourceLocation field_147506_e = new ResourceLocation("textures/entity/chest/trapped.png");
   private static final ResourceLocation field_147503_f = new ResourceLocation("textures/entity/chest/christmas.png");
   private static final ResourceLocation field_147504_g = new ResourceLocation("textures/entity/chest/normal.png");
   private static final ResourceLocation field_199348_i = new ResourceLocation("textures/entity/chest/ender.png");
   private final ModelChest field_147510_h = new ModelChest();
   private final ModelChest field_147511_i = new ModelLargeChest();
   private boolean field_147509_j;

   public TileEntityChestRenderer() {
      Calendar ☃ = Calendar.getInstance();
      if (☃.get(2) + 1 == 12 && ☃.get(5) >= 24 && ☃.get(5) <= 26) {
         this.field_147509_j = true;
      }
   }

   @Override
   public void func_199341_a(T var1, double var2, double var4, double var6, float var8, int var9) {
      GlStateManager.func_179126_j();
      GlStateManager.func_179143_c(515);
      GlStateManager.func_179132_a(true);
      IBlockState ☃ = ☃.func_145830_o() ? ☃.func_195044_w() : Blocks.field_150486_ae.func_176223_P().func_206870_a(BlockChest.field_176459_a, EnumFacing.SOUTH);
      ChestType ☃x = ☃.func_196959_b(BlockChest.field_196314_b) ? ☃.func_177229_b(BlockChest.field_196314_b) : ChestType.SINGLE;
      if (☃x != ChestType.LEFT) {
         boolean ☃xx = ☃x != ChestType.SINGLE;
         ModelChest ☃xxx = this.func_199347_a(☃, ☃, ☃xx);
         if (☃ >= 0) {
            GlStateManager.func_179128_n(5890);
            GlStateManager.func_179094_E();
            GlStateManager.func_179152_a(☃xx ? 8.0F : 4.0F, 4.0F, 1.0F);
            GlStateManager.func_179109_b(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.func_179128_n(5888);
         } else {
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179091_B();
         GlStateManager.func_179109_b((float)☃, (float)☃ + 1.0F, (float)☃ + 1.0F);
         GlStateManager.func_179152_a(1.0F, -1.0F, -1.0F);
         float ☃xx = ((EnumFacing)☃.func_177229_b(BlockChest.field_176459_a)).func_185119_l();
         if ((double)Math.abs(☃xx) > 1.0E-5) {
            GlStateManager.func_179109_b(0.5F, 0.5F, 0.5F);
            GlStateManager.func_179114_b(☃xx, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179109_b(-0.5F, -0.5F, -0.5F);
         }

         this.func_199346_a(☃, ☃, ☃xxx);
         ☃xxx.func_78231_a();
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

   private ModelChest func_199347_a(T var1, int var2, boolean var3) {
      ResourceLocation ☃;
      if (☃ >= 0) {
         ☃ = field_178460_a[☃];
      } else if (this.field_147509_j) {
         ☃ = ☃ ? field_147508_c : field_147503_f;
      } else if (☃ instanceof TileEntityTrappedChest) {
         ☃ = ☃ ? field_147507_b : field_147506_e;
      } else if (☃ instanceof TileEntityEnderChest) {
         ☃ = field_199348_i;
      } else {
         ☃ = ☃ ? field_147505_d : field_147504_g;
      }

      this.func_147499_a(☃);
      return ☃ ? this.field_147511_i : this.field_147510_h;
   }

   private void func_199346_a(T var1, float var2, ModelChest var3) {
      float ☃ = ☃.func_195480_a(☃);
      ☃ = 1.0F - ☃;
      ☃ = 1.0F - ☃ * ☃ * ☃;
      ☃.func_205058_b().field_78795_f = -(☃ * (float) (Math.PI / 2));
   }
}
