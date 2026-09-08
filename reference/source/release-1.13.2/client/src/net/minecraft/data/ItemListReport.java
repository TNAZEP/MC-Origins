package net.minecraft.data;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class ItemListReport implements IDataProvider {
   private final DataGenerator field_200401_a;

   public ItemListReport(DataGenerator var1) {
      this.field_200401_a = ☃;
   }

   @Override
   public void func_200398_a(DirectoryCache var1) throws IOException {
      JsonObject ☃ = new JsonObject();

      for(Item ☃x : IRegistry.field_212630_s) {
         ResourceLocation ☃xx = IRegistry.field_212630_s.func_177774_c(☃x);
         JsonObject ☃xxx = new JsonObject();
         ☃xxx.addProperty("protocol_id", Item.func_150891_b(☃x));
         ☃.add(☃xx.toString(), ☃xxx);
      }

      Path ☃x = this.field_200401_a.func_200391_b().resolve("reports/items.json");
      Files.createDirectories(☃x.getParent());
      BufferedWriter ☃xx = Files.newBufferedWriter(☃x, StandardCharsets.UTF_8);
      Throwable var18 = null;

      try {
         String ☃xxx = new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)☃);
         ☃xx.write(☃xxx);
      } catch (Throwable var14) {
         var18 = var14;
         throw var14;
      } finally {
         if (☃xx != null) {
            if (var18 != null) {
               try {
                  ☃xx.close();
               } catch (Throwable var13) {
                  var18.addSuppressed(var13);
               }
            } else {
               ☃xx.close();
            }
         }
      }
   }

   @Override
   public String func_200397_b() {
      return "Item List";
   }
}
