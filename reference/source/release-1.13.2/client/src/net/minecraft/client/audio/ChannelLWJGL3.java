package net.minecraft.client.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import javax.sound.sampled.AudioFormat;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import paulscode.sound.Channel;

public class ChannelLWJGL3 extends Channel {
   public IntBuffer field_195851_a;
   public int field_195852_b;
   public int field_195853_c;
   public float field_195854_d;

   public ChannelLWJGL3(int var1, IntBuffer var2) {
      super(☃);
      this.libraryType = LibraryLWJGL3.class;
      this.field_195851_a = ☃;
   }

   @Override
   public void cleanup() {
      if (this.field_195851_a != null) {
         try {
            AL10.alSourceStop(this.field_195851_a.get(0));
            AL10.alGetError();
         } catch (Exception var3) {
         }

         try {
            AL10.alDeleteSources(this.field_195851_a);
            AL10.alGetError();
         } catch (Exception var2) {
         }

         this.field_195851_a.clear();
      }

      this.field_195851_a = null;
      super.cleanup();
   }

   public boolean func_195847_a(IntBuffer var1) {
      if (this.errorCheck(this.channelType != 0, "Sound buffers may only be attached to normal sources.")) {
         return false;
      } else {
         AL10.alSourcei(this.field_195851_a.get(0), 4105, ☃.get(0));
         if (this.attachedSource != null && this.attachedSource.soundBuffer != null && this.attachedSource.soundBuffer.audioFormat != null) {
            this.setAudioFormat(this.attachedSource.soundBuffer.audioFormat);
         }

         return this.func_195849_a();
      }
   }

   @Override
   public void setAudioFormat(AudioFormat var1) {
      int ☃;
      if (☃.getChannels() == 1) {
         if (☃.getSampleSizeInBits() == 8) {
            ☃ = 4352;
         } else {
            if (☃.getSampleSizeInBits() != 16) {
               this.errorMessage("Illegal sample size in method 'setAudioFormat'");
               return;
            }

            ☃ = 4353;
         }
      } else {
         if (☃.getChannels() != 2) {
            this.errorMessage("Audio data neither mono nor stereo in method 'setAudioFormat'");
            return;
         }

         if (☃.getSampleSizeInBits() == 8) {
            ☃ = 4354;
         } else {
            if (☃.getSampleSizeInBits() != 16) {
               this.errorMessage("Illegal sample size in method 'setAudioFormat'");
               return;
            }

            ☃ = 4355;
         }
      }

      this.field_195852_b = ☃;
      this.field_195853_c = (int)☃.getSampleRate();
   }

   public void func_195848_a(int var1, int var2) {
      this.field_195852_b = ☃;
      this.field_195853_c = ☃;
   }

