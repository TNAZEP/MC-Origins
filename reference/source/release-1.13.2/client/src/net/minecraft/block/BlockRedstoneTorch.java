package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockRedstoneTorch extends BlockTorch {
   public static final BooleanProperty field_196528_a = BlockStateProperties.field_208190_q;
   private static final Map<IBlockReader, List<BlockRedstoneTorch.Toggle>> field_196529_b = Maps.newHashMap();

   protected BlockRedstoneTorch(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_196528_a, Boolean.valueOf(true)));
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 2;
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      for(EnumFacing ☃ : EnumFacing.values()) {
         ☃.func_195593_d(☃.func_177972_a(☃), this);
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (!☃) {
         for(EnumFacing ☃ : EnumFacing.values()) {
            ☃.func_195593_d(☃.func_177972_a(☃), this);
         }
      }
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_196528_a) && EnumFacing.UP != ☃ ? 15 : 0;
   }

   protected boolean func_176597_g(World var1, BlockPos var2, IBlockState var3) {
      return ☃.func_175709_b(☃.func_177977_b(), EnumFacing.DOWN);
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      func_196527_a(☃, ☃, ☃, ☃, this.func_176597_g(☃, ☃, ☃));
   }

   public static void func_196527_a(IBlockState var0, World var1, BlockPos var2, Random var3, boolean var4) {
      List<BlockRedstoneTorch.Toggle> ☃ = (List)field_196529_b.get(☃);

      while(☃ != null && !☃.isEmpty() && ☃.func_82737_E() - ((BlockRedstoneTorch.Toggle)☃.get(0)).field_150844_d > 60L) {
         ☃.remove(0);
      }

      if (☃.func_177229_b(field_196528_a)) {
         if (☃) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_196528_a, Boolean.valueOf(false)), 3);
            if (func_176598_a(☃, ☃, true)) {
               ☃.func_184133_a(
                  null, ☃, SoundEvents.field_187745_eA, SoundCategory.BLOCKS, 0.5F, 2.6F + (☃.field_73012_v.nextFloat() - ☃.field_73012_v.nextFloat()) * 0.8F
               );

               for(int ☃x = 0; ☃x < 5; ++☃x) {
                  double ☃xx = (double)☃.func_177958_n() + ☃.nextDouble() * 0.6 + 0.2;
                  double ☃xxx = (double)☃.func_177956_o() + ☃.nextDouble() * 0.6 + 0.2;
                  double ☃xxxx = (double)☃.func_177952_p() + ☃.nextDouble() * 0.6 + 0.2;
                  ☃.func_195594_a(Particles.field_197601_L, ☃xx, ☃xxx, ☃xxxx, 0.0, 0.0, 0.0);
               }

               ☃.func_205220_G_().func_205360_a(☃, ☃.func_180495_p(☃).func_177230_c(), 160);
            }
         }
      } else if (!☃ && !func_176598_a(☃, ☃, false)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_196528_a, Boolean.valueOf(true)), 3);
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (☃.func_177229_b(field_196528_a) == this.func_176597_g(☃, ☃, ☃) && !☃.func_205220_G_().func_205361_b(☃, this)) {
         ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
      }
   }

   @Override
   public int func_176211_b(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.DOWN ? ☃.func_185911_a(☃, ☃, ☃) : 0;
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return true;
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.func_177229_b(field_196528_a)) {
         double ☃ = (double)☃.func_177958_n() + 0.5 + (☃.nextDouble() - 0.5) * 0.2;
         double ☃x = (double)☃.func_177956_o() + 0.7 + (☃.nextDouble() - 0.5) * 0.2;
         double ☃xx = (double)☃.func_177952_p() + 0.5 + (☃.nextDouble() - 0.5) * 0.2;
         ☃.func_195594_a(RedstoneParticleData.field_197564_a, ☃, ☃x, ☃xx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public int func_149750_m(IBlockState var1) {
      return ☃.func_177229_b(field_196528_a) ? super.func_149750_m(☃) : 0;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196528_a);
   }

   private static boolean func_176598_a(World var0, BlockPos var1, boolean var2) {
      List<BlockRedstoneTorch.Toggle> ☃ = (List)field_196529_b.get(☃);
      if (☃ == null) {
         ☃ = Lists.<BlockRedstoneTorch.Toggle>newArrayList();
         field_196529_b.put(☃, ☃);
      }

      if (☃) {
         ☃.add(new BlockRedstoneTorch.Toggle(☃.func_185334_h(), ☃.func_82737_E()));
      }

      int ☃ = 0;

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         BlockRedstoneTorch.Toggle ☃xx = (BlockRedstoneTorch.Toggle)☃.get(☃x);
         if (☃xx.field_180111_a.equals(☃)) {
            if (++☃ >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   public static class Toggle {
      private final BlockPos field_180111_a;
      private final long field_150844_d;

      public Toggle(BlockPos var1, long var2) {
         this.field_180111_a = ☃;
         this.field_150844_d = ☃;
      }
   }
}
