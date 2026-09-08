package net.minecraft.data.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockListReport implements DataProvider {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private final DataGenerator generator;

   public BlockListReport(DataGenerator var1) {
      this.generator = â˜ƒ;
   }

   @Override
   public void run(HashCache var1) throws IOException {
      JsonObject â˜ƒ = new JsonObject();

      for(Block â˜ƒx : Registry.BLOCK) {
         ResourceLocation â˜ƒxx = Registry.BLOCK.getKey(â˜ƒx);
         JsonObject â˜ƒxxx = new JsonObject();
         StateDefinition<Block, BlockState> â˜ƒxxxx = â˜ƒx.getStateDefinition();
         if (!â˜ƒxxxx.getProperties().isEmpty()) {
            JsonObject â˜ƒxxxxx = new JsonObject();

            for(Property<?> â˜ƒxxxxxx : â˜ƒxxxx.getProperties()) {
               JsonArray â˜ƒxxxxxxx = new JsonArray();

               for(Comparable<?> â˜ƒxxxxxxxx : â˜ƒxxxxxx.getPossibleValues()) {
                  â˜ƒxxxxxxx.add(Util.getPropertyName(â˜ƒxxxxxx, â˜ƒxxxxxxxx));
               }

               â˜ƒxxxxx.add(â˜ƒxxxxxx.getName(), â˜ƒxxxxxxx);
            }

            â˜ƒxxx.add("properties", â˜ƒxxxxx);
         }

         JsonArray â˜ƒxx = new JsonArray();

         for(BlockState â˜ƒxxx : â˜ƒxxxx.getPossibleStates()) {
            JsonObject â˜ƒxxxx = new JsonObject();
            JsonObject â˜ƒxxxxx = new JsonObject();

            for(Property<?> â˜ƒxxxxxx : â˜ƒxxxx.getProperties()) {
               â˜ƒxxxxx.addProperty(â˜ƒxxxxxx.getName(), Util.getPropertyName(â˜ƒxxxxxx, â˜ƒxxx.getValue(â˜ƒxxxxxx)));
            }

            if (â˜ƒxxxxx.size() > 0) {
               â˜ƒxxxx.add("properties", â˜ƒxxxxx);
            }

            â˜ƒxxxx.addProperty("id", Block.getId(â˜ƒxxx));
            if (â˜ƒxxx == â˜ƒx.defaultBlockState()) {
               â˜ƒxxxx.addProperty("default", true);
            }

            â˜ƒxx.add(â˜ƒxxxx);
         }

         â˜ƒxxx.add("states", â˜ƒxx);
         â˜ƒ.add(â˜ƒxx.toString(), â˜ƒxxx);
      }

      Path â˜ƒx = this.generator.getOutputFolder().resolve("reports/blocks.json");
      DataProvider.save(GSON, â˜ƒ, â˜ƒ, â˜ƒx);
   }

   @Override
   public String getName() {
      return "Block List";
   }
}
