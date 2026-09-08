package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ComparatorMode;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityComparator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider {
   public static final EnumProperty<ComparatorMode> field_176463_b = BlockStateProperties.field_208141_ap;

   public BlockRedstoneComparator(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_185512_D, EnumFacing.NORTH)
            .func_206870_a(field_196348_c, Boolean.valueOf(false))
            .func_206870_a(field_176463_b, ComparatorMode.COMPARE)
      );
   }

   @Override
   protected int func_196346_i(IBlockState var1) {
      return 2;
   }

   @Override
   protected int func_176408_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      return ☃ instanceof TileEntityComparator ? ((TileEntityComparator)☃).func_145996_a() : 0;
   }

   private int func_176460_j(World var1, BlockPos var2, IBlockState var3) {
      return ☃.func_177229_b(field_176463_b) == ComparatorMode.SUBTRACT
         ? Math.max(this.func_176397_f(☃, ☃, ☃) - this.func_176407_c(☃, ☃, ☃), 0)
         : this.func_176397_f(☃, ☃, ☃);
   }

   @Override
   protected boolean func_176404_e(World var1, BlockPos var2, IBlockState var3) {
      int ☃ = this.func_176397_f(☃, ☃, ☃);
      if (☃ >= 15) {
         return true;
      } else if (☃ == 0) {
         return false;
      } else {
         return ☃ >= this.func_176407_c(☃, ☃, ☃);
      }
   }

   @Override
   protected void func_211326_a(World var1, BlockPos var2) {
      ☃.func_175713_t(☃);
   }

   @Override
   protected int func_176397_f(World var1, BlockPos var2, IBlockState var3) {
      int ☃ = super.func_176397_f(☃, ☃, ☃);
      EnumFacing ☃x = ☃.func_177229_b(field_185512_D);
      BlockPos ☃xx = ☃.func_177972_a(☃x);
      IBlockState ☃xxx = ☃.func_180495_p(☃xx);
      if (☃xxx.func_185912_n()) {
         ☃ = ☃xxx.func_185888_a(☃, ☃xx);
      } else if (☃ < 15 && ☃xxx.func_185915_l()) {
         ☃xx = ☃xx.func_177972_a(☃x);
         ☃xxx = ☃.func_180495_p(☃xx);
         if (☃xxx.func_185912_n()) {
            ☃ = ☃xxx.func_185888_a(☃, ☃xx);
         } else if (☃xxx.func_196958_f()) {
            EntityItemFrame ☃ = this.func_176461_a(☃, ☃x, ☃xx);
            if (☃ != null) {
               ☃ = ☃.func_174866_q();
            }
         }
      }

      return ☃;
   }

   @Nullable
   private EntityItemFrame func_176461_a(World var1, EnumFacing var2, BlockPos var3) {
      List<EntityItemFrame> ☃ = ☃.func_175647_a(
         EntityItemFrame.class,
         new AxisAlignedBB(
            (double)☃.func_177958_n(),
            (double)☃.func_177956_o(),
            (double)☃.func_177952_p(),
            (double)(☃.func_177958_n() + 1),
            (double)(☃.func_177956_o() + 1),
            (double)(☃.func_177952_p() + 1)
         ),
         var1x -> var1x != null && var1x.func_174811_aO() == ☃
      );
      return ☃.size() == 1 ? (EntityItemFrame)☃.get(0) : null;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (!☃.field_71075_bZ.field_75099_e) {
         return false;
      } else {
         ☃ = ☃.func_177231_a(field_176463_b);
         float ☃ = ☃.func_177229_b(field_176463_b) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
         ☃.func_184133_a(☃, ☃, SoundEvents.field_187556_aj, SoundCategory.BLOCKS, 0.3F, ☃);
         ☃.func_180501_a(☃, ☃, 2);
         this.func_176462_k(☃, ☃, ☃);
         return true;
      }
   }

   @Override
   protected void func_176398_g(World var1, BlockPos var2, IBlockState var3) {
      if (!☃.func_205220_G_().func_205361_b(☃, this)) {
         int ☃ = this.func_176460_j(☃, ☃, ☃);
         TileEntity ☃x = ☃.func_175625_s(☃);
         int ☃xx = ☃x instanceof TileEntityComparator ? ((TileEntityComparator)☃x).func_145996_a() : 0;
         if (☃ != ☃xx || ☃.func_177229_b(field_196348_c) != this.func_176404_e(☃, ☃, ☃)) {
            TickPriority ☃xxx = this.func_176402_i(☃, ☃, ☃) ? TickPriority.HIGH : TickPriority.NORMAL;
            ☃.func_205220_G_().func_205362_a(☃, this, 2, ☃xxx);
         }
      }
   }

   private void func_176462_k(World var1, BlockPos var2, IBlockState var3) {
      int ☃ = this.func_176460_j(☃, ☃, ☃);
      TileEntity ☃x = ☃.func_175625_s(☃);
      int ☃xx = 0;
      if (☃x instanceof TileEntityComparator) {
         TileEntityComparator ☃xxx = (TileEntityComparator)☃x;
         ☃xx = ☃xxx.func_145996_a();
         ☃xxx.func_145995_a(☃);
      }

      if (☃xx != ☃ || ☃.func_177229_b(field_176463_b) == ComparatorMode.COMPARE) {
         boolean ☃ = this.func_176404_e(☃, ☃, ☃);
         boolean ☃x = ☃.func_177229_b(field_196348_c);
         if (☃x && !☃) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_196348_c, Boolean.valueOf(false)), 2);
         } else if (!☃x && ☃) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_196348_c, Boolean.valueOf(true)), 2);
         }

         this.func_176400_h(☃, ☃, ☃);
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      this.func_176462_k(☃, ☃, ☃);
   }

   @Override
   public boolean func_189539_a(IBlockState var1, World var2, BlockPos var3, int var4, int var5) {
      super.func_189539_a(☃, ☃, ☃, ☃, ☃);
      TileEntity ☃ = ☃.func_175625_s(☃);
      return ☃ != null && ☃.func_145842_c(☃, ☃);
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityComparator();
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185512_D, field_176463_b, field_196348_c);
   }
}
