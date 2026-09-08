package net.minecraft.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ChestType;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockChest extends BlockContainer implements IBucketPickupHandler, ILiquidContainer {
   public static final DirectionProperty field_176459_a = BlockHorizontal.field_185512_D;
   public static final EnumProperty<ChestType> field_196314_b = BlockStateProperties.field_208140_ao;
   public static final BooleanProperty field_204511_c = BlockStateProperties.field_208198_y;
   protected static final VoxelShape field_196316_c = Block.func_208617_a(1.0, 0.0, 0.0, 15.0, 14.0, 15.0);
   protected static final VoxelShape field_196317_y = Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 14.0, 16.0);
   protected static final VoxelShape field_196318_z = Block.func_208617_a(0.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   protected static final VoxelShape field_196313_A = Block.func_208617_a(1.0, 0.0, 1.0, 16.0, 14.0, 15.0);
   protected static final VoxelShape field_196315_B = Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);

   protected BlockChest(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176459_a, EnumFacing.NORTH)
            .func_206870_a(field_196314_b, ChestType.SINGLE)
            .func_206870_a(field_204511_c, Boolean.valueOf(false))
      );
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_190946_v(IBlockState var1) {
      return true;
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_177229_b(field_204511_c)) {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
      }

      if (☃.func_177230_c() == this && ☃.func_176740_k().func_176722_c()) {
         ChestType ☃ = ☃.func_177229_b(field_196314_b);
         if (☃.func_177229_b(field_196314_b) == ChestType.SINGLE
            && ☃ != ChestType.SINGLE
            && ☃.func_177229_b(field_176459_a) == ☃.func_177229_b(field_176459_a)
            && func_196311_i(☃) == ☃.func_176734_d()) {
            return ☃.func_206870_a(field_196314_b, ☃.func_208081_a());
         }
      } else if (func_196311_i(☃) == ☃) {
         return ☃.func_206870_a(field_196314_b, ChestType.SINGLE);
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      if (☃.func_177229_b(field_196314_b) == ChestType.SINGLE) {
         return field_196315_B;
      } else {
         switch(func_196311_i(☃)) {
            case NORTH:
            default:
               return field_196316_c;
            case SOUTH:
               return field_196317_y;
            case WEST:
               return field_196318_z;
            case EAST:
               return field_196313_A;
         }
      }
   }

   public static EnumFacing func_196311_i(IBlockState var0) {
      EnumFacing ☃ = ☃.func_177229_b(field_176459_a);
      return ☃.func_177229_b(field_196314_b) == ChestType.LEFT ? ☃.func_176746_e() : ☃.func_176735_f();
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      ChestType ☃ = ChestType.SINGLE;
      EnumFacing ☃x = ☃.func_195992_f().func_176734_d();
      IFluidState ☃xx = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
      boolean ☃xxx = ☃.func_195998_g();
      EnumFacing ☃xxxx = ☃.func_196000_l();
      if (☃xxxx.func_176740_k().func_176722_c() && ☃xxx) {
         EnumFacing ☃xxxxx = this.func_196312_a(☃, ☃xxxx.func_176734_d());
         if (☃xxxxx != null && ☃xxxxx.func_176740_k() != ☃xxxx.func_176740_k()) {
            ☃x = ☃xxxxx;
            ☃ = ☃xxxxx.func_176735_f() == ☃xxxx.func_176734_d() ? ChestType.RIGHT : ChestType.LEFT;
         }
      }

      if (☃ == ChestType.SINGLE && !☃xxx) {
         if (☃x == this.func_196312_a(☃, ☃x.func_176746_e())) {
            ☃ = ChestType.LEFT;
         } else if (☃x == this.func_196312_a(☃, ☃x.func_176735_f())) {
            ☃ = ChestType.RIGHT;
         }
      }

      return this.func_176223_P()
         .func_206870_a(field_176459_a, ☃x)
         .func_206870_a(field_196314_b, ☃)
         .func_206870_a(field_204511_c, Boolean.valueOf(☃xx.func_206886_c() == Fluids.field_204546_a));
   }

   @Override
   public Fluid func_204508_a(IWorld var1, BlockPos var2, IBlockState var3) {
      if (☃.func_177229_b(field_204511_c)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_204511_c, Boolean.valueOf(false)), 3);
         return Fluids.field_204546_a;
      } else {
         return Fluids.field_204541_a;
      }
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return ☃.func_177229_b(field_204511_c) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(☃);
   }

   @Override
   public boolean func_204510_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      return !☃.func_177229_b(field_204511_c) && ☃ == Fluids.field_204546_a;
   }

   @Override
   public boolean func_204509_a(IWorld var1, BlockPos var2, IBlockState var3, IFluidState var4) {
      if (!☃.func_177229_b(field_204511_c) && ☃.func_206886_c() == Fluids.field_204546_a) {
         if (!☃.func_201670_d()) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_204511_c, Boolean.valueOf(true)), 3);
            ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   private EnumFacing func_196312_a(BlockItemUseContext var1, EnumFacing var2) {
      IBlockState ☃ = ☃.func_195991_k().func_180495_p(☃.func_195995_a().func_177972_a(☃));
      return ☃.func_177230_c() == this && ☃.func_177229_b(field_196314_b) == ChestType.SINGLE ? ☃.func_177229_b(field_176459_a) : null;
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (☃.func_82837_s()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityChest) {
            ((TileEntityChest)☃).func_200226_a(☃.func_200301_q());
         }
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof IInventory) {
            InventoryHelper.func_180175_a(☃, ☃, (IInventory)☃);
            ☃.func_175666_e(☃, this);
         }

         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.field_72995_K) {
         return true;
      } else {
         ILockableContainer ☃ = this.func_196309_a(☃, ☃, ☃, false);
         if (☃ != null) {
            ☃.func_71007_a(☃);
            ☃.func_71029_a(this.func_196310_d());
         }

         return true;
      }
   }

   protected Stat<ResourceLocation> func_196310_d() {
      return StatList.field_199092_j.func_199076_b(StatList.field_188063_ac);
   }

   @Nullable
   public ILockableContainer func_196309_a(IBlockState var1, World var2, BlockPos var3, boolean var4) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (!(☃ instanceof TileEntityChest)) {
         return null;
      } else if (!☃ && this.func_176457_m(☃, ☃)) {
         return null;
      } else {
         ILockableContainer ☃ = (TileEntityChest)☃;
         ChestType ☃x = ☃.func_177229_b(field_196314_b);
         if (☃x == ChestType.SINGLE) {
            return ☃;
         } else {
            BlockPos ☃ = ☃.func_177972_a(func_196311_i(☃));
            IBlockState ☃x = ☃.func_180495_p(☃);
            if (☃x.func_177230_c() == this) {
               ChestType ☃xx = ☃x.func_177229_b(field_196314_b);
               if (☃xx != ChestType.SINGLE && ☃x != ☃xx && ☃x.func_177229_b(field_176459_a) == ☃.func_177229_b(field_176459_a)) {
                  if (!☃ && this.func_176457_m(☃, ☃)) {
                     return null;
                  }

                  TileEntity ☃xxx = ☃.func_175625_s(☃);
                  if (☃xxx instanceof TileEntityChest) {
                     ILockableContainer ☃xxxx = ☃x == ChestType.RIGHT ? ☃ : (ILockableContainer)☃xxx;
                     ILockableContainer ☃xxxxx = ☃x == ChestType.RIGHT ? (ILockableContainer)☃xxx : ☃;
                     ☃ = new InventoryLargeChest(new TextComponentTranslation("container.chestDouble"), ☃xxxx, ☃xxxxx);
                  }
               }
            }

            return ☃;
         }
      }
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityChest();
   }

   private boolean func_176457_m(World var1, BlockPos var2) {
      return this.func_176456_n(☃, ☃) || this.func_176453_o(☃, ☃);
   }

   private boolean func_176456_n(IBlockReader var1, BlockPos var2) {
      return ☃.func_180495_p(☃.func_177984_a()).func_185915_l();
   }

   private boolean func_176453_o(World var1, BlockPos var2) {
      List<EntityOcelot> ☃ = ☃.func_72872_a(
         EntityOcelot.class,
         new AxisAlignedBB(
            (double)☃.func_177958_n(),
            (double)(☃.func_177956_o() + 1),
            (double)☃.func_177952_p(),
            (double)(☃.func_177958_n() + 1),
            (double)(☃.func_177956_o() + 2),
            (double)(☃.func_177952_p() + 1)
         )
      );
      if (!☃.isEmpty()) {
         for(EntityOcelot ☃x : ☃) {
            if (☃x.func_70906_o()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean func_149740_M(IBlockState var1) {
      return true;
   }

   @Override
   public int func_180641_l(IBlockState var1, World var2, BlockPos var3) {
      return Container.func_94526_b(this.func_196309_a(☃, ☃, ☃, false));
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176459_a, ☃.func_185831_a(☃.func_177229_b(field_176459_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176459_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176459_a, field_196314_b, field_204511_c);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
