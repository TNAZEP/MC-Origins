package net.minecraft.client.multiplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.EmptyLevelChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientChunkCache extends ChunkSource {
   static final Logger LOGGER = LogManager.getLogger();
   private final LevelChunk emptyChunk;
   private final LevelLightEngine lightEngine;
   volatile ClientChunkCache.Storage storage;
   final ClientLevel level;

   public ClientChunkCache(ClientLevel var1, int var2) {
      this.level = â˜ƒ;
      this.emptyChunk = new EmptyLevelChunk(â˜ƒ, new ChunkPos(0, 0));
      this.lightEngine = new LevelLightEngine(this, true, â˜ƒ.dimensionType().hasSkyLight());
      this.storage = new ClientChunkCache.Storage(calculateStorageRange(â˜ƒ));
   }

   @Override
   public LevelLightEngine getLightEngine() {
      return this.lightEngine;
   }

   private static boolean isValidChunk(@Nullable LevelChunk var0, int var1, int var2) {
      if (â˜ƒ == null) {
         return false;
      } else {
         ChunkPos â˜ƒ = â˜ƒ.getPos();
         return â˜ƒ.x == â˜ƒ && â˜ƒ.z == â˜ƒ;
      }
   }

   public void drop(int var1, int var2) {
      if (this.storage.inRange(â˜ƒ, â˜ƒ)) {
         int â˜ƒ = this.storage.getIndex(â˜ƒ, â˜ƒ);
         LevelChunk â˜ƒx = this.storage.getChunk(â˜ƒ);
         if (isValidChunk(â˜ƒx, â˜ƒ, â˜ƒ)) {
            this.storage.replace(â˜ƒ, â˜ƒx, null);
         }
      }
   }

   @Nullable
   public LevelChunk getChunk(int var1, int var2, ChunkStatus var3, boolean var4) {
      if (this.storage.inRange(â˜ƒ, â˜ƒ)) {
         LevelChunk â˜ƒ = this.storage.getChunk(this.storage.getIndex(â˜ƒ, â˜ƒ));
         if (isValidChunk(â˜ƒ, â˜ƒ, â˜ƒ)) {
            return â˜ƒ;
         }
      }

      return â˜ƒ ? this.emptyChunk : null;
   }

   @Override
   public BlockGetter getLevel() {
      return this.level;
   }

   @Nullable
   public LevelChunk replaceWithPacketData(int var1, int var2, ChunkBiomeContainer var3, FriendlyByteBuf var4, CompoundTag var5, BitSet var6) {
      if (!this.storage.inRange(â˜ƒ, â˜ƒ)) {
         LOGGER.warn("Ignoring chunk since it's not in the view range: {}, {}", â˜ƒ, â˜ƒ);
         return null;
      } else {
         int â˜ƒ = this.storage.getIndex(â˜ƒ, â˜ƒ);
         LevelChunk â˜ƒx = (LevelChunk)this.storage.chunks.get(â˜ƒ);
         ChunkPos â˜ƒxx = new ChunkPos(â˜ƒ, â˜ƒ);
         if (!isValidChunk(â˜ƒx, â˜ƒ, â˜ƒ)) {
            â˜ƒx = new LevelChunk(this.level, â˜ƒxx, â˜ƒ);
            â˜ƒx.replaceWithPacketData(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            this.storage.replace(â˜ƒ, â˜ƒx);
         } else {
            â˜ƒx.replaceWithPacketData(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         LevelChunkSection[] â˜ƒ = â˜ƒx.getSections();
         LevelLightEngine â˜ƒx = this.getLightEngine();
         â˜ƒx.enableLightSources(â˜ƒxx, true);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.length; ++â˜ƒxx) {
            LevelChunkSection â˜ƒxxx = â˜ƒ[â˜ƒxx];
            int â˜ƒxxxx = this.level.getSectionYFromSectionIndex(â˜ƒxx);
            â˜ƒx.updateSectionStatus(SectionPos.of(â˜ƒ, â˜ƒxxxx, â˜ƒ), LevelChunkSection.isEmpty(â˜ƒxxx));
         }

         this.level.onChunkLoaded(â˜ƒxx);
         return â˜ƒx;
      }
   }

   @Override
   public void tick(BooleanSupplier var1) {
   }

   public void updateViewCenter(int var1, int var2) {
      this.storage.viewCenterX = â˜ƒ;
      this.storage.viewCenterZ = â˜ƒ;
   }

   public void updateViewRadius(int var1) {
      int â˜ƒ = this.storage.chunkRadius;
      int â˜ƒx = calculateStorageRange(â˜ƒ);
      if (â˜ƒ != â˜ƒx) {
         ClientChunkCache.Storage â˜ƒxx = new ClientChunkCache.Storage(â˜ƒx);
         â˜ƒxx.viewCenterX = this.storage.viewCenterX;
         â˜ƒxx.viewCenterZ = this.storage.viewCenterZ;

         for(int â˜ƒxxx = 0; â˜ƒxxx < this.storage.chunks.length(); ++â˜ƒxxx) {
            LevelChunk â˜ƒxxxx = (LevelChunk)this.storage.chunks.get(â˜ƒxxx);
            if (â˜ƒxxxx != null) {
               ChunkPos â˜ƒxxxxx = â˜ƒxxxx.getPos();
               if (â˜ƒxx.inRange(â˜ƒxxxxx.x, â˜ƒxxxxx.z)) {
                  â˜ƒxx.replace(â˜ƒxx.getIndex(â˜ƒxxxxx.x, â˜ƒxxxxx.z), â˜ƒxxxx);
               }
            }
         }

         this.storage = â˜ƒxx;
      }
   }

   private static int calculateStorageRange(int var0) {
      return Math.max(2, â˜ƒ) + 3;
   }

   @Override
   public String gatherStats() {
      return this.storage.chunks.length() + ", " + this.getLoadedChunksCount();
   }

   @Override
   public int getLoadedChunksCount() {
      return this.storage.chunkCount;
   }

   @Override
   public void onLightUpdate(LightLayer var1, SectionPos var2) {
      Minecraft.getInstance().levelRenderer.setSectionDirty(â˜ƒ.x(), â˜ƒ.y(), â˜ƒ.z());
   }

   final class Storage {
      final AtomicReferenceArray<LevelChunk> chunks;
      final int chunkRadius;
      private final int viewRange;
      volatile int viewCenterX;
      volatile int viewCenterZ;
      int chunkCount;

      Storage(int var2) {
         this.chunkRadius = â˜ƒ;
         this.viewRange = â˜ƒ * 2 + 1;
         this.chunks = new AtomicReferenceArray(this.viewRange * this.viewRange);
      }

      int getIndex(int var1, int var2) {
         return Math.floorMod(â˜ƒ, this.viewRange) * this.viewRange + Math.floorMod(â˜ƒ, this.viewRange);
      }

      protected void replace(int var1, @Nullable LevelChunk var2) {
         LevelChunk â˜ƒ = (LevelChunk)this.chunks.getAndSet(â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            --this.chunkCount;
            ClientChunkCache.this.level.unload(â˜ƒ);
         }

         if (â˜ƒ != null) {
            ++this.chunkCount;
         }
      }

      protected LevelChunk replace(int var1, LevelChunk var2, @Nullable LevelChunk var3) {
         if (this.chunks.compareAndSet(â˜ƒ, â˜ƒ, â˜ƒ) && â˜ƒ == null) {
            --this.chunkCount;
         }

         ClientChunkCache.this.level.unload(â˜ƒ);
         return â˜ƒ;
      }

      boolean inRange(int var1, int var2) {
         return Math.abs(â˜ƒ - this.viewCenterX) <= this.chunkRadius && Math.abs(â˜ƒ - this.viewCenterZ) <= this.chunkRadius;
      }

      @Nullable
      protected LevelChunk getChunk(int var1) {
         return (LevelChunk)this.chunks.get(â˜ƒ);
      }

      private void dumpChunks(String var1) {
         try {
            FileOutputStream â˜ƒ = new FileOutputStream(new File(â˜ƒ));

            try {
               int â˜ƒx = ClientChunkCache.this.storage.chunkRadius;

               for(int â˜ƒxx = this.viewCenterZ - â˜ƒx; â˜ƒxx <= this.viewCenterZ + â˜ƒx; ++â˜ƒxx) {
                  for(int â˜ƒxxx = this.viewCenterX - â˜ƒx; â˜ƒxxx <= this.viewCenterX + â˜ƒx; ++â˜ƒxxx) {
                     LevelChunk â˜ƒxxxx = (LevelChunk)ClientChunkCache.this.storage.chunks.get(ClientChunkCache.this.storage.getIndex(â˜ƒxxx, â˜ƒxx));
                     if (â˜ƒxxxx != null) {
                        ChunkPos â˜ƒxxxxx = â˜ƒxxxx.getPos();
                        â˜ƒ.write((â˜ƒxxxxx.x + "\t" + â˜ƒxxxxx.z + "\t" + â˜ƒxxxx.isEmpty() + "\n").getBytes(StandardCharsets.UTF_8));
                     }
                  }
               }
            } catch (Throwable var9) {
               try {
                  â˜ƒ.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }

               throw var9;
            }

            â˜ƒ.close();
         } catch (IOException var10) {
            ClientChunkCache.LOGGER.error(var10);
         }
      }
   }
}
