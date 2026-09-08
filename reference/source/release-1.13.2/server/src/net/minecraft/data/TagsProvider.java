package net.minecraft.data;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TagsProvider<T> implements IDataProvider {
   private static final Logger field_200436_d = LogManager.getLogger();
   private static final Gson field_200437_e = new GsonBuilder().setPrettyPrinting().create();
   protected final DataGenerator field_200433_a;
   protected final IRegistry<T> field_200435_c;
   protected final Map<Tag<T>, Tag.Builder<T>> field_200434_b = Maps.<Tag<T>, Tag.Builder<T>>newLinkedHashMap();

   protected TagsProvider(DataGenerator var1, IRegistry<T> var2) {
      this.field_200433_a = ☃;
      this.field_200435_c = ☃;
   }

   protected abstract void func_200432_c();

   @Override
   public void func_200398_a(DirectoryCache var1) throws IOException {
      this.field_200434_b.clear();
      this.func_200432_c();
      TagCollection<T> ☃ = new TagCollection<>(var0 -> false, var0 -> null, "", false, "generated");

      for(Entry<Tag<T>, Tag.Builder<T>> ☃x : this.field_200434_b.entrySet()) {
         ResourceLocation ☃xx = ((Tag)☃x.getKey()).func_199886_b();
         if (!((Tag.Builder)☃x.getValue()).func_200160_a(☃::func_199910_a)) {
            throw new UnsupportedOperationException("Unsupported referencing of tags!");
         }

         Tag<T> ☃xx = ((Tag.Builder)☃x.getValue()).func_200051_a(☃xx);
         JsonObject ☃xxx = ☃xx.func_200571_a(this.field_200435_c::func_177774_c);
         Path ☃xxxx = this.func_200431_a(☃xx);
         ☃.func_199912_a(☃xx);
         this.func_200429_a(☃);

         try {
            String ☃xxxxx = field_200437_e.toJson((JsonElement)☃xxx);
            String ☃xxxxxx = field_208307_a.hashUnencodedChars(☃xxxxx).toString();
            if (!Objects.equals(☃.func_208323_a(☃xxxx), ☃xxxxxx) || !Files.exists(☃xxxx, new LinkOption[0])) {
               Files.createDirectories(☃xxxx.getParent());
               BufferedWriter ☃xxxxxxx = Files.newBufferedWriter(☃xxxx);
               Throwable var12 = null;

               try {
                  ☃xxxxxxx.write(☃xxxxx);
               } catch (Throwable var22) {
                  var12 = var22;
                  throw var22;
               } finally {
                  if (☃xxxxxxx != null) {
                     if (var12 != null) {
                        try {
                           ☃xxxxxxx.close();
                        } catch (Throwable var21) {
                           var12.addSuppressed(var21);
                        }
                     } else {
                        ☃xxxxxxx.close();
                     }
                  }
               }
            }

            ☃.func_208316_a(☃xxxx, ☃xxxxxx);
         } catch (IOException var24) {
            field_200436_d.error("Couldn't save tags to {}", ☃xxxx, var24);
         }
      }
   }

   protected abstract void func_200429_a(TagCollection<T> var1);

   protected abstract Path func_200431_a(ResourceLocation var1);

   protected Tag.Builder<T> func_200426_a(Tag<T> var1) {
      return (Tag.Builder<T>)this.field_200434_b.computeIfAbsent(☃, var0 -> Tag.Builder.func_200047_a());
   }
}
