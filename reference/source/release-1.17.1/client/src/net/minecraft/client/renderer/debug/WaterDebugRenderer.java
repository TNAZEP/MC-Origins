package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

public class WaterDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft minecraft;

   public WaterDebugRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      BlockPos â˜ƒ = this.minecraft.player.blockPosition();
      LevelReader â˜ƒx = this.minecraft.player.level;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderColor(0.0F, 1.0F, 0.0F, 0.75F);
      RenderSystem.disableTexture();
      RenderSystem.lineWidth(6.0F);

      for(BlockPos â˜ƒxx : BlockPos.betweenClosed(â˜ƒ.offset(-10, -10, -10), â˜ƒ.offset(10, 10, 10))) {
         FluidState â˜ƒxxx = â˜ƒx.getFluidState(â˜ƒxx);
         if (â˜ƒxxx.is(FluidTags.WATER)) {
            double â˜ƒxxxx = (double)((float)â˜ƒxx.getY() + â˜ƒxxx.getHeight(â˜ƒx, â˜ƒxx));
            DebugRenderer.renderFilledBox(
               new AABB(
                     (double)((float)â˜ƒxx.getX() + 0.01F),
                     (double)((float)â˜ƒxx.getY() + 0.01F),
                     (double)((float)â˜ƒxx.getZ() + 0.01F),
                     (double)((float)â˜ƒxx.getX() + 0.99F),
                     â˜ƒxxxx,
                     (double)((float)â˜ƒxx.getZ() + 0.99F)
                  )
                  .move(-â˜ƒ, -â˜ƒ, -â˜ƒ),
               1.0F,
               1.0F,
               1.0F,
               0.2F
            );
         }
      }

      for(BlockPos â˜ƒxx : BlockPos.betweenClosed(â˜ƒ.offset(-10, -10, -10), â˜ƒ.offset(10, 10, 10))) {
         FluidState â˜ƒxxx = â˜ƒx.getFluidState(â˜ƒxx);
         if (â˜ƒxxx.is(FluidTags.WATER)) {
            DebugRenderer.renderFloatingText(
               String.valueOf(â˜ƒxxx.getAmount()),
               (double)â˜ƒxx.getX() + 0.5,
               (double)((float)â˜ƒxx.getY() + â˜ƒxxx.getHeight(â˜ƒx, â˜ƒxx)),
               (double)â˜ƒxx.getZ() + 0.5,
               -16777216
            );
         }
      }

      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }
}
