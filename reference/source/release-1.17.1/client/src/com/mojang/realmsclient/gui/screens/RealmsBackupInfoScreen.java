package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.dto.Backup;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameType;

public class RealmsBackupInfoScreen extends RealmsScreen {
   private static final Component TEXT_UNKNOWN = new TextComponent("UNKNOWN");
   private final Screen lastScreen;
   final Backup backup;
   private RealmsBackupInfoScreen.BackupInfoList backupInfoList;

   public RealmsBackupInfoScreen(Screen var1, Backup var2) {
      super(new TextComponent("Changes from last backup"));
      this.lastScreen = â˜ƒ;
      this.backup = â˜ƒ;
   }

   @Override
   public void tick() {
   }

   @Override
   public void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height / 4 + 120 + 24, 200, 20, CommonComponents.GUI_BACK, var1 -> this.minecraft.setScreen(this.lastScreen))
      );
      this.backupInfoList = new RealmsBackupInfoScreen.BackupInfoList(this.minecraft);
      this.addWidget(this.backupInfoList);
      this.magicalSpecialHackyFocus(this.backupInfoList);
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
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

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.backupInfoList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 10, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   Component checkForSpecificMetadata(String var1, String var2) {
      String â˜ƒ = â˜ƒ.toLowerCase(Locale.ROOT);
      if (â˜ƒ.contains("game") && â˜ƒ.contains("mode")) {
         return this.gameModeMetadata(â˜ƒ);
      } else {
         return (Component)(â˜ƒ.contains("game") && â˜ƒ.contains("difficulty") ? this.gameDifficultyMetadata(â˜ƒ) : new TextComponent(â˜ƒ));
      }
   }

   private Component gameDifficultyMetadata(String var1) {
      try {
         return ((Difficulty)RealmsSlotOptionsScreen.DIFFICULTIES.get(Integer.parseInt(â˜ƒ))).getDisplayName();
      } catch (Exception var3) {
         return TEXT_UNKNOWN;
      }
   }

   private Component gameModeMetadata(String var1) {
      try {
         return ((GameType)RealmsSlotOptionsScreen.GAME_MODES.get(Integer.parseInt(â˜ƒ))).getShortDisplayName();
      } catch (Exception var3) {
         return TEXT_UNKNOWN;
      }
   }

   class BackupInfoList extends ObjectSelectionList<RealmsBackupInfoScreen.BackupInfoListEntry> {
      public BackupInfoList(Minecraft var2) {
         super(â˜ƒ, RealmsBackupInfoScreen.this.width, RealmsBackupInfoScreen.this.height, 32, RealmsBackupInfoScreen.this.height - 64, 36);
         this.setRenderSelection(false);
         if (RealmsBackupInfoScreen.this.backup.changeList != null) {
            RealmsBackupInfoScreen.this.backup
               .changeList
               .forEach((var1x, var2x) -> this.addEntry(RealmsBackupInfoScreen.this.new BackupInfoListEntry(var1x, var2x)));
         }
      }
   }

   class BackupInfoListEntry extends ObjectSelectionList.Entry<RealmsBackupInfoScreen.BackupInfoListEntry> {
      private final String key;
      private final String value;

      public BackupInfoListEntry(String var2, String var3) {
         this.key = â˜ƒ;
         this.value = â˜ƒ;
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         Font â˜ƒ = RealmsBackupInfoScreen.this.minecraft.font;
         GuiComponent.drawString(â˜ƒ, â˜ƒ, this.key, â˜ƒ, â˜ƒ, 10526880);
         GuiComponent.drawString(â˜ƒ, â˜ƒ, RealmsBackupInfoScreen.this.checkForSpecificMetadata(this.key, this.value), â˜ƒ, â˜ƒ + 12, 16777215);
      }

      @Override
      public Component getNarration() {
         return new TranslatableComponent("narrator.select", this.key + " " + this.value);
      }
   }
}
