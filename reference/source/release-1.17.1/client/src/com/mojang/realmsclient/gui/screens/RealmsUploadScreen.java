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
import com.mojang.realmsclient.client.FileUpload;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.client.UploadStatus;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.util.UploadTokenCache;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPOutputStream;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsUploadScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ReentrantLock UPLOAD_LOCK = new ReentrantLock();
   private static final String[] DOTS = new String[]{"", ".", ". .", ". . ."};
   private static final Component VERIFYING_TEXT = new TranslatableComponent("mco.upload.verifying");
   private final RealmsResetWorldScreen lastScreen;
   private final LevelSummary selectedLevel;
   private final long worldId;
   private final int slotId;
   private final UploadStatus uploadStatus;
   private final RateLimiter narrationRateLimiter;
   private volatile Component[] errorMessage;
   private volatile Component status = new TranslatableComponent("mco.upload.preparing");
   private volatile String progress;
   private volatile boolean cancelled;
   private volatile boolean uploadFinished;
   private volatile boolean showDots = true;
   private volatile boolean uploadStarted;
   private Button backButton;
   private Button cancelButton;
   private int tickCount;
   private Long previousWrittenBytes;
   private Long previousTimeSnapshot;
   private long bytesPersSecond;
   private final Runnable callback;

   public RealmsUploadScreen(long var1, int var3, RealmsResetWorldScreen var4, LevelSummary var5, Runnable var6) {
      super(NarratorChatListener.NO_TITLE);
      this.worldId = â˜ƒ;
      this.slotId = â˜ƒ;
      this.lastScreen = â˜ƒ;
      this.selectedLevel = â˜ƒ;
      this.uploadStatus = new UploadStatus();
      this.narrationRateLimiter = RateLimiter.create(0.1F);
      this.callback = â˜ƒ;
   }

   @Override
   public void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.backButton = this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 42, 200, 20, CommonComponents.GUI_BACK, var1 -> this.onBack()));
      this.backButton.visible = false;
      this.cancelButton = this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height - 42, 200, 20, CommonComponents.GUI_CANCEL, var1 -> this.onCancel())
      );
      if (!this.uploadStarted) {
         if (this.lastScreen.slot == -1) {
            this.upload();
         } else {
            this.lastScreen.switchSlot(() -> {
               if (!this.uploadStarted) {
                  this.uploadStarted = true;
                  this.minecraft.setScreen(this);
                  this.upload();
               }
            });
         }
      }
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   private void onBack() {
      this.callback.run();
   }

   private void onCancel() {
      this.cancelled = true;
      this.minecraft.setScreen(this.lastScreen);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         if (this.showDots) {
            this.onCancel();
         } else {
            this.onBack();
         }

         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      if (!this.uploadFinished && this.uploadStatus.bytesWritten != 0L && this.uploadStatus.bytesWritten == this.uploadStatus.totalBytes) {
         this.status = VERIFYING_TEXT;
         this.cancelButton.active = false;
      }

      drawCenteredString(â˜ƒ, this.font, this.status, this.width / 2, 50, 16777215);
      if (this.showDots) {
         this.drawDots(â˜ƒ);
      }

      if (this.uploadStatus.bytesWritten != 0L && !this.cancelled) {
         this.drawProgressBar(â˜ƒ);
         this.drawUploadSpeed(â˜ƒ);
      }

      if (this.errorMessage != null) {
         for(int â˜ƒ = 0; â˜ƒ < this.errorMessage.length; ++â˜ƒ) {
            drawCenteredString(â˜ƒ, this.font, this.errorMessage[â˜ƒ], this.width / 2, 110 + 12 * â˜ƒ, 16711680);
         }
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void drawDots(PoseStack var1) {
      int â˜ƒ = this.font.width(this.status);
      this.font.draw(â˜ƒ, DOTS[this.tickCount / 10 % DOTS.length], (float)(this.width / 2 + â˜ƒ / 2 + 5), 50.0F, 16777215);
   }

   private void drawProgressBar(PoseStack var1) {
      double â˜ƒ = Math.min((double)this.uploadStatus.bytesWritten / (double)this.uploadStatus.totalBytes, 1.0);
      this.progress = String.format(Locale.ROOT, "%.1f", â˜ƒ * 100.0);
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableTexture();
      double â˜ƒx = (double)(this.width / 2 - 100);
      double â˜ƒxx = 0.5;
      Tesselator â˜ƒxxx = Tesselator.getInstance();
      BufferBuilder â˜ƒxxxx = â˜ƒxxx.getBuilder();
      â˜ƒxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      â˜ƒxxxx.vertex(â˜ƒx - 0.5, 95.5, 0.0).color(217, 210, 210, 255).endVertex();
      â˜ƒxxxx.vertex(â˜ƒx + 200.0 * â˜ƒ + 0.5, 95.5, 0.0).color(217, 210, 210, 255).endVertex();
      â˜ƒxxxx.vertex(â˜ƒx + 200.0 * â˜ƒ + 0.5, 79.5, 0.0).color(217, 210, 210, 255).endVertex();
      â˜ƒxxxx.vertex(â˜ƒx - 0.5, 79.5, 0.0).color(217, 210, 210, 255).endVertex();
      â˜ƒxxxx.vertex(â˜ƒx, 95.0, 0.0).color(128, 128, 128, 255).endVertex();
      â˜ƒxxxx.vertex(â˜ƒx + 200.0 * â˜ƒ, 95.0, 0.0).color(128, 128, 128, 255).endVertex();
      â˜ƒxxxx.vertex(â˜ƒx + 200.0 * â˜ƒ, 80.0, 0.0).color(128, 128, 128, 255).endVertex();
      â˜ƒxxxx.vertex(â˜ƒx, 80.0, 0.0).color(128, 128, 128, 255).endVertex();
      â˜ƒxxx.end();
      RenderSystem.enableTexture();
      drawCenteredString(â˜ƒ, this.font, this.progress + " %", this.width / 2, 84, 16777215);
   }

   private void drawUploadSpeed(PoseStack var1) {
      if (this.tickCount % 20 == 0) {
         if (this.previousWrittenBytes != null) {
            long â˜ƒ = Util.getMillis() - this.previousTimeSnapshot;
            if (â˜ƒ == 0L) {
               â˜ƒ = 1L;
            }

            this.bytesPersSecond = 1000L * (this.uploadStatus.bytesWritten - this.previousWrittenBytes) / â˜ƒ;
            this.drawUploadSpeed0(â˜ƒ, this.bytesPersSecond);
         }

         this.previousWrittenBytes = this.uploadStatus.bytesWritten;
         this.previousTimeSnapshot = Util.getMillis();
      } else {
         this.drawUploadSpeed0(â˜ƒ, this.bytesPersSecond);
      }
   }

   private void drawUploadSpeed0(PoseStack var1, long var2) {
      if (â˜ƒ > 0L) {
         int â˜ƒ = this.font.width(this.progress);
         String â˜ƒx = "(" + Unit.humanReadable(â˜ƒ) + "/s)";
         this.font.draw(â˜ƒ, â˜ƒx, (float)(this.width / 2 + â˜ƒ / 2 + 15), 84.0F, 16777215);
      }
   }

   @Override
   public void tick() {
      super.tick();
      ++this.tickCount;
      if (this.status != null && this.narrationRateLimiter.tryAcquire(1)) {
         Component â˜ƒ = this.createProgressNarrationMessage();
         NarratorChatListener.INSTANCE.sayNow(â˜ƒ);
      }
   }

   private Component createProgressNarrationMessage() {
      List<Component> â˜ƒ = Lists.<Component>newArrayList();
      â˜ƒ.add(this.status);
      if (this.progress != null) {
         â˜ƒ.add(new TextComponent(this.progress + "%"));
      }

      if (this.errorMessage != null) {
         â˜ƒ.addAll(Arrays.asList(this.errorMessage));
      }

      return CommonComponents.joinLines(â˜ƒ);
   }

   private void upload() {
      this.uploadStarted = true;
      new Thread(
            () -> {
               File â˜ƒ = null;
               RealmsClient â˜ƒx = RealmsClient.create();
               long â˜ƒxx = this.worldId;
      
               try {
                  UploadInfo â˜ƒ;
                  try {
                     if (!UPLOAD_LOCK.tryLock(1L, TimeUnit.SECONDS)) {
                        this.status = new TranslatableComponent("mco.upload.close.failure");
                     } else {
                        â˜ƒ = null;
      
                        int â˜ƒ;
                        for(â˜ƒ = 0; â˜ƒ < 20; ++â˜ƒ) {
                           try {
                              if (this.cancelled) {
                                 this.uploadCancelled();
                                 return;
                              }
      
                              â˜ƒ = â˜ƒx.requestUploadInfo(â˜ƒxx, UploadTokenCache.get(â˜ƒxx));
                              if (â˜ƒ != null) {
                                 break;
                              }
                           } catch (RetryCallException var20) {
                              Thread.sleep((long)(var20.delaySeconds * 1000));
                           }
                        }
      
                        if (â˜ƒ == null) {
                           this.status = new TranslatableComponent("mco.upload.close.failure");
                        } else {
                           UploadTokenCache.put(â˜ƒxx, â˜ƒ.getToken());
                           if (!â˜ƒ.isWorldClosed()) {
                              this.status = new TranslatableComponent("mco.upload.close.failure");
                           } else if (this.cancelled) {
                              this.uploadCancelled();
                           } else {
                              â˜ƒ = (int)(new File(this.minecraft.gameDirectory.getAbsolutePath(), "saves"));
                              â˜ƒ = this.tarGzipArchive(new File(â˜ƒ, this.selectedLevel.getLevelId()));
                              if (this.cancelled) {
                                 this.uploadCancelled();
                              } else if (this.verify(â˜ƒ)) {
                                 this.status = new TranslatableComponent("mco.upload.uploading", this.selectedLevel.getLevelName());
                                 FileUpload â˜ƒxxx = new FileUpload(
                                    â˜ƒ,
                                    this.worldId,
                                    this.slotId,
                                    â˜ƒ,
                                    this.minecraft.getUser(),
                                    SharedConstants.getCurrentVersion().getName(),
                                    this.uploadStatus
                                 );
                                 â˜ƒxxx.upload(var3x -> {
                                    if (var3x.statusCode >= 200 && var3x.statusCode < 300) {
                                       this.uploadFinished = true;
                                       this.status = new TranslatableComponent("mco.upload.done");
                                       this.backButton.setMessage(CommonComponents.GUI_DONE);
                                       UploadTokenCache.invalidate(â˜ƒ);
                                    } else if (var3x.statusCode == 400 && var3x.errorMessage != null) {
                                       this.setErrorMessage(new TranslatableComponent("mco.upload.failed", var3x.errorMessage));
                                    } else {
                                       this.setErrorMessage(new TranslatableComponent("mco.upload.failed", var3x.statusCode));
                                    }
                                 });
      
                                 while(!â˜ƒxxx.isFinished()) {
                                    if (this.cancelled) {
                                       â˜ƒxxx.cancel();
                                       this.uploadCancelled();
                                       return;
                                    }
      
                                    try {
                                       Thread.sleep(500L);
                                    } catch (InterruptedException var19) {
                                       LOGGER.error("Failed to check Realms file upload status");
                                    }
                                 }
                              } else {
                                 long â˜ƒxxx = â˜ƒ.length();
                                 Unit â˜ƒxxxx = Unit.getLargest(â˜ƒxxx);
                                 Unit â˜ƒxxxxx = Unit.getLargest(5368709120L);
                                 if (Unit.humanReadable(â˜ƒxxx, â˜ƒxxxx).equals(Unit.humanReadable(5368709120L, â˜ƒxxxxx)) && â˜ƒxxxx != Unit.B) {
                                    Unit â˜ƒxxxxxx = Unit.values()[â˜ƒxxxx.ordinal() - 1];
                                    this.setErrorMessage(
                                       new TranslatableComponent("mco.upload.size.failure.line1", this.selectedLevel.getLevelName()),
                                       new TranslatableComponent(
                                          "mco.upload.size.failure.line2", Unit.humanReadable(â˜ƒxxx, â˜ƒxxxxxx), Unit.humanReadable(5368709120L, â˜ƒxxxxxx)
                                       )
                                    );
                                 } else {
                                    this.setErrorMessage(
                                       new TranslatableComponent("mco.upload.size.failure.line1", this.selectedLevel.getLevelName()),
                                       new TranslatableComponent(
                                          "mco.upload.size.failure.line2", Unit.humanReadable(â˜ƒxxx, â˜ƒxxxx), Unit.humanReadable(5368709120L, â˜ƒxxxxx)
                                       )
                                    );
                                 }
                              }
                           }
                        }
                     }
                  } catch (IOException var21) {
                     â˜ƒ = var21;
                     this.setErrorMessage(new TranslatableComponent("mco.upload.failed", var21.getMessage()));
                  } catch (RealmsServiceException var22) {
                     â˜ƒ = var22;
                     this.setErrorMessage(new TranslatableComponent("mco.upload.failed", var22.toString()));
                  } catch (InterruptedException var23) {
                     â˜ƒ = var23;
                     LOGGER.error("Could not acquire upload lock");
                  }
               } finally {
                  this.uploadFinished = true;
                  if (UPLOAD_LOCK.isHeldByCurrentThread()) {
                     UPLOAD_LOCK.unlock();
                     this.showDots = false;
                     this.backButton.visible = true;
                     this.cancelButton.visible = false;
                     if (â˜ƒ != null) {
                        LOGGER.debug("Deleting file {}", â˜ƒ.getAbsolutePath());
                        â˜ƒ.delete();
                     }
                  } else {
                     return;
                  }
               }
            }
         )
         .start();
   }

   private void setErrorMessage(Component... var1) {
      this.errorMessage = â˜ƒ;
   }

   private void uploadCancelled() {
      this.status = new TranslatableComponent("mco.upload.cancelled");
      LOGGER.debug("Upload was cancelled");
   }

   private boolean verify(File var1) {
      return â˜ƒ.length() < 5368709120L;
   }

   private File tarGzipArchive(File var1) throws IOException {
      TarArchiveOutputStream â˜ƒ = null;

      File var4;
      try {
         File â˜ƒx = File.createTempFile("realms-upload-file", ".tar.gz");
         â˜ƒ = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream(â˜ƒx)));
         â˜ƒ.setLongFileMode(3);
         this.addFileToTarGz(â˜ƒ, â˜ƒ.getAbsolutePath(), "world", true);
         â˜ƒ.finish();
         var4 = â˜ƒx;
      } finally {
         if (â˜ƒ != null) {
            â˜ƒ.close();
         }
      }

      return var4;
   }

   private void addFileToTarGz(TarArchiveOutputStream var1, String var2, String var3, boolean var4) throws IOException {
      if (!this.cancelled) {
         File â˜ƒ = new File(â˜ƒ);
         String â˜ƒx = â˜ƒ ? â˜ƒ : â˜ƒ + â˜ƒ.getName();
         TarArchiveEntry â˜ƒxx = new TarArchiveEntry(â˜ƒ, â˜ƒx);
         â˜ƒ.putArchiveEntry(â˜ƒxx);
         if (â˜ƒ.isFile()) {
            IOUtils.copy(new FileInputStream(â˜ƒ), â˜ƒ);
            â˜ƒ.closeArchiveEntry();
         } else {
            â˜ƒ.closeArchiveEntry();
            File[] â˜ƒ = â˜ƒ.listFiles();
            if (â˜ƒ != null) {
               for(File â˜ƒx : â˜ƒ) {
                  this.addFileToTarGz(â˜ƒ, â˜ƒx.getAbsolutePath(), â˜ƒx + "/", false);
               }
            }
         }
      }
   }
}
