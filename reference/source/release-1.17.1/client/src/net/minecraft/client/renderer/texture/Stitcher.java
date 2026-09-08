package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.util.Mth;

public class Stitcher {
   private static final Comparator<Stitcher.Holder> HOLDER_COMPARATOR = Comparator.comparing(var0 -> -var0.height)
      .thenComparing(var0 -> -var0.width)
      .thenComparing(var0 -> var0.spriteInfo.name());
   private final int mipLevel;
   private final Set<Stitcher.Holder> texturesToBeStitched = Sets.<Stitcher.Holder>newHashSetWithExpectedSize(256);
   private final List<Stitcher.Region> storage = Lists.<Stitcher.Region>newArrayListWithCapacity(256);
   private int storageX;
   private int storageY;
   private final int maxWidth;
   private final int maxHeight;

   public Stitcher(int var1, int var2, int var3) {
      this.mipLevel = â˜ƒ;
      this.maxWidth = â˜ƒ;
      this.maxHeight = â˜ƒ;
   }

   public int getWidth() {
      return this.storageX;
   }

   public int getHeight() {
      return this.storageY;
   }

   public void registerSprite(TextureAtlasSprite.Info var1) {
      Stitcher.Holder â˜ƒ = new Stitcher.Holder(â˜ƒ, this.mipLevel);
      this.texturesToBeStitched.add(â˜ƒ);
   }

   public void stitch() {
      List<Stitcher.Holder> â˜ƒ = Lists.<Stitcher.Holder>newArrayList(this.texturesToBeStitched);
      â˜ƒ.sort(HOLDER_COMPARATOR);

      for(Stitcher.Holder â˜ƒx : â˜ƒ) {
         if (!this.addToStorage(â˜ƒx)) {
            throw new StitcherException(
               â˜ƒx.spriteInfo, (Collection<TextureAtlasSprite.Info>)â˜ƒ.stream().map(var0 -> var0.spriteInfo).collect(ImmutableList.toImmutableList())
            );
         }
      }

      this.storageX = Mth.smallestEncompassingPowerOfTwo(this.storageX);
      this.storageY = Mth.smallestEncompassingPowerOfTwo(this.storageY);
   }

   public void gatherSprites(Stitcher.SpriteLoader var1) {
      for(Stitcher.Region â˜ƒ : this.storage) {
         â˜ƒ.walk(var2 -> {
            Stitcher.Holder â˜ƒ = var2.getHolder();
            TextureAtlasSprite.Info â˜ƒx = â˜ƒ.spriteInfo;
            â˜ƒ.load(â˜ƒx, this.storageX, this.storageY, var2.getX(), var2.getY());
         });
      }
   }

   static int smallestFittingMinTexel(int var0, int var1) {
      return (â˜ƒ >> â˜ƒ) + ((â˜ƒ & (1 << â˜ƒ) - 1) == 0 ? 0 : 1) << â˜ƒ;
   }

   private boolean addToStorage(Stitcher.Holder var1) {
      for(Stitcher.Region â˜ƒ : this.storage) {
         if (â˜ƒ.add(â˜ƒ)) {
            return true;
         }
      }

      return this.expand(â˜ƒ);
   }

   private boolean expand(Stitcher.Holder var1) {
      int â˜ƒ = Mth.smallestEncompassingPowerOfTwo(this.storageX);
      int â˜ƒx = Mth.smallestEncompassingPowerOfTwo(this.storageY);
      int â˜ƒxx = Mth.smallestEncompassingPowerOfTwo(this.storageX + â˜ƒ.width);
      int â˜ƒxxx = Mth.smallestEncompassingPowerOfTwo(this.storageY + â˜ƒ.height);
      boolean â˜ƒxxxx = â˜ƒxx <= this.maxWidth;
      boolean â˜ƒxxxxx = â˜ƒxxx <= this.maxHeight;
      if (!â˜ƒxxxx && !â˜ƒxxxxx) {
         return false;
      } else {
         boolean â˜ƒx = â˜ƒxxxx && â˜ƒ != â˜ƒxx;
         boolean â˜ƒxx = â˜ƒxxxxx && â˜ƒx != â˜ƒxxx;
         boolean â˜ƒ;
         if (â˜ƒx ^ â˜ƒxx) {
            â˜ƒ = â˜ƒx;
         } else {
            â˜ƒ = â˜ƒxxxx && â˜ƒ <= â˜ƒx;
         }

         Stitcher.Region â˜ƒ;
         if (â˜ƒ) {
            if (this.storageY == 0) {
               this.storageY = â˜ƒ.height;
            }

            â˜ƒ = new Stitcher.Region(this.storageX, 0, â˜ƒ.width, this.storageY);
            this.storageX += â˜ƒ.width;
         } else {
            â˜ƒ = new Stitcher.Region(0, this.storageY, this.storageX, â˜ƒ.height);
            this.storageY += â˜ƒ.height;
         }

         â˜ƒ.add(â˜ƒ);
         this.storage.add(â˜ƒ);
         return true;
      }
   }

