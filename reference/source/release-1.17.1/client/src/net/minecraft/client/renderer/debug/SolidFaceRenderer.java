package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SolidFaceRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft minecraft;

   public SolidFaceRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      BlockGetter â˜ƒ = this.minecraft.player.level;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.lineWidth(2.0F);
      RenderSystem.disableTexture();
      RenderSystem.depthMask(false);
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      BlockPos â˜ƒx = new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ);

      for(BlockPos â˜ƒxx : BlockPos.betweenClosed(â˜ƒx.offset(-6, -6, -6), â˜ƒx.offset(6, 6, 6))) {
         BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
         if (!â˜ƒxxx.is(Blocks.AIR)) {
            VoxelShape â˜ƒxxxx = â˜ƒxxx.getShape(â˜ƒ, â˜ƒxx);

            for(AABB â˜ƒxxxxx : â˜ƒxxxx.toAabbs()) {
               AABB â˜ƒxxxxxx = â˜ƒxxxxx.move(â˜ƒxx).inflate(0.002).move(-â˜ƒ, -â˜ƒ, -â˜ƒ);
               double â˜ƒxxxxxxx = â˜ƒxxxxxx.minX;
               double â˜ƒxxxxxxxx = â˜ƒxxxxxx.minY;
               double â˜ƒxxxxxxxxx = â˜ƒxxxxxx.minZ;
               double â˜ƒxxxxxxxxxx = â˜ƒxxxxxx.maxX;
               double â˜ƒxxxxxxxxxxx = â˜ƒxxxxxx.maxY;
               double â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxx.maxZ;
               float â˜ƒxxxxxxxxxxxxx = 1.0F;
               float â˜ƒxxxxxxxxxxxxxx = 0.0F;
               float â˜ƒxxxxxxxxxxxxxxx = 0.0F;
               float â˜ƒxxxxxxxxxxxxxxxx = 0.5F;
               if (â˜ƒxxx.isFaceSturdy(â˜ƒ, â˜ƒxx, Direction.WEST)) {
                  Tesselator â˜ƒxxxxxxxxxxxxxxxxx = Tesselator.getInstance();
                  BufferBuilder â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx.getBuilder();
                  â˜ƒxxxxxxxxxxxxxxxxxx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                  â˜ƒxxxxxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxxxxxxxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxxxxxxxxxxxx.end();
               }

               if (â˜ƒxxx.isFaceSturdy(â˜ƒ, â˜ƒxx, Direction.SOUTH)) {
                  Tesselator â˜ƒxxxxxx = Tesselator.getInstance();
                  BufferBuilder â˜ƒxxxxxxx = â˜ƒxxxxxx.getBuilder();
                  â˜ƒxxxxxxx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxx.end();
               }

               if (â˜ƒxxx.isFaceSturdy(â˜ƒ, â˜ƒxx, Direction.EAST)) {
                  Tesselator â˜ƒxxxxxx = Tesselator.getInstance();
                  BufferBuilder â˜ƒxxxxxxx = â˜ƒxxxxxx.getBuilder();
                  â˜ƒxxxxxxx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxx.end();
               }

               if (â˜ƒxxx.isFaceSturdy(â˜ƒ, â˜ƒxx, Direction.NORTH)) {
                  Tesselator â˜ƒxxxxxx = Tesselator.getInstance();
                  BufferBuilder â˜ƒxxxxxxx = â˜ƒxxxxxx.getBuilder();
                  â˜ƒxxxxxxx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxx.end();
               }

               if (â˜ƒxxx.isFaceSturdy(â˜ƒ, â˜ƒxx, Direction.DOWN)) {
                  Tesselator â˜ƒxxxxxx = Tesselator.getInstance();
                  BufferBuilder â˜ƒxxxxxxx = â˜ƒxxxxxx.getBuilder();
                  â˜ƒxxxxxxx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxx.end();
               }

               if (â˜ƒxxx.isFaceSturdy(â˜ƒ, â˜ƒxx, Direction.UP)) {
                  Tesselator â˜ƒxxxxxx = Tesselator.getInstance();
                  BufferBuilder â˜ƒxxxxxxx = â˜ƒxxxxxx.getBuilder();
                  â˜ƒxxxxxxx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxxx.vertex(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
                  â˜ƒxxxxxx.end();
               }
            }
         }
      }

      RenderSystem.depthMask(true);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }
}
