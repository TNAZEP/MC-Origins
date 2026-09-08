package net.minecraft.client.audio;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import io.netty.util.internal.ThreadLocalRandom;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.IRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.SoundSystemLogger;
import paulscode.sound.Source;
import paulscode.sound.codecs.CodecJOrbis;

public class SoundManager {
   private static final Marker field_148623_a = MarkerManager.getMarker("SOUNDS");
   private static final Logger field_148621_b = LogManager.getLogger();
   private static final Set<ResourceLocation> field_188775_c = Sets.<ResourceLocation>newHashSet();
   private final SoundHandler field_148622_c;
   private final GameSettings field_148619_d;
   private SoundManager.SoundSystemStarterThread field_148620_e;
   private boolean field_148617_f;
   private int field_148618_g;
   private final Map<String, ISound> field_148629_h = HashBiMap.create();
   private final Map<ISound, String> field_148630_i = ((BiMap)this.field_148629_h).inverse();
   private final Multimap<SoundCategory, String> field_188776_k = HashMultimap.create();
   private final List<ITickableSound> field_148625_l = Lists.<ITickableSound>newArrayList();
   private final Map<ISound, Integer> field_148626_m = Maps.newHashMap();
   private final Map<String, Integer> field_148624_n = Maps.newHashMap();
   private final List<ISoundEventListener> field_188777_o = Lists.<ISoundEventListener>newArrayList();
   private final List<String> field_189000_p = Lists.newArrayList();
   private final List<Sound> field_204261_q = Lists.<Sound>newArrayList();

   public SoundManager(SoundHandler var1, GameSettings var2) {
      this.field_148622_c = ☃;
      this.field_148619_d = ☃;

      try {
         SoundSystemConfig.addLibrary(LibraryLWJGL3.class);
         SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
      } catch (SoundSystemException var4) {
         field_148621_b.error(field_148623_a, "Error linking with the LibraryJavaSound plug-in", var4);
      }
   }

   public void func_148596_a() {
      field_188775_c.clear();

      for(SoundEvent ☃ : IRegistry.field_212633_v) {
         ResourceLocation ☃x = ☃.func_187503_a();
         if (this.field_148622_c.func_184398_a(☃x) == null) {
            field_148621_b.warn("Missing sound for event: {}", IRegistry.field_212633_v.func_177774_c(☃));
            field_188775_c.add(☃x);
         }
      }

      this.func_148613_b();
      this.func_148608_i();
   }

