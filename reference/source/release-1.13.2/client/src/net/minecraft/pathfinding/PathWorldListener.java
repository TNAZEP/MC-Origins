package net.minecraft.pathfinding;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldEventListener;

public class PathWorldListener implements IWorldEventListener {
   private final List<PathNavigate> field_189519_a = Lists.<PathNavigate>newArrayList();

   @Override
   public void func_184376_a(IBlockReader var1, BlockPos var2, IBlockState var3, IBlockState var4, int var5) {
      if (this.func_184378_a(☃, ☃, ☃, ☃)) {
         int ☃ = 0;

         for(int ☃x = this.field_189519_a.size(); ☃ < ☃x; ++☃) {
            PathNavigate ☃xx = (PathNavigate)this.field_189519_a.get(☃);
            if (☃xx != null && !☃xx.func_188553_i()) {
               Path ☃xxx = ☃xx.func_75505_d();
               if (☃xxx != null && !☃xxx.func_75879_b() && ☃xxx.func_75874_d() != 0) {
                  PathPoint ☃xxxx = ☃xx.field_75514_c.func_75870_c();
                  double ☃xxxxx = ☃.func_177954_c(
                     ((double)☃xxxx.field_75839_a + ☃xx.field_75515_a.field_70165_t) / 2.0,
                     ((double)☃xxxx.field_75837_b + ☃xx.field_75515_a.field_70163_u) / 2.0,
                     ((double)☃xxxx.field_75838_c + ☃xx.field_75515_a.field_70161_v) / 2.0
                  );
                  int ☃xxxxxx = (☃xxx.func_75874_d() - ☃xxx.func_75873_e()) * (☃xxx.func_75874_d() - ☃xxx.func_75873_e());
                  if (☃xxxxx < (double)☃xxxxxx) {
                     ☃xx.func_188554_j();
                  }
               }
            }
         }
      }
   }

   protected boolean func_184378_a(IBlockReader var1, BlockPos var2, IBlockState var3, IBlockState var4) {
      VoxelShape ☃ = ☃.func_196952_d(☃, ☃);
      VoxelShape ☃x = ☃.func_196952_d(☃, ☃);
      return VoxelShapes.func_197879_c(☃, ☃x, IBooleanFunction.NOT_SAME);
   }

   @Override
   public void func_174959_b(BlockPos var1) {
   }

   @Override
   public void func_147585_a(int var1, int var2, int var3, int var4, int var5, int var6) {
   }

   @Override
   public void func_184375_a(@Nullable EntityPlayer var1, SoundEvent var2, SoundCategory var3, double var4, double var6, double var8, float var10, float var11) {
   }

   @Override
   public void func_195461_a(IParticleData var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
   }

   @Override
   public void func_195462_a(IParticleData var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14) {
   }

   @Override
   public void func_72703_a(Entity var1) {
      if (☃ instanceof EntityLiving) {
         this.field_189519_a.add(((EntityLiving)☃).func_70661_as());
      }
   }

   @Override
   public void func_72709_b(Entity var1) {
      if (☃ instanceof EntityLiving) {
         this.field_189519_a.remove(((EntityLiving)☃).func_70661_as());
      }
   }

   @Override
   public void func_184377_a(SoundEvent var1, BlockPos var2) {
   }

   @Override
   public void func_180440_a(int var1, BlockPos var2, int var3) {
   }

   @Override
   public void func_180439_a(EntityPlayer var1, int var2, BlockPos var3, int var4) {
   }

   @Override
   public void func_180441_b(int var1, BlockPos var2, int var3) {
   }
}
