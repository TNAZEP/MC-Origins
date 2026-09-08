package net.minecraft.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Objects;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.JsonToNBT;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SNBTToNBTConverter implements IDataProvider {
   private static final Logger field_200424_a = LogManager.getLogger();
   private final DataGenerator field_200425_b;

   public SNBTToNBTConverter(DataGenerator var1) {
      this.field_200425_b = ☃;
   }

   @Override
   public void func_200398_a(DirectoryCache var1) throws IOException {
      Path ☃ = this.field_200425_b.func_200391_b();

      for(Path ☃x : this.field_200425_b.func_200389_a()) {
         Files.walk(☃x).filter(var0 -> var0.toString().endsWith(".snbt")).forEach(var4x -> this.func_208314_a(☃, var4x, this.func_200423_a(☃, var4x), ☃));
      }
   }

   @Override
   public String func_200397_b() {
      return "SNBT -> NBT";
   }

   private String func_200423_a(Path var1, Path var2) {
      String ☃ = ☃.relativize(☃).toString().replaceAll("\\\\", "/");
      return ☃.substring(0, ☃.length() - ".snbt".length());
   }

   private void func_208314_a(DirectoryCache var1, Path var2, String var3, Path var4) {
      try {
         Path ☃ = ☃.resolve(☃ + ".nbt");
         BufferedReader ☃x = Files.newBufferedReader(☃);
         Throwable var7 = null;

         try {
            String ☃xx = IOUtils.toString(☃x);
            String ☃xxx = field_208307_a.hashUnencodedChars(☃xx).toString();
            if (!Objects.equals(☃.func_208323_a(☃), ☃xxx) || !Files.exists(☃, new LinkOption[0])) {
               Files.createDirectories(☃.getParent());
               OutputStream ☃xxxx = Files.newOutputStream(☃);
               Throwable var11 = null;

               try {
                  CompressedStreamTools.func_74799_a(JsonToNBT.func_180713_a(☃xx), ☃xxxx);
               } catch (Throwable var38) {
                  var11 = var38;
                  throw var38;
               } finally {
                  if (☃xxxx != null) {
                     if (var11 != null) {
                        try {
                           ☃xxxx.close();
                        } catch (Throwable var37) {
                           var11.addSuppressed(var37);
                        }
                     } else {
                        ☃xxxx.close();
                     }
                  }
               }
            }

            ☃.func_208316_a(☃, ☃xxx);
         } catch (Throwable var40) {
            var7 = var40;
            throw var40;
         } finally {
            if (☃x != null) {
               if (var7 != null) {
                  try {
                     ☃x.close();
                  } catch (Throwable var36) {
                     var7.addSuppressed(var36);
                  }
               } else {
                  ☃x.close();
               }
            }
         }
      } catch (CommandSyntaxException var42) {
         field_200424_a.error("Couldn't convert {} from SNBT to NBT at {} as it's invalid SNBT", ☃, ☃, var42);
      } catch (IOException var43) {
         field_200424_a.error("Couldn't convert {} from SNBT to NBT at {}", ☃, ☃, var43);
      }
   }
}
