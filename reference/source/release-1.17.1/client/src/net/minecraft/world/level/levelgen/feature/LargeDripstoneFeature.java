package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.phys.Vec3;

public class LargeDripstoneFeature extends Feature<LargeDripstoneConfiguration> {
   public LargeDripstoneFeature(Codec<LargeDripstoneConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<LargeDripstoneConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      LargeDripstoneConfiguration â˜ƒxx = â˜ƒ.config();
      Random â˜ƒxxx = â˜ƒ.random();
      if (!DripstoneUtils.isEmptyOrWater(â˜ƒ, â˜ƒx)) {
         return false;
      } else {
         Optional<Column> â˜ƒx = Column.scan(â˜ƒ, â˜ƒx, â˜ƒxx.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isDripstoneBaseOrLava);
         if (â˜ƒx.isPresent() && â˜ƒx.get() instanceof Column.Range â˜ƒ) {
            if (â˜ƒ.height() < 4) {
               return false;
            } else {
               int â˜ƒxxx = (int)((float)â˜ƒ.height() * â˜ƒxx.maxColumnRadiusToCaveHeightRatio);
               int â˜ƒxxxx = Mth.clamp(â˜ƒxxx, â˜ƒxx.columnRadius.getMinValue(), â˜ƒxx.columnRadius.getMaxValue());
               int â˜ƒxxxxx = Mth.randomBetweenInclusive(â˜ƒxxx, â˜ƒxx.columnRadius.getMinValue(), â˜ƒxxxx);
               LargeDripstoneFeature.LargeDripstone â˜ƒxxxxxx = makeDripstone(
                  â˜ƒx.atY(â˜ƒ.ceiling() - 1), false, â˜ƒxxx, â˜ƒxxxxx, â˜ƒxx.stalactiteBluntness, â˜ƒxx.heightScale
               );
               LargeDripstoneFeature.LargeDripstone â˜ƒxxxxxxx = makeDripstone(
                  â˜ƒx.atY(â˜ƒ.floor() + 1), true, â˜ƒxxx, â˜ƒxxxxx, â˜ƒxx.stalagmiteBluntness, â˜ƒxx.heightScale
               );
               LargeDripstoneFeature.WindOffsetter â˜ƒxx;
               if (â˜ƒxxxxxx.isSuitableForWind(â˜ƒxx) && â˜ƒxxxxxxx.isSuitableForWind(â˜ƒxx)) {
                  â˜ƒxx = new LargeDripstoneFeature.WindOffsetter(â˜ƒx.getY(), â˜ƒxxx, â˜ƒxx.windSpeed);
               } else {
                  â˜ƒxx = LargeDripstoneFeature.WindOffsetter.noWind();
               }

               boolean â˜ƒxx = â˜ƒxxxxxx.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(â˜ƒ, â˜ƒxx);
               boolean â˜ƒxxx = â˜ƒxxxxxxx.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(â˜ƒ, â˜ƒxx);
               if (â˜ƒxx) {
                  â˜ƒxxxxxx.placeBlocks(â˜ƒ, â˜ƒxxx, â˜ƒxx);
               }

               if (â˜ƒxxx) {
                  â˜ƒxxxxxxx.placeBlocks(â˜ƒ, â˜ƒxxx, â˜ƒxx);
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   private static LargeDripstoneFeature.LargeDripstone makeDripstone(BlockPos var0, boolean var1, Random var2, int var3, FloatProvider var4, FloatProvider var5) {
      return new LargeDripstoneFeature.LargeDripstone(â˜ƒ, â˜ƒ, â˜ƒ, (double)â˜ƒ.sample(â˜ƒ), (double)â˜ƒ.sample(â˜ƒ));
   }

   private void placeDebugMarkers(WorldGenLevel var1, BlockPos var2, Column.Range var3, LargeDripstoneFeature.WindOffsetter var4) {
      â˜ƒ.setBlock(â˜ƒ.offset(â˜ƒ.atY(â˜ƒ.ceiling() - 1)), Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
      â˜ƒ.setBlock(â˜ƒ.offset(â˜ƒ.atY(â˜ƒ.floor() + 1)), Blocks.GOLD_BLOCK.defaultBlockState(), 2);

      for(BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.atY(â˜ƒ.floor() + 2).mutable(); â˜ƒ.getY() < â˜ƒ.ceiling() - 1; â˜ƒ.move(Direction.UP)) {
         BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ);
         if (DripstoneUtils.isEmptyOrWater(â˜ƒ, â˜ƒx) || â˜ƒ.getBlockState(â˜ƒx).is(Blocks.DRIPSTONE_BLOCK)) {
            â˜ƒ.setBlock(â˜ƒx, Blocks.CREEPER_HEAD.defaultBlockState(), 2);
         }
      }
   }

   static final class LargeDripstone {
      private BlockPos root;
      private final boolean pointingUp;
      private int radius;
      private final double bluntness;
      private final double scale;

      LargeDripstone(BlockPos var1, boolean var2, int var3, double var4, double var6) {
         this.root = â˜ƒ;
         this.pointingUp = â˜ƒ;
         this.radius = â˜ƒ;
         this.bluntness = â˜ƒ;
         this.scale = â˜ƒ;
      }

      private int getHeight() {
         return this.getHeightAtRadius(0.0F);
      }

      private int getMinY() {
         return this.pointingUp ? this.root.getY() : this.root.getY() - this.getHeight();
      }

      private int getMaxY() {
         return !this.pointingUp ? this.root.getY() : this.root.getY() + this.getHeight();
      }

      boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel var1, LargeDripstoneFeature.WindOffsetter var2) {
         while(this.radius > 1) {
            BlockPos.MutableBlockPos â˜ƒ = this.root.mutable();
            int â˜ƒx = Math.min(10, this.getHeight());

            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
               if (â˜ƒ.getBlockState(â˜ƒ).is(Blocks.LAVA)) {
                  return false;
               }

               if (DripstoneUtils.isCircleMostlyEmbeddedInStone(â˜ƒ, â˜ƒ.offset(â˜ƒ), this.radius)) {
                  this.root = â˜ƒ;
                  return true;
               }

               â˜ƒ.move(this.pointingUp ? Direction.DOWN : Direction.UP);
            }

            this.radius /= 2;
         }

         return false;
      }

      private int getHeightAtRadius(float var1) {
         return (int)DripstoneUtils.getDripstoneHeight((double)â˜ƒ, (double)this.radius, this.scale, this.bluntness);
      }

      void placeBlocks(WorldGenLevel var1, Random var2, LargeDripstoneFeature.WindOffsetter var3) {
         for(int â˜ƒ = -this.radius; â˜ƒ <= this.radius; ++â˜ƒ) {
            for(int â˜ƒx = -this.radius; â˜ƒx <= this.radius; ++â˜ƒx) {
               float â˜ƒxx = Mth.sqrt((float)(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx));
               if (!(â˜ƒxx > (float)this.radius)) {
                  int â˜ƒxxx = this.getHeightAtRadius(â˜ƒxx);
                  if (â˜ƒxxx > 0) {
                     if ((double)â˜ƒ.nextFloat() < 0.2) {
                        â˜ƒxxx = (int)((float)â˜ƒxxx * Mth.randomBetween(â˜ƒ, 0.8F, 1.0F));
                     }

                     BlockPos.MutableBlockPos â˜ƒxxxx = this.root.offset(â˜ƒ, 0, â˜ƒx).mutable();
                     boolean â˜ƒxxxxx = false;

                     for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxx) {
                        BlockPos â˜ƒxxxxxxx = â˜ƒ.offset(â˜ƒxxxx);
                        if (DripstoneUtils.isEmptyOrWaterOrLava(â˜ƒ, â˜ƒxxxxxxx)) {
                           â˜ƒxxxxx = true;
                           Block â˜ƒxxxxxxxx = Blocks.DRIPSTONE_BLOCK;
                           â˜ƒ.setBlock(â˜ƒxxxxxxx, â˜ƒxxxxxxxx.defaultBlockState(), 2);
                        } else if (â˜ƒxxxxx && â˜ƒ.getBlockState(â˜ƒxxxxxxx).is(BlockTags.BASE_STONE_OVERWORLD)) {
                           break;
                        }

                        â˜ƒxxxx.move(this.pointingUp ? Direction.UP : Direction.DOWN);
                     }
                  }
               }
            }
         }
      }

      boolean isSuitableForWind(LargeDripstoneConfiguration var1) {
         return this.radius >= â˜ƒ.minRadiusForWind && this.bluntness >= (double)â˜ƒ.minBluntnessForWind;
      }
   }

   static final class WindOffsetter {
      private final int originY;
      @Nullable
      private final Vec3 windSpeed;

      WindOffsetter(int var1, Random var2, FloatProvider var3) {
         this.originY = â˜ƒ;
         float â˜ƒ = â˜ƒ.sample(â˜ƒ);
         float â˜ƒx = Mth.randomBetween(â˜ƒ, 0.0F, (float) Math.PI);
         this.windSpeed = new Vec3((double)(Mth.cos(â˜ƒx) * â˜ƒ), 0.0, (double)(Mth.sin(â˜ƒx) * â˜ƒ));
      }

      private WindOffsetter() {
         this.originY = 0;
         this.windSpeed = null;
      }

      static LargeDripstoneFeature.WindOffsetter noWind() {
         return new LargeDripstoneFeature.WindOffsetter();
      }

      BlockPos offset(BlockPos var1) {
         if (this.windSpeed == null) {
            return â˜ƒ;
         } else {
            int â˜ƒ = this.originY - â˜ƒ.getY();
            Vec3 â˜ƒx = this.windSpeed.scale((double)â˜ƒ);
            return â˜ƒ.offset(â˜ƒx.x, 0.0, â˜ƒx.z);
         }
      }
   }
}
