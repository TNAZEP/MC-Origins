package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.properties.Property;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class SkinManager {
   public static final String PROPERTY_TEXTURES = "textures";
   private final TextureManager textureManager;
   private final File skinsDirectory;
   private final MinecraftSessionService sessionService;
   private final LoadingCache<String, Map<Type, MinecraftProfileTexture>> insecureSkinCache;

   public SkinManager(TextureManager var1, File var2, final MinecraftSessionService var3) {
      this.textureManager = â˜ƒ;
      this.skinsDirectory = â˜ƒ;
      this.sessionService = â˜ƒ;
      this.insecureSkinCache = CacheBuilder.newBuilder()
         .expireAfterAccess(15L, TimeUnit.SECONDS)
         .build(new CacheLoader<String, Map<Type, MinecraftProfileTexture>>() {
            public Map<Type, MinecraftProfileTexture> load(String var1) {
               GameProfile â˜ƒ = new GameProfile(null, "dummy_mcdummyface");
               â˜ƒ.getProperties().put("textures", new Property("textures", â˜ƒ, ""));
   
               try {
                  return â˜ƒ.getTextures(â˜ƒ, false);
               } catch (Throwable var4) {
                  return ImmutableMap.of();
               }
            }
         });
   }

   public ResourceLocation registerTexture(MinecraftProfileTexture var1, Type var2) {
      return this.registerTexture(â˜ƒ, â˜ƒ, null);
   }

   private ResourceLocation registerTexture(MinecraftProfileTexture var1, Type var2, @Nullable SkinManager.SkinTextureCallback var3) {
      String â˜ƒ = Hashing.sha1().hashUnencodedChars(â˜ƒ.getHash()).toString();
      ResourceLocation â˜ƒx = new ResourceLocation("skins/" + â˜ƒ);
      AbstractTexture â˜ƒxx = this.textureManager.getTexture(â˜ƒx, MissingTextureAtlasSprite.getTexture());
      if (â˜ƒxx == MissingTextureAtlasSprite.getTexture()) {
         File â˜ƒxxx = new File(this.skinsDirectory, â˜ƒ.length() > 2 ? â˜ƒ.substring(0, 2) : "xx");
         File â˜ƒxxxx = new File(â˜ƒxxx, â˜ƒ);
         HttpTexture â˜ƒxxxxx = new HttpTexture(â˜ƒxxxx, â˜ƒ.getUrl(), DefaultPlayerSkin.getDefaultSkin(), â˜ƒ == Type.SKIN, () -> {
            if (â˜ƒ != null) {
               â˜ƒ.onSkinTextureAvailable(â˜ƒ, â˜ƒ, â˜ƒ);
            }
         });
         this.textureManager.register(â˜ƒx, â˜ƒxxxxx);
      } else if (â˜ƒ != null) {
         â˜ƒ.onSkinTextureAvailable(â˜ƒ, â˜ƒx, â˜ƒ);
      }

      return â˜ƒx;
   }

   public void registerSkins(GameProfile var1, SkinManager.SkinTextureCallback var2, boolean var3) {
      Runnable â˜ƒ = () -> {
         Map<Type, MinecraftProfileTexture> â˜ƒ = Maps.newHashMap();

         try {
            â˜ƒ.putAll(this.sessionService.getTextures(â˜ƒ, â˜ƒ));
         } catch (InsecureTextureException var7) {
         }

         if (â˜ƒ.isEmpty()) {
            â˜ƒ.getProperties().clear();
            if (â˜ƒ.getId().equals(Minecraft.getInstance().getUser().getGameProfile().getId())) {
               â˜ƒ.getProperties().putAll(Minecraft.getInstance().getProfileProperties());
               â˜ƒ.putAll(this.sessionService.getTextures(â˜ƒ, false));
            } else {
               this.sessionService.fillProfileProperties(â˜ƒ, â˜ƒ);

               try {
                  â˜ƒ.putAll(this.sessionService.getTextures(â˜ƒ, â˜ƒ));
               } catch (InsecureTextureException var6) {
               }
            }
         }

         Minecraft.getInstance().execute(() -> RenderSystem.recordRenderCall(() -> ImmutableList.of(Type.SKIN, Type.CAPE).forEach(var3x -> {
                  if (â˜ƒ.containsKey(var3x)) {
                     this.registerTexture((MinecraftProfileTexture)â˜ƒ.get(var3x), var3x, â˜ƒ);
                  }
               })));
      };
      Util.backgroundExecutor().execute(â˜ƒ);
   }

   public Map<Type, MinecraftProfileTexture> getInsecureSkinInformation(GameProfile var1) {
      Property â˜ƒ = Iterables.getFirst(â˜ƒ.getProperties().get("textures"), null);
      return (Map<Type, MinecraftProfileTexture>)(â˜ƒ == null ? ImmutableMap.of() : (Map)this.insecureSkinCache.getUnchecked(â˜ƒ.getValue()));
   }

   public interface SkinTextureCallback {
      void onSkinTextureAvailable(Type var1, ResourceLocation var2, MinecraftProfileTexture var3);
   }
}
