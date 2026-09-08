package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationPath;

public class VibrationParticleOption implements ParticleOptions {
   public static final Codec<VibrationParticleOption> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(VibrationPath.CODEC.fieldOf("vibration").forGetter(var0x -> var0x.vibrationPath)).apply(var0, VibrationParticleOption::new)
   );
   public static final ParticleOptions.Deserializer<VibrationParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<VibrationParticleOption>() {
      public VibrationParticleOption fromCommand(ParticleType<VibrationParticleOption> var1, StringReader var2) throws CommandSyntaxException {
         â˜ƒ.expect(' ');
         float â˜ƒ = (float)â˜ƒ.readDouble();
         â˜ƒ.expect(' ');
         float â˜ƒx = (float)â˜ƒ.readDouble();
         â˜ƒ.expect(' ');
         float â˜ƒxx = (float)â˜ƒ.readDouble();
         â˜ƒ.expect(' ');
         float â˜ƒxxx = (float)â˜ƒ.readDouble();
         â˜ƒ.expect(' ');
         float â˜ƒxxxx = (float)â˜ƒ.readDouble();
         â˜ƒ.expect(' ');
         float â˜ƒxxxxx = (float)â˜ƒ.readDouble();
         â˜ƒ.expect(' ');
         int â˜ƒxxxxxx = â˜ƒ.readInt();
         BlockPos â˜ƒxxxxxxx = new BlockPos((double)â˜ƒ, (double)â˜ƒx, (double)â˜ƒxx);
         BlockPos â˜ƒxxxxxxxx = new BlockPos((double)â˜ƒxxx, (double)â˜ƒxxxx, (double)â˜ƒxxxxx);
         return new VibrationParticleOption(new VibrationPath(â˜ƒxxxxxxx, new BlockPositionSource(â˜ƒxxxxxxxx), â˜ƒxxxxxx));
      }

      public VibrationParticleOption fromNetwork(ParticleType<VibrationParticleOption> var1, FriendlyByteBuf var2) {
         VibrationPath â˜ƒ = VibrationPath.read(â˜ƒ);
         return new VibrationParticleOption(â˜ƒ);
      }
   };
   private final VibrationPath vibrationPath;

   public VibrationParticleOption(VibrationPath var1) {
      this.vibrationPath = â˜ƒ;
   }

   @Override
   public void writeToNetwork(FriendlyByteBuf var1) {
      VibrationPath.write(â˜ƒ, this.vibrationPath);
   }

   @Override
   public String writeToString() {
      BlockPos â˜ƒ = this.vibrationPath.getOrigin();
      double â˜ƒx = (double)â˜ƒ.getX();
      double â˜ƒxx = (double)â˜ƒ.getY();
      double â˜ƒxxx = (double)â˜ƒ.getZ();
      return String.format(
         Locale.ROOT,
         "%s %.2f %.2f %.2f %.2f %.2f %.2f %d",
         Registry.PARTICLE_TYPE.getKey(this.getType()),
         â˜ƒx,
         â˜ƒxx,
         â˜ƒxxx,
         â˜ƒx,
         â˜ƒxx,
         â˜ƒxxx,
         this.vibrationPath.getArrivalInTicks()
      );
   }

   @Override
   public ParticleType<VibrationParticleOption> getType() {
      return ParticleTypes.VIBRATION;
   }

   public VibrationPath getVibrationPath() {
      return this.vibrationPath;
   }
}
