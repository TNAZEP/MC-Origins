package net.minecraft.world.level.storage.loot.providers.score;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class FixedScoreboardNameProvider implements ScoreboardNameProvider {
   final String name;

   FixedScoreboardNameProvider(String var1) {
      this.name = â˜ƒ;
   }

   public static ScoreboardNameProvider forName(String var0) {
      return new FixedScoreboardNameProvider(â˜ƒ);
   }

   @Override
   public LootScoreProviderType getType() {
      return ScoreboardNameProviders.FIXED;
   }

   public String getName() {
      return this.name;
   }

   @Nullable
   @Override
   public String getScoreboardName(LootContext var1) {
      return this.name;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of();
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<FixedScoreboardNameProvider> {
      public void serialize(JsonObject var1, FixedScoreboardNameProvider var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("name", â˜ƒ.name);
      }

      public FixedScoreboardNameProvider deserialize(JsonObject var1, JsonDeserializationContext var2) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "name");
         return new FixedScoreboardNameProvider(â˜ƒ);
      }
   }
}
