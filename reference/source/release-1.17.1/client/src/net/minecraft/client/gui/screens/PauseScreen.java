package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class PauseScreen extends Screen {
   private static final String URL_FEEDBACK_SNAPSHOT = "https://aka.ms/snapshotfeedback?ref=game";
   private static final String URL_FEEDBACK_RELEASE = "https://aka.ms/javafeedback?ref=game";
   private static final String URL_BUGS = "https://aka.ms/snapshotbugs?ref=game";
   private final boolean showPauseMenu;

   public PauseScreen(boolean var1) {
      super(â˜ƒ ? new TranslatableComponent("menu.game") : new TranslatableComponent("menu.paused"));
      this.showPauseMenu = â˜ƒ;
   }

   @Override
   protected void init() {
      if (this.showPauseMenu) {
         this.createPauseMenu();
      }
   }

   private void createPauseMenu() {
      int â˜ƒ = -16;
      int â˜ƒx = 98;
      this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, new TranslatableComponent("menu.returnToGame"), var1x -> {
         this.minecraft.setScreen(null);
         this.minecraft.mouseHandler.grabMouse();
      }));
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 102,
            this.height / 4 + 48 + -16,
            98,
            20,
            new TranslatableComponent("gui.advancements"),
            var1x -> this.minecraft.setScreen(new AdvancementsScreen(this.minecraft.player.connection.getAdvancements()))
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 + 4,
            this.height / 4 + 48 + -16,
            98,
            20,
            new TranslatableComponent("gui.stats"),
            var1x -> this.minecraft.setScreen(new StatsScreen(this, this.minecraft.player.getStats()))
         )
      );
      String â˜ƒxx = SharedConstants.getCurrentVersion().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game";
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 102,
            this.height / 4 + 72 + -16,
            98,
            20,
            new TranslatableComponent("menu.sendFeedback"),
            var2x -> this.minecraft.setScreen(new ConfirmLinkScreen(var2xx -> {
                  if (var2xx) {
                     Util.getPlatform().openUri(â˜ƒ);
                  }
      
                  this.minecraft.setScreen(this);
               }, â˜ƒ, true))
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 + 4,
            this.height / 4 + 72 + -16,
            98,
            20,
            new TranslatableComponent("menu.reportBugs"),
            var1x -> this.minecraft.setScreen(new ConfirmLinkScreen(var1xx -> {
                  if (var1xx) {
                     Util.getPlatform().openUri("https://aka.ms/snapshotbugs?ref=game");
                  }
      
                  this.minecraft.setScreen(this);
               }, "https://aka.ms/snapshotbugs?ref=game", true))
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 102,
            this.height / 4 + 96 + -16,
            98,
            20,
            new TranslatableComponent("menu.options"),
            var1x -> this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options))
         )
      );
      Button â˜ƒxxx = this.addRenderableWidget(
         new Button(
            this.width / 2 + 4,
            this.height / 4 + 96 + -16,
            98,
            20,
            new TranslatableComponent("menu.shareToLan"),
            var1x -> this.minecraft.setScreen(new ShareToLanScreen(this))
         )
      );
      â˜ƒxxx.active = this.minecraft.hasSingleplayerServer() && !this.minecraft.getSingleplayerServer().isPublished();
      Component â˜ƒxxxx = this.minecraft.isLocalServer() ? new TranslatableComponent("menu.returnToMenu") : new TranslatableComponent("menu.disconnect");
      this.addRenderableWidget(new Button(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20, â˜ƒxxxx, var1x -> {
         boolean â˜ƒ = this.minecraft.isLocalServer();
         boolean â˜ƒx = this.minecraft.isConnectedToRealms();
         var1x.active = false;
         this.minecraft.level.disconnect();
         if (â˜ƒ) {
            this.minecraft.clearLevel(new GenericDirtMessageScreen(new TranslatableComponent("menu.savingLevel")));
         } else {
            this.minecraft.clearLevel();
         }

         TitleScreen â˜ƒ = new TitleScreen();
         if (â˜ƒ) {
            this.minecraft.setScreen(â˜ƒ);
         } else if (â˜ƒx) {
            this.minecraft.setScreen(new RealmsMainScreen(â˜ƒ));
         } else {
            this.minecraft.setScreen(new JoinMultiplayerScreen(â˜ƒ));
         }
      }));
   }

   @Override
   public void tick() {
      super.tick();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      if (this.showPauseMenu) {
         this.renderBackground(â˜ƒ);
         drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 40, 16777215);
      } else {
         drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 10, 16777215);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
