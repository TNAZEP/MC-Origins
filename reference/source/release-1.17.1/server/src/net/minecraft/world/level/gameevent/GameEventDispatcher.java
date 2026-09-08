package net.minecraft.world.level.gameevent;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

public interface GameEventDispatcher {
   GameEventDispatcher NOOP = new GameEventDispatcher() {
      @Override
      public boolean isEmpty() {
         return true;
      }

      @Override
      public void register(GameEventListener var1) {
      }

      @Override
      public void unregister(GameEventListener var1) {
      }

      @Override
      public void post(GameEvent var1, @Nullable Entity var2, BlockPos var3) {
      }
   };

   boolean isEmpty();

   void register(GameEventListener var1);

   void unregister(GameEventListener var1);

   void post(GameEvent var1, @Nullable Entity var2, BlockPos var3);
}
