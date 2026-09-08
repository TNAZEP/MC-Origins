package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class DustParticleOptions extends DustParticleOptionsBase {
   public static final Vector3f REDSTONE_PARTICLE_COLOR = new Vector3f(Vec3.fromRGB24(16711680));
   public static final DustParticleOptions REDSTONE = new DustParticleOptions(REDSTONE_PARTICLE_COLOR, 1.0F);
   public static final Codec<DustParticleOptions> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(Vector3f.CODEC.fieldOf("color").forGetter(var0x -> var0x.color), Codec.FLOAT.fieldOf("scale").forGetter(var0x -> var0x.scale))
            .apply(var0, DustParticleOptions::new)
   );
   public static final ParticleOptions.Deserializer<DustParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<DustParticleOptions>() {
      public DustParticleOptions fromCommand(ParticleType<DustParticleOptions> var1, StringReader var2) throws CommandSyntaxException {
         Vector3f â˜ƒ = DustParticleOptionsBase.readVector3f(â˜ƒ);
         â˜ƒ.expect(' ');
         float â˜ƒx = â˜ƒ.readFloat();
         return new DustParticleOptions(â˜ƒ, â˜ƒx);
      }

      public DustParticleOptions fromNetwork(ParticleType<DustParticleOptions> var1, FriendlyByteBuf var2) {
         return new DustParticleOptions(DustParticleOptionsBase.readVector3f(â˜ƒ), â˜ƒ.readFloat());
      }
   };

   public DustParticleOptions(Vector3f var1, float var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   public ParticleType<DustParticleOptions> getType() {
      return ParticleTypes.DUST;
   }
}
