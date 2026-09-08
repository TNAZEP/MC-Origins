package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockStructure extends BlockContainer {
   public static final EnumProperty<StructureMode> field_185587_a = BlockStateProperties.field_208147_av;

   protected BlockStructure(Block.Properties var1) {
      super(☃);
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityStructure();
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      return ☃ instanceof TileEntityStructure ? ((TileEntityStructure)☃).func_189701_a(☃) : false;
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, @Nullable EntityLivingBase var4, ItemStack var5) {
      if (!☃.field_72995_K) {
         if (☃ != null) {
            TileEntity ☃ = ☃.func_175625_s(☃);
            if (☃ instanceof TileEntityStructure) {
               ((TileEntityStructure)☃).func_189720_a(☃);
            }
         }
      }
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 0;
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_185587_a, StructureMode.DATA);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185587_a);
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (!☃.field_72995_K) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityStructure) {
            TileEntityStructure ☃x = (TileEntityStructure)☃;
            boolean ☃xx = ☃.func_175640_z(☃);
            boolean ☃xxx = ☃x.func_189722_G();
            if (☃xx && !☃xxx) {
               ☃x.func_189723_d(true);
               this.func_189874_a(☃x);
            } else if (!☃xx && ☃xxx) {
               ☃x.func_189723_d(false);
            }
         }
      }
   }

   private void func_189874_a(TileEntityStructure var1) {
      switch(☃.func_189700_k()) {
         case SAVE:
            ☃.func_189712_b(false);
            break;
         case LOAD:
            ☃.func_189714_c(false);
            break;
         case CORNER:
            ☃.func_189706_E();
         case DATA:
      }
   }
}
