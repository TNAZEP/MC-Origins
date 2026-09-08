package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Locale;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;

public class PathfindingRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Map<Integer, Path> pathMap = Maps.newHashMap();
   private final Map<Integer, Float> pathMaxDist = Maps.newHashMap();
   private final Map<Integer, Long> creationMap = Maps.newHashMap();
   private static final long TIMEOUT = 5000L;
   private static final float MAX_RENDER_DIST = 80.0F;
   private static final boolean SHOW_OPEN_CLOSED = true;
   private static final boolean SHOW_OPEN_CLOSED_COST_MALUS = false;
   private static final boolean SHOW_OPEN_CLOSED_NODE_TYPE_WITH_TEXT = false;
   private static final boolean SHOW_OPEN_CLOSED_NODE_TYPE_WITH_BOX = true;
   private static final boolean SHOW_GROUND_LABELS = true;
   private static final float TEXT_SCALE = 0.02F;

   public void addPath(int var1, Path var2, float var3) {
      this.pathMap.put(â˜ƒ, â˜ƒ);
      this.creationMap.put(â˜ƒ, Util.getMillis());
      this.pathMaxDist.put(â˜ƒ, â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      if (!this.pathMap.isEmpty()) {
         long â˜ƒ = Util.getMillis();

         for(Integer â˜ƒx : this.pathMap.keySet()) {
            Path â˜ƒxx = (Path)this.pathMap.get(â˜ƒx);
            float â˜ƒxxx = this.pathMaxDist.get(â˜ƒx);
            renderPath(â˜ƒxx, â˜ƒxxx, true, true, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         for(Integer â˜ƒx : (Integer[])this.creationMap.keySet().toArray(new Integer[0])) {
            if (â˜ƒ - this.creationMap.get(â˜ƒx) > 5000L) {
               this.pathMap.remove(â˜ƒx);
               this.creationMap.remove(â˜ƒx);
            }
         }
      }
   }

   public static void renderPath(Path var0, float var1, boolean var2, boolean var3, double var4, double var6, double var8) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderColor(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      RenderSystem.lineWidth(6.0F);
      doRenderPath(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }

   private static void doRenderPath(Path var0, float var1, boolean var2, boolean var3, double var4, double var6, double var8) {
      renderPathLine(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      BlockPos â˜ƒ = â˜ƒ.getTarget();
      if (distanceToCamera(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) <= 80.0F) {
         DebugRenderer.renderFilledBox(
            new AABB(
                  (double)((float)â˜ƒ.getX() + 0.25F),
                  (double)((float)â˜ƒ.getY() + 0.25F),
                  (double)â˜ƒ.getZ() + 0.25,
                  (double)((float)â˜ƒ.getX() + 0.75F),
                  (double)((float)â˜ƒ.getY() + 0.75F),
                  (double)((float)â˜ƒ.getZ() + 0.75F)
               )
               .move(-â˜ƒ, -â˜ƒ, -â˜ƒ),
            0.0F,
            1.0F,
            0.0F,
            0.5F
         );

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getNodeCount(); ++â˜ƒx) {
            Node â˜ƒxx = â˜ƒ.getNode(â˜ƒx);
            if (distanceToCamera(â˜ƒxx.asBlockPos(), â˜ƒ, â˜ƒ, â˜ƒ) <= 80.0F) {
               float â˜ƒxxx = â˜ƒx == â˜ƒ.getNextNodeIndex() ? 1.0F : 0.0F;
               float â˜ƒxxxx = â˜ƒx == â˜ƒ.getNextNodeIndex() ? 0.0F : 1.0F;
               DebugRenderer.renderFilledBox(
                  new AABB(
                        (double)((float)â˜ƒxx.x + 0.5F - â˜ƒ),
                        (double)((float)â˜ƒxx.y + 0.01F * (float)â˜ƒx),
                        (double)((float)â˜ƒxx.z + 0.5F - â˜ƒ),
                        (double)((float)â˜ƒxx.x + 0.5F + â˜ƒ),
                        (double)((float)â˜ƒxx.y + 0.25F + 0.01F * (float)â˜ƒx),
                        (double)((float)â˜ƒxx.z + 0.5F + â˜ƒ)
                     )
                     .move(-â˜ƒ, -â˜ƒ, -â˜ƒ),
                  â˜ƒxxx,
                  0.0F,
                  â˜ƒxxxx,
                  0.5F
               );
            }
         }
      }

      if (â˜ƒ) {
         for(Node â˜ƒ : â˜ƒ.getClosedSet()) {
            if (distanceToCamera(â˜ƒ.asBlockPos(), â˜ƒ, â˜ƒ, â˜ƒ) <= 80.0F) {
               DebugRenderer.renderFilledBox(
                  new AABB(
                        (double)((float)â˜ƒ.x + 0.5F - â˜ƒ / 2.0F),
                        (double)((float)â˜ƒ.y + 0.01F),
                        (double)((float)â˜ƒ.z + 0.5F - â˜ƒ / 2.0F),
                        (double)((float)â˜ƒ.x + 0.5F + â˜ƒ / 2.0F),
                        (double)â˜ƒ.y + 0.1,
                        (double)((float)â˜ƒ.z + 0.5F + â˜ƒ / 2.0F)
                     )
                     .move(-â˜ƒ, -â˜ƒ, -â˜ƒ),
                  1.0F,
                  0.8F,
                  0.8F,
                  0.5F
               );
            }
         }

         for(Node â˜ƒ : â˜ƒ.getOpenSet()) {
            if (distanceToCamera(â˜ƒ.asBlockPos(), â˜ƒ, â˜ƒ, â˜ƒ) <= 80.0F) {
               DebugRenderer.renderFilledBox(
                  new AABB(
                        (double)((float)â˜ƒ.x + 0.5F - â˜ƒ / 2.0F),
                        (double)((float)â˜ƒ.y + 0.01F),
                        (double)((float)â˜ƒ.z + 0.5F - â˜ƒ / 2.0F),
                        (double)((float)â˜ƒ.x + 0.5F + â˜ƒ / 2.0F),
                        (double)â˜ƒ.y + 0.1,
                        (double)((float)â˜ƒ.z + 0.5F + â˜ƒ / 2.0F)
                     )
                     .move(-â˜ƒ, -â˜ƒ, -â˜ƒ),
                  0.8F,
                  1.0F,
                  1.0F,
                  0.5F
               );
            }
         }
      }

      if (â˜ƒ) {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getNodeCount(); ++â˜ƒ) {
            Node â˜ƒx = â˜ƒ.getNode(â˜ƒ);
            if (distanceToCamera(â˜ƒx.asBlockPos(), â˜ƒ, â˜ƒ, â˜ƒ) <= 80.0F) {
               DebugRenderer.renderFloatingText(
                  String.format("%s", â˜ƒx.type), (double)â˜ƒx.x + 0.5, (double)â˜ƒx.y + 0.75, (double)â˜ƒx.z + 0.5, -1, 0.02F, true, 0.0F, true
               );
               DebugRenderer.renderFloatingText(
                  String.format(Locale.ROOT, "%.2f", â˜ƒx.costMalus),
                  (double)â˜ƒx.x + 0.5,
                  (double)â˜ƒx.y + 0.25,
                  (double)â˜ƒx.z + 0.5,
                  -1,
                  0.02F,
                  true,
                  0.0F,
                  true
               );
            }
         }
      }
   }

   public static void renderPathLine(Path var0, double var1, double var3, double var5) {
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      â˜ƒx.begin(VertexFormat.Mode.LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getNodeCount(); ++â˜ƒxx) {
         Node â˜ƒxxx = â˜ƒ.getNode(â˜ƒxx);
         if (!(distanceToCamera(â˜ƒxxx.asBlockPos(), â˜ƒ, â˜ƒ, â˜ƒ) > 80.0F)) {
            float â˜ƒxxxx = (float)â˜ƒxx / (float)â˜ƒ.getNodeCount() * 0.33F;
            int â˜ƒxxxxx = â˜ƒxx == 0 ? 0 : Mth.hsvToRgb(â˜ƒxxxx, 0.9F, 0.9F);
            int â˜ƒxxxxxx = â˜ƒxxxxx >> 16 & 0xFF;
            int â˜ƒxxxxxxx = â˜ƒxxxxx >> 8 & 0xFF;
            int â˜ƒxxxxxxxx = â˜ƒxxxxx & 0xFF;
            â˜ƒx.vertex((double)â˜ƒxxx.x - â˜ƒ + 0.5, (double)â˜ƒxxx.y - â˜ƒ + 0.5, (double)â˜ƒxxx.z - â˜ƒ + 0.5)
               .color(â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, 255)
               .endVertex();
         }
      }

      â˜ƒ.end();
   }

   private static float distanceToCamera(BlockPos var0, double var1, double var3, double var5) {
      return (float)(Math.abs((double)â˜ƒ.getX() - â˜ƒ) + Math.abs((double)â˜ƒ.getY() - â˜ƒ) + Math.abs((double)â˜ƒ.getZ() - â˜ƒ));
   }
}
