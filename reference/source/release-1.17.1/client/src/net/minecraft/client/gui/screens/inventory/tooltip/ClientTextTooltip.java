package net.minecraft.client.gui.screens.inventory.tooltip;

import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;

public class ClientTextTooltip implements ClientTooltipComponent {
   private final FormattedCharSequence text;

   public ClientTextTooltip(FormattedCharSequence var1) {
      this.text = â˜ƒ;
   }

   @Override
   public int getWidth(Font var1) {
      return â˜ƒ.width(this.text);
   }

   @Override
   public int getHeight() {
      return 10;
   }

   @Override
   public void renderText(Font var1, int var2, int var3, Matrix4f var4, MultiBufferSource.BufferSource var5) {
      â˜ƒ.drawInBatch(this.text, (float)â˜ƒ, (float)â˜ƒ, -1, true, â˜ƒ, â˜ƒ, false, 0, 15728880);
   }
}
