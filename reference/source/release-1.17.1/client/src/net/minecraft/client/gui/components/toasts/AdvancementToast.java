package net.minecraft.client.gui.components.toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class AdvancementToast implements Toast {
   private final Advancement advancement;
   private boolean playedSound;

   public AdvancementToast(Advancement var1) {
      this.advancement = â˜ƒ;
   }

   @Override
   public Toast.Visibility render(PoseStack var1, ToastComponent var2, long var3) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, TEXTURE);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      DisplayInfo â˜ƒ = this.advancement.getDisplay();
      â˜ƒ.blit(â˜ƒ, 0, 0, 0, 0, this.width(), this.height());
      if (â˜ƒ != null) {
         List<FormattedCharSequence> â˜ƒx = â˜ƒ.getMinecraft().font.split(â˜ƒ.getTitle(), 125);
         int â˜ƒxx = â˜ƒ.getFrame() == FrameType.CHALLENGE ? 16746751 : 16776960;
         if (â˜ƒx.size() == 1) {
            â˜ƒ.getMinecraft().font.draw(â˜ƒ, â˜ƒ.getFrame().getDisplayName(), 30.0F, 7.0F, â˜ƒxx | 0xFF000000);
            â˜ƒ.getMinecraft().font.draw(â˜ƒ, (FormattedCharSequence)â˜ƒx.get(0), 30.0F, 18.0F, -1);
         } else {
            int â˜ƒx = 1500;
            float â˜ƒxx = 300.0F;
            if (â˜ƒ < 1500L) {
               int â˜ƒxxx = Mth.floor(Mth.clamp((float)(1500L - â˜ƒ) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
               â˜ƒ.getMinecraft().font.draw(â˜ƒ, â˜ƒ.getFrame().getDisplayName(), 30.0F, 11.0F, â˜ƒxx | â˜ƒxxx);
            } else {
               int â˜ƒx = Mth.floor(Mth.clamp((float)(â˜ƒ - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
               int â˜ƒxx = this.height() / 2 - â˜ƒx.size() * 9 / 2;

               for(FormattedCharSequence â˜ƒxxx : â˜ƒx) {
                  â˜ƒ.getMinecraft().font.draw(â˜ƒ, â˜ƒxxx, 30.0F, (float)â˜ƒxx, 16777215 | â˜ƒx);
                  â˜ƒxx += 9;
               }
            }
         }

         if (!this.playedSound && â˜ƒ > 0L) {
            this.playedSound = true;
            if (â˜ƒ.getFrame() == FrameType.CHALLENGE) {
               â˜ƒ.getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
            }
         }

         â˜ƒ.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(â˜ƒ.getIcon(), 8, 8);
         return â˜ƒ >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
      } else {
         return Toast.Visibility.HIDE;
      }
   }
}
