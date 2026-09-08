package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class NeighborsUpdateRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft minecraft;
   private final Map<Long, Map<BlockPos, Integer>> lastUpdate = Maps.newTreeMap(Ordering.natural().reverse());

   NeighborsUpdateRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   public void addUpdate(long var1, BlockPos var3) {
      Map<BlockPos, Integer> â˜ƒ = (Map)this.lastUpdate.computeIfAbsent(â˜ƒ, var0 -> Maps.newHashMap());
      int â˜ƒx = â˜ƒ.getOrDefault(â˜ƒ, 0);
      â˜ƒ.put(â˜ƒ, â˜ƒx + 1);
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      long â˜ƒ = this.minecraft.level.getGameTime();
      int â˜ƒx = 200;
      double â˜ƒxx = 0.0025;
      Set<BlockPos> â˜ƒxxx = Sets.<BlockPos>newHashSet();
      Map<BlockPos, Integer> â˜ƒxxxx = Maps.newHashMap();
      VertexConsumer â˜ƒxxxxx = â˜ƒ.getBuffer(RenderType.lines());
      Iterator<Entry<Long, Map<BlockPos, Integer>>> â˜ƒxxxxxx = this.lastUpdate.entrySet().iterator();

      while(â˜ƒxxxxxx.hasNext()) {
         Entry<Long, Map<BlockPos, Integer>> â˜ƒxxxxxxx = (Entry)â˜ƒxxxxxx.next();
         Long â˜ƒxxxxxxxx = (Long)â˜ƒxxxxxxx.getKey();
         Map<BlockPos, Integer> â˜ƒxxxxxxxxx = (Map)â˜ƒxxxxxxx.getValue();
         long â˜ƒxxxxxxxxxx = â˜ƒ - â˜ƒxxxxxxxx;
         if (â˜ƒxxxxxxxxxx > 200L) {
            â˜ƒxxxxxx.remove();
         } else {
            for(Entry<BlockPos, Integer> â˜ƒxxxxxxx : â˜ƒxxxxxxxxx.entrySet()) {
               BlockPos â˜ƒxxxxxxxx = (BlockPos)â˜ƒxxxxxxx.getKey();
               Integer â˜ƒxxxxxxxxx = (Integer)â˜ƒxxxxxxx.getValue();
               if (â˜ƒxxx.add(â˜ƒxxxxxxxx)) {
                  AABB â˜ƒxxxxxxxxxx = new AABB(BlockPos.ZERO)
                     .inflate(0.002)
                     .deflate(0.0025 * (double)â˜ƒxxxxxxxxxx)
                     .move((double)â˜ƒxxxxxxxx.getX(), (double)â˜ƒxxxxxxxx.getY(), (double)â˜ƒxxxxxxxx.getZ())
                     .move(-â˜ƒ, -â˜ƒ, -â˜ƒ);
                  LevelRenderer.renderLineBox(
                     â˜ƒ,
                     â˜ƒxxxxx,
                     â˜ƒxxxxxxxxxx.minX,
                     â˜ƒxxxxxxxxxx.minY,
                     â˜ƒxxxxxxxxxx.minZ,
                     â˜ƒxxxxxxxxxx.maxX,
                     â˜ƒxxxxxxxxxx.maxY,
                     â˜ƒxxxxxxxxxx.maxZ,
                     1.0F,
                     1.0F,
                     1.0F,
                     1.0F
                  );
                  â˜ƒxxxx.put(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
               }
            }
         }
      }

      for(Entry<BlockPos, Integer> â˜ƒxxxxxxx : â˜ƒxxxx.entrySet()) {
         BlockPos â˜ƒxxxxxxxx = (BlockPos)â˜ƒxxxxxxx.getKey();
         Integer â˜ƒxxxxxxxxx = (Integer)â˜ƒxxxxxxx.getValue();
         DebugRenderer.renderFloatingText(String.valueOf(â˜ƒxxxxxxxxx), â˜ƒxxxxxxxx.getX(), â˜ƒxxxxxxxx.getY(), â˜ƒxxxxxxxx.getZ(), -1);
      }
   }
}
