package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PredicateManager extends SimpleJsonResourceReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = Deserializers.createConditionSerializer().create();
   private Map<ResourceLocation, LootItemCondition> conditions = ImmutableMap.of();

   public PredicateManager() {
      super(GSON, "predicates");
   }

   @Nullable
   public LootItemCondition get(ResourceLocation var1) {
      return (LootItemCondition)this.conditions.get(â˜ƒ);
   }

   protected void apply(Map<ResourceLocation, JsonElement> var1, ResourceManager var2, ProfilerFiller var3) {
      Builder<ResourceLocation, LootItemCondition> â˜ƒ = ImmutableMap.builder();
      â˜ƒ.forEach((var1x, var2x) -> {
         try {
            if (var2x.isJsonArray()) {
               LootItemCondition[] â˜ƒ = (LootItemCondition[])GSON.fromJson(var2x, LootItemCondition[].class);
               â˜ƒ.put(var1x, new PredicateManager.CompositePredicate(â˜ƒ));
            } else {
               LootItemCondition â˜ƒ = GSON.fromJson(var2x, LootItemCondition.class);
               â˜ƒ.put(var1x, â˜ƒ);
            }
         } catch (Exception var4xx) {
            LOGGER.error("Couldn't parse loot table {}", var1x, var4xx);
         }
      });
      Map<ResourceLocation, LootItemCondition> â˜ƒx = â˜ƒ.build();
      ValidationContext â˜ƒxx = new ValidationContext(LootContextParamSets.ALL_PARAMS, â˜ƒx::get, var0 -> null);
      â˜ƒx.forEach((var1x, var2x) -> var2x.validate(â˜ƒ.enterCondition("{" + var1x + "}", var1x)));
      â˜ƒxx.getProblems().forEach((var0, var1x) -> LOGGER.warn("Found validation problem in {}: {}", var0, var1x));
      this.conditions = â˜ƒx;
   }

   public Set<ResourceLocation> getKeys() {
      return Collections.unmodifiableSet(this.conditions.keySet());
   }

   static class CompositePredicate implements LootItemCondition {
      private final LootItemCondition[] terms;
      private final Predicate<LootContext> composedPredicate;

      CompositePredicate(LootItemCondition[] var1) {
         this.terms = â˜ƒ;
         this.composedPredicate = LootItemConditions.andConditions(â˜ƒ);
      }

      public final boolean test(LootContext var1) {
         return this.composedPredicate.test(â˜ƒ);
      }

      @Override
      public void validate(ValidationContext var1) {
         LootItemCondition.super.validate(â˜ƒ);

         for(int â˜ƒ = 0; â˜ƒ < this.terms.length; ++â˜ƒ) {
            this.terms[â˜ƒ].validate(â˜ƒ.forChild(".term[" + â˜ƒ + "]"));
         }
      }

      @Override
      public LootItemConditionType getType() {
         throw new UnsupportedOperationException();
      }
   }
}
