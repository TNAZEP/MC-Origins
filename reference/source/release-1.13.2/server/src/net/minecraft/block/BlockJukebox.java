package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityJukebox;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockJukebox extends BlockContainer {
   public static final BooleanProperty field_176432_a = BlockStateProperties.field_208187_n;

   protected BlockJukebox(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176432_a, Boolean.valueOf(false)));
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.func_177229_b(field_176432_a)) {
         this.func_203419_a(☃, ☃);
         ☃ = ☃.func_206870_a(field_176432_a, Boolean.valueOf(false));
         ☃.func_180501_a(☃, ☃, 2);
         return true;
      } else {
         return false;
      }
   }

   public void func_176431_a(IWorld var1, BlockPos var2, IBlockState var3, ItemStack var4) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (☃ instanceof TileEntityJukebox) {
         ((TileEntityJukebox)☃).func_195535_a(☃.func_77946_l());
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176432_a, Boolean.valueOf(true)), 2);
      }
   }

   private void func_203419_a(World var1, BlockPos var2) {
      if (!☃.field_72995_K) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityJukebox) {
            TileEntityJukebox ☃x = (TileEntityJukebox)☃;
            ItemStack ☃xx = ☃x.func_195537_c();
            if (!☃xx.func_190926_b()) {
               ☃.func_175718_b(1010, ☃, 0);
               ☃.func_184149_a(☃, null);
               ☃x.func_195535_a(ItemStack.field_190927_a);
               float ☃xxx = 0.7F;
               double ☃xxxx = (double)(☃.field_73012_v.nextFloat() * 0.7F) + 0.15F;
               double ☃xxxxx = (double)(☃.field_73012_v.nextFloat() * 0.7F) + 0.060000002F + 0.6;
               double ☃xxxxxx = (double)(☃.field_73012_v.nextFloat() * 0.7F) + 0.15F;
               ItemStack ☃xxxxxxx = ☃xx.func_77946_l();
               EntityItem ☃xxxxxxxx = new EntityItem(
                  ☃, (double)☃.func_177958_n() + ☃xxxx, (double)☃.func_177956_o() + ☃xxxxx, (double)☃.func_177952_p() + ☃xxxxxx, ☃xxxxxxx
               );
               ☃xxxxxxxx.func_174869_p();
               ☃.func_72838_d(☃xxxxxxxx);
            }
         }
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         this.func_203419_a(☃, ☃);
         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      if (!☃.field_72995_K) {
         super.func_196255_a(☃, ☃, ☃, ☃, 0);
      }
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityJukebox();
   }

   @Override
   public boolean func_149740_M(IBlockState var1) {
      return true;
   }

   @Override
   public int func_180641_l(IBlockState var1, World var2, BlockPos var3) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (☃ instanceof TileEntityJukebox) {
         Item ☃x = ((TileEntityJukebox)☃).func_195537_c().func_77973_b();
         if (☃x instanceof ItemRecord) {
            return ((ItemRecord)☃x).func_195975_g();
         }
      }

      return 0;
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176432_a);
   }
}
