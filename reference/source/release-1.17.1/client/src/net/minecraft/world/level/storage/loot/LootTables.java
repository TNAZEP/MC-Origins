package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTables extends SimpleJsonResourceReloadListener {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = Deserializers.createLootTableSerializer().create();
   private Map<ResourceLocation, LootTable> tables = ImmutableMap.of();
   private final PredicateManager predicateManager;

   public LootTables(PredicateManager var1) {
      super(GSON, "loot_tables");
      this.predicateManager = â˜ƒ;
   }

   public LootTable get(ResourceLocation var1) {
      return (LootTable)this.tables.getOrDefault(â˜ƒ, LootTable.EMPTY);
   }

   protected void apply(Map<ResourceLocation, JsonElement> var1, ResourceManager var2, ProfilerFiller var3) {
      Builder<ResourceLocation, LootTable> â˜ƒ = ImmutableMap.builder();
      JsonElement â˜ƒx = (JsonElement)â˜ƒ.remove(BuiltInLootTables.EMPTY);
      if (â˜ƒx != null) {
         LOGGER.warn("Datapack tried to redefine {} loot table, ignoring", BuiltInLootTables.EMPTY);
      }

      â˜ƒ.forEach((var1x, var2x) -> {
         try {
            LootTable â˜ƒ = GSON.fromJson(var2x, LootTable.class);
            â˜ƒ.put(var1x, â˜ƒ);
         } catch (Exception var4xx) {
            LOGGER.error("Couldn't parse loot table {}", var1x, var4xx);
         }
      });
      â˜ƒ.put(BuiltInLootTables.EMPTY, LootTable.EMPTY);
      ImmutableMap<ResourceLocation, LootTable> â˜ƒ = â˜ƒ.build();
      ValidationContext â˜ƒx = new ValidationContext(LootContextParamSets.ALL_PARAMS, this.predicateManager::get, â˜ƒ::get);
      â˜ƒ.forEach((var1x, var2x) -> validate(â˜ƒ, var1x, var2x));
      â˜ƒx.getProblems().forEach((var0, var1x) -> LOGGER.warn("Found validation problem in {}: {}", var0, var1x));
      this.tables = â˜ƒ;
   }

   public static void validate(ValidationContext var0, ResourceLocation var1, LootTable var2) {
      â˜ƒ.validate(â˜ƒ.setParams(â˜ƒ.getParamSet()).enterTable("{" + â˜ƒ + "}", â˜ƒ));
   }

   public static JsonElement serialize(LootTable var0) {
      return GSON.toJsonTree(â˜ƒ);
   }

   public Set<ResourceLocation> getIds() {
      return this.tables.keySet();
   }
}
