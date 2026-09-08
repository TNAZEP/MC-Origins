package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockLeaves extends Block {
   public static final IntegerProperty field_208494_a = BlockStateProperties.field_208514_aa;
   public static final BooleanProperty field_208495_b = BlockStateProperties.field_208515_s;
   protected static boolean field_196478_c;

   public BlockLeaves(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_208494_a, Integer.valueOf(7)).func_206870_a(field_208495_b, Boolean.valueOf(false))
      );
   }

   @Override
   public boolean func_149653_t(IBlockState var1) {
      return ☃.func_177229_b(field_208494_a) == 7 && !☃.func_177229_b(field_208495_b);
   }

   @Override
   public void func_196265_a(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.func_177229_b(field_208495_b) && ☃.func_177229_b(field_208494_a) == 7) {
         ☃.func_196949_c(☃, ☃, 0);
         ☃.func_175698_g(☃);
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      ☃.func_180501_a(☃, func_208493_b(☃, ☃, ☃), 3);
   }

   @Override
   public int func_200011_d(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return 1;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      int ☃ = func_208492_w(☃) + 1;
      if (☃ != 1 || ☃.func_177229_b(field_208494_a) != ☃) {
         ☃.func_205220_G_().func_205360_a(☃, this, 1);
      }

      return ☃;
   }

   private static IBlockState func_208493_b(IBlockState var0, IWorld var1, BlockPos var2) {
      int ☃ = 7;

      try (BlockPos.PooledMutableBlockPos ☃x = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         for(EnumFacing ☃xx : EnumFacing.values()) {
            ☃x.func_189533_g(☃).func_189536_c(☃xx);
            ☃ = Math.min(☃, func_208492_w(☃.func_180495_p(☃x)) + 1);
            if (☃ == 1) {
               break;
            }
         }
      }

      return ☃.func_206870_a(field_208494_a, Integer.valueOf(☃));
   }

   private static int func_208492_w(IBlockState var0) {
      if (BlockTags.field_200031_h.func_199685_a_(☃.func_177230_c())) {
         return 0;
      } else {
         return ☃.func_177230_c() instanceof BlockLeaves ? ☃.func_177229_b(field_208494_a) : 7;
      }
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.func_175727_C(☃.func_177984_a()) && !☃.func_180495_p(☃.func_177977_b()).func_185896_q() && ☃.nextInt(15) == 1) {
         double ☃ = (double)((float)☃.func_177958_n() + ☃.nextFloat());
         double ☃x = (double)☃.func_177956_o() - 0.05;
         double ☃xx = (double)((float)☃.func_177952_p() + ☃.nextFloat());
         ☃.func_195594_a(Particles.field_197618_k, ☃, ☃x, ☃xx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return ☃.nextInt(20) == 0 ? 1 : 0;
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      Block ☃ = ☃.func_177230_c();
      if (☃ == Blocks.field_196642_W) {
         return Blocks.field_196674_t;
      } else if (☃ == Blocks.field_196645_X) {
         return Blocks.field_196675_u;
      } else if (☃ == Blocks.field_196647_Y) {
         return Blocks.field_196676_v;
      } else if (☃ == Blocks.field_196648_Z) {
         return Blocks.field_196678_w;
      } else if (☃ == Blocks.field_196572_aa) {
         return Blocks.field_196679_x;
      } else {
         return ☃ == Blocks.field_196574_ab ? Blocks.field_196680_y : Blocks.field_196674_t;
      }
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      if (!☃.field_72995_K) {
         int ☃ = this.func_196472_i(☃);
         if (☃ > 0) {
            ☃ -= 2 << ☃;
            if (☃ < 10) {
               ☃ = 10;
            }
         }

         if (☃.field_73012_v.nextInt(☃) == 0) {
            func_180635_a(☃, ☃, new ItemStack(this.func_199769_a(☃, ☃, ☃, ☃)));
         }

         ☃ = 200;
         if (☃ > 0) {
            ☃ -= 10 << ☃;
            if (☃ < 40) {
               ☃ = 40;
            }
         }

         this.func_196474_a(☃, ☃, ☃, ☃);
      }
   }

   protected void func_196474_a(World var1, BlockPos var2, IBlockState var3, int var4) {
      if ((☃.func_177230_c() == Blocks.field_196642_W || ☃.func_177230_c() == Blocks.field_196574_ab) && ☃.field_73012_v.nextInt(☃) == 0) {
         func_180635_a(☃, ☃, new ItemStack(Items.field_151034_e));
      }
   }

   protected int func_196472_i(IBlockState var1) {
      return ☃.func_177230_c() == Blocks.field_196648_Z ? 40 : 20;
   }

   public static void func_196475_b(boolean var0) {
      field_196478_c = ☃;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return field_196478_c ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
   }

   @Override
   public boolean func_176214_u(IBlockState var1) {
      return false;
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      if (!☃.field_72995_K && ☃.func_77973_b() == Items.field_151097_aZ) {
         ☃.func_71029_a(StatList.field_188065_ae.func_199076_b(this));
         ☃.func_71020_j(0.005F);
         func_180635_a(☃, ☃, new ItemStack(this));
      } else {
         super.func_180657_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_208494_a, field_208495_b);
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return func_208493_b(this.func_176223_P().func_206870_a(field_208495_b, Boolean.valueOf(true)), ☃.func_195991_k(), ☃.func_195995_a());
   }
}
