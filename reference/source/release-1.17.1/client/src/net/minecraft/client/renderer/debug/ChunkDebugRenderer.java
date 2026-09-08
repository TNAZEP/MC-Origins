package net.minecraft.client.renderer.debug;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class ChunkDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   final Minecraft minecraft;
   private double lastUpdateTime = Double.MIN_VALUE;
   private final int radius = 12;
   @Nullable
   private ChunkDebugRenderer.ChunkData data;

   public ChunkDebugRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      double â˜ƒ = (double)Util.getNanos();
      if (â˜ƒ - this.lastUpdateTime > 3.0E9) {
         this.lastUpdateTime = â˜ƒ;
         IntegratedServer â˜ƒx = this.minecraft.getSingleplayerServer();
         if (â˜ƒx != null) {
            this.data = new ChunkDebugRenderer.ChunkData(â˜ƒx, â˜ƒ, â˜ƒ);
         } else {
            this.data = null;
         }
      }

      if (this.data != null) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.lineWidth(2.0F);
         RenderSystem.disableTexture();
         RenderSystem.depthMask(false);
         Map<ChunkPos, String> â˜ƒ = (Map)this.data.serverData.getNow(null);
         double â˜ƒx = this.minecraft.gameRenderer.getMainCamera().getPosition().y * 0.85;

         for(Entry<ChunkPos, String> â˜ƒxx : this.data.clientData.entrySet()) {
            ChunkPos â˜ƒxxx = (ChunkPos)â˜ƒxx.getKey();
            String â˜ƒxxxx = (String)â˜ƒxx.getValue();
            if (â˜ƒ != null) {
               â˜ƒxxxx = â˜ƒxxxx + (String)â˜ƒ.get(â˜ƒxxx);
            }

            String[] â˜ƒxxx = â˜ƒxxxx.split("\n");
            int â˜ƒxxxx = 0;

            for(String â˜ƒxxxxx : â˜ƒxxx) {
               DebugRenderer.renderFloatingText(
                  â˜ƒxxxxx,
                  (double)SectionPos.sectionToBlockCoord(â˜ƒxxx.x, 8),
                  â˜ƒx + (double)â˜ƒxxxx,
                  (double)SectionPos.sectionToBlockCoord(â˜ƒxxx.z, 8),
                  -1,
                  0.15F
               );
               â˜ƒxxxx -= 2;
            }
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableTexture();
         RenderSystem.disableBlend();
      }
   }

   final class ChunkData {
      final Map<ChunkPos, String> clientData;
      final CompletableFuture<Map<ChunkPos, String>> serverData;

      ChunkData(IntegratedServer var2, double var3, double var5) {
         ClientLevel â˜ƒ = ChunkDebugRenderer.this.minecraft.level;
         ResourceKey<Level> â˜ƒx = â˜ƒ.dimension();
         int â˜ƒxx = SectionPos.posToSectionCoord(â˜ƒ);
         int â˜ƒxxx = SectionPos.posToSectionCoord(â˜ƒ);
         Builder<ChunkPos, String> â˜ƒxxxx = ImmutableMap.builder();
         ClientChunkCache â˜ƒxxxxx = â˜ƒ.getChunkSource();

         for(int â˜ƒxxxxxx = â˜ƒxx - 12; â˜ƒxxxxxx <= â˜ƒxx + 12; ++â˜ƒxxxxxx) {
            for(int â˜ƒxxxxxxx = â˜ƒxxx - 12; â˜ƒxxxxxxx <= â˜ƒxxx + 12; ++â˜ƒxxxxxxx) {
               ChunkPos â˜ƒxxxxxxxx = new ChunkPos(â˜ƒxxxxxx, â˜ƒxxxxxxx);
               String â˜ƒxxxxxxxxx = "";
               LevelChunk â˜ƒxxxxxxxxxx = â˜ƒxxxxx.getChunk(â˜ƒxxxxxx, â˜ƒxxxxxxx, false);
               â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxx + "Client: ";
               if (â˜ƒxxxxxxxxxx == null) {
                  â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxx + "0n/a\n";
               } else {
                  â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxx + (â˜ƒxxxxxxxxxx.isEmpty() ? " E" : "");
                  â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxx + "\n";
               }

               â˜ƒxxxx.put(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
            }
         }

         this.clientData = â˜ƒxxxx.build();
         this.serverData = â˜ƒ.submit(() -> {
            ServerLevel â˜ƒ = â˜ƒ.getLevel(â˜ƒ);
            if (â˜ƒ == null) {
               return ImmutableMap.of();
            } else {
               Builder<ChunkPos, String> â˜ƒ = ImmutableMap.builder();
               ServerChunkCache â˜ƒx = â˜ƒ.getChunkSource();

               for(int â˜ƒxx = â˜ƒ - 12; â˜ƒxx <= â˜ƒ + 12; ++â˜ƒxx) {
                  for(int â˜ƒxxx = â˜ƒ - 12; â˜ƒxxx <= â˜ƒ + 12; ++â˜ƒxxx) {
                     ChunkPos â˜ƒxxxx = new ChunkPos(â˜ƒxx, â˜ƒxxx);
                     â˜ƒ.put(â˜ƒxxxx, "Server: " + â˜ƒx.getChunkDebugData(â˜ƒxxxx));
                  }
               }

               return â˜ƒ.build();
            }
         });
      }
   }
}
