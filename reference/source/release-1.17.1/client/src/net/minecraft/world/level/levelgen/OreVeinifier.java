package net.minecraft.world.level.levelgen;

import java.util.Random;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class OreVeinifier {
   private static final float RARITY = 1.0F;
   private static final float RIDGE_NOISE_FREQUENCY = 4.0F;
   private static final float THICKNESS = 0.08F;
   private static final float VEININESS_THRESHOLD = 0.5F;
   private static final double VEININESS_FREQUENCY = 1.5;
   private static final int EDGE_ROUNDOFF_BEGIN = 20;
   private static final double MAX_EDGE_ROUNDOFF = 0.2;
   private static final float VEIN_SOLIDNESS = 0.7F;
   private static final float MIN_RICHNESS = 0.1F;
   private static final float MAX_RICHNESS = 0.3F;
   private static final float MAX_RICHNESS_THRESHOLD = 0.6F;
   private static final float CHANCE_OF_RAW_ORE_BLOCK = 0.02F;
   private static final float SKIP_ORE_IF_GAP_NOISE_IS_BELOW = -0.3F;
   private final int veinMaxY;
   private final int veinMinY;
   private final BlockState normalBlock;
   private final NormalNoise veininessNoiseSource;
   private final NormalNoise veinANoiseSource;
   private final NormalNoise veinBNoiseSource;
   private final NormalNoise gapNoise;
   private final int cellWidth;
   private final int cellHeight;

   public OreVeinifier(long var1, BlockState var3, int var4, int var5, int var6) {
      Random â˜ƒ = new Random(â˜ƒ);
      this.normalBlock = â˜ƒ;
      this.veininessNoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 1.0);
      this.veinANoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -7, 1.0);
      this.veinBNoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -7, 1.0);
      this.gapNoise = NormalNoise.create(new SimpleRandomSource(0L), -5, 1.0);
      this.cellWidth = â˜ƒ;
      this.cellHeight = â˜ƒ;
      this.veinMaxY = Stream.of(OreVeinifier.VeinType.values()).mapToInt(var0 -> var0.maxY).max().orElse(â˜ƒ);
      this.veinMinY = Stream.of(OreVeinifier.VeinType.values()).mapToInt(var0 -> var0.minY).min().orElse(â˜ƒ);
   }

   public void fillVeininessNoiseColumn(double[] var1, int var2, int var3, int var4, int var5) {
      this.fillNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, this.veininessNoiseSource, 1.5, â˜ƒ, â˜ƒ);
   }

   public void fillNoiseColumnA(double[] var1, int var2, int var3, int var4, int var5) {
      this.fillNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, this.veinANoiseSource, 4.0, â˜ƒ, â˜ƒ);
   }

   public void fillNoiseColumnB(double[] var1, int var2, int var3, int var4, int var5) {
      this.fillNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, this.veinBNoiseSource, 4.0, â˜ƒ, â˜ƒ);
   }

   public void fillNoiseColumn(double[] var1, int var2, int var3, NormalNoise var4, double var5, int var7, int var8) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         int â˜ƒxx = â˜ƒ + â˜ƒ;
         int â˜ƒxxx = â˜ƒ * this.cellWidth;
         int â˜ƒxxxx = â˜ƒxx * this.cellHeight;
         int â˜ƒxxxxx = â˜ƒ * this.cellWidth;
         double â˜ƒx;
         if (â˜ƒxxxx >= this.veinMinY && â˜ƒxxxx <= this.veinMaxY) {
            â˜ƒx = â˜ƒ.getValue((double)â˜ƒxxx * â˜ƒ, (double)â˜ƒxxxx * â˜ƒ, (double)â˜ƒxxxxx * â˜ƒ);
         } else {
            â˜ƒx = 0.0;
         }

         â˜ƒ[â˜ƒ] = â˜ƒx;
      }
   }

   public BlockState oreVeinify(RandomSource var1, int var2, int var3, int var4, double var5, double var7, double var9) {
      BlockState â˜ƒ = this.normalBlock;
      OreVeinifier.VeinType â˜ƒx = this.getVeinType(â˜ƒ, â˜ƒ);
      if (â˜ƒx == null) {
         return â˜ƒ;
      } else if (â˜ƒ.nextFloat() > 0.7F) {
         return â˜ƒ;
      } else if (this.isVein(â˜ƒ, â˜ƒ)) {
         double â˜ƒ = Mth.clampedMap(Math.abs(â˜ƒ), 0.5, 0.6F, 0.1F, 0.3F);
         if ((double)â˜ƒ.nextFloat() < â˜ƒ && this.gapNoise.getValue((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ) > -0.3F) {
            return â˜ƒ.nextFloat() < 0.02F ? â˜ƒx.rawOreBlock : â˜ƒx.ore;
         } else {
            return â˜ƒx.filler;
         }
      } else {
         return â˜ƒ;
      }
   }

   private boolean isVein(double var1, double var3) {
      double â˜ƒ = Math.abs(1.0 * â˜ƒ) - 0.08F;
      double â˜ƒx = Math.abs(1.0 * â˜ƒ) - 0.08F;
      return Math.max(â˜ƒ, â˜ƒx) < 0.0;
   }

   @Nullable
   private OreVeinifier.VeinType getVeinType(double var1, int var3) {
      OreVeinifier.VeinType â˜ƒ = â˜ƒ > 0.0 ? OreVeinifier.VeinType.COPPER : OreVeinifier.VeinType.IRON;
      int â˜ƒx = â˜ƒ.maxY - â˜ƒ;
      int â˜ƒxx = â˜ƒ - â˜ƒ.minY;
      if (â˜ƒxx >= 0 && â˜ƒx >= 0) {
         int â˜ƒxxx = Math.min(â˜ƒx, â˜ƒxx);
         double â˜ƒxxxx = Mth.clampedMap((double)â˜ƒxxx, 0.0, 20.0, -0.2, 0.0);
         return Math.abs(â˜ƒ) + â˜ƒxxxx < 0.5 ? null : â˜ƒ;
      } else {
         return null;
      }
   }

   static enum VeinType {
      COPPER(Blocks.COPPER_ORE.defaultBlockState(), Blocks.RAW_COPPER_BLOCK.defaultBlockState(), Blocks.GRANITE.defaultBlockState(), 0, 50),
      IRON(Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(), Blocks.RAW_IRON_BLOCK.defaultBlockState(), Blocks.TUFF.defaultBlockState(), -60, -8);

      final BlockState ore;
      final BlockState rawOreBlock;
      final BlockState filler;
      final int minY;
      final int maxY;

      private VeinType(BlockState var3, BlockState var4, BlockState var5, int var6, int var7) {
         this.ore = â˜ƒ;
         this.rawOreBlock = â˜ƒ;
         this.filler = â˜ƒ;
         this.minY = â˜ƒ;
         this.maxY = â˜ƒ;
      }
   }
}
