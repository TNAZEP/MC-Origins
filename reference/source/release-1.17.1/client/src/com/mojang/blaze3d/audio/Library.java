package com.mojang.blaze3d.audio;

import com.google.common.collect.Sets;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryStack;

public class Library {
   private static final int NUM_OPEN_DEVICE_RETRIES = 3;
   static final Logger LOGGER = LogManager.getLogger();
   private static final int DEFAULT_CHANNEL_COUNT = 30;
   private long device;
   private long context;
   private static final Library.ChannelPool EMPTY = new Library.ChannelPool() {
      @Nullable
      @Override
      public Channel acquire() {
         return null;
      }

      @Override
      public boolean release(Channel var1) {
         return false;
      }

      @Override
      public void cleanup() {
      }

      @Override
      public int getMaxCount() {
         return 0;
      }

      @Override
      public int getUsedCount() {
         return 0;
      }
   };
   private Library.ChannelPool staticChannels = EMPTY;
   private Library.ChannelPool streamingChannels = EMPTY;
   private final Listener listener = new Listener();

   public void init() {
      this.device = tryOpenDevice();
      ALCCapabilities â˜ƒ = ALC.createCapabilities(this.device);
      if (OpenAlUtil.checkALCError(this.device, "Get capabilities")) {
         throw new IllegalStateException("Failed to get OpenAL capabilities");
      } else if (!â˜ƒ.OpenALC11) {
         throw new IllegalStateException("OpenAL 1.1 not supported");
      } else {
         this.context = ALC10.alcCreateContext(this.device, (IntBuffer)null);
         ALC10.alcMakeContextCurrent(this.context);
         int â˜ƒ = this.getChannelCount();
         int â˜ƒx = Mth.clamp((int)Mth.sqrt((float)â˜ƒ), 2, 8);
         int â˜ƒxx = Mth.clamp(â˜ƒ - â˜ƒx, 8, 255);
         this.staticChannels = new Library.CountingChannelPool(â˜ƒxx);
         this.streamingChannels = new Library.CountingChannelPool(â˜ƒx);
         ALCapabilities â˜ƒxxx = AL.createCapabilities(â˜ƒ);
         OpenAlUtil.checkALError("Initialization");
         if (!â˜ƒxxx.AL_EXT_source_distance_model) {
            throw new IllegalStateException("AL_EXT_source_distance_model is not supported");
         } else {
            AL10.alEnable(512);
            if (!â˜ƒxxx.AL_EXT_LINEAR_DISTANCE) {
               throw new IllegalStateException("AL_EXT_LINEAR_DISTANCE is not supported");
            } else {
               OpenAlUtil.checkALError("Enable per-source distance models");
               LOGGER.info("OpenAL initialized.");
            }
         }
      }
   }

   private int getChannelCount() {
      try (MemoryStack â˜ƒ = MemoryStack.stackPush()) {
         int â˜ƒx = ALC10.alcGetInteger(this.device, 4098);
         if (OpenAlUtil.checkALCError(this.device, "Get attributes size")) {
            throw new IllegalStateException("Failed to get OpenAL attributes");
         }

         IntBuffer â˜ƒx = â˜ƒ.mallocInt(â˜ƒx);
         ALC10.alcGetIntegerv(this.device, 4099, â˜ƒx);
         if (OpenAlUtil.checkALCError(this.device, "Get attributes")) {
            throw new IllegalStateException("Failed to get OpenAL attributes");
         }

         int â˜ƒx = 0;

         while(â˜ƒx < â˜ƒx) {
            int â˜ƒxx = â˜ƒx.get(â˜ƒx++);
            if (â˜ƒxx == 0) {
               break;
            }

            int â˜ƒxx = â˜ƒx.get(â˜ƒx++);
            if (â˜ƒxx == 4112) {
               return â˜ƒxx;
            }
         }
      }

      return 30;
   }

   private static long tryOpenDevice() {
      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         long â˜ƒx = ALC10.alcOpenDevice((ByteBuffer)null);
         if (â˜ƒx != 0L && !OpenAlUtil.checkALCError(â˜ƒx, "Open device")) {
            return â˜ƒx;
         }
      }

      throw new IllegalStateException("Failed to open OpenAL device");
   }

   public void cleanup() {
      this.staticChannels.cleanup();
      this.streamingChannels.cleanup();
      ALC10.alcDestroyContext(this.context);
      if (this.device != 0L) {
         ALC10.alcCloseDevice(this.device);
      }
   }

   public Listener getListener() {
      return this.listener;
   }

   @Nullable
   public Channel acquireChannel(Library.Pool var1) {
      return (â˜ƒ == Library.Pool.STREAMING ? this.streamingChannels : this.staticChannels).acquire();
   }

   public void releaseChannel(Channel var1) {
      if (!this.staticChannels.release(â˜ƒ) && !this.streamingChannels.release(â˜ƒ)) {
         throw new IllegalStateException("Tried to release unknown channel");
      }
   }

   public String getDebugString() {
      return String.format(
         "Sounds: %d/%d + %d/%d",
         this.staticChannels.getUsedCount(),
         this.staticChannels.getMaxCount(),
         this.streamingChannels.getUsedCount(),
         this.streamingChannels.getMaxCount()
      );
   }

   interface ChannelPool {
      @Nullable
      Channel acquire();

      boolean release(Channel var1);

      void cleanup();

      int getMaxCount();

      int getUsedCount();
   }

   static class CountingChannelPool implements Library.ChannelPool {
      private final int limit;
      private final Set<Channel> activeChannels = Sets.newIdentityHashSet();

      public CountingChannelPool(int var1) {
         this.limit = â˜ƒ;
      }

      @Nullable
      @Override
      public Channel acquire() {
         if (this.activeChannels.size() >= this.limit) {
            Library.LOGGER.warn("Maximum sound pool size {} reached", this.limit);
            return null;
         } else {
            Channel â˜ƒ = Channel.create();
            if (â˜ƒ != null) {
               this.activeChannels.add(â˜ƒ);
            }

            return â˜ƒ;
         }
      }

      @Override
      public boolean release(Channel var1) {
         if (!this.activeChannels.remove(â˜ƒ)) {
            return false;
         } else {
            â˜ƒ.destroy();
            return true;
         }
      }

      @Override
      public void cleanup() {
         this.activeChannels.forEach(Channel::destroy);
         this.activeChannels.clear();
      }

      @Override
      public int getMaxCount() {
         return this.limit;
      }

      @Override
      public int getUsedCount() {
         return this.activeChannels.size();
      }
   }

   public static enum Pool {
      STATIC,
      STREAMING;
   }
}
