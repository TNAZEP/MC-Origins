package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

public class AmbientParticleSettings {
   public static final Codec<AmbientParticleSettings> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               ParticleTypes.CODEC.fieldOf("options").forGetter(var0x -> var0x.options),
               Codec.FLOAT.fieldOf("probability").forGetter(var0x -> var0x.probability)
            )
            .apply(var0, AmbientParticleSettings::new)
   );
   private final ParticleOptions options;
   private final float probability;

   public AmbientParticleSettings(ParticleOptions var1, float var2) {
      this.options = â˜ƒ;
      this.probability = â˜ƒ;
   }

   public ParticleOptions getOptions() {
      return this.options;
   }

   public boolean canSpawn(Random var1) {
      return â˜ƒ.nextFloat() <= this.probability;
   }
}
