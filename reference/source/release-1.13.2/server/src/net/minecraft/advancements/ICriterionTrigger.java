package net.minecraft.advancements;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

public interface ICriterionTrigger<T extends ICriterionInstance> {
   ResourceLocation func_192163_a();

   void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<T> var2);

   void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<T> var2);

   void func_192167_a(PlayerAdvancements var1);

   T func_192166_a(JsonObject var1, JsonDeserializationContext var2);

   public static class Listener<T extends ICriterionInstance> {
      private final T field_192160_a;
      private final Advancement field_192161_b;
      private final String field_192162_c;

      public Listener(T var1, Advancement var2, String var3) {
         this.field_192160_a = ☃;
         this.field_192161_b = ☃;
         this.field_192162_c = ☃;
      }

      public T func_192158_a() {
         return this.field_192160_a;
      }

      public void func_192159_a(PlayerAdvancements var1) {
         ☃.func_192750_a(this.field_192161_b, this.field_192162_c);
      }

      public boolean equals(Object var1) {
         if (this == ☃) {
            return true;
         } else if (☃ != null && this.getClass() == ☃.getClass()) {
            ICriterionTrigger.Listener<?> ☃ = (ICriterionTrigger.Listener)☃;
            if (!this.field_192160_a.equals(☃.field_192160_a)) {
               return false;
            } else {
               return !this.field_192161_b.equals(☃.field_192161_b) ? false : this.field_192162_c.equals(☃.field_192162_c);
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int ☃ = this.field_192160_a.hashCode();
         ☃ = 31 * ☃ + this.field_192161_b.hashCode();
         return 31 * ☃ + this.field_192162_c.hashCode();
      }
   }
}
