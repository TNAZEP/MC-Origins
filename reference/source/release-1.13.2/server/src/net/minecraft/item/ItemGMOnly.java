package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;

public class ItemGMOnly extends ItemBlock {
   public ItemGMOnly(Block var1, Item.Properties var2) {
      super(☃, ☃);
   }

   @Nullable
   @Override
   protected IBlockState func_195945_b(BlockItemUseContext var1) {
      EntityPlayer ☃ = ☃.func_195999_j();
      return ☃ != null && !☃.func_195070_dx() ? null : super.func_195945_b(☃);
   }
}
