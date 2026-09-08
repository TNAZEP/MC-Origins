package com.mojang.blaze3d.platform;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Nullable;
import org.lwjgl.system.Pointer;

public class DebugMemoryUntracker {
   @Nullable
   private static final MethodHandle UNTRACK = GLX.make(() -> {
      try {
         Lookup â˜ƒ = MethodHandles.lookup();
         Class<?> â˜ƒx = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
         Method â˜ƒxx = â˜ƒx.getDeclaredMethod("untrack", Long.TYPE);
         â˜ƒxx.setAccessible(true);
         Field â˜ƒxxx = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
         â˜ƒxxx.setAccessible(true);
         Object â˜ƒxxxx = â˜ƒxxx.get(null);
         return â˜ƒx.isInstance(â˜ƒxxxx) ? â˜ƒ.unreflect(â˜ƒxx) : null;
      } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | ClassNotFoundException var5) {
         throw new RuntimeException(var5);
      }
   });

   public static void untrack(long var0) {
      if (UNTRACK != null) {
         try {
            UNTRACK.invoke(â˜ƒ);
         } catch (Throwable var3) {
            throw new RuntimeException(var3);
         }
      }
   }

   public static void untrack(Pointer var0) {
      untrack(â˜ƒ.address());
   }
}
