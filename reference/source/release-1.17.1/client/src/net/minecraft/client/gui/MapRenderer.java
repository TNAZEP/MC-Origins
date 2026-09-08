package net.minecraft.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class MapRenderer implements AutoCloseable {
   private static final ResourceLocation MAP_ICONS_LOCATION = new ResourceLocation("textures/map/map_icons.png");
   static final RenderType MAP_ICONS = RenderType.text(MAP_ICONS_LOCATION);
   private static final int WIDTH = 128;
   private static final int HEIGHT = 128;
   final TextureManager textureManager;
   private final Int2ObjectMap<MapRenderer.MapInstance> maps = new Int2ObjectOpenHashMap<>();

   public MapRenderer(TextureManager var1) {
      this.textureManager = â˜ƒ;
   }

   public void update(int var1, MapItemSavedData var2) {
      this.getOrCreateMapInstance(â˜ƒ, â˜ƒ).forceUpload();
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, MapItemSavedData var4, boolean var5, int var6) {
      this.getOrCreateMapInstance(â˜ƒ, â˜ƒ).draw(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private MapRenderer.MapInstance getOrCreateMapInstance(int var1, MapItemSavedData var2) {
      return this.maps.compute(â˜ƒ, (var2x, var3) -> {
         if (var3 == null) {
            return new MapRenderer.MapInstance(var2x, â˜ƒ);
         } else {
            var3.replaceMapData(â˜ƒ);
            return var3;
         }
      });
   }

   public void resetData() {
      for(MapRenderer.MapInstance â˜ƒ : this.maps.values()) {
         â˜ƒ.close();
      }

      this.maps.clear();
   }

   public void close() {
      this.resetData();
   }

   class MapInstance implements AutoCloseable {
      private MapItemSavedData data;
      private final DynamicTexture texture;
      private final RenderType renderType;
      private boolean requiresUpload = true;

      MapInstance(int var2, MapItemSavedData var3) {
         this.data = â˜ƒ;
         this.texture = new DynamicTexture(128, 128, true);
         ResourceLocation â˜ƒ = MapRenderer.this.textureManager.register("map/" + â˜ƒ, this.texture);
         this.renderType = RenderType.text(â˜ƒ);
      }

      void replaceMapData(MapItemSavedData var1) {
         boolean â˜ƒ = this.data != â˜ƒ;
         this.data = â˜ƒ;
         this.requiresUpload |= â˜ƒ;
      }

      public void forceUpload() {
         this.requiresUpload = true;
      }

      private void updateTexture() {
         for(int â˜ƒ = 0; â˜ƒ < 128; ++â˜ƒ) {
            for(int â˜ƒx = 0; â˜ƒx < 128; ++â˜ƒx) {
               int â˜ƒxx = â˜ƒx + â˜ƒ * 128;
               int â˜ƒxxx = this.data.colors[â˜ƒxx] & 255;
               if (â˜ƒxxx / 4 == 0) {
                  this.texture.getPixels().setPixelRGBA(â˜ƒx, â˜ƒ, 0);
               } else {
                  this.texture.getPixels().setPixelRGBA(â˜ƒx, â˜ƒ, MaterialColor.MATERIAL_COLORS[â˜ƒxxx / 4].calculateRGBColor(â˜ƒxxx & 3));
               }
            }
         }

         this.texture.upload();
      }

      void draw(PoseStack var1, MultiBufferSource var2, boolean var3, int var4) {
         if (this.requiresUpload) {
            this.updateTexture();
            this.requiresUpload = false;
         }

         int â˜ƒ = 0;
         int â˜ƒx = 0;
         float â˜ƒxx = 0.0F;
         Matrix4f â˜ƒxxx = â˜ƒ.last().pose();
         VertexConsumer â˜ƒxxxx = â˜ƒ.getBuffer(this.renderType);
         â˜ƒxxxx.vertex(â˜ƒxxx, 0.0F, 128.0F, -0.01F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2(â˜ƒ).endVertex();
         â˜ƒxxxx.vertex(â˜ƒxxx, 128.0F, 128.0F, -0.01F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2(â˜ƒ).endVertex();
         â˜ƒxxxx.vertex(â˜ƒxxx, 128.0F, 0.0F, -0.01F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2(â˜ƒ).endVertex();
         â˜ƒxxxx.vertex(â˜ƒxxx, 0.0F, 0.0F, -0.01F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2(â˜ƒ).endVertex();
         int â˜ƒxxxxx = 0;

         for(MapDecoration â˜ƒxxxxxx : this.data.getDecorations()) {
            if (!â˜ƒ || â˜ƒxxxxxx.renderOnFrame()) {
               â˜ƒ.pushPose();
               â˜ƒ.translate((double)(0.0F + (float)â˜ƒxxxxxx.getX() / 2.0F + 64.0F), (double)(0.0F + (float)â˜ƒxxxxxx.getY() / 2.0F + 64.0F), -0.02F);
               â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees((float)(â˜ƒxxxxxx.getRot() * 360) / 16.0F));
               â˜ƒ.scale(4.0F, 4.0F, 3.0F);
               â˜ƒ.translate(-0.125, 0.125, 0.0);
               byte â˜ƒxxxxxxx = â˜ƒxxxxxx.getImage();
               float â˜ƒxxxxxxxx = (float)(â˜ƒxxxxxxx % 16 + 0) / 16.0F;
               float â˜ƒxxxxxxxxx = (float)(â˜ƒxxxxxxx / 16 + 0) / 16.0F;
               float â˜ƒxxxxxxxxxx = (float)(â˜ƒxxxxxxx % 16 + 1) / 16.0F;
               float â˜ƒxxxxxxxxxxx = (float)(â˜ƒxxxxxxx / 16 + 1) / 16.0F;
               Matrix4f â˜ƒxxxxxxxxxxxx = â˜ƒ.last().pose();
               float â˜ƒxxxxxxxxxxxxx = -0.001F;
               VertexConsumer â˜ƒxxxxxxxxxxxxxx = â˜ƒ.getBuffer(MapRenderer.MAP_ICONS);
               â˜ƒxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxxxx, -1.0F, 1.0F, (float)â˜ƒxxxxx * -0.001F)
                  .color(255, 255, 255, 255)
                  .uv(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx)
                  .uv2(â˜ƒ)
                  .endVertex();
               â˜ƒxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxxxx, 1.0F, 1.0F, (float)â˜ƒxxxxx * -0.001F)
                  .color(255, 255, 255, 255)
                  .uv(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx)
                  .uv2(â˜ƒ)
                  .endVertex();
               â˜ƒxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxxxx, 1.0F, -1.0F, (float)â˜ƒxxxxx * -0.001F)
                  .color(255, 255, 255, 255)
                  .uv(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx)
                  .uv2(â˜ƒ)
                  .endVertex();
               â˜ƒxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxxxx, -1.0F, -1.0F, (float)â˜ƒxxxxx * -0.001F)
                  .color(255, 255, 255, 255)
                  .uv(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxx)
                  .uv2(â˜ƒ)
                  .endVertex();
               â˜ƒ.popPose();
               if (â˜ƒxxxxxx.getName() != null) {
                  Font â˜ƒxxxxxxxxxxxxxxx = Minecraft.getInstance().font;
                  Component â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxx.getName();
                  float â˜ƒxxxxxxxxxxxxxxxxx = (float)â˜ƒxxxxxxxxxxxxxxx.width(â˜ƒxxxxxxxxxxxxxxxx);
                  float â˜ƒxxxxxxxxxxxxxxxxxx = Mth.clamp(25.0F / â˜ƒxxxxxxxxxxxxxxxxx, 0.0F, 6.0F / 9.0F);
                  â˜ƒ.pushPose();
                  â˜ƒ.translate(
                     (double)(0.0F + (float)â˜ƒxxxxxx.getX() / 2.0F + 64.0F - â˜ƒxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxx / 2.0F),
                     (double)(0.0F + (float)â˜ƒxxxxxx.getY() / 2.0F + 64.0F + 4.0F),
                     -0.025F
                  );
                  â˜ƒ.scale(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, 1.0F);
                  â˜ƒ.translate(0.0, 0.0, -0.1F);
                  â˜ƒxxxxxxxxxxxxxxx.drawInBatch(â˜ƒxxxxxxxxxxxxxxxx, 0.0F, 0.0F, -1, false, â˜ƒ.last().pose(), â˜ƒ, false, Integer.MIN_VALUE, â˜ƒ);
                  â˜ƒ.popPose();
               }

               ++â˜ƒxxxxx;
            }
         }
      }

      public void close() {
         this.texture.close();
      }
   }
}
