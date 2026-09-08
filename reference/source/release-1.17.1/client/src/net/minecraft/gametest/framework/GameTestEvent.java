package net.minecraft.gametest.framework;

import javax.annotation.Nullable;

class GameTestEvent {
   @Nullable
   public final Long expectedDelay;
   public final Runnable assertion;

   private GameTestEvent(@Nullable Long var1, Runnable var2) {
      this.expectedDelay = â˜ƒ;
      this.assertion = â˜ƒ;
   }

   static GameTestEvent create(Runnable var0) {
      return new GameTestEvent(null, â˜ƒ);
   }

   static GameTestEvent create(long var0, Runnable var2) {
      return new GameTestEvent(â˜ƒ, â˜ƒ);
   }
}
