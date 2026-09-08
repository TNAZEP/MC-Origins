package com.mojang.blaze3d.platform;

import ca.weblite.objc.NSObject;
import com.sun.jna.Pointer;
import java.util.Optional;
import org.lwjgl.glfw.GLFWNativeCocoa;

public class MacosUtil {
   private static final int NS_FULL_SCREEN_WINDOW_MASK = 16384;

   public static void toggleFullscreen(long var0) {
      getNsWindow(â˜ƒ).filter(MacosUtil::isInKioskMode).ifPresent(MacosUtil::toggleFullscreen);
   }

   private static Optional<NSObject> getNsWindow(long var0) {
      long â˜ƒ = GLFWNativeCocoa.glfwGetCocoaWindow(â˜ƒ);
      return â˜ƒ != 0L ? Optional.of(new NSObject(new Pointer(â˜ƒ))) : Optional.empty();
   }

   private static boolean isInKioskMode(NSObject var0) {
      return (â˜ƒ.sendRaw("styleMask", new Object[0]) & 16384L) == 16384L;
   }

   private static void toggleFullscreen(NSObject var0) {
      â˜ƒ.send("toggleFullScreen:", new Object[0]);
   }
}
