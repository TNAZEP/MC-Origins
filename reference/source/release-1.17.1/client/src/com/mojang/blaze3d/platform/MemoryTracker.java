package com.mojang.blaze3d.platform;

import java.nio.ByteBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.MemoryUtil.MemoryAllocator;

public class MemoryTracker {
   private static final MemoryAllocator ALLOCATOR = MemoryUtil.getAllocator(false);

   public static ByteBuffer create(int var0) {
      long â˜ƒ = ALLOCATOR.malloc((long)â˜ƒ);
      if (â˜ƒ == 0L) {
         throw new OutOfMemoryError("Failed to allocate " + â˜ƒ + " bytes");
      } else {
         return MemoryUtil.memByteBuffer(â˜ƒ, â˜ƒ);
      }
   }

   public static ByteBuffer resize(ByteBuffer var0, int var1) {
      long â˜ƒ = ALLOCATOR.realloc(MemoryUtil.memAddress0(â˜ƒ), (long)â˜ƒ);
      if (â˜ƒ == 0L) {
         throw new OutOfMemoryError("Failed to resize buffer from " + â˜ƒ.capacity() + " bytes to " + â˜ƒ + " bytes");
      } else {
         return MemoryUtil.memByteBuffer(â˜ƒ, â˜ƒ);
      }
   }
}
