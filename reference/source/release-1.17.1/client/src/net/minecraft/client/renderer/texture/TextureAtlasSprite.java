package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.client.renderer.SpriteCoordinateExpander;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureAtlasSprite implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final TextureAtlas atlas;
   private final ResourceLocation name;
   final int width;
   final int height;
   protected final NativeImage[] mainImage;
   @Nullable
   private final TextureAtlasSprite.AnimatedTexture animatedTexture;
   private final int x;
   private final int y;
   private final float u0;
   private final float u1;
   private final float v0;
   private final float v1;

   protected TextureAtlasSprite(TextureAtlas var1, TextureAtlasSprite.Info var2, int var3, int var4, int var5, int var6, int var7, NativeImage var8) {
      this.atlas = â˜ƒ;
      this.width = â˜ƒ.width;
      this.height = â˜ƒ.height;
      this.name = â˜ƒ.name;
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.u0 = (float)â˜ƒ / (float)â˜ƒ;
      this.u1 = (float)(â˜ƒ + this.width) / (float)â˜ƒ;
      this.v0 = (float)â˜ƒ / (float)â˜ƒ;
      this.v1 = (float)(â˜ƒ + this.height) / (float)â˜ƒ;
      this.animatedTexture = this.createTicker(â˜ƒ, â˜ƒ.getWidth(), â˜ƒ.getHeight(), â˜ƒ);

      try {
         try {
            this.mainImage = MipmapGenerator.generateMipLevels(â˜ƒ, â˜ƒ);
         } catch (Throwable var12) {
            CrashReport â˜ƒ = CrashReport.forThrowable(var12, "Generating mipmaps for frame");
            CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Frame being iterated");
            â˜ƒx.setDetail("First frame", (CrashReportDetail<String>)(() -> {
               StringBuilder â˜ƒ = new StringBuilder();
               if (â˜ƒ.length() > 0) {
                  â˜ƒ.append(", ");
               }

               â˜ƒ.append(â˜ƒ.getWidth()).append("x").append(â˜ƒ.getHeight());
               return â˜ƒ.toString();
            }));
            throw new ReportedException(â˜ƒ);
         }
      } catch (Throwable var13) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var13, "Applying mipmap");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Sprite being mipmapped");
         â˜ƒx.setDetail("Sprite name", this.name::toString);
         â˜ƒx.setDetail("Sprite size", (CrashReportDetail<String>)(() -> this.width + " x " + this.height));
         â˜ƒx.setDetail("Sprite frames", (CrashReportDetail<String>)(() -> this.getFrameCount() + " frames"));
         â˜ƒx.setDetail("Mipmap levels", â˜ƒ);
         throw new ReportedException(â˜ƒ);
      }
   }

   private int getFrameCount() {
      return this.animatedTexture != null ? this.animatedTexture.frames.size() : 1;
   }

   @Nullable
   private TextureAtlasSprite.AnimatedTexture createTicker(TextureAtlasSprite.Info var1, int var2, int var3, int var4) {
      AnimationMetadataSection â˜ƒ = â˜ƒ.metadata;
      int â˜ƒx = â˜ƒ / â˜ƒ.getFrameWidth(â˜ƒ.width);
      int â˜ƒxx = â˜ƒ / â˜ƒ.getFrameHeight(â˜ƒ.height);
      int â˜ƒxxx = â˜ƒx * â˜ƒxx;
      List<TextureAtlasSprite.FrameInfo> â˜ƒxxxx = Lists.<TextureAtlasSprite.FrameInfo>newArrayList();
      â˜ƒ.forEachFrame((var1x, var2x) -> â˜ƒ.add(new TextureAtlasSprite.FrameInfo(var1x, var2x)));
      if (â˜ƒxxxx.isEmpty()) {
         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxx; ++â˜ƒxxxxx) {
            â˜ƒxxxx.add(new TextureAtlasSprite.FrameInfo(â˜ƒxxxxx, â˜ƒ.getDefaultFrameTime()));
         }
      } else {
         int â˜ƒ = 0;
         IntSet â˜ƒx = new IntOpenHashSet();

         for(Iterator<TextureAtlasSprite.FrameInfo> â˜ƒxx = â˜ƒxxxx.iterator(); â˜ƒxx.hasNext(); ++â˜ƒ) {
            TextureAtlasSprite.FrameInfo â˜ƒxxx = (TextureAtlasSprite.FrameInfo)â˜ƒxx.next();
            boolean â˜ƒxxxx = true;
            if (â˜ƒxxx.time <= 0) {
               LOGGER.warn("Invalid frame duration on sprite {} frame {}: {}", this.name, â˜ƒ, â˜ƒxxx.time);
               â˜ƒxxxx = false;
            }

            if (â˜ƒxxx.index < 0 || â˜ƒxxx.index >= â˜ƒxxx) {
               LOGGER.warn("Invalid frame index on sprite {} frame {}: {}", this.name, â˜ƒ, â˜ƒxxx.index);
               â˜ƒxxxx = false;
            }

            if (â˜ƒxxxx) {
               â˜ƒx.add(â˜ƒxxx.index);
            } else {
               â˜ƒxx.remove();
            }
         }

         int[] â˜ƒxx = IntStream.range(0, â˜ƒxxx).filter(var1x -> !â˜ƒ.contains(var1x)).toArray();
         if (â˜ƒxx.length > 0) {
            LOGGER.warn("Unused frames in sprite {}: {}", this.name, Arrays.toString(â˜ƒxx));
         }
      }

      if (â˜ƒxxxx.size() <= 1) {
         return null;
      } else {
         TextureAtlasSprite.InterpolationData â˜ƒ = â˜ƒ.isInterpolatedFrames() ? new TextureAtlasSprite.InterpolationData(â˜ƒ, â˜ƒ) : null;
         return new TextureAtlasSprite.AnimatedTexture(ImmutableList.copyOf(â˜ƒxxxx), â˜ƒx, â˜ƒ);
      }
   }

   void upload(int var1, int var2, NativeImage[] var3) {
      for(int â˜ƒ = 0; â˜ƒ < this.mainImage.length; ++â˜ƒ) {
         â˜ƒ[â˜ƒ].upload(â˜ƒ, this.x >> â˜ƒ, this.y >> â˜ƒ, â˜ƒ >> â˜ƒ, â˜ƒ >> â˜ƒ, this.width >> â˜ƒ, this.height >> â˜ƒ, this.mainImage.length > 1, false);
      }
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public float getU0() {
      return this.u0;
   }

   public float getU1() {
      return this.u1;
   }

   public float getU(double var1) {
      float â˜ƒ = this.u1 - this.u0;
      return this.u0 + â˜ƒ * (float)â˜ƒ / 16.0F;
   }

   public float getUOffset(float var1) {
      float â˜ƒ = this.u1 - this.u0;
      return (â˜ƒ - this.u0) / â˜ƒ * 16.0F;
   }

   public float getV0() {
      return this.v0;
   }

   public float getV1() {
      return this.v1;
   }

   public float getV(double var1) {
      float â˜ƒ = this.v1 - this.v0;
      return this.v0 + â˜ƒ * (float)â˜ƒ / 16.0F;
   }

   public float getVOffset(float var1) {
      float â˜ƒ = this.v1 - this.v0;
      return (â˜ƒ - this.v0) / â˜ƒ * 16.0F;
   }

   public ResourceLocation getName() {
      return this.name;
   }

   public TextureAtlas atlas() {
      return this.atlas;
   }

   public IntStream getUniqueFrames() {
      return this.animatedTexture != null ? this.animatedTexture.getUniqueFrames() : IntStream.of(1);
   }

   public void close() {
      for(NativeImage â˜ƒ : this.mainImage) {
         if (â˜ƒ != null) {
            â˜ƒ.close();
         }
      }

      if (this.animatedTexture != null) {
         this.animatedTexture.close();
      }
   }

   public String toString() {
      return "TextureAtlasSprite{name='"
         + this.name
         + "', frameCount="
         + this.getFrameCount()
         + ", x="
         + this.x
         + ", y="
         + this.y
         + ", height="
         + this.height
         + ", width="
         + this.width
         + ", u0="
         + this.u0
         + ", u1="
         + this.u1
         + ", v0="
         + this.v0
         + ", v1="
         + this.v1
         + "}";
   }

   public boolean isTransparent(int var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ;
      int â˜ƒx = â˜ƒ;
      if (this.animatedTexture != null) {
         â˜ƒ = â˜ƒ + this.animatedTexture.getFrameX(â˜ƒ) * this.width;
         â˜ƒx = â˜ƒ + this.animatedTexture.getFrameY(â˜ƒ) * this.height;
      }

      return (this.mainImage[0].getPixelRGBA(â˜ƒ, â˜ƒx) >> 24 & 0xFF) == 0;
   }

   public void uploadFirstFrame() {
      if (this.animatedTexture != null) {
         this.animatedTexture.uploadFirstFrame();
      } else {
         this.upload(0, 0, this.mainImage);
      }
   }

   private float atlasSize() {
      float â˜ƒ = (float)this.width / (this.u1 - this.u0);
      float â˜ƒx = (float)this.height / (this.v1 - this.v0);
      return Math.max(â˜ƒx, â˜ƒ);
   }

   public float uvShrinkRatio() {
      return 4.0F / this.atlasSize();
   }

   @Nullable
   public Tickable getAnimationTicker() {
      return this.animatedTexture;
   }

   public VertexConsumer wrap(VertexConsumer var1) {
      return new SpriteCoordinateExpander(â˜ƒ, this);
   }

   class AnimatedTexture implements Tickable, AutoCloseable {
      int frame;
      int subFrame;
      final List<TextureAtlasSprite.FrameInfo> frames;
      private final int frameRowSize;
      @Nullable
      private final TextureAtlasSprite.InterpolationData interpolationData;

      AnimatedTexture(List<TextureAtlasSprite.FrameInfo> var2, int var3, @Nullable TextureAtlasSprite.InterpolationData var4) {
         this.frames = â˜ƒ;
         this.frameRowSize = â˜ƒ;
         this.interpolationData = â˜ƒ;
      }

      int getFrameX(int var1) {
         return â˜ƒ % this.frameRowSize;
      }

      int getFrameY(int var1) {
         return â˜ƒ / this.frameRowSize;
      }

      private void uploadFrame(int var1) {
         int â˜ƒ = this.getFrameX(â˜ƒ) * TextureAtlasSprite.this.width;
         int â˜ƒx = this.getFrameY(â˜ƒ) * TextureAtlasSprite.this.height;
         TextureAtlasSprite.this.upload(â˜ƒ, â˜ƒx, TextureAtlasSprite.this.mainImage);
      }

      public void close() {
         if (this.interpolationData != null) {
            this.interpolationData.close();
         }
      }

      @Override
      public void tick() {
         ++this.subFrame;
         TextureAtlasSprite.FrameInfo â˜ƒ = (TextureAtlasSprite.FrameInfo)this.frames.get(this.frame);
         if (this.subFrame >= â˜ƒ.time) {
            int â˜ƒx = â˜ƒ.index;
            this.frame = (this.frame + 1) % this.frames.size();
            this.subFrame = 0;
            int â˜ƒxx = ((TextureAtlasSprite.FrameInfo)this.frames.get(this.frame)).index;
            if (â˜ƒx != â˜ƒxx) {
               this.uploadFrame(â˜ƒxx);
            }
         } else if (this.interpolationData != null) {
            if (!RenderSystem.isOnRenderThread()) {
               RenderSystem.recordRenderCall(() -> this.interpolationData.uploadInterpolatedFrame(this));
            } else {
               this.interpolationData.uploadInterpolatedFrame(this);
            }
         }
      }

      public void uploadFirstFrame() {
         this.uploadFrame(((TextureAtlasSprite.FrameInfo)this.frames.get(0)).index);
      }

      public IntStream getUniqueFrames() {
         return this.frames.stream().mapToInt(var0 -> var0.index).distinct();
      }
   }

   static class FrameInfo {
      final int index;
      final int time;

      FrameInfo(int var1, int var2) {
         this.index = â˜ƒ;
         this.time = â˜ƒ;
      }
   }

   public static final class Info {
      final ResourceLocation name;
      final int width;
      final int height;
      final AnimationMetadataSection metadata;

      public Info(ResourceLocation var1, int var2, int var3, AnimationMetadataSection var4) {
         this.name = â˜ƒ;
         this.width = â˜ƒ;
         this.height = â˜ƒ;
         this.metadata = â˜ƒ;
      }

      public ResourceLocation name() {
         return this.name;
      }

      public int width() {
         return this.width;
      }

      public int height() {
         return this.height;
      }
   }

   final class InterpolationData implements AutoCloseable {
      private final NativeImage[] activeFrame;

      InterpolationData(TextureAtlasSprite.Info var2, int var3) {
         this.activeFrame = new NativeImage[â˜ƒ + 1];

         for(int â˜ƒ = 0; â˜ƒ < this.activeFrame.length; ++â˜ƒ) {
            int â˜ƒx = â˜ƒ.width >> â˜ƒ;
            int â˜ƒxx = â˜ƒ.height >> â˜ƒ;
            if (this.activeFrame[â˜ƒ] == null) {
               this.activeFrame[â˜ƒ] = new NativeImage(â˜ƒx, â˜ƒxx, false);
            }
         }
      }

      void uploadInterpolatedFrame(TextureAtlasSprite.AnimatedTexture var1) {
         TextureAtlasSprite.FrameInfo â˜ƒ = (TextureAtlasSprite.FrameInfo)â˜ƒ.frames.get(â˜ƒ.frame);
         double â˜ƒx = 1.0 - (double)â˜ƒ.subFrame / (double)â˜ƒ.time;
         int â˜ƒxx = â˜ƒ.index;
         int â˜ƒxxx = ((TextureAtlasSprite.FrameInfo)â˜ƒ.frames.get((â˜ƒ.frame + 1) % â˜ƒ.frames.size())).index;
         if (â˜ƒxx != â˜ƒxxx) {
            for(int â˜ƒxxxx = 0; â˜ƒxxxx < this.activeFrame.length; ++â˜ƒxxxx) {
               int â˜ƒxxxxx = TextureAtlasSprite.this.width >> â˜ƒxxxx;
               int â˜ƒxxxxxx = TextureAtlasSprite.this.height >> â˜ƒxxxx;

               for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxxxx; ++â˜ƒxxxxxxx) {
                  for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxxxx) {
                     int â˜ƒxxxxxxxxx = this.getPixel(â˜ƒ, â˜ƒxx, â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx);
                     int â˜ƒxxxxxxxxxx = this.getPixel(â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx);
                     int â˜ƒxxxxxxxxxxx = this.mix(â˜ƒx, â˜ƒxxxxxxxxx >> 16 & 0xFF, â˜ƒxxxxxxxxxx >> 16 & 0xFF);
                     int â˜ƒxxxxxxxxxxxx = this.mix(â˜ƒx, â˜ƒxxxxxxxxx >> 8 & 0xFF, â˜ƒxxxxxxxxxx >> 8 & 0xFF);
                     int â˜ƒxxxxxxxxxxxxx = this.mix(â˜ƒx, â˜ƒxxxxxxxxx & 0xFF, â˜ƒxxxxxxxxxx & 0xFF);
                     this.activeFrame[â˜ƒxxxx]
                        .setPixelRGBA(â˜ƒxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxx & 0xFF000000 | â˜ƒxxxxxxxxxxx << 16 | â˜ƒxxxxxxxxxxxx << 8 | â˜ƒxxxxxxxxxxxxx);
                  }
               }
            }

            TextureAtlasSprite.this.upload(0, 0, this.activeFrame);
         }
      }

      private int getPixel(TextureAtlasSprite.AnimatedTexture var1, int var2, int var3, int var4, int var5) {
         return TextureAtlasSprite.this.mainImage[â˜ƒ]
            .getPixelRGBA(â˜ƒ + (â˜ƒ.getFrameX(â˜ƒ) * TextureAtlasSprite.this.width >> â˜ƒ), â˜ƒ + (â˜ƒ.getFrameY(â˜ƒ) * TextureAtlasSprite.this.height >> â˜ƒ));
      }

      private int mix(double var1, int var3, int var4) {
         return (int)(â˜ƒ * (double)â˜ƒ + (1.0 - â˜ƒ) * (double)â˜ƒ);
      }

      public void close() {
         for(NativeImage â˜ƒ : this.activeFrame) {
            if (â˜ƒ != null) {
               â˜ƒ.close();
            }
         }
      }
   }
}
