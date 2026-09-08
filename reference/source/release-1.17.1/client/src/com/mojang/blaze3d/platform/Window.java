package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.SilentInitException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWImage.Buffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public final class Window implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final GLFWErrorCallback defaultErrorCallback = GLFWErrorCallback.create(this::defaultErrorCallback);
   private final WindowEventHandler eventHandler;
   private final ScreenManager screenManager;
   private final long window;
   private int windowedX;
   private int windowedY;
   private int windowedWidth;
   private int windowedHeight;
   private Optional<VideoMode> preferredFullscreenVideoMode;
   private boolean fullscreen;
   private boolean actuallyFullscreen;
   private int x;
   private int y;
   private int width;
   private int height;
   private int framebufferWidth;
   private int framebufferHeight;
   private int guiScaledWidth;
   private int guiScaledHeight;
   private double guiScale;
   private String errorSection = "";
   private boolean dirty;
   private int framerateLimit;
   private boolean vsync;

   public Window(WindowEventHandler var1, ScreenManager var2, DisplayData var3, @Nullable String var4, String var5) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      this.screenManager = â˜ƒ;
      this.setBootErrorCallback();
      this.setErrorSection("Pre startup");
      this.eventHandler = â˜ƒ;
      Optional<VideoMode> â˜ƒ = VideoMode.read(â˜ƒ);
      if (â˜ƒ.isPresent()) {
         this.preferredFullscreenVideoMode = â˜ƒ;
      } else if (â˜ƒ.fullscreenWidth.isPresent() && â˜ƒ.fullscreenHeight.isPresent()) {
         this.preferredFullscreenVideoMode = Optional.of(new VideoMode(â˜ƒ.fullscreenWidth.getAsInt(), â˜ƒ.fullscreenHeight.getAsInt(), 8, 8, 8, 60));
      } else {
         this.preferredFullscreenVideoMode = Optional.empty();
      }

      this.actuallyFullscreen = this.fullscreen = â˜ƒ.isFullscreen;
      Monitor â˜ƒ = â˜ƒ.getMonitor(GLFW.glfwGetPrimaryMonitor());
      this.windowedWidth = this.width = â˜ƒ.width > 0 ? â˜ƒ.width : 1;
      this.windowedHeight = this.height = â˜ƒ.height > 0 ? â˜ƒ.height : 1;
      GLFW.glfwDefaultWindowHints();
      GLFW.glfwWindowHint(139265, 196609);
      GLFW.glfwWindowHint(139275, 221185);
      GLFW.glfwWindowHint(139266, 3);
      GLFW.glfwWindowHint(139267, 2);
      GLFW.glfwWindowHint(139272, 204801);
      GLFW.glfwWindowHint(139270, 1);
      this.window = GLFW.glfwCreateWindow(this.width, this.height, â˜ƒ, this.fullscreen && â˜ƒ != null ? â˜ƒ.getMonitor() : 0L, 0L);
      if (â˜ƒ != null) {
         VideoMode â˜ƒx = â˜ƒ.getPreferredVidMode(this.fullscreen ? this.preferredFullscreenVideoMode : Optional.empty());
         this.windowedX = this.x = â˜ƒ.getX() + â˜ƒx.getWidth() / 2 - this.width / 2;
         this.windowedY = this.y = â˜ƒ.getY() + â˜ƒx.getHeight() / 2 - this.height / 2;
      } else {
         int[] â˜ƒ = new int[1];
         int[] â˜ƒx = new int[1];
         GLFW.glfwGetWindowPos(this.window, â˜ƒ, â˜ƒx);
         this.windowedX = this.x = â˜ƒ[0];
         this.windowedY = this.y = â˜ƒx[0];
      }

      GLFW.glfwMakeContextCurrent(this.window);
      GL.createCapabilities();
      this.setMode();
      this.refreshFramebufferSize();
      GLFW.glfwSetFramebufferSizeCallback(this.window, this::onFramebufferResize);
      GLFW.glfwSetWindowPosCallback(this.window, this::onMove);
      GLFW.glfwSetWindowSizeCallback(this.window, this::onResize);
      GLFW.glfwSetWindowFocusCallback(this.window, this::onFocus);
      GLFW.glfwSetCursorEnterCallback(this.window, this::onEnter);
   }

   public int getRefreshRate() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GLX._getRefreshRate(this);
   }

   public boolean shouldClose() {
      return GLX._shouldClose(this);
   }

   public static void checkGlfwError(BiConsumer<Integer, String> var0) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);

      try (MemoryStack â˜ƒ = MemoryStack.stackPush()) {
         PointerBuffer â˜ƒx = â˜ƒ.mallocPointer(1);
         int â˜ƒxx = GLFW.glfwGetError(â˜ƒx);
         if (â˜ƒxx != 0) {
            long â˜ƒxxx = â˜ƒx.get();
            String â˜ƒxxxx = â˜ƒxxx == 0L ? "" : MemoryUtil.memUTF8(â˜ƒxxx);
            â˜ƒ.accept(â˜ƒxx, â˜ƒxxxx);
         }
      }
   }

   public void setIcon(InputStream var1, InputStream var2) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);

      try (MemoryStack â˜ƒ = MemoryStack.stackPush()) {
         if (â˜ƒ == null) {
            throw new FileNotFoundException("icons/icon_16x16.png");
         }

         if (â˜ƒ == null) {
            throw new FileNotFoundException("icons/icon_32x32.png");
         }

         IntBuffer â˜ƒx = â˜ƒ.mallocInt(1);
         IntBuffer â˜ƒxx = â˜ƒ.mallocInt(1);
         IntBuffer â˜ƒxxx = â˜ƒ.mallocInt(1);
         Buffer â˜ƒxxxx = GLFWImage.mallocStack(2, â˜ƒ);
         ByteBuffer â˜ƒxxxxx = this.readIconPixels(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
         if (â˜ƒxxxxx == null) {
            throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
         }

         â˜ƒxxxx.position(0);
         â˜ƒxxxx.width(â˜ƒx.get(0));
         â˜ƒxxxx.height(â˜ƒxx.get(0));
         â˜ƒxxxx.pixels(â˜ƒxxxxx);
         ByteBuffer â˜ƒx = this.readIconPixels(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
         if (â˜ƒx == null) {
            throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
         }

         â˜ƒxxxx.position(1);
         â˜ƒxxxx.width(â˜ƒx.get(0));
         â˜ƒxxxx.height(â˜ƒxx.get(0));
         â˜ƒxxxx.pixels(â˜ƒx);
         â˜ƒxxxx.position(0);
         GLFW.glfwSetWindowIcon(this.window, â˜ƒxxxx);
         STBImage.stbi_image_free(â˜ƒxxxxx);
         STBImage.stbi_image_free(â˜ƒx);
      } catch (IOException var12) {
         LOGGER.error("Couldn't set icon", var12);
      }
   }

   @Nullable
   private ByteBuffer readIconPixels(InputStream var1, IntBuffer var2, IntBuffer var3, IntBuffer var4) throws IOException {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      ByteBuffer â˜ƒ = null;

      ByteBuffer var6;
      try {
         â˜ƒ = TextureUtil.readResource(â˜ƒ);
         â˜ƒ.rewind();
         var6 = STBImage.stbi_load_from_memory(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0);
      } finally {
         if (â˜ƒ != null) {
            MemoryUtil.memFree(â˜ƒ);
         }
      }

      return var6;
   }

   public void setErrorSection(String var1) {
      this.errorSection = â˜ƒ;
   }

   private void setBootErrorCallback() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      GLFW.glfwSetErrorCallback(Window::bootCrash);
   }

   private static void bootCrash(int var0, long var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      String â˜ƒ = "GLFW error " + â˜ƒ + ": " + MemoryUtil.memUTF8(â˜ƒ);
      TinyFileDialogs.tinyfd_messageBox(
         "Minecraft", â˜ƒ + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).", "ok", "error", false
      );
      throw new Window.WindowInitFailed(â˜ƒ);
   }

   public void defaultErrorCallback(int var1, long var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      String â˜ƒ = MemoryUtil.memUTF8(â˜ƒ);
      LOGGER.error("########## GL ERROR ##########");
      LOGGER.error("@ {}", this.errorSection);
      LOGGER.error("{}: {}", â˜ƒ, â˜ƒ);
   }

   public void setDefaultErrorCallback() {
      GLFWErrorCallback â˜ƒ = GLFW.glfwSetErrorCallback(this.defaultErrorCallback);
      if (â˜ƒ != null) {
         â˜ƒ.free();
      }
   }

   public void updateVsync(boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.vsync = â˜ƒ;
      GLFW.glfwSwapInterval(â˜ƒ ? 1 : 0);
   }

   public void close() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      Callbacks.glfwFreeCallbacks(this.window);
      this.defaultErrorCallback.close();
      GLFW.glfwDestroyWindow(this.window);
      GLFW.glfwTerminate();
   }

   private void onMove(long var1, int var3, int var4) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
   }

   private void onFramebufferResize(long var1, int var3, int var4) {
      if (â˜ƒ == this.window) {
         int â˜ƒ = this.getWidth();
         int â˜ƒx = this.getHeight();
         if (â˜ƒ != 0 && â˜ƒ != 0) {
            this.framebufferWidth = â˜ƒ;
            this.framebufferHeight = â˜ƒ;
            if (this.getWidth() != â˜ƒ || this.getHeight() != â˜ƒx) {
               this.eventHandler.resizeDisplay();
            }
         }
      }
   }

   private void refreshFramebufferSize() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      int[] â˜ƒ = new int[1];
      int[] â˜ƒx = new int[1];
      GLFW.glfwGetFramebufferSize(this.window, â˜ƒ, â˜ƒx);
      this.framebufferWidth = â˜ƒ[0] > 0 ? â˜ƒ[0] : 1;
      this.framebufferHeight = â˜ƒx[0] > 0 ? â˜ƒx[0] : 1;
   }

   private void onResize(long var1, int var3, int var4) {
      this.width = â˜ƒ;
      this.height = â˜ƒ;
   }

   private void onFocus(long var1, boolean var3) {
      if (â˜ƒ == this.window) {
         this.eventHandler.setWindowActive(â˜ƒ);
      }
   }

   private void onEnter(long var1, boolean var3) {
      if (â˜ƒ) {
         this.eventHandler.cursorEntered();
      }
   }

   public void setFramerateLimit(int var1) {
      this.framerateLimit = â˜ƒ;
   }

   public int getFramerateLimit() {
      return this.framerateLimit;
   }

   public void updateDisplay() {
      RenderSystem.flipFrame(this.window);
      if (this.fullscreen != this.actuallyFullscreen) {
         this.actuallyFullscreen = this.fullscreen;
         this.updateFullscreen(this.vsync);
      }
   }

   public Optional<VideoMode> getPreferredFullscreenVideoMode() {
      return this.preferredFullscreenVideoMode;
   }

   public void setPreferredFullscreenVideoMode(Optional<VideoMode> var1) {
      boolean â˜ƒ = !â˜ƒ.equals(this.preferredFullscreenVideoMode);
      this.preferredFullscreenVideoMode = â˜ƒ;
      if (â˜ƒ) {
         this.dirty = true;
      }
   }

   public void changeFullscreenVideoMode() {
      if (this.fullscreen && this.dirty) {
         this.dirty = false;
         this.setMode();
         this.eventHandler.resizeDisplay();
      }
   }

   private void setMode() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      boolean â˜ƒ = GLFW.glfwGetWindowMonitor(this.window) != 0L;
      if (this.fullscreen) {
         Monitor â˜ƒx = this.screenManager.findBestMonitor(this);
         if (â˜ƒx == null) {
            LOGGER.warn("Failed to find suitable monitor for fullscreen mode");
            this.fullscreen = false;
         } else {
            if (Minecraft.ON_OSX) {
               MacosUtil.toggleFullscreen(this.window);
            }

            VideoMode â˜ƒx = â˜ƒx.getPreferredVidMode(this.preferredFullscreenVideoMode);
            if (!â˜ƒ) {
               this.windowedX = this.x;
               this.windowedY = this.y;
               this.windowedWidth = this.width;
               this.windowedHeight = this.height;
            }

            this.x = 0;
            this.y = 0;
            this.width = â˜ƒx.getWidth();
            this.height = â˜ƒx.getHeight();
            GLFW.glfwSetWindowMonitor(this.window, â˜ƒx.getMonitor(), this.x, this.y, this.width, this.height, â˜ƒx.getRefreshRate());
         }
      } else {
         this.x = this.windowedX;
         this.y = this.windowedY;
         this.width = this.windowedWidth;
         this.height = this.windowedHeight;
         GLFW.glfwSetWindowMonitor(this.window, 0L, this.x, this.y, this.width, this.height, -1);
      }
   }

   public void toggleFullScreen() {
      this.fullscreen = !this.fullscreen;
   }

   public void setWindowed(int var1, int var2) {
      this.windowedWidth = â˜ƒ;
      this.windowedHeight = â˜ƒ;
      this.fullscreen = false;
      this.setMode();
   }

   private void updateFullscreen(boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);

      try {
         this.setMode();
         this.eventHandler.resizeDisplay();
         this.updateVsync(â˜ƒ);
         this.updateDisplay();
      } catch (Exception var3) {
         LOGGER.error("Couldn't toggle fullscreen", var3);
      }
   }

   public int calculateScale(int var1, boolean var2) {
      int â˜ƒ = 1;

      while(
         â˜ƒ != â˜ƒ
            && â˜ƒ < this.framebufferWidth
            && â˜ƒ < this.framebufferHeight
            && this.framebufferWidth / (â˜ƒ + 1) >= 320
            && this.framebufferHeight / (â˜ƒ + 1) >= 240
      ) {
         ++â˜ƒ;
      }

      if (â˜ƒ && â˜ƒ % 2 != 0) {
         ++â˜ƒ;
      }

      return â˜ƒ;
   }

   public void setGuiScale(double var1) {
      this.guiScale = â˜ƒ;
      int â˜ƒ = (int)((double)this.framebufferWidth / â˜ƒ);
      this.guiScaledWidth = (double)this.framebufferWidth / â˜ƒ > (double)â˜ƒ ? â˜ƒ + 1 : â˜ƒ;
      int â˜ƒx = (int)((double)this.framebufferHeight / â˜ƒ);
      this.guiScaledHeight = (double)this.framebufferHeight / â˜ƒ > (double)â˜ƒx ? â˜ƒx + 1 : â˜ƒx;
   }

   public void setTitle(String var1) {
      GLFW.glfwSetWindowTitle(this.window, â˜ƒ);
   }

   public long getWindow() {
      return this.window;
   }

   public boolean isFullscreen() {
      return this.fullscreen;
   }

   public int getWidth() {
      return this.framebufferWidth;
   }

   public int getHeight() {
      return this.framebufferHeight;
   }

   public void setWidth(int var1) {
      this.framebufferWidth = â˜ƒ;
   }

   public void setHeight(int var1) {
      this.framebufferHeight = â˜ƒ;
   }

   public int getScreenWidth() {
      return this.width;
   }

   public int getScreenHeight() {
      return this.height;
   }

   public int getGuiScaledWidth() {
      return this.guiScaledWidth;
   }

   public int getGuiScaledHeight() {
      return this.guiScaledHeight;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public double getGuiScale() {
      return this.guiScale;
   }

   @Nullable
   public Monitor findBestMonitor() {
      return this.screenManager.findBestMonitor(this);
   }

   public void updateRawMouseInput(boolean var1) {
      InputConstants.updateRawMouseInput(this.window, â˜ƒ);
   }

   public static class WindowInitFailed extends SilentInitException {
      WindowInitFailed(String var1) {
         super(â˜ƒ);
      }
   }
}
