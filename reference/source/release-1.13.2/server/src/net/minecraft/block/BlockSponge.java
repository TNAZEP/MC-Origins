package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.Queue;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSponge extends Block {
   protected BlockSponge(Block.Properties var1) {
      super(☃);
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         this.func_196510_a(☃, ☃);
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      this.func_196510_a(☃, ☃);
      super.func_189540_a(☃, ☃, ☃, ☃, ☃);
   }

   protected void func_196510_a(World var1, BlockPos var2) {
      if (this.func_176312_d(☃, ☃)) {
         ☃.func_180501_a(☃, Blocks.field_196577_ad.func_176223_P(), 2);
         ☃.func_175718_b(2001, ☃, Block.func_196246_j(Blocks.field_150355_j.func_176223_P()));
      }
   }

   private boolean func_176312_d(World var1, BlockPos var2) {
      Queue<Tuple<BlockPos, Integer>> ☃ = Lists.<Tuple<BlockPos, Integer>>newLinkedList();
      ☃.add(new Tuple<>(☃, 0));
      int ☃x = 0;

      while(!☃.isEmpty()) {
         Tuple<BlockPos, Integer> ☃xx = (Tuple)☃.poll();
         BlockPos ☃xxx = ☃xx.func_76341_a();
         int ☃xxxx = ☃xx.func_76340_b();

         for(EnumFacing ☃xxxxx : EnumFacing.values()) {
            BlockPos ☃xxxxxx = ☃xxx.func_177972_a(☃xxxxx);
            IBlockState ☃xxxxxxx = ☃.func_180495_p(☃xxxxxx);
            IFluidState ☃xxxxxxxx = ☃.func_204610_c(☃xxxxxx);
            Material ☃xxxxxxxxx = ☃xxxxxxx.func_185904_a();
            if (☃xxxxxxxx.func_206884_a(FluidTags.field_206959_a)) {
               if (☃xxxxxxx.func_177230_c() instanceof IBucketPickupHandler
                  && ((IBucketPickupHandler)☃xxxxxxx.func_177230_c()).func_204508_a(☃, ☃xxxxxx, ☃xxxxxxx) != Fluids.field_204541_a) {
                  ++☃x;
                  if (☃xxxx < 6) {
                     ☃.add(new Tuple<>(☃xxxxxx, ☃xxxx + 1));
                  }
               } else if (☃xxxxxxx.func_177230_c() instanceof BlockFlowingFluid) {
                  ☃.func_180501_a(☃xxxxxx, Blocks.field_150350_a.func_176223_P(), 3);
                  ++☃x;
                  if (☃xxxx < 6) {
                     ☃.add(new Tuple<>(☃xxxxxx, ☃xxxx + 1));
                  }
               } else if (☃xxxxxxxxx == Material.field_203243_f || ☃xxxxxxxxx == Material.field_204868_h) {
                  ☃xxxxxxx.func_196949_c(☃, ☃xxxxxx, 0);
                  ☃.func_180501_a(☃xxxxxx, Blocks.field_150350_a.func_176223_P(), 3);
                  ++☃x;
                  if (☃xxxx < 6) {
                     ☃.add(new Tuple<>(☃xxxxxx, ☃xxxx + 1));
                  }
               }
            }
         }

         if (☃x > 64) {
            break;
         }
      }

      return ☃x > 0;
   }
}
