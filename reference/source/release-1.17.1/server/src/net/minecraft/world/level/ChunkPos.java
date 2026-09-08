package net.minecraft.world.level;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;

public class ChunkPos {
   public static final long INVALID_CHUNK_POS = asLong(1875016, 1875016);
   private static final long COORD_BITS = 32L;
   private static final long COORD_MASK = 4294967295L;
   private static final int REGION_BITS = 5;
   private static final int REGION_MASK = 31;
   public final int x;
   public final int z;
   private static final int HASH_A = 1664525;
   private static final int HASH_C = 1013904223;
   private static final int HASH_Z_XOR = -559038737;

   public ChunkPos(int var1, int var2) {
      this.x = â˜ƒ;
      this.z = â˜ƒ;
   }

   public ChunkPos(BlockPos var1) {
      this.x = SectionPos.blockToSectionCoord(â˜ƒ.getX());
      this.z = SectionPos.blockToSectionCoord(â˜ƒ.getZ());
   }

   public ChunkPos(long var1) {
      this.x = (int)â˜ƒ;
      this.z = (int)(â˜ƒ >> 32);
   }

   public long toLong() {
      return asLong(this.x, this.z);
   }

   public static long asLong(int var0, int var1) {
      return (long)â˜ƒ & 4294967295L | ((long)â˜ƒ & 4294967295L) << 32;
   }

   public static long asLong(BlockPos var0) {
      return asLong(SectionPos.blockToSectionCoord(â˜ƒ.getX()), SectionPos.blockToSectionCoord(â˜ƒ.getZ()));
   }

   public static int getX(long var0) {
      return (int)(â˜ƒ & 4294967295L);
   }

   public static int getZ(long var0) {
      return (int)(â˜ƒ >>> 32 & 4294967295L);
   }

   public int hashCode() {
      int â˜ƒ = 1664525 * this.x + 1013904223;
      int â˜ƒx = 1664525 * (this.z ^ -559038737) + 1013904223;
      return â˜ƒ ^ â˜ƒx;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof ChunkPos)) {
         return false;
      } else {
         ChunkPos â˜ƒ = (ChunkPos)â˜ƒ;
         return this.x == â˜ƒ.x && this.z == â˜ƒ.z;
      }
   }

   public int getMiddleBlockX() {
      return this.getBlockX(8);
   }

   public int getMiddleBlockZ() {
      return this.getBlockZ(8);
   }

   public int getMinBlockX() {
      return SectionPos.sectionToBlockCoord(this.x);
   }

   public int getMinBlockZ() {
      return SectionPos.sectionToBlockCoord(this.z);
   }

   public int getMaxBlockX() {
      return this.getBlockX(15);
   }

   public int getMaxBlockZ() {
      return this.getBlockZ(15);
   }

   public int getRegionX() {
      return this.x >> 5;
   }

   public int getRegionZ() {
      return this.z >> 5;
   }

   public int getRegionLocalX() {
      return this.x & 31;
   }

   public int getRegionLocalZ() {
      return this.z & 31;
   }

   public BlockPos getBlockAt(int var1, int var2, int var3) {
      return new BlockPos(this.getBlockX(â˜ƒ), â˜ƒ, this.getBlockZ(â˜ƒ));
   }

   public int getBlockX(int var1) {
      return SectionPos.sectionToBlockCoord(this.x, â˜ƒ);
   }

   public int getBlockZ(int var1) {
      return SectionPos.sectionToBlockCoord(this.z, â˜ƒ);
   }

   public BlockPos getMiddleBlockPosition(int var1) {
      return new BlockPos(this.getMiddleBlockX(), â˜ƒ, this.getMiddleBlockZ());
   }

   public String toString() {
      return "[" + this.x + ", " + this.z + "]";
   }

   public BlockPos getWorldPosition() {
      return new BlockPos(this.getMinBlockX(), 0, this.getMinBlockZ());
   }

   public int getChessboardDistance(ChunkPos var1) {
      return Math.max(Math.abs(this.x - â˜ƒ.x), Math.abs(this.z - â˜ƒ.z));
   }

   public static Stream<ChunkPos> rangeClosed(ChunkPos var0, int var1) {
      return rangeClosed(new ChunkPos(â˜ƒ.x - â˜ƒ, â˜ƒ.z - â˜ƒ), new ChunkPos(â˜ƒ.x + â˜ƒ, â˜ƒ.z + â˜ƒ));
   }

   public static Stream<ChunkPos> rangeClosed(final ChunkPos var0, final ChunkPos var1) {
      int â˜ƒ = Math.abs(â˜ƒ.x - â˜ƒ.x) + 1;
      int â˜ƒx = Math.abs(â˜ƒ.z - â˜ƒ.z) + 1;
      final int â˜ƒxx = â˜ƒ.x < â˜ƒ.x ? 1 : -1;
      final int â˜ƒxxx = â˜ƒ.z < â˜ƒ.z ? 1 : -1;
      return StreamSupport.stream(new AbstractSpliterator<ChunkPos>((long)(â˜ƒ * â˜ƒx), 64) {
         @Nullable
         private ChunkPos pos;

         public boolean tryAdvance(Consumer<? super ChunkPos> var1x) {
            if (this.pos == null) {
               this.pos = â˜ƒ;
            } else {
               int â˜ƒ = this.pos.x;
               int â˜ƒx = this.pos.z;
               if (â˜ƒ == â˜ƒ.x) {
                  if (â˜ƒx == â˜ƒ.z) {
                     return false;
                  }

                  this.pos = new ChunkPos(â˜ƒ.x, â˜ƒx + â˜ƒ);
               } else {
                  this.pos = new ChunkPos(â˜ƒ + â˜ƒ, â˜ƒx);
               }
            }

            â˜ƒ.accept(this.pos);
            return true;
         }
      }, false);
   }
}
