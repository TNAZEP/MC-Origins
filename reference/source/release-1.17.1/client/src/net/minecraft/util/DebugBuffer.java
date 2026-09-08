package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class DebugBuffer<T> {
   private final AtomicReferenceArray<T> data;
   private final AtomicInteger index;

   public DebugBuffer(int var1) {
      this.data = new AtomicReferenceArray(â˜ƒ);
      this.index = new AtomicInteger(0);
   }

   public void push(T var1) {
      int â˜ƒ = this.data.length();

      int â˜ƒ;
      int â˜ƒ;
      do {
         â˜ƒ = this.index.get();
         â˜ƒ = (â˜ƒ + 1) % â˜ƒ;
      } while(!this.index.compareAndSet(â˜ƒ, â˜ƒ));

      this.data.set(â˜ƒ, â˜ƒ);
   }

   public List<T> dump() {
      int â˜ƒ = this.index.get();
      Builder<T> â˜ƒx = ImmutableList.builder();

      for(int â˜ƒxx = 0; â˜ƒxx < this.data.length(); ++â˜ƒxx) {
         int â˜ƒxxx = Math.floorMod(â˜ƒ - â˜ƒxx, this.data.length());
         T â˜ƒxxxx = (T)this.data.get(â˜ƒxxx);
         if (â˜ƒxxxx != null) {
            â˜ƒx.add(â˜ƒxxxx);
         }
      }

      return â˜ƒx.build();
   }
}
