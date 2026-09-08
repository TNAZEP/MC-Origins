package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Set;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class LootItemBlockStatePropertyCondition implements LootItemCondition {
   final Block block;
   final StatePropertiesPredicate properties;

   LootItemBlockStatePropertyCondition(Block var1, StatePropertiesPredicate var2) {
      this.block = â˜ƒ;
      this.properties = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.BLOCK_STATE_PROPERTY;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.BLOCK_STATE);
   }

   public boolean test(LootContext var1) {
      BlockState â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.BLOCK_STATE);
      return â˜ƒ != null && â˜ƒ.is(this.block) && this.properties.matches(â˜ƒ);
   }

   public static LootItemBlockStatePropertyCondition.Builder hasBlockStateProperties(Block var0) {
      return new LootItemBlockStatePropertyCondition.Builder(â˜ƒ);
   }

   public static class Builder implements LootItemCondition.Builder {
      private final Block block;
      private StatePropertiesPredicate properties = StatePropertiesPredicate.ANY;

      public Builder(Block var1) {
         this.block = â˜ƒ;
      }

      public LootItemBlockStatePropertyCondition.Builder setProperties(StatePropertiesPredicate.Builder var1) {
         this.properties = â˜ƒ.build();
         return this;
      }

      @Override
      public LootItemCondition build() {
         return new LootItemBlockStatePropertyCondition(this.block, this.properties);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemBlockStatePropertyCondition> {
      public void serialize(JsonObject var1, LootItemBlockStatePropertyCondition var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("block", Registry.BLOCK.getKey(â˜ƒ.block).toString());
         â˜ƒ.add("properties", â˜ƒ.properties.serializeToJson());
      }

      public LootItemBlockStatePropertyCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "block"));
         Block â˜ƒx = (Block)Registry.BLOCK.getOptional(â˜ƒ).orElseThrow(() -> new IllegalArgumentException("Can't find block " + â˜ƒ));
         StatePropertiesPredicate â˜ƒxx = StatePropertiesPredicate.fromJson(â˜ƒ.get("properties"));
         â˜ƒxx.checkState(â˜ƒx.getStateDefinition(), var1x -> {
            throw new JsonSyntaxException("Block " + â˜ƒ + " has no property " + var1x);
         });
         return new LootItemBlockStatePropertyCondition(â˜ƒx, â˜ƒxx);
      }
   }
}
