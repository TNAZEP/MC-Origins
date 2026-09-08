package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class DustColorTransitionOptions extends DustParticleOptionsBase {
   public static final Vector3f SCULK_PARTICLE_COLOR = new Vector3f(Vec3.fromRGB24(3790560));
   public static final DustColorTransitionOptions SCULK_TO_REDSTONE = new DustColorTransitionOptions(
      SCULK_PARTICLE_COLOR, DustParticleOptions.REDSTONE_PARTICLE_COLOR, 1.0F
   );
   public static final Codec<DustColorTransitionOptions> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Vector3f.CODEC.fieldOf("fromColor").forGetter(var0x -> var0x.color),
               Vector3f.CODEC.fieldOf("toColor").forGetter(var0x -> var0x.toColor),
               Codec.FLOAT.fieldOf("scale").forGetter(var0x -> var0x.scale)
            )
            .apply(var0, DustColorTransitionOptions::new)
   );
   public static final ParticleOptions.Deserializer<DustColorTransitionOptions> DESERIALIZER = new ParticleOptions.Deserializer<DustColorTransitionOptions>() {
      public DustColorTransitionOptions fromCommand(ParticleType<DustColorTransitionOptions> var1, StringReader var2) throws CommandSyntaxException {
         Vector3f â˜ƒ = DustParticleOptionsBase.readVector3f(â˜ƒ);
         â˜ƒ.expect(' ');
         float â˜ƒx = â˜ƒ.readFloat();
         Vector3f â˜ƒxx = DustParticleOptionsBase.readVector3f(â˜ƒ);
         return new DustColorTransitionOptions(â˜ƒ, â˜ƒxx, â˜ƒx);
      }

      public DustColorTransitionOptions fromNetwork(ParticleType<DustColorTransitionOptions> var1, FriendlyByteBuf var2) {
         Vector3f â˜ƒ = DustParticleOptionsBase.readVector3f(â˜ƒ);
         float â˜ƒx = â˜ƒ.readFloat();
         Vector3f â˜ƒxx = DustParticleOptionsBase.readVector3f(â˜ƒ);
         return new DustColorTransitionOptions(â˜ƒ, â˜ƒxx, â˜ƒx);
      }
   };
   private final Vector3f toColor;

   public DustColorTransitionOptions(Vector3f var1, Vector3f var2, float var3) {
      super(â˜ƒ, â˜ƒ);
      this.toColor = â˜ƒ;
   }

   public Vector3f getFromColor() {
      return this.color;
   }

   public Vector3f getToColor() {
      return this.toColor;
   }

   @Override
   public void writeToNetwork(FriendlyByteBuf var1) {
      super.writeToNetwork(â˜ƒ);
      â˜ƒ.writeFloat(this.toColor.x());
      â˜ƒ.writeFloat(this.toColor.y());
      â˜ƒ.writeFloat(this.toColor.z());
   }

   @Override
   public String writeToString() {
      return String.format(
         Locale.ROOT,
         "%s %.2f %.2f %.2f %.2f %.2f %.2f %.2f",
         Registry.PARTICLE_TYPE.getKey(this.getType()),
         this.color.x(),
         this.color.y(),
         this.color.z(),
         this.scale,
         this.toColor.x(),
         this.toColor.y(),
         this.toColor.z()
      );
   }

   @Override
   public ParticleType<DustColorTransitionOptions> getType() {
      return ParticleTypes.DUST_COLOR_TRANSITION;
   }
}
