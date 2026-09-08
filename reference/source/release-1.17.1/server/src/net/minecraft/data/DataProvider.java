package net.minecraft.data;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Objects;

public interface DataProvider {
   HashFunction SHA1 = Hashing.sha1();

   void run(HashCache var1) throws IOException;

   String getName();

   static void save(Gson var0, HashCache var1, JsonElement var2, Path var3) throws IOException {
      String â˜ƒ = â˜ƒ.toJson(â˜ƒ);
      String â˜ƒx = SHA1.hashUnencodedChars(â˜ƒ).toString();
      if (!Objects.equals(â˜ƒ.getHash(â˜ƒ), â˜ƒx) || !Files.exists(â˜ƒ, new LinkOption[0])) {
         Files.createDirectories(â˜ƒ.getParent());
         BufferedWriter â˜ƒxx = Files.newBufferedWriter(â˜ƒ);

         try {
            â˜ƒxx.write(â˜ƒ);
         } catch (Throwable var10) {
            if (â˜ƒxx != null) {
               try {
                  â˜ƒxx.close();
               } catch (Throwable var9) {
                  var10.addSuppressed(var9);
               }
            }

            throw var10;
         }

         if (â˜ƒxx != null) {
            â˜ƒxx.close();
         }
      }

      â˜ƒ.putNew(â˜ƒ, â˜ƒx);
   }
}
