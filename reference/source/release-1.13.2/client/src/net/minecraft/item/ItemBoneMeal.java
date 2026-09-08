package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.BlockCoralWallFanDead;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class ItemBoneMeal extends ItemDye {
   public ItemBoneMeal(EnumDyeColor var1, Item.Properties var2) {
      super(☃, ☃);
   }

   @Override
   public EnumActionResult func_195939_a(ItemUseContext var1) {
      World ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      BlockPos ☃xx = ☃x.func_177972_a(☃.func_196000_l());
      if (func_195966_a(☃.func_195996_i(), ☃, ☃x)) {
         if (!☃.field_72995_K) {
            ☃.func_175718_b(2005, ☃x, 0);
         }

         return EnumActionResult.SUCCESS;
      } else {
         IBlockState ☃ = ☃.func_180495_p(☃x);
         boolean ☃x = ☃.func_193401_d(☃, ☃x, ☃.func_196000_l()) == BlockFaceShape.SOLID;
         if (☃x && func_203173_b(☃.func_195996_i(), ☃, ☃xx, ☃.func_196000_l())) {
            if (!☃.field_72995_K) {
               ☃.func_175718_b(2005, ☃xx, 0);
            }

            return EnumActionResult.SUCCESS;
         } else {
            return EnumActionResult.PASS;
         }
      }
   }

   public static boolean func_195966_a(ItemStack var0, World var1, BlockPos var2) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      if (☃.func_177230_c() instanceof IGrowable) {
         IGrowable ☃x = (IGrowable)☃.func_177230_c();
         if (☃x.func_176473_a(☃, ☃, ☃, ☃.field_72995_K)) {
            if (!☃.field_72995_K) {
               if (☃x.func_180670_a(☃, ☃.field_73012_v, ☃, ☃)) {
                  ☃x.func_176474_b(☃, ☃.field_73012_v, ☃, ☃);
               }

               ☃.func_190918_g(1);
            }

            return true;
         }
      }

      return false;
   }

   public static boolean func_203173_b(ItemStack var0, World var1, BlockPos var2, @Nullable EnumFacing var3) {
      if (☃.func_180495_p(☃).func_177230_c() == Blocks.field_150355_j && ☃.func_204610_c(☃).func_206882_g() == 8) {
         if (!☃.field_72995_K) {
            label79:
            for(int ☃ = 0; ☃ < 128; ++☃) {
               BlockPos ☃x = ☃;
               Biome ☃xx = ☃.func_180494_b(☃);
               IBlockState ☃xxx = Blocks.field_203198_aQ.func_176223_P();

               for(int ☃xxxx = 0; ☃xxxx < ☃ / 16; ++☃xxxx) {
                  ☃x = ☃x.func_177982_a(
                     field_77697_d.nextInt(3) - 1, (field_77697_d.nextInt(3) - 1) * field_77697_d.nextInt(3) / 2, field_77697_d.nextInt(3) - 1
                  );
                  ☃xx = ☃.func_180494_b(☃x);
                  if (☃.func_180495_p(☃x).func_185898_k()) {
                     continue label79;
                  }
               }

               if (☃xx == Biomes.field_203614_T || ☃xx == Biomes.field_203617_W) {
                  if (☃ == 0 && ☃ != null && ☃.func_176740_k().func_176722_c()) {
                     ☃xxx = BlockTags.field_211922_B.func_205596_a(☃.field_73012_v).func_176223_P().func_206870_a(BlockCoralWallFanDead.field_211884_b, ☃);
                  } else if (field_77697_d.nextInt(4) == 0) {
                     ☃xxx = BlockTags.field_212741_H.func_205596_a(field_77697_d).func_176223_P();
                  }
               }

               if (☃xxx.func_177230_c().func_203417_a(BlockTags.field_211922_B)) {
                  for(int ☃xxxx = 0; !☃xxx.func_196955_c(☃, ☃x) && ☃xxxx < 4; ++☃xxxx) {
                     ☃xxx = ☃xxx.func_206870_a(BlockCoralWallFanDead.field_211884_b, EnumFacing.Plane.HORIZONTAL.func_179518_a(field_77697_d));
                  }
               }

               if (☃xxx.func_196955_c(☃, ☃x)) {
                  IBlockState ☃xxxx = ☃.func_180495_p(☃x);
                  if (☃xxxx.func_177230_c() == Blocks.field_150355_j && ☃.func_204610_c(☃x).func_206882_g() == 8) {
                     ☃.func_180501_a(☃x, ☃xxx, 3);
                  } else if (☃xxxx.func_177230_c() == Blocks.field_203198_aQ && field_77697_d.nextInt(10) == 0) {
                     ((IGrowable)Blocks.field_203198_aQ).func_176474_b(☃, field_77697_d, ☃x, ☃xxxx);
                  }
               }
            }

            ☃.func_190918_g(1);
         }

         return true;
      } else {
         return false;
      }
   }

   public static void func_195965_a(IWorld var0, BlockPos var1, int var2) {
      if (☃ == 0) {
         ☃ = 15;
      }

      IBlockState ☃ = ☃.func_180495_p(☃);
      if (!☃.func_196958_f()) {
         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            double ☃xx = field_77697_d.nextGaussian() * 0.02;
            double ☃xxx = field_77697_d.nextGaussian() * 0.02;
            double ☃xxxx = field_77697_d.nextGaussian() * 0.02;
            ☃.func_195594_a(
               Particles.field_197632_y,
               (double)((float)☃.func_177958_n() + field_77697_d.nextFloat()),
               (double)☃.func_177956_o() + (double)field_77697_d.nextFloat() * ☃.func_196954_c(☃, ☃).func_197758_c(EnumFacing.Axis.Y),
               (double)((float)☃.func_177952_p() + field_77697_d.nextFloat()),
               ☃xx,
               ☃xxx,
               ☃xxxx
            );
         }
      }
   }
}
