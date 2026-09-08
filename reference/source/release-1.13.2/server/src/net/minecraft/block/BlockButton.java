package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public abstract class BlockButton extends BlockHorizontalFace {
   public static final BooleanProperty field_176584_b = BlockStateProperties.field_208194_u;
   protected static final VoxelShape field_196370_b = Block.func_208617_a(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
   protected static final VoxelShape field_196371_c = Block.func_208617_a(5.0, 14.0, 6.0, 11.0, 16.0, 10.0);
   protected static final VoxelShape field_196376_y = Block.func_208617_a(6.0, 0.0, 5.0, 10.0, 2.0, 11.0);
   protected static final VoxelShape field_196377_z = Block.func_208617_a(5.0, 0.0, 6.0, 11.0, 2.0, 10.0);
   protected static final VoxelShape field_185622_d = Block.func_208617_a(5.0, 6.0, 14.0, 11.0, 10.0, 16.0);
   protected static final VoxelShape field_185624_e = Block.func_208617_a(5.0, 6.0, 0.0, 11.0, 10.0, 2.0);
   protected static final VoxelShape field_185626_f = Block.func_208617_a(14.0, 6.0, 5.0, 16.0, 10.0, 11.0);
   protected static final VoxelShape field_185628_g = Block.func_208617_a(0.0, 6.0, 5.0, 2.0, 10.0, 11.0);
   protected static final VoxelShape field_196372_E = Block.func_208617_a(6.0, 15.0, 5.0, 10.0, 16.0, 11.0);
   protected static final VoxelShape field_196373_F = Block.func_208617_a(5.0, 15.0, 6.0, 11.0, 16.0, 10.0);
   protected static final VoxelShape field_196374_G = Block.func_208617_a(6.0, 0.0, 5.0, 10.0, 1.0, 11.0);
   protected static final VoxelShape field_196375_H = Block.func_208617_a(5.0, 0.0, 6.0, 11.0, 1.0, 10.0);
   protected static final VoxelShape field_185623_D = Block.func_208617_a(5.0, 6.0, 15.0, 11.0, 10.0, 16.0);
   protected static final VoxelShape field_185625_E = Block.func_208617_a(5.0, 6.0, 0.0, 11.0, 10.0, 1.0);
   protected static final VoxelShape field_185627_F = Block.func_208617_a(15.0, 6.0, 5.0, 16.0, 10.0, 11.0);
   protected static final VoxelShape field_185629_G = Block.func_208617_a(0.0, 6.0, 5.0, 1.0, 10.0, 11.0);
   private final boolean field_150047_a;

   protected BlockButton(boolean var1, Block.Properties var2) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_185512_D, EnumFacing.NORTH)
            .func_206870_a(field_176584_b, Boolean.valueOf(false))
            .func_206870_a(field_196366_M, AttachFace.WALL)
      );
      this.field_150047_a = ☃;
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return this.field_150047_a ? 30 : 20;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_185512_D);
      boolean ☃x = ☃.func_177229_b(field_176584_b);
      switch((AttachFace)☃.func_177229_b(field_196366_M)) {
         case FLOOR:
            if (☃.func_176740_k() == EnumFacing.Axis.X) {
               return ☃x ? field_196374_G : field_196376_y;
            }

            return ☃x ? field_196375_H : field_196377_z;
         case WALL:
            switch(☃) {
               case EAST:
                  return ☃x ? field_185629_G : field_185628_g;
               case WEST:
                  return ☃x ? field_185627_F : field_185626_f;
               case SOUTH:
                  return ☃x ? field_185625_E : field_185624_e;
               case NORTH:
               default:
                  return ☃x ? field_185623_D : field_185622_d;
            }
         case CEILING:
         default:
            if (☃.func_176740_k() == EnumFacing.Axis.X) {
               return ☃x ? field_196372_E : field_196370_b;
            } else {
               return ☃x ? field_196373_F : field_196371_c;
            }
      }
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.func_177229_b(field_176584_b)) {
         return true;
      } else {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176584_b, Boolean.valueOf(true)), 3);
         this.func_196367_a(☃, ☃, ☃, true);
         this.func_196368_e(☃, ☃, ☃);
         ☃.func_205220_G_().func_205360_a(☃, this, this.func_149738_a(☃));
         return true;
      }
   }

   protected void func_196367_a(@Nullable EntityPlayer var1, IWorld var2, BlockPos var3, boolean var4) {
      ☃.func_184133_a(☃ ? ☃ : null, ☃, this.func_196369_b(☃), SoundCategory.BLOCKS, 0.3F, ☃ ? 0.6F : 0.5F);
   }

   protected abstract SoundEvent func_196369_b(boolean var1);

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (!☃ && ☃.func_177230_c() != ☃.func_177230_c()) {
         if (☃.func_177229_b(field_176584_b)) {
            this.func_196368_e(☃, ☃, ☃);
         }

         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_176584_b) ? 15 : 0;
   }

   @Override
   public int func_176211_b(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_176584_b) && func_196365_i(☃) == ☃ ? 15 : 0;
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return true;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.field_72995_K && ☃.func_177229_b(field_176584_b)) {
         if (this.field_150047_a) {
            this.func_185616_e(☃, ☃, ☃);
         } else {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_176584_b, Boolean.valueOf(false)), 3);
            this.func_196368_e(☃, ☃, ☃);
            this.func_196367_a(null, ☃, ☃, false);
         }
      }
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      if (!☃.field_72995_K && this.field_150047_a && !☃.func_177229_b(field_176584_b)) {
         this.func_185616_e(☃, ☃, ☃);
      }
   }

   private void func_185616_e(IBlockState var1, World var2, BlockPos var3) {
      List<? extends Entity> ☃ = ☃.func_72872_a(EntityArrow.class, ☃.func_196954_c(☃, ☃).func_197752_a().func_186670_a(☃));
      boolean ☃x = !☃.isEmpty();
      boolean ☃xx = ☃.func_177229_b(field_176584_b);
      if (☃x != ☃xx) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176584_b, Boolean.valueOf(☃x)), 3);
         this.func_196368_e(☃, ☃, ☃);
         this.func_196367_a(null, ☃, ☃, ☃x);
      }

      if (☃x) {
         ☃.func_205220_G_().func_205360_a(new BlockPos(☃), this, this.func_149738_a(☃));
      }
   }

   private void func_196368_e(IBlockState var1, World var2, BlockPos var3) {
      ☃.func_195593_d(☃, this);
      ☃.func_195593_d(☃.func_177972_a(func_196365_i(☃).func_176734_d()), this);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185512_D, field_176584_b, field_196366_M);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
