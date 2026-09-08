package net.minecraft.util.text.event;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ClickEvent {
   private final ClickEvent.Action field_150671_a;
   private final String field_150670_b;

   public ClickEvent(ClickEvent.Action var1, String var2) {
      this.field_150671_a = ☃;
      this.field_150670_b = ☃;
   }

   public ClickEvent.Action func_150669_a() {
      return this.field_150671_a;
   }

   public String func_150668_b() {
      return this.field_150670_b;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ != null && this.getClass() == ☃.getClass()) {
         ClickEvent ☃ = (ClickEvent)☃;
         if (this.field_150671_a != ☃.field_150671_a) {
            return false;
         } else {
            return this.field_150670_b != null ? this.field_150670_b.equals(☃.field_150670_b) : ☃.field_150670_b == null;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return "ClickEvent{action=" + this.field_150671_a + ", value='" + this.field_150670_b + '\'' + '}';
   }

   public int hashCode() {
      int ☃ = this.field_150671_a.hashCode();
      return 31 * ☃ + (this.field_150670_b != null ? this.field_150670_b.hashCode() : 0);
   }

   public static enum Action {
      OPEN_URL("open_url", true),
      OPEN_FILE("open_file", false),
      RUN_COMMAND("run_command", true),
      SUGGEST_COMMAND("suggest_command", true),
      CHANGE_PAGE("change_page", true);

      private static final Map<String, ClickEvent.Action> field_150679_e = (Map<String, ClickEvent.Action>)Arrays.stream(values())
         .collect(Collectors.toMap(ClickEvent.Action::func_150673_b, var0 -> var0));
      private final boolean field_150676_f;
      private final String field_150677_g;

      private Action(String var3, boolean var4) {
         this.field_150677_g = ☃;
         this.field_150676_f = ☃;
      }

      public boolean func_150674_a() {
         return this.field_150676_f;
      }

      public String func_150673_b() {
         return this.field_150677_g;
      }

      public static ClickEvent.Action func_150672_a(String var0) {
         return (ClickEvent.Action)field_150679_e.get(☃);
      }
   }
}
