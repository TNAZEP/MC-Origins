package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class ItemPickedUpByEntityTrigger extends SimpleCriterionTrigger<ItemPickedUpByEntityTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("thrown_item_picked_up_by_entity");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   protected ItemPickedUpByEntityTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      ItemPredicate â˜ƒ = ItemPredicate.fromJson(â˜ƒ.get("item"));
      EntityPredicate.Composite â˜ƒx = EntityPredicate.Composite.fromJson(â˜ƒ, "entity", â˜ƒ);
      return new ItemPickedUpByEntityTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, ItemStack var2, Entity var3) {
      LootContext â˜ƒ = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
      this.trigger(â˜ƒ, var3x -> var3x.matches(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate item;
      private final EntityPredicate.Composite entity;

      public TriggerInstance(EntityPredicate.Composite var1, ItemPredicate var2, EntityPredicate.Composite var3) {
         super(ItemPickedUpByEntityTrigger.ID, â˜ƒ);
         this.item = â˜ƒ;
         this.entity = â˜ƒ;
      }

      public static ItemPickedUpByEntityTrigger.TriggerInstance itemPickedUpByEntity(
         EntityPredicate.Composite var0, ItemPredicate.Builder var1, EntityPredicate.Composite var2
      ) {
         return new ItemPickedUpByEntityTrigger.TriggerInstance(â˜ƒ, â˜ƒ.build(), â˜ƒ);
      }

      public boolean matches(ServerPlayer var1, ItemStack var2, LootContext var3) {
         if (!this.item.matches(â˜ƒ)) {
            return false;
         } else {
            return this.entity.matches(â˜ƒ);
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("item", this.item.serializeToJson());
         â˜ƒ.add("entity", this.entity.toJson(â˜ƒ));
         return â˜ƒ;
      }
   }
}
