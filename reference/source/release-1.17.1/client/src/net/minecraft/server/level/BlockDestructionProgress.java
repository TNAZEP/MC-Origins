package net.minecraft.server.level;

import net.minecraft.core.BlockPos;

public class BlockDestructionProgress implements Comparable<BlockDestructionProgress> {
   private final int id;
   private final BlockPos pos;
   private int progress;
   private int updatedRenderTick;

   public BlockDestructionProgress(int var1, BlockPos var2) {
      this.id = â˜ƒ;
      this.pos = â˜ƒ;
   }

   public int getId() {
      return this.id;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public void setProgress(int var1) {
      if (â˜ƒ > 10) {
         â˜ƒ = 10;
      }

      this.progress = â˜ƒ;
   }

   public int getProgress() {
      return this.progress;
   }

   public void updateTick(int var1) {
      this.updatedRenderTick = â˜ƒ;
   }

   public int getUpdatedRenderTick() {
      return this.updatedRenderTick;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         BlockDestructionProgress â˜ƒ = (BlockDestructionProgress)â˜ƒ;
         return this.id == â˜ƒ.id;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Integer.hashCode(this.id);
   }

   public int compareTo(BlockDestructionProgress var1) {
      return this.progress != â˜ƒ.progress ? Integer.compare(this.progress, â˜ƒ.progress) : Integer.compare(this.id, â˜ƒ.id);
   }
}
