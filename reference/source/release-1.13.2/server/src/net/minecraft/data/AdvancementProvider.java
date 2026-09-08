package net.minecraft.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.advancements.AdventureAdvancements;
import net.minecraft.data.advancements.EndAdvancements;
import net.minecraft.data.advancements.HusbandryAdvancements;
import net.minecraft.data.advancements.NetherAdvancements;
import net.minecraft.data.advancements.StoryAdvancements;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementProvider implements IDataProvider {
   private static final Logger field_204023_a = LogManager.getLogger();
   private static final Gson field_204024_b = new GsonBuilder().setPrettyPrinting().create();
   private final DataGenerator field_204025_c;
   private final List<Consumer<Consumer<Advancement>>> field_204283_d = ImmutableList.of(
      new EndAdvancements(), new HusbandryAdvancements(), new AdventureAdvancements(), new NetherAdvancements(), new StoryAdvancements()
   );

   public AdvancementProvider(DataGenerator var1) {
      this.field_204025_c = ☃;
   }

   @Override
   public void func_200398_a(DirectoryCache var1) throws IOException {
      Path ☃ = this.field_204025_c.func_200391_b();
      Set<ResourceLocation> ☃x = Sets.<ResourceLocation>newHashSet();
      Consumer<Advancement> ☃xx = var4x -> {
         if (!☃.add(var4x.func_192067_g())) {
            throw new IllegalStateException("Duplicate advancement " + var4x.func_192067_g());
         } else {
            this.func_208309_a(
               ☃,
               var4x.func_192075_a().func_200273_b(),
               ☃.resolve("data/" + var4x.func_192067_g().func_110624_b() + "/advancements/" + var4x.func_192067_g().func_110623_a() + ".json")
            );
         }
      };

      for(Consumer<Consumer<Advancement>> ☃xxx : this.field_204283_d) {
         ☃xxx.accept(☃xx);
      }
   }

   private void func_208309_a(DirectoryCache var1, JsonObject var2, Path var3) {
      try {
         String ☃ = field_204024_b.toJson((JsonElement)☃);
         String ☃x = field_208307_a.hashUnencodedChars(☃).toString();
         if (!Objects.equals(☃.func_208323_a(☃), ☃x) || !Files.exists(☃, new LinkOption[0])) {
            Files.createDirectories(☃.getParent());
            BufferedWriter ☃xx = Files.newBufferedWriter(☃);
            Throwable var7 = null;

            try {
               ☃xx.write(☃);
            } catch (Throwable var17) {
               var7 = var17;
               throw var17;
            } finally {
               if (☃xx != null) {
                  if (var7 != null) {
                     try {
                        ☃xx.close();
                     } catch (Throwable var16) {
                        var7.addSuppressed(var16);
                     }
                  } else {
                     ☃xx.close();
                  }
               }
            }
         }

         ☃.func_208316_a(☃, ☃x);
      } catch (IOException var19) {
         field_204023_a.error("Couldn't save advancement {}", ☃, var19);
      }
   }

   @Override
   public String func_200397_b() {
      return "Advancements";
   }
}
