package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemLead;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockFence extends BlockFourWay {
   private final VoxelShape[] field_199609_B;

   public BlockFence(Block.Properties var1) {
      super(2.0F, 2.0F, 16.0F, 16.0F, 24.0F, ☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_196409_a, Boolean.valueOf(false))
            .func_206870_a(field_196411_b, Boolean.valueOf(false))
            .func_206870_a(field_196413_c, Boolean.valueOf(false))
            .func_206870_a(field_196414_y, Boolean.valueOf(false))
            .func_206870_a(field_204514_u, Boolean.valueOf(false))
      );
      this.field_199609_B = this.func_196408_a(2.0F, 1.0F, 16.0F, 6.0F, 15.0F);
   }

   @Override
   public VoxelShape func_196247_c(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return this.field_199609_B[this.func_196406_i(☃)];
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }

   public boolean func_196416_a(IBlockState var1, BlockFaceShape var2) {
      Block ☃ = ☃.func_177230_c();
      boolean ☃x = ☃ == BlockFaceShape.MIDDLE_POLE && (☃.func_185904_a() == this.field_149764_J || ☃ instanceof BlockFenceGate);
      return !func_194142_e(☃) && ☃ == BlockFaceShape.SOLID || ☃x;
   }

   public static boolean func_194142_e(Block var0) {
      return Block.func_193382_c(☃)
         || ☃ == Blocks.field_180401_cv
         || ☃ == Blocks.field_150440_ba
         || ☃ == Blocks.field_150423_aK
         || ☃ == Blocks.field_196625_cS
         || ☃ == Blocks.field_196628_cT
         || ☃ == Blocks.field_185778_de
         || ☃ == Blocks.field_150335_W;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (!☃.field_72995_K) {
         return ItemLead.func_180618_a(☃, ☃, ☃);
      } else {
         ItemStack ☃ = ☃.func_184586_b(☃);
         return ☃.func_77973_b() == Items.field_151058_ca || ☃.func_190926_b();
      }
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockReader ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      IFluidState ☃xx = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
      BlockPos ☃xxx = ☃x.func_177978_c();
      BlockPos ☃xxxx = ☃x.func_177974_f();
      BlockPos ☃xxxxx = ☃x.func_177968_d();
      BlockPos ☃xxxxxx = ☃x.func_177976_e();
      IBlockState ☃xxxxxxx = ☃.func_180495_p(☃xxx);
      IBlockState ☃xxxxxxxx = ☃.func_180495_p(☃xxxx);
      IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃xxxxx);
      IBlockState ☃xxxxxxxxxx = ☃.func_180495_p(☃xxxxxx);
      return super.func_196258_a(☃)
         .func_206870_a(field_196409_a, Boolean.valueOf(this.func_196416_a(☃xxxxxxx, ☃xxxxxxx.func_193401_d(☃, ☃xxx, EnumFacing.SOUTH))))
         .func_206870_a(field_196411_b, Boolean.valueOf(this.func_196416_a(☃xxxxxxxx, ☃xxxxxxxx.func_193401_d(☃, ☃xxxx, EnumFacing.WEST))))
         .func_206870_a(field_196413_c, Boolean.valueOf(this.func_196416_a(☃xxxxxxxxx, ☃xxxxxxxxx.func_193401_d(☃, ☃xxxxx, EnumFacing.NORTH))))
         .func_206870_a(field_196414_y, Boolean.valueOf(this.func_196416_a(☃xxxxxxxxxx, ☃xxxxxxxxxx.func_193401_d(☃, ☃xxxxxx, EnumFacing.EAST))))
         .func_206870_a(field_204514_u, Boolean.valueOf(☃xx.func_206886_c() == Fluids.field_204546_a));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_177229_b(field_204514_u)) {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
      }

      return ☃.func_176740_k().func_176716_d() == EnumFacing.Plane.HORIZONTAL
         ? ☃.func_206870_a((IProperty)field_196415_z.get(☃), Boolean.valueOf(this.func_196416_a(☃, ☃.func_193401_d(☃, ☃, ☃.func_176734_d()))))
         : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196409_a, field_196411_b, field_196414_y, field_196413_c, field_204514_u);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ != EnumFacing.UP && ☃ != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE : BlockFaceShape.CENTER;
   }
}
