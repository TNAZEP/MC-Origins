package net.minecraft.client.renderer.chunk;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.gen.Heightmap;

public class RenderChunkCache implements IWorldReader {
   protected final int field_212400_a;
   protected final int field_212401_b;
   protected final BlockPos field_212402_c;
   protected final int field_212403_d;
   protected final int field_212404_e;
   protected final int field_212405_f;
   protected final Chunk[][] field_212406_g;
   protected final RenderChunkCache.Entry[] field_212407_h;
   protected final World field_212408_i;

   @Nullable
   public static RenderChunkCache func_212397_a(World var0, BlockPos var1, BlockPos var2, int var3) {
      int ☃ = ☃.func_177958_n() - ☃ >> 4;
      int ☃x = ☃.func_177952_p() - ☃ >> 4;
      int ☃xx = ☃.func_177958_n() + ☃ >> 4;
      int ☃xxx = ☃.func_177952_p() + ☃ >> 4;
      Chunk[][] ☃xxxx = new Chunk[☃xx - ☃ + 1][☃xxx - ☃x + 1];

      for(int ☃xxxxx = ☃; ☃xxxxx <= ☃xx; ++☃xxxxx) {
         for(int ☃xxxxxx = ☃x; ☃xxxxxx <= ☃xxx; ++☃xxxxxx) {
            ☃xxxx[☃xxxxx - ☃][☃xxxxxx - ☃x] = ☃.func_72964_e(☃xxxxx, ☃xxxxxx);
         }
      }

      boolean ☃xxxxx = true;

      for(int ☃xxxxxx = ☃.func_177958_n() >> 4; ☃xxxxxx <= ☃.func_177958_n() >> 4; ++☃xxxxxx) {
         for(int ☃xxxxxxx = ☃.func_177952_p() >> 4; ☃xxxxxxx <= ☃.func_177952_p() >> 4; ++☃xxxxxxx) {
            Chunk ☃xxxxxxxx = ☃xxxx[☃xxxxxx - ☃][☃xxxxxxx - ☃x];
            if (!☃xxxxxxxx.func_76606_c(☃.func_177956_o(), ☃.func_177956_o())) {
               ☃xxxxx = false;
            }
         }
      }

      if (☃xxxxx) {
         return null;
      } else {
         int ☃xxxxxx = 1;
         BlockPos ☃xxxxxxx = ☃.func_177982_a(-1, -1, -1);
         BlockPos ☃xxxxxxxx = ☃.func_177982_a(1, 1, 1);
         return new RenderChunkCache(☃, ☃, ☃x, ☃xxxx, ☃xxxxxxx, ☃xxxxxxxx);
      }
   }

   public RenderChunkCache(World var1, int var2, int var3, Chunk[][] var4, BlockPos var5, BlockPos var6) {
      this.field_212408_i = ☃;
      this.field_212400_a = ☃;
      this.field_212401_b = ☃;
      this.field_212406_g = ☃;
      this.field_212402_c = ☃;
      this.field_212403_d = ☃.func_177958_n() - ☃.func_177958_n() + 1;
      this.field_212404_e = ☃.func_177956_o() - ☃.func_177956_o() + 1;
      this.field_212405_f = ☃.func_177952_p() - ☃.func_177952_p() + 1;
      this.field_212407_h = new RenderChunkCache.Entry[this.field_212403_d * this.field_212404_e * this.field_212405_f];

      for(BlockPos.MutableBlockPos ☃ : BlockPos.func_177975_b(☃, ☃)) {
         this.field_212407_h[this.func_212398_a(☃)] = new RenderChunkCache.Entry(☃, ☃);
      }
   }

   protected int func_212398_a(BlockPos var1) {
      int ☃ = ☃.func_177958_n() - this.field_212402_c.func_177958_n();
      int ☃x = ☃.func_177956_o() - this.field_212402_c.func_177956_o();
      int ☃xx = ☃.func_177952_p() - this.field_212402_c.func_177952_p();
      return ☃xx * this.field_212403_d * this.field_212404_e + ☃x * this.field_212403_d + ☃;
   }

