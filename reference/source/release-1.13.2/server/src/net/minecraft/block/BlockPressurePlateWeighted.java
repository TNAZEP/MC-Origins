package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate {
   public static final IntegerProperty field_176579_a = BlockStateProperties.field_208136_ak;
   private final int field_150068_a;

   protected BlockPressurePlateWeighted(int var1, Block.Properties var2) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176579_a, Integer.valueOf(0)));
      this.field_150068_a = ☃;
   }

   @Override
   protected int func_180669_e(World var1, BlockPos var2) {
      int ☃ = Math.min(☃.func_72872_a(Entity.class, field_185511_c.func_186670_a(☃)).size(), this.field_150068_a);
      if (☃ > 0) {
         float ☃x = (float)Math.min(this.field_150068_a, ☃) / (float)this.field_150068_a;
         return MathHelper.func_76123_f(☃x * 15.0F);
      } else {
         return 0;
      }
   }

   @Override
   protected void func_185507_b(IWorld var1, BlockPos var2) {
      ☃.func_184133_a(null, ☃, SoundEvents.field_187776_dp, SoundCategory.BLOCKS, 0.3F, 0.90000004F);
   }

   @Override
   protected void func_185508_c(IWorld var1, BlockPos var2) {
      ☃.func_184133_a(null, ☃, SoundEvents.field_187774_do, SoundCategory.BLOCKS, 0.3F, 0.75F);
   }

   @Override
   protected int func_176576_e(IBlockState var1) {
      return ☃.func_177229_b(field_176579_a);
   }

   @Override
   protected IBlockState func_176575_a(IBlockState var1, int var2) {
      return ☃.func_206870_a(field_176579_a, Integer.valueOf(☃));
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 10;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176579_a);
   }
}
