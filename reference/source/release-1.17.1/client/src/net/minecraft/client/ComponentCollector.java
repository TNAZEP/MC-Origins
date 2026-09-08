package net.minecraft.client;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.FormattedText;

public class ComponentCollector {
   private final List<FormattedText> parts = Lists.<FormattedText>newArrayList();

   public void append(FormattedText var1) {
      this.parts.add(â˜ƒ);
   }

   @Nullable
   public FormattedText getResult() {
      if (this.parts.isEmpty()) {
         return null;
      } else {
         return this.parts.size() == 1 ? (FormattedText)this.parts.get(0) : FormattedText.composite(this.parts);
      }
   }

   public FormattedText getResultOrEmpty() {
      FormattedText â˜ƒ = this.getResult();
      return â˜ƒ != null ? â˜ƒ : FormattedText.EMPTY;
   }

   public void reset() {
      this.parts.clear();
   }
}
