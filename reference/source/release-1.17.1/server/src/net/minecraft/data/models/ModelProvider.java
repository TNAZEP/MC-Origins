package net.minecraft.data.models;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelProvider implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private final DataGenerator generator;

   public ModelProvider(DataGenerator var1) {
      this.generator = â˜ƒ;
   }

   @Override
   public void run(HashCache var1) {
      Path â˜ƒ = this.generator.getOutputFolder();
      Map<Block, BlockStateGenerator> â˜ƒx = Maps.<Block, BlockStateGenerator>newHashMap();
      Consumer<BlockStateGenerator> â˜ƒxx = var1x -> {
         Block â˜ƒ = var1x.getBlock();
         BlockStateGenerator â˜ƒx = (BlockStateGenerator)â˜ƒ.put(â˜ƒ, var1x);
         if (â˜ƒx != null) {
            throw new IllegalStateException("Duplicate blockstate definition for " + â˜ƒ);
         }
      };
      Map<ResourceLocation, Supplier<JsonElement>> â˜ƒxxx = Maps.newHashMap();
      Set<Item> â˜ƒxxxx = Sets.<Item>newHashSet();
      BiConsumer<ResourceLocation, Supplier<JsonElement>> â˜ƒxxxxx = (var1x, var2x) -> {
         Supplier<JsonElement> â˜ƒ = (Supplier)â˜ƒ.put(var1x, var2x);
         if (â˜ƒ != null) {
            throw new IllegalStateException("Duplicate model definition for " + var1x);
         }
      };
      Consumer<Item> â˜ƒxxxxxx = â˜ƒxxxx::add;
      new BlockModelGenerators(â˜ƒxx, â˜ƒxxxxx, â˜ƒxxxxxx).run();
      new ItemModelGenerators(â˜ƒxxxxx).run();
      List<Block> â˜ƒxxxxxxx = (List)Registry.BLOCK.stream().filter(var1x -> !â˜ƒ.containsKey(var1x)).collect(Collectors.toList());
      if (!â˜ƒxxxxxxx.isEmpty()) {
         throw new IllegalStateException("Missing blockstate definitions for: " + â˜ƒxxxxxxx);
      } else {
         Registry.BLOCK.forEach(var2x -> {
            Item â˜ƒ = (Item)Item.BY_BLOCK.get(var2x);
            if (â˜ƒ != null) {
               if (â˜ƒ.contains(â˜ƒ)) {
                  return;
               }

               ResourceLocation â˜ƒx = ModelLocationUtils.getModelLocation(â˜ƒ);
               if (!â˜ƒ.containsKey(â˜ƒx)) {
                  â˜ƒ.put(â˜ƒx, new DelegatedModel(ModelLocationUtils.getModelLocation(var2x)));
               }
            }
         });
         this.saveCollection(â˜ƒ, â˜ƒ, â˜ƒx, ModelProvider::createBlockStatePath);
         this.saveCollection(â˜ƒ, â˜ƒ, â˜ƒxxx, ModelProvider::createModelPath);
      }
   }

   private <T> void saveCollection(HashCache var1, Path var2, Map<T, ? extends Supplier<JsonElement>> var3, BiFunction<Path, T, Path> var4) {
      â˜ƒ.forEach((var3x, var4x) -> {
         Path â˜ƒ = (Path)â˜ƒ.apply(â˜ƒ, var3x);

         try {
            DataProvider.save(GSON, â˜ƒ, (JsonElement)var4x.get(), â˜ƒ);
         } catch (Exception var7) {
            LOGGER.error("Couldn't save {}", â˜ƒ, var7);
         }
      });
   }

   private static Path createBlockStatePath(Path var0, Block var1) {
      ResourceLocation â˜ƒ = Registry.BLOCK.getKey(â˜ƒ);
      return â˜ƒ.resolve("assets/" + â˜ƒ.getNamespace() + "/blockstates/" + â˜ƒ.getPath() + ".json");
   }

   private static Path createModelPath(Path var0, ResourceLocation var1) {
      return â˜ƒ.resolve("assets/" + â˜ƒ.getNamespace() + "/models/" + â˜ƒ.getPath() + ".json");
   }

   @Override
   public String getName() {
      return "Block State Definitions";
   }
}
