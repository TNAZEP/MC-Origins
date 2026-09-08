package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.util.ExtraCodecs;

public class NoiseSlideSettings {
   public static final Codec<NoiseSlideSettings> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.INT.fieldOf("target").forGetter(NoiseSlideSettings::target),
               ExtraCodecs.NON_NEGATIVE_INT.fieldOf("size").forGetter(NoiseSlideSettings::size),
               Codec.INT.fieldOf("offset").forGetter(NoiseSlideSettings::offset)
            )
            .apply(var0, NoiseSlideSettings::new)
   );
   private final int target;
   private final int size;
   private final int offset;

   public NoiseSlideSettings(int var1, int var2, int var3) {
      this.target = â˜ƒ;
      this.size = â˜ƒ;
      this.offset = â˜ƒ;
   }

   public int target() {
      return this.target;
   }

   public int size() {
      return this.size;
   }

   public int offset() {
      return this.offset;
   }
}
