package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.components.TooltipAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class OptionsSubScreen extends Screen {
   protected final Screen lastScreen;
   protected final Options options;

   public OptionsSubScreen(Screen var1, Options var2, Component var3) {
      super(â˜ƒ);
      this.lastScreen = â˜ƒ;
      this.options = â˜ƒ;
   }

   @Override
   public void removed() {
      this.minecraft.options.save();
   }

   @Override
   public void onClose() {
      this.minecraft.setScreen(this.lastScreen);
   }

   public static List<FormattedCharSequence> tooltipAt(OptionsList var0, int var1, int var2) {
      Optional<AbstractWidget> â˜ƒ = â˜ƒ.getMouseOver((double)â˜ƒ, (double)â˜ƒ);
      return (List<FormattedCharSequence>)(â˜ƒ.isPresent() && â˜ƒ.get() instanceof TooltipAccessor
         ? ((TooltipAccessor)â˜ƒ.get()).getTooltip()
         : ImmutableList.of());
   }
}
