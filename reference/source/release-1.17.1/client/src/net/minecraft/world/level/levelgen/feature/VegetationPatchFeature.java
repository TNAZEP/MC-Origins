package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public class VegetationPatchFeature extends Feature<VegetationPatchConfiguration> {
   public VegetationPatchFeature(Codec<VegetationPatchConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<VegetationPatchConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      VegetationPatchConfiguration â˜ƒx = â˜ƒ.config();
      Random â˜ƒxx = â˜ƒ.random();
      BlockPos â˜ƒxxx = â˜ƒ.origin();
      Predicate<BlockState> â˜ƒxxxx = getReplaceableTag(â˜ƒx);
      int â˜ƒxxxxx = â˜ƒx.xzRadius.sample(â˜ƒxx) + 1;
      int â˜ƒxxxxxx = â˜ƒx.xzRadius.sample(â˜ƒxx) + 1;
      Set<BlockPos> â˜ƒxxxxxxx = this.placeGroundPatch(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
      this.distributeVegetation(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
      return !â˜ƒxxxxxxx.isEmpty();
   }

   protected Set<BlockPos> placeGroundPatch(
      WorldGenLevel var1, VegetationPatchConfiguration var2, Random var3, BlockPos var4, Predicate<BlockState> var5, int var6, int var7
   ) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
      BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.mutable();
      Direction â˜ƒxx = â˜ƒ.surface.getDirection();
      Direction â˜ƒxxx = â˜ƒxx.getOpposite();
      Set<BlockPos> â˜ƒxxxx = new HashSet();

      for(int â˜ƒxxxxx = -â˜ƒ; â˜ƒxxxxx <= â˜ƒ; ++â˜ƒxxxxx) {
         boolean â˜ƒxxxxxx = â˜ƒxxxxx == -â˜ƒ || â˜ƒxxxxx == â˜ƒ;

         for(int â˜ƒxxxxxxx = -â˜ƒ; â˜ƒxxxxxxx <= â˜ƒ; ++â˜ƒxxxxxxx) {
            boolean â˜ƒxxxxxxxx = â˜ƒxxxxxxx == -â˜ƒ || â˜ƒxxxxxxx == â˜ƒ;
            boolean â˜ƒxxxxxxxxx = â˜ƒxxxxxx || â˜ƒxxxxxxxx;
            boolean â˜ƒxxxxxxxxxx = â˜ƒxxxxxx && â˜ƒxxxxxxxx;
            boolean â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx && !â˜ƒxxxxxxxxxx;
            if (!â˜ƒxxxxxxxxxx && (!â˜ƒxxxxxxxxxxx || â˜ƒ.extraEdgeColumnChance != 0.0F && !(â˜ƒ.nextFloat() > â˜ƒ.extraEdgeColumnChance))) {
               â˜ƒ.setWithOffset(â˜ƒ, â˜ƒxxxxx, 0, â˜ƒxxxxxxx);

               for(int â˜ƒxxxxxxxxxxxx = 0;
                  â˜ƒ.isStateAtPosition(â˜ƒ, BlockBehaviour.BlockStateBase::isAir) && â˜ƒxxxxxxxxxxxx < â˜ƒ.verticalRange;
                  ++â˜ƒxxxxxxxxxxxx
               ) {
                  â˜ƒ.move(â˜ƒxx);
               }

               for(int var25 = 0; â˜ƒ.isStateAtPosition(â˜ƒ, var0 -> !var0.isAir()) && var25 < â˜ƒ.verticalRange; ++var25) {
                  â˜ƒ.move(â˜ƒxxx);
               }

               â˜ƒx.setWithOffset(â˜ƒ, â˜ƒ.surface.getDirection());
               BlockState â˜ƒxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒx);
               if (â˜ƒ.isEmptyBlock(â˜ƒ) && â˜ƒxxxxxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒx, â˜ƒ.surface.getDirection().getOpposite())) {
                  int â˜ƒxxxxxxxxxxxxx = â˜ƒ.depth.sample(â˜ƒ) + (â˜ƒ.extraBottomBlockChance > 0.0F && â˜ƒ.nextFloat() < â˜ƒ.extraBottomBlockChance ? 1 : 0);
                  BlockPos â˜ƒxxxxxxxxxxxxxx = â˜ƒx.immutable();
                  boolean â˜ƒxxxxxxxxxxxxxxx = this.placeGround(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxxxxxxxxxxxx);
                  if (â˜ƒxxxxxxxxxxxxxxx) {
                     â˜ƒxxxx.add(â˜ƒxxxxxxxxxxxxxx);
                  }
               }
            }
         }
      }

      return â˜ƒxxxx;
   }

   protected void distributeVegetation(
      FeaturePlaceContext<VegetationPatchConfiguration> var1,
      WorldGenLevel var2,
      VegetationPatchConfiguration var3,
      Random var4,
      Set<BlockPos> var5,
      int var6,
      int var7
   ) {
      for(BlockPos â˜ƒ : â˜ƒ) {
         if (â˜ƒ.vegetationChance > 0.0F && â˜ƒ.nextFloat() < â˜ƒ.vegetationChance) {
            this.placeVegetation(â˜ƒ, â˜ƒ, â˜ƒ.chunkGenerator(), â˜ƒ, â˜ƒ);
         }
      }
   }

   protected boolean placeVegetation(WorldGenLevel var1, VegetationPatchConfiguration var2, ChunkGenerator var3, Random var4, BlockPos var5) {
      return ((ConfiguredFeature)â˜ƒ.vegetationFeature.get()).place(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.relative(â˜ƒ.surface.getDirection().getOpposite()));
   }

   protected boolean placeGround(
      WorldGenLevel var1, VegetationPatchConfiguration var2, Predicate<BlockState> var3, Random var4, BlockPos.MutableBlockPos var5, int var6
   ) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         BlockState â˜ƒx = â˜ƒ.groundState.getState(â˜ƒ, â˜ƒ);
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
         if (!â˜ƒx.is(â˜ƒxx.getBlock())) {
            if (!â˜ƒ.test(â˜ƒxx)) {
               return â˜ƒ != 0;
            }

            â˜ƒ.setBlock(â˜ƒ, â˜ƒx, 2);
            â˜ƒ.move(â˜ƒ.surface.getDirection());
         }
      }

      return true;
   }

   private static Predicate<BlockState> getReplaceableTag(VegetationPatchConfiguration var0) {
      Tag<Block> â˜ƒ = BlockTags.getAllTags().getTag(â˜ƒ.replaceable);
      return â˜ƒ == null ? var0x -> true : var1x -> var1x.is(â˜ƒ);
   }
}
