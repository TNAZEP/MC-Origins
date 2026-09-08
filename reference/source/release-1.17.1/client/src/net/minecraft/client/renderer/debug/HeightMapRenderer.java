package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;

public class HeightMapRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft minecraft;
   private static final int CHUNK_DIST = 2;
   private static final float BOX_HEIGHT = 0.09375F;

   public HeightMapRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      LevelAccessor â˜ƒ = this.minecraft.level;
      RenderSystem.disableBlend();
      RenderSystem.disableTexture();
      RenderSystem.enableDepthTest();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      BlockPos â˜ƒx = new BlockPos(â˜ƒ, 0.0, â˜ƒ);
      Tesselator â˜ƒxx = Tesselator.getInstance();
      BufferBuilder â˜ƒxxx = â˜ƒxx.getBuilder();
      â˜ƒxxx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

      for(int â˜ƒxxxx = -2; â˜ƒxxxx <= 2; ++â˜ƒxxxx) {
         for(int â˜ƒxxxxx = -2; â˜ƒxxxxx <= 2; ++â˜ƒxxxxx) {
            ChunkAccess â˜ƒxxxxxx = â˜ƒ.getChunk(â˜ƒx.offset(â˜ƒxxxx * 16, 0, â˜ƒxxxxx * 16));

            for(Entry<Heightmap.Types, Heightmap> â˜ƒxxxxxxx : â˜ƒxxxxxx.getHeightmaps()) {
               Heightmap.Types â˜ƒxxxxxxxx = (Heightmap.Types)â˜ƒxxxxxxx.getKey();
               ChunkPos â˜ƒxxxxxxxxx = â˜ƒxxxxxx.getPos();
               Vector3f â˜ƒxxxxxxxxxx = this.getColor(â˜ƒxxxxxxxx);

               for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx < 16; ++â˜ƒxxxxxxxxxxx) {
                  for(int â˜ƒxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxx < 16; ++â˜ƒxxxxxxxxxxxx) {
                     int â˜ƒxxxxxxxxxxxxx = SectionPos.sectionToBlockCoord(â˜ƒxxxxxxxxx.x, â˜ƒxxxxxxxxxxx);
                     int â˜ƒxxxxxxxxxxxxxx = SectionPos.sectionToBlockCoord(â˜ƒxxxxxxxxx.z, â˜ƒxxxxxxxxxxxx);
                     float â˜ƒxxxxxxxxxxxxxxx = (float)(
                        (double)((float)â˜ƒ.getHeight(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx) + (float)â˜ƒxxxxxxxx.ordinal() * 0.09375F) - â˜ƒ
                     );
                     LevelRenderer.addChainedFilledBoxVertices(
                        â˜ƒxxx,
                        (double)((float)â˜ƒxxxxxxxxxxxxx + 0.25F) - â˜ƒ,
                        (double)â˜ƒxxxxxxxxxxxxxxx,
                        (double)((float)â˜ƒxxxxxxxxxxxxxx + 0.25F) - â˜ƒ,
                        (double)((float)â˜ƒxxxxxxxxxxxxx + 0.75F) - â˜ƒ,
                        (double)(â˜ƒxxxxxxxxxxxxxxx + 0.09375F),
                        (double)((float)â˜ƒxxxxxxxxxxxxxx + 0.75F) - â˜ƒ,
                        â˜ƒxxxxxxxxxx.x(),
                        â˜ƒxxxxxxxxxx.y(),
                        â˜ƒxxxxxxxxxx.z(),
                        1.0F
                     );
                  }
               }
            }
         }
      }

      â˜ƒxx.end();
      RenderSystem.enableTexture();
   }

   private Vector3f getColor(Heightmap.Types var1) {
      switch(â˜ƒ) {
         case WORLD_SURFACE_WG:
            return new Vector3f(1.0F, 1.0F, 0.0F);
         case OCEAN_FLOOR_WG:
            return new Vector3f(1.0F, 0.0F, 1.0F);
         case WORLD_SURFACE:
            return new Vector3f(0.0F, 0.7F, 0.0F);
         case OCEAN_FLOOR:
            return new Vector3f(0.0F, 0.0F, 0.5F);
         case MOTION_BLOCKING:
            return new Vector3f(0.0F, 0.3F, 0.3F);
         case MOTION_BLOCKING_NO_LEAVES:
            return new Vector3f(0.0F, 0.5F, 0.5F);
         default:
            return new Vector3f(0.0F, 0.0F, 0.0F);
      }
   }
}
