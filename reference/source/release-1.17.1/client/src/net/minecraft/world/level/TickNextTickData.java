package net.minecraft.world.level;

import java.util.Comparator;
import net.minecraft.core.BlockPos;

public class TickNextTickData<T> {
   private static long counter;
   private final T type;
   public final BlockPos pos;
   public final long triggerTick;
   public final TickPriority priority;
   private final long c;

   public TickNextTickData(BlockPos var1, T var2) {
      this(â˜ƒ, â˜ƒ, 0L, TickPriority.NORMAL);
   }

   public TickNextTickData(BlockPos var1, T var2, long var3, TickPriority var5) {
      this.c = (long)(counter++);
      this.pos = â˜ƒ.immutable();
      this.type = â˜ƒ;
      this.triggerTick = â˜ƒ;
      this.priority = â˜ƒ;
   }

   public boolean equals(Object var1) {
      if (!(â˜ƒ instanceof TickNextTickData)) {
         return false;
      } else {
         TickNextTickData<?> â˜ƒ = (TickNextTickData)â˜ƒ;
         return this.pos.equals(â˜ƒ.pos) && this.type == â˜ƒ.type;
      }
   }

   public int hashCode() {
      return this.pos.hashCode();
   }

   public static <T> Comparator<TickNextTickData<T>> createTimeComparator() {
      return Comparator.comparingLong(var0 -> var0.triggerTick).thenComparing(var0 -> var0.priority).thenComparingLong(var0 -> var0.c);
   }

   public String toString() {
      return this.type + ": " + this.pos + ", " + this.triggerTick + ", " + this.priority + ", " + this.c;
   }

   public T getType() {
      return this.type;
   }
}
