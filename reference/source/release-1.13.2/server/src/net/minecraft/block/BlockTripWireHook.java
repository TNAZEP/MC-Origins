package net.minecraft.block;

import com.google.common.base.MoreObjects;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockTripWireHook extends Block {
   public static final DirectionProperty field_176264_a = BlockHorizontal.field_185512_D;
   public static final BooleanProperty field_176263_b = BlockStateProperties.field_208194_u;
   public static final BooleanProperty field_176265_M = BlockStateProperties.field_208174_a;
   protected static final VoxelShape field_185743_d = Block.func_208617_a(5.0, 0.0, 10.0, 11.0, 10.0, 16.0);
   protected static final VoxelShape field_185744_e = Block.func_208617_a(5.0, 0.0, 0.0, 11.0, 10.0, 6.0);
   protected static final VoxelShape field_185745_f = Block.func_208617_a(10.0, 0.0, 5.0, 16.0, 10.0, 11.0);
   protected static final VoxelShape field_185746_g = Block.func_208617_a(0.0, 0.0, 5.0, 6.0, 10.0, 11.0);

   public BlockTripWireHook(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176264_a, EnumFacing.NORTH)
            .func_206870_a(field_176263_b, Boolean.valueOf(false))
            .func_206870_a(field_176265_M, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      switch((EnumFacing)☃.func_177229_b(field_176264_a)) {
         case EAST:
         default:
            return field_185746_g;
         case WEST:
            return field_185745_f;
         case SOUTH:
            return field_185744_e;
         case NORTH:
            return field_185743_d;
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_176264_a);
      BlockPos ☃x = ☃.func_177972_a(☃.func_176734_d());
      IBlockState ☃xx = ☃.func_180495_p(☃x);
      boolean ☃xxx = func_193382_c(☃xx.func_177230_c());
      return !☃xxx && ☃.func_176740_k().func_176722_c() && ☃xx.func_193401_d(☃, ☃x, ☃) == BlockFaceShape.SOLID && !☃xx.func_185897_m();
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃.func_176734_d() == ☃.func_177229_b(field_176264_a) && !☃.func_196955_c(☃, ☃)
         ? Blocks.field_150350_a.func_176223_P()
         : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = this.func_176223_P().func_206870_a(field_176263_b, Boolean.valueOf(false)).func_206870_a(field_176265_M, Boolean.valueOf(false));
      IWorldReaderBase ☃x = ☃.func_195991_k();
      BlockPos ☃xx = ☃.func_195995_a();
      EnumFacing[] ☃xxx = ☃.func_196009_e();

      for(EnumFacing ☃xxxx : ☃xxx) {
         if (☃xxxx.func_176740_k().func_176722_c()) {
            EnumFacing ☃xxxxx = ☃xxxx.func_176734_d();
            ☃ = ☃.func_206870_a(field_176264_a, ☃xxxxx);
            if (☃.func_196955_c(☃x, ☃xx)) {
               return ☃;
            }
         }
      }

      return null;
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      this.func_176260_a(☃, ☃, ☃, false, false, -1, null);
   }

   public void func_176260_a(World var1, BlockPos var2, IBlockState var3, boolean var4, boolean var5, int var6, @Nullable IBlockState var7) {
      EnumFacing ☃ = ☃.func_177229_b(field_176264_a);
      boolean ☃x = ☃.func_177229_b(field_176265_M);
      boolean ☃xx = ☃.func_177229_b(field_176263_b);
      boolean ☃xxx = !☃;
      boolean ☃xxxx = false;
      int ☃xxxxx = 0;
      IBlockState[] ☃xxxxxx = new IBlockState[42];

      for(int ☃xxxxxxx = 1; ☃xxxxxxx < 42; ++☃xxxxxxx) {
         BlockPos ☃xxxxxxxx = ☃.func_177967_a(☃, ☃xxxxxxx);
         IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃xxxxxxxx);
         if (☃xxxxxxxxx.func_177230_c() == Blocks.field_150479_bC) {
            if (☃xxxxxxxxx.func_177229_b(field_176264_a) == ☃.func_176734_d()) {
               ☃xxxxx = ☃xxxxxxx;
            }
            break;
         }

         if (☃xxxxxxxxx.func_177230_c() != Blocks.field_150473_bD && ☃xxxxxxx != ☃) {
            ☃xxxxxx[☃xxxxxxx] = null;
            ☃xxx = false;
         } else {
            if (☃xxxxxxx == ☃) {
               ☃xxxxxxxxx = MoreObjects.firstNonNull(☃, ☃xxxxxxxxx);
            }

            boolean ☃xxxxxxxx = !☃xxxxxxxxx.func_177229_b(BlockTripWire.field_176295_N);
            boolean ☃xxxxxxxxx = ☃xxxxxxxxx.func_177229_b(BlockTripWire.field_176293_a);
            ☃xxxx |= ☃xxxxxxxx && ☃xxxxxxxxx;
            ☃xxxxxx[☃xxxxxxx] = ☃xxxxxxxxx;
            if (☃xxxxxxx == ☃) {
               ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
               ☃xxx &= ☃xxxxxxxx;
            }
         }
      }

      ☃xxx &= ☃xxxxx > 1;
      ☃xxxx &= ☃xxx;
      IBlockState ☃xxxxxxx = this.func_176223_P().func_206870_a(field_176265_M, Boolean.valueOf(☃xxx)).func_206870_a(field_176263_b, Boolean.valueOf(☃xxxx));
      if (☃xxxxx > 0) {
         BlockPos ☃xxxxxxxx = ☃.func_177967_a(☃, ☃xxxxx);
         EnumFacing ☃xxxxxxxxx = ☃.func_176734_d();
         ☃.func_180501_a(☃xxxxxxxx, ☃xxxxxxx.func_206870_a(field_176264_a, ☃xxxxxxxxx), 3);
         this.func_176262_b(☃, ☃xxxxxxxx, ☃xxxxxxxxx);
         this.func_180694_a(☃, ☃xxxxxxxx, ☃xxx, ☃xxxx, ☃x, ☃xx);
      }

      this.func_180694_a(☃, ☃, ☃xxx, ☃xxxx, ☃x, ☃xx);
      if (!☃) {
         ☃.func_180501_a(☃, ☃xxxxxxx.func_206870_a(field_176264_a, ☃), 3);
         if (☃) {
            this.func_176262_b(☃, ☃, ☃);
         }
      }

      if (☃x != ☃xxx) {
         for(int ☃xxxxxxx = 1; ☃xxxxxxx < ☃xxxxx; ++☃xxxxxxx) {
            BlockPos ☃xxxxxxxx = ☃.func_177967_a(☃, ☃xxxxxxx);
            IBlockState ☃xxxxxxxxx = ☃xxxxxx[☃xxxxxxx];
            if (☃xxxxxxxxx != null) {
               ☃.func_180501_a(☃xxxxxxxx, ☃xxxxxxxxx.func_206870_a(field_176265_M, Boolean.valueOf(☃xxx)), 3);
               if (!☃.func_180495_p(☃xxxxxxxx).func_196958_f()) {
               }
            }
         }
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      this.func_176260_a(☃, ☃, ☃, false, true, -1, null);
   }

   private void func_180694_a(World var1, BlockPos var2, boolean var3, boolean var4, boolean var5, boolean var6) {
      if (☃ && !☃) {
         ☃.func_184133_a(null, ☃, SoundEvents.field_187907_gg, SoundCategory.BLOCKS, 0.4F, 0.6F);
      } else if (!☃ && ☃) {
         ☃.func_184133_a(null, ☃, SoundEvents.field_187906_gf, SoundCategory.BLOCKS, 0.4F, 0.5F);
      } else if (☃ && !☃) {
         ☃.func_184133_a(null, ☃, SoundEvents.field_187905_ge, SoundCategory.BLOCKS, 0.4F, 0.7F);
      } else if (!☃ && ☃) {
         ☃.func_184133_a(null, ☃, SoundEvents.field_187908_gh, SoundCategory.BLOCKS, 0.4F, 1.2F / (☃.field_73012_v.nextFloat() * 0.2F + 0.9F));
      }
   }

   private void func_176262_b(World var1, BlockPos var2, EnumFacing var3) {
      ☃.func_195593_d(☃, this);
      ☃.func_195593_d(☃.func_177972_a(☃.func_176734_d()), this);
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (!☃ && ☃.func_177230_c() != ☃.func_177230_c()) {
         boolean ☃ = ☃.func_177229_b(field_176265_M);
         boolean ☃x = ☃.func_177229_b(field_176263_b);
         if (☃ || ☃x) {
            this.func_176260_a(☃, ☃, ☃, true, false, -1, null);
         }

         if (☃x) {
            ☃.func_195593_d(☃, this);
            ☃.func_195593_d(☃.func_177972_a(((EnumFacing)☃.func_177229_b(field_176264_a)).func_176734_d()), this);
         }

         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_176263_b) ? 15 : 0;
   }

   @Override
   public int func_176211_b(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      if (!☃.func_177229_b(field_176263_b)) {
         return 0;
      } else {
         return ☃.func_177229_b(field_176264_a) == ☃ ? 15 : 0;
      }
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return true;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176264_a, ☃.func_185831_a(☃.func_177229_b(field_176264_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176264_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176264_a, field_176263_b, field_176265_M);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
