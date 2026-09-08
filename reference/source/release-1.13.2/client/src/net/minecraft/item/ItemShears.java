package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemShears extends Item {
   public ItemShears(Item.Properties var1) {
      super(☃);
   }

   @Override
   public boolean func_179218_a(ItemStack var1, World var2, IBlockState var3, BlockPos var4, EntityLivingBase var5) {
      if (!☃.field_72995_K) {
         ☃.func_77972_a(1, ☃);
      }

      Block ☃ = ☃.func_177230_c();
      return !☃.func_203425_a(BlockTags.field_206952_E)
            && ☃ != Blocks.field_196553_aF
            && ☃ != Blocks.field_150349_c
            && ☃ != Blocks.field_196554_aH
            && ☃ != Blocks.field_196555_aI
            && ☃ != Blocks.field_150395_bd
            && ☃ != Blocks.field_150473_bD
            && !☃.func_203417_a(BlockTags.field_199897_a)
         ? super.func_179218_a(☃, ☃, ☃, ☃, ☃)
         : true;
   }

   @Override
   public boolean func_150897_b(IBlockState var1) {
      Block ☃ = ☃.func_177230_c();
      return ☃ == Blocks.field_196553_aF || ☃ == Blocks.field_150488_af || ☃ == Blocks.field_150473_bD;
   }

   @Override
   public float func_150893_a(ItemStack var1, IBlockState var2) {
      Block ☃ = ☃.func_177230_c();
      if (☃ == Blocks.field_196553_aF || ☃.func_203425_a(BlockTags.field_206952_E)) {
         return 15.0F;
      } else {
         return ☃.func_203417_a(BlockTags.field_199897_a) ? 5.0F : super.func_150893_a(☃, ☃);
      }
   }
}
