package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.List;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;

public class WorldGenAttemptRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final List<BlockPos> toRender = Lists.<BlockPos>newArrayList();
   private final List<Float> scales = Lists.newArrayList();
   private final List<Float> alphas = Lists.newArrayList();
   private final List<Float> reds = Lists.newArrayList();
   private final List<Float> greens = Lists.newArrayList();
   private final List<Float> blues = Lists.newArrayList();

   public void addPos(BlockPos var1, float var2, float var3, float var4, float var5, float var6) {
      this.toRender.add(â˜ƒ);
      this.scales.add(â˜ƒ);
      this.alphas.add(â˜ƒ);
      this.reds.add(â˜ƒ);
      this.greens.add(â˜ƒ);
      this.blues.add(â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      â˜ƒx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

      for(int â˜ƒxx = 0; â˜ƒxx < this.toRender.size(); ++â˜ƒxx) {
         BlockPos â˜ƒxxx = (BlockPos)this.toRender.get(â˜ƒxx);
         Float â˜ƒxxxx = (Float)this.scales.get(â˜ƒxx);
         float â˜ƒxxxxx = â˜ƒxxxx / 2.0F;
         LevelRenderer.addChainedFilledBoxVertices(
            â˜ƒx,
            (double)((float)â˜ƒxxx.getX() + 0.5F - â˜ƒxxxxx) - â˜ƒ,
            (double)((float)â˜ƒxxx.getY() + 0.5F - â˜ƒxxxxx) - â˜ƒ,
            (double)((float)â˜ƒxxx.getZ() + 0.5F - â˜ƒxxxxx) - â˜ƒ,
            (double)((float)â˜ƒxxx.getX() + 0.5F + â˜ƒxxxxx) - â˜ƒ,
            (double)((float)â˜ƒxxx.getY() + 0.5F + â˜ƒxxxxx) - â˜ƒ,
            (double)((float)â˜ƒxxx.getZ() + 0.5F + â˜ƒxxxxx) - â˜ƒ,
            this.reds.get(â˜ƒxx),
            this.greens.get(â˜ƒxx),
            this.blues.get(â˜ƒxx),
            this.alphas.get(â˜ƒxx)
         );
      }

      â˜ƒ.end();
      RenderSystem.enableTexture();
   }
}
