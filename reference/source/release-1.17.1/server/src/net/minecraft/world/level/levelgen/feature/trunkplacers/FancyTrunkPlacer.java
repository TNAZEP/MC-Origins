package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public class FancyTrunkPlacer extends TrunkPlacer {
   public static final Codec<FancyTrunkPlacer> CODEC = RecordCodecBuilder.create(var0 -> trunkPlacerParts(var0).apply(var0, FancyTrunkPlacer::new));
   private static final double TRUNK_HEIGHT_SCALE = 0.618;
   private static final double CLUSTER_DENSITY_MAGIC = 1.382;
   private static final double BRANCH_SLOPE = 0.381;
   private static final double BRANCH_LENGTH_MAGIC = 0.328;

   public FancyTrunkPlacer(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected TrunkPlacerType<?> type() {
      return TrunkPlacerType.FANCY_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.FoliageAttachment> placeTrunk(
      LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, int var4, BlockPos var5, TreeConfiguration var6
   ) {
      int â˜ƒ = 5;
      int â˜ƒx = â˜ƒ + 2;
      int â˜ƒxx = Mth.floor((double)â˜ƒx * 0.618);
      setDirtAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.below(), â˜ƒ);
      double â˜ƒxxx = 1.0;
      int â˜ƒxxxx = Math.min(1, Mth.floor(1.382 + Math.pow(1.0 * (double)â˜ƒx / 13.0, 2.0)));
      int â˜ƒxxxxx = â˜ƒ.getY() + â˜ƒxx;
      int â˜ƒxxxxxx = â˜ƒx - 5;
      List<FancyTrunkPlacer.FoliageCoords> â˜ƒxxxxxxx = Lists.<FancyTrunkPlacer.FoliageCoords>newArrayList();
      â˜ƒxxxxxxx.add(new FancyTrunkPlacer.FoliageCoords(â˜ƒ.above(â˜ƒxxxxxx), â˜ƒxxxxx));

      for(; â˜ƒxxxxxx >= 0; --â˜ƒxxxxxx) {
         float â˜ƒxxxxxxxx = treeShape(â˜ƒx, â˜ƒxxxxxx);
         if (!(â˜ƒxxxxxxxx < 0.0F)) {
            for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxxxxx) {
               double â˜ƒxxxxxxxxxx = 1.0;
               double â˜ƒxxxxxxxxxxx = 1.0 * (double)â˜ƒxxxxxxxx * ((double)â˜ƒ.nextFloat() + 0.328);
               double â˜ƒxxxxxxxxxxxx = (double)(â˜ƒ.nextFloat() * 2.0F) * Math.PI;
               double â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx * Math.sin(â˜ƒxxxxxxxxxxxx) + 0.5;
               double â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx * Math.cos(â˜ƒxxxxxxxxxxxx) + 0.5;
               BlockPos â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.offset(â˜ƒxxxxxxxxxxxxx, (double)(â˜ƒxxxxxx - 1), â˜ƒxxxxxxxxxxxxxx);
               BlockPos â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx.above(5);
               if (this.makeLimb(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, false, â˜ƒ)) {
                  int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒ.getX() - â˜ƒxxxxxxxxxxxxxxx.getX();
                  int â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒ.getZ() - â˜ƒxxxxxxxxxxxxxxx.getZ();
                  double â˜ƒxxxxxxxxxxxxxxxxxxx = (double)â˜ƒxxxxxxxxxxxxxxx.getY()
                     - Math.sqrt((double)(â˜ƒxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxx)) * 0.381;
                  int â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx > (double)â˜ƒxxxxx ? â˜ƒxxxxx : (int)â˜ƒxxxxxxxxxxxxxxxxxxx;
                  BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxx = new BlockPos(â˜ƒ.getX(), â˜ƒxxxxxxxxxxxxxxxxxxxx, â˜ƒ.getZ());
                  if (this.makeLimb(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, false, â˜ƒ)) {
                     â˜ƒxxxxxxx.add(new FancyTrunkPlacer.FoliageCoords(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx.getY()));
                  }
               }
            }
         }
      }

      this.makeLimb(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.above(â˜ƒxx), true, â˜ƒ);
      this.makeBranches(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxxxxxxx, â˜ƒ);
      List<FoliagePlacer.FoliageAttachment> â˜ƒxxxxxxxx = Lists.<FoliagePlacer.FoliageAttachment>newArrayList();

      for(FancyTrunkPlacer.FoliageCoords â˜ƒxxxxxxxxx : â˜ƒxxxxxxx) {
         if (this.trimBranches(â˜ƒx, â˜ƒxxxxxxxxx.getBranchBase() - â˜ƒ.getY())) {
            â˜ƒxxxxxxxx.add(â˜ƒxxxxxxxxx.attachment);
         }
      }

      return â˜ƒxxxxxxxx;
   }

   private boolean makeLimb(
      LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, BlockPos var4, BlockPos var5, boolean var6, TreeConfiguration var7
   ) {
      if (!â˜ƒ && Objects.equals(â˜ƒ, â˜ƒ)) {
         return true;
      } else {
         BlockPos â˜ƒ = â˜ƒ.offset(-â˜ƒ.getX(), -â˜ƒ.getY(), -â˜ƒ.getZ());
         int â˜ƒx = this.getSteps(â˜ƒ);
         float â˜ƒxx = (float)â˜ƒ.getX() / (float)â˜ƒx;
         float â˜ƒxxx = (float)â˜ƒ.getY() / (float)â˜ƒx;
         float â˜ƒxxxx = (float)â˜ƒ.getZ() / (float)â˜ƒx;

         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx <= â˜ƒx; ++â˜ƒxxxxx) {
            BlockPos â˜ƒxxxxxx = â˜ƒ.offset(
               (double)(0.5F + (float)â˜ƒxxxxx * â˜ƒxx), (double)(0.5F + (float)â˜ƒxxxxx * â˜ƒxxx), (double)(0.5F + (float)â˜ƒxxxxx * â˜ƒxxxx)
            );
            if (â˜ƒ) {
               TrunkPlacer.placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxx, â˜ƒ, var3x -> var3x.setValue(RotatedPillarBlock.AXIS, this.getLogAxis(â˜ƒ, â˜ƒ)));
            } else if (!TreeFeature.isFree(â˜ƒ, â˜ƒxxxxxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private int getSteps(BlockPos var1) {
      int â˜ƒ = Mth.abs(â˜ƒ.getX());
      int â˜ƒx = Mth.abs(â˜ƒ.getY());
      int â˜ƒxx = Mth.abs(â˜ƒ.getZ());
      return Math.max(â˜ƒ, Math.max(â˜ƒx, â˜ƒxx));
   }

   private Direction.Axis getLogAxis(BlockPos var1, BlockPos var2) {
      Direction.Axis â˜ƒ = Direction.Axis.Y;
      int â˜ƒx = Math.abs(â˜ƒ.getX() - â˜ƒ.getX());
      int â˜ƒxx = Math.abs(â˜ƒ.getZ() - â˜ƒ.getZ());
      int â˜ƒxxx = Math.max(â˜ƒx, â˜ƒxx);
      if (â˜ƒxxx > 0) {
         if (â˜ƒx == â˜ƒxxx) {
            â˜ƒ = Direction.Axis.X;
         } else {
            â˜ƒ = Direction.Axis.Z;
         }
      }

      return â˜ƒ;
   }

   private boolean trimBranches(int var1, int var2) {
      return (double)â˜ƒ >= (double)â˜ƒ * 0.2;
   }

   private void makeBranches(
      LevelSimulatedReader var1,
      BiConsumer<BlockPos, BlockState> var2,
      Random var3,
      int var4,
      BlockPos var5,
      List<FancyTrunkPlacer.FoliageCoords> var6,
      TreeConfiguration var7
   ) {
      for(FancyTrunkPlacer.FoliageCoords â˜ƒ : â˜ƒ) {
         int â˜ƒx = â˜ƒ.getBranchBase();
         BlockPos â˜ƒxx = new BlockPos(â˜ƒ.getX(), â˜ƒx, â˜ƒ.getZ());
         if (!â˜ƒxx.equals(â˜ƒ.attachment.pos()) && this.trimBranches(â˜ƒ, â˜ƒx - â˜ƒ.getY())) {
            this.makeLimb(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒ.attachment.pos(), true, â˜ƒ);
         }
      }
   }

   private static float treeShape(int var0, int var1) {
      if ((float)â˜ƒ < (float)â˜ƒ * 0.3F) {
         return -1.0F;
      } else {
         float â˜ƒ = (float)â˜ƒ / 2.0F;
         float â˜ƒx = â˜ƒ - (float)â˜ƒ;
         float â˜ƒxx = Mth.sqrt(â˜ƒ * â˜ƒ - â˜ƒx * â˜ƒx);
         if (â˜ƒx == 0.0F) {
            â˜ƒxx = â˜ƒ;
         } else if (Math.abs(â˜ƒx) >= â˜ƒ) {
            return 0.0F;
         }

         return â˜ƒxx * 0.5F;
      }
   }

   static class FoliageCoords {
      final FoliagePlacer.FoliageAttachment attachment;
      private final int branchBase;

      public FoliageCoords(BlockPos var1, int var2) {
         this.attachment = new FoliagePlacer.FoliageAttachment(â˜ƒ, 0, false);
         this.branchBase = â˜ƒ;
      }

      public int getBranchBase() {
         return this.branchBase;
      }
   }
}
