package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class LootTableTrigger extends SimpleCriterionTrigger<LootTableTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("player_generates_container_loot");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   protected LootTableTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "loot_table"));
      return new LootTableTrigger.TriggerInstance(â˜ƒ, â˜ƒ);
   }

   public void trigger(ServerPlayer var1, ResourceLocation var2) {
      this.trigger(â˜ƒ, var1x -> var1x.matches(â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ResourceLocation lootTable;

      public TriggerInstance(EntityPredicate.Composite var1, ResourceLocation var2) {
         super(LootTableTrigger.ID, â˜ƒ);
         this.lootTable = â˜ƒ;
      }

      public static LootTableTrigger.TriggerInstance lootTableUsed(ResourceLocation var0) {
         return new LootTableTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ);
      }

      public boolean matches(ResourceLocation var1) {
         return this.lootTable.equals(â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.addProperty("loot_table", this.lootTable.toString());
         return â˜ƒ;
      }
   }
}
