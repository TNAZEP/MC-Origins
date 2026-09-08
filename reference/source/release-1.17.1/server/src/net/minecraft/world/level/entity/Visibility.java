package net.minecraft.world.level.entity;

import net.minecraft.server.level.ChunkHolder;

public enum Visibility {
   HIDDEN(false, false),
   TRACKED(true, false),
   TICKING(true, true);

   private final boolean accessible;
   private final boolean ticking;

   private Visibility(boolean var3, boolean var4) {
      this.accessible = â˜ƒ;
      this.ticking = â˜ƒ;
   }

   public boolean isTicking() {
      return this.ticking;
   }

   public boolean isAccessible() {
      return this.accessible;
   }

   public static Visibility fromFullChunkStatus(ChunkHolder.FullChunkStatus var0) {
      if (â˜ƒ.isOrAfter(ChunkHolder.FullChunkStatus.ENTITY_TICKING)) {
         return TICKING;
      } else {
         return â˜ƒ.isOrAfter(ChunkHolder.FullChunkStatus.BORDER) ? TRACKED : HIDDEN;
      }
   }
}
