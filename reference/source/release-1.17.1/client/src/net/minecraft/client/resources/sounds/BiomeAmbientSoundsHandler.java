package net.minecraft.client.resources.sounds;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Optional;
import java.util.Random;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;

public class BiomeAmbientSoundsHandler implements AmbientSoundHandler {
   private static final int LOOP_SOUND_CROSS_FADE_TIME = 40;
   private static final float SKY_MOOD_RECOVERY_RATE = 0.001F;
   private final LocalPlayer player;
   private final SoundManager soundManager;
   private final BiomeManager biomeManager;
   private final Random random;
   private final Object2ObjectArrayMap<Biome, BiomeAmbientSoundsHandler.LoopSoundInstance> loopSounds = new Object2ObjectArrayMap<>();
   private Optional<AmbientMoodSettings> moodSettings = Optional.empty();
   private Optional<AmbientAdditionsSettings> additionsSettings = Optional.empty();
   private float moodiness;
   private Biome previousBiome;

   public BiomeAmbientSoundsHandler(LocalPlayer var1, SoundManager var2, BiomeManager var3) {
      this.random = â˜ƒ.level.getRandom();
      this.player = â˜ƒ;
      this.soundManager = â˜ƒ;
      this.biomeManager = â˜ƒ;
   }

   public float getMoodiness() {
      return this.moodiness;
   }

   @Override
   public void tick() {
      this.loopSounds.values().removeIf(AbstractTickableSoundInstance::isStopped);
      Biome â˜ƒ = this.biomeManager.getNoiseBiomeAtPosition(this.player.getX(), this.player.getY(), this.player.getZ());
      if (â˜ƒ != this.previousBiome) {
         this.previousBiome = â˜ƒ;
         this.moodSettings = â˜ƒ.getAmbientMood();
         this.additionsSettings = â˜ƒ.getAmbientAdditions();
         this.loopSounds.values().forEach(BiomeAmbientSoundsHandler.LoopSoundInstance::fadeOut);
         â˜ƒ.getAmbientLoop().ifPresent(var2 -> this.loopSounds.compute(â˜ƒ, (var2x, var3) -> {
               if (var3 == null) {
                  var3 = new BiomeAmbientSoundsHandler.LoopSoundInstance(var2);
                  this.soundManager.play(var3);
               }

               var3.fadeIn();
               return var3;
            }));
      }

      this.additionsSettings.ifPresent(var1x -> {
         if (this.random.nextDouble() < var1x.getTickChance()) {
            this.soundManager.play(SimpleSoundInstance.forAmbientAddition(var1x.getSoundEvent()));
         }
      });
      this.moodSettings
         .ifPresent(
            var1x -> {
               Level â˜ƒ = this.player.level;
               int â˜ƒx = var1x.getBlockSearchExtent() * 2 + 1;
               BlockPos â˜ƒxx = new BlockPos(
                  this.player.getX() + (double)this.random.nextInt(â˜ƒx) - (double)var1x.getBlockSearchExtent(),
                  this.player.getEyeY() + (double)this.random.nextInt(â˜ƒx) - (double)var1x.getBlockSearchExtent(),
                  this.player.getZ() + (double)this.random.nextInt(â˜ƒx) - (double)var1x.getBlockSearchExtent()
               );
               int â˜ƒxxx = â˜ƒ.getBrightness(LightLayer.SKY, â˜ƒxx);
               if (â˜ƒxxx > 0) {
                  this.moodiness -= (float)â˜ƒxxx / (float)â˜ƒ.getMaxLightLevel() * 0.001F;
               } else {
                  this.moodiness -= (float)(â˜ƒ.getBrightness(LightLayer.BLOCK, â˜ƒxx) - 1) / (float)var1x.getTickDelay();
               }
      
               if (this.moodiness >= 1.0F) {
                  double â˜ƒ = (double)â˜ƒxx.getX() + 0.5;
                  double â˜ƒx = (double)â˜ƒxx.getY() + 0.5;
                  double â˜ƒxx = (double)â˜ƒxx.getZ() + 0.5;
                  double â˜ƒxxx = â˜ƒ - this.player.getX();
                  double â˜ƒxxxx = â˜ƒx - this.player.getEyeY();
                  double â˜ƒxxxxx = â˜ƒxx - this.player.getZ();
                  double â˜ƒxxxxxx = Math.sqrt(â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxxxx * â˜ƒxxxxx);
                  double â˜ƒxxxxxxx = â˜ƒxxxxxx + var1x.getSoundPositionOffset();
                  SimpleSoundInstance â˜ƒxxxxxxxx = SimpleSoundInstance.forAmbientMood(
                     var1x.getSoundEvent(),
                     this.player.getX() + â˜ƒxxx / â˜ƒxxxxxx * â˜ƒxxxxxxx,
                     this.player.getEyeY() + â˜ƒxxxx / â˜ƒxxxxxx * â˜ƒxxxxxxx,
                     this.player.getZ() + â˜ƒxxxxx / â˜ƒxxxxxx * â˜ƒxxxxxxx
                  );
                  this.soundManager.play(â˜ƒxxxxxxxx);
                  this.moodiness = 0.0F;
               } else {
                  this.moodiness = Math.max(this.moodiness, 0.0F);
               }
            }
         );
   }

   public static class LoopSoundInstance extends AbstractTickableSoundInstance {
      private int fadeDirection;
      private int fade;

      public LoopSoundInstance(SoundEvent var1) {
         super(â˜ƒ, SoundSource.AMBIENT);
         this.looping = true;
         this.delay = 0;
         this.volume = 1.0F;
         this.relative = true;
      }

      @Override
      public void tick() {
         if (this.fade < 0) {
            this.stop();
         }

         this.fade += this.fadeDirection;
         this.volume = Mth.clamp((float)this.fade / 40.0F, 0.0F, 1.0F);
      }

      public void fadeOut() {
         this.fade = Math.min(this.fade, 40);
         this.fadeDirection = -1;
      }

      public void fadeIn() {
         this.fade = Math.max(0, this.fade);
         this.fadeDirection = 1;
      }
   }
}
