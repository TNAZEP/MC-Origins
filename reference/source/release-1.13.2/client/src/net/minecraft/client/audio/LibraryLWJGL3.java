package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.sound.sampled.AudioFormat;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import paulscode.sound.Channel;
import paulscode.sound.FilenameURL;
import paulscode.sound.ICodec;
import paulscode.sound.Library;
import paulscode.sound.ListenerData;
import paulscode.sound.SoundBuffer;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.Source;

public class LibraryLWJGL3 extends Library {
   private FloatBuffer field_195839_a;
   private FloatBuffer field_195840_b;
   private FloatBuffer field_195841_c;
   private Map<String, IntBuffer> field_195842_d;
   private static boolean field_195843_e = true;
   private String field_195844_f = "PreInit";
   private long field_195845_g;
   private long field_195846_h;

   public LibraryLWJGL3() throws SoundSystemException {
      this.field_195842_d = Maps.newHashMap();
      this.reverseByteOrder = true;
   }

   @Override
   public void init() throws SoundSystemException {
      boolean ☃ = false;
      long ☃x = ALC10.alcOpenDevice((ByteBuffer)null);
      if (☃x == 0L) {
         throw new LibraryLWJGL3.LWJGL3SoundSystemException("Failed to open default device", 101);
      } else {
         ALCCapabilities ☃ = ALC.createCapabilities(☃x);
         if (!☃.OpenALC10) {
            throw new LibraryLWJGL3.LWJGL3SoundSystemException("OpenAL 1.0 not supported", 101);
         } else {
            this.field_195846_h = ALC10.alcCreateContext(☃x, (IntBuffer)null);
            ALC10.alcMakeContextCurrent(this.field_195846_h);
            AL.createCapabilities(☃);
            this.message("OpenAL initialized.");
            this.field_195839_a = BufferUtils.createFloatBuffer(3)
               .put(new float[]{this.listener.position.x, this.listener.position.y, this.listener.position.z});
            this.field_195840_b = BufferUtils.createFloatBuffer(6)
               .put(
                  new float[]{
                     this.listener.lookAt.x, this.listener.lookAt.y, this.listener.lookAt.z, this.listener.up.x, this.listener.up.y, this.listener.up.z
                  }
               );
            this.field_195841_c = BufferUtils.createFloatBuffer(3).put(new float[]{0.0F, 0.0F, 0.0F});
            this.field_195839_a.flip();
            this.field_195840_b.flip();
            this.field_195841_c.flip();
            this.field_195844_f = "Post Init";
            AL10.alListenerfv(4100, this.field_195839_a);
            ☃ = this.func_195837_d() || ☃;
            AL10.alListenerfv(4111, this.field_195840_b);
            ☃ = this.func_195837_d() || ☃;
            AL10.alListenerfv(4102, this.field_195841_c);
            ☃ = this.func_195837_d() || ☃;
            AL10.alDopplerFactor(SoundSystemConfig.getDopplerFactor());
            ☃ = this.func_195837_d() || ☃;
            AL10.alDopplerVelocity(SoundSystemConfig.getDopplerVelocity());
            ☃ = this.func_195837_d() || ☃;
            if (☃) {
               this.importantMessage("OpenAL did not initialize properly!");
               throw new LibraryLWJGL3.LWJGL3SoundSystemException(
                  "Problem encountered while loading OpenAL or creating the listener. Probable cause: OpenAL not supported", 101
               );
            } else {
               super.init();
               ChannelLWJGL3 ☃ = (ChannelLWJGL3)this.normalChannels.get(1);

               try {
                  AL10.alSourcef(☃.field_195851_a.get(0), 4099, 1.0F);
                  if (this.func_195837_d()) {
                     func_195838_a(true, false);
                     throw new LibraryLWJGL3.LWJGL3SoundSystemException("OpenAL: AL_PITCH not supported.", 108);
                  }

                  func_195838_a(true, true);
               } catch (Exception var7) {
                  func_195838_a(true, false);
                  throw new LibraryLWJGL3.LWJGL3SoundSystemException("OpenAL: AL_PITCH not supported.", 108);
               }

               this.field_195844_f = "Running";
            }
         }
      }
   }

