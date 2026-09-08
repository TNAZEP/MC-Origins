package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockFalling extends Block {
   public static boolean field_149832_M;

   public BlockFalling(Block.Properties var1) {
      super(☃);
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.field_72995_K) {
         this.func_176503_e(☃, ☃);
      }
   }

   private void func_176503_e(World var1, BlockPos var2) {
      if (func_185759_i(☃.func_180495_p(☃.func_177977_b())) && ☃.func_177956_o() >= 0) {
         int ☃ = 32;
         if (!field_149832_M && ☃.func_175707_a(☃.func_177982_a(-32, -32, -32), ☃.func_177982_a(32, 32, 32))) {
            if (!☃.field_72995_K) {
               EntityFallingBlock ☃x = new EntityFallingBlock(
                  ☃, (double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o(), (double)☃.func_177952_p() + 0.5, ☃.func_180495_p(☃)
               );
               this.func_149829_a(☃x);
               ☃.func_72838_d(☃x);
            }
         } else {
            if (☃.func_180495_p(☃).func_177230_c() == this) {
               ☃.func_175698_g(☃);
            }

            BlockPos ☃ = ☃.func_177977_b();

            while(func_185759_i(☃.func_180495_p(☃)) && ☃.func_177956_o() > 0) {
               ☃ = ☃.func_177977_b();
            }

            if (☃.func_177956_o() > 0) {
               ☃.func_175656_a(☃.func_177984_a(), this.func_176223_P());
            }
         }
      }
   }

   protected void func_149829_a(EntityFallingBlock var1) {
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 2;
   }

   public static boolean func_185759_i(IBlockState var0) {
      Block ☃ = ☃.func_177230_c();
      Material ☃x = ☃.func_185904_a();
      return ☃.func_196958_f() || ☃ == Blocks.field_150480_ab || ☃x.func_76224_d() || ☃x.func_76222_j();
   }

   public void func_176502_a_(World var1, BlockPos var2, IBlockState var3, IBlockState var4) {
   }

   public void func_190974_b(World var1, BlockPos var2) {
   }
}
