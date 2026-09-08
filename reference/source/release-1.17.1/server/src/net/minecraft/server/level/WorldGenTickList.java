package net.minecraft.server.level;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.TickPriority;

public class WorldGenTickList<T> implements TickList<T> {
   private final Function<BlockPos, TickList<T>> index;

   public WorldGenTickList(Function<BlockPos, TickList<T>> var1) {
      this.index = â˜ƒ;
   }

   @Override
   public boolean hasScheduledTick(BlockPos var1, T var2) {
      return ((TickList)this.index.apply(â˜ƒ)).hasScheduledTick(â˜ƒ, â˜ƒ);
   }

   @Override
   public void scheduleTick(BlockPos var1, T var2, int var3, TickPriority var4) {
      ((TickList)this.index.apply(â˜ƒ)).scheduleTick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
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
