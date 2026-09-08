package net.minecraft.block;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockTripWire extends Block {
   public static final BooleanProperty field_176293_a = BlockStateProperties.field_208194_u;
   public static final BooleanProperty field_176294_M = BlockStateProperties.field_208174_a;
   public static final BooleanProperty field_176295_N = BlockStateProperties.field_208178_e;
   public static final BooleanProperty field_176296_O = BlockSixWay.field_196488_a;
   public static final BooleanProperty field_176291_P = BlockSixWay.field_196490_b;
   public static final BooleanProperty field_176289_Q = BlockSixWay.field_196492_c;
   public static final BooleanProperty field_176292_R = BlockSixWay.field_196495_y;
   private static final Map<EnumFacing, BooleanProperty> field_196537_E = BlockFourWay.field_196415_z;
   protected static final VoxelShape field_185747_B = Block.func_208617_a(0.0, 1.0, 0.0, 16.0, 2.5, 16.0);
   protected static final VoxelShape field_185748_C = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   private final BlockTripWireHook field_196538_F;

   public BlockTripWire(BlockTripWireHook var1, Block.Properties var2) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176293_a, Boolean.valueOf(false))
            .func_206870_a(field_176294_M, Boolean.valueOf(false))
            .func_206870_a(field_176295_N, Boolean.valueOf(false))
            .func_206870_a(field_176296_O, Boolean.valueOf(false))
            .func_206870_a(field_176291_P, Boolean.valueOf(false))
            .func_206870_a(field_176289_Q, Boolean.valueOf(false))
            .func_206870_a(field_176292_R, Boolean.valueOf(false))
      );
      this.field_196538_F = ☃;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_177229_b(field_176294_M) ? field_185747_B : field_185748_C;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockReader ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      return this.func_176223_P()
         .func_206870_a(field_176296_O, Boolean.valueOf(this.func_196536_a(☃.func_180495_p(☃x.func_177978_c()), EnumFacing.NORTH)))
         .func_206870_a(field_176291_P, Boolean.valueOf(this.func_196536_a(☃.func_180495_p(☃x.func_177974_f()), EnumFacing.EAST)))
         .func_206870_a(field_176289_Q, Boolean.valueOf(this.func_196536_a(☃.func_180495_p(☃x.func_177968_d()), EnumFacing.SOUTH)))
         .func_206870_a(field_176292_R, Boolean.valueOf(this.func_196536_a(☃.func_180495_p(☃x.func_177976_e()), EnumFacing.WEST)));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃.func_176740_k().func_176722_c()
         ? ☃.func_206870_a((IProperty)field_196537_E.get(☃), Boolean.valueOf(this.func_196536_a(☃, ☃)))
         : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         this.func_176286_e(☃, ☃, ☃);
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (!☃ && ☃.func_177230_c() != ☃.func_177230_c()) {
         this.func_176286_e(☃, ☃, ☃.func_206870_a(field_176293_a, Boolean.valueOf(true)));
      }
   }

   @Override
   public void func_176208_a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (!☃.field_72995_K && !☃.func_184614_ca().func_190926_b() && ☃.func_184614_ca().func_77973_b() == Items.field_151097_aZ) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176295_N, Boolean.valueOf(true)), 4);
      }

      super.func_176208_a(☃, ☃, ☃, ☃);
   }

   private void func_176286_e(World var1, BlockPos var2, IBlockState var3) {
      for(EnumFacing ☃ : new EnumFacing[]{EnumFacing.SOUTH, EnumFacing.WEST}) {
         for(int ☃x = 1; ☃x < 42; ++☃x) {
            BlockPos ☃xx = ☃.func_177967_a(☃, ☃x);
            IBlockState ☃xxx = ☃.func_180495_p(☃xx);
            if (☃xxx.func_177230_c() == this.field_196538_F) {
               if (☃xxx.func_177229_b(BlockTripWireHook.field_176264_a) == ☃.func_176734_d()) {
                  this.field_196538_F.func_176260_a(☃, ☃xx, ☃xxx, false, true, ☃x, ☃);
               }
               break;
            }

            if (☃xxx.func_177230_c() != this) {
               break;
            }
         }
      }
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      if (!☃.field_72995_K) {
         if (!☃.func_177229_b(field_176293_a)) {
            this.func_176288_d(☃, ☃);
         }
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.field_72995_K) {
         if (☃.func_180495_p(☃).func_177229_b(field_176293_a)) {
            this.func_176288_d(☃, ☃);
         }
      }
   }

   private void func_176288_d(World var1, BlockPos var2) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      boolean ☃x = ☃.func_177229_b(field_176293_a);
      boolean ☃xx = false;
      List<? extends Entity> ☃xxx = ☃.func_72839_b(null, ☃.func_196954_c(☃, ☃).func_197752_a().func_186670_a(☃));
      if (!☃xxx.isEmpty()) {
         for(Entity ☃xxxx : ☃xxx) {
            if (!☃xxxx.func_145773_az()) {
               ☃xx = true;
               break;
            }
         }
      }

      if (☃xx != ☃x) {
         ☃ = ☃.func_206870_a(field_176293_a, Boolean.valueOf(☃xx));
         ☃.func_180501_a(☃, ☃, 3);
         this.func_176286_e(☃, ☃, ☃);
      }

      if (☃xx) {
         ☃.func_205220_G_().func_205360_a(new BlockPos(☃), this, this.func_149738_a(☃));
      }
   }

   public boolean func_196536_a(IBlockState var1, EnumFacing var2) {
      Block ☃ = ☃.func_177230_c();
      if (☃ == this.field_196538_F) {
         return ☃.func_177229_b(BlockTripWireHook.field_176264_a) == ☃.func_176734_d();
      } else {
         return ☃ == this;
      }
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      switch(☃) {
         case CLOCKWISE_180:
            return ☃.func_206870_a(field_176296_O, ☃.func_177229_b(field_176289_Q))
               .func_206870_a(field_176291_P, ☃.func_177229_b(field_176292_R))
               .func_206870_a(field_176289_Q, ☃.func_177229_b(field_176296_O))
               .func_206870_a(field_176292_R, ☃.func_177229_b(field_176291_P));
         case COUNTERCLOCKWISE_90:
            return ☃.func_206870_a(field_176296_O, ☃.func_177229_b(field_176291_P))
               .func_206870_a(field_176291_P, ☃.func_177229_b(field_176289_Q))
               .func_206870_a(field_176289_Q, ☃.func_177229_b(field_176292_R))
               .func_206870_a(field_176292_R, ☃.func_177229_b(field_176296_O));
         case CLOCKWISE_90:
            return ☃.func_206870_a(field_176296_O, ☃.func_177229_b(field_176292_R))
               .func_206870_a(field_176291_P, ☃.func_177229_b(field_176296_O))
               .func_206870_a(field_176289_Q, ☃.func_177229_b(field_176291_P))
               .func_206870_a(field_176292_R, ☃.func_177229_b(field_176289_Q));
         default:
            return ☃;
      }
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      switch(☃) {
         case LEFT_RIGHT:
            return ☃.func_206870_a(field_176296_O, ☃.func_177229_b(field_176289_Q)).func_206870_a(field_176289_Q, ☃.func_177229_b(field_176296_O));
         case FRONT_BACK:
            return ☃.func_206870_a(field_176291_P, ☃.func_177229_b(field_176292_R)).func_206870_a(field_176292_R, ☃.func_177229_b(field_176291_P));
         default:
            return super.func_185471_a(☃, ☃);
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176293_a, field_176294_M, field_176295_N, field_176296_O, field_176291_P, field_176292_R, field_176289_Q);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
