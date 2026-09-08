package net.minecraft.data.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;

public class RegistryDumpReport implements DataProvider {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final DataGenerator generator;

   public RegistryDumpReport(DataGenerator var1) {
      this.generator = â˜ƒ;
   }

   @Override
   public void run(HashCache var1) throws IOException {
      JsonObject â˜ƒ = new JsonObject();
      Registry.REGISTRY.keySet().forEach(var1x -> â˜ƒ.add(var1x.toString(), dumpRegistry(Registry.REGISTRY.get(var1x))));
      Path â˜ƒx = this.generator.getOutputFolder().resolve("reports/registries.json");
      DataProvider.save(GSON, â˜ƒ, â˜ƒ, â˜ƒx);
   }

   private static <T> JsonElement dumpRegistry(Registry<T> var0) {
      JsonObject â˜ƒ = new JsonObject();
      if (â˜ƒ instanceof DefaultedRegistry) {
         ResourceLocation â˜ƒx = ((DefaultedRegistry)â˜ƒ).getDefaultKey();
         â˜ƒ.addProperty("default", â˜ƒx.toString());
      }

      int â˜ƒ = Registry.REGISTRY.getId(â˜ƒ);
      â˜ƒ.addProperty("protocol_id", â˜ƒ);
      JsonObject â˜ƒx = new JsonObject();

      for(ResourceLocation â˜ƒxx : â˜ƒ.keySet()) {
         T â˜ƒxxx = â˜ƒ.get(â˜ƒxx);
         int â˜ƒxxxx = â˜ƒ.getId(â˜ƒxxx);
         JsonObject â˜ƒxxxxx = new JsonObject();
         â˜ƒxxxxx.addProperty("protocol_id", â˜ƒxxxx);
         â˜ƒx.add(â˜ƒxx.toString(), â˜ƒxxxxx);
      }

      â˜ƒ.add("entries", â˜ƒx);
      return â˜ƒ;
   }

   @Override
   public String getName() {
      return "Registry Dump";
   }
}
