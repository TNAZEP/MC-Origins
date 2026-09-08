package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityTurtle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockTurtleEgg extends Block {
   private static final VoxelShape field_203172_c = Block.func_208617_a(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
   private static final VoxelShape field_206843_t = Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
   public static final IntegerProperty field_203170_a = BlockStateProperties.field_208128_ac;
   public static final IntegerProperty field_203171_b = BlockStateProperties.field_208127_ab;

   public BlockTurtleEgg(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_203170_a, Integer.valueOf(0)).func_206870_a(field_203171_b, Integer.valueOf(1))
      );
   }

   @Override
   public void func_176199_a(World var1, BlockPos var2, Entity var3) {
      this.func_203167_a(☃, ☃, ☃, 100);
      super.func_176199_a(☃, ☃, ☃);
   }

   @Override
   public void func_180658_a(World var1, BlockPos var2, Entity var3, float var4) {
      if (!(☃ instanceof EntityZombie)) {
         this.func_203167_a(☃, ☃, ☃, 3);
      }

      super.func_180658_a(☃, ☃, ☃, ☃);
   }

   private void func_203167_a(World var1, BlockPos var2, Entity var3, int var4) {
      if (!this.func_212570_a(☃, ☃)) {
         super.func_176199_a(☃, ☃, ☃);
      } else {
         if (!☃.field_72995_K && ☃.field_73012_v.nextInt(☃) == 0) {
            this.func_203166_c(☃, ☃, ☃.func_180495_p(☃));
         }
      }
   }

   private void func_203166_c(World var1, BlockPos var2, IBlockState var3) {
      ☃.func_184133_a(null, ☃, SoundEvents.field_203281_iz, SoundCategory.BLOCKS, 0.7F, 0.9F + ☃.field_73012_v.nextFloat() * 0.2F);
      int ☃ = ☃.func_177229_b(field_203171_b);
      if (☃ <= 1) {
         ☃.func_175655_b(☃, false);
      } else {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_203171_b, Integer.valueOf(☃ - 1)), 2);
         ☃.func_175718_b(2001, ☃, Block.func_196246_j(☃));
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (this.func_203169_a(☃) && this.func_203168_a(☃, ☃)) {
         int ☃ = ☃.func_177229_b(field_203170_a);
         if (☃ < 2) {
            ☃.func_184133_a(null, ☃, SoundEvents.field_203280_iy, SoundCategory.BLOCKS, 0.7F, 0.9F + ☃.nextFloat() * 0.2F);
            ☃.func_180501_a(☃, ☃.func_206870_a(field_203170_a, Integer.valueOf(☃ + 1)), 2);
         } else {
            ☃.func_184133_a(null, ☃, SoundEvents.field_203279_ix, SoundCategory.BLOCKS, 0.7F, 0.9F + ☃.nextFloat() * 0.2F);
            ☃.func_175698_g(☃);
            if (!☃.field_72995_K) {
               for(int ☃ = 0; ☃ < ☃.func_177229_b(field_203171_b); ++☃) {
                  ☃.func_175718_b(2001, ☃, Block.func_196246_j(☃));
                  EntityTurtle ☃x = new EntityTurtle(☃);
                  ☃x.func_70873_a(-24000);
                  ☃x.func_203011_g(☃);
                  ☃x.func_70012_b((double)☃.func_177958_n() + 0.3 + (double)☃ * 0.2, (double)☃.func_177956_o(), (double)☃.func_177952_p() + 0.3, 0.0F, 0.0F);
                  ☃.func_72838_d(☃x);
               }
            }
         }
      }
   }

   private boolean func_203168_a(IBlockReader var1, BlockPos var2) {
      return ☃.func_180495_p(☃.func_177977_b()).func_177230_c() == Blocks.field_150354_m;
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (this.func_203168_a(☃, ☃) && !☃.field_72995_K) {
         ☃.func_175718_b(2005, ☃, 0);
      }
   }

   private boolean func_203169_a(World var1) {
      float ☃ = ☃.func_72826_c(1.0F);
      if ((double)☃ < 0.69 && (double)☃ > 0.65) {
         return true;
      } else {
         return ☃.field_73012_v.nextInt(500) == 0;
      }
   }

   @Override
   protected boolean func_149700_E() {
      return true;
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      super.func_180657_a(☃, ☃, ☃, ☃, ☃, ☃);
      this.func_203166_c(☃, ☃, ☃);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   public boolean func_196253_a(IBlockState var1, BlockItemUseContext var2) {
      return ☃.func_195996_i().func_77973_b() == this.func_199767_j() && ☃.func_177229_b(field_203171_b) < 4 ? true : super.func_196253_a(☃, ☃);
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = ☃.func_195991_k().func_180495_p(☃.func_195995_a());
      return ☃.func_177230_c() == this
         ? ☃.func_206870_a(field_203171_b, Integer.valueOf(Math.min(4, ☃.func_177229_b(field_203171_b) + 1)))
         : super.func_196258_a(☃);
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_177229_b(field_203171_b) > 1 ? field_206843_t : field_203172_c;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_203170_a, field_203171_b);
   }

   private boolean func_212570_a(World var1, Entity var2) {
      if (☃ instanceof EntityTurtle) {
         return false;
      } else {
         return ☃ instanceof EntityLivingBase && !(☃ instanceof EntityPlayer) ? ☃.func_82736_K().func_82766_b("mobGriefing") : true;
      }
   }
}
