package net.minecraft.client.renderer.debug;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public class VillageSectionsDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final int MAX_RENDER_DIST_FOR_VILLAGE_SECTIONS = 60;
   private final Set<SectionPos> villageSections = Sets.<SectionPos>newHashSet();

   VillageSectionsDebugRenderer() {
   }

   @Override
   public void clear() {
      this.villageSections.clear();
   }

   public void setVillageSection(SectionPos var1) {
      this.villageSections.add(â˜ƒ);
   }

   public void setNotVillageSection(SectionPos var1) {
      this.villageSections.remove(â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      this.doRender(â˜ƒ, â˜ƒ, â˜ƒ);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }

   private void doRender(double var1, double var3, double var5) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      this.villageSections.forEach(var1x -> {
         if (â˜ƒ.closerThan(var1x.center(), 60.0)) {
            highlightVillageSection(var1x);
         }
      });
   }

   private static void highlightVillageSection(SectionPos var0) {
      float â˜ƒ = 1.0F;
      BlockPos â˜ƒx = â˜ƒ.center();
      BlockPos â˜ƒxx = â˜ƒx.offset(-1.0, -1.0, -1.0);
      BlockPos â˜ƒxxx = â˜ƒx.offset(1.0, 1.0, 1.0);
      DebugRenderer.renderFilledBox(â˜ƒxx, â˜ƒxxx, 0.2F, 1.0F, 0.2F, 0.15F);
   }
}
