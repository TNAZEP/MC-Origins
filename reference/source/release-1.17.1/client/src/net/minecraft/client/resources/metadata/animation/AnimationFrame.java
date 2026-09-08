package net.minecraft.client.resources.metadata.animation;

public class AnimationFrame {
   public static final int UNKNOWN_FRAME_TIME = -1;
   private final int index;
   private final int time;

   public AnimationFrame(int var1) {
      this(â˜ƒ, -1);
   }

   public AnimationFrame(int var1, int var2) {
      this.index = â˜ƒ;
      this.time = â˜ƒ;
   }

   public int getTime(int var1) {
      return this.time == -1 ? â˜ƒ : this.time;
   }

   public int getIndex() {
      return this.index;
   }
}
