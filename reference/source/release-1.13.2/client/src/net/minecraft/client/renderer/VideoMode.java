package net.minecraft.client.renderer;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

public final class VideoMode {
   private final int field_198069_a;
   private final int field_198070_b;
   private final int field_198071_c;
   private final int field_198072_d;
   private final int field_198073_e;
   private final int field_198074_f;
   private static final Pattern field_198075_g = Pattern.compile("(\\d+)x(\\d+)(?:@(\\d+)(?::(\\d+))?)?");

   public VideoMode(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field_198069_a = ☃;
      this.field_198070_b = ☃;
      this.field_198071_c = ☃;
      this.field_198072_d = ☃;
      this.field_198073_e = ☃;
      this.field_198074_f = ☃;
   }

   public VideoMode(Buffer var1) {
      this.field_198069_a = ☃.width();
      this.field_198070_b = ☃.height();
      this.field_198071_c = ☃.redBits();
      this.field_198072_d = ☃.greenBits();
      this.field_198073_e = ☃.blueBits();
      this.field_198074_f = ☃.refreshRate();
   }

   public VideoMode(GLFWVidMode var1) {
      this.field_198069_a = ☃.width();
      this.field_198070_b = ☃.height();
      this.field_198071_c = ☃.redBits();
      this.field_198072_d = ☃.greenBits();
      this.field_198073_e = ☃.blueBits();
      this.field_198074_f = ☃.refreshRate();
   }

   public int func_198064_a() {
      return this.field_198069_a;
   }

   public int func_198065_b() {
      return this.field_198070_b;
   }

   public int func_198062_c() {
      return this.field_198071_c;
   }

   public int func_198063_d() {
      return this.field_198072_d;
   }

   public int func_198068_e() {
      return this.field_198073_e;
   }

   public int func_198067_f() {
      return this.field_198074_f;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ != null && this.getClass() == ☃.getClass()) {
         VideoMode ☃ = (VideoMode)☃;
         return this.field_198069_a == ☃.field_198069_a
            && this.field_198070_b == ☃.field_198070_b
            && this.field_198071_c == ☃.field_198071_c
            && this.field_198072_d == ☃.field_198072_d
            && this.field_198073_e == ☃.field_198073_e
            && this.field_198074_f == ☃.field_198074_f;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(
         new Object[]{this.field_198069_a, this.field_198070_b, this.field_198071_c, this.field_198072_d, this.field_198073_e, this.field_198074_f}
      );
   }

   public String toString() {
      return String.format(
         "%sx%s@%s (%sbit)", this.field_198069_a, this.field_198070_b, this.field_198074_f, this.field_198071_c + this.field_198072_d + this.field_198073_e
      );
   }

   public static Optional<VideoMode> func_198061_a(String var0) {
      try {
         Matcher ☃ = field_198075_g.matcher(☃);
         if (☃.matches()) {
            int ☃xx = Integer.parseInt(☃.group(1));
            int ☃xxx = Integer.parseInt(☃.group(2));
            String ☃xxxx = ☃.group(3);
            int ☃x;
            if (☃xxxx == null) {
               ☃x = 60;
            } else {
               ☃x = Integer.parseInt(☃xxxx);
            }

            String ☃xx = ☃.group(4);
            int ☃x;
            if (☃xx == null) {
               ☃x = 24;
            } else {
               ☃x = Integer.parseInt(☃xx);
            }

            int ☃x = ☃x / 3;
            return Optional.of(new VideoMode(☃xx, ☃xxx, ☃x, ☃x, ☃x, ☃x));
         }
      } catch (Exception var9) {
      }

      return Optional.empty();
   }

   public String func_198066_g() {
      return String.format(
         "%sx%s@%s:%s", this.field_198069_a, this.field_198070_b, this.field_198074_f, this.field_198071_c + this.field_198072_d + this.field_198073_e
      );
   }
}
