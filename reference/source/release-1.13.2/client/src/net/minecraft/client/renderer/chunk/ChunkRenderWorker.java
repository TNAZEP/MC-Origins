package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRenderWorker implements Runnable {
   private static final Logger field_152478_a = LogManager.getLogger();
   private final ChunkRenderDispatcher field_178477_b;
   private final RegionRenderCacheBuilder field_178478_c;
   private boolean field_188265_d = true;

   public ChunkRenderWorker(ChunkRenderDispatcher var1) {
      this(☃, null);
   }

   public ChunkRenderWorker(ChunkRenderDispatcher var1, @Nullable RegionRenderCacheBuilder var2) {
      this.field_178477_b = ☃;
      this.field_178478_c = ☃;
   }

   public void run() {
      while(this.field_188265_d) {
         try {
            this.func_178474_a(this.field_178477_b.func_178511_d());
         } catch (InterruptedException var3) {
            field_152478_a.debug("Stopping chunk worker due to interrupt");
            return;
         } catch (Throwable var4) {
            CrashReport ☃ = CrashReport.func_85055_a(var4, "Batching chunks");
            Minecraft.func_71410_x().func_71404_a(Minecraft.func_71410_x().func_71396_d(☃));
            return;
         }
      }
   }

   protected void func_178474_a(final ChunkRenderTask var1) throws InterruptedException {
      ☃.func_178540_f().lock();

      try {
         if (☃.func_178546_a() != ChunkRenderTask.Status.PENDING) {
            if (!☃.func_178537_h()) {
               field_152478_a.warn("Chunk render task was {} when I expected it to be pending; ignoring task", ☃.func_178546_a());
            }

            return;
         }

         BlockPos ☃ = new BlockPos(Minecraft.func_71410_x().field_71439_g);
         BlockPos ☃x = ☃.func_178536_b().func_178568_j();
         int ☃xx = 16;
         int ☃xxx = 8;
         int ☃xxxx = 24;
         if (☃x.func_177982_a(8, 8, 8).func_177951_i(☃) > 576.0) {
            World ☃xxxxx = ☃.func_178536_b().func_188283_p();
            BlockPos.MutableBlockPos ☃xxxxxx = new BlockPos.MutableBlockPos(☃x);
            if (!this.func_188263_a(☃xxxxxx.func_189533_g(☃x).func_189534_c(EnumFacing.WEST, 16), ☃xxxxx)
               || !this.func_188263_a(☃xxxxxx.func_189533_g(☃x).func_189534_c(EnumFacing.NORTH, 16), ☃xxxxx)
               || !this.func_188263_a(☃xxxxxx.func_189533_g(☃x).func_189534_c(EnumFacing.EAST, 16), ☃xxxxx)
               || !this.func_188263_a(☃xxxxxx.func_189533_g(☃x).func_189534_c(EnumFacing.SOUTH, 16), ☃xxxxx)) {
               return;
            }
         }

         ☃.func_178535_a(ChunkRenderTask.Status.COMPILING);
      } finally {
         ☃.func_178540_f().unlock();
      }

      Entity ☃ = Minecraft.func_71410_x().func_175606_aa();
      if (☃ == null) {
         ☃.func_178542_e();
      } else {
         ☃.func_178541_a(this.func_178475_b());
         Vec3d ☃ = ActiveRenderInfo.func_178806_a(☃, 1.0);
         float ☃x = (float)☃.field_72450_a;
         float ☃xx = (float)☃.field_72448_b;
         float ☃xxx = (float)☃.field_72449_c;
         ChunkRenderTask.Type ☃xxxx = ☃.func_178538_g();
         if (☃xxxx == ChunkRenderTask.Type.REBUILD_CHUNK) {
            ☃.func_178536_b().func_178581_b(☃x, ☃xx, ☃xxx, ☃);
         } else if (☃xxxx == ChunkRenderTask.Type.RESORT_TRANSPARENCY) {
            ☃.func_178536_b().func_178570_a(☃x, ☃xx, ☃xxx, ☃);
         }

         ☃.func_178540_f().lock();

         try {
            if (☃.func_178546_a() != ChunkRenderTask.Status.COMPILING) {
               if (!☃.func_178537_h()) {
                  field_152478_a.warn("Chunk render task was {} when I expected it to be compiling; aborting task", ☃.func_178546_a());
               }

               this.func_178473_b(☃);
               return;
            }

            ☃.func_178535_a(ChunkRenderTask.Status.UPLOADING);
         } finally {
            ☃.func_178540_f().unlock();
         }

         final CompiledChunk var26 = ☃.func_178544_c();
         ArrayList var9 = Lists.newArrayList();
         if (☃xxxx == ChunkRenderTask.Type.REBUILD_CHUNK) {
            for(BlockRenderLayer ☃ : BlockRenderLayer.values()) {
               if (var26.func_178492_d(☃)) {
                  var9.add(this.field_178477_b.func_188245_a(☃, ☃.func_178545_d().func_179038_a(☃), ☃.func_178536_b(), var26, ☃.func_188228_i()));
               }
            }
         } else if (☃xxxx == ChunkRenderTask.Type.RESORT_TRANSPARENCY) {
            var9.add(
               this.field_178477_b
                  .func_188245_a(
                     BlockRenderLayer.TRANSLUCENT, ☃.func_178545_d().func_179038_a(BlockRenderLayer.TRANSLUCENT), ☃.func_178536_b(), var26, ☃.func_188228_i()
                  )
            );
         }

         ListenableFuture<List<Object>> ☃ = Futures.allAsList(var9);
         ☃.func_178539_a(() -> ☃.cancel(false));
         Futures.addCallback(☃, new FutureCallback<List<Object>>() {
            public void onSuccess(@Nullable List<Object> var1x) {
               ChunkRenderWorker.this.func_178473_b(☃);
               ☃.func_178540_f().lock();

               label41: {
                  try {
                     if (☃.func_178546_a() == ChunkRenderTask.Status.UPLOADING) {
                        ☃.func_178535_a(ChunkRenderTask.Status.DONE);
                        break label41;
                     }

                     if (!☃.func_178537_h()) {
                        ChunkRenderWorker.field_152478_a.warn("Chunk render task was {} when I expected it to be uploading; aborting task", ☃.func_178546_a());
                     }
                  } finally {
                     ☃.func_178540_f().unlock();
                  }

                  return;
               }

               ☃.func_178536_b().func_178580_a(var26);
            }

            @Override
            public void onFailure(Throwable var1x) {
               ChunkRenderWorker.this.func_178473_b(☃);
               if (!(☃ instanceof CancellationException) && !(☃ instanceof InterruptedException)) {
                  Minecraft.func_71410_x().func_71404_a(CrashReport.func_85055_a(☃, "Rendering chunk"));
               }
            }
         });
      }
   }

   private boolean func_188263_a(BlockPos var1, World var2) {
      return !☃.func_72964_e(☃.func_177958_n() >> 4, ☃.func_177952_p() >> 4).func_76621_g();
   }

   private RegionRenderCacheBuilder func_178475_b() throws InterruptedException {
      return this.field_178478_c != null ? this.field_178478_c : this.field_178477_b.func_178515_c();
   }

   private void func_178473_b(ChunkRenderTask var1) {
      if (this.field_178478_c == null) {
         this.field_178477_b.func_178512_a(☃.func_178545_d());
      }
   }

   public void func_188264_a() {
      this.field_188265_d = false;
   }
}
