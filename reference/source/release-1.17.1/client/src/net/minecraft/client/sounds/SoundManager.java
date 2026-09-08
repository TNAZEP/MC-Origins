package net.minecraft.client.sounds;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.Camera;
import net.minecraft.client.Options;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraft.client.resources.sounds.SoundEventRegistrationSerializer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundManager extends SimplePreparableReloadListener<SoundManager.Preparations> {
   public static final Sound EMPTY_SOUND = new Sound("meta:missing_sound", 1.0F, 1.0F, 1, Sound.Type.FILE, false, false, 16);
   static final Logger LOGGER = LogManager.getLogger();
   private static final String SOUNDS_PATH = "sounds.json";
   private static final Gson GSON = new GsonBuilder()
      .registerTypeHierarchyAdapter(Component.class, new Component.Serializer())
      .registerTypeAdapter(SoundEventRegistration.class, new SoundEventRegistrationSerializer())
      .create();
   private static final TypeToken<Map<String, SoundEventRegistration>> SOUND_EVENT_REGISTRATION_TYPE = new TypeToken<Map<String, SoundEventRegistration>>() {
   };
   private final Map<ResourceLocation, WeighedSoundEvents> registry = Maps.<ResourceLocation, WeighedSoundEvents>newHashMap();
   private final SoundEngine soundEngine;

   public SoundManager(ResourceManager var1, Options var2) {
      this.soundEngine = new SoundEngine(this, â˜ƒ, â˜ƒ);
   }

   protected SoundManager.Preparations prepare(ResourceManager var1, ProfilerFiller var2) {
      SoundManager.Preparations â˜ƒ = new SoundManager.Preparations();
      â˜ƒ.startTick();

      for(String â˜ƒx : â˜ƒ.getNamespaces()) {
         â˜ƒ.push(â˜ƒx);

         try {
            for(Resource â˜ƒxx : â˜ƒ.getResources(new ResourceLocation(â˜ƒx, "sounds.json"))) {
               â˜ƒ.push(â˜ƒxx.getSourceName());

               try {
                  InputStream â˜ƒxxx = â˜ƒxx.getInputStream();

                  try {
                     Reader â˜ƒxxxx = new InputStreamReader(â˜ƒxxx, StandardCharsets.UTF_8);

                     try {
                        â˜ƒ.push("parse");
                        Map<String, SoundEventRegistration> â˜ƒxxxxx = GsonHelper.fromJson(GSON, â˜ƒxxxx, SOUND_EVENT_REGISTRATION_TYPE);
                        â˜ƒ.popPush("register");

                        for(Entry<String, SoundEventRegistration> â˜ƒxxxxxx : â˜ƒxxxxx.entrySet()) {
                           â˜ƒ.handleRegistration(new ResourceLocation(â˜ƒx, (String)â˜ƒxxxxxx.getKey()), (SoundEventRegistration)â˜ƒxxxxxx.getValue(), â˜ƒ);
                        }

                        â˜ƒ.pop();
                     } catch (Throwable var16) {
                        try {
                           â˜ƒxxxx.close();
                        } catch (Throwable var15) {
                           var16.addSuppressed(var15);
                        }

                        throw var16;
                     }

                     â˜ƒxxxx.close();
                  } catch (Throwable var17) {
                     if (â˜ƒxxx != null) {
                        try {
                           â˜ƒxxx.close();
                        } catch (Throwable var14) {
                           var17.addSuppressed(var14);
                        }
                     }

                     throw var17;
                  }

                  if (â˜ƒxxx != null) {
                     â˜ƒxxx.close();
                  }
               } catch (RuntimeException var18) {
                  LOGGER.warn("Invalid {} in resourcepack: '{}'", "sounds.json", â˜ƒxx.getSourceName(), var18);
               }

               â˜ƒ.pop();
            }
         } catch (IOException var19) {
         }

         â˜ƒ.pop();
      }

      â˜ƒ.endTick();
      return â˜ƒ;
   }

   protected void apply(SoundManager.Preparations var1, ResourceManager var2, ProfilerFiller var3) {
      â˜ƒ.apply(this.registry, this.soundEngine);
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         for(ResourceLocation â˜ƒ : this.registry.keySet()) {
            WeighedSoundEvents â˜ƒx = (WeighedSoundEvents)this.registry.get(â˜ƒ);
            if (â˜ƒx.getSubtitle() instanceof TranslatableComponent) {
               String â˜ƒxx = ((TranslatableComponent)â˜ƒx.getSubtitle()).getKey();
               if (!I18n.exists(â˜ƒxx) && Registry.SOUND_EVENT.containsKey(â˜ƒ)) {
                  LOGGER.error("Missing subtitle {} for sound event: {}", â˜ƒxx, â˜ƒ);
               }
            }
         }
      }

      if (LOGGER.isDebugEnabled()) {
         for(ResourceLocation â˜ƒ : this.registry.keySet()) {
            if (!Registry.SOUND_EVENT.containsKey(â˜ƒ)) {
               LOGGER.debug("Not having sound event for: {}", â˜ƒ);
            }
         }
      }

      this.soundEngine.reload();
   }

   static boolean validateSoundResource(Sound var0, ResourceLocation var1, ResourceManager var2) {
      ResourceLocation â˜ƒ = â˜ƒ.getPath();
      if (!â˜ƒ.hasResource(â˜ƒ)) {
         LOGGER.warn("File {} does not exist, cannot add it to event {}", â˜ƒ, â˜ƒ);
         return false;
      } else {
         return true;
      }
   }

   @Nullable
   public WeighedSoundEvents getSoundEvent(ResourceLocation var1) {
      return (WeighedSoundEvents)this.registry.get(â˜ƒ);
   }

   public Collection<ResourceLocation> getAvailableSounds() {
      return this.registry.keySet();
   }

   public void queueTickingSound(TickableSoundInstance var1) {
      this.soundEngine.queueTickingSound(â˜ƒ);
   }

   public void play(SoundInstance var1) {
      this.soundEngine.play(â˜ƒ);
   }

   public void playDelayed(SoundInstance var1, int var2) {
      this.soundEngine.playDelayed(â˜ƒ, â˜ƒ);
   }

   public void updateSource(Camera var1) {
      this.soundEngine.updateSource(â˜ƒ);
   }

   public void pause() {
      this.soundEngine.pause();
   }

   public void stop() {
      this.soundEngine.stopAll();
   }

   public void destroy() {
      this.soundEngine.destroy();
   }

   public void tick(boolean var1) {
      this.soundEngine.tick(â˜ƒ);
   }

   public void resume() {
      this.soundEngine.resume();
   }

   public void updateSourceVolume(SoundSource var1, float var2) {
      if (â˜ƒ == SoundSource.MASTER && â˜ƒ <= 0.0F) {
         this.stop();
      }

      this.soundEngine.updateCategoryVolume(â˜ƒ, â˜ƒ);
   }

   public void stop(SoundInstance var1) {
      this.soundEngine.stop(â˜ƒ);
   }

   public boolean isActive(SoundInstance var1) {
      return this.soundEngine.isActive(â˜ƒ);
   }

   public void addListener(SoundEventListener var1) {
      this.soundEngine.addEventListener(â˜ƒ);
   }

   public void removeListener(SoundEventListener var1) {
      this.soundEngine.removeEventListener(â˜ƒ);
   }

   public void stop(@Nullable ResourceLocation var1, @Nullable SoundSource var2) {
      this.soundEngine.stop(â˜ƒ, â˜ƒ);
   }

   public String getDebugString() {
      return this.soundEngine.getDebugString();
   }

   protected static class Preparations {
      final Map<ResourceLocation, WeighedSoundEvents> registry = Maps.<ResourceLocation, WeighedSoundEvents>newHashMap();

      void handleRegistration(ResourceLocation var1, SoundEventRegistration var2, ResourceManager var3) {
         WeighedSoundEvents â˜ƒ = (WeighedSoundEvents)this.registry.get(â˜ƒ);
         boolean â˜ƒx = â˜ƒ == null;
         if (â˜ƒx || â˜ƒ.isReplace()) {
            if (!â˜ƒx) {
               SoundManager.LOGGER.debug("Replaced sound event location {}", â˜ƒ);
            }

            â˜ƒ = new WeighedSoundEvents(â˜ƒ, â˜ƒ.getSubtitle());
            this.registry.put(â˜ƒ, â˜ƒ);
         }

         for(final Sound â˜ƒ : â˜ƒ.getSounds()) {
            final ResourceLocation â˜ƒxx = â˜ƒ.getLocation();
            Weighted<Sound> â˜ƒx;
            switch(â˜ƒ.getType()) {
               case FILE:
                  if (!SoundManager.validateSoundResource(â˜ƒ, â˜ƒ, â˜ƒ)) {
                     continue;
                  }

                  â˜ƒx = â˜ƒ;
                  break;
               case SOUND_EVENT:
                  â˜ƒx = new Weighted<Sound>() {
                     @Override
                     public int getWeight() {
                        WeighedSoundEvents â˜ƒ = (WeighedSoundEvents)Preparations.this.registry.get(â˜ƒ);
                        return â˜ƒ == null ? 0 : â˜ƒ.getWeight();
                     }

                     public Sound getSound() {
                        WeighedSoundEvents â˜ƒ = (WeighedSoundEvents)Preparations.this.registry.get(â˜ƒ);
                        if (â˜ƒ == null) {
                           return SoundManager.EMPTY_SOUND;
                        } else {
                           Sound â˜ƒ = â˜ƒ.getSound();
                           return new Sound(
                              â˜ƒ.getLocation().toString(),
                              â˜ƒ.getVolume() * â˜ƒ.getVolume(),
                              â˜ƒ.getPitch() * â˜ƒ.getPitch(),
                              â˜ƒ.getWeight(),
                              Sound.Type.FILE,
                              â˜ƒ.shouldStream() || â˜ƒ.shouldStream(),
                              â˜ƒ.shouldPreload(),
                              â˜ƒ.getAttenuationDistance()
                           );
                        }
                     }

                     @Override
                     public void preloadIfRequired(SoundEngine var1) {
                        WeighedSoundEvents â˜ƒ = (WeighedSoundEvents)Preparations.this.registry.get(â˜ƒ);
                        if (â˜ƒ != null) {
                           â˜ƒ.preloadIfRequired(â˜ƒ);
                        }
                     }
                  };
                  break;
               default:
                  throw new IllegalStateException("Unknown SoundEventRegistration type: " + â˜ƒ.getType());
            }

            â˜ƒ.addSound(â˜ƒx);
         }
      }

      public void apply(Map<ResourceLocation, WeighedSoundEvents> var1, SoundEngine var2) {
         â˜ƒ.clear();

         for(Entry<ResourceLocation, WeighedSoundEvents> â˜ƒ : this.registry.entrySet()) {
            â˜ƒ.put((ResourceLocation)â˜ƒ.getKey(), (WeighedSoundEvents)â˜ƒ.getValue());
            ((WeighedSoundEvents)â˜ƒ.getValue()).preloadIfRequired(â˜ƒ);
         }
      }
   }
}