   @Override
   public IBlockState func_180495_p(BlockPos var1) {
      return this.field_212407_h[this.func_212398_a(☃)].field_212495_a;
   }

   @Override
   public IFluidState func_204610_c(BlockPos var1) {
      return this.field_212407_h[this.func_212398_a(☃)].field_212496_b;
   }

   @Override
   public Biome func_180494_b(BlockPos var1) {
      int ☃ = (☃.func_177958_n() >> 4) - this.field_212400_a;
      int ☃x = (☃.func_177952_p() >> 4) - this.field_212401_b;
      return this.field_212406_g[☃][☃x].func_201600_k(☃);
   }

   private int func_212396_b(EnumLightType var1, BlockPos var2) {
      return this.field_212407_h[this.func_212398_a(☃)].func_212493_a(☃, ☃);
   }

   @Override
   public int func_175626_b(BlockPos var1, int var2) {
      int ☃ = this.func_212396_b(EnumLightType.SKY, ☃);
      int ☃x = this.func_212396_b(EnumLightType.BLOCK, ☃);
      if (☃x < ☃) {
         ☃x = ☃;
      }

      return ☃ << 20 | ☃x << 4;
   }

   @Nullable
   @Override
   public TileEntity func_175625_s(BlockPos var1) {
      return this.func_212399_a(☃, Chunk.EnumCreateEntityType.IMMEDIATE);
   }

   @Nullable
   public TileEntity func_212399_a(BlockPos var1, Chunk.EnumCreateEntityType var2) {
      int ☃ = (☃.func_177958_n() >> 4) - this.field_212400_a;
      int ☃x = (☃.func_177952_p() >> 4) - this.field_212401_b;
      return this.field_212406_g[☃][☃x].func_177424_a(☃, ☃);
   }

   @Override
   public float func_205052_D(BlockPos var1) {
      return this.field_212408_i.field_73011_w.func_177497_p()[this.func_201696_r(☃)];
   }

   @Override
   public int func_205049_d(BlockPos var1, int var2) {
      if (this.func_180495_p(☃).func_200130_c(this, ☃)) {
         int ☃ = 0;

         for(EnumFacing ☃x : EnumFacing.values()) {
            int ☃xx = this.func_201669_a(☃.func_177972_a(☃x), ☃);
            if (☃xx > ☃) {
               ☃ = ☃xx;
            }

            if (☃ >= 15) {
               return ☃;
            }
         }

         return ☃;
      } else {
         return this.func_201669_a(☃, ☃);
      }
   }

   @Override
   public Dimension func_201675_m() {
      return this.field_212408_i.func_201675_m();
   }

   @Override
   public int func_201669_a(BlockPos var1, int var2) {
      if (☃.func_177958_n() < -30000000 || ☃.func_177952_p() < -30000000 || ☃.func_177958_n() >= 30000000 || ☃.func_177952_p() > 30000000) {
         return 15;
      } else if (☃.func_177956_o() < 0) {
         return 0;
      } else if (☃.func_177956_o() >= 256) {
         int ☃ = 15 - ☃;
         if (☃ < 0) {
            ☃ = 0;
         }

         return ☃;
      } else {
         int ☃ = (☃.func_177958_n() >> 4) - this.field_212400_a;
         int ☃x = (☃.func_177952_p() >> 4) - this.field_212401_b;
         return this.field_212406_g[☃][☃x].func_177443_a(☃, ☃);
      }
   }

   @Override
   public boolean func_175680_a(int var1, int var2, boolean var3) {
      return this.func_212395_a(☃, ☃);
   }

   @Override
   public boolean func_175678_i(BlockPos var1) {
      return false;
   }

   public boolean func_212395_a(int var1, int var2) {
      int ☃ = ☃ - this.field_212400_a;
      int ☃x = ☃ - this.field_212401_b;
      return ☃ >= 0 && ☃ < this.field_212406_g.length && ☃x >= 0 && ☃x < this.field_212406_g[☃].length;
   }

