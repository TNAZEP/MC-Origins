package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockMagma extends Block {
   public BlockMagma(Block.Properties var1) {
      super(☃);
   }

   @Override
   public void func_176199_a(World var1, BlockPos var2, Entity var3) {
      if (!☃.func_70045_F() && ☃ instanceof EntityLivingBase && !EnchantmentHelper.func_189869_j((EntityLivingBase)☃)) {
         ☃.func_70097_a(DamageSource.field_190095_e, 1.0F);
      }

      super.func_176199_a(☃, ☃, ☃);
   }

   @Override
   public int func_185484_c(IBlockState var1, IWorldReader var2, BlockPos var3) {
      return 15728880;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      BlockBubbleColumn.func_203159_a(☃, ☃.func_177984_a(), true);
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃ == EnumFacing.UP && ☃.func_177230_c() == Blocks.field_150355_j) {
         ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_196265_a(IBlockState var1, World var2, BlockPos var3, Random var4) {
      BlockPos ☃ = ☃.func_177984_a();
      if (☃.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a)) {
         ☃.func_184133_a(
            null, ☃, SoundEvents.field_187646_bt, SoundCategory.BLOCKS, 0.5F, 2.6F + (☃.field_73012_v.nextFloat() - ☃.field_73012_v.nextFloat()) * 0.8F
         );
         if (☃ instanceof WorldServer) {
            ((WorldServer)☃)
               .func_195598_a(
                  Particles.field_197594_E,
                  (double)☃.func_177958_n() + 0.5,
                  (double)☃.func_177956_o() + 0.25,
                  (double)☃.func_177952_p() + 0.5,
                  8,
                  0.5,
                  0.25,
                  0.5,
                  0.0
               );
         }
      }
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 20;
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
   }

   @Override
   public boolean func_189872_a(IBlockState var1, Entity var2) {
      return ☃.func_70045_F();
   }

   @Override
   public boolean func_201783_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return true;
   }
}
