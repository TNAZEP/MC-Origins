package net.minecraft.data.worldgen.biome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeReport implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final DataGenerator generator;

   public BiomeReport(DataGenerator var1) {
      this.generator = â˜ƒ;
   }

   @Override
   public void run(HashCache var1) {
      Path â˜ƒ = this.generator.getOutputFolder();

      for(Entry<ResourceKey<Biome>, Biome> â˜ƒx : BuiltinRegistries.BIOME.entrySet()) {
         Path â˜ƒxx = createPath(â˜ƒ, ((ResourceKey)â˜ƒx.getKey()).location());
         Biome â˜ƒxxx = (Biome)â˜ƒx.getValue();
         Function<Supplier<Biome>, DataResult<JsonElement>> â˜ƒxxxx = JsonOps.INSTANCE.withEncoder(Biome.CODEC);

         try {
            Optional<JsonElement> â˜ƒxxxxx = ((DataResult)â˜ƒxxxx.apply((Supplier)() -> â˜ƒ)).result();
            if (â˜ƒxxxxx.isPresent()) {
               DataProvider.save(GSON, â˜ƒ, (JsonElement)â˜ƒxxxxx.get(), â˜ƒxx);
            } else {
               LOGGER.error("Couldn't serialize biome {}", â˜ƒxx);
            }
         } catch (IOException var9) {
            LOGGER.error("Couldn't save biome {}", â˜ƒxx, var9);
         }
      }
   }

   private static Path createPath(Path var0, ResourceLocation var1) {
      return â˜ƒ.resolve("reports/biomes/" + â˜ƒ.getPath() + ".json");
   }

   @Override
   public String getName() {
      return "Biomes";
   }
}
