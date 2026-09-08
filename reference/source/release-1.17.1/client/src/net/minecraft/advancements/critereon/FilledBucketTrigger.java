package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class FilledBucketTrigger extends SimpleCriterionTrigger<FilledBucketTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("filled_bucket");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public FilledBucketTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      ItemPredicate â˜ƒ = ItemPredicate.fromJson(â˜ƒ.get("item"));
      return new FilledBucketTrigger.TriggerInstance(â˜ƒ, â˜ƒ);
   }

   public void trigger(ServerPlayer var1, ItemStack var2) {
      this.trigger(â˜ƒ, var1x -> var1x.matches(â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate item;

      public TriggerInstance(EntityPredicate.Composite var1, ItemPredicate var2) {
         super(FilledBucketTrigger.ID, â˜ƒ);
         this.item = â˜ƒ;
      }

      public static FilledBucketTrigger.TriggerInstance filledBucket(ItemPredicate var0) {
         return new FilledBucketTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ);
      }

      public boolean matches(ItemStack var1) {
         return this.item.matches(â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("item", this.item.serializeToJson());
         return â˜ƒ;
      }
   }
}
