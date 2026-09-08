package net.minecraft.client.renderer.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;

public class DebugRendererHeightMap implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_190061_a;

   public DebugRendererHeightMap(Minecraft var1) {
      this.field_190061_a = ☃;
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      EntityPlayer ☃ = this.field_190061_a.field_71439_g;
      IWorld ☃x = this.field_190061_a.field_71441_e;
      double ☃xx = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃xxx = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xxxx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      GlStateManager.func_179094_E();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179090_x();
      BlockPos ☃xxxxx = new BlockPos(☃.field_70165_t, 0.0, ☃.field_70161_v);
      Iterable<BlockPos> ☃xxxxxx = BlockPos.func_177980_a(☃xxxxx.func_177982_a(-40, 0, -40), ☃xxxxx.func_177982_a(40, 0, 40));
      Tessellator ☃xxxxxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxxxxx = ☃xxxxxxx.func_178180_c();
      ☃xxxxxxxx.func_181668_a(5, DefaultVertexFormats.field_181706_f);

      for(BlockPos ☃xxxxxxxxx : ☃xxxxxx) {
         int ☃xxxxxxxxxx = ☃x.func_201676_a(Heightmap.Type.WORLD_SURFACE_WG, ☃xxxxxxxxx.func_177958_n(), ☃xxxxxxxxx.func_177952_p());
         if (☃x.func_180495_p(☃xxxxxxxxx.func_177982_a(0, ☃xxxxxxxxxx, 0).func_177977_b()).func_196958_f()) {
            WorldRenderer.func_189693_b(
               ☃xxxxxxxx,
               (double)((float)☃xxxxxxxxx.func_177958_n() + 0.25F) - ☃xx,
               (double)☃xxxxxxxxxx - ☃xxx,
               (double)((float)☃xxxxxxxxx.func_177952_p() + 0.25F) - ☃xxxx,
               (double)((float)☃xxxxxxxxx.func_177958_n() + 0.75F) - ☃xx,
               (double)☃xxxxxxxxxx + 0.09375 - ☃xxx,
               (double)((float)☃xxxxxxxxx.func_177952_p() + 0.75F) - ☃xxxx,
               0.0F,
               0.0F,
               1.0F,
               0.5F
            );
         } else {
            WorldRenderer.func_189693_b(
               ☃xxxxxxxx,
               (double)((float)☃xxxxxxxxx.func_177958_n() + 0.25F) - ☃xx,
               (double)☃xxxxxxxxxx - ☃xxx,
               (double)((float)☃xxxxxxxxx.func_177952_p() + 0.25F) - ☃xxxx,
               (double)((float)☃xxxxxxxxx.func_177958_n() + 0.75F) - ☃xx,
               (double)☃xxxxxxxxxx + 0.09375 - ☃xxx,
               (double)((float)☃xxxxxxxxx.func_177952_p() + 0.75F) - ☃xxxx,
               0.0F,
               1.0F,
               0.0F,
               0.5F
            );
         }
      }

      ☃xxxxxxx.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179121_F();
   }
}
