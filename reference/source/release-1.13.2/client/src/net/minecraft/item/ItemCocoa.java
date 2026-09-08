package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ItemCocoa extends ItemDye {
   public ItemCocoa(EnumDyeColor var1, Item.Properties var2) {
      super(☃, ☃);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      BlockItemUseContext ☃ = new BlockItemUseContext(☃);
      if (☃.func_196011_b()) {
         IWorld ☃x = ☃.func_195991_k();
         IBlockState ☃xx = Blocks.field_150375_by.func_196258_a(☃);
         BlockPos ☃xxx = ☃.func_195995_a();
         if (☃xx != null && ☃x.func_180501_a(☃xxx, ☃xx, 2)) {
            ItemStack ☃xxxx = ☃.func_195996_i();
            EntityPlayer ☃xxxxx = ☃.func_195999_j();
            if (☃xxxxx instanceof EntityPlayerMP) {
               CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)☃xxxxx, ☃xxx, ☃xxxx);
            }

            ☃xxxx.func_190918_g(1);
            return EnumActionResult.SUCCESS;
         }
      }

      return EnumActionResult.FAIL;
   }
}
