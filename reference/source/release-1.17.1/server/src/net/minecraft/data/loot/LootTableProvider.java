package net.minecraft.data.loot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableProvider implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private final DataGenerator generator;
   private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> subProviders = ImmutableList.of(
      Pair.of(FishingLoot::new, LootContextParamSets.FISHING),
      Pair.of(ChestLoot::new, LootContextParamSets.CHEST),
      Pair.of(EntityLoot::new, LootContextParamSets.ENTITY),
      Pair.of(BlockLoot::new, LootContextParamSets.BLOCK),
      Pair.of(PiglinBarterLoot::new, LootContextParamSets.PIGLIN_BARTER),
      Pair.of(GiftLoot::new, LootContextParamSets.GIFT)
   );

   public LootTableProvider(DataGenerator var1) {
      this.generator = â˜ƒ;
   }

   @Override
   public void run(HashCache var1) {
      Path â˜ƒ = this.generator.getOutputFolder();
      Map<ResourceLocation, LootTable> â˜ƒx = Maps.<ResourceLocation, LootTable>newHashMap();
      this.subProviders.forEach(var1x -> ((Consumer)((Supplier)var1x.getFirst()).get()).accept((BiConsumer)(var2x, var3x) -> {
            if (â˜ƒ.put(var2x, var3x.setParamSet((LootContextParamSet)var1x.getSecond()).build()) != null) {
               throw new IllegalStateException("Duplicate loot table " + var2x);
            }
         }));
      ValidationContext â˜ƒxx = new ValidationContext(LootContextParamSets.ALL_PARAMS, var0 -> null, â˜ƒx::get);

      for(ResourceLocation â˜ƒxxx : Sets.difference(BuiltInLootTables.all(), â˜ƒx.keySet())) {
         â˜ƒxx.reportProblem("Missing built-in table: " + â˜ƒxxx);
      }

      â˜ƒx.forEach((var1x, var2x) -> LootTables.validate(â˜ƒ, var1x, var2x));
      Multimap<String, String> â˜ƒxxx = â˜ƒxx.getProblems();
      if (!â˜ƒxxx.isEmpty()) {
         â˜ƒxxx.forEach((var0, var1x) -> LOGGER.warn("Found validation problem in {}: {}", var0, var1x));
         throw new IllegalStateException("Failed to validate loot tables, see logs");
      } else {
         â˜ƒx.forEach((var2x, var3x) -> {
            Path â˜ƒ = createPath(â˜ƒ, var2x);

            try {
               DataProvider.save(GSON, â˜ƒ, LootTables.serialize(var3x), â˜ƒ);
            } catch (IOException var6) {
               LOGGER.error("Couldn't save loot table {}", â˜ƒ, var6);
            }
         });
      }
   }

   private static Path createPath(Path var0, ResourceLocation var1) {
      return â˜ƒ.resolve("data/" + â˜ƒ.getNamespace() + "/loot_tables/" + â˜ƒ.getPath() + ".json");
   }

   @Override
   public String getName() {
      return "LootTables";
   }
}
