package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemLead extends Item {
   public ItemLead(Item.Properties var1) {
      super(☃);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      Block ☃xx = ☃.func_180495_p(☃x).func_177230_c();
      if (☃xx instanceof BlockFence) {
         EntityPlayer ☃xxx = ☃.func_195999_j();
         if (!☃.field_72995_K && ☃xxx != null) {
            func_180618_a(☃xxx, ☃, ☃x);
         }

         return EnumActionResult.SUCCESS;
      } else {
         return EnumActionResult.PASS;
      }
   }

   public static boolean func_180618_a(EntityPlayer var0, World var1, BlockPos var2) {
      EntityLeashKnot ☃ = EntityLeashKnot.func_174863_b(☃, ☃);
      boolean ☃x = false;
      double ☃xx = 7.0;
      int ☃xxx = ☃.func_177958_n();
      int ☃xxxx = ☃.func_177956_o();
      int ☃xxxxx = ☃.func_177952_p();

      for(EntityLiving ☃xxxxxx : ☃.func_72872_a(
         EntityLiving.class,
         new AxisAlignedBB((double)☃xxx - 7.0, (double)☃xxxx - 7.0, (double)☃xxxxx - 7.0, (double)☃xxx + 7.0, (double)☃xxxx + 7.0, (double)☃xxxxx + 7.0)
      )) {
         if (☃xxxxxx.func_110167_bD() && ☃xxxxxx.func_110166_bE() == ☃) {
            if (☃ == null) {
               ☃ = EntityLeashKnot.func_174862_a(☃, ☃);
            }

            ☃xxxxxx.func_110162_b(☃, true);
            ☃x = true;
         }
      }

      return ☃x;
   }
}
