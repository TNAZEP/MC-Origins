package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import javax.sound.sampled.AudioFormat;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import paulscode.sound.Channel;
import paulscode.sound.FilenameURL;
import paulscode.sound.SoundBuffer;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.Source;

public class SourceLWJGL3 extends Source {
   private ChannelLWJGL3 field_195861_a = (ChannelLWJGL3)this.channel;
   private IntBuffer field_195862_b;
   private FloatBuffer field_195863_c;
   private FloatBuffer field_195864_d;
   private FloatBuffer field_195865_e;

   public SourceLWJGL3(
      FloatBuffer var1,
      IntBuffer var2,
      boolean var3,
      boolean var4,
      boolean var5,
      String var6,
      FilenameURL var7,
      SoundBuffer var8,
      float var9,
      float var10,
      float var11,
      int var12,
      float var13,
      boolean var14
   ) {
      super(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      if (this.codec != null) {
         this.codec.reverseByteOrder(true);
      }

      this.field_195863_c = ☃;
      this.field_195862_b = ☃;
      this.libraryType = LibraryLWJGL3.class;
      this.pitch = 1.0F;
      this.func_195858_b();
   }

   public SourceLWJGL3(FloatBuffer var1, IntBuffer var2, Source var3, SoundBuffer var4) {
      super(☃, ☃);
      if (this.codec != null) {
         this.codec.reverseByteOrder(true);
      }

      this.field_195863_c = ☃;
      this.field_195862_b = ☃;
      this.libraryType = LibraryLWJGL3.class;
      this.pitch = 1.0F;
      this.func_195858_b();
   }

   public SourceLWJGL3(FloatBuffer var1, AudioFormat var2, boolean var3, String var4, float var5, float var6, float var7, int var8, float var9) {
      super(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_195863_c = ☃;
      this.libraryType = LibraryLWJGL3.class;
      this.pitch = 1.0F;
      this.func_195858_b();
   }

   @Override
   public boolean incrementSoundSequence() {
      if (!this.toStream) {
         this.errorMessage("Method 'incrementSoundSequence' may only be used for streaming sources.");
         return false;
      } else {
         synchronized(this.soundSequenceLock) {
            if (this.soundSequenceQueue != null && !this.soundSequenceQueue.isEmpty()) {
               this.filenameURL = (FilenameURL)this.soundSequenceQueue.remove(0);
               if (this.codec != null) {
                  this.codec.cleanup();
               }

               this.codec = SoundSystemConfig.getCodec(this.filenameURL.getFilename());
               if (this.codec == null) {
                  return true;
               } else {
                  this.codec.reverseByteOrder(true);
                  if (this.codec.getAudioFormat() == null) {
                     this.codec.initialize(this.filenameURL.getURL());
                  }

                  AudioFormat ☃ = this.codec.getAudioFormat();
                  if (☃ == null) {
                     this.errorMessage("Audio Format null in method 'incrementSoundSequence'");
                     return false;
                  } else {
                     int ☃;
                     if (☃.getChannels() == 1) {
                        if (☃.getSampleSizeInBits() == 8) {
                           ☃ = 4352;
                        } else {
                           if (☃.getSampleSizeInBits() != 16) {
                              this.errorMessage("Illegal sample size in method 'incrementSoundSequence'");
                              return false;
                           }

                           ☃ = 4353;
                        }
                     } else {
                        if (☃.getChannels() != 2) {
                           this.errorMessage("Audio data neither mono nor stereo in method 'incrementSoundSequence'");
                           return false;
                        }

                        if (☃.getSampleSizeInBits() == 8) {
                           ☃ = 4354;
                        } else {
                           if (☃.getSampleSizeInBits() != 16) {
                              this.errorMessage("Illegal sample size in method 'incrementSoundSequence'");
                              return false;
                           }

                           ☃ = 4355;
                        }
                     }

                     this.field_195861_a.func_195848_a(☃, (int)☃.getSampleRate());
                     this.preLoad = true;
                     return true;
                  }
               }
            } else {
               return false;
            }
         }
      }
   }

   @Override
   public void listenerMoved() {
      this.positionChanged();
   }

   @Override
   public void setPosition(float var1, float var2, float var3) {
      super.setPosition(☃, ☃, ☃);
      if (this.field_195864_d == null) {
         this.func_195858_b();
      } else {
         this.positionChanged();
      }

      this.field_195864_d.put(0, ☃);
      this.field_195864_d.put(1, ☃);
      this.field_195864_d.put(2, ☃);
      if (this.channel != null && this.channel.attachedSource == this && this.field_195861_a != null && this.field_195861_a.field_195851_a != null) {
         AL10.alSourcefv(this.field_195861_a.field_195851_a.get(0), 4100, this.field_195864_d);
         this.func_195857_e();
      }
   }

   @Override
   public void positionChanged() {
      this.func_195859_c();
      this.func_195860_d();
      if (this.channel != null && this.channel.attachedSource == this && this.field_195861_a != null && this.field_195861_a.field_195851_a != null) {
         AL10.alSourcef(this.field_195861_a.field_195851_a.get(0), 4106, this.gain * this.sourceVolume * Math.abs(this.fadeOutGain) * this.fadeInGain);
         this.func_195857_e();
      }

      this.func_195856_a();
   }

   private void func_195856_a() {
      if (this.channel != null
         && this.channel.attachedSource == this
         && LibraryLWJGL3.func_195836_a()
         && this.field_195861_a != null
         && this.field_195861_a.field_195851_a != null) {
         AL10.alSourcef(this.field_195861_a.field_195851_a.get(0), 4099, this.pitch);
         this.func_195857_e();
      }
   }

   @Override
   public void setLooping(boolean var1) {
      super.setLooping(☃);
      if (this.channel != null && this.channel.attachedSource == this && this.field_195861_a != null && this.field_195861_a.field_195851_a != null) {
         AL10.alSourcei(this.field_195861_a.field_195851_a.get(0), 4103, ☃ ? 1 : 0);
         this.func_195857_e();
      }
   }

   @Override
   public void setAttenuation(int var1) {
      super.setAttenuation(☃);
      if (this.channel != null && this.channel.attachedSource == this && this.field_195861_a != null && this.field_195861_a.field_195851_a != null) {
         if (☃ == 1) {
            AL10.alSourcef(this.field_195861_a.field_195851_a.get(0), 4129, this.distOrRoll);
         } else {
            AL10.alSourcef(this.field_195861_a.field_195851_a.get(0), 4129, 0.0F);
         }

         this.func_195857_e();
      }
   }

   @Override
   public void setDistOrRoll(float var1) {
      super.setDistOrRoll(☃);
      if (this.channel != null && this.channel.attachedSource == this && this.field_195861_a != null && this.field_195861_a.field_195851_a != null) {
         if (this.attModel == 1) {
            AL10.alSourcef(this.field_195861_a.field_195851_a.get(0), 4129, ☃);
         } else {
            AL10.alSourcef(this.field_195861_a.field_195851_a.get(0), 4129, 0.0F);
         }

         this.func_195857_e();
      }
   }

   @Override
   public void setVelocity(float var1, float var2, float var3) {
      super.setVelocity(☃, ☃, ☃);
      this.field_195865_e = BufferUtils.createFloatBuffer(3).put(new float[]{☃, ☃, ☃});
      this.field_195865_e.flip();
      if (this.channel != null && this.channel.attachedSource == this && this.field_195861_a != null && this.field_195861_a.field_195851_a != null) {
         AL10.alSourcefv(this.field_195861_a.field_195851_a.get(0), 4102, this.field_195865_e);
         this.func_195857_e();
      }
   }

   @Override
   public void setPitch(float var1) {
      super.setPitch(☃);
      this.func_195856_a();
   }

   @Override
   public void play(Channel var1) {
      if (!this.active()) {
         if (this.toLoop) {
            this.toPlay = true;
         }
      } else if (☃ == null) {
         this.errorMessage("Unable to play source, because channel was null");
      } else {
         boolean ☃ = this.channel != ☃;
         if (this.channel != null && this.channel.attachedSource != this) {
            ☃ = true;
         }

         boolean ☃ = this.paused();
         super.play(☃);
         this.field_195861_a = (ChannelLWJGL3)this.channel;
         if (☃) {
            this.setPosition(this.position.x, this.position.y, this.position.z);
            this.func_195856_a();
            if (this.field_195861_a != null && this.field_195861_a.field_195851_a != null) {
               if (LibraryLWJGL3.func_195836_a()) {
                  AL10.alSourcef(this.field_195861_a.field_195851_a.get(0), 4099, this.pitch);
                  this.func_195857_e();
               }

               AL10.alSourcefv(this.field_195861_a.field_195851_a.get(0), 4100, this.field_195864_d);
               this.func_195857_e();
               AL10.alSourcefv(this.field_195861_a.field_195851_a.get(0), 4102, this.field_195865_e);
               this.func_195857_e();
               if (this.attModel == 1) {
                  AL10.alSourcef(this.field_195861_a.field_195851_a.get(0), 4129, this.distOrRoll);
               } else {
                  AL10.alSourcef(this.field_195861_a.field_195851_a.get(0), 4129, 0.0F);
               }

               this.func_195857_e();
               if (this.toLoop && !this.toStream) {
                  AL10.alSourcei(this.field_195861_a.field_195851_a.get(0), 4103, 1);
               } else {
                  AL10.alSourcei(this.field_195861_a.field_195851_a.get(0), 4103, 0);
               }

               this.func_195857_e();
            }

            if (!this.toStream) {
               if (this.field_195862_b == null) {
                  this.errorMessage("No sound buffer to play");
                  return;
               }

               this.field_195861_a.func_195847_a(this.field_195862_b);
            }
         }

         if (!this.playing()) {
            if (this.toStream && !☃) {
               if (this.codec == null) {
                  this.errorMessage("Decoder null in method 'play'");
                  return;
               }

               if (this.codec.getAudioFormat() == null) {
                  this.codec.initialize(this.filenameURL.getURL());
               }

               AudioFormat ☃ = this.codec.getAudioFormat();
               if (☃ == null) {
                  this.errorMessage("Audio Format null in method 'play'");
                  return;
               }

               int ☃;
               if (☃.getChannels() == 1) {
                  if (☃.getSampleSizeInBits() == 8) {
                     ☃ = 4352;
                  } else {
                     if (☃.getSampleSizeInBits() != 16) {
                        this.errorMessage("Illegal sample size in method 'play'");
                        return;
                     }

                     ☃ = 4353;
                  }
               } else {
                  if (☃.getChannels() != 2) {
                     this.errorMessage("Audio data neither mono nor stereo in method 'play'");
                     return;
                  }

                  if (☃.getSampleSizeInBits() == 8) {
                     ☃ = 4354;
                  } else {
                     if (☃.getSampleSizeInBits() != 16) {
                        this.errorMessage("Illegal sample size in method 'play'");
                        return;
                     }

                     ☃ = 4355;
                  }
               }

               this.field_195861_a.func_195848_a(☃, (int)☃.getSampleRate());
               this.preLoad = true;
            }

            this.channel.play();
            if (this.pitch != 1.0F) {
               this.func_195856_a();
            }
         }
      }
   }

   @Override
   public boolean preLoad() {
      if (this.codec == null) {
         return false;
      } else {
         this.codec.initialize(this.filenameURL.getURL());
         LinkedList<byte[]> ☃ = Lists.newLinkedList();

         for(int ☃x = 0; ☃x < SoundSystemConfig.getNumberStreamingBuffers(); ++☃x) {
            this.soundBuffer = this.codec.read();
            if (this.soundBuffer == null || this.soundBuffer.audioData == null) {
               break;
            }

            ☃.add(this.soundBuffer.audioData);
         }

         this.positionChanged();
         this.channel.preLoadBuffers(☃);
         this.preLoad = false;
         return true;
      }
   }

   private void func_195858_b() {
      this.field_195864_d = BufferUtils.createFloatBuffer(3).put(new float[]{this.position.x, this.position.y, this.position.z});
      this.field_195865_e = BufferUtils.createFloatBuffer(3).put(new float[]{this.velocity.x, this.velocity.y, this.velocity.z});
      this.field_195864_d.flip();
      this.field_195865_e.flip();
      this.positionChanged();
   }

   private void func_195859_c() {
      if (this.field_195863_c != null) {
         double ☃ = (double)(this.position.x - this.field_195863_c.get(0));
         double ☃x = (double)(this.position.y - this.field_195863_c.get(1));
         double ☃xx = (double)(this.position.z - this.field_195863_c.get(2));
         this.distanceFromListener = (float)Math.sqrt(☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx);
      }
   }

   private void func_195860_d() {
      if (this.attModel == 2) {
         if (this.distanceFromListener <= 0.0F) {
            this.gain = 1.0F;
         } else if (this.distanceFromListener >= this.distOrRoll) {
            this.gain = 0.0F;
         } else {
            this.gain = 1.0F - this.distanceFromListener / this.distOrRoll;
         }

         if (this.gain > 1.0F) {
            this.gain = 1.0F;
         }

         if (this.gain < 0.0F) {
            this.gain = 0.0F;
         }
      } else {
         this.gain = 1.0F;
      }
   }

   private boolean func_195857_e() {
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
