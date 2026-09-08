package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ChunkBufferBuilderPack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderDispatcher {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int MAX_WORKERS_32_BIT = 4;
   private static final VertexFormat VERTEX_FORMAT = DefaultVertexFormat.BLOCK;
   private final PriorityQueue<ChunkRenderDispatcher.RenderChunk.ChunkCompileTask> toBatch = Queues.newPriorityQueue();
   private final Queue<ChunkBufferBuilderPack> freeBuffers;
   private final Queue<Runnable> toUpload = Queues.newConcurrentLinkedQueue();
   private volatile int toBatchCount;
   private volatile int freeBufferCount;
   final ChunkBufferBuilderPack fixedBuffers;
   private final ProcessorMailbox<Runnable> mailbox;
   private final Executor executor;
   Level level;
   final LevelRenderer renderer;
   private Vec3 camera = Vec3.ZERO;

   public ChunkRenderDispatcher(Level var1, LevelRenderer var2, Executor var3, boolean var4, ChunkBufferBuilderPack var5) {
      this.level = â˜ƒ;
      this.renderer = â˜ƒ;
      int â˜ƒ = Math.max(
         1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3) / (RenderType.chunkBufferLayers().stream().mapToInt(RenderType::bufferSize).sum() * 4) - 1
      );
      int â˜ƒx = Runtime.getRuntime().availableProcessors();
      int â˜ƒxx = â˜ƒ ? â˜ƒx : Math.min(â˜ƒx, 4);
      int â˜ƒxxx = Math.max(1, Math.min(â˜ƒxx, â˜ƒ));
      this.fixedBuffers = â˜ƒ;
      List<ChunkBufferBuilderPack> â˜ƒxxxx = Lists.<ChunkBufferBuilderPack>newArrayListWithExpectedSize(â˜ƒxxx);

      try {
         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxx; ++â˜ƒxxxxx) {
            â˜ƒxxxx.add(new ChunkBufferBuilderPack());
         }
      } catch (OutOfMemoryError var14) {
         LOGGER.warn("Allocated only {}/{} buffers", â˜ƒxxxx.size(), â˜ƒxxx);
         int â˜ƒxxxxx = Math.min(â˜ƒxxxx.size() * 2 / 3, â˜ƒxxxx.size() - 1);

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxx) {
            â˜ƒxxxx.remove(â˜ƒxxxx.size() - 1);
         }

         System.gc();
      }

      this.freeBuffers = Queues.<ChunkBufferBuilderPack>newArrayDeque(â˜ƒxxxx);
      this.freeBufferCount = this.freeBuffers.size();
      this.executor = â˜ƒ;
      this.mailbox = ProcessorMailbox.create(â˜ƒ, "Chunk Renderer");
      this.mailbox.tell(this::runTask);
   }

   public void setLevel(Level var1) {
      this.level = â˜ƒ;
   }

   private void runTask() {
      if (!this.freeBuffers.isEmpty()) {
         ChunkRenderDispatcher.RenderChunk.ChunkCompileTask â˜ƒ = (ChunkRenderDispatcher.RenderChunk.ChunkCompileTask)this.toBatch.poll();
         if (â˜ƒ != null) {
            ChunkBufferBuilderPack â˜ƒx = (ChunkBufferBuilderPack)this.freeBuffers.poll();
            this.toBatchCount = this.toBatch.size();
            this.freeBufferCount = this.freeBuffers.size();
            CompletableFuture.runAsync(() -> {
            }, this.executor).thenCompose(var2x -> â˜ƒ.doTask(â˜ƒ)).whenComplete((var2x, var3) -> {
               if (var3 != null) {
                  CrashReport â˜ƒ = CrashReport.forThrowable(var3, "Batching chunks");
                  Minecraft.getInstance().delayCrash(Minecraft.getInstance().fillReport(â˜ƒ));
               } else {
                  this.mailbox.tell((Runnable)() -> {
                     if (var2x == ChunkRenderDispatcher.ChunkTaskResult.SUCCESSFUL) {
                        â˜ƒ.clearAll();
                     } else {
                        â˜ƒ.discardAll();
                     }

                     this.freeBuffers.add(â˜ƒ);
                     this.freeBufferCount = this.freeBuffers.size();
                     this.runTask();
                  });
               }
            });
         }
      }
   }

   public String getStats() {
      return String.format("pC: %03d, pU: %02d, aB: %02d", this.toBatchCount, this.toUpload.size(), this.freeBufferCount);
   }

   public int getToBatchCount() {
      return this.toBatchCount;
   }

   public int getToUpload() {
      return this.toUpload.size();
   }

   public int getFreeBufferCount() {
      return this.freeBufferCount;
   }

   public void setCamera(Vec3 var1) {
      this.camera = â˜ƒ;
   }

   public Vec3 getCameraPosition() {
      return this.camera;
   }

   public boolean uploadAllPendingUploads() {
      boolean â˜ƒ;
      Runnable â˜ƒ;
      for(â˜ƒ = false; (â˜ƒ = (Runnable)this.toUpload.poll()) != null; â˜ƒ = true) {
         â˜ƒ.run();
      }

      return â˜ƒ;
   }

   public void rebuildChunkSync(ChunkRenderDispatcher.RenderChunk var1) {
      â˜ƒ.compileSync();
   }

   public void blockUntilClear() {
      this.clearBatchQueue();
   }

   public void schedule(ChunkRenderDispatcher.RenderChunk.ChunkCompileTask var1) {
      this.mailbox.tell((Runnable)() -> {
         this.toBatch.offer(â˜ƒ);
         this.toBatchCount = this.toBatch.size();
         this.runTask();
      });
   }

   public CompletableFuture<Void> uploadChunkLayer(BufferBuilder var1, VertexBuffer var2) {
      return CompletableFuture.runAsync(() -> {
      }, this.toUpload::add).thenCompose(var3 -> this.doUploadChunkLayer(â˜ƒ, â˜ƒ));
   }

   private CompletableFuture<Void> doUploadChunkLayer(BufferBuilder var1, VertexBuffer var2) {
      return â˜ƒ.uploadLater(â˜ƒ);
   }

   private void clearBatchQueue() {
      while(!this.toBatch.isEmpty()) {
         ChunkRenderDispatcher.RenderChunk.ChunkCompileTask â˜ƒ = (ChunkRenderDispatcher.RenderChunk.ChunkCompileTask)this.toBatch.poll();
         if (â˜ƒ != null) {
            â˜ƒ.cancel();
         }
      }

      this.toBatchCount = 0;
   }

   public boolean isQueueEmpty() {
      return this.toBatchCount == 0 && this.toUpload.isEmpty();
   }

   public void dispose() {
      this.clearBatchQueue();
      this.mailbox.close();
      this.freeBuffers.clear();
   }

   static enum ChunkTaskResult {
      SUCCESSFUL,
      CANCELLED;
   }

   public static class CompiledChunk {
      public static final ChunkRenderDispatcher.CompiledChunk UNCOMPILED = new ChunkRenderDispatcher.CompiledChunk() {
         @Override
         public boolean facesCanSeeEachother(Direction var1, Direction var2) {
            return false;
         }
      };
      final Set<RenderType> hasBlocks = new ObjectArraySet<>();
      final Set<RenderType> hasLayer = new ObjectArraySet<>();
      boolean isCompletelyEmpty = true;
      final List<BlockEntity> renderableBlockEntities = Lists.<BlockEntity>newArrayList();
      VisibilitySet visibilitySet = new VisibilitySet();
      @Nullable
      BufferBuilder.SortState transparencyState;

      public boolean hasNoRenderableLayers() {
         return this.isCompletelyEmpty;
      }

      public boolean isEmpty(RenderType var1) {
         return !this.hasBlocks.contains(â˜ƒ);
      }

      public List<BlockEntity> getRenderableBlockEntities() {
         return this.renderableBlockEntities;
      }

      public boolean facesCanSeeEachother(Direction var1, Direction var2) {
         return this.visibilitySet.visibilityBetween(â˜ƒ, â˜ƒ);
      }
   }

   public class RenderChunk {
      public static final int SIZE = 16;
      public final int index;
      public final AtomicReference<ChunkRenderDispatcher.CompiledChunk> compiled = new AtomicReference(ChunkRenderDispatcher.CompiledChunk.UNCOMPILED);
      @Nullable
      private ChunkRenderDispatcher.RenderChunk.RebuildTask lastRebuildTask;
      @Nullable
      private ChunkRenderDispatcher.RenderChunk.ResortTransparencyTask lastResortTransparencyTask;
      private final Set<BlockEntity> globalBlockEntities = Sets.<BlockEntity>newHashSet();
      private final Map<RenderType, VertexBuffer> buffers = (Map<RenderType, VertexBuffer>)RenderType.chunkBufferLayers()
         .stream()
         .collect(Collectors.toMap(var0 -> var0, var0 -> new VertexBuffer()));
      public AABB bb;
      private int lastFrame = -1;
      private boolean dirty = true;
      final BlockPos.MutableBlockPos origin = new BlockPos.MutableBlockPos(-1, -1, -1);
      private final BlockPos.MutableBlockPos[] relativeOrigins = Util.make(new BlockPos.MutableBlockPos[6], var0 -> {
         for(int â˜ƒ = 0; â˜ƒ < var0.length; ++â˜ƒ) {
            var0[â˜ƒ] = new BlockPos.MutableBlockPos();
         }
      });
      private boolean playerChanged;

      public RenderChunk(int var2) {
         this.index = â˜ƒ;
      }

      private boolean doesChunkExistAt(BlockPos var1) {
         return ChunkRenderDispatcher.this.level
               .getChunk(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()), ChunkStatus.FULL, false)
            != null;
      }

      public boolean hasAllNeighbors() {
         int â˜ƒ = 24;
         if (!(this.getDistToPlayerSqr() > 576.0)) {
            return true;
         } else {
            return this.doesChunkExistAt(this.relativeOrigins[Direction.WEST.ordinal()])
               && this.doesChunkExistAt(this.relativeOrigins[Direction.NORTH.ordinal()])
               && this.doesChunkExistAt(this.relativeOrigins[Direction.EAST.ordinal()])
               && this.doesChunkExistAt(this.relativeOrigins[Direction.SOUTH.ordinal()]);
         }
      }

      public boolean setFrame(int var1) {
         if (this.lastFrame == â˜ƒ) {
            return false;
         } else {
            this.lastFrame = â˜ƒ;
            return true;
         }
      }

      public VertexBuffer getBuffer(RenderType var1) {
         return (VertexBuffer)this.buffers.get(â˜ƒ);
      }

      public void setOrigin(int var1, int var2, int var3) {
         if (â˜ƒ != this.origin.getX() || â˜ƒ != this.origin.getY() || â˜ƒ != this.origin.getZ()) {
            this.reset();
            this.origin.set(â˜ƒ, â˜ƒ, â˜ƒ);
            this.bb = new AABB((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ, (double)(â˜ƒ + 16), (double)(â˜ƒ + 16), (double)(â˜ƒ + 16));

            for(Direction â˜ƒ : Direction.values()) {
               this.relativeOrigins[â˜ƒ.ordinal()].set(this.origin).move(â˜ƒ, 16);
            }
         }
      }

      protected double getDistToPlayerSqr() {
         Camera â˜ƒ = Minecraft.getInstance().gameRenderer.getMainCamera();
         double â˜ƒx = this.bb.minX + 8.0 - â˜ƒ.getPosition().x;
         double â˜ƒxx = this.bb.minY + 8.0 - â˜ƒ.getPosition().y;
         double â˜ƒxxx = this.bb.minZ + 8.0 - â˜ƒ.getPosition().z;
         return â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx;
      }

      void beginLayer(BufferBuilder var1) {
         â˜ƒ.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
      }

      public ChunkRenderDispatcher.CompiledChunk getCompiledChunk() {
         return (ChunkRenderDispatcher.CompiledChunk)this.compiled.get();
      }

      private void reset() {
         this.cancelTasks();
         this.compiled.set(ChunkRenderDispatcher.CompiledChunk.UNCOMPILED);
         this.dirty = true;
      }

      public void releaseBuffers() {
         this.reset();
         this.buffers.values().forEach(VertexBuffer::close);
      }

      public BlockPos getOrigin() {
         return this.origin;
      }

      public void setDirty(boolean var1) {
         boolean â˜ƒ = this.dirty;
         this.dirty = true;
         this.playerChanged = â˜ƒ | (â˜ƒ && this.playerChanged);
      }

      public void setNotDirty() {
         this.dirty = false;
         this.playerChanged = false;
      }

      public boolean isDirty() {
         return this.dirty;
      }

      public boolean isDirtyFromPlayer() {
         return this.dirty && this.playerChanged;
      }

      public BlockPos getRelativeOrigin(Direction var1) {
         return this.relativeOrigins[â˜ƒ.ordinal()];
      }

      public boolean resortTransparency(RenderType var1, ChunkRenderDispatcher var2) {
         ChunkRenderDispatcher.CompiledChunk â˜ƒ = this.getCompiledChunk();
         if (this.lastResortTransparencyTask != null) {
            this.lastResortTransparencyTask.cancel();
         }

         if (!â˜ƒ.hasLayer.contains(â˜ƒ)) {
            return false;
         } else {
            this.lastResortTransparencyTask = new ChunkRenderDispatcher.RenderChunk.ResortTransparencyTask(this.getDistToPlayerSqr(), â˜ƒ);
            â˜ƒ.schedule(this.lastResortTransparencyTask);
            return true;
         }
      }

      protected void cancelTasks() {
         if (this.lastRebuildTask != null) {
            this.lastRebuildTask.cancel();
            this.lastRebuildTask = null;
         }

         if (this.lastResortTransparencyTask != null) {
            this.lastResortTransparencyTask.cancel();
            this.lastResortTransparencyTask = null;
         }
      }

      public ChunkRenderDispatcher.RenderChunk.ChunkCompileTask createCompileTask() {
         this.cancelTasks();
         BlockPos â˜ƒ = this.origin.immutable();
         int â˜ƒx = 1;
         RenderChunkRegion â˜ƒxx = RenderChunkRegion.createIfNotEmpty(ChunkRenderDispatcher.this.level, â˜ƒ.offset(-1, -1, -1), â˜ƒ.offset(16, 16, 16), 1);
         this.lastRebuildTask = new ChunkRenderDispatcher.RenderChunk.RebuildTask(this.getDistToPlayerSqr(), â˜ƒxx);
         return this.lastRebuildTask;
      }

      public void rebuildChunkAsync(ChunkRenderDispatcher var1) {
         ChunkRenderDispatcher.RenderChunk.ChunkCompileTask â˜ƒ = this.createCompileTask();
         â˜ƒ.schedule(â˜ƒ);
      }

      void updateGlobalBlockEntities(Set<BlockEntity> var1) {
         Set<BlockEntity> â˜ƒ = Sets.<BlockEntity>newHashSet(â˜ƒ);
         Set<BlockEntity> â˜ƒx = Sets.<BlockEntity>newHashSet(this.globalBlockEntities);
         â˜ƒ.removeAll(this.globalBlockEntities);
         â˜ƒx.removeAll(â˜ƒ);
         this.globalBlockEntities.clear();
         this.globalBlockEntities.addAll(â˜ƒ);
         ChunkRenderDispatcher.this.renderer.updateGlobalBlockEntities(â˜ƒx, â˜ƒ);
      }

      public void compileSync() {
         ChunkRenderDispatcher.RenderChunk.ChunkCompileTask â˜ƒ = this.createCompileTask();
         â˜ƒ.doTask(ChunkRenderDispatcher.this.fixedBuffers);
      }

      abstract class ChunkCompileTask implements Comparable<ChunkRenderDispatcher.RenderChunk.ChunkCompileTask> {
         protected final double distAtCreation;
         protected final AtomicBoolean isCancelled = new AtomicBoolean(false);

         public ChunkCompileTask(double var2) {
            this.distAtCreation = â˜ƒ;
         }

         public abstract CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> doTask(ChunkBufferBuilderPack var1);

         public abstract void cancel();

         public int compareTo(ChunkRenderDispatcher.RenderChunk.ChunkCompileTask var1) {
            return Doubles.compare(this.distAtCreation, â˜ƒ.distAtCreation);
         }
      }

      class RebuildTask extends ChunkRenderDispatcher.RenderChunk.ChunkCompileTask {
         @Nullable
         protected RenderChunkRegion region;

         public RebuildTask(double var2, @Nullable RenderChunkRegion var4) {
            super(â˜ƒ);
            this.region = â˜ƒ;
         }

         @Override
         public CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> doTask(ChunkBufferBuilderPack var1) {
            if (this.isCancelled.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (!RenderChunk.this.hasAllNeighbors()) {
               this.region = null;
               RenderChunk.this.setDirty(false);
               this.isCancelled.set(true);
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (this.isCancelled.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else {
               Vec3 â˜ƒ = ChunkRenderDispatcher.this.getCameraPosition();
               float â˜ƒx = (float)â˜ƒ.x;
               float â˜ƒxx = (float)â˜ƒ.y;
               float â˜ƒxxx = (float)â˜ƒ.z;
               ChunkRenderDispatcher.CompiledChunk â˜ƒxxxx = new ChunkRenderDispatcher.CompiledChunk();
               Set<BlockEntity> â˜ƒxxxxx = this.compile(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒ);
               RenderChunk.this.updateGlobalBlockEntities(â˜ƒxxxxx);
               if (this.isCancelled.get()) {
                  return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
               } else {
                  List<CompletableFuture<Void>> â˜ƒ = Lists.newArrayList();
                  â˜ƒxxxx.hasLayer
                     .forEach(var3x -> â˜ƒ.add(ChunkRenderDispatcher.this.uploadChunkLayer(â˜ƒ.builder(var3x), RenderChunk.this.getBuffer(var3x))));
                  return Util.sequenceFailFast(â˜ƒ).handle((var2x, var3x) -> {
                     if (var3x != null && !(var3x instanceof CancellationException) && !(var3x instanceof InterruptedException)) {
                        Minecraft.getInstance().delayCrash(CrashReport.forThrowable(var3x, "Rendering chunk"));
                     }

                     if (this.isCancelled.get()) {
                        return ChunkRenderDispatcher.ChunkTaskResult.CANCELLED;
                     } else {
                        RenderChunk.this.compiled.set(â˜ƒ);
                        return ChunkRenderDispatcher.ChunkTaskResult.SUCCESSFUL;
                     }
                  });
               }
            }
         }

         private Set<BlockEntity> compile(float var1, float var2, float var3, ChunkRenderDispatcher.CompiledChunk var4, ChunkBufferBuilderPack var5) {
            int â˜ƒ = 1;
            BlockPos â˜ƒx = RenderChunk.this.origin.immutable();
            BlockPos â˜ƒxx = â˜ƒx.offset(15, 15, 15);
            VisGraph â˜ƒxxx = new VisGraph();
            Set<BlockEntity> â˜ƒxxxx = Sets.<BlockEntity>newHashSet();
            RenderChunkRegion â˜ƒxxxxx = this.region;
            this.region = null;
            PoseStack â˜ƒxxxxxx = new PoseStack();
            if (â˜ƒxxxxx != null) {
               ModelBlockRenderer.enableCaching();
               Random â˜ƒxxxxxxx = new Random();
               BlockRenderDispatcher â˜ƒxxxxxxxx = Minecraft.getInstance().getBlockRenderer();

               for(BlockPos â˜ƒxxxxxxxxx : BlockPos.betweenClosed(â˜ƒx, â˜ƒxx)) {
                  BlockState â˜ƒxxxxxxxxxx = â˜ƒxxxxx.getBlockState(â˜ƒxxxxxxxxx);
                  if (â˜ƒxxxxxxxxxx.isSolidRender(â˜ƒxxxxx, â˜ƒxxxxxxxxx)) {
                     â˜ƒxxx.setOpaque(â˜ƒxxxxxxxxx);
                  }

                  if (â˜ƒxxxxxxxxxx.hasBlockEntity()) {
                     BlockEntity â˜ƒxxxxxxxxxx = â˜ƒxxxxx.getBlockEntity(â˜ƒxxxxxxxxx, LevelChunk.EntityCreationType.CHECK);
                     if (â˜ƒxxxxxxxxxx != null) {
                        this.handleBlockEntity(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxxxxxx);
                     }
                  }

                  FluidState â˜ƒxxxxxxxxxx = â˜ƒxxxxx.getFluidState(â˜ƒxxxxxxxxx);
                  if (!â˜ƒxxxxxxxxxx.isEmpty()) {
                     RenderType â˜ƒxxxxxxxxxxx = ItemBlockRenderTypes.getRenderLayer(â˜ƒxxxxxxxxxx);
                     BufferBuilder â˜ƒxxxxxxxxxxxx = â˜ƒ.builder(â˜ƒxxxxxxxxxxx);
                     if (â˜ƒ.hasLayer.add(â˜ƒxxxxxxxxxxx)) {
                        RenderChunk.this.beginLayer(â˜ƒxxxxxxxxxxxx);
                     }

                     if (â˜ƒxxxxxxxx.renderLiquid(â˜ƒxxxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxx)) {
                        â˜ƒ.isCompletelyEmpty = false;
                        â˜ƒ.hasBlocks.add(â˜ƒxxxxxxxxxxx);
                     }
                  }

                  if (â˜ƒxxxxxxxxxx.getRenderShape() != RenderShape.INVISIBLE) {
                     RenderType â˜ƒxxxxxxxxxx = ItemBlockRenderTypes.getChunkRenderType(â˜ƒxxxxxxxxxx);
                     BufferBuilder â˜ƒxxxxxxxxxxx = â˜ƒ.builder(â˜ƒxxxxxxxxxx);
                     if (â˜ƒ.hasLayer.add(â˜ƒxxxxxxxxxx)) {
                        RenderChunk.this.beginLayer(â˜ƒxxxxxxxxxxx);
                     }

                     â˜ƒxxxxxx.pushPose();
                     â˜ƒxxxxxx.translate((double)(â˜ƒxxxxxxxxx.getX() & 15), (double)(â˜ƒxxxxxxxxx.getY() & 15), (double)(â˜ƒxxxxxxxxx.getZ() & 15));
                     if (â˜ƒxxxxxxxx.renderBatched(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxxxxx, true, â˜ƒxxxxxxx)) {
                        â˜ƒ.isCompletelyEmpty = false;
                        â˜ƒ.hasBlocks.add(â˜ƒxxxxxxxxxx);
                     }

                     â˜ƒxxxxxx.popPose();
                  }
               }

               if (â˜ƒ.hasBlocks.contains(RenderType.translucent())) {
                  BufferBuilder â˜ƒxxxxxxxxx = â˜ƒ.builder(RenderType.translucent());
                  â˜ƒxxxxxxxxx.setQuadSortOrigin(â˜ƒ - (float)â˜ƒx.getX(), â˜ƒ - (float)â˜ƒx.getY(), â˜ƒ - (float)â˜ƒx.getZ());
                  â˜ƒ.transparencyState = â˜ƒxxxxxxxxx.getSortState();
               }

               â˜ƒ.hasLayer.stream().map(â˜ƒ::builder).forEach(BufferBuilder::end);
               ModelBlockRenderer.clearCache();
            }

            â˜ƒ.visibilitySet = â˜ƒxxx.resolve();
            return â˜ƒxxxx;
         }

         private <E extends BlockEntity> void handleBlockEntity(ChunkRenderDispatcher.CompiledChunk var1, Set<BlockEntity> var2, E var3) {
            BlockEntityRenderer<E> â˜ƒ = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(â˜ƒ);
            if (â˜ƒ != null) {
               â˜ƒ.renderableBlockEntities.add(â˜ƒ);
               if (â˜ƒ.shouldRenderOffScreen(â˜ƒ)) {
                  â˜ƒ.add(â˜ƒ);
               }
            }
         }

         @Override
         public void cancel() {
            this.region = null;
            if (this.isCancelled.compareAndSet(false, true)) {
               RenderChunk.this.setDirty(false);
            }
         }
      }

      class ResortTransparencyTask extends ChunkRenderDispatcher.RenderChunk.ChunkCompileTask {
         private final ChunkRenderDispatcher.CompiledChunk compiledChunk;

         public ResortTransparencyTask(double var2, ChunkRenderDispatcher.CompiledChunk var4) {
            super(â˜ƒ);
            this.compiledChunk = â˜ƒ;
         }

         @Override
         public CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> doTask(ChunkBufferBuilderPack var1) {
            if (this.isCancelled.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (!RenderChunk.this.hasAllNeighbors()) {
               this.isCancelled.set(true);
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else if (this.isCancelled.get()) {
               return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
            } else {
               Vec3 â˜ƒ = ChunkRenderDispatcher.this.getCameraPosition();
               float â˜ƒx = (float)â˜ƒ.x;
               float â˜ƒxx = (float)â˜ƒ.y;
               float â˜ƒxxx = (float)â˜ƒ.z;
               BufferBuilder.SortState â˜ƒxxxx = this.compiledChunk.transparencyState;
               if (â˜ƒxxxx != null && this.compiledChunk.hasBlocks.contains(RenderType.translucent())) {
                  BufferBuilder â˜ƒxxxxx = â˜ƒ.builder(RenderType.translucent());
                  RenderChunk.this.beginLayer(â˜ƒxxxxx);
                  â˜ƒxxxxx.restoreSortState(â˜ƒxxxx);
                  â˜ƒxxxxx.setQuadSortOrigin(
                     â˜ƒx - (float)RenderChunk.this.origin.getX(),
                     â˜ƒxx - (float)RenderChunk.this.origin.getY(),
                     â˜ƒxxx - (float)RenderChunk.this.origin.getZ()
                  );
                  this.compiledChunk.transparencyState = â˜ƒxxxxx.getSortState();
                  â˜ƒxxxxx.end();
                  if (this.isCancelled.get()) {
                     return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
                  } else {
                     CompletableFuture<ChunkRenderDispatcher.ChunkTaskResult> â˜ƒxxxxx = ChunkRenderDispatcher.this.uploadChunkLayer(
                           â˜ƒ.builder(RenderType.translucent()), RenderChunk.this.getBuffer(RenderType.translucent())
                        )
                        .thenApply(var0 -> ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
                     return â˜ƒxxxxx.handle((var1x, var2x) -> {
                        if (var2x != null && !(var2x instanceof CancellationException) && !(var2x instanceof InterruptedException)) {
                           Minecraft.getInstance().delayCrash(CrashReport.forThrowable(var2x, "Rendering chunk"));
                        }

                        return this.isCancelled.get() ? ChunkRenderDispatcher.ChunkTaskResult.CANCELLED : ChunkRenderDispatcher.ChunkTaskResult.SUCCESSFUL;
                     });
                  }
               } else {
                  return CompletableFuture.completedFuture(ChunkRenderDispatcher.ChunkTaskResult.CANCELLED);
               }
            }
         }

         @Override
         public void cancel() {
            this.isCancelled.set(true);
         }
      }
   }
}
