package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSign extends ItemWallOrFloor {
   public ItemSign(Item.Properties var1) {
      super(Blocks.field_196649_cc, Blocks.field_150444_as, ☃);
   }

   @Override
   protected boolean func_195943_a(BlockPos var1, World var2, @Nullable EntityPlayer var3, ItemStack var4, IBlockState var5) {
      boolean ☃ = super.func_195943_a(☃, ☃, ☃, ☃, ☃);
      if (!☃.field_72995_K && !☃ && ☃ != null) {
         ☃.func_175141_a((TileEntitySign)☃.func_175625_s(☃));
      }

      return ☃;
   }
}
