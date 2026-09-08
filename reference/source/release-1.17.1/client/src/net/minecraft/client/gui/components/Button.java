package net.minecraft.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class Button extends AbstractButton {
   public static final Button.OnTooltip NO_TOOLTIP = (var0, var1, var2, var3) -> {
   };
   protected final Button.OnPress onPress;
   protected final Button.OnTooltip onTooltip;

   public Button(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, NO_TOOLTIP);
   }

   public Button(int var1, int var2, int var3, int var4, Component var5, Button.OnPress var6, Button.OnTooltip var7) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.onPress = â˜ƒ;
      this.onTooltip = â˜ƒ;
   }

   @Override
   public void onPress() {
      this.onPress.onPress(this);
   }

   @Override
   public void renderButton(PoseStack var1, int var2, int var3, float var4) {
      super.renderButton(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.isHovered()) {
         this.renderToolTip(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void renderToolTip(PoseStack var1, int var2, int var3) {
      this.onTooltip.onTooltip(this, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void updateNarration(NarrationElementOutput var1) {
      this.defaultButtonNarrationText(â˜ƒ);
      this.onTooltip.narrateTooltip(var1x -> â˜ƒ.add(NarratedElementType.HINT, var1x));
   }

   public interface OnPress {
      void onPress(Button var1);
   }

   public interface OnTooltip {
      void onTooltip(Button var1, PoseStack var2, int var3, int var4);

      default void narrateTooltip(Consumer<Component> var1) {
      }
   }
}
