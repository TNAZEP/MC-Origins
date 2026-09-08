package net.minecraft.util.text.event;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.util.text.ITextComponent;

public class HoverEvent {
   private final HoverEvent.Action field_150704_a;
   private final ITextComponent field_150703_b;

   public HoverEvent(HoverEvent.Action var1, ITextComponent var2) {
      this.field_150704_a = ☃;
      this.field_150703_b = ☃;
   }

   public HoverEvent.Action func_150701_a() {
      return this.field_150704_a;
   }

   public ITextComponent func_150702_b() {
      return this.field_150703_b;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ != null && this.getClass() == ☃.getClass()) {
         HoverEvent ☃ = (HoverEvent)☃;
         if (this.field_150704_a != ☃.field_150704_a) {
            return false;
         } else {
            return this.field_150703_b != null ? this.field_150703_b.equals(☃.field_150703_b) : ☃.field_150703_b == null;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return "HoverEvent{action=" + this.field_150704_a + ", value='" + this.field_150703_b + '\'' + '}';
   }

   public int hashCode() {
      int ☃ = this.field_150704_a.hashCode();
      return 31 * ☃ + (this.field_150703_b != null ? this.field_150703_b.hashCode() : 0);
   }

   public static enum Action {
      SHOW_TEXT("show_text", true),
      SHOW_ITEM("show_item", true),
      SHOW_ENTITY("show_entity", true);

      private static final Map<String, HoverEvent.Action> field_150690_d = (Map<String, HoverEvent.Action>)Arrays.stream(values())
         .collect(Collectors.toMap(HoverEvent.Action::func_150685_b, var0 -> var0));
      private final boolean field_150691_e;
      private final String field_150688_f;

      private Action(String var3, boolean var4) {
         this.field_150688_f = ☃;
         this.field_150691_e = ☃;
      }

      public boolean func_150686_a() {
         return this.field_150691_e;
      }

      public String func_150685_b() {
         return this.field_150688_f;
      }

      public static HoverEvent.Action func_150684_a(String var0) {
         return (HoverEvent.Action)field_150690_d.get(☃);
      }
   }
}
