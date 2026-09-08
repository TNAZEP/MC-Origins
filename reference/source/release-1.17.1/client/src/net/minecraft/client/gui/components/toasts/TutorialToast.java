package net.minecraft.client.gui.components.toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class TutorialToast implements Toast {
   public static final int PROGRESS_BAR_WIDTH = 154;
   public static final int PROGRESS_BAR_HEIGHT = 1;
   public static final int PROGRESS_BAR_X = 3;
   public static final int PROGRESS_BAR_Y = 28;
   private final TutorialToast.Icons icon;
   private final Component title;
   private final Component message;
   private Toast.Visibility visibility = Toast.Visibility.SHOW;
   private long lastProgressTime;
   private float lastProgress;
   private float progress;
   private final boolean progressable;

   public TutorialToast(TutorialToast.Icons var1, Component var2, @Nullable Component var3, boolean var4) {
      this.icon = â˜ƒ;
      this.title = â˜ƒ;
      this.message = â˜ƒ;
      this.progressable = â˜ƒ;
   }

   @Override
   public Toast.Visibility render(PoseStack var1, ToastComponent var2, long var3) {
      RenderSystem.setShaderTexture(0, TEXTURE);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.blit(â˜ƒ, 0, 0, 0, 96, this.width(), this.height());
      this.icon.render(â˜ƒ, â˜ƒ, 6, 6);
      if (this.message == null) {
         â˜ƒ.getMinecraft().font.draw(â˜ƒ, this.title, 30.0F, 12.0F, -11534256);
      } else {
         â˜ƒ.getMinecraft().font.draw(â˜ƒ, this.title, 30.0F, 7.0F, -11534256);
         â˜ƒ.getMinecraft().font.draw(â˜ƒ, this.message, 30.0F, 18.0F, -16777216);
      }

      if (this.progressable) {
         GuiComponent.fill(â˜ƒ, 3, 28, 157, 29, -1);
         float â˜ƒx = Mth.clampedLerp(this.lastProgress, this.progress, (float)(â˜ƒ - this.lastProgressTime) / 100.0F);
         int â˜ƒ;
         if (this.progress >= this.lastProgress) {
            â˜ƒ = -16755456;
         } else {
            â˜ƒ = -11206656;
         }

         GuiComponent.fill(â˜ƒ, 3, 28, (int)(3.0F + 154.0F * â˜ƒx), 29, â˜ƒ);
         this.lastProgress = â˜ƒx;
         this.lastProgressTime = â˜ƒ;
      }

      return this.visibility;
   }

   public void hide() {
      this.visibility = Toast.Visibility.HIDE;
   }

   public void updateProgress(float var1) {
      this.progress = â˜ƒ;
   }

   public static enum Icons {
      MOVEMENT_KEYS(0, 0),
      MOUSE(1, 0),
      TREE(2, 0),
      RECIPE_BOOK(0, 1),
      WOODEN_PLANKS(1, 1),
      SOCIAL_INTERACTIONS(2, 1),
      RIGHT_CLICK(3, 1);

      private final int x;
      private final int y;

      private Icons(int var3, int var4) {
         this.x = â˜ƒ;
         this.y = â˜ƒ;
      }

      public void render(PoseStack var1, GuiComponent var2, int var3, int var4) {
         RenderSystem.enableBlend();
         â˜ƒ.blit(â˜ƒ, â˜ƒ, â˜ƒ, 176 + this.x * 20, this.y * 20, 20, 20);
         RenderSystem.enableBlend();
      }
   }
}
