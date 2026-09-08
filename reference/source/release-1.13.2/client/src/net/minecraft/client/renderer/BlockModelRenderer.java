package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IWorldReader;

public class BlockModelRenderer {
   private final BlockColors field_187499_a;
   private static final ThreadLocal<Object2IntLinkedOpenHashMap<BlockPos>> field_210267_b = ThreadLocal.withInitial(() -> {
      Object2IntLinkedOpenHashMap<BlockPos> ☃ = new Object2IntLinkedOpenHashMap<BlockPos>(50) {
         @Override
         protected void rehash(int var1) {
         }
      };
      ☃.defaultReturnValue(Integer.MAX_VALUE);
      return ☃;
   });
   private static final ThreadLocal<Boolean> field_211848_c = ThreadLocal.withInitial(() -> false);

   public BlockModelRenderer(BlockColors var1) {
      this.field_187499_a = ☃;
   }

   public boolean func_199324_a(IWorldReader var1, IBakedModel var2, IBlockState var3, BlockPos var4, BufferBuilder var5, boolean var6, Random var7, long var8) {
      boolean ☃ = Minecraft.func_71379_u() && ☃.func_185906_d() == 0 && ☃.func_177555_b();

      try {
         return ☃ ? this.func_199326_b(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃) : this.func_199325_c(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      } catch (Throwable var14) {
         CrashReport ☃x = CrashReport.func_85055_a(var14, "Tesselating block model");
         CrashReportCategory ☃xx = ☃x.func_85058_a("Block model being tesselated");
         CrashReportCategory.func_175750_a(☃xx, ☃, ☃);
         ☃xx.func_71507_a("Using AO", ☃);
         throw new ReportedException(☃x);
      }
   }

   public boolean func_199326_b(IWorldReader var1, IBakedModel var2, IBlockState var3, BlockPos var4, BufferBuilder var5, boolean var6, Random var7, long var8) {
      boolean ☃ = false;
      float[] ☃x = new float[EnumFacing.values().length * 2];
      BitSet ☃xx = new BitSet(3);
      BlockModelRenderer.AmbientOcclusionFace ☃xxx = new BlockModelRenderer.AmbientOcclusionFace();

      for(EnumFacing ☃xxxx : EnumFacing.values()) {
         ☃.setSeed(☃);
         List<BakedQuad> ☃xxxxx = ☃.func_200117_a(☃, ☃xxxx, ☃);
         if (!☃xxxxx.isEmpty() && (!☃ || Block.func_176225_a(☃, ☃, ☃, ☃xxxx))) {
            this.func_187492_a(☃, ☃, ☃, ☃, ☃xxxxx, ☃x, ☃xx, ☃xxx);
            ☃ = true;
         }
      }

      ☃.setSeed(☃);
      List<BakedQuad> ☃xxxx = ☃.func_200117_a(☃, null, ☃);
      if (!☃xxxx.isEmpty()) {
         this.func_187492_a(☃, ☃, ☃, ☃, ☃xxxx, ☃x, ☃xx, ☃xxx);
         ☃ = true;
      }

      return ☃;
   }

   public boolean func_199325_c(IWorldReader var1, IBakedModel var2, IBlockState var3, BlockPos var4, BufferBuilder var5, boolean var6, Random var7, long var8) {
      boolean ☃ = false;
      BitSet ☃x = new BitSet(3);

      for(EnumFacing ☃xx : EnumFacing.values()) {
         ☃.setSeed(☃);
         List<BakedQuad> ☃xxx = ☃.func_200117_a(☃, ☃xx, ☃);
         if (!☃xxx.isEmpty() && (!☃ || Block.func_176225_a(☃, ☃, ☃, ☃xx))) {
            int ☃xxxx = ☃.func_185889_a(☃, ☃.func_177972_a(☃xx));
            this.func_187496_a(☃, ☃, ☃, ☃xxxx, false, ☃, ☃xxx, ☃x);
            ☃ = true;
         }
      }

      ☃.setSeed(☃);
      List<BakedQuad> ☃xx = ☃.func_200117_a(☃, null, ☃);
      if (!☃xx.isEmpty()) {
         this.func_187496_a(☃, ☃, ☃, -1, true, ☃, ☃xx, ☃x);
         ☃ = true;
      }

      return ☃;
   }

   private void func_187492_a(
      IWorldReader var1,
      IBlockState var2,
      BlockPos var3,
      BufferBuilder var4,
      List<BakedQuad> var5,
      float[] var6,
      BitSet var7,
      BlockModelRenderer.AmbientOcclusionFace var8
   ) {
      Vec3d ☃ = ☃.func_191059_e(☃, ☃);
      double ☃x = (double)☃.func_177958_n() + ☃.field_72450_a;
      double ☃xx = (double)☃.func_177956_o() + ☃.field_72448_b;
      double ☃xxx = (double)☃.func_177952_p() + ☃.field_72449_c;
      int ☃xxxx = 0;

      for(int ☃xxxxx = ☃.size(); ☃xxxx < ☃xxxxx; ++☃xxxx) {
         BakedQuad ☃xxxxxx = (BakedQuad)☃.get(☃xxxx);
         this.func_187494_a(☃, ☃xxxxxx.func_178209_a(), ☃xxxxxx.func_178210_d(), ☃, ☃);
         ☃.func_187491_a(☃, ☃, ☃, ☃xxxxxx.func_178210_d(), ☃, ☃);
         ☃.func_178981_a(☃xxxxxx.func_178209_a());
         ☃.func_178962_a(☃.field_178207_c[0], ☃.field_178207_c[1], ☃.field_178207_c[2], ☃.field_178207_c[3]);
         if (☃xxxxxx.func_178212_b()) {
            int ☃xxxxxxx = this.field_187499_a.func_186724_a(☃, ☃, ☃, ☃xxxxxx.func_178211_c());
            float ☃xxxxxxxx = (float)(☃xxxxxxx >> 16 & 0xFF) / 255.0F;
            float ☃xxxxxxxxx = (float)(☃xxxxxxx >> 8 & 0xFF) / 255.0F;
            float ☃xxxxxxxxxx = (float)(☃xxxxxxx & 0xFF) / 255.0F;
            ☃.func_178978_a(☃.field_178206_b[0] * ☃xxxxxxxx, ☃.field_178206_b[0] * ☃xxxxxxxxx, ☃.field_178206_b[0] * ☃xxxxxxxxxx, 4);
            ☃.func_178978_a(☃.field_178206_b[1] * ☃xxxxxxxx, ☃.field_178206_b[1] * ☃xxxxxxxxx, ☃.field_178206_b[1] * ☃xxxxxxxxxx, 3);
            ☃.func_178978_a(☃.field_178206_b[2] * ☃xxxxxxxx, ☃.field_178206_b[2] * ☃xxxxxxxxx, ☃.field_178206_b[2] * ☃xxxxxxxxxx, 2);
            ☃.func_178978_a(☃.field_178206_b[3] * ☃xxxxxxxx, ☃.field_178206_b[3] * ☃xxxxxxxxx, ☃.field_178206_b[3] * ☃xxxxxxxxxx, 1);
         } else {
            ☃.func_178978_a(☃.field_178206_b[0], ☃.field_178206_b[0], ☃.field_178206_b[0], 4);
            ☃.func_178978_a(☃.field_178206_b[1], ☃.field_178206_b[1], ☃.field_178206_b[1], 3);
            ☃.func_178978_a(☃.field_178206_b[2], ☃.field_178206_b[2], ☃.field_178206_b[2], 2);
            ☃.func_178978_a(☃.field_178206_b[3], ☃.field_178206_b[3], ☃.field_178206_b[3], 1);
         }

         ☃.func_178987_a(☃x, ☃xx, ☃xxx);
      }
   }

   private void func_187494_a(IBlockState var1, int[] var2, EnumFacing var3, @Nullable float[] var4, BitSet var5) {
      float ☃ = 32.0F;
      float ☃x = 32.0F;
      float ☃xx = 32.0F;
      float ☃xxx = -32.0F;
      float ☃xxxx = -32.0F;
      float ☃xxxxx = -32.0F;

      for(int ☃xxxxxx = 0; ☃xxxxxx < 4; ++☃xxxxxx) {
         float ☃xxxxxxx = Float.intBitsToFloat(☃[☃xxxxxx * 7]);
         float ☃xxxxxxxx = Float.intBitsToFloat(☃[☃xxxxxx * 7 + 1]);
         float ☃xxxxxxxxx = Float.intBitsToFloat(☃[☃xxxxxx * 7 + 2]);
         ☃ = Math.min(☃, ☃xxxxxxx);
         ☃x = Math.min(☃x, ☃xxxxxxxx);
         ☃xx = Math.min(☃xx, ☃xxxxxxxxx);
         ☃xxx = Math.max(☃xxx, ☃xxxxxxx);
         ☃xxxx = Math.max(☃xxxx, ☃xxxxxxxx);
         ☃xxxxx = Math.max(☃xxxxx, ☃xxxxxxxxx);
      }

      if (☃ != null) {
         ☃[EnumFacing.WEST.func_176745_a()] = ☃;
         ☃[EnumFacing.EAST.func_176745_a()] = ☃xxx;
         ☃[EnumFacing.DOWN.func_176745_a()] = ☃x;
         ☃[EnumFacing.UP.func_176745_a()] = ☃xxxx;
         ☃[EnumFacing.NORTH.func_176745_a()] = ☃xx;
         ☃[EnumFacing.SOUTH.func_176745_a()] = ☃xxxxx;
         int ☃xxxxxx = EnumFacing.values().length;
         ☃[EnumFacing.WEST.func_176745_a() + ☃xxxxxx] = 1.0F - ☃;
         ☃[EnumFacing.EAST.func_176745_a() + ☃xxxxxx] = 1.0F - ☃xxx;
         ☃[EnumFacing.DOWN.func_176745_a() + ☃xxxxxx] = 1.0F - ☃x;
         ☃[EnumFacing.UP.func_176745_a() + ☃xxxxxx] = 1.0F - ☃xxxx;
         ☃[EnumFacing.NORTH.func_176745_a() + ☃xxxxxx] = 1.0F - ☃xx;
         ☃[EnumFacing.SOUTH.func_176745_a() + ☃xxxxxx] = 1.0F - ☃xxxxx;
      }

      float ☃xxxxxx = 1.0E-4F;
      float ☃xxxxxxx = 0.9999F;
      switch(☃) {
         case DOWN:
            ☃.set(1, ☃ >= 1.0E-4F || ☃xx >= 1.0E-4F || ☃xxx <= 0.9999F || ☃xxxxx <= 0.9999F);
            ☃.set(0, (☃x < 1.0E-4F || ☃.func_185917_h()) && ☃x == ☃xxxx);
            break;
         case UP:
            ☃.set(1, ☃ >= 1.0E-4F || ☃xx >= 1.0E-4F || ☃xxx <= 0.9999F || ☃xxxxx <= 0.9999F);
            ☃.set(0, (☃xxxx > 0.9999F || ☃.func_185917_h()) && ☃x == ☃xxxx);
            break;
         case NORTH:
            ☃.set(1, ☃ >= 1.0E-4F || ☃x >= 1.0E-4F || ☃xxx <= 0.9999F || ☃xxxx <= 0.9999F);
            ☃.set(0, (☃xx < 1.0E-4F || ☃.func_185917_h()) && ☃xx == ☃xxxxx);
            break;
         case SOUTH:
            ☃.set(1, ☃ >= 1.0E-4F || ☃x >= 1.0E-4F || ☃xxx <= 0.9999F || ☃xxxx <= 0.9999F);
            ☃.set(0, (☃xxxxx > 0.9999F || ☃.func_185917_h()) && ☃xx == ☃xxxxx);
            break;
         case WEST:
            ☃.set(1, ☃x >= 1.0E-4F || ☃xx >= 1.0E-4F || ☃xxxx <= 0.9999F || ☃xxxxx <= 0.9999F);
            ☃.set(0, (☃ < 1.0E-4F || ☃.func_185917_h()) && ☃ == ☃xxx);
            break;
         case EAST:
            ☃.set(1, ☃x >= 1.0E-4F || ☃xx >= 1.0E-4F || ☃xxxx <= 0.9999F || ☃xxxxx <= 0.9999F);
            ☃.set(0, (☃xxx > 0.9999F || ☃.func_185917_h()) && ☃ == ☃xxx);
      }
   }

   private void func_187496_a(IWorldReader var1, IBlockState var2, BlockPos var3, int var4, boolean var5, BufferBuilder var6, List<BakedQuad> var7, BitSet var8) {
      Vec3d ☃ = ☃.func_191059_e(☃, ☃);
      double ☃x = (double)☃.func_177958_n() + ☃.field_72450_a;
      double ☃xx = (double)☃.func_177956_o() + ☃.field_72448_b;
      double ☃xxx = (double)☃.func_177952_p() + ☃.field_72449_c;
      int ☃xxxx = 0;

      for(int ☃xxxxx = ☃.size(); ☃xxxx < ☃xxxxx; ++☃xxxx) {
         BakedQuad ☃xxxxxx = (BakedQuad)☃.get(☃xxxx);
         if (☃) {
            this.func_187494_a(☃, ☃xxxxxx.func_178209_a(), ☃xxxxxx.func_178210_d(), null, ☃);
            BlockPos ☃xxxxxxx = ☃.get(0) ? ☃.func_177972_a(☃xxxxxx.func_178210_d()) : ☃;
            ☃ = ☃.func_185889_a(☃, ☃xxxxxxx);
         }

         ☃.func_178981_a(☃xxxxxx.func_178209_a());
         ☃.func_178962_a(☃, ☃, ☃, ☃);
         if (☃xxxxxx.func_178212_b()) {
            int ☃xxxxxx = this.field_187499_a.func_186724_a(☃, ☃, ☃, ☃xxxxxx.func_178211_c());
            float ☃xxxxxxx = (float)(☃xxxxxx >> 16 & 0xFF) / 255.0F;
            float ☃xxxxxxxx = (float)(☃xxxxxx >> 8 & 0xFF) / 255.0F;
            float ☃xxxxxxxxx = (float)(☃xxxxxx & 0xFF) / 255.0F;
            ☃.func_178978_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 4);
            ☃.func_178978_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 3);
            ☃.func_178978_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 2);
            ☃.func_178978_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 1);
         }

         ☃.func_178987_a(☃x, ☃xx, ☃xxx);
      }
   }

   public void func_178262_a(IBakedModel var1, float var2, float var3, float var4, float var5) {
      this.func_187495_a(null, ☃, ☃, ☃, ☃, ☃);
   }

   public void func_187495_a(@Nullable IBlockState var1, IBakedModel var2, float var3, float var4, float var5, float var6) {
      Random ☃ = new Random();
      long ☃x = 42L;

      for(EnumFacing ☃xx : EnumFacing.values()) {
         ☃.setSeed(42L);
         this.func_178264_a(☃, ☃, ☃, ☃, ☃.func_200117_a(☃, ☃xx, ☃));
      }

      ☃.setSeed(42L);
      this.func_178264_a(☃, ☃, ☃, ☃, ☃.func_200117_a(☃, null, ☃));
   }

   public void func_178266_a(IBakedModel var1, IBlockState var2, float var3, boolean var4) {
      GlStateManager.func_179114_b(90.0F, 0.0F, 1.0F, 0.0F);
      int ☃ = this.field_187499_a.func_186724_a(☃, null, null, 0);
      float ☃x = (float)(☃ >> 16 & 0xFF) / 255.0F;
      float ☃xx = (float)(☃ >> 8 & 0xFF) / 255.0F;
      float ☃xxx = (float)(☃ & 0xFF) / 255.0F;
      if (!☃) {
         GlStateManager.func_179131_c(☃, ☃, ☃, 1.0F);
      }

      this.func_187495_a(☃, ☃, ☃, ☃x, ☃xx, ☃xxx);
   }

   private void func_178264_a(float var1, float var2, float var3, float var4, List<BakedQuad> var5) {
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      int ☃xx = 0;

      for(int ☃xxx = ☃.size(); ☃xx < ☃xxx; ++☃xx) {
         BakedQuad ☃xxxx = (BakedQuad)☃.get(☃xx);
         ☃x.func_181668_a(7, DefaultVertexFormats.field_176599_b);
         ☃x.func_178981_a(☃xxxx.func_178209_a());
         if (☃xxxx.func_178212_b()) {
            ☃x.func_178990_f(☃ * ☃, ☃ * ☃, ☃ * ☃);
         } else {
            ☃x.func_178990_f(☃, ☃, ☃);
         }

         Vec3i ☃xxxx = ☃xxxx.func_178210_d().func_176730_m();
         ☃x.func_178975_e((float)☃xxxx.func_177958_n(), (float)☃xxxx.func_177956_o(), (float)☃xxxx.func_177952_p());
         ☃.func_78381_a();
      }
   }

   public static void func_211847_a() {
      field_211848_c.set(true);
   }

   public static void func_210266_a() {
      ((Object2IntLinkedOpenHashMap)field_210267_b.get()).clear();
      field_211848_c.set(false);
   }

   private static int func_210264_b(IBlockState var0, IWorldReader var1, BlockPos var2) {
      Boolean ☃ = (Boolean)field_211848_c.get();
      Object2IntLinkedOpenHashMap<BlockPos> ☃x = null;
      if (☃) {
         ☃x = (Object2IntLinkedOpenHashMap)field_210267_b.get();
         int ☃xx = ☃x.getInt(☃);
         if (☃xx != Integer.MAX_VALUE) {
            return ☃xx;
         }
      }

      int ☃ = ☃.func_185889_a(☃, ☃);
      if (☃x != null) {
         if (☃x.size() == 50) {
            ☃x.removeFirstInt();
         }

         ☃x.put(☃.func_185334_h(), ☃);
      }

      return ☃;
   }

   class AmbientOcclusionFace {
      private final float[] field_178206_b = new float[4];
      private final int[] field_178207_c = new int[4];

      public AmbientOcclusionFace() {
      }

      public void func_187491_a(IWorldReader var1, IBlockState var2, BlockPos var3, EnumFacing var4, float[] var5, BitSet var6) {
         BlockPos ☃xx = ☃.get(0) ? ☃.func_177972_a(☃) : ☃;
         BlockModelRenderer.EnumNeighborInfo ☃xxx = BlockModelRenderer.EnumNeighborInfo.func_178273_a(☃);
         BlockPos.MutableBlockPos ☃xxxx = new BlockPos.MutableBlockPos();
         ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[0]);
         int ☃xxxxx = BlockModelRenderer.func_210264_b(☃, ☃, ☃xxxx);
         float ☃xxxxxx = ☃.func_180495_p(☃xxxx).func_185892_j();
         ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[1]);
         int ☃xxxxxxx = BlockModelRenderer.func_210264_b(☃, ☃, ☃xxxx);
         float ☃xxxxxxxx = ☃.func_180495_p(☃xxxx).func_185892_j();
         ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[2]);
         int ☃xxxxxxxxx = BlockModelRenderer.func_210264_b(☃, ☃, ☃xxxx);
         float ☃xxxxxxxxxx = ☃.func_180495_p(☃xxxx).func_185892_j();
         ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[3]);
         int ☃xxxxxxxxxxx = BlockModelRenderer.func_210264_b(☃, ☃, ☃xxxx);
         float ☃xxxxxxxxxxxx = ☃.func_180495_p(☃xxxx).func_185892_j();
         ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[0]).func_189536_c(☃);
         boolean ☃xxxxxxxxxxxxx = ☃.func_180495_p(☃xxxx).func_200016_a(☃, ☃xxxx) == 0;
         ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[1]).func_189536_c(☃);
         boolean ☃xxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxx).func_200016_a(☃, ☃xxxx) == 0;
         ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[2]).func_189536_c(☃);
         boolean ☃xxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxx).func_200016_a(☃, ☃xxxx) == 0;
         ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[3]).func_189536_c(☃);
         boolean ☃xxxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxx).func_200016_a(☃, ☃xxxx) == 0;
         float ☃;
         int ☃x;
         if (!☃xxxxxxxxxxxxxxx && !☃xxxxxxxxxxxxx) {
            ☃ = ☃xxxxxx;
            ☃x = ☃xxxxx;
         } else {
            ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[0]).func_189536_c(☃xxx.field_178276_g[2]);
            ☃ = ☃.func_180495_p(☃xxxx).func_185892_j();
            ☃x = BlockModelRenderer.func_210264_b(☃, ☃, ☃xxxx);
         }

         float ☃;
         int ☃x;
         if (!☃xxxxxxxxxxxxxxxx && !☃xxxxxxxxxxxxx) {
            ☃ = ☃xxxxxx;
            ☃x = ☃xxxxx;
         } else {
            ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[0]).func_189536_c(☃xxx.field_178276_g[3]);
            ☃ = ☃.func_180495_p(☃xxxx).func_185892_j();
            ☃x = BlockModelRenderer.func_210264_b(☃, ☃, ☃xxxx);
         }

         float ☃;
         int ☃x;
         if (!☃xxxxxxxxxxxxxxx && !☃xxxxxxxxxxxxxx) {
            ☃ = ☃xxxxxxxx;
            ☃x = ☃xxxxxxx;
         } else {
            ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[1]).func_189536_c(☃xxx.field_178276_g[2]);
            ☃ = ☃.func_180495_p(☃xxxx).func_185892_j();
            ☃x = BlockModelRenderer.func_210264_b(☃, ☃, ☃xxxx);
         }

         float ☃;
         int ☃x;
         if (!☃xxxxxxxxxxxxxxxx && !☃xxxxxxxxxxxxxx) {
            ☃ = ☃xxxxxxxx;
            ☃x = ☃xxxxxxx;
         } else {
            ☃xxxx.func_189533_g(☃xx).func_189536_c(☃xxx.field_178276_g[1]).func_189536_c(☃xxx.field_178276_g[3]);
            ☃ = ☃.func_180495_p(☃xxxx).func_185892_j();
            ☃x = BlockModelRenderer.func_210264_b(☃, ☃, ☃xxxx);
         }

         int ☃ = BlockModelRenderer.func_210264_b(☃, ☃, ☃);
         ☃xxxx.func_189533_g(☃).func_189536_c(☃);
         if (☃.get(0) || !☃.func_180495_p(☃xxxx).func_200015_d(☃, ☃xxxx)) {
            ☃ = BlockModelRenderer.func_210264_b(☃, ☃, ☃xxxx);
         }

         float ☃ = ☃.get(0) ? ☃.func_180495_p(☃xx).func_185892_j() : ☃.func_180495_p(☃).func_185892_j();
         BlockModelRenderer.VertexTranslations ☃x = BlockModelRenderer.VertexTranslations.func_178184_a(☃);
         if (☃.get(1) && ☃xxx.field_178289_i) {
            float ☃xx = (☃xxxxxxxxxxxx + ☃xxxxxx + ☃ + ☃) * 0.25F;
            float ☃xxx = (☃xxxxxxxxxx + ☃xxxxxx + ☃ + ☃) * 0.25F;
            float ☃xxxx = (☃xxxxxxxxxx + ☃xxxxxxxx + ☃ + ☃) * 0.25F;
            float ☃xxxxx = (☃xxxxxxxxxxxx + ☃xxxxxxxx + ☃ + ☃) * 0.25F;
            float ☃xxxxxx = ☃[☃xxx.field_178286_j[0].field_178229_m] * ☃[☃xxx.field_178286_j[1].field_178229_m];
            float ☃xxxxxxx = ☃[☃xxx.field_178286_j[2].field_178229_m] * ☃[☃xxx.field_178286_j[3].field_178229_m];
            float ☃xxxxxxxx = ☃[☃xxx.field_178286_j[4].field_178229_m] * ☃[☃xxx.field_178286_j[5].field_178229_m];
            float ☃xxxxxxxxx = ☃[☃xxx.field_178286_j[6].field_178229_m] * ☃[☃xxx.field_178286_j[7].field_178229_m];
            float ☃xxxxxxxxxx = ☃[☃xxx.field_178287_k[0].field_178229_m] * ☃[☃xxx.field_178287_k[1].field_178229_m];
            float ☃xxxxxxxxxxx = ☃[☃xxx.field_178287_k[2].field_178229_m] * ☃[☃xxx.field_178287_k[3].field_178229_m];
            float ☃xxxxxxxxxxxx = ☃[☃xxx.field_178287_k[4].field_178229_m] * ☃[☃xxx.field_178287_k[5].field_178229_m];
            float ☃xxxxxxxxxxxxx = ☃[☃xxx.field_178287_k[6].field_178229_m] * ☃[☃xxx.field_178287_k[7].field_178229_m];
            float ☃xxxxxxxxxxxxxx = ☃[☃xxx.field_178284_l[0].field_178229_m] * ☃[☃xxx.field_178284_l[1].field_178229_m];
            float ☃xxxxxxxxxxxxxxx = ☃[☃xxx.field_178284_l[2].field_178229_m] * ☃[☃xxx.field_178284_l[3].field_178229_m];
            float ☃xxxxxxxxxxxxxxxx = ☃[☃xxx.field_178284_l[4].field_178229_m] * ☃[☃xxx.field_178284_l[5].field_178229_m];
            float ☃xxxxxxxxxxxxxxxxx = ☃[☃xxx.field_178284_l[6].field_178229_m] * ☃[☃xxx.field_178284_l[7].field_178229_m];
            float ☃xxxxxxxxxxxxxxxxxx = ☃[☃xxx.field_178285_m[0].field_178229_m] * ☃[☃xxx.field_178285_m[1].field_178229_m];
            float ☃xxxxxxxxxxxxxxxxxxx = ☃[☃xxx.field_178285_m[2].field_178229_m] * ☃[☃xxx.field_178285_m[3].field_178229_m];
            float ☃xxxxxxxxxxxxxxxxxxxx = ☃[☃xxx.field_178285_m[4].field_178229_m] * ☃[☃xxx.field_178285_m[5].field_178229_m];
            float ☃xxxxxxxxxxxxxxxxxxxxx = ☃[☃xxx.field_178285_m[6].field_178229_m] * ☃[☃xxx.field_178285_m[7].field_178229_m];
            this.field_178206_b[☃x.field_178191_g] = ☃xx * ☃xxxxxx + ☃xxx * ☃xxxxxxx + ☃xxxx * ☃xxxxxxxx + ☃xxxxx * ☃xxxxxxxxx;
            this.field_178206_b[☃x.field_178200_h] = ☃xx * ☃xxxxxxxxxx + ☃xxx * ☃xxxxxxxxxxx + ☃xxxx * ☃xxxxxxxxxxxx + ☃xxxxx * ☃xxxxxxxxxxxxx;
            this.field_178206_b[☃x.field_178201_i] = ☃xx * ☃xxxxxxxxxxxxxx + ☃xxx * ☃xxxxxxxxxxxxxxx + ☃xxxx * ☃xxxxxxxxxxxxxxxx + ☃xxxxx * ☃xxxxxxxxxxxxxxxxx;
            this.field_178206_b[☃x.field_178198_j] = ☃xx * ☃xxxxxxxxxxxxxxxxxx
               + ☃xxx * ☃xxxxxxxxxxxxxxxxxxx
               + ☃xxxx * ☃xxxxxxxxxxxxxxxxxxxx
               + ☃xxxxx * ☃xxxxxxxxxxxxxxxxxxxxx;
            int ☃xxxxxxxxxxxxxxxxxxxxxx = this.func_147778_a(☃xxxxxxxxxxx, ☃xxxxx, ☃x, ☃);
            int ☃xxxxxxxxxxxxxxxxxxxxxxx = this.func_147778_a(☃xxxxxxxxx, ☃xxxxx, ☃x, ☃);
            int ☃xxxxxxxxxxxxxxxxxxxxxxxx = this.func_147778_a(☃xxxxxxxxx, ☃xxxxxxx, ☃x, ☃);
            int ☃xxxxxxxxxxxxxxxxxxxxxxxxx = this.func_147778_a(☃xxxxxxxxxxx, ☃xxxxxxx, ☃x, ☃);
            this.field_178207_c[☃x.field_178191_g] = this.func_178203_a(
               ☃xxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxx,
               ☃xxxxxxx,
               ☃xxxxxxxx,
               ☃xxxxxxxxx
            );
            this.field_178207_c[☃x.field_178200_h] = this.func_178203_a(
               ☃xxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxx,
               ☃xxxxxxxxxxx,
               ☃xxxxxxxxxxxx,
               ☃xxxxxxxxxxxxx
            );
            this.field_178207_c[☃x.field_178201_i] = this.func_178203_a(
               ☃xxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxx
            );
            this.field_178207_c[☃x.field_178198_j] = this.func_178203_a(
               ☃xxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxx,
               ☃xxxxxxxxxxxxxxxxxxxxx
            );
         } else {
            float ☃ = (☃xxxxxxxxxxxx + ☃xxxxxx + ☃ + ☃) * 0.25F;
            float ☃x = (☃xxxxxxxxxx + ☃xxxxxx + ☃ + ☃) * 0.25F;
            float ☃xx = (☃xxxxxxxxxx + ☃xxxxxxxx + ☃ + ☃) * 0.25F;
            float ☃xxx = (☃xxxxxxxxxxxx + ☃xxxxxxxx + ☃ + ☃) * 0.25F;
            this.field_178207_c[☃x.field_178191_g] = this.func_147778_a(☃xxxxxxxxxxx, ☃xxxxx, ☃x, ☃);
            this.field_178207_c[☃x.field_178200_h] = this.func_147778_a(☃xxxxxxxxx, ☃xxxxx, ☃x, ☃);
            this.field_178207_c[☃x.field_178201_i] = this.func_147778_a(☃xxxxxxxxx, ☃xxxxxxx, ☃x, ☃);
            this.field_178207_c[☃x.field_178198_j] = this.func_147778_a(☃xxxxxxxxxxx, ☃xxxxxxx, ☃x, ☃);
            this.field_178206_b[☃x.field_178191_g] = ☃;
            this.field_178206_b[☃x.field_178200_h] = ☃x;
            this.field_178206_b[☃x.field_178201_i] = ☃xx;
            this.field_178206_b[☃x.field_178198_j] = ☃xxx;
         }
      }

      private int func_147778_a(int var1, int var2, int var3, int var4) {
         if (☃ == 0) {
            ☃ = ☃;
         }

         if (☃ == 0) {
            ☃ = ☃;
         }

         if (☃ == 0) {
            ☃ = ☃;
         }

         return ☃ + ☃ + ☃ + ☃ >> 2 & 16711935;
      }

      private int func_178203_a(int var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8) {
         int ☃ = (int)((float)(☃ >> 16 & 0xFF) * ☃ + (float)(☃ >> 16 & 0xFF) * ☃ + (float)(☃ >> 16 & 0xFF) * ☃ + (float)(☃ >> 16 & 0xFF) * ☃) & 0xFF;
         int ☃x = (int)((float)(☃ & 0xFF) * ☃ + (float)(☃ & 0xFF) * ☃ + (float)(☃ & 0xFF) * ☃ + (float)(☃ & 0xFF) * ☃) & 0xFF;
         return ☃ << 16 | ☃x;
      }
   }

   public static enum EnumNeighborInfo {
      DOWN(
         new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH},
         0.5F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.SOUTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.SOUTH
         }
      ),
      UP(
         new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH},
         1.0F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.SOUTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.SOUTH
         }
      ),
      NORTH(
         new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST},
         0.8F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_WEST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_EAST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_EAST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_WEST
         }
      ),
      SOUTH(
         new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP},
         0.8F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.WEST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.WEST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.EAST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.EAST
         }
      ),
      WEST(
         new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH},
         0.6F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.SOUTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.SOUTH
         }
      ),
      EAST(
         new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH},
         0.6F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.SOUTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.SOUTH
         }
      );

      private final EnumFacing[] field_178276_g;
      private final boolean field_178289_i;
      private final BlockModelRenderer.Orientation[] field_178286_j;
      private final BlockModelRenderer.Orientation[] field_178287_k;
      private final BlockModelRenderer.Orientation[] field_178284_l;
      private final BlockModelRenderer.Orientation[] field_178285_m;
      private static final BlockModelRenderer.EnumNeighborInfo[] field_178282_n = Util.func_200696_a(new BlockModelRenderer.EnumNeighborInfo[6], var0 -> {
         var0[EnumFacing.DOWN.func_176745_a()] = DOWN;
         var0[EnumFacing.UP.func_176745_a()] = UP;
         var0[EnumFacing.NORTH.func_176745_a()] = NORTH;
         var0[EnumFacing.SOUTH.func_176745_a()] = SOUTH;
         var0[EnumFacing.WEST.func_176745_a()] = WEST;
         var0[EnumFacing.EAST.func_176745_a()] = EAST;
      });

      private EnumNeighborInfo(
         EnumFacing[] var3,
         float var4,
         boolean var5,
         BlockModelRenderer.Orientation[] var6,
         BlockModelRenderer.Orientation[] var7,
         BlockModelRenderer.Orientation[] var8,
         BlockModelRenderer.Orientation[] var9
      ) {
         this.field_178276_g = ☃;
         this.field_178289_i = ☃;
         this.field_178286_j = ☃;
         this.field_178287_k = ☃;
         this.field_178284_l = ☃;
         this.field_178285_m = ☃;
      }

      public static BlockModelRenderer.EnumNeighborInfo func_178273_a(EnumFacing var0) {
         return field_178282_n[☃.func_176745_a()];
      }
   }

   public static enum Orientation {
      DOWN(EnumFacing.DOWN, false),
      UP(EnumFacing.UP, false),
      NORTH(EnumFacing.NORTH, false),
      SOUTH(EnumFacing.SOUTH, false),
      WEST(EnumFacing.WEST, false),
      EAST(EnumFacing.EAST, false),
      FLIP_DOWN(EnumFacing.DOWN, true),
      FLIP_UP(EnumFacing.UP, true),
      FLIP_NORTH(EnumFacing.NORTH, true),
      FLIP_SOUTH(EnumFacing.SOUTH, true),
      FLIP_WEST(EnumFacing.WEST, true),
      FLIP_EAST(EnumFacing.EAST, true);

      private final int field_178229_m;

      private Orientation(EnumFacing var3, boolean var4) {
         this.field_178229_m = ☃.func_176745_a() + (☃ ? EnumFacing.values().length : 0);
      }
   }

   static enum VertexTranslations {
      DOWN(0, 1, 2, 3),
      UP(2, 3, 0, 1),
      NORTH(3, 0, 1, 2),
      SOUTH(0, 1, 2, 3),
      WEST(3, 0, 1, 2),
      EAST(1, 2, 3, 0);

      private final int field_178191_g;
      private final int field_178200_h;
      private final int field_178201_i;
      private final int field_178198_j;
      private static final BlockModelRenderer.VertexTranslations[] field_178199_k = Util.func_200696_a(new BlockModelRenderer.VertexTranslations[6], var0 -> {
         var0[EnumFacing.DOWN.func_176745_a()] = DOWN;
         var0[EnumFacing.UP.func_176745_a()] = UP;
         var0[EnumFacing.NORTH.func_176745_a()] = NORTH;
         var0[EnumFacing.SOUTH.func_176745_a()] = SOUTH;
         var0[EnumFacing.WEST.func_176745_a()] = WEST;
         var0[EnumFacing.EAST.func_176745_a()] = EAST;
      });

      private VertexTranslations(int var3, int var4, int var5, int var6) {
         this.field_178191_g = ☃;
         this.field_178200_h = ☃;
         this.field_178201_i = ☃;
         this.field_178198_j = ☃;
      }

      public static BlockModelRenderer.VertexTranslations func_178184_a(EnumFacing var0) {
         return field_178199_k[☃.func_176745_a()];
      }
   }
}
