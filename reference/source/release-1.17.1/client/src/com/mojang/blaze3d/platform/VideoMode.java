package com.mojang.blaze3d.platform;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

public final class VideoMode {
   private final int width;
   private final int height;
   private final int redBits;
   private final int greenBits;
   private final int blueBits;
   private final int refreshRate;
   private static final Pattern PATTERN = Pattern.compile("(\\d+)x(\\d+)(?:@(\\d+)(?::(\\d+))?)?");

   public VideoMode(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.redBits = â˜ƒ;
      this.greenBits = â˜ƒ;
      this.blueBits = â˜ƒ;
      this.refreshRate = â˜ƒ;
   }

   public VideoMode(Buffer var1) {
      this.width = â˜ƒ.width();
      this.height = â˜ƒ.height();
      this.redBits = â˜ƒ.redBits();
      this.greenBits = â˜ƒ.greenBits();
      this.blueBits = â˜ƒ.blueBits();
      this.refreshRate = â˜ƒ.refreshRate();
   }

   public VideoMode(GLFWVidMode var1) {
      this.width = â˜ƒ.width();
      this.height = â˜ƒ.height();
      this.redBits = â˜ƒ.redBits();
      this.greenBits = â˜ƒ.greenBits();
      this.blueBits = â˜ƒ.blueBits();
      this.refreshRate = â˜ƒ.refreshRate();
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int getRedBits() {
      return this.redBits;
   }

   public int getGreenBits() {
      return this.greenBits;
   }

   public int getBlueBits() {
      return this.blueBits;
   }

   public int getRefreshRate() {
      return this.refreshRate;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         VideoMode â˜ƒ = (VideoMode)â˜ƒ;
         return this.width == â˜ƒ.width
            && this.height == â˜ƒ.height
            && this.redBits == â˜ƒ.redBits
            && this.greenBits == â˜ƒ.greenBits
            && this.blueBits == â˜ƒ.blueBits
            && this.refreshRate == â˜ƒ.refreshRate;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.width, this.height, this.redBits, this.greenBits, this.blueBits, this.refreshRate});
   }

   public String toString() {
      return String.format("%sx%s@%s (%sbit)", this.width, this.height, this.refreshRate, this.redBits + this.greenBits + this.blueBits);
   }

   public static Optional<VideoMode> read(@Nullable String var0) {
      if (â˜ƒ == null) {
         return Optional.empty();
      } else {
         try {
            Matcher â˜ƒ = PATTERN.matcher(â˜ƒ);
            if (â˜ƒ.matches()) {
               int â˜ƒxx = Integer.parseInt(â˜ƒ.group(1));
               int â˜ƒxxx = Integer.parseInt(â˜ƒ.group(2));
               String â˜ƒxxxx = â˜ƒ.group(3);
               int â˜ƒx;
               if (â˜ƒxxxx == null) {
                  â˜ƒx = 60;
               } else {
                  â˜ƒx = Integer.parseInt(â˜ƒxxxx);
               }

               String â˜ƒxx = â˜ƒ.group(4);
               int â˜ƒx;
               if (â˜ƒxx == null) {
                  â˜ƒx = 24;
               } else {
                  â˜ƒx = Integer.parseInt(â˜ƒxx);
               }

               int â˜ƒx = â˜ƒx / 3;
               return Optional.of(new VideoMode(â˜ƒxx, â˜ƒxxx, â˜ƒx, â˜ƒx, â˜ƒx, â˜ƒx));
            }
         } catch (Exception var9) {
         }

         return Optional.empty();
      }
   }

   public String write() {
      return String.format("%sx%s@%s:%s", this.width, this.height, this.refreshRate, this.redBits + this.greenBits + this.blueBits);
   }
}
