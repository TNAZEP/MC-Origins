package net.minecraft.client.sounds;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.audio.OggAudioStream;
import com.mojang.blaze3d.audio.SoundBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.minecraft.Util;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

public class SoundBufferLibrary {
   private final ResourceManager resourceManager;
   private final Map<ResourceLocation, CompletableFuture<SoundBuffer>> cache = Maps.newHashMap();

   public SoundBufferLibrary(ResourceManager var1) {
      this.resourceManager = â˜ƒ;
   }

   public CompletableFuture<SoundBuffer> getCompleteBuffer(ResourceLocation var1) {
      return (CompletableFuture<SoundBuffer>)this.cache.computeIfAbsent(â˜ƒ, var1x -> CompletableFuture.supplyAsync(() -> {
            try {
               Resource â˜ƒ = this.resourceManager.getResource(var1x);

               SoundBuffer var6;
               try {
                  InputStream â˜ƒx = â˜ƒ.getInputStream();

                  try {
                     OggAudioStream â˜ƒxx = new OggAudioStream(â˜ƒx);

                     try {
                        ByteBuffer â˜ƒxxx = â˜ƒxx.readAll();
                        var6 = new SoundBuffer(â˜ƒxxx, â˜ƒxx.getFormat());
                     } catch (Throwable var10) {
                        try {
                           â˜ƒxx.close();
                        } catch (Throwable var9) {
                           var10.addSuppressed(var9);
                        }

                        throw var10;
                     }

                     â˜ƒxx.close();
                  } catch (Throwable var11) {
                     if (â˜ƒx != null) {
                        try {
                           â˜ƒx.close();
                        } catch (Throwable var8) {
                           var11.addSuppressed(var8);
                        }
                     }

                     throw var11;
                  }

                  if (â˜ƒx != null) {
                     â˜ƒx.close();
                  }
               } catch (Throwable var12) {
                  if (â˜ƒ != null) {
                     try {
                        â˜ƒ.close();
                     } catch (Throwable var7) {
                        var12.addSuppressed(var7);
                     }
                  }

                  throw var12;
               }

               if (â˜ƒ != null) {
                  â˜ƒ.close();
               }

               return var6;
            } catch (IOException var13) {
               throw new CompletionException(var13);
            }
         }, Util.backgroundExecutor()));
   }

   public CompletableFuture<AudioStream> getStream(ResourceLocation var1, boolean var2) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            Resource â˜ƒ = this.resourceManager.getResource(â˜ƒ);
            InputStream â˜ƒx = â˜ƒ.getInputStream();
            return (AudioStream)(â˜ƒ ? new LoopingAudioStream(OggAudioStream::new, â˜ƒx) : new OggAudioStream(â˜ƒx));
         } catch (IOException var5) {
            throw new CompletionException(var5);
         }
      }, Util.backgroundExecutor());
   }

   public void clear() {
      this.cache.values().forEach(var0 -> var0.thenAccept(SoundBuffer::discardAlBuffer));
      this.cache.clear();
   }

   public CompletableFuture<?> preload(Collection<Sound> var1) {
      return CompletableFuture.allOf(
         (CompletableFuture[])â˜ƒ.stream().map(var1x -> this.getCompleteBuffer(var1x.getPath())).toArray(var0 -> new CompletableFuture[var0])
      );
   }
}
