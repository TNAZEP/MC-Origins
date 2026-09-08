package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.level.GameType;

public class ShareToLanScreen extends Screen {
   private static final Component ALLOW_COMMANDS_LABEL = new TranslatableComponent("selectWorld.allowCommands");
   private static final Component GAME_MODE_LABEL = new TranslatableComponent("selectWorld.gameMode");
   private static final Component INFO_TEXT = new TranslatableComponent("lanServer.otherPlayers");
   private final Screen lastScreen;
   private GameType gameMode = GameType.SURVIVAL;
   private boolean commands;

   public ShareToLanScreen(Screen var1) {
      super(new TranslatableComponent("lanServer.title"));
      this.lastScreen = â˜ƒ;
   }

   @Override
   protected void init() {
      this.addRenderableWidget(
         CycleButton.builder(GameType::getShortDisplayName)
            .withValues(GameType.SURVIVAL, GameType.SPECTATOR, GameType.CREATIVE, GameType.ADVENTURE)
            .withInitialValue(this.gameMode)
            .create(this.width / 2 - 155, 100, 150, 20, GAME_MODE_LABEL, (var1, var2) -> this.gameMode = var2)
      );
      this.addRenderableWidget(
         CycleButton.onOffBuilder(this.commands).create(this.width / 2 + 5, 100, 150, 20, ALLOW_COMMANDS_LABEL, (var1, var2) -> this.commands = var2)
      );
      this.addRenderableWidget(new Button(this.width / 2 - 155, this.height - 28, 150, 20, new TranslatableComponent("lanServer.start"), var1 -> {
         this.minecraft.setScreen(null);
         int â˜ƒx = HttpUtil.getAvailablePort();
         Component â˜ƒ;
         if (this.minecraft.getSingleplayerServer().publishServer(this.gameMode, this.commands, â˜ƒx)) {
            â˜ƒ = new TranslatableComponent("commands.publish.started", â˜ƒx);
         } else {
            â˜ƒ = new TranslatableComponent("commands.publish.failed");
         }

         this.minecraft.gui.getChat().addMessage(â˜ƒ);
         this.minecraft.updateTitle();
      }));
      this.addRenderableWidget(
         new Button(this.width / 2 + 5, this.height - 28, 150, 20, CommonComponents.GUI_CANCEL, var1 -> this.minecraft.setScreen(this.lastScreen))
      );
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 50, 16777215);
      drawCenteredString(â˜ƒ, this.font, INFO_TEXT, this.width / 2, 82, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
