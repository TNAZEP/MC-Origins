package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.realmsclient.Unit;
import com.mojang.realmsclient.client.FileDownload;
import com.mojang.realmsclient.dto.WorldDownload;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsDownloadLatestWorldScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ReentrantLock DOWNLOAD_LOCK = new ReentrantLock();
   private final Screen lastScreen;
   private final WorldDownload worldDownload;
   private final Component downloadTitle;
   private final RateLimiter narrationRateLimiter;
   private Button cancelButton;
   private final String worldName;
   private final RealmsDownloadLatestWorldScreen.DownloadStatus downloadStatus;
   private volatile Component errorMessage;
   private volatile Component status = new TranslatableComponent("mco.download.preparing");
   private volatile String progress;
   private volatile boolean cancelled;
   private volatile boolean showDots = true;
   private volatile boolean finished;
   private volatile boolean extracting;
   private Long previousWrittenBytes;
   private Long previousTimeSnapshot;
   private long bytesPersSecond;
   private int animTick;
   private static final String[] DOTS = new String[]{"", ".", ". .", ". . ."};
   private int dotIndex;
   private boolean checked;
   private final BooleanConsumer callback;

   public RealmsDownloadLatestWorldScreen(Screen var1, WorldDownload var2, String var3, BooleanConsumer var4) {
      super(NarratorChatListener.NO_TITLE);
      this.callback = â˜ƒ;
      this.lastScreen = â˜ƒ;
      this.worldName = â˜ƒ;
      this.worldDownload = â˜ƒ;
      this.downloadStatus = new RealmsDownloadLatestWorldScreen.DownloadStatus();
      this.downloadTitle = new TranslatableComponent("mco.download.title");
      this.narrationRateLimiter = RateLimiter.create(0.1F);
   }

   @Override
   public void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.cancelButton = this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 42, 200, 20, CommonComponents.GUI_CANCEL, var1 -> {
         this.cancelled = true;
         this.backButtonClicked();
      }));
      this.checkDownloadSize();
   }

   private void checkDownloadSize() {
      if (!this.finished) {
         if (!this.checked && this.getContentLength(this.worldDownload.downloadLink) >= 5368709120L) {
            Component â˜ƒ = new TranslatableComponent("mco.download.confirmation.line1", Unit.humanReadable(5368709120L));
            Component â˜ƒx = new TranslatableComponent("mco.download.confirmation.line2");
            this.minecraft.setScreen(new RealmsLongConfirmationScreen(var1x -> {
               this.checked = true;
               this.minecraft.setScreen(this);
               this.downloadSave();
            }, RealmsLongConfirmationScreen.Type.Warning, â˜ƒ, â˜ƒx, false));
         } else {
            this.downloadSave();
         }
      }
   }

   private long getContentLength(String var1) {
      FileDownload â˜ƒ = new FileDownload();
      return â˜ƒ.contentLength(â˜ƒ);
   }

   @Override
   public void tick() {
      super.tick();
      ++this.animTick;
      if (this.status != null && this.narrationRateLimiter.tryAcquire(1)) {
         Component â˜ƒ = this.createProgressNarrationMessage();
         NarratorChatListener.INSTANCE.sayNow(â˜ƒ);
      }
   }

   private Component createProgressNarrationMessage() {
      List<Component> â˜ƒ = Lists.<Component>newArrayList();
      â˜ƒ.add(this.downloadTitle);
      â˜ƒ.add(this.status);
      if (this.progress != null) {
         â˜ƒ.add(new TextComponent(this.progress + "%"));
         â˜ƒ.add(new TextComponent(Unit.humanReadable(this.bytesPersSecond) + "/s"));
      }

      if (this.errorMessage != null) {
         â˜ƒ.add(this.errorMessage);
      }

      return CommonComponents.joinLines(â˜ƒ);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.cancelled = true;
         this.backButtonClicked();
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void backButtonClicked() {
      if (this.finished && this.callback != null && this.errorMessage == null) {
         this.callback.accept(true);
      }

      this.minecraft.setScreen(this.lastScreen);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.downloadTitle, this.width / 2, 20, 16777215);
      drawCenteredString(â˜ƒ, this.font, this.status, this.width / 2, 50, 16777215);
      if (this.showDots) {
         this.drawDots(â˜ƒ);
      }

      if (this.downloadStatus.bytesWritten != 0L && !this.cancelled) {
         this.drawProgressBar(â˜ƒ);
         this.drawDownloadSpeed(â˜ƒ);
      }

      if (this.errorMessage != null) {
         drawCenteredString(â˜ƒ, this.font, this.errorMessage, this.width / 2, 110, 16711680);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void drawDots(PoseStack var1) {
      int â˜ƒ = this.font.width(this.status);
      if (this.animTick % 10 == 0) {
         ++this.dotIndex;
      }

      this.font.draw(â˜ƒ, DOTS[this.dotIndex % DOTS.length], (float)(this.width / 2 + â˜ƒ / 2 + 5), 50.0F, 16777215);
   }

   private void drawProgressBar(PoseStack var1) {
      double â˜ƒ = Math.min((double)this.downloadStatus.bytesWritten / (double)this.downloadStatus.totalBytes, 1.0);
      this.progress = String.format(Locale.ROOT, "%.1f", â˜ƒ * 100.0);
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableTexture();
      Tesselator â˜ƒx = Tesselator.getInstance();
      BufferBuilder â˜ƒxx = â˜ƒx.getBuilder();
      â˜ƒxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      double â˜ƒxxx = (double)(this.width / 2 - 100);
      double â˜ƒxxxx = 0.5;
      â˜ƒxx.vertex(â˜ƒxxx - 0.5, 95.5, 0.0).color(217, 210, 210, 255).endVertex();
      â˜ƒxx.vertex(â˜ƒxxx + 200.0 * â˜ƒ + 0.5, 95.5, 0.0).color(217, 210, 210, 255).endVertex();
      â˜ƒxx.vertex(â˜ƒxxx + 200.0 * â˜ƒ + 0.5, 79.5, 0.0).color(217, 210, 210, 255).endVertex();
      â˜ƒxx.vertex(â˜ƒxxx - 0.5, 79.5, 0.0).color(217, 210, 210, 255).endVertex();
      â˜ƒxx.vertex(â˜ƒxxx, 95.0, 0.0).color(128, 128, 128, 255).endVertex();
      â˜ƒxx.vertex(â˜ƒxxx + 200.0 * â˜ƒ, 95.0, 0.0).color(128, 128, 128, 255).endVertex();
      â˜ƒxx.vertex(â˜ƒxxx + 200.0 * â˜ƒ, 80.0, 0.0).color(128, 128, 128, 255).endVertex();
      â˜ƒxx.vertex(â˜ƒxxx, 80.0, 0.0).color(128, 128, 128, 255).endVertex();
      â˜ƒx.end();
      RenderSystem.enableTexture();
      drawCenteredString(â˜ƒ, this.font, this.progress + " %", this.width / 2, 84, 16777215);
   }

   private void drawDownloadSpeed(PoseStack var1) {
      if (this.animTick % 20 == 0) {
         if (this.previousWrittenBytes != null) {
            long â˜ƒ = Util.getMillis() - this.previousTimeSnapshot;
            if (â˜ƒ == 0L) {
               â˜ƒ = 1L;
            }

            this.bytesPersSecond = 1000L * (this.downloadStatus.bytesWritten - this.previousWrittenBytes) / â˜ƒ;
            this.drawDownloadSpeed0(â˜ƒ, this.bytesPersSecond);
         }

         this.previousWrittenBytes = this.downloadStatus.bytesWritten;
         this.previousTimeSnapshot = Util.getMillis();
      } else {
         this.drawDownloadSpeed0(â˜ƒ, this.bytesPersSecond);
      }
   }

   private void drawDownloadSpeed0(PoseStack var1, long var2) {
      if (â˜ƒ > 0L) {
         int â˜ƒ = this.font.width(this.progress);
         String â˜ƒx = "(" + Unit.humanReadable(â˜ƒ) + "/s)";
         this.font.draw(â˜ƒ, â˜ƒx, (float)(this.width / 2 + â˜ƒ / 2 + 15), 84.0F, 16777215);
      }
   }

   private void downloadSave() {
      new Thread(() -> {
         try {
            try {
               if (!DOWNLOAD_LOCK.tryLock(1L, TimeUnit.SECONDS)) {
                  this.status = new TranslatableComponent("mco.download.failed");
                  return;
               }

               if (this.cancelled) {
                  this.downloadCancelled();
                  return;
               }

               this.status = new TranslatableComponent("mco.download.downloading", this.worldName);
               FileDownload â˜ƒ = new FileDownload();
               â˜ƒ.contentLength(this.worldDownload.downloadLink);
               â˜ƒ.download(this.worldDownload, this.worldName, this.downloadStatus, this.minecraft.getLevelSource());

               while(!â˜ƒ.isFinished()) {
                  if (â˜ƒ.isError()) {
                     â˜ƒ.cancel();
                     this.errorMessage = new TranslatableComponent("mco.download.failed");
                     this.cancelButton.setMessage(CommonComponents.GUI_DONE);
                     return;
                  }

                  if (â˜ƒ.isExtracting()) {
                     if (!this.extracting) {
                        this.status = new TranslatableComponent("mco.download.extracting");
                     }

                     this.extracting = true;
                  }

                  if (this.cancelled) {
                     â˜ƒ.cancel();
                     this.downloadCancelled();
                     return;
                  }

                  try {
                     Thread.sleep(500L);
                  } catch (InterruptedException var8) {
                     LOGGER.error("Failed to check Realms backup download status");
                  }
               }

               this.finished = true;
               this.status = new TranslatableComponent("mco.download.done");
               this.cancelButton.setMessage(CommonComponents.GUI_DONE);
               return;
            } catch (InterruptedException var9) {
               LOGGER.error("Could not acquire upload lock");
            } catch (Exception var10) {
               this.errorMessage = new TranslatableComponent("mco.download.failed");
               var10.printStackTrace();
            }
         } finally {
            if (!DOWNLOAD_LOCK.isHeldByCurrentThread()) {
               return;
            } else {
               DOWNLOAD_LOCK.unlock();
               this.showDots = false;
               this.finished = true;
            }
         }
      }).start();
   }

   private void downloadCancelled() {
      this.status = new TranslatableComponent("mco.download.cancelled");
   }

   public class DownloadStatus {
      public volatile long bytesWritten;
      public volatile long totalBytes;
   }
}
