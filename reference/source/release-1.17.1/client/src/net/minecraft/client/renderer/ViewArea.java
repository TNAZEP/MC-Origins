package net.minecraft.client.renderer;

import javax.annotation.Nullable;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public class ViewArea {
   protected final LevelRenderer levelRenderer;
   protected final Level level;
   protected int chunkGridSizeY;
   protected int chunkGridSizeX;
   protected int chunkGridSizeZ;
   public ChunkRenderDispatcher.RenderChunk[] chunks;

   public ViewArea(ChunkRenderDispatcher var1, Level var2, int var3, LevelRenderer var4) {
      this.levelRenderer = â˜ƒ;
      this.level = â˜ƒ;
      this.setViewDistance(â˜ƒ);
      this.createChunks(â˜ƒ);
   }

   protected void createChunks(ChunkRenderDispatcher var1) {
      int â˜ƒ = this.chunkGridSizeX * this.chunkGridSizeY * this.chunkGridSizeZ;
      this.chunks = new ChunkRenderDispatcher.RenderChunk[â˜ƒ];

      for(int â˜ƒx = 0; â˜ƒx < this.chunkGridSizeX; ++â˜ƒx) {
         for(int â˜ƒxx = 0; â˜ƒxx < this.chunkGridSizeY; ++â˜ƒxx) {
            for(int â˜ƒxxx = 0; â˜ƒxxx < this.chunkGridSizeZ; ++â˜ƒxxx) {
               int â˜ƒxxxx = this.getChunkIndex(â˜ƒx, â˜ƒxx, â˜ƒxxx);
               this.chunks[â˜ƒxxxx] = â˜ƒ.new RenderChunk(â˜ƒxxxx);
               this.chunks[â˜ƒxxxx].setOrigin(â˜ƒx * 16, â˜ƒxx * 16, â˜ƒxxx * 16);
            }
         }
      }
   }

   public void releaseAllBuffers() {
      for(ChunkRenderDispatcher.RenderChunk â˜ƒ : this.chunks) {
         â˜ƒ.releaseBuffers();
      }
   }

   private int getChunkIndex(int var1, int var2, int var3) {
      return (â˜ƒ * this.chunkGridSizeY + â˜ƒ) * this.chunkGridSizeX + â˜ƒ;
   }

   protected void setViewDistance(int var1) {
      int â˜ƒ = â˜ƒ * 2 + 1;
      this.chunkGridSizeX = â˜ƒ;
      this.chunkGridSizeY = this.level.getSectionsCount();
      this.chunkGridSizeZ = â˜ƒ;
   }

   public void repositionCamera(double var1, double var3) {
      int â˜ƒ = Mth.floor(â˜ƒ);
      int â˜ƒx = Mth.floor(â˜ƒ);

      for(int â˜ƒxx = 0; â˜ƒxx < this.chunkGridSizeX; ++â˜ƒxx) {
         int â˜ƒxxx = this.chunkGridSizeX * 16;
         int â˜ƒxxxx = â˜ƒ - 8 - â˜ƒxxx / 2;
         int â˜ƒxxxxx = â˜ƒxxxx + Math.floorMod(â˜ƒxx * 16 - â˜ƒxxxx, â˜ƒxxx);

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < this.chunkGridSizeZ; ++â˜ƒxxxxxx) {
            int â˜ƒxxxxxxx = this.chunkGridSizeZ * 16;
            int â˜ƒxxxxxxxx = â˜ƒx - 8 - â˜ƒxxxxxxx / 2;
            int â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx + Math.floorMod(â˜ƒxxxxxx * 16 - â˜ƒxxxxxxxx, â˜ƒxxxxxxx);

            for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx < this.chunkGridSizeY; ++â˜ƒxxxxxxxxxx) {
               int â˜ƒxxxxxxxxxxx = this.level.getMinBuildHeight() + â˜ƒxxxxxxxxxx * 16;
               ChunkRenderDispatcher.RenderChunk â˜ƒxxxxxxxxxxxx = this.chunks[this.getChunkIndex(â˜ƒxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxx)];
               â˜ƒxxxxxxxxxxxx.setOrigin(â˜ƒxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxx);
            }
         }
      }
   }

   public void setDirty(int var1, int var2, int var3, boolean var4) {
      int â˜ƒ = Math.floorMod(â˜ƒ, this.chunkGridSizeX);
      int â˜ƒx = Math.floorMod(â˜ƒ - this.level.getMinSection(), this.chunkGridSizeY);
      int â˜ƒxx = Math.floorMod(â˜ƒ, this.chunkGridSizeZ);
      ChunkRenderDispatcher.RenderChunk â˜ƒxxx = this.chunks[this.getChunkIndex(â˜ƒ, â˜ƒx, â˜ƒxx)];
      â˜ƒxxx.setDirty(â˜ƒ);
   }

   @Nullable
   protected ChunkRenderDispatcher.RenderChunk getRenderChunkAt(BlockPos var1) {
      int â˜ƒ = Mth.intFloorDiv(â˜ƒ.getX(), 16);
      int â˜ƒx = Mth.intFloorDiv(â˜ƒ.getY() - this.level.getMinBuildHeight(), 16);
      int â˜ƒxx = Mth.intFloorDiv(â˜ƒ.getZ(), 16);
      if (â˜ƒx >= 0 && â˜ƒx < this.chunkGridSizeY) {
         â˜ƒ = Mth.positiveModulo(â˜ƒ, this.chunkGridSizeX);
         â˜ƒxx = Mth.positiveModulo(â˜ƒxx, this.chunkGridSizeZ);
         return this.chunks[this.getChunkIndex(â˜ƒ, â˜ƒx, â˜ƒxx)];
      } else {
         return null;
      }
   }
}
