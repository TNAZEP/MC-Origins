package net.minecraft.world.storage.loot.conditions;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;

public class EntityHasScore implements LootCondition {
   private final Map<String, RandomValueRange> field_186634_a;
   private final LootContext.EntityTarget field_186635_b;

   public EntityHasScore(Map<String, RandomValueRange> var1, LootContext.EntityTarget var2) {
      this.field_186634_a = ☃;
      this.field_186635_b = ☃;
   }

   @Override
   public boolean func_186618_a(Random var1, LootContext var2) {
      Entity ☃ = ☃.func_186494_a(this.field_186635_b);
      if (☃ == null) {
         return false;
      } else {
         Scoreboard ☃ = ☃.field_70170_p.func_96441_U();

         for(Entry<String, RandomValueRange> ☃x : this.field_186634_a.entrySet()) {
            if (!this.func_186631_a(☃, ☃, (String)☃x.getKey(), (RandomValueRange)☃x.getValue())) {
               return false;
            }
         }

         return true;
      }
   }

   protected boolean func_186631_a(Entity var1, Scoreboard var2, String var3, RandomValueRange var4) {
      ScoreObjective ☃ = ☃.func_96518_b(☃);
      if (☃ == null) {
         return false;
      } else {
         String ☃ = ☃.func_195047_I_();
         return !☃.func_178819_b(☃, ☃) ? false : ☃.func_186510_a(☃.func_96529_a(☃, ☃).func_96652_c());
      }
   }

   public static class Serializer extends LootCondition.Serializer<EntityHasScore> {
      protected Serializer() {
         super(new ResourceLocation("entity_scores"), EntityHasScore.class);
      }

      public void func_186605_a(JsonObject var1, EntityHasScore var2, JsonSerializationContext var3) {
         JsonObject ☃ = new JsonObject();

         for(Entry<String, RandomValueRange> ☃x : ☃.field_186634_a.entrySet()) {
            ☃.add((String)☃x.getKey(), ☃.serialize(☃x.getValue()));
         }

         ☃.add("scores", ☃);
         ☃.add("entity", ☃.serialize(☃.field_186635_b));
      }

      public EntityHasScore func_186603_b(JsonObject var1, JsonDeserializationContext var2) {
         Set<Entry<String, JsonElement>> ☃ = JsonUtils.func_152754_s(☃, "scores").entrySet();
         Map<String, RandomValueRange> ☃x = Maps.newLinkedHashMap();

         for(Entry<String, JsonElement> ☃xx : ☃) {
            ☃x.put(☃xx.getKey(), JsonUtils.func_188179_a((JsonElement)☃xx.getValue(), "score", ☃, RandomValueRange.class));
         }

         return new EntityHasScore(☃x, JsonUtils.func_188174_a(☃, "entity", ☃, LootContext.EntityTarget.class));
      }
   }
}
