package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

public class EntityHasScoreCondition implements LootItemCondition {
   final Map<String, IntRange> scores;
   final LootContext.EntityTarget entityTarget;

   EntityHasScoreCondition(Map<String, IntRange> var1, LootContext.EntityTarget var2) {
      this.scores = ImmutableMap.copyOf(â˜ƒ);
      this.entityTarget = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.ENTITY_SCORES;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return (Set<LootContextParam<?>>)Stream.concat(
            Stream.of(this.entityTarget.getParam()), this.scores.values().stream().flatMap(var0 -> var0.getReferencedContextParams().stream())
         )
         .collect(ImmutableSet.toImmutableSet());
   }

   public boolean test(LootContext var1) {
      Entity â˜ƒ = â˜ƒ.getParamOrNull(this.entityTarget.getParam());
      if (â˜ƒ == null) {
         return false;
      } else {
         Scoreboard â˜ƒ = â˜ƒ.level.getScoreboard();

         for(Entry<String, IntRange> â˜ƒx : this.scores.entrySet()) {
            if (!this.hasScore(â˜ƒ, â˜ƒ, â˜ƒ, (String)â˜ƒx.getKey(), (IntRange)â˜ƒx.getValue())) {
               return false;
            }
         }

         return true;
      }
   }

   protected boolean hasScore(LootContext var1, Entity var2, Scoreboard var3, String var4, IntRange var5) {
      Objective â˜ƒ = â˜ƒ.getObjective(â˜ƒ);
      if (â˜ƒ == null) {
         return false;
      } else {
         String â˜ƒ = â˜ƒ.getScoreboardName();
         return !â˜ƒ.hasPlayerScore(â˜ƒ, â˜ƒ) ? false : â˜ƒ.test(â˜ƒ, â˜ƒ.getOrCreatePlayerScore(â˜ƒ, â˜ƒ).getScore());
      }
   }

   public static EntityHasScoreCondition.Builder hasScores(LootContext.EntityTarget var0) {
      return new EntityHasScoreCondition.Builder(â˜ƒ);
   }

   public static class Builder implements LootItemCondition.Builder {
      private final Map<String, IntRange> scores = Maps.newHashMap();
      private final LootContext.EntityTarget entityTarget;

      public Builder(LootContext.EntityTarget var1) {
         this.entityTarget = â˜ƒ;
      }

      public EntityHasScoreCondition.Builder withScore(String var1, IntRange var2) {
         this.scores.put(â˜ƒ, â˜ƒ);
         return this;
      }

      @Override
      public LootItemCondition build() {
         return new EntityHasScoreCondition(this.scores, this.entityTarget);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<EntityHasScoreCondition> {
      public void serialize(JsonObject var1, EntityHasScoreCondition var2, JsonSerializationContext var3) {
         JsonObject â˜ƒ = new JsonObject();

         for(Entry<String, IntRange> â˜ƒx : â˜ƒ.scores.entrySet()) {
            â˜ƒ.add((String)â˜ƒx.getKey(), â˜ƒ.serialize(â˜ƒx.getValue()));
         }

         â˜ƒ.add("scores", â˜ƒ);
         â˜ƒ.add("entity", â˜ƒ.serialize(â˜ƒ.entityTarget));
      }

      public EntityHasScoreCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         Set<Entry<String, JsonElement>> â˜ƒ = GsonHelper.getAsJsonObject(â˜ƒ, "scores").entrySet();
         Map<String, IntRange> â˜ƒx = Maps.newLinkedHashMap();

         for(Entry<String, JsonElement> â˜ƒxx : â˜ƒ) {
            â˜ƒx.put((String)â˜ƒxx.getKey(), (IntRange)GsonHelper.convertToObject((JsonElement)â˜ƒxx.getValue(), "score", â˜ƒ, IntRange.class));
         }

         return new EntityHasScoreCondition(â˜ƒx, GsonHelper.getAsObject(â˜ƒ, "entity", â˜ƒ, LootContext.EntityTarget.class));
      }
   }
}
