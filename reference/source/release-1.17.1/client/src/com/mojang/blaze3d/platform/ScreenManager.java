package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;

public class ScreenManager {
   private final Long2ObjectMap<Monitor> monitors = new Long2ObjectOpenHashMap<>();
   private final MonitorCreator monitorCreator;

   public ScreenManager(MonitorCreator var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      this.monitorCreator = â˜ƒ;
      GLFW.glfwSetMonitorCallback(this::onMonitorChange);
      PointerBuffer â˜ƒ = GLFW.glfwGetMonitors();
      if (â˜ƒ != null) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.limit(); ++â˜ƒx) {
            long â˜ƒxx = â˜ƒ.get(â˜ƒx);
            this.monitors.put(â˜ƒxx, â˜ƒ.createMonitor(â˜ƒxx));
         }
      }
   }

   private void onMonitorChange(long var1, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ == 262145) {
         this.monitors.put(â˜ƒ, this.monitorCreator.createMonitor(â˜ƒ));
      } else if (â˜ƒ == 262146) {
         this.monitors.remove(â˜ƒ);
      }
   }

   @Nullable
   public Monitor getMonitor(long var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return this.monitors.get(â˜ƒ);
   }

   @Nullable
   public Monitor findBestMonitor(Window var1) {
      long â˜ƒ = GLFW.glfwGetWindowMonitor(â˜ƒ.getWindow());
      if (â˜ƒ != 0L) {
         return this.getMonitor(â˜ƒ);
      } else {
         int â˜ƒ = â˜ƒ.getX();
         int â˜ƒx = â˜ƒ + â˜ƒ.getScreenWidth();
         int â˜ƒxx = â˜ƒ.getY();
         int â˜ƒxxx = â˜ƒxx + â˜ƒ.getScreenHeight();
         int â˜ƒxxxx = -1;
         Monitor â˜ƒxxxxx = null;

         for(Monitor â˜ƒxxxxxx : this.monitors.values()) {
            int â˜ƒxxxxxxx = â˜ƒxxxxxx.getX();
            int â˜ƒxxxxxxxx = â˜ƒxxxxxxx + â˜ƒxxxxxx.getCurrentMode().getWidth();
            int â˜ƒxxxxxxxxx = â˜ƒxxxxxx.getY();
            int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx + â˜ƒxxxxxx.getCurrentMode().getHeight();
            int â˜ƒxxxxxxxxxxx = clamp(â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
            int â˜ƒxxxxxxxxxxxx = clamp(â˜ƒx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
            int â˜ƒxxxxxxxxxxxxx = clamp(â˜ƒxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
            int â˜ƒxxxxxxxxxxxxxx = clamp(â˜ƒxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
            int â˜ƒxxxxxxxxxxxxxxx = Math.max(0, â˜ƒxxxxxxxxxxxx - â˜ƒxxxxxxxxxxx);
            int â˜ƒxxxxxxxxxxxxxxxx = Math.max(0, â˜ƒxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxx);
            int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxx;
            if (â˜ƒxxxxxxxxxxxxxxxxx > â˜ƒxxxx) {
               â˜ƒxxxxx = â˜ƒxxxxxx;
               â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxxxxx;
            }
         }

         return â˜ƒxxxxx;
      }
   }

   public static int clamp(int var0, int var1, int var2) {
      if (â˜ƒ < â˜ƒ) {
         return â˜ƒ;
      } else {
         return â˜ƒ > â˜ƒ ? â˜ƒ : â˜ƒ;
      }
   }

   public void shutdown() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GLFWMonitorCallback â˜ƒ = GLFW.glfwSetMonitorCallback(null);
      if (â˜ƒ != null) {
         â˜ƒ.free();
      }
   }
}
