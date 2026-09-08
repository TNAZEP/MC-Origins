package net.minecraft.item;

import net.minecraft.block.BlockFire;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFireCharge extends Item {
   public ItemFireCharge(Item.Properties var1) {
      super(☃);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      if (☃.field_72995_K) {
         return EnumActionResult.SUCCESS;
      } else {
         BlockPos ☃ = ☃.func_195995_a().func_177972_a(☃.func_196000_l());
         if (☃.func_180495_p(☃).func_196958_f()) {
            ☃.func_184133_a(
               null, ☃, SoundEvents.field_187616_bj, SoundCategory.BLOCKS, 1.0F, (field_77697_d.nextFloat() - field_77697_d.nextFloat()) * 0.2F + 1.0F
            );
            ☃.func_175656_a(☃, ((BlockFire)Blocks.field_150480_ab).func_196448_a(☃, ☃));
         }

         ☃.func_195996_i().func_190918_g(1);
         return EnumActionResult.SUCCESS;
      }
   }
}
