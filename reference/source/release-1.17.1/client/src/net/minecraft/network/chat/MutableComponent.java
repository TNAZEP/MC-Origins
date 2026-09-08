package net.minecraft.network.chat;

import java.util.function.UnaryOperator;
import net.minecraft.ChatFormatting;

public interface MutableComponent extends Component {
   MutableComponent setStyle(Style var1);

   default MutableComponent append(String var1) {
      return this.append(new TextComponent(â˜ƒ));
   }

   MutableComponent append(Component var1);

   default MutableComponent withStyle(UnaryOperator<Style> var1) {
      this.setStyle((Style)â˜ƒ.apply(this.getStyle()));
      return this;
   }

   default MutableComponent withStyle(Style var1) {
      this.setStyle(â˜ƒ.applyTo(this.getStyle()));
      return this;
   }

   default MutableComponent withStyle(ChatFormatting... var1) {
      this.setStyle(this.getStyle().applyFormats(â˜ƒ));
      return this;
   }

   default MutableComponent withStyle(ChatFormatting var1) {
      this.setStyle(this.getStyle().applyFormat(â˜ƒ));
      return this;
   }
}
