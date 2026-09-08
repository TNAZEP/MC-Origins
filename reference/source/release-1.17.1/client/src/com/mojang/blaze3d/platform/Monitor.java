package com.mojang.blaze3d.platform;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

public final class Monitor {
   private final long monitor;
   private final List<VideoMode> videoModes;
   private VideoMode currentMode;
   private int x;
   private int y;

   public Monitor(long var1) {
      this.monitor = â˜ƒ;
      this.videoModes = Lists.<VideoMode>newArrayList();
      this.refreshVideoModes();
   }

   public void refreshVideoModes() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      this.videoModes.clear();
      Buffer â˜ƒ = GLFW.glfwGetVideoModes(this.monitor);

      for(int â˜ƒx = â˜ƒ.limit() - 1; â˜ƒx >= 0; --â˜ƒx) {
         â˜ƒ.position(â˜ƒx);
         VideoMode â˜ƒxx = new VideoMode(â˜ƒ);
         if (â˜ƒxx.getRedBits() >= 8 && â˜ƒxx.getGreenBits() >= 8 && â˜ƒxx.getBlueBits() >= 8) {
            this.videoModes.add(â˜ƒxx);
         }
      }

      int[] â˜ƒx = new int[1];
      int[] â˜ƒxx = new int[1];
      GLFW.glfwGetMonitorPos(this.monitor, â˜ƒx, â˜ƒxx);
      this.x = â˜ƒx[0];
      this.y = â˜ƒxx[0];
      GLFWVidMode â˜ƒxxx = GLFW.glfwGetVideoMode(this.monitor);
      this.currentMode = new VideoMode(â˜ƒxxx);
   }

   public VideoMode getPreferredVidMode(Optional<VideoMode> var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      if (â˜ƒ.isPresent()) {
         VideoMode â˜ƒ = (VideoMode)â˜ƒ.get();

         for(VideoMode â˜ƒx : this.videoModes) {
            if (â˜ƒx.equals(â˜ƒ)) {
               return â˜ƒx;
            }
         }
      }

      return this.getCurrentMode();
   }

   public int getVideoModeIndex(VideoMode var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return this.videoModes.indexOf(â˜ƒ);
   }

   public VideoMode getCurrentMode() {
      return this.currentMode;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public VideoMode getMode(int var1) {
      return (VideoMode)this.videoModes.get(â˜ƒ);
   }

   public int getModeCount() {
      return this.videoModes.size();
   }

   public long getMonitor() {
      return this.monitor;
   }

   public String toString() {
      return String.format("Monitor[%s %sx%s %s]", this.monitor, this.x, this.y, this.currentMode);
   }
}
