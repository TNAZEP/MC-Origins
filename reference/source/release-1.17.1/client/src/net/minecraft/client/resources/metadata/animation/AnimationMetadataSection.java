package net.minecraft.client.resources.metadata.animation;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;

public class AnimationMetadataSection {
   public static final AnimationMetadataSectionSerializer SERIALIZER = new AnimationMetadataSectionSerializer();
   public static final String SECTION_NAME = "animation";
   public static final int DEFAULT_FRAME_TIME = 1;
   public static final int UNKNOWN_SIZE = -1;
   public static final AnimationMetadataSection EMPTY = new AnimationMetadataSection(Lists.newArrayList(), -1, -1, 1, false) {
      @Override
      public Pair<Integer, Integer> getFrameSize(int var1, int var2) {
         return Pair.of(â˜ƒ, â˜ƒ);
      }
   };
   private final List<AnimationFrame> frames;
   private final int frameWidth;
   private final int frameHeight;
   private final int defaultFrameTime;
   private final boolean interpolatedFrames;

   public AnimationMetadataSection(List<AnimationFrame> var1, int var2, int var3, int var4, boolean var5) {
      this.frames = â˜ƒ;
      this.frameWidth = â˜ƒ;
      this.frameHeight = â˜ƒ;
      this.defaultFrameTime = â˜ƒ;
      this.interpolatedFrames = â˜ƒ;
   }

   private static boolean isDivisionInteger(int var0, int var1) {
      return â˜ƒ / â˜ƒ * â˜ƒ == â˜ƒ;
   }

   public Pair<Integer, Integer> getFrameSize(int var1, int var2) {
      Pair<Integer, Integer> â˜ƒ = this.calculateFrameSize(â˜ƒ, â˜ƒ);
      int â˜ƒx = â˜ƒ.getFirst();
      int â˜ƒxx = â˜ƒ.getSecond();
      if (isDivisionInteger(â˜ƒ, â˜ƒx) && isDivisionInteger(â˜ƒ, â˜ƒxx)) {
         return â˜ƒ;
      } else {
         throw new IllegalArgumentException(String.format("Image size %s,%s is not multiply of frame size %s,%s", â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx));
      }
   }

   private Pair<Integer, Integer> calculateFrameSize(int var1, int var2) {
      if (this.frameWidth != -1) {
         return this.frameHeight != -1 ? Pair.of(this.frameWidth, this.frameHeight) : Pair.of(this.frameWidth, â˜ƒ);
      } else if (this.frameHeight != -1) {
         return Pair.of(â˜ƒ, this.frameHeight);
      } else {
         int â˜ƒ = Math.min(â˜ƒ, â˜ƒ);
         return Pair.of(â˜ƒ, â˜ƒ);
      }
   }

   public int getFrameHeight(int var1) {
      return this.frameHeight == -1 ? â˜ƒ : this.frameHeight;
   }

   public int getFrameWidth(int var1) {
      return this.frameWidth == -1 ? â˜ƒ : this.frameWidth;
   }

   public int getDefaultFrameTime() {
      return this.defaultFrameTime;
   }

   public boolean isInterpolatedFrames() {
      return this.interpolatedFrames;
   }

   public void forEachFrame(AnimationMetadataSection.FrameOutput var1) {
      for(AnimationFrame â˜ƒ : this.frames) {
         â˜ƒ.accept(â˜ƒ.getIndex(), â˜ƒ.getTime(this.defaultFrameTime));
      }
   }

   @FunctionalInterface
   public interface FrameOutput {
      void accept(int var1, int var2);
   }
}
