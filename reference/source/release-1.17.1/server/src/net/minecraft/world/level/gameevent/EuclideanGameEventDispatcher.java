package net.minecraft.world.level.gameevent;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EuclideanGameEventDispatcher implements GameEventDispatcher {
   private final List<GameEventListener> listeners = Lists.<GameEventListener>newArrayList();
   private final Level level;

   public EuclideanGameEventDispatcher(Level var1) {
      this.level = â˜ƒ;
   }

   @Override
   public boolean isEmpty() {
      return this.listeners.isEmpty();
   }

   @Override
   public void register(GameEventListener var1) {
      this.listeners.add(â˜ƒ);
      DebugPackets.sendGameEventListenerInfo(this.level, â˜ƒ);
   }

   @Override
   public void unregister(GameEventListener var1) {
      this.listeners.remove(â˜ƒ);
   }

   @Override
   public void post(GameEvent var1, @Nullable Entity var2, BlockPos var3) {
      boolean â˜ƒ = false;

      for(GameEventListener â˜ƒx : this.listeners) {
         if (this.postToListener(this.level, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx)) {
            â˜ƒ = true;
         }
      }

      if (â˜ƒ) {
         DebugPackets.sendGameEventInfo(this.level, â˜ƒ, â˜ƒ);
      }
   }

   private boolean postToListener(Level var1, GameEvent var2, @Nullable Entity var3, BlockPos var4, GameEventListener var5) {
      Optional<BlockPos> â˜ƒ = â˜ƒ.getListenerSource().getPosition(â˜ƒ);
      if (!â˜ƒ.isPresent()) {
         return false;
      } else {
         double â˜ƒ = ((BlockPos)â˜ƒ.get()).distSqr(â˜ƒ, false);
         int â˜ƒx = â˜ƒ.getListenerRadius() * â˜ƒ.getListenerRadius();
         return â˜ƒ <= (double)â˜ƒx && â˜ƒ.handleGameEvent(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
