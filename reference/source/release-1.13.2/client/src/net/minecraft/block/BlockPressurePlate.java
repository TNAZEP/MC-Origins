package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockPressurePlate extends BlockBasePressurePlate {
   public static final BooleanProperty field_176580_a = BlockStateProperties.field_208194_u;
   private final BlockPressurePlate.Sensitivity field_150069_a;

   protected BlockPressurePlate(BlockPressurePlate.Sensitivity var1, Block.Properties var2) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176580_a, Boolean.valueOf(false)));
      this.field_150069_a = ☃;
   }

   @Override
   protected int func_176576_e(IBlockState var1) {
      return ☃.func_177229_b(field_176580_a) ? 15 : 0;
   }

   @Override
   protected IBlockState func_176575_a(IBlockState var1, int var2) {
      return ☃.func_206870_a(field_176580_a, Boolean.valueOf(☃ > 0));
   }

   @Override
   protected void func_185507_b(IWorld var1, BlockPos var2) {
      if (this.field_149764_J == Material.field_151575_d) {
         ☃.func_184133_a(null, ☃, SoundEvents.field_187895_gX, SoundCategory.BLOCKS, 0.3F, 0.8F);
      } else {
         ☃.func_184133_a(null, ☃, SoundEvents.field_187901_ga, SoundCategory.BLOCKS, 0.3F, 0.6F);
      }
   }

   @Override
   protected void func_185508_c(IWorld var1, BlockPos var2) {
      if (this.field_149764_J == Material.field_151575_d) {
         ☃.func_184133_a(null, ☃, SoundEvents.field_187893_gW, SoundCategory.BLOCKS, 0.3F, 0.7F);
      } else {
         ☃.func_184133_a(null, ☃, SoundEvents.field_187847_fZ, SoundCategory.BLOCKS, 0.3F, 0.5F);
      }
   }

   @Override
   protected int func_180669_e(World var1, BlockPos var2) {
      AxisAlignedBB ☃x = field_185511_c.func_186670_a(☃);
      List<? extends Entity> ☃;
      switch(this.field_150069_a) {
         case EVERYTHING:
            ☃ = ☃.func_72839_b(null, ☃x);
            break;
         case MOBS:
            ☃ = ☃.func_72872_a(EntityLivingBase.class, ☃x);
            break;
         default:
            return 0;
      }

      if (!☃.isEmpty()) {
         for(Entity ☃ : ☃) {
            if (!☃.func_145773_az()) {
               return 15;
            }
         }
      }

      return 0;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176580_a);
   }

   public static enum Sensitivity {
      EVERYTHING,
      MOBS;
   }
}
