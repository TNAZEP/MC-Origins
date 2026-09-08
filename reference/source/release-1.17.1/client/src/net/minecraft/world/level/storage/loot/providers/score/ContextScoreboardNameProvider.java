package net.minecraft.world.level.storage.loot.providers.score;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class ContextScoreboardNameProvider implements ScoreboardNameProvider {
   final LootContext.EntityTarget target;

   ContextScoreboardNameProvider(LootContext.EntityTarget var1) {
      this.target = â˜ƒ;
   }

   public static ScoreboardNameProvider forTarget(LootContext.EntityTarget var0) {
      return new ContextScoreboardNameProvider(â˜ƒ);
   }

   @Override
   public LootScoreProviderType getType() {
      return ScoreboardNameProviders.CONTEXT;
   }

   @Nullable
   @Override
   public String getScoreboardName(LootContext var1) {
      Entity â˜ƒ = â˜ƒ.getParamOrNull(this.target.getParam());
      return â˜ƒ != null ? â˜ƒ.getScoreboardName() : null;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(this.target.getParam());
   }

   public static class InlineSerializer implements GsonAdapterFactory.InlineSerializer<ContextScoreboardNameProvider> {
      public JsonElement serialize(ContextScoreboardNameProvider var1, JsonSerializationContext var2) {
         return â˜ƒ.serialize(â˜ƒ.target);
      }

      public ContextScoreboardNameProvider deserialize(JsonElement var1, JsonDeserializationContext var2) {
         LootContext.EntityTarget â˜ƒ = â˜ƒ.deserialize(â˜ƒ, LootContext.EntityTarget.class);
         return new ContextScoreboardNameProvider(â˜ƒ);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ContextScoreboardNameProvider> {
      public void serialize(JsonObject var1, ContextScoreboardNameProvider var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("target", â˜ƒ.target.name());
      }

      public ContextScoreboardNameProvider deserialize(JsonObject var1, JsonDeserializationContext var2) {
         LootContext.EntityTarget â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "target", â˜ƒ, LootContext.EntityTarget.class);
         return new ContextScoreboardNameProvider(â˜ƒ);
      }
   }
}
