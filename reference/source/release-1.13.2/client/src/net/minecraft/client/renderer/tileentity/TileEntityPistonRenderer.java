package net.minecraft.client.renderer.tileentity;

import java.util.Random;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.PistonType;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityPistonRenderer extends TileEntityRenderer<TileEntityPiston> {
   private final BlockRendererDispatcher field_178462_c = Minecraft.func_71410_x().func_175602_ab();

   public void func_199341_a(TileEntityPiston var1, double var2, double var4, double var6, float var8, int var9) {
      BlockPos ☃ = ☃.func_174877_v().func_177972_a(☃.func_195509_h().func_176734_d());
      IBlockState ☃x = ☃.func_200230_i();
      if (!☃x.func_196958_f() && !(☃.func_145860_a(☃) >= 1.0F)) {
         Tessellator ☃xx = Tessellator.func_178181_a();
         BufferBuilder ☃xxx = ☃xx.func_178180_c();
         this.func_147499_a(TextureMap.field_110575_b);
         RenderHelper.func_74518_a();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.func_179147_l();
         GlStateManager.func_179129_p();
         if (Minecraft.func_71379_u()) {
            GlStateManager.func_179103_j(7425);
         } else {
            GlStateManager.func_179103_j(7424);
         }

         ☃xxx.func_181668_a(7, DefaultVertexFormats.field_176600_a);
         ☃xxx.func_178969_c(
            ☃ - (double)☃.func_177958_n() + (double)☃.func_174929_b(☃),
            ☃ - (double)☃.func_177956_o() + (double)☃.func_174928_c(☃),
            ☃ - (double)☃.func_177952_p() + (double)☃.func_174926_d(☃)
         );
         World ☃xx = this.func_178459_a();
         if (☃x.func_177230_c() == Blocks.field_150332_K && ☃.func_145860_a(☃) <= 4.0F) {
            ☃x = ☃x.func_206870_a(BlockPistonExtension.field_176327_M, Boolean.valueOf(true));
            this.func_188186_a(☃, ☃x, ☃xxx, ☃xx, false);
         } else if (☃.func_145867_d() && !☃.func_145868_b()) {
            PistonType ☃xx = ☃x.func_177230_c() == Blocks.field_150320_F ? PistonType.STICKY : PistonType.DEFAULT;
            IBlockState ☃xxx = Blocks.field_150332_K
               .func_176223_P()
               .func_206870_a(BlockPistonExtension.field_176325_b, ☃xx)
               .func_206870_a(BlockPistonExtension.field_176387_N, ☃x.func_177229_b(BlockPistonBase.field_176387_N));
            ☃xxx = ☃xxx.func_206870_a(BlockPistonExtension.field_176327_M, Boolean.valueOf(☃.func_145860_a(☃) >= 0.5F));
            this.func_188186_a(☃, ☃xxx, ☃xxx, ☃xx, false);
            BlockPos ☃xxxx = ☃.func_177972_a(☃.func_195509_h());
            ☃xxx.func_178969_c(☃ - (double)☃xxxx.func_177958_n(), ☃ - (double)☃xxxx.func_177956_o(), ☃ - (double)☃xxxx.func_177952_p());
            ☃x = ☃x.func_206870_a(BlockPistonBase.field_176320_b, Boolean.valueOf(true));
            this.func_188186_a(☃xxxx, ☃x, ☃xxx, ☃xx, true);
         } else {
            this.func_188186_a(☃, ☃x, ☃xxx, ☃xx, false);
         }

         ☃xxx.func_178969_c(0.0, 0.0, 0.0);
         ☃xx.func_78381_a();
         RenderHelper.func_74519_b();
      }
   }

   private boolean func_188186_a(BlockPos var1, IBlockState var2, BufferBuilder var3, World var4, boolean var5) {
      return this.field_178462_c.func_175019_b().func_199324_a(☃, this.field_178462_c.func_184389_a(☃), ☃, ☃, ☃, ☃, new Random(), ☃.func_209533_a(☃));
   }
}
