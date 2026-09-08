package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class ItemBed extends ItemBlock {
   public ItemBed(Block var1, Item.Properties var2) {
      super(☃, ☃);
   }

   @Override
   protected boolean func_195941_b(BlockItemUseContext var1, IBlockState var2) {
      return ☃.func_195991_k().func_180501_a(☃.func_195995_a(), ☃, 26);
   }
}
