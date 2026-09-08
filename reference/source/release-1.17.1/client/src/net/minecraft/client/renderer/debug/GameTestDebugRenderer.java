package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;

public class GameTestDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final float PADDING = 0.02F;
   private final Map<BlockPos, GameTestDebugRenderer.Marker> markers = Maps.<BlockPos, GameTestDebugRenderer.Marker>newHashMap();

   public void addMarker(BlockPos var1, int var2, String var3, int var4) {
      this.markers.put(â˜ƒ, new GameTestDebugRenderer.Marker(â˜ƒ, â˜ƒ, Util.getMillis() + (long)â˜ƒ));
   }

   @Override
   public void clear() {
      this.markers.clear();
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      long â˜ƒ = Util.getMillis();
      this.markers.entrySet().removeIf(var2x -> â˜ƒ > ((GameTestDebugRenderer.Marker)var2x.getValue()).removeAtTime);
      this.markers.forEach(this::renderMarker);
   }

   private void renderMarker(BlockPos var1, GameTestDebugRenderer.Marker var2) {
      RenderSystem.enableBlend();
      RenderSystem.blendFuncSeparate(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      RenderSystem.setShaderColor(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      DebugRenderer.renderFilledBox(â˜ƒ, 0.02F, â˜ƒ.getR(), â˜ƒ.getG(), â˜ƒ.getB(), â˜ƒ.getA());
      if (!â˜ƒ.text.isEmpty()) {
         double â˜ƒ = (double)â˜ƒ.getX() + 0.5;
         double â˜ƒx = (double)â˜ƒ.getY() + 1.2;
         double â˜ƒxx = (double)â˜ƒ.getZ() + 0.5;
         DebugRenderer.renderFloatingText(â˜ƒ.text, â˜ƒ, â˜ƒx, â˜ƒxx, -1, 0.01F, true, 0.0F, true);
      }

      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }

   static class Marker {
      public int color;
      public String text;
      public long removeAtTime;

      public Marker(int var1, String var2, long var3) {
         this.color = â˜ƒ;
         this.text = â˜ƒ;
         this.removeAtTime = â˜ƒ;
      }

      public float getR() {
         return (float)(this.color >> 16 & 0xFF) / 255.0F;
      }

      public float getG() {
         return (float)(this.color >> 8 & 0xFF) / 255.0F;
      }

      public float getB() {
         return (float)(this.color & 0xFF) / 255.0F;
      }

      public float getA() {
         return (float)(this.color >> 24 & 0xFF) / 255.0F;
      }
   }
}