   @Override
   protected Channel createChannel(int var1) {
      IntBuffer ☃ = BufferUtils.createIntBuffer(1);

      try {
         AL10.alGenSources(☃);
      } catch (Exception var4) {
         AL10.alGetError();
         return null;
      }

      return AL10.alGetError() != 0 ? null : new ChannelLWJGL3(☃, ☃);
   }

   @Override
   public void cleanup() {
      super.cleanup();

      for(String ☃ : this.bufferMap.keySet()) {
         IntBuffer ☃x = (IntBuffer)this.field_195842_d.get(☃);
         if (☃x != null) {
            AL10.alDeleteBuffers(☃x);
            this.func_195837_d();
            ☃x.clear();
         }
      }

      this.bufferMap.clear();
      ALC10.alcDestroyContext(this.field_195846_h);
      if (this.field_195845_g != 0L) {
         ALC10.alcCloseDevice(this.field_195845_g);
      }

      this.bufferMap = null;
      this.field_195839_a = null;
      this.field_195840_b = null;
      this.field_195841_c = null;
   }

   @Override
   public boolean loadSound(FilenameURL var1) {
      if (this.bufferMap == null) {
         this.bufferMap = Maps.newHashMap();
         this.importantMessage("Buffer Map was null in method 'loadSound'");
      }

      if (this.field_195842_d == null) {
         this.field_195842_d = Maps.newHashMap();
         this.importantMessage("Open AL Buffer Map was null in method 'loadSound'");
      }

      if (this.errorCheck(☃ == null, "Filename/URL not specified in method 'loadSound'")) {
         return false;
      } else if (this.bufferMap.get(☃.getFilename()) != null) {
         return true;
      } else {
         ICodec ☃ = SoundSystemConfig.getCodec(☃.getFilename());
         if (this.errorCheck(☃ == null, "No codec found for file '" + ☃.getFilename() + "' in method 'loadSound'")) {
            return false;
         } else {
            ☃.reverseByteOrder(true);
            URL ☃ = ☃.getURL();
            if (this.errorCheck(☃ == null, "Unable to open file '" + ☃.getFilename() + "' in method 'loadSound'")) {
               return false;
            } else {
               ☃.initialize(☃);
               SoundBuffer ☃ = ☃.readAll();
               ☃.cleanup();
               ICodec var8 = null;
               if (this.errorCheck(☃ == null, "Sound buffer null in method 'loadSound'")) {
                  return false;
               } else {
                  this.bufferMap.put(☃.getFilename(), ☃);
                  AudioFormat ☃x = ☃.audioFormat;
                  int ☃;
                  if (☃x.getChannels() == 1) {
                     if (☃x.getSampleSizeInBits() == 8) {
                        ☃ = 4352;
                     } else {
                        if (☃x.getSampleSizeInBits() != 16) {
                           this.errorMessage("Illegal sample size in method 'loadSound'");
                           return false;
                        }

                        ☃ = 4353;
                     }
                  } else {
                     if (☃x.getChannels() != 2) {
                        this.errorMessage("File neither mono nor stereo in method 'loadSound'");
                        return false;
                     }

                     if (☃x.getSampleSizeInBits() == 8) {
                        ☃ = 4354;
                     } else {
                        if (☃x.getSampleSizeInBits() != 16) {
                           this.errorMessage("Illegal sample size in method 'loadSound'");
                           return false;
                        }

                        ☃ = 4355;
                     }
                  }

                  IntBuffer ☃ = BufferUtils.createIntBuffer(1);
                  AL10.alGenBuffers(☃);
                  if (this.errorCheck(AL10.alGetError() != 0, "alGenBuffers error when loading " + ☃.getFilename())) {
                     return false;
                  } else {
                     AL10.alBufferData(
                        ☃.get(0), ☃, (ByteBuffer)BufferUtils.createByteBuffer(☃.audioData.length).put(☃.audioData).flip(), (int)☃x.getSampleRate()
                     );
                     if (this.errorCheck(AL10.alGetError() != 0, "alBufferData error when loading " + ☃.getFilename())
                        && this.errorCheck(☃ == null, "Sound buffer was not created for " + ☃.getFilename())) {
                        return false;
                     } else {
                        this.field_195842_d.put(☃.getFilename(), ☃);
                        return true;
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean loadSound(SoundBuffer var1, String var2) {
      if (this.bufferMap == null) {
         this.bufferMap = Maps.newHashMap();
         this.importantMessage("Buffer Map was null in method 'loadSound'");
      }

      if (this.field_195842_d == null) {
         this.field_195842_d = Maps.newHashMap();
         this.importantMessage("Open AL Buffer Map was null in method 'loadSound'");
      }

      if (this.errorCheck(☃ == null, "Identifier not specified in method 'loadSound'")) {
         return false;
      } else if (this.bufferMap.get(☃) != null) {
         return true;
      } else if (this.errorCheck(☃ == null, "Sound buffer null in method 'loadSound'")) {
         return false;
      } else {
         this.bufferMap.put(☃, ☃);
         AudioFormat ☃x = ☃.audioFormat;
         int ☃;
         if (☃x.getChannels() == 1) {
            if (☃x.getSampleSizeInBits() == 8) {
               ☃ = 4352;
            } else {
               if (☃x.getSampleSizeInBits() != 16) {
                  this.errorMessage("Illegal sample size in method 'loadSound'");
                  return false;
               }

               ☃ = 4353;
            }
         } else {
            if (☃x.getChannels() != 2) {
               this.errorMessage("File neither mono nor stereo in method 'loadSound'");
               return false;
            }

            if (☃x.getSampleSizeInBits() == 8) {
               ☃ = 4354;
            } else {
               if (☃x.getSampleSizeInBits() != 16) {
                  this.errorMessage("Illegal sample size in method 'loadSound'");
                  return false;
               }

               ☃ = 4355;
            }
         }

         IntBuffer ☃ = BufferUtils.createIntBuffer(1);
         AL10.alGenBuffers(☃);
         if (this.errorCheck(AL10.alGetError() != 0, "alGenBuffers error when saving " + ☃)) {
            return false;
         } else {
            AL10.alBufferData(☃.get(0), ☃, (ByteBuffer)BufferUtils.createByteBuffer(☃.audioData.length).put(☃.audioData).flip(), (int)☃x.getSampleRate());
            if (this.errorCheck(AL10.alGetError() != 0, "alBufferData error when saving " + ☃)
               && this.errorCheck(☃ == null, "Sound buffer was not created for " + ☃)) {
               return false;
            } else {
               this.field_195842_d.put(☃, ☃);
               return true;
            }
         }
      }
   }

   @Override
   public void unloadSound(String var1) {
      this.field_195842_d.remove(☃);
      super.unloadSound(☃);
   }

   @Override
   public void setMasterVolume(float var1) {
      super.setMasterVolume(☃);
      AL10.alListenerf(4106, ☃);
      this.func_195837_d();
   }

   @Override
   public void newSource(boolean var1, boolean var2, boolean var3, String var4, FilenameURL var5, float var6, float var7, float var8, int var9, float var10) {
      IntBuffer ☃ = null;
      if (!☃) {
         ☃ = (IntBuffer)this.field_195842_d.get(☃.getFilename());
         if (☃ == null && !this.loadSound(☃)) {
            this.errorMessage(String.format("Source '%s' was not created because an error occurred while loading %s", ☃, ☃.getFilename()));
            return;
         }

         ☃ = (IntBuffer)this.field_195842_d.get(☃.getFilename());
         if (☃ == null) {
            this.errorMessage(String.format("Source '%s' was not created because a sound buffer was not found for %s", ☃, ☃.getFilename()));
            return;
         }
      }

      SoundBuffer ☃ = null;
      if (!☃) {
         ☃ = (SoundBuffer)this.bufferMap.get(☃.getFilename());
         if (☃ == null && !this.loadSound(☃)) {
            this.errorMessage(String.format("Source '%s' was not created because an error occurred while loading %s", ☃, ☃.getFilename()));
            return;
         }

         ☃ = (SoundBuffer)this.bufferMap.get(☃.getFilename());
         if (☃ == null) {
            this.errorMessage(String.format("Source '%s' was not created because audio data was not found for %s", ☃, ☃.getFilename()));
            return;
         }
      }

      this.sourceMap.put(☃, new SourceLWJGL3(this.field_195839_a, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, false));
   }

   @Override
   public void rawDataStream(AudioFormat var1, boolean var2, String var3, float var4, float var5, float var6, int var7, float var8) {
      this.sourceMap.put(☃, new SourceLWJGL3(this.field_195839_a, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃));
   }

   @Override
   public void quickPlay(
      boolean var1, boolean var2, boolean var3, String var4, FilenameURL var5, float var6, float var7, float var8, int var9, float var10, boolean var11
   ) {
      IntBuffer ☃ = null;
      if (!☃) {
         ☃ = (IntBuffer)this.field_195842_d.get(☃.getFilename());
         if (☃ == null) {
            this.loadSound(☃);
         }

         ☃ = (IntBuffer)this.field_195842_d.get(☃.getFilename());
         if (☃ == null) {
            this.errorMessage("Sound buffer was not created for " + ☃.getFilename());
            return;
         }
      }

      SoundBuffer ☃ = null;
      if (!☃) {
         ☃ = (SoundBuffer)this.bufferMap.get(☃.getFilename());
         if (☃ == null && !this.loadSound(☃)) {
            this.errorMessage(String.format("Source '%s' was not created because an error occurred while loading %s", ☃, ☃.getFilename()));
            return;
         }

         ☃ = (SoundBuffer)this.bufferMap.get(☃.getFilename());
         if (☃ == null) {
            this.errorMessage(String.format("Source '%s' was not created because audio data was not found for %s", ☃, ☃.getFilename()));
            return;
         }
      }

      SourceLWJGL3 ☃ = new SourceLWJGL3(this.field_195839_a, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, false);
      this.sourceMap.put(☃, ☃);
      this.play(☃);
      if (☃) {
         ☃.setTemporary(true);
      }
   }

   @Override
   public void copySources(HashMap<String, Source> var1) {
      if (☃ != null) {
         Set<String> ☃ = ☃.keySet();
         Iterator<String> ☃x = ☃.iterator();
         if (this.bufferMap == null) {
            this.bufferMap = Maps.newHashMap();
            this.importantMessage("Buffer Map was null in method 'copySources'");
         }

         if (this.field_195842_d == null) {
            this.field_195842_d = Maps.newHashMap();
            this.importantMessage("Open AL Buffer Map was null in method 'copySources'");
         }

         this.sourceMap.clear();

         while(☃x.hasNext()) {
            String ☃ = (String)☃x.next();
            Source ☃x = (Source)☃.get(☃);
            if (☃x != null) {
               SoundBuffer ☃xx = null;
               if (!☃x.toStream) {
                  this.loadSound(☃x.filenameURL);
                  ☃xx = (SoundBuffer)this.bufferMap.get(☃x.filenameURL.getFilename());
               }

               if (☃x.toStream || ☃xx != null) {
                  this.sourceMap.put(☃, new SourceLWJGL3(this.field_195839_a, (IntBuffer)this.field_195842_d.get(☃x.filenameURL.getFilename()), ☃x, ☃xx));
               }
            }
         }
      }
   }

   @Override
   public void setListenerPosition(float var1, float var2, float var3) {
      super.setListenerPosition(☃, ☃, ☃);
      this.field_195839_a.put(0, ☃);
      this.field_195839_a.put(1, ☃);
      this.field_195839_a.put(2, ☃);
      AL10.alListenerfv(4100, this.field_195839_a);
      this.func_195837_d();
   }

   @Override
   public void setListenerAngle(float var1) {
      super.setListenerAngle(☃);
      this.field_195840_b.put(0, this.listener.lookAt.x);
      this.field_195840_b.put(2, this.listener.lookAt.z);
      AL10.alListenerfv(4111, this.field_195840_b);
      this.func_195837_d();
   }

   @Override
   public void setListenerOrientation(float var1, float var2, float var3, float var4, float var5, float var6) {
      super.setListenerOrientation(☃, ☃, ☃, ☃, ☃, ☃);
      this.field_195840_b.put(0, ☃);
      this.field_195840_b.put(1, ☃);
      this.field_195840_b.put(2, ☃);
      this.field_195840_b.put(3, ☃);
      this.field_195840_b.put(4, ☃);
      this.field_195840_b.put(5, ☃);
      AL10.alListenerfv(4111, this.field_195840_b);
      this.func_195837_d();
   }

   @Override
   public void setListenerData(ListenerData var1) {
      super.setListenerData(☃);
      this.field_195839_a.put(0, ☃.position.x);
      this.field_195839_a.put(1, ☃.position.y);
      this.field_195839_a.put(2, ☃.position.z);
      AL10.alListenerfv(4100, this.field_195839_a);
      this.func_195837_d();
      this.field_195840_b.put(0, ☃.lookAt.x);
      this.field_195840_b.put(1, ☃.lookAt.y);
      this.field_195840_b.put(2, ☃.lookAt.z);
      this.field_195840_b.put(3, ☃.up.x);
      this.field_195840_b.put(4, ☃.up.y);
      this.field_195840_b.put(5, ☃.up.z);
      AL10.alListenerfv(4111, this.field_195840_b);
      this.func_195837_d();
      this.field_195841_c.put(0, ☃.velocity.x);
      this.field_195841_c.put(1, ☃.velocity.y);
      this.field_195841_c.put(2, ☃.velocity.z);
      AL10.alListenerfv(4102, this.field_195841_c);
      this.func_195837_d();
   }

   @Override
   public void setListenerVelocity(float var1, float var2, float var3) {
      super.setListenerVelocity(☃, ☃, ☃);
      this.field_195841_c.put(0, this.listener.velocity.x);
      this.field_195841_c.put(1, this.listener.velocity.y);
      this.field_195841_c.put(2, this.listener.velocity.z);
      AL10.alListenerfv(4102, this.field_195841_c);
   }

   @Override
   public void dopplerChanged() {
      super.dopplerChanged();
      AL10.alDopplerFactor(SoundSystemConfig.getDopplerFactor());
      this.func_195837_d();
      AL10.alDopplerVelocity(SoundSystemConfig.getDopplerVelocity());
      this.func_195837_d();
   }

   private boolean func_195837_d() {
      switch(AL10.alGetError()) {
         case 0:
            return false;
         case 40961:
            this.errorMessage("Invalid name parameter: " + this.field_195844_f);
            return true;
         case 40962:
            this.errorMessage("Invalid parameter: " + this.field_195844_f);
            return true;
         case 40963:
            this.errorMessage("Invalid enumerated parameter value: " + this.field_195844_f);
            return true;
         case 40964:
            this.errorMessage("Illegal call: " + this.field_195844_f);
            return true;
         case 40965:
            this.errorMessage("Unable to allocate memory: " + this.field_195844_f);
            return true;
         default:
            this.errorMessage("An unrecognized error occurred: " + this.field_195844_f);
            return true;
      }
   }

   public static boolean func_195836_a() {
      return func_195838_a(false, false);
   }

   private static synchronized boolean func_195838_a(boolean var0, boolean var1) {
      if (☃) {
         field_195843_e = ☃;
      }

      return field_195843_e;
   }

   @Override
   public String getClassName() {
      return "LibraryLWJGL3";
   }

   public static class LWJGL3SoundSystemException extends SoundSystemException {
      public LWJGL3SoundSystemException(String var1) {
         super(☃);
      }

      public LWJGL3SoundSystemException(String var1, int var2) {
         super(☃, ☃);
      }
   }
}
