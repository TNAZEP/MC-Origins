package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class DebugRendererWorldGenAttempts implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_201735_a;
   private final List<BlockPos> field_201736_b = Lists.<BlockPos>newArrayList();
   private final List<Float> field_201737_c = Lists.newArrayList();
   private final List<Float> field_201738_d = Lists.newArrayList();
   private final List<Float> field_201739_e = Lists.newArrayList();
   private final List<Float> field_201740_f = Lists.newArrayList();
   private final List<Float> field_201741_g = Lists.newArrayList();

   public DebugRendererWorldGenAttempts(Minecraft var1) {
      this.field_201735_a = ☃;
   }

   public void func_201734_a(BlockPos var1, float var2, float var3, float var4, float var5, float var6) {
      this.field_201736_b.add(☃);
      this.field_201737_c.add(☃);
      this.field_201738_d.add(☃);
      this.field_201739_e.add(☃);
      this.field_201740_f.add(☃);
      this.field_201741_g.add(☃);
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      EntityPlayer ☃ = this.field_201735_a.field_71439_g;
      IBlockReader ☃x = this.field_201735_a.field_71441_e;
      double ☃xx = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃xxx = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xxxx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      GlStateManager.func_179094_E();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179090_x();
      new BlockPos(☃.field_70165_t, 0.0, ☃.field_70161_v);
      Tessellator ☃xxxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxxx = ☃xxxxx.func_178180_c();
      ☃xxxxxx.func_181668_a(5, DefaultVertexFormats.field_181706_f);

      for(int ☃xxxxxxx = 0; ☃xxxxxxx < this.field_201736_b.size(); ++☃xxxxxxx) {
         BlockPos ☃xxxxxxxx = (BlockPos)this.field_201736_b.get(☃xxxxxxx);
         Float ☃xxxxxxxxx = (Float)this.field_201737_c.get(☃xxxxxxx);
         float ☃xxxxxxxxxx = ☃xxxxxxxxx / 2.0F;
         WorldRenderer.func_189693_b(
            ☃xxxxxx,
            (double)((float)☃xxxxxxxx.func_177958_n() + 0.5F - ☃xxxxxxxxxx) - ☃xx,
            (double)((float)☃xxxxxxxx.func_177956_o() + 0.5F - ☃xxxxxxxxxx) - ☃xxx,
            (double)((float)☃xxxxxxxx.func_177952_p() + 0.5F - ☃xxxxxxxxxx) - ☃xxxx,
            (double)((float)☃xxxxxxxx.func_177958_n() + 0.5F + ☃xxxxxxxxxx) - ☃xx,
            (double)((float)☃xxxxxxxx.func_177956_o() + 0.5F + ☃xxxxxxxxxx) - ☃xxx,
            (double)((float)☃xxxxxxxx.func_177952_p() + 0.5F + ☃xxxxxxxxxx) - ☃xxxx,
            this.field_201739_e.get(☃xxxxxxx),
            this.field_201740_f.get(☃xxxxxxx),
            this.field_201741_g.get(☃xxxxxxx),
            this.field_201738_d.get(☃xxxxxxx)
         );
      }

      ☃xxxxx.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179121_F();
   }
}
