package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

public abstract class DustParticleOptionsBase implements ParticleOptions {
   public static final float MIN_SCALE = 0.01F;
   public static final float MAX_SCALE = 4.0F;
   protected final Vector3f color;
   protected final float scale;

   public DustParticleOptionsBase(Vector3f var1, float var2) {
      this.color = â˜ƒ;
      this.scale = Mth.clamp(â˜ƒ, 0.01F, 4.0F);
   }

   public static Vector3f readVector3f(StringReader var0) throws CommandSyntaxException {
      â˜ƒ.expect(' ');
      float â˜ƒ = â˜ƒ.readFloat();
      â˜ƒ.expect(' ');
      float â˜ƒx = â˜ƒ.readFloat();
      â˜ƒ.expect(' ');
      float â˜ƒxx = â˜ƒ.readFloat();
      return new Vector3f(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public static Vector3f readVector3f(FriendlyByteBuf var0) {
      return new Vector3f(â˜ƒ.readFloat(), â˜ƒ.readFloat(), â˜ƒ.readFloat());
   }

   @Override
   public void writeToNetwork(FriendlyByteBuf var1) {
      â˜ƒ.writeFloat(this.color.x());
      â˜ƒ.writeFloat(this.color.y());
      â˜ƒ.writeFloat(this.color.z());
      â˜ƒ.writeFloat(this.scale);
   }

   @Override
   public String writeToString() {
      return String.format(
         Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()), this.color.x(), this.color.y(), this.color.z(), this.scale
      );
   }

   public Vector3f getColor() {
      return this.color;
   }

   public float getScale() {
      return this.scale;
   }
}
