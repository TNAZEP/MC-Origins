package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsSelectFileToUploadScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   static final Component WORLD_TEXT = new TranslatableComponent("selectWorld.world");
   static final Component REQUIRES_CONVERSION_TEXT = new TranslatableComponent("selectWorld.conversion");
   static final Component HARDCORE_TEXT = new TranslatableComponent("mco.upload.hardcore").withStyle(ChatFormatting.DARK_RED);
   static final Component CHEATS_TEXT = new TranslatableComponent("selectWorld.cheats");
   private static final DateFormat DATE_FORMAT = new SimpleDateFormat();
   private final RealmsResetWorldScreen lastScreen;
   private final long worldId;
   private final int slotId;
   Button uploadButton;
   List<LevelSummary> levelList = Lists.<LevelSummary>newArrayList();
   int selectedWorld = -1;
   RealmsSelectFileToUploadScreen.WorldSelectionList worldSelectionList;
   private final Runnable callback;

   public RealmsSelectFileToUploadScreen(long var1, int var3, RealmsResetWorldScreen var4, Runnable var5) {
      super(new TranslatableComponent("mco.upload.select.world.title"));
      this.lastScreen = â˜ƒ;
      this.worldId = â˜ƒ;
      this.slotId = â˜ƒ;
      this.callback = â˜ƒ;
   }

   private void loadLevelList() throws Exception {
      this.levelList = (List)this.minecraft.getLevelSource().getLevelList().stream().sorted((var0, var1) -> {
         if (var0.getLastPlayed() < var1.getLastPlayed()) {
            return 1;
         } else {
            return var0.getLastPlayed() > var1.getLastPlayed() ? -1 : var0.getLevelId().compareTo(var1.getLevelId());
         }
      }).collect(Collectors.toList());

      for(LevelSummary â˜ƒ : this.levelList) {
         this.worldSelectionList.addEntry(â˜ƒ);
      }
   }

   @Override
   public void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.worldSelectionList = new RealmsSelectFileToUploadScreen.WorldSelectionList();

      try {
         this.loadLevelList();
      } catch (Exception var2) {
         LOGGER.error("Couldn't load level list", var2);
         this.minecraft
            .setScreen(new RealmsGenericErrorScreen(new TextComponent("Unable to load worlds"), Component.nullToEmpty(var2.getMessage()), this.lastScreen));
         return;
      }

      this.addWidget(this.worldSelectionList);
      this.uploadButton = this.addRenderableWidget(
         new Button(this.width / 2 - 154, this.height - 32, 153, 20, new TranslatableComponent("mco.upload.button.name"), var1 -> this.upload())
      );
      this.uploadButton.active = this.selectedWorld >= 0 && this.selectedWorld < this.levelList.size();
      this.addRenderableWidget(
         new Button(this.width / 2 + 6, this.height - 32, 153, 20, CommonComponents.GUI_BACK, var1 -> this.minecraft.setScreen(this.lastScreen))
      );
      this.addLabel(new RealmsLabel(new TranslatableComponent("mco.upload.select.world.subtitle"), this.width / 2, row(-1), 10526880));
      if (this.levelList.isEmpty()) {
         this.addLabel(new RealmsLabel(new TranslatableComponent("mco.upload.select.world.none"), this.width / 2, this.height / 2 - 20, 16777215));
      }
   }

   @Override
   public Component getNarrationMessage() {
      return CommonComponents.joinForNarration(this.getTitle(), this.createLabelNarration());
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   private void upload() {
      if (this.selectedWorld != -1 && !((LevelSummary)this.levelList.get(this.selectedWorld)).isHardcore()) {
         LevelSummary â˜ƒ = (LevelSummary)this.levelList.get(this.selectedWorld);
         this.minecraft.setScreen(new RealmsUploadScreen(this.worldId, this.slotId, this.lastScreen, â˜ƒ, this.callback));
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.worldSelectionList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 13, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
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

   static Component gameModeName(LevelSummary var0) {
      return â˜ƒ.getGameMode().getLongDisplayName();
   }

   static String formatLastPlayed(LevelSummary var0) {
      return DATE_FORMAT.format(new Date(â˜ƒ.getLastPlayed()));
   }

   class Entry extends ObjectSelectionList.Entry<RealmsSelectFileToUploadScreen.Entry> {
      private final LevelSummary levelSummary;
      private final String name;
      private final String id;
      private final Component info;

      public Entry(LevelSummary var2) {
         this.levelSummary = â˜ƒ;
         this.name = â˜ƒ.getLevelName();
         this.id = â˜ƒ.getLevelId() + " (" + RealmsSelectFileToUploadScreen.formatLastPlayed(â˜ƒ) + ")";
         if (â˜ƒ.isRequiresConversion()) {
            this.info = RealmsSelectFileToUploadScreen.REQUIRES_CONVERSION_TEXT;
         } else {
            Component â˜ƒ;
            if (â˜ƒ.isHardcore()) {
               â˜ƒ = RealmsSelectFileToUploadScreen.HARDCORE_TEXT;
            } else {
               â˜ƒ = RealmsSelectFileToUploadScreen.gameModeName(â˜ƒ);
            }

            if (â˜ƒ.hasCheats()) {
               â˜ƒ = â˜ƒ.copy().append(", ").append(RealmsSelectFileToUploadScreen.CHEATS_TEXT);
            }

            this.info = â˜ƒ;
         }
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.renderItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         RealmsSelectFileToUploadScreen.this.worldSelectionList.selectItem(RealmsSelectFileToUploadScreen.this.levelList.indexOf(this.levelSummary));
         return true;
      }

      protected void renderItem(PoseStack var1, int var2, int var3, int var4) {
         String â˜ƒ;
         if (this.name.isEmpty()) {
            â˜ƒ = RealmsSelectFileToUploadScreen.WORLD_TEXT + " " + (â˜ƒ + 1);
         } else {
            â˜ƒ = this.name;
         }

         RealmsSelectFileToUploadScreen.this.font.draw(â˜ƒ, â˜ƒ, (float)(â˜ƒ + 2), (float)(â˜ƒ + 1), 16777215);
         RealmsSelectFileToUploadScreen.this.font.draw(â˜ƒ, this.id, (float)(â˜ƒ + 2), (float)(â˜ƒ + 12), 8421504);
         RealmsSelectFileToUploadScreen.this.font.draw(â˜ƒ, this.info, (float)(â˜ƒ + 2), (float)(â˜ƒ + 12 + 10), 8421504);
      }

      @Override
      public Component getNarration() {
         Component â˜ƒ = CommonComponents.joinLines(
            new TextComponent(this.levelSummary.getLevelName()),
            new TextComponent(RealmsSelectFileToUploadScreen.formatLastPlayed(this.levelSummary)),
            RealmsSelectFileToUploadScreen.gameModeName(this.levelSummary)
         );
         return new TranslatableComponent("narrator.select", â˜ƒ);
      }
   }

   class WorldSelectionList extends RealmsObjectSelectionList<RealmsSelectFileToUploadScreen.Entry> {
      public WorldSelectionList() {
         super(
            RealmsSelectFileToUploadScreen.this.width,
            RealmsSelectFileToUploadScreen.this.height,
            RealmsSelectFileToUploadScreen.row(0),
            RealmsSelectFileToUploadScreen.this.height - 40,
            36
         );
      }

      public void addEntry(LevelSummary var1) {
         this.addEntry(RealmsSelectFileToUploadScreen.this.new Entry(â˜ƒ));
      }

      @Override
      public int getMaxPosition() {
         return RealmsSelectFileToUploadScreen.this.levelList.size() * 36;
      }

      @Override
      public boolean isFocused() {
         return RealmsSelectFileToUploadScreen.this.getFocused() == this;
      }

      @Override
      public void renderBackground(PoseStack var1) {
         RealmsSelectFileToUploadScreen.this.renderBackground(â˜ƒ);
      }

      public void setSelected(@Nullable RealmsSelectFileToUploadScreen.Entry var1) {
         super.setSelected(â˜ƒ);
         RealmsSelectFileToUploadScreen.this.selectedWorld = this.children().indexOf(â˜ƒ);
         RealmsSelectFileToUploadScreen.this.uploadButton.active = RealmsSelectFileToUploadScreen.this.selectedWorld >= 0
            && RealmsSelectFileToUploadScreen.this.selectedWorld < this.getItemCount()
            && !((LevelSummary)RealmsSelectFileToUploadScreen.this.levelList.get(RealmsSelectFileToUploadScreen.this.selectedWorld)).isHardcore();
      }
   }
}
