package net.minecraft.client.sounds;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;

public class MusicManager {
   private static final int STARTING_DELAY = 100;
   private final Random random = new Random();
   private final Minecraft minecraft;
   @Nullable
   private SoundInstance currentMusic;
   private int nextSongDelay = 100;

   public MusicManager(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   public void tick() {
      Music â˜ƒ = this.minecraft.getSituationalMusic();
      if (this.currentMusic != null) {
         if (!â˜ƒ.getEvent().getLocation().equals(this.currentMusic.getLocation()) && â˜ƒ.replaceCurrentMusic()) {
            this.minecraft.getSoundManager().stop(this.currentMusic);
            this.nextSongDelay = Mth.nextInt(this.random, 0, â˜ƒ.getMinDelay() / 2);
         }

         if (!this.minecraft.getSoundManager().isActive(this.currentMusic)) {
            this.currentMusic = null;
            this.nextSongDelay = Math.min(this.nextSongDelay, Mth.nextInt(this.random, â˜ƒ.getMinDelay(), â˜ƒ.getMaxDelay()));
         }
      }

      this.nextSongDelay = Math.min(this.nextSongDelay, â˜ƒ.getMaxDelay());
      if (this.currentMusic == null && this.nextSongDelay-- <= 0) {
         this.startPlaying(â˜ƒ);
      }
   }

   public void startPlaying(Music var1) {
      this.currentMusic = SimpleSoundInstance.forMusic(â˜ƒ.getEvent());
      if (this.currentMusic.getSound() != SoundManager.EMPTY_SOUND) {
         this.minecraft.getSoundManager().play(this.currentMusic);
      }

      this.nextSongDelay = Integer.MAX_VALUE;
   }

   public void stopPlaying() {
      if (this.currentMusic != null) {
         this.minecraft.getSoundManager().stop(this.currentMusic);
         this.currentMusic = null;
      }

      this.nextSongDelay += 100;
   }

   public boolean isPlayingMusic(Music var1) {
      return this.currentMusic == null ? false : â˜ƒ.getEvent().getLocation().equals(this.currentMusic.getLocation());
   }
}
