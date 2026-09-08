package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class WeightedConfiguredFeature {
   public static final Codec<WeightedConfiguredFeature> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               ConfiguredFeature.CODEC
                  .fieldOf("feature")
                  .flatXmap(ExtraCodecs.nonNullSupplierCheck(), ExtraCodecs.nonNullSupplierCheck())
                  .forGetter(var0x -> var0x.feature),
               Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter(var0x -> var0x.chance)
            )
            .apply(var0, WeightedConfiguredFeature::new)
   );
   public final Supplier<ConfiguredFeature<?, ?>> feature;
   public final float chance;

   public WeightedConfiguredFeature(ConfiguredFeature<?, ?> var1, float var2) {
      this(() -> â˜ƒ, â˜ƒ);
   }

   private WeightedConfiguredFeature(Supplier<ConfiguredFeature<?, ?>> var1, float var2) {
      this.feature = â˜ƒ;
      this.chance = â˜ƒ;
   }

   public boolean place(WorldGenLevel var1, ChunkGenerator var2, Random var3, BlockPos var4) {
      return ((ConfiguredFeature)this.feature.get()).place(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
