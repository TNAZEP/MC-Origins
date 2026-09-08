package net.minecraft.world.level.chunk.storage;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.LegacyStructureDataHandler;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class ChunkStorage implements AutoCloseable {
   private final IOWorker worker;
   protected final DataFixer fixerUpper;
   @Nullable
   private LegacyStructureDataHandler legacyStructureHandler;

   public ChunkStorage(File var1, DataFixer var2, boolean var3) {
      this.fixerUpper = â˜ƒ;
      this.worker = new IOWorker(â˜ƒ, â˜ƒ, "chunk");
   }

   public CompoundTag upgradeChunkTag(ResourceKey<Level> var1, Supplier<DimensionDataStorage> var2, CompoundTag var3) {
      int â˜ƒ = getVersion(â˜ƒ);
      int â˜ƒx = 1493;
      if (â˜ƒ < 1493) {
         â˜ƒ = NbtUtils.update(this.fixerUpper, DataFixTypes.CHUNK, â˜ƒ, â˜ƒ, 1493);
         if (â˜ƒ.getCompound("Level").getBoolean("hasLegacyStructureData")) {
            if (this.legacyStructureHandler == null) {
               this.legacyStructureHandler = LegacyStructureDataHandler.getLegacyStructureHandler(â˜ƒ, (DimensionDataStorage)â˜ƒ.get());
            }

            â˜ƒ = this.legacyStructureHandler.updateFromLegacy(â˜ƒ);
         }
      }

      â˜ƒ = NbtUtils.update(this.fixerUpper, DataFixTypes.CHUNK, â˜ƒ, Math.max(1493, â˜ƒ));
      if (â˜ƒ < SharedConstants.getCurrentVersion().getWorldVersion()) {
         â˜ƒ.putInt("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());
      }

      return â˜ƒ;
   }

   public static int getVersion(CompoundTag var0) {
      return â˜ƒ.contains("DataVersion", 99) ? â˜ƒ.getInt("DataVersion") : -1;
   }

   @Nullable
   public CompoundTag read(ChunkPos var1) throws IOException {
      return this.worker.load(â˜ƒ);
   }

   public void write(ChunkPos var1, CompoundTag var2) {
      this.worker.store(â˜ƒ, â˜ƒ);
      if (this.legacyStructureHandler != null) {
         this.legacyStructureHandler.removeIndex(â˜ƒ.toLong());
      }
   }

   public void flushWorker() {
      this.worker.synchronize(true).join();
   }

   public void close() throws IOException {
      this.worker.close();
   }
}
