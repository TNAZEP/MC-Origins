package net.minecraft.advancements.critereon;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeserializationContext {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ResourceLocation id;
   private final PredicateManager predicateManager;
   private final Gson predicateGson = Deserializers.createConditionSerializer().create();

   public DeserializationContext(ResourceLocation var1, PredicateManager var2) {
      this.id = â˜ƒ;
      this.predicateManager = â˜ƒ;
   }

   public final LootItemCondition[] deserializeConditions(JsonArray var1, String var2, LootContextParamSet var3) {
      LootItemCondition[] â˜ƒ = (LootItemCondition[])this.predicateGson.fromJson(â˜ƒ, LootItemCondition[].class);
      ValidationContext â˜ƒx = new ValidationContext(â˜ƒ, this.predicateManager::get, var0 -> null);

      for(LootItemCondition â˜ƒxx : â˜ƒ) {
         â˜ƒxx.validate(â˜ƒx);
         â˜ƒx.getProblems().forEach((var1x, var2x) -> LOGGER.warn("Found validation problem in advancement trigger {}/{}: {}", â˜ƒ, var1x, var2x));
      }

      return â˜ƒ;
   }

   public ResourceLocation getAdvancementId() {
      return this.id;
   }
}
