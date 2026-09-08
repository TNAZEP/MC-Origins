package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class TradeTrigger extends SimpleCriterionTrigger<TradeTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("villager_trade");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public TradeTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      EntityPredicate.Composite â˜ƒ = EntityPredicate.Composite.fromJson(â˜ƒ, "villager", â˜ƒ);
      ItemPredicate â˜ƒx = ItemPredicate.fromJson(â˜ƒ.get("item"));
      return new TradeTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, AbstractVillager var2, ItemStack var3) {
      LootContext â˜ƒ = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
      this.trigger(â˜ƒ, var2x -> var2x.matches(â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite villager;
      private final ItemPredicate item;

      public TriggerInstance(EntityPredicate.Composite var1, EntityPredicate.Composite var2, ItemPredicate var3) {
         super(TradeTrigger.ID, â˜ƒ);
         this.villager = â˜ƒ;
         this.item = â˜ƒ;
      }

      public static TradeTrigger.TriggerInstance tradedWithVillager() {
         return new TradeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, EntityPredicate.Composite.ANY, ItemPredicate.ANY);
      }

      public boolean matches(LootContext var1, ItemStack var2) {
         if (!this.villager.matches(â˜ƒ)) {
            return false;
         } else {
            return this.item.matches(â˜ƒ);
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("item", this.item.serializeToJson());
         â˜ƒ.add("villager", this.villager.toJson(â˜ƒ));
         return â˜ƒ;
      }
   }
}
