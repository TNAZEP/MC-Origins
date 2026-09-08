package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class MatchTool implements LootItemCondition {
   final ItemPredicate predicate;

   public MatchTool(ItemPredicate var1) {
      this.predicate = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.MATCH_TOOL;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.TOOL);
   }

   public boolean test(LootContext var1) {
      ItemStack â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.TOOL);
      return â˜ƒ != null && this.predicate.matches(â˜ƒ);
   }

   public static LootItemCondition.Builder toolMatches(ItemPredicate.Builder var0) {
      return () -> new MatchTool(â˜ƒ.build());
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<MatchTool> {
      public void serialize(JsonObject var1, MatchTool var2, JsonSerializationContext var3) {
         â˜ƒ.add("predicate", â˜ƒ.predicate.serializeToJson());
      }

      public MatchTool deserialize(JsonObject var1, JsonDeserializationContext var2) {
         ItemPredicate â˜ƒ = ItemPredicate.fromJson(â˜ƒ.get("predicate"));
         return new MatchTool(â˜ƒ);
      }
   }
}
