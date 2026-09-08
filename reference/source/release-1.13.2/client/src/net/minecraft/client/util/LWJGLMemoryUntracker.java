package net.minecraft.client.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import org.lwjgl.system.Pointer;

public class LWJGLMemoryUntracker {
   @Nullable
   private static final MethodHandle field_197934_a = Util.func_199748_a(() -> {
      try {
         Lookup ☃ = MethodHandles.lookup();
         Class<?> ☃x = Class.forName("org.lwjgl.system.MemoryManage$DebugAllocator");
         Method ☃xx = ☃x.getDeclaredMethod("untrack", Long.TYPE);
         ☃xx.setAccessible(true);
         Field ☃xxx = Class.forName("org.lwjgl.system.MemoryUtil$LazyInit").getDeclaredField("ALLOCATOR");
         ☃xxx.setAccessible(true);
         Object ☃xxxx = ☃xxx.get(null);
         return ☃x.isInstance(☃xxxx) ? ☃.unreflect(☃xx) : null;
      } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | ClassNotFoundException var5) {
         throw new RuntimeException(var5);
      }
   });

   public static void func_197933_a(long var0) {
      if (field_197934_a != null) {
         try {
            field_197934_a.invoke(☃);
         } catch (Throwable var3) {
            throw new RuntimeException(var3);
         }
      }
   }

   public static void func_211545_a(Pointer var0) {
      func_197933_a(☃.address());
   }
}
