package net.minecraft.client;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.renderer.VideoMode;
import net.minecraft.client.renderer.VirtualScreen;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

public final class Monitor {
   private final VirtualScreen field_197996_a;
   private final long field_197997_b;
   private final List<VideoMode> field_197998_c;
   private VideoMode field_197999_d;
   private int field_198000_e;
   private int field_198001_f;

   public Monitor(VirtualScreen var1, long var2) {
      this.field_197996_a = ☃;
      this.field_197997_b = ☃;
      this.field_197998_c = Lists.<VideoMode>newArrayList();
      this.func_197988_a();
   }

   public void func_197988_a() {
      this.field_197998_c.clear();
      Buffer ☃ = GLFW.glfwGetVideoModes(this.field_197997_b);

      for(int ☃x = 0; ☃x < ☃.limit(); ++☃x) {
         ☃.position(☃x);
         VideoMode ☃xx = new VideoMode(☃);
         if (☃xx.func_198062_c() >= 8 && ☃xx.func_198063_d() >= 8 && ☃xx.func_198068_e() >= 8) {
            this.field_197998_c.add(☃xx);
         }
      }

      int[] ☃x = new int[1];
      int[] ☃xx = new int[1];
      GLFW.glfwGetMonitorPos(this.field_197997_b, ☃x, ☃xx);
      this.field_198000_e = ☃x[0];
      this.field_198001_f = ☃xx[0];
      GLFWVidMode ☃xxx = GLFW.glfwGetVideoMode(this.field_197997_b);
      this.field_197999_d = new VideoMode(☃xxx);
   }

   VideoMode func_197992_a(Optional<VideoMode> var1) {
      if (☃.isPresent()) {
         VideoMode ☃ = (VideoMode)☃.get();

         for(VideoMode ☃x : Lists.reverse(this.field_197998_c)) {
            if (☃x.equals(☃)) {
               return ☃x;
            }
         }
      }

      return this.func_197987_b();
   }

   int func_197993_b(Optional<VideoMode> var1) {
      if (☃.isPresent()) {
         VideoMode ☃ = (VideoMode)☃.get();

         for(int ☃x = this.field_197998_c.size() - 1; ☃x >= 0; --☃x) {
            if (☃.equals(this.field_197998_c.get(☃x))) {
               return ☃x;
            }
         }
      }

      return this.field_197998_c.indexOf(this.func_197987_b());
   }

   public VideoMode func_197987_b() {
      return this.field_197999_d;
   }

   public int func_197989_c() {
      return this.field_198000_e;
   }

   public int func_197990_d() {
      return this.field_198001_f;
   }

   public VideoMode func_197991_a(int var1) {
      return (VideoMode)this.field_197998_c.get(☃);
   }

   public int func_197994_e() {
      return this.field_197998_c.size();
   }

   public long func_197995_f() {
      return this.field_197997_b;
   }

   public String toString() {
      return String.format("Monitor[%s %sx%s %s]", this.field_197997_b, this.field_198000_e, this.field_198001_f, this.field_197999_d);
   }
}
