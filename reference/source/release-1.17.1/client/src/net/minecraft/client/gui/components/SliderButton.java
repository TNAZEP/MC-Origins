package net.minecraft.client.gui.components;

import java.util.List;
import net.minecraft.client.Options;
import net.minecraft.client.ProgressOption;
import net.minecraft.util.FormattedCharSequence;

public class SliderButton extends AbstractOptionSliderButton implements TooltipAccessor {
   private final ProgressOption option;
   private final List<FormattedCharSequence> tooltip;

   public SliderButton(Options var1, int var2, int var3, int var4, int var5, ProgressOption var6, List<FormattedCharSequence> var7) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (double)((float)â˜ƒ.toPct(â˜ƒ.get(â˜ƒ))));
      this.option = â˜ƒ;
      this.tooltip = â˜ƒ;
      this.updateMessage();
   }

   @Override
   protected void applyValue() {
      this.option.set(this.options, this.option.toValue(this.value));
      this.options.save();
   }

   @Override
   protected void updateMessage() {
      this.setMessage(this.option.getMessage(this.options));
   }

   @Override
   public List<FormattedCharSequence> getTooltip() {
      return this.tooltip;
   }
}
