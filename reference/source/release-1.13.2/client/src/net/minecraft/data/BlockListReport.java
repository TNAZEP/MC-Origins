package net.minecraft.data;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.IRegistry;

public class BlockListReport implements IDataProvider {
   private final DataGenerator field_200399_a;

   public BlockListReport(DataGenerator var1) {
      this.field_200399_a = ☃;
   }

   @Override
   public void func_200398_a(DirectoryCache var1) throws IOException {
      JsonObject ☃ = new JsonObject();

      for(Block ☃x : IRegistry.field_212618_g) {
         ResourceLocation ☃xx = IRegistry.field_212618_g.func_177774_c(☃x);
         JsonObject ☃xxx = new JsonObject();
         StateContainer<Block, IBlockState> ☃xxxx = ☃x.func_176194_O();
         if (!☃xxxx.func_177623_d().isEmpty()) {
            JsonObject ☃xxxxx = new JsonObject();

            for(IProperty<?> ☃xxxxxx : ☃xxxx.func_177623_d()) {
               JsonArray ☃xxxxxxx = new JsonArray();

               for(Comparable<?> ☃xxxxxxxx : ☃xxxxxx.func_177700_c()) {
                  ☃xxxxxxx.add(Util.func_200269_a(☃xxxxxx, ☃xxxxxxxx));
               }

               ☃xxxxx.add(☃xxxxxx.func_177701_a(), ☃xxxxxxx);
            }

            ☃xxx.add("properties", ☃xxxxx);
         }

         JsonArray ☃xx = new JsonArray();

         for(IBlockState ☃xxx : ☃xxxx.func_177619_a()) {
            JsonObject ☃xxxx = new JsonObject();
            JsonObject ☃xxxxx = new JsonObject();

            for(IProperty<?> ☃xxxxxx : ☃xxxx.func_177623_d()) {
               ☃xxxxx.addProperty(☃xxxxxx.func_177701_a(), Util.func_200269_a(☃xxxxxx, ☃xxx.func_177229_b(☃xxxxxx)));
            }

            if (☃xxxxx.size() > 0) {
               ☃xxxx.add("properties", ☃xxxxx);
            }

            ☃xxxx.addProperty("id", Block.func_196246_j(☃xxx));
            if (☃xxx == ☃x.func_176223_P()) {
               ☃xxxx.addProperty("default", true);
            }

            ☃xx.add(☃xxxx);
         }

         ☃xxx.add("states", ☃xx);
         ☃.add(☃xx.toString(), ☃xxx);
      }

      Path ☃x = this.field_200399_a.func_200391_b().resolve("reports/blocks.json");
      Files.createDirectories(☃x.getParent());
      BufferedWriter ☃xx = Files.newBufferedWriter(☃x, StandardCharsets.UTF_8);
      Throwable var26 = null;

      try {
         String ☃xxx = new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)☃);
         ☃xx.write(☃xxx);
      } catch (Throwable var22) {
         var26 = var22;
         throw var22;
      } finally {
         if (☃xx != null) {
            if (var26 != null) {
               try {
                  ☃xx.close();
               } catch (Throwable var21) {
                  var26.addSuppressed(var21);
               }
            } else {
               ☃xx.close();
            }
         }
      }
   }

   @Override
   public String func_200397_b() {
      return "Block List";
   }
}
