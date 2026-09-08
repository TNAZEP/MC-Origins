package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.util.RealmsUtil;
import com.mojang.realmsclient.util.task.DownloadTask;
import com.mojang.realmsclient.util.task.RestoreTask;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBackupScreen extends RealmsScreen {
   static final Logger LOGGER = LogManager.getLogger();
   static final ResourceLocation PLUS_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/plus_icon.png");
   static final ResourceLocation RESTORE_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/restore_icon.png");
   static final Component RESTORE_TOOLTIP = new TranslatableComponent("mco.backup.button.restore");
   static final Component HAS_CHANGES_TOOLTIP = new TranslatableComponent("mco.backup.changes.tooltip");
   private static final Component TITLE = new TranslatableComponent("mco.configure.world.backup");
   private static final Component NO_BACKUPS_LABEL = new TranslatableComponent("mco.backup.nobackups");
   static int lastScrollPosition = -1;
   private final RealmsConfigureWorldScreen lastScreen;
   List<Backup> backups = Collections.emptyList();
   @Nullable
   Component toolTip;
   RealmsBackupScreen.BackupObjectSelectionList backupObjectSelectionList;
   int selectedBackup = -1;
   private final int slotId;
   private Button downloadButton;
   private Button restoreButton;
   private Button changesButton;
   Boolean noBackups = false;
   final RealmsServer serverData;
   private static final String UPLOADED_KEY = "Uploaded";

   public RealmsBackupScreen(RealmsConfigureWorldScreen var1, RealmsServer var2, int var3) {
      super(new TranslatableComponent("mco.configure.world.backup"));
      this.lastScreen = â˜ƒ;
      this.serverData = â˜ƒ;
      this.slotId = â˜ƒ;
   }

   @Override
   public void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.backupObjectSelectionList = new RealmsBackupScreen.BackupObjectSelectionList();
      if (lastScrollPosition != -1) {
         this.backupObjectSelectionList.setScrollAmount((double)lastScrollPosition);
      }

      (new Thread("Realms-fetch-backups") {
         public void run() {
            RealmsClient â˜ƒ = RealmsClient.create();

            try {
               List<Backup> â˜ƒx = â˜ƒ.backupsFor(RealmsBackupScreen.this.serverData.id).backups;
               RealmsBackupScreen.this.minecraft.execute(() -> {
                  RealmsBackupScreen.this.backups = â˜ƒ;
                  RealmsBackupScreen.this.noBackups = RealmsBackupScreen.this.backups.isEmpty();
                  RealmsBackupScreen.this.backupObjectSelectionList.clear();

                  for(Backup â˜ƒ : RealmsBackupScreen.this.backups) {
                     RealmsBackupScreen.this.backupObjectSelectionList.addEntry(â˜ƒ);
                  }

                  RealmsBackupScreen.this.generateChangeList();
               });
            } catch (RealmsServiceException var3) {
               RealmsBackupScreen.LOGGER.error("Couldn't request backups", var3);
            }
         }
      }).start();
      this.downloadButton = this.addRenderableWidget(
         new Button(this.width - 135, row(1), 120, 20, new TranslatableComponent("mco.backup.button.download"), var1 -> this.downloadClicked())
      );
      this.restoreButton = this.addRenderableWidget(
         new Button(this.width - 135, row(3), 120, 20, new TranslatableComponent("mco.backup.button.restore"), var1 -> this.restoreClicked(this.selectedBackup))
      );
      this.changesButton = this.addRenderableWidget(
         new Button(this.width - 135, row(5), 120, 20, new TranslatableComponent("mco.backup.changes.tooltip"), var1 -> {
            this.minecraft.setScreen(new RealmsBackupInfoScreen(this, (Backup)this.backups.get(this.selectedBackup)));
            this.selectedBackup = -1;
         })
      );
      this.addRenderableWidget(
         new Button(this.width - 100, this.height - 35, 85, 20, CommonComponents.GUI_BACK, var1 -> this.minecraft.setScreen(this.lastScreen))
      );
      this.addWidget(this.backupObjectSelectionList);
      this.magicalSpecialHackyFocus(this.backupObjectSelectionList);
      this.updateButtonStates();
   }

   void generateChangeList() {
      if (this.backups.size() > 1) {
         for(int â˜ƒ = 0; â˜ƒ < this.backups.size() - 1; ++â˜ƒ) {
            Backup â˜ƒx = (Backup)this.backups.get(â˜ƒ);
            Backup â˜ƒxx = (Backup)this.backups.get(â˜ƒ + 1);
            if (!â˜ƒx.metadata.isEmpty() && !â˜ƒxx.metadata.isEmpty()) {
               for(String â˜ƒxxx : â˜ƒx.metadata.keySet()) {
                  if (!â˜ƒxxx.contains("Uploaded") && â˜ƒxx.metadata.containsKey(â˜ƒxxx)) {
                     if (!((String)â˜ƒx.metadata.get(â˜ƒxxx)).equals(â˜ƒxx.metadata.get(â˜ƒxxx))) {
                        this.addToChangeList(â˜ƒx, â˜ƒxxx);
                     }
                  } else {
                     this.addToChangeList(â˜ƒx, â˜ƒxxx);
                  }
               }
            }
         }
      }
   }

   private void addToChangeList(Backup var1, String var2) {
      if (â˜ƒ.contains("Uploaded")) {
         String â˜ƒ = DateFormat.getDateTimeInstance(3, 3).format(â˜ƒ.lastModifiedDate);
         â˜ƒ.changeList.put(â˜ƒ, â˜ƒ);
         â˜ƒ.setUploadedVersion(true);
      } else {
         â˜ƒ.changeList.put(â˜ƒ, (String)â˜ƒ.metadata.get(â˜ƒ));
      }
   }

   void updateButtonStates() {
      this.restoreButton.visible = this.shouldRestoreButtonBeVisible();
      this.changesButton.visible = this.shouldChangesButtonBeVisible();
   }

   private boolean shouldChangesButtonBeVisible() {
      if (this.selectedBackup == -1) {
         return false;
      } else {
         return !((Backup)this.backups.get(this.selectedBackup)).changeList.isEmpty();
      }
   }

   private boolean shouldRestoreButtonBeVisible() {
      if (this.selectedBackup == -1) {
         return false;
      } else {
         return !this.serverData.expired;
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.minecraft.setScreen(this.lastScreen);
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   void restoreClicked(int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < this.backups.size() && !this.serverData.expired) {
         this.selectedBackup = â˜ƒ;
         Date â˜ƒ = ((Backup)this.backups.get(â˜ƒ)).lastModifiedDate;
         String â˜ƒx = DateFormat.getDateTimeInstance(3, 3).format(â˜ƒ);
         String â˜ƒxx = RealmsUtil.convertToAgePresentationFromInstant(â˜ƒ);
         Component â˜ƒxxx = new TranslatableComponent("mco.configure.world.restore.question.line1", â˜ƒx, â˜ƒxx);
         Component â˜ƒxxxx = new TranslatableComponent("mco.configure.world.restore.question.line2");
         this.minecraft.setScreen(new RealmsLongConfirmationScreen(var1x -> {
            if (var1x) {
               this.restore();
            } else {
               this.selectedBackup = -1;
               this.minecraft.setScreen(this);
            }
         }, RealmsLongConfirmationScreen.Type.Warning, â˜ƒxxx, â˜ƒxxxx, true));
      }
   }

   private void downloadClicked() {
      Component â˜ƒ = new TranslatableComponent("mco.configure.world.restore.download.question.line1");
      Component â˜ƒx = new TranslatableComponent("mco.configure.world.restore.download.question.line2");
      this.minecraft.setScreen(new RealmsLongConfirmationScreen(var1x -> {
         if (var1x) {
            this.downloadWorldData();
         } else {
            this.minecraft.setScreen(this);
         }
      }, RealmsLongConfirmationScreen.Type.Info, â˜ƒ, â˜ƒx, true));
   }

   private void downloadWorldData() {
      this.minecraft
         .setScreen(
            new RealmsLongRunningMcoTaskScreen(
               this.lastScreen.getNewScreen(),
               new DownloadTask(
                  this.serverData.id,
                  this.slotId,
                  this.serverData.name
                     + " ("
                     + ((RealmsWorldOptions)this.serverData.slots.get(this.serverData.activeSlot)).getSlotName(this.serverData.activeSlot)
                     + ")",
                  this
               )
            )
         );
   }

   private void restore() {
      Backup â˜ƒ = (Backup)this.backups.get(this.selectedBackup);
      this.selectedBackup = -1;
      this.minecraft.setScreen(new RealmsLongRunningMcoTaskScreen(this.lastScreen.getNewScreen(), new RestoreTask(â˜ƒ, this.serverData.id, this.lastScreen)));
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.toolTip = null;
      this.renderBackground(â˜ƒ);
      this.backupObjectSelectionList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 12, 16777215);
      this.font.draw(â˜ƒ, TITLE, (float)((this.width - 150) / 2 - 90), 20.0F, 10526880);
      if (this.noBackups) {
         this.font.draw(â˜ƒ, NO_BACKUPS_LABEL, 20.0F, (float)(this.height / 2 - 10), 16777215);
      }

      this.downloadButton.active = !this.noBackups;
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.toolTip != null) {
         this.renderMousehoverTooltip(â˜ƒ, this.toolTip, â˜ƒ, â˜ƒ);
      }
   }

   protected void renderMousehoverTooltip(PoseStack var1, @Nullable Component var2, int var3, int var4) {
      if (â˜ƒ != null) {
         int â˜ƒ = â˜ƒ + 12;
         int â˜ƒx = â˜ƒ - 12;
         int â˜ƒxx = this.font.width(â˜ƒ);
         this.fillGradient(â˜ƒ, â˜ƒ - 3, â˜ƒx - 3, â˜ƒ + â˜ƒxx + 3, â˜ƒx + 8 + 3, -1073741824, -1073741824);
         this.font.drawShadow(â˜ƒ, â˜ƒ, (float)â˜ƒ, (float)â˜ƒx, 16777215);
      }
   }

   class BackupObjectSelectionList extends RealmsObjectSelectionList<RealmsBackupScreen.Entry> {
      public BackupObjectSelectionList() {
         super(RealmsBackupScreen.this.width - 150, RealmsBackupScreen.this.height, 32, RealmsBackupScreen.this.height - 15, 36);
      }

      public void addEntry(Backup var1) {
         this.addEntry(RealmsBackupScreen.this.new Entry(â˜ƒ));
      }

      @Override
      public int getRowWidth() {
         return (int)((double)this.width * 0.93);
      }

      @Override
      public boolean isFocused() {
         return RealmsBackupScreen.this.getFocused() == this;
      }

      @Override
      public int getMaxPosition() {
         return this.getItemCount() * 36;
      }

      @Override
      public void renderBackground(PoseStack var1) {
         RealmsBackupScreen.this.renderBackground(â˜ƒ);
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         if (â˜ƒ != 0) {
            return false;
         } else if (â˜ƒ < (double)this.getScrollbarPosition() && â˜ƒ >= (double)this.y0 && â˜ƒ <= (double)this.y1) {
            int â˜ƒ = this.width / 2 - 92;
            int â˜ƒx = this.width;
            int â˜ƒxx = (int)Math.floor(â˜ƒ - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount();
            int â˜ƒxxx = â˜ƒxx / this.itemHeight;
            if (â˜ƒ >= (double)â˜ƒ && â˜ƒ <= (double)â˜ƒx && â˜ƒxxx >= 0 && â˜ƒxx >= 0 && â˜ƒxxx < this.getItemCount()) {
               this.selectItem(â˜ƒxxx);
               this.itemClicked(â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒ, this.width);
            }

            return true;
         } else {
            return false;
         }
      }

      @Override
      public int getScrollbarPosition() {
         return this.width - 5;
      }

      @Override
      public void itemClicked(int var1, int var2, double var3, double var5, int var7) {
         int â˜ƒ = this.width - 35;
         int â˜ƒx = â˜ƒ * this.itemHeight + 36 - (int)this.getScrollAmount();
         int â˜ƒxx = â˜ƒ + 10;
         int â˜ƒxxx = â˜ƒx - 3;
         if (â˜ƒ >= (double)â˜ƒ && â˜ƒ <= (double)(â˜ƒ + 9) && â˜ƒ >= (double)â˜ƒx && â˜ƒ <= (double)(â˜ƒx + 9)) {
            if (!((Backup)RealmsBackupScreen.this.backups.get(â˜ƒ)).changeList.isEmpty()) {
               RealmsBackupScreen.this.selectedBackup = -1;
               RealmsBackupScreen.lastScrollPosition = (int)this.getScrollAmount();
               this.minecraft.setScreen(new RealmsBackupInfoScreen(RealmsBackupScreen.this, (Backup)RealmsBackupScreen.this.backups.get(â˜ƒ)));
            }
         } else if (â˜ƒ >= (double)â˜ƒxx && â˜ƒ < (double)(â˜ƒxx + 13) && â˜ƒ >= (double)â˜ƒxxx && â˜ƒ < (double)(â˜ƒxxx + 15)) {
            RealmsBackupScreen.lastScrollPosition = (int)this.getScrollAmount();
            RealmsBackupScreen.this.restoreClicked(â˜ƒ);
         }
      }

      @Override
      public void selectItem(int var1) {
         super.selectItem(â˜ƒ);
         this.selectInviteListItem(â˜ƒ);
      }

      public void selectInviteListItem(int var1) {
         RealmsBackupScreen.this.selectedBackup = â˜ƒ;
         RealmsBackupScreen.this.updateButtonStates();
      }

      public void setSelected(@Nullable RealmsBackupScreen.Entry var1) {
         super.setSelected(â˜ƒ);
         RealmsBackupScreen.this.selectedBackup = this.children().indexOf(â˜ƒ);
         RealmsBackupScreen.this.updateButtonStates();
      }
   }

   class Entry extends ObjectSelectionList.Entry<RealmsBackupScreen.Entry> {
      private final Backup backup;

      public Entry(Backup var2) {
         this.backup = â˜ƒ;
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.renderBackupItem(â˜ƒ, this.backup, â˜ƒ - 40, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      private void renderBackupItem(PoseStack var1, Backup var2, int var3, int var4, int var5, int var6) {
         int â˜ƒ = â˜ƒ.isUploadedVersion() ? -8388737 : 16777215;
         RealmsBackupScreen.this.font
            .draw(â˜ƒ, "Backup (" + RealmsUtil.convertToAgePresentationFromInstant(â˜ƒ.lastModifiedDate) + ")", (float)(â˜ƒ + 40), (float)(â˜ƒ + 1), â˜ƒ);
         RealmsBackupScreen.this.font.draw(â˜ƒ, this.getMediumDatePresentation(â˜ƒ.lastModifiedDate), (float)(â˜ƒ + 40), (float)(â˜ƒ + 12), 5000268);
         int â˜ƒx = RealmsBackupScreen.this.width - 175;
         int â˜ƒxx = -3;
         int â˜ƒxxx = â˜ƒx - 10;
         int â˜ƒxxxx = 0;
         if (!RealmsBackupScreen.this.serverData.expired) {
            this.drawRestore(â˜ƒ, â˜ƒx, â˜ƒ + -3, â˜ƒ, â˜ƒ);
         }

         if (!â˜ƒ.changeList.isEmpty()) {
            this.drawInfo(â˜ƒ, â˜ƒxxx, â˜ƒ + 0, â˜ƒ, â˜ƒ);
         }
      }

      private String getMediumDatePresentation(Date var1) {
         return DateFormat.getDateTimeInstance(3, 3).format(â˜ƒ);
      }

      private void drawRestore(PoseStack var1, int var2, int var3, int var4, int var5) {
         boolean â˜ƒ = â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 12 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 14 && â˜ƒ < RealmsBackupScreen.this.height - 15 && â˜ƒ > 32;
         RenderSystem.setShaderTexture(0, RealmsBackupScreen.RESTORE_ICON_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         â˜ƒ.pushPose();
         â˜ƒ.scale(0.5F, 0.5F, 0.5F);
         float â˜ƒx = â˜ƒ ? 28.0F : 0.0F;
         GuiComponent.blit(â˜ƒ, â˜ƒ * 2, â˜ƒ * 2, 0.0F, â˜ƒx, 23, 28, 23, 56);
         â˜ƒ.popPose();
         if (â˜ƒ) {
            RealmsBackupScreen.this.toolTip = RealmsBackupScreen.RESTORE_TOOLTIP;
         }
      }

      private void drawInfo(PoseStack var1, int var2, int var3, int var4, int var5) {
         boolean â˜ƒ = â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 8 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 8 && â˜ƒ < RealmsBackupScreen.this.height - 15 && â˜ƒ > 32;
         RenderSystem.setShaderTexture(0, RealmsBackupScreen.PLUS_ICON_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         â˜ƒ.pushPose();
         â˜ƒ.scale(0.5F, 0.5F, 0.5F);
         float â˜ƒx = â˜ƒ ? 15.0F : 0.0F;
         GuiComponent.blit(â˜ƒ, â˜ƒ * 2, â˜ƒ * 2, 0.0F, â˜ƒx, 15, 15, 15, 30);
         â˜ƒ.popPose();
         if (â˜ƒ) {
            RealmsBackupScreen.this.toolTip = RealmsBackupScreen.HAS_CHANGES_TOOLTIP;
         }
      }

      @Override
      public Component getNarration() {
         return new TranslatableComponent("narrator.select", this.backup.lastModifiedDate.toString());
      }
   }
}
