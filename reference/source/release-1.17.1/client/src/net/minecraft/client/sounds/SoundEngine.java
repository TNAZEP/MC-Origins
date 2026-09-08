package net.minecraft.client.sounds;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Library;
import com.mojang.blaze3d.audio.Listener;
import com.mojang.blaze3d.audio.SoundBuffer;
import com.mojang.math.Vector3f;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.Options;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class SoundEngine {
   private static final Marker MARKER = MarkerManager.getMarker("SOUNDS");
   private static final Logger LOGGER = LogManager.getLogger();
   private static final float PITCH_MIN = 0.5F;
   private static final float PITCH_MAX = 2.0F;
   private static final float VOLUME_MIN = 0.0F;
   private static final float VOLUME_MAX = 1.0F;
   private static final int MIN_SOURCE_LIFETIME = 20;
   private static final Set<ResourceLocation> ONLY_WARN_ONCE = Sets.<ResourceLocation>newHashSet();
   public static final String MISSING_SOUND = "FOR THE DEBUG!";
   private final SoundManager soundManager;
   private final Options options;
   private boolean loaded;
   private final Library library = new Library();
   private final Listener listener = this.library.getListener();
   private final SoundBufferLibrary soundBuffers;
   private final SoundEngineExecutor executor = new SoundEngineExecutor();
   private final ChannelAccess channelAccess = new ChannelAccess(this.library, this.executor);
   private int tickCount;
   private final Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel = Maps.<SoundInstance, ChannelAccess.ChannelHandle>newHashMap();
   private final Multimap<SoundSource, SoundInstance> instanceBySource = HashMultimap.create();
   private final List<TickableSoundInstance> tickingSounds = Lists.<TickableSoundInstance>newArrayList();
   private final Map<SoundInstance, Integer> queuedSounds = Maps.newHashMap();
   private final Map<SoundInstance, Integer> soundDeleteTime = Maps.newHashMap();
   private final List<SoundEventListener> listeners = Lists.<SoundEventListener>newArrayList();
   private final List<TickableSoundInstance> queuedTickableSounds = Lists.<TickableSoundInstance>newArrayList();
   private final List<Sound> preloadQueue = Lists.<Sound>newArrayList();

   public SoundEngine(SoundManager var1, Options var2, ResourceManager var3) {
      this.soundManager = â˜ƒ;
      this.options = â˜ƒ;
      this.soundBuffers = new SoundBufferLibrary(â˜ƒ);
   }

   public void reload() {
      ONLY_WARN_ONCE.clear();

      for(SoundEvent â˜ƒ : Registry.SOUND_EVENT) {
         ResourceLocation â˜ƒx = â˜ƒ.getLocation();
         if (this.soundManager.getSoundEvent(â˜ƒx) == null) {
            LOGGER.warn("Missing sound for event: {}", Registry.SOUND_EVENT.getKey(â˜ƒ));
            ONLY_WARN_ONCE.add(â˜ƒx);
         }
      }

      this.destroy();
      this.loadLibrary();
   }

   private synchronized void loadLibrary() {
      if (!this.loaded) {
         try {
            this.library.init();
            this.listener.reset();
            this.listener.setGain(this.options.getSoundSourceVolume(SoundSource.MASTER));
            this.soundBuffers.preload(this.preloadQueue).thenRun(this.preloadQueue::clear);
            this.loaded = true;
            LOGGER.info(MARKER, "Sound engine started");
         } catch (RuntimeException var2) {
            LOGGER.error(MARKER, "Error starting SoundSystem. Turning off sounds & music", var2);
         }
      }
   }

   private float getVolume(@Nullable SoundSource var1) {
      return â˜ƒ != null && â˜ƒ != SoundSource.MASTER ? this.options.getSoundSourceVolume(â˜ƒ) : 1.0F;
   }

   public void updateCategoryVolume(SoundSource var1, float var2) {
      if (this.loaded) {
         if (â˜ƒ == SoundSource.MASTER) {
            this.listener.setGain(â˜ƒ);
         } else {
            this.instanceToChannel.forEach((var1x, var2x) -> {
               float â˜ƒ = this.calculateVolume(var1x);
               var2x.execute(var1xx -> {
                  if (â˜ƒ <= 0.0F) {
                     var1xx.stop();
                  } else {
                     var1xx.setVolume(â˜ƒ);
                  }
               });
            });
         }
      }
   }

   public void destroy() {
      if (this.loaded) {
         this.stopAll();
         this.soundBuffers.clear();
         this.library.cleanup();
         this.loaded = false;
      }
   }

   public void stop(SoundInstance var1) {
      if (this.loaded) {
         ChannelAccess.ChannelHandle â˜ƒ = (ChannelAccess.ChannelHandle)this.instanceToChannel.get(â˜ƒ);
         if (â˜ƒ != null) {
            â˜ƒ.execute(Channel::stop);
         }
      }
   }

   public void stopAll() {
      if (this.loaded) {
         this.executor.flush();
         this.instanceToChannel.values().forEach(var0 -> var0.execute(Channel::stop));
         this.instanceToChannel.clear();
         this.channelAccess.clear();
         this.queuedSounds.clear();
         this.tickingSounds.clear();
         this.instanceBySource.clear();
         this.soundDeleteTime.clear();
         this.queuedTickableSounds.clear();
      }
   }

   public void addEventListener(SoundEventListener var1) {
      this.listeners.add(â˜ƒ);
   }

   public void removeEventListener(SoundEventListener var1) {
      this.listeners.remove(â˜ƒ);
   }

   public void tick(boolean var1) {
      if (!â˜ƒ) {
         this.tickNonPaused();
      }

      this.channelAccess.scheduleTick();
   }

   private void tickNonPaused() {
      ++this.tickCount;
      this.queuedTickableSounds.stream().filter(SoundInstance::canPlaySound).forEach(this::play);
      this.queuedTickableSounds.clear();

      for(TickableSoundInstance â˜ƒ : this.tickingSounds) {
         if (!â˜ƒ.canPlaySound()) {
            this.stop(â˜ƒ);
         }

         â˜ƒ.tick();
         if (â˜ƒ.isStopped()) {
            this.stop(â˜ƒ);
         } else {
            float â˜ƒx = this.calculateVolume(â˜ƒ);
            float â˜ƒxx = this.calculatePitch(â˜ƒ);
            Vec3 â˜ƒxxx = new Vec3(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
            ChannelAccess.ChannelHandle â˜ƒxxxx = (ChannelAccess.ChannelHandle)this.instanceToChannel.get(â˜ƒ);
            if (â˜ƒxxxx != null) {
               â˜ƒxxxx.execute(var3x -> {
                  var3x.setVolume(â˜ƒ);
                  var3x.setPitch(â˜ƒ);
                  var3x.setSelfPosition(â˜ƒ);
               });
            }
         }
      }

      Iterator<Entry<SoundInstance, ChannelAccess.ChannelHandle>> â˜ƒ = this.instanceToChannel.entrySet().iterator();

      while(â˜ƒ.hasNext()) {
         Entry<SoundInstance, ChannelAccess.ChannelHandle> â˜ƒx = (Entry)â˜ƒ.next();
         ChannelAccess.ChannelHandle â˜ƒxx = (ChannelAccess.ChannelHandle)â˜ƒx.getValue();
         SoundInstance â˜ƒxxx = (SoundInstance)â˜ƒx.getKey();
         float â˜ƒxxxx = this.options.getSoundSourceVolume(â˜ƒxxx.getSource());
         if (â˜ƒxxxx <= 0.0F) {
            â˜ƒxx.execute(Channel::stop);
            â˜ƒ.remove();
         } else if (â˜ƒxx.isStopped()) {
            int â˜ƒx = this.soundDeleteTime.get(â˜ƒxxx);
            if (â˜ƒx <= this.tickCount) {
               if (shouldLoopManually(â˜ƒxxx)) {
                  this.queuedSounds.put(â˜ƒxxx, this.tickCount + â˜ƒxxx.getDelay());
               }

               â˜ƒ.remove();
               LOGGER.debug(MARKER, "Removed channel {} because it's not playing anymore", â˜ƒxx);
               this.soundDeleteTime.remove(â˜ƒxxx);

               try {
                  this.instanceBySource.remove(â˜ƒxxx.getSource(), â˜ƒxxx);
               } catch (RuntimeException var8) {
               }

               if (â˜ƒxxx instanceof TickableSoundInstance) {
                  this.tickingSounds.remove(â˜ƒxxx);
               }
            }
         }
      }

      Iterator<Entry<SoundInstance, Integer>> â˜ƒx = this.queuedSounds.entrySet().iterator();

      while(â˜ƒx.hasNext()) {
         Entry<SoundInstance, Integer> â˜ƒxx = (Entry)â˜ƒx.next();
         if (this.tickCount >= â˜ƒxx.getValue()) {
            SoundInstance â˜ƒxxx = (SoundInstance)â˜ƒxx.getKey();
            if (â˜ƒxxx instanceof TickableSoundInstance) {
               ((TickableSoundInstance)â˜ƒxxx).tick();
            }

            this.play(â˜ƒxxx);
            â˜ƒx.remove();
         }
      }
   }

   private static boolean requiresManualLooping(SoundInstance var0) {
      return â˜ƒ.getDelay() > 0;
   }

   private static boolean shouldLoopManually(SoundInstance var0) {
      return â˜ƒ.isLooping() && requiresManualLooping(â˜ƒ);
   }

   private static boolean shouldLoopAutomatically(SoundInstance var0) {
      return â˜ƒ.isLooping() && !requiresManualLooping(â˜ƒ);
   }

   public boolean isActive(SoundInstance var1) {
      if (!this.loaded) {
         return false;
      } else {
         return this.soundDeleteTime.containsKey(â˜ƒ) && this.soundDeleteTime.get(â˜ƒ) <= this.tickCount ? true : this.instanceToChannel.containsKey(â˜ƒ);
      }
   }

   public void play(SoundInstance var1) {
      if (this.loaded) {
         if (â˜ƒ.canPlaySound()) {
            WeighedSoundEvents â˜ƒ = â˜ƒ.resolve(this.soundManager);
            ResourceLocation â˜ƒx = â˜ƒ.getLocation();
            if (â˜ƒ == null) {
               if (ONLY_WARN_ONCE.add(â˜ƒx)) {
                  LOGGER.warn(MARKER, "Unable to play unknown soundEvent: {}", â˜ƒx);
               }
            } else {
               Sound â˜ƒ = â˜ƒ.getSound();
               if (â˜ƒ == SoundManager.EMPTY_SOUND) {
                  if (ONLY_WARN_ONCE.add(â˜ƒx)) {
                     LOGGER.warn(MARKER, "Unable to play empty soundEvent: {}", â˜ƒx);
                  }
               } else {
                  float â˜ƒ = â˜ƒ.getVolume();
                  float â˜ƒx = Math.max(â˜ƒ, 1.0F) * (float)â˜ƒ.getAttenuationDistance();
                  SoundSource â˜ƒxx = â˜ƒ.getSource();
                  float â˜ƒxxx = this.calculateVolume(â˜ƒ);
                  float â˜ƒxxxx = this.calculatePitch(â˜ƒ);
                  SoundInstance.Attenuation â˜ƒxxxxx = â˜ƒ.getAttenuation();
                  boolean â˜ƒxxxxxx = â˜ƒ.isRelative();
                  if (â˜ƒxxx == 0.0F && !â˜ƒ.canStartSilent()) {
                     LOGGER.debug(MARKER, "Skipped playing sound {}, volume was zero.", â˜ƒ.getLocation());
                  } else {
                     Vec3 â˜ƒ = new Vec3(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
                     if (!this.listeners.isEmpty()) {
                        boolean â˜ƒx = â˜ƒxxxxxx
                           || â˜ƒxxxxx == SoundInstance.Attenuation.NONE
                           || this.listener.getListenerPosition().distanceToSqr(â˜ƒ) < (double)(â˜ƒx * â˜ƒx);
                        if (â˜ƒx) {
                           for(SoundEventListener â˜ƒxx : this.listeners) {
                              â˜ƒxx.onPlaySound(â˜ƒ, â˜ƒ);
                           }
                        } else {
                           LOGGER.debug(MARKER, "Did not notify listeners of soundEvent: {}, it is too far away to hear", â˜ƒx);
                        }
                     }

                     if (this.listener.getGain() <= 0.0F) {
                        LOGGER.debug(MARKER, "Skipped playing soundEvent: {}, master volume was zero", â˜ƒx);
                     } else {
                        boolean â˜ƒ = shouldLoopAutomatically(â˜ƒ);
                        boolean â˜ƒx = â˜ƒ.shouldStream();
                        CompletableFuture<ChannelAccess.ChannelHandle> â˜ƒxx = this.channelAccess
                           .createHandle(â˜ƒ.shouldStream() ? Library.Pool.STREAMING : Library.Pool.STATIC);
                        ChannelAccess.ChannelHandle â˜ƒxxx = (ChannelAccess.ChannelHandle)â˜ƒxx.join();
                        if (â˜ƒxxx == null) {
                           LOGGER.warn("Failed to create new sound handle");
                        } else {
                           LOGGER.debug(MARKER, "Playing sound {} for event {}", â˜ƒ.getLocation(), â˜ƒx);
                           this.soundDeleteTime.put(â˜ƒ, this.tickCount + 20);
                           this.instanceToChannel.put(â˜ƒ, â˜ƒxxx);
                           this.instanceBySource.put(â˜ƒxx, â˜ƒ);
                           â˜ƒxxx.execute(var8x -> {
                              var8x.setPitch(â˜ƒ);
                              var8x.setVolume(â˜ƒ);
                              if (â˜ƒ == SoundInstance.Attenuation.LINEAR) {
                                 var8x.linearAttenuation(â˜ƒ);
                              } else {
                                 var8x.disableAttenuation();
                              }

                              var8x.setLooping(â˜ƒ && !â˜ƒ);
                              var8x.setSelfPosition(â˜ƒ);
                              var8x.setRelative(â˜ƒ);
                           });
                           if (!â˜ƒx) {
                              this.soundBuffers.getCompleteBuffer(â˜ƒ.getPath()).thenAccept(var1x -> â˜ƒ.execute(var1xx -> {
                                    var1xx.attachStaticBuffer(var1x);
                                    var1xx.play();
                                 }));
                           } else {
                              this.soundBuffers.getStream(â˜ƒ.getPath(), â˜ƒ).thenAccept(var1x -> â˜ƒ.execute(var1xx -> {
                                    var1xx.attachBufferStream(var1x);
                                    var1xx.play();
                                 }));
                           }

                           if (â˜ƒ instanceof TickableSoundInstance) {
                              this.tickingSounds.add((TickableSoundInstance)â˜ƒ);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void queueTickingSound(TickableSoundInstance var1) {
      this.queuedTickableSounds.add(â˜ƒ);
   }

   public void requestPreload(Sound var1) {
      this.preloadQueue.add(â˜ƒ);
   }

   private float calculatePitch(SoundInstance var1) {
      return Mth.clamp(â˜ƒ.getPitch(), 0.5F, 2.0F);
   }

   private float calculateVolume(SoundInstance var1) {
      return Mth.clamp(â˜ƒ.getVolume() * this.getVolume(â˜ƒ.getSource()), 0.0F, 1.0F);
   }

   public void pause() {
      if (this.loaded) {
         this.channelAccess.executeOnChannels(var0 -> var0.forEach(Channel::pause));
      }
   }

   public void resume() {
      if (this.loaded) {
         this.channelAccess.executeOnChannels(var0 -> var0.forEach(Channel::unpause));
      }
   }

   public void playDelayed(SoundInstance var1, int var2) {
      this.queuedSounds.put(â˜ƒ, this.tickCount + â˜ƒ);
   }

   public void updateSource(Camera var1) {
      if (this.loaded && â˜ƒ.isInitialized()) {
         Vec3 â˜ƒ = â˜ƒ.getPosition();
         Vector3f â˜ƒx = â˜ƒ.getLookVector();
         Vector3f â˜ƒxx = â˜ƒ.getUpVector();
         this.executor.execute(() -> {
            this.listener.setListenerPosition(â˜ƒ);
            this.listener.setListenerOrientation(â˜ƒ, â˜ƒ);
         });
      }
   }

   public void stop(@Nullable ResourceLocation var1, @Nullable SoundSource var2) {
      if (â˜ƒ != null) {
         for(SoundInstance â˜ƒ : this.instanceBySource.get(â˜ƒ)) {
            if (â˜ƒ == null || â˜ƒ.getLocation().equals(â˜ƒ)) {
               this.stop(â˜ƒ);
            }
         }
      } else if (â˜ƒ == null) {
         this.stopAll();
      } else {
         for(SoundInstance â˜ƒ : this.instanceToChannel.keySet()) {
            if (â˜ƒ.getLocation().equals(â˜ƒ)) {
               this.stop(â˜ƒ);
            }
         }
      }
   }

   public String getDebugString() {
      return this.library.getDebugString();
   }
}