   static class Holder {
      public final TextureAtlasSprite.Info spriteInfo;
      public final int width;
      public final int height;

      public Holder(TextureAtlasSprite.Info var1, int var2) {
         this.spriteInfo = â˜ƒ;
         this.width = Stitcher.smallestFittingMinTexel(â˜ƒ.width(), â˜ƒ);
         this.height = Stitcher.smallestFittingMinTexel(â˜ƒ.height(), â˜ƒ);
      }

      public String toString() {
         return "Holder{width=" + this.width + ", height=" + this.height + "}";
      }
   }

   public static class Region {
      private final int originX;
      private final int originY;
      private final int width;
      private final int height;
      private List<Stitcher.Region> subSlots;
      private Stitcher.Holder holder;

      public Region(int var1, int var2, int var3, int var4) {
         this.originX = â˜ƒ;
         this.originY = â˜ƒ;
         this.width = â˜ƒ;
         this.height = â˜ƒ;
      }

      public Stitcher.Holder getHolder() {
         return this.holder;
      }

      public int getX() {
         return this.originX;
      }

      public int getY() {
         return this.originY;
      }

      public boolean add(Stitcher.Holder var1) {
         if (this.holder != null) {
            return false;
         } else {
            int â˜ƒ = â˜ƒ.width;
            int â˜ƒx = â˜ƒ.height;
            if (â˜ƒ <= this.width && â˜ƒx <= this.height) {
               if (â˜ƒ == this.width && â˜ƒx == this.height) {
                  this.holder = â˜ƒ;
                  return true;
               } else {
                  if (this.subSlots == null) {
                     this.subSlots = Lists.<Stitcher.Region>newArrayListWithCapacity(1);
                     this.subSlots.add(new Stitcher.Region(this.originX, this.originY, â˜ƒ, â˜ƒx));
                     int â˜ƒxx = this.width - â˜ƒ;
                     int â˜ƒxxx = this.height - â˜ƒx;
                     if (â˜ƒxxx > 0 && â˜ƒxx > 0) {
                        int â˜ƒxxxx = Math.max(this.height, â˜ƒxx);
                        int â˜ƒxxxxx = Math.max(this.width, â˜ƒxxx);
                        if (â˜ƒxxxx >= â˜ƒxxxxx) {
                           this.subSlots.add(new Stitcher.Region(this.originX, this.originY + â˜ƒx, â˜ƒ, â˜ƒxxx));
                           this.subSlots.add(new Stitcher.Region(this.originX + â˜ƒ, this.originY, â˜ƒxx, this.height));
                        } else {
                           this.subSlots.add(new Stitcher.Region(this.originX + â˜ƒ, this.originY, â˜ƒxx, â˜ƒx));
                           this.subSlots.add(new Stitcher.Region(this.originX, this.originY + â˜ƒx, this.width, â˜ƒxxx));
                        }
                     } else if (â˜ƒxx == 0) {
                        this.subSlots.add(new Stitcher.Region(this.originX, this.originY + â˜ƒx, â˜ƒ, â˜ƒxxx));
                     } else if (â˜ƒxxx == 0) {
                        this.subSlots.add(new Stitcher.Region(this.originX + â˜ƒ, this.originY, â˜ƒxx, â˜ƒx));
                     }
                  }

                  for(Stitcher.Region â˜ƒxx : this.subSlots) {
                     if (â˜ƒxx.add(â˜ƒ)) {
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public void walk(Consumer<Stitcher.Region> var1) {
         if (this.holder != null) {
            â˜ƒ.accept(this);
         } else if (this.subSlots != null) {
            for(Stitcher.Region â˜ƒ : this.subSlots) {
               â˜ƒ.walk(â˜ƒ);
            }
         }
      }

      public String toString() {
         return "Slot{originX="
            + this.originX
            + ", originY="
            + this.originY
            + ", width="
            + this.width
            + ", height="
            + this.height
            + ", texture="
            + this.holder
            + ", subSlots="
            + this.subSlots
            + "}";
      }
   }

   public interface SpriteLoader {
      void load(TextureAtlasSprite.Info var1, int var2, int var3, int var4, int var5);
   }
}
