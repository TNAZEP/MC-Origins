package com.mojang.blaze3d.platform;

import com.google.common.base.Charsets;
import java.nio.ByteBuffer;
import net.minecraft.util.StringDecomposer;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.system.MemoryUtil;

public class ClipboardManager {
   public static final int FORMAT_UNAVAILABLE = 65545;
   private final ByteBuffer clipboardScratchBuffer = BufferUtils.createByteBuffer(8192);

   public String getClipboard(long var1, GLFWErrorCallbackI var3) {
      GLFWErrorCallback â˜ƒ = GLFW.glfwSetErrorCallback(â˜ƒ);
      String â˜ƒx = GLFW.glfwGetClipboardString(â˜ƒ);
      â˜ƒx = â˜ƒx != null ? StringDecomposer.filterBrokenSurrogates(â˜ƒx) : "";
      GLFWErrorCallback â˜ƒxx = GLFW.glfwSetErrorCallback(â˜ƒ);
      if (â˜ƒxx != null) {
         â˜ƒxx.free();
      }

      return â˜ƒx;
   }

   private static void pushClipboard(long var0, ByteBuffer var2, byte[] var3) {
      â˜ƒ.clear();
      â˜ƒ.put(â˜ƒ);
      â˜ƒ.put((byte)0);
      â˜ƒ.flip();
      GLFW.glfwSetClipboardString(â˜ƒ, â˜ƒ);
   }

   public void setClipboard(long var1, String var3) {
      byte[] â˜ƒ = â˜ƒ.getBytes(Charsets.UTF_8);
      int â˜ƒx = â˜ƒ.length + 1;
      if (â˜ƒx < this.clipboardScratchBuffer.capacity()) {
         pushClipboard(â˜ƒ, this.clipboardScratchBuffer, â˜ƒ);
      } else {
         ByteBuffer â˜ƒ = MemoryUtil.memAlloc(â˜ƒx);

         try {
            pushClipboard(â˜ƒ, â˜ƒ, â˜ƒ);
         } finally {
            MemoryUtil.memFree(â˜ƒ);
         }
      }
   }
}
