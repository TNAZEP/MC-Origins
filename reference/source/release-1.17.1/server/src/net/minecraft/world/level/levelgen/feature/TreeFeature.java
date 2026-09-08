package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

public class TreeFeature extends Feature<TreeConfiguration> {
   private static final int BLOCK_UPDATE_FLAGS = 19;

   public TreeFeature(Codec<TreeConfiguration> var1) {
      super(â˜ƒ);
   }

   public static boolean isFree(LevelSimulatedReader var0, BlockPos var1) {
      return validTreePos(â˜ƒ, â˜ƒ) || â˜ƒ.isStateAtPosition(â˜ƒ, var0x -> var0x.is(BlockTags.LOGS));
   }

   private static boolean isVine(LevelSimulatedReader var0, BlockPos var1) {
      return â˜ƒ.isStateAtPosition(â˜ƒ, var0x -> var0x.is(Blocks.VINE));
   }

   private static boolean isBlockWater(LevelSimulatedReader var0, BlockPos var1) {
      return â˜ƒ.isStateAtPosition(â˜ƒ, var0x -> var0x.is(Blocks.WATER));
   }

   public static boolean isAirOrLeaves(LevelSimulatedReader var0, BlockPos var1) {
      return â˜ƒ.isStateAtPosition(â˜ƒ, var0x -> var0x.isAir() || var0x.is(BlockTags.LEAVES));
   }

   private static boolean isReplaceablePlant(LevelSimulatedReader var0, BlockPos var1) {
      return â˜ƒ.isStateAtPosition(â˜ƒ, var0x -> {
         Material â˜ƒ = var0x.getMaterial();
         return â˜ƒ == Material.REPLACEABLE_PLANT;
      });
   }

