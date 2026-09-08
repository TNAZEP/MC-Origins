package net.minecraft.world.level.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DimensionDataStorage {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<String, SavedData> cache = Maps.newHashMap();
   private final DataFixer fixerUpper;
   private final File dataFolder;

   public DimensionDataStorage(File var1, DataFixer var2) {
      this.fixerUpper = â˜ƒ;
      this.dataFolder = â˜ƒ;
   }

   private File getDataFile(String var1) {
      return new File(this.dataFolder, â˜ƒ + ".dat");
   }

   public <T extends SavedData> T computeIfAbsent(Function<CompoundTag, T> var1, Supplier<T> var2, String var3) {
      T â˜ƒ = this.get(â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         return â˜ƒ;
      } else {
         T â˜ƒ = (T)â˜ƒ.get();
         this.set(â˜ƒ, â˜ƒ);
         return â˜ƒ;
      }
   }

   @Nullable
   public <T extends SavedData> T get(Function<CompoundTag, T> var1, String var2) {
      SavedData â˜ƒ = (SavedData)this.cache.get(â˜ƒ);
      if (â˜ƒ == null && !this.cache.containsKey(â˜ƒ)) {
         â˜ƒ = this.readSavedData(â˜ƒ, â˜ƒ);
         this.cache.put(â˜ƒ, â˜ƒ);
      }

      return (T)â˜ƒ;
   }

   @Nullable
   private <T extends SavedData> T readSavedData(Function<CompoundTag, T> var1, String var2) {
      try {
         File â˜ƒ = this.getDataFile(â˜ƒ);
         if (â˜ƒ.exists()) {
            CompoundTag â˜ƒx = this.readTagFromDisk(â˜ƒ, SharedConstants.getCurrentVersion().getWorldVersion());
            return (T)â˜ƒ.apply(â˜ƒx.getCompound("data"));
         }
      } catch (Exception var5) {
         LOGGER.error("Error loading saved data: {}", â˜ƒ, var5);
      }

      return null;
   }

   public void set(String var1, SavedData var2) {
      this.cache.put(â˜ƒ, â˜ƒ);
   }

   public CompoundTag readTagFromDisk(String var1, int var2) throws IOException {
      File â˜ƒ = this.getDataFile(â˜ƒ);
      FileInputStream â˜ƒx = new FileInputStream(â˜ƒ);

      CompoundTag var8;
      try {
         PushbackInputStream â˜ƒxx = new PushbackInputStream(â˜ƒx, 2);

         try {
            CompoundTag â˜ƒxxx;
            if (this.isGzip(â˜ƒxx)) {
               â˜ƒxxx = NbtIo.readCompressed(â˜ƒxx);
            } else {
               DataInputStream â˜ƒxxx = new DataInputStream(â˜ƒxx);

               try {
                  â˜ƒxxx = NbtIo.read(â˜ƒxxx);
               } catch (Throwable var13) {
                  try {
                     â˜ƒxxx.close();
                  } catch (Throwable var12) {
                     var13.addSuppressed(var12);
                  }

                  throw var13;
               }

               â˜ƒxxx.close();
            }

            int â˜ƒxxx = â˜ƒxxx.contains("DataVersion", 99) ? â˜ƒxxx.getInt("DataVersion") : 1343;
            var8 = NbtUtils.update(this.fixerUpper, DataFixTypes.SAVED_DATA, â˜ƒxxx, â˜ƒxxx, â˜ƒ);
         } catch (Throwable var14) {
            try {
               â˜ƒxx.close();
            } catch (Throwable var11) {
               var14.addSuppressed(var11);
            }

            throw var14;
         }

         â˜ƒxx.close();
      } catch (Throwable var15) {
         try {
            â˜ƒx.close();
         } catch (Throwable var10) {
            var15.addSuppressed(var10);
         }

         throw var15;
      }

      â˜ƒx.close();
      return var8;
   }

   private boolean isGzip(PushbackInputStream var1) throws IOException {
      byte[] â˜ƒ = new byte[2];
      boolean â˜ƒx = false;
      int â˜ƒxx = â˜ƒ.read(â˜ƒ, 0, 2);
      if (â˜ƒxx == 2) {
         int â˜ƒxxx = (â˜ƒ[1] & 255) << 8 | â˜ƒ[0] & 255;
         if (â˜ƒxxx == 35615) {
            â˜ƒx = true;
         }
      }

      if (â˜ƒxx != 0) {
         â˜ƒ.unread(â˜ƒ, 0, â˜ƒxx);
      }

      return â˜ƒx;
   }

   public void save() {
      this.cache.forEach((var1, var2) -> {
         if (var2 != null) {
            var2.save(this.getDataFile(var1));
         }
      });
   }
}
