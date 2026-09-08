package net.minecraft.network.chat;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ClickEvent {
   private final ClickEvent.Action action;
   private final String value;

   public ClickEvent(ClickEvent.Action var1, String var2) {
      this.action = â˜ƒ;
      this.value = â˜ƒ;
   }

   public ClickEvent.Action getAction() {
      return this.action;
   }

   public String getValue() {
      return this.value;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         ClickEvent â˜ƒ = (ClickEvent)â˜ƒ;
         if (this.action != â˜ƒ.action) {
            return false;
         } else {
            return this.value != null ? this.value.equals(â˜ƒ.value) : â˜ƒ.value == null;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return "ClickEvent{action=" + this.action + ", value='" + this.value + "'}";
   }

   public int hashCode() {
      int â˜ƒ = this.action.hashCode();
      return 31 * â˜ƒ + (this.value != null ? this.value.hashCode() : 0);
   }

   public static enum Action {
      OPEN_URL("open_url", true),
      OPEN_FILE("open_file", false),
      RUN_COMMAND("run_command", true),
      SUGGEST_COMMAND("suggest_command", true),
      CHANGE_PAGE("change_page", true),
      COPY_TO_CLIPBOARD("copy_to_clipboard", true);

      private static final Map<String, ClickEvent.Action> LOOKUP = (Map<String, ClickEvent.Action>)Arrays.stream(values())
         .collect(Collectors.toMap(ClickEvent.Action::getName, var0 -> var0));
      private final boolean allowFromServer;
      private final String name;

      private Action(String var3, boolean var4) {
         this.name = â˜ƒ;
         this.allowFromServer = â˜ƒ;
      }

      public boolean isAllowedFromServer() {
         return this.allowFromServer;
      }

      public String getName() {
         return this.name;
      }

      public static ClickEvent.Action getByName(String var0) {
         return (ClickEvent.Action)LOOKUP.get(â˜ƒ);
      }
   }
}
