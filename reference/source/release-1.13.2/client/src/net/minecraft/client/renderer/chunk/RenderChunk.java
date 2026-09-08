package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Sets;
import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class RenderChunk {
   private volatile World field_178588_d;
   private final WorldRenderer field_178589_e;
   public static int field_178592_a;
   public CompiledChunk field_178590_b = CompiledChunk.field_178502_a;
   private final ReentrantLock field_178587_g = new ReentrantLock();
   private final ReentrantLock field_178598_h = new ReentrantLock();
   private ChunkRenderTask field_178599_i;
   private final Set<TileEntity> field_181056_j = Sets.<TileEntity>newHashSet();
   private final FloatBuffer field_178597_k = GLAllocation.func_74529_h(16);
   private final VertexBuffer[] field_178594_l = new VertexBuffer[BlockRenderLayer.values().length];
   public AxisAlignedBB field_178591_c;
   private int field_178595_m = -1;
   private boolean field_178593_n = true;
   private final BlockPos.MutableBlockPos field_178586_f = new BlockPos.MutableBlockPos(-1, -1, -1);
   private final BlockPos.MutableBlockPos[] field_181702_p = Util.func_200696_a(new BlockPos.MutableBlockPos[6], var0 -> {
      for(int ☃ = 0; ☃ < var0.length; ++☃) {
         var0[☃] = new BlockPos.MutableBlockPos();
      }
   });
   private boolean field_188284_q;

   public RenderChunk(World var1, WorldRenderer var2) {
      this.field_178588_d = ☃;
      this.field_178589_e = ☃;
      if (OpenGlHelper.func_176075_f()) {
         for(int ☃ = 0; ☃ < BlockRenderLayer.values().length; ++☃) {
            this.field_178594_l[☃] = new VertexBuffer(DefaultVertexFormats.field_176600_a);
         }
      }
   }

   public boolean func_178577_a(int var1) {
      if (this.field_178595_m == ☃) {
         return false;
      } else {
         this.field_178595_m = ☃;
         return true;
      }
   }

   public VertexBuffer func_178565_b(int var1) {
      return this.field_178594_l[☃];
   }

   public void func_189562_a(int var1, int var2, int var3) {
      if (☃ != this.field_178586_f.func_177958_n() || ☃ != this.field_178586_f.func_177956_o() || ☃ != this.field_178586_f.func_177952_p()) {
         this.func_178585_h();
         this.field_178586_f.func_181079_c(☃, ☃, ☃);
         this.field_178591_c = new AxisAlignedBB((double)☃, (double)☃, (double)☃, (double)(☃ + 16), (double)(☃ + 16), (double)(☃ + 16));

         for(EnumFacing ☃ : EnumFacing.values()) {
            this.field_181702_p[☃.ordinal()].func_189533_g(this.field_178586_f).func_189534_c(☃, 16);
         }

         this.func_178567_n();
      }
   }

   public void func_178570_a(float var1, float var2, float var3, ChunkRenderTask var4) {
      CompiledChunk ☃ = ☃.func_178544_c();
      if (☃.func_178487_c() != null && !☃.func_178491_b(BlockRenderLayer.TRANSLUCENT)) {
         this.func_178573_a(☃.func_178545_d().func_179038_a(BlockRenderLayer.TRANSLUCENT), this.field_178586_f);
         ☃.func_178545_d().func_179038_a(BlockRenderLayer.TRANSLUCENT).func_178993_a(☃.func_178487_c());
         this.func_178584_a(BlockRenderLayer.TRANSLUCENT, ☃, ☃, ☃, ☃.func_178545_d().func_179038_a(BlockRenderLayer.TRANSLUCENT), ☃);
      }
   }

   public void func_178581_b(float var1, float var2, float var3, ChunkRenderTask var4) {
      CompiledChunk ☃ = new CompiledChunk();
      int ☃x = 1;
      BlockPos ☃xx = this.field_178586_f.func_185334_h();
      BlockPos ☃xxx = ☃xx.func_177982_a(15, 15, 15);
      World ☃xxxx = this.field_178588_d;
      if (☃xxxx != null) {
         ☃.func_178540_f().lock();

         try {
            if (☃.func_178546_a() != ChunkRenderTask.Status.COMPILING) {
               return;
            }

            ☃.func_178543_a(☃);
         } finally {
            ☃.func_178540_f().unlock();
         }

         RenderChunkCache var10 = RenderChunkCache.func_212397_a(☃xxxx, ☃xx.func_177982_a(-1, -1, -1), ☃xx.func_177982_a(16, 16, 16), 1);
         VisGraph var11 = new VisGraph();
         HashSet var12 = Sets.newHashSet();
         if (var10 != null) {
            ++field_178592_a;
            boolean[] ☃xxxxx = new boolean[BlockRenderLayer.values().length];
            BlockModelRenderer.func_211847_a();
            Random ☃xxxxxx = new Random();
            BlockRendererDispatcher ☃xxxxxxx = Minecraft.func_71410_x().func_175602_ab();

            for(BlockPos.MutableBlockPos ☃xxxxxxxx : BlockPos.func_177975_b(☃xx, ☃xxx)) {
               IBlockState ☃xxxxxxxxx = var10.func_180495_p(☃xxxxxxxx);
               Block ☃xxxxxxxxxx = ☃xxxxxxxxx.func_177230_c();
               if (☃xxxxxxxxx.func_200015_d(var10, ☃xxxxxxxx)) {
                  var11.func_178606_a(☃xxxxxxxx);
               }

               if (☃xxxxxxxxxx.func_149716_u()) {
                  TileEntity ☃xxxxxxxxx = var10.func_212399_a(☃xxxxxxxx, Chunk.EnumCreateEntityType.CHECK);
                  if (☃xxxxxxxxx != null) {
                     TileEntityRenderer<TileEntity> ☃xxxxxxxxxx = TileEntityRendererDispatcher.field_147556_a.func_147547_b(☃xxxxxxxxx);
                     if (☃xxxxxxxxxx != null) {
                        ☃.func_178490_a(☃xxxxxxxxx);
                        if (☃xxxxxxxxxx.func_188185_a(☃xxxxxxxxx)) {
                           var12.add(☃xxxxxxxxx);
                        }
                     }
                  }
               }

               IFluidState ☃xxxxxxxxx = var10.func_204610_c(☃xxxxxxxx);
               if (!☃xxxxxxxxx.func_206888_e()) {
                  BlockRenderLayer ☃xxxxxxxxxx = ☃xxxxxxxxx.func_180664_k();
                  int ☃xxxxxxxxxxx = ☃xxxxxxxxxx.ordinal();
                  BufferBuilder ☃xxxxxxxxxxxx = ☃.func_178545_d().func_179039_a(☃xxxxxxxxxxx);
                  if (!☃.func_178492_d(☃xxxxxxxxxx)) {
                     ☃.func_178493_c(☃xxxxxxxxxx);
                     this.func_178573_a(☃xxxxxxxxxxxx, ☃xx);
                  }

                  ☃xxxxx[☃xxxxxxxxxxx] |= ☃xxxxxxx.func_205318_a(☃xxxxxxxx, var10, ☃xxxxxxxxxxxx, ☃xxxxxxxxx);
               }

               if (☃xxxxxxxxx.func_185901_i() != EnumBlockRenderType.INVISIBLE) {
                  BlockRenderLayer ☃xxxxxxxxx = ☃xxxxxxxxxx.func_180664_k();
                  int ☃xxxxxxxxxx = ☃xxxxxxxxx.ordinal();
                  BufferBuilder ☃xxxxxxxxxxx = ☃.func_178545_d().func_179039_a(☃xxxxxxxxxx);
                  if (!☃.func_178492_d(☃xxxxxxxxx)) {
                     ☃.func_178493_c(☃xxxxxxxxx);
                     this.func_178573_a(☃xxxxxxxxxxx, ☃xx);
                  }

                  ☃xxxxx[☃xxxxxxxxxx] |= ☃xxxxxxx.func_195475_a(☃xxxxxxxxx, ☃xxxxxxxx, var10, ☃xxxxxxxxxxx, ☃xxxxxx);
               }
            }

            for(BlockRenderLayer ☃xxxxxxxx : BlockRenderLayer.values()) {
               if (☃xxxxx[☃xxxxxxxx.ordinal()]) {
                  ☃.func_178486_a(☃xxxxxxxx);
               }

               if (☃.func_178492_d(☃xxxxxxxx)) {
                  this.func_178584_a(☃xxxxxxxx, ☃, ☃, ☃, ☃.func_178545_d().func_179038_a(☃xxxxxxxx), ☃);
               }
            }

            BlockModelRenderer.func_210266_a();
         }

         ☃.func_178488_a(var11.func_178607_a());
         this.field_178587_g.lock();

         try {
            Set<TileEntity> ☃xxxxx = Sets.<TileEntity>newHashSet(var12);
            Set<TileEntity> ☃xxxxxx = Sets.<TileEntity>newHashSet(this.field_181056_j);
            ☃xxxxx.removeAll(this.field_181056_j);
            ☃xxxxxx.removeAll(var12);
            this.field_181056_j.clear();
            this.field_181056_j.addAll(var12);
            this.field_178589_e.func_181023_a(☃xxxxxx, ☃xxxxx);
         } finally {
            this.field_178587_g.unlock();
         }
      }
   }

   protected void func_178578_b() {
      this.field_178587_g.lock();

      try {
         if (this.field_178599_i != null && this.field_178599_i.func_178546_a() != ChunkRenderTask.Status.DONE) {
            this.field_178599_i.func_178542_e();
            this.field_178599_i = null;
         }
      } finally {
         this.field_178587_g.unlock();
      }
   }

   public ReentrantLock func_178579_c() {
      return this.field_178587_g;
   }

   public ChunkRenderTask func_178574_d() {
      this.field_178587_g.lock();

      ChunkRenderTask var1;
      try {
         this.func_178578_b();
         this.field_178599_i = new ChunkRenderTask(this, ChunkRenderTask.Type.REBUILD_CHUNK, this.func_188280_f());
         var1 = this.field_178599_i;
      } finally {
         this.field_178587_g.unlock();
      }

      return var1;
   }

   @Nullable
   public ChunkRenderTask func_178582_e() {
      this.field_178587_g.lock();

      Object var1;
      try {
         if (this.field_178599_i == null || this.field_178599_i.func_178546_a() != ChunkRenderTask.Status.PENDING) {
            if (this.field_178599_i != null && this.field_178599_i.func_178546_a() != ChunkRenderTask.Status.DONE) {
               this.field_178599_i.func_178542_e();
               this.field_178599_i = null;
            }

            this.field_178599_i = new ChunkRenderTask(this, ChunkRenderTask.Type.RESORT_TRANSPARENCY, this.func_188280_f());
            this.field_178599_i.func_178543_a(this.field_178590_b);
            return this.field_178599_i;
         }

         var1 = null;
      } finally {
         this.field_178587_g.unlock();
      }

      return (ChunkRenderTask)var1;
   }

   protected double func_188280_f() {
      EntityPlayerSP ☃ = Minecraft.func_71410_x().field_71439_g;
      double ☃x = this.field_178591_c.field_72340_a + 8.0 - ☃.field_70165_t;
      double ☃xx = this.field_178591_c.field_72338_b + 8.0 - ☃.field_70163_u;
      double ☃xxx = this.field_178591_c.field_72339_c + 8.0 - ☃.field_70161_v;
      return ☃x * ☃x + ☃xx * ☃xx + ☃xxx * ☃xxx;
   }

   private void func_178573_a(BufferBuilder var1, BlockPos var2) {
      ☃.func_181668_a(7, DefaultVertexFormats.field_176600_a);
      ☃.func_178969_c((double)(-☃.func_177958_n()), (double)(-☃.func_177956_o()), (double)(-☃.func_177952_p()));
   }

   private void func_178584_a(BlockRenderLayer var1, float var2, float var3, float var4, BufferBuilder var5, CompiledChunk var6) {
      if (☃ == BlockRenderLayer.TRANSLUCENT && !☃.func_178491_b(☃)) {
         ☃.func_181674_a(☃, ☃, ☃);
         ☃.func_178494_a(☃.func_181672_a());
      }

      ☃.func_178977_d();
   }

   private void func_178567_n() {
      GlStateManager.func_179094_E();
      GlStateManager.func_179096_D();
      float ☃ = 1.000001F;
      GlStateManager.func_179109_b(-8.0F, -8.0F, -8.0F);
      GlStateManager.func_179152_a(1.000001F, 1.000001F, 1.000001F);
      GlStateManager.func_179109_b(8.0F, 8.0F, 8.0F);
      GlStateManager.func_179111_a(2982, this.field_178597_k);
      GlStateManager.func_179121_F();
   }

   public void func_178572_f() {
      GlStateManager.func_179110_a(this.field_178597_k);
   }

   public CompiledChunk func_178571_g() {
      return this.field_178590_b;
   }

   public void func_178580_a(CompiledChunk var1) {
      this.field_178598_h.lock();

      try {
         this.field_178590_b = ☃;
      } finally {
         this.field_178598_h.unlock();
      }
   }

   public void func_178585_h() {
      this.func_178578_b();
      this.field_178590_b = CompiledChunk.field_178502_a;
   }

   public void func_178566_a() {
      this.func_178585_h();
      this.field_178588_d = null;

      for(int ☃ = 0; ☃ < BlockRenderLayer.values().length; ++☃) {
         if (this.field_178594_l[☃] != null) {
            this.field_178594_l[☃].func_177362_c();
         }
      }
   }

   public BlockPos func_178568_j() {
      return this.field_178586_f;
   }

   public void func_178575_a(boolean var1) {
      if (this.field_178593_n) {
         ☃ |= this.field_188284_q;
      }

      this.field_178593_n = true;
      this.field_188284_q = ☃;
   }

   public void func_188282_m() {
      this.field_178593_n = false;
      this.field_188284_q = false;
   }

   public boolean func_178569_m() {
      return this.field_178593_n;
   }

   public boolean func_188281_o() {
      return this.field_178593_n && this.field_188284_q;
   }

   public BlockPos func_181701_a(EnumFacing var1) {
      return this.field_181702_p[☃.ordinal()];
   }

   public World func_188283_p() {
      return this.field_178588_d;
   }
}
