package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class BackupConfirmScreen extends Screen {
   @Nullable
   private final Screen lastScreen;
   protected final BackupConfirmScreen.Listener listener;
   private final Component description;
   private final boolean promptForCacheErase;
   private MultiLineLabel message = MultiLineLabel.EMPTY;
   protected int id;
   private Checkbox eraseCache;

   public BackupConfirmScreen(@Nullable Screen var1, BackupConfirmScreen.Listener var2, Component var3, Component var4, boolean var5) {
      super(â˜ƒ);
      this.lastScreen = â˜ƒ;
      this.listener = â˜ƒ;
      this.description = â˜ƒ;
      this.promptForCacheErase = â˜ƒ;
   }

   @Override
   protected void init() {
      super.init();
      this.message = MultiLineLabel.create(this.font, this.description, this.width - 50);
      int â˜ƒ = (this.message.getLineCount() + 1) * 9;
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 155,
            100 + â˜ƒ,
            150,
            20,
            new TranslatableComponent("selectWorld.backupJoinConfirmButton"),
            var1x -> this.listener.proceed(true, this.eraseCache.selected())
         )
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 155 + 160,
            100 + â˜ƒ,
            150,
            20,
            new TranslatableComponent("selectWorld.backupJoinSkipButton"),
            var1x -> this.listener.proceed(false, this.eraseCache.selected())
         )
      );
      this.addRenderableWidget(
         new Button(this.width / 2 - 155 + 80, 124 + â˜ƒ, 150, 20, CommonComponents.GUI_CANCEL, var1x -> this.minecraft.setScreen(this.lastScreen))
      );
      this.eraseCache = new Checkbox(this.width / 2 - 155 + 80, 76 + â˜ƒ, 150, 20, new TranslatableComponent("selectWorld.backupEraseCache"), false);
      if (this.promptForCacheErase) {
         this.addRenderableWidget(this.eraseCache);
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 50, 16777215);
      this.message.renderCentered(â˜ƒ, this.width / 2, 70);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
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

   public interface Listener {
      void proceed(boolean var1, boolean var2);
   }
}
