package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.ProgressListener;

public class ProgressScreen extends Screen implements ProgressListener {
   @Nullable
   private Component header;
   @Nullable
   private Component stage;
   private int progress;
   private boolean stop;
   private final boolean clearScreenAfterStop;

   public ProgressScreen(boolean var1) {
      super(NarratorChatListener.NO_TITLE);
      this.clearScreenAfterStop = â˜ƒ;
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   @Override
   public void progressStartNoAbort(Component var1) {
      this.progressStart(â˜ƒ);
   }

   @Override
   public void progressStart(Component var1) {
      this.header = â˜ƒ;
      this.progressStage(new TranslatableComponent("progress.working"));
   }

   @Override
   public void progressStage(Component var1) {
      this.stage = â˜ƒ;
      this.progressStagePercentage(0);
   }

   @Override
   public void progressStagePercentage(int var1) {
      this.progress = â˜ƒ;
   }

   @Override
   public void stop() {
      this.stop = true;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      if (this.stop) {
         if (this.clearScreenAfterStop) {
            this.minecraft.setScreen(null);
         }
      } else {
         this.renderBackground(â˜ƒ);
         if (this.header != null) {
            drawCenteredString(â˜ƒ, this.font, this.header, this.width / 2, 70, 16777215);
         }

         if (this.stage != null && this.progress != 0) {
            drawCenteredString(â˜ƒ, this.font, new TextComponent("").append(this.stage).append(" " + this.progress + "%"), this.width / 2, 90, 16777215);
         }

         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