   @Override
   public boolean preLoadBuffers(LinkedList<byte[]> var1) {
      if (this.errorCheck(this.channelType != 1, "Buffers may only be queued for streaming sources.")) {
         return false;
      } else if (this.errorCheck(☃ == null, "Buffer List null in method 'preLoadBuffers'")) {
         return false;
      } else {
         boolean ☃ = this.playing();
         if (☃) {
            AL10.alSourceStop(this.field_195851_a.get(0));
            this.func_195849_a();
         }

         int ☃ = AL10.alGetSourcei(this.field_195851_a.get(0), 4118);
         if (☃ > 0) {
            IntBuffer ☃x = BufferUtils.createIntBuffer(☃);
            AL10.alGenBuffers(☃x);
            if (this.errorCheck(this.func_195849_a(), "Error clearing stream buffers in method 'preLoadBuffers'")) {
               return false;
            }

            AL10.alSourceUnqueueBuffers(this.field_195851_a.get(0), ☃x);
            if (this.errorCheck(this.func_195849_a(), "Error unqueuing stream buffers in method 'preLoadBuffers'")) {
               return false;
            }
         }

         if (☃) {
            AL10.alSourcePlay(this.field_195851_a.get(0));
            this.func_195849_a();
         }

         IntBuffer ☃ = BufferUtils.createIntBuffer(☃.size());
         AL10.alGenBuffers(☃);
         if (this.errorCheck(this.func_195849_a(), "Error generating stream buffers in method 'preLoadBuffers'")) {
            return false;
         } else {
            for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
               ByteBuffer ☃x = (ByteBuffer)BufferUtils.createByteBuffer(((byte[])☃.get(☃)).length).put((byte[])☃.get(☃)).flip();

               try {
                  AL10.alBufferData(☃.get(☃), this.field_195852_b, ☃x, this.field_195853_c);
               } catch (Exception var9) {
                  this.errorMessage("Error creating buffers in method 'preLoadBuffers'");
                  this.printStackTrace(var9);
                  return false;
               }

               if (this.errorCheck(this.func_195849_a(), "Error creating buffers in method 'preLoadBuffers'")) {
                  return false;
               }
            }

            try {
               AL10.alSourceQueueBuffers(this.field_195851_a.get(0), ☃);
            } catch (Exception var8) {
               this.errorMessage("Error queuing buffers in method 'preLoadBuffers'");
               this.printStackTrace(var8);
               return false;
            }

            if (this.errorCheck(this.func_195849_a(), "Error queuing buffers in method 'preLoadBuffers'")) {
               return false;
            } else {
               AL10.alSourcePlay(this.field_195851_a.get(0));
               return !this.errorCheck(this.func_195849_a(), "Error playing source in method 'preLoadBuffers'");
            }
         }
      }
   }

   @Override
   public boolean queueBuffer(byte[] var1) {
      if (this.errorCheck(this.channelType != 1, "Buffers may only be queued for streaming sources.")) {
         return false;
      } else {
         ByteBuffer ☃ = (ByteBuffer)BufferUtils.createByteBuffer(☃.length).put(☃).flip();
         IntBuffer ☃x = BufferUtils.createIntBuffer(1);
         AL10.alSourceUnqueueBuffers(this.field_195851_a.get(0), ☃x);
         if (this.func_195849_a()) {
            return false;
         } else {
            if (AL10.alIsBuffer(☃x.get(0))) {
               this.field_195854_d += this.func_195850_a(☃x.get(0));
            }

            this.func_195849_a();
            AL10.alBufferData(☃x.get(0), this.field_195852_b, ☃, this.field_195853_c);
            if (this.func_195849_a()) {
               return false;
            } else {
               AL10.alSourceQueueBuffers(this.field_195851_a.get(0), ☃x);
               return !this.func_195849_a();
            }
         }
      }
   }

   @Override
   public int feedRawAudioData(byte[] var1) {
      if (this.errorCheck(this.channelType != 1, "Raw audio data can only be fed to streaming sources.")) {
         return -1;
      } else {
         ByteBuffer ☃x = (ByteBuffer)BufferUtils.createByteBuffer(☃.length).put(☃).flip();
         int ☃xx = AL10.alGetSourcei(this.field_195851_a.get(0), 4118);
         IntBuffer ☃;
         if (☃xx > 0) {
            ☃ = BufferUtils.createIntBuffer(☃xx);
            AL10.alGenBuffers(☃);
            if (this.errorCheck(this.func_195849_a(), "Error clearing stream buffers in method 'feedRawAudioData'")) {
               return -1;
            }

            AL10.alSourceUnqueueBuffers(this.field_195851_a.get(0), ☃);
            if (this.errorCheck(this.func_195849_a(), "Error unqueuing stream buffers in method 'feedRawAudioData'")) {
               return -1;
            }

            if (AL10.alIsBuffer(☃.get(0))) {
               this.field_195854_d += this.func_195850_a(☃.get(0));
            }

            this.func_195849_a();
         } else {
            ☃ = BufferUtils.createIntBuffer(1);
            AL10.alGenBuffers(☃);
            if (this.errorCheck(this.func_195849_a(), "Error generating stream buffers in method 'preLoadBuffers'")) {
               return -1;
            }
         }

         AL10.alBufferData(☃.get(0), this.field_195852_b, ☃x, this.field_195853_c);
         if (this.func_195849_a()) {
            return -1;
         } else {
            AL10.alSourceQueueBuffers(this.field_195851_a.get(0), ☃);
            if (this.func_195849_a()) {
               return -1;
            } else {
               if (this.attachedSource != null && this.attachedSource.channel == this && this.attachedSource.active() && !this.playing()) {
                  AL10.alSourcePlay(this.field_195851_a.get(0));
                  this.func_195849_a();
               }

               return ☃xx;
            }
         }
      }
   }

   public float func_195850_a(int var1) {
      return (float)(1000 * AL10.alGetBufferi(☃, 8196) / AL10.alGetBufferi(☃, 8195)) / ((float)AL10.alGetBufferi(☃, 8194) / 8.0F) / (float)this.field_195853_c;
   }

   @Override
   public float millisecondsPlayed() {
      float ☃ = (float)AL10.alGetSourcei(this.field_195851_a.get(0), 4134);
      float ☃x = 1.0F;
      switch(this.field_195852_b) {
         case 4352:
            ☃x = 1.0F;
            break;
         case 4353:
            ☃x = 2.0F;
            break;
         case 4354:
            ☃x = 2.0F;
            break;
         case 4355:
            ☃x = 4.0F;
      }

      ☃ = ☃ / ☃x / (float)this.field_195853_c * 1000.0F;
      if (this.channelType == 1) {
         ☃ += this.field_195854_d;
      }

      return ☃;
   }

   @Override
   public int buffersProcessed() {
      if (this.channelType != 1) {
         return 0;
      } else {
         int ☃ = AL10.alGetSourcei(this.field_195851_a.get(0), 4118);
         return this.func_195849_a() ? 0 : ☃;
      }
   }

   @Override
   public void flush() {
      if (this.channelType == 1) {
         int ☃ = AL10.alGetSourcei(this.field_195851_a.get(0), 4117);
         if (!this.func_195849_a()) {
            for(IntBuffer ☃x = BufferUtils.createIntBuffer(1); ☃ > 0; --☃) {
               try {
                  AL10.alSourceUnqueueBuffers(this.field_195851_a.get(0), ☃x);
               } catch (Exception var4) {
                  return;
               }

               if (this.func_195849_a()) {
                  return;
               }
            }

            this.field_195854_d = 0.0F;
         }
      }
   }

   @Override
   public void close() {
      try {
         AL10.alSourceStop(this.field_195851_a.get(0));
         AL10.alGetError();
      } catch (Exception var2) {
      }

      if (this.channelType == 1) {
         this.flush();
      }
   }

   @Override
   public void play() {
      AL10.alSourcePlay(this.field_195851_a.get(0));
      this.func_195849_a();
   }

   @Override
   public void pause() {
      AL10.alSourcePause(this.field_195851_a.get(0));
      this.func_195849_a();
   }

   @Override
   public void stop() {
      AL10.alSourceStop(this.field_195851_a.get(0));
      if (!this.func_195849_a()) {
         this.field_195854_d = 0.0F;
      }
   }

   @Override
   public void rewind() {
      if (this.channelType != 1) {
         AL10.alSourceRewind(this.field_195851_a.get(0));
         if (!this.func_195849_a()) {
            this.field_195854_d = 0.0F;
         }
      }
   }

   @Override
   public boolean playing() {
      int ☃ = AL10.alGetSourcei(this.field_195851_a.get(0), 4112);
      if (this.func_195849_a()) {
         return false;
      } else {
         return ☃ == 4114;
      }
   }

   private boolean func_195849_a() {
      switch(AL10.alGetError()) {
         case 0:
            return false;
         case 40961:
            this.errorMessage("Invalid name parameter.");
            return true;
         case 40962:
            this.errorMessage("Invalid parameter.");
            return true;
         case 40963:
            this.errorMessage("Invalid enumerated parameter value.");
            return true;
         case 40964:
            this.errorMessage("Illegal call.");
            return true;
         case 40965:
            this.errorMessage("Unable to allocate memory.");
            return true;
         default:
            this.errorMessage("An unrecognized error occurred.");
            return true;
      }
   }
}
