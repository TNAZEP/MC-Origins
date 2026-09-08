package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public class BlockEventData {
   private final BlockPos pos;
   private final Block block;
   private final int paramA;
   private final int paramB;

   public BlockEventData(BlockPos var1, Block var2, int var3, int var4) {
      this.pos = â˜ƒ;
      this.block = â˜ƒ;
      this.paramA = â˜ƒ;
      this.paramB = â˜ƒ;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public Block getBlock() {
      return this.block;
   }

   public int getParamA() {
      return this.paramA;
   }

   public int getParamB() {
      return this.paramB;
   }

   public boolean equals(Object var1) {
      if (!(â˜ƒ instanceof BlockEventData)) {
         return false;
      } else {
         BlockEventData â˜ƒ = (BlockEventData)â˜ƒ;
         return this.pos.equals(â˜ƒ.pos) && this.paramA == â˜ƒ.paramA && this.paramB == â˜ƒ.paramB && this.block == â˜ƒ.block;
      }
   }

   public int hashCode() {
      int â˜ƒ = this.pos.hashCode();
      â˜ƒ = 31 * â˜ƒ + this.block.hashCode();
      â˜ƒ = 31 * â˜ƒ + this.paramA;
      return 31 * â˜ƒ + this.paramB;
   }

   public String toString() {
      return "TE(" + this.pos + ")," + this.paramA + "," + this.paramB + "," + this.block;
   }
}
