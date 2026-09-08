package net.minecraft.network.chat;

import java.util.Arrays;
import java.util.Collection;

public class CommonComponents {
   public static final Component OPTION_ON = new TranslatableComponent("options.on");
   public static final Component OPTION_OFF = new TranslatableComponent("options.off");
   public static final Component GUI_DONE = new TranslatableComponent("gui.done");
   public static final Component GUI_CANCEL = new TranslatableComponent("gui.cancel");
   public static final Component GUI_YES = new TranslatableComponent("gui.yes");
   public static final Component GUI_NO = new TranslatableComponent("gui.no");
   public static final Component GUI_PROCEED = new TranslatableComponent("gui.proceed");
   public static final Component GUI_BACK = new TranslatableComponent("gui.back");
   public static final Component CONNECT_FAILED = new TranslatableComponent("connect.failed");
   public static final Component NEW_LINE = new TextComponent("\n");
   public static final Component NARRATION_SEPARATOR = new TextComponent(". ");

   public static Component optionStatus(boolean var0) {
      return â˜ƒ ? OPTION_ON : OPTION_OFF;
   }

   public static MutableComponent optionStatus(Component var0, boolean var1) {
      return new TranslatableComponent(â˜ƒ ? "options.on.composed" : "options.off.composed", â˜ƒ);
   }

   public static MutableComponent optionNameValue(Component var0, Component var1) {
      return new TranslatableComponent("options.generic_value", â˜ƒ, â˜ƒ);
   }

   public static MutableComponent joinForNarration(Component var0, Component var1) {
      return new TextComponent("").append(â˜ƒ).append(NARRATION_SEPARATOR).append(â˜ƒ);
   }

   public static Component joinLines(Component... var0) {
      return joinLines(Arrays.asList(â˜ƒ));
   }

   public static Component joinLines(Collection<? extends Component> var0) {
      return ComponentUtils.formatList(â˜ƒ, NEW_LINE);
   }
}
