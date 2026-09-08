package net.minecraft.client.resources.sounds;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SimpleSoundInstance extends AbstractSoundInstance {
   public SimpleSoundInstance(SoundEvent var1, SoundSource var2, float var3, float var4, BlockPos var5) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5);
   }

   public static SimpleSoundInstance forUI(SoundEvent var0, float var1) {
      return forUI(â˜ƒ, â˜ƒ, 0.25F);
   }

   public static SimpleSoundInstance forUI(SoundEvent var0, float var1, float var2) {
      return new SimpleSoundInstance(â˜ƒ.getLocation(), SoundSource.MASTER, â˜ƒ, â˜ƒ, false, 0, SoundInstance.Attenuation.NONE, 0.0, 0.0, 0.0, true);
   }

   public static SimpleSoundInstance forMusic(SoundEvent var0) {
      return new SimpleSoundInstance(â˜ƒ.getLocation(), SoundSource.MUSIC, 1.0F, 1.0F, false, 0, SoundInstance.Attenuation.NONE, 0.0, 0.0, 0.0, true);
   }

   public static SimpleSoundInstance forRecord(SoundEvent var0, double var1, double var3, double var5) {
      return new SimpleSoundInstance(â˜ƒ, SoundSource.RECORDS, 4.0F, 1.0F, false, 0, SoundInstance.Attenuation.LINEAR, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static SimpleSoundInstance forLocalAmbience(SoundEvent var0, float var1, float var2) {
      return new SimpleSoundInstance(â˜ƒ.getLocation(), SoundSource.AMBIENT, â˜ƒ, â˜ƒ, false, 0, SoundInstance.Attenuation.NONE, 0.0, 0.0, 0.0, true);
   }

   public static SimpleSoundInstance forAmbientAddition(SoundEvent var0) {
      return forLocalAmbience(â˜ƒ, 1.0F, 1.0F);
   }

   public static SimpleSoundInstance forAmbientMood(SoundEvent var0, double var1, double var3, double var5) {
      return new SimpleSoundInstance(â˜ƒ, SoundSource.AMBIENT, 1.0F, 1.0F, false, 0, SoundInstance.Attenuation.LINEAR, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public SimpleSoundInstance(SoundEvent var1, SoundSource var2, float var3, float var4, double var5, double var7, double var9) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, 0, SoundInstance.Attenuation.LINEAR, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private SimpleSoundInstance(
      SoundEvent var1,
      SoundSource var2,
      float var3,
      float var4,
      boolean var5,
      int var6,
      SoundInstance.Attenuation var7,
      double var8,
      double var10,
      double var12
   ) {
      this(â˜ƒ.getLocation(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   public SimpleSoundInstance(
      ResourceLocation var1,
      SoundSource var2,
      float var3,
      float var4,
      boolean var5,
      int var6,
      SoundInstance.Attenuation var7,
      double var8,
      double var10,
      double var12,
      boolean var14
   ) {
      super(â˜ƒ, â˜ƒ);
      this.volume = â˜ƒ;
      this.pitch = â˜ƒ;
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.looping = â˜ƒ;
      this.delay = â˜ƒ;
      this.attenuation = â˜ƒ;
      this.relative = â˜ƒ;
   }
}
