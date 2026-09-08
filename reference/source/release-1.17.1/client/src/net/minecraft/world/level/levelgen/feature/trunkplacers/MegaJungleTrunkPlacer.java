package net.minecraft.world.level.levelgen.feature.trunkplacers;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;

public class MegaJungleTrunkPlacer extends GiantTrunkPlacer {
   public static final Codec<MegaJungleTrunkPlacer> CODEC = RecordCodecBuilder.create(var0 -> trunkPlacerParts(var0).apply(var0, MegaJungleTrunkPlacer::new));

   public MegaJungleTrunkPlacer(int var1, int var2, int var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected TrunkPlacerType<?> type() {
      return TrunkPlacerType.MEGA_JUNGLE_TRUNK_PLACER;
   }

   @Override
   public List<FoliagePlacer.FoliageAttachment> placeTrunk(
      LevelSimulatedReader var1, BiConsumer<BlockPos, BlockState> var2, Random var3, int var4, BlockPos var5, TreeConfiguration var6
   ) {
      List<FoliagePlacer.FoliageAttachment> â˜ƒ = Lists.<FoliagePlacer.FoliageAttachment>newArrayList();
      â˜ƒ.addAll(super.placeTrunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));

      for(int â˜ƒx = â˜ƒ - 2 - â˜ƒ.nextInt(4); â˜ƒx > â˜ƒ / 2; â˜ƒx -= 2 + â˜ƒ.nextInt(4)) {
         float â˜ƒxx = â˜ƒ.nextFloat() * (float) (Math.PI * 2);
         int â˜ƒxxx = 0;
         int â˜ƒxxxx = 0;

         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 5; ++â˜ƒxxxxx) {
            â˜ƒxxx = (int)(1.5F + Mth.cos(â˜ƒxx) * (float)â˜ƒxxxxx);
            â˜ƒxxxx = (int)(1.5F + Mth.sin(â˜ƒxx) * (float)â˜ƒxxxxx);
            BlockPos â˜ƒxxxxxx = â˜ƒ.offset(â˜ƒxxx, â˜ƒx - 3 + â˜ƒxxxxx / 2, â˜ƒxxxx);
            placeLog(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxx, â˜ƒ);
         }

         â˜ƒ.add(new FoliagePlacer.FoliageAttachment(â˜ƒ.offset(â˜ƒxxx, â˜ƒx, â˜ƒxxxx), -2, false));
      }

      return â˜ƒ;
   }
}
