package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ItemDurabilityTrigger extends SimpleCriterionTrigger<ItemDurabilityTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("item_durability_changed");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public ItemDurabilityTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      ItemPredicate â˜ƒ = ItemPredicate.fromJson(â˜ƒ.get("item"));
      MinMaxBounds.Ints â˜ƒx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("durability"));
      MinMaxBounds.Ints â˜ƒxx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("delta"));
      return new ItemDurabilityTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public void trigger(ServerPlayer var1, ItemStack var2, int var3) {
      this.trigger(â˜ƒ, var2x -> var2x.matches(â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate item;
      private final MinMaxBounds.Ints durability;
      private final MinMaxBounds.Ints delta;

      public TriggerInstance(EntityPredicate.Composite var1, ItemPredicate var2, MinMaxBounds.Ints var3, MinMaxBounds.Ints var4) {
         super(ItemDurabilityTrigger.ID, â˜ƒ);
         this.item = â˜ƒ;
         this.durability = â˜ƒ;
         this.delta = â˜ƒ;
      }

      public static ItemDurabilityTrigger.TriggerInstance changedDurability(ItemPredicate var0, MinMaxBounds.Ints var1) {
         return changedDurability(EntityPredicate.Composite.ANY, â˜ƒ, â˜ƒ);
      }

      public static ItemDurabilityTrigger.TriggerInstance changedDurability(EntityPredicate.Composite var0, ItemPredicate var1, MinMaxBounds.Ints var2) {
         return new ItemDurabilityTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒ, MinMaxBounds.Ints.ANY);
      }

      public boolean matches(ItemStack var1, int var2) {
         if (!this.item.matches(â˜ƒ)) {
            return false;
         } else if (!this.durability.matches(â˜ƒ.getMaxDamage() - â˜ƒ)) {
            return false;
         } else {
            return this.delta.matches(â˜ƒ.getDamageValue() - â˜ƒ);
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("item", this.item.serializeToJson());
         â˜ƒ.add("durability", this.durability.serializeToJson());
         â˜ƒ.add("delta", this.delta.serializeToJson());
         return â˜ƒ;
      }
   }
}
