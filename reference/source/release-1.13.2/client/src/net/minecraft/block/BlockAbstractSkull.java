package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class BlockAbstractSkull extends BlockContainer {
   private final BlockSkull.ISkullType field_196293_a;

   public BlockAbstractSkull(BlockSkull.ISkullType var1, Block.Properties var2) {
      super(☃);
      this.field_196293_a = ☃;
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
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntitySkull();
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
   }

   @Override
   public void func_176208_a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (!☃.field_72995_K && ☃.field_71075_bZ.field_75098_d) {
         TileEntitySkull.func_195486_a(☃, ☃);
      }

      super.func_176208_a(☃, ☃, ☃, ☃);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c() && !☃.field_72995_K) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntitySkull) {
            TileEntitySkull ☃x = (TileEntitySkull)☃;
            if (☃x.func_195487_d()) {
               ItemStack ☃xx = this.func_185473_a(☃, ☃, ☃);
               Block ☃xxx = ☃x.func_195044_w().func_177230_c();
               if ((☃xxx == Blocks.field_196710_eS || ☃xxx == Blocks.field_196709_eR) && ☃x.func_152108_a() != null) {
                  NBTTagCompound ☃xxxx = new NBTTagCompound();
                  NBTUtil.func_180708_a(☃xxxx, ☃x.func_152108_a());
                  ☃xx.func_196082_o().func_74782_a("SkullOwner", ☃xxxx);
               }

               func_180635_a(☃, ☃, ☃xx);
            }
         }

         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   public BlockSkull.ISkullType func_196292_N_() {
      return this.field_196293_a;
   }
}
