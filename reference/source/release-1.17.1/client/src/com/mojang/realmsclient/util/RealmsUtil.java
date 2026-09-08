package com.mojang.realmsclient.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;

public class RealmsUtil {
   private static final YggdrasilAuthenticationService AUTHENTICATION_SERVICE = new YggdrasilAuthenticationService(Minecraft.getInstance().getProxy());
   static final MinecraftSessionService SESSION_SERVICE = AUTHENTICATION_SERVICE.createMinecraftSessionService();
   public static LoadingCache<String, GameProfile> gameProfileCache = CacheBuilder.newBuilder()
      .expireAfterWrite(60L, TimeUnit.MINUTES)
      .build(new CacheLoader<String, GameProfile>() {
         public GameProfile load(String var1) throws Exception {
            GameProfile â˜ƒ = RealmsUtil.SESSION_SERVICE.fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(â˜ƒ), null), false);
            if (â˜ƒ == null) {
               throw new Exception("Couldn't get profile");
            } else {
               return â˜ƒ;
            }
         }
      });
   private static final int MINUTES = 60;
   private static final int HOURS = 3600;
   private static final int DAYS = 86400;

   public static String uuidToName(String var0) throws Exception {
      GameProfile â˜ƒ = gameProfileCache.get(â˜ƒ);
      return â˜ƒ.getName();
   }

   public static Map<Type, MinecraftProfileTexture> getTextures(String var0) {
      try {
         GameProfile â˜ƒ = gameProfileCache.get(â˜ƒ);
         return SESSION_SERVICE.getTextures(â˜ƒ, false);
      } catch (Exception var2) {
         return Maps.newHashMap();
      }
   }

   public static String convertToAgePresentation(long var0) {
      if (â˜ƒ < 0L) {
         return "right now";
      } else {
         long â˜ƒ = â˜ƒ / 1000L;
         if (â˜ƒ < 60L) {
            return (â˜ƒ == 1L ? "1 second" : â˜ƒ + " seconds") + " ago";
         } else if (â˜ƒ < 3600L) {
            long â˜ƒ = â˜ƒ / 60L;
            return (â˜ƒ == 1L ? "1 minute" : â˜ƒ + " minutes") + " ago";
         } else if (â˜ƒ < 86400L) {
            long â˜ƒ = â˜ƒ / 3600L;
            return (â˜ƒ == 1L ? "1 hour" : â˜ƒ + " hours") + " ago";
         } else {
            long â˜ƒ = â˜ƒ / 86400L;
            return (â˜ƒ == 1L ? "1 day" : â˜ƒ + " days") + " ago";
         }
      }
   }

   public static String convertToAgePresentationFromInstant(Date var0) {
      return convertToAgePresentation(System.currentTimeMillis() - â˜ƒ.getTime());
   }
}
