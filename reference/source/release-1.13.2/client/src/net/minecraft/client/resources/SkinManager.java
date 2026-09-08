package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.ThreadDownloadImageData;
import net.minecraft.util.ResourceLocation;

public class SkinManager {
   private static final ExecutorService field_152794_b = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue());
   private final TextureManager field_152795_c;
   private final File field_152796_d;
   private final MinecraftSessionService field_152797_e;
   private final LoadingCache<GameProfile, Map<Type, MinecraftProfileTexture>> field_152798_f;

   public SkinManager(TextureManager var1, File var2, MinecraftSessionService var3) {
      this.field_152795_c = ☃;
      this.field_152796_d = ☃;
      this.field_152797_e = ☃;
      this.field_152798_f = CacheBuilder.newBuilder()
         .expireAfterAccess(15L, TimeUnit.SECONDS)
         .build(new CacheLoader<GameProfile, Map<Type, MinecraftProfileTexture>>() {
            public Map<Type, MinecraftProfileTexture> load(GameProfile var1) throws Exception {
               try {
                  return Minecraft.func_71410_x().func_152347_ac().getTextures(☃, false);
               } catch (Throwable var3) {
                  return Maps.newHashMap();
               }
            }
         });
   }

   public ResourceLocation func_152792_a(MinecraftProfileTexture var1, Type var2) {
      return this.func_152789_a(☃, ☃, null);
   }

   public ResourceLocation func_152789_a(final MinecraftProfileTexture var1, final Type var2, @Nullable final SkinManager.SkinAvailableCallback var3) {
      String ☃ = Hashing.sha1().hashUnencodedChars(☃.getHash()).toString();
      final ResourceLocation ☃x = new ResourceLocation("skins/" + ☃);
      ITextureObject ☃xx = this.field_152795_c.func_110581_b(☃x);
      if (☃xx != null) {
         if (☃ != null) {
            ☃.onSkinTextureAvailable(☃, ☃x, ☃);
         }
      } else {
         File ☃ = new File(this.field_152796_d, ☃.length() > 2 ? ☃.substring(0, 2) : "xx");
         File ☃x = new File(☃, ☃);
         final IImageBuffer ☃xx = ☃ == Type.SKIN ? new ImageBufferDownload() : null;
         ThreadDownloadImageData ☃xxx = new ThreadDownloadImageData(☃x, ☃.getUrl(), DefaultPlayerSkin.func_177335_a(), new IImageBuffer() {
            @Override
            public NativeImage func_195786_a(NativeImage var1x) {
               return ☃ != null ? ☃.func_195786_a(☃) : ☃;
            }

            @Override
            public void func_152634_a() {
               if (☃ != null) {
                  ☃.func_152634_a();
               }

               if (☃ != null) {
                  ☃.onSkinTextureAvailable(☃, ☃, ☃);
               }
            }
         });
         this.field_152795_c.func_110579_a(☃x, ☃xxx);
      }

      return ☃x;
   }

   public void func_152790_a(GameProfile var1, SkinManager.SkinAvailableCallback var2, boolean var3) {
      field_152794_b.submit(() -> {
         Map<Type, MinecraftProfileTexture> ☃ = Maps.newHashMap();

         try {
            ☃.putAll(this.field_152797_e.getTextures(☃, ☃));
         } catch (InsecureTextureException var7) {
         }

         if (☃.isEmpty()) {
            ☃.getProperties().clear();
            if (☃.getId().equals(Minecraft.func_71410_x().func_110432_I().func_148256_e().getId())) {
               ☃.getProperties().putAll(Minecraft.func_71410_x().func_181037_M());
               ☃.putAll(this.field_152797_e.getTextures(☃, false));
            } else {
               this.field_152797_e.fillProfileProperties(☃, ☃);

               try {
                  ☃.putAll(this.field_152797_e.getTextures(☃, ☃));
               } catch (InsecureTextureException var6) {
               }
            }
         }

         Minecraft.func_71410_x().func_152344_a(() -> {
            if (☃.containsKey(Type.SKIN)) {
               this.func_152789_a((MinecraftProfileTexture)☃.get(Type.SKIN), Type.SKIN, ☃);
            }

            if (☃.containsKey(Type.CAPE)) {
               this.func_152789_a((MinecraftProfileTexture)☃.get(Type.CAPE), Type.CAPE, ☃);
            }
         });
      });
   }

   public Map<Type, MinecraftProfileTexture> func_152788_a(GameProfile var1) {
      return (Map<Type, MinecraftProfileTexture>)this.field_152798_f.getUnchecked(☃);
   }

   public interface SkinAvailableCallback {
      void onSkinTextureAvailable(Type var1, ResourceLocation var2, MinecraftProfileTexture var3);
   }
}
