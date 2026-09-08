package net.minecraft.client.renderer.debug;

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
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;

public class DebugRendererStructure implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_201730_a;
   private final Map<Integer, Map<String, MutableBoundingBox>> field_201731_b = Maps.newHashMap();
   private final Map<Integer, Map<String, MutableBoundingBox>> field_201732_c = Maps.newHashMap();
   private final Map<Integer, Map<String, Boolean>> field_201733_d = Maps.newHashMap();

   public DebugRendererStructure(Minecraft var1) {
      this.field_201730_a = ☃;
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      EntityPlayer ☃ = this.field_201730_a.field_71439_g;
      IWorld ☃x = this.field_201730_a.field_71441_e;
      int ☃xx = ☃x.func_72912_H().func_202836_i();
      double ☃xxx = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃xxxx = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xxxxx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      GlStateManager.func_179094_E();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      BlockPos ☃xxxxxx = new BlockPos(☃.field_70165_t, 0.0, ☃.field_70161_v);
      Tessellator ☃xxxxxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxxxxx = ☃xxxxxxx.func_178180_c();
      ☃xxxxxxxx.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      GlStateManager.func_187441_d(1.0F);
      if (this.field_201731_b.containsKey(☃xx)) {
         for(MutableBoundingBox ☃xxxxxxxxx : ((Map)this.field_201731_b.get(☃xx)).values()) {
            if (☃xxxxxx.func_185332_f(☃xxxxxxxxx.field_78897_a, ☃xxxxxxxxx.field_78895_b, ☃xxxxxxxxx.field_78896_c) < 500.0) {
               WorldRenderer.func_189698_a(
                  ☃xxxxxxxx,
                  (double)☃xxxxxxxxx.field_78897_a - ☃xxx,
                  (double)☃xxxxxxxxx.field_78895_b - ☃xxxx,
                  (double)☃xxxxxxxxx.field_78896_c - ☃xxxxx,
                  (double)(☃xxxxxxxxx.field_78893_d + 1) - ☃xxx,
                  (double)(☃xxxxxxxxx.field_78894_e + 1) - ☃xxxx,
                  (double)(☃xxxxxxxxx.field_78892_f + 1) - ☃xxxxx,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F
               );
            }
         }
      }

      if (this.field_201732_c.containsKey(☃xx)) {
         for(Entry<String, MutableBoundingBox> ☃ : ((Map)this.field_201732_c.get(☃xx)).entrySet()) {
            String ☃x = (String)☃.getKey();
            MutableBoundingBox ☃xx = (MutableBoundingBox)☃.getValue();
            Boolean ☃xxx = (Boolean)((Map)this.field_201733_d.get(☃xx)).get(☃x);
            if (☃xxxxxx.func_185332_f(☃xx.field_78897_a, ☃xx.field_78895_b, ☃xx.field_78896_c) < 500.0) {
               if (☃xxx) {
                  WorldRenderer.func_189698_a(
                     ☃xxxxxxxx,
                     (double)☃xx.field_78897_a - ☃xxx,
                     (double)☃xx.field_78895_b - ☃xxxx,
                     (double)☃xx.field_78896_c - ☃xxxxx,
                     (double)(☃xx.field_78893_d + 1) - ☃xxx,
                     (double)(☃xx.field_78894_e + 1) - ☃xxxx,
                     (double)(☃xx.field_78892_f + 1) - ☃xxxxx,
                     0.0F,
                     1.0F,
                     0.0F,
                     1.0F
                  );
               } else {
                  WorldRenderer.func_189698_a(
                     ☃xxxxxxxx,
                     (double)☃xx.field_78897_a - ☃xxx,
                     (double)☃xx.field_78895_b - ☃xxxx,
                     (double)☃xx.field_78896_c - ☃xxxxx,
                     (double)(☃xx.field_78893_d + 1) - ☃xxx,
                     (double)(☃xx.field_78894_e + 1) - ☃xxxx,
                     (double)(☃xx.field_78892_f + 1) - ☃xxxxx,
                     0.0F,
                     0.0F,
                     1.0F,
                     1.0F
                  );
               }
            }
         }
      }

      ☃xxxxxxx.func_78381_a();
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179121_F();
   }

   public void func_201729_a(MutableBoundingBox var1, List<MutableBoundingBox> var2, List<Boolean> var3, int var4) {
      if (!this.field_201731_b.containsKey(☃)) {
         this.field_201731_b.put(☃, Maps.newHashMap());
      }

      if (!this.field_201732_c.containsKey(☃)) {
         this.field_201732_c.put(☃, Maps.newHashMap());
         this.field_201733_d.put(☃, Maps.newHashMap());
      }

      ((Map)this.field_201731_b.get(☃)).put(☃.toString(), ☃);

      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         MutableBoundingBox ☃x = (MutableBoundingBox)☃.get(☃);
         Boolean ☃xx = (Boolean)☃.get(☃);
         ((Map)this.field_201732_c.get(☃)).put(☃x.toString(), ☃x);
         ((Map)this.field_201733_d.get(☃)).put(☃x.toString(), ☃xx);
      }
   }
}
