package net.minecraft.world.level;

import net.minecraft.core.BlockPos;

public class EmptyTickList<T> implements TickList<T> {
   private static final EmptyTickList<Object> INSTANCE = new EmptyTickList<>();

   public static <T> EmptyTickList<T> empty() {
      return INSTANCE;
   }

   @Override
   public boolean hasScheduledTick(BlockPos var1, T var2) {
      return false;
   }

   @Override
   public void scheduleTick(BlockPos var1, T var2, int var3) {
   }

   @Override
   public void scheduleTick(BlockPos var1, T var2, int var3, TickPriority var4) {
   }

   @Override
   public boolean willTickThisTick(BlockPos var1, T var2) {
      return false;
   }

   @Override
   public int size() {
      return 0;
   }
}
