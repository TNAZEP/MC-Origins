package net.minecraft.client.renderer.texture;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.RealmsMainScreen;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager implements PreparableReloadListener, Tickable, AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final ResourceLocation INTENTIONAL_MISSING_TEXTURE = new ResourceLocation("");
   private final Map<ResourceLocation, AbstractTexture> byPath = Maps.<ResourceLocation, AbstractTexture>newHashMap();
   private final Set<Tickable> tickableTextures = Sets.<Tickable>newHashSet();
   private final Map<String, Integer> prefixRegister = Maps.newHashMap();
   private final ResourceManager resourceManager;

   public TextureManager(ResourceManager var1) {
      this.resourceManager = â˜ƒ;
   }

   public void bindForSetup(ResourceLocation var1) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this._bind(â˜ƒ));
      } else {
         this._bind(â˜ƒ);
      }
   }

   private void _bind(ResourceLocation var1) {
      AbstractTexture â˜ƒ = (AbstractTexture)this.byPath.get(â˜ƒ);
      if (â˜ƒ == null) {
         â˜ƒ = new SimpleTexture(â˜ƒ);
         this.register(â˜ƒ, â˜ƒ);
      }

      â˜ƒ.bind();
   }

   public void register(ResourceLocation var1, AbstractTexture var2) {
      â˜ƒ = this.loadTexture(â˜ƒ, â˜ƒ);
      AbstractTexture â˜ƒ = (AbstractTexture)this.byPath.put(â˜ƒ, â˜ƒ);
      if (â˜ƒ != â˜ƒ) {
         if (â˜ƒ != null && â˜ƒ != MissingTextureAtlasSprite.getTexture()) {
            this.tickableTextures.remove(â˜ƒ);
            this.safeClose(â˜ƒ, â˜ƒ);
         }

         if (â˜ƒ instanceof Tickable) {
            this.tickableTextures.add((Tickable)â˜ƒ);
         }
      }
   }

   private void safeClose(ResourceLocation var1, AbstractTexture var2) {
      if (â˜ƒ != MissingTextureAtlasSprite.getTexture()) {
         try {
            â˜ƒ.close();
         } catch (Exception var4) {
            LOGGER.warn("Failed to close texture {}", â˜ƒ, var4);
         }
      }

      â˜ƒ.releaseId();
   }

   private AbstractTexture loadTexture(ResourceLocation var1, AbstractTexture var2) {
      try {
         â˜ƒ.load(this.resourceManager);
         return â˜ƒ;
      } catch (IOException var6) {
         if (â˜ƒ != INTENTIONAL_MISSING_TEXTURE) {
            LOGGER.warn("Failed to load texture: {}", â˜ƒ, var6);
         }

         return MissingTextureAtlasSprite.getTexture();
      } catch (Throwable var7) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var7, "Registering texture");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Resource location being registered");
         â˜ƒx.setDetail("Resource location", â˜ƒ);
         â˜ƒx.setDetail("Texture object class", (CrashReportDetail<String>)(() -> â˜ƒ.getClass().getName()));
         throw new ReportedException(â˜ƒ);
      }
   }

   public AbstractTexture getTexture(ResourceLocation var1) {
      AbstractTexture â˜ƒ = (AbstractTexture)this.byPath.get(â˜ƒ);
      if (â˜ƒ == null) {
         â˜ƒ = new SimpleTexture(â˜ƒ);
         this.register(â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   public AbstractTexture getTexture(ResourceLocation var1, AbstractTexture var2) {
      return (AbstractTexture)this.byPath.getOrDefault(â˜ƒ, â˜ƒ);
   }

   public ResourceLocation register(String var1, DynamicTexture var2) {
      Integer â˜ƒ = (Integer)this.prefixRegister.get(â˜ƒ);
      if (â˜ƒ == null) {
         â˜ƒ = 1;
      } else {
         â˜ƒ = â˜ƒ + 1;
      }

      this.prefixRegister.put(â˜ƒ, â˜ƒ);
      ResourceLocation â˜ƒ = new ResourceLocation(String.format(Locale.ROOT, "dynamic/%s_%d", â˜ƒ, â˜ƒ));
      this.register(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public CompletableFuture<Void> preload(ResourceLocation var1, Executor var2) {
      if (!this.byPath.containsKey(â˜ƒ)) {
         PreloadedTexture â˜ƒ = new PreloadedTexture(this.resourceManager, â˜ƒ, â˜ƒ);
         this.byPath.put(â˜ƒ, â˜ƒ);
         return â˜ƒ.getFuture().thenRunAsync(() -> this.register(â˜ƒ, â˜ƒ), TextureManager::execute);
      } else {
         return CompletableFuture.completedFuture(null);
      }
   }

   private static void execute(Runnable var0) {
      Minecraft.getInstance().execute(() -> RenderSystem.recordRenderCall(â˜ƒ::run));
   }

   @Override
   public void tick() {
      for(Tickable â˜ƒ : this.tickableTextures) {
         â˜ƒ.tick();
      }
   }

   public void release(ResourceLocation var1) {
      AbstractTexture â˜ƒ = this.getTexture(â˜ƒ, MissingTextureAtlasSprite.getTexture());
      if (â˜ƒ != MissingTextureAtlasSprite.getTexture()) {
         TextureUtil.releaseTextureId(â˜ƒ.getId());
      }
   }

   public void close() {
      this.byPath.forEach(this::safeClose);
      this.byPath.clear();
      this.tickableTextures.clear();
      this.prefixRegister.clear();
   }

   @Override
   public CompletableFuture<Void> reload(
      PreparableReloadListener.PreparationBarrier var1, ResourceManager var2, ProfilerFiller var3, ProfilerFiller var4, Executor var5, Executor var6
   ) {
      return CompletableFuture.allOf(TitleScreen.preloadResources(this, â˜ƒ), this.preload(AbstractWidget.WIDGETS_LOCATION, â˜ƒ))
         .thenCompose(â˜ƒ::wait)
         .thenAcceptAsync(var3x -> {
            MissingTextureAtlasSprite.getTexture();
            RealmsMainScreen.updateTeaserImages(this.resourceManager);
            Iterator<Entry<ResourceLocation, AbstractTexture>> â˜ƒ = this.byPath.entrySet().iterator();
   
            while(â˜ƒ.hasNext()) {
               Entry<ResourceLocation, AbstractTexture> â˜ƒx = (Entry)â˜ƒ.next();
               ResourceLocation â˜ƒxx = (ResourceLocation)â˜ƒx.getKey();
               AbstractTexture â˜ƒxxx = (AbstractTexture)â˜ƒx.getValue();
               if (â˜ƒxxx == MissingTextureAtlasSprite.getTexture() && !â˜ƒxx.equals(MissingTextureAtlasSprite.getLocation())) {
                  â˜ƒ.remove();
               } else {
                  â˜ƒxxx.reset(this, â˜ƒ, â˜ƒxx, â˜ƒ);
               }
            }
         }, var0 -> RenderSystem.recordRenderCall(var0::run));
   }
}
