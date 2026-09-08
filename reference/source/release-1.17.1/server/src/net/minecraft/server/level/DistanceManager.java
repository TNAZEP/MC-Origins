package net.minecraft.server.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.util.SortedArraySet;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DistanceManager {
   static final Logger LOGGER = LogManager.getLogger();
   private static final int ENTITY_TICKING_RANGE = 2;
   static final int PLAYER_TICKET_LEVEL = 33 + ChunkStatus.getDistance(ChunkStatus.FULL) - 2;
   private static final int INITIAL_TICKET_LIST_CAPACITY = 4;
   final Long2ObjectMap<ObjectSet<ServerPlayer>> playersPerChunk = new Long2ObjectOpenHashMap<>();
   final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> tickets = new Long2ObjectOpenHashMap();
   private final DistanceManager.ChunkTicketTracker ticketTracker = new DistanceManager.ChunkTicketTracker();
   private final DistanceManager.FixedPlayerDistanceChunkTracker naturalSpawnChunkCounter = new DistanceManager.FixedPlayerDistanceChunkTracker(8);
   private final DistanceManager.PlayerTicketTracker playerTicketManager = new DistanceManager.PlayerTicketTracker(33);
   final Set<ChunkHolder> chunksToUpdateFutures = Sets.<ChunkHolder>newHashSet();
   final ChunkTaskPriorityQueueSorter ticketThrottler;
   final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> ticketThrottlerInput;
   final ProcessorHandle<ChunkTaskPriorityQueueSorter.Release> ticketThrottlerReleaser;
   final LongSet ticketsToRelease = new LongOpenHashSet();
   final Executor mainThreadExecutor;
   private long ticketTickCounter;

   protected DistanceManager(Executor var1, Executor var2) {
      ProcessorHandle<Runnable> â˜ƒ = ProcessorHandle.of("player ticket throttler", â˜ƒ::execute);
      ChunkTaskPriorityQueueSorter â˜ƒx = new ChunkTaskPriorityQueueSorter(ImmutableList.of(â˜ƒ), â˜ƒ, 4);
      this.ticketThrottler = â˜ƒx;
      this.ticketThrottlerInput = â˜ƒx.getProcessor(â˜ƒ, true);
      this.ticketThrottlerReleaser = â˜ƒx.getReleaseProcessor(â˜ƒ);
      this.mainThreadExecutor = â˜ƒ;
   }

   protected void purgeStaleTickets() {
      ++this.ticketTickCounter;
      ObjectIterator<Entry<SortedArraySet<Ticket<?>>>> â˜ƒ = this.tickets.long2ObjectEntrySet().fastIterator();

      while(â˜ƒ.hasNext()) {
         Entry<SortedArraySet<Ticket<?>>> â˜ƒx = (Entry)â˜ƒ.next();
         if (((SortedArraySet)â˜ƒx.getValue()).removeIf(var1x -> var1x.timedOut(this.ticketTickCounter))) {
            this.ticketTracker.update(â˜ƒx.getLongKey(), getTicketLevelAt((SortedArraySet<Ticket<?>>)â˜ƒx.getValue()), false);
         }

         if (((SortedArraySet)â˜ƒx.getValue()).isEmpty()) {
            â˜ƒ.remove();
         }
      }
   }

   private static int getTicketLevelAt(SortedArraySet<Ticket<?>> var0) {
      return !â˜ƒ.isEmpty() ? â˜ƒ.first().getTicketLevel() : ChunkMap.MAX_CHUNK_DISTANCE + 1;
   }

   protected abstract boolean isChunkToRemove(long var1);

   @Nullable
   protected abstract ChunkHolder getChunk(long var1);

   @Nullable
   protected abstract ChunkHolder updateChunkScheduling(long var1, int var3, @Nullable ChunkHolder var4, int var5);

   public boolean runAllUpdates(ChunkMap var1) {
      this.naturalSpawnChunkCounter.runAllUpdates();
      this.playerTicketManager.runAllUpdates();
      int â˜ƒ = Integer.MAX_VALUE - this.ticketTracker.runDistanceUpdates(Integer.MAX_VALUE);
      boolean â˜ƒx = â˜ƒ != 0;
      if (â˜ƒx) {
      }

      if (!this.chunksToUpdateFutures.isEmpty()) {
         this.chunksToUpdateFutures.forEach(var2x -> var2x.updateFutures(â˜ƒ, this.mainThreadExecutor));
         this.chunksToUpdateFutures.clear();
         return true;
      } else {
         if (!this.ticketsToRelease.isEmpty()) {
            LongIterator â˜ƒ = this.ticketsToRelease.iterator();

            while(â˜ƒ.hasNext()) {
               long â˜ƒx = â˜ƒ.nextLong();
               if (this.getTickets(â˜ƒx).stream().anyMatch(var0 -> var0.getType() == TicketType.PLAYER)) {
                  ChunkHolder â˜ƒxx = â˜ƒ.getUpdatingChunkIfPresent(â˜ƒx);
                  if (â˜ƒxx == null) {
                     throw new IllegalStateException();
                  }

                  CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> â˜ƒxx = â˜ƒxx.getEntityTickingChunkFuture();
                  â˜ƒxx.thenAccept(
                     var3x -> this.mainThreadExecutor.execute(() -> this.ticketThrottlerReleaser.tell(ChunkTaskPriorityQueueSorter.release(() -> {
                           }, â˜ƒ, false)))
                  );
               }
            }

            this.ticketsToRelease.clear();
         }

         return â˜ƒx;
      }
   }

   void addTicket(long var1, Ticket<?> var3) {
      SortedArraySet<Ticket<?>> â˜ƒ = this.getTickets(â˜ƒ);
      int â˜ƒx = getTicketLevelAt(â˜ƒ);
      Ticket<?> â˜ƒxx = â˜ƒ.addOrGet(â˜ƒ);
      â˜ƒxx.setCreatedTick(this.ticketTickCounter);
      if (â˜ƒ.getTicketLevel() < â˜ƒx) {
         this.ticketTracker.update(â˜ƒ, â˜ƒ.getTicketLevel(), true);
      }
   }

   void removeTicket(long var1, Ticket<?> var3) {
      SortedArraySet<Ticket<?>> â˜ƒ = this.getTickets(â˜ƒ);
      if (â˜ƒ.remove(â˜ƒ)) {
      }

      if (â˜ƒ.isEmpty()) {
         this.tickets.remove(â˜ƒ);
      }

      this.ticketTracker.update(â˜ƒ, getTicketLevelAt(â˜ƒ), false);
   }

   public <T> void addTicket(TicketType<T> var1, ChunkPos var2, int var3, T var4) {
      this.addTicket(â˜ƒ.toLong(), new Ticket<>(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public <T> void removeTicket(TicketType<T> var1, ChunkPos var2, int var3, T var4) {
      Ticket<T> â˜ƒ = new Ticket<>(â˜ƒ, â˜ƒ, â˜ƒ);
      this.removeTicket(â˜ƒ.toLong(), â˜ƒ);
   }

   public <T> void addRegionTicket(TicketType<T> var1, ChunkPos var2, int var3, T var4) {
      this.addTicket(â˜ƒ.toLong(), new Ticket<>(â˜ƒ, 33 - â˜ƒ, â˜ƒ));
   }

   public <T> void removeRegionTicket(TicketType<T> var1, ChunkPos var2, int var3, T var4) {
      Ticket<T> â˜ƒ = new Ticket<>(â˜ƒ, 33 - â˜ƒ, â˜ƒ);
      this.removeTicket(â˜ƒ.toLong(), â˜ƒ);
   }

   private SortedArraySet<Ticket<?>> getTickets(long var1) {
      return (SortedArraySet<Ticket<?>>)this.tickets.computeIfAbsent(â˜ƒ, var0 -> SortedArraySet.create(4));
   }

   protected void updateChunkForced(ChunkPos var1, boolean var2) {
      Ticket<ChunkPos> â˜ƒ = new Ticket<>(TicketType.FORCED, 31, â˜ƒ);
      if (â˜ƒ) {
         this.addTicket(â˜ƒ.toLong(), â˜ƒ);
      } else {
         this.removeTicket(â˜ƒ.toLong(), â˜ƒ);
      }
   }

   public void addPlayer(SectionPos var1, ServerPlayer var2) {
      long â˜ƒ = â˜ƒ.chunk().toLong();
      this.playersPerChunk.computeIfAbsent(â˜ƒ, var0 -> new ObjectOpenHashSet()).add(â˜ƒ);
      this.naturalSpawnChunkCounter.update(â˜ƒ, 0, true);
      this.playerTicketManager.update(â˜ƒ, 0, true);
   }

   public void removePlayer(SectionPos var1, ServerPlayer var2) {
      long â˜ƒ = â˜ƒ.chunk().toLong();
      ObjectSet<ServerPlayer> â˜ƒx = this.playersPerChunk.get(â˜ƒ);
      â˜ƒx.remove(â˜ƒ);
      if (â˜ƒx.isEmpty()) {
         this.playersPerChunk.remove(â˜ƒ);
         this.naturalSpawnChunkCounter.update(â˜ƒ, Integer.MAX_VALUE, false);
         this.playerTicketManager.update(â˜ƒ, Integer.MAX_VALUE, false);
      }
   }

   protected String getTicketDebugString(long var1) {
      SortedArraySet<Ticket<?>> â˜ƒx = (SortedArraySet)this.tickets.get(â˜ƒ);
      String â˜ƒ;
      if (â˜ƒx != null && !â˜ƒx.isEmpty()) {
         â˜ƒ = â˜ƒx.first().toString();
      } else {
         â˜ƒ = "no_ticket";
      }

      return â˜ƒ;
   }

   protected void updatePlayerTickets(int var1) {
      this.playerTicketManager.updateViewDistance(â˜ƒ);
   }

   public int getNaturalSpawnChunkCount() {
      this.naturalSpawnChunkCounter.runAllUpdates();
      return this.naturalSpawnChunkCounter.chunks.size();
   }

   public boolean hasPlayersNearby(long var1) {
      this.naturalSpawnChunkCounter.runAllUpdates();
      return this.naturalSpawnChunkCounter.chunks.containsKey(â˜ƒ);
   }

   public String getDebugStatus() {
      return this.ticketThrottler.getDebugStatus();
   }

   private void dumpTickets(String var1) {
      try {
         FileOutputStream â˜ƒ = new FileOutputStream(new File(â˜ƒ));

         try {
            for(Entry<SortedArraySet<Ticket<?>>> â˜ƒx : this.tickets.long2ObjectEntrySet()) {
               ChunkPos â˜ƒxx = new ChunkPos(â˜ƒx.getLongKey());

               for(Ticket<?> â˜ƒxxx : (SortedArraySet)â˜ƒx.getValue()) {
                  â˜ƒ.write((â˜ƒxx.x + "\t" + â˜ƒxx.z + "\t" + â˜ƒxxx.getType() + "\t" + â˜ƒxxx.getTicketLevel() + "\t\n").getBytes(StandardCharsets.UTF_8));
               }
            }
         } catch (Throwable var9) {
            try {
               â˜ƒ.close();
            } catch (Throwable var8) {
               var9.addSuppressed(var8);
            }

            throw var9;
         }

         â˜ƒ.close();
      } catch (IOException var10) {
         LOGGER.error(var10);
      }
   }

   class ChunkTicketTracker extends ChunkTracker {
      public ChunkTicketTracker() {
         super(ChunkMap.MAX_CHUNK_DISTANCE + 2, 16, 256);
      }

      @Override
      protected int getLevelFromSource(long var1) {
         SortedArraySet<Ticket<?>> â˜ƒ = (SortedArraySet)DistanceManager.this.tickets.get(â˜ƒ);
         if (â˜ƒ == null) {
            return Integer.MAX_VALUE;
         } else {
            return â˜ƒ.isEmpty() ? Integer.MAX_VALUE : â˜ƒ.first().getTicketLevel();
         }
      }

      @Override
      protected int getLevel(long var1) {
         if (!DistanceManager.this.isChunkToRemove(â˜ƒ)) {
            ChunkHolder â˜ƒ = DistanceManager.this.getChunk(â˜ƒ);
            if (â˜ƒ != null) {
               return â˜ƒ.getTicketLevel();
            }
         }

         return ChunkMap.MAX_CHUNK_DISTANCE + 1;
      }

      @Override
      protected void setLevel(long var1, int var3) {
         ChunkHolder â˜ƒ = DistanceManager.this.getChunk(â˜ƒ);
         int â˜ƒx = â˜ƒ == null ? ChunkMap.MAX_CHUNK_DISTANCE + 1 : â˜ƒ.getTicketLevel();
         if (â˜ƒx != â˜ƒ) {
            â˜ƒ = DistanceManager.this.updateChunkScheduling(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
            if (â˜ƒ != null) {
               DistanceManager.this.chunksToUpdateFutures.add(â˜ƒ);
            }
         }
      }

      public int runDistanceUpdates(int var1) {
         return this.runUpdates(â˜ƒ);
      }
   }

   class FixedPlayerDistanceChunkTracker extends ChunkTracker {
      protected final Long2ByteMap chunks = new Long2ByteOpenHashMap();
      protected final int maxDistance;

      protected FixedPlayerDistanceChunkTracker(int var2) {
         super(â˜ƒ + 2, 16, 256);
         this.maxDistance = â˜ƒ;
         this.chunks.defaultReturnValue((byte)(â˜ƒ + 2));
      }

      @Override
      protected int getLevel(long var1) {
         return this.chunks.get(â˜ƒ);
      }

      @Override
      protected void setLevel(long var1, int var3) {
         byte â˜ƒ;
         if (â˜ƒ > this.maxDistance) {
            â˜ƒ = this.chunks.remove(â˜ƒ);
         } else {
            â˜ƒ = this.chunks.put(â˜ƒ, (byte)â˜ƒ);
         }

         this.onLevelChange(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      protected void onLevelChange(long var1, int var3, int var4) {
      }

      @Override
      protected int getLevelFromSource(long var1) {
         return this.havePlayer(â˜ƒ) ? 0 : Integer.MAX_VALUE;
      }

      private boolean havePlayer(long var1) {
         ObjectSet<ServerPlayer> â˜ƒ = DistanceManager.this.playersPerChunk.get(â˜ƒ);
         return â˜ƒ != null && !â˜ƒ.isEmpty();
      }

      public void runAllUpdates() {
         this.runUpdates(Integer.MAX_VALUE);
      }

      private void dumpChunks(String var1) {
         try {
            FileOutputStream â˜ƒ = new FileOutputStream(new File(â˜ƒ));

            try {
               for(it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry â˜ƒx : this.chunks.long2ByteEntrySet()) {
                  ChunkPos â˜ƒxx = new ChunkPos(â˜ƒx.getLongKey());
                  String â˜ƒxxx = Byte.toString(â˜ƒx.getByteValue());
                  â˜ƒ.write((â˜ƒxx.x + "\t" + â˜ƒxx.z + "\t" + â˜ƒxxx + "\n").getBytes(StandardCharsets.UTF_8));
               }
            } catch (Throwable var8) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }

               throw var8;
            }

            â˜ƒ.close();
         } catch (IOException var9) {
            DistanceManager.LOGGER.error(var9);
         }
      }
   }

   class PlayerTicketTracker extends DistanceManager.FixedPlayerDistanceChunkTracker {
      private int viewDistance;
      private final Long2IntMap queueLevels = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
      private final LongSet toUpdate = new LongOpenHashSet();

      protected PlayerTicketTracker(int var2) {
         super(â˜ƒ);
         this.viewDistance = 0;
         this.queueLevels.defaultReturnValue(â˜ƒ + 2);
      }

      @Override
      protected void onLevelChange(long var1, int var3, int var4) {
         this.toUpdate.add(â˜ƒ);
      }

      public void updateViewDistance(int var1) {
         for(it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry â˜ƒ : this.chunks.long2ByteEntrySet()) {
            byte â˜ƒx = â˜ƒ.getByteValue();
            long â˜ƒxx = â˜ƒ.getLongKey();
            this.onLevelChange(â˜ƒxx, â˜ƒx, this.haveTicketFor(â˜ƒx), â˜ƒx <= â˜ƒ - 2);
         }

         this.viewDistance = â˜ƒ;
      }

      private void onLevelChange(long var1, int var3, boolean var4, boolean var5) {
         if (â˜ƒ != â˜ƒ) {
            Ticket<?> â˜ƒ = new Ticket<>(TicketType.PLAYER, DistanceManager.PLAYER_TICKET_LEVEL, new ChunkPos(â˜ƒ));
            if (â˜ƒ) {
               DistanceManager.this.ticketThrottlerInput
                  .tell(ChunkTaskPriorityQueueSorter.message((Runnable)(() -> DistanceManager.this.mainThreadExecutor.execute(() -> {
                        if (this.haveTicketFor(this.getLevel(â˜ƒ))) {
                           DistanceManager.this.addTicket(â˜ƒ, â˜ƒ);
                           DistanceManager.this.ticketsToRelease.add(â˜ƒ);
                        } else {
                           DistanceManager.this.ticketThrottlerReleaser.tell(ChunkTaskPriorityQueueSorter.release(() -> {
                           }, â˜ƒ, false));
                        }
                     })), â˜ƒ, () -> â˜ƒ));
            } else {
               DistanceManager.this.ticketThrottlerReleaser
                  .tell(
                     ChunkTaskPriorityQueueSorter.release(
                        () -> DistanceManager.this.mainThreadExecutor.execute(() -> DistanceManager.this.removeTicket(â˜ƒ, â˜ƒ)), â˜ƒ, true
                     )
                  );
            }
         }
      }

      @Override
      public void runAllUpdates() {
         super.runAllUpdates();
         if (!this.toUpdate.isEmpty()) {
            LongIterator â˜ƒ = this.toUpdate.iterator();

            while(â˜ƒ.hasNext()) {
               long â˜ƒx = â˜ƒ.nextLong();
               int â˜ƒxx = this.queueLevels.get(â˜ƒx);
               int â˜ƒxxx = this.getLevel(â˜ƒx);
               if (â˜ƒxx != â˜ƒxxx) {
                  DistanceManager.this.ticketThrottler.onLevelChange(new ChunkPos(â˜ƒx), () -> this.queueLevels.get(â˜ƒ), â˜ƒxxx, var3 -> {
                     if (var3 >= this.queueLevels.defaultReturnValue()) {
                        this.queueLevels.remove(â˜ƒ);
                     } else {
                        this.queueLevels.put(â˜ƒ, var3);
                     }
                  });
                  this.onLevelChange(â˜ƒx, â˜ƒxxx, this.haveTicketFor(â˜ƒxx), this.haveTicketFor(â˜ƒxxx));
               }
            }

            this.toUpdate.clear();
         }
      }

      private boolean haveTicketFor(int var1) {
         return â˜ƒ <= this.viewDistance - 2;
      }
   }
}
