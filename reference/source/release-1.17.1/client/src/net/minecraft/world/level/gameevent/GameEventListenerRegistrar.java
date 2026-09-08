package net.minecraft.world.level.gameevent;

import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;

public class GameEventListenerRegistrar {
   private final GameEventListener listener;
   @Nullable
   private SectionPos sectionPos;

   public GameEventListenerRegistrar(GameEventListener var1) {
      this.listener = â˜ƒ;
   }

   public void onListenerRemoved(Level var1) {
      this.ifEventDispatcherExists(â˜ƒ, this.sectionPos, var1x -> var1x.unregister(this.listener));
   }

   public void onListenerMove(Level var1) {
      Optional<BlockPos> â˜ƒ = this.listener.getListenerSource().getPosition(â˜ƒ);
      if (â˜ƒ.isPresent()) {
         long â˜ƒx = SectionPos.blockToSection(((BlockPos)â˜ƒ.get()).asLong());
         if (this.sectionPos == null || this.sectionPos.asLong() != â˜ƒx) {
            SectionPos â˜ƒxx = this.sectionPos;
            this.sectionPos = SectionPos.of(â˜ƒx);
            this.ifEventDispatcherExists(â˜ƒ, â˜ƒxx, var1x -> var1x.unregister(this.listener));
            this.ifEventDispatcherExists(â˜ƒ, this.sectionPos, var1x -> var1x.register(this.listener));
         }
      }
   }

   private void ifEventDispatcherExists(Level var1, @Nullable SectionPos var2, Consumer<GameEventDispatcher> var3) {
      if (â˜ƒ != null) {
         ChunkAccess â˜ƒ = â˜ƒ.getChunk(â˜ƒ.x(), â˜ƒ.z(), ChunkStatus.FULL, false);
         if (â˜ƒ != null) {
            â˜ƒ.accept(â˜ƒ.getEventDispatcher(â˜ƒ.y()));
         }
      }
   }
}
