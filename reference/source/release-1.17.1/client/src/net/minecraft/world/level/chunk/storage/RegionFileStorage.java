package net.minecraft.world.level.chunk.storage;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.ExceptionCollector;
import net.minecraft.world.level.ChunkPos;

public final class RegionFileStorage implements AutoCloseable {
   public static final String ANVIL_EXTENSION = ".mca";
   private static final int MAX_CACHE_SIZE = 256;
   private final Long2ObjectLinkedOpenHashMap<RegionFile> regionCache = new Long2ObjectLinkedOpenHashMap<>();
   private final File folder;
   private final boolean sync;

   RegionFileStorage(File var1, boolean var2) {
      this.folder = â˜ƒ;
      this.sync = â˜ƒ;
   }

   private RegionFile getRegionFile(ChunkPos var1) throws IOException {
      long â˜ƒ = ChunkPos.asLong(â˜ƒ.getRegionX(), â˜ƒ.getRegionZ());
      RegionFile â˜ƒx = this.regionCache.getAndMoveToFirst(â˜ƒ);
      if (â˜ƒx != null) {
         return â˜ƒx;
      } else {
         if (this.regionCache.size() >= 256) {
            this.regionCache.removeLast().close();
         }

         if (!this.folder.exists()) {
            this.folder.mkdirs();
         }

         File â˜ƒ = new File(this.folder, "r." + â˜ƒ.getRegionX() + "." + â˜ƒ.getRegionZ() + ".mca");
         RegionFile â˜ƒx = new RegionFile(â˜ƒ, this.folder, this.sync);
         this.regionCache.putAndMoveToFirst(â˜ƒ, â˜ƒx);
         return â˜ƒx;
      }
   }

   @Nullable
   public CompoundTag read(ChunkPos var1) throws IOException {
      RegionFile â˜ƒ = this.getRegionFile(â˜ƒ);
      DataInputStream â˜ƒx = â˜ƒ.getChunkDataInputStream(â˜ƒ);

      CompoundTag var8;
      label43: {
         try {
            if (â˜ƒx == null) {
               var8 = null;
               break label43;
            }

            var8 = NbtIo.read(â˜ƒx);
         } catch (Throwable var7) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }

         return var8;
      }

      if (â˜ƒx != null) {
         â˜ƒx.close();
      }

      return var8;
   }

   protected void write(ChunkPos var1, @Nullable CompoundTag var2) throws IOException {
      RegionFile â˜ƒ = this.getRegionFile(â˜ƒ);
      if (â˜ƒ == null) {
         â˜ƒ.clear(â˜ƒ);
      } else {
         DataOutputStream â˜ƒ = â˜ƒ.getChunkDataOutputStream(â˜ƒ);

         try {
            NbtIo.write(â˜ƒ, â˜ƒ);
         } catch (Throwable var8) {
            if (â˜ƒ != null) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (â˜ƒ != null) {
            â˜ƒ.close();
         }
      }
   }

   public void close() throws IOException {
      ExceptionCollector<IOException> â˜ƒ = new ExceptionCollector();

      for(RegionFile â˜ƒx : this.regionCache.values()) {
         try {
            â˜ƒx.close();
         } catch (IOException var5) {
            â˜ƒ.add(var5);
         }
      }

      â˜ƒ.throwIfPresent();
   }

   public void flush() throws IOException {
      for(RegionFile â˜ƒ : this.regionCache.values()) {
         â˜ƒ.flush();
      }
   }
}
