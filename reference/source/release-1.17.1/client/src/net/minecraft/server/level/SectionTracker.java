package net.minecraft.server.level;

import net.minecraft.core.SectionPos;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;

public abstract class SectionTracker extends DynamicGraphMinFixedPoint {
   protected SectionTracker(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected boolean isSource(long var1) {
      return â˜ƒ == Long.MAX_VALUE;
   }

   @Override
   protected void checkNeighborsAfterUpdate(long var1, int var3, boolean var4) {
      for(int â˜ƒ = -1; â˜ƒ <= 1; ++â˜ƒ) {
         for(int â˜ƒx = -1; â˜ƒx <= 1; ++â˜ƒx) {
            for(int â˜ƒxx = -1; â˜ƒxx <= 1; ++â˜ƒxx) {
               long â˜ƒxxx = SectionPos.offset(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
               if (â˜ƒxxx != â˜ƒ) {
                  this.checkNeighbor(â˜ƒ, â˜ƒxxx, â˜ƒ, â˜ƒ);
               }
            }
         }
      }
   }

   @Override
   protected int getComputedLevel(long var1, long var3, int var5) {
      int â˜ƒ = â˜ƒ;

      for(int â˜ƒx = -1; â˜ƒx <= 1; ++â˜ƒx) {
         for(int â˜ƒxx = -1; â˜ƒxx <= 1; ++â˜ƒxx) {
            for(int â˜ƒxxx = -1; â˜ƒxxx <= 1; ++â˜ƒxxx) {
               long â˜ƒxxxx = SectionPos.offset(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
               if (â˜ƒxxxx == â˜ƒ) {
                  â˜ƒxxxx = Long.MAX_VALUE;
               }

               if (â˜ƒxxxx != â˜ƒ) {
                  int â˜ƒxxxx = this.computeLevelFromNeighbor(â˜ƒxxxx, â˜ƒ, this.getLevel(â˜ƒxxxx));
                  if (â˜ƒ > â˜ƒxxxx) {
                     â˜ƒ = â˜ƒxxxx;
                  }

                  if (â˜ƒ == 0) {
                     return â˜ƒ;
                  }
               }
            }
         }
      }

      return â˜ƒ;
   }

   @Override
   protected int computeLevelFromNeighbor(long var1, long var3, int var5) {
      return â˜ƒ == Long.MAX_VALUE ? this.getLevelFromSource(â˜ƒ) : â˜ƒ + 1;
   }

   protected abstract int getLevelFromSource(long var1);

   public void update(long var1, int var3, boolean var4) {
      this.checkEdge(Long.MAX_VALUE, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
