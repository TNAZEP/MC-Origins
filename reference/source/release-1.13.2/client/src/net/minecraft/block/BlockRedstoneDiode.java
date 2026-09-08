package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

public abstract class BlockRedstoneDiode extends BlockHorizontal {
   protected static final VoxelShape field_196347_b = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   public static final BooleanProperty field_196348_c = BlockStateProperties.field_208194_u;

   protected BlockRedstoneDiode(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196347_b;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      return ☃.func_180495_p(☃.func_177977_b()).func_185896_q();
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!this.func_176405_b(☃, ☃, ☃)) {
         boolean ☃ = ☃.func_177229_b(field_196348_c);
         boolean ☃x = this.func_176404_e(☃, ☃, ☃);
         if (☃ && !☃x) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_196348_c, Boolean.valueOf(false)), 2);
         } else if (!☃) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_196348_c, Boolean.valueOf(true)), 2);
            if (!☃x) {
               ☃.func_205220_G_().func_205362_a(☃, this, this.func_196346_i(☃), TickPriority.HIGH);
            }
         }
      }
   }

   @Override
   public int func_176211_b(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_185911_a(☃, ☃, ☃);
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      if (!☃.func_177229_b(field_196348_c)) {
         return 0;
      } else {
         return ☃.func_177229_b(field_185512_D) == ☃ ? this.func_176408_a(☃, ☃, ☃) : 0;
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (☃.func_196955_c(☃, ☃)) {
         this.func_176398_g(☃, ☃, ☃);
      } else {
         ☃.func_196949_c(☃, ☃, 0);
         ☃.func_175698_g(☃);

         for(EnumFacing ☃ : EnumFacing.values()) {
            ☃.func_195593_d(☃.func_177972_a(☃), this);
         }
      }
   }

   protected void func_176398_g(World var1, BlockPos var2, IBlockState var3) {
      if (!this.func_176405_b(☃, ☃, ☃)) {
         boolean ☃ = ☃.func_177229_b(field_196348_c);
         boolean ☃x = this.func_176404_e(☃, ☃, ☃);
         if (☃ != ☃x && !☃.func_205220_G_().func_205361_b(☃, this)) {
            TickPriority ☃xx = TickPriority.HIGH;
            if (this.func_176402_i(☃, ☃, ☃)) {
               ☃xx = TickPriority.EXTREMELY_HIGH;
            } else if (☃) {
               ☃xx = TickPriority.VERY_HIGH;
            }

            ☃.func_205220_G_().func_205362_a(☃, this, this.func_196346_i(☃), ☃xx);
         }
      }
   }

   public boolean func_176405_b(IWorldReaderBase var1, BlockPos var2, IBlockState var3) {
      return false;
   }

   protected boolean func_176404_e(World var1, BlockPos var2, IBlockState var3) {
      return this.func_176397_f(☃, ☃, ☃) > 0;
   }

   protected int func_176397_f(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_185512_D);
      BlockPos ☃x = ☃.func_177972_a(☃);
      int ☃xx = ☃.func_175651_c(☃x, ☃);
      if (☃xx >= 15) {
         return ☃xx;
      } else {
         IBlockState ☃ = ☃.func_180495_p(☃x);
         return Math.max(☃xx, ☃.func_177230_c() == Blocks.field_150488_af ? ☃.func_177229_b(BlockRedstoneWire.field_176351_O) : 0);
      }
   }

   protected int func_176407_c(IWorldReaderBase var1, BlockPos var2, IBlockState var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_185512_D);
      EnumFacing ☃x = ☃.func_176746_e();
      EnumFacing ☃xx = ☃.func_176735_f();
      return Math.max(this.func_176401_c(☃, ☃.func_177972_a(☃x), ☃x), this.func_176401_c(☃, ☃.func_177972_a(☃xx), ☃xx));
   }

   protected int func_176401_c(IWorldReaderBase var1, BlockPos var2, EnumFacing var3) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      Block ☃x = ☃.func_177230_c();
      if (this.func_185545_A(☃)) {
         if (☃x == Blocks.field_150451_bX) {
            return 15;
         } else {
            return ☃x == Blocks.field_150488_af ? ☃.func_177229_b(BlockRedstoneWire.field_176351_O) : ☃.func_175627_a(☃, ☃);
         }
      } else {
         return 0;
      }
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return true;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_185512_D, ☃.func_195992_f().func_176734_d());
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (this.func_176404_e(☃, ☃, ☃)) {
         ☃.func_205220_G_().func_205360_a(☃, this, 1);
      }
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      this.func_176400_h(☃, ☃, ☃);
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (!☃ && ☃.func_177230_c() != ☃.func_177230_c()) {
         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
         this.func_211326_a(☃, ☃);
         this.func_176400_h(☃, ☃, ☃);
      }
   }

   protected void func_211326_a(World var1, BlockPos var2) {
   }

   protected void func_176400_h(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_185512_D);
      BlockPos ☃x = ☃.func_177972_a(☃.func_176734_d());
      ☃.func_190524_a(☃x, this, ☃);
      ☃.func_175695_a(☃x, this, ☃);
   }

   protected boolean func_185545_A(IBlockState var1) {
      return ☃.func_185897_m();
   }

   protected int func_176408_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return 15;
   }

   public static boolean func_185546_B(IBlockState var0) {
      return ☃.func_177230_c() instanceof BlockRedstoneDiode;
   }

   public boolean func_176402_i(IBlockReader var1, BlockPos var2, IBlockState var3) {
      EnumFacing ☃ = ((EnumFacing)☃.func_177229_b(field_185512_D)).func_176734_d();
      IBlockState ☃x = ☃.func_180495_p(☃.func_177972_a(☃));
      return func_185546_B(☃x) && ☃x.func_177229_b(field_185512_D) != ☃;
   }

   protected abstract int func_196346_i(IBlockState var1);

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public boolean func_200124_e(IBlockState var1) {
      return true;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }
}
