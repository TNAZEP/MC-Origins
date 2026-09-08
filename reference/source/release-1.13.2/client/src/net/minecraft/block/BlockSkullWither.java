package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class BlockSkullWither extends BlockSkull {
   private static BlockPattern field_196300_c;
   private static BlockPattern field_196301_y;

   protected BlockSkullWither(Block.Properties var1) {
      super(BlockSkull.Types.WITHER_SKELETON, ☃);
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, @Nullable EntityLivingBase var4, ItemStack var5) {
      super.func_180633_a(☃, ☃, ☃, ☃, ☃);
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (☃ instanceof TileEntitySkull) {
         func_196298_a(☃, ☃, (TileEntitySkull)☃);
      }
   }

   public static void func_196298_a(World var0, BlockPos var1, TileEntitySkull var2) {
      Block ☃ = ☃.func_195044_w().func_177230_c();
      boolean ☃x = ☃ == Blocks.field_196705_eO || ☃ == Blocks.field_196704_eN;
      if (☃x && ☃.func_177956_o() >= 2 && ☃.func_175659_aa() != EnumDifficulty.PEACEFUL && !☃.field_72995_K) {
         BlockPattern ☃xx = func_196296_d();
         BlockPattern.PatternHelper ☃xxx = ☃xx.func_177681_a(☃, ☃);
         if (☃xxx != null) {
            for(int ☃xxxx = 0; ☃xxxx < 3; ++☃xxxx) {
               TileEntitySkull.func_195486_a(☃, ☃xxx.func_177670_a(☃xxxx, 0, 0).func_177508_d());
            }

            for(int ☃xxxx = 0; ☃xxxx < ☃xx.func_177684_c(); ++☃xxxx) {
               for(int ☃xxxxx = 0; ☃xxxxx < ☃xx.func_177685_b(); ++☃xxxxx) {
                  ☃.func_180501_a(☃xxx.func_177670_a(☃xxxx, ☃xxxxx, 0).func_177508_d(), Blocks.field_150350_a.func_176223_P(), 2);
               }
            }

            BlockPos ☃xxxx = ☃xxx.func_177670_a(1, 0, 0).func_177508_d();
            EntityWither ☃xxxxx = new EntityWither(☃);
            BlockPos ☃xxxxxx = ☃xxx.func_177670_a(1, 2, 0).func_177508_d();
            ☃xxxxx.func_70012_b(
               (double)☃xxxxxx.func_177958_n() + 0.5,
               (double)☃xxxxxx.func_177956_o() + 0.55,
               (double)☃xxxxxx.func_177952_p() + 0.5,
               ☃xxx.func_177669_b().func_176740_k() == EnumFacing.Axis.X ? 0.0F : 90.0F,
               0.0F
            );
            ☃xxxxx.field_70761_aq = ☃xxx.func_177669_b().func_176740_k() == EnumFacing.Axis.X ? 0.0F : 90.0F;
            ☃xxxxx.func_82206_m();

            for(EntityPlayerMP ☃xxxxxxx : ☃.func_72872_a(EntityPlayerMP.class, ☃xxxxx.func_174813_aQ().func_186662_g(50.0))) {
               CriteriaTriggers.field_192133_m.func_192229_a(☃xxxxxxx, ☃xxxxx);
            }

            ☃.func_72838_d(☃xxxxx);

            for(int ☃xxxxxxx = 0; ☃xxxxxxx < 120; ++☃xxxxxxx) {
               ☃.func_195594_a(
                  Particles.field_197593_D,
                  (double)☃xxxx.func_177958_n() + ☃.field_73012_v.nextDouble(),
                  (double)(☃xxxx.func_177956_o() - 2) + ☃.field_73012_v.nextDouble() * 3.9,
                  (double)☃xxxx.func_177952_p() + ☃.field_73012_v.nextDouble(),
                  0.0,
                  0.0,
                  0.0
               );
            }

            for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃xx.func_177684_c(); ++☃xxxxxxx) {
               for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < ☃xx.func_177685_b(); ++☃xxxxxxxx) {
                  ☃.func_195592_c(☃xxx.func_177670_a(☃xxxxxxx, ☃xxxxxxxx, 0).func_177508_d(), Blocks.field_150350_a);
               }
            }
         }
      }
   }

   public static boolean func_196299_b(World var0, BlockPos var1, ItemStack var2) {
      if (☃.func_77973_b() == Items.field_196183_dw && ☃.func_177956_o() >= 2 && ☃.func_175659_aa() != EnumDifficulty.PEACEFUL && !☃.field_72995_K) {
         return func_196297_e().func_177681_a(☃, ☃) != null;
      } else {
         return false;
      }
   }

   protected static BlockPattern func_196296_d() {
      if (field_196300_c == null) {
         field_196300_c = FactoryBlockPattern.func_177660_a()
            .func_177659_a("^^^", "###", "~#~")
            .func_177662_a('#', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150425_aM)))
            .func_177662_a(
               '^',
               BlockWorldState.func_177510_a(
                  BlockStateMatcher.func_177638_a(Blocks.field_196705_eO).or(BlockStateMatcher.func_177638_a(Blocks.field_196704_eN))
               )
            )
            .func_177662_a('~', BlockWorldState.func_177510_a(BlockMaterialMatcher.func_189886_a(Material.field_151579_a)))
            .func_177661_b();
      }

      return field_196300_c;
   }

   protected static BlockPattern func_196297_e() {
      if (field_196301_y == null) {
         field_196301_y = FactoryBlockPattern.func_177660_a()
            .func_177659_a("   ", "###", "~#~")
            .func_177662_a('#', BlockWorldState.func_177510_a(BlockStateMatcher.func_177638_a(Blocks.field_150425_aM)))
            .func_177662_a('~', BlockWorldState.func_177510_a(BlockMaterialMatcher.func_189886_a(Material.field_151579_a)))
            .func_177661_b();
      }

      return field_196301_y;
   }
}
