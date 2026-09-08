package net.minecraft.server.level.progress;

import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

public class ProcessorChunkProgressListener implements ChunkProgressListener {
   private final ChunkProgressListener delegate;
   private final ProcessorMailbox<Runnable> mailbox;

   private ProcessorChunkProgressListener(ChunkProgressListener var1, Executor var2) {
      this.delegate = â˜ƒ;
      this.mailbox = ProcessorMailbox.create(â˜ƒ, "progressListener");
   }

   public static ProcessorChunkProgressListener createStarted(ChunkProgressListener var0, Executor var1) {
      ProcessorChunkProgressListener â˜ƒ = new ProcessorChunkProgressListener(â˜ƒ, â˜ƒ);
      â˜ƒ.start();
      return â˜ƒ;
   }

   @Override
   public void updateSpawnPos(ChunkPos var1) {
      this.mailbox.tell((Runnable)() -> this.delegate.updateSpawnPos(â˜ƒ));
   }

   @Override
   public void onStatusChange(ChunkPos var1, @Nullable ChunkStatus var2) {
      this.mailbox.tell((Runnable)() -> this.delegate.onStatusChange(â˜ƒ, â˜ƒ));
   }

   @Override
   public void start() {
      this.mailbox.tell(this.delegate::start);
   }

   @Override
   public void stop() {
      this.mailbox.tell(this.delegate::stop);
   }
}
