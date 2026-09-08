package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ITickable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundHandler implements ITickable, IResourceManagerReloadListener {
   public static final Sound field_147700_a = new Sound("meta:missing_sound", 1.0F, 1.0F, 1, Sound.Type.FILE, false, false, 16);
   private static final Logger field_147698_b = LogManager.getLogger();
   private static final Gson field_147699_c = new GsonBuilder()
      .registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer())
      .registerTypeAdapter(SoundList.class, new SoundListSerializer())
      .create();
   private static final ParameterizedType field_147696_d = new ParameterizedType() {
      public Type[] getActualTypeArguments() {
         return new Type[]{String.class, SoundList.class};
      }

      public Type getRawType() {
         return Map.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };
   private final Map<ResourceLocation, SoundEventAccessor> field_147697_e = Maps.<ResourceLocation, SoundEventAccessor>newHashMap();
   private final SoundManager field_147694_f;
   private final IResourceManager field_147695_g;

   public SoundHandler(IResourceManager var1, GameSettings var2) {
      this.field_147695_g = ☃;
      this.field_147694_f = new SoundManager(this, ☃);
   }

   @Override
   public void func_195410_a(IResourceManager var1) {
      this.field_147697_e.clear();

      for(String ☃ : ☃.func_199001_a()) {
         try {
            for(IResource ☃x : ☃.func_199004_b(new ResourceLocation(☃, "sounds.json"))) {
               try {
                  Map<String, SoundList> ☃xx = this.func_175085_a(☃x.func_199027_b());

                  for(Entry<String, SoundList> ☃xxx : ☃xx.entrySet()) {
                     this.func_147693_a(new ResourceLocation(☃, (String)☃xxx.getKey()), (SoundList)☃xxx.getValue());
                  }
               } catch (RuntimeException var10) {
                  field_147698_b.warn("Invalid sounds.json in resourcepack: '{}'", ☃x.func_199026_d(), var10);
               }
            }
         } catch (IOException var11) {
         }
      }

      for(ResourceLocation ☃ : this.field_147697_e.keySet()) {
         SoundEventAccessor ☃x = (SoundEventAccessor)this.field_147697_e.get(☃);
         if (☃x.func_188712_c() instanceof TextComponentTranslation) {
            String ☃xx = ((TextComponentTranslation)☃x.func_188712_c()).func_150268_i();
            if (!I18n.func_188566_a(☃xx)) {
               field_147698_b.debug("Missing subtitle {} for event: {}", ☃xx, ☃);
            }
         }
      }

      for(ResourceLocation ☃ : this.field_147697_e.keySet()) {
         if (IRegistry.field_212633_v.func_212608_b(☃) == null) {
            field_147698_b.debug("Not having sound event for: {}", ☃);
         }
      }

      this.field_147694_f.func_148596_a();
   }

   @Nullable
   protected Map<String, SoundList> func_175085_a(InputStream var1) {
      Map var2;
      try {
         var2 = JsonUtils.func_193841_a(field_147699_c, new InputStreamReader(☃, StandardCharsets.UTF_8), field_147696_d);
      } finally {
         IOUtils.closeQuietly(☃);
      }

      return var2;
   }

   private void func_147693_a(ResourceLocation var1, SoundList var2) {
      SoundEventAccessor ☃ = (SoundEventAccessor)this.field_147697_e.get(☃);
      boolean ☃x = ☃ == null;
      if (☃x || ☃.func_148574_b()) {
         if (!☃x) {
            field_147698_b.debug("Replaced sound event location {}", ☃);
         }

         ☃ = new SoundEventAccessor(☃, ☃.func_188701_c());
         this.field_147697_e.put(☃, ☃);
      }

      for(final Sound ☃ : ☃.func_188700_a()) {
         final ResourceLocation ☃xx = ☃.func_188719_a();
         ISoundEventAccessor<Sound> ☃x;
         switch(☃.func_188722_g()) {
            case FILE:
               if (!this.func_184401_a(☃, ☃)) {
                  continue;
               }

               ☃x = ☃;
               break;
            case SOUND_EVENT:
               ☃x = new ISoundEventAccessor<Sound>() {
                  @Override
                  public int func_148721_a() {
                     SoundEventAccessor ☃ = (SoundEventAccessor)SoundHandler.this.field_147697_e.get(☃);
                     return ☃ == null ? 0 : ☃.func_148721_a();
                  }

                  public Sound func_148720_g() {
                     SoundEventAccessor ☃ = (SoundEventAccessor)SoundHandler.this.field_147697_e.get(☃);
                     if (☃ == null) {
                        return SoundHandler.field_147700_a;
                     } else {
                        Sound ☃ = ☃.func_148720_g();
                        return new Sound(
                           ☃.func_188719_a().toString(),
                           ☃.func_188724_c() * ☃.func_188724_c(),
                           ☃.func_188725_d() * ☃.func_188725_d(),
                           ☃.func_148721_a(),
                           Sound.Type.FILE,
                           ☃.func_188723_h() || ☃.func_188723_h(),
                           ☃.func_204257_i(),
                           ☃.func_206255_j()
                        );
                     }
                  }
               };
               break;
            default:
               throw new IllegalStateException("Unknown SoundEventRegistration type: " + ☃.func_188722_g());
         }

         if (☃x.func_148720_g().func_204257_i()) {
            this.field_147694_f.func_204259_a(☃x.func_148720_g());
         }

         ☃.func_188715_a(☃x);
      }
   }

   private boolean func_184401_a(Sound var1, ResourceLocation var2) {
      ResourceLocation ☃ = ☃.func_188721_b();
      IResource ☃x = null;

      boolean var6;
      try {
         ☃x = this.field_147695_g.func_199002_a(☃);
         ☃x.func_199027_b();
         return true;
      } catch (FileNotFoundException var11) {
         field_147698_b.warn("File {} does not exist, cannot add it to event {}", ☃, ☃);
         return false;
      } catch (IOException var12) {
         field_147698_b.warn("Could not load sound file {}, cannot add it to event {}", ☃, ☃, var12);
         var6 = false;
      } finally {
         IOUtils.closeQuietly(☃x);
      }

      return var6;
   }

   @Nullable
   public SoundEventAccessor func_184398_a(ResourceLocation var1) {
      return (SoundEventAccessor)this.field_147697_e.get(☃);
   }

   public Collection<ResourceLocation> func_195477_a() {
      return this.field_147697_e.keySet();
   }

   public void func_147682_a(ISound var1) {
      this.field_147694_f.func_148611_c(☃);
   }

   public void func_147681_a(ISound var1, int var2) {
      this.field_147694_f.func_148599_a(☃, ☃);
   }

   public void func_147691_a(EntityPlayer var1, float var2) {
      this.field_147694_f.func_148615_a(☃, ☃);
   }

   public void func_147689_b() {
      this.field_147694_f.func_148610_e();
   }

   public void func_147690_c() {
      this.field_147694_f.func_148614_c();
   }

   public void func_147685_d() {
      this.field_147694_f.func_148613_b();
   }

   @Override
   public void func_73660_a() {
      this.field_147694_f.func_148605_d();
   }

   public void func_147687_e() {
      this.field_147694_f.func_148604_f();
   }

   public void func_184399_a(SoundCategory var1, float var2) {
      if (☃ == SoundCategory.MASTER && ☃ <= 0.0F) {
         this.func_147690_c();
      }

      this.field_147694_f.func_188771_a(☃, ☃);
   }

   public void func_147683_b(ISound var1) {
      this.field_147694_f.func_148602_b(☃);
   }

   public boolean func_147692_c(ISound var1) {
      return this.field_147694_f.func_148597_a(☃);
   }

   public void func_184402_a(ISoundEventListener var1) {
      this.field_147694_f.func_188774_a(☃);
   }

   public void func_184400_b(ISoundEventListener var1) {
      this.field_147694_f.func_188773_b(☃);
   }

   public void func_195478_a(@Nullable ResourceLocation var1, @Nullable SoundCategory var2) {
      this.field_147694_f.func_195855_a(☃, ☃);
   }
}
