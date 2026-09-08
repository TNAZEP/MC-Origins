package com.mojang.blaze3d.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Locale;

public class BlendMode {
   private static BlendMode lastApplied;
   private final int srcColorFactor;
   private final int srcAlphaFactor;
   private final int dstColorFactor;
   private final int dstAlphaFactor;
   private final int blendFunc;
   private final boolean separateBlend;
   private final boolean opaque;

   private BlendMode(boolean var1, boolean var2, int var3, int var4, int var5, int var6, int var7) {
      this.separateBlend = â˜ƒ;
      this.srcColorFactor = â˜ƒ;
      this.dstColorFactor = â˜ƒ;
      this.srcAlphaFactor = â˜ƒ;
      this.dstAlphaFactor = â˜ƒ;
      this.opaque = â˜ƒ;
      this.blendFunc = â˜ƒ;
   }

   public BlendMode() {
      this(false, true, 1, 0, 1, 0, 32774);
   }

   public BlendMode(int var1, int var2, int var3) {
      this(false, false, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public BlendMode(int var1, int var2, int var3, int var4, int var5) {
      this(true, false, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void apply() {
      if (!this.equals(lastApplied)) {
         if (lastApplied == null || this.opaque != lastApplied.isOpaque()) {
            lastApplied = this;
            if (this.opaque) {
               RenderSystem.disableBlend();
               return;
            }

            RenderSystem.enableBlend();
         }

         RenderSystem.blendEquation(this.blendFunc);
         if (this.separateBlend) {
            RenderSystem.blendFuncSeparate(this.srcColorFactor, this.dstColorFactor, this.srcAlphaFactor, this.dstAlphaFactor);
         } else {
            RenderSystem.blendFunc(this.srcColorFactor, this.dstColorFactor);
         }
      }
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof BlendMode)) {
         return false;
      } else {
         BlendMode â˜ƒ = (BlendMode)â˜ƒ;
         if (this.blendFunc != â˜ƒ.blendFunc) {
            return false;
         } else if (this.dstAlphaFactor != â˜ƒ.dstAlphaFactor) {
            return false;
         } else if (this.dstColorFactor != â˜ƒ.dstColorFactor) {
            return false;
         } else if (this.opaque != â˜ƒ.opaque) {
            return false;
         } else if (this.separateBlend != â˜ƒ.separateBlend) {
            return false;
         } else if (this.srcAlphaFactor != â˜ƒ.srcAlphaFactor) {
            return false;
         } else {
            return this.srcColorFactor == â˜ƒ.srcColorFactor;
         }
      }
   }

   public int hashCode() {
      int â˜ƒ = this.srcColorFactor;
      â˜ƒ = 31 * â˜ƒ + this.srcAlphaFactor;
      â˜ƒ = 31 * â˜ƒ + this.dstColorFactor;
      â˜ƒ = 31 * â˜ƒ + this.dstAlphaFactor;
      â˜ƒ = 31 * â˜ƒ + this.blendFunc;
      â˜ƒ = 31 * â˜ƒ + (this.separateBlend ? 1 : 0);
      return 31 * â˜ƒ + (this.opaque ? 1 : 0);
   }

   public boolean isOpaque() {
      return this.opaque;
   }

   public static int stringToBlendFunc(String var0) {
      String â˜ƒ = â˜ƒ.trim().toLowerCase(Locale.ROOT);
      if ("add".equals(â˜ƒ)) {
         return 32774;
      } else if ("subtract".equals(â˜ƒ)) {
         return 32778;
      } else if ("reversesubtract".equals(â˜ƒ)) {
         return 32779;
      } else if ("reverse_subtract".equals(â˜ƒ)) {
         return 32779;
      } else if ("min".equals(â˜ƒ)) {
         return 32775;
      } else {
         return "max".equals(â˜ƒ) ? 32776 : 32774;
      }
   }

   public static int stringToBlendFactor(String var0) {
      String â˜ƒ = â˜ƒ.trim().toLowerCase(Locale.ROOT);
      â˜ƒ = â˜ƒ.replaceAll("_", "");
      â˜ƒ = â˜ƒ.replaceAll("one", "1");
      â˜ƒ = â˜ƒ.replaceAll("zero", "0");
      â˜ƒ = â˜ƒ.replaceAll("minus", "-");
      if ("0".equals(â˜ƒ)) {
         return 0;
      } else if ("1".equals(â˜ƒ)) {
         return 1;
      } else if ("srccolor".equals(â˜ƒ)) {
         return 768;
      } else if ("1-srccolor".equals(â˜ƒ)) {
         return 769;
      } else if ("dstcolor".equals(â˜ƒ)) {
         return 774;
      } else if ("1-dstcolor".equals(â˜ƒ)) {
         return 775;
      } else if ("srcalpha".equals(â˜ƒ)) {
         return 770;
      } else if ("1-srcalpha".equals(â˜ƒ)) {
         return 771;
      } else if ("dstalpha".equals(â˜ƒ)) {
         return 772;
      } else {
         return "1-dstalpha".equals(â˜ƒ) ? 773 : -1;
      }
   }
}
