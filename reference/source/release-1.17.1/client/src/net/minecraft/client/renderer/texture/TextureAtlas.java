package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.PngInfo;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.InventoryMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureAtlas extends AbstractTexture implements Tickable {
   private static final Logger LOGGER = LogManager.getLogger();
   @Deprecated
   public static final ResourceLocation LOCATION_BLOCKS = InventoryMenu.BLOCK_ATLAS;
   @Deprecated
   public static final ResourceLocation LOCATION_PARTICLES = new ResourceLocation("textures/atlas/particles.png");
   private static final String FILE_EXTENSION = ".png";
   private final List<Tickable> animatedTextures = Lists.<Tickable>newArrayList();
   private final Set<ResourceLocation> sprites = Sets.<ResourceLocation>newHashSet();
   private final Map<ResourceLocation, TextureAtlasSprite> texturesByName = Maps.<ResourceLocation, TextureAtlasSprite>newHashMap();
   private final ResourceLocation location;
   private final int maxSupportedTextureSize;

   public TextureAtlas(ResourceLocation var1) {
      this.location = â˜ƒ;
      this.maxSupportedTextureSize = RenderSystem.maxSupportedTextureSize();
   }

   @Override
   public void load(ResourceManager var1) {
   }

   public void reload(TextureAtlas.Preparations var1) {
      this.sprites.clear();
      this.sprites.addAll(â˜ƒ.sprites);
      LOGGER.info("Created: {}x{}x{} {}-atlas", â˜ƒ.width, â˜ƒ.height, â˜ƒ.mipLevel, this.location);
      TextureUtil.prepareImage(this.getId(), â˜ƒ.mipLevel, â˜ƒ.width, â˜ƒ.height);
      this.clearTextureData();

      for(TextureAtlasSprite â˜ƒ : â˜ƒ.regions) {
         this.texturesByName.put(â˜ƒ.getName(), â˜ƒ);

         try {
            â˜ƒ.uploadFirstFrame();
         } catch (Throwable var7) {
            CrashReport â˜ƒx = CrashReport.forThrowable(var7, "Stitching texture atlas");
            CrashReportCategory â˜ƒxx = â˜ƒx.addCategory("Texture being stitched together");
            â˜ƒxx.setDetail("Atlas path", this.location);
            â˜ƒxx.setDetail("Sprite", â˜ƒ);
            throw new ReportedException(â˜ƒx);
         }

         Tickable â˜ƒx = â˜ƒ.getAnimationTicker();
         if (â˜ƒx != null) {
            this.animatedTextures.add(â˜ƒx);
         }
      }
   }

   public TextureAtlas.Preparations prepareToStitch(ResourceManager var1, Stream<ResourceLocation> var2, ProfilerFiller var3, int var4) {
      â˜ƒ.push("preparing");
      Set<ResourceLocation> â˜ƒ = (Set)â˜ƒ.peek(var0 -> {
         if (var0 == null) {
            throw new IllegalArgumentException("Location cannot be null!");
         }
      }).collect(Collectors.toSet());
      int â˜ƒx = this.maxSupportedTextureSize;
      Stitcher â˜ƒxx = new Stitcher(â˜ƒx, â˜ƒx, â˜ƒ);
      int â˜ƒxxx = Integer.MAX_VALUE;
      int â˜ƒxxxx = 1 << â˜ƒ;
      â˜ƒ.popPush("extracting_frames");

      for(TextureAtlasSprite.Info â˜ƒxxxxx : this.getBasicSpriteInfos(â˜ƒ, â˜ƒ)) {
         â˜ƒxxx = Math.min(â˜ƒxxx, Math.min(â˜ƒxxxxx.width(), â˜ƒxxxxx.height()));
         int â˜ƒxxxxxx = Math.min(Integer.lowestOneBit(â˜ƒxxxxx.width()), Integer.lowestOneBit(â˜ƒxxxxx.height()));
         if (â˜ƒxxxxxx < â˜ƒxxxx) {
            LOGGER.warn(
               "Texture {} with size {}x{} limits mip level from {} to {}",
               â˜ƒxxxxx.name(),
               â˜ƒxxxxx.width(),
               â˜ƒxxxxx.height(),
               Mth.log2(â˜ƒxxxx),
               Mth.log2(â˜ƒxxxxxx)
            );
            â˜ƒxxxx = â˜ƒxxxxxx;
         }

         â˜ƒxx.registerSprite(â˜ƒxxxxx);
      }

      int â˜ƒxxxxxx = Math.min(â˜ƒxxx, â˜ƒxxxx);
      int â˜ƒxxxxxxx = Mth.log2(â˜ƒxxxxxx);
      int â˜ƒxxxxx;
      if (â˜ƒxxxxxxx < â˜ƒ) {
         LOGGER.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.location, â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxxx);
         â˜ƒxxxxx = â˜ƒxxxxxxx;
      } else {
         â˜ƒxxxxx = â˜ƒ;
      }

      â˜ƒ.popPush("register");
      â˜ƒxx.registerSprite(MissingTextureAtlasSprite.info());
      â˜ƒ.popPush("stitching");

      try {
         â˜ƒxx.stitch();
      } catch (StitcherException var16) {
         CrashReport â˜ƒxxxxx = CrashReport.forThrowable(var16, "Stitching");
         CrashReportCategory â˜ƒxxxxxx = â˜ƒxxxxx.addCategory("Stitcher");
         â˜ƒxxxxxx.setDetail(
            "Sprites",
            var16.getAllSprites().stream().map(var0 -> String.format("%s[%dx%d]", var0.name(), var0.width(), var0.height())).collect(Collectors.joining(","))
         );
         â˜ƒxxxxxx.setDetail("Max Texture Size", â˜ƒx);
         throw new ReportedException(â˜ƒxxxxx);
      }

      â˜ƒ.popPush("loading");
      List<TextureAtlasSprite> â˜ƒxxxxx = this.getLoadedSprites(â˜ƒ, â˜ƒxx, â˜ƒxxxxx);
      â˜ƒ.pop();
      return new TextureAtlas.Preparations(â˜ƒ, â˜ƒxx.getWidth(), â˜ƒxx.getHeight(), â˜ƒxxxxx, â˜ƒxxxxx);
   }

   private Collection<TextureAtlasSprite.Info> getBasicSpriteInfos(ResourceManager var1, Set<ResourceLocation> var2) {
      List<CompletableFuture<?>> â˜ƒ = Lists.newArrayList();
      Queue<TextureAtlasSprite.Info> â˜ƒx = new ConcurrentLinkedQueue();

      for(ResourceLocation â˜ƒxx : â˜ƒ) {
         if (!MissingTextureAtlasSprite.getLocation().equals(â˜ƒxx)) {
            â˜ƒ.add(CompletableFuture.runAsync(() -> {
               ResourceLocation â˜ƒ = this.getResourceLocation(â˜ƒ);

               TextureAtlasSprite.Info â˜ƒ;
               try {
                  Resource â˜ƒx = â˜ƒ.getResource(â˜ƒ);

                  try {
                     PngInfo â˜ƒxx = new PngInfo(â˜ƒx.toString(), â˜ƒx.getInputStream());
                     AnimationMetadataSection â˜ƒxxx = â˜ƒx.getMetadata(AnimationMetadataSection.SERIALIZER);
                     if (â˜ƒxxx == null) {
                        â˜ƒxxx = AnimationMetadataSection.EMPTY;
                     }

                     Pair<Integer, Integer> â˜ƒxx = â˜ƒxxx.getFrameSize(â˜ƒxx.width, â˜ƒxx.height);
                     â˜ƒ = new TextureAtlasSprite.Info(â˜ƒ, â˜ƒxx.getFirst(), â˜ƒxx.getSecond(), â˜ƒxxx);
                  } catch (Throwable var11) {
                     if (â˜ƒx != null) {
                        try {
                           â˜ƒx.close();
                        } catch (Throwable var10) {
                           var11.addSuppressed(var10);
                        }
                     }

                     throw var11;
                  }

                  if (â˜ƒx != null) {
                     â˜ƒx.close();
                  }
               } catch (RuntimeException var12) {
                  LOGGER.error("Unable to parse metadata from {} : {}", â˜ƒ, var12);
                  return;
               } catch (IOException var13) {
                  LOGGER.error("Using missing texture, unable to load {} : {}", â˜ƒ, var13);
                  return;
               }

               â˜ƒ.add(â˜ƒ);
            }, Util.backgroundExecutor()));
         }
      }

      CompletableFuture.allOf((CompletableFuture[])â˜ƒ.toArray(new CompletableFuture[0])).join();
      return â˜ƒx;
   }

   private List<TextureAtlasSprite> getLoadedSprites(ResourceManager var1, Stitcher var2, int var3) {
      Queue<TextureAtlasSprite> â˜ƒ = new ConcurrentLinkedQueue();
      List<CompletableFuture<?>> â˜ƒx = Lists.newArrayList();
      â˜ƒ.gatherSprites((var5x, var6, var7, var8, var9) -> {
         if (var5x == MissingTextureAtlasSprite.info()) {
            MissingTextureAtlasSprite â˜ƒ = MissingTextureAtlasSprite.newInstance(this, â˜ƒ, var6, var7, var8, var9);
            â˜ƒ.add(â˜ƒ);
         } else {
            â˜ƒ.add(CompletableFuture.runAsync(() -> {
               TextureAtlasSprite â˜ƒ = this.load(â˜ƒ, var5x, var6, var7, â˜ƒ, var8, var9);
               if (â˜ƒ != null) {
                  â˜ƒ.add(â˜ƒ);
               }
            }, Util.backgroundExecutor()));
         }
      });
      CompletableFuture.allOf((CompletableFuture[])â˜ƒx.toArray(new CompletableFuture[0])).join();
      return Lists.<TextureAtlasSprite>newArrayList(â˜ƒ);
   }

   @Nullable
   private TextureAtlasSprite load(ResourceManager var1, TextureAtlasSprite.Info var2, int var3, int var4, int var5, int var6, int var7) {
      ResourceLocation â˜ƒ = this.getResourceLocation(â˜ƒ.name());

      try {
         Resource â˜ƒx = â˜ƒ.getResource(â˜ƒ);

         TextureAtlasSprite var11;
         try {
            NativeImage â˜ƒxx = NativeImage.read(â˜ƒx.getInputStream());
            var11 = new TextureAtlasSprite(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx);
         } catch (Throwable var13) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var12) {
                  var13.addSuppressed(var12);
               }
            }

            throw var13;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }

         return var11;
      } catch (RuntimeException var14) {
         LOGGER.error("Unable to parse metadata from {}", â˜ƒ, var14);
         return null;
      } catch (IOException var15) {
         LOGGER.error("Using missing texture, unable to load {}", â˜ƒ, var15);
         return null;
      }
   }

   private ResourceLocation getResourceLocation(ResourceLocation var1) {
      return new ResourceLocation(â˜ƒ.getNamespace(), String.format("textures/%s%s", â˜ƒ.getPath(), ".png"));
   }

   public void cycleAnimationFrames() {
      this.bind();

      for(Tickable â˜ƒ : this.animatedTextures) {
         â˜ƒ.tick();
      }
   }

   @Override
   public void tick() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::cycleAnimationFrames);
      } else {
         this.cycleAnimationFrames();
      }
   }

   public TextureAtlasSprite getSprite(ResourceLocation var1) {
      TextureAtlasSprite â˜ƒ = (TextureAtlasSprite)this.texturesByName.get(â˜ƒ);
      return â˜ƒ == null ? (TextureAtlasSprite)this.texturesByName.get(MissingTextureAtlasSprite.getLocation()) : â˜ƒ;
   }

   public void clearTextureData() {
      for(TextureAtlasSprite â˜ƒ : this.texturesByName.values()) {
         â˜ƒ.close();
      }

      this.texturesByName.clear();
      this.animatedTextures.clear();
   }

   public ResourceLocation location() {
      return this.location;
   }

   public void updateFilter(TextureAtlas.Preparations var1) {
      this.setFilter(false, â˜ƒ.mipLevel > 0);
   }

   public static class Preparations {
      final Set<ResourceLocation> sprites;
      final int width;
      final int height;
      final int mipLevel;
      final List<TextureAtlasSprite> regions;

      public Preparations(Set<ResourceLocation> var1, int var2, int var3, int var4, List<TextureAtlasSprite> var5) {
         this.sprites = â˜ƒ;
         this.width = â˜ƒ;
         this.height = â˜ƒ;
         this.mipLevel = â˜ƒ;
         this.regions = â˜ƒ;
      }
   }
}
