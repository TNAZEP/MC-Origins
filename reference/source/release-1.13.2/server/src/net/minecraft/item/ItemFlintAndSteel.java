package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ItemFlintAndSteel extends Item {
   public ItemFlintAndSteel(Item.Properties var1) {
      super(☃);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      EntityPlayer ☃ = ☃.func_195999_j();
      IWorld ☃x = ☃.func_195991_k();
      BlockPos ☃xx = ☃.func_195995_a().func_177972_a(☃.func_196000_l());
      if (func_201825_a(☃x, ☃xx)) {
         ☃x.func_184133_a(☃, ☃xx, SoundEvents.field_187649_bu, SoundCategory.BLOCKS, 1.0F, field_77697_d.nextFloat() * 0.4F + 0.8F);
         IBlockState ☃xxx = ((BlockFire)Blocks.field_150480_ab).func_196448_a(☃x, ☃xx);
         ☃x.func_180501_a(☃xx, ☃xxx, 11);
         ItemStack ☃xxxx = ☃.func_195996_i();
         if (☃ instanceof EntityPlayerMP) {
            CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)☃, ☃xx, ☃xxxx);
         }

         if (☃ != null) {
            ☃xxxx.func_77972_a(1, ☃);
         }

         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.FAIL;
      }
   }

   public static boolean func_201825_a(IWorld var0, BlockPos var1) {
      IBlockState ☃ = ((BlockFire)Blocks.field_150480_ab).func_196448_a(☃, ☃);
      boolean ☃x = false;

      for(EnumFacing ☃xx : EnumFacing.Plane.HORIZONTAL) {
         if (☃.func_180495_p(☃.func_177972_a(☃xx)).func_177230_c() == Blocks.field_150343_Z
            && ((BlockPortal)Blocks.field_150427_aO).func_201816_b(☃, ☃) != null) {
            ☃x = true;
         }
      }

      return ☃.func_175623_d(☃) && (☃.func_196955_c(☃, ☃) || ☃x);
   }
}
