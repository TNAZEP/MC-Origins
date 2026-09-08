package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockDoor extends Block {
   public static final DirectionProperty field_176520_a = BlockHorizontal.field_185512_D;
   public static final BooleanProperty field_176519_b = BlockStateProperties.field_208193_t;
   public static final EnumProperty<DoorHingeSide> field_176521_M = BlockStateProperties.field_208142_aq;
   public static final BooleanProperty field_176522_N = BlockStateProperties.field_208194_u;
   public static final EnumProperty<DoubleBlockHalf> field_176523_O = BlockStateProperties.field_208163_P;
   protected static final VoxelShape field_185658_f = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape field_185659_g = Block.func_208617_a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185656_B = Block.func_208617_a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185657_C = Block.func_208617_a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

   protected BlockDoor(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176520_a, EnumFacing.NORTH)
            .func_206870_a(field_176519_b, Boolean.valueOf(false))
            .func_206870_a(field_176521_M, DoorHingeSide.LEFT)
            .func_206870_a(field_176522_N, Boolean.valueOf(false))
            .func_206870_a(field_176523_O, DoubleBlockHalf.LOWER)
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_176520_a);
      boolean ☃x = !☃.func_177229_b(field_176519_b);
      boolean ☃xx = ☃.func_177229_b(field_176521_M) == DoorHingeSide.RIGHT;
      switch(☃) {
         case EAST:
         default:
            return ☃x ? field_185657_C : (☃xx ? field_185659_g : field_185658_f);
         case SOUTH:
            return ☃x ? field_185658_f : (☃xx ? field_185657_C : field_185656_B);
         case WEST:
            return ☃x ? field_185656_B : (☃xx ? field_185658_f : field_185659_g);
         case NORTH:
            return ☃x ? field_185659_g : (☃xx ? field_185656_B : field_185657_C);
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      DoubleBlockHalf ☃ = ☃.func_177229_b(field_176523_O);
      if (☃.func_176740_k() != EnumFacing.Axis.Y || ☃ == DoubleBlockHalf.LOWER != (☃ == EnumFacing.UP)) {
         return ☃ == DoubleBlockHalf.LOWER && ☃ == EnumFacing.DOWN && !☃.func_196955_c(☃, ☃)
            ? Blocks.field_150350_a.func_176223_P()
            : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      } else {
         return ☃.func_177230_c() == this && ☃.func_177229_b(field_176523_O) != ☃
            ? ☃.func_206870_a(field_176520_a, ☃.func_177229_b(field_176520_a))
               .func_206870_a(field_176519_b, ☃.func_177229_b(field_176519_b))
               .func_206870_a(field_176521_M, ☃.func_177229_b(field_176521_M))
               .func_206870_a(field_176522_N, ☃.func_177229_b(field_176522_N))
            : Blocks.field_150350_a.func_176223_P();
      }
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      super.func_180657_a(☃, ☃, ☃, Blocks.field_150350_a.func_176223_P(), ☃, ☃);
   }

   @Override
   public void func_176208_a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      DoubleBlockHalf ☃ = ☃.func_177229_b(field_176523_O);
      boolean ☃x = ☃ == DoubleBlockHalf.LOWER;
      BlockPos ☃xx = ☃x ? ☃.func_177984_a() : ☃.func_177977_b();
      IBlockState ☃xxx = ☃.func_180495_p(☃xx);
      if (☃xxx.func_177230_c() == this && ☃xxx.func_177229_b(field_176523_O) != ☃) {
         ☃.func_180501_a(☃xx, Blocks.field_150350_a.func_176223_P(), 35);
         ☃.func_180498_a(☃, 2001, ☃xx, Block.func_196246_j(☃xxx));
         if (!☃.field_72995_K && !☃.func_184812_l_()) {
            if (☃x) {
               ☃.func_196949_c(☃, ☃, 0);
            } else {
               ☃xxx.func_196949_c(☃, ☃xx, 0);
            }
         }
      }

      super.func_176208_a(☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      switch(☃) {
         case LAND:
            return ☃.func_177229_b(field_176519_b);
         case WATER:
            return false;
         case AIR:
            return ☃.func_177229_b(field_176519_b);
         default:
            return false;
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   private int func_185654_e() {
      return this.field_149764_J == Material.field_151573_f ? 1011 : 1012;
   }

   private int func_185655_g() {
      return this.field_149764_J == Material.field_151573_f ? 1005 : 1006;
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      BlockPos ☃ = ☃.func_195995_a();
      if (☃.func_177956_o() < 255 && ☃.func_195991_k().func_180495_p(☃.func_177984_a()).func_196953_a(☃)) {
         World ☃x = ☃.func_195991_k();
         boolean ☃xx = ☃x.func_175640_z(☃) || ☃x.func_175640_z(☃.func_177984_a());
         return this.func_176223_P()
            .func_206870_a(field_176520_a, ☃.func_195992_f())
            .func_206870_a(field_176521_M, this.func_208073_b(☃))
            .func_206870_a(field_176522_N, Boolean.valueOf(☃xx))
            .func_206870_a(field_176519_b, Boolean.valueOf(☃xx))
            .func_206870_a(field_176523_O, DoubleBlockHalf.LOWER);
      } else {
         return null;
      }
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      ☃.func_180501_a(☃.func_177984_a(), ☃.func_206870_a(field_176523_O, DoubleBlockHalf.UPPER), 3);
   }

   private DoorHingeSide func_208073_b(BlockItemUseContext var1) {
      IBlockReader ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      EnumFacing ☃xx = ☃.func_195992_f();
      BlockPos ☃xxx = ☃x.func_177984_a();
      EnumFacing ☃xxxx = ☃xx.func_176735_f();
      IBlockState ☃xxxxx = ☃.func_180495_p(☃x.func_177972_a(☃xxxx));
      IBlockState ☃xxxxxx = ☃.func_180495_p(☃xxx.func_177972_a(☃xxxx));
      EnumFacing ☃xxxxxxx = ☃xx.func_176746_e();
      IBlockState ☃xxxxxxxx = ☃.func_180495_p(☃x.func_177972_a(☃xxxxxxx));
      IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃xxx.func_177972_a(☃xxxxxxx));
      int ☃xxxxxxxxxx = (☃xxxxx.func_185898_k() ? -1 : 0)
         + (☃xxxxxx.func_185898_k() ? -1 : 0)
         + (☃xxxxxxxx.func_185898_k() ? 1 : 0)
         + (☃xxxxxxxxx.func_185898_k() ? 1 : 0);
      boolean ☃xxxxxxxxxxx = ☃xxxxx.func_177230_c() == this && ☃xxxxx.func_177229_b(field_176523_O) == DoubleBlockHalf.LOWER;
      boolean ☃xxxxxxxxxxxx = ☃xxxxxxxx.func_177230_c() == this && ☃xxxxxxxx.func_177229_b(field_176523_O) == DoubleBlockHalf.LOWER;
      if ((!☃xxxxxxxxxxx || ☃xxxxxxxxxxxx) && ☃xxxxxxxxxx <= 0) {
         if ((!☃xxxxxxxxxxxx || ☃xxxxxxxxxxx) && ☃xxxxxxxxxx >= 0) {
            int ☃xxxxxxxxxxxxx = ☃xx.func_82601_c();
            int ☃xxxxxxxxxxxxxx = ☃xx.func_82599_e();
            float ☃xxxxxxxxxxxxxxx = ☃.func_195997_m();
            float ☃xxxxxxxxxxxxxxxx = ☃.func_195994_o();
            return (☃xxxxxxxxxxxxx >= 0 || !(☃xxxxxxxxxxxxxxxx < 0.5F))
                  && (☃xxxxxxxxxxxxx <= 0 || !(☃xxxxxxxxxxxxxxxx > 0.5F))
                  && (☃xxxxxxxxxxxxxx >= 0 || !(☃xxxxxxxxxxxxxxx > 0.5F))
                  && (☃xxxxxxxxxxxxxx <= 0 || !(☃xxxxxxxxxxxxxxx < 0.5F))
               ? DoorHingeSide.LEFT
               : DoorHingeSide.RIGHT;
         } else {
            return DoorHingeSide.LEFT;
         }
      } else {
         return DoorHingeSide.RIGHT;
      }
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (this.field_149764_J == Material.field_151573_f) {
         return false;
      } else {
         ☃ = ☃.func_177231_a(field_176519_b);
         ☃.func_180501_a(☃, ☃, 10);
         ☃.func_180498_a(☃, ☃.func_177229_b(field_176519_b) ? this.func_185655_g() : this.func_185654_e(), ☃, 0);
         return true;
      }
   }

   public void func_176512_a(World var1, BlockPos var2, boolean var3) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      if (☃.func_177230_c() == this && ☃.func_177229_b(field_176519_b) != ☃) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176519_b, Boolean.valueOf(☃)), 10);
         this.func_196426_b(☃, ☃, ☃);
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      boolean ☃ = ☃.func_175640_z(☃)
         || ☃.func_175640_z(☃.func_177972_a(☃.func_177229_b(field_176523_O) == DoubleBlockHalf.LOWER ? EnumFacing.UP : EnumFacing.DOWN));
      if (☃ != this && ☃ != ☃.func_177229_b(field_176522_N)) {
         if (☃ != ☃.func_177229_b(field_176519_b)) {
            this.func_196426_b(☃, ☃, ☃);
         }

         ☃.func_180501_a(☃, ☃.func_206870_a(field_176522_N, Boolean.valueOf(☃)).func_206870_a(field_176519_b, Boolean.valueOf(☃)), 2);
      }
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177977_b());
      if (☃.func_177229_b(field_176523_O) == DoubleBlockHalf.LOWER) {
         return ☃.func_185896_q();
      } else {
         return ☃.func_177230_c() == this;
      }
   }

   private void func_196426_b(World var1, BlockPos var2, boolean var3) {
      ☃.func_180498_a(null, ☃ ? this.func_185655_g() : this.func_185654_e(), ☃, 0);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return (IItemProvider)(☃.func_177229_b(field_176523_O) == DoubleBlockHalf.UPPER ? Items.field_190931_a : super.func_199769_a(☃, ☃, ☃, ☃));
   }

   @Override
   public EnumPushReaction func_149656_h(IBlockState var1) {
      return EnumPushReaction.DESTROY;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176520_a, ☃.func_185831_a(☃.func_177229_b(field_176520_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃ == Mirror.NONE ? ☃ : ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176520_a))).func_177231_a(field_176521_M);
   }

   @Override
   public long func_209900_a(IBlockState var1, BlockPos var2) {
      return MathHelper.func_180187_c(
         ☃.func_177958_n(), ☃.func_177979_c(☃.func_177229_b(field_176523_O) == DoubleBlockHalf.LOWER ? 0 : 1).func_177956_o(), ☃.func_177952_p()
      );
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176523_O, field_176520_a, field_176519_b, field_176521_M, field_176522_N);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
