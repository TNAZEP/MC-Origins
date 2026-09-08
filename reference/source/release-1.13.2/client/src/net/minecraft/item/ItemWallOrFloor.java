package net.minecraft.item;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class ItemWallOrFloor extends ItemBlock {
   protected final Block field_195947_b;

   public ItemWallOrFloor(Block var1, Block var2, Item.Properties var3) {
      super(☃, ☃);
      this.field_195947_b = ☃;
   }

   @Nullable
   @Override
   protected IBlockState func_195945_b(BlockItemUseContext var1) {
      IBlockState ☃ = this.field_195947_b.func_196258_a(☃);
      IBlockState ☃x = null;
      IWorldReaderBase ☃xx = ☃.func_195991_k();
      BlockPos ☃xxx = ☃.func_195995_a();

      for(EnumFacing ☃xxxx : ☃.func_196009_e()) {
         if (☃xxxx != EnumFacing.UP) {
            IBlockState ☃xxxxx = ☃xxxx == EnumFacing.DOWN ? this.func_179223_d().func_196258_a(☃) : ☃;
            if (☃xxxxx != null && ☃xxxxx.func_196955_c(☃xx, ☃xxx)) {
               ☃x = ☃xxxxx;
               break;
            }
         }
      }

      return ☃x != null && ☃xx.func_195584_a(☃x, ☃xxx) ? ☃x : null;
   }

   @Override
   public void func_195946_a(Map<Block, Item> var1, Item var2) {
      super.func_195946_a(☃, ☃);
      ☃.put(this.field_195947_b, ☃);
   }
}
