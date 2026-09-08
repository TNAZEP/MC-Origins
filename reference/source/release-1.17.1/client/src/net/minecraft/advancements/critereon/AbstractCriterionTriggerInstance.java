package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractCriterionTriggerInstance implements CriterionTriggerInstance {
   private final ResourceLocation criterion;
   private final EntityPredicate.Composite player;

   public AbstractCriterionTriggerInstance(ResourceLocation var1, EntityPredicate.Composite var2) {
      this.criterion = â˜ƒ;
      this.player = â˜ƒ;
   }

   @Override
   public ResourceLocation getCriterion() {
      return this.criterion;
   }

   protected EntityPredicate.Composite getPlayerPredicate() {
      return this.player;
   }

   @Override
   public JsonObject serializeToJson(SerializationContext var1) {
      JsonObject â˜ƒ = new JsonObject();
      â˜ƒ.add("player", this.player.toJson(â˜ƒ));
      return â˜ƒ;
   }

   public String toString() {
      return "AbstractCriterionInstance{criterion=" + this.criterion + "}";
   }
}
