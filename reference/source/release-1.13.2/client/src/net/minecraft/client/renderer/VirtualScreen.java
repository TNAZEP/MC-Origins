package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.GameSettings;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Monitor;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;

public final class VirtualScreen implements AutoCloseable {
   private final Minecraft field_198057_a;
   private final Map<Long, Monitor> field_198058_b = Maps.newHashMap();
   private final Map<Long, MainWindow> field_198059_c = Maps.newHashMap();
   private final Map<MainWindow, Monitor> field_198060_d = Maps.<MainWindow, Monitor>newHashMap();

   public VirtualScreen(Minecraft var1) {
      this.field_198057_a = ☃;
      GLFW.glfwSetMonitorCallback(this::func_198056_a);
      PointerBuffer ☃ = GLFW.glfwGetMonitors();

      for(int ☃x = 0; ☃x < ☃.limit(); ++☃x) {
         long ☃xx = ☃.get(☃x);
         this.field_198058_b.put(☃xx, new Monitor(this, ☃xx));
      }
   }

   private void func_198056_a(long var1, int var3) {
      if (☃ == 262145) {
         this.field_198058_b.put(☃, new Monitor(this, ☃));
      } else if (☃ == 262146) {
         this.field_198058_b.remove(☃);
      }
   }

   public Monitor func_198054_a(long var1) {
      return (Monitor)this.field_198058_b.get(☃);
   }

   public Monitor func_198055_a(MainWindow var1) {
      long ☃ = GLFW.glfwGetWindowMonitor(☃.func_198092_i());
      if (☃ != 0L) {
         return (Monitor)this.field_198058_b.get(☃);
      } else {
         Monitor ☃ = (Monitor)this.field_198058_b.values().iterator().next();
         int ☃x = -1;
         int ☃xx = ☃.func_198099_q();
         int ☃xxx = ☃xx + ☃.func_198105_m();
         int ☃xxxx = ☃.func_198079_r();
         int ☃xxxxx = ☃xxxx + ☃.func_198083_n();

         for(Monitor ☃xxxxxx : this.field_198058_b.values()) {
            int ☃xxxxxxx = ☃xxxxxx.func_197989_c();
            int ☃xxxxxxxx = ☃xxxxxxx + ☃xxxxxx.func_197987_b().func_198064_a();
            int ☃xxxxxxxxx = ☃xxxxxx.func_197990_d();
            int ☃xxxxxxxxxx = ☃xxxxxxxxx + ☃xxxxxx.func_197987_b().func_198065_b();
            int ☃xxxxxxxxxxx = MathHelper.func_76125_a(☃xx, ☃xxxxxxx, ☃xxxxxxxx);
            int ☃xxxxxxxxxxxx = MathHelper.func_76125_a(☃xxx, ☃xxxxxxx, ☃xxxxxxxx);
            int ☃xxxxxxxxxxxxx = MathHelper.func_76125_a(☃xxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx);
            int ☃xxxxxxxxxxxxxx = MathHelper.func_76125_a(☃xxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx);
            int ☃xxxxxxxxxxxxxxx = Math.max(0, ☃xxxxxxxxxxxx - ☃xxxxxxxxxxx);
            int ☃xxxxxxxxxxxxxxxx = Math.max(0, ☃xxxxxxxxxxxxxx - ☃xxxxxxxxxxxxx);
            int ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxx;
            if (☃xxxxxxxxxxxxxxxxx > ☃x) {
               ☃ = ☃xxxxxx;
               ☃x = ☃xxxxxxxxxxxxxxxxx;
            }
         }

         if (☃ != this.field_198060_d.get(☃)) {
            this.field_198060_d.put(☃, ☃);
            GameSettings.Options.FULLSCREEN_RESOLUTION.func_148263_a((float)☃.func_197994_e());
         }

         return ☃;
      }
   }

   public MainWindow func_198053_a(GameConfiguration.DisplayInformation var1, String var2) {
      return new MainWindow(this.field_198057_a, this, ☃, ☃);
   }

   public void close() {
      GLFWMonitorCallback ☃ = GLFW.glfwSetMonitorCallback(null);
      if (☃ != null) {
         ☃.free();
      }
   }
}