   private static void setBlockKnownShape(LevelWriter var0, BlockPos var1, BlockState var2) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 19);
   }

   public static boolean validTreePos(LevelSimulatedReader var0, BlockPos var1) {
      return isAirOrLeaves(â˜ƒ, â˜ƒ) || isReplaceablePlant(â˜ƒ, â˜ƒ) || isBlockWater(â˜ƒ, â˜ƒ);
   }

   private boolean doPlace(
      WorldGenLevel var1, Random var2, BlockPos var3, BiConsumer<BlockPos, BlockState> var4, BiConsumer<BlockPos, BlockState> var5, TreeConfiguration var6
   ) {
      int â˜ƒ = â˜ƒ.trunkPlacer.getTreeHeight(â˜ƒ);
      int â˜ƒx = â˜ƒ.foliagePlacer.foliageHeight(â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒxx = â˜ƒ - â˜ƒx;
      int â˜ƒxxx = â˜ƒ.foliagePlacer.foliageRadius(â˜ƒ, â˜ƒxx);
      if (â˜ƒ.getY() < â˜ƒ.getMinBuildHeight() + 1 || â˜ƒ.getY() + â˜ƒ + 1 > â˜ƒ.getMaxBuildHeight()) {
         return false;
      } else if (!â˜ƒ.saplingProvider.getState(â˜ƒ, â˜ƒ).canSurvive(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         OptionalInt â˜ƒ = â˜ƒ.minimumSize.minClippedHeight();
         int â˜ƒx = this.getMaxFreeTreeHeight(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒx >= â˜ƒ || â˜ƒ.isPresent() && â˜ƒx >= â˜ƒ.getAsInt()) {
            List<FoliagePlacer.FoliageAttachment> â˜ƒxx = â˜ƒ.trunkPlacer.placeTrunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
            â˜ƒxx.forEach(var7x -> â˜ƒ.foliagePlacer.createFoliage(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, var7x, â˜ƒ, â˜ƒ));
            return true;
         } else {
            return false;
         }
      }
   }

   private int getMaxFreeTreeHeight(LevelSimulatedReader var1, int var2, BlockPos var3, TreeConfiguration var4) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

      for(int â˜ƒx = 0; â˜ƒx <= â˜ƒ + 1; ++â˜ƒx) {
         int â˜ƒxx = â˜ƒ.minimumSize.getSizeAtHeight(â˜ƒ, â˜ƒx);

         for(int â˜ƒxxx = -â˜ƒxx; â˜ƒxxx <= â˜ƒxx; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = -â˜ƒxx; â˜ƒxxxx <= â˜ƒxx; ++â˜ƒxxxx) {
               â˜ƒ.setWithOffset(â˜ƒ, â˜ƒxxx, â˜ƒx, â˜ƒxxxx);
               if (!isFree(â˜ƒ, â˜ƒ) || !â˜ƒ.ignoreVines && isVine(â˜ƒ, â˜ƒ)) {
                  return â˜ƒx - 2;
               }
            }
         }
      }

      return â˜ƒ;
   }

   @Override
   protected void setBlock(LevelWriter var1, BlockPos var2, BlockState var3) {
      setBlockKnownShape(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public final boolean place(FeaturePlaceContext<TreeConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      Random â˜ƒx = â˜ƒ.random();
      BlockPos â˜ƒxx = â˜ƒ.origin();
      TreeConfiguration â˜ƒxxx = â˜ƒ.config();
      Set<BlockPos> â˜ƒxxxx = Sets.<BlockPos>newHashSet();
      Set<BlockPos> â˜ƒxxxxx = Sets.<BlockPos>newHashSet();
      Set<BlockPos> â˜ƒxxxxxx = Sets.<BlockPos>newHashSet();
      BiConsumer<BlockPos, BlockState> â˜ƒxxxxxxx = (var2x, var3x) -> {
         â˜ƒ.add(var2x.immutable());
         â˜ƒ.setBlock(var2x, var3x, 19);
      };
      BiConsumer<BlockPos, BlockState> â˜ƒxxxxxxxx = (var2x, var3x) -> {
         â˜ƒ.add(var2x.immutable());
         â˜ƒ.setBlock(var2x, var3x, 19);
      };
      BiConsumer<BlockPos, BlockState> â˜ƒxxxxxxxxx = (var2x, var3x) -> {
         â˜ƒ.add(var2x.immutable());
         â˜ƒ.setBlock(var2x, var3x, 19);
      };
      boolean â˜ƒxxxxxxxxxx = this.doPlace(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxx);
      if (â˜ƒxxxxxxxxxx && (!â˜ƒxxxx.isEmpty() || !â˜ƒxxxxx.isEmpty())) {
         if (!â˜ƒxxx.decorators.isEmpty()) {
            List<BlockPos> â˜ƒxxxxxxxxxxx = Lists.<BlockPos>newArrayList(â˜ƒxxxx);
            List<BlockPos> â˜ƒxxxxxxxxxxxx = Lists.<BlockPos>newArrayList(â˜ƒxxxxx);
            â˜ƒxxxxxxxxxxx.sort(Comparator.comparingInt(Vec3i::getY));
            â˜ƒxxxxxxxxxxxx.sort(Comparator.comparingInt(Vec3i::getY));
            â˜ƒxxx.decorators.forEach(var5x -> var5x.place(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
         }

         return BoundingBox.encapsulatingPositions(Iterables.concat(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx)).map(var3x -> {
            DiscreteVoxelShape â˜ƒ = updateLeaves(â˜ƒ, var3x, â˜ƒ, â˜ƒ);
            StructureTemplate.updateShapeAtEdge(â˜ƒ, 3, â˜ƒ, var3x.minX(), var3x.minY(), var3x.minZ());
            return true;
         }).orElse(false);
      } else {
         return false;
      }
   }

   private static DiscreteVoxelShape updateLeaves(LevelAccessor var0, BoundingBox var1, Set<BlockPos> var2, Set<BlockPos> var3) {
      List<Set<BlockPos>> â˜ƒ = Lists.newArrayList();
      DiscreteVoxelShape â˜ƒx = new BitSetDiscreteVoxelShape(â˜ƒ.getXSpan(), â˜ƒ.getYSpan(), â˜ƒ.getZSpan());
      int â˜ƒxx = 6;

      for(int â˜ƒxxx = 0; â˜ƒxxx < 6; ++â˜ƒxxx) {
         â˜ƒ.add(Sets.newHashSet());
      }

      BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

      for(BlockPos â˜ƒxxxx : Lists.newArrayList(â˜ƒ)) {
         if (â˜ƒ.isInside(â˜ƒxxxx)) {
            â˜ƒx.fill(â˜ƒxxxx.getX() - â˜ƒ.minX(), â˜ƒxxxx.getY() - â˜ƒ.minY(), â˜ƒxxxx.getZ() - â˜ƒ.minZ());
         }
      }

      for(BlockPos â˜ƒxxxx : Lists.newArrayList(â˜ƒ)) {
         if (â˜ƒ.isInside(â˜ƒxxxx)) {
            â˜ƒx.fill(â˜ƒxxxx.getX() - â˜ƒ.minX(), â˜ƒxxxx.getY() - â˜ƒ.minY(), â˜ƒxxxx.getZ() - â˜ƒ.minZ());
         }

         for(Direction â˜ƒxxxxx : Direction.values()) {
            â˜ƒxxx.setWithOffset(â˜ƒxxxx, â˜ƒxxxxx);
            if (!â˜ƒ.contains(â˜ƒxxx)) {
               BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
               if (â˜ƒxxxxxx.hasProperty(BlockStateProperties.DISTANCE)) {
                  ((Set)â˜ƒ.get(0)).add(â˜ƒxxx.immutable());
                  setBlockKnownShape(â˜ƒ, â˜ƒxxx, â˜ƒxxxxxx.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(1)));
                  if (â˜ƒ.isInside(â˜ƒxxx)) {
                     â˜ƒx.fill(â˜ƒxxx.getX() - â˜ƒ.minX(), â˜ƒxxx.getY() - â˜ƒ.minY(), â˜ƒxxx.getZ() - â˜ƒ.minZ());
                  }
               }
            }
         }
      }

      for(int â˜ƒxxxx = 1; â˜ƒxxxx < 6; ++â˜ƒxxxx) {
         Set<BlockPos> â˜ƒxxxxx = (Set)â˜ƒ.get(â˜ƒxxxx - 1);
         Set<BlockPos> â˜ƒxxxxxx = (Set)â˜ƒ.get(â˜ƒxxxx);

         for(BlockPos â˜ƒxxxxxxx : â˜ƒxxxxx) {
            if (â˜ƒ.isInside(â˜ƒxxxxxxx)) {
               â˜ƒx.fill(â˜ƒxxxxxxx.getX() - â˜ƒ.minX(), â˜ƒxxxxxxx.getY() - â˜ƒ.minY(), â˜ƒxxxxxxx.getZ() - â˜ƒ.minZ());
            }

            for(Direction â˜ƒxxxxxxxx : Direction.values()) {
               â˜ƒxxx.setWithOffset(â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
               if (!â˜ƒxxxxx.contains(â˜ƒxxx) && !â˜ƒxxxxxx.contains(â˜ƒxxx)) {
                  BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
                  if (â˜ƒxxxxxxxxx.hasProperty(BlockStateProperties.DISTANCE)) {
                     int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx.getValue(BlockStateProperties.DISTANCE);
                     if (â˜ƒxxxxxxxxxx > â˜ƒxxxx + 1) {
                        BlockState â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx.setValue(BlockStateProperties.DISTANCE, Integer.valueOf(â˜ƒxxxx + 1));
                        setBlockKnownShape(â˜ƒ, â˜ƒxxx, â˜ƒxxxxxxxxxxx);
                        if (â˜ƒ.isInside(â˜ƒxxx)) {
                           â˜ƒx.fill(â˜ƒxxx.getX() - â˜ƒ.minX(), â˜ƒxxx.getY() - â˜ƒ.minY(), â˜ƒxxx.getZ() - â˜ƒ.minZ());
                        }

                        â˜ƒxxxxxx.add(â˜ƒxxx.immutable());
                     }
                  }
               }
            }
         }
      }

      return â˜ƒx;
   }
}
