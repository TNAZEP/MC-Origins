package net.minecraft.client.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class DeathScreen extends Screen {
   private int delayTicker;
   private final Component causeOfDeath;
   private final boolean hardcore;
   private Component deathScore;
   private final List<Button> exitButtons = Lists.<Button>newArrayList();

   public DeathScreen(@Nullable Component var1, boolean var2) {
      super(new TranslatableComponent(â˜ƒ ? "deathScreen.title.hardcore" : "deathScreen.title"));
      this.causeOfDeath = â˜ƒ;
      this.hardcore = â˜ƒ;
   }

   @Override
   protected void init() {
      this.delayTicker = 0;
      this.exitButtons.clear();
      this.exitButtons
         .add(
            this.addRenderableWidget(
               new Button(
                  this.width / 2 - 100,
                  this.height / 4 + 72,
                  200,
                  20,
                  this.hardcore ? new TranslatableComponent("deathScreen.spectate") : new TranslatableComponent("deathScreen.respawn"),
                  var1 -> {
                     this.minecraft.player.respawn();
                     this.minecraft.setScreen(null);
                  }
               )
            )
         );
      this.exitButtons
         .add(
            this.addRenderableWidget(
               new Button(
                  this.width / 2 - 100,
                  this.height / 4 + 96,
                  200,
                  20,
                  new TranslatableComponent("deathScreen.titleScreen"),
                  var1 -> {
                     if (this.hardcore) {
                        this.exitToTitleScreen();
                     } else {
                        ConfirmScreen â˜ƒ = new ConfirmScreen(
                           this::confirmResult,
                           new TranslatableComponent("deathScreen.quit.confirm"),
                           TextComponent.EMPTY,
                           new TranslatableComponent("deathScreen.titleScreen"),
                           new TranslatableComponent("deathScreen.respawn")
                        );
                        this.minecraft.setScreen(â˜ƒ);
                        â˜ƒ.setDelay(20);
                     }
                  }
               )
            )
         );

      for(Button â˜ƒ : this.exitButtons) {
         â˜ƒ.active = false;
      }

      this.deathScore = new TranslatableComponent("deathScreen.score")
         .append(": ")
         .append(new TextComponent(Integer.toString(this.minecraft.player.getScore())).withStyle(ChatFormatting.YELLOW));
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   private void confirmResult(boolean var1) {
      if (â˜ƒ) {
         this.exitToTitleScreen();
      } else {
         this.minecraft.player.respawn();
         this.minecraft.setScreen(null);
      }
   }

   private void exitToTitleScreen() {
      if (this.minecraft.level != null) {
         this.minecraft.level.disconnect();
      }

      this.minecraft.clearLevel(new GenericDirtMessageScreen(new TranslatableComponent("menu.savingLevel")));
      this.minecraft.setScreen(new TitleScreen());
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.fillGradient(â˜ƒ, 0, 0, this.width, this.height, 1615855616, -1602211792);
      â˜ƒ.pushPose();
      â˜ƒ.scale(2.0F, 2.0F, 2.0F);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2 / 2, 30, 16777215);
      â˜ƒ.popPose();
      if (this.causeOfDeath != null) {
         drawCenteredString(â˜ƒ, this.font, this.causeOfDeath, this.width / 2, 85, 16777215);
      }

      drawCenteredString(â˜ƒ, this.font, this.deathScore, this.width / 2, 100, 16777215);
      if (this.causeOfDeath != null && â˜ƒ > 85 && â˜ƒ < 85 + 9) {
         Style â˜ƒ = this.getClickedComponentStyleAt(â˜ƒ);
         this.renderComponentHoverEffect(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   private Style getClickedComponentStyleAt(int var1) {
      if (this.causeOfDeath == null) {
         return null;
      } else {
         int â˜ƒ = this.minecraft.font.width(this.causeOfDeath);
         int â˜ƒx = this.width / 2 - â˜ƒ / 2;
         int â˜ƒxx = this.width / 2 + â˜ƒ / 2;
         return â˜ƒ >= â˜ƒx && â˜ƒ <= â˜ƒxx ? this.minecraft.font.getSplitter().componentStyleAtWidth(this.causeOfDeath, â˜ƒ - â˜ƒx) : null;
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.causeOfDeath != null && â˜ƒ > 85.0 && â˜ƒ < (double)(85 + 9)) {
         Style â˜ƒ = this.getClickedComponentStyleAt((int)â˜ƒ);
         if (â˜ƒ != null && â˜ƒ.getClickEvent() != null && â˜ƒ.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
            this.handleComponentClicked(â˜ƒ);
            return false;
         }
      }

      return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   @Override
   public void tick() {
      super.tick();
      ++this.delayTicker;
      if (this.delayTicker == 20) {
         for(Button â˜ƒ : this.exitButtons) {
            â˜ƒ.active = true;
         }
      }
   }
}
