package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;

public class ChangeDimensionTrigger extends SimpleCriterionTrigger<ChangeDimensionTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("changed_dimension");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public ChangeDimensionTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      ResourceKey<Level> â˜ƒ = â˜ƒ.has("from")
         ? ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "from")))
         : null;
      ResourceKey<Level> â˜ƒx = â˜ƒ.has("to") ? ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "to"))) : null;
      return new ChangeDimensionTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, ResourceKey<Level> var2, ResourceKey<Level> var3) {
      this.trigger(â˜ƒ, var2x -> var2x.matches(â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      @Nullable
      private final ResourceKey<Level> from;
      @Nullable
      private final ResourceKey<Level> to;

      public TriggerInstance(EntityPredicate.Composite var1, @Nullable ResourceKey<Level> var2, @Nullable ResourceKey<Level> var3) {
         super(ChangeDimensionTrigger.ID, â˜ƒ);
         this.from = â˜ƒ;
         this.to = â˜ƒ;
      }

      public static ChangeDimensionTrigger.TriggerInstance changedDimension() {
         return new ChangeDimensionTrigger.TriggerInstance(EntityPredicate.Composite.ANY, null, null);
      }

      public static ChangeDimensionTrigger.TriggerInstance changedDimension(ResourceKey<Level> var0, ResourceKey<Level> var1) {
         return new ChangeDimensionTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, â˜ƒ);
      }

      public static ChangeDimensionTrigger.TriggerInstance changedDimensionTo(ResourceKey<Level> var0) {
         return new ChangeDimensionTrigger.TriggerInstance(EntityPredicate.Composite.ANY, null, â˜ƒ);
      }

      public static ChangeDimensionTrigger.TriggerInstance changedDimensionFrom(ResourceKey<Level> var0) {
         return new ChangeDimensionTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, null);
      }

      public boolean matches(ResourceKey<Level> var1, ResourceKey<Level> var2) {
         if (this.from != null && this.from != â˜ƒ) {
            return false;
         } else {
            return this.to == null || this.to == â˜ƒ;
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         if (this.from != null) {
            â˜ƒ.addProperty("from", this.from.location().toString());
         }

         if (this.to != null) {
            â˜ƒ.addProperty("to", this.to.location().toString());
         }

         return â˜ƒ;
      }
   }
}
