package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public abstract class TrunkPlacer {
   public static final Codec<TrunkPlacer> CODEC = Registry.TRUNK_PLACER_TYPES.dispatch(TrunkPlacer::type, TrunkPlacerType::codec);
   private static final int MAX_BASE_HEIGHT = 32;
   private static final int MAX_RAND = 24;
   public static final int MAX_HEIGHT = 80;
   protected final int baseHeight;
   protected final int heightRandA;
   protected final int heightRandB;

   protected static <P extends TrunkPlacer> P3<Mu<P>, Integer, Integer, Integer> trunkPlacerParts(Instance<P> var0) {
      return â˜ƒ.group(
         Codec.intRange(0, 32).fieldOf("base_height").forGetter(var0x -> var0x.baseHeight),
         Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter(var0x -> var0x.heightRandA),
         Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter(var0x -> var0x.heightRandB)
      );
   }

   public TrunkPlacer(int var1, int var2, int var3) {
      this.baseHeight = â˜ƒ;
      this.heightRandA = â˜ƒ;
      this.heightRandB = â˜ƒ;
   }

   protected abstract TrunkPlacerType<?> type();

   public abstract List<FoliagePlacer.FoliageAttachment> placeTrunk(
      LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, int var4, BlockPos var5, TreeConfiguration var6
   );

   public int getTreeHeight(Random var1) {
      return this.baseHeight + â˜ƒ.nextInt(this.heightRandA + 1) + â˜ƒ.nextInt(this.heightRandB + 1);
   }

   private static boolean isDirt(LevelSimulatedReader var0, BlockPos var1) {
      return â˜ƒ.isStateAtPosition(â˜ƒ, var0x -> Feature.isDirt(var0x) && !var0x.is(Blocks.GRASS_BLOCK) && !var0x.is(Blocks.MYCELIUM));
   }

   protected static void setDirtAt(LevelSimulatedReader var0, BiConsumer<BlockPos, BlockState> var1, Random var2, BlockPos var3, TreeConfiguration var4) {
      if (â˜ƒ.forceDirt || !isDirt(â˜ƒ, â˜ƒ)) {
         â˜ƒ.accept(â˜ƒ, â˜ƒ.dirtProvider.getState(â˜ƒ, â˜ƒ));
      }
   }

   protected static boolean placeLog(LevelSimulatedReader var0, BiConsumer<BlockPos, BlockState> var1, Random var2, BlockPos var3, TreeConfiguration var4) {
      return placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Function.identity());
   }

   protected static boolean placeLog(
      LevelSimulatedReader var0,
      BiConsumer<BlockPos, BlockState> var1,
      Random var2,
      BlockPos var3,
      TreeConfiguration var4,
      Function<BlockState, BlockState> var5
   ) {
      if (TreeFeature.validTreePos(â˜ƒ, â˜ƒ)) {
         â˜ƒ.accept(â˜ƒ, (BlockState)â˜ƒ.apply(â˜ƒ.trunkProvider.getState(â˜ƒ, â˜ƒ)));
         return true;
      } else {
         return false;
      }
   }

   protected static void placeLogIfFree(
      LevelSimulatedReader var0, BiConsumer<BlockPos, BlockState> var1, Random var2, BlockPos.MutableBlockPos var3, TreeConfiguration var4
   ) {
      if (TreeFeature.isFree(â˜ƒ, â˜ƒ)) {
         placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
