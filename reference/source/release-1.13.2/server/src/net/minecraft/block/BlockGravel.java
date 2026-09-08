package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockGravel extends BlockFalling {
   public BlockGravel(Block.Properties var1) {
      super(☃);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      if (☃ > 3) {
         ☃ = 3;
      }

      return (IItemProvider)(☃.field_73012_v.nextInt(10 - ☃ * 3) == 0 ? Items.field_151145_ak : super.func_199769_a(☃, ☃, ☃, ☃));
   }
}
