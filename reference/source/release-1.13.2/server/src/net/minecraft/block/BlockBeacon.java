package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class BlockBeacon extends BlockContainer {
   public BlockBeacon(Block.Properties var1) {
      super(☃);
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityBeacon();
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.field_72995_K) {
         return true;
      } else {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityBeacon) {
            ☃.func_71007_a((TileEntityBeacon)☃);
            ☃.func_195066_a(StatList.field_188082_P);
         }

         return true;
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (☃.func_82837_s()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityBeacon) {
            ((TileEntityBeacon)☃).func_200227_a(☃.func_200301_q());
         }
      }
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   public static void func_176450_d(World var0, BlockPos var1) {
      HttpUtil.field_180193_a.submit((Runnable)(() -> {
         Chunk ☃ = ☃.func_175726_f(☃);

         for(int ☃x = ☃.func_177956_o() - 1; ☃x >= 0; --☃x) {
            BlockPos ☃xx = new BlockPos(☃.func_177958_n(), ☃x, ☃.func_177952_p());
            if (!☃.func_177444_d(☃xx)) {
               break;
            }

            IBlockState ☃xx = ☃.func_180495_p(☃xx);
            if (☃xx.func_177230_c() == Blocks.field_150461_bJ) {
               ((WorldServer)☃).func_152344_a(() -> {
                  TileEntity ☃ = ☃.func_175625_s(☃);
                  if (☃ instanceof TileEntityBeacon) {
                     ((TileEntityBeacon)☃).func_174908_m();
                     ☃.func_175641_c(☃, Blocks.field_150461_bJ, 1, 0);
                  }
               });
            }
         }
      }));
   }
}
