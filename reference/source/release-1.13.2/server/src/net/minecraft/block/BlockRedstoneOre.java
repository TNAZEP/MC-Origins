package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRedstoneOre extends Block {
   public static final BooleanProperty field_196501_a = BlockRedstoneTorch.field_196528_a;

   public BlockRedstoneOre(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.func_176223_P().func_206870_a(field_196501_a, Boolean.valueOf(false)));
   }

   @Override
   public int func_149750_m(IBlockState var1) {
      return ☃.func_177229_b(field_196501_a) ? super.func_149750_m(☃) : 0;
   }

   @Override
   public void func_196270_a(IBlockState var1, World var2, BlockPos var3, EntityPlayer var4) {
      func_196500_d(☃, ☃, ☃);
      super.func_196270_a(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_176199_a(World var1, BlockPos var2, Entity var3) {
      func_196500_d(☃.func_180495_p(☃), ☃, ☃);
      super.func_176199_a(☃, ☃, ☃);
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      func_196500_d(☃, ☃, ☃);
      return super.func_196250_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   private static void func_196500_d(IBlockState var0, World var1, BlockPos var2) {
      func_180691_e(☃, ☃);
      if (!☃.func_177229_b(field_196501_a)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_196501_a, Boolean.valueOf(true)), 3);
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.func_177229_b(field_196501_a)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_196501_a, Boolean.valueOf(false)), 3);
      }
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_151137_ax;
   }

   @Override
   public int func_196251_a(IBlockState var1, int var2, World var3, BlockPos var4, Random var5) {
      return this.func_196264_a(☃, ☃) + ☃.nextInt(☃ + 1);
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 4 + ☃.nextInt(2);
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      super.func_196255_a(☃, ☃, ☃, ☃, ☃);
      if (this.func_199769_a(☃, ☃, ☃, ☃) != this) {
         int ☃ = 1 + ☃.field_73012_v.nextInt(5);
         this.func_180637_b(☃, ☃, ☃);
      }
   }

   private static void func_180691_e(World var0, BlockPos var1) {
      double ☃ = 0.5625;
      Random ☃x = ☃.field_73012_v;

      for(EnumFacing ☃xx : EnumFacing.values()) {
         BlockPos ☃xxx = ☃.func_177972_a(☃xx);
         if (!☃.func_180495_p(☃xxx).func_200015_d(☃, ☃xxx)) {
            EnumFacing.Axis ☃xxxx = ☃xx.func_176740_k();
            double ☃xxxxx = ☃xxxx == EnumFacing.Axis.X ? 0.5 + 0.5625 * (double)☃xx.func_82601_c() : (double)☃x.nextFloat();
            double ☃xxxxxx = ☃xxxx == EnumFacing.Axis.Y ? 0.5 + 0.5625 * (double)☃xx.func_96559_d() : (double)☃x.nextFloat();
            double ☃xxxxxxx = ☃xxxx == EnumFacing.Axis.Z ? 0.5 + 0.5625 * (double)☃xx.func_82599_e() : (double)☃x.nextFloat();
            ☃.func_195594_a(
               RedstoneParticleData.field_197564_a,
               (double)☃.func_177958_n() + ☃xxxxx,
               (double)☃.func_177956_o() + ☃xxxxxx,
               (double)☃.func_177952_p() + ☃xxxxxxx,
               0.0,
               0.0,
               0.0
            );
         }
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196501_a);
   }
}
