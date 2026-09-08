package net.minecraft.enchantment;

import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EnchantmentFrostWalker extends Enchantment {
   public EnchantmentFrostWalker(Enchantment.Rarity var1, EntityEquipmentSlot... var2) {
      super(☃, EnumEnchantmentType.ARMOR_FEET, ☃);
   }

   @Override
   public int func_77321_a(int var1) {
      return ☃ * 10;
   }

   @Override
   public int func_77317_b(int var1) {
      return this.func_77321_a(☃) + 15;
   }

   @Override
   public boolean func_185261_e() {
      return true;
   }

   @Override
   public int func_77325_b() {
      return 2;
   }

   public static void func_185266_a(EntityLivingBase var0, World var1, BlockPos var2, int var3) {
      if (☃.field_70122_E) {
         IBlockState ☃ = Blocks.field_185778_de.func_176223_P();
         float ☃x = (float)Math.min(16, 2 + ☃);
         BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos(0, 0, 0);

         for(BlockPos.MutableBlockPos ☃xxx : BlockPos.func_177975_b(
            ☃.func_177963_a((double)(-☃x), -1.0, (double)(-☃x)), ☃.func_177963_a((double)☃x, -1.0, (double)☃x)
         )) {
            if (☃xxx.func_177957_d(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v) <= (double)(☃x * ☃x)) {
               ☃xx.func_181079_c(☃xxx.func_177958_n(), ☃xxx.func_177956_o() + 1, ☃xxx.func_177952_p());
               IBlockState ☃xxxx = ☃.func_180495_p(☃xx);
               if (☃xxxx.func_196958_f()) {
                  IBlockState ☃xxxxx = ☃.func_180495_p(☃xxx);
                  if (☃xxxxx.func_185904_a() == Material.field_151586_h
                     && ☃xxxxx.func_177229_b(BlockFlowingFluid.field_176367_b) == 0
                     && ☃.func_196955_c(☃, ☃xxx)
                     && ☃.func_195584_a(☃, ☃xxx)) {
                     ☃.func_175656_a(☃xxx, ☃);
                     ☃.func_205220_G_().func_205360_a(☃xxx.func_185334_h(), Blocks.field_185778_de, MathHelper.func_76136_a(☃.func_70681_au(), 60, 120));
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean func_77326_a(Enchantment var1) {
      return super.func_77326_a(☃) && ☃ != Enchantments.field_185300_i;
   }
}
