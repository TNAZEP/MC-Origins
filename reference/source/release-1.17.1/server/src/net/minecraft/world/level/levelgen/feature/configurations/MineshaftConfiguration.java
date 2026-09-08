package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.world.level.levelgen.feature.MineshaftFeature;

public class MineshaftConfiguration implements FeatureConfiguration {
   public static final Codec<MineshaftConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(var0x -> var0x.probability),
               MineshaftFeature.Type.CODEC.fieldOf("type").forGetter(var0x -> var0x.type)
            )
            .apply(var0, MineshaftConfiguration::new)
   );
   public final float probability;
   public final MineshaftFeature.Type type;

   public MineshaftConfiguration(float var1, MineshaftFeature.Type var2) {
      this.probability = â˜ƒ;
      this.type = â˜ƒ;
   }
}
