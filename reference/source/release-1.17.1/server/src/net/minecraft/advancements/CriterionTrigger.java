package net.minecraft.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;

public interface CriterionTrigger<T extends CriterionTriggerInstance> {
   ResourceLocation getId();

   void addPlayerListener(PlayerAdvancements var1, CriterionTrigger.Listener<T> var2);

   void removePlayerListener(PlayerAdvancements var1, CriterionTrigger.Listener<T> var2);

   void removePlayerListeners(PlayerAdvancements var1);

   T createInstance(JsonObject var1, DeserializationContext var2);

   public static class Listener<T extends CriterionTriggerInstance> {
      private final T trigger;
      private final Advancement advancement;
      private final String criterion;

      public Listener(T var1, Advancement var2, String var3) {
         this.trigger = â˜ƒ;
         this.advancement = â˜ƒ;
         this.criterion = â˜ƒ;
      }

      public T getTriggerInstance() {
         return this.trigger;
      }

      public void run(PlayerAdvancements var1) {
         â˜ƒ.award(this.advancement, this.criterion);
      }

      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
            CriterionTrigger.Listener<?> â˜ƒ = (CriterionTrigger.Listener)â˜ƒ;
            if (!this.trigger.equals(â˜ƒ.trigger)) {
               return false;
            } else {
               return !this.advancement.equals(â˜ƒ.advancement) ? false : this.criterion.equals(â˜ƒ.criterion);
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int â˜ƒ = this.trigger.hashCode();
         â˜ƒ = 31 * â˜ƒ + this.advancement.hashCode();
         return 31 * â˜ƒ + this.criterion.hashCode();
      }
   }
}
