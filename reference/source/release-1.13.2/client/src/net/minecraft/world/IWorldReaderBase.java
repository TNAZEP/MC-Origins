package net.minecraft.world;

import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapeInt;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePartBitSet;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.gen.Heightmap;

public interface IWorldReaderBase extends IBlockReader {
   boolean func_175623_d(BlockPos var1);

   Biome func_180494_b(BlockPos var1);

   int func_175642_b(EnumLightType var1, BlockPos var2);

   default boolean func_175710_j(BlockPos var1) {
      if (☃.func_177956_o() >= this.func_181545_F()) {
         return this.func_175678_i(☃);
      } else {
         BlockPos ☃ = new BlockPos(☃.func_177958_n(), this.func_181545_F(), ☃.func_177952_p());
         if (!this.func_175678_i(☃)) {
            return false;
         } else {
            for(BlockPos var4 = ☃.func_177977_b(); var4.func_177956_o() > ☃.func_177956_o(); var4 = var4.func_177977_b()) {
               IBlockState ☃ = this.func_180495_p(var4);
               if (☃.func_200016_a(this, var4) > 0 && !☃.func_185904_a().func_76224_d()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   int func_201669_a(BlockPos var1, int var2);

   boolean func_175680_a(int var1, int var2, boolean var3);

   boolean func_175678_i(BlockPos var1);

   default BlockPos func_205770_a(Heightmap.Type var1, BlockPos var2) {
      return new BlockPos(☃.func_177958_n(), this.func_201676_a(☃, ☃.func_177958_n(), ☃.func_177952_p()), ☃.func_177952_p());
   }

   int func_201676_a(Heightmap.Type var1, int var2, int var3);

   default float func_205052_D(BlockPos var1) {
      return this.func_201675_m().func_177497_p()[this.func_201696_r(☃)];
   }

   @Nullable
   default EntityPlayer func_72890_a(Entity var1, double var2) {
      return this.func_184137_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃, false);
   }

   @Nullable
   default EntityPlayer func_184136_b(Entity var1, double var2) {
      return this.func_184137_a(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, ☃, true);
   }

   @Nullable
   default EntityPlayer func_184137_a(double var1, double var3, double var5, double var7, boolean var9) {
      Predicate<Entity> ☃ = ☃ ? EntitySelectors.field_188444_d : EntitySelectors.field_180132_d;
      return this.func_190525_a(☃, ☃, ☃, ☃, ☃);
   }

   @Nullable
   EntityPlayer func_190525_a(double var1, double var3, double var5, double var7, Predicate<Entity> var9);

   int func_175657_ab();

   WorldBorder func_175723_af();

   boolean func_195585_a(@Nullable Entity var1, VoxelShape var2);

   int func_175627_a(BlockPos var1, EnumFacing var2);

   boolean func_201670_d();

   int func_181545_F();

   default boolean func_195584_a(IBlockState var1, BlockPos var2) {
      VoxelShape ☃ = ☃.func_196952_d(this, ☃);
      return ☃.func_197766_b() || this.func_195585_a(null, ☃.func_197751_a((double)☃.func_177958_n(), (double)☃.func_177956_o(), (double)☃.func_177952_p()));
   }

   default boolean func_195587_c(@Nullable Entity var1, AxisAlignedBB var2) {
      return this.func_195585_a(☃, VoxelShapes.func_197881_a(☃));
   }

   default Stream<VoxelShape> func_212391_a(VoxelShape var1, VoxelShape var2, boolean var3) {
      int ☃ = MathHelper.func_76128_c(☃.func_197762_b(EnumFacing.Axis.X)) - 1;
      int ☃x = MathHelper.func_76143_f(☃.func_197758_c(EnumFacing.Axis.X)) + 1;
      int ☃xx = MathHelper.func_76128_c(☃.func_197762_b(EnumFacing.Axis.Y)) - 1;
      int ☃xxx = MathHelper.func_76143_f(☃.func_197758_c(EnumFacing.Axis.Y)) + 1;
      int ☃xxxx = MathHelper.func_76128_c(☃.func_197762_b(EnumFacing.Axis.Z)) - 1;
      int ☃xxxxx = MathHelper.func_76143_f(☃.func_197758_c(EnumFacing.Axis.Z)) + 1;
      WorldBorder ☃xxxxxx = this.func_175723_af();
      boolean ☃xxxxxxx = ☃xxxxxx.func_177726_b() < (double)☃
         && (double)☃x < ☃xxxxxx.func_177728_d()
         && ☃xxxxxx.func_177736_c() < (double)☃xxxx
         && (double)☃xxxxx < ☃xxxxxx.func_177733_e();
      VoxelShapePart ☃xxxxxxxx = new VoxelShapePartBitSet(☃x - ☃, ☃xxx - ☃xx, ☃xxxxx - ☃xxxx);
      Predicate<VoxelShape> ☃xxxxxxxxx = var1x -> !var1x.func_197766_b() && VoxelShapes.func_197879_c(☃, var1x, IBooleanFunction.AND);
      Stream<VoxelShape> ☃xxxxxxxxxx = StreamSupport.stream(
            BlockPos.MutableBlockPos.func_191531_b(☃, ☃xx, ☃xxxx, ☃x - 1, ☃xxx - 1, ☃xxxxx - 1).spliterator(), false
         )
         .map(var12x -> {
            int ☃ = var12x.func_177958_n();
            int ☃x = var12x.func_177956_o();
            int ☃xx = var12x.func_177952_p();
            boolean ☃xxx = ☃ == ☃ || ☃ == ☃ - 1;
            boolean ☃xxxx = ☃x == ☃ || ☃x == ☃ - 1;
            boolean ☃xxxxx = ☃xx == ☃ || ☃xx == ☃ - 1;
            if ((!☃xxx || !☃xxxx) && (!☃xxxx || !☃xxxxx) && (!☃xxxxx || !☃xxx) && this.func_175667_e(var12x)) {
               VoxelShape ☃xxxxxx;
               if (☃ && !☃ && !☃.func_177746_a(var12x)) {
                  ☃xxxxxx = VoxelShapes.func_197868_b();
               } else {
                  ☃xxxxxx = this.func_180495_p(var12x).func_196952_d(this, var12x);
               }
   
               VoxelShape ☃xxxxxx = ☃.func_197751_a((double)(-☃), (double)(-☃x), (double)(-☃xx));
               if (VoxelShapes.func_197879_c(☃xxxxxx, ☃xxxxxx, IBooleanFunction.AND)) {
                  return VoxelShapes.func_197880_a();
               } else if (☃xxxxxx == VoxelShapes.func_197868_b()) {
                  ☃.func_199625_a(☃ - ☃, ☃x - ☃, ☃xx - ☃, true, true);
                  return VoxelShapes.func_197880_a();
               } else {
                  return ☃xxxxxx.func_197751_a((double)☃, (double)☃x, (double)☃xx);
               }
            } else {
               return VoxelShapes.func_197880_a();
            }
         })
         .filter(☃xxxxxxxxx);
      return Stream.concat(☃xxxxxxxxxx, Stream.generate(() -> new VoxelShapeInt(☃, ☃, ☃, ☃)).limit(1L).filter(☃xxxxxxxxx));
   }

   default Stream<VoxelShape> func_199406_a(@Nullable Entity var1, AxisAlignedBB var2, double var3, double var5, double var7) {
      return this.func_212389_a(☃, ☃, Collections.emptySet(), ☃, ☃, ☃);
   }

   default Stream<VoxelShape> func_212389_a(@Nullable Entity var1, AxisAlignedBB var2, Set<Entity> var3, double var4, double var6, double var8) {
      double ☃ = 1.0E-7;
      VoxelShape ☃x = VoxelShapes.func_197881_a(☃);
      VoxelShape ☃xx = VoxelShapes.func_197881_a(☃.func_72317_d(☃ > 0.0 ? -1.0E-7 : 1.0E-7, ☃ > 0.0 ? -1.0E-7 : 1.0E-7, ☃ > 0.0 ? -1.0E-7 : 1.0E-7));
      VoxelShape ☃xxx = VoxelShapes.func_197882_b(VoxelShapes.func_197881_a(☃.func_72321_a(☃, ☃, ☃).func_186662_g(1.0E-7)), ☃xx, IBooleanFunction.ONLY_FIRST);
      return this.func_212392_a(☃, ☃xxx, ☃x, ☃);
   }

   default Stream<VoxelShape> func_212388_b(@Nullable Entity var1, AxisAlignedBB var2) {
      return this.func_212392_a(☃, VoxelShapes.func_197881_a(☃), VoxelShapes.func_197880_a(), Collections.emptySet());
   }

   default Stream<VoxelShape> func_212392_a(@Nullable Entity var1, VoxelShape var2, VoxelShape var3, Set<Entity> var4) {
      boolean ☃ = ☃ != null && ☃.func_174832_aS();
      boolean ☃x = ☃ != null && this.func_191503_g(☃);
      if (☃ != null && ☃ == ☃x) {
         ☃.func_174821_h(!☃x);
      }

      return this.func_212391_a(☃, ☃, ☃x);
   }

   default boolean func_191503_g(Entity var1) {
      WorldBorder ☃ = this.func_175723_af();
      double ☃x = ☃.func_177726_b();
      double ☃xx = ☃.func_177736_c();
      double ☃xxx = ☃.func_177728_d();
      double ☃xxxx = ☃.func_177733_e();
      if (☃.func_174832_aS()) {
         ++☃x;
         ++☃xx;
         --☃xxx;
         --☃xxxx;
      } else {
         --☃x;
         --☃xx;
         ++☃xxx;
         ++☃xxxx;
      }

      return ☃.field_70165_t > ☃x && ☃.field_70165_t < ☃xxx && ☃.field_70161_v > ☃xx && ☃.field_70161_v < ☃xxxx;
   }

   default boolean func_211156_a(@Nullable Entity var1, AxisAlignedBB var2, Set<Entity> var3) {
      return this.func_212392_a(☃, VoxelShapes.func_197881_a(☃), VoxelShapes.func_197880_a(), ☃).allMatch(VoxelShape::func_197766_b);
   }

   default boolean func_195586_b(@Nullable Entity var1, AxisAlignedBB var2) {
      return this.func_211156_a(☃, ☃, Collections.emptySet());
   }

   default boolean func_201671_F(BlockPos var1) {
      return this.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a);
   }

   default boolean func_72953_d(AxisAlignedBB var1) {
      int ☃ = MathHelper.func_76128_c(☃.field_72340_a);
      int ☃x = MathHelper.func_76143_f(☃.field_72336_d);
      int ☃xx = MathHelper.func_76128_c(☃.field_72338_b);
      int ☃xxx = MathHelper.func_76143_f(☃.field_72337_e);
      int ☃xxxx = MathHelper.func_76128_c(☃.field_72339_c);
      int ☃xxxxx = MathHelper.func_76143_f(☃.field_72334_f);

      try (BlockPos.PooledMutableBlockPos ☃xxxxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         for(int ☃xxxxxxx = ☃; ☃xxxxxxx < ☃x; ++☃xxxxxxx) {
            for(int ☃xxxxxxxx = ☃xx; ☃xxxxxxxx < ☃xxx; ++☃xxxxxxxx) {
               for(int ☃xxxxxxxxx = ☃xxxx; ☃xxxxxxxxx < ☃xxxxx; ++☃xxxxxxxxx) {
                  IBlockState ☃xxxxxxxxxx = this.func_180495_p(☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx));
                  if (!☃xxxxxxxxxx.func_204520_s().func_206888_e()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   default int func_201696_r(BlockPos var1) {
      return this.func_205049_d(☃, this.func_175657_ab());
   }

   default int func_205049_d(BlockPos var1, int var2) {
      if (☃.func_177958_n() < -30000000 || ☃.func_177952_p() < -30000000 || ☃.func_177958_n() >= 30000000 || ☃.func_177952_p() >= 30000000) {
         return 15;
      } else if (this.func_180495_p(☃).func_200130_c(this, ☃)) {
         int ☃ = this.func_201669_a(☃.func_177984_a(), ☃);
         int ☃x = this.func_201669_a(☃.func_177974_f(), ☃);
         int ☃xx = this.func_201669_a(☃.func_177976_e(), ☃);
         int ☃xxx = this.func_201669_a(☃.func_177968_d(), ☃);
         int ☃xxxx = this.func_201669_a(☃.func_177978_c(), ☃);
         if (☃x > ☃) {
            ☃ = ☃x;
         }

         if (☃xx > ☃) {
            ☃ = ☃xx;
         }

         if (☃xxx > ☃) {
            ☃ = ☃xxx;
         }

         if (☃xxxx > ☃) {
            ☃ = ☃xxxx;
         }

         return ☃;
      } else {
         return this.func_201669_a(☃, ☃);
      }
   }

   default boolean func_175667_e(BlockPos var1) {
      return this.func_175668_a(☃, true);
   }

   default boolean func_175668_a(BlockPos var1, boolean var2) {
      return this.func_175680_a(☃.func_177958_n() >> 4, ☃.func_177952_p() >> 4, ☃);
   }

   default boolean func_205050_e(BlockPos var1, int var2) {
      return this.func_175648_a(☃, ☃, true);
   }

   default boolean func_175648_a(BlockPos var1, int var2, boolean var3) {
      return this.func_175663_a(
         ☃.func_177958_n() - ☃, ☃.func_177956_o() - ☃, ☃.func_177952_p() - ☃, ☃.func_177958_n() + ☃, ☃.func_177956_o() + ☃, ☃.func_177952_p() + ☃, ☃
      );
   }

   default boolean func_175707_a(BlockPos var1, BlockPos var2) {
      return this.func_175706_a(☃, ☃, true);
   }

   default boolean func_175706_a(BlockPos var1, BlockPos var2, boolean var3) {
      return this.func_175663_a(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p(), ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p(), ☃);
   }

   default boolean func_175711_a(MutableBoundingBox var1) {
      return this.func_175639_b(☃, true);
   }

   default boolean func_175639_b(MutableBoundingBox var1, boolean var2) {
      return this.func_175663_a(☃.field_78897_a, ☃.field_78895_b, ☃.field_78896_c, ☃.field_78893_d, ☃.field_78894_e, ☃.field_78892_f, ☃);
   }

   default boolean func_175663_a(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      if (☃ >= 0 && ☃ < 256) {
         ☃ >>= 4;
         ☃ >>= 4;
         ☃ >>= 4;
         ☃ >>= 4;

         for(int ☃ = ☃; ☃ <= ☃; ++☃) {
            for(int ☃x = ☃; ☃x <= ☃; ++☃x) {
               if (!this.func_175680_a(☃, ☃x, ☃)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   Dimension func_201675_m();
}
