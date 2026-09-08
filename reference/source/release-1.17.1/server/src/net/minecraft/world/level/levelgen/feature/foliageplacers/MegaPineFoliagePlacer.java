package net.minecraft.world.level.levelgen.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class MegaPineFoliagePlacer extends FoliagePlacer {
   public static final Codec<MegaPineFoliagePlacer> CODEC = RecordCodecBuilder.create(
      var0 -> foliagePlacerParts(var0)
            .and(IntProvider.codec(0, 24).fieldOf("crown_height").forGetter(var0x -> var0x.crownHeight))
            .apply(var0, MegaPineFoliagePlacer::new)
   );
   private final IntProvider crownHeight;

   public MegaPineFoliagePlacer(IntProvider var1, IntProvider var2, IntProvider var3) {
      super(â˜ƒ, â˜ƒ);
      this.crownHeight = â˜ƒ;
   }

   @Override
   protected FoliagePlacerType<?> type() {
      return FoliagePlacerType.MEGA_PINE_FOLIAGE_PLACER;
   }

   @Override
   protected void createFoliage(
      LevelSimulatedReader var1,
      BiConsumer<BlockPos, BlockState> var2,
      Random var3,
      TreeConfiguration var4,
      int var5,
      FoliagePlacer.FoliageAttachment var6,
      int var7,
      int var8,
      int var9
   ) {
      BlockPos â˜ƒ = â˜ƒ.pos();
      int â˜ƒx = 0;

      for(int â˜ƒxx = â˜ƒ.getY() - â˜ƒ + â˜ƒ; â˜ƒxx <= â˜ƒ.getY() + â˜ƒ; ++â˜ƒxx) {
         int â˜ƒxxxx = â˜ƒ.getY() - â˜ƒxx;
         int â˜ƒxxxxx = â˜ƒ + â˜ƒ.radiusOffset() + Mth.floor((float)â˜ƒxxxx / (float)â˜ƒ * 3.5F);
         int â˜ƒxxx;
         if (â˜ƒxxxx > 0 && â˜ƒxxxxx == â˜ƒx && (â˜ƒxx & 1) == 0) {
            â˜ƒxxx = â˜ƒxxxxx + 1;
         } else {
            â˜ƒxxx = â˜ƒxxxxx;
         }

         this.placeLeavesRow(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, new BlockPos(â˜ƒ.getX(), â˜ƒxx, â˜ƒ.getZ()), â˜ƒxxx, 0, â˜ƒ.doubleTrunk());
         â˜ƒx = â˜ƒxxxxx;
      }
   }

   @Override
   public int foliageHeight(Random var1, int var2, TreeConfiguration var3) {
      return this.crownHeight.sample(â˜ƒ);
   }

   @Override
   protected boolean shouldSkipLocation(Random var1, int var2, int var3, int var4, int var5, boolean var6) {
      if (â˜ƒ + â˜ƒ >= 7) {
         return true;
      } else {
         return â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ > â˜ƒ * â˜ƒ;
      }
   }
}
