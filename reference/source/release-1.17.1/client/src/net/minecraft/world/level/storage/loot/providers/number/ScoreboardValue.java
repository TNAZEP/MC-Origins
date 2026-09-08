package net.minecraft.world.level.storage.loot.providers.number;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.score.ContextScoreboardNameProvider;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

public class ScoreboardValue implements NumberProvider {
   final ScoreboardNameProvider target;
   final String score;
   final float scale;

   ScoreboardValue(ScoreboardNameProvider var1, String var2, float var3) {
      this.target = â˜ƒ;
      this.score = â˜ƒ;
      this.scale = â˜ƒ;
   }

   @Override
   public LootNumberProviderType getType() {
      return NumberProviders.SCORE;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.target.getReferencedContextParams();
   }

   public static ScoreboardValue fromScoreboard(LootContext.EntityTarget var0, String var1) {
      return fromScoreboard(â˜ƒ, â˜ƒ, 1.0F);
   }

   public static ScoreboardValue fromScoreboard(LootContext.EntityTarget var0, String var1, float var2) {
      return new ScoreboardValue(ContextScoreboardNameProvider.forTarget(â˜ƒ), â˜ƒ, â˜ƒ);
   }

   @Override
   public float getFloat(LootContext var1) {
      String â˜ƒ = this.target.getScoreboardName(â˜ƒ);
      if (â˜ƒ == null) {
         return 0.0F;
      } else {
         Scoreboard â˜ƒ = â˜ƒ.getLevel().getScoreboard();
         Objective â˜ƒx = â˜ƒ.getObjective(this.score);
         if (â˜ƒx == null) {
            return 0.0F;
         } else {
            return !â˜ƒ.hasPlayerScore(â˜ƒ, â˜ƒx) ? 0.0F : (float)â˜ƒ.getOrCreatePlayerScore(â˜ƒ, â˜ƒx).getScore() * this.scale;
         }
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ScoreboardValue> {
      public ScoreboardValue deserialize(JsonObject var1, JsonDeserializationContext var2) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "score");
         float â˜ƒx = GsonHelper.getAsFloat(â˜ƒ, "scale", 1.0F);
         ScoreboardNameProvider â˜ƒxx = GsonHelper.getAsObject(â˜ƒ, "target", â˜ƒ, ScoreboardNameProvider.class);
         return new ScoreboardValue(â˜ƒxx, â˜ƒ, â˜ƒx);
      }

      public void serialize(JsonObject var1, ScoreboardValue var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("score", â˜ƒ.score);
         â˜ƒ.add("target", â˜ƒ.serialize(â˜ƒ.target));
         â˜ƒ.addProperty("scale", â˜ƒ.scale);
      }
   }
}
