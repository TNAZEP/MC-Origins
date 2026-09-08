package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.primitives.Doubles;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.VertexBufferUploader;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderDispatcher {
   private static final Logger field_178523_a = LogManager.getLogger();
   private static final ThreadFactory field_178521_b = new ThreadFactoryBuilder()
      .setNameFormat("Chunk Batcher %d")
      .setDaemon(true)
      .setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_178523_a))
      .build();
   private final int field_188249_c;
   private final List<Thread> field_188250_d = Lists.newArrayList();
   private final List<ChunkRenderWorker> field_178522_c = Lists.<ChunkRenderWorker>newArrayList();
   private final PriorityBlockingQueue<ChunkRenderTask> field_178519_d = Queues.newPriorityBlockingQueue();
   private final BlockingQueue<RegionRenderCacheBuilder> field_178520_e;
   private final WorldVertexBufferUploader field_178517_f = new WorldVertexBufferUploader();
   private final VertexBufferUploader field_178518_g = new VertexBufferUploader();
   private final Queue<ChunkRenderDispatcher.PendingUpload> field_178524_h = Queues.<ChunkRenderDispatcher.PendingUpload>newPriorityQueue();
   private final ChunkRenderWorker field_178525_i;

   public ChunkRenderDispatcher() {
      int ☃ = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3) / 10485760);
      int ☃x = Math.max(1, MathHelper.func_76125_a(Runtime.getRuntime().availableProcessors(), 1, ☃ / 5));
      this.field_188249_c = MathHelper.func_76125_a(☃x * 10, 1, ☃);
      if (☃x > 1) {
         for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
            ChunkRenderWorker ☃xxx = new ChunkRenderWorker(this);
            Thread ☃xxxx = field_178521_b.newThread(☃xxx);
            ☃xxxx.start();
            this.field_178522_c.add(☃xxx);
            this.field_188250_d.add(☃xxxx);
         }
      }

      this.field_178520_e = Queues.<RegionRenderCacheBuilder>newArrayBlockingQueue(this.field_188249_c);

      for(int ☃ = 0; ☃ < this.field_188249_c; ++☃) {
         this.field_178520_e.add(new RegionRenderCacheBuilder());
      }

      this.field_178525_i = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
   }

   public String func_178504_a() {
      return this.field_188250_d.isEmpty()
         ? String.format("pC: %03d, single-threaded", this.field_178519_d.size())
         : String.format("pC: %03d, pU: %1d, aB: %1d", this.field_178519_d.size(), this.field_178524_h.size(), this.field_178520_e.size());
   }

   public boolean func_178516_a(long var1) {
      boolean ☃ = false;

      boolean ☃;
      do {
         ☃ = false;
         if (this.field_188250_d.isEmpty()) {
            ChunkRenderTask ☃x = (ChunkRenderTask)this.field_178519_d.poll();
            if (☃x != null) {
               try {
                  this.field_178525_i.func_178474_a(☃x);
                  ☃ = true;
               } catch (InterruptedException var8) {
                  field_178523_a.warn("Skipped task due to interrupt");
               }
            }
         }

         synchronized(this.field_178524_h) {
            if (!this.field_178524_h.isEmpty()) {
               ((ChunkRenderDispatcher.PendingUpload)this.field_178524_h.poll()).field_188241_b.run();
               ☃ = true;
               ☃ = true;
            }
         }
      } while(☃ != 0L && ☃ && ☃ >= Util.func_211178_c());

      return ☃;
   }

   public boolean func_178507_a(RenderChunk var1) {
      ☃.func_178579_c().lock();

      boolean var4;
      try {
         ChunkRenderTask ☃ = ☃.func_178574_d();
         ☃.func_178539_a(() -> this.field_178519_d.remove(☃));
         boolean ☃x = this.field_178519_d.offer(☃);
         if (!☃x) {
            ☃.func_178542_e();
         }

         var4 = ☃x;
      } finally {
         ☃.func_178579_c().unlock();
      }

      return var4;
   }

   public boolean func_178505_b(RenderChunk var1) {
      ☃.func_178579_c().lock();

      boolean var3;
      try {
         ChunkRenderTask ☃ = ☃.func_178574_d();

         try {
            this.field_178525_i.func_178474_a(☃);
         } catch (InterruptedException var7) {
         }

         var3 = true;
      } finally {
         ☃.func_178579_c().unlock();
      }

      return var3;
   }

   public void func_178514_b() {
      this.func_178513_e();
      List<RegionRenderCacheBuilder> ☃ = Lists.<RegionRenderCacheBuilder>newArrayList();

      while(☃.size() != this.field_188249_c) {
         this.func_178516_a(Long.MAX_VALUE);

         try {
            ☃.add(this.func_178515_c());
         } catch (InterruptedException var3) {
         }
      }

      this.field_178520_e.addAll(☃);
   }

   public void func_178512_a(RegionRenderCacheBuilder var1) {
      this.field_178520_e.add(☃);
   }

   public RegionRenderCacheBuilder func_178515_c() throws InterruptedException {
      return (RegionRenderCacheBuilder)this.field_178520_e.take();
   }

   public ChunkRenderTask func_178511_d() throws InterruptedException {
      return (ChunkRenderTask)this.field_178519_d.take();
   }

   public boolean func_178509_c(RenderChunk var1) {
      ☃.func_178579_c().lock();

      boolean var3;
      try {
         ChunkRenderTask ☃ = ☃.func_178582_e();
         if (☃ == null) {
            return true;
         }

         ☃.func_178539_a(() -> this.field_178519_d.remove(☃));
         var3 = this.field_178519_d.offer(☃);
      } finally {
         ☃.func_178579_c().unlock();
      }

      return var3;
   }

   public ListenableFuture<Object> func_188245_a(BlockRenderLayer var1, BufferBuilder var2, RenderChunk var3, CompiledChunk var4, double var5) {
      if (Minecraft.func_71410_x().func_152345_ab()) {
         if (OpenGlHelper.func_176075_f()) {
            this.func_178506_a(☃, ☃.func_178565_b(☃.ordinal()));
         } else {
            this.func_178510_a(☃, ((ListedRenderChunk)☃).func_178600_a(☃, ☃), ☃);
         }

         ☃.func_178969_c(0.0, 0.0, 0.0);
         return Futures.immediateFuture(null);
      } else {
         ListenableFutureTask<Object> ☃ = ListenableFutureTask.create(() -> this.func_188245_a(☃, ☃, ☃, ☃, ☃), null);
         synchronized(this.field_178524_h) {
            this.field_178524_h.add(new ChunkRenderDispatcher.PendingUpload(☃, ☃));
            return ☃;
         }
      }
   }

   private void func_178510_a(BufferBuilder var1, int var2, RenderChunk var3) {
      GlStateManager.func_187423_f(☃, 4864);
      GlStateManager.func_179094_E();
      ☃.func_178572_f();
      this.field_178517_f.func_181679_a(☃);
      GlStateManager.func_179121_F();
      GlStateManager.func_187415_K();
   }

   private void func_178506_a(BufferBuilder var1, VertexBuffer var2) {
      this.field_178518_g.func_178178_a(☃);
      this.field_178518_g.func_181679_a(☃);
   }

   public void func_178513_e() {
      while(!this.field_178519_d.isEmpty()) {
         ChunkRenderTask ☃ = (ChunkRenderTask)this.field_178519_d.poll();
         if (☃ != null) {
            ☃.func_178542_e();
         }
      }
   }

   public boolean func_188247_f() {
      return this.field_178519_d.isEmpty() && this.field_178524_h.isEmpty();
   }

   public void func_188244_g() {
      this.func_178513_e();

      for(ChunkRenderWorker ☃ : this.field_178522_c) {
         ☃.func_188264_a();
      }

      for(Thread ☃ : this.field_188250_d) {
         try {
            ☃.interrupt();
            ☃.join();
         } catch (InterruptedException var4) {
            field_178523_a.warn("Interrupted whilst waiting for worker to die", var4);
         }
      }

      this.field_178520_e.clear();
   }

   public boolean func_188248_h() {
      return this.field_178520_e.isEmpty();
   }

   class PendingUpload implements Comparable<ChunkRenderDispatcher.PendingUpload> {
      private final ListenableFutureTask<Object> field_188241_b;
      private final double field_188242_c;

      public PendingUpload(ListenableFutureTask<Object> var2, double var3) {
         this.field_188241_b = ☃;
         this.field_188242_c = ☃;
      }

      public int compareTo(ChunkRenderDispatcher.PendingUpload var1) {
         return Doubles.compare(this.field_188242_c, ☃.field_188242_c);
      }
   }
}
