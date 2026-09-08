package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockCoralWallFan extends BlockCoralWallFanDead {
   private final Block field_211886_c;

   protected BlockCoralWallFan(Block var1, Block.Properties var2) {
      super(☃);
      this.field_211886_c = ☃;
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      this.func_212558_a(☃, ☃, ☃);
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!func_212557_b_(☃, ☃, ☃)) {
         ☃.func_180501_a(
            ☃,
            this.field_211886_c
               .func_176223_P()
               .func_206870_a(field_212560_b, Boolean.valueOf(false))
               .func_206870_a(field_211884_b, ☃.func_177229_b(field_211884_b)),
            2
         );
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_176734_d() == ☃.func_177229_b(field_211884_b) && !☃.func_196955_c(☃, ☃)) {
         return Blocks.field_150350_a.func_176223_P();
      } else {
         if (☃.func_177229_b(field_212560_b)) {
            ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
         }

         this.func_212558_a(☃, ☃, ☃);
         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