   @Override
   public int func_201676_a(Heightmap.Type var1, int var2, int var3) {
      throw new RuntimeException("NOT IMPLEMENTED!");
   }

   @Override
   public WorldBorder func_175723_af() {
      return this.field_212408_i.func_175723_af();
   }

   @Override
   public boolean func_195585_a(@Nullable Entity var1, VoxelShape var2) {
      throw new RuntimeException("This method should never be called here. No entity logic inside Region");
   }

   @Nullable
   @Override
   public EntityPlayer func_190525_a(double var1, double var3, double var5, double var7, Predicate<Entity> var9) {
      throw new RuntimeException("This method should never be called here. No entity logic inside Region");
   }

   @Override
   public int func_175657_ab() {
      return 0;
   }

   @Override
   public boolean func_175623_d(BlockPos var1) {
      return this.func_180495_p(☃).func_196958_f();
   }

   @Override
   public int func_175642_b(EnumLightType var1, BlockPos var2) {
      if (☃.func_177956_o() >= 0 && ☃.func_177956_o() < 256) {
         int ☃ = (☃.func_177958_n() >> 4) - this.field_212400_a;
         int ☃x = (☃.func_177952_p() >> 4) - this.field_212401_b;
         return this.field_212406_g[☃][☃x].func_177413_a(☃, ☃);
      } else {
         return ☃.field_77198_c;
      }
   }

   @Override
   public int func_175627_a(BlockPos var1, EnumFacing var2) {
      return this.func_180495_p(☃).func_185893_b(this, ☃, ☃);
   }

   @Override
   public boolean func_201670_d() {
      throw new RuntimeException("Not yet implemented");
   }

   @Override
   public int func_181545_F() {
      throw new RuntimeException("Not yet implemented");
   }

   public class Entry {
      protected final IBlockState field_212495_a;
      protected final IFluidState field_212496_b;
      private int[] field_212498_d;

      protected Entry(World var2, BlockPos var3) {
         this.field_212495_a = ☃.func_180495_p(☃);
         this.field_212496_b = ☃.func_204610_c(☃);
      }

      protected int func_212493_a(EnumLightType var1, BlockPos var2) {
         if (this.field_212498_d == null) {
            this.func_212492_a(☃);
         }

         return this.field_212498_d[☃.ordinal()];
      }

      private void func_212492_a(BlockPos var1) {
         this.field_212498_d = new int[EnumLightType.values().length];

         for(EnumLightType ☃ : EnumLightType.values()) {
            this.field_212498_d[☃.ordinal()] = this.func_212494_b(☃, ☃);
         }
      }

      private int func_212494_b(EnumLightType var1, BlockPos var2) {
         if (☃ == EnumLightType.SKY && !RenderChunkCache.this.field_212408_i.func_201675_m().func_191066_m()) {
            return 0;
         } else if (☃.func_177956_o() >= 0 && ☃.func_177956_o() < 256) {
            if (this.field_212495_a.func_200130_c(RenderChunkCache.this, ☃)) {
               int ☃ = 0;

               for(EnumFacing ☃x : EnumFacing.values()) {
                  int ☃xx = RenderChunkCache.this.func_175642_b(☃, ☃.func_177972_a(☃x));
                  if (☃xx > ☃) {
                     ☃ = ☃xx;
                  }

                  if (☃ >= 15) {
                     return ☃;
                  }
               }

               return ☃;
            } else {
               int ☃ = (☃.func_177958_n() >> 4) - RenderChunkCache.this.field_212400_a;
               int ☃x = (☃.func_177952_p() >> 4) - RenderChunkCache.this.field_212401_b;
               return RenderChunkCache.this.field_212406_g[☃][☃x].func_177413_a(☃, ☃);
            }
         } else {
            return ☃.field_77198_c;
         }
      }
   }
}
