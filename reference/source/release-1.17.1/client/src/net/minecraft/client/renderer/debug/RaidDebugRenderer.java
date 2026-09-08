package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;

public class RaidDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final int MAX_RENDER_DIST = 160;
   private static final float TEXT_SCALE = 0.04F;
   private final Minecraft minecraft;
   private Collection<BlockPos> raidCenters = Lists.<BlockPos>newArrayList();

   public RaidDebugRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   public void setRaidCenters(Collection<BlockPos> var1) {
      this.raidCenters = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      BlockPos â˜ƒ = this.getCamera().getBlockPosition();

      for(BlockPos â˜ƒx : this.raidCenters) {
         if (â˜ƒ.closerThan(â˜ƒx, 160.0)) {
            highlightRaidCenter(â˜ƒx);
         }
      }
   }

   private static void highlightRaidCenter(BlockPos var0) {
      DebugRenderer.renderFilledBox(â˜ƒ.offset(-0.5, -0.5, -0.5), â˜ƒ.offset(1.5, 1.5, 1.5), 1.0F, 0.0F, 0.0F, 0.15F);
      int â˜ƒ = -65536;
      renderTextOverBlock("Raid center", â˜ƒ, -65536);
   }

   private static void renderTextOverBlock(String var0, BlockPos var1, int var2) {
      double â˜ƒ = (double)â˜ƒ.getX() + 0.5;
      double â˜ƒx = (double)â˜ƒ.getY() + 1.3;
      double â˜ƒxx = (double)â˜ƒ.getZ() + 0.5;
      DebugRenderer.renderFloatingText(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ, 0.04F, true, 0.0F, true);
   }

   private Camera getCamera() {
      return this.minecraft.gameRenderer.getMainCamera();
   }
}
