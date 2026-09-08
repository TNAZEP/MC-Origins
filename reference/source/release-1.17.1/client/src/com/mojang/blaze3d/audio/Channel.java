package com.mojang.blaze3d.audio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sounds.AudioStream;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;

public class Channel {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int QUEUED_BUFFER_COUNT = 4;
   public static final int BUFFER_DURATION_SECONDS = 1;
   private final int source;
   private final AtomicBoolean initialized = new AtomicBoolean(true);
   private int streamingBufferSize = 16384;
   @Nullable
   private AudioStream stream;

   @Nullable
   static Channel create() {
      int[] â˜ƒ = new int[1];
      AL10.alGenSources(â˜ƒ);
      return OpenAlUtil.checkALError("Allocate new source") ? null : new Channel(â˜ƒ[0]);
   }

   private Channel(int var1) {
      this.source = â˜ƒ;
   }

   public void destroy() {
      if (this.initialized.compareAndSet(true, false)) {
         AL10.alSourceStop(this.source);
         OpenAlUtil.checkALError("Stop");
         if (this.stream != null) {
            try {
               this.stream.close();
            } catch (IOException var2) {
               LOGGER.error("Failed to close audio stream", var2);
            }

            this.removeProcessedBuffers();
            this.stream = null;
         }

         AL10.alDeleteSources(new int[]{this.source});
         OpenAlUtil.checkALError("Cleanup");
      }
   }

   public void play() {
      AL10.alSourcePlay(this.source);
   }

   private int getState() {
      return !this.initialized.get() ? 4116 : AL10.alGetSourcei(this.source, 4112);
   }

   public void pause() {
      if (this.getState() == 4114) {
         AL10.alSourcePause(this.source);
      }
   }

   public void unpause() {
      if (this.getState() == 4115) {
         AL10.alSourcePlay(this.source);
      }
   }

   public void stop() {
      if (this.initialized.get()) {
         AL10.alSourceStop(this.source);
         OpenAlUtil.checkALError("Stop");
      }
   }

   public boolean playing() {
      return this.getState() == 4114;
   }

   public boolean stopped() {
      return this.getState() == 4116;
   }

   public void setSelfPosition(Vec3 var1) {
      AL10.alSourcefv(this.source, 4100, new float[]{(float)â˜ƒ.x, (float)â˜ƒ.y, (float)â˜ƒ.z});
   }

   public void setPitch(float var1) {
      AL10.alSourcef(this.source, 4099, â˜ƒ);
   }

   public void setLooping(boolean var1) {
      AL10.alSourcei(this.source, 4103, â˜ƒ ? 1 : 0);
   }

   public void setVolume(float var1) {
      AL10.alSourcef(this.source, 4106, â˜ƒ);
   }

   public void disableAttenuation() {
      AL10.alSourcei(this.source, 53248, 0);
   }

   public void linearAttenuation(float var1) {
      AL10.alSourcei(this.source, 53248, 53251);
      AL10.alSourcef(this.source, 4131, â˜ƒ);
      AL10.alSourcef(this.source, 4129, 1.0F);
      AL10.alSourcef(this.source, 4128, 0.0F);
   }

   public void setRelative(boolean var1) {
      AL10.alSourcei(this.source, 514, â˜ƒ ? 1 : 0);
   }

   public void attachStaticBuffer(SoundBuffer var1) {
      â˜ƒ.getAlBuffer().ifPresent(var1x -> AL10.alSourcei(this.source, 4105, var1x));
   }

   public void attachBufferStream(AudioStream var1) {
      this.stream = â˜ƒ;
      AudioFormat â˜ƒ = â˜ƒ.getFormat();
      this.streamingBufferSize = calculateBufferSize(â˜ƒ, 1);
      this.pumpBuffers(4);
   }

   private static int calculateBufferSize(AudioFormat var0, int var1) {
      return (int)((float)(â˜ƒ * â˜ƒ.getSampleSizeInBits()) / 8.0F * (float)â˜ƒ.getChannels() * â˜ƒ.getSampleRate());
   }

   private void pumpBuffers(int var1) {
      if (this.stream != null) {
         try {
            for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
               ByteBuffer â˜ƒx = this.stream.read(this.streamingBufferSize);
               if (â˜ƒx != null) {
                  new SoundBuffer(â˜ƒx, this.stream.getFormat()).releaseAlBuffer().ifPresent(var1x -> AL10.alSourceQueueBuffers(this.source, new int[]{var1x}));
               }
            }
         } catch (IOException var4) {
            LOGGER.error("Failed to read from audio stream", var4);
         }
      }
   }

   public void updateStream() {
      if (this.stream != null) {
         int â˜ƒ = this.removeProcessedBuffers();
         this.pumpBuffers(â˜ƒ);
      }
   }

   private int removeProcessedBuffers() {
      int â˜ƒ = AL10.alGetSourcei(this.source, 4118);
      if (â˜ƒ > 0) {
         int[] â˜ƒx = new int[â˜ƒ];
         AL10.alSourceUnqueueBuffers(this.source, â˜ƒx);
         OpenAlUtil.checkALError("Unqueue buffers");
         AL10.alDeleteBuffers(â˜ƒx);
         OpenAlUtil.checkALError("Remove processed buffers");
      }

      return â˜ƒ;
   }
}
