package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RenderFallingBlock extends Render<EntityFallingBlock> {
   public RenderFallingBlock(RenderManager var1) {
      super(☃);
      this.field_76989_e = 0.5F;
   }

   public void func_76986_a(EntityFallingBlock var1, double var2, double var4, double var6, float var8, float var9) {
      IBlockState ☃ = ☃.func_195054_l();
      if (☃.func_185901_i() == EnumBlockRenderType.MODEL) {
         World ☃x = ☃.func_145807_e();
         if (☃ != ☃x.func_180495_p(new BlockPos(☃)) && ☃.func_185901_i() != EnumBlockRenderType.INVISIBLE) {
            this.func_110776_a(TextureMap.field_110575_b);
            GlStateManager.func_179094_E();
            GlStateManager.func_179140_f();
            Tessellator ☃xx = Tessellator.func_178181_a();
            BufferBuilder ☃xxx = ☃xx.func_178180_c();
            if (this.field_188301_f) {
               GlStateManager.func_179142_g();
               GlStateManager.func_187431_e(this.func_188298_c(☃));
            }

            ☃xxx.func_181668_a(7, DefaultVertexFormats.field_176600_a);
            BlockPos ☃xx = new BlockPos(☃.field_70165_t, ☃.func_174813_aQ().field_72337_e, ☃.field_70161_v);
            GlStateManager.func_179109_b(
               (float)(☃ - (double)☃xx.func_177958_n() - 0.5), (float)(☃ - (double)☃xx.func_177956_o()), (float)(☃ - (double)☃xx.func_177952_p() - 0.5)
            );
            BlockRendererDispatcher ☃xxx = Minecraft.func_71410_x().func_175602_ab();
            ☃xxx.func_175019_b().func_199324_a(☃x, ☃xxx.func_184389_a(☃), ☃, ☃xx, ☃xxx, false, new Random(), ☃.func_209533_a(☃.func_184531_j()));
            ☃xx.func_78381_a();
            if (this.field_188301_f) {
               GlStateManager.func_187417_n();
               GlStateManager.func_179119_h();
            }

            GlStateManager.func_179145_e();
            GlStateManager.func_179121_F();
            super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
         }
      }
   }

   protected ResourceLocation func_110775_a(EntityFallingBlock var1) {
      return TextureMap.field_110575_b;
   }
}
