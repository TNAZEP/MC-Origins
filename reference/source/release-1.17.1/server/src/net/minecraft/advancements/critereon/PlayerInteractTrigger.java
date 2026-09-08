package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class PlayerInteractTrigger extends SimpleCriterionTrigger<PlayerInteractTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("player_interacted_with_entity");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   protected PlayerInteractTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      ItemPredicate â˜ƒ = ItemPredicate.fromJson(â˜ƒ.get("item"));
      EntityPredicate.Composite â˜ƒx = EntityPredicate.Composite.fromJson(â˜ƒ, "entity", â˜ƒ);
      return new PlayerInteractTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, ItemStack var2, Entity var3) {
      LootContext â˜ƒ = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
      this.trigger(â˜ƒ, var2x -> var2x.matches(â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate item;
      private final EntityPredicate.Composite entity;

      public TriggerInstance(EntityPredicate.Composite var1, ItemPredicate var2, EntityPredicate.Composite var3) {
         super(PlayerInteractTrigger.ID, â˜ƒ);
         this.item = â˜ƒ;
         this.entity = â˜ƒ;
      }

      public static PlayerInteractTrigger.TriggerInstance itemUsedOnEntity(
         EntityPredicate.Composite var0, ItemPredicate.Builder var1, EntityPredicate.Composite var2
      ) {
         return new PlayerInteractTrigger.TriggerInstance(â˜ƒ, â˜ƒ.build(), â˜ƒ);
      }

      public boolean matches(ItemStack var1, LootContext var2) {
         return !this.item.matches(â˜ƒ) ? false : this.entity.matches(â˜ƒ);
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