   private synchronized void func_148608_i() {
      if (!this.field_148617_f) {
         try {
            Thread ☃ = new Thread(() -> {
               SoundSystemConfig.setLogger(new SoundSystemLogger() {
                  @Override
                  public void message(String var1, int var2) {
                     if (!☃.isEmpty()) {
                        SoundManager.field_148621_b.info(☃);
                     }
                  }

                  @Override
                  public void importantMessage(String var1, int var2) {
                     if (☃.startsWith("Author:")) {
                        SoundManager.field_148621_b.info("SoundSystem {}", ☃);
                     } else if (!☃.isEmpty()) {
                        SoundManager.field_148621_b.warn(☃);
                     }
                  }

                  @Override
                  public void errorMessage(String var1, String var2, int var3) {
                     if (!☃.isEmpty()) {
                        SoundManager.field_148621_b.error("Error in class '{}'", ☃);
                        SoundManager.field_148621_b.error(☃);
                     }
                  }
               });
               this.field_148620_e = new SoundManager.SoundSystemStarterThread();
               this.field_148617_f = true;
               this.field_148620_e.setMasterVolume(this.field_148619_d.func_186711_a(SoundCategory.MASTER));
               Iterator<Sound> ☃ = this.field_204261_q.iterator();

               while(☃.hasNext()) {
                  Sound ☃x = (Sound)☃.next();
                  this.func_204260_b(☃x);
                  ☃.remove();
               }

               field_148621_b.info(field_148623_a, "Sound engine started");
            }, "Sound Library Loader");
            ☃.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_148621_b));
            ☃.start();
         } catch (RuntimeException var2) {
            field_148621_b.error(field_148623_a, "Error starting SoundSystem. Turning off sounds & music", var2);
            this.field_148619_d.func_186712_a(SoundCategory.MASTER, 0.0F);
            this.field_148619_d.func_74303_b();
         }
      }
   }

   private float func_188769_a(SoundCategory var1) {
      return ☃ != null && ☃ != SoundCategory.MASTER ? this.field_148619_d.func_186711_a(☃) : 1.0F;
   }

   public void func_188771_a(SoundCategory var1, float var2) {
      if (this.field_148617_f) {
         if (☃ == SoundCategory.MASTER) {
            this.field_148620_e.setMasterVolume(☃);
         } else {
            for(String ☃ : this.field_188776_k.get(☃)) {
               ISound ☃x = (ISound)this.field_148629_h.get(☃);
               float ☃xx = this.func_188770_e(☃x);
               if (☃xx <= 0.0F) {
                  this.func_148602_b(☃x);
               } else {
                  this.field_148620_e.setVolume(☃, ☃xx);
               }
            }
         }
      }
   }

   public void func_148613_b() {
      if (this.field_148617_f) {
         this.func_148614_c();
         this.field_148620_e.cleanup();
         this.field_148617_f = false;
      }
   }

   public void func_148614_c() {
      if (this.field_148617_f) {
         for(String ☃ : this.field_148629_h.keySet()) {
            this.field_148620_e.stop(☃);
         }

         this.field_148629_h.clear();
         this.field_148626_m.clear();
         this.field_148625_l.clear();
         this.field_189000_p.clear();
         this.field_188776_k.clear();
         this.field_148624_n.clear();
      }
   }

   public void func_188774_a(ISoundEventListener var1) {
      this.field_188777_o.add(☃);
   }

   public void func_188773_b(ISoundEventListener var1) {
      this.field_188777_o.remove(☃);
   }

   public void func_148605_d() {
      ++this.field_148618_g;

      for(ITickableSound ☃ : this.field_148625_l) {
         ☃.func_73660_a();
         if (☃.func_147667_k()) {
            this.func_148602_b(☃);
         } else {
            String ☃x = (String)this.field_148630_i.get(☃);
            this.field_148620_e.setVolume(☃x, this.func_188770_e(☃));
            this.field_148620_e.setPitch(☃x, this.func_188772_d(☃));
            this.field_148620_e.setPosition(☃x, ☃.func_147649_g(), ☃.func_147654_h(), ☃.func_147651_i());
         }
      }

      Iterator<Entry<String, ISound>> ☃ = this.field_148629_h.entrySet().iterator();

      while(☃.hasNext()) {
         Entry<String, ISound> ☃x = (Entry)☃.next();
         String ☃xx = (String)☃x.getKey();
         ISound ☃xxx = (ISound)☃x.getValue();
         float ☃xxxx = this.field_148619_d.func_186711_a(☃xxx.func_184365_d());
         if (☃xxxx <= 0.0F) {
            this.func_148602_b(☃xxx);
         }

         if (!this.field_148620_e.playing(☃xx)) {
            int ☃x = this.field_148624_n.get(☃xx);
            if (☃x <= this.field_148618_g) {
               int ☃xx = ☃xxx.func_147652_d();
               if (☃xxx.func_147657_c() && ☃xx > 0) {
                  this.field_148626_m.put(☃xxx, this.field_148618_g + ☃xx);
               }

               ☃.remove();
               field_148621_b.debug(field_148623_a, "Removed channel {} because it's not playing anymore", ☃xx);
               this.field_148620_e.removeSource(☃xx);
               this.field_148624_n.remove(☃xx);

               try {
                  this.field_188776_k.remove(☃xxx.func_184365_d(), ☃xx);
               } catch (RuntimeException var9) {
               }

               if (☃xxx instanceof ITickableSound) {
                  this.field_148625_l.remove(☃xxx);
               }
            }
         }
      }

      Iterator<Entry<ISound, Integer>> ☃x = this.field_148626_m.entrySet().iterator();

      while(☃x.hasNext()) {
         Entry<ISound, Integer> ☃xx = (Entry)☃x.next();
         if (this.field_148618_g >= ☃xx.getValue()) {
            ISound ☃xxx = (ISound)☃xx.getKey();
            if (☃xxx instanceof ITickableSound) {
               ((ITickableSound)☃xxx).func_73660_a();
            }

            this.func_148611_c(☃xxx);
            ☃x.remove();
         }
      }
   }

   public boolean func_148597_a(ISound var1) {
      if (!this.field_148617_f) {
         return false;
      } else {
         String ☃ = (String)this.field_148630_i.get(☃);
         if (☃ == null) {
            return false;
         } else {
            return this.field_148620_e.playing(☃) || this.field_148624_n.containsKey(☃) && this.field_148624_n.get(☃) <= this.field_148618_g;
         }
      }
   }

   public void func_148602_b(ISound var1) {
      if (this.field_148617_f) {
         String ☃ = (String)this.field_148630_i.get(☃);
         if (☃ != null) {
            this.field_148620_e.stop(☃);
         }
      }
   }

   public void func_148611_c(ISound var1) {
      if (this.field_148617_f) {
         SoundEventAccessor ☃ = ☃.func_184366_a(this.field_148622_c);
         ResourceLocation ☃x = ☃.func_147650_b();
         if (☃ == null) {
            if (field_188775_c.add(☃x)) {
               field_148621_b.warn(field_148623_a, "Unable to play unknown soundEvent: {}", ☃x);
            }
         } else {
            if (!this.field_188777_o.isEmpty()) {
               for(ISoundEventListener ☃ : this.field_188777_o) {
                  ☃.func_184067_a(☃, ☃);
               }
            }

            if (this.field_148620_e.getMasterVolume() <= 0.0F) {
               field_148621_b.debug(field_148623_a, "Skipped playing soundEvent: {}, master volume was zero", ☃x);
            } else {
               Sound ☃ = ☃.func_184364_b();
               if (☃ == SoundHandler.field_147700_a) {
                  if (field_188775_c.add(☃x)) {
                     field_148621_b.warn(field_148623_a, "Unable to play empty soundEvent: {}", ☃x);
                  }
               } else {
                  float ☃ = ☃.func_147653_e();
                  float ☃x = (float)☃.func_206255_j();
                  if (☃ > 1.0F) {
                     ☃x *= ☃;
                  }

                  SoundCategory ☃ = ☃.func_184365_d();
                  float ☃x = this.func_188770_e(☃);
                  float ☃xx = this.func_188772_d(☃);
                  if (☃x == 0.0F && !☃.func_211503_n()) {
                     field_148621_b.debug(field_148623_a, "Skipped playing sound {}, volume was zero.", ☃.func_188719_a());
                  } else {
                     boolean ☃ = ☃.func_147657_c() && ☃.func_147652_d() == 0;
                     String ☃x = MathHelper.func_180182_a(ThreadLocalRandom.current()).toString();
                     ResourceLocation ☃xx = ☃.func_188721_b();
                     if (☃.func_188723_h()) {
                        this.field_148620_e
                           .newStreamingSource(
                              ☃.func_204200_l(),
                              ☃x,
                              func_148612_a(☃xx),
                              ☃xx.toString(),
                              ☃,
                              ☃.func_147649_g(),
                              ☃.func_147654_h(),
                              ☃.func_147651_i(),
                              ☃.func_147656_j().func_148586_a(),
                              ☃x
                           );
                     } else {
                        this.field_148620_e
                           .newSource(
                              ☃.func_204200_l(),
                              ☃x,
                              func_148612_a(☃xx),
                              ☃xx.toString(),
                              ☃,
                              ☃.func_147649_g(),
                              ☃.func_147654_h(),
                              ☃.func_147651_i(),
                              ☃.func_147656_j().func_148586_a(),
                              ☃x
                           );
                     }

                     field_148621_b.debug(field_148623_a, "Playing sound {} for event {} as channel {}", ☃.func_188719_a(), ☃x, ☃x);
                     this.field_148620_e.setPitch(☃x, ☃xx);
                     this.field_148620_e.setVolume(☃x, ☃x);
                     this.field_148620_e.play(☃x);
                     this.field_148624_n.put(☃x, this.field_148618_g + 20);
                     this.field_148629_h.put(☃x, ☃);
                     this.field_188776_k.put(☃, ☃x);
                     if (☃ instanceof ITickableSound) {
                        this.field_148625_l.add((ITickableSound)☃);
                     }
                  }
               }
            }
         }
      }
   }

   public void func_204259_a(Sound var1) {
      this.field_204261_q.add(☃);
   }

   private void func_204260_b(Sound var1) {
      ResourceLocation ☃ = ☃.func_188721_b();
      field_148621_b.info(field_148623_a, "Preloading sound {}", ☃);
      this.field_148620_e.loadSound(func_148612_a(☃), ☃.toString());
   }

   private float func_188772_d(ISound var1) {
      return MathHelper.func_76131_a(☃.func_147655_f(), 0.5F, 2.0F);
   }

   private float func_188770_e(ISound var1) {
      return MathHelper.func_76131_a(☃.func_147653_e() * this.func_188769_a(☃.func_184365_d()), 0.0F, 1.0F);
   }

   public void func_148610_e() {
      for(Entry<String, ISound> ☃ : this.field_148629_h.entrySet()) {
         String ☃x = (String)☃.getKey();
         boolean ☃xx = this.func_148597_a((ISound)☃.getValue());
         if (☃xx) {
            field_148621_b.debug(field_148623_a, "Pausing channel {}", ☃x);
            this.field_148620_e.pause(☃x);
            this.field_189000_p.add(☃x);
         }
      }
   }

   public void func_148604_f() {
      for(String ☃ : this.field_189000_p) {
         field_148621_b.debug(field_148623_a, "Resuming channel {}", ☃);
         this.field_148620_e.play(☃);
      }

      this.field_189000_p.clear();
   }

   public void func_148599_a(ISound var1, int var2) {
      this.field_148626_m.put(☃, this.field_148618_g + ☃);
   }

   private static URL func_148612_a(final ResourceLocation var0) {
      String ☃ = String.format("%s:%s:%s", "mcsounddomain", ☃.func_110624_b(), ☃.func_110623_a());
      URLStreamHandler ☃x = new URLStreamHandler() {
         protected URLConnection openConnection(URL var1) {
            return new URLConnection(☃) {
               public void connect() {
               }

               public InputStream getInputStream() throws IOException {
                  return Minecraft.func_71410_x().func_195551_G().func_199002_a(☃).func_199027_b();
               }
            };
         }
      };

      try {
         return new URL(null, ☃, ☃x);
      } catch (MalformedURLException var4) {
         throw new Error("TODO: Sanely handle url exception! :D");
      }
   }

   public void func_148615_a(EntityPlayer var1, float var2) {
      if (this.field_148617_f && ☃ != null) {
         float ☃ = ☃.field_70127_C + (☃.field_70125_A - ☃.field_70127_C) * ☃;
         float ☃x = ☃.field_70126_B + (☃.field_70177_z - ☃.field_70126_B) * ☃;
         double ☃xx = ☃.field_70169_q + (☃.field_70165_t - ☃.field_70169_q) * (double)☃;
         double ☃xxx = ☃.field_70167_r + (☃.field_70163_u - ☃.field_70167_r) * (double)☃ + (double)☃.func_70047_e();
         double ☃xxxx = ☃.field_70166_s + (☃.field_70161_v - ☃.field_70166_s) * (double)☃;
         float ☃xxxxx = MathHelper.func_76134_b((☃x + 90.0F) * (float) (Math.PI / 180.0));
         float ☃xxxxxx = MathHelper.func_76126_a((☃x + 90.0F) * (float) (Math.PI / 180.0));
         float ☃xxxxxxx = MathHelper.func_76134_b(-☃ * (float) (Math.PI / 180.0));
         float ☃xxxxxxxx = MathHelper.func_76126_a(-☃ * (float) (Math.PI / 180.0));
         float ☃xxxxxxxxx = MathHelper.func_76134_b((-☃ + 90.0F) * (float) (Math.PI / 180.0));
         float ☃xxxxxxxxxx = MathHelper.func_76126_a((-☃ + 90.0F) * (float) (Math.PI / 180.0));
         float ☃xxxxxxxxxxx = ☃xxxxx * ☃xxxxxxx;
         float ☃xxxxxxxxxxxx = ☃xxxxxx * ☃xxxxxxx;
         float ☃xxxxxxxxxxxxx = ☃xxxxx * ☃xxxxxxxxx;
         float ☃xxxxxxxxxxxxxx = ☃xxxxxx * ☃xxxxxxxxx;
         this.field_148620_e.setListenerPosition((float)☃xx, (float)☃xxx, (float)☃xxxx);
         this.field_148620_e.setListenerOrientation(☃xxxxxxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxxxxx);
      }
   }

   public void func_195855_a(@Nullable ResourceLocation var1, @Nullable SoundCategory var2) {
      if (☃ != null) {
         for(String ☃ : this.field_188776_k.get(☃)) {
            ISound ☃x = (ISound)this.field_148629_h.get(☃);
            if (☃ == null) {
               this.func_148602_b(☃x);
            } else if (☃x.func_147650_b().equals(☃)) {
               this.func_148602_b(☃x);
            }
         }
      } else if (☃ == null) {
         this.func_148614_c();
      } else {
         for(ISound ☃ : this.field_148629_h.values()) {
            if (☃.func_147650_b().equals(☃)) {
               this.func_148602_b(☃);
            }
         }
      }
   }

   class SoundSystemStarterThread extends SoundSystem {
      private SoundSystemStarterThread() {
      }

      @Override
      public boolean playing(String var1) {
         synchronized(SoundSystemConfig.THREAD_SYNC) {
            if (this.soundLibrary == null) {
               return false;
            } else {
               Map<String, Source> ☃ = this.soundLibrary.getSources();
               if (☃ == null) {
                  return false;
               } else {
                  Source ☃ = (Source)☃.get(☃);
                  if (☃ == null) {
                     return false;
                  } else {
                     return ☃.playing() || ☃.paused() || ☃.preLoad;
                  }
               }
            }
         }
      }
   }
}
