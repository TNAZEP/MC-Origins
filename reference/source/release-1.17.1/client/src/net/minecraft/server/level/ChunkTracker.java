package net.minecraft.server.level;

import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;

public abstract class ChunkTracker extends DynamicGraphMinFixedPoint {
   protected ChunkTracker(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean isSource(long var1) {
      return â˜ƒ == ChunkPos.INVALID_CHUNK_POS;
   }

   @Override
   protected void checkNeighborsAfterUpdate(long var1, int var3, boolean var4) {
      ChunkPos â˜ƒ = new ChunkPos(â˜ƒ);
      int â˜ƒx = â˜ƒ.x;
      int â˜ƒxx = â˜ƒ.z;

      for(int â˜ƒxxx = -1; â˜ƒxxx <= 1; ++â˜ƒxxx) {
         for(int â˜ƒxxxx = -1; â˜ƒxxxx <= 1; ++â˜ƒxxxx) {
            long â˜ƒxxxxx = ChunkPos.asLong(â˜ƒx + â˜ƒxxx, â˜ƒxx + â˜ƒxxxx);
            if (â˜ƒxxxxx != â˜ƒ) {
               this.checkNeighbor(â˜ƒ, â˜ƒxxxxx, â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   @Override
   protected int getComputedLevel(long var1, long var3, int var5) {
      int â˜ƒ = â˜ƒ;
      ChunkPos â˜ƒx = new ChunkPos(â˜ƒ);
      int â˜ƒxx = â˜ƒx.x;
      int â˜ƒxxx = â˜ƒx.z;

      for(int â˜ƒxxxx = -1; â˜ƒxxxx <= 1; ++â˜ƒxxxx) {
         for(int â˜ƒxxxxx = -1; â˜ƒxxxxx <= 1; ++â˜ƒxxxxx) {
            long â˜ƒxxxxxx = ChunkPos.asLong(â˜ƒxx + â˜ƒxxxx, â˜ƒxxx + â˜ƒxxxxx);
            if (â˜ƒxxxxxx == â˜ƒ) {
               â˜ƒxxxxxx = ChunkPos.INVALID_CHUNK_POS;
            }

            if (â˜ƒxxxxxx != â˜ƒ) {
               int â˜ƒxxxxxx = this.computeLevelFromNeighbor(â˜ƒxxxxxx, â˜ƒ, this.getLevel(â˜ƒxxxxxx));
               if (â˜ƒ > â˜ƒxxxxxx) {
                  â˜ƒ = â˜ƒxxxxxx;
               }

               if (â˜ƒ == 0) {
                  return â˜ƒ;
               }
            }
         }
      }

      return â˜ƒ;
   }

   @Override
   protected int computeLevelFromNeighbor(long var1, long var3, int var5) {
      return â˜ƒ == ChunkPos.INVALID_CHUNK_POS ? this.getLevelFromSource(â˜ƒ) : â˜ƒ + 1;
   }

   protected abstract int getLevelFromSource(long var1);

   public void update(long var1, int var3, boolean var4) {
      this.checkEdge(ChunkPos.INVALID_CHUNK_POS, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
