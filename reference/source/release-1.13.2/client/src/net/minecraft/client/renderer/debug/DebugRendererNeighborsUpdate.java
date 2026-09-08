package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class DebugRendererNeighborsUpdate implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_191554_a;
   private final Map<Long, Map<BlockPos, Integer>> field_191555_b = Maps.newTreeMap(Ordering.natural().reverse());

   DebugRendererNeighborsUpdate(Minecraft var1) {
      this.field_191554_a = ☃;
   }

   public void func_191553_a(long var1, BlockPos var3) {
      Map<BlockPos, Integer> ☃ = (Map)this.field_191555_b.get(☃);
      if (☃ == null) {
         ☃ = Maps.newHashMap();
         this.field_191555_b.put(☃, ☃);
      }

      Integer ☃ = (Integer)☃.get(☃);
      if (☃ == null) {
         ☃ = 0;
      }

      ☃.put(☃, ☃ + 1);
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      long ☃ = this.field_191554_a.field_71441_e.func_82737_E();
      EntityPlayer ☃x = this.field_191554_a.field_71439_g;
      double ☃xx = ☃x.field_70142_S + (☃x.field_70165_t - ☃x.field_70142_S) * (double)☃;
      double ☃xxx = ☃x.field_70137_T + (☃x.field_70163_u - ☃x.field_70137_T) * (double)☃;
      double ☃xxxx = ☃x.field_70136_U + (☃x.field_70161_v - ☃x.field_70136_U) * (double)☃;
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_187441_d(2.0F);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);
      int ☃xxxxx = 200;
      double ☃xxxxxx = 0.0025;
      Set<BlockPos> ☃xxxxxxx = Sets.<BlockPos>newHashSet();
      Map<BlockPos, Integer> ☃xxxxxxxx = Maps.newHashMap();
      Iterator<Entry<Long, Map<BlockPos, Integer>>> ☃xxxxxxxxx = this.field_191555_b.entrySet().iterator();

      while(☃xxxxxxxxx.hasNext()) {
         Entry<Long, Map<BlockPos, Integer>> ☃xxxxxxxxxx = (Entry)☃xxxxxxxxx.next();
         Long ☃xxxxxxxxxxx = (Long)☃xxxxxxxxxx.getKey();
         Map<BlockPos, Integer> ☃xxxxxxxxxxxx = (Map)☃xxxxxxxxxx.getValue();
         long ☃xxxxxxxxxxxxx = ☃ - ☃xxxxxxxxxxx;
         if (☃xxxxxxxxxxxxx > 200L) {
            ☃xxxxxxxxx.remove();
         } else {
            for(Entry<BlockPos, Integer> ☃xxxxxxxxxx : ☃xxxxxxxxxxxx.entrySet()) {
               BlockPos ☃xxxxxxxxxxx = (BlockPos)☃xxxxxxxxxx.getKey();
               Integer ☃xxxxxxxxxxxx = (Integer)☃xxxxxxxxxx.getValue();
               if (☃xxxxxxx.add(☃xxxxxxxxxxx)) {
                  WorldRenderer.func_189697_a(
                     new AxisAlignedBB(BlockPos.field_177992_a)
                        .func_186662_g(0.002)
                        .func_186664_h(0.0025 * (double)☃xxxxxxxxxxxxx)
                        .func_72317_d((double)☃xxxxxxxxxxx.func_177958_n(), (double)☃xxxxxxxxxxx.func_177956_o(), (double)☃xxxxxxxxxxx.func_177952_p())
                        .func_72317_d(-☃xx, -☃xxx, -☃xxxx),
                     1.0F,
                     1.0F,
                     1.0F,
                     1.0F
                  );
                  ☃xxxxxxxx.put(☃xxxxxxxxxxx, ☃xxxxxxxxxxxx);
               }
            }
         }
      }

      for(Entry<BlockPos, Integer> ☃xxxxxxxxxx : ☃xxxxxxxx.entrySet()) {
         BlockPos ☃xxxxxxxxxxx = (BlockPos)☃xxxxxxxxxx.getKey();
         Integer ☃xxxxxxxxxxxx = (Integer)☃xxxxxxxxxx.getValue();
         DebugRenderer.func_191556_a(
            String.valueOf(☃xxxxxxxxxxxx), ☃xxxxxxxxxxx.func_177958_n(), ☃xxxxxxxxxxx.func_177956_o(), ☃xxxxxxxxxxx.func_177952_p(), ☃, -1
         );
      }

      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }
}
