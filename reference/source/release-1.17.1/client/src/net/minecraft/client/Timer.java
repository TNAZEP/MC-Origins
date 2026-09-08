package net.minecraft.client;

public class Timer {
   public float partialTick;
   public float tickDelta;
   private long lastMs;
   private final float msPerTick;

   public Timer(float var1, long var2) {
      this.msPerTick = 1000.0F / â˜ƒ;
      this.lastMs = â˜ƒ;
   }

   public int advanceTime(long var1) {
      this.tickDelta = (float)(â˜ƒ - this.lastMs) / this.msPerTick;
      this.lastMs = â˜ƒ;
      this.partialTick += this.tickDelta;
      int â˜ƒ = (int)this.partialTick;
      this.partialTick -= (float)â˜ƒ;
      return â˜ƒ;
   }
}
