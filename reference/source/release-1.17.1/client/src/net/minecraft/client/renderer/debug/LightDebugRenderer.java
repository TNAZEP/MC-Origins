package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class LightDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft minecraft;
   private static final int MAX_RENDER_DIST = 10;

   public LightDebugRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      Level â˜ƒ = this.minecraft.level;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      BlockPos â˜ƒx = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      LongSet â˜ƒxx = new LongOpenHashSet();

      for(BlockPos â˜ƒxxx : BlockPos.betweenClosed(â˜ƒx.offset(-10, -10, -10), â˜ƒx.offset(10, 10, 10))) {
         int â˜ƒxxxx = â˜ƒ.getBrightness(LightLayer.SKY, â˜ƒxxx);
         float â˜ƒxxxxx = (float)(15 - â˜ƒxxxx) / 15.0F * 0.5F + 0.16F;
         int â˜ƒxxxxxx = Mth.hsvToRgb(â˜ƒxxxxx, 0.9F, 0.9F);
         long â˜ƒxxxxxxx = SectionPos.blockToSection(â˜ƒxxx.asLong());
         if (â˜ƒxx.add(â˜ƒxxxxxxx)) {
            DebugRenderer.renderFloatingText(
               â˜ƒ.getChunkSource().getLightEngine().getDebugData(LightLayer.SKY, SectionPos.of(â˜ƒxxxxxxx)),
               (double)SectionPos.sectionToBlockCoord(SectionPos.x(â˜ƒxxxxxxx), 8),
               (double)SectionPos.sectionToBlockCoord(SectionPos.y(â˜ƒxxxxxxx), 8),
               (double)SectionPos.sectionToBlockCoord(SectionPos.z(â˜ƒxxxxxxx), 8),
               16711680,
               0.3F
            );
         }

         if (â˜ƒxxxx != 15) {
            DebugRenderer.renderFloatingText(
               String.valueOf(â˜ƒxxxx), (double)â˜ƒxxx.getX() + 0.5, (double)â˜ƒxxx.getY() + 0.25, (double)â˜ƒxxx.getZ() + 0.5, â˜ƒxxxxxx
            );
         }
      }

      RenderSystem.enableTexture();
   }
}
