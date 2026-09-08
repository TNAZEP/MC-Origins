package net.minecraft.world;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.gen.Heightmap;

public class Region implements IWorldReader {
   protected int field_72818_a;
   protected int field_72816_b;
   protected Chunk[][] field_72817_c;
   protected boolean field_72814_d;
   protected World field_72815_e;

   public Region(World var1, BlockPos var2, BlockPos var3, int var4) {
      this.field_72815_e = ☃;
      this.field_72818_a = ☃.func_177958_n() - ☃ >> 4;
      this.field_72816_b = ☃.func_177952_p() - ☃ >> 4;
      int ☃ = ☃.func_177958_n() + ☃ >> 4;
      int ☃x = ☃.func_177952_p() + ☃ >> 4;
      this.field_72817_c = new Chunk[☃ - this.field_72818_a + 1][☃x - this.field_72816_b + 1];
      this.field_72814_d = true;

      for(int ☃xx = this.field_72818_a; ☃xx <= ☃; ++☃xx) {
         for(int ☃xxx = this.field_72816_b; ☃xxx <= ☃x; ++☃xxx) {
            this.field_72817_c[☃xx - this.field_72818_a][☃xxx - this.field_72816_b] = ☃.func_72964_e(☃xx, ☃xxx);
         }
      }

      for(int ☃xx = ☃.func_177958_n() >> 4; ☃xx <= ☃.func_177958_n() >> 4; ++☃xx) {
         for(int ☃xxx = ☃.func_177952_p() >> 4; ☃xxx <= ☃.func_177952_p() >> 4; ++☃xxx) {
            Chunk ☃xxxx = this.field_72817_c[☃xx - this.field_72818_a][☃xxx - this.field_72816_b];
            if (☃xxxx != null && !☃xxxx.func_76606_c(☃.func_177956_o(), ☃.func_177956_o())) {
               this.field_72814_d = false;
            }
         }
      }
   }

   @Nullable
   @Override
   public TileEntity func_175625_s(BlockPos var1) {
      return this.func_190300_a(☃, Chunk.EnumCreateEntityType.IMMEDIATE);
   }

   @Nullable
   public TileEntity func_190300_a(BlockPos var1, Chunk.EnumCreateEntityType var2) {
      int ☃ = (☃.func_177958_n() >> 4) - this.field_72818_a;
      int ☃x = (☃.func_177952_p() >> 4) - this.field_72816_b;
      return this.field_72817_c[☃][☃x].func_177424_a(☃, ☃);
   }

   @Override
   public float func_205052_D(BlockPos var1) {
      return this.field_72815_e.field_73011_w.func_177497_p()[this.func_201696_r(☃)];
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
      return this.field_72815_e.func_201675_m();
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
         int ☃ = (☃.func_177958_n() >> 4) - this.field_72818_a;
         int ☃x = (☃.func_177952_p() >> 4) - this.field_72816_b;
         return this.field_72817_c[☃][☃x].func_177443_a(☃, ☃);
      }
   }

   @Override
   public boolean func_175680_a(int var1, int var2, boolean var3) {
      return this.func_205054_a(☃, ☃);
   }

   @Override
   public boolean func_175678_i(BlockPos var1) {
      return false;
   }

   public boolean func_205054_a(int var1, int var2) {
      int ☃ = ☃ - this.field_72818_a;
      int ☃x = ☃ - this.field_72816_b;
      return ☃ >= 0 && ☃ < this.field_72817_c.length && ☃x >= 0 && ☃x < this.field_72817_c[☃].length;
   }

   @Override
   public int func_201676_a(Heightmap.Type var1, int var2, int var3) {
      throw new RuntimeException("NOT IMPLEMENTED!");
   }

   @Override
   public WorldBorder func_175723_af() {
      return this.field_72815_e.func_175723_af();
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
   public IBlockState func_180495_p(BlockPos var1) {
      if (☃.func_177956_o() >= 0 && ☃.func_177956_o() < 256) {
         int ☃ = (☃.func_177958_n() >> 4) - this.field_72818_a;
         int ☃x = (☃.func_177952_p() >> 4) - this.field_72816_b;
         if (☃ >= 0 && ☃ < this.field_72817_c.length && ☃x >= 0 && ☃x < this.field_72817_c[☃].length) {
            Chunk ☃xx = this.field_72817_c[☃][☃x];
            if (☃xx != null) {
               return ☃xx.func_180495_p(☃);
            }
         }
      }

      return Blocks.field_150350_a.func_176223_P();
   }

   @Override
   public IFluidState func_204610_c(BlockPos var1) {
      if (☃.func_177956_o() >= 0 && ☃.func_177956_o() < 256) {
         int ☃ = (☃.func_177958_n() >> 4) - this.field_72818_a;
         int ☃x = (☃.func_177952_p() >> 4) - this.field_72816_b;
         if (☃ >= 0 && ☃ < this.field_72817_c.length && ☃x >= 0 && ☃x < this.field_72817_c[☃].length) {
            Chunk ☃xx = this.field_72817_c[☃][☃x];
            if (☃xx != null) {
               return ☃xx.func_204610_c(☃);
            }
         }
      }

      return Fluids.field_204541_a.func_207188_f();
   }

   @Override
   public int func_175657_ab() {
      return 0;
   }

   @Override
   public Biome func_180494_b(BlockPos var1) {
      int ☃ = (☃.func_177958_n() >> 4) - this.field_72818_a;
      int ☃x = (☃.func_177952_p() >> 4) - this.field_72816_b;
      return this.field_72817_c[☃][☃x].func_201600_k(☃);
   }

   @Override
   public boolean func_175623_d(BlockPos var1) {
      return this.func_180495_p(☃).func_196958_f();
   }

   @Override
   public int func_175642_b(EnumLightType var1, BlockPos var2) {
      if (☃.func_177956_o() >= 0 && ☃.func_177956_o() < 256) {
         int ☃ = (☃.func_177958_n() >> 4) - this.field_72818_a;
         int ☃x = (☃.func_177952_p() >> 4) - this.field_72816_b;
         return this.field_72817_c[☃][☃x].func_177413_a(☃, ☃);
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
}
