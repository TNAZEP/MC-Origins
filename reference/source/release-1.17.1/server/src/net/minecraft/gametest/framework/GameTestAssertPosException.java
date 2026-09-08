package net.minecraft.gametest.framework;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;

public class GameTestAssertPosException extends GameTestAssertException {
   private final BlockPos absolutePos;
   private final BlockPos relativePos;
   private final long tick;

   public GameTestAssertPosException(String var1, BlockPos var2, BlockPos var3, long var4) {
      super(â˜ƒ);
      this.absolutePos = â˜ƒ;
      this.relativePos = â˜ƒ;
      this.tick = â˜ƒ;
   }

   public String getMessage() {
      String â˜ƒ = this.absolutePos.getX()
         + ","
         + this.absolutePos.getY()
         + ","
         + this.absolutePos.getZ()
         + " (relative: "
         + this.relativePos.getX()
         + ","
         + this.relativePos.getY()
         + ","
         + this.relativePos.getZ()
         + ")";
      return super.getMessage() + " at " + â˜ƒ + " (t=" + this.tick + ")";
   }

   @Nullable
   public String getMessageToShowAtBlock() {
      return super.getMessage();
   }

   @Nullable
   public BlockPos getRelativePos() {
      return this.relativePos;
   }

   @Nullable
   public BlockPos getAbsolutePos() {
      return this.absolutePos;
   }
}
