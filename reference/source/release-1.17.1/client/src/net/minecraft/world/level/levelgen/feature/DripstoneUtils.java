package net.minecraft.world.level.levelgen.feature;

import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

public class DripstoneUtils {
   protected static double getDripstoneHeight(double var0, double var2, double var4, double var6) {
      if (â˜ƒ < â˜ƒ) {
         â˜ƒ = â˜ƒ;
      }

      double â˜ƒ = 0.384;
      double â˜ƒx = â˜ƒ / â˜ƒ * 0.384;
      double â˜ƒxx = 0.75 * Math.pow(â˜ƒx, 1.3333333333333333);
      double â˜ƒxxx = Math.pow(â˜ƒx, 0.6666666666666666);
      double â˜ƒxxxx = 0.3333333333333333 * Math.log(â˜ƒx);
      double â˜ƒxxxxx = â˜ƒ * (â˜ƒxx - â˜ƒxxx - â˜ƒxxxx);
      â˜ƒxxxxx = Math.max(â˜ƒxxxxx, 0.0);
      return â˜ƒxxxxx / 0.384 * â˜ƒ;
   }

   protected static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel var0, BlockPos var1, int var2) {
      if (isEmptyOrWaterOrLava(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         float â˜ƒ = 6.0F;
         float â˜ƒx = 6.0F / (float)â˜ƒ;

         for(float â˜ƒxx = 0.0F; â˜ƒxx < (float) (Math.PI * 2); â˜ƒxx += â˜ƒx) {
            int â˜ƒxxx = (int)(Mth.cos(â˜ƒxx) * (float)â˜ƒ);
            int â˜ƒxxxx = (int)(Mth.sin(â˜ƒxx) * (float)â˜ƒ);
            if (isEmptyOrWaterOrLava(â˜ƒ, â˜ƒ.offset(â˜ƒxxx, 0, â˜ƒxxxx))) {
               return false;
            }
         }

         return true;
      }
   }

   protected static boolean isEmptyOrWater(LevelAccessor var0, BlockPos var1) {
      return â˜ƒ.isStateAtPosition(â˜ƒ, DripstoneUtils::isEmptyOrWater);
   }

   protected static boolean isEmptyOrWaterOrLava(LevelAccessor var0, BlockPos var1) {
      return â˜ƒ.isStateAtPosition(â˜ƒ, DripstoneUtils::isEmptyOrWaterOrLava);
   }

   protected static void buildBaseToTipColumn(Direction var0, int var1, boolean var2, Consumer<BlockState> var3) {
      if (â˜ƒ >= 3) {
         â˜ƒ.accept(createPointedDripstone(â˜ƒ, DripstoneThickness.BASE));

         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ - 3; ++â˜ƒ) {
            â˜ƒ.accept(createPointedDripstone(â˜ƒ, DripstoneThickness.MIDDLE));
         }
      }

      if (â˜ƒ >= 2) {
         â˜ƒ.accept(createPointedDripstone(â˜ƒ, DripstoneThickness.FRUSTUM));
      }

      if (â˜ƒ >= 1) {
         â˜ƒ.accept(createPointedDripstone(â˜ƒ, â˜ƒ ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
      }
   }

   protected static void growPointedDripstone(WorldGenLevel var0, BlockPos var1, Direction var2, int var3, boolean var4) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
      buildBaseToTipColumn(â˜ƒ, â˜ƒ, â˜ƒ, var3x -> {
         if (var3x.is(Blocks.POINTED_DRIPSTONE)) {
            var3x = var3x.setValue(PointedDripstoneBlock.WATERLOGGED, Boolean.valueOf(â˜ƒ.isWaterAt(â˜ƒ)));
         }

         â˜ƒ.setBlock(â˜ƒ, var3x, 2);
         â˜ƒ.move(â˜ƒ);
      });
   }

   protected static boolean placeDripstoneBlockIfPossible(WorldGenLevel var0, BlockPos var1) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒ.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
         â˜ƒ.setBlock(â˜ƒ, Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 2);
         return true;
      } else {
         return false;
      }
   }

   private static BlockState createPointedDripstone(Direction var0, DripstoneThickness var1) {
      return Blocks.POINTED_DRIPSTONE.defaultBlockState().setValue(PointedDripstoneBlock.TIP_DIRECTION, â˜ƒ).setValue(PointedDripstoneBlock.THICKNESS, â˜ƒ);
   }

   public static boolean isDripstoneBaseOrLava(BlockState var0) {
      return isDripstoneBase(â˜ƒ) || â˜ƒ.is(Blocks.LAVA);
   }

   public static boolean isDripstoneBase(BlockState var0) {
      return â˜ƒ.is(Blocks.DRIPSTONE_BLOCK) || â˜ƒ.is(BlockTags.DRIPSTONE_REPLACEABLE);
   }

   public static boolean isEmptyOrWater(BlockState var0) {
      return â˜ƒ.isAir() || â˜ƒ.is(Blocks.WATER);
   }

   public static boolean isEmptyOrWaterOrLava(BlockState var0) {
      return â˜ƒ.isAir() || â˜ƒ.is(Blocks.WATER) || â˜ƒ.is(Blocks.LAVA);
   }
}
