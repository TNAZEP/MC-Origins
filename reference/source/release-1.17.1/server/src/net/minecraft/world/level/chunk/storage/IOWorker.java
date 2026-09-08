package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.util.thread.StrictQueue;
import net.minecraft.world.level.ChunkPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IOWorker implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final AtomicBoolean shutdownRequested = new AtomicBoolean();
   private final ProcessorMailbox<StrictQueue.IntRunnable> mailbox;
   private final RegionFileStorage storage;
   private final Map<ChunkPos, IOWorker.PendingStore> pendingWrites = Maps.<ChunkPos, IOWorker.PendingStore>newLinkedHashMap();

   protected IOWorker(File var1, boolean var2, String var3) {
      this.storage = new RegionFileStorage(â˜ƒ, â˜ƒ);
      this.mailbox = new ProcessorMailbox<>(new StrictQueue.FixedPriorityQueue(IOWorker.Priority.values().length), Util.ioPool(), "IOWorker-" + â˜ƒ);
   }

   public CompletableFuture<Void> store(ChunkPos var1, @Nullable CompoundTag var2) {
      return this.submitTask(() -> {
         IOWorker.PendingStore â˜ƒ = (IOWorker.PendingStore)this.pendingWrites.computeIfAbsent(â˜ƒ, var1x -> new IOWorker.PendingStore(â˜ƒ));
         â˜ƒ.data = â˜ƒ;
         return Either.left(â˜ƒ.result);
      }).thenCompose(Function.identity());
   }

   @Nullable
   public CompoundTag load(ChunkPos var1) throws IOException {
      CompletableFuture<CompoundTag> â˜ƒ = this.loadAsync(â˜ƒ);

      try {
         return (CompoundTag)â˜ƒ.join();
      } catch (CompletionException var4) {
         if (var4.getCause() instanceof IOException) {
            throw (IOException)var4.getCause();
         } else {
            throw var4;
         }
      }
   }

   protected CompletableFuture<CompoundTag> loadAsync(ChunkPos var1) {
      return this.submitTask(() -> {
         IOWorker.PendingStore â˜ƒ = (IOWorker.PendingStore)this.pendingWrites.get(â˜ƒ);
         if (â˜ƒ != null) {
            return Either.left(â˜ƒ.data);
         } else {
            try {
               CompoundTag â˜ƒ = this.storage.read(â˜ƒ);
               return Either.left(â˜ƒ);
            } catch (Exception var4) {
               LOGGER.warn("Failed to read chunk {}", â˜ƒ, var4);
               return Either.right(var4);
            }
         }
      });
   }

   public CompletableFuture<Void> synchronize(boolean var1) {
      CompletableFuture<Void> â˜ƒ = this.submitTask(
            () -> Either.left(
                  CompletableFuture.allOf(
                     (CompletableFuture[])this.pendingWrites.values().stream().map(var0 -> var0.result).toArray(var0 -> new CompletableFuture[var0])
                  )
               )
         )
         .thenCompose(Function.identity());
      return â˜ƒ ? â˜ƒ.thenCompose(var1x -> this.submitTask(() -> {
            try {
               this.storage.flush();
               return Either.left(null);
            } catch (Exception var2xx) {
               LOGGER.warn("Failed to synchronize chunks", var2xx);
               return Either.right(var2xx);
            }
         })) : â˜ƒ.thenCompose(var1x -> this.submitTask(() -> Either.left(null)));
   }

   private <T> CompletableFuture<T> submitTask(Supplier<Either<T, Exception>> var1) {
      return this.mailbox.askEither(var2 -> new StrictQueue.IntRunnable(IOWorker.Priority.FOREGROUND.ordinal(), () -> {
            if (!this.shutdownRequested.get()) {
               var2.tell((Either)â˜ƒ.get());
            }

            this.tellStorePending();
         }));
   }

   private void storePendingChunk() {
      if (!this.pendingWrites.isEmpty()) {
         Iterator<Entry<ChunkPos, IOWorker.PendingStore>> â˜ƒ = this.pendingWrites.entrySet().iterator();
         Entry<ChunkPos, IOWorker.PendingStore> â˜ƒx = (Entry)â˜ƒ.next();
         â˜ƒ.remove();
         this.runStore((ChunkPos)â˜ƒx.getKey(), (IOWorker.PendingStore)â˜ƒx.getValue());
         this.tellStorePending();
      }
   }

   private void tellStorePending() {
      this.mailbox.tell(new StrictQueue.IntRunnable(IOWorker.Priority.BACKGROUND.ordinal(), this::storePendingChunk));
   }

   private void runStore(ChunkPos var1, IOWorker.PendingStore var2) {
      try {
         this.storage.write(â˜ƒ, â˜ƒ.data);
         â˜ƒ.result.complete(null);
      } catch (Exception var4) {
         LOGGER.error("Failed to store chunk {}", â˜ƒ, var4);
         â˜ƒ.result.completeExceptionally(var4);
      }
   }

   public void close() throws IOException {
      if (this.shutdownRequested.compareAndSet(false, true)) {
         this.mailbox.ask(var0 -> new StrictQueue.IntRunnable(IOWorker.Priority.SHUTDOWN.ordinal(), () -> var0.tell(Unit.INSTANCE))).join();
         this.mailbox.close();

         try {
            this.storage.close();
         } catch (Exception var2) {
            LOGGER.error("Failed to close storage", var2);
         }
      }
   }

   static class PendingStore {
      @Nullable
      CompoundTag data;
      final CompletableFuture<Void> result = new CompletableFuture();

      public PendingStore(@Nullable CompoundTag var1) {
         this.data = â˜ƒ;
      }
   }

   static enum Priority {
      FOREGROUND,
      BACKGROUND,
      SHUTDOWN;
   }
}
