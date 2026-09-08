package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockLever extends BlockHorizontalFace {
   public static final BooleanProperty field_176359_b = BlockStateProperties.field_208194_u;
   protected static final VoxelShape field_185692_c = Block.func_208617_a(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
   protected static final VoxelShape field_185693_d = Block.func_208617_a(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
   protected static final VoxelShape field_185694_e = Block.func_208617_a(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
   protected static final VoxelShape field_185695_f = Block.func_208617_a(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);
   protected static final VoxelShape field_209348_r = Block.func_208617_a(5.0, 0.0, 4.0, 11.0, 6.0, 12.0);
   protected static final VoxelShape field_209349_s = Block.func_208617_a(4.0, 0.0, 5.0, 12.0, 6.0, 11.0);
   protected static final VoxelShape field_209350_t = Block.func_208617_a(5.0, 10.0, 4.0, 11.0, 16.0, 12.0);
   protected static final VoxelShape field_209351_u = Block.func_208617_a(4.0, 10.0, 5.0, 12.0, 16.0, 11.0);

   protected BlockLever(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_185512_D, EnumFacing.NORTH)
            .func_206870_a(field_176359_b, Boolean.valueOf(false))
            .func_206870_a(field_196366_M, AttachFace.WALL)
      );
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      switch((AttachFace)☃.func_177229_b(field_196366_M)) {
         case FLOOR:
            switch(((EnumFacing)☃.func_177229_b(field_185512_D)).func_176740_k()) {
               case X:
                  return field_209349_s;
               case Z:
               default:
                  return field_209348_r;
            }
         case WALL:
            switch((EnumFacing)☃.func_177229_b(field_185512_D)) {
               case EAST:
                  return field_185695_f;
               case WEST:
                  return field_185694_e;
               case SOUTH:
                  return field_185693_d;
               case NORTH:
               default:
                  return field_185692_c;
            }
         case CEILING:
         default:
            switch(((EnumFacing)☃.func_177229_b(field_185512_D)).func_176740_k()) {
               case X:
                  return field_209351_u;
               case Z:
               default:
                  return field_209350_t;
            }
      }
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      ☃ = ☃.func_177231_a(field_176359_b);
      boolean ☃ = ☃.func_177229_b(field_176359_b);
      if (☃.field_72995_K) {
         if (☃) {
            func_196379_a(☃, ☃, ☃, 1.0F);
         }

         return true;
      } else {
         ☃.func_180501_a(☃, ☃, 3);
         float ☃ = ☃ ? 0.6F : 0.5F;
         ☃.func_184133_a(null, ☃, SoundEvents.field_187750_dc, SoundCategory.BLOCKS, 0.3F, ☃);
         this.func_196378_d(☃, ☃, ☃);
         return true;
      }
   }

   private static void func_196379_a(IBlockState var0, IWorld var1, BlockPos var2, float var3) {
      EnumFacing ☃ = ((EnumFacing)☃.func_177229_b(field_185512_D)).func_176734_d();
      EnumFacing ☃x = func_196365_i(☃).func_176734_d();
      double ☃xx = (double)☃.func_177958_n() + 0.5 + 0.1 * (double)☃.func_82601_c() + 0.2 * (double)☃x.func_82601_c();
      double ☃xxx = (double)☃.func_177956_o() + 0.5 + 0.1 * (double)☃.func_96559_d() + 0.2 * (double)☃x.func_96559_d();
      double ☃xxxx = (double)☃.func_177952_p() + 0.5 + 0.1 * (double)☃.func_82599_e() + 0.2 * (double)☃x.func_82599_e();
      ☃.func_195594_a(new RedstoneParticleData(1.0F, 0.0F, 0.0F, ☃), ☃xx, ☃xxx, ☃xxxx, 0.0, 0.0, 0.0);
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.func_177229_b(field_176359_b) && ☃.nextFloat() < 0.25F) {
         func_196379_a(☃, ☃, ☃, 0.5F);
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (!☃ && ☃.func_177230_c() != ☃.func_177230_c()) {
         if (☃.func_177229_b(field_176359_b)) {
            this.func_196378_d(☃, ☃, ☃);
         }

         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_176359_b) ? 15 : 0;
   }

   @Override
   public int func_176211_b(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃.func_177229_b(field_176359_b) && func_196365_i(☃) == ☃ ? 15 : 0;
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return true;
   }

   private void func_196378_d(IBlockState var1, World var2, BlockPos var3) {
      ☃.func_195593_d(☃, this);
      ☃.func_195593_d(☃.func_177972_a(func_196365_i(☃).func_176734_d()), this);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196366_M, field_185512_D, field_176359_b);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
