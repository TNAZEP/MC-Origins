package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class StructureRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft minecraft;
   private final Map<DimensionType, Map<String, BoundingBox>> postMainBoxes = Maps.newIdentityHashMap();
   private final Map<DimensionType, Map<String, BoundingBox>> postPiecesBoxes = Maps.newIdentityHashMap();
   private final Map<DimensionType, Map<String, Boolean>> startPiecesMap = Maps.newIdentityHashMap();
   private static final int MAX_RENDER_DIST = 500;

   public StructureRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      Camera â˜ƒ = this.minecraft.gameRenderer.getMainCamera();
      LevelAccessor â˜ƒx = this.minecraft.level;
      DimensionType â˜ƒxx = â˜ƒx.dimensionType();
      BlockPos â˜ƒxxx = new BlockPos(â˜ƒ.getPosition().x, 0.0, â˜ƒ.getPosition().z);
      VertexConsumer â˜ƒxxxx = â˜ƒ.getBuffer(RenderType.lines());
      if (this.postMainBoxes.containsKey(â˜ƒxx)) {
         for(BoundingBox â˜ƒxxxxx : ((Map)this.postMainBoxes.get(â˜ƒxx)).values()) {
            if (â˜ƒxxx.closerThan(â˜ƒxxxxx.getCenter(), 500.0)) {
               LevelRenderer.renderLineBox(
                  â˜ƒ,
                  â˜ƒxxxx,
                  (double)â˜ƒxxxxx.minX() - â˜ƒ,
                  (double)â˜ƒxxxxx.minY() - â˜ƒ,
                  (double)â˜ƒxxxxx.minZ() - â˜ƒ,
                  (double)(â˜ƒxxxxx.maxX() + 1) - â˜ƒ,
                  (double)(â˜ƒxxxxx.maxY() + 1) - â˜ƒ,
                  (double)(â˜ƒxxxxx.maxZ() + 1) - â˜ƒ,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  1.0F
               );
            }
         }
      }

      if (this.postPiecesBoxes.containsKey(â˜ƒxx)) {
         for(Entry<String, BoundingBox> â˜ƒ : ((Map)this.postPiecesBoxes.get(â˜ƒxx)).entrySet()) {
            String â˜ƒx = (String)â˜ƒ.getKey();
            BoundingBox â˜ƒxx = (BoundingBox)â˜ƒ.getValue();
            Boolean â˜ƒxxx = (Boolean)((Map)this.startPiecesMap.get(â˜ƒxx)).get(â˜ƒx);
            if (â˜ƒxxx.closerThan(â˜ƒxx.getCenter(), 500.0)) {
               if (â˜ƒxxx) {
                  LevelRenderer.renderLineBox(
                     â˜ƒ,
                     â˜ƒxxxx,
                     (double)â˜ƒxx.minX() - â˜ƒ,
                     (double)â˜ƒxx.minY() - â˜ƒ,
                     (double)â˜ƒxx.minZ() - â˜ƒ,
                     (double)(â˜ƒxx.maxX() + 1) - â˜ƒ,
                     (double)(â˜ƒxx.maxY() + 1) - â˜ƒ,
                     (double)(â˜ƒxx.maxZ() + 1) - â˜ƒ,
                     0.0F,
                     1.0F,
                     0.0F,
                     1.0F,
                     0.0F,
                     1.0F,
                     0.0F
                  );
               } else {
                  LevelRenderer.renderLineBox(
                     â˜ƒ,
                     â˜ƒxxxx,
                     (double)â˜ƒxx.minX() - â˜ƒ,
                     (double)â˜ƒxx.minY() - â˜ƒ,
                     (double)â˜ƒxx.minZ() - â˜ƒ,
                     (double)(â˜ƒxx.maxX() + 1) - â˜ƒ,
                     (double)(â˜ƒxx.maxY() + 1) - â˜ƒ,
                     (double)(â˜ƒxx.maxZ() + 1) - â˜ƒ,
                     0.0F,
                     0.0F,
                     1.0F,
                     1.0F,
                     0.0F,
                     0.0F,
                     1.0F
                  );
               }
            }
         }
      }
   }

   public void addBoundingBox(BoundingBox var1, List<BoundingBox> var2, List<Boolean> var3, DimensionType var4) {
      if (!this.postMainBoxes.containsKey(â˜ƒ)) {
         this.postMainBoxes.put(â˜ƒ, Maps.newHashMap());
      }

      if (!this.postPiecesBoxes.containsKey(â˜ƒ)) {
         this.postPiecesBoxes.put(â˜ƒ, Maps.newHashMap());
         this.startPiecesMap.put(â˜ƒ, Maps.newHashMap());
      }

      ((Map)this.postMainBoxes.get(â˜ƒ)).put(â˜ƒ.toString(), â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         BoundingBox â˜ƒx = (BoundingBox)â˜ƒ.get(â˜ƒ);
         Boolean â˜ƒxx = (Boolean)â˜ƒ.get(â˜ƒ);
         ((Map)this.postPiecesBoxes.get(â˜ƒ)).put(â˜ƒx.toString(), â˜ƒx);
         ((Map)this.startPiecesMap.get(â˜ƒ)).put(â˜ƒx.toString(), â˜ƒxx);
      }
   }

   @Override
   public void clear() {
      this.postMainBoxes.clear();
      this.postPiecesBoxes.clear();
      this.startPiecesMap.clear();
   }
}
