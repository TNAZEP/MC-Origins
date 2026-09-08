package net.minecraft.data.advancements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementProvider implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final DataGenerator generator;
   private final List<Consumer<Consumer<Advancement>>> tabs = ImmutableList.of(
      new TheEndAdvancements(), new HusbandryAdvancements(), new AdventureAdvancements(), new NetherAdvancements(), new StoryAdvancements()
   );

   public AdvancementProvider(DataGenerator var1) {
      this.generator = â˜ƒ;
   }

   @Override
   public void run(HashCache var1) {
      Path â˜ƒ = this.generator.getOutputFolder();
      Set<ResourceLocation> â˜ƒx = Sets.<ResourceLocation>newHashSet();
      Consumer<Advancement> â˜ƒxx = var3x -> {
         if (!â˜ƒ.add(var3x.getId())) {
            throw new IllegalStateException("Duplicate advancement " + var3x.getId());
         } else {
            Path â˜ƒ = createPath(â˜ƒ, var3x);

            try {
               DataProvider.save(GSON, â˜ƒ, var3x.deconstruct().serializeToJson(), â˜ƒ);
            } catch (IOException var6xx) {
               LOGGER.error("Couldn't save advancement {}", â˜ƒ, var6xx);
            }
         }
      };

      for(Consumer<Consumer<Advancement>> â˜ƒxxx : this.tabs) {
         â˜ƒxxx.accept(â˜ƒxx);
      }
   }

   private static Path createPath(Path var0, Advancement var1) {
      return â˜ƒ.resolve("data/" + â˜ƒ.getId().getNamespace() + "/advancements/" + â˜ƒ.getId().getPath() + ".json");
   }

   @Override
   public String getName() {
      return "Advancements";
   }
}
