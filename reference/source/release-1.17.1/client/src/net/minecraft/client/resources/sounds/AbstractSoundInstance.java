package net.minecraft.client.resources.sounds;

import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public abstract class AbstractSoundInstance implements SoundInstance {
   protected Sound sound;
   protected final SoundSource source;
   protected final ResourceLocation location;
   protected float volume = 1.0F;
   protected float pitch = 1.0F;
   protected double x;
   protected double y;
   protected double z;
   protected boolean looping;
   protected int delay;
   protected SoundInstance.Attenuation attenuation = SoundInstance.Attenuation.LINEAR;
   protected boolean relative;

   protected AbstractSoundInstance(SoundEvent var1, SoundSource var2) {
      this(â˜ƒ.getLocation(), â˜ƒ);
   }

   protected AbstractSoundInstance(ResourceLocation var1, SoundSource var2) {
      this.location = â˜ƒ;
      this.source = â˜ƒ;
   }

   @Override
   public ResourceLocation getLocation() {
      return this.location;
   }

   @Override
   public WeighedSoundEvents resolve(SoundManager var1) {
      WeighedSoundEvents â˜ƒ = â˜ƒ.getSoundEvent(this.location);
      if (â˜ƒ == null) {
         this.sound = SoundManager.EMPTY_SOUND;
      } else {
         this.sound = â˜ƒ.getSound();
      }

      return â˜ƒ;
   }

   @Override
   public Sound getSound() {
      return this.sound;
   }

   @Override
   public SoundSource getSource() {
      return this.source;
   }

   @Override
   public boolean isLooping() {
      return this.looping;
   }

   @Override
   public int getDelay() {
      return this.delay;
   }

   @Override
   public float getVolume() {
      return this.volume * this.sound.getVolume();
   }

   @Override
   public float getPitch() {
      return this.pitch * this.sound.getPitch();
   }

   @Override
   public double getX() {
      return this.x;
   }

   @Override
   public double getY() {
      return this.y;
   }

   @Override
   public double getZ() {
      return this.z;
   }

   @Override
   public SoundInstance.Attenuation getAttenuation() {
      return this.attenuation;
   }

   @Override
   public boolean isRelative() {
      return this.relative;
   }

   public String toString() {
      return "SoundInstance[" + this.location + "]";
   }
}
