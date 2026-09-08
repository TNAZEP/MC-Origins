package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.datafixers.Products.P2;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public abstract class FoliagePlacer {
   public static final Codec<FoliagePlacer> CODEC = Registry.FOLIAGE_PLACER_TYPES.dispatch(FoliagePlacer::type, FoliagePlacerType::codec);
   protected final IntProvider radius;
   protected final IntProvider offset;

   protected static <P extends FoliagePlacer> P2<Mu<P>, IntProvider, IntProvider> foliagePlacerParts(Instance<P> var0) {
      return â˜ƒ.group(
         IntProvider.codec(0, 16).fieldOf("radius").forGetter(var0x -> var0x.radius),
         IntProvider.codec(0, 16).fieldOf("offset").forGetter(var0x -> var0x.offset)
      );
   }

   public FoliagePlacer(IntProvider var1, IntProvider var2) {
      this.radius = â˜ƒ;
      this.offset = â˜ƒ;
   }

   protected abstract FoliagePlacerType<?> type();

   public void createFoliage(
      LevelSimulatedReader var1,
      BiConsumer<BlockPos, BlockState> var2,
      Random var3,
      TreeConfiguration var4,
      int var5,
      FoliagePlacer.FoliageAttachment var6,
      int var7,
      int var8
   ) {
      this.createFoliage(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.offset(â˜ƒ));
   }

   protected abstract void createFoliage(
      LevelSimulatedReader var1,
      BiConsumer<BlockPos, BlockState> var2,
      Random var3,
      TreeConfiguration var4,
      int var5,
      FoliagePlacer.FoliageAttachment var6,
      int var7,
      int var8,
      int var9
   );

   public abstract int foliageHeight(Random var1, int var2, TreeConfiguration var3);

   public int foliageRadius(Random var1, int var2) {
      return this.radius.sample(â˜ƒ);
   }

   private int offset(Random var1) {
      return this.offset.sample(â˜ƒ);
   }

   protected abstract boolean shouldSkipLocation(Random var1, int var2, int var3, int var4, int var5, boolean var6);

   protected boolean shouldSkipLocationSigned(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      int â˜ƒ;
      int â˜ƒx;
      if (â˜ƒ) {
         â˜ƒ = Math.min(Math.abs(â˜ƒ), Math.abs(â˜ƒ - 1));
         â˜ƒx = Math.min(Math.abs(â˜ƒ), Math.abs(â˜ƒ - 1));
      } else {
         â˜ƒ = Math.abs(â˜ƒ);
         â˜ƒx = Math.abs(â˜ƒ);
      }

      return this.shouldSkipLocation(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
   }

   protected void placeLeavesRow(
      LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, TreeConfiguration var4, BlockPos var5, int var6, int var7, boolean var8
   ) {
      int â˜ƒ = â˜ƒ ? 1 : 0;
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxx = -â˜ƒ; â˜ƒxx <= â˜ƒ + â˜ƒ; ++â˜ƒxx) {
         for(int â˜ƒxxx = -â˜ƒ; â˜ƒxxx <= â˜ƒ + â˜ƒ; ++â˜ƒxxx) {
            if (!this.shouldSkipLocationSigned(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒxxx, â˜ƒ, â˜ƒ)) {
               â˜ƒx.setWithOffset(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒxxx);
               tryPlaceLeaf(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
            }
         }
      }
   }

   protected static void tryPlaceLeaf(LevelSimulatedReader var0, BiConsumer<BlockPos, BlockState> var1, Random var2, TreeConfiguration var3, BlockPos var4) {
      if (TreeFeature.validTreePos(â˜ƒ, â˜ƒ)) {
         â˜ƒ.accept(â˜ƒ, â˜ƒ.foliageProvider.getState(â˜ƒ, â˜ƒ));
      }
   }

   public static final class FoliageAttachment {
      private final BlockPos pos;
      private final int radiusOffset;
      private final boolean doubleTrunk;

      public FoliageAttachment(BlockPos var1, int var2, boolean var3) {
         this.pos = â˜ƒ;
         this.radiusOffset = â˜ƒ;
         this.doubleTrunk = â˜ƒ;
      }

      public BlockPos pos() {
         return this.pos;
      }

      public int radiusOffset() {
         return this.radiusOffset;
      }

      public boolean doubleTrunk() {
         return this.doubleTrunk;
      }
   }
}
