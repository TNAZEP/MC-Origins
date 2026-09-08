package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class DebugRendererCave implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_201743_a;
   private final Map<BlockPos, BlockPos> field_201744_b = Maps.<BlockPos, BlockPos>newHashMap();
   private final Map<BlockPos, Float> field_201745_c = Maps.newHashMap();
   private final List<BlockPos> field_201746_d = Lists.<BlockPos>newArrayList();

   public DebugRendererCave(Minecraft var1) {
      this.field_201743_a = ☃;
   }

   public void func_201742_a(BlockPos var1, List<BlockPos> var2, List<Float> var3) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         this.field_201744_b.put(☃.get(☃), ☃);
         this.field_201745_c.put(☃.get(☃), ☃.get(☃));
      }

      this.field_201746_d.add(☃);
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      EntityPlayer ☃ = this.field_201743_a.field_71439_g;
      IBlockReader ☃x = this.field_201743_a.field_71441_e;
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
      Tessellator ☃xxxxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxxxx = ☃xxxxxx.func_178180_c();
      ☃xxxxxxx.func_181668_a(5, DefaultVertexFormats.field_181706_f);

      for(Entry<BlockPos, BlockPos> ☃xxxxxxxx : this.field_201744_b.entrySet()) {
         BlockPos ☃xxxxxxxxx = (BlockPos)☃xxxxxxxx.getKey();
         BlockPos ☃xxxxxxxxxx = (BlockPos)☃xxxxxxxx.getValue();
         float ☃xxxxxxxxxxx = (float)(☃xxxxxxxxxx.func_177958_n() * 128 % 256) / 256.0F;
         float ☃xxxxxxxxxxxx = (float)(☃xxxxxxxxxx.func_177956_o() * 128 % 256) / 256.0F;
         float ☃xxxxxxxxxxxxx = (float)(☃xxxxxxxxxx.func_177952_p() * 128 % 256) / 256.0F;
         float ☃xxxxxxxxxxxxxx = this.field_201745_c.get(☃xxxxxxxxx);
         if (☃xxxxx.func_196233_m(☃xxxxxxxxx) < 160.0) {
            WorldRenderer.func_189693_b(
               ☃xxxxxxx,
               (double)((float)☃xxxxxxxxx.func_177958_n() + 0.5F) - ☃xx - (double)☃xxxxxxxxxxxxxx,
               (double)((float)☃xxxxxxxxx.func_177956_o() + 0.5F) - ☃xxx - (double)☃xxxxxxxxxxxxxx,
               (double)((float)☃xxxxxxxxx.func_177952_p() + 0.5F) - ☃xxxx - (double)☃xxxxxxxxxxxxxx,
               (double)((float)☃xxxxxxxxx.func_177958_n() + 0.5F) - ☃xx + (double)☃xxxxxxxxxxxxxx,
               (double)((float)☃xxxxxxxxx.func_177956_o() + 0.5F) - ☃xxx + (double)☃xxxxxxxxxxxxxx,
               (double)((float)☃xxxxxxxxx.func_177952_p() + 0.5F) - ☃xxxx + (double)☃xxxxxxxxxxxxxx,
               ☃xxxxxxxxxxx,
               ☃xxxxxxxxxxxx,
               ☃xxxxxxxxxxxxx,
               0.5F
            );
         }
      }

      for(BlockPos ☃xxxxxxxx : this.field_201746_d) {
         if (☃xxxxx.func_196233_m(☃xxxxxxxx) < 160.0) {
            WorldRenderer.func_189693_b(
               ☃xxxxxxx,
               (double)☃xxxxxxxx.func_177958_n() - ☃xx,
               (double)☃xxxxxxxx.func_177956_o() - ☃xxx,
               (double)☃xxxxxxxx.func_177952_p() - ☃xxxx,
               (double)((float)☃xxxxxxxx.func_177958_n() + 1.0F) - ☃xx,
               (double)((float)☃xxxxxxxx.func_177956_o() + 1.0F) - ☃xxx,
               (double)((float)☃xxxxxxxx.func_177952_p() + 1.0F) - ☃xxxx,
               1.0F,
               1.0F,
               1.0F,
               1.0F
            );
         }
      }

      ☃xxxxxx.func_78381_a();
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179121_F();
   }
}
