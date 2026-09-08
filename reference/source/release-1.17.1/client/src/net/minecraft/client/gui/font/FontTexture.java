package net.minecraft.client.gui.font;

import com.mojang.blaze3d.font.RawGlyph;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import javax.annotation.Nullable;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class FontTexture extends AbstractTexture {
   private static final int SIZE = 256;
   private final ResourceLocation name;
   private final RenderType normalType;
   private final RenderType seeThroughType;
   private final RenderType polygonOffsetType;
   private final boolean colored;
   private final FontTexture.Node root;

   public FontTexture(ResourceLocation var1, boolean var2) {
      this.name = â˜ƒ;
      this.colored = â˜ƒ;
      this.root = new FontTexture.Node(0, 0, 256, 256);
      TextureUtil.prepareImage(â˜ƒ ? NativeImage.InternalGlFormat.RGBA : NativeImage.InternalGlFormat.RED, this.getId(), 256, 256);
      this.normalType = â˜ƒ ? RenderType.text(â˜ƒ) : RenderType.textIntensity(â˜ƒ);
      this.seeThroughType = â˜ƒ ? RenderType.textSeeThrough(â˜ƒ) : RenderType.textIntensitySeeThrough(â˜ƒ);
      this.polygonOffsetType = â˜ƒ ? RenderType.textPolygonOffset(â˜ƒ) : RenderType.textIntensityPolygonOffset(â˜ƒ);
   }

   @Override
   public void load(ResourceManager var1) {
   }

   @Override
   public void close() {
      this.releaseId();
   }

   @Nullable
   public BakedGlyph add(RawGlyph var1) {
      if (â˜ƒ.isColored() != this.colored) {
         return null;
      } else {
         FontTexture.Node â˜ƒ = this.root.insert(â˜ƒ);
         if (â˜ƒ != null) {
            this.bind();
            â˜ƒ.upload(â˜ƒ.x, â˜ƒ.y);
            float â˜ƒx = 256.0F;
            float â˜ƒxx = 256.0F;
            float â˜ƒxxx = 0.01F;
            return new BakedGlyph(
               this.normalType,
               this.seeThroughType,
               this.polygonOffsetType,
               ((float)â˜ƒ.x + 0.01F) / 256.0F,
               ((float)â˜ƒ.x - 0.01F + (float)â˜ƒ.getPixelWidth()) / 256.0F,
               ((float)â˜ƒ.y + 0.01F) / 256.0F,
               ((float)â˜ƒ.y - 0.01F + (float)â˜ƒ.getPixelHeight()) / 256.0F,
               â˜ƒ.getLeft(),
               â˜ƒ.getRight(),
               â˜ƒ.getUp(),
               â˜ƒ.getDown()
            );
         } else {
            return null;
         }
      }
   }

   public ResourceLocation getName() {
      return this.name;
   }

   static class Node {
      final int x;
      final int y;
      private final int width;
      private final int height;
      private FontTexture.Node left;
      private FontTexture.Node right;
      private boolean occupied;

      Node(int var1, int var2, int var3, int var4) {
         this.x = â˜ƒ;
         this.y = â˜ƒ;
         this.width = â˜ƒ;
         this.height = â˜ƒ;
      }

      @Nullable
      FontTexture.Node insert(RawGlyph var1) {
         if (this.left != null && this.right != null) {
            FontTexture.Node â˜ƒ = this.left.insert(â˜ƒ);
            if (â˜ƒ == null) {
               â˜ƒ = this.right.insert(â˜ƒ);
            }

            return â˜ƒ;
         } else if (this.occupied) {
            return null;
         } else {
            int â˜ƒ = â˜ƒ.getPixelWidth();
            int â˜ƒx = â˜ƒ.getPixelHeight();
            if (â˜ƒ > this.width || â˜ƒx > this.height) {
               return null;
            } else if (â˜ƒ == this.width && â˜ƒx == this.height) {
               this.occupied = true;
               return this;
            } else {
               int â˜ƒ = this.width - â˜ƒ;
               int â˜ƒx = this.height - â˜ƒx;
               if (â˜ƒ > â˜ƒx) {
                  this.left = new FontTexture.Node(this.x, this.y, â˜ƒ, this.height);
                  this.right = new FontTexture.Node(this.x + â˜ƒ + 1, this.y, this.width - â˜ƒ - 1, this.height);
               } else {
                  this.left = new FontTexture.Node(this.x, this.y, this.width, â˜ƒx);
                  this.right = new FontTexture.Node(this.x, this.y + â˜ƒx + 1, this.width, this.height - â˜ƒx - 1);
               }

               return this.left.insert(â˜ƒ);
            }
         }
      }
   }
}
