package net.minecraft.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BlockSourceImpl;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.PositionImpl;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockDispenser extends BlockContainer {
   public static final DirectionProperty field_176441_a = BlockDirectional.field_176387_N;
   public static final BooleanProperty field_176440_b = BlockStateProperties.field_208197_x;
   private static final Map<Item, IBehaviorDispenseItem> field_149943_a = Util.func_200696_a(
      new Object2ObjectOpenHashMap<>(), var0 -> var0.defaultReturnValue(new BehaviorDefaultDispenseItem())
   );

   public static void func_199774_a(IItemProvider var0, IBehaviorDispenseItem var1) {
      field_149943_a.put(☃.func_199767_j(), ☃);
   }

   protected BlockDispenser(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176441_a, EnumFacing.NORTH).func_206870_a(field_176440_b, Boolean.valueOf(false))
      );
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 4;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.field_72995_K) {
         return true;
      } else {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityDispenser) {
            ☃.func_71007_a((TileEntityDispenser)☃);
            if (☃ instanceof TileEntityDropper) {
               ☃.func_195066_a(StatList.field_188083_Q);
            } else {
               ☃.func_195066_a(StatList.field_188085_S);
            }
         }

         return true;
      }
   }

   protected void func_176439_d(World var1, BlockPos var2) {
      BlockSourceImpl ☃ = new BlockSourceImpl(☃, ☃);
      TileEntityDispenser ☃x = ☃.func_150835_j();
      int ☃xx = ☃x.func_146017_i();
      if (☃xx < 0) {
         ☃.func_175718_b(1001, ☃, 0);
      } else {
         ItemStack ☃ = ☃x.func_70301_a(☃xx);
         IBehaviorDispenseItem ☃x = this.func_149940_a(☃);
         if (☃x != IBehaviorDispenseItem.NOOP) {
            ☃x.func_70299_a(☃xx, ☃x.dispense(☃, ☃));
         }
      }
   }

   protected IBehaviorDispenseItem func_149940_a(ItemStack var1) {
      return (IBehaviorDispenseItem)field_149943_a.get(☃.func_77973_b());
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      boolean ☃ = ☃.func_175640_z(☃) || ☃.func_175640_z(☃.func_177984_a());
      boolean ☃x = ☃.func_177229_b(field_176440_b);
      if (☃ && !☃x) {
         ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176440_b, Boolean.valueOf(true)), 4);
      } else if (!☃ && ☃x) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176440_b, Boolean.valueOf(false)), 4);
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.field_72995_K) {
         this.func_176439_d(☃, ☃);
      }
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityDispenser();
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_176441_a, ☃.func_196010_d().func_176734_d());
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (☃.func_82837_s()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityDispenser) {
            ((TileEntityDispenser)☃).func_200226_a(☃.func_200301_q());
         }
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityDispenser) {
            InventoryHelper.func_180175_a(☃, ☃, (TileEntityDispenser)☃);
            ☃.func_175666_e(☃, this);
         }

         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   public static IPosition func_149939_a(IBlockSource var0) {
      EnumFacing ☃ = ☃.func_189992_e().func_177229_b(field_176441_a);
      double ☃x = ☃.func_82615_a() + 0.7 * (double)☃.func_82601_c();
      double ☃xx = ☃.func_82617_b() + 0.7 * (double)☃.func_96559_d();
      double ☃xxx = ☃.func_82616_c() + 0.7 * (double)☃.func_82599_e();
      return new PositionImpl(☃x, ☃xx, ☃xxx);
   }

   @Override
   public boolean func_149740_M(IBlockState var1) {
      return true;
   }

   @Override
   public int func_180641_l(IBlockState var1, World var2, BlockPos var3) {
      return Container.func_178144_a(☃.func_175625_s(☃));
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176441_a, ☃.func_185831_a(☃.func_177229_b(field_176441_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176441_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176441_a, field_176440_b);
   }
}
